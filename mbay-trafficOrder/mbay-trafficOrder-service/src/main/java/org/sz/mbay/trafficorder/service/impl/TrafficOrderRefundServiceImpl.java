package org.sz.mbay.trafficorder.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.user.enums.TradeType;
import org.sz.mbay.channel.user.exception.UserAccountTradeException;
import org.sz.mbay.channel.user.service.UserAccountService;
import org.sz.mbay.trafficorder.bean.TrafficOrder;
import org.sz.mbay.trafficorder.bean.TrafficOrderRefund;
import org.sz.mbay.trafficorder.dao.TrafficOrderRefundDao;
import org.sz.mbay.trafficorder.enums.TrafficOrderRefundStatus;
import org.sz.mbay.trafficorder.qo.TrafficOrderRefundQO;
import org.sz.mbay.trafficorder.service.TrafficOrderRefundService;
import org.sz.mbay.trafficorder.service.TrafficOrderService;

@Service
public class TrafficOrderRefundServiceImpl extends BaseServiceImpl implements
		TrafficOrderRefundService {
		
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TrafficOrderRefundServiceImpl.class);
			
	@Autowired
	private TrafficOrderRefundDao trafficOrderRefundDao;
	@Autowired
	private UserAccountService accountService;
	@Autowired
	private TrafficOrderService trafficOrderService;
	
	@Override
	public void create(TrafficOrderRefund refund) {
		trafficOrderRefundDao.create(refund);
	}
	
	@Override
	public TrafficOrderRefund findByOrderNumber(String orderNumber) {
		TrafficOrder order = trafficOrderService.findTrafficOrder(orderNumber);
		TrafficOrderRefund refund = trafficOrderRefundDao
				.findByOrderNumber(orderNumber);
		refund.setOrder(order);
		return refund;
	}
	
	@Override
	public List<TrafficOrderRefund> findList(TrafficOrderRefundQO form,
			PageInfo pageInfo) {
		return trafficOrderRefundDao.findList(form, pageInfo,
				"TrafficOrderRefund");
	}
	
	@Override
	public ExecuteResult refundPass(TrafficOrderRefund refund) {
		// 状态检查
		if (refund.getStatus() != TrafficOrderRefundStatus.PROCESSING) {
			return new ExecuteResult(false, "退款状态异常");
		}
		
		// 退款
		try {
			accountService.income(
					refund.getOrder().getUserNumber(),
					TradeType.TRAFFIC_REFUND,
					refund.getOrderNumber(),
					refund.getMbayPrice(),
					"退款" + refund.getMbayPrice() + "美贝");
		} catch (UserAccountTradeException e) {
			LOGGER.error("app诱惑退款失败：{}", e.getMessage());
			return new ExecuteResult(false, e.getMessage());
		}
		
		// 修改状态
		TrafficOrderRefund bean = new TrafficOrderRefund();
		bean.setStatus(TrafficOrderRefundStatus.SUCCESS);
		bean.setOrderNumber(refund.getOrderNumber());
		trafficOrderRefundDao.updateByOrderNumberSelective(bean);
		return new ExecuteResult(true, "操作成功");
	}
	
	@Override
	public ExecuteResult refundUnpass(TrafficOrderRefund refund) {
		// 状态检查
		if (refund.getStatus() != TrafficOrderRefundStatus.PROCESSING) {
			return new ExecuteResult(false, "退款状态异常");
		}
		
		// 修改状态
		TrafficOrderRefund bean = new TrafficOrderRefund();
		bean.setStatus(TrafficOrderRefundStatus.FAIL);
		bean.setOrderNumber(refund.getOrderNumber());
		bean.setReason(refund.getReason());
		trafficOrderRefundDao.updateByOrderNumberSelective(bean);
		return new ExecuteResult(true, "操作成功");
	}
	
}
