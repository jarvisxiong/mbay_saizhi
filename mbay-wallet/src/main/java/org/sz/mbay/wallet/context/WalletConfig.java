package org.sz.mbay.wallet.context;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 读取配置文件
 * 
 * @author jerry
 */
public class WalletConfig {
	
	private static final String CONFIG_FILE = "config.properties";
	private static Properties properties;
	
	static {
		try {
			ClassPathResource resource = new ClassPathResource(CONFIG_FILE);
			properties = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException ex) {
			throw new IllegalStateException("Could not load '[" + CONFIG_FILE
					+ "]': " + ex.getMessage());
		}
	}
	
	/**
	 * 获取值
	 * 
	 * @param name
	 * @return
	 */
	public static String getProperty(String name) {
		return properties.getProperty(name);
	}
	
	/*------------------------------------------------------
	 *                       配置key
	 *----------------------------------------------------*/
	
	/** 兑吧 */
	public static final String DUIBA_URL = getProperty("duiba_url");
	
	/** 兑吧商城id */
	public static final String MALLID = getProperty("mallId");
	
	/** 钱包域名 */
	public static final String MOBILE_DOMAIN = getProperty("mobileDomain");
	
	/** izhangxin游戏接口 */
	public static final String IZHANGXIN = getProperty("izhangxin");
			
}
