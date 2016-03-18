package org.sz.mbay.channel.action;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.sz.mbay.base.exception.ServiceException;
import org.sz.mbay.captcha.CaptchaResult;
import org.sz.mbay.captcha.SMSAuthCodeUtil;
import org.sz.mbay.channel.bean.EmailTemplate;
import org.sz.mbay.channel.bean.Emailretrievepwdrecord;
import org.sz.mbay.channel.context.EmailTemplateSupport;
import org.sz.mbay.channel.costant.error.PasssWordManageError;
import org.sz.mbay.channel.costant.error.PwdManageError;
import org.sz.mbay.channel.framework.action.BaseAction;
import org.sz.mbay.channel.service.EmailretRievepwdRecordService;
import org.sz.mbay.channel.user.bean.User;
import org.sz.mbay.channel.user.enums.CertStatus;
import org.sz.mbay.channel.user.enums.ChannelProperty;
import org.sz.mbay.channel.user.service.CertificateAuthService;
import org.sz.mbay.channel.user.service.UserAccountService;
import org.sz.mbay.channel.user.service.UserService;
import org.sz.mbay.channel.util.EmailHiddenUtil;
import org.sz.mbay.channel.util.PhoneHiddenUtil;
import org.sz.mbay.common.util.MbayDateFormat;
import org.sz.mbay.common.util.MbayDateFormat.DatePattern;
import org.sz.mbay.email.MbayMail;
import org.sz.mbay.sms.client.MbaySMS;
import org.sz.mbay.sms.template.enums.CaptchaSMSType;

/**
 * @author Tom
 * @version 创建时间：2014-8-7下午3:38:31
 * @type 类型描述 public class PasssWordManageAction{ }
 */
@Controller
@RequestMapping("resetpwd")
public class PassWordManageAction extends BaseAction {
	
	@Autowired
	UserService userService;
	@Autowired
	CertificateAuthService certAuthService;
	@Autowired
	UserAccountService userAccountService;
	@Autowired
	EmailretRievepwdRecordService emailretrievepwdrecordService;
	
	// 找回密码
	public static final String FIND_PASSWORD = "findpwd/index";
	
	public static final String REDIRECT_FIND_PASSWORD = "redirect:/resetpwd/findpwd/tofind.mbay";
	
	// 找回密码三种方式：1-电信，2-邮箱，3-人工
	public static final String FIND_PASSWORD_METHOD = "findpwd/method";
	
	// 短信找回密码方式
	public static final String PHONE_FIND_PAGE = "findpwd/phonefind";
	
	// 邮箱找回密码方式
	public static final String EMAIL_FIND_PAGE = "findpwd/emailfind";
	
	// 人工找回密码方式
	public static final String ARTIFICIA_FIND_PAGE = "findpwd/artificiafind";
	
	// 尚未实名认证页面
	public static final String REMINDER_PAGE = "findpwd/reminder";
	
	public static final String TO_REMINDER_PAGE = "findpwd/unaudited_voucher";
	
	// 重置密码
	public static final String RESETPWD_PAGE = "findpwd/resetpwd";
	
	// 密码重置成功
	public static final String RESET_SUCCESS_PAGE = "findpwd/resetsuc";
	
	// 面膜重置失败
	public static final String RESET_FAILED_PAGE = "findpwd/failed";
	
	// 邮箱重置密码链接失效
	public static final String FINDPWD_RESETLINK_EXPIRE = "findpwd/resetlink_expire";
	
	public static final String COOKIENAME = "username";
	public static final String FINDPWD_USERNUMBER = "findPsdUserNumber";
	public static final String CERT_AUTHCODE = "certauthcode";
	/** 找回密码加密账户编号 ***/
	public static final String ENCRYPT_FINDPWD_USERNUMBER = "encrypt_findpwd_usernumber";
	
	/**
	 * 进入找回密码页面
	 * 
	 * @return String
	 * @author tom
	 * @time 2014-7-28上午11:58:35
	 */
	@RequestMapping("findpwd/tofind")
	public String tofindPassword() {
		return FIND_PASSWORD;
	}
	
