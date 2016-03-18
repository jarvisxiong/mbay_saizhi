package org.sz.mbay.channel.costant.error;

import org.sz.mbay.base.exception.error.ErrorInfo;

/** 
* @Description: 全局异常类型常量
* @author han.han 
* @date 2014-10-25 上午12:48:07 
*  
*/
public final class GloblError{
	public static  final ErrorInfo SQL_EXCEPTION=new ErrorInfo("SQL_EXCEPTION","数据库处理异常，请联系管理员！",null);
	
	public static  final ErrorInfo NOT_FOUND=new ErrorInfo("NOT_FOUND","信息未填写完整或格式错误，请重新填写!",null);
	
	public static  final ErrorInfo ACCOUNT_BALANCE_NOT_ENOUGH=new ErrorInfo("ACCOUNT_BALANCE_NOT_ENOUGH","您的账户余额不足，请充值!",null);
	
	public static  final ErrorInfo ACCOUNT_TRADE_ERROR=new ErrorInfo("ACCOUNT_TRADE_ERROR","账户交易失败，请检查账户余额是否充足!",null);
}
