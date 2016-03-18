package org.sz.mbay.common.util;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 资源文件辅助类
 * 
 * @author jerry
 */
public class PropertiesUtil {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PropertiesUtil.class);
			
	/**
	 * 读取类路径或类所在包下的配置文件信息
	 * 
	 * @param fileName
	 * @param cls
	 * @return
	 */
	public static Properties loadClasspathOrPackageFile(String fileName,
			Class<?> cls) {
		Properties props = loadClasspathFile(fileName);
		if (props == null) {
			props = loadPackageFile(fileName, cls);
		}
		return props;
	}
	
	/**
	 * 读取类路径配置文件信息
	 * 
	 * @param fileName
	 * @return
	 */
	public static Properties loadClasspathFile(String fileName) {
		if (StringUtils.isEmpty(fileName)) {
			return null;
		}
		try {
			// 读取类路径下的配置文件
			Resource resource = new ClassPathResource(fileName);
			return PropertiesLoaderUtils.loadProperties(resource);
		} catch (Exception e) {
			LOGGER.warn("Could not load ["
					+ fileName + "]: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * 读取类所在包下的配置文件信息
	 * 
	 * @param fileName
	 * @param cls
	 * @return
	 */
	public static Properties loadPackageFile(String fileName,
			Class<?> cls) {
		if (StringUtils.isEmpty(fileName) || cls == null) {
			return null;
		}
		try {
			// 读取自带配置文件，与类同包
			InputStream in = cls.getResourceAsStream(fileName);
			Resource resource = new InputStreamResource(in);
			return PropertiesLoaderUtils.loadProperties(resource);
		} catch (Exception e) {
			LOGGER.warn("Could not load ["
					+ fileName + "]: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * 读取类路径下的配置文件信息
	 * 
	 * 示例：a.b.c.file
	 * 或a/b/c/file
	 * 
	 * PS：注意文件file.properties的后缀.properties一定不要写
	 * 
	 * @param fileName
	 * @return
	 */
	public static Properties loadPackageFile(String filePath) {
		if (StringUtils.isEmpty(filePath)) {
			return null;
		}
		try {
			// 读取自带配置文件
			ResourceBundle b = ResourceBundle.getBundle(filePath);
			Properties props = new Properties();
			Iterator<String> keys = b.keySet().iterator();
			String key = null;
			while (keys.hasNext()) {
				key = keys.next();
				props.setProperty(key, b.getString(key));
			}
			return props;
		} catch (Exception e) {
			LOGGER.warn("Could not load ["
					+ filePath + ".properties]: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * 往src中新增键值对，重复的会覆盖之前的
	 * 
	 * @param cls
	 */
	public static void addProperties(final Properties src,
			final Properties dis) {
		if (src == null || dis == null) {
			return;
		}
		Iterator<Object> keys = dis.keySet().iterator();
		Object key = null;
		while (keys.hasNext()) {
			key = keys.next();
			src.put(key, dis.get(key));
		}
	}
}
