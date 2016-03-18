package org.sz.mbay.redis.common.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * redis缓存
 *
 * @author jerry
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCache {
	
	/**
	 * 指定方法中哪些参数（下标）加入redis key中作为限定名
	 * 
	 * key格式：类全名@方法名@arg[0]@arg[1]...
	 * 
	 * 默认key格式为：类全名@方法名
	 * 
	 * @return
	 */
	String[]value() default {};
	
	/**
	 * 该缓存是否可被更新
	 * 
	 * @return
	 */
	boolean updatable() default true;
	
}
