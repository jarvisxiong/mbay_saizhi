package org.sz.mbay.trafficred.redeem.traffic;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sz.mbay.base.area.Area;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.hcode.MbayHcode;
import org.sz.mbay.hcode.bean.HcodeInfo;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.remote.interfaces.wallet.RIMBAccountUtil;
import org.sz.mbay.trafficred.bean.TrafficRedCampaignPackage;
import org.sz.mbay.trafficred.bean.TrafficRedPackage;
import org.sz.mbay.trafficred.bean.TrafficRedProductConfig;
import org.sz.mbay.trafficred.dto.ResultDTO;
import org.sz.mbay.trafficred.dto.ResultDTO.ResultType;
import org.sz.mbay.trafficred.enums.ProductType;
import org.sz.mbay.trafficred.redeem.mb.RedeemMbay;
import org.sz.mbay.trafficred.result.RedeemDataResult;
import org.sz.mbay.trafficred.result.RedeemResult;
import org.sz.mbay.trafficred.result.Result;
import org.sz.mbay.trafficred.service.TrafficRedCampaignPackageService;
import org.sz.mbay.trafficred.service.TrafficRedCampaignService;
import org.sz.mbay.trafficred.service.TrafficRedProductConfigService;
import org.sz.mbay.trafficred.service.TrafficRedRedeemService;

/**
 * 兑换流量
 * 
 * @author jerry
 */
