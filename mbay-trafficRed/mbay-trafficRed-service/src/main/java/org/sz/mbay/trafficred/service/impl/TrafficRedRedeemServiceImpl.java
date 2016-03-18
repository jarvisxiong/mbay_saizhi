package org.sz.mbay.trafficred.service.impl;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.channel.user.enums.TradeType;
import org.sz.mbay.channel.user.service.MBAccountService;
import org.sz.mbay.channel.user.service.RedTrafficAccountService;
import org.sz.mbay.operator.bean.TrafficPackage;
import org.sz.mbay.operator.service.OperatorService;
import org.sz.mbay.trafficred.bean.TrafficRedExchangeRecord;
import org.sz.mbay.trafficred.bean.TrafficRedPackage;
import org.sz.mbay.trafficred.enums.PackageState;
import org.sz.mbay.trafficred.enums.ProductType;
import org.sz.mbay.trafficred.enums.RedeemType;
import org.sz.mbay.trafficred.result.RedeemDataResult;
import org.sz.mbay.trafficred.result.RedeemResult;
import org.sz.mbay.trafficred.result.Result;
import org.sz.mbay.trafficred.service.TrafficRedCampaignService;
import org.sz.mbay.trafficred.service.TrafficRedExchangeRecordService;
import org.sz.mbay.trafficred.service.TrafficRedProductConfigService;
import org.sz.mbay.trafficred.service.TrafficRedRedeemService;

@Service
public class TrafficRedRedeemServiceImpl extends BaseServiceImpl
		implements TrafficRedRedeemService {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TrafficRedRedeemServiceImpl.class);
	
	@Autowired
	private TrafficRedCampaignService campaignService;
	@Autowired
	private TrafficRedExchangeRecordService exchangeRecordService;
	@Autowired
	private OperatorService operatorService;
	@Autowired
	private MBAccountService mbAccountService;
	@Autowired
	private RedTrafficAccountService redTrafficAccountService;
	@Autowired
	private TrafficRedProductConfigService productConfigService;
	
	@Override
	@Transactional
	public Result operateTraffic(long campaignId, TrafficRedPackage pack,
			String mobile) {
		try {
			TrafficPackage tp = operatorService
					.findTrafficPackage(pack.getPackageId());
			double traffic = tp.getMbayprice();
			
			// 红包池剩余流量减少
			boolean suc = productConfigService.reduceProductPoolRemain(
					campaignId, ProductType.TRAFFIC_PACKAGE, traffic);
			if (!suc) {
				return RedeemResult.TRAFFIC_RUN_OUT_ALL;
			}
			
			// 红包池单日剩余流量减少
			boolean suc2 = productConfigService.reduceProductDailyRemain(
					campaignId, ProductType.TRAFFIC_PACKAGE, traffic);
			if (!suc2) {
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
				return RedeemResult.TRAFFIC_RUN_OUT_DAY;
			}
			
			// 查询活动编号
			String campaignNumber = campaignService
					.findCampaignNumberById(campaignId);
					
			// 记录号码兑换记录
			TrafficRedExchangeRecord record = new TrafficRedExchangeRecord();
			record.setCampaignNumber(campaignNumber);
			record.setTime(DateTime.now());
			record.setMobile(mobile);
			record.setPackageId(pack.getPackageId());
			record.setRedeemType(RedeemType.TRAFFIC);
			String name = tp.getOperator().getArea().getName()
					+ tp.getOperator().getType().getValue()
					+ pack.getTraffic() + "MB流量红包";
			record.setContent(name);
			record.setPackageState(PackageState.CREATED);
			record.setSize(traffic);
			exchangeRecordService.createRecord(record);
			
			// 查询活动归属用户
			String userNumber = campaignService
					.findCampaignBelongUser(campaignId);
					
			// 账户扣款
			LOGGER.info("解除美贝锁定{},扣除红包美贝{}", traffic, traffic);
			redTrafficAccountService.unlockedTraffic(userNumber, traffic);
			redTrafficAccountService.expenditure(traffic, userNumber,
					TradeType.TRAFFIC_RED, String.valueOf(record.getId()),
					"流量红包活动：" + campaignNumber);
					
			// 检查并发送阀值警告
			this.campaignService.checkThresholdAndSendWarning(campaignId,
					ProductType.TRAFFIC_PACKAGE);
			return RedeemDataResult.create(record.getId());
		} catch (Exception e) {
			LOGGER.error("流量红包【{}】：{}", campaignId, e.getMessage());
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return RedeemResult.PERSIST_ERROR;
		}
	}
	
	@Override
	@Transactional
	public Result operateMbay(long campaignId, Integer mbay, String mobile) {
		try {
			// 美贝流量池剩余流量减少
			boolean suc = productConfigService.reduceProductPoolRemain(
					campaignId, ProductType.MBAY_PACKAGE, mbay);
			if (!suc) {
				return RedeemResult.MBAY_RUN_OUT_ALL;
			}
			
			// 美贝池单日剩余流量减少
			boolean suc2 = productConfigService.reduceProductDailyRemain(
					campaignId, ProductType.MBAY_PACKAGE, mbay);
			if (!suc2) {
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
				return RedeemResult.MBAY_RUN_OUT_DAY;
			}
			
			// 查询活动编号
			String campaignNumber = campaignService
					.findCampaignNumberById(campaignId);
					
			// 记录号码兑换记录
			TrafficRedExchangeRecord record = new TrafficRedExchangeRecord();
			record.setCampaignNumber(campaignNumber);
			record.setTime(DateTime.now());
			record.setMobile(mobile);
			record.setRedeemType(RedeemType.MBAY);
			record.setPackageState(PackageState.CREATED);
			record.setContent("摇一摇获得" + mbay + RedeemType.MBAY.getValue());
			record.setSize(mbay.doubleValue());
			exchangeRecordService.createRecord(record);
			
			// 查询活动归属用户
			String userNumber = campaignService
					.findCampaignBelongUser(campaignId);
					
			// 账户扣款
			mbAccountService.unlockedTraffic(userNumber, mbay);
			mbAccountService.expenditure(mbay, userNumber,
					TradeType.TRAFFIC_RED, String.valueOf(record.getId()),
					"流量红包活动：" + campaignNumber);
					
			// 检查并发送阀值警告
			this.campaignService.checkThresholdAndSendWarning(campaignId,
					ProductType.MBAY_PACKAGE);
					
			return RedeemDataResult.create(record.getId());
		} catch (Exception e) {
			LOGGER.error("流量红包【ID：{}】：{}", campaignId, e.getMessage());
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return RedeemResult.PERSIST_ERROR;
		}
	}
}
