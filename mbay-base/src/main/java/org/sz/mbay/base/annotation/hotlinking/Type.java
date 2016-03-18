package org.sz.mbay.base.annotation.hotlinking;

/**
 * 注解行为
 *
 * @author jerry
 */
public enum Type {
	
	/**
	 * 不是该类型
	 */
	NON,
	
	/**
	 * 在需要检测盗链的方法前
	 * 
	 * 实现：设置session值
	 */
	SET,
	
	/**
	 * 当前方法检测盗链
	 * 
	 * 实现：检测session是否有某指定值
	 */
	CHECK;
}
