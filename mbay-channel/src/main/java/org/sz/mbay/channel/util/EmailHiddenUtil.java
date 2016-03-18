package org.sz.mbay.channel.util;

/** 
* @Description:hidden the full address of email
* @author han.han 
* @date 2015-1-22 下午11:35:33 
*  
*/
public class EmailHiddenUtil {
	
	public static  String hidden(String email){
		int emailSignIndex=email.indexOf("@");
		String emailName=email.substring(0,emailSignIndex+1);
		if(emailName.length()>3){
			emailName=email.substring(0, 3);
		}else{
			emailName=emailName.substring(0,1);
		}
		email = emailName + "****"
				+ email.substring(emailSignIndex, email.length());
		return email;
	}

}
