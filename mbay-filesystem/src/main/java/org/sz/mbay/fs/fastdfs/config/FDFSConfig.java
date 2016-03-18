package org.sz.mbay.fs.fastdfs.config;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.TrackerGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.sz.mbay.fs.fastdfs.EnhancedTrackerGroup;

public class FDFSConfig {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FDFSConfig.class);
			
	private static final String CONFIG_FILE = "fastdfs.properties";
	
	private static Properties props;
	
	private FDFSConfig() {
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
				InputStream in = FDFSConfig.class
						.getResourceAsStream(CONFIG_FILE);
				resource = new InputStreamResource(in);
				props = PropertiesLoaderUtils.loadProperties(resource);
			} catch (Exception ex) {
				throw new IllegalStateException("Could not load ["
						+ CONFIG_FILE + "]: " + ex.getMessage());
			}
		}
	}
	
	/**
	 * 初始化配置
	 */
	public static void initFDFS() {
		if (props != null) {
			// enable
			String enable = props.getProperty("enable", "true");
			boolean enableBool = (enable.equalsIgnoreCase("yes"))
					|| (enable.equalsIgnoreCase("on"))
					|| (enable.equalsIgnoreCase("true"))
					|| (enable.equals("1"));
			EnhancedTrackerGroup.setEnable(enableBool);
			
			// enable_easy_read
			String enableEasyRead = props.getProperty("enable_easy_read",
					"true");
			boolean enableEasyReadBool = (enableEasyRead
					.equalsIgnoreCase("yes"))
					|| (enableEasyRead.equalsIgnoreCase("on"))
					|| (enableEasyRead.equalsIgnoreCase("true"))
					|| (enableEasyRead.equals("1"));
			EnhancedTrackerGroup.setEnableEasyRead(enableEasyReadBool);
			
			// anti_steal_token
			String steal = props.getProperty("http.anti_steal_token", "no");
			boolean stealBool = (steal.equalsIgnoreCase("yes"))
					|| (steal.equalsIgnoreCase("on"))
					|| (steal.equalsIgnoreCase("true"))
					|| (steal.equals("1"));
			ClientGlobal.setG_anti_steal_token(stealBool);
			
			// secret_key
			if (stealBool) {
				ClientGlobal.setG_secret_key(props
						.getProperty("http.secret_key"));
			}
			
			// tracker_http_port
			int httpPort = Integer.parseInt(props.getProperty(
					"http.tracker_http_port", "80"));
			ClientGlobal.setG_tracker_http_port(httpPort);
			
			// charset
			ClientGlobal
					.setG_charset(props.getProperty("charset", "ISO8859-1"));
					
			// tracker链接超时
			int connTimeOut = Integer.parseInt(props.getProperty(
					"connect_timeout",
					"5"));
			ClientGlobal.setG_connect_timeout(connTimeOut * 1000);
			
			// 网络链接超时
			int netTimeOut = Integer.parseInt(props.getProperty(
					"network_timeout",
					"30"));
			ClientGlobal.setG_network_timeout(netTimeOut * 1000);
			
			// tracker
			String trackerPrefix = "tracker_server";
			List<InetSocketAddress> addrList = new ArrayList<InetSocketAddress>();
			Set<Object> keySet = props.keySet();
			Iterator<Object> itor = keySet.iterator();
			String key = null, value = null;
			String[] splits = null, domain = null;
			while (itor.hasNext()) {
				key = itor.next().toString();
				if (key.startsWith(trackerPrefix)) {
					value = props.getProperty(key, "");
					domain = value.split("[/\\\\]");
					
					if (domain.length > 0) {
						splits = domain[0].split(":");
						try {
							// 内网tracker
							addrList.add(new InetSocketAddress(splits[0],
									Integer.parseInt(splits[1])));
									
							// tracker对应的外网地址
							if (domain.length > 1) {
								EnhancedTrackerGroup.setPublicNetwordIp(
										splits[0],
										domain[1]);
							}
						} catch (Exception e) {
							LOGGER.error(
									"fastdfs create InetSocketAddress error:"
											+ value);
						}
					}
				}
			}
			TrackerGroup group = new TrackerGroup(
					addrList.toArray(new InetSocketAddress[addrList.size()]));
			EnhancedTrackerGroup.setTrackerGroup(group);
			ClientGlobal.setG_tracker_group(group);
		}
	}
}
