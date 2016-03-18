package org.sz.mbay.trafficorder.enums;

/**
 * @Description: 运营商流量充值状态
 * @author han.han
 * @date 2014-8-22 下午5:27:55
 * 
 */
public enum OperatorRechargeStatus {
	
	NON("未请求"),
	CONNECT_MBAYINTERFACE_FAIL("请求mbay_traffic失败"), // 此状态未使用
	OPER_RECHARGEING("充值中"),
	OPER_RECHARGE_SUCCESS("充值成功"),
	OPER_RECHARGE_FAIL("充值失败");
	
	private String value;
	
	private OperatorRechargeStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
