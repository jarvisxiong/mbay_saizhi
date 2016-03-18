package org.sz.mbay.channel.bean;

import java.io.Serializable;

import org.joda.time.DateTime;

public class BaseEntityModel implements Serializable {
	
	private static final long serialVersionUID = -4830009910216871452L;
	/** 实体主键 **/
	private long id;
	/** 实体是否被删除 **/
	private boolean deleted = false;
	/** 创建时间 **/
	private DateTime dateCreated = new DateTime();
	/** 修改时间 **/
	private DateTime dateModified = new DateTime();
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public DateTime getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(DateTime dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public DateTime getDateModified() {
		return dateModified;
	}
	
	public void setDateModified(DateTime dateModified) {
		this.dateModified = dateModified;
	}
}
