package org.sz.mbay.channel.action;

import java.io.UnsupportedEncodingException;
import static org.springframework.web.servlet.view.UrlBasedViewResolver.REDIRECT_URL_PREFIX;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.sz.mbay.base.annotation.CacheControl;
import org.sz.mbay.base.annotation.CachePolicy;
import org.sz.mbay.base.annotation.Token;
import org.sz.mbay.base.constant.Globals;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.web.servlet.tags.token.TokenProcessor;
import org.sz.mbay.base.web.utils.RequestUtil;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.bean.ChannelPartner;
import org.sz.mbay.channel.bean.DynamicItem;
import org.sz.mbay.channel.bean.Notice;
import org.sz.mbay.channel.context.ChannelConfig;
import org.sz.mbay.channel.context.ChannelContext;
import org.sz.mbay.channel.context.SessionUser;
import org.sz.mbay.channel.framework.action.BaseAction;
import org.sz.mbay.channel.service.ChannelDynamicService;
import org.sz.mbay.channel.service.ChannelPartnerService;
import org.sz.mbay.channel.service.NoticeService;
import org.sz.mbay.channel.service.StoreActivityService;
import org.sz.mbay.channel.user.bean.User;
import org.sz.mbay.channel.user.bean.UserContext;
import org.sz.mbay.channel.user.bean.UserRemindPoint;
import org.sz.mbay.channel.user.enums.CertStatus;
import org.sz.mbay.channel.user.service.CertificateAuthService;
import org.sz.mbay.channel.user.service.MBAccountService;
import org.sz.mbay.channel.user.service.RedTrafficAccountService;
import org.sz.mbay.channel.user.service.UserAccountService;
import org.sz.mbay.channel.user.service.UserContextService;
import org.sz.mbay.channel.user.service.UserService;
import org.sz.mbay.channel.useraccount.bean.MbayTraffic;
import org.sz.mbay.channel.useraccount.bean.RedTraffic;
import org.sz.mbay.channel.useraccount.bean.UserAccount;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.fs.FSClient;
import org.sz.mbay.fs.FSClientFactory;
import org.sz.mbay.fs.FSClientFactory.ClientType;
import org.sz.mbay.fs.fastdfs.FDFSFileInfo;
import org.sz.mbay.wechat.service.WeChatCampaignService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 渠道商用户Action 处理登录，退出等基本功能 注册，用户名验证
 * 
 * @author ONECITY
 * 		
 */
