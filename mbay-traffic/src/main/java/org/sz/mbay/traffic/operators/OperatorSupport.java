package org.sz.mbay.traffic.operators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sz.mbay.operator.bean.TrafficPackage;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.operator.enums.TrafficType;
import org.sz.mbay.traffic.context.SpringApplicationContext;
import org.sz.mbay.traffic.operators.mobile.gd.GDMobile;
import org.sz.mbay.traffic.operators.telecom.sh.SHTelecom;
import org.sz.mbay.traffic.operators.telecom.state.gd.GDTelecomState;
import org.sz.mbay.traffic.operators.unicom.state.bj.BJUnicomState;
import org.sz.mbay.traffic.operators.unicom.zj.ZJUnicom;
import org.sz.mbay.traffic.operators.vno.state.liumi.LiuMiVNO;
import org.sz.mbay.traffic.util.Area;

/** 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author han.han 
* @date 2015-2-3 上午11:48:30 
*  
*/
/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author han.han
 * @date 2015-2-3 上午11:48:38
 * 
 */
public class OperatorSupport {
	private static Map<String, Operator> operators = new HashMap<String, Operator>();

	static {
		// 浙江联通
		List<TrafficType> zjflowtypes = new ArrayList<TrafficType>();
		zjflowtypes.add(TrafficType.PROVINCE);
		operators.put("332", new Operator("浙江中国联通", zjflowtypes,
				ZJUnicom.class.getSimpleName()));
				// 上海移动
				// List<TrafficType> shMobile=new ArrayList<TrafficType>();
				// shMobile.add(TrafficType.PROVINCE);
				// operators.put("311", new Operator("上海中国移动", shMobile,
				// SHMobile.class.getSimpleName()));

		// 上海联通
		/****
		 * List<TrafficType> shflowtypes=new ArrayList<TrafficType>();
		 * shflowtypes.add(TrafficType.PROVINCE);
		 * shflowtypes.add(TrafficType.STATE); operators.put("312", new
		 * Operator("上海中国联通", shflowtypes,
		 * SHUnicomBBSServer.class.getSimpleName()));
		 *****/
		// 上海电信
		List<TrafficType> shteleTypes = new ArrayList<TrafficType>();
		shteleTypes.add(TrafficType.PROVINCE);
		shteleTypes.add(TrafficType.STATE);
		operators.put("313", new Operator("上海中国电信", shteleTypes,
				SHTelecom.class.getSimpleName()));

		// //北京移动
		// List<TrafficType> bjMobile=new ArrayList<TrafficType>();
		// bjMobile.add(TrafficType.PROVINCE);
		// operators.put("111", new Operator("北京中国移动", bjMobile,
		// BJMobile.class.getSimpleName()));

		// 广东
		List<TrafficType> gdMobile = new ArrayList<TrafficType>();
		gdMobile.add(TrafficType.PROVINCE);
		gdMobile.add(TrafficType.STATE);
		operators.put("441", new Operator("广东中国移动", gdMobile,
				GDMobile.class.getSimpleName()));

		// 全国移动
		List<TrafficType> stateMobileTypes = new ArrayList<TrafficType>();
		stateMobileTypes.add(TrafficType.STATE);
		operators.put(Area.QUANGUO + String.valueOf(OperatorType.MOBILE),
				new Operator("全国移动", stateMobileTypes,
						LiuMiVNO.class.getSimpleName()));
		// 全国联通
		List<TrafficType> stateUnicomTypes = new ArrayList<TrafficType>();
		stateUnicomTypes.add(TrafficType.STATE);
		operators.put(Area.QUANGUO + String.valueOf(OperatorType.UNICOM),

		new Operator("全国联通", stateUnicomTypes,
				BJUnicomState.class.getSimpleName()));
		// 全国电信
		List<TrafficType> stateTelcomTypes = new ArrayList<TrafficType>();
		stateTelcomTypes.add(TrafficType.STATE);
		//operators.put(Area.QUANGUO + String.valueOf(OperatorType.TELECOM),
		//		new Operator("全国电信", stateTelcomTypes,
		//				GDTelecomState.class.getSimpleName()));
		operators.put(Area.QUANGUO + String.valueOf(OperatorType.TELECOM),
						new Operator("全国电信", stateTelcomTypes,
								LiuMiVNO.class.getSimpleName()));
		

	}

	// /**
	// * 判断是否对接了此运营商流量充值接口
	// * @param attribution 归属地
	// * @param flowtype 流量类型
	// * @return
	// */
	// public static boolean existOperator(Area area,TeleOperator
	// opertype,TrafficType traffictype){
	// String
	// attribution=String.valueOf(area.getValue())+String.valueOf(opertype.getValue());
	// if(operators.get(attribution)!=null){
	// if(operators.get(attribution).getFlowtypes().contains(traffictype)){
	// return true;
	// }d
	// }
	// //无本地查询是否全国
	// attribution=String.valueOf(Area.QUANGUO.getValue())+String.valueOf(opertype.getValue());
	// if(operators.get(attribution)!=null){
	// return true;
	// }
	// return false;
	// }

	public static TrafficRecharge getChargeOperator(Area area,
			TrafficPackage trafficPackage) {
		OperatorType opertype = trafficPackage.getOperator().getType();
		String attribution = area + String.valueOf(opertype);
		Operator operator = operators.get(attribution);
		TrafficType traffictype = trafficPackage.getTrafficType();
		if (operator != null && operator.getFlowtypes().contains(traffictype)) {
			TrafficRecharge charge = (TrafficRecharge) SpringApplicationContext
					.getBean(operator.getRegisterclass());
			if (charge.isSupportTraffic(traffictype,
					trafficPackage.getTraffic())) {
				return charge;
			}

		}
		attribution = Area.QUANGUO + String.valueOf(opertype.getValue());
		operator = operators.get(attribution);
		if (operator != null) {
			TrafficRecharge charge = (TrafficRecharge) SpringApplicationContext
					.getBean(operator.getRegisterclass());
			if (charge.isSupportTraffic(traffictype,
					trafficPackage.getTraffic())) {
				return charge;
			}
		}
		return null;
	}
}
