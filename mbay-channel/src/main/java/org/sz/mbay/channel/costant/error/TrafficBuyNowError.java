package org.sz.mbay.channel.costant.error;

import org.sz.mbay.base.exception.error.ErrorInfo;

/** 
* @Description: 流量直冲错误
* @author han.han 
* @date 2015-1-20 下午10:30:06 
*  
*/
public class TrafficBuyNowError {
	public static  final ErrorInfo NOT_FOUND_TRAFFIC_ORDER=new ErrorInfo("NOT_FOUND_TRAFFIC_ORDER","订单不存在，请返回重试！","/traffic/purchase.mbay");
	
	public static  final ErrorInfo NOT_AVALID_TRAFFIC_PACKAGE=new ErrorInfo("NOT_AVALID_TRAFFIC_PACKAGE","流量包无效，请返回重试！","/traffic/purchase.mbay");
}
