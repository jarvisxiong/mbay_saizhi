package org.sz.mbay.sms.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YZXMapperUtil {
	
	private Properties prop = null;
	private static YZXMapperUtil yzxMapperUtil = new YZXMapperUtil();
	private String propFile = "yzx_mapper.properties";
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private YZXMapperUtil() {
		try {
			prop = new Properties();
			InputStream inStream = this.getClass().getClassLoader()
					.getResourceAsStream(propFile);
			prop.load(inStream);
		} catch (IOException e) {
			logger.error("YZXMapperUtil-Construct", e.fillInStackTrace());
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public static YZXMapperUtil getInstance() {
		return yzxMapperUtil;
	}
	
	public String getContent(String key) {
		return prop.getProperty(key);
	}
	
}
