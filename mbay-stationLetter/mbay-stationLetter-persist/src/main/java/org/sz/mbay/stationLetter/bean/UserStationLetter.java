package org.sz.mbay.stationLetter.bean;

import java.io.Serializable;

/**
 * 用户与站内信关联表
 * 
 * @author frank.zong
 * 
 */
public class UserStationLetter implements Serializable {
	
	private static final long serialVersionUID = -9127909920938361975L;
	
	private int id;
	
	/* 发件人编号 */
	private String senderUnumber;
	
	/* 收件人编号 */
	private String receverUnumber;
	
	/* 发件箱状态0--普通；1--删除 */
	private int sendStatus;
	
	/* 收件箱状态0--普通；1--删除 */
	private int receveStatus;
	
	/* 信息阅读状态0--未读；1--已读 */
	private int readStatus;
	
	/* 关联对应的站内信* */
	private StationLetter message;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getSenderUnumber() {
		return senderUnumber;
	}
	
	public void setSenderUnumber(String senderUnumber) {
		this.senderUnumber = senderUnumber;
	}
	
	public String getReceverUnumber() {
		return receverUnumber;
	}
	
	public void setReceverUnumber(String receverUnumber) {
		this.receverUnumber = receverUnumber;
	}
	
	public int getSendStatus() {
		return sendStatus;
	}
	
	public void setSendStatus(int sendStatus) {
		this.sendStatus = sendStatus;
	}
	
	public int getReceveStatus() {
		return receveStatus;
	}
	
	public void setReceveStatus(int receveStatus) {
		this.receveStatus = receveStatus;
	}
	
	public int getReadStatus() {
		return readStatus;
	}
	
	public void setReadStatus(int readStatus) {
		this.readStatus = readStatus;
	}
	
	public StationLetter getMessage() {
		return message;
	}
	
	public void setMessage(StationLetter message) {
		this.message = message;
	}
	
}
