package org.sz.mbay.fs.fastdfs;

import java.util.HashMap;
import java.util.Map;

import org.csource.fastdfs.TrackerGroup;

/**
 * TrackerGroup增强
 * 
 * @author jerry
 */
public class EnhancedTrackerGroup {
	
	// 文件系统是否开启
	private static boolean enable;
	
	// 是否开启直接通过tracker读取文件
	// 多个tracker时需要所有storage配置相同的tracker
	private static boolean enableEasyRead;
	
	// 跟踪器组
	private static TrackerGroup trackerGroup;
	
	// 内网跟踪器对应的外网访问地址
	private static final Map<String, String> inetIpMap = new HashMap<String, String>();
	
	/**
	 * 设置某跟踪器的外网访问地址
	 * 
	 * @param trackerServer
	 * @param pubIp
	 */
	public static void setPublicNetwordIp(String trackerServer, String pubIp) {
		inetIpMap.put(trackerServer, pubIp);
	}
	
	/**
	 * 获取某跟踪器的外网访问地址
	 * 
	 * @param trackerServer
	 * @return
	 */
	public static String getPublicNetworkIp(String trackerServer) {
		return inetIpMap.get(trackerServer);
	}
	
	public static TrackerGroup getTrackerGroup() {
		return trackerGroup;
	}
	
	public static void setTrackerGroup(TrackerGroup trackerGroup) {
		EnhancedTrackerGroup.trackerGroup = trackerGroup;
	}
	
	public static boolean isEnable() {
		return enable;
	}
	
	public static void setEnable(boolean enable) {
		EnhancedTrackerGroup.enable = enable;
	}
	
	public static boolean isEnableEasyRead() {
		return enableEasyRead;
	}
	
	public static void setEnableEasyRead(boolean enableEasyRead) {
		EnhancedTrackerGroup.enableEasyRead = enableEasyRead;
	}
	
}
