package org.sz.mbay.weixin;

import java.io.Serializable;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.mbay.common.util.HttpSupport;
import org.sz.mbay.weixin.config.ResourceConfig;

/**
 * @Description: 微信js-sdk
 * @author frank.zong
 * @date 2015-1-14 上午12:14:40
 * 		
 */
public class WeChatJSUtil {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(WeChatJSUtil.class);
			
	private WeChatJSUtil() {
	}
	
	/*** 时间 */
	private static DateTime lastTime;
	
	/** 获取频率 */
	private static final int FREQUENCY_SECOND = 7100;
	
	/*** access_token */
	private static String access_token;
	
	/*** ticket */
	private static String ticket;
	
	private final static String nonce_str = UUID.randomUUID().toString();
	
	/**
	 * 配置微信
	 * 
	 * @param request
	 * @param response
	 * @param url
	 *            -> 需要调用微信js的页面地址
	 * @return map
	 */
	public static WeChatVerifyConfig getVerifyConfig(String url) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("请求微信URL为：{}" + url);
		}
		// 如果超过7200秒，重新获取access_token,ticket,time
		if (StringUtils.isEmpty(access_token) || lastTime
				.plusSeconds(FREQUENCY_SECOND).isBefore(DateTime.now())) {
			access_token = getAccessToken();
			ticket = getTicket(access_token);
			lastTime = DateTime.now();
		}
		
		// timestamp
		String timestamp = String.valueOf(lastTime.getMillis() / 1000);
		// signature
		String signString = "jsapi_ticket=" + ticket + "&noncestr=" + nonce_str
				+ "&timestamp=" + timestamp + "&url=" + url;
		String signature = DigestUtils.sha1Hex(signString.getBytes());
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(
					"请求微信参数为：appId->{},nonceStr->{},signature->{},timestamp->{}",
					ResourceConfig.getAppId(),
					nonce_str, signature, timestamp);
		}
		
		WeChatVerifyConfig config = new WeChatVerifyConfig(
				ResourceConfig.getAppId(), timestamp,
				nonce_str, signature);
		return config;
	}
	
	private static String getAccessToken() {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+ ResourceConfig.getAppId()
				+ "&secret="
				+ ResourceConfig.getAppSecret();
		String access_token_result;
		try {
			access_token_result = HttpSupport.build(url).connect();
			if (!"".equals(access_token_result)) {
				JSONObject json = JSONObject.fromObject(access_token_result);
				if (json.get("errcode") == null) {
					access_token = json.get("access_token").toString();
					LOGGER.info("获取access_token成功,access_token:{}",
							access_token);
					return access_token;
				}
				LOGGER.error("获取access_token出错，出错原因:{}", json.get("errmsg")
						.toString());
			}
		} catch (Exception e) {
			LOGGER.error("获取accessToke异常", e.fillInStackTrace());
		}
		return "";
	}
	
	private static String getTicket(String access_token) {
		if (!"".equals(access_token)) {
			String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
					+ access_token + "&type=jsapi";
			String ticket_result;
			try {
				ticket_result = HttpSupport.build(url).connect();
				if (!"".equals(ticket_result)) {
					JSONObject json = JSONObject.fromObject(ticket_result);
					if ("ok".equals(json.get("errmsg").toString())) {
						ticket = json.get("ticket").toString();
						LOGGER.info("获取ticket成功,ticket:{}", ticket);
						return ticket;
					}
					LOGGER.error("获取ticket出错，出错原因:{}", json.get("errmsg")
							.toString());
				}
			} catch (Exception e) {
				LOGGER.error("获取Ticket异常", e.fillInStackTrace());
			}
		}
		return "";
	}
	
	/**
	 * 微信参数内部类
	 */
	public static class WeChatVerifyConfig implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -227310479070005948L;
		/**
		 * appid
		 */
		private String appId;
		/**
		 * 当前时间
		 */
		private String timestamp;
		/**
		 * 随机字符串
		 */
		private String nonceStr;
		/**
		 * 签名
		 */
		private String signature;
		
		/**
		 * 配置微信所需参数
		 * 
		 * @param appId
		 *            appid
		 * @param timestamp
		 *            当前时间
		 * @param nonceStr
		 *            随机字符串
		 * @param signature
		 *            签名
		 */
		public WeChatVerifyConfig(String appId, String timestamp,
				String nonceStr, String signature) {
			this.appId = appId;
			this.timestamp = timestamp;
			this.nonceStr = nonceStr;
			this.signature = signature;
		}
		
		@Override
		public String toString() {
			return "appId=" + appId + ";timestamp=" + timestamp + ";nonceStr="
					+ nonceStr + ";signature=" + signature;
		}
		
		public String getAppId() {
			return appId;
		}
		
		public void setAppId(String appId) {
			this.appId = appId;
		}
		
		public String getTimestamp() {
			return timestamp;
		}
		
		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}
		
		public String getNonceStr() {
			return nonceStr;
		}
		
		public void setNonceStr(String nonceStr) {
			this.nonceStr = nonceStr;
		}
		
		public String getSignature() {
			return signature;
		}
		
		public void setSignature(String signature) {
			this.signature = signature;
		}
	}
}
