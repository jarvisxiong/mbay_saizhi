package org.sz.mbay.fs.fastdfs;

import org.sz.mbay.fs.FSClient;

/**
 * fastdfs客户代理
 * 
 * @author jerry
 */
public class FDFSClient extends FSClient {
	
	// 文件服务
	private FDFSService fdfsService;
	
	// 单利实例
	private static FDFSClient instance;
	
	private FDFSClient() {
		fdfsService = new FDFSService();
		setFsSerice(fdfsService);
	}
	
	public static FDFSClient getInstance() {
		if (instance == null) {
			synchronized (FDFSClient.class) {
				if (instance == null) {
					instance = new FDFSClient();
				}
			}
		}
		return instance;
	}
	
	public FDFSService getFdfsService() {
		return fdfsService;
	}
}
