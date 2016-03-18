package org.sz.mbay.captcha;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.sz.mbay.sms.template.enums.CaptchaSMSType;

/**
 * @Description: 短信验证码Util类
 * @author han.han
 * @date 2015-1-23 上午9:54:32
 * 		
 */
public class SMSAuthCodeUtil {
	
	/** 最大错误次数 **/
	private static final int MAX_ERROR_TIMES = 3;
	/** 验证码有效期：分钟 ***/
	private static final int VALIDITY_TIME = 30;
	/** Session中包含的验证码Name key ***/
	private static final String AUTHCODE_KEY = "AUTHCODE";
	
	private static final Random random = new Random();
	
	private static final CaptchaResult SUCCESS = new CaptchaResult(
			true, "SUCCESS", "验证通过!");
			
	private static final CaptchaResult INVALID_AUTHCODE = new CaptchaResult(
			false, "RE_OBTAIN", "校验码已经失效,请重新获取校验码!");
			
	private static final CaptchaResult INCORRECT_OVERLIMIT = new CaptchaResult(
			false, "RE_OBTAIN", "错误次数超过" + MAX_ERROR_TIMES + "次,请重新获取短信");
			
	private static final CaptchaResult INCORRECT_AUTHCODE = new CaptchaResult(
			false, "INCORRECT_AUTHCODE", "校验码不正确，请重新输入!");
			
	private static final CaptchaResult MOBILE_NOT_SAME = new CaptchaResult(
			false, "MOBILE_NOT_SAME", "手机号码不正确，请使用接收验证码的手机号!");
			
	/**
	 * 生成验证码
	 * 
	 * @param type
	 *            短信模板类型
	 * @param mobile
	 *            号码
	 * @return
	 */
	public static String generateAuthCode(CaptchaSMSType type, String mobile) {
		return generateAuthCodeCommon(getHttpSession(), type, mobile);
	}
	
	/**
	 * 生成验证码
	 * 
	 * @param type
	 *            短信模板类型
	 * @param mobile
	 *            号码
	 * @return
	 */
	public static String generateAuthCode(HttpSession session,
			CaptchaSMSType type, String mobile) {
		return generateAuthCodeCommon(session, type, mobile);
	}
	
	private static String generateAuthCodeCommon(HttpSession session,
			CaptchaSMSType type, String mobile) {
		String key = getKey(type);
		AuthCode saved = (AuthCode) session.getAttribute(key);
		
		// 已发送过验证码
		if (saved != null && saved.getMobile().equals(mobile)) {
			int errorTimes = saved.getErrorTimes();
			DateTime generatedTime = saved.getGeneratedTime();
			
			// VALIDITY_TIME 时间内 并且错误次数小于3次 发送session中的验证码
			if (!generatedTime.plusMinutes(VALIDITY_TIME)
					.isBefore(DateTime.now())
					&& errorTimes < MAX_ERROR_TIMES) {
				return saved.getCode();
			}
		}
		
		String randomCode = getSixRandom();
		AuthCode authcode = new AuthCode(mobile, randomCode, DateTime.now(), 0);
		session.setAttribute(key, authcode);
		return randomCode;
	}
	
	/**
	 * 获取验证码生成时间
	 * 
	 * @param type
	 * @return
	 */
	public static DateTime getGeneratedTime(CaptchaSMSType type) {
		HttpSession session = getHttpSession();
		return getGeneratedTimeCommon(session, type);
	}
	
	/**
	 * 获取验证码生成时间
	 * 
	 * @param type
	 * @return
	 */
	public static DateTime getGeneratedTime(HttpSession session,
			CaptchaSMSType type) {
		return getGeneratedTimeCommon(session, type);
	}
	
	private static DateTime getGeneratedTimeCommon(HttpSession session,
			CaptchaSMSType type) {
		String key = getKey(type);
		AuthCode saved = (AuthCode) session.getAttribute(key);
		if (saved != null) {
			return saved.getGeneratedTime();
		}
		return null;
	}
	
	/**
	 * 获取验证码是否已发送过
	 * 
	 * @param type
	 * @return
	 */
	public static boolean isSended(CaptchaSMSType type) {
		HttpSession session = getHttpSession();
		return isSendedCommon(session, type);
	}
	
	/**
	 * 获取验证码是否已发送过
	 * 
	 * @param type
	 * @return
	 */
	public static boolean isSended(HttpSession session, CaptchaSMSType type) {
		return isSendedCommon(session, type);
	}
	
	private static boolean isSendedCommon(HttpSession session,
			CaptchaSMSType type) {
		String key = getKey(type);
		AuthCode saved = (AuthCode) session.getAttribute(key);
		if (saved != null) {
			return saved.isSended();
		}
		return false;
	}
	
