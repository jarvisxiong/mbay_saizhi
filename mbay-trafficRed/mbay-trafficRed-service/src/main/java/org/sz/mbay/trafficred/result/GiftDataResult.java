package org.sz.mbay.trafficred.result;

public class GiftDataResult extends Result {
	
	private Object data;
	
	public static Result create(Object data) {
		return create(data, (GiftResult) GiftResult.SUCCESS);
	}
	
	public static Result create(Object data, GiftResult result) {
		GiftDataResult res = new GiftDataResult();
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
