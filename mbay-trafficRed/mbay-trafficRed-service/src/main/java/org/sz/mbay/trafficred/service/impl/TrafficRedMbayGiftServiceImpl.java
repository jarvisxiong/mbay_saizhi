package org.sz.mbay.trafficred.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.remote.interfaces.wallet.RIMBAccountUtil;
import org.sz.mbay.remote.interfaces.wallet.RITradeRecordUtil;
import org.sz.mbay.remote.interfaces.wallet.base.RIResponse;
import org.sz.mbay.trafficred.bean.TrafficRedMbayGift;
import org.sz.mbay.trafficred.bean.TrafficRedMbayGiftLimitConfig;
import org.sz.mbay.trafficred.dao.TrafficRedMbayGiftDao;
import org.sz.mbay.trafficred.enums.MbayGiftState;
import org.sz.mbay.trafficred.result.GiftDataResult;
import org.sz.mbay.trafficred.result.GiftResult;
import org.sz.mbay.trafficred.result.Result;
import org.sz.mbay.trafficred.service.TrafficRedMbayGiftLimitService;
import org.sz.mbay.trafficred.service.TrafficRedMbayGiftService;

@Service
public class TrafficRedMbayGiftServiceImpl extends BaseServiceImpl implements
		TrafficRedMbayGiftService {
		
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TrafficRedMbayGiftServiceImpl.class);
			
	@Autowired
	private TrafficRedMbayGiftDao mbayGiftDao;
	@Autowired
	private TrafficRedMbayGiftLimitService mbayGiftLimitService;
	
	@Override
	public boolean create(TrafficRedMbayGift gift) {
		return mbayGiftDao.insert(gift) == 1;
	}
	
	@Override
	public TrafficRedMbayGift findBySerialNumber(String decNum) {
		return mbayGiftDao.selectBySerialNumber(decNum);
	}
	
	@Override
	public boolean checkExistBySerialNumber(String decNum) {
		return mbayGiftDao.checkExistBySerialNumber(decNum);
	}
	
	@Override
	public boolean updateByIdSelective(TrafficRedMbayGift gift) {
		return mbayGiftDao.updateByIdSelective(gift) == 1;
	}
	
	@Override
	public Result checkParticipable(String mobile) {
		// 日/周/月参与次数限制
		TrafficRedMbayGiftLimitConfig config = mbayGiftLimitService.find();
		int[] pTimes = new int[] { config.getDayLimit(),
				config.getWeekLimit(), config.getMonthLimit() };
				
		boolean[] passList = mbayGiftDao.checkParticipable(mobile, pTimes);
		if (!passList[0]) {
			return GiftResult.TIMES_EXCEED_DAY;
		} else if (!passList[1]) {
			return GiftResult.TIMES_EXCEED_WEEK;
		} else if (!passList[2]) {
			return GiftResult.TIMES_EXCEED_MONTH;
		}
		return GiftResult.SUCCESS;
	}
	
	@Override
	@Transactional
	public Result grab(Long id, String serialNumber, String mobile) {
		// 查找源交易记录
		RIResponse resp = null;
		try {
			resp = RITradeRecordUtil
					.requestGetTradeRecordBySerialNum(serialNumber);
		} catch (Exception e) {
			LOGGER.error("lookup trade record error: {}", e.getMessage());
			return GiftResult.create(false, null, e.getMessage());
		}
		double amount = resp.getData().getJSONObject("result")
				.getDouble("amount");
				
		try {
			// 修改送人记录相关信息
			TrafficRedMbayGift gift = new TrafficRedMbayGift();
			gift.setId(id);
			gift.setMbayGiftState(MbayGiftState.RECIEVED);
			gift.setReciever(mobile);
			boolean suc = updateByIdSelective(gift);
			if (!suc) {
				return GiftResult.MBAY_GIFT_RECIEVERD;
			}
			
			// 钱包账户增款
			RIResponse mbResp = RIMBAccountUtil.requestUserEnterOfAccount(
					mobile, amount, "TRAFFIC_RED_MBAY_GIFT_GRAB",
					String.valueOf(id), null);
			String snumber = mbResp.getData().getString("serialNumber");
			return GiftDataResult.create(DigestUtils.pbeEncrypt(snumber));
		} catch (Exception e) {
			LOGGER.error("grab error: {}", e.getMessage());
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return GiftResult.create(false, null, e.getMessage());
		}
	}

	@Override
	public TrafficRedMbayGift findById(long id) {
		return mbayGiftDao.findById(id);
	}
}
