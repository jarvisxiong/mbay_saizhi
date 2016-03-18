package org.sz.mbay.fs.fastdfs;

import java.net.InetSocketAddress;

import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;

/**
 * TrackerClient增强
 * 
 * @author jerry
 */
public class EnhancedTrackerClient extends TrackerClient {
	
	public EnhancedTrackerClient() {
		super();
	}
	
	public EnhancedTrackerClient(TrackerGroup trackerGroup) {
		super(trackerGroup);
	}
	
	/**
	 * 获取特定下标的跟踪器
	 * 
	 * @param serverIndex
	 *            配置文件中跟踪器下标，从0开始
	 * @return
	 * @throws Exception
	 */
	public TrackerServer getConnection(int serverIndex) throws Exception {
		return tracker_group.getConnection(serverIndex);
	}
	
	/**
	 * 获取特定ip地址的跟踪器
	 * 
	 * @param serverIpAddr
	 *            配置文件中定义的跟踪器ip地址
	 * @return
	 * @throws Exception
	 */
	public TrackerServer getConnection(String serverIpAddr) throws Exception {
		if (serverIpAddr == null || serverIpAddr.length() == 0) {
			return null;
		}
		InetSocketAddress[] addrArr = tracker_group.tracker_servers;
		for (int i = 0; i < addrArr.length; i++) {
			if (serverIpAddr.equals(addrArr[i].getHostString())) {
				return tracker_group.getConnection(i);
			}
		}
		return null;
	}
}
