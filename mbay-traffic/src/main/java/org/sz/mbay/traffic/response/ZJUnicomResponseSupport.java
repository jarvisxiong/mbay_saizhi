package org.sz.mbay.traffic.response;


public class ZJUnicomResponseSupport extends ResponseSupport {
	/**
	private static final String STR_OPERATORSYSTEMTIMEOUT = "OPERATOR_SYSTEM_TIMEOUT";

	private static final String STR_OPERATORUNABLECURRENTTIME = "OPERATOR_UNABLE_TIME";

	private static final String STR_OPERATORRECHARGEFAIL = "OPERATOR_RECHARGE_FAIL";

	private static final String STR_TRAFFIC_CARD_ERROR = "TRAFFIC_CARD_ERROR";

	
	private static final TrafficResponse OPERATOR_SYSTEM_TIMEOUT = new TrafficResponse(
			STR_OPERATORSYSTEMTIMEOUT, "通信运营商帐务系统操作超时", MsgType.TEXT);

	private static final TrafficResponse OPERATOR_UNABLE_TIME = new TrafficResponse(
			STR_OPERATORUNABLECURRENTTIME, "运营商不支持当前时间段充值", MsgType.TEXT);

	private static final TrafficResponse OPERATOR_RECHARGE_FAIL = new TrafficResponse(
			STR_OPERATORRECHARGEFAIL, "运营商充值失败", MsgType.TEXT);

	private static final TrafficResponse TRAFFIC_CARD_ERROR = new TrafficResponse(
			STR_TRAFFIC_CARD_ERROR, "系统卡密池维护，请稍后再试或联系客服人员", MsgType.TEXT);
			****/
	/*
	 * 00 缴费卡缴费成功 01 系统故障 02 缴费账户不存在 03 缴费账户存在但是不能用于缴费 04 帐务系统操作超时 05 不在缴费时段 06
	 * 缴费操作失败 07 卡不存在 08 密码错误 09 卡已过期 10 卡已经注销 11 充值中 12 卡已充值 13 非激活卡 14 余额不足
	 */
	public static ChargeResult rechargeResponse(String code, String card) {
		if ("00".equals(code) || "11".equals(code)) {
			return new ChargeResult(ChargeResult.CHARGE_SUCCESS,
					"充值成功,运营商：浙江联通,卡密：" + card);
		}
		return new ChargeResult(ChargeResult.CHARGE_FAILE, "充值失败,运营商：浙江联通,卡密："
				+ card + ",错误码：" + getErrorMsgByCode(code));
	}

	private static String getErrorMsgByCode(String code) {
		if ("00".equals(code)) {
			return "00 缴费卡缴费成功";
		} else if ("01".equals(code)) {
			return "01 系统故障";
		} else if ("02".equals(code)) {
			return "02 缴费账户不存在";
		} else if ("03".equals(code)) {
			return "03 缴费账户存在但是不能用于缴费";
		} else if ("04".equals(code)) {
			return "04 帐务系统操作超时";
		} else if ("05".equals(code)) {
			return "05 不在缴费时段";
		} else if ("06".equals(code)) {
			return "06缴费操作失败";
		} else if ("07".equals(code)) {
			return "07 卡不存在";
		} else if ("08".equals(code)) {
			return "08 密码错误";
		} else if ("09".equals(code)) {
			return "09 卡已过期";
		} else if ("10".equals(code)) {
			return "10 卡已经注销";
		} else if ("11".equals(code)) {
			return "11 充值中";
		} else if ("12".equals(code)) {
			return "12 卡已充值";
		} else if ("13".equals(code)) {
			return "13 非激活卡";
		} else if ("14".equals(code)) {
			return "14 余额不足";
		}
		return code + "未知";
	}

}
