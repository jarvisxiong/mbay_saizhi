package org.sz.mbay.remote.interfaces.wallet.config;

import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class Config {
	
	private static final String CONFIG_FILE = "remote-interfaces-wallet.properties";
	
	private static Properties props;
	
	private Config() {
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
				InputStream in = Config.class
						.getResourceAsStream(CONFIG_FILE);
				resource = new InputStreamResource(in);
				props = PropertiesLoaderUtils.loadProperties(resource);
			} catch (Exception ex) {
				throw new IllegalStateException("Could not load ["
						+ CONFIG_FILE + "]: " + ex.getMessage());
			}
		}
	}
	
	public static final String OUTTER_INTERFACE = props
			.getProperty("outter_interface");
}
