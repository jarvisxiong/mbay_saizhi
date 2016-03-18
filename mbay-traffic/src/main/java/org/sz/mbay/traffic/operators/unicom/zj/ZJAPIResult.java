package org.sz.mbay.traffic.operators.unicom.zj;

/**
 * @author ONECITY
 *浙江接口返回结果
 */
public class ZJAPIResult {
	
	public ZJAPIResult(){
	}
	public static  ZJAPIResult connectionTimeOut(){
		ZJAPIResult result=new ZJAPIResult(false);
		result.code=CONNECTIONTIMEOUT;
		return result;
	}
	public static  ZJAPIResult readeTimeOut(){
		ZJAPIResult result=new ZJAPIResult(false);
		result.code=READTIMEOUT;
		return result;
	}
	
	public ZJAPIResult(boolean success){
		this.success=success;
	}
	public static final int CONNECTIONTIMEOUT = 0;

	public static final int READTIMEOUT = 1;

	public boolean success;

	public int code;

	public String message;
	
	public ZJResult zjresult;

	public  class ZJResult {
		public String status;
		public String code;
		public String desc;
	}

}
