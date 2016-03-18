package org.sz.mbay.channel.user.bean;

import java.sql.Timestamp;

import org.sz.mbay.base.enums.Gender;
import org.sz.mbay.base.model.BaseEntityModel;
import org.sz.mbay.channel.user.enums.CertStatus;
import org.sz.mbay.channel.user.enums.Department;
import org.sz.mbay.channel.user.enums.EnterpriseProperty;
import org.sz.mbay.channel.user.enums.Staff;

/**
 * 企业账户认证信息
 * 
 * @author meibei-hrain
 * 
 */
public class ChannelInfo extends BaseEntityModel {
	
	private static final long serialVersionUID = 4481801170400082808L;
	
	/** 用户ID ****/
	private long userid;
	
	/** 常用地址 */
	private String address;
	
	/** 联系电话 */
	private String phone;
	
	/** 所属行业 */
	private String businessCategory;
	
	/** 所在省 */
	private String province;
	
	/** 所在市区 */
	private String city;
	/** 所在区县 */
	private String area;
	/** 企业人数 */
	private Staff employees;
	/** 联系人姓名 */
	private String contactsname;
	/** 所在部门 */
	private Department department;
	/** 职务,头衔 */
	private String jobtitle;
	/** 固定电话 区号-电话号-分机号 */
	private String fixphone;
	
	/** 联系人邮箱 */
	private String contactsEmail;
	/** 联系人手机 */
	private String contactsphone;
	/** 联系人性别 0:男 1：女 */
	private Gender gender;
	/** 企业名称 **/
	private String enterpriseName;
	/** 企业性质 **/
	private EnterpriseProperty property;
	/** 企业网址 **/
	private String website;
	/** 营业执照编号 **/
	private String licenseNumber;
	/** 法人代表 **/
	private String legaltative;
	/** 注册资本 **/
	private int capital;
	/** 成立日期 */
	private String registerDate;
	/** 组织机构代码 */
	private String organizationCode;
	/** 税务登记证号 */
	private String taxNumber;
	
	/** 营业执照副本 */
	private String copyLicense;
	/** 税务登记证副本 */
	private String copyTax;
	/** 组织机构代码证副本 */
	private String copyCode;
	
	/**
	 * 申请时间
	 */
	private Timestamp createtime;
	/** 认证状态 **/
	private CertStatus authstate;
	
	/** 认证信息 ***/
	private String authinfo;
	
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
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
	
	public String getProvince() {
		return province;
	}
	
	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getBusinessCategory() {
		return businessCategory;
	}
	
	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}
	
	public Staff getEmployees() {
		return employees;
	}
	
	public void setEmployees(Staff employees) {
		this.employees = employees;
	}
	
	public String getFixphone() {
		return fixphone;
	}
	
	public void setFixphone(String fixphone) {
		this.fixphone = fixphone;
	}
	
	public EnterpriseProperty getProperty() {
		return property;
	}
	
	public void setProperty(EnterpriseProperty property) {
		this.property = property;
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
	
	public long getUserid() {
		return userid;
	}
	
	public void setUserid(long userid) {
		this.userid = userid;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getContactsname() {
		return contactsname;
	}
	
	public void setContactsname(String contactsname) {
		this.contactsname = contactsname;
	}
	
	public Department getDepartment() {
		return department;
	}
	
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	public String getJobtitle() {
		return jobtitle;
	}
	
	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle;
	}
	
	public String getContactsEmail() {
		return contactsEmail;
	}
	
	public void setContactsEmail(String contactsEmail) {
		this.contactsEmail = contactsEmail;
	}
	
	public String getContactsphone() {
		return contactsphone;
	}
	
	public void setContactsphone(String contactsphone) {
		this.contactsphone = contactsphone;
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public String getEnterpriseName() {
		return enterpriseName;
	}
	
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	
	public String getWebsite() {
		return website;
	}
	
	public void setWebsite(String website) {
		this.website = website;
	}
	
	public String getLicenseNumber() {
		return licenseNumber;
	}
	
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	
	public String getLegaltative() {
		return legaltative;
	}
	
	public void setLegaltative(String legaltative) {
		this.legaltative = legaltative;
	}
	
	public int getCapital() {
		return capital;
	}
	
	public void setCapital(int capital) {
		this.capital = capital;
	}
	
	public String getRegisterDate() {
		return registerDate;
	}
	
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
	
	public String getOrganizationCode() {
		return organizationCode;
	}
	
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
	
	public String getTaxNumber() {
		return taxNumber;
	}
	
	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}
	
	public String getCopyLicense() {
		return copyLicense;
	}
	
	public void setCopyLicense(String copyLicense) {
		this.copyLicense = copyLicense;
	}
	
	public String getCopyTax() {
		return copyTax;
	}
	
	public void setCopyTax(String copyTax) {
		this.copyTax = copyTax;
	}
	
	public String getCopyCode() {
		return copyCode;
	}
	
	public void setCopyCode(String copyCode) {
		this.copyCode = copyCode;
	}
	
}
