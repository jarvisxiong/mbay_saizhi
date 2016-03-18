package org.sz.mbay.fs.fastdfs;

import java.io.IOException;
import java.net.Socket;

import org.csource.common.MyException;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * StorageClient增强
 * 
 * @author jerry
 */
public class EnhancedStorageClient extends StorageClient {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(EnhancedStorageClient.class);
	
	public EnhancedStorageClient() {
		super();
	}
	
	public EnhancedStorageClient(TrackerServer trackerServer,
			StorageServer storageServer) {
		super(trackerServer, storageServer);
	}
	
	public TrackerServer getTrackerServer() {
		return trackerServer;
	}
	
	public StorageServer getStorageServer() {
		return storageServer;
	}
	
	/**
	 * 获取文件可访问存储服务器地址
	 * 
	 * @param groupName
	 * @param remoteFileName
	 * @return
	 * @throws IOException
	 * @throws MyException
	 */
	public String getFileStorageAddr(String groupName, String remoteFileName)
			throws IOException, MyException {
		boolean bNewConnection = newUpdatableStorageConnection(groupName,
				remoteFileName);
		Socket storageSocket = storageServer.getSocket();
		
		try {
			send_package((byte) 15, groupName, remoteFileName);
			ProtoCommon.RecvPackageInfo pkgInfo = ProtoCommon.recvPackage(
					storageSocket.getInputStream(), (byte) 100, -1L);
			
			this.errno = pkgInfo.errno;
			if (pkgInfo.errno != 0) {
				return null;
			}
			return storageSocket.getInetAddress().getHostAddress();
		} catch (IOException ex) {
			if (!bNewConnection) {
				try {
					storageServer.close();
				} catch (IOException ex1) {
					LOGGER.error("EnhancedStorageClient getFileAccessableAddr error:"
							+ ex1.fillInStackTrace());
				}
			}
			throw ex;
		} finally {
			if (bNewConnection) {
				try {
					storageServer.close();
				} catch (IOException ex1) {
					LOGGER.error("EnhancedStorageClient getFileAccessableAddr error:"
							+ ex1.fillInStackTrace());
				} finally {
					storageServer = null;
				}
			}
		}
	}
}
