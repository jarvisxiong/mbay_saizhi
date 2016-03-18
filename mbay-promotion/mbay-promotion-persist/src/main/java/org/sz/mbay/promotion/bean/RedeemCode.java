package org.sz.mbay.promotion.bean;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.sz.mbay.base.enums.EventType;
import org.sz.mbay.promotion.enums.CodeType;
import org.sz.mbay.promotion.enums.RedeemCodeMethod;
import org.sz.mbay.promotion.enums.RedeemCodeStu;
import org.sz.mbay.trafficorder.bean.TrafficOrder;

/**
 * 兑换 码 表
 * 
 * @author meibei-hrain
 * 		
 */
public class RedeemCode implements Serializable {
	
	/* */
	private static final long serialVersionUID = -6797628156855673680L;
	private long id;
	/* 兑换码 * */
	private String code;
	/* 开始时间 * */
	private DateTime starttime;
	/* 过期时间 */
	private DateTime expiretime;
	/* 生成时间 */
	private DateTime createtime;
	/* 活动编号 */
	private String eventnumber;
	/* 活动类别 */
	private EventType eventType;
	/* 兑换码类被 */
	private CodeType codeType;
	/* 兑换码状态 */
	private RedeemCodeStu codeStatus;
	/* 对应的单号 */
	private String ordernumber;
	/* 关联的原兑换单号 */
	private String involvedNumber;
	/* 绑定手机号 */
	private String bindMobile;
	/* 生成方式 */
	private RedeemCodeMethod method;
	/* 订单信息 */
	private TrafficOrder trafficOrder;
	/* 核销码 */
	private String verificateCode;
	/* 领取时间 */
	private DateTime gettime;
	/* 兑换时间 */
	private DateTime redeemtime;
	/* 产品大小 */
	private double mbayprice;
	/* 是否赠送过mb */
	private boolean send;
	
	public boolean isSend() {
		return send;
	}
	
	public void setSend(boolean send) {
		this.send = send;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public RedeemCodeStu getCodeStatus() {
		return codeStatus;
	}
	
	public void setCodeStatus(RedeemCodeStu codeStatus) {
		this.codeStatus = codeStatus;
	}
	
	public String getEventnumber() {
		return eventnumber;
	}
	
	public void setEventnumber(String eventnumber) {
		this.eventnumber = eventnumber;
	}
	
	public EventType getEventType() {
		return eventType;
	}
	
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
	
	public CodeType getCodeType() {
		return codeType;
	}
	
	public void setCodeType(CodeType codeType) {
		this.codeType = codeType;
	}
	
	public String getOrdernumber() {
		return ordernumber;
	}
	
	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}
	
	public String getInvolvedNumber() {
		return involvedNumber;
	}
	
	public void setInvolvedNumber(String involvedNumber) {
		this.involvedNumber = involvedNumber;
	}
	
	public DateTime getStarttime() {
		return starttime;
	}
	
	public void setStarttime(DateTime starttime) {
		this.starttime = starttime;
	}
	
	public DateTime getExpiretime() {
		return expiretime;
	}
	
	public void setExpiretime(DateTime expiretime) {
		this.expiretime = expiretime;
	}
	
	public DateTime getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(DateTime createtime) {
		this.createtime = createtime;
	}
	
	public String getBindMobile() {
		return bindMobile;
	}
	
	public void setBindMobile(String bindMobile) {
		this.bindMobile = bindMobile;
	}
	
	public RedeemCodeMethod getMethod() {
		return method;
	}
	
	public void setMethod(RedeemCodeMethod method) {
		this.method = method;
	}
	
	public TrafficOrder getTrafficOrder() {
		return trafficOrder;
	}
	
	public void setTrafficOrder(TrafficOrder trafficOrder) {
		this.trafficOrder = trafficOrder;
	}
	
	public String getVerificateCode() {
		return verificateCode;
	}
	
	public void setVerificateCode(String verificateCode) {
		this.verificateCode = verificateCode;
	}
	
	public DateTime getGettime() {
		return gettime;
	}
	
	public void setGettime(DateTime gettime) {
		this.gettime = gettime;
	}
	
	public DateTime getRedeemtime() {
		return redeemtime;
	}
	
	public void setRedeemtime(DateTime redeemtime) {
		this.redeemtime = redeemtime;
	}
	
	public double getMbayprice() {
		return mbayprice;
	}
	
	public void setMbayprice(double mbayprice) {
		this.mbayprice = mbayprice;
	}
	
}
