package org.sz.mbay.traffic.response;


/**
 * @author ONECITY
 *
 */
public class TrafficResponse {
	/**系统异常**/
	public static final TrafficResponse SYSTEM_ERROR=new TrafficResponse(0);
	/**充值成功**/
	public static final TrafficResponse RECHARGE_SUCCESS=new TrafficResponse(1);
	/**充值中**/
	public static final TrafficResponse RECHARGING=new TrafficResponse(2);
	/**不存在的订单***/
	public static final TrafficResponse NONE_EXIST_ORDER=new TrafficResponse(3);
	/**不支持的号码**/
	public static final TrafficResponse NONE_SUPPORT_MOBILE=new TrafficResponse(4);
	/**未开通的通信运营商***/
	public static final TrafficResponse NONE_SUPPORT_OPERATOR=new TrafficResponse(5);
	
	public static final TrafficResponse MOBILE_ERROR=new TrafficResponse(5);
	
	public static final TrafficResponse PACKAGER_ERROR=new TrafficResponse(5);
	
	private TrafficResponse(){}
	private TrafficResponse(int code){
		this.code=code;
	}
	public int code;

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}

}
