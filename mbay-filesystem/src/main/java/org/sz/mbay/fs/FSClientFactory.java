package org.sz.mbay.fs;

import org.sz.mbay.fs.fastdfs.FDFSClient;

/**
 * 不同策略文件系统工厂
 * 
 * @author jerry
 */
public final class FSClientFactory {
	
	public static enum ClientType {
		// fastdfs分布式文件管理
		FDFS
	}
	
	public static FSClient getClient(ClientType clientType) {
		switch (clientType) {
			case FDFS:
				return FDFSClient.getInstance();
			default:
				return null;
		}
	}
}
