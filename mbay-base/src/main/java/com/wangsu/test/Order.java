package com.wangsu.test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.sz.mbay.common.util.MbayRandom.Digest;

import net.sf.json.JSONObject;

public class Order {
	
	public static void main(String[] args) {
		new Order().order();
	}
	
	
	public void order() {
		// 访问地址
		String urlstr = "https://capi.fdn-test.chinanetcenter.com/user/order";
		// API_KEY
		String key = "f8aedf8dca7d425ea52b3553e02214d9501d7340ceb7456ab5afac09c8f7ff85";
		// 手机号
		String phone = "18625427511";
		// 订购编号,多个之间使用英文;号分隔,比如 YD1000;DX1000;LT3000
		String cpOrderNos = "LT_a0d0129609fc4950bad0bf86db4ac680";
		// body数据
		String body = getBody(phone, cpOrderNos);
		// 认证字段,MD5(BODY_JSON+API_KEY)
		String auth = getAuth(body, key);
		
		try {
			URL url = new URL(urlstr);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			bout.write(body.getBytes());
			byte[] b = bout.toByteArray();
			conn.setRequestProperty("Content-Type","application/json; charset=utf-8");
			conn.setRequestProperty("X-FDN-Auth", auth);
			conn.connect();
			OutputStream out = conn.getOutputStream();
			out.write(b);
			out.close();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(conn.getInputStream(), "utf-8"));
			System.out.println("=============================");
			System.out.println("connection start");
			System.out.println("=============================");
			StringBuffer sb = new StringBuffer();
			String s;
			while ((s = reader.readLine()) != null) {
				sb.append(s);
			}
			String result = sb.toString();
			if(!StringUtils.isEmpty(result)){
				JSONObject json = JSONObject.fromObject(result);
				String responseCode = json.getString("responseCode");
				System.out.println("结果编码 -> " + responseCode);
				if("10000".equals(responseCode)){
					String responseData = json.getString("responseData");
					JSONObject json2 = JSONObject.fromObject(responseData);
					String orderId = json2.getString("orderId");
					System.out.println("订单号 -> " + orderId);
				}
			}
			reader.close();
			conn.disconnect();
			System.out.println("=============================");
			System.out.println("connection end");
			System.out.println("=============================");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成body数据
	 * 
	 * @param phone
	 * @param cpOrderNos
	 * @return
	 */
	private String getBody(String phone, String cpOrderNos) {
		JSONObject json = new JSONObject();
		json.put("phone", phone);
		json.put("cpOrderNos", cpOrderNos);
		return json.toString();
	}
	
	/**
	 * 生成认证字段 : MD5(BODY_JSON+API_KEY)
	 * 
	 * @param json
	 * @param key
	 * @return
	 */
	private String getAuth(String body, String key) {
		String value = body + key;
		return org.sz.mbay.common.util.DigestUtils.md5Encrypt(value);
	}
}
