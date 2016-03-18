package org.sz.mbay.weixin.config;

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
public class ResourceConfig {
	
	private ResourceConfig() {
	}
	
	private static final String CONFIG_FILE = "weixin.properties";
	
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
				InputStream in = ResourceConfig.class
						.getResourceAsStream(CONFIG_FILE);
				resource = new InputStreamResource(in);
				props = PropertiesLoaderUtils.loadProperties(resource);
			} catch (Exception ex) {
				throw new IllegalStateException("Could not load ["
						+ CONFIG_FILE + "]: " + ex.getMessage());
			}
		}
	}
	
	public static String getAppId() {
		return props.getProperty("appid");
	}
	
	public static String getAppSecret() {
		return props.getProperty("appsecret");
	}
}