	/**
	 * 选择找回密码方式
	 * 
	 * @param username
	 * @return String
	 * @author tom
	 * @time 2014-7-28下午1:59:07
	 */
	@RequestMapping("findpwd/selectStrategy")
	public String findPassword(@RequestParam("username") String username,
			Model model, HttpServletRequest request) {
		User user = userService.findUserAuthAndPropertyByName(username);
		if (user == null) {
			throw new ServiceException(
					PasssWordManageError.ACCOUNT_NAME_NOT_EXIST);
		}
		CertStatus certStatus = user.getCertStatus();
		if (CertStatus.APPROVED.equals(certStatus)) {
			String email = "";
			String pthoneNumber = "";
			ChannelProperty property = user.getProperty();
			if (ChannelProperty.PERSONAL.equals(property)) {
				// 个人信息
				email = this.certAuthService.findEmailOfPersonal(user
						.getUsernumber());
				pthoneNumber = this.certAuthService.findPhoneOfPersonal(user
						.getUsernumber());
			} else if (ChannelProperty.ENTERPRISE.equals(property)) {
				// 企业
				email = this.certAuthService.findEmailOfChannel(user
						.getUsernumber());
				pthoneNumber = this.certAuthService.findPhoneOfChannel(user
						.getUsernumber());
			}
			model.addAttribute("email", EmailHiddenUtil.hidden(email));
			model.addAttribute("phonenum",
					PhoneHiddenUtil.hidden(pthoneNumber));
			request.getSession().setAttribute(FINDPWD_USERNUMBER,
					user.getUsernumber());
		} else {
			// 提示未实名认证，通过人工找回
			return TO_REMINDER_PAGE;
		}
		return FIND_PASSWORD_METHOD;
	}
	
	@RequestMapping("findpwd/unaudited_voucher")
	public String unauditedvoucher() {
		return REMINDER_PAGE;
	}
	
	/**
	 * 进入发送手机验证码页面
	 * 
	 * @return String
	 * @author tom
	 * @throws Exception
	 * @time 2014-7-29下午6:26:37
	 */
	@RequestMapping("findpwd/sms_strategy")
	public String smsRetrieve(Model model, HttpServletRequest request)
			throws Exception {
		Object objUserNumber = request.getSession().getAttribute(
				FINDPWD_USERNUMBER);
		if (objUserNumber == null) {
			// / "您的信息提交未成功，请尝试关闭浏览器，重新打开信息提交页面，填写并及时提交您的身份信息";
			return REDIRECT_FIND_PASSWORD;
		}
		String userNumber = String.valueOf(objUserNumber);
		ChannelProperty property = this.userService
				.findUserProperty(userNumber);
		String phoneNumber = "";// 商户手机号
		if (ChannelProperty.PERSONAL.equals(property)) {// 个人
			phoneNumber = this.certAuthService.findPhoneOfPersonal(userNumber);
		} else if (ChannelProperty.ENTERPRISE.equals(property)) {
			phoneNumber = this.certAuthService.findPhoneOfChannel(userNumber);
		}
		model.addAttribute("phoneNumber", PhoneHiddenUtil.hidden(phoneNumber));
		return PHONE_FIND_PAGE;
	}
	
	/**
	 * 发送短信验证码
	 * 
	 * @param request
	 * @author tom
	 * @time 2014-7-28下午11:19:57
	 */
	@RequestMapping("findpwd/sms_strategy/send_authcode")
	public void sendAuthCodeMsg(HttpServletRequest request)
			throws ServiceException, Exception {
		String objUserNumber = (String) request.getSession().getAttribute(
				FINDPWD_USERNUMBER);
		if (StringUtils.isEmpty(objUserNumber)) {
			return;
		}
		String userNumber = String.valueOf(objUserNumber);
		ChannelProperty property = this.userService
				.findUserProperty(userNumber);
		String phoneNumber = "";// 商户手机号
		if (ChannelProperty.PERSONAL.equals(property)) {// 个人
			phoneNumber = this.certAuthService.findPhoneOfPersonal(userNumber);
		} else if (ChannelProperty.ENTERPRISE.equals(property)) {
			phoneNumber = this.certAuthService.findPhoneOfChannel(userNumber);
		}
		String code = SMSAuthCodeUtil
				.generateAuthCode(CaptchaSMSType.RESET_PASSWORD, phoneNumber);
		MbaySMS.sendCaptchaSMS(CaptchaSMSType.RESET_PASSWORD, phoneNumber,
				code);
	}
	
	/**
	 * 检测验证码
	 * 
	 * @param request
	 * @author hrain.han
	 * @time 2014-7-28下午11:19:57
	 */
	@ResponseBody
	@RequestMapping("findpwd/validate_authcode")
	public CaptchaResult ValidateAuthCodeMsg(HttpServletRequest request) {
		String requestCode = request.getParameter("AUTHCODE");
		CaptchaResult executeResult = SMSAuthCodeUtil
				.isAuthCodeValid(CaptchaSMSType.RESET_PASSWORD, requestCode);
		return executeResult;
	}
	
