package org.sz.mbay.trafficred.redeem.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sz.mbay.trafficred.result.RedeemResult;
import org.sz.mbay.trafficred.result.Result;
import org.sz.mbay.trafficred.service.TrafficRedCampaignService;
import org.sz.mbay.trafficred.service.TrafficRedExchangeRecordService;

/**
 * 忽略策略
 * 
 * @author jerry
 */
@Component
public class Ignore {
	
	private static TrafficRedExchangeRecordService exchangeRecordService;
	private static TrafficRedCampaignService campaignService;
	
	/**
	 * 执行策略
	 * 
	 * @param campaign
	 * @param mobile
	 * @return
	 */
	public static Result process(long campaignId, String mobile) {
		// 记录号码兑换记录 - 空记录
		String campaignNumber = campaignService
				.findCampaignNumberById(campaignId);
		exchangeRecordService.createNoWinRecord(campaignNumber, mobile);
		return RedeemResult.NOT_WIN;
	}
	
	@Autowired
	public void setExchangeRecordService(
			TrafficRedExchangeRecordService exchangeRecordService) {
		Ignore.exchangeRecordService = exchangeRecordService;
	}
	
	@Autowired
	public void setCampaignService(TrafficRedCampaignService campaignService) {
		Ignore.campaignService = campaignService;
	}
	
}