	/**
	 * 设置验证码状态为已发送过
	 * 
	 * @param type
	 */
	public static void setSended(CaptchaSMSType type) {
		HttpSession session = getHttpSession();
		setSendedCommon(session, type);
	}
	
	/**
	 * 设置验证码状态为已发送过
	 * 
	 * @param type
	 */
	public static void setSended(HttpSession session, CaptchaSMSType type) {
		setSendedCommon(session, type);
	}
	
	public static void setSendedCommon(HttpSession session,
			CaptchaSMSType type) {
		String key = getKey(type);
		AuthCode saved = (AuthCode) session.getAttribute(key);
		if (saved != null) {
			saved.setSended(true);
			session.setAttribute(key, saved);
		}
	}
	
	/*
	 * 拼接key
	 */
	private static String getKey(CaptchaSMSType type) {
		String key = AUTHCODE_KEY;
		if (type != null) {
			key += "@" + type.name();
		}
		return key;
	}
	
	/**
	 * 判断验证码是否正确
	 * 
	 * 默认手机号码正确
	 * 
	 * @param type
	 * @param requestCode
	 * @return
	 */
	public static CaptchaResult isAuthCodeValid(CaptchaSMSType type,
			String requestCode) {
		return isAuthCodeValid(type, requestCode, null);
	}
	
	/**
	 * 判断验证码是否正确
	 * 
	 * 默认手机号码正确
	 * 
	 * @param type
	 * @param requestCode
	 * @return
	 */
	public static CaptchaResult isAuthCodeValid(HttpSession session, CaptchaSMSType type,
			String requestCode) {
		return isAuthCodeValid(session, type, requestCode, null);
	}
	
	/**
	 * 判断验证码是否正确
	 * 
	 * @param type
	 * @param requestCode
	 * @param mobile
	 * @return
	 */
	public static CaptchaResult isAuthCodeValid(CaptchaSMSType type,
			String requestCode, String mobile) {
		HttpSession session = getHttpSession();
		return isAuthCodeVallidCommon(session, type, requestCode, mobile);
	}
	
	/**
	 * 判断验证码是否正确
	 * 
	 * @param type
	 * @param requestCode
	 * @param mobile
	 * @return
	 */
	public static CaptchaResult isAuthCodeValid(HttpSession session,
			CaptchaSMSType type,
			String requestCode, String mobile) {
		return isAuthCodeVallidCommon(session, type, requestCode, mobile);
	}
	
	public static CaptchaResult isAuthCodeVallidCommon(HttpSession session,
			CaptchaSMSType type,
			String requestCode, String mobile) {
		String key = getKey(type);
		AuthCode authcode = (AuthCode) session.getAttribute(key);
		if (authcode == null || StringUtils.isEmpty(requestCode)) {
			return INCORRECT_AUTHCODE;
		}
		// 判断号码是否一致
		if (mobile != null && !authcode.getMobile().equals(mobile)) {
			return MOBILE_NOT_SAME;
		}
		// 判断错误次数
		if (authcode.getErrorTimes() == MAX_ERROR_TIMES) {
			return INCORRECT_OVERLIMIT;
		}
		// 判断是否在有效时间内
		DateTime generateTime = authcode.getGeneratedTime();
		if (generateTime.plusMinutes(VALIDITY_TIME).isBefore(DateTime.now())) {
			return INVALID_AUTHCODE;
		}
		// 判断是否一致
		String code = authcode.getCode();
		if (!requestCode.equals(code)) {
			authcode.setErrorTimes(authcode.getErrorTimes() + 1);
			session.setAttribute(key, authcode);
			return INCORRECT_AUTHCODE;
		}
		return SUCCESS;
	}
	
	/**
	 * 删除验证码
	 * 
	 * @param request
	 */
	public static void removeAuthCode(CaptchaSMSType type) {
		HttpSession session = getHttpSession();
		removeAuthCodeCommon(session, type);
	}
	
	/**
	 * 删除验证码(jfinal)
	 * 
	 * @param request
	 */
	public static void removeAuthCode(HttpSession session,
			CaptchaSMSType type) {
		removeAuthCodeCommon(session, type);
	}
	
	private static void removeAuthCodeCommon(HttpSession session,
			CaptchaSMSType type) {
		String key = getKey(type);
		session.removeAttribute(key);
	}
	
	/**
	 * 获取六位随机数(只包含数字)
	 * 
	 * @return
	 */
	private static String getSixRandom() {
		StringBuilder sb = new StringBuilder(6);
		for (int i = 0; i < 6; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}
	
	private static HttpServletRequest getServletRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request;
	}
	
	private static HttpSession getHttpSession() {
		return getServletRequest().getSession();
	}
}
