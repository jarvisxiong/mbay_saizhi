package org.sz.mbay.channel.service.impl;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.constant.SerialSeed;
import org.sz.mbay.base.service.UtilService;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.channel.bean.SMSSendOrder;
import org.sz.mbay.channel.dao.SMSSendOrderDao;
import org.sz.mbay.channel.service.SMSSendOrderService;
import org.sz.mbay.common.util.MbayDateFormat;
import org.sz.mbay.common.util.MbayDateFormat.DatePattern;

@Service("SMSSendOrderServiceImpl")
public class SMSSendOrderServiceImpl extends BaseServiceImpl implements SMSSendOrderService {

	@Autowired
	UtilService utilService;
	@Autowired
	SMSSendOrderDao sendOrderDao;

	@Override
	public String createSMSSendOrder(SMSSendOrder order) {
		String number = MbayDateFormat.formatDateTime(DateTime.now(),
				DatePattern.yyyyMMdd);
		int nextid = utilService.getNextIndex(SMSSendOrder.class);
		order.setOrderNumber(number + (SerialSeed.SMS_PURCHASE)
				+ (SerialSeed.CARDINAL_NUMBER + nextid));
		sendOrderDao.createBean(order);
		return order.getOrderNumber();
	}

}
