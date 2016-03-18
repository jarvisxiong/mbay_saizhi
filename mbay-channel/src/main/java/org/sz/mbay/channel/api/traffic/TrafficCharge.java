package org.sz.mbay.channel.api.traffic;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.base.wrap.RedeemResponse;
import org.sz.mbay.channel.api.traffic.service.TrafficChargeService;
import org.sz.mbay.channel.user.service.UserService;
import org.sz.mbay.common.util.DigestUtils;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("api/trafficcharge")
public class TrafficCharge {
	
	@Autowired
	TrafficChargeService chargeService;
	
	@Autowired
	UserService userService;
	
	@SuppressWarnings("unused")
	@RequestMapping("charge")
	@ResponseBody
	public JSONObject charge(HttpServletRequest request) {
		int channelid = 0;
		int strategyid = 0;
		try {
			// // request.setCharacterEncoding("utf-8");
			channelid = Integer.valueOf(request.getParameter("channelid"));
			String privatekey = userService.getPrivatekeyByUid(channelid);
			String strategyrquest = new String(request.getParameter("strategy")
					.toString().getBytes("ISO-8859-1"), "UTF-8");
			String strategystr = DigestUtils.des3Decrypt(strategyrquest);
			strategyid = Integer.valueOf(strategystr);
		} catch (Exception e) {
			// 参数信息错误
			return JSONObject.fromObject(RedeemCodeJson.INVALID_PARAMETER);
		}
		// TODO 判断手机号的有效性
		String mobile = request.getParameter("mobile");
		RedeemResponse response = chargeService.chargeTraffic(mobile,
				channelid, strategyid);
		return JSONObject.fromObject(response);
	}
	
}
