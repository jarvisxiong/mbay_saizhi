package org.sz.mbay.trafficred.redeem.mb;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.remote.interfaces.wallet.RIMBAccountUtil;
import org.sz.mbay.remote.interfaces.wallet.base.RIResponse;
import org.sz.mbay.trafficred.bean.TrafficRedCampaignMbay;
import org.sz.mbay.trafficred.bean.TrafficRedExchangeRecord;
import org.sz.mbay.trafficred.bean.TrafficRedProductConfig;
import org.sz.mbay.trafficred.dto.ResultDTO;
import org.sz.mbay.trafficred.dto.ResultDTO.ResultType;
import org.sz.mbay.trafficred.enums.PackageState;
import org.sz.mbay.trafficred.enums.ProductType;
import org.sz.mbay.trafficred.result.RedeemDataResult;
import org.sz.mbay.trafficred.result.RedeemResult;
import org.sz.mbay.trafficred.result.Result;
import org.sz.mbay.trafficred.service.TrafficRedCampaignMbayService;
import org.sz.mbay.trafficred.service.TrafficRedCampaignService;
import org.sz.mbay.trafficred.service.TrafficRedExchangeRecordService;
import org.sz.mbay.trafficred.service.TrafficRedProductConfigService;
import org.sz.mbay.trafficred.service.TrafficRedRedeemService;

/**
 * 兑换美贝
 * 
 * @author jerry
 */
