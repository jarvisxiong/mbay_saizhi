package org.sz.mbay.channel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 实名认证注解：判断是用户是否已经过实名认证，若用户未经过实名认证，则不执行当前方法跳转至提醒实名认证页面
 * @author han.han
 * @date 2015-3-25 下午5:46:04
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CertAuth {
}
