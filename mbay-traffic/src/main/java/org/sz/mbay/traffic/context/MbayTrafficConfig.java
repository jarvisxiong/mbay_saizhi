package org.sz.mbay.traffic.context;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServlet;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * @author ONECITY
 * 
 */
@SuppressWarnings("serial")
public class MbayTrafficConfig extends HttpServlet {
	private static final String CONFIG_FILE = "config.properties";
	private static Properties properties;

	
	private static String TRAFFIC_RECHARGE_EXCEL_URL = "traffic_recharge_excel_url";

	
	/**美贝支付请求地址***/
	public static String getTrafficRechargeExcelURL(){
		return properties.getProperty(TRAFFIC_RECHARGE_EXCEL_URL);
	}
	
	
	static {
		try {
			ClassPathResource resource = new ClassPathResource(CONFIG_FILE);
			properties = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException ex) {
			throw new IllegalStateException("Could not load '[" + CONFIG_FILE
					+ "]': " + ex.getMessage());
		}

	}

}
