//package org.sz.mbay.wallet.user.bean;
//
//import org.apache.ibatis.type.Alias;
//import org.joda.time.DateTime;
//import org.sz.mbay.base.enums.Gender;
//import org.sz.mbay.base.model.BaseEntityModel;
//
///**
// * 账户
// * 
// * @author jerry
// */
//@Alias("WalletUser")
//public class User extends BaseEntityModel {
//	
//	private static final long serialVersionUID = -7640327070308318086L;
//	
//	// 用户编号
//	private String userNumber;
//	
//	// 真实姓名
//	private String realName;
//	
//	// 昵称
//	private String nickName;
//	
//	// 性别
//	private Gender gender;
//	
//	// 地址
//	private String address;
//	
//	// 用户名/手机号
//	private String telephone;
//	
//	// 登陆密码
//	private String password;
//	
//	// 支付密码
//	private String payPassword;
//	
//	// 开户时间
//	private DateTime createTime;
//	
//	// 头像路径
//	private String portrait;
//	
//	// 主账户
//	private MbayAccount mbayAccount;
//	
//	// 赞数量
//	private Integer likeNum;
//	
//	// 注册来源
//	private String regSource;
//	
//	public String getRealName() {
//		return realName;
//	}
//	
//	public void setRealName(String realName) {
//		this.realName = realName;
//	}
//	
//	public String getNickName() {
//		return nickName;
//	}
//	
//	public void setNickName(String nickName) {
//		this.nickName = nickName;
//	}
//	
//	public Gender getGender() {
//		return gender;
//	}
//	
//	public void setGender(Gender gender) {
//		this.gender = gender;
//	}
//	
//	public String getTelephone() {
//		return telephone;
//	}
//	
//	public void setTelephone(String telephone) {
//		this.telephone = telephone;
//	}
//	
//	public String getPassword() {
//		return password;
//	}
//	
//	public void setPassword(String password) {
//		this.password = password;
//	}
//	
//	public DateTime getCreateTime() {
//		return createTime;
//	}
//	
//	public void setCreateTime(DateTime createTime) {
//		this.createTime = createTime;
//	}
//	
//	public String getPortrait() {
//		return portrait;
//	}
//	
//	public void setPortrait(String portrait) {
//		this.portrait = portrait;
//	}
//	
//	public MbayAccount getMbayAccount() {
//		return mbayAccount;
//	}
//	
//	public void setMbayAccount(MbayAccount mbayAccount) {
//		this.mbayAccount = mbayAccount;
//	}
//	
//	public String getPayPassword() {
//		return payPassword;
//	}
//	
//	public void setPayPassword(String payPassword) {
//		this.payPassword = payPassword;
//	}
//	
//	public Integer getLikeNum() {
//		return likeNum;
//	}
//	
//	public void setLikeNum(Integer likeNum) {
//		this.likeNum = likeNum;
//	}
//	
//	public String getUserNumber() {
//		return userNumber;
//	}
//	
//	public void setUserNumber(String userNumber) {
//		this.userNumber = userNumber;
//	}
//	
//	public String getAddress() {
//		return address;
//	}
//	
//	public void setAddress(String address) {
//		this.address = address;
//	}
//	
//	public String getRegSource() {
//		return regSource;
//	}
//	
//	public void setRegSource(String regSource) {
//		this.regSource = regSource;
//	}
//	
//}
