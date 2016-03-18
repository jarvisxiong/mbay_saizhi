package org.sz.mbay.traffic.operators.mobile.gd;

import java.util.HashMap;
import java.util.Map;

class GDMobilePackage {
	/**
	 * 
	 赛志科技移动1G广东省内通用包 prod.10000008585106 赛志科技移动600M广东省内通用包 prod.10000008585105
	 * 赛志科技移动280M广东省内通用包 prod.10000008585104 赛志科技移动150M广东省内通用包
	 * prod.10000008585103 赛志科技移动70M广东省内通用包 prod.10000008585102 赛志科技移动30M广东省内通用包
	 * prod.10000008585101
	 * 
	 * **/
	private static Map<Integer, String> map = new HashMap<Integer, String>();

	static {
		map.put(10, "prod.10086000000121");
		map.put(30, "prod.10000008585101");
		map.put(70, "prod.10000008585102");
		map.put(150, "prod.10000008585103");
		map.put(280, "prod.10000008585104");
		map.put(600, "prod.10000008585105");
		map.put(1024, "prod.10000008585106");
	}
	public static String getPackCodeByTraffic(int traffic) {
		return map.get(traffic);
	}

}