@Controller
@RequestMapping("/channel")
public class ChannelAction extends BaseAction {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ChannelAction.class);
	/**
	 * 登录名cookie常量
	 */
	public static final String USERNAMECOOKIE = "username";
	/**
	 * 工作平台
	 */
	public static final String WORKBENCH = "portal/workbench";
	/**
	 * 首页
	 */
	public static final String TOINDEX = "login/index";
	
	/**
	 * 登录页
	 */
	public static final String TOLOGIN = "login/login";
	/**
	 * 重定向到登录页面
	 */
	public static final String REDIRECT_LOGIN = REDIRECT_URL_PREFIX+"/channel/toLogin.mbay";
	/**
	 * 重定向到工作台
	 */
	public static final String REDIRECT_WORKBENCH = REDIRECT_URL_PREFIX+"/channel/workbench.mbay";
	
	/**
	 * 账户信息管理页面
	 */
	public static final String ACCOUNTMANAGER = "account/accountmanager";
	/**
	 * 变更上级代理页面
	 */
	public static final String TO_CHANGE_SUPNUMBER = "account/supnumber_change/changefill";
	/**
	 * 变更上级代理成功提示页面
	 */
	public static final String SUPNUMBER_CHANGE_SUCCESS = "account/supnumber_change/change_success";
	/**
	 * 修改密码
	 */
	public static final String TO_CHANGEPWD_PAGE = "account/changepwd";
	
	/**
	 * 更改密码成功页面
	 */
	public static final String CHANGEPWD_SUCCESS = "account/changepwdsuccess";
	/**
	 * 下级代理商页面
	 */
	public static final String SUB_AGENTS = "account/subchannel/agents_list";
	/**
	 * 修改用户提醒设置页面
	 */
	public static final String SET_REMIND = "/account/set_remind";
	
	@Autowired
	UserService userService;
	@Autowired
	UserAccountService userAccountservice;
	@Autowired
	UserContextService usercontextservice;
	@Autowired
	CertificateAuthService certificateauthservice;
	@Autowired
	ChannelDynamicService pictureService;
	@Autowired
	NoticeService noticeService;
	@Autowired
	StoreActivityService storeActivityService;
	@Autowired
	WeChatCampaignService marketingEventService;
	@Autowired
	ChannelPartnerService channelPartnerService;
	@Autowired
	RedTrafficAccountService redTrafficAccountService;
	@Autowired
	MBAccountService mbAccountService;
	
	// fastdfs文件管理
	private FSClient fsClient = FSClientFactory.getClient(ClientType.FDFS);
	
	/**
	 * 首页
	 */
	@RequestMapping("/index")
	@CacheControl(policy = CachePolicy.NO_CACHE)
	public ModelAndView index(ModelAndView view) {
		System.out.println(Charset.defaultCharset());
		SessionUser user = ChannelContext.getSessionChannel();
		if (user != null) {
			view.addObject("username", user.getUsername());
		}
		view.setViewName(TOINDEX);
		return view;
	}
	
	/**
	 * 登陆页面是否记住用户名处理
	 * 
	 * @param view
	 * @param cookie中username不为空
	 *            model 添加用户名model中
	 * @param request
	 *            获取USERNAMECOOKIE值
	 * @param response
	 * @return
	 */
	@RequestMapping("/toLogin")
	@CacheControl(policy = CachePolicy.NO_CACHE)
	public ModelAndView toLogin(ModelAndView view, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = RequestUtil.getCookie(request, USERNAMECOOKIE);
		if (cookie != null) {
			model.addAttribute(USERNAMECOOKIE, cookie.getValue());
		}
		// 返回首页时删除cookie
		Cookie certnameCoolie = RequestUtil.getCookie(request,
				CertificateAuthAction.COOKIE_CERT_USERNUMBER);
		if (certnameCoolie != null) {
			RequestUtil.deleteCookie(response,
					CertificateAuthAction.COOKIE_CERT_USERNUMBER);
		}
		view.setViewName(TOLOGIN);
		return view;
	}
	
	// ajax获取合作伙伴
	@RequestMapping("/ajax/channelPartner")
	@ResponseBody
	public JSONArray ajaxGetChannelPartner() {
		List<ChannelPartner> channelPartnerList = channelPartnerService
				.findList();
		return JSONArray.fromObject(channelPartnerList);
	}
	
	// ajax获取直通车动态
	@RequestMapping("/ajax/dynamicItem")
	@ResponseBody
	public JSONArray ajaxGetDynamicItem() {
		List<DynamicItem> dynamicItemList = pictureService.findCategory();
		return JSONArray.fromObject(dynamicItemList);
	}
	
	/***
	 * 注销
	 */
	@RequestMapping("/loginout")
	public String loginOut(HttpServletRequest request) {
		request.getSession().invalidate();
		return REDIRECT_LOGIN;
	}
	
	/**
	 * 登录
	 * 
	 * @param view
	 * @param username
	 * @param password
	 * @param authcode
	 * @param request
	 * @return 成功跳转至工作台
	 * @throws Exception
	 */
	@RequestMapping("/login")
	public String login(RedirectAttributes redirectAttribute,
			HttpServletRequest request, HttpServletResponse response) {
		// /MyBatisXMLConfigBuilder
		String authcode = request.getParameter("authcode");
		if (StringUtils.isEmpty(authcode) || !authcode
				.equals(request.getSession().getAttribute("authcode"))) {
			redirectAttribute.addFlashAttribute("message", "验证码不正确");
			return REDIRECT_LOGIN;
		}
		String username = request.getParameter("username");
		if (StringUtils.isEmpty(username)) {
			redirectAttribute.addFlashAttribute("message", "用户名或密码错误");
			return REDIRECT_LOGIN;
		}
		String password = request.getParameter("password");
		AuthImg.removeAuthcode(request.getSession());
		User user = this.userService.findChannel(username);
		if (user == null) {
			redirectAttribute.addFlashAttribute("message", "用户名或密码错误");
			return REDIRECT_LOGIN;
		}
		// 验证加密方式:md5(md5(md5(密码) + 登录账户 + 验证码)
		String passwd = user.getPassword();// md5(密码)
		String hash_first = DigestUtils
				.md5Encrypt(passwd + username + authcode);
		String hash = DigestUtils.md5Encrypt(hash_first);
		if (!hash.equals(password)) {
			redirectAttribute.addFlashAttribute("message", "用户名或密码错误");
			return REDIRECT_LOGIN;
		}
		
		if (request.getParameter("remeber") != null) {
			RequestUtil.setCookie(response, USERNAMECOOKIE, username,
					60 * 60 * 24);
		} else {
			RequestUtil.deleteCookie(response, USERNAMECOOKIE);
		}
		
		// session中保存uername
		/// usercontextservice.initUserContext(user.getUsernumber());
		
		// 检查o2o红包活动过期情况
		// this.storeActivityService.checkIsExpire(user.getUsernumber());
		
		// 设置未读公告数量
		request.getSession().setAttribute(Globals.UNREAD_NOTICE_NUM,
				getUnreadedNoticeNum(request, response, user.getUsernumber()));
				
		// usercontextservice.updateUserStrategyInUserCount(user.getId());
		user.setUsername(username);
		setSeesionUser(user);
		return REDIRECT_WORKBENCH;
	}
	
	/**
	 * 获取未读公告数量
	 * 
	 * @return
	 */
	private int getUnreadedNoticeNum(HttpServletRequest request,
			HttpServletResponse response, String usernumber) {
		List<Notice> noticelist = noticeService.findAllNotice(null);
		int readNums = 0;
		String cookieName = Globals.READED_NOTICE_COOKIE + "_" + usernumber;
		// 剔除已读公告
		for (Cookie cookie : request.getCookies()) {
			if (cookieName.equals(cookie.getName())) {
				String value = cookie.getValue();
				if (value != null && !"".equals(value)) {
					String ids[] = value.split(",");
					boolean idExist;
					List<String> unExistIds = new ArrayList<String>();
					
					for (String id : ids) {
						idExist = false;
						for (Notice notice : noticelist) {
							if (Integer.parseInt(id) == notice.getId()) {
								readNums++;
								idExist = true;
							}
						}
						// 记录cookie中存在而数据库已不存在的记录id
						if (!idExist) {
							unExistIds.add(id);
						}
					}
					// cookie中去除数据库已不存在的记录id
					List<String> newIds = new ArrayList<String>(
							Arrays.asList(ids));
					newIds.removeAll(unExistIds);
					
					// cookie设置新值
					StringBuffer newValue = new StringBuffer();
					for (String id : newIds) {
						newValue.append(id + ",");
					}
					newValue.deleteCharAt(newValue.length() - 1);
					cookie.setValue(newValue.toString());
					cookie.setPath("/");
					cookie.setMaxAge(365 * 24 * 60 * 60);
					response.addCookie(cookie);
				}
			}
		}
		
		return noticelist.size() - readNums;
	}
	
	/**
	 * 设置session
	 * 
	 * @param user
	 *            存储session key值为channel
	 */
	private void setSeesionUser(User user) {
		SessionUser suser = new SessionUser(user.getId(), user.getUsernumber(),
				user.getUsername(), user.getProperty());
		suser.setCertStatus(user.getCertStatus());
		ChannelContext.setSessionChannel(suser);
	}
	
	/**
	 * 跳转至工作平台
	 * 
	 * @param view
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/workbench", method = RequestMethod.GET)
	public ModelAndView workbench(ModelAndView view, Model model)
			throws Exception {
		UserAccount useraccount = userAccountservice.findUserAccount(
				ChannelContext.getSessionChannel().getUserNumber());
		UserContext usercontext = this.usercontextservice.findUserContext(
				ChannelContext.getSessionChannel().getUserNumber());
		User user = this.userService
				.findUser(ChannelContext.getSessionChannel().getUserNumber());
		// 跳转至工作台时刷新session中用户认证状态
		ChannelContext.getSessionChannel().setCertStatus(user.getCertStatus());
		String portrait = userService.findPortrait(
				ChannelContext.getSessionChannel().getUserNumber());
		String value = "";
		if (!StringUtils.isEmpty(portrait)) {
			FDFSFileInfo info = (FDFSFileInfo) fsClient.getFileInfo(portrait);
			value = info.getFullPath();
		}
		view.addObject("useraccount", useraccount);
		view.addObject("usercontext", usercontext);
		view.addObject("user", user);
		view.addObject("portrait", value);
		view.setViewName(WORKBENCH);
		return view;
	}
	
	/**
	 * 账户管理
	 * 
	 * @return
	 */
	@RequestMapping(value = "accountset", method = RequestMethod.GET)
	public String accountset(Model model) {
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		UserAccount useraccount = userAccountservice
				.findUserAccount(userNumber);
		User user = this.userService.findUser(userNumber);
		MbayTraffic mbaytraffic = mbAccountService
				.findMbayTraffic(userNumber);
		RedTraffic redtraffic = redTrafficAccountService
				.findRedTraffic(userNumber);
		model.addAttribute("useraccount", useraccount);
		model.addAttribute("user", user);
		model.addAttribute("mbaytraffic", mbaytraffic);
		model.addAttribute("redtraffic", redtraffic);
		return ACCOUNTMANAGER;
	}
	
	/**
	 * 修改上级编号页面
	 * 
	 * @param model
	 * @return String
	 * @author tom
	 * @time 2014-9-5下午2:53:47
	 */
	@RequestMapping(value = "toChangeSupnumber", method = RequestMethod.GET)
	public String to_change_supnumber(Model model) {
		
		return TO_CHANGE_SUPNUMBER;
	}
	
	/**
	 * 进入修改密码页面
	 * 
	 * @param model
	 * @return String
	 * @author tom
	 * @time 2014-9-5下午2:15:28
	 */
	@RequestMapping(value = "toChangepwd", method = RequestMethod.GET)
	@Token(save = true)
	public String to_Changepwd(HttpServletRequest request) {
		return TO_CHANGEPWD_PAGE;
	}
	
	/**
	 * 修改密码
	 * 
	 * @param model
	 * @return String
	 * @author tom
	 * @time 2014-9-5下午2:15:28
	 */
	@RequestMapping("changepwd")
	@Token(reset = true)
	public String changepwd(HttpServletRequest request,
			RedirectAttributes redAttr) {
		String password = request.getParameter("password");
		// 加密
		String encrypt_password = DigestUtils.md5Encrypt(password);
		boolean suc = this.userService.updateUserPassword(encrypt_password,
				ChannelContext.getSessionChannel().getUserNumber());
		if (!suc) {
			redAttr.addAttribute(PUBLIC_MSG_NOTE_KEY, "密码修改失败，请返回重试！");
			TokenProcessor.getInstance().saveToken(request);
			return REDIRECT_PUBLIC_MSG_NOTE;
		}
		return CHANGEPWD_SUCCESS;
	}
	
	/**
	 * 验证原始密码
	 * 
	 * @param request
	 *            获取输入密码
	 * @return 返回密码验证结果
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("authOriginalpwd")
	public String auth_Originalpwd(HttpServletRequest request)
			throws Exception {
		String password = request.getParameter("param");
		// 加密
		String encrypt_password = DigestUtils.md5Encrypt(password);
		ExecuteResult result = authOriginalpwd(encrypt_password);
		Map<String, String> ret = new HashMap<>();
		ret.put("info", result.isSuccess() ? "通过信息验证！" : result.getMessage());
		ret.put("status", result.isSuccess() ? "y" : "n");
		JSONObject obj = JSONObject.fromObject(ret);
		return obj.toString();
	}
	
	private ExecuteResult authOriginalpwd(String password) throws Exception {
		boolean flag = this.userService.authOriginalpwd(
				ChannelContext.getSessionChannel().getUserNumber(), password);
		if (flag) {
			return new ExecuteResult(false, "原始密码不正确！");
		}
		return new ExecuteResult(true, "");
		
	}
	
	/**
	 * 验证新密码原始密码不能和新密码相同
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("authNewpwd")
	public String auth_Newpwd(HttpServletRequest request) throws Exception {
		String password = request.getParameter("param");
		ExecuteResult result = authNewpwd(password);
		
		Map<String, String> ret = new HashMap<>();
		ret.put("info", result.isSuccess() ? "通过信息验证！" : result.getMessage());
		ret.put("status", result.isSuccess() ? "y" : "n");
		JSONObject obj = JSONObject.fromObject(ret);
		return obj.toString();
		
	}
	
	private ExecuteResult authNewpwd(String newpassword) throws Exception {
		boolean flag = this.userService.authUserPassword(
				ChannelContext.getSessionChannel().getUserNumber(),
				newpassword);
		if (flag) {
			return new ExecuteResult(true, "");
		}
		return new ExecuteResult(false, "新登录密码必须和当前登录密码不同！");
	}
	
	/**
	 * 变更上级代理编号
	 * 
	 * @param supnumber
	 * @param model
	 * @return
	 */
	@RequestMapping("changeSupnumber")
	public String changeSupnumber(@RequestParam("supnumber") String supnumber,
			Model model) {
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		ExecuteResult result = this.userService.changeSupNumber(userNumber,
				supnumber);
		if (result.isSuccess() != true) {
			model.addAttribute("message", result.getMessage());
			// TODO:变更上级地理编号失败
			// / return PUBLIC_MSG_NOTE;
		}
		return SUPNUMBER_CHANGE_SUCCESS;
	}
	
	/**
	 * 查询所填父账户转账状态
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("getSupUserStatus")
	public String getSupUserStatus(HttpServletRequest request)
			throws Exception {
		String usernumber = request.getParameter("param");
		ExecuteResult result = authSupUser(usernumber);
		Map<String, String> ret = new HashMap<>();
		ret.put("info", result.isSuccess() ? "通过信息验证！" : result.getMessage());
		ret.put("status", result.isSuccess() ? "y" : "n");
		JSONObject obj = JSONObject.fromObject(ret);
		return obj.toString();
	}
	
	/**
	 * @param usernumber
	 *            验证更改的父级账号
	 * @return
	 * @throws Exception
	 */
	
	private ExecuteResult authSupUser(String usernumber) {
		User userself = this.userService.findSubNumberAndAuthState(
				ChannelContext.getSessionChannel().getUserNumber());
		User user = this.userService.findSubNumberAndAuthState(usernumber);
		if (user == null
				|| ChannelConfig.getMarketUsernumber().equals(usernumber)) {
			return new ExecuteResult(false, "账户不存在!");
		}
		if (ChannelContext.getSessionChannel().getUserNumber()
				.equals(usernumber)) {
			return new ExecuteResult(false, "请输入其他账号！");
		}
		if (userself.getSupnumber().equals(usernumber)) {
			return new ExecuteResult(false, "上级编号不能重复绑定!");
		}
		if (user.getSupnumber()
				.equals(ChannelContext.getSessionChannel().getUserNumber())) {
			return new ExecuteResult(false, "不可绑定下级编号!");
		}
		if (!user.getCertStatus().equals(CertStatus.APPROVED)) {
			return new ExecuteResult(false, "账号必须通过认证才能绑定!");
		}
		return new ExecuteResult(true, user.getUsernumber() + "");
	}
	
	/**
	 * 代理商列表
	 * 
	 * @return 代理商列表页面
	 */
	@RequestMapping("sub_agents")
	public String agentslist(Model model, PageInfo pageinfo,
			HttpServletRequest request) {
		// 查询当前用户的所有下级代理商信息
		List<User> subagentslist = userService.findSubAgents(
				ChannelContext.getSessionChannel().getUserNumber());
		model.addAttribute("subagentslist", subagentslist);
		model.addAttribute(Globals.PAGEINFO_KEY, pageinfo);
		return SUB_AGENTS;
	}
	
	/** 获取通讯录 */
	@ResponseBody
	@RequestMapping("findagents")
	public String findagents() {
		// 获取当前登录用户的所有下级显示通讯录中
		List<User> subAgets = this.userService.findSubAgents(
				ChannelContext.getSessionChannel().getUserNumber());
		JSONArray object = JSONArray.fromObject(subAgets);
		return object.toString();
	}
	
	/** 查询下级对应编号的信息 */
	@ResponseBody
	@RequestMapping("checkAgentNumber")
	public JSONObject checkAgentNumber(
			@RequestParam("usernumber") String usernumber) {
		String companyNmae = this.userService
				.findChannelCertRealName(usernumber);
		if (!StringUtils.isEmpty(companyNmae)) {
			if (companyNmae.length() > 4) {
				companyNmae = companyNmae.substring(0, 4) + "***";
			} else {
				companyNmae = companyNmae.substring(0, 1) + "***";
			}
			companyNmae = companyNmae + "(" + usernumber + ")";
		} else {
			companyNmae = usernumber;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("channel_name", companyNmae);
		return JSONObject.fromObject(map);
	}
	
	/** 查询发送邮件时输入的账户信息 */
	@ResponseBody
	@RequestMapping("checkSendNumber")
	public JSONObject checkSendNumber(
			@RequestParam("usernumber") String inputusernumber) {
		try {
			inputusernumber = new String(inputusernumber.getBytes("iso8859-1"),
					"utf-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("checkAgentNumber", e.fillInStackTrace());
		}
		if (inputusernumber.length() > 8
				&& inputusernumber.indexOf("(") != -1) {
			int begin = inputusernumber.indexOf("(") + 1;
			int end = inputusernumber.indexOf(")");
			inputusernumber = inputusernumber.substring(begin, end);
		}
		String companyNmae = this.userService
				.findChannelCompanyName(inputusernumber);
		if (!StringUtils.isEmpty(companyNmae)) {
			if (companyNmae.length() > 4) {
				companyNmae = companyNmae.substring(0, 4) + "***";
			} else {
				companyNmae = companyNmae.substring(0, 1) + "***";
			}
			companyNmae = companyNmae + "(" + inputusernumber + ")";
		} else {
			companyNmae = inputusernumber;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("channel_name", companyNmae);
		return JSONObject.fromObject(map);
	}
	
	/**
	 * 返回美贝，短信余额提醒阀值设置页面 请求方式只能是GET
	 * 
	 * @return 美贝、短息剩余值设置页面
	 */
	@RequestMapping(value = "set_remind", method = RequestMethod.GET)
	public ModelAndView setRemind() {
		UserRemindPoint remindPoint = this.usercontextservice
				.findUserRemindPoint(
						ChannelContext.getSessionChannel().getUserNumber());
		ModelAndView modelAndView = new ModelAndView(SET_REMIND);
		modelAndView.addObject("remindPoint", remindPoint);
		return modelAndView;
	}
	
	/**
	 * 处理用户提交的自定义的美贝、短信提醒阀值，处理成功后修改sessionUser中相关的数值
	 * 
	 * @param smsRemindNum
	 *            短信余额提醒阀值
	 * @param mbRemindNum
	 *            美贝余额提醒阀值
	 * @return 是否修改成功
	 */
	@RequestMapping(value = "do_set_remind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doSetRemind(@Valid UserRemindPoint remind,
			BindingResult bindResult) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (bindResult.hasErrors()) {
			result.put("success", false);
			result.put("message", "数据验证失败!");
			return result;
		}
		
		// 获取用户编号
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		// 更新用户提醒对象
		boolean isSuccess = usercontextservice.updateUserRemindPoint(remind,
				userNumber);
		result.put("success", isSuccess);
		return result;
	}
}
