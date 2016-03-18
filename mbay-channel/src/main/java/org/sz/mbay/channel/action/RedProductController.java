package org.sz.mbay.channel.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.trafficred.bean.TrafficRedPackage;
import org.sz.mbay.trafficred.service.TrafficRedMbayService;
import org.sz.mbay.trafficred.service.TrafficRedPackageService;

@Controller
@RequestMapping("product")
public class RedProductController {

	@Autowired
	TrafficRedPackageService trafficRedPackageService;
	@Autowired
	TrafficRedMbayService trafficRedMbayService;

	@ResponseBody
	@RequestMapping("trafficProducts")
	public Object trafficProducts() {
		List<TrafficRedPackage> mobilePackages = this.trafficRedPackageService
				.findByOperatorType(OperatorType.MOBILE);
		List<TrafficRedPackage> unicomPackages = this.trafficRedPackageService
				.findByOperatorType(OperatorType.UNICOM);
		List<TrafficRedPackage> telecomPackages = this.trafficRedPackageService
				.findByOperatorType(OperatorType.TELECOM);
		Map<String, List<TrafficRedPackage>> trafficPackages = new HashMap<String, List<TrafficRedPackage>>();
		trafficPackages.put(OperatorType.MOBILE.name(), mobilePackages);
		trafficPackages.put(OperatorType.UNICOM.name(), unicomPackages);
		trafficPackages.put(OperatorType.TELECOM.name(), telecomPackages);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String jsonString = objectMapper
					.writeValueAsString(trafficPackages);
			System.out.println(jsonString);
			return JSONObject.fromObject(jsonString);
		} catch (IOException e) {
		}
		return null;
	}

}
