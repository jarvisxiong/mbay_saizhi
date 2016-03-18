package org.sz.mbay.fs.fastdfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.sz.mbay.fs.FSExtraData;
import org.sz.mbay.fs.FSFileInfo;
import org.sz.mbay.fs.FSService;
import org.sz.mbay.fs.fastdfs.config.FDFSConfig;

/**
 * fastdfs文件处理服务
 * 
 * 单利模式
 * 
 * @author jerry
 */
public class FDFSService implements FSService {
	
	static {
		// 读取配置并初始化
		FDFSConfig.initFDFS();
	}
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FDFSService.class);
			
	// 跟踪器调用者
	private static EnhancedTrackerClient trackerClient;
	
	/*-------------------------------------------------------
	 *                       自身服务
	 *-----------------------------------------------------*/
	
	/**
	 * 获取存储器服务
	 * 
	 * 任意取一个tracker，返回其下挂载特定组名的storageServer
	 * 
	 * PS：若storage配置文件没有全部配置成相同tracker，有可能任意取得的tracker没有指定组名的存储器，则会抛出异常。
	 * 此处遍历所有tracker确保找到正确的存储器
	 * 
	 * @param groupName
	 * @return
	 * @throws IOException
	 */
	public EnhancedStorageClient getStorageClient(String groupName) {
		StorageServer storageServer = null;
		TrackerServer trackerServer = null;
		try {
			// 先任意取一次。只要服务配置得当，第一次就能成功
			// 若直接遍历，会丧失fastdfs的负载均衡能力，只在失败处进行遍历操作
			trackerServer = getTrackerServer();
			storageServer = getTrackerClient().getStoreStorage(trackerServer,
					groupName);
			if (storageServer == null) {
				LOGGER.info("not find {} in {}:", groupName,
						trackerServer.getInetSocketAddress());
				throw new Exception();
			}
		} catch (Exception e) {
			for (InetSocketAddress addr : EnhancedTrackerGroup
					.getTrackerGroup().tracker_servers) {
				try {
					trackerServer = getTrackerServer(addr.getHostString());
					storageServer = getTrackerClient().getStoreStorage(
							trackerServer, groupName);
					if (storageServer == null) {
						LOGGER.info("not find {} in {}:", groupName,
								addr.getHostString());
						continue;
					}
					break;
				} catch (IOException ex) {
					LOGGER.error("get storage server error:{}",
							ex.getMessage());
				}
			}
			LOGGER.error("get storage server error:{}", e.getMessage());
		}
		
		if (storageServer == null || trackerServer == null) {
			return null;
		}
		return new EnhancedStorageClient(trackerServer, storageServer);
	}
	
	/**
	 * 获取存储器服务调用者
	 * 
	 * @return
	 * @throws IOException
	 */
	public EnhancedStorageClient getStorageClient() {
		return getStorageClient(null);
	}
	
	/*-------------------------------------------------------
	 *                     实现的服务
	 *------------------------------------------------------*/
	
	@Override
	public String uploadFile(String filePath) {
		return uploadFile(filePath, null);
	}
	
	@Override
	public String uploadFile(String filePath, FSExtraData extraData) {
		if (checkPath(filePath)) {
			return null;
		}
		File file = new File(filePath);
		return uploadFile(file, extraData);
	}
	
	@Override
	public String uploadFile(File file) {
		return uploadFile(file, null);
	}
	
	@Override
	public String uploadFile(File file, FSExtraData extraData) {
		if (file == null || !file.exists()) {
			return null;
		}
		try {
			return uploadFile(new FileInputStream(file), file.getName(),
					extraData);
		} catch (Exception e) {
			LOGGER.error("fastdfs upload file fail, error code: {}",
					e.fillInStackTrace());
			return null;
		}
	}
	
	@Override
	public String uploadFile(MultipartFile file) {
		return uploadFile(file, null);
	}
	
	@Override
	public String uploadFile(MultipartFile file, FSExtraData extraData) {
		if (file == null || file.isEmpty()) {
			return null;
		}
		try {
			return uploadFile(file.getInputStream(),
					file.getOriginalFilename(), extraData);
		} catch (IOException e) {
			LOGGER.error("fastdfs upload file fail, error code: {}",
					e.fillInStackTrace());
			return null;
		}
	}
	
	@Override
	public String uploadFile(InputStream fileStream, String fileName) {
		return uploadFile(fileStream, fileName, null);
	}
	
	@Override
	public String uploadFile(InputStream fileStream, String fileName,
			FSExtraData extraData) {
		if (isDisabled()) {
			return null;
		}
		
		EnhancedStorageClient storageClient = null;
		try {
			Map<String, String> metas = null;
			String groupName = null;
			if (extraData != null) {
				FDFSExtraData extras = (FDFSExtraData) extraData;
				metas = extras.getMetas();
				groupName = extras.getGroupName();
			}
			
			// 元数据
			NameValuePair[] metaList = null;
			
			// 获取文件字节
			byte[] file_buff = null;
			if (fileStream != null) {
				int len = fileStream.available();
				file_buff = new byte[len];
				fileStream.read(file_buff);
				fileStream.close();
			} else {
				return null;
			}
			
			// 默认的文件后缀
			String suffix = "jpg";
			if (fileName != null && fileName.length() > 0) {
				suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
				
				// 若未提供元数据，默认保存原文件名
				if (metas == null || metas.isEmpty()) {
					metaList = new NameValuePair[] { new NameValuePair(
							"fileName", fileName) };
				}
			}
			
			// Map参数转化为NameValuePair元数据数组
			if (metas != null && !metas.isEmpty()) {
				metaList = new NameValuePair[metas.size()];
				int i = 0;
				for (Entry<String, String> entry : metas.entrySet()) {
					metaList[i++] = new NameValuePair(entry.getKey(),
							entry.getValue());
				}
			}
			
			// 获取存储服务调用者上传文件
			storageClient = getStorageClient(groupName);
			String[] results = storageClient.upload_file(file_buff, suffix,
					metaList);
			if (results == null) {
				LOGGER.error("fastdfs upload file fail, error code: {}",
						storageClient.getErrorCode());
				return null;
			}
			
			// 上传的文件服务器路径处理
			String serverPath = results[0] + File.separator + results[1];
			serverPath = serverPath.replace("\\", "/");
			
			// 返回路径
			return serverPath;
		} catch (Exception e) {
			LOGGER.error(
					"FastDFSUtil uploadFile error:{}", e.fillInStackTrace());
			return null;
		} finally {
			closeResource(storageClient);
		}
	}
	
	@Override
	public byte[] downloadFile(String fileId) {
		return downloadFile(fileId, null);
	}
	
	@Override
	public byte[] downloadFile(String fileId, FSExtraData extraData) {
		if (isDisabled() || checkPath(fileId)) {
			return null;
		}
		EnhancedStorageClient storageClient = null;
		try {
			String[] splits = splitGroupAndPath(fileId);
			storageClient = getStorageClient(splits[0]);
			return storageClient.download_file(splits[0], splits[1]);
		} catch (Exception e) {
			LOGGER.error("FastDFSUtil downloadFile error:{}",
					e.fillInStackTrace());
			return null;
		} finally {
			closeResource(storageClient);
		}
	}
	
	@Override
	public void deleteFile(String fileId) {
		deleteFile(fileId, null);
	}
	
	@Override
	public void deleteFile(String fileId, FSExtraData extraData) {
		if (isDisabled() || checkPath(fileId)) {
			return;
		}
		EnhancedStorageClient storageClient = null;
		try {
			String[] splits = splitGroupAndPath(fileId);
			storageClient = getStorageClient(splits[0]);
			storageClient.delete_file(splits[0], splits[1]);
		} catch (Exception e) {
			LOGGER.error("FastDFSUtil deleteFile error:{}",
					e.fillInStackTrace());
		} finally {
			closeResource(storageClient);
		}
	}
	
	@Override
	public FSFileInfo getFileInfo(String fileId) {
		return getFileInfo(fileId, null);
	}
	
	@Override
	public FSFileInfo getFileInfo(String fileId, FSExtraData extraData) {
		FDFSFileInfo fi = new FDFSFileInfo();
		if (isDisabled() || checkPath(fileId)) {
			return fi;
		}
		
		EnhancedStorageClient sc = null;
		try {
			String[] splits = splitGroupAndPath(fileId);
			
			// 读取文件信息
			sc = getStorageClient(splits[0]);
			if (sc == null) {
				return fi;
			}
			
			// 设置组名和路径名
			fi.setGroupName(splits[0]);
			fi.setFilePath(splits[1]);
			
			if (sc.getTrackerServer() != null
					&& sc.getStorageServer() != null) {
				// 设置可访问到文件的某一跟踪服务器ip地址
				fi.setTrackerIpAddr(sc.getTrackerServer()
						.getInetSocketAddress().getHostString());
						
				// 设置可访问到文件的某一跟踪服务器http端口
				fi.setTrackerHttpPort(ClientGlobal.getG_tracker_http_port());
				
				// 设置跟踪器外网ip
				fi.setPublicNetworkIp(EnhancedTrackerGroup
						.getPublicNetworkIp(fi.getTrackerIpAddr()));
						
				// 读取元数据
				NameValuePair nvps[] = sc.get_metadata(splits[0], splits[1]);
				if (nvps != null && nvps.length > 0) {
					Map<String, String> meta = new HashMap<String, String>();
					for (NameValuePair nvp : nvps) {
						meta.put(nvp.getName(), nvp.getValue());
					}
					fi.setMetaData(meta);
				}
			}
			
			// 设置文件初始上传时的基本信息
			FileInfo info = sc.get_file_info(splits[0], splits[1]);
			if (info != null) {
				// 设置读取到的基本信息
				fi.setSourceIpAddr(info.getSourceIpAddr());
				fi.setFileSize(info.getFileSize());
				fi.setCreateTime(info.getCreateTimestamp());
				fi.setCrc32(info.getCrc32());
			}
		} catch (Exception e) {
			LOGGER.error("getFileInfo error:{}", e.fillInStackTrace());
		} finally {
			closeResource(sc);
		}
		return fi;
	}
	
	@Override
	public String getFullPath(String fileId) {
		if (isDisabled()) {
			return null;
		}
		
		// fileId不存在，返回任意一个tracker地址
		if (checkPath(fileId)) {
			return initUseAnyTracker().getFullPath();
		}
		
		if (EnhancedTrackerGroup.isEnableEasyRead()) {
			String[] splits = splitGroupAndPath(fileId);
			FDFSFileInfo fi = initUseAnyTracker();
			fi.setGroupName(splits[0]);
			fi.setFilePath(splits[1]);
			return fi.getFullPath();
		} else {
			return ((FDFSFileInfo) getFileInfo(fileId)).getFullPath();
		}
	}
	
	/*-------------------------------------------------------
	 *                        私有方法
	 *-------------------------------------------------------*/
	
	/*
	 * 获取fastdfs服务调用者
	 */
	private EnhancedTrackerClient getTrackerClient() {
		if (trackerClient == null) {
			synchronized (FDFSService.class) {
				if (trackerClient == null) {
					trackerClient = new EnhancedTrackerClient();
				}
			}
		}
		return trackerClient;
	}
	
	/*
	 * 获取跟踪器服务
	 * 任意返回一个
	 */
	private TrackerServer getTrackerServer() {
		try {
			return getTrackerClient().getConnection();
		} catch (Exception e) {
			LOGGER.error("getTrackerServer error:{}", e.getMessage());
		}
		return null;
	}
	
	/*
	 * 获取特定下标的跟踪器
	 */
	public TrackerServer getTrackerServer(int serverIndex) {
		try {
			return getTrackerClient().getConnection(serverIndex);
		} catch (Exception e) {
			LOGGER.error("getTrackerServer error:{}", e.getMessage());
		}
		return null;
	}
	
	/*
	 * 获取特定ip地址的跟踪器
	 */
	public TrackerServer getTrackerServer(String serverIpAddr) {
		try {
			return getTrackerClient().getConnection(serverIpAddr);
		} catch (Exception e) {
			LOGGER.error("getTrackerServer error:{}", e.getMessage());
		}
		return null;
	}
	
	/*
	 * 文件名（包含组名）非空验证
	 */
	private boolean checkPath(String filePath) {
		return filePath == null || filePath.length() == 0;
	}
	
	/*
	 * 从路径中分割组名和文件名
	 */
	private String[] splitGroupAndPath(String filePath) {
		// 字符\替换为/
		filePath = filePath.replace("\\", "/");
		
		// 如果路径以/开头，则剔除
		if (filePath.startsWith("/")) {
			filePath = filePath.substring(1);
		}
		
		String groupName = filePath.substring(0, filePath.indexOf("/"));
		String fileName = filePath.substring(filePath.indexOf("/") + 1);
		return new String[] { groupName, fileName };
	}
	
	/*
	 * 关闭资源
	 */
	private void closeResource(EnhancedStorageClient client) {
		if (client != null && client.getStorageServer() != null) {
			try {
				client.getStorageServer().close();
			} catch (IOException e) {
				LOGGER.error("fastdfs close server failed:{}",
						e.fillInStackTrace());
			}
		}
	}
	
	/*
	 * 检测系统是否禁用
	 */
	private boolean isDisabled() {
		if (!EnhancedTrackerGroup.isEnable()) {
			LOGGER.error("uploadFile error: fastdfs is disabled, "
					+ "check the enble attribute in the "
					+ "configuration file of 'fastdfs.properties'");
			return true;
		}
		return false;
	}
	
	/*
	 * 使用任意一个tracker初始化相关参数
	 */
	private FDFSFileInfo initUseAnyTracker() {
		FDFSFileInfo fi = new FDFSFileInfo();
		InetSocketAddress addr = EnhancedTrackerGroup
				.getTrackerGroup().tracker_servers[0];
		fi.setTrackerIpAddr(addr.getHostString());
		fi.setTrackerHttpPort(ClientGlobal.getG_tracker_http_port());
		fi.setPublicNetworkIp(EnhancedTrackerGroup
				.getPublicNetworkIp(fi.getTrackerIpAddr()));
		return fi;
	}
	
}
