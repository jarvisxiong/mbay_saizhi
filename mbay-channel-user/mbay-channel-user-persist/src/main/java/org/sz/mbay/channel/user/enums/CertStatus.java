package org.sz.mbay.channel.user.enums;

/** 
* @Description: 商户实名状态标识
* @author han.han 
* 
*  
*/
public enum CertStatus {
	
	UNAPPLY("未申请"), UNDERREVIEW("审核中"), UNPASSED("审核不通过"), APPROVED("已认证");
	
	private String value;
	
	private CertStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	

}
