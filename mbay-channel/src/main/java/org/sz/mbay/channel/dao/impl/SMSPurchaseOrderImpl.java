package org.sz.mbay.channel.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.channel.bean.SMSPurchaseOrder;
import org.sz.mbay.channel.dao.SMSPurchaseOrderDao;
import org.sz.mbay.channel.form.SMSPurchaseOrderForm;

@Repository
public class SMSPurchaseOrderImpl extends BaseDaoImpl<SMSPurchaseOrder> implements SMSPurchaseOrderDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SMSPurchaseOrderImpl.class);
	
	@Override
	public List<SMSPurchaseOrder> findAllSMSPurchaseOrder(String userNumber, PageInfo pageinfo,
			SMSPurchaseOrderForm sMSPurchaseOrderCriteria) {
		List<SMSPurchaseOrder> sMSPurchaseOrderList = null;
		
		try {
			sMSPurchaseOrderList = super.findList(sMSPurchaseOrderCriteria, pageinfo, "SMSPurchaseOrder");
		} catch (Exception e) {
			LOGGER.error("SMSPurchaseOrderImpl", e.fillInStackTrace());
		}
		return sMSPurchaseOrderList;
	}
	
	@Override
	public boolean saveSMSPurchaseOrder(SMSPurchaseOrder sMSPurchaseOrder) {
		try {
			return this.template.insert("createSMSPurchaseOrder", sMSPurchaseOrder) == 1;
		} catch (Exception e) {
			LOGGER.error("saveSMSPurchaseOrder", e.fillInStackTrace());
			return false;
		}
	}
	
	@Override
	public SMSPurchaseOrder findSMSPurchaseOrderByOrderId(String orderId) {
		return template.selectOne("findSMSPurchaseOrderByOrderId", orderId);
	}
}
