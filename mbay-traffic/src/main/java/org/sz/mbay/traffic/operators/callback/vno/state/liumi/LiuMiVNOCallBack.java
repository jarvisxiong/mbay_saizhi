package org.sz.mbay.traffic.operators.callback.vno.state.liumi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.traffic.operators.TrafficRechargeNotify;
import org.sz.mbay.traffic.operators.callback.telecom.state.gd.GDTelecomStateCallBack;
import org.sz.mbay.traffic.service.TrafficChargeService;

import net.sf.json.JSONObject;

@Component
public class LiuMiVNOCallBack {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GDTelecomStateCallBack.class);
	@Autowired
	TrafficChargeService rechargeService;

	@RequestMapping("liumimobile_state/callback")
	@ResponseBody
	public String orderProdOfferCallback(HttpServletRequest request) {
		LOGGER.info("接收到流米流量充值回调通知");
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(request.getInputStream(), "utf-8"))) {
			String result;
			while ((result = reader.readLine()) != null) {
				JSONObject json = JSONObject.fromObject(result);

				String status = json.getString("status");
				// 状态码
				// String code = json.getString("code");
				// 状态'成功'代表成功
				String orderNumber = json.getString("extNo");
				boolean success = "成功".equals(status);
				LOGGER.info("回调单号：{},充值:{},发送充值结果通知", orderNumber,
						success ? "成功" : "失败，失败编码：" + status);
				if (success) {
					TrafficRechargeNotify.successNotify(orderNumber);
				} else {
					TrafficRechargeNotify.failNotify(orderNumber,
							"失败编码：" + status);
				}
			}

		} catch (IOException e) {
			LOGGER.error("读取流米流量充值回调数据异常", e.fillInStackTrace());
		}
		return "1";
	}

}
