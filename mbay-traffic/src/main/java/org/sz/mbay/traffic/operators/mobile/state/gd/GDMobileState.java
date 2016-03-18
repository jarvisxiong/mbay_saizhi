package org.sz.mbay.traffic.operators.mobile.state.gd;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.sz.mbay.operator.enums.TrafficType;
import org.sz.mbay.traffic.operators.TrafficRecharge;
import org.sz.mbay.traffic.operators.TrafficRechargeNotify;
import org.sz.mbay.traffic.response.ChargeResult;
import org.sz.mbay.traffic.util.encrypt.AESUtil;

import net.sf.json.JSONObject;

@Component("GDMobileState")
public class GDMobileState implements TrafficRecharge {

	static final Logger LOGGER = LoggerFactory.getLogger(GDMobileState.class);

	@Override
	public ChargeResult charge(String mobile,
			TrafficType traffictype, int traffic,String orderNumber) {
		if (true) {
			return new ChargeResult(ChargeResult.CHARGE_FAILE, "接口已停用！");
		}
		// 合作方ID
		@SuppressWarnings("unused")
		String partner_no = "100459727";
		// 请求流水号,长度小于30
		String request_no = orderNumber;
		// 服务编码
		String service_code = "FS0001";
		// 合同ID 表示：移动
		String contract_id = "100177";
		// 订单ID,固定为0
		String order_id = "0";
		// 销售品ID 即流量包大小
		String plat_offer_id = GDMobileStatePackage
				.getPackCodeByTraffic(traffic);
		// 销售品价格,可不传
		String price = "";
		// 手机号码
		String phone_id = mobile;
		// 活动ID 移动标识
		String activity_id = "100513";
		// 订购类型,1：订购；2：退订
		String order_type = "1";
		// 渠道ID 固定
		String channel_id = "1";
		// 需要加密内容
		JSONObject json = new JSONObject();
		json.put("request_no", request_no);
		json.put("service_code", service_code);
		json.put("contract_id", contract_id);
		json.put("order_id", order_id);
		json.put("plat_offer_id", plat_offer_id);
		if (!"".equals(price)) {
			json.put("price", price);
		}
		json.put("phone_id", phone_id);
		json.put("activity_id", activity_id);
		json.put("order_type", order_type);
		json.put("channel_id", channel_id);

		System.out.println("加密前 -> " + json.toString());
		// 加密key 写死，由移动分配
		String password = "xxMfiNBZILWMkqdk";
		// 加密向量
		String value = "5474590804207477";
		String code = AESUtil.encrypt(json.toString(), password, value);
		System.out.println("加密后 -> " + code);

		json.clear();
		json.put("partner_no", partner_no);
		json.put("code", code);

		String urlstr = "http://118.123.170.184:8080/fps/flowService.do";
		StringBuilder sb = new StringBuilder();
		try {
			URL url = new URL(urlstr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type", "text/xml");
			conn.setRequestProperty("Content-length",
					String.valueOf(json.toString().length()));
			conn.connect();
			OutputStream out = conn.getOutputStream();
			out.write(json.toString().getBytes());
			out.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));
			String lines;
			while ((lines = reader.readLine()) != null) {
				sb.append(lines);
			}
			LOGGER.info("订单{}充值全国移动流量返回:{}", orderNumber, sb.toString());
			reader.close();
			conn.disconnect();
		} catch (Exception e) {
			LOGGER.error("请求移动全国接口异常", e.fillInStackTrace());
			TrafficRechargeNotify.failNotify(orderNumber, "请求异常");
			return new ChargeResult(ChargeResult.CHARGE_FAILE, "请求全移动接口失败");
		}
		JSONObject result = JSONObject.fromObject(sb.toString());
		String result_code = result.getString("result_code");
		if ("00000".equals(result_code)) {
			TrafficRechargeNotify.successNotify(orderNumber);
			return ChargeResult.SUCCESS;
		} else {
			TrafficRechargeNotify.failNotify(orderNumber, "错误编码："+result_code);
			return new ChargeResult(ChargeResult.CHARGE_FAILE, "流量充值失败,失败编码："
					+ result_code);
		}
	}

	/**
	 * 返回信息说明：{"request_no":"201412151702","result_code":"10001"}
	 * 
	 */

	@Override
	public boolean isSupportTraffic(TrafficType ttype, int traffic) {
		return GDMobileStatePackage.getPackCodeByTraffic(traffic) != null;
	}
}