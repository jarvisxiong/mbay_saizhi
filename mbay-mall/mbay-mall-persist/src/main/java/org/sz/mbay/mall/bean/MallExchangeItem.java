package org.sz.mbay.mall.bean;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;
import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.duiba.bean.DuiBaMall;
import org.sz.mbay.mall.enums.MallAudit;
import org.sz.mbay.mall.enums.MallItemType;
import org.sz.mbay.mall.enums.MallStatus;
import org.sz.mbay.mall.enums.MallUserLimitType;

/**
 * 商城兑换项
 * 
 * @author frank.zong
 */
public class MallExchangeItem implements Serializable {
	
	private static final long serialVersionUID = 6105393127609343558L;
	
	/** 兑换码编号 **/
	private String itemNumber;
	
	/** 用户名 **/
	private String userNumber;
	
	/** 创建时间 **/
	private DateTime createTime;
	
	/** 商品标题 **/
	@NotBlank
	@Size(min = 1, max = 16)
	private String title;
	
	/** 说明文字 **/
	@NotBlank
	@Size(min = 1, max = 32)
	private String description;
	
	/** 规则名称 **/
	@NotBlank
	@Size(min = 1, max = 16)
	private String ruleName;
	
	/** 规则内容 **/
	@NotBlank
	@Size(min = 1, max = 32)
	private String ruleContent;
	
	/** 说明名称 **/
	@NotBlank
	@Size(min = 1, max = 16)
	private String detailName;
	
	/** 说明内容 **/
	@NotBlank
	@Size(min = 1, max = 32)
	private String detailContent;
	
	/** 兑换成功文案 **/
	private String successDescription;
	
	/** 市面价值 **/
	private int price = 0;
	
	/** 需要美贝 **/
	private int mbay = 1;
	
	/** 用户兑换限制(每个用户最多可兑换的次数，不填则不做限制) **/
	private String userLimit;
	
	/** 用户兑换限制类型 **/
	private MallUserLimitType userLimitType;
	
	/** 兑换项审核 **/
	private MallAudit audit;
	
	/** 额外兑换限制(默认不需要额外兑换限制) **/
	private EnableState extendLimit;
	
	/** 剩余库存 **/
	private int remainder = 0;
	
	/** 商品类型 **/
	private MallItemType type;
	
	/** 上下架状态 **/
	private MallStatus status;
	
	/** 兑换项区隐藏(默认不隐藏) **/
	private EnableState hide;
	
	/** 所属商城 **/
	@NotNull
	private DuiBaMall mall;
	
	public String getItemNumber() {
		return itemNumber;
	}
	
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	
	public String getUserNumber() {
		return userNumber;
	}
	
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	
	public DateTime getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getRuleName() {
		return ruleName;
	}
	
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	
	public String getRuleContent() {
		return ruleContent;
	}
	
	public void setRuleContent(String ruleContent) {
		this.ruleContent = ruleContent;
	}
	
	public String getDetailName() {
		return detailName;
	}
	
	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}
	
	public String getDetailContent() {
		return detailContent;
	}
	
	public void setDetailContent(String detailContent) {
		this.detailContent = detailContent;
	}
	
	public String getSuccessDescription() {
		return successDescription;
	}
	
	public void setSuccessDescription(String successDescription) {
		this.successDescription = successDescription;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getMbay() {
		return mbay;
	}
	
	public void setMbay(int mbay) {
		this.mbay = mbay;
	}
	
	public int getRemainder() {
		return remainder;
	}
	
	public void setRemainder(int remainder) {
		this.remainder = remainder;
	}
	
	public String getUserLimit() {
		return userLimit;
	}
	
	public void setUserLimit(String userLimit) {
		this.userLimit = userLimit;
	}
	
	public MallUserLimitType getUserLimitType() {
		return userLimitType;
	}
	
	public void setUserLimitType(MallUserLimitType userLimitType) {
		this.userLimitType = userLimitType;
	}
	
	public MallAudit getAudit() {
		return audit;
	}
	
	public void setAudit(MallAudit audit) {
		this.audit = audit;
	}
	
	public EnableState getExtendLimit() {
		return extendLimit;
	}
	
	public void setExtendLimit(EnableState extendLimit) {
		this.extendLimit = extendLimit;
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
	
	public EnableState getHide() {
		return hide;
	}
	
	public void setHide(EnableState hide) {
		this.hide = hide;
	}
	
	public DuiBaMall getMall() {
		return mall;
	}
	
	public void setMall(DuiBaMall mall) {
		this.mall = mall;
	}
	
}
