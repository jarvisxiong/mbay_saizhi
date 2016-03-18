package org.sz.mbay.session.client.config;

import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class ClientConfig {
	
	private static final String CONFIG_FILE = "session-client-config.properties";
	private static Properties properties;
	
	static {
		Resource resource = null;
		try {
			// 读取类路径下的配置文件
			resource = new ClassPathResource(CONFIG_FILE);
			properties = PropertiesLoaderUtils.loadProperties(resource);
		} catch (Exception e) {
			try {
				// 读取自带配置文件
				InputStream in = ClientConfig.class.getResourceAsStream(CONFIG_FILE);
				resource = new InputStreamResource(in);
				properties = PropertiesLoaderUtils.loadProperties(resource);
			} catch (Exception ex) {
				throw new IllegalStateException("Could not load [" + CONFIG_FILE
						+ "]: " + ex.getMessage());
			}
		}
	}
	
	public static String getProperty(String name) {
		return properties.getProperty(name);
	}
	
	public static String getProperty(String name, String defVal) {
		return properties.getProperty(name, defVal);
	}
	
	/*----------------------- 配置项 ------------------------------*/
	public static final String SERVER = "server";
	
	public static final String KEY_PREFIX = "key_prefix";
	
	public static final String TIMEOUT = "timeout";
	
}
