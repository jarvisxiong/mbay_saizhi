package org.sz.mbay.trafficred.redeem.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sz.mbay.trafficred.drawlot.ExchangeContext;
import org.sz.mbay.trafficred.drawlot.Response;
import org.sz.mbay.trafficred.drawlot.simple.SimpleExchange;
import org.sz.mbay.trafficred.drawlot.simple.SimpleParam;
import org.sz.mbay.trafficred.drawlot.simple.SimpleResponse;
import org.sz.mbay.trafficred.redeem.mb.RedeemMbay;
import org.sz.mbay.trafficred.redeem.traffic.RedeemTraffic;
import org.sz.mbay.trafficred.result.RedeemResult;
import org.sz.mbay.trafficred.result.Result;
import org.sz.mbay.trafficred.service.TrafficRedCampaignService;

/**
 * 红包/美贝产品循环必中，当一个产品不满足时抽取另一种产品
 * 
 * @author jerry
 */
@Component
public class TrafficMbayCycleHit {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TrafficMbayCycleHit.class);
	
	private static TrafficRedCampaignService campaignService;
	
	// 兑奖
	private static final ExchangeContext extContext = ExchangeContext
			.getInstance();
	
	static {
		// 简单抽奖策略
		extContext.setExchange(new SimpleExchange());
	}
	
	/**
	 * 执行策略
	 * 
	 * @param campaign
	 * @param mobile
	 * @return
	 */
	public static Result process(long campaignId, String mobile) {
		// 摇奖
		Double rate = campaignService.findProductHitRate(campaignId);
		SimpleParam param = new SimpleParam();
		param.setRatio(rate);
		Response resp = extContext.exchange(param);
		
		// 初始化兑换步奏
		String[] steps = null;
		if (resp.getStatus()) {
			if (SimpleResponse.HIT.equals(resp)) {
				steps = new String[] { "TRAFFIC", "MBAY" };
			} else {
				steps = new String[] { "MBAY", "TRAFFIC" };
			}
		} else {
			LOGGER.info("流量红包【ID{}/{}】：{}", campaignId, mobile,
					resp.getCode());
			return Result.create(resp.getStatus(), resp.getCode(),
					resp.getContent());
		}
		
		// 兑换
		// 某些情况下第一步不成功兑换第二个
		for (String step : steps) {
			if ("TRAFFIC".equals(step)) {
				Result resTraffic = RedeemTraffic.redeem(campaignId, mobile);
				if (resTraffic.getStatus() || (!resTraffic.getStatus()
						&& !((RedeemResult) resTraffic).isContinuable())) {
					return resTraffic;
				}
			} else {
				Result resMbay = RedeemMbay.redeem(campaignId, mobile);
				if (resMbay.getStatus() || (!resMbay.getStatus()
						&& !((RedeemResult) resMbay).isContinuable())) {
					return resMbay;
				}
			}
		}
		
		// 到达这里说明红包池和美贝池都已耗尽或者达到单日上限
		return RedeemResult.PRODUCT_OVER;
	}
	
	@Autowired
	public void setCampaignService(
			TrafficRedCampaignService campaignService) {
		TrafficMbayCycleHit.campaignService = campaignService;
	}
}
