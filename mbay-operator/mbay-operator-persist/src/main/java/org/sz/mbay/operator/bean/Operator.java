package org.sz.mbay.operator.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.sz.mbay.base.area.Area;
import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.operator.enums.OperatorType;

/**
 * @author ONECITY 通信运营商表
 * 
 */
@SuppressWarnings("serial")
public class Operator implements Serializable {
	public Operator() {}

	public Operator(int id) {
		this.id = id;
	}

	/** 主键ID，同时标识地区 */
	private int id;
	
	/** 运营商名称 */
	private String name;

	/** 0：启用，1：禁用 */
	private EnableState enable;

	/** 地区 */
	private Area area;

	/** 签约时间（即创建时间） */
	private Timestamp signtime;

	/** 联系电话 */
	private String contactnumber;

	/** 联系人 */
	private String contacts;
	
	/** 技术联系人名字*/
	private String technicist;
	
	/** 技术联系人电话*/
	private String technicistphone;

	/** 运营商类型( 1.中国移动，2.中国联通，3.中国电信 ) */
	private OperatorType type;

	/** 地区编码+运营商类型编码(例：上海移动 上海31+移动1=311) */
	private String serialnumber;
	
	private List<Contract> contracts;
	
	private List<TrafficPackage> packages;

	public EnableState getEnable() {
		return enable;
	}

	public void setEnable(EnableState enable) {
		this.enable = enable;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getSigntime() {
		return signtime;
	}

	public void setSigntime(Timestamp signtime) {
		this.signtime = signtime;
	}

	public String getContactnumber() {
		return contactnumber;
	}

	public void setContactnumber(String contactnumber) {
		this.contactnumber = contactnumber;
	}

	public String getContacts() {
		return contacts;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public void setType(OperatorType type) {
		this.type = type;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSerialnumber() {
		return serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	public OperatorType getType() {
		return type;
	}

	public List<Contract> getContracts() {
		return contracts;
	}
	
	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}

	public List<TrafficPackage> getPackages() {
		return packages;
	}

	public void setPackages(List<TrafficPackage> packages) {
		this.packages = packages;
	}

	public String getTechnicist() {
		return technicist;
	}

	public void setTechnicist(String technicist) {
		this.technicist = technicist;
	}

	public String getTechnicistphone() {
		return technicistphone;
	}

	public void setTechnicistphone(String technicistphone) {
		this.technicistphone = technicistphone;
	}

}
