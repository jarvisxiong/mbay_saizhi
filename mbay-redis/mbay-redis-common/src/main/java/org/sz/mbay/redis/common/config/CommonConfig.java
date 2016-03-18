package org.sz.mbay.redis.common.config;

import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 配置文件类
 * 
 * @author frank.zong
 */
public class CommonConfig {
	
	private CommonConfig() {
	}
	
	private static final String CONFIG_FILE = "redis-common.properties";
	
	private static Properties props;
	
	static {
		Resource resource = null;
		try {
			// 读取类路径下的配置文件
			resource = new ClassPathResource(CONFIG_FILE);
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (Exception e) {
			try {
				// 读取自带配置文件，与类同包
				InputStream in = CommonConfig.class
						.getResourceAsStream(CONFIG_FILE);
				resource = new InputStreamResource(in);
				props = PropertiesLoaderUtils.loadProperties(resource);
			} catch (Exception ex) {
				throw new IllegalStateException("Could not load ["
						+ CONFIG_FILE + "]: " + ex.getMessage());
			}
		}
	}
	
	public static String getCommonRedisServer() {
		return props.getProperty("redis_server_get");
	}
	
	public static String setCommonRedisServer() {
		return props.getProperty("redis_server_set");
	}
	
	public static String delCommonRedisServer() {
		return props.getProperty("redis_server_del");
	}
}
