package org.sz.mbay.traffic.operators.telecom.state.gd;

import java.util.HashMap;
import java.util.Map;

class GDTelecomStatePackage {
	/**
	 * 
	 赛志科技5M国内通用包 101701
	 * 
	 * 赛志科技10M国内通用包 101702
	 * 
	 * 
	 * 赛志科技30M国内通用包 101703
	 * 
	 * 赛志科技50M国内通用包 101704
	 * 
	 * 赛志科技100M国内通用包 101705
	 * 
	 * 赛志科技200M国内通用包 101706
	 * 
	 * 赛志科技500M国内通用包 101707
	 * 
	 * 赛志科技1G国内通用包 101708
	 * 
	 * 
	 * 
	 * **/
	private static Map<Integer, String> map = new HashMap<Integer, String>();

	static {
		map.put(5, "101701");
		map.put(10, "101702");
		map.put(30, "101703");
		map.put(50, "101704");
		map.put(100, "101705");
		map.put(200, "101706");
		map.put(500, "101707");
		map.put(1024, "101708");
	}

	public static String getPackCodeByTraffic(int traffic) {
		return map.get(traffic);
	}

}
