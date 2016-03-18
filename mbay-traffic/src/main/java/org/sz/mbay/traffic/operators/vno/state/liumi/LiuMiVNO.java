package org.sz.mbay.traffic.operators.vno.state.liumi;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.sz.mbay.common.util.HttpSupport;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.operator.enums.TrafficType;
import org.sz.mbay.traffic.operators.TrafficRecharge;
import org.sz.mbay.traffic.operators.TrafficRechargeNotify;
import org.sz.mbay.traffic.response.ChargeResult;

/**
 * @Description: 流米虚拟运营商流量充值接口
 * @author han.han
 * @date 2015-2-11 下午12:03:44
 * 
 */
@Component("LiuMiVNO")
public class LiuMiVNO implements TrafficRecharge {

	static final Logger LOGGER = LoggerFactory.getLogger(LiuMiVNO.class);

	// 平台接口地址前缀
	private final static String url = "https://api.tenchang.com/server";

	// appkey,由平台分配
	private final static String appkey = "saizhikeji";

	// appsecret,由平台分配,需要进行md5加密
	private final static String appsecret = DigestUtils.md5Hex("szkj20150210");

	// appver
	private final static String appver = "Https";

	//private static boolean flag = false;

	@Override
	public ChargeResult charge(String mobile,
			TrafficType traffictype, int traffic,String ordernumber) {
		LOGGER.info("接收到流米接口流量充值。ordernumber:{},mobile:{},traffic:{}",
				ordernumber, mobile, traffic);
		String token = this.getToken();// 获取token；
		if (token == null) {
			return new ChargeResult(ChargeResult.CHARGE_FAILE, "获取token失败");
		}
		TrafficPackage trafficPackage = TrafficPackage
				.getTrafficPacketeCode(OperatorType.MOBILE, traffic);
		if (trafficPackage == null) {
			return new ChargeResult(ChargeResult.CHARGE_FAILE, "获取流量包失败");
		}
		String postpackage = trafficPackage.getCode();
		String sign = "appkey" + appkey + "appsecret" + appsecret + "appver"
				+ appver + "extno" + ordernumber + "mobile" + mobile
				+ "postpackage" + postpackage + "token" + token;
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("appkey", appkey);
			param.put("appsecret", appsecret);
			param.put("appver", appver);
			param.put("extno", ordernumber);
			param.put("fixtime", "");
			param.put("mobile", mobile);
			param.put("postpackage", postpackage);
			param.put("token", token);
			param.put("sign", DigestUtils.sha1Hex(sign));
			String value = HttpSupport.connect(url + "/placeOrder", param);
			JsonNode jsonNode = new ObjectMapper().readTree(value);
			String code = jsonNode.get("code").getTextValue();
			if ("000".equals(code)) {
				JsonNode data = jsonNode.get("data");
				String orderNo = data.get("orderNO").getTextValue();
				LOGGER.info("流米流量充值下单成功,单号orderNO:{}", orderNo);
				return ChargeResult.RECHARGING;
			} else {
				LOGGER.warn("流米流量充值下单失败,失败编码：" + code);
				TrafficRechargeNotify.failNotify(ordernumber, "下单失败");
			}
		} catch (Exception e) {
			LOGGER.error("流米流量充值系统异常", e.fillInStackTrace());
			TrafficRechargeNotify.failNotify(ordernumber, "mbaytraffic系统异常,请查看日志信息");
			return new ChargeResult(ChargeResult.CHARGE_FAILE,
					"mbaytraffic系统异常");
		}
		return null;
		
	}

	// 1.获取token
	private String getToken() {
		String sign = "appkey" + appkey + "appsecret" + appsecret;
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("appkey", appkey);
		paraMap.put("appsecret", appsecret);
		paraMap.put("sign", DigestUtils.sha1Hex(sign));
		try {
			String value = HttpSupport.connect(url + "/getToken", paraMap);
			JsonNode actualObj = new ObjectMapper().readTree(value);
			JsonNode code = actualObj.get("code");
			if ("000".equals(code.getTextValue())) {
				JsonNode data = actualObj.get("data");
				return data.get("token").getTextValue();
			}
			LOGGER.error("流米流量充值获取Token 失败，失败编码：{}", code.getTextValue());
		} catch (Exception e) {
			LOGGER.error("流米流量充值获取Token 异常", e.fillInStackTrace());
		}
		return null;
	}

	static class TrafficPackage {
		private int traffic;
		private String code;
		private double salePrice;

		public TrafficPackage(int traffic, String code, double salePrice) {
			this.traffic = traffic;
			this.code = code;
			this.salePrice = salePrice;
		}

		public int getTraffic() {
			return traffic;
		}

		public void setTraffic(int traffic) {
			this.traffic = traffic;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public double getSalePrice() {
			return salePrice;
		}

		public void setSalePrice(double salePrice) {
			this.salePrice = salePrice;
		}

		private static Map<OperatorType, Map<Integer, TrafficPackage>> statePackagemap = new HashMap<OperatorType, Map<Integer, TrafficPackage>>();

		static {
			Map<Integer, TrafficPackage> mobilePackage = new HashMap<Integer, TrafficPackage>();
			mobilePackage.put(10, new TrafficPackage(10, "YD10", 2.4));
			mobilePackage.put(30, new TrafficPackage(30, "YD30", 4));
			mobilePackage.put(70, new TrafficPackage(70, "YD70", 8));
			mobilePackage.put(150, new TrafficPackage(150, "YD150", 16));
			mobilePackage.put(500, new TrafficPackage(500, "YD500", 24));
			mobilePackage.put(1024, new TrafficPackage(1024, "YD1024", 40));
			statePackagemap.put(OperatorType.MOBILE, mobilePackage);
			/*
			 * List<TrafficPackage> unicomPackage = new
			 * ArrayList<TrafficPackage>(); unicomPackage.put(50, "LT50");
			 * unicomPackage.put(200, "LT200");
			 * statePackagemap.put(TeleOperator.UNICOM, unicomPackage);
			 * Map<Integer, String> telecomPackage = new HashMap<Integer,
			 * String>(); telecomPackage.put(10, "DX10"); telecomPackage.put(30,
			 * "DX30"); telecomPackage.put(50, "DX50"); telecomPackage.put(100,
			 * "DX100"); telecomPackage.put(200, "DX200");
			 * telecomPackage.put(500, "DX500"); telecomPackage.put(1024,
			 * "DX1024"); statePackagemap.put(TeleOperator.TELECOM,
			 * telecomPackage);
			 */
		}

		public static TrafficPackage getTrafficPacketeCode(
				OperatorType operator, int traffic) {
			return statePackagemap.get(operator).get(traffic);
		}

	}

	@Override
	public boolean isSupportTraffic(TrafficType ttype, int traffic) {
		return TrafficPackage.getTrafficPacketeCode(OperatorType.MOBILE,
				traffic) != null;
	}

	public static void main(String[] args) {
		new LiuMiVNO().charge( "13402199832",
				TrafficType.STATE, 500,"20150311100012");

	}

}