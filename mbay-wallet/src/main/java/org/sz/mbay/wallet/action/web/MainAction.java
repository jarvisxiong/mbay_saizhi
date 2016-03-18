package org.sz.mbay.wallet.action.web;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.base.annotation.CacheControl;
import org.sz.mbay.base.annotation.CachePolicy;
import org.sz.mbay.base.regex.pattern.RegExp;
import org.sz.mbay.base.web.utils.RequestUtil;
import org.sz.mbay.base.wrap.Response;
import org.sz.mbay.base.wrap.ResponseFail;
import org.sz.mbay.base.wrap.ResponseSuccess;
import org.sz.mbay.captcha.CaptchaResult;
import org.sz.mbay.captcha.SMSAuthCodeUtil;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.remote.interfaces.wallet.RIUserUtil;
import org.sz.mbay.remote.interfaces.wallet.base.RIResponse;
import org.sz.mbay.sms.client.MbaySMS;
import org.sz.mbay.sms.template.enums.CaptchaSMSType;
import org.sz.mbay.wallet.constant.Global;
import org.sz.mbay.wallet.constant.Global.SessionKey;
import org.sz.mbay.wallet.constant.error.GlobalError;
import org.sz.mbay.wallet.constant.error.UserError;
import org.sz.mbay.wallet.context.WalletConfig;
import org.sz.mbay.wallet.context.WalletContext;
import org.sz.mbay.wallet.entity.SessionUser;
import org.sz.mbay.wallet.interceptor.IdentifyInterceptor;

/**
 * 网页版钱包
 * 
 * @author Fenlon
 * 		
 */
