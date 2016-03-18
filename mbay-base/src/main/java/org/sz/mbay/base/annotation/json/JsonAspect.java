package org.sz.mbay.base.annotation.json;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.mbay.common.util.TypeUtil;

/**
 * 拦截控制层@ResponseBody返回值，使用jackson输出
 * 
 * 在需要拦截的方法上加@ResponseBody和JsonSerialize
 * 
 * @author jerry
 */
@Aspect
public class JsonAspect {
	
	private static Logger LOGGER = LoggerFactory.getLogger(JsonAspect.class);
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	@Pointcut("execution("
			+ "@org.springframework.web.bind.annotation.ResponseBody "
			+ "@(org.codehaus.jackson.map.annotate.JsonSerialize || "
			+ "com.fasterxml.jackson.databind.annotation.JsonSerialize) * "
			+ "(org.sz.mbay..controller..* || org.sz.mbay..action..*).*(..))")
	private void ResponseBodyMethod() {
	}
	
	@Around("ResponseBodyMethod()")
	public Object doAround(ProceedingJoinPoint pj) throws Throwable {
		Object returnVal = pj.proceed();
		
		if (!TypeUtil.isPrimitive(returnVal)) {
			try {
				return mapper.writeValueAsString(returnVal);
			} catch (Exception e) {
				LOGGER.error("JsonAspect:{}", e.getMessage());
			}
		}
		return returnVal;
	}
	
}
