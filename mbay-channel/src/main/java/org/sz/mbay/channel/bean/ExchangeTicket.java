package org.sz.mbay.channel.bean;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.sz.mbay.channel.enums.TicketType;

/**
 * 美贝钱包 - 券包
 * 
 * @author jerry
 */
public class ExchangeTicket implements Serializable {
	
	private static final long serialVersionUID = -4938527408627454126L;
	
	// 主键
	private Integer id;
	
	// 券包种类
	private TicketType ticketType;
	
	// 活动编号
	private String eventNumber;
	
	// 流量订单编号
	private String orderNumber;
	
	// 券包拥有者号码
	private String ownerNumber;
	
	// 活动链接
	private String link;
	
	// 商家图片
	private byte[] image;
	
	// 商家名称
	private String enterpriseName;
	
	// 是否已使用，0-否，1-是
	private boolean used;
	
	// 创建时间
	private DateTime createTime;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getEventNumber() {
		return eventNumber;
	}
	
	public void setEventNumber(String eventNumber) {
		this.eventNumber = eventNumber;
	}
	
	public String getOrderNumber() {
		return orderNumber;
	}
	
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public byte[] getImage() {
		return image;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
	}
	
	public String getEnterpriseName() {
		return enterpriseName;
	}
	
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	
	public boolean isUsed() {
		return used;
	}
	
	public void setUsed(boolean used) {
		this.used = used;
	}
	
	public String getOwnerNumber() {
		return ownerNumber;
	}
	
	public void setOwnerNumber(String ownerNumber) {
		this.ownerNumber = ownerNumber;
	}
	
	public DateTime getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}
	
	public TicketType getTicketType() {
		return ticketType;
	}
	
	public void setTicketType(TicketType ticketType) {
		this.ticketType = ticketType;
	}

}
