package org.sz.mbay.rocketmq.helper.config;

import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class RocketMQHelperConfig {
	
	private static final String CONFIG_FILE = "rocketmq-helper.properties";
	
	private static Properties props;
	
	private RocketMQHelperConfig() {
	}
	
	static {
		Resource resource = null;
		try {
			// 读取类路径下的配置文件
			resource = new ClassPathResource(CONFIG_FILE);
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (Exception e) {
			try {
				// 读取自带配置文件，与类同包
				InputStream in = RocketMQHelperConfig.class
						.getResourceAsStream(CONFIG_FILE);
				resource = new InputStreamResource(in);
				props = PropertiesLoaderUtils.loadProperties(resource);
			} catch (Exception ex) {
				throw new IllegalStateException("Could not load ["
						+ CONFIG_FILE + "]: " + ex.getMessage());
			}
		}
	}
	
	private static final String PROJECT_PATH = props
			.getProperty("project_path");
			
	public static final String SEND_MSG_URL = PROJECT_PATH
			+ props.getProperty("send_msg");
}
