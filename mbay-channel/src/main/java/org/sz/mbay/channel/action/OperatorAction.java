package org.sz.mbay.channel.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.base.area.Area;
import org.sz.mbay.channel.framework.action.BaseAction;
import org.sz.mbay.operator.bean.Operator;
import org.sz.mbay.operator.bean.TrafficPackage;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.operator.enums.TrafficType;
import org.sz.mbay.operator.service.OperatorService;

/**
 * @author ONECITY 运营商控制层
 */
@Controller
@RequestMapping("operator")
public class OperatorAction extends BaseAction {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OperatorAction.class);
	
	@Autowired
	OperatorService operatorservice;
	
	@RequestMapping("findOperatorsByArea")
	@ResponseBody
	public String findOperatorsByArea(@RequestParam("area") int area) {
		Area sarea = Area.valueOf(area);
		List<Operator> operators = operatorservice.findAllOperatorByArea(sarea);
		return JSONArray.fromObject(operators).toString();
		
	}
	
	/**
	 * 根据运营商和地区查询对应的所有流量包
	 * 
	 * @param area
	 * @param operator
	 * @return <result property="id" column="id" /> <result property="traffic"
	 *         column="traffic" /> <result property="mbayprice"
	 *         column="mbayprice" /> <result property="trafficType"
	 *         column="traffictype" /> <association property="operator"
	 *         javaType="trafficPackage"> <result property="type.key"
	 *         column="operator_type" /> </association>
	 */
	@RequestMapping("queryTrafficPackage")
	@ResponseBody
	public Object queryTrafficPackage(@RequestParam("area") int area,
			@RequestParam int operator, @RequestParam TrafficType trafficType) {
		List<TrafficPackage> packages = this.operatorservice
				.findAllTrafficPackage(Area.valueOf(area),
						OperatorType.valueOf(operator), trafficType);
		if (packages == null || packages.size() == 0) {
			packages = this.operatorservice.findAllTrafficPackage(Area.QUANGUO,
					OperatorType.valueOf(operator), trafficType);
		}
		if (packages != null && packages.size() > 0) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (TrafficPackage tpackage : packages) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", tpackage.getId());
				map.put("traffic", tpackage.getTraffic());
				map.put("mbayprice", tpackage.getMbayprice());
				map.put("traffictype", tpackage.getTrafficType().name());
				list.add(map);
			}
			LOGGER.info(JSONArray.fromObject(list).toString());
			return list;
		}
		return null;
	}
	
}