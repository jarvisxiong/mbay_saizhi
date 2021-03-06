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
import org.sz.mbay.trafficred.redeem.traffic.RedeemTraffic;
import org.sz.mbay.trafficred.result.RedeemResult;
import org.sz.mbay.trafficred.result.Result;
import org.sz.mbay.trafficred.service.TrafficRedCampaignService;
import org.sz.mbay.trafficred.service.TrafficRedExchangeRecordService;

/**
 * 流量正常中奖策略，按照概率中/不中
 * 
 * @author jerry
 */
@Component
public class TrafficNormal {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TrafficNormal.class);
	
	private static TrafficRedExchangeRecordService exchangeRecordService;
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
		
		if (resp.getStatus()) {
			if (SimpleResponse.HIT.equals(resp)) {
				return RedeemTraffic.redeem(campaignId, mobile);
			}
			
			// 信息验证
			Result ckRes = RedeemTraffic.checkInfo(campaignId, mobile);
			if (!ckRes.getStatus()) {
				return ckRes;
			}
			
			String campaignNumber = campaignService
					.findCampaignNumberById(campaignId);
			// 记录号码兑换记录 - 空记录
			exchangeRecordService.createNoWinRecord(campaignNumber,
					mobile);
			
			return RedeemResult.NOT_WIN;
		} else {
			LOGGER.info("流量红包【ID{}/{}】：{}", campaignId, mobile,
					resp.getCode());
			return Result.create(resp.getStatus(), resp.getCode(),
					resp.getContent());
		}
	}
	
	@Autowired
	public void setExchangeRecordService(
			TrafficRedExchangeRecordService exchangeRecordService) {
		TrafficNormal.exchangeRecordService = exchangeRecordService;
	}
	
	@Autowired
	public void setCampaignService(TrafficRedCampaignService campaignService) {
		TrafficNormal.campaignService = campaignService;
	}
	
}
