package org.sz.mbay.traffic.operators.callback.telecom.state.gd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.traffic.operators.TrafficRechargeNotify;

import net.sf.json.JSONObject;

public class GDTelecomStateCallBack {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GDTelecomStateCallBack.class);

	@RequestMapping("gdtelecom_state/callback")
	@ResponseBody
	public String orderProdOfferCallback(HttpServletRequest request) {
		LOGGER.info("接收到广东电信全国通知");
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(request.getInputStream(), "utf-8"))) {
			String result;
			while ((result = reader.readLine()) != null) {
				JSONObject json = JSONObject.fromObject(result);
				// 订单号
				String orderNumber = json.getString("request_no");
				// 返回码
				String code = json.getString("result_code");
				LOGGER.info("回调单号：{},回调结果码：{}", orderNumber, code);
				// 00000代表成功
				boolean success = "00000".equals(code);

				LOGGER.info("回调单号：{},充值:{},发送充值结果通知", orderNumber,
						success ? "成功" : "失败，失败编码：" + code);
				String notifyMessage = success ? "" : "失败编码：" + code;
				if (success) {
					TrafficRechargeNotify.successNotify(orderNumber);
				} else {
					TrafficRechargeNotify.failNotify(orderNumber,
							notifyMessage);
				}
			}
			LOGGER.info("返回信息{}", result);
		} catch (IOException e) {
			LOGGER.error("读取广东电信全国流量充值回调数据异常", e.fillInStackTrace());
		}

		return "1";
	}

}
