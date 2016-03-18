package org.sz.mbay.trafficred.result;

public class RedeemDataResult extends Result {
	
	private Object data;
	
	public static Result create(Object data) {
		return create(data, (RedeemResult) RedeemResult.SUCCESS);
	}
	
	public static Result create(Object data, RedeemResult result) {
		RedeemDataResult res = new RedeemDataResult();
		res.setCode(result.getCode());
		res.setStatus(result.getStatus());
		res.setContent(result.getContent());
		res.setData(data);
		return res;
	}
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
}