@Component
public class RedeemMbay {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RedeemMbay.class);
			
	private static TrafficRedCampaignMbayService mbayService;
	private static TrafficRedCampaignService campaignService;
	private static TrafficRedRedeemService redeemService;
	private static TrafficRedExchangeRecordService exchangeRecordService;
	private static TrafficRedProductConfigService productConfigService;
	
	/**
	 * 处理摇美贝
	 * 
	 * @param campaignNumber
	 * @param mobile
	 * @param campaignId
	 * @param decNum
	 * @return
	 */
	public static Result redeem(long campaignId, String mobile) {
		// 产品配置
		TrafficRedProductConfig config = productConfigService.findProductConfig(
				campaignId, ProductType.MBAY_PACKAGE);
				
		// 信息检测
		Result ckRes = checkInfo(config, mobile);
		if (!ckRes.getStatus()) {
			return ckRes;
		}
		
		// 抽取美贝
		Result result = select(config);
		if (!result.getStatus()) {
			LOGGER.info("流量红包【ID{}/{}】：{}", campaignId, mobile,
					result.getCode());
			return result;
		}
		
		// 操作数据库
		int mbay = (int) ((RedeemDataResult) result).getData();
		LOGGER.info("手机号{}要到美贝产品{}MB", mobile, mbay);
		Result response = redeemService.operateMbay(campaignId, mbay, mobile);
		if (!response.getStatus()) {
			LOGGER.info("流量红包【ID{}/{}】：{}", campaignId, mobile,
					result.getCode());
			return response;
		}
		Long recordId = (Long) ((RedeemDataResult) response).getData();
		
		// 美贝存入钱包
		RIResponse resp = null;
		try {
			resp = RIMBAccountUtil.requestUserEnterOfAccount(mobile,
					mbay, "TRAFFIC_RED_MBAY", String.valueOf(recordId), null);
		} catch (Exception e) {
			LOGGER.error("add balance error:{}", e.getMessage());
			return RedeemResult.create(false, null, e.getMessage());
		}
		String serialNumber = resp.getData().getString("serialNumber");
		
		// 更新兑换记录状态
		TrafficRedExchangeRecord record = new TrafficRedExchangeRecord();
		record.setId(recordId);
		record.setPackageState(PackageState.RECIEVED);
		exchangeRecordService.updateRecord(record);
		
		String campaignNumber = campaignService
				.findCampaignNumberById(campaignId);
				
		// 返回结果
		ResultDTO data = new ResultDTO();
		data.setSize(mbay);
		data.setType(ResultType.MBAY);
		data.setMobile(mobile);
		data.setcNumber(DigestUtils.pbeEncrypt(campaignNumber));
		data.setRecordId(DigestUtils.pbeEncrypt(String.valueOf(recordId)));
		data.setSerialNumber(DigestUtils.pbeEncrypt(serialNumber));
		return RedeemDataResult.create(data);
	}
	
	/*
	 * 信息检测
	 */
	public static Result checkInfo(long campaignId, String mobile) {
		TrafficRedProductConfig config = productConfigService.findProductConfig(
				campaignId, ProductType.MBAY_PACKAGE);
		return checkInfo(config, mobile);
	}
	
	/*
	 * 信息检测
	 */
	public static Result checkInfo(TrafficRedProductConfig config,
			String mobile) {
		long campaignId = config.getCampaignId();
		
		// 获取最小美贝产品
		TrafficRedCampaignMbay minMbay = mbayService.findValidMin(campaignId);
		
		// 未设置美贝产品
		if (minMbay == null || minMbay.getRatio() <= 0) {
			LOGGER.info("流量红包【ID{}/{}】：{}", campaignId, mobile,
					RedeemResult.MBAY_PRODUCT_NOT_EXIST.getCode());
			return RedeemResult.MBAY_PRODUCT_NOT_EXIST;
		}
		
		// 美贝池耗尽
		if (config.getPoolRemain() < minMbay.getMbay().getMbay()) {
			LOGGER.info("流量红包【ID{}/{}】：{}", campaignId, mobile, "美贝池耗尽");
			return RedeemResult.MBAY_RUN_OUT_ALL;
		}
		
		// 流量是否达到单日领取上限
		if (config.getDailyLimit() != TrafficRedProductConfig.TRAFFIC_UNLIMIT
				&& config.getDailyRemain() < minMbay.getMbay().getMbay()) {
			LOGGER.info("流量红包【ID{}/{}】：{}", campaignId, mobile, "达到单日领取上限");
			return RedeemResult.MBAY_RUN_OUT_DAY;
		}
		
		return RedeemResult.SUCCESS;
	}
	
	/*
	 * 随机抽取一个美贝产品
	 */
	private static Result select(TrafficRedProductConfig config) {
		long campaignId = config.getCampaignId();
		
		// 获取产品包
		List<TrafficRedCampaignMbay> datas = mbayService
				.findByCampaignId(campaignId);
				
		// 产品包不存在
		if (datas == null || datas.isEmpty()) {
			return RedeemResult.MBAY_PRODUCT_NOT_EXIST;
		}
		
		// 本次可领最大美贝
		double maxMbay = config.getPoolRemain();
		if (config.getDailyLimit() != TrafficRedProductConfig.TRAFFIC_UNLIMIT
				&& maxMbay > config.getDailyRemain()) {
			maxMbay = config.getDailyRemain();
		}
		
		Iterator<TrafficRedCampaignMbay> itor = datas.iterator();
		TrafficRedCampaignMbay tmp = null;
		int seed = 0;
		while (itor.hasNext()) {
			tmp = itor.next();
			
			// 去除不能满足的美贝包
			if (tmp.getRatio() <= 0 || tmp.getMbay().getMbay() > maxMbay) {
				itor.remove();
			} else {
				// 权重相加
				seed += tmp.getRatio();
			}
		}
		
		// 只选择了一个美贝产品，且权重为0
		if (seed == 0) {
			return RedeemResult.MBAY_SEED_ZERO;
		}
		
		// 抽取
		int rand = RandomUtils.nextInt(seed) + 1;
		for (TrafficRedCampaignMbay mb : datas) {
			rand -= mb.getRatio();
			if (rand <= 0) {
				return RedeemDataResult.create(mb.getMbay().getMbay());
			}
		}
		return RedeemResult.UNEXCEPTED_ERROR;
	}
	
	@Autowired
	public void setMbayService(TrafficRedCampaignMbayService mbayService) {
		RedeemMbay.mbayService = mbayService;
	}
	
	@Autowired
	public void setRedeemService(TrafficRedRedeemService redeemService) {
		RedeemMbay.redeemService = redeemService;
	}
	
	@Autowired
	public void setExchangeRecordService(
			TrafficRedExchangeRecordService exchangeRecordService) {
		RedeemMbay.exchangeRecordService = exchangeRecordService;
	}
	
	@Autowired
	public void setCampaignService(
			TrafficRedCampaignService campaignService) {
		RedeemMbay.campaignService = campaignService;
	}
	
	@Autowired
	public void setProductConfigService(
			TrafficRedProductConfigService productConfigService) {
		RedeemMbay.productConfigService = productConfigService;
	}
	
}
