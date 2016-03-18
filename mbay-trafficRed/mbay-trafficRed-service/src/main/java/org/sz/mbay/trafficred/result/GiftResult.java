package org.sz.mbay.trafficred.result;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.sz.mbay.base.wrap.Response;
import org.sz.mbay.base.wrap.ResponseFail;

public class GiftResult extends Result {
	
	private static final Map<String, Result> values = new HashMap<>();
	
	public static Result valueOf(String code) {
		return values.get(code);
	}
	
	public GiftResult(boolean status, String code, String content) {
		super(status, code, content);
	}
	
	public static GiftResult create(boolean status, String code,
			String content) {
		return new GiftResult(status, code, content);
	}
	
	public static GiftResult create(boolean status, String content) {
		return new GiftResult(status, null, content);
	}
	
	public static Result valueOf(Response resp) {
		if (resp instanceof ResponseFail) {
			return create(false, ((ResponseFail) resp).getErrorCode(),
					((ResponseFail) resp).getErrorMsg());
		} else {
			return resp.getStatus() ? SUCCESS : FAIL;
		}
	}
	
	/*---------------------------------------------------------------
	 *                          活动状态描述
	 *-------------------------------------------------------------*/
	
	public static Result SUCCESS = new GiftResult(true, "SUCCESS", "成功");
	
	public static Result FAIL = new GiftResult(false, "FAIL", "失败");
	
	public static Result MBAY_GIFT_CONFIG_NOT_EXIST = new GiftResult(
			false, "MBAY_GIFT_CONFIG_NOT_EXIST", "没有找到送人配置信息~~");
			
	public static Result TRADE_RECORD_NUMBER_ERROR = new GiftResult(
			false, "TRADE_RECORD_NUMBER_ERROR", "咦？这个交易号不对哦");
			
	public static Result MBAY_GIFT_SHARE_REPEAT = new GiftResult(
			false, "MBAY_GIFT_SHARE_REPEAT", "这份MB您已送出去啦~");
			
	public static Result MBAY_GIFT_RECIEVERD = new GiftResult(
			false, "MBAY_GIFT_RECIEVERD", "您来晚啦，流量已经被其他人领走了/(ㄒoㄒ)/~~");
			
	public static Result MBAY_GIFT_RECIEVERD_YOURSELF = new GiftResult(
			false, "MBAY_GIFT_RECIEVERD_YOURSELF", "该流量您此前已经领取过啦~");
			
	public static Result MBAY_GIFT_NOT_EXIST = new GiftResult(
			false, "MBAY_GIFT_NOT_EXIST", "咦？这个领取码不对哦");
			
	public static Result MBAY_GIFT_LINK_EXPIRED = new GiftResult(
			false, "MBAY_GIFT_LINK_EXPIRED", "链接已过期");
			
	public static Result TIMES_EXCEED_DAY = new GiftResult(
			false, "TIMES_EXCEED_DAY", "亲，您今天的领取次数已经用完啦！");
			
	public static Result TIMES_EXCEED_WEEK = new GiftResult(
			false, "TIMES_EXCEED_WEEK", "亲，您本周的领取次数已经用完啊！");
			
	public static Result TIMES_EXCEED_MONTH = new GiftResult(
			false, "TIMES_EXCEED_MONTH", "亲，您本月的领取次数已经用完啦！");
			
	public static Result SELF_RECIEVE_FORBID = new GiftResult(
			false, "SELF_RECIEVE_FORBID", "这份流量是您送出去的哟~");
			
	public static Result BALANCE_INSUFFICIENT = new GiftResult(
			false, "BALANCE_INSUFFICIENT", "账户余额不足");
			
	/*
	 * 加载所有状态描述到Map中，方便根据状态码找到对象
	 * 注：该静态块必须放到状态描述下面
	 */
	static {
		Result res;
		for (Field f : GiftResult.class.getDeclaredFields()) {
			if (Modifier.isStatic(f.getModifiers())
					&& Modifier.isPublic(f.getModifiers())) {
				try {
					res = (Result) f.get(GiftResult.class);
					values.put(res.getCode(), res);
				} catch (Exception e) {
				}
			}
		}
	}
}
