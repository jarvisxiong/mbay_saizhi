package org.sz.mbay.wechat.bean;

import java.io.Serializable;

import org.sz.mbay.base.area.Area;
import org.sz.mbay.base.enums.StrategyState;
import org.sz.mbay.operator.bean.TrafficPackage;
import org.sz.mbay.operator.enums.OperatorType;

/**
* @Description: 微信伴侣-活动策略
* @author frank.zong
* @date 2015-1-13 下午3:57:24 
*
 */
@SuppressWarnings("serial")
public class WeChatCampaignStrategy implements Serializable {
	/**
	 * 活动标记
	 */
	private String strategynumber;
	
	/**
	 * 活动编号
	 */
	private String eventnumber;
	/**
	 * 地区
	 */
	private Area supportArea;
	
	/**
	 * 运营商
	 */
	private OperatorType supportOperator;
		
	/**
	 * 流量包
	 */
	private TrafficPackage trafficPackage;
	
	/**
	 * 已送出数量
	 */
	private int sendednum;
	/**
	 * 启用， 禁用
	 */
	private StrategyState state;

	public String getEventnumber() {
		return eventnumber;
	}

	public void setEventnumber(String eventnumber) {
		this.eventnumber = eventnumber;
	}

	public TrafficPackage getTrafficPackage() {
		return trafficPackage;
	}

	public void setTrafficPackage(TrafficPackage trafficPackage) {
		this.trafficPackage = trafficPackage;
	}

	public int getSendednum() {
		return sendednum;
	}

	public void setSendednum(int sendednum) {
		this.sendednum = sendednum;
	}

	public StrategyState getState() {
		return state;
	}

	public void setState(StrategyState state) {
		this.state = state;
	}

	public String getStrategynumber() {
		return strategynumber;
	}

	public void setStrategynumber(String strategynumber) {
		this.strategynumber = strategynumber;
	}

	public Area getSupportArea() {
		return supportArea;
	}

	public void setSupportArea(Area supportArea) {
		this.supportArea = supportArea;
	}

	public OperatorType getSupportOperator() {
		return supportOperator;
	}

	public void setSupportOperator(OperatorType supportOperator) {
		this.supportOperator = supportOperator;
	}
}