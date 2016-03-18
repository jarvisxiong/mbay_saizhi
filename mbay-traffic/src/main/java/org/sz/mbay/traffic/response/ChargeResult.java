package org.sz.mbay.traffic.response;

public class ChargeResult {
	public static final int CHARGING = 1;
	public static final int CHARGE_SUCCESS = 2;
	public static final int CHARGE_FAILE = 3;
	
	public static ChargeResult SUCCESS=new ChargeResult(CHARGE_SUCCESS,"充值成功");
	
	public static ChargeResult RECHARGING=new ChargeResult(CHARGING,"充值中");
	
	@SuppressWarnings("unused")
	private ChargeResult() {
	}

	public ChargeResult(int chargestate, String message) {
		this.chargestate=chargestate;
		this.message=message;
	}

	private int chargestate;

	private String message;

	public int getChargestate() {
		return chargestate;
	}

	public String getMessage() {
		return message;
	}

}
