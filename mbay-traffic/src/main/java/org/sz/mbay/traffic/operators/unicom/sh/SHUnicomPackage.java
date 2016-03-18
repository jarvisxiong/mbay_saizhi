package org.sz.mbay.traffic.operators.unicom.sh;

import java.util.HashMap;
import java.util.Map;

class SHUnicomPackage{
	/**
	 * 
13777	20M
13808	50M
13751	100M
13768	200M
13786	300M
13797	500M
13757	1G
	 * **/
	public static final String  agentid="90386";
	public static final String  imark="2";
	public static final String  jobid="ZXTGH07";
	public static final String  productpwd="huijuaction";
	public static final String despwd="njaction";
	private static  Map<Integer,String> provincePackagemap=new HashMap<Integer, String>();
	private static  Map<Integer,String> statePackagemap=new HashMap<Integer, String>();
	static{
		provincePackagemap.put(1, "freebd001");
		provincePackagemap.put(10, "freebd10");
		provincePackagemap.put(20, "13777");
		provincePackagemap.put(30, "freebd30");
		provincePackagemap.put(50, "13808");
		provincePackagemap.put(80, "freebd80");
		provincePackagemap.put(100, "13751");//old code:freebd100
		provincePackagemap.put(120, "freebd120");
		provincePackagemap.put(130, "freebd130");
		provincePackagemap.put(150, "freebd150");
		provincePackagemap.put(180, "freebd180");
		provincePackagemap.put(200, "13768");//old:freebd200
		provincePackagemap.put(220, "freebd220");
		provincePackagemap.put(230, "freebd230");
		provincePackagemap.put(250, "freebd250");
		provincePackagemap.put(300, "13786");
		provincePackagemap.put(350, "freebd350");
		provincePackagemap.put(400, "freebd400");
		provincePackagemap.put(450, "freebd450");
		provincePackagemap.put(500, "13797");
		provincePackagemap.put(1024, "13757");
		//上海联通全国流量包
		statePackagemap.put(10, "1384180000478");
		statePackagemap.put(20, "1384180000488");
		statePackagemap.put(50, "1384180000498");
		statePackagemap.put(100, "1384180000508");
		statePackagemap.put(200, "1384180000518");
		statePackagemap.put(300, "1384180000528");
		statePackagemap.put(500, "1384180000538");
		
		
		
		
		///statePackagemap.put(500, "13901");
	}	
	public static  String getProvincePackCodeByTraffic(int traffic){
		return provincePackagemap.get(traffic);
	}
	public static  String getStatePackCodeByTraffic(int traffic){
		return statePackagemap.get(traffic);
	}

}
