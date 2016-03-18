package org.sz.mbay.sms.client;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.sz.mbay.captcha.SMSAuthCodeUtil;
import org.sz.mbay.common.util.HttpSupport;
import org.sz.mbay.sms.config.MbaySmsConfig;
import org.sz.mbay.sms.template.base.BaseSMS;
import org.sz.mbay.sms.template.enums.CaptchaSMSType;
import org.sz.mbay.sms.template.enums.SMSType;

import net.sf.json.JSONObject;

/**
 * 短信辅助类
 * 
 * @author jerry
 */
public class MbaySMS {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MbaySMS.class);
	
	// 时间段内不再重新发送(分钟)
	private static final int NOT_SEND_TIME = 1;
	
	/*
	 * 短信发送
	 */
	private static void sendSMS(String smsName, String mobiles, String params) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(mobiles + "短信发送：" + "--短信模板:" + smsName
					+ "--短信模板参数:" + params
					+ " *********************************************");
		}
		if (!StringUtils.isEmpty(smsName) && !StringUtils.isEmpty(mobiles)) {
			Map<String, String> map = new HashMap<>();
			map.put("mobiles", mobiles);
			map.put("smsName", smsName);
			map.put("param", params);
			new SMSThread(MbaySmsConfig.SMS_URL, map).start();
		}
	}
	
	/**
	 * 短信发送
	 * 
	 * @param type
	 *            短信类型
	 * @param mobiles
	 *            手机号 多个手机号用,隔开
	 * @param params
	 *            多个参数,号分隔
	 */
	public static void sendSMS(SMSType type, String mobiles,
			String params) {
		if (type != null && !StringUtils.isEmpty(mobiles)) {
			sendSMS(type.name(), mobiles, params);
		}
	}
	
	/**
	 * 短信发送
	 * 
	 * @param type
	 *            短信类型
	 * @param mobiles
	 *            手机号 多个手机号用,隔开
	 * @param code
	 *            多个参数,号分隔
	 */
	public static void sendCaptchaSMS(CaptchaSMSType type, String mobiles,
			String code) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		sendCaptchaSMSCommon(request.getSession(), type, mobiles, code);
	}
	
	/**
	 * 短信发送
	 * 
	 * @param type
	 *            短信类型
	 * @param mobiles
	 *            手机号 多个手机号用,隔开
	 * @param code
	 *            多个参数,号分隔
	 */
	public static void sendCaptchaSMS(HttpSession session, CaptchaSMSType type,
			String mobiles,
			String code) {
		sendCaptchaSMSCommon(session, type, mobiles, code);
	}
	
	private static void sendCaptchaSMSCommon(HttpSession session,
			CaptchaSMSType type, String mobiles,
			String code) {
		if (type != null && !StringUtils.isEmpty(mobiles)
				&& !StringUtils.isEmpty(code)) {
			if (SMSAuthCodeUtil.isSended(session, type)) {
				DateTime genTime = SMSAuthCodeUtil.getGeneratedTime(session,
						type);
				if (genTime != null && DateTime.now()
						.isBefore(genTime.plusMinutes(NOT_SEND_TIME))) {
					return;
				}
			}
			sendSMS(type.name(), mobiles, code);
			SMSAuthCodeUtil.setSended(session, type);
		}
	}
	
	/**
	 * 通知短信模板缓存更新（从数据库重新读取）
	 * 
	 * @param type
	 */
	public static boolean updateSMSCache(BaseSMS type) {
		if (type != null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("更新短信模板：{}", type.getName());
			}
			StringBuffer sb = new StringBuffer();
			sb.append("smsName=").append(type.getName());
			try {
				String resp = HttpSupport.build(
						MbaySmsConfig.SMS_TEMPLATE_UPDATE_URL + "?"
								+ sb.toString())
						.connect();
				return Boolean.parseBoolean(resp);
			} catch (Exception e) {
				LOGGER.error("请求短信接口异常：{}", e.getMessage());
				return false;
			}
		}
		return false;
	}
	
	/**
	 * 获取短信模板内容
	 * 
	 * @param type
	 */
	public static String getSMSContent(BaseSMS type) {
		if (type != null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("获取短信模板：{}", type.getName());
			}
			StringBuffer sb = new StringBuffer();
			sb.append("smsName=").append(type.getName());
			try {
				String resp = HttpSupport
						.build(MbaySmsConfig.SMS_TEMPLATE_GET_URL + "?"
								+ sb.toString())
						.connect();
				if (!StringUtils.isEmpty(resp)) {
					JSONObject obj = JSONObject.fromObject(resp);
					return obj.getString("content");
				}
			} catch (Exception e) {
				LOGGER.error("请求短信接口异常：{}", e.getMessage());
			}
		}
		return null;
	}
	
	/**
	 * 语音验证码发送
	 * 
	 * @param mobile
	 * 			
	 * @param smsTemplateId
	 *            短信模板id
	 * @param params
	 *            多个参数,号分隔
	 * @return
	 */
	public static void sendVoiceCode(String mobile, String code) {
		if (!StringUtils.isEmpty(mobile)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(mobile + "语音验证码：" + code
						+ " *********************************************");
			}
			if (!StringUtils.isEmpty(code)) {
				Map<String, String> map = new HashMap<>();
				map.put("mobile", mobile);
				map.put("code", code);
				new SMSThread(MbaySmsConfig.VOICE_CODE_URL, map).start();
			}
		}
	}
	
	/**
	 * 发短信线程
	 * 
	 * @author jerry
	 */
	private static class SMSThread extends Thread {
		
		private String smsUrl;
		private Map<String, String> params;
		
		public SMSThread(String smsUrl, Map<String, String> params) {
			this.params = params;
			this.smsUrl = smsUrl;
		}
		
		@Override
		public void run() {
			try {
				HttpSupport.build(smsUrl, params).connect();
			} catch (Exception e) {
				LOGGER.error("请求短信接口异常：{}", e.getMessage());
			}
		}
	}
	
}
