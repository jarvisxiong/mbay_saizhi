package org.sz.mbay.channel.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import org.sz.mbay.base.area.Area;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.operator.enums.TrafficType;

@SuppressWarnings("serial")
public class Strategy implements Serializable {
	
	/* 渠道商id */
	private long userid;
	
	/* 主键* */
	private int id;
	
	/* 策略名称* */
	private String strategyname;
	
	/* 安全码 */
	private String securitycode;
	
	/* 覆盖地区* */
	private Area area;
	
	/* 运营商 */
	private OperatorType teloperator;
	
	/* 赠送方式** */
	
	/* 活动开始日期** */
	private Date starttime;
	
	/* 活动结束日期** */
	private Date endtime;
	
	/* 生成时间* */
	private Timestamp createtime;
	
	/* 已送出个数 */
	private int sended;
	
	/* 流量单价 */
	private double unitmb;
	
	/* 获取兑换码连接** */
	public String redeemcodeurl;
	
	/* 有效期** */
	private int validityday;
	
	/* 每个用户赠送数量* */
	private int sendnum;
	
	/* 总共个数 */
	private int quantity;
	
	/* 此策略总花费美贝* */
	private double mbayamount;
	
	/* 美贝余额* */
	private double mbaybalance;
	
	/* 赠送策略 */
	private int strategytype;
	
	/* 流量类型(1.国内流量 2.省内流量) */
	public TrafficType traffictype;
	
	public double getMbayamount() {
		return mbayamount;
	}
	
	public void setMbayamount(double mbayamount) {
		this.mbayamount = mbayamount;
	}
	
	public double getMbaybalance() {
		return mbaybalance;
	}
	
	public void setMbaybalance(double mbaybalance) {
		this.mbaybalance = mbaybalance;
	}
	
	public int getStrategytype() {
		return strategytype;
	}
	
	public void setStrategytype(int strategytype) {
		this.strategytype = strategytype;
	}
	
	public String getRedeemcodeurl() {
		return redeemcodeurl;
	}
	
	public void setRedeemcodeurl(String redeemcodeurl) {
		this.redeemcodeurl = redeemcodeurl;
	}
	
	public double getUnitmb() {
		return unitmb;
	}
	
	public void setUnitmb(double unitmb) {
		this.unitmb = unitmb;
	}
	
	public int getSended() {
		return sended;
	}
	
	public void setSended(int sended) {
		this.sended = sended;
	}
	
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	public TrafficType getTraffictype() {
		return traffictype;
	}
	
	public void setTraffictype(TrafficType traffictype) {
		this.traffictype = traffictype;
	}
	
	public long getUserid() {
		return userid;
	}
	
	public void setUserid(long userid) {
		this.userid = userid;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getStrategyname() {
		return strategyname;
	}
	
	public void setStrategyname(String strategyname) {
		this.strategyname = strategyname;
	}
	
	public String getSecuritycode() {
		return securitycode;
	}
	
	public void setSecuritycode(String securitycode) {
		this.securitycode = securitycode;
	}
	
	public Area getArea() {
		return area;
	}
	
	public void setArea(Area area) {
		this.area = area;
	}
	
	public OperatorType getTeloperator() {
		return teloperator;
	}
	
	public void setTeloperator(OperatorType teloperator) {
		this.teloperator = teloperator;
	}
	
	public Date getStarttime() {
		return starttime;
	}
	
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	
	public Date getEndtime() {
		return endtime;
	}
	
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	
	public int getValidityday() {
		return validityday;
	}
	
	public void setValidityday(int validityday) {
		this.validityday = validityday;
	}
	
	public int getSendnum() {
		return sendnum;
	}
	
	public void setSendnum(int sendnum) {
		this.sendnum = sendnum;
	}
	
}
