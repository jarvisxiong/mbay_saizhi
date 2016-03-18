package org.sz.mbay.traffic.operators.telecom.sh;

import java.util.HashMap;
import java.util.Map;

import org.sz.mbay.operator.enums.TrafficType;

public class SHTelePackage {
	/***
	2014手机上网本地流量30天包，100M/个（后向经营，5元/个，有效期30天）   2-QESP6BA
	2014手机上网本地流量30天包，30M/个（后向经营，2元/个，有效期30天）  2-QESP67N
	kimi 2014/9/25 16:22:30
	一共4个 都是本地的
	2014手机上网本地流量30天包，500M/个（后向经营，20元/个，有效期30天） 2-QESP6CY
	2014手机上网本地流量30天包，50M/个（后向经营，3元/个，有效期30天）  2-QESP69M****/
	private static Map<String,String> provinceMap=new HashMap<String,String>();
	static{
		provinceMap.put("30"+TrafficType.PROVINCE.value,  "2-QESP67N");
		provinceMap.put("50"+TrafficType.PROVINCE.value,  "2-QESP69M");
		provinceMap.put("100"+TrafficType.PROVINCE.value, "2-QESP6BA");
		provinceMap.put("500"+TrafficType.PROVINCE.value, "2-QESP6CY");
		provinceMap.put("60"+TrafficType.STATE.value, "2-SACI1MF");
		provinceMap.put("150"+TrafficType.STATE.value, "2-SACI1V4");
		provinceMap.put("300"+TrafficType.STATE.value, "2-SACI1XM");
	}
	public static  String getPackCodeByTraffic(TrafficType trafficType,int traffic){
		String key=String.valueOf(traffic)+trafficType.getValue();
		return provinceMap.get(key);
	}


}
