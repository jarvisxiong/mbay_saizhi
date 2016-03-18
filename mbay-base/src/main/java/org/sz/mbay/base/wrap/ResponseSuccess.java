package org.sz.mbay.base.wrap;

import org.sz.mbay.base.pagehelper.PageInfo;

/**
 * 成功数据
 * 
 * @author jerry
 */
public class ResponseSuccess extends Response {
	
	private static final long serialVersionUID = 2971929040669437917L;
	
	// 成功时的返回数据，必须保证所有属性可被json解析
	private Object data;
	
	// 分页信息
	private PageInfo pageInfo;
	
	public ResponseSuccess() {}
	
	public ResponseSuccess(Object data, PageInfo pageInfo) {
		super(true);
		this.data = data;
		this.pageInfo = pageInfo;
	}
	
	/**
	 * 操作成功 + 返回的数据
	 * 
	 * @param status
	 * @param data
	 */
	public static Response create(Object data) {
		return new ResponseSuccess(data, null);
	}
	
	/**
	 * 操作成功 + 返回的数据 + 分页
	 * 
	 * @param data
	 * @param pageInfo
	 * @return
	 */
	public static Response create(Object data, PageInfo pageInfo) {
		return new ResponseSuccess(data, pageInfo);
	}
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
	public PageInfo getPageInfo() {
		return pageInfo;
	}
	
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
