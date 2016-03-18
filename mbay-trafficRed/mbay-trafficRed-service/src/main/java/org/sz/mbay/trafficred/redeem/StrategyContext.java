package org.sz.mbay.trafficred.redeem;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sz.mbay.trafficred.bean.ClassifyPackageNums;
import org.sz.mbay.trafficred.redeem.strategy.Ignore;
import org.sz.mbay.trafficred.redeem.strategy.MbayMustHit;
import org.sz.mbay.trafficred.redeem.strategy.MbayNormal;
import org.sz.mbay.trafficred.redeem.strategy.TrafficMbayCycleHit;
import org.sz.mbay.trafficred.redeem.strategy.TrafficMustHit;
import org.sz.mbay.trafficred.redeem.strategy.TrafficNormal;
import org.sz.mbay.trafficred.result.RedeemResult;
import org.sz.mbay.trafficred.result.Result;
import org.sz.mbay.trafficred.service.TrafficRedCampaignMbayService;
import org.sz.mbay.trafficred.service.TrafficRedCampaignPackageService;
import org.sz.mbay.trafficred.service.TrafficRedCampaignService;

/**
 * 兑换策略表
 * 
 * traffic：红包产品
 * mbay：MB产品
 * traffic/mbay: 1-存在产品, 0-不存在产品
 * 
 * @author jerry
 */
@Component
public class StrategyContext {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StrategyContext.class);
	
	private static TrafficRedCampaignService campaignService;
	private static TrafficRedCampaignPackageService packageService;
	private static TrafficRedCampaignMbayService mbayService;
	
	/**
	 * Ignore
	 * 无任何产品，忽略概率，不中
	 */
	public static final int IGNORE = 0;
	
	/**
	 * Traffic_Normal
	 * 正常中奖概率，对于流量产品中/不中
	 */
	public static final int TNOR = 1;
	
	/**
	 * Mbay_Must_Hit
	 * MB必中（产品耗尽除外）
	 */
	public static final int MMH = 2;
	
	/**
	 * Traffic_Must_Hit
	 * 红包必中（产品耗尽除外）
	 */
	public static final int TMH = 3;
	
	/**
	 * Traffic_Mbay_Cycle_Hit
	 * 循环必中，当一个产品不满足时抽取另一种产品（所有产品耗尽除外）
	 */
	public static final int TMCH = 4;
	
	/**
	 * Mbay_Normal
	 * 正常中奖概率，对于美贝产品中/不中
	 */
	public static final int MNOR = 5;
	
	// --------------------------------------------------------
	// | - - - - traffic - - - - - | mbay | - - -rate(%) - - -|
	// --------------------------------------------------------
	// | MOBILE | UNICOM | TELECOM | - - -| 0 | (0,100) | 100 |
	// --------------------------------------------------------
	
	public static final int[][][] table = new int[][][]
	{
			{ { 0, 0, 0 }, { 0 }, { IGNORE, IGNORE, IGNORE } },
			{ { 0, 0, 0 }, { 1 }, { IGNORE, MNOR, MMH } },
			{ { 0, 0, 1 }, { 0 }, { IGNORE, TNOR, TMH } },
			{ { 0, 0, 1 }, { 1 }, { MMH, TMCH, TMH } },
			{ { 0, 1, 0 }, { 0 }, { IGNORE, TNOR, TMH } },
			{ { 0, 1, 0 }, { 1 }, { MMH, TMCH, TMH } },
			{ { 0, 1, 1 }, { 0 }, { IGNORE, TNOR, TMH } },
			{ { 0, 1, 1 }, { 1 }, { MMH, TMCH, TMH } },
			{ { 1, 0, 0 }, { 0 }, { IGNORE, TNOR, TMH } },
			{ { 1, 0, 0 }, { 1 }, { MMH, TMCH, TMH } },
			{ { 1, 0, 1 }, { 0 }, { IGNORE, TNOR, TMH } },
			{ { 1, 0, 1 }, { 1 }, { MMH, TMCH, TMH } },
			{ { 1, 1, 0 }, { 0 }, { IGNORE, TNOR, TMH } },
			{ { 1, 1, 0 }, { 1 }, { MMH, TMCH, TMH } },
			{ { 1, 1, 1 }, { 0 }, { IGNORE, TNOR, TMH } },
			{ { 1, 1, 1 }, { 1 }, { MMH, TMCH, TMH } },
	};
	
	/**
	 * 执行
	 * 1.查询出对应策略
	 * 2.执行策略
	 * 
	 * @return
	 */
	public static Result process(long campaignId, String mobile) {
		// 活动三大运营商流量包数量
		ClassifyPackageNums packNums = packageService
				.findClassifyPackages(campaignId);
		
		// 活动美贝产品数量
		int mbays = mbayService.countByCampaignId(
				campaignId);
		
		// 产品数组
		int[] trafficProducts = {
				packNums.getMobiles() > 0 ? 1 : 0,
				packNums.getUnicoms() > 0 ? 1 : 0,
				packNums.getTelecoms() > 0 ? 1 : 0 };
		int mbayProduct = mbays > 0 ? 1 : 0;
		
		// 查询策略数组
		int[] strategyArray = null;
		for (int[][] r : table) {
			if (r[0][0] != trafficProducts[0]
					|| r[0][1] != trafficProducts[1]
					|| r[0][2] != trafficProducts[2]
					|| r[1][0] != mbayProduct) {
				continue;
			}
			strategyArray = r[2];
			break;
		}
		
		double hitRate = campaignService.findProductHitRate(campaignId);
		
		// 查询具体策略
		BigDecimal dec = new BigDecimal(hitRate);
		double rt = dec.setScale(5, RoundingMode.HALF_UP).doubleValue();
		int strategy = rt == 0 ? strategyArray[0]
				: rt == 100 ? strategyArray[2] : strategyArray[1];
		
		// 执行策略方法
		switch (strategy) {
			case IGNORE:
				return Ignore.process(campaignId, mobile);
			case TNOR:
				return TrafficNormal.process(campaignId, mobile);
			case MMH:
				return MbayMustHit.process(campaignId, mobile);
			case TMH:
				return TrafficMustHit.process(campaignId, mobile);
			case TMCH:
				return TrafficMbayCycleHit.process(campaignId, mobile);
			case MNOR:
				return MbayNormal.process(campaignId, mobile);
			default:
				LOGGER.error("StrategyTable process: no strategy find【{}】",
						strategy);
				return RedeemResult.UNEXCEPTED_ERROR;
		}
	}
	
	@Autowired
	public void setMbayService(TrafficRedCampaignMbayService mbayService) {
		StrategyContext.mbayService = mbayService;
	}
	
	@Autowired
	public void setPackageService(
			TrafficRedCampaignPackageService packageService) {
		StrategyContext.packageService = packageService;
	}
	
	@Autowired
	public void setCampaignService(
			TrafficRedCampaignService campaignService) {
		StrategyContext.campaignService = campaignService;
	}
}
