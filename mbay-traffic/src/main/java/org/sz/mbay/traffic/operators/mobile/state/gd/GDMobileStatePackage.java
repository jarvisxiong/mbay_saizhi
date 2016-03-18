package org.sz.mbay.traffic.operators.mobile.state.gd;

import java.util.HashMap;
import java.util.Map;

class GDMobileStatePackage {
	/**
	 * 
	 赛志科技移动1G国内通用包 101803 赛志科技移动500M国内通用包 101802 赛志科技移动150M国内通用包 101801
	 * 赛志科技移动70M国内通用包 101800 赛志科技移动30M国内通用包 101799 赛志科技移动10M国内通用包 101798
	 * 
	 * **/
	private static Map<Integer, String> map = new HashMap<Integer, String>();
	
	static {
		map.put(10, "101798");
		map.put(30, "101799");
		map.put(70, "101800");
		map.put(150, "101801");
		map.put(500, "101802");
		map.put(1024, "101803");
	}

	public static String getPackCodeByTraffic(int traffic) {
		return map.get(traffic);
	}

}
