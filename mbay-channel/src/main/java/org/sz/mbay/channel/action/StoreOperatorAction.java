package org.sz.mbay.channel.action;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.base.config.ResourceConfig;
import org.sz.mbay.base.constant.Globals;
import org.sz.mbay.base.context.WebContext;
import org.sz.mbay.base.exception.ServiceException;
import org.sz.mbay.base.exception.error.ErrorInfo;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.regex.pattern.RegExp;
import org.sz.mbay.base.web.utils.RequestUtil;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.bean.CampaignRedeemCode;
import org.sz.mbay.channel.bean.Store;
import org.sz.mbay.channel.bean.StoreCampaign;
import org.sz.mbay.channel.bean.StoreOperator;
import org.sz.mbay.channel.service.CampaignRedeemCodeService;
import org.sz.mbay.channel.service.StoreActivityService;
import org.sz.mbay.channel.service.StoreOperatorService;
import org.sz.mbay.channel.service.StoreService;
import org.sz.mbay.promotion.enums.RedeemCodeStu;

@Controller
@RequestMapping(value = "/store_ope/")
public class StoreOperatorAction {
	
	public static final String OPERATOR_LOGIN_COOKIE = "operator_login_cookie";
	/** cookie保存一个月 **/
	public static final int OPERATOR_LOGIN_COOKIE_TIME = 365 * 24 * 60 * 60;
	
	@Autowired
	StoreOperatorService storeOperatorService;
	
	@Autowired
	StoreActivityService storeActivityService;
	
	@Autowired
	StoreService storeService;
	
	@Autowired
	private CampaignRedeemCodeService campaignRedeemCodeService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/init", method = RequestMethod.POST)
	@ResponseBody
	public ExecuteResult init(String cellphone, String password, String checkPassword, String authCode, HttpServletResponse response) {
		ExecuteResult result = new ExecuteResult(true, "初始化成功！");
		if (cellphone == null) {
			result.setError_code("PHONE_NULL");
			result.setSuccess(false);
			result.setMessage("手机不能为空!");
			return result;
		}
		
		Pattern p = RegExp.mobile;
		Matcher m = p.matcher(cellphone);
		if (m.matches() == false) {
			result.setError_code("PHONE_NUMBER_NOT_SUPPORT");
			result.setSuccess(false);
			result.setMessage("手机格式不正确!");
			return result;
		}
		
		if (!password.equals(checkPassword)) {
			result.setError_code("PWD_ERROR");
			result.setSuccess(false);
			result.setMessage("前后密码不一致!");
			return result;
		}
		
		Store store = this.storeService.findStoreByAuthCode(authCode);
		if (store == null) {
			result.setError_code("STORE_NOT_EXIST");
			result.setSuccess(false);
			result.setMessage("门店授权码不存在！");
			return result;
		}
		
		StoreOperator operator = this.storeOperatorService.findOperator(authCode, cellphone);
		if (operator != null) {
			result.setError_code("HAS INITED");
			result.setSuccess(false);
			result.setMessage("该号码已经绑定到此门店，请直接登录!");
			return result;
		}
		operator = this.storeOperatorService.init(cellphone, password, authCode);
		if (operator != null) {
			RequestUtil.setCookie(response, OPERATOR_LOGIN_COOKIE, cellphone + "-" + authCode, OPERATOR_LOGIN_COOKIE_TIME);
		}
		return result;
	}
	
	@RequestMapping(value = "/initUI", method = RequestMethod.GET)
	public String initUI() {
		return "/o2o_campaign/operator/initUI";
	}
	
