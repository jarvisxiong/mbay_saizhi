package org.sz.mbay.traffic.operators.unicom.sh;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.sz.mbay.operator.enums.TrafficType;
import org.sz.mbay.traffic.response.ChargeResult;
import org.sz.mbay.traffic.response.TrafficResponse;
import org.sz.mbay.traffic.util.DES;
import org.sz.mbay.traffic.util.Md5Encrypt;

@Component("SHUnicom")
public class SHUnicom  {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SHUnicom.class);

	/*****
	 * freebd10 10M3G本地流量包_2013合作版 90386 ZXTGH07 huijuaction freebd20
	 * 20M3G本地流量包_2013合作版 90386 ZXTGH07 huijuaction freebd30 30M3G本地流量包_2013合作版
	 * 90386 ZXTGH07 huijuaction freebd50 50M3G本地流量包_2013合作版 90386 ZXTGH07
	 * huijuaction freebd80 80M3G本地流量包_2013合作版 90386 ZXTGH07 huijuaction
	 * freebd100 100M3G本地流量包_2013合作版 90386 ZXTGH07 huijuaction freebd120
	 * 120M3G本地流量包_2013合作版 90386 ZXTGH07 huijuaction freebd130
	 * 130M3G本地流量包_2013合作版 90386 ZXTGH07 huijuaction freebd150
	 * 150M3G本地流量包_2013合作版 90386 ZXTGH07 huijuaction freebd180
	 * 180M3G本地流量包_2013合作版 90386 ZXTGH07 huijuaction freebd200
	 * 200M3G本地流量包_2013合作版 90386 ZXTGH07 huijuaction freebd220
	 * 220M3G本地流量包_2013合作版 90386 ZXTGH07 huijuaction freebd230
	 * 230M3G本地流量包_2013合作版 90386 ZXTGH07 huijuaction freebd250
	 * 250M3G本地流量包_2013合作版 90386 ZXTGH07 huijuaction freebd300
	 * 300M3G本地流量包_2013合作版 90386 ZXTGH07 huijuaction freebd350
	 * 350M3G本地流量包_2013合作版 90386 ZXTGH07 huijuaction freebd400
	 * 400M3G本地流量包_2013合作版 90386 ZXTGH07 huijuaction freebd450
	 * 450M3G本地流量包_2013合作版 90386 ZXTGH07 huijuaction freebd500
	 * 500M3G本地流量包_2013合作版 90386 ZXTGH07 huijuaction
	 *****/

	public ChargeResult charge(String ordernumber,String mobile, TrafficType flowtype, int traffic) {

		if (TrafficType.PROVINCE.equals(flowtype)) {// 省内流量兑换
			return null;
			///return recharegePorvince(mobile, traffic);

		} else if (TrafficType.STATE.equals(flowtype)) {// 国内流量兑换
			///return recharegeState(mobile, traffic);
			return null;
		}
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("SHUnicom 找不到对应的兑换类型。请求类型：{}", flowtype);
		}
		return null;
	///	return ResponseSupport.NO_CORRESPOND_TRAFFIC;

	}

	/**
	 * 兑换省内流量
	 * 
	 * @param mobile
	 * @param traffic
	 * @return
	 */
	public TrafficResponse recharegePorvince(String mobile, int traffic) {
		String url = "http://www.ishwap.com/navsh/Cooperation/Comm/OrderAfter.aspx";
		String productid = SHUnicomPackage.getProvincePackCodeByTraffic(traffic);
		Map<String,String> map=new HashMap<String,String>();
		String timestamp = (System.currentTimeMillis() + "").substring(0, 10);
		String responsestr = SHUnicomPackage.agentid + productid + SHUnicomPackage.jobid + timestamp
				+ SHUnicomPackage.productpwd;
		String response = Md5Encrypt.md5(responsestr, "UTF-8");
		map.put("agentid", SHUnicomPackage.agentid);
		map.put("imark", SHUnicomPackage.imark);
		map.put("timestamp", timestamp);
		map.put("response", response);
		map.put("jobid", DES.encode(SHUnicomPackage.jobid,SHUnicomPackage.despwd));
		map.put("productid", productid);
		map.put("mobile", DES.encode(mobile,SHUnicomPackage.despwd));
		map.put("showtop", "false");
		String link=createUrl(url,map);
		if(LOGGER.isInfoEnabled()){
			LOGGER.info("上海联通兑换连接{}",link);
		}
	///	TrafficResponse trafficresponse=new TrafficResponse(true,ResponseSupport.STR_RECHARGEING,link);
	///	 return trafficresponse;
		return null;
	}
	private String createUrl(String url,Map<String,String> map){
		StringBuilder sb=new StringBuilder(100);
		sb.append(url+"?");
		Iterator<Map.Entry<String, String>> iterator=map.entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry<String, String>entity=iterator.next();
			sb.append(entity.getKey()+"="+entity.getValue());
			if(iterator.hasNext()){
				sb.append("&");
			}
		}
		return sb.toString();
	}

	/**
	 * 兑换国内流量
	 * 
	 * @param mobile
	 * @param traffic
	 * @return
	 */
	public TrafficResponse recharegeState(String mobile, int traffic) {
		return null;
	}

}
