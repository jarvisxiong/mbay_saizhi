package org.sz.mbay.channel.action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.sz.mbay.base.annotation.CacheControl;
import org.sz.mbay.base.annotation.CachePolicy;
import org.sz.mbay.base.annotation.Token;
import org.sz.mbay.base.web.servlet.tags.token.TokenProcessor;
import org.sz.mbay.base.web.utils.RequestUtil;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.costant.error.Error;
import org.sz.mbay.channel.framework.action.BaseAction;
import org.sz.mbay.channel.user.bean.User;
import org.sz.mbay.channel.user.enums.ChannelProperty;
import org.sz.mbay.channel.user.service.UserService;

/**
 * 渠道商用户Action 基本功能 注册，用户名验证
 * 
 * @author ONECITY
 * 
 */
@Controller
@RequestMapping("signup")
public class SignupController extends BaseAction {
	
	/**
	 * 跳转到--》个人商户注册说明页面
	 */
	public static final String REGISTER = "signup/person_read_tips";
	/**
	 * 个人商户注册页面
	 */
	public static final String PERSONREGISTER = "signup/person_signup";
	/**
	 * 个人商户注册成功
	 */
	public static final String PERSON_REGISTER_SUCCESS = "signup/person_signup_success";
	/**
	 * 跳转到--》个人商户注册成功
	 */
	public static final String REDIRECT_PERSON_REG_SUCCESS = "redirect:/signup/person_signup_success.mbay";
	/**
	 * 跳转到--》企业商户注册说明页面
	 */
	public static final String COMPANYREGISTER = "signup/enterprise_read_tips";
	/**
	 * 填写企业商户注册信息页面
	 */
	public static final String ENTERPRISREGISTER = "signup/enterprise_signup";
	/**
	 * 企业商户注册成功提示信息页面
	 */
	public static final String ENTERPRISE_REGISTER_SUCCESS = "signup/enterprise_signup_success";
	/**
	 * 跳转到--》企业商户注册成功
	 */
	public static final String REDIRECT_ENTERPRISE_REG_SUCCESS = "redirect:/signup/enterprise_signup_success.mbay";
	
	@Autowired
	UserService userService;
	
	/**
	 * 跳转至个人注册说明页面
	 * 
	 * @return
	 */
	@RequestMapping("signup/index")
	public String regindex() {
		return REGISTER;
	}
	
	/**
	 * 跳转至企业注册说明页面
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String regCompany() {
		return COMPANYREGISTER;
	}
	
	/**
	 * 个人注册信息填写页面
	 * 
	 * @return person_signup.jsp String
	 * @author tom
	 * @time 2014-7-18下午3:51:26
	 */
	@RequestMapping("/to_person_signup")
	@CacheControl(policy = CachePolicy.NO_CACHE)
	@Token(save = true)
	public String personReg(HttpServletRequest request) {
		return PERSONREGISTER;
	}
	
	/**
	 * 企业注册页面填写页面
	 * 
	 * @return enterprise_signup.jsp String
	 * @author tom
	 * @time 2014-7-18下午3:51:26
	 */
	@RequestMapping("/enterprise")
	@CacheControl(policy = CachePolicy.NO_CACHE)
	@Token(save = true)
	public String enterpriseReg(HttpServletRequest request) {
		return ENTERPRISREGISTER;
	}
	
