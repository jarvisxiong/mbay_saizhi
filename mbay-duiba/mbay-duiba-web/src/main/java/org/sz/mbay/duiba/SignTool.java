package org.sz.mbay.duiba;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class SignTool {

	//private static final Logger LOGGER = LoggerFactory.getLogger(SignTool.class);
	
	public static boolean signVerify(String appSecret,HttpServletRequest request){
		Map<String, String[]> map=request.getParameterMap();
		Map<String, String> data=new HashMap<String, String>();
		for(String key:map.keySet()){
			String value = map.get(key)[0];
			if(key.equals("description") || key.equals("errorMessage")){
				try {
					/*String encode_value = java.net.URLEncoder.encode(value,"iso-8859-1");
					value = java.net.URLDecoder.decode(encode_value,"utf-8");*/
					value = java.net.URLDecoder.decode(value,"utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			data.put(key, value);
		}
		return signVerify(appSecret, data);
	}
	
	public static boolean signVerify(String appSecret,Map<String, String> params){
		Map<String, String> map=new HashMap<String, String>();
		map.put("appSecret", appSecret);
		
		for(String key:params.keySet()){
			if(!key.equals("sign")){
				map.put(key, params.get(key));
			}
		}
		//解析验证
		/*for(String key: map.keySet()){
			LOGGER.info("key->"+key+",value->"+map.get(key));
		}*/
		String sign=sign(map);
		if(sign.equals(params.get("sign"))){
			return true;
		}
		return false;
	}
	
	private static String toHexValue(byte[] messageDigest) {
		if (messageDigest == null)
			return "";
		StringBuilder hexValue = new StringBuilder();
		for (byte aMessageDigest : messageDigest) {
			int val = 0xFF & aMessageDigest;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
	/**
	 * 
	 * @param params
	 * @return
	 */
	public static String sign(Map<String,String> params){
		List<String> keys=new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String string="";
		for(String s:keys){
			string+=params.get(s);
		}
		String sign="";
		try {
			sign = toHexValue(encryptMD5(string.getBytes(Charset.forName("utf-8"))));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("md5 error");
		}
		return sign;
	}
	
	private static byte[] encryptMD5(byte[] data)throws Exception{
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(data);
		return md5.digest();
	}
	
	public static void main(String[] args) {
		String appKey="3xkYdP56iZSf8haQkPV7Jmuqq4ik";
		String appSecret="uYxGFzrKXWz5pF2yorQH543envX";
		
		Map<String, String> params=new HashMap<String, String>();
		try{
			params.put("uid", "18625427511");
			params.put("phone", "");
			params.put("credits", "1");
			params.put("orderNum", "2015041411540610000811603");
			params.put("params", "");
			params.put("type", "coupon");
			params.put("ip", "116.238.176.188");
			params.put("waitAudit", "false");
			params.put("timestamp", "1428983646160");
			params.put("actualPrice", "0");
			params.put("alipay", "");
			String value = "%E8%88%AA%E7%A9%BA%E6%84%8F%E5%A4%96%E9%99%A9";
			System.out.println("decode->"+java.net.URLDecoder.decode(value,"utf-8"));
			params.put("description", java.net.URLDecoder.decode(value,"utf-8"));
			params.put("facePrice", "10");
			params.put("qq", "");
			params.put("appSecret", appSecret);
			params.put("appKey", appKey);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String sign=sign(params);
		System.out.println(sign.equals("b497412378f8f8fb702bf3fc14c70790"));
		
		params.put("sign", sign);
		System.out.println(signVerify(appSecret, params));
	}
}