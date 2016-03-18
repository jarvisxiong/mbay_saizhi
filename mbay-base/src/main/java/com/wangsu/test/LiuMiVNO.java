package com.wangsu.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import net.sf.json.JSONObject;

/**
 * @Description: 流米虚拟运营商流量充值接口
 * @author han.han
 * @date 2015-2-11 下午12:03:44
 * 
 */
public class LiuMiVNO {
	//static final Logger LOGGER = LoggerFactory.getLogger(LiuMiVNO.class);

	// 平台接口地址前缀
	private final static String url = "https://api.tenchang.com/server";

	// appkey,由平台分配
	private final static String appkey = "saizhikeji";

	// appsecret,由平台分配,需要进行md5加密
	private final static String appsecret = DigestUtils.md5Hex("szkj20150210");

	// appver
	private final static String appver = "Https";
	
	private static final String OPERATOR_CODE = "";//ResourceConfig.getProperty("LiuMiVNO");
	
	
	private static final String key = "87fc414edfe046fab02e38d8bce2a176da1776772ed94c6d861fce5fe1a6300b";

	// private static boolean flag = false;


	

	

	private static void recharge(String mobile, String packageCode, String orderNumber) {
		 System.out.println("接收到流米接口流量充值。ordernumber:{},mobile:{}");
		String token = getToken();// 获取token；
		if (token == null) {
			//return  ChargeResult.fail("获取token失败");
		}
	///	String packageCode = trafficPackage.getOperatorPackageCode();
		String sign = "appkey" + appkey + "appsecret" + appsecret + "appver" + appver + "extno" + orderNumber + "mobile"
				+ mobile + "postpackage" + packageCode + "token" + token;
		try {
			JSONObject json = new JSONObject();
			json.put("appkey", appkey);
			json.put("appsecret", appsecret);
			json.put("appver", appver);
			json.put("extno", orderNumber);
			json.put("fixtime", "");
			json.put("mobile", mobile);
			json.put("postpackage", packageCode);
			json.put("token", token);
			json.put("sign", DigestUtils.sha1Hex(sign));
			String value = org.sz.mbay.traffic.util.http.HttpSupport.connectViaPost(url + "/placeOrder", json.toString());
			JsonNode jsonNode = new ObjectMapper().readTree(value);
			String code = jsonNode.get("code").getTextValue();
			if ("000".equals(code)) {
				JsonNode data = jsonNode.get("data");
				String orderNo = data.get("orderNO").getTextValue();
				System.out.println("流米单号orderNO"+orderNo);
				
				//LOGGER.info("流米单号orderNO:{}", orderNo);
			//	return ChargeResult.recharging(trafficPackage, orderNo);
			} else {
				//return ChargeResult.fail("流米移动流量充值下单失败，失败编码：" + code);
			}
		} catch (Exception e) {
		//	LOGGER.error("流米流量充值系统异常", e.fillInStackTrace());
			//return  ChargeResult.fail("mbaytraffic系统异常");
		}
	}
	
	
	/**
	 * 生成body数据
	 * 
	 * @param phone
	 * @param cpOrderNos
	 * @return
	 */
	private static String getBody(String phone, String cpOrderNos) {
	
		JSONObject json = new JSONObject();
		json.put("phone", phone);
		json.put("cpOrderNos", cpOrderNos);
		return json.toString();
	}
	
	// 访问地址
	private static final String urlstr = "https://capi.fdn.chinanetcenter.com/user/order";
	
