package org.sz.mbay.promotion.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.user.exception.MBAccountTradeException;
import org.sz.mbay.channel.user.exception.RedAccountTradeException;
import org.sz.mbay.channel.user.service.MBAccountService;
import org.sz.mbay.channel.user.service.RedTrafficAccountService;
import org.sz.mbay.promotion.bean.PromotionProductConfig;
import org.sz.mbay.promotion.dao.PromotionProductConfigDao;
import org.sz.mbay.promotion.service.PromotionProductConfigService;
import org.sz.mbay.trafficred.bean.ThresholdWarnInfo;
import org.sz.mbay.trafficred.bean.TrafficRedProductConfig;
import org.sz.mbay.trafficred.enums.ProductType;

@Service
public class PromotionProductConfigServiceImpl extends BaseServiceImpl
		implements PromotionProductConfigService {
		
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PromotionProductConfigServiceImpl.class);
			
	@Autowired
	PromotionProductConfigDao dao;
	@Autowired
	RedTrafficAccountService trafficService;
	@Autowired
	MBAccountService mbayService;
	
	@Override
	public PromotionProductConfig findProductConfig(String campaignNumber,
			ProductType type) {
		return dao.findProductConfig(campaignNumber, type);
	}
	
	@Override
	public ExecuteResult updateProductDailyLimit(String campaignNumber,
			ProductType productType, double dailyLimit) {
		boolean execute = dao.updateProductDailyLimit(campaignNumber,
				productType, dailyLimit);
		if (execute) {
			return ExecuteResult.successExecute;
		} else {
			return ExecuteResult.failExecute;
		}
	}
	
	@Override
	public ExecuteResult increaseProductPoolSize(String campaignNumber,
			ProductType productType, double increasePool, String userNumber) {
		ExecuteResult expedResult = null;
		if (!this.isSelectedProduct(campaignNumber, productType)) {
			expedResult = new ExecuteResult(false, "您还没有选择产品，请先添加产品！");
			return expedResult;
		}
		TransactionStatus status = this.startTransaction();
		
		switch (productType) {
			case TRAFFIC_PACKAGE:
				double redtraffic = trafficService
						.getAvailableBalance(userNumber);
				if (increasePool > redtraffic) {
					return new ExecuteResult(false, "红包账户流量余额不足！");
					
				}
				try {
					this.trafficService.lockedTraffic(userNumber,
							increasePool);
				} catch (RedAccountTradeException e1) {
					return new ExecuteResult(false, "红包账户流量余额不足！");
				}
				break;
			case MBAY_PACKAGE:
				double mbayTraffic = this.mbayService
						.getAvailableBalance(userNumber);
				if (increasePool > mbayTraffic) {
					return new ExecuteResult(false, "美贝池流量余额不足！");
				}
				try {
					this.mbayService.lockedTraffic(userNumber,
							increasePool);
				} catch (MBAccountTradeException e1) {
					return new ExecuteResult(false, "美贝池流量余额不足！");
				}
				break;
		}
		try {
			dao.increaseProductPoolSize(campaignNumber, productType,
					increasePool);
			this.commitTransaction(status);
			if (!dao.isProductPoolRemainLessThanThreshold(
					campaignNumber, productType)) {
				dao.resetProductThsholdWarnStatus(campaignNumber,
						productType);
			}
			
		} catch (Exception e) {
			this.rollbackTransaction(status);
			LOGGER.error("increaseProductPoolSize 异常:", e.fillInStackTrace());
			return ExecuteResult.failExecute;
		}
		return ExecuteResult.successExecute;
	}
	
	@Override
	public boolean isSelectedProduct(String campaignNumber,
			ProductType productType) {
		return dao.isSelectedProduct(campaignNumber, productType);
	}
	
	@Override
	public ExecuteResult updateProductThreshold(String campaignNumber,
			ProductType productType, int threshold) {
		boolean execute = dao.updateProductThreshold(campaignNumber,
				productType, threshold);
		if (execute) {
			return ExecuteResult.successExecute;
		}
		return ExecuteResult.failExecute;
	}
	
	@Override
	public boolean reduceProductPoolRemain(String campaignNumber,
			ProductType productType, double mbay) {
		try {
			return dao.reduceProductPoolRemain(campaignNumber,
					productType, mbay) == 1;
		} catch (Exception e) {
			LOGGER.error("reduceProductPooolReamin Fail", e.fillInStackTrace());
			return false;
		}
	}
	
	@Override
	public boolean reduceProductDailyRemain(String campaignNumber,
			ProductType productType, double mbay) {
		double dailyLimit = findProductDailyLimit(campaignNumber,
				productType);
		if (dailyLimit != TrafficRedProductConfig.TRAFFIC_UNLIMIT) {
			try {
				return dao.reduceProductDailyRemain(campaignNumber,
						productType, mbay) == 1;
			} catch (Exception e) {
				LOGGER.error("reduceProductDailyReamin Fail",
						e.fillInStackTrace());
				return false;
			}
		}
		return true;
	}
	
	@Override
	public double findProductDailyLimit(String campaignNumber,
			ProductType productType) {
		return dao.findProductDailyLimit(campaignNumber, productType);
	}
	
	@Override
	public ThresholdWarnInfo findThresholdWarnInfo(String campaignNumber,
			ProductType productType) {
		return dao.findThresholdWarnInfo(campaignNumber, productType);
	}
	
	@Override
	public boolean changeProductThresholdWarned(String campaignNumber,
			ProductType productType) {
		return dao.changeProductThresholdWarned(campaignNumber, productType);
	}
	
	@Override
	public boolean isProductPoolRemainLessThanThreshold(String campaignNumber,
			ProductType productType) {
		return dao.isProductPoolRemainLessThanThreshold(campaignNumber,
				productType);
	}
	
	@Override
	public void resetDailyRemain() {
		dao.resetDailyRemain();
	}
}
