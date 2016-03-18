package org.sz.mbay.sms.config;

import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class MbaySmsConfig {
	
	private static final String CONFIG_FILE = "sms-client.properties";
	
	private static Properties properties;
	
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
	
	static {
		Resource resource = null;
		try {
			// 读取类路径下的配置文件
			resource = new ClassPathResource(CONFIG_FILE);
			properties = PropertiesLoaderUtils.loadProperties(resource);
		} catch (Exception e) {
			try {
				// 读取自带配置文件，与类同包
				InputStream in = MbaySmsConfig.class
						.getResourceAsStream(CONFIG_FILE);
				resource = new InputStreamResource(in);
				properties = PropertiesLoaderUtils.loadProperties(resource);
			} catch (Exception ex) {
				throw new IllegalStateException("Could not load ["
						+ CONFIG_FILE
						+ "]: " + ex.getMessage());
			}
		}
	}
	
	/*------------------------------------------
	 *                  配置key
	 *----------------------------------------*/
	
	public static final String PROJECT_PATH = getProperty("project_path");
	
	public static final String SMS_URL = PROJECT_PATH + getProperty("sms_url");
	
	public static final String VOICE_CODE_URL = PROJECT_PATH
			+ getProperty("voice_code_url");
	
	public static final String SMS_TEMPLATE_UPDATE_URL = PROJECT_PATH
			+ getProperty("sms_template_update_url");
	
	public static final String SMS_TEMPLATE_GET_URL = PROJECT_PATH
			+ getProperty("sms_template_get_url");
	
}
