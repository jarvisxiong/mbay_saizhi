package org.sz.mbay.base.annotation.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 输出方法运行时间到日志
 *
 * @author jerry
 */
@Aspect
public class RunTimeAspect {
	
	private static Logger LOGGER = LoggerFactory.getLogger(RunTimeAspect.class);
	
	@Around("execution(* (org.sz.mbay.base.service.impl.BaseServiceImpl+"
			+ "|| org.sz.mbay.base.dao.impl.BaseDaoImpl+"
			+ "|| org.sz.mbay..action..*"
			+ "|| org.sz.mbay..controller..*).*(..))")
	public Object doAround(ProceedingJoinPoint pj)
			throws Throwable {
		long start = System.nanoTime();
		Object val = pj.proceed();
		long end = System.nanoTime();
		LOGGER.info(getDescription(pj, start, end));
		return val;
	}
	
	/*
	 * 格式化方法信息
	 */
	private static String getDescription(ProceedingJoinPoint pj,
			long start, long end)
					throws Exception {
		String targetName = pj.getTarget().getClass().getName();
		String methodName = pj.getSignature().getName();
		String desc = "【" + targetName + " ~ " + methodName + "】耗时：";
		
		long diff = end - start;
		if (diff < 1000) {
			return desc + diff + "纳秒";
		}
		
		diff /= 1000;
		if (diff < 1000) {
			return desc + diff + "微秒";
		}
		
		diff /= 1000;
		if (diff < 1000) {
			return desc + diff + "毫秒";
		}
		
		diff /= 1000;
		if (diff < 60) {
			return desc + diff + "秒";
		}
		
		long s = diff % 60;
		diff /= 60;
		if (diff < 60) {
			return desc + diff + "分" + s + "秒";
		}
		
		long m = diff % 60;
		diff /= 60;
		return desc + diff + "时" + m + "分" + s + "秒";
	}
}
