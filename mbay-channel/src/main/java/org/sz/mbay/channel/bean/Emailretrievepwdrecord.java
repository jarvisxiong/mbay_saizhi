package org.sz.mbay.channel.bean;

import java.io.Serializable;

import org.joda.time.DateTime;


/**
 * 通过邮箱找回密码记录表
 *@author Tom
 *@version 创建时间：2014-8-7上午1:27:38
 *@type 类型描述 
 */
@SuppressWarnings("serial")
public class Emailretrievepwdrecord implements Serializable {
	
	//用户名
	private String userNumber;
	//找回时间
	private DateTime createtime;
	//验证码
	private String digest;
	
	

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public DateTime getCreatetime() {
		return createtime;
	}

	public void setCreatetime(DateTime createtime) {
		this.createtime = createtime;
	}

	
	public String getUserNumber() {
		return userNumber;
	}

	
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	
	
	

}