	/**
	 * 个人提交注册账户信息
	 * 
	 * @return
	 */
	@RequestMapping("/person_signup")
	@Token(reset = true)
	public String regest(Model model, @Validated User user, BindingResult br,
			HttpServletRequest request, HttpServletResponse response) {
		if (br.hasErrors()) {
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, Error.FORM_VALIDATE_ERROR);
			TokenProcessor.getInstance().saveToken(request);
			return personReg(request);// 返回至申请页面
		}
		String authcode = request.getParameter("authcode");
		if (!authcode.equals(request.getSession().getAttribute("authcode"))) {
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, "验证码不正确");
			TokenProcessor.getInstance().saveToken(request);
			return personReg(request);// 返回至申请页面
		}
		
		// 移除验证码
		AuthImg.removeAuthcode(request.getSession());
		user.setProperty(ChannelProperty.PERSONAL);
		user = userService.registerUser(user);
		// 设置cookie用于后续实名认证更具用户名查询用户信息
		RequestUtil.setCookie(response,
				CertificateAuthAction.COOKIE_CERT_USERNUMBER, user.getUsernumber(),
				60 * 30);
		// 重定向传递用户登录名
		// redattr.addFlashAttribute(CertificateAuthAction.COOKIE_CERT_USERNAME,user.getUsername());
		return REDIRECT_PERSON_REG_SUCCESS;
	}
	
	/**
	 * 跳转至--》个人注册账户成功页面
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/person_signup_success")
	public String toPers_Reg_SUCCESS(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Object username = request
				.getAttribute(CertificateAuthAction.COOKIE_CERT_USERNUMBER);
		if (username != null) {
			// /set username cookie deafult 20 minute
			RequestUtil.setCookie(response,
					CertificateAuthAction.COOKIE_CERT_USERNUMBER,
					username.toString(), 60 * 20);
		} else {// else 通过request未获取到username,可能为刷新
			// 判断cookie是否已保存用户名
			Cookie cookie = RequestUtil.getCookie(request,
					CertificateAuthAction.COOKIE_CERT_USERNUMBER);// 表明为刷新页面
			if (cookie == null) {
				// 未保存cookie，表名未注册或cookie过期，跳转到注册页
				return regCompany();
			}
		}
		return PERSON_REGISTER_SUCCESS;
	}
	
	/**
	 * 企业提交注册账户信息
	 * 
	 * @return
	 */
	@RequestMapping("/enterprise_signup")
	@Token(reset = true)
	public String comregest(Model model, @Validated User user,
			BindingResult br, RedirectAttributes redattr,
			HttpServletRequest request, HttpServletResponse response) {
		String authcode = request.getParameter("authcode");
		if (!authcode.equals(request.getSession().getAttribute("authcode"))) {
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, "验证码不正确");
			TokenProcessor.getInstance().saveToken(request);
			return enterpriseReg(request);// 返回至申请页面
		}
		if (br.hasErrors()) {
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, Error.FORM_VALIDATE_ERROR);
			TokenProcessor.getInstance().saveToken(request);
			return enterpriseReg(request);// 返回至申请页面
		}
		if (!this.userService.authExistUserByUserNumber(user.getSupnumber())) {
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, "邀请码不存在！");
			TokenProcessor.getInstance().saveToken(request);
			return enterpriseReg(request);// 返回至申请页面
		}
		// 移除验证码
		AuthImg.removeAuthcode(request.getSession());
		user.setProperty(ChannelProperty.ENTERPRISE);
		user = userService.registerUser(user);
		// 在cookie中保存用户名，以便继续完善用户实名认证。
		RequestUtil.setCookie(response,
				CertificateAuthAction.COOKIE_CERT_USERNUMBER, user.getUsernumber(),
				60 * 30);// 保存30分钟
		return REDIRECT_ENTERPRISE_REG_SUCCESS;
	}
	
	/**
	 * 跳转至--》注册成功
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/enterprise_signup_success")
	public String toErp_Reg_SUCCESS(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		// 判断cookie是否已保存用户名
		Cookie cookie = RequestUtil.getCookie(request,
				CertificateAuthAction.COOKIE_CERT_USERNUMBER);
		if (cookie == null) {
			// 未保存cookie，表名未注册或cookie过期，跳转到注册页
			return regCompany();
		}
		return ENTERPRISE_REGISTER_SUCCESS;
	}
	
	/**
	 * 注册时查询用户是否存在
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/authRegUserName")
	public String authRegUserName(HttpServletRequest request) {
		String username = request.getParameter("param");
		if (username == null || username.length() == 0) {
			return "用户名不能为空!";
		}
		boolean result = userService.isExistUserName(username);
		if (!result) {
			// 通过validform验证,如果通过验证,需返回小写字母y
			return "y";
		}
		return "用户名已存在！";
	}
	
	/**
	 * 忘记密码时查询用户是否存在
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/forgetPwdFindUserName")
	public String forgetPwdFindUserName(HttpServletRequest request) {
		String username = request.getParameter("param");
		if (username == null || username.length() == 0) {
			return "用户名不能为空!";
		}
		boolean result = userService.isExistUserName(username);
		if (result) {
			// 通过validform验证,如果通过验证,需返回小写字母y
			return "y";
		}
		return "用户名不存在!";
	}
	
	/**
	 * 注册时查询用户邀请码是否存在 邀请码即推荐用户的usernumber
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/authInvitationCode")
	public String authInvitationCode(HttpServletRequest request) {
		String usernumber = request.getParameter("param");
		if (usernumber == null || usernumber.length() == 0) {
			return "邀请码不能为空!";
		}
		ExecuteResult result = userService.authInvitationCode(usernumber);
		if (result.isSuccess()) {
			// 通过validform验证,如果通过验证,需返回小写字母y
			return "y";
		}
		return result.getMessage();
	}
	
}
