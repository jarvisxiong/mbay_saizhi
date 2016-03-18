package org.sz.mbay.channel.util;

/** 
* @Description:hidden the full phoneNumber
* @author han.han 
* @date 2015-1-22 下午11:35:33 
*  
*/
public class PhoneHiddenUtil {
	
	public static  String hidden(String phone){
		return phone.substring(0,3)+"****"+phone.substring(7);
	}

}
