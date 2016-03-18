package org.sz.mbay.traffic.operators.callback.telecom.sh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.sz.mbay.traffic.operators.TrafficRechargeNotify;

@javax.jws.WebService(endpointInterface = "org.sz.mbay.traffic.operators.callback.telecom.sh.CSBThroughService", targetNamespace = "http://www.shtel.com.cn/csb/v2/", serviceName = "CSB_Through_Service", portName = "CSB_Through_ServicePort")
public class CSB_Through_ServicePortImpl {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CSB_Through_ServicePortImpl.class);

	public OrderProdOfferCallbackResponse orderProdOfferCallback(
			OrderProdOfferCallbackRequest opoc) {
		LOGGER.error("接收到上海电信通知");
		String error_code = opoc.getERRORCODE();
		String error_message = opoc.getERRORCODE();
		String mesgid = opoc.getMSGID();
		String order_num = opoc.getORDERNUM();
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("error_code:" + error_code);
			LOGGER.info("error_message:" + error_message);
			LOGGER.info("mesgid:" + mesgid);
			LOGGER.info("order_num:" + order_num);
		}
		if (StringUtils.isEmpty(mesgid)) {
			// mesgid:2014120410001492||SAIZ
			String orderNumber = mesgid.split("\\|")[0];
			// TrafficChargeService service=(TrafficChargeService)
			// SpringApplicationContext.getBean(TrafficChargeServiceImpl.class.getSimpleName());
			/// service.createRechargeResultRecord(orderNumber,
			// ChargeOrderStatus.RECHARGE_SUCCESS, "接收到上海电信流量充值回调，充值成功。");
			//// service.updateOrderStatus(orderNumber,
			// ChargeOrderStatus.RECHARGEING,OperatorRechargeStatus.OPER_RECHARGE_SUCCESS);
			TrafficRechargeNotify.successNotify(orderNumber);
		}
		OrderProdOfferCallbackResponse response = new OrderProdOfferCallbackResponse();
		response.setCode("0");
		response.setMsg("");
		response.setResult(0);
		return response;
	}

}