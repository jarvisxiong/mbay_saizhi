package org.sz.mbay.trafficorder.service.impl;

import static org.springframework.util.Assert.notNull;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.UtilService;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.base.web.utils.RequestHolder;
import org.sz.mbay.base.web.utils.RequestUtil;
import org.sz.mbay.common.util.MbayDateFormat;
import org.sz.mbay.common.util.MbayDateFormat.DatePattern;
import org.sz.mbay.operator.bean.TrafficPackage;
import org.sz.mbay.operator.service.OperatorService;
import org.sz.mbay.trafficorder.bean.TrafficOrder;
import org.sz.mbay.trafficorder.bean.TrafficOrderRecord;
import org.sz.mbay.trafficorder.bean.TrafficRechargeInfo;
import org.sz.mbay.trafficorder.dao.TrafficOrderDao;
import org.sz.mbay.trafficorder.enums.OperatorRechargeStatus;
import org.sz.mbay.trafficorder.enums.TrafficOrderStatus;
import org.sz.mbay.trafficorder.exception.TrafficOrderException;
import org.sz.mbay.trafficorder.qo.TrafficOrderQO;
import org.sz.mbay.trafficorder.service.TrafficOrderRecordService;
import org.sz.mbay.trafficorder.service.TrafficOrderService;

@Service
public class TrafficOrderServiceImpl extends BaseServiceImpl implements TrafficOrderService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TrafficOrderDao trafficOrderDao;
	@Autowired
	private OperatorService operatroService;
	@Autowired
	private UtilService utilService;
	@Autowired
	private TrafficOrderRecordService trafficOrderRecordService;

	@Override
	public String create(TrafficRechargeInfo rechargeInfo) {
		notNull(rechargeInfo, "Property 'rechargeInfo' is required");
		// 查询对应的流量包信息
		TrafficPackage trafficPackage = this.operatroService.findTrafficPackage(rechargeInfo.getTrafficPackageNumber());
		if (trafficPackage == null) {// 根据充值流量包编码未查询到对应的流量包信息 抛出异常
			throw new TrafficOrderException("根据流量包编号：" + rechargeInfo.getTrafficPackageNumber() + " 未查询到对应的流量包信息");
		}

		// 创建充值订单
		DateTime now = DateTime.now();
		String snumber = MbayDateFormat.formatDateTime(now, DatePattern.yyyyMMdd); // 创建订单
		int rrcord = utilService.getNextIndex(TrafficOrder.class);
		String orderNumber = snumber + rechargeInfo.getRechargeType().ordinal() + (10000000 + rrcord);
		String orderName = rechargeInfo.getRechargeType().getValue() + "流量下发";
		TrafficOrder trafficOrder = new TrafficOrder();
		trafficOrder.setCreateTime(now);
		TrafficPackage tpackage = new TrafficPackage();
		tpackage.setId(rechargeInfo.getTrafficPackageNumber());
		trafficOrder.setTrafficPackage(tpackage);
		trafficOrder.setRoutePackageid(tpackage.getId());
		trafficOrder.setMbayPrice(trafficPackage.getMbayprice());
		trafficOrder.setMobile(rechargeInfo.getMobile());
		trafficOrder.setNumber(orderNumber);
		trafficOrder.setOrderType(rechargeInfo.getRechargeType());
		trafficOrder.setOrs(OperatorRechargeStatus.NON);
		trafficOrder.setStatus(TrafficOrderStatus.RECHARGEING);
		trafficOrder.setRelateNumber(rechargeInfo.getRelationNumber());
		trafficOrder.setUserNumber(rechargeInfo.getUserNumber());
		trafficOrder.setOrderName(orderName);
		this.trafficOrderDao.createBean(trafficOrder);

		// 创建订单记录
		TrafficOrderRecord orderRecord = new TrafficOrderRecord();
		orderRecord.setOrderNumber(trafficOrder.getNumber());
		orderRecord.setStatus(TrafficOrderStatus.RECHARGEING);
		orderRecord.setOrs(OperatorRechargeStatus.NON);
		if (RequestHolder.getServletRequest() != null) {
			String IP = RequestUtil.getIP(RequestHolder.getServletRequest());
			orderRecord.setIP(IP);
		}
		orderRecord.setCreateTime(now);
		orderRecord.setContent("创建流量充值订单");
		trafficOrderRecordService.create(orderRecord);

		return trafficOrder.getNumber();

	}

	@Override
	public List<TrafficOrder> findAllTrafficOrder(TrafficOrderQO orderQO, PageInfo pageinfo) {
		return this.trafficOrderDao.findList(TrafficOrder.class, orderQO, pageinfo);
	}

	@Override
	public TrafficOrder findTrafficOrder(String orderNumber) {
		return this.trafficOrderDao.getBean(orderNumber);
	}

	@Override
	@Transactional
	public void updateOrderStatus(String orderNumber, TrafficOrderStatus orderstatus, OperatorRechargeStatus ops,
			String description) {
		try {
			this.trafficOrderDao.updateOrderStatus(orderNumber, orderstatus, ops);
			TrafficOrderRecord orderRecord = new TrafficOrderRecord();
			orderRecord.setOrderNumber(orderNumber);
			orderRecord.setStatus(orderstatus);
			String IP = RequestUtil.getIP(RequestHolder.getServletRequest());
			orderRecord.setIP(IP);
			orderRecord.setCreateTime(DateTime.now());
			orderRecord.setOrs(ops);
			orderRecord.setStatus(orderstatus);
			orderRecord.setContent(description);
			trafficOrderRecordService.create(orderRecord);
		} catch (Exception e) {
			logger.error("修改流量订单状态失败！", e.fillInStackTrace());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

	}

	@Override
	public List<TrafficOrder> findTrafficOrderByRelateNumber(String relateNumber) {
		return this.trafficOrderDao.findTrafficOrderByRelateNumber(relateNumber);
	}

	@Override
	public boolean isExistOrder(String orderNumber, String userNumber) {
		TrafficOrderQO qo = new TrafficOrderQO();
		qo.setOrderNumber(orderNumber);
		qo.setUserNumber(userNumber);
		List<TrafficOrder> orders = this.findAllTrafficOrder(qo, null);
		return !orders.isEmpty();
	}

	@Override
	public void setOrderOperatorNumber(String orderNumber, String operatorNumber) {
		this.trafficOrderDao.setOrderOperatorNumber(orderNumber, operatorNumber);

	}

	@Override
	public void updateOrderStatusByOperatorNumber(String operatorNumber, TrafficOrderStatus orderstatus,
			OperatorRechargeStatus ops, String description) {
		String orderNumber = this.trafficOrderDao.findOrderNumberByOperatorNumber(operatorNumber);
		this.updateOrderStatus(orderNumber, orderstatus, ops, description);
	}

}
