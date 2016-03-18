package org.sz.mbay.base.wrap;

public class RedeemResponse {

	private static final String STR_NONSUPPORTMOBILE = "NONSUPPORT_MOBILE";

	public static final String STR_RECHARGEING = "TRAFFIC_RECHARGEING";
	/** 系统异常 **/
	public static final String STR_SYSTEMERROR = "SYSTEM_ERROR";
	/** 无效的兑换码 *****/
	private static final String STR_INVALIDREDEEMCODE = "INVALID_REDEEMCODE";

	private static final String STR_EXPIREDREDEEMCODE = "EXPIRED_REDEEMCODE";
	private static final String INSUFFICIENTACCOUNT = "INSUFFICIENT_ACCOUNT";

	private boolean success;

	private String status;

	private String msgType;

	private String content;
	
	
	public RedeemResponse() {
	}

	public RedeemResponse(String status, String content, String msgType) {
		this.status = status;
		this.msgType = msgType;
		this.content = content;
	}

	public RedeemResponse(boolean success, String status, String content,
			String msgType) {
		this.success = success;
		this.status = status;
		this.msgType = msgType;
		this.content = content;
	}

	public static final RedeemResponse TRAFFIC_RECHARGEING = new RedeemResponse(
			true, STR_RECHARGEING, "流量兑换中", MsgType.TEXT);

	public static final RedeemResponse TRAFFIC_EXCHANGE_OVER = new RedeemResponse(
			STR_RECHARGEING, "亲，您来晚了，活动已结束！", MsgType.TEXT);

	public static final RedeemResponse INVALIDREDEEMCODE = new RedeemResponse(
			STR_INVALIDREDEEMCODE, "无效的兑换码", MsgType.TEXT);

	public static final RedeemResponse EXPIREDREDEEMCODE = new RedeemResponse(
			STR_EXPIREDREDEEMCODE, "兑换码已过期", MsgType.TEXT);

	public static final RedeemResponse CODE_REDEEMED = new RedeemResponse(
			STR_EXPIREDREDEEMCODE, "兑换码已使用", MsgType.TEXT);

	public static final RedeemResponse NOTFOUND_MOBILE = new RedeemResponse(
			STR_NONSUPPORTMOBILE, "未找到对应的手机号信息", MsgType.TEXT);

	public static final RedeemResponse NONSUPPORT_MOBILE = new RedeemResponse(
			STR_NONSUPPORTMOBILE, "不支持的手机号", MsgType.TEXT);

	public static final RedeemResponse SYSTEMERROR = new RedeemResponse(
			STR_SYSTEMERROR, "系统异常", MsgType.TEXT);

	public static final RedeemResponse INSUFFICIENT_ACCOUNT = new RedeemResponse(
			INSUFFICIENTACCOUNT, "亲，您来晚了，奖品已发完！", MsgType.TEXT);

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
