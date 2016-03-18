package org.sz.mbay.channel.bean;

import java.io.Serializable;

import org.sz.mbay.base.qo.BaseForm;
import org.sz.mbay.channel.enums.RemittanceStatus;

/**
 * 汇款记录实体表
 * @author Frank
 *
 */
@SuppressWarnings("serial")
public class RemittanceRecord extends BaseForm implements Serializable{
	
	private int id; //id
    private String usernumber; //用户编号
    private String price; //汇款金额
    private String time; //汇款时间
    private String description; //备注
    private AssetBank bank; //银行
    private RemittanceStatus status; //状态
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUsernumber() {
		return usernumber;
	}
	public void setUsernumber(String usernumber) {
		this.usernumber = usernumber;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public AssetBank getBank() {
		return bank;
	}
	public void setBank(AssetBank bank) {
		this.bank = bank;
	}
	public RemittanceStatus getStatus() {
		return status;
	}
	public void setStatus(RemittanceStatus status) {
		this.status = status;
	}
}