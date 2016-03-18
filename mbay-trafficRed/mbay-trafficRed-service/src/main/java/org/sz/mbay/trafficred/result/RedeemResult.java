package org.sz.mbay.trafficred.result;

import org.sz.mbay.base.wrap.Response;
import org.sz.mbay.base.wrap.ResponseFail;

public class RedeemResult extends Result {
	
	// 是否可兑换下一步奏
	private boolean continuable;
	
	public boolean isContinuable() {
		return continuable;
	}
	
	public void setContinuable(boolean continuable) {
		this.continuable = continuable;
	}
	
	public RedeemResult(boolean status, String code, String content) {
		super(status, code, content);
		this.continuable = false;
	}
	
	public RedeemResult(boolean status, String code, String content,
			boolean continuable) {
		super(status, code, content);
		this.continuable = continuable;
	}
	
	public static RedeemResult create(boolean status, String code,
			String content) {
		return new RedeemResult(status, code, content);
	}
	
	public static Result valueOf(Response resp) {
		if (resp instanceof ResponseFail) {
			return create(false, ((ResponseFail) resp).getErrorCode(),
					((ResponseFail) resp).getErrorMsg());
		} else {
			return resp.getStatus() ? SUCCESS : FAIL;
		}
	}
	
	public static Result SUCCESS = new RedeemResult(true, "SUCCESS", "成功");
	
	public static Result FAIL = new RedeemResult(false, "FAIL", "失败");
	
	/*-----------------------------------------------
	 *                      活动错误
	 *----------------------------------------------*/
	
	public static Result NOT_WIN = new RedeemResult(
			false, "NOT_WIN", "手气真差，没有摇到/(ㄒoㄒ)/~~");
	
	public static Result INFO_INSUFFICIENT = new RedeemResult(
			false, "INFO_INSUFFICIENT", "请检查您填写的信息是否完整");
	
	public static Result MOBILE_INCORRECT = new RedeemResult(
			false, "MOBILE_INCORRECT", "您使用的号码有问题哦请检查");
	
	public static Result MOBILE_HCODE_NOT_FOUND = new RedeemResult(
			false, "MOBILE_HCODE_NOT_FOUND", "您使用的号码有问题哦请检查");
	
	public static Result CAMPAIGN_NOT_STARTED = new RedeemResult(
			false, "CAMPAIGN_NOT_STARTED", "太心急啦！活动还没有开始");
	
	public static Result CAMPAIGN_NUMBER_ERROR = new RedeemResult(
			false, "CAMPAIGN_NUMBER_ERROR", "咦？这个活动编号不对哦");
	
	public static Result CAMPAIGN_NOT_EXIST = new RedeemResult(
			false, "CAMPAIGN_NOT_EXIST", "咦？这个活动不对哦");
	
	public static Result TEMPLATE_NOT_EXIST = new RedeemResult(
			false, "TEMPLATE_NOT_EXIST", "咦？这个活动模板不对哦");
	
	public static Result SHAREINFO_NOT_EXIST = new RedeemResult(
			false, "SHAREINFO_NOT_EXIST", "咦？这个活动分享信息不对哦");
	
	public static Result CAMPAIGN_ENDED = new RedeemResult(
			false, "CAMPAIGN_ENDED", "您来晚咯，这个活动已经结束了");
	
	public static Result CAMPAIGN_CANCELED = new RedeemResult(
			false, "CAMPAIGN_CANCELED", "小伙伴们太踊跃，活动提前结束啦~");
	
	public static Result PRODUCT_OVER = new RedeemResult(
			false, "PRODUCT_OVER", "流量在路上，稍后再来摇~");
	
	public static Result TIMES_EXCEED_TOTAL = new RedeemResult(
			false, "TIMES_EXCEED_TOTAL", "啊，摇一摇次数用完啦！~谢谢参与");
	
	public static Result TIMES_EXCEED_DAY = new RedeemResult(
			false, "TIMES_EXCEED_DAY", "啊，今天的摇一摇次数已经用完啦！");
	
