package org.sz.mbay.channel.bean;

import java.io.Serializable;

/**
 * 汇款账户实体
 * @author Frank
 *
 */
@SuppressWarnings("serial")
public class AssetBank implements Serializable{
	/*id*/
	private int id; 
	/*创建时间*/
    private String createTime; 
    /*银行名称*/
    private String bankName; 
    /*公司名称*/
    private String companyName; 
    /*账户号*/
    private String account; 
    /*汇率描述*/
    private String description; 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}