	/**
	 * 重置密码页面
	 * 
	 * @return String
	 * @author tom
	 * @time 2014-7-29下午6:26:37
	 */
	@RequestMapping("findpwd/sms_strategy/toresetpwd")
	public String smsResetpwd(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userNumber = (String) session.getAttribute(FINDPWD_USERNUMBER);
		if (StringUtils.isEmpty(userNumber)) {
			// 非法请求
			return REDIRECT_FIND_PASSWORD;
		}
		String requestCode = request.getParameter("AUTHCODE");
		CaptchaResult executeResult = SMSAuthCodeUtil
				.isAuthCodeValid(CaptchaSMSType.RESET_PASSWORD, requestCode);
		if (!executeResult.isSuccess()) {
			throw new ServiceException(PwdManageError.NONE_MANIPULATE);
		}
		SMSAuthCodeUtil.removeAuthCode(CaptchaSMSType.RESET_PASSWORD);
		session.setAttribute(ENCRYPT_FINDPWD_USERNUMBER,
				encrptPWDFindUserNumber(userNumber));
		return RESETPWD_PAGE;
	}
	
	/**
	 * 加密找回密码用户编号 以便验证设置的密码为找回的账户。
	 * 
	 * @param userNumber
	 * @return
	 */
	private String encrptPWDFindUserNumber(String userNumber) {
		return DigestUtils.sha1Hex(DigestUtils.md5Hex(userNumber));
	}
	
	/**
	 * 邮箱找回页面
	 * 
	 * @return String
	 * @author tom
	 * @throws Exception
	 * @time 2014-7-29下午6:26:37
	 */
	@RequestMapping("findpwd/email_strategy")
	public String findbyemail(Model model, HttpServletRequest request)
			throws Exception {
		String userNumber = (String) request.getSession().getAttribute(
				FINDPWD_USERNUMBER);
		if (StringUtils.isEmpty(userNumber)) {
			// / "您的信息提交未成功，请尝试关闭浏览器，重新打开信息提交页面，填写并及时提交您的身份信息";
			return REDIRECT_FIND_PASSWORD;
		}
		ChannelProperty property = this.userService
				.findUserProperty(userNumber);
		String email = null;
		if (ChannelProperty.PERSONAL.equals(property)) {// 个人
			email = this.certAuthService.findEmailOfPersonal(userNumber);
		} else if (ChannelProperty.ENTERPRISE.equals(property)) {
			email = this.certAuthService.findEmailOfChannel(userNumber);
		}
		
		String showemail = EmailHiddenUtil.hidden(email);
		String u = email.substring(email.indexOf('@') + 1, email.length());
		String emailurl = "mail." + u;
		model.addAttribute("open", emailurl);
		model.addAttribute("showemail", showemail);
		sendEmail(userNumber, email);
		return EMAIL_FIND_PAGE;
	}
	
	/**
	 * ajax重发邮件
	 * 
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("findpwd/resend_email")
	public String resendEmail(HttpSession session, HttpServletRequest request)
			throws Exception {
		String userNumber = (String) request.getSession().getAttribute(
				FINDPWD_USERNUMBER);
		if (StringUtils.isEmpty(userNumber)) {
			// / "您的信息提交未成功，请尝试关闭浏览器，重新打开信息提交页面，填写并及时提交您的身份信息";
			return "fail";
		}
		ChannelProperty property = this.userService
				.findUserProperty(userNumber);
		String email = null;
		if (ChannelProperty.PERSONAL.equals(property)) {// 个人
			email = this.certAuthService.findEmailOfPersonal(userNumber);
		} else if (ChannelProperty.ENTERPRISE.equals(property)) {
			email = this.certAuthService.findEmailOfChannel(userNumber);
		}
		sendEmail(userNumber, email);
		return "success";
		
	}
	
	/**
	 * 发邮件
	 * 
	 * @param user
	 * @param email
	 * @param userid
	 * @throws Exception
	 */
	private void sendEmail(String userNumber, String email) throws Exception {
		DateTime now = DateTime.now();
		// 发送邮件处理方式
		// 链接参数
		String uuid = UUID.randomUUID().toString();
		String time = MbayDateFormat.formatDateTime(DateTime.now(),
				DatePattern.isoTime);
		String digeststr = userNumber + uuid + time;
		String digest = org.sz.mbay.common.util.DigestUtils
				.md5Encrypt(digeststr);
		Emailretrievepwdrecord emailretrievepwdrecord = new Emailretrievepwdrecord();
		emailretrievepwdrecord.setUserNumber(userNumber);
		emailretrievepwdrecord.setDigest(digest);
		emailretrievepwdrecord.setCreatetime(now);
		
		// 数据库插入记录 ,先查找id是否存在 ,如果存在删除记录
		this.emailretrievepwdrecordService
				.deleteEmailRetrievepwdRecord(userNumber);
		/* .deleteEmailRetrievepwdRecord(userNumber); */
		this.emailretrievepwdrecordService
				.insertEmailretrievepwdrecord(emailretrievepwdrecord);
				
		EmailTemplate emailTemp = EmailTemplateSupport.getResetPasswordEmail(
				userNumber, digest);
		MbayMail.sendMail(email, emailTemp.getName(), emailTemp.getContent());
	}
	
