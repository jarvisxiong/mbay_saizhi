package org.sz.mbay.traffic.operators.unicom.state.bj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.sz.mbay.operator.enums.TrafficType;
import org.sz.mbay.traffic.operators.TrafficRecharge;
import org.sz.mbay.traffic.operators.TrafficRechargeNotify;
import org.sz.mbay.traffic.response.ChargeResult;

import com.bonc.vip.bj.md5.VIPMD5;

import net.sf.json.JSONObject;

@Component("BJUnicomState")
public class BJUnicomState implements TrafficRecharge {
	static final Logger LOGGER = LoggerFactory.getLogger(BJUnicomState.class);
	
	public static void main(String[] args) {
		
		System.out.println("ffffff");		
		new BJUnicomState().charge("18516071831", TrafficType.STATE, 500,"201503081104");
	}
	@Override
	public ChargeResult charge(String mobile,
			TrafficType traffictype, int traffic,String orderNumber) {
		String host = "61.50.254.200"; // 接口服务器地址
		String port = "8001"; // 接口服务端口
		String key = "94378946"; // 线下验证码key
		String t = mobile; // /"18612560539"; //订购电话号码
		String p = "G"; // 用户类型（G-GSM, D-宽带, F-固话, V- VPN集团,//
						// I-IP(VOIP),P-长途(193), Z-其他 (根据实际参数调整) 小灵通- L）默认传G
		String b = BJUnicomStatePackage.getPackCodeByTraffic(traffic); // 产品代码
		String a = "0"; // 生效类型:0 当月生效, 1 次月生效
		String u = "71678769"; // 渠道工号
		String m = "";
		try {
			m = VIPMD5.encode(key + t + p + b + a + u);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("北京联通全国接口VIPMD5加密异常", e.fillInStackTrace());
			return new ChargeResult(ChargeResult.CHARGE_FAILE,
					"联通全国接口VIPMD5加密异常");
		}
		// MD5加密字符串（参见附录2）
		String urlstr = "http://" + host + ":" + port + "/ua/order/t/" + t
				+ "/p/" + p + "/b/" + b + "/a/" + a + "/u/" + u + "/m/" + m;
		
		StringBuilder sb = new StringBuilder();
		try {
			URL url = new URL(urlstr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");// 提交模式
			conn.setDoOutput(true);// 是否输入参数
			conn.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));
			String lines;
			while ((lines = reader.readLine()) != null) {
				sb.append(lines);
			}
			LOGGER.info("订单{}充值全国联通流量返回:{}", orderNumber, sb.toString());
			reader.close();
			conn.disconnect();
		} catch (Exception e) {
			LOGGER.error("请求联通全国接口异常", e.fillInStackTrace());
			TrafficRechargeNotify.failNotify(orderNumber, "mbaytraffic异常，请查看日志信息");
			return new ChargeResult(ChargeResult.CHARGE_FAILE,
					"请求全国联通接口失败");
		}
		JSONObject result = JSONObject.fromObject(sb.toString());
		JSONObject message = JSONObject.fromObject(result.get("result").toString());
		if (StringUtils.isEmpty(message)) {
			TrafficRechargeNotify.failNotify(orderNumber, "未知返回信息："+ sb.toString());
			return new ChargeResult(ChargeResult.CHARGE_FAILE, "未知返回信息："
					+ sb.toString());
		}
		String code = message.getString("code");
		if (StringUtils.isEmpty(code)) {
			TrafficRechargeNotify.failNotify(orderNumber, "未知返回信息："+ sb.toString());
			return new ChargeResult(ChargeResult.CHARGE_FAILE, "未知返回信息："
					+ sb.toString());
		}
		if ("00000".equals(code)) {
			TrafficRechargeNotify.successNotify(orderNumber);
			return ChargeResult.SUCCESS;
		} else {
			String errorMsg = message.getString("message");
			TrafficRechargeNotify.failNotify(orderNumber,errorMsg);
			return new ChargeResult(ChargeResult.CHARGE_FAILE, errorMsg);
		}
		
		
		

	}

	@Override
	public boolean isSupportTraffic(TrafficType ttype, int traffic) {
		return BJUnicomStatePackage.getPackCodeByTraffic(traffic)!=null;
	}

}
