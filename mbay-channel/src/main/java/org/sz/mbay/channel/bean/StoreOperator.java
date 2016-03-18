package org.sz.mbay.channel.bean;

import org.sz.mbay.base.enums.EnableState;

/**  门店操作员
 * 
 * @author Fenlon
 * 
 */
public class StoreOperator extends BaseEntityModel {
	
	private static final long serialVersionUID = -8894721591484026125L;
	/** 门店编号 **/
	private long storeId;
	/** 操作员授权码 **/
	private String authCode;
	/** 操作员手机号 **/
	private String cellphone;
	/** 操作员状态 (禁用，启用) **/
	private EnableState status;
	/** 操作员密码 **/
	private String password;
	/** 操作员是否被删除 **/
	private boolean deleted;
	
	public String getAuthCode() {
		return authCode;
	}
	
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	
	public String getCellphone() {
		return cellphone;
	}
	
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public EnableState getStatus() {
		return status;
	}
	
	public void setStatus(EnableState status) {
		this.status = status;
	}
	
	public long getStoreId() {
		return storeId;
	}
	
	public void setStoreId(long storeId) {
		this.storeId = storeId;
	}
}