	/**
	 * // 重定向找回密码页面
	 * 
	 * @param userNumber
	 *            商户编号。前期通过用户名字传递，故此处参数变量名为username
	 * @param digest
	 *            加密验证串
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("findpwd/emailtoresetpwd")
	public String findpwd(@RequestParam("username") String userNumber,
			@RequestParam("digest") String digest, Model model,
			HttpServletRequest request) {
		Emailretrievepwdrecord record = this.emailretrievepwdrecordService
				.findEmailRetrievepwdRecord(userNumber);
		if (record == null) {// record 为 null 表示已通过邮箱连接修改密码，修改成功后删除记录
			return FINDPWD_RESETLINK_EXPIRE;
		}
		DateTime nowTime = DateTime.now().minusDays(1);
		// record.getCreatetime()
		// 判断时间大于当前时间并且不超过24小时
		if (record.getCreatetime().isAfter(nowTime)
				&& digest.equalsIgnoreCase(record.getDigest())) {
			HttpSession session = request.getSession();
			session.setAttribute(FINDPWD_USERNUMBER, userNumber);
			session.setAttribute(ENCRYPT_FINDPWD_USERNUMBER,
					encrptPWDFindUserNumber(userNumber));
			return RESETPWD_PAGE;
		}
		// 链接失效，返回人工页面
		return ARTIFICIA_FIND_PAGE;
	}
	
	/**
	 * 人工找回页面
	 * 
	 * @return String
	 * @author tom
	 * @time 2014-7-29下午6:26:37
	 */
	@RequestMapping("findpwd/artificia_retrieve")
	public String findbyartificial(Model model, HttpServletRequest request) {
		return ARTIFICIA_FIND_PAGE;
	}
	
	/**
	 * 短信找回 重置密码
	 * 
	 * @return String
	 * @author tom
	 * @throws Exception
	 * @time 2014-7-29下午6:26:37
	 */
	@RequestMapping("findpwd/resetpwd")
	public String resetpwd(@RequestParam("password") String password,
			RedirectAttributes redirectAttribute, HttpServletRequest request)
					throws Exception {
		HttpSession session = request.getSession();
		// 根据用户id修改密码
		String userNumber = (String) session.getAttribute(FINDPWD_USERNUMBER);
		if (userNumber == null) {
			// session失效，返回找回密码首页
			return REDIRECT_FIND_PASSWORD;
		}
		String requestEncrptUserNumber = (String) session
				.getAttribute(ENCRYPT_FINDPWD_USERNUMBER);
		String encrptyUserNumber = this.encrptPWDFindUserNumber(userNumber);
		if (!encrptyUserNumber.equals(requestEncrptUserNumber)) {
			throw new ServiceException(PwdManageError.NONE_SUCCESS);
		}
		if (userService.updateUserPassword(
				org.sz.mbay.common.util.DigestUtils.md5Encrypt(password),
				userNumber)) {
			session.removeAttribute(FINDPWD_USERNUMBER);
			session.removeAttribute(ENCRYPT_FINDPWD_USERNUMBER);
			
			// 密码重置成功
			return RESET_SUCCESS_PAGE;
		}
		// 密码重置失败
		return RESET_FAILED_PAGE;
	}
	
	@RequestMapping("findpwd/resetlink_expire")
	public String resetlinkExpire() {
		return FINDPWD_RESETLINK_EXPIRE;
	}
	
	@RequestMapping("findpwd/resetsuc")
	public String resetSuccess() {
		return RESET_SUCCESS_PAGE;
	}
	
}
