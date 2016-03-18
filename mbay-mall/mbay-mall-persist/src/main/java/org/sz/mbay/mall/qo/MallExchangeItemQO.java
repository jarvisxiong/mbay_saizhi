package org.sz.mbay.mall.qo;

import java.io.Serializable;

import org.sz.mbay.duiba.bean.DuiBaMall;
import org.sz.mbay.mall.enums.MallAudit;
import org.sz.mbay.mall.enums.MallItemType;
import org.sz.mbay.mall.enums.MallStatus;

/**
 * 查询条件
 * 
 * @author frank.zong
 * 
 */
@SuppressWarnings("serial")
public class MallExchangeItemQO implements Serializable {
	
	/** 所属商城 ***/
	private DuiBaMall mall;
	
	/** 商品类型 ***/
	private MallItemType type;
	
	/** 状态 **/
	private MallStatus status;
	
	/** 兑换项标题 **/
	private String title;
	
	/** 用户编号 **/
	private String userNumber;
	
	/** 兑换项审核 **/
	private MallAudit audit;
	
	/** 兑换码编号 **/
	private String itemNumber;
	
	public DuiBaMall getMall() {
		return mall;
	}
	
	public void setMall(DuiBaMall mall) {
		this.mall = mall;
	}
	
	public MallItemType getType() {
		return type;
	}
	
	public void setType(MallItemType type) {
		this.type = type;
	}
	
	public MallStatus getStatus() {
		return status;
	}
	
	public void setStatus(MallStatus status) {
		this.status = status;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getUserNumber() {
		return userNumber;
	}
	
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	
	public MallAudit getAudit() {
		return audit;
	}
	
	public void setAudit(MallAudit audit) {
		this.audit = audit;
	}
	
	public String getItemNumber() {
		return itemNumber;
	}
	
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	
}
