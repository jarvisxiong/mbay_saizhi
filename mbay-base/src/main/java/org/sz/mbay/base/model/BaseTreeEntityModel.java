package org.sz.mbay.base.model;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * 
 * @ClassName: BaseTreeEntityModel
 * 
 * @Description: 定义树形实体,继承基类
 * 
 * @author <a href="mailto:fenlonxiong@gmail.com">Fenlon</a>
 * 
 * @date 2014年12月3日 下午9:23:28
 * 
 * @param <T>
 */

@SuppressWarnings("rawtypes")
public class BaseTreeEntityModel<T extends BaseTreeEntityModel> extends
		BaseEntityModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5961264427451119166L;
	
	@JsonManagedReference("parent-children")
	List<T> children;
	
	boolean leaf = true;
	
	@JsonBackReference("parent-children")
	T parent;
	
	String text;
	
	public List<T> getChildren() {
		return children;
	}
	
	public T getParent() {
		return parent;
	}
	
	public String getText() {
		return text;
	}
	
	public boolean isLeaf() {
		if (null != this.children && this.children.size() > 0) {
			this.leaf = false;
		} else {
			this.leaf = true;
		}
		return leaf;
	}
	
	public void setChildren(List<T> children) {
		this.children = children;
	}
	
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	
	public void setParent(T parent) {
		this.parent = parent;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE, false);
	}
	
}