	private static void recharge2(String mobile, String packageCode, String orderNumber) {
		System.out.println("mobile="+mobile);
		// 订购编号,多个之间使用英文;号分隔,比如 移动-30M;移动-10M;联通-全国-20M;电信-全国-10M
		String cpOrderNos = packageCode;
		//LOGGER.info("执行网宿流量充值,充值信息：{}", "ordernumber:" + orderNumber + ",mobile:" + mobile + ",cpOrder:" + cpOrderNos);
		// body数据
		String body = getBody(mobile, cpOrderNos);
		// 认证字段,MD5(BODY_JSON+API_KEY)
		String auth = org.sz.mbay.common.util.DigestUtils.md5Encrypt(body + key);

		try {
			URL url = new URL(urlstr);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			byte[] b = body.getBytes();
			conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			conn.setRequestProperty("X-FDN-Auth", auth);
			conn.connect();
			OutputStream out = conn.getOutputStream();
			out.write(b);
			out.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			StringBuffer sb = new StringBuffer();
			String s;
			while ((s = reader.readLine()) != null) {
				sb.append(s);
			}
			reader.close();
			conn.disconnect();
			String result = sb.toString();
			if (!StringUtils.isEmpty(result)) {
				JSONObject json = JSONObject.fromObject(result);
				String responseCode = json.getString("responseCode");
				if ("10000".equals(responseCode)) {
					String responseData = json.getString("responseData");
					JSONObject json2 = JSONObject.fromObject(responseData);
					String orderId = json2.getString("orderId");
					System.out.println("订单网速下单成功，单号orderNO"+orderNumber+""+orderId);
					//return ChargeResult.recharging(trafficPackage, orderId);
				}
				System.out.println(responseCode);
				//return ChargeResult.fail("网速流量充值下单失败，失败编码：" + responseCode);
			}
			throw new IOException("无返回信息");
		} catch (IOException e) {
			//LOGGER.error("网宿流量充值系统异常", e.fillInStackTrace());
			///return ChargeResult.fail("mbaytraffic系统异常");
		}
	}


	// 1.获取token
	private static String getToken() {
		String sign = "appkey" + appkey + "appsecret" + appsecret;
		JSONObject json = new JSONObject();
		json.put("appkey", appkey);
		json.put("appsecret", appsecret);
		json.put("sign", DigestUtils.sha1Hex(sign));
		try {
			String value =org.sz.mbay.traffic.util.http.HttpSupport.connectViaPost(url + "/getToken", json.toString());
			JsonNode actualObj = new ObjectMapper().readTree(value);
			JsonNode code = actualObj.get("code");
			if ("000".equals(code.getTextValue())) {
				JsonNode data = actualObj.get("data");
				return data.get("token").getTextValue();
			}
			///LOGGER.error("流米流量充值获取Token 失败，失败编码：{}", code.getTextValue());
		} catch (Exception e) {
		//	LOGGER.error("流米流量充值获取Token 异常", e.fillInStackTrace());
		}
		return null;
	}

	/*

	private static Map<OperatorType, Map<Integer, String>> statePackagemap = new HashMap<OperatorType, Map<Integer, String>>();

	static {
		Map<Integer, String> telecomPackages = new HashMap<Integer, String>() {

			private static final long serialVersionUID = 1L;

			{
				put(5, "DX5");
				put(10, "DX10");
				put(30, "DX30");
				put(50, "DX50");
				put(100, "DX100");
				put(200, "DX200");
				put(500, "DX500");
				put(1024, "DX1024");
			}
		};
		Map<Integer, String> mobilePackages = new HashMap<Integer, String>() {
			private static final long serialVersionUID = 1L;

			{
				put(10, "YD10");
				put(30, "YD30");
				put(70, "YD70");
				put(150, "YD150");
				put(500, "YD500");
				put(1024, "YD1024");
			}
		};
		Map<Integer, String> unicomPackages = new HashMap<Integer, String>() {

			/**
			 * unicomPackage.put(20,new TrafficPackage(20, "LT20", 2.4));
			 * unicomPackage.put(50,new TrafficPackage(50, "LT50", 2.4));
			 * unicomPackage.put(100,new TrafficPackage(100, "LT100", 4));
			 * unicomPackage.put(200,new TrafficPackage(200, "LT200", 8));
			 * unicomPackage.put(500,new TrafficPackage(500, "LT500", 16));
			 
			private static final long serialVersionUID = 1L;

			{
				put(20, "LT20");
				put(50, "LT50");
				put(100, "LT100");
				put(200, "LT200");
				put(500, "LT500");
			}
		};

		statePackagemap.put(OperatorType.TELECOM, telecomPackages);
		statePackagemap.put(OperatorType.UNICOM, unicomPackages);
		statePackagemap.put(OperatorType.MOBILE, mobilePackages);

	}*/

	public static void main(String[] args) {
		System.out.println("888888");
		
    	System.out.println("azzzzzzzzzzz");	
    	//LT_57e3745412854766a41e8771eec75c03
    	//("18625427511", "LT500", "2016020110000076784");
	   LiuMiVNO.recharge("13402199832", "YD500", "2016020110000087489");
	   
	   System.out.println("ccccccccccccccccccccccccc");
		
	}
	
	
	
}