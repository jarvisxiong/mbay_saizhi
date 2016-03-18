package org.sz.mbay.particularcase.pingan.trafficred.enums;

/**
 * 平安返回状态
 * 
 * @author frank.zong
 *		
 */
public enum PingAnStatus {
	
	FAIL("注册失败"), SUCCESS("注册成功"), ONCE("已经注册过");
	
	private String value;
	
	private PingAnStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
