package org.sz.mbay.base.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ResourceConfig {
	
	private static ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	private static Properties properties = new Properties();
	private static final String CONFIG_FILE = "*config.properties";
	
	/**
	 * web端域名
	 */
	private static String WEB_DOMAIN = "webDomain";
	
	/**
	 * 获取WEB端域名
	 */
	public static String getWebDomain() {
		return ResourceConfig.getProperty(WEB_DOMAIN);
	}
	
	/**
	 * 返回符合正则的值数组
	 * 
	 * @param regKey
	 * @return
	 */
	public static List<String> getRegProperties(String regKey) {
		Iterator<Object> itor = properties.keySet().iterator();
		Pattern p = Pattern.compile(regKey, Pattern.CASE_INSENSITIVE);
		List<String> val = new ArrayList<>();
		String key = null;
		while (itor.hasNext()) {
			key = String.valueOf(itor.next());
			if (p.matcher(key).matches()) {
				val.add(properties.getProperty(key));
			}
		}
		return val;
	}
	
	/**
	 * 获取项目路径
	 */
	public static String getContextPath() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request.getContextPath();
	}
	
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
	
	public static String getProperty(String key, String defVal) {
		return properties.getProperty(key, defVal);
	}
	
	static {
		try {
			String configPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
					+ CONFIG_FILE;
			Resource[] resources = resourcePatternResolver
					.getResources(configPath);
			for (Resource resource : resources) {
				Properties propertie = PropertiesLoaderUtils
						.loadProperties(resource);
				if (!propertie.isEmpty()) {
					Set<String> propertyNames = propertie.stringPropertyNames();
					for (String key : propertyNames) {
						properties.put(key, propertie.get(key));
					}
				}
			}
		} catch (IOException e) {
			throw new IllegalStateException("Could not load '[" + CONFIG_FILE
					+ "]': " + e.getMessage());
		}
	}
}
