package org.sz.mbay.channel.user.bean;

import java.sql.Timestamp;

import org.sz.mbay.base.enums.Gender;
import org.sz.mbay.base.model.BaseEntityModel;
import org.sz.mbay.channel.user.enums.CertStatus;

/**
 * 个体商户证件信息
 * 
 * @author ONECITY
 * 
 */
public class Certificate extends BaseEntityModel {
	
	/* 用户ID */
	
	private static final long serialVersionUID = 1210191253416817530L;
	
	private long userid;
	
	/* 证件号码 */
	private String identityNo;
	
	/* 真实姓名 */
	private String true_name;
	
	/* 性别 */
	private Gender gender;
	
	/* email */
	private String email;
	
	private String telephone;
	/* 所在省 */
	private String province;
	
	/* 所在市区 */
	private String city;
	/* 所在区县 */
	private String area;
	/* 详细地址 */
	private String address;
	/* 证件类型 */
	private String IDType;
	
	public String getIDType() {
		return IDType;
	}
	
	public void setIDType(String iDType) {
		IDType = iDType;
	}
	
	/**
	 * 证件正面
	 */
	private String frontimgurl;
	
	/**
	 * 证件反面
	 */
	private String backimgurl;
	
	/**
	 * 申请时间
	 */
	private Timestamp createtime;
	/** 认证状态 **/
	private CertStatus authstate;
	
	/** 认证信息 ***/
	private String authinfo;
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public CertStatus getAuthstate() {
		return authstate;
	}
	
	public void setAuthstate(CertStatus authstate) {
		this.authstate = authstate;
	}
	
	public String getAuthinfo() {
		return authinfo;
	}
	
	public void setAuthinfo(String authinfo) {
		this.authinfo = authinfo;
	}
	
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	public String getFrontimgurl() {
		return frontimgurl;
	}
	
	public void setFrontimgurl(String frontimgurl) {
		this.frontimgurl = frontimgurl;
	}
	
	public String getBackimgurl() {
		return backimgurl;
	}
	
	public void setBackimgurl(String backimgurl) {
		this.backimgurl = backimgurl;
	}
	
	public String getIdentityNo() {
		return identityNo;
	}
	
	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}
	
	public String getTrue_name() {
		return true_name;
	}
	
	public void setTrue_name(String true_name) {
		this.true_name = true_name;
	}
	
	public long getUserid() {
		return userid;
	}
	
	public void setUserid(long userid) {
		this.userid = userid;
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTelephone() {
		return telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public String getProvince() {
		return province;
	}
	
	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getArea() {
		return area;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	
}
