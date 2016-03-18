package org.sz.mbay.channel.service.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.sz.mbay.base.constant.SerialSeed;
import org.sz.mbay.base.exception.ServiceException;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.UtilService;
import org.sz.mbay.channel.bean.SMSPackage;
import org.sz.mbay.channel.bean.SMSPurchaseOrder;
import org.sz.mbay.channel.context.ChannelContext;
import org.sz.mbay.channel.costant.error.SMSPurchaseError;
import org.sz.mbay.channel.dao.SMSPurchaseOrderDao;
import org.sz.mbay.channel.enums.DepositState;
import org.sz.mbay.channel.form.SMSPurchaseOrderForm;
import org.sz.mbay.channel.service.SMSPurchaseOrderService;
import org.sz.mbay.channel.user.dao.UserContextDao;
import org.sz.mbay.channel.user.enums.TradeType;
import org.sz.mbay.channel.user.service.UserAccountService;
import org.sz.mbay.common.util.MbayDateFormat;
import org.sz.mbay.common.util.MbayDateFormat.DatePattern;

@Service
public class SMSPurchaseOrderServiceImpl implements SMSPurchaseOrderService {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SMSPackageServiceImpl.class);
	
	@Autowired
	private SMSPurchaseOrderDao sMSPurchaseOrderDao;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	UserContextDao userContextDao;
	
	@Autowired
	UtilService utilService;
	
	@Override
	public List<SMSPurchaseOrder> findAllSMSPurchaseOrder(String userNumber,
			PageInfo pageinfo, SMSPurchaseOrderForm sMSPurchaseOrderCriteria) {
		List<SMSPurchaseOrder> sMSPurchaseOrderList = null;
		try {
			sMSPurchaseOrderList = this.sMSPurchaseOrderDao
					.findAllSMSPurchaseOrder(userNumber, pageinfo,
							sMSPurchaseOrderCriteria);
		} catch (Exception e) {
			LOGGER.error("findAllSMSPurchaseOrder", e.fillInStackTrace());
		}
		return sMSPurchaseOrderList;
	}
	
	@Override
	@Transactional
	public void purchaseSMS(SMSPackage smsPackage) {
		String number = MbayDateFormat.formatDateTime(DateTime.now(),
				DatePattern.yyyyMMdd);
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		int nextid = utilService.getNextIndex(SMSPurchaseOrder.class);
		SMSPurchaseOrder sMSPurchaseOrder = new SMSPurchaseOrder();
		DateTime now = DateTime.now();
		sMSPurchaseOrder.setCreateTime(now);
		sMSPurchaseOrder.setMbayAmount(smsPackage.getMbayAmount());
		sMSPurchaseOrder.setState(DepositState.SUCCESS);
		sMSPurchaseOrder.setUserNumber(ChannelContext.getSessionChannel()
				.getUserNumber());
		sMSPurchaseOrder.setSmsAmount(smsPackage.getSmsAmount());
		sMSPurchaseOrder.setOrderId(number + (SerialSeed.SMS_PURCHASE)
				+ (SerialSeed.CARDINAL_NUMBER + nextid));
		try {
			sMSPurchaseOrderDao.saveSMSPurchaseOrder(sMSPurchaseOrder);
			userAccountService.expenditure(userNumber, TradeType.SMS_PURCHASE,
					sMSPurchaseOrder.getOrderId(), smsPackage.getMbayAmount(), "短信购买");
			userContextDao.accretionSMS(userNumber, smsPackage.getSmsAmount());
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOGGER.error("账户交易失败", e.fillInStackTrace());
			throw new ServiceException(SMSPurchaseError.SMS_PURSE_ERROR);
		}
		
	}
	
	@Override
	public SMSPurchaseOrder findSMSPurchaseOrder(String orderNumber) {
		SMSPurchaseOrder order = sMSPurchaseOrderDao
				.findSMSPurchaseOrderByOrderId(orderNumber);
		return order == null ? new SMSPurchaseOrder() : order;
	}
}
