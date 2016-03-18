package org.sz.mbay.traffic.controller;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.base.regex.pattern.RegExp;
import org.sz.mbay.hcode.bean.HcodeInfo;
import org.sz.mbay.operator.bean.TrafficPackage;
import org.sz.mbay.operator.service.OperatorService;
import org.sz.mbay.traffic.component.hcode.HCodeSupport;
import org.sz.mbay.traffic.framework.controller.BaseController;
import org.sz.mbay.traffic.operators.OperatorSupport;
import org.sz.mbay.traffic.operators.TrafficRecharge;
import org.sz.mbay.traffic.response.TrafficResponse;
import org.sz.mbay.traffic.service.TrafficChargeService;
import org.sz.mbay.traffic.util.Area;

import net.sf.json.JSONObject;

/**
 * @Description: 流量充值接口
 * @author han.han
 * @date 2014-9-26 下午4:28:30
 * 
 */
@Controller
public class TrafficRechargeController extends BaseController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TrafficRechargeController.class);
	ExecutorService executorService = Executors.newFixedThreadPool(50);

	@Autowired
	TrafficChargeService rechargeService;

	@Autowired
	OperatorService operatorService;

	@RequestMapping(value = "recharge")
	@ResponseBody
	public String traffieRecharge(HttpServletRequest request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("接收到流量充值请求");
		}

		String mobile = request.getParameter("mobile");// 手机号
		String packageNo = request.getParameter("packageNo");// 流量包编号
		String orderNumber = request.getParameter("orderNumber");
		// 记录请求参数日志
		if (LOGGER.isInfoEnabled()) {
			@SuppressWarnings("unchecked")
			Map<String, String> parameMap = request.getParameterMap();
			StringBuffer str = new StringBuffer(100);
			parameMap.forEach((key, value) -> {
				str.append(key + ":" + value + ";");
			});
			LOGGER.info("请求参数信息：" + str.toString());
		}

		// 判断手机号是否符合标准
		if (StringUtils.isEmpty(mobile)
				|| !RegExp.mobile.matcher(mobile).matches()) {
			return JSONObject.fromObject(TrafficResponse.MOBILE_ERROR)
					.toString();
		}
		try {
			Integer.valueOf(packageNo);
		} catch (Exception e) {
			return JSONObject.fromObject(TrafficResponse.PACKAGER_ERROR)
					.toString();
		}
		// 判断流量包编号是否符合标准
		final int packageId = Integer.valueOf(packageNo);
		executorService.execute(() -> {
			TrafficPackage trafficPackage = operatorService
					.findTrafficPackage(packageId);
			HcodeInfo info = HCodeSupport.getInstance().getHcodeInfo(mobile);
			Area area = Area.getArea(info.getProvcode());
			TrafficRecharge trafficCharge = OperatorSupport
					.getChargeOperator(area, trafficPackage);
			trafficCharge.charge(mobile, trafficPackage.getTrafficType(),
					trafficPackage.getTraffic(), orderNumber);

		});

		//
		return JSONObject.fromObject(TrafficResponse.RECHARGING).toString();
	}
}