	/**
	 * 根据兑换页面
	 * 
	 * @param code
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/redeemUI", method = RequestMethod.GET)
	public String redeemUI(@RequestParam(required = false) String code, Model model) {
		if (code != null) {
			
			CampaignRedeemCode redeemCode = this.campaignRedeemCodeService.findRedeemCode(code);
			StoreCampaign campaign = this.storeActivityService.findCampaignById(redeemCode.getCampaignId());
			if (campaign == null) {
				throw new ServiceException(new ErrorInfo("CODE NOT EXIST", "兑换码不存在!"));
			}
			model.addAttribute("code", code);
			model.addAttribute("campaign", campaign);
		}
		HttpServletRequest request = WebContext.getServletRequest();
		Cookie cookie = RequestUtil.getCookie(request, StoreOperatorAction.OPERATOR_LOGIN_COOKIE);
		if (cookie == null) {
			// 初始化操作员
			return "/o2o_campaign/operator/loginUI";
		}
		
		String value = cookie.getValue();
		String[] vs = value.split("-");
		if (vs.length != 2) {
			return "/o2o_campaign/operator/loginUI";
		}
		
		String cellPhone = vs[0];
		String authCode = vs[1];
		// 判断Cookie信息是否正确
		StoreOperator operator = this.storeOperatorService.findOperator(authCode, cellPhone);
		if (operator == null) {
			return "/o2o_campaign/operator/loginUI";
		}
		// 下面说明Cookie信息正确，且免登录
		model.addAttribute("cellPhone", cellPhone);
		model.addAttribute("authCode", authCode);
		// 判断兑换门店是否禁用
		Store redeemStore = this.storeService.findStoreByAuthCode(authCode);
		if (!redeemStore.isEnable()) {
			return "/o2o_campaign/operator/loginUI";
		}
		try {
			// 转到兑换页面，并将兑换码带过去
			String url = ResourceConfig.getWebDomain() + request.getContextPath() + "/store_ope/redeemUI.mbay";
			if (code != null) {
				url = url + "?code=" + code;
			}
		} catch (Exception e) {
			logger.error("", e.fillInStackTrace());
		}
		model.addAttribute("storeId", redeemStore.getId());
		return "/o2o_campaign/store/redeemUI";
	}
	
	@RequestMapping(value = "loginUI", method = RequestMethod.GET)
	public String loginUI(Model model) {
		HttpServletRequest request = WebContext.getServletRequest();
		Cookie cookie = RequestUtil.getCookie(request, OPERATOR_LOGIN_COOKIE);
		if (cookie != null) {
			String content = cookie.getValue();
			String[] cs = content.split("-");
			String cellphone = cs[0];
			String authCode = cs[1];
			model.addAttribute("cellphone", cellphone);
			model.addAttribute("authCode", authCode);
		}
		return "/o2o_campaign/operator/loginUI";
	}
	
	@RequestMapping(value = "record", method = RequestMethod.GET)
	public String queryRedeemRecord(PageInfo pageInfo, String storeId, Model model) {
		List<CampaignRedeemCode> records = this.campaignRedeemCodeService.findRedeemRecord(Long.valueOf(storeId), pageInfo);
		if (records != null) {
			model.addAttribute("records", records);
			model.addAttribute(Globals.PAGEINFO_KEY, pageInfo);
		}
		return "/o2o_campaign/operator/record";
	}
	
	@RequestMapping(value = "/login")
	@ResponseBody
	public ExecuteResult login(@RequestParam String cellphone, @RequestParam String password, @RequestParam String authCode, HttpServletResponse response) {
		
		ExecuteResult result = new ExecuteResult(true, "登录成功!");
		Store redeemStore = this.storeService.findStoreByAuthCode(authCode);
		if (redeemStore == null) {
			result.setError_code("NOT FOUND STORE");
			result.setMessage("授权码错误，门店不存在!");
			result.setSuccess(false);
			return result;
		}
		// 判断兑换门店是否禁用
		if (!redeemStore.isEnable()) {
			result.setError_code("STORE HAS DISABLED");
			result.setMessage("门店已经被禁用,无法登陆进系统!");
			result.setSuccess(false);
			return result;
		}
		StoreOperator operator = this.storeOperatorService.findOperator(authCode, cellphone);
		if (operator == null) {
			result.setSuccess(false);
			result.setError_code("USER_NOT_EXIST");
			result.setMessage("用户不存在!");
			return result;
		}
		if (password.trim().equals("") || password == null) {
			result.setSuccess(false);
			result.setError_code("PWD_ERROR");
			result.setMessage("密码错误!");
			return result;
		}
		if (!operator.getPassword().equals(password)) {
			result.setSuccess(false);
			result.setError_code("PWD_ERROR");
			result.setMessage("密码错误!");
			return result;
		}
		// 清楚之前保存的Cookie
		RequestUtil.deleteCookie(response, OPERATOR_LOGIN_COOKIE);
		// 重新设置Cookie
		RequestUtil.setCookie(response, OPERATOR_LOGIN_COOKIE, operator.getCellphone() + "-" + operator.getAuthCode(), OPERATOR_LOGIN_COOKIE_TIME);
		result.setError_code("SUCCESS");
		return result;
	}
	
	/**
	 * 根据Id删除操作员
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public ExecuteResult delete(long opeId, long storeId) {
		ExecuteResult result = new ExecuteResult(true, "");
		boolean success = this.storeOperatorService.delete(opeId, storeId);
		String message = "";
		if (!success) {
			message = "delete operator failed";
			result.setSuccess(false);
			result.setError_code("FAILED");
			return result;
		}
		message = "delete operator success";
		result.setError_code("SUCCESS");
		result.setMessage(message);
		return result;
	}
	
	@RequestMapping(value = "/redeem", method = RequestMethod.POST)
	@ResponseBody
	public ExecuteResult redeem(String code) {
		ExecuteResult result = new ExecuteResult(true, "SUCCESS", "");
		HttpServletRequest request = WebContext.getServletRequest();
		Cookie cookie = RequestUtil.getCookie(request, StoreOperatorAction.OPERATOR_LOGIN_COOKIE);
		if (cookie == null) {
			result.setSuccess(false);
			result.setMessage("请先登录!");
			result.setError_code("NOT LOGIN");
			return result;
		}
		CampaignRedeemCode redeemCode = this.campaignRedeemCodeService.findRedeemCode(code);
		if (redeemCode == null) {
			result.setSuccess(false);
			result.setMessage("兑换码不存在！");
			result.setError_code("CODE_NOT_EXIST");
			return result;
		}
		if (redeemCode.getStatus().equals(RedeemCodeStu.REDEEMED)) {
			result.setSuccess(false);
			result.setMessage("兑换码已使用，无效!");
			result.setError_code("CODE_INACTIVE");
			return result;
		}
		String[] cs = cookie.getValue().split("-");
		if (cs.length < 2) {
			result.setSuccess(false);
			result.setMessage("系统繁忙!");
			result.setError_code("COOKIE ERROR");
			this.logger.error("redeem:=", "获取到的cookie有问题");
			return result;
		}
		String cellphone = cs[0];
		String authCode = cs[1];
		StoreOperator operator = this.storeOperatorService.findOperator(authCode, cellphone);
		result = this.campaignRedeemCodeService.redeem(redeemCode, operator);
		return result;
	}
}