@Controller("Web_MainAction")
@RequestMapping("web/main")
public class MainAction {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MainAction.class);
			
	/**
	 * 登录界面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@CacheControl(policy = CachePolicy.NO_CACHE)
	@RequestMapping("login/ui")
	public String loginUi(
			@RequestParam(value = "mobile", required = false,
					defaultValue = "") String mobile,
			Model model,
			HttpServletRequest request,
			HttpServletResponse response) {
		Response rsp = autoLogin(mobile, request, response);
		if (rsp.getStatus()) {
			// 是否需要重定向到拦截页面
			if (rsp instanceof ResponseSuccess) {
				try {
					String red = String
							.valueOf(((ResponseSuccess) rsp).getData());
					if (StringUtils.isNotEmpty(red)) {
						response.sendRedirect(red);
					}
				} catch (IOException e) {
					LOGGER.error("redirectAfterLogin error:{}", e.getMessage());
				}
				return null;
			}
			return "redirect:/web/main/index/ui.mbay?mobile=" + mobile;
		}
		if (StringUtils.isEmpty(mobile)) {
			String[] loginInfo = getLoginInfo(request);
			if (loginInfo[0] != null) {
				mobile = loginInfo[0];
			}
		}
		model.addAttribute("mobile", mobile);
		return "main/login";
	}
	
	/*
	 * 是否可以自动登陆
	 */
	private Response autoLogin(
			String mobile,
			HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.info("try autologin:{}", mobile);
		
		// 已登录，session含有登陆信息
		SessionUser suser = WalletContext.getSessionUser();
		if (suser != null) {
			LOGGER.info("session user exists:{}", suser.getMobile());
			return Response.SUCCESS;
		}
		
		// cookie中含有登陆信息
		String[] loginInfo = getLoginInfo(request);
		if (StringUtils.isEmpty(mobile)) {
			if (loginInfo[0] != null) {
				mobile = loginInfo[0];
			}
		}
		if (loginInfo[1] != null) {
			Response result = (Response) loginPwd(mobile, loginInfo[1], true,
					response);
			LOGGER.info("loginPwd result:{}", result);
			return result;
		}
		return Response.FAIL;
	}
	
	/*
	 * 获取cookie登陆信息
	 */
	private String[] getLoginInfo(HttpServletRequest request) {
		String[] datas = new String[2];
		Cookie mobile = RequestUtil.getCookie(request, Global.LOGIN_MOBILE);
		if (mobile != null) {
			datas[0] = mobile.getValue();
		}
		Cookie passwd = RequestUtil.getCookie(request, Global.LOGIN_PASSWORD);
		if (passwd != null) {
			datas[1] = passwd.getValue();
		}
		return datas;
	}
	
	/*
	 * 记录登陆信息
	 */
	private void remLoginInfo(HttpServletResponse response, String uname,
			String encodedPwd) {
		if (StringUtils.isNotEmpty(uname)) {
			RequestUtil.setCookie(response, Global.LOGIN_MOBILE, uname,
					60 * 60 * 24 * 30);
		}
		if (StringUtils.isNotEmpty(encodedPwd)) {
			RequestUtil.setCookie(response, Global.LOGIN_PASSWORD,
					encodedPwd, 60 * 60 * 24 * 30);
		}
	}
	
	/**
	 * 登录 - 账号/密码
	 * 
	 * @param mobile
	 *            手机号
	 * @param pwd
	 *            密码
	 * @return
	 */
	@ResponseBody
	@RequestMapping("login/pwd")
	public Object loginPwd(
			@RequestParam("mobile") String mobile,
			@RequestParam("pwd") String pwd,
			@RequestParam(value = "enc", required = false,
					defaultValue = "false") boolean encodedPwd,
			HttpServletResponse response) {
		try {
			if (!encodedPwd) {
				pwd = DigestUtils.md5Encrypt(DigestUtils.md5Encrypt(pwd));
			}
			RIResponse resp = RIUserUtil.requestUserLogin(mobile, pwd);
			
			// 将用户保存到session中
			HttpSession session = WalletContext.getHttpSession();
			SessionUser user = new SessionUser();
			user.setMobile(mobile);
			user.setUserNumber(resp.getData().getString("userNum"));
			session.setAttribute(SessionKey.SESSION_USER, user);
			
			// 记录登陆信息到cookie
			remLoginInfo(response, mobile, pwd);
			
			// 是否需要重定向到拦截页面
			Object red = session
					.getAttribute(IdentifyInterceptor.redirectAfterLogin);
			if (red != null) {
				session.removeAttribute(IdentifyInterceptor.redirectAfterLogin);
				return ResponseSuccess.create(String.valueOf(red));
			}
			
			return Response.SUCCESS;
		} catch (Exception e) {
			LOGGER.error("login error: {}", e.getMessage());
			return ResponseFail.create(e.getMessage());
		}
	}
	
	/**
	 * 获取登陆验证码
	 * 
	 * @param mobile
	 * @param flag
	 * @return
	 */
	@RequestMapping("login/code/get")
	@ResponseBody
	public Object loginCodeGet(
			@RequestParam("mobile") String mobile,
			@RequestParam(value = "type", required = false,
					defaultValue = "1") int type) {
		if (StringUtils.isEmpty(mobile)) {
			return UserError.UNAME_EMPTY_REJECT;
		}
		if (!RegExp.mobile.matcher(mobile).matches()) {
			return UserError.UNAME_FORMAT_ERROR;
		}
		
		String code = SMSAuthCodeUtil
				.generateAuthCode(CaptchaSMSType.WL_REGISTER_CODE, mobile);
		switch (type) {
			case 1: // 短信
				MbaySMS.sendCaptchaSMS(CaptchaSMSType.WL_REGISTER_CODE,
						mobile, code);
				break;
			case 2: // 语音验证码
				MbaySMS.sendVoiceCode(mobile, code);
				break;
			default:
				return GlobalError.SMS_TYPE_ERROR;
		}
		
		WalletContext.getHttpSession().setAttribute(SessionKey.MOBILE, mobile);
		return Response.SUCCESS;
	}
	
	/**
	 * 登录 - 账号/验证码
	 * 
	 * @param mobile
	 *            手机号
	 * @param pwd
	 *            密码
	 * @return
	 */
	@ResponseBody
	@RequestMapping("login/code")
	public Object loginCode(
			@RequestParam("mobile") String mobile,
			@RequestParam("code") String code,
			HttpServletResponse response) {
		if (StringUtils.isEmpty(mobile)) {
			return UserError.UNAME_EMPTY_REJECT;
		}
		if (!RegExp.mobile.matcher(mobile).matches()) {
			return UserError.UNAME_FORMAT_ERROR;
		}
		
		// 验证验证码
		CaptchaResult cr = SMSAuthCodeUtil
				.isAuthCodeValid(CaptchaSMSType.WL_REGISTER_CODE, code, mobile);
		if (!cr.isSuccess()) {
			return ResponseFail.create(cr.getError_code(), cr.getMessage());
		}
		return Response.SUCCESS;
	}
	
	/**
	 * 注销
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("logout")
	public String logout(HttpServletResponse response) {
		SessionUser suser = WalletContext.getSessionUser();
		WalletContext.getHttpSession().removeAttribute(
				SessionKey.SESSION_USER);
		RequestUtil.deleteCookie(response, Global.LOGIN_PASSWORD);
		return "redirect:/web/main/login/ui.mbay?mobile=" + (suser == null
				? StringUtils.EMPTY : suser.getMobile());
	}
	
	/**
	 * 钱包首页
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@CacheControl(policy = CachePolicy.NO_CACHE)
	@RequestMapping("index/ui")
	public String indexUi(
			@RequestParam(value = "mobile", required = false) String mobile,
			Model model,
			HttpServletRequest request,
			HttpServletResponse response) {
		// 自动登陆
		autoLogin(mobile, request, response);
		
		SessionUser suser = WalletContext.getSessionUser();
		if (StringUtils.isEmpty(mobile) && suser != null) {
			mobile = suser.getMobile();
		}
		
		if (StringUtils.isNotEmpty(mobile)) {
			// 安全检查
			// 用一个号码登录后，使用另一个号码请求该接口
			if (suser != null) {
				String sessMobile = suser.getMobile();
				if (!sessMobile.equals(mobile)) {
					WalletContext.getHttpSession().removeAttribute(
							SessionKey.SESSION_USER);
				}
			}
			
			// 获取基本信息
			try {
				RIResponse resp = RIUserUtil.requestUserInfoByMobile(mobile);
				model.addAttribute("telephone", mobile);
				model.addAttribute("balance",
						resp.getData().getDouble("balance"));
			} catch (Exception e) {
				LOGGER.error("get user info error:{}", e.getMessage());
			}
		}
		WalletContext.getHttpSession().setAttribute(SessionKey.MOBILE, mobile);
		return "main/index";
	}
	
	/**
	 * 兑吧商城
	 * 
	 * @return
	 */
	@CacheControl(policy = CachePolicy.NO_CACHE)
	@RequestMapping("duiba/ui")
	public void duibaUi(
			@RequestParam(value = "mallId", required = false,
					defaultValue = "0") Integer mallId,
			HttpServletResponse response) {
		String mobile = WalletContext.getSessionUser().getMobile();
		String duiba_url = WalletConfig.DUIBA_URL;
		duiba_url = duiba_url.replace("MOBILE",
				DigestUtils.pbeEncrypt(mobile));
		duiba_url = duiba_url.replace("MALLID",
				mallId == 0 ? WalletConfig.MALLID
						: String.valueOf(mallId));
		try {
			response.sendRedirect(duiba_url);
		} catch (IOException e) {
			LOGGER.error("redirect to duiba market error:{}", e.getMessage());
		}
	}
	
	/**
	 * 忘记密码/注册
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@CacheControl(policy = CachePolicy.NO_CACHE)
	@RequestMapping("pwd/forget/ui")
	public String pwdForgetUi(
			@RequestParam(value = "mobile", required = false) String mobile,
			Model model) {
		model.addAttribute("mobile", mobile);
		return "main/pwd/forget";
	}
	
	/**
	 * 忘记密码/注册 - 获取重置验证码
	 * 
	 * @param mobile
	 * @param flag
	 * @return
	 */
	@RequestMapping("pwd/forget/reset/code/get")
	@ResponseBody
	public Object pwdForgetResetCodeGet(
			@RequestParam("mobile") String mobile,
			@RequestParam(value = "type", required = false,
					defaultValue = "1") int type) {
		if (StringUtils.isEmpty(mobile)) {
			return UserError.UNAME_EMPTY_REJECT;
		}
		if (!RegExp.mobile.matcher(mobile).matches()) {
			return UserError.UNAME_FORMAT_ERROR;
		}
		
		// 发送验证码
		String code = SMSAuthCodeUtil
				.generateAuthCode(CaptchaSMSType.WL_MODIFY_PASSWD, mobile);
		switch (type) {
			case 1: // 短信
				MbaySMS.sendCaptchaSMS(CaptchaSMSType.WL_MODIFY_PASSWD,
						mobile, code);
				break;
			case 2: // 语音验证码
				MbaySMS.sendVoiceCode(mobile, code);
				break;
			default:
				return GlobalError.SMS_TYPE_ERROR;
		}
		
		WalletContext.getHttpSession().setAttribute(SessionKey.MOBILE, mobile);
		return Response.SUCCESS;
	}
	
	/**
	 * 忘记密码/注册 - 检查重置验证码
	 * 
	 * @param mobile
	 * @param flag
	 * @return
	 */
	@RequestMapping("pwd/forget/reset/code/check")
	@ResponseBody
	public Object pwdForgetResetCodeCheck(
			@RequestParam("checkcode") String checkcode,
			@RequestParam("mobile") String mobile) {
		CaptchaResult cr = SMSAuthCodeUtil
				.isAuthCodeValid(CaptchaSMSType.WL_MODIFY_PASSWD, checkcode,
						mobile);
		if (!cr.isSuccess()) {
			return ResponseFail.create(cr.getError_code(), cr.getMessage());
		}
		return ResponseSuccess.create(checkcode);
	}
	
	/**
	 * 忘记密码/注册 - 设置新密码界面
	 * 
	 * @return
	 */
	@CacheControl(policy = CachePolicy.NO_CACHE)
	@RequestMapping("pwd/forget/set/ui")
	public String pwdForgetSetUi(
			@RequestParam("checkcode") String checkcode,
			@RequestParam("type") String type,
			Model model) {
		model.addAttribute("checkcode", checkcode);
		model.addAttribute("type", type);
		return "main/pwd/forget/set";
	}
	
	/**
	 * 忘记密码/注册 - 设置新密码
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("pwd/forget/set")
	public Object pwdForgetSet(
			@RequestParam("checkcode") String checkcode,
			@RequestParam("passwd") String passwd,
			@RequestParam("type") String type,
			HttpServletResponse response) {
		// 从session中获取手机号
		HttpSession session = WalletContext.getHttpSession();
		String mobile = (String) session.getAttribute(SessionKey.MOBILE);
		
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("setNewPwd:type{}checkcode{}mobile:{}", type,
					checkcode, mobile);
		}
		
		// 参数验证
		if (StringUtils.isEmpty(mobile)) {
			return GlobalError.PAGE_EXPIRED;
		}
		if (!RegExp.mobile.matcher(mobile).matches()) {
			return UserError.UNAME_FORMAT_ERROR;
		}
		if (StringUtils.isEmpty(passwd)) {
			return UserError.PASSWD_EMPTY_REJECT;
		}
		
		// 再次检测验证码（防止跳过前面的步奏，直接请求该接口）
		CaptchaSMSType ct = CaptchaSMSType.valueOf(type);
		CaptchaResult cr = SMSAuthCodeUtil.isAuthCodeValid(ct, checkcode,
				mobile);
		if (!cr.isSuccess()) {
			return ResponseFail.create(cr.getError_code(), cr.getMessage());
		}
		SMSAuthCodeUtil.removeAuthCode(ct);
		
		// 删除session
		session.removeAttribute(SessionKey.MOBILE);
		
		// 自动注册
		try {
			RIUserUtil.requestWalletAutoReg(mobile, "美贝钱包");
		} catch (Exception e) {
			LOGGER.error("register error:{}", e.getMessage());
			return ResponseFail.create(e.getMessage());
		}
		
		// 更新用户密码
		try {
			RIResponse respWD = RIUserUtil
					.requestUserUpdatePassWord(mobile, passwd);
					
			// 记录登陆信息到cookie
			remLoginInfo(response, mobile,
					respWD.getData().getString("password"));
		} catch (Exception e) {
			LOGGER.error("update password error:{}", e.getMessage());
			return ResponseFail.create(e.getMessage());
		}
		
		return ResponseSuccess.create(mobile);
	}
	
	/**
	 * 检测用户是否存在
	 * 
	 * @param mobile
	 * @return
	 */
	@ResponseBody
	@RequestMapping("isUserExsits")
	public boolean isUserExsits(@RequestParam("mobile") String mobile) {
		try {
			RIResponse resp = RIUserUtil.requestUserIsExistByMobile(mobile);
			return resp.getData().getBoolean("exist");
		} catch (Exception e) {
			LOGGER.error("check user exsits error:{}", e.getMessage());
		}
		return false;
	}
	
}
