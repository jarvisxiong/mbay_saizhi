package org.sz.mbay.channel.api.traffic;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.base.wrap.RedeemResponse;
import org.sz.mbay.channel.api.traffic.service.CodeRedeemTrafficService;
import org.sz.mbay.channel.service.StrategyService;
import org.sz.mbay.channel.user.service.UserService;
import org.sz.mbay.common.util.DigestUtils;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("api/redeemcode")
public class RedeemCodeSupport {
	
	@Autowired
	StrategyService strategyservice;
	@Autowired
	UserService userService;
	@Autowired
	CodeRedeemTrafficService codeRedeemService;
	
	/**
	 * 获取兑换码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("get")
	@ResponseBody
	public JSONObject getredeemcode(HttpServletRequest request) {
		
		int strategyid = 0;
		try {
			String strategyrquest = new String(request.getParameter("strategy")
					.toString().getBytes("ISO-8859-1"), "UTF-8");
			String strategystr = DigestUtils.des3Decrypt(strategyrquest);
			strategyid = Integer.valueOf(strategystr);
		} catch (Exception e) {
			// 参数信息错误
			return JSONObject.fromObject(RedeemCodeJson.INVALID_PARAMETER);
		}
		// TODO 判断手机号的有效性
		// String mobile=request.getParameter("mobile");
		RedeemCodeJson json = this.strategyservice
				.generateRedeemcodeJson(strategyid);
		return JSONObject.fromObject(json);
	}
	
	/**
	 * 兑换码兑换流量
	 * 
	 * @param redeemcode
	 * @param mobile
	 * @return
	 */
	@RequestMapping("redeemTraffic")
	@ResponseBody
	public JSONObject redeemTraffic(
			@RequestParam("redeemcode") String redeemcode,
			@RequestParam("mobile") String mobile) {
		// TODO 验证手机号码的有效性
		RedeemResponse response = this.codeRedeemService.redeem(redeemcode, mobile);
		return JSONObject.fromObject(response);
	}
	
}
