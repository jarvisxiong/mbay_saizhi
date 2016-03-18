package org.sz.mbay.redis.common.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 决定什么操作更新缓存
 *
 * @author jerry
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisUpdate {
	
	Class<?>[]clazz() default {};
	
	String[]value() default {};
	
}
