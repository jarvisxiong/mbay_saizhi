package org.sz.mbay.traffic.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TrafficBackHandler {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TrafficBackHandler.class);
	@RequestMapping("backHandler")
	@ResponseBody
	public String backHandler(HttpServletRequest request) {
		String pay=request.getParameter("pay");
		String mobile=request.getParameter("phone");
		Map<String,String> map=new HashMap<String,String>();
		map.put("pay", pay);
		String key=request.getParameter("key");
		LOGGER.info("流量充值结果pay={}",pay);
		LOGGER.info("流量充值结果phone={}",mobile);
		LOGGER.info("流量充值结果key={}",key);
		return JSONObject.fromObject(map).toString();
		
		
	}

}
