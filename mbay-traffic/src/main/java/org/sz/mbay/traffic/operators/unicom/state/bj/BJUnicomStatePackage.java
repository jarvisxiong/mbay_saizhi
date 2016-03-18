package org.sz.mbay.traffic.operators.unicom.state.bj;

import java.util.HashMap;
import java.util.Map;

class BJUnicomStatePackage {
	/**
	 * 
	 * 13777 20M 13808 50M 13751 100M 13768 200M 13786 300M 13797 500M 13757 1G
	 **/
	public static final String agentid = "90386";
	public static final String imark = "2";
	public static final String jobid = "ZXTGH07";
	public static final String productpwd = "huijuaction";
	public static final String despwd = "njaction";
	private static Map<Integer, String> map = new HashMap<Integer, String>();

	static {
		map.put(20, "G00020");
		map.put(50, "G00050");
		map.put(100, "G00100");
		map.put(200, "G00200");
		map.put(500, "G00500");
	}

	public static String getPackCodeByTraffic(int traffic) {
		return map.get(traffic);
	}

}
