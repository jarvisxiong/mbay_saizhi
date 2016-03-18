package org.sz.mbay.traffic.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.traffic.framework.controller.BaseController;
import org.sz.mbay.traffic.operators.unicom.sh.SHUnicom;
import org.sz.mbay.traffic.response.TrafficResponse;
import org.sz.mbay.traffic.service.CodeRedeemService;

@Controller
public class CodeRedeem extends BaseController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SHUnicom.class);

	/***
	 * 
	 * 			
	 * */
	@Autowired
	CodeRedeemService coderedeemserice;

	@RequestMapping(value = "/coderedeem", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public TrafficResponse redeem(
			@RequestParam("redeemcode") String redeemcode,
			@RequestParam("mobile") String mobile,
			@RequestParam("area") int area,
			@RequestParam("operator") int operator) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("接收到兑换码流量兑换请求，code:{},phone:{},are:{},operator:{}",
					redeemcode, mobile, area, operator);
		}

		return this.coderedeemserice.redeem(redeemcode, mobile, area, operator);
	}

}
