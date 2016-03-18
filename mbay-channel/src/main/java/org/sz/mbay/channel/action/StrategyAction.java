package org.sz.mbay.channel.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.bean.Strategy;
import org.sz.mbay.channel.context.ChannelContext;
import org.sz.mbay.channel.framework.action.BaseAction;
import org.sz.mbay.channel.service.StrategyService;
import org.sz.mbay.channel.user.bean.User;
import org.sz.mbay.channel.user.bean.UserContext;
import org.sz.mbay.channel.user.service.UserAccountService;
import org.sz.mbay.channel.user.service.UserContextService;
import org.sz.mbay.channel.user.service.UserService;
import org.sz.mbay.channel.useraccount.bean.UserAccount;
import org.sz.mbay.common.util.DigestUtils;

import net.sf.json.JSONObject;

/**
 * @author ONECITY 策略管理
 * 
 */
@Controller
@RequestMapping("strategy")
public class StrategyAction extends BaseAction {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StrategyAction.class);
	public static final String STRATEGYMANAGE = "strategy/strategylist";
	public static final String ADDCODESTRATEGY = "strategy/addcodestrategy";
	public static final String ADDWECHATSTRATEGY = "strategy/addwechattrategy";
	public static final String GENERATESTRGYSUCCESS = "redirect:/strategy/strategymanage.mbay";
	public static final String CHANNELREDEEMCODE = "strategy/channelRedeemcode";
	public static final String STRATEGYINFO = "strategy/strategyinfo";
	
	@Autowired
	StrategyService strategyservice;
	@Autowired
	UserAccountService userAccountService;
	@Autowired
	UserContextService usercontextservice;
	@Autowired
	UserService userService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat(
				"yyyy-MM-dd"), true);
		binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping("strategymanage")
	public String strategymanage(Model model, Strategy strategy,
			PageInfo pageinfo) {
		strategy.setUserid(ChannelContext.getSessionChannel().getId());
		List<Strategy> strategys = this.strategyservice.findAllStrategy(
				strategy, pageinfo);
		model.addAttribute("strategys", strategys);
		model.addAttribute("pageinfo", pageinfo);
		return STRATEGYMANAGE;
	}
	
	/**
	 * 兑换码营销策略
	 * 
	 * @return
	 */
	@RequestMapping("codestrategy")
	public String codestrategy(Model model) {
		// long userid = ChannelContext.getSessionChannel().getId();
		UserAccount useraccount = null;// assetsservice.findUserAccountByUid(userid);
		UserContext usercontext = null;// this.usercontextservice.findUserContextByUID(userid);
		User user = null;// this.channelservice.findUserById(userid);
		model.addAttribute("useraccount", useraccount);
		model.addAttribute("usercontext", usercontext);
		model.addAttribute("user", user);
		return ADDCODESTRATEGY;
	}
	
	/**
	 * 创建策略
	 * 
	 * @param strategy
	 * @return
	 */
	@RequestMapping("generatestrgy")
	public String generatestrgy(Strategy strategy, Model model) {
		long userid = ChannelContext.getSessionChannel().getId();
		strategy.setUserid(userid);
		strategy.setSecuritycode(DigestUtils.md5Encrypt(
				System.currentTimeMillis() + ""));
		strategy.setCreatetime(new Timestamp(System.currentTimeMillis()));
		ExecuteResult result = strategyservice.addStrategy(strategy);
		if (!result.isSuccess()) {
			model.addAttribute("message", result.getMessage());
			// return PUBLIC_MSG_NOTE;
		}
		return GENERATESTRGYSUCCESS;
	}
	
	@RequestMapping(value = "channelRedeemcode")
	public String channelRedeemcode(@RequestParam("strategyid") int strategyid,
			Model model) {
		long userid = ChannelContext.getSessionChannel().getId();
		ExecuteResult result = this.strategyservice.generateRedeemcode(userid,
				strategyid);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("兑换码生成结果{SUCCESS:" + result.isSuccess() + ",message:"
					+ result.getMessage() + "}");
		}
		model.addAttribute("executeresult", result);
		
		return CHANNELREDEEMCODE;
	}
	
	@RequestMapping(value = "generateRedeemcode", method = RequestMethod.GET)
	public String generateRedeemcode(
			@RequestParam("strategyid") int strategyid,
			@RequestParam("ac") String ac, @RequestParam("mobile") String mobile) {
		return "";
	}
	
	@RequestMapping("strategyinfo")
	public String strategyinfo(Model model,
			@RequestParam("strategyid") int strategyid) {
		long userid = ChannelContext.getSessionChannel().getId();
		Strategy strategy = this.strategyservice
				.getStrategyDetailinfo(strategyid);
		UserAccount useraccount = null;// assetsservice.findUserAccountByUid(userid);
		UserContext usercontext = null;// this.usercontextservice.findUserContextByUID(userid);
		User user = null;// this.channelservice.findUserById(userid);
		model.addAttribute("useraccount", useraccount);
		model.addAttribute("usercontext", usercontext);
		model.addAttribute("user", user);
		model.addAttribute("strategy", strategy);
		return STRATEGYINFO;
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public JSONObject dleteStrategy(@RequestParam("id") int id) {
		
		ExecuteResult result = this.strategyservice.deleteStrategy(id);
		
		return JSONObject.fromObject(result);
	}
	
	/**
	 * 微信营销策略
	 * 
	 * @return
	 */
	@RequestMapping("wechatstrategy")
	public String wechatstrategy() {
		return ADDWECHATSTRATEGY;
	}
	
	@RequestMapping("generatewstrgy")
	public String generatewstragy() {
		return "";
	}
	
}
