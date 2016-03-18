package org.sz.mbay.trafficred.service.impl;

import static org.springframework.util.Assert.notNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.sz.mbay.base.annotation.log.CalTimeConsuming;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.user.exception.MBAccountTradeException;
import org.sz.mbay.channel.user.exception.RedAccountTradeException;
import org.sz.mbay.channel.user.service.MBAccountService;
import org.sz.mbay.channel.user.service.RedTrafficAccountService;
import org.sz.mbay.trafficred.bean.ThresholdWarnInfo;
import org.sz.mbay.trafficred.bean.TrafficRedProductConfig;
import org.sz.mbay.trafficred.dao.TrafficRedProductConfigDao;
import org.sz.mbay.trafficred.enums.ProductType;
import org.sz.mbay.trafficred.service.TrafficRedProductConfigService;

@Service
public class TrafficRedProductConfigServiceImpl extends BaseServiceImpl
		implements TrafficRedProductConfigService {
		
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TrafficRedProductConfigServiceImpl.class);
			
	@Autowired
	private TrafficRedProductConfigDao productConfigDao;
	@Autowired
	private RedTrafficAccountService redTrafficAccountService;
	@Autowired
	private MBAccountService mbAccountService;
	
	@Override
	public boolean create(TrafficRedProductConfig config) {
		return productConfigDao.create(config) == 1;
	}
	
	@CalTimeConsuming
	@Override
	public void resetDailyRemain() {
		productConfigDao.resetDailyRemain();
	}
	
	@Override
	public boolean isSelectedProduct(long campaignId, ProductType productType) {
		return productConfigDao.isSelectedProduct(campaignId, productType);
	}
	
	@Override
	public ExecuteResult increaseProductPoolSize(long campaignId,
			ProductType productType, double increasePool, String userNumber) {
		ExecuteResult expedResult = null;
		if (!this.isSelectedProduct(campaignId, productType)) {
			expedResult = new ExecuteResult(false, "您还没有选择产品，请先添加产品！");
			return expedResult;
		}
		TransactionStatus status = this.startTransaction();
		
		switch (productType) {
			case TRAFFIC_PACKAGE:
				double redtraffic = redTrafficAccountService
						.getAvailableBalance(userNumber);
				if (increasePool > redtraffic) {
					return new ExecuteResult(false, "红包账户流量余额不足！");
					
				}
				try {
					this.redTrafficAccountService.lockedTraffic(userNumber,
							increasePool);
				} catch (RedAccountTradeException e1) {
					return new ExecuteResult(false, "红包账户流量余额不足！");
				}
				break;
			case MBAY_PACKAGE:
				double mbayTraffic = this.mbAccountService
						.getAvailableBalance(userNumber);
				if (increasePool > mbayTraffic) {
					return new ExecuteResult(false, "美贝池流量余额不足！");
				}
				try {
					this.mbAccountService.lockedTraffic(userNumber,
							increasePool);
				} catch (MBAccountTradeException e1) {
					return new ExecuteResult(false, "美贝池流量余额不足！");
				}
				break;
		}
		try {
			productConfigDao.increaseProductPoolSize(campaignId, productType,
					increasePool);
			this.commitTransaction(status);
			if (!productConfigDao.isProductPoolRemainLessThanThreshold(
					campaignId, productType)) {
				productConfigDao.resetProductThsholdWarnStatus(campaignId,
						productType);
			}
			
		} catch (Exception e) {
			this.rollbackTransaction(
					TransactionAspectSupport.currentTransactionStatus());
			LOGGER.error("increaseProductPoolSize 异常:", e.fillInStackTrace());
			return ExecuteResult.failExecute;
		}
		return ExecuteResult.successExecute;
	}
	
	@Override
	public boolean isProductPoolRemainLessThanThreshold(long campaignId,
			ProductType productType) {
		return productConfigDao.isProductPoolRemainLessThanThreshold(campaignId,
				productType);
	}
	
	@Override
	public boolean changeProductThresholdWarned(long campaignId,
			ProductType productType) {
		return productConfigDao.changeProductThresholdWarned(campaignId,
				productType);
	}
	
	@Override
	public ExecuteResult updateProductThreshold(long campaignId,
			ProductType productType, int threshold) {
		boolean execute = productConfigDao.updateProductThreshold(campaignId,
				productType, threshold);
		if (execute) {
			return ExecuteResult.successExecute;
		}
		return ExecuteResult.failExecute;
	}
	
	@Override
	public boolean reduceProductPoolRemain(long campaignId,
			ProductType productType, double mbay) {
		try {
			return productConfigDao.reduceProductPoolRemain(campaignId,
					productType, mbay) == 1;
		} catch (Exception e) {
			LOGGER.error("reduceProductPooolReamin Fail", e.fillInStackTrace());
			return false;
		}
	}
	
	@Override
	public boolean reduceProductDailyRemain(long campaignId,
			ProductType productType, double mbay) {
		double dailyLimit = findProductDailyLimit(campaignId,
				productType);
		if (dailyLimit != TrafficRedProductConfig.TRAFFIC_UNLIMIT) {
			try {
				return productConfigDao.reduceProductDailyRemain(campaignId,
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
	public double findProductDailyLimit(long campaignId,
			ProductType productType) {
		return productConfigDao.findProductDailyLimit(campaignId, productType);
	}
	
	@Override
	public ExecuteResult updateProductDailyLimit(long campaignId,
			ProductType productType, double dailyLimit) {
		boolean execute = productConfigDao.updateProductDailyLimit(campaignId,
				productType, dailyLimit);
		if (execute) {
			return ExecuteResult.successExecute;
		}
		return ExecuteResult.failExecute;
	}
	
	@Override
	public TrafficRedProductConfig findProductConfig(long campaignId,
			ProductType productType) {
		notNull(productType, "Property 'productType' is required");
		return productConfigDao.findTrafficRedProductConfig(campaignId,
				productType);
	}
	
	@Override
	public ThresholdWarnInfo findThresholdWarnInfo(long campaignId,
			ProductType productType) {
		return productConfigDao.findThresholdWarnInfo(campaignId, productType);
	}
	
	@Override
	public void clearConfig(Long id) {
		productConfigDao.clearConfig(id);
	}
}
