package org.sz.mbay.base.annotation.hotlinking;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 防盗链
 * 
 * 使用需求：
 * 1.需要结合spring mvc的@RequestMapping，@RequestMapping需要定义两个不同的url
 * 2.link设置盗链地址，或linkId引用web.xml的上下文配置，详情参考方法描述。地址要写完整，比如
 * 重定向到action要加上前缀'redirect:/'和配置的后缀如'.do'。
 * 
 * <pre>
 * linkId配置示例：
 * <context-param>
 *     <param-name>hotlinking</param-name>
 *     <param-value>/traffic_red/mobile/shake/hotlinking</param-value>
 * </context-param>
 * hotlinking是默认值，若未改变此名称，则注解使用时可省略linkId参数
 * </pre>
 * 
 * 注意点：
 * 1.不适用ajax请求（含有@ResponseBody的方法不会拦截）
 * 2.返回值必须为String，不能为ModelAndView
 * 3.不适用文件提交的POST请求
 * 4.POST请求会被重定向为GET请求，参数会带到url后面，推荐GET请求使用，且不要带中文请求参数
 *
 * @author jerry
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Hotlinking {
	
	/** 默认linkId值 */
	public static final String DEFAULT_LINKID = "hotlinking";
	
	/**
	 * 盗链重定向地址（优先级大于linkId）
	 * 
	 * @return
	 */
	String link() default "";
	
	/**
	 * 盗链重定向配置id
	 * 
	 * web.xml -> context-param -> param-name
	 * 
	 * @return
	 */
	String linkId() default DEFAULT_LINKID;
	
	/**
	 * 初始设置session的RequestMapping地址下标
	 * 
	 * @return
	 */
	int set() default 0;
	
	/**
	 * 需要检测盗链的RequestMapping地址下标
	 * 
	 * @return
	 */
	int check() default 1;
}
