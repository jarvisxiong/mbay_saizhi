/*package org.sz.mbay.channel.component.trafficcharge;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.mbay.base.context.SpringApplicationContext;
import org.sz.mbay.channel.bean.TrafficChargeOrderRecord;
import org.sz.mbay.channel.context.ChannelConfig;
import org.sz.mbay.channel.enums.ChargeOrderStatus;
import org.sz.mbay.channel.redeem.response.MsgType;
import org.sz.mbay.channel.redeem.response.RedeemResponse;
import org.sz.mbay.operator.bean.TrafficPackage;
import org.sz.mbay.wechat.util.http.HttpSupport;

public class TrafficCharge implements Runnable {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TrafficCharge.class);
	*//** 充值单号 *//*
	private String ordernumber = "";
	
	*//**
	 * 构造函数，需提供流量充值单号
	 * 
	 * @param ordernumber
	 *//*
	private TrafficCharge(String ordernumber) {
		this.ordernumber = ordernumber;
	}
	
	public static void rechargeTraffic(String trafficOrderNumber) {
		Thread thread = new Thread(new TrafficCharge(trafficOrderNumber));
		thread.start();
	}
	
	@Override
	public void run() {
		this.trafficCharge();
	}
	
	private void trafficCharge() {
		TrafficService trafficservice = (TrafficService) SpringApplicationContext
				.getBean(TrafficServiceImpl.class.getSimpleName());
		String url = ChannelConfig.getTrafficMbayRedeemURL() + "?"
				+ "ordernumber=" + ordernumber;
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("请求美贝流量接口充值流量：{}", url);
		}
		String response = "";
		try {
			response = HttpSupport.connectViaGet(url);
			
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Response Info:{}", response);
			}
		} catch (Exception e) {
			TrafficChargeOrderRecord record = new TrafficChargeOrderRecord();
			record.setOrdernumber(ordernumber);
			record.setCreatetime(new Timestamp(System.currentTimeMillis()));
			record.setOrderstatus(ChargeOrderStatus.RECHARGEING);
			record.setContent("请求mbaytraffic失败");
			record.setIp("");
			
			trafficservice.createOrderRecord(record);
			LOGGER.error("订单：" + this.ordernumber + "请求Mbaytraffic接口流量充值异常",
					e.fillInStackTrace());
			Thread.currentThread().interrupt();
		}
	}
	
	@Override
	public String toString() {
		return "TrafficCharge []";
	}
	
	public static RedeemResponse trafficCharge(String mobile,
			TrafficPackage trafficPackage) {
		String url = ChannelConfig.getTrafficMbayRedeemURL() + "?" + "mobile="
				+ mobile + "&area="
				+ trafficPackage.getOperator().getArea().value + "&operator="
				+ trafficPackage.getOperator().getType().ordinal()
				+ "&flowtype=" + trafficPackage.getTrafficType().getValue()
				+ "&flow=" + trafficPackage.getTraffic() + "&userid=" + 0;
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(mobile + "请求美贝接口兑换流量：" + url);
		}
		String response = "";
		try {
			response = HttpSupport.connectViaGet(url);
		} catch (Exception e) {
			LOGGER.error("订单：" + url + "请求Mbaytraffic接口流量充值异常",
					e.fillInStackTrace());
			return new RedeemResponse("SYSTEM_ERROR", "无法连接到服务器，请稍后再试",
					MsgType.TEXT);
		}
		if (LOGGER.isInfoEnabled()) {
			String str;
			try {
				str = new String(response.getBytes("ISO-8859-1"), "utf-8");
				LOGGER.info("请求返回结果：" + str);
			} catch (UnsupportedEncodingException e) {
			}
		}
		SAXReader saxReader = new SAXReader();
		Document document;
		try {
			document = saxReader.read(new ByteArrayInputStream(response
					.getBytes("utf-8")));
			Element rootElement = document.getRootElement();
			String status = rootElement.elementTextTrim("status");
			String msgType = rootElement.elementTextTrim("msgType");
			String content = new String(rootElement.elementTextTrim("content")
					.getBytes("ISO-8859-1"), "utf-8");
			if ("TRAFFIC_RECHARGEING".equals(status)
					|| "TRAFFIC_RECHARGE_SUCCESS".equals(status)) {
				return new RedeemResponse(true, status, content, msgType);
			}
			return new RedeemResponse(false, status, content, msgType);
		} catch (Exception e) {
			System.out.println("解析异常");
			e.printStackTrace();
		}
		return null;
	}
}
*/