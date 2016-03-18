package org.sz.mbay.trafficred.dto;

import java.math.BigDecimal;

import org.sz.mbay.base.enums.Gender;

/**
 * 用户数据传输对象
 * 
 * @author jerry
 */
public class UserDTO {
	
	// 用户编号
	private String userNumber;
	
	// 手机号
	private String telephone;
	
	// 昵称
	private String nickName;
	
	// 主账户余额
	private BigDecimal mbayBalance;
	
	// 红包账户余额
	private BigDecimal redBagBalance;
	
	// 赞数量
	private Integer likeNum;
	
	// 性别
	private Gender gender;
	
	// 头像
	private String portrait;
	
	// 地址
	private String address;
	
	// 支付密码是否已设置
	private Boolean payPasswdSetted;
	
	// 可用券包数量
	private Integer usableTicketNums;
	
	// 密码是否已设置
	private Boolean passwdSetted;
	
	public String getTelephone() {
		return telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public String getNickName() {
		return nickName;
	}
	
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public BigDecimal getMbayBalance() {
		return mbayBalance;
	}
	
	public void setMbayBalance(BigDecimal mbayBalance) {
		this.mbayBalance = mbayBalance;
	}
	
	public BigDecimal getRedBagBalance() {
		return redBagBalance;
	}
	
	public void setRedBagBalance(BigDecimal redBagBalance) {
		this.redBagBalance = redBagBalance;
	}
	
	public Integer getLikeNum() {
		return likeNum;
	}
	
	public void setLikeNum(Integer likeNum) {
		this.likeNum = likeNum;
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public String getPortrait() {
		return portrait;
	}
	
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getUserNumber() {
		return userNumber;
	}
	
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	
	public Boolean getPayPasswdSetted() {
		return payPasswdSetted;
	}
	
	public void setPayPasswdSetted(Boolean payPasswdSetted) {
		this.payPasswdSetted = payPasswdSetted;
	}
	
	public Integer getUsableTicketNums() {
		return usableTicketNums;
	}
	
	public void setUsableTicketNums(Integer usableTicketNums) {
		this.usableTicketNums = usableTicketNums;
	}
	
	public Boolean getPasswdSetted() {
		return passwdSetted;
	}
	
	public void setPasswdSetted(Boolean passwdSetted) {
		this.passwdSetted = passwdSetted;
	}
	
}
