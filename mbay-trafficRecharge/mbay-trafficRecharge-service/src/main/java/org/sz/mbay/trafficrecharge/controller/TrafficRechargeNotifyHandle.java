package org.sz.mbay.trafficrecharge.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.trafficorder.enums.OperatorRechargeStatus;
import org.sz.mbay.trafficorder.service.TrafficOrderService;

@Controller
public class TrafficRechargeNotifyHandle {
	
	@Autowired
	TrafficOrderService trafficOrderService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ResponseBody
	@RequestMapping("api/notify/handle")
	public String notifyHandle(HttpServletRequest request) {
		String orderNumber = request.getParameter("orderNumber");
		String isSuccess = request.getParameter("isSuccess");
		String message = request.getParameter("message");
		if (logger.isInfoEnabled()) {
			logger.info("接收到mbaytraffic充值回调通知.orderNumber：" + orderNumber
					+ ",isSuccess:" + isSuccess + ",message:" + message);
		}
		message = StringUtils.isEmpty(message) ? "" : ":" + message;
		boolean success = Boolean.valueOf(isSuccess);
		if (success) {
			trafficOrderService.updateOrderStatus(orderNumber, null,
					OperatorRechargeStatus.OPER_RECHARGE_SUCCESS,
					"流量充值成功" + message);
		} else {
			trafficOrderService.updateOrderStatus(orderNumber, null,
					OperatorRechargeStatus.OPER_RECHARGE_FAIL,
					"流量充值失败" + message);
		}
		return "1";
	}
	
}
