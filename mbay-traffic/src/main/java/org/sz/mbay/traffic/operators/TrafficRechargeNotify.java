package org.sz.mbay.traffic.operators;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.mbay.base.config.ResourceConfig;
import org.sz.mbay.common.util.HttpSupport;

/**
 * @author han.han 流量充值回调通知
 *
 */
public class TrafficRechargeNotify {

	// 线程池
	private static ScheduledExecutorService executors = Executors
			.newScheduledThreadPool(50);
	// 通知失败池
	private static Map<String, Integer> notifyFails = new HashMap<String, Integer>();

	private static final int MAX_ERROR_TIMES = 5;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TrafficRechargeNotify.class);

	private static final String CHANNEL_NOTIFY_URL_KEY = "notify_url";

	private static final String CHANNEL_NOTIFY_URL = ResourceConfig
			.getProperty(CHANNEL_NOTIFY_URL_KEY);

	private TrafficRechargeNotify() {
	}

	public static void successNotify(String orderNumber) {
		LOGGER.info("发送订单{}流量 【充值成功】 回调通知", orderNumber);
		notify(orderNumber, true, "");

	}

	public static void failNotify(String orderNumber, String failMessage) {
		LOGGER.info("发送订单{}流量 【充值失败】 回调通知", orderNumber);
		notify(orderNumber, false, failMessage);

	}

	private static void notify(String orderNumber, boolean success,
			String failMessage) {
		int flag = success ? 1 : 0;// 0为false 1为true;
		String url = CHANNEL_NOTIFY_URL.concat("?orderNumber=" + orderNumber
				+ "&isSuccess=" + flag + "&message=" + failMessage);
		LOGGER.info("订单{}请求回调，URL:" + url, orderNumber);
		try {
			String responseMsg = HttpSupport.connect(url);
			LOGGER.info("订单{}回调返回信息:" + responseMsg, orderNumber);
			if (!"1".equals(responseMsg)) {
				LOGGER.info("订单{}回调返回信息不为1,回调通知失败", orderNumber);
				throw new Exception("回到返回信息非1，回调失败！");
			}
		} catch (Exception e) {
			LOGGER.error("订单" + orderNumber + "回调通知异常,异常信息：" + e.getMessage());
			int errorTimes = 1;
			if (notifyFails.containsKey(orderNumber)) {
				errorTimes = notifyFails.get(orderNumber) + 1;
				if (errorTimes == MAX_ERROR_TIMES) {
					LOGGER.warn("订单{}回调失败次数超过{}次，不再通知", orderNumber,
							MAX_ERROR_TIMES);
					notifyFails.remove(orderNumber);
					return;
				}
			}
			notifyFails.put(orderNumber, errorTimes);
			LOGGER.info("订单{}回调失败，4分钟后再次通知");
			executors.schedule(() -> {
				notify(orderNumber, success, failMessage);
			} , 4, TimeUnit.MINUTES);
			return;

			// executor.

		}
		notifyFails.remove(orderNumber);

	}

}