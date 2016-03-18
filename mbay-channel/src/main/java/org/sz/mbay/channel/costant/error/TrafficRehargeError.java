package org.sz.mbay.channel.costant.error;

import org.sz.mbay.base.exception.error.ErrorInfo;

/** 
* @Description: 全局异常类型常量
* @author han.han 
* @date 2014-10-25 上午12:48:07 
*  
*/
public final class TrafficRehargeError{
	public static  final ErrorInfo INVALID_RECHARGE_ORDER=new ErrorInfo("INVALID_RECHARGE_ORDER","流量充值订单无效，请返回重试！",null);
	
	public static  final ErrorInfo RECHARGE_FAIL=new ErrorInfo("RECHARGE_FAIL","流量充值失败，请返回重试！",null);
}
