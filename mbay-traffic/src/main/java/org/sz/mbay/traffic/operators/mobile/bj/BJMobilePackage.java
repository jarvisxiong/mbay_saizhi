package org.sz.mbay.traffic.operators.mobile.bj;

import java.util.HashMap;
import java.util.Map;

import org.sz.mbay.operator.enums.TrafficType;

public class BJMobilePackage {
	/***
	2014手机上网本地流量30天包，100M/个（后向经营，5元/个，有效期30天）   2-QESP6BA
	2014手机上网本地流量30天包，30M/个（后向经营，2元/个，有效期30天）  2-QESP67N
	kimi 2014/9/25 16:22:30
	一共4个 都是本地的
	2014手机上网本地流量30天包，500M/个（后向经营，20元/个，有效期30天） 2-QESP6CY
	2014手机上网本地流量30天包，50M/个（后向经营，3元/个，有效期30天）  2-QESP69M****/
	private static Map<String,String[]> provinceMap=new HashMap<String,String[]>();
	static{
		provinceMap.put("10"+TrafficType.PROVINCE.value,  new String[]{"10","3"});
		provinceMap.put("30"+TrafficType.PROVINCE.value,  new String[]{"11","5"});
		provinceMap.put("70"+TrafficType.PROVINCE.value, new String[]{"12","10"});
		provinceMap.put("150"+TrafficType.PROVINCE.value, new String[]{"13","150"});
		provinceMap.put("500"+TrafficType.PROVINCE.value, new String[]{"18","500"});
	}
	public static  String[] getPackCodeByTraffic(TrafficType trafficType,int traffic){
		String key=String.valueOf(traffic)+trafficType.getValue();
		return provinceMap.get(key);
	}


}
