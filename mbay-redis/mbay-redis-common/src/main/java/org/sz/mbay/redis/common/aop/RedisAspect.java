package org.sz.mbay.redis.common.aop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.sz.mbay.common.util.TypeUtil;
import org.sz.mbay.redis.common.MbayRedisCommon;

/**
 * redis缓存
 *
 * @author jerry
 */
@Aspect
public class RedisAspect {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RedisAspect.class);
			
	// 更新 - 目标格式
	private static final Pattern TARGET_REG = Pattern.compile(
			"^(?<clazz>\\$\\{\\d+\\})?(?<method>[a-zA-Z]+)(?<arg>\\$\\{.+\\})*$");
			
	// 更新/存储 - 函数参数格式 - 数字类型
	private static final Pattern SPYDIG_REG = Pattern.compile("^\\d+");
	
	// 更新/存储 - 函数参数格式 - 属性类型
	private static final Pattern SPYPRO_REG = Pattern.compile(
			"^\\d+\\.[\\.a-zA-Z0-9]+");
			
	private static final Class<?>[] EMPTY_CLASS = new Class<?>[0];
	private static final Object[] EMPTY_OBJECT = new Object[0];
	
	private static String path;
	private static Properties props;
	
	static {
		try {
			path = System.getProperty("user.home")
					+ "/channel/redis-update-notice.properties";
			File file = new File(path);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			
			Resource resource = new FileSystemResource(file);
			props = PropertiesLoaderUtils.loadProperties(resource);
			
			LOGGER.info("load file {} successfully", path);
		} catch (IOException e) {
			LOGGER.error("load file error：{}, reason：{}", path,
					e.getMessage());
		}
	}
	
	/**
	 * 读/写缓存
	 * 
	 * @param pj
	 * @param rc
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(rc)")
	public Object aroundCache(ProceedingJoinPoint pj, RedisCache rc)
			throws Throwable {
		// 生成redis key
		String className = pj.getTarget().getClass().getName();
		String methodName = pj.getSignature().getName();
		String key = className + "@" + methodName;
		key += getKeySuffix(pj.getArgs(), rc.value(),
				StringUtils.EMPTY);
				
		// 查询是否有更新通知。如果关闭更新选项，此处跳过不读通知
		String notice = null;
		if (rc.updatable()) {
			String keyTmp = null;
			if (props != null && !props.isEmpty()) {
				Iterator<Object> keySets = props.keySet().iterator();
				while (keySets.hasNext()) {
					keyTmp = (String) keySets.next();
					if (Pattern.matches(keyTmp, key)) {
						notice = keyTmp;
						break;
					}
				}
			}
		}
		
		// 反射获取返回值类型
		Method[] ms = pj.getTarget().getClass().getDeclaredMethods();
		Class<?> reType = null;
		for (Method m : ms) {
			if (m.getName().equals(methodName)) {
				reType = m.getReturnType();
				break;
			}
		}
		
		// 读取/缓存数据
		if (reType != null) {
			if (reType.isAssignableFrom(List.class)) {
				List<Object> vals = null;
				if (notice != null) {
					vals = cacheList(key, pj);
					deleteUpdateNotice(notice);
				} else {
					vals = MbayRedisCommon.getList(key);
					if (vals == null) {
						vals = cacheList(key, pj);
					}
				}
				return vals;
			} else {
				Object val = null;
				if (notice != null) {
					val = cacheOne(key, pj);
					deleteUpdateNotice(notice);
				} else {
					val = MbayRedisCommon.getOne(key);
					if (val == null) {
						val = cacheOne(key, pj);
					}
				}
				return val;
			}
		}
		return pj.proceed();
	}
	
	/*
	 * 拼接获取key后缀
	 */
	private String getKeySuffix(Object[] args, String[] specify, String ext)
			throws Exception {
		String key = "";
		if (specify != null && specify.length > 0) {
			for (String argStr : specify) {
				// 数字类型参数
				if (SPYDIG_REG.matcher(argStr).matches()) {
					int argIndex = Integer.parseInt(argStr);
					
					// 数组下标越界
					if (argIndex >= args.length) {
						throw new Exception("specify index out of bound");
					}
					// 参数不是java基本类型
					if (!TypeUtil.isPrimitive(args[argIndex])) {
						throw new Exception("arg index of " + argIndex
								+ " isn't java primitive type");
					}
					key += "@" + String.valueOf(args[argIndex]) + ext;
				}
				// 对象型参数
				else if (SPYPRO_REG.matcher(argStr).matches()) {
					int index = Integer.parseInt(argStr.substring(0,
							argStr.indexOf(".")));
					Object value = args[index];
					argStr = argStr.substring(argStr.indexOf(".") + 1) + ".";
					String methodStr = null;
					while (argStr.indexOf(".") >= 0) {
						methodStr = "get" + argStr.substring(0, 1).toUpperCase()
								+ argStr.substring(1, argStr.indexOf("."));
						value = value.getClass()
								.getMethod(methodStr, EMPTY_CLASS)
								.invoke(value, EMPTY_OBJECT);
						argStr = argStr.substring(argStr.indexOf(".") + 1);
					}
					key += "@" + String.valueOf(value) + ext;
				}
				// 格式错误
				else {
					throw new Exception("specify format error：" + argStr);
				}
			}
		}
		return key;
	}
	
	/*
	 * 删除更新通知
	 */
	private void deleteUpdateNotice(String notice) {
		props.remove(notice);
		storeProps();
	}
	
	/*
	 * 内存资源数据写入文件
	 */
	private void storeProps() {
		OutputStream out = null;
		try {
			out = new FileOutputStream(path);
			props.store(out, "UPDATE");
		} catch (Exception e) {
			LOGGER.error("props write to file error, {}",
					e.getMessage());
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/*
	 * 获取并缓存对象
	 */
	private Object cacheOne(String key, ProceedingJoinPoint pj)
			throws Throwable {
		Object val = pj.proceed();
		MbayRedisCommon.setOne(key, val);
		return val;
	}
	
	/*
	 * 获取并缓存列表
	 */
	@SuppressWarnings("unchecked")
	private List<Object> cacheList(String key, ProceedingJoinPoint pj)
			throws Throwable {
		List<Object> vals = (List<Object>) pj.proceed();
		MbayRedisCommon.setList(key, vals);
		return vals;
	}
	
	/**
	 * 更新缓存
	 * 
	 * 执行完后发一条通知到文件和内存
	 * 
	 * @param pj
	 * @param rc
	 * @return
	 * @throws Throwable
	 */
	@AfterReturning("@annotation(ru)")
	public void afterReturningUpdate(JoinPoint pj, RedisUpdate ru)
			throws Throwable {
		if (ru != null && ru.value().length > 0) {
			// 循环中所需临时参数
			String regKey = null;
			Matcher mat = null;
			String tmp = null;
			int clsIndex = -1;
			
			// 遍历目标对象，需要更新的目标加入资源文件
			for (int i = 0; i < ru.value().length; i++) {
				mat = TARGET_REG.matcher(ru.value()[i]);
				if (mat.matches()) {
					// 提取类下标
					tmp = mat.group("clazz");
					if (tmp == null) {
						regKey = pj.getTarget().getClass().getName();
					} else {
						clsIndex = Integer.parseInt(fetch(tmp)[0]);
						
						// 类下标越界
						if (clsIndex >= ru.clazz().length) {
							throw new Exception("index of clazz out of bound:"
									+ clsIndex);
						}
						
						regKey = ru.clazz()[clsIndex].getName();
					}
					
					// 提取方法名
					regKey += "@" + mat.group("method");
					
					// 提取参数列表，生成参数后缀
					tmp = mat.group("arg");
					if (tmp != null) {
						regKey += ".*" + getKeySuffix(pj.getArgs(), fetch(tmp),
								".*");
					}
					
					props.put(regKey, "0");
				} else {
					throw new Exception(ru.value()[i] + " mismatch pattern："
							+ TARGET_REG.pattern());
				}
			}
			
			// 内存数据写入文件
			storeProps();
		}
	}
	
	/*
	 * 提取${s}值
	 */
	private String[] fetch(String str) {
		String[] sp = str.split("\\$");
		List<String> val = new ArrayList<>();
		for (String s : sp) {
			if (StringUtils.isNotEmpty(s)) {
				val.add(s.substring(1, s.length() - 1).trim());
			}
		}
		
		if (val.size() == 0) {
			return null;
		} else {
			return val.toArray(new String[val.size()]);
		}
	}
}
