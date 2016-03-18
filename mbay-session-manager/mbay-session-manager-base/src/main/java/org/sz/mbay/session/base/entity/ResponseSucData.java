package org.sz.mbay.session.base.entity;

/**
 * 成功数据
 * 
 * @author jerry
 */
public class ResponseSucData extends ResponseData {
	
	private static final long serialVersionUID = 2971929040669437917L;
	
	// 成功时的返回数据，必须保证所有属性可被json解析
	private Object data;
	
	public ResponseSucData() {
	}
	
	public ResponseSucData(Object data) {
		super(true);
		this.data = data;
	}
	
	/**
	 * 操作成功 + 返回的数据
	 * 
	 * @param status
	 * @param data
	 */
	public static ResponseData create(Object data) {
		return new ResponseSucData(data);
	}
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
