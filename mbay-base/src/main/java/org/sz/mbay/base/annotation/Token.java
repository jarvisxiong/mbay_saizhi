package org.sz.mbay.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * token 注解。在需要生成token 的方法上加上此注解表设置save为true，save 默认为false；
 * 在需要检查的重复提交的方法上加上此注解 ，并设置reset为false；
 * @author han.han
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Token {
	boolean save() default false;//
	boolean reset() default false;

}