@Component
public class RedeemTraffic {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RedeemMbay.class);
			
	private static TrafficRedCampaignService campaignService;
	private static TrafficRedCampaignPackageService packageService;
	private static TrafficRedRedeemService redeemService;
	private static TrafficRedProductConfigService productConfigService;
	
	/**
	 * 处理摇流量
	 * 
	 * @param campaignId
	 * @param mobile
	 * @return
	 */
	public static Result redeem(long campaignId, String mobile) {
		// 手机号验证
		HcodeInfo hcode = MbayHcode.getHcodeInfo(mobile);
		if (hcode == null) {
			return RedeemResult.MOBILE_INCORRECT;
		}
		
		// 获取产品配置信息
		TrafficRedProductConfig config = productConfigService.findProductConfig(
				campaignId, ProductType.TRAFFIC_PACKAGE);
				
		// 信息验证
		Result ckRes = checkInfo(config, mobile, hcode);
		if (!ckRes.getStatus()) {
			return ckRes;
		}
		
		// 抽取对应运营商流量包
		Result result = select(config,
				OperatorType.valueOf(hcode.getOperator()));
		if (!result.getStatus()) {
			LOGGER.info("流量红包【	ID:{}/{}】：{}", campaignId, mobile,
					result.getCode());
			return result;
		}
		
		// 操作数据库
		TrafficRedCampaignPackage pack = (TrafficRedCampaignPackage) ((RedeemDataResult) result)
				.getData();
		TrafficRedPackage pk = pack.getTrafficPackage();
		Result response = redeemService.operateTraffic(campaignId, pk, mobile);
		if (!response.getStatus()) {
			LOGGER.info("流量红包【ID:{}/{}】：{}", campaignId, mobile,
					response.getCode());
			return response;
		}
		Long recordId = (Long) ((RedeemDataResult) response).getData();
		
		// 记录钱包交易记录
		String desc = Area.QUANGUO.getName() + pk.getOperatorType().getValue()
				+ pk.getTraffic() + "MB流量红包";
		try {
			RIMBAccountUtil.requestUserEnterOfAccountTraffic(mobile,
					String.valueOf(recordId), desc);
		} catch (Exception e) {
			LOGGER.error("add traffic balance error:{}", e.getMessage());
		}
		
		// 查询活动编号
		String campaignNumber = campaignService
				.findCampaignNumberById(campaignId);
				
		// 返回结果
		ResultDTO data = new ResultDTO();
		data.setSize(pk.getTraffic());
		data.setType(ResultType.TRAFFIC);
		data.setMobile(mobile);
		data.setcNumber(DigestUtils.pbeEncrypt(campaignNumber));
		data.setRecordId(DigestUtils.pbeEncrypt(String.valueOf(recordId)));
		return RedeemDataResult.create(data);
	}
	
	/*
	 * 信息检测 内部使用
	 */
	private static Result checkInfo(TrafficRedProductConfig config,
			String mobile, HcodeInfo hcode) {
		long campaignId = config.getCampaignId();
		
		// 获取最小流量包
		TrafficRedCampaignPackage minTraffic = packageService.findValidMin(
				campaignId, OperatorType.valueOf(hcode.getOperator()));
				
		// 未设置红包产品
		if (minTraffic == null || minTraffic.getRatio() <= 0) {
			LOGGER.info("流量红包活动【{}/{}】：{}", campaignId, mobile,
					RedeemResult.TRAFFIC_PRODUCT_NOT_EXIST.getCode());
			return RedeemResult.TRAFFIC_PRODUCT_NOT_EXIST;
		}
		
		// 红包池耗尽
		double minPrice = minTraffic.getTrafficPackage().getTrafficPackage()
				.getMbayprice();
		if (config.getPoolRemain() < minPrice) {
			LOGGER.info("流量红包【{}/{}】：{}", campaignId, mobile, "红包流量池耗尽");
			return RedeemResult.TRAFFIC_RUN_OUT_ALL;
		}
		
		// 流量是否达到单日领取上限
		if (config.getDailyLimit() != TrafficRedProductConfig.TRAFFIC_UNLIMIT
				&& config.getDailyRemain() < minPrice) {
			LOGGER.info("流量红包【{}/{}】：{}", campaignId, mobile, "达到单日领取上限");
			return RedeemResult.TRAFFIC_RUN_OUT_DAY;
		}
		
		return RedeemResult.SUCCESS;
	}
	
	/**
	 * 信息检测 外部使用
	 * 
	 * @param campaign
	 * @param mobile
	 * @return
	 */
	public static Result checkInfo(long campaignId, String mobile) {
		HcodeInfo hcode = MbayHcode.getHcodeInfo(mobile);
		TrafficRedProductConfig config = productConfigService.findProductConfig(
				campaignId, ProductType.TRAFFIC_PACKAGE);
		return checkInfo(config, mobile, hcode);
	}
	
	/**
	 * 随机抽取一个流量包
	 * 
	 * @param param
	 * @return
	 */
	private static Result select(TrafficRedProductConfig config,
			OperatorType operatorType) {
		long campaignId = config.getCampaignId();
		
		// 当前运营商可用流量包
		List<TrafficRedCampaignPackage> packs = packageService
				.findByCampaignIdAndOperatorType(campaignId,
						operatorType);
						
		// 产品包不存在
		if (packs == null || packs.isEmpty()) {
			return RedeemResult.TRAFFIC_PRODUCT_NOT_EXIST;
		}
		
		// 本次可领最大流量
		double maxTraffic = config.getPoolRemain();
		if (config.getDailyLimit() != TrafficRedProductConfig.TRAFFIC_UNLIMIT
				&& maxTraffic > config.getDailyRemain()) {
			maxTraffic = config.getDailyRemain();
		}
		
		Iterator<TrafficRedCampaignPackage> itor = packs.iterator();
		TrafficRedCampaignPackage tmp = null;
		int seed = 0;
		while (itor.hasNext()) {
			tmp = itor.next();
			double packagePrice = tmp.getTrafficPackage().getTrafficPackage()
					.getMbayprice();
					
			// 去除不能满足的流量包
			if (tmp.getRatio() <= 0 || packagePrice > maxTraffic) {
				itor.remove();
			}
			// 权重相加
			else {
				seed += tmp.getRatio();
			}
		}
		
		// 只选择了一个流量包，且权重为0
		if (seed == 0) {
			return RedeemResult.TRAFFIC_SEED_ZERO;
		}
		
		// 抽取
		int rand = RandomUtils.nextInt(seed) + 1;
		for (TrafficRedCampaignPackage pc : packs) {
			rand -= pc.getRatio();
			if (rand <= 0) {
				return RedeemDataResult.create(pc);
			}
		}
		return RedeemResult.UNEXCEPTED_ERROR;
	}
	
	@Autowired
	public void setPackageService(
			TrafficRedCampaignPackageService packageService) {
		RedeemTraffic.packageService = packageService;
	}
	
	@Autowired
	public void setRedeemService(TrafficRedRedeemService redeemService) {
		RedeemTraffic.redeemService = redeemService;
	}
	
	@Autowired
	public void setCampaignService(
			TrafficRedCampaignService campaignService) {
		RedeemTraffic.campaignService = campaignService;
	}
	
	@Autowired
	public void setProductConfigService(
			TrafficRedProductConfigService productConfigService) {
		RedeemTraffic.productConfigService = productConfigService;
	}
}
