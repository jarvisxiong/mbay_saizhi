package org.sz.mbay.privilege.bean;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 权限表
 * 
 * @author Frank.zong
 *		
 */
@SuppressWarnings("serial")
public class Privilege implements Serializable {
	
	/** id ***/
	private int id;
	
	/** 权限名称 */
	@NotBlank
	@Size(min=1,max=50)
	private String name;
	
	/** 创建时间 **/
	private String createTime;
	
	/** 父权限:如果parent_id为0代表父节点 */
	private int parentId;
	
	/** 后台路径:url代表具体的action后台地址，进入响应的后台方法 */
	@NotBlank
	@Size(min=1,max=50)
	private String url;
	
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
	
	public String getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public int getParentId() {
		return parentId;
	}
	
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
}