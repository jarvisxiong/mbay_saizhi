package org.sz.mbay.trafficrecharge.recharge;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.mbay.base.config.ResourceConfig;
import org.sz.mbay.base.context.SpringApplicationContext;
import org.sz.mbay.common.util.HttpSupport;
import org.sz.mbay.operator.bean.TrafficPackage;
import org.sz.mbay.trafficorder.bean.TrafficOrder;
import org.sz.mbay.trafficorder.enums.OperatorRechargeStatus;
import org.sz.mbay.trafficorder.service.TrafficOrderService;

public class TrafficCharge {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TrafficCharge.class);

	private static ExecutorService executorService = Executors
			.newFixedThreadPool(100);

	private static TrafficOrderService trafficOrderService = SpringApplicationContext
			.getBean(TrafficOrderService.class);

	private static final String MBAY_TRAFFIC_URL = ResourceConfig
			.getProperty("mbytraffic_url");

	/**
	 * 构造函数，需提供流量充值单号
	 * 
	 * @param ordernumber
	 */

	public static void rechargeTraffic(String trafficOrderNumber) {

		executorService.execute(() -> {
			TrafficOrder trafficOrder = trafficOrderService
					.findTrafficOrder(trafficOrderNumber);
			TrafficPackage trafficPackage = trafficOrder.getTrafficPackage();
			String mobile = trafficOrder.getMobile();
			String url = MBAY_TRAFFIC_URL.concat("?mobile=" + mobile)
					.concat("&packageNo=" + trafficPackage.getId())
					.concat("&orderNumber=" + trafficOrderNumber);
			LOGGER.info("订单{}请求mbaytraffic流量充值。充值URL:{}", trafficOrderNumber,
					url);
			try {
				String msg = HttpSupport.build(url).connect();
				LOGGER.info("订单{}请求返回{}", trafficOrderNumber, msg);
			} catch (Exception e) {
				trafficOrderService.updateOrderStatus(trafficOrderNumber, null,
						OperatorRechargeStatus.CONNECT_MBAYINTERFACE_FAIL,
						"请求mbaytraffic失败，请查看日志信息。");
				LOGGER.error("订单{}请求mbaytraffic异常:{}", trafficOrderNumber,
						e.getMessage());
			}
		});
	}

	@Override
	public String toString() {
		return "TrafficCharge []";
	}

}
