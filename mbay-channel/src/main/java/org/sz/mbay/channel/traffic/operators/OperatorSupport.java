package org.sz.mbay.channel.traffic.operators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sz.mbay.hcode.bean.HcodeInfo;
import org.sz.mbay.operator.enums.TrafficType;

public class OperatorSupport {
	
	private static Map<String, Operator> operators = new HashMap<String, Operator>();
	
	static {
		List<TrafficType> zjflowtypes = new ArrayList<TrafficType>();
		zjflowtypes.add(TrafficType.PROVINCE);
		operators.put("332", new Operator("浙江中国联通", zjflowtypes));
		
		List<TrafficType> shflowtypes = new ArrayList<TrafficType>();
		shflowtypes.add(TrafficType.PROVINCE);
		operators.put("312", new Operator("上海中国联通", shflowtypes));
	}
	
	/**
	 * 判断是否对接了此运营商流量充值接口
	 * 
	 * @param attribution
	 *            归属地
	 * @param flowtype
	 *            流量类型
	 * @return
	 */
	public static boolean existOperator(String attribution, int flowtype) {
		if (operators.get(attribution) != null) {
			if (operators.get(attribution).getFlowtypes().contains(flowtype)) {
				return true;
			}
		}
		return false;
	}
	
	public static Operator getOperator(HcodeInfo codeinfo) {
		return operators.get(codeinfo.getProvcode() + "" + codeinfo.getOperator());
		
	}
	
}
