package org.sz.mbay.channel.costant.error;

import org.sz.mbay.base.exception.error.ErrorInfo;

/** 
* @Description: 修改密码异常
* @author han.han 
* @date 2014-10-25 上午12:48:07 
*  
*/
public final class PwdManageError {
	public static final ErrorInfo SESSION_EXPIRED = new ErrorInfo("SESSION_EXPIRED", "页面过期", "/resetpwd/findpwd/tofind.mbay");
	public static final ErrorInfo NONE_SUCCESS=new ErrorInfo("NONE_SUCCESS","您的信息提交未成功，请尝试关闭浏览器，重新打开信息提交页面，填写并及时提交您的身份信息","/resetpwd/findpwd/tofind.mbay");
	public static final ErrorInfo NONE_MANIPULATE=new ErrorInfo("NONE_MANIPULATE","您的账户无法进行此项操作","/resetpwd/findpwd/tofind.mbay");

}
