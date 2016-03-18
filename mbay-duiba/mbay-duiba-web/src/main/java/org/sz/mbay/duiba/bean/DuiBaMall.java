package org.sz.mbay.duiba.bean;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;
import org.sz.mbay.base.enums.EnableState;

/**
 * 兑吧商城实体bean
 * @author Frank
 *
 */
@SuppressWarnings("serial")
public class DuiBaMall implements Serializable {

	private int id; //id
	@NotBlank
	private String name; //名称
	@NotBlank
	private String appkey;//appkey
	@NotBlank
	private String appsecret;//appsecret
	@NotNull
	private EnableState status;//状态
    private DateTime createTime; //时间
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAppkey() {
		return appkey;
	}
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	public String getAppsecret() {
		return appsecret;
	}
	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
	public EnableState getStatus() {
		return status;
	}
	public void setStatus(EnableState status) {
		this.status = status;
	}
	public DateTime getCreateTime() {
		return createTime;
	}
	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}
}