package org.sz.mbay.channel.util;

/**
 * @Description: 接口管理-返回结果
 * @author frank.zong
 * @date 2014-11-3 下午18:14:40
 * 
 */
public class Result {

	public static final Result SUCCESS = new Result("成功", "000000");
	
	public static final Result ERROR_URL = new Result("请求格式不正确", "100001");
	public static final Result ERROR_SIGN_NOTSIGN = new Result("签约还未申请", "100002");
	public static final Result ERROR_SIGN_DISENABLED = new Result("此签约已被禁用", "100003");
	public static final Result ERROR_SIGN_NOTAUDIT = new Result("签约还未审核", "100004");
	public static final Result ERROR_SIGN_FAIL = new Result("签约审核不通过", "100005");

	public static final Result ERROR_PID = new Result("密钥验证失败", "200001");
	
	public static final Result ERROR_PACKAGE = new Result("没有这种流量包", "300001");
	public static final Result ERROR_PACKAGE_DISENABLED = new Result("此流量包已被禁用", "300002");
	
	public static final Result ERROR_PACKAGE_NOTENOUGH = new Result("该账户美贝余额不够订购这种流量包", "400001");
	
	public static final Result ERROR_PACKAGE_FAIL = new Result("该手机号当月已经订购过此流量包", "500001");
	
	public static final Result ERROR_ORDER_FAIL = new Result("流量直充订单创建失败", "600001");
	
	private String message;
	private String code;

	public Result(String message, String code) {
		this.message = message;
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}