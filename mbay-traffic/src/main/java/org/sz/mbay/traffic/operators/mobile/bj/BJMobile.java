package org.sz.mbay.traffic.operators.mobile.bj;

import org.springframework.stereotype.Component;
import org.sz.mbay.operator.enums.TrafficType;
import org.sz.mbay.traffic.operators.TrafficRecharge;
import org.sz.mbay.traffic.response.ChargeResult;

@Component("BJMobile")
public class BJMobile implements TrafficRecharge  {

	@Override
	public ChargeResult charge(String mobile,
			TrafficType traffictype, int traffic,String ordernumber) {
		//上海移动未提供接口返回充值中
		return ChargeResult.RECHARGING;
	}

	@Override
	public boolean isSupportTraffic(TrafficType ttype, int traffic) {
		return BJMobilePackage.getPackCodeByTraffic(ttype,traffic)!=null;
	}
}
