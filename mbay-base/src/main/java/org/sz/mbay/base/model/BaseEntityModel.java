package org.sz.mbay.base.model;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.sz.mbay.base.json.LongDeserialize;
import org.sz.mbay.base.json.LongSerialize;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * 
 * @ClassName: BaseEntityModel
 * 			
 * @Description: 集成Base基类，实习其抽象方法
 * 				
 * @author <a href="mailto:fenlonxiong@gmail.com">Fenlon</a>
 * 		
 * @date 2014年12月3日 下午9:21:46
 * 		
 */

public class BaseEntityModel extends BaseModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6163675075289529459L;
	
	String entityName;
	
	/**
	 * 实体主键
	 */
	Long id;
	
	@Override
	public boolean equals(Object obj) {
		if (null != obj) {
			if (obj instanceof BaseEntityModel) {
				BaseEntityModel domain = (BaseEntityModel) obj;
				if (this.id == domain.id
						&& this.getClass() == domain.getClass()) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		if (this.id == null) {
			this.id = 0L;
		}
		return HashCodeBuilder.reflectionHashCode(this.id);
	}
	
	@JsonSerialize(using = LongSerialize.class)
	public Long getId() {
		return id;
	}
	
	@JsonDeserialize(using = LongDeserialize.class)
	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
	
}