	public static Result TIMES_EXCEED_WEEK = new RedeemResult(
			false, "TIMES_EXCEED_WEEK", "啊，这周的摇一摇次数已经用完啊！");
	
	public static Result TIMES_EXCEED_MONTH = new RedeemResult(
			false, "TIMES_EXCEED_MONTH", "啊，这个月的摇一摇次数已经用完啦！");
	
	public static Result TRAFFIC_RUN_OUT_ALL = new RedeemResult(
			false, "TRAFFIC_RUN_OUT_ALL", "流量全部都领完咯~先去商城看看能换什么吧~", true);
	
	public static Result TRAFFIC_RUN_OUT_DAY = new RedeemResult(
			false, "TRAFFIC_RUN_OUT_DAY", "今天的流量已经领完咯，明天再来试试吧~", true);
	
	public static Result MBAY_RUN_OUT_ALL = new RedeemResult(
			false, "MBAY_RUN_OUT_ALL", "流量全部都领完咯~先去商城看看能换什么吧~", true);
	
	public static Result MBAY_RUN_OUT_DAY = new RedeemResult(
			false, "MBAY_RUN_OUT_DAY", "今天的流量已经领完咯，明天再来试试吧~", true);
	
	public static Result TIME_NOT_RATIONAL = new RedeemResult(
			false, "TIME_NOT_RATIONAL", "请在指定的时间段参与活动");
	
	public static Result TRAFFIC_PRODUCT_NOT_EXIST = new RedeemResult(
			false, "TRAFFIC_PRODUCT_NOT_EXIST", "本次活动的流量包不支持您的手机号码～～");
	
	public static Result TRAFFIC_SEED_ZERO = new RedeemResult(
			false, "TRAFFIC_SEED_ZERO", "本次活动的流量包不支持您的手机号码～～");
	
	public static Result MBAY_PRODUCT_NOT_EXIST = new RedeemResult(
			false, "MBAY_PRODUCT_NOT_EXIST", "美贝产品包现在暂时无法使用哦~");
	
	public static Result MBAY_SEED_ZERO = new RedeemResult(
			false, "MBAY_SEED_ZERO", "美贝产品包现在暂时无法使用哦~");
	
	public static Result TRAFFIC_REDEEM_REPEAT = new RedeemResult(
			false, "TRAFFIC_REDEEM_REPEAT", "已经兑换过咯，请不要重复兑换");
	
	public static Result MOBILE_NOT_SAME = new RedeemResult(
			false, "MOBILE_NOT_SAME", "请输入您首次输入的手机号码");
	
	public static Result EXCHANGE_RECORD_ID_ERROR = new RedeemResult(
			false, "EXCHANGE_RECORD_ID_ERROR", "咦？这个兑换码不对哦");
	
	public static Result COOKIE_NOT_SUPPORTTED = new RedeemResult(
			false, "COOKIE_NOT_SUPPORTTED",
			"您的手机不支持cookie或cookie信息已被删除，请返回重新操作");
	
	public static final Result TRAFFIC_SIZE_ERROR = new RedeemResult(
			false, "TRAFFIC_SIZE_ERROR", "流量大小错误");
	
	/*-----------------------------------------------
	 *                      系统错误
	 *---------------------------------------------*/
	
	public static Result UNEXCEPTED_ERROR = new RedeemResult(
			false, "UNEXCEPTED_ERROR", "兑换时候有问题啊~");
	
	public static Result PERSIST_ERROR = new RedeemResult(
			false, "PERSIST_ERROR", "系统撑不住啦，请等等吧~");
	
	public static Result HTTP_CONNECT_ERROR = new RedeemResult(
			false, "HTTP_CONNECT_ERROR", "发送请求失败，稍后再试试吧~");
	
	public static Result AUTO_REGISTER_ERROR = new RedeemResult(
			false, "AUTO_REGISTER_ERROR", "完善下注册信息吧~");
}
