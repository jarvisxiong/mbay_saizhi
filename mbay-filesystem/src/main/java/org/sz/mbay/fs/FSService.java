package org.sz.mbay.fs;

import java.io.File;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件系统服务接口
 * 
 * @author jerry
 */
public interface FSService {
	
	/**
	 * 上传文件
	 * 
	 * @param filePath
	 *            绝对路径
	 * @return 存储路径
	 */
	String uploadFile(String filePath);
	
	/**
	 * 上传文件
	 * 
	 * @param filePath
	 *            绝对路径
	 * @param extraData
	 *            额外参数
	 * @return 存储路径
	 */
	String uploadFile(String filePath, FSExtraData extraData);
	
	/**
	 * 上传文件
	 * 
	 * @param file
	 *            文件对象
	 * @return 存储路径
	 */
	String uploadFile(File file);
	
	/**
	 * 上传文件
	 * 
	 * @param file
	 *            文件对象
	 * @param extraData
	 *            额外参数
	 * @return 存储路径
	 */
	String uploadFile(File file, FSExtraData extraData);
	
	/**
	 * 上传文件
	 * 
	 * @param file
	 *            文件对象
	 * @return 存储路径
	 */
	String uploadFile(MultipartFile file);
	
	/**
	 * 上传文件
	 * 
	 * @param file
	 *            文件对象
	 * @param extraData
	 *            额外参数
	 * @return 存储路径
	 */
	String uploadFile(MultipartFile file, FSExtraData extraData);
	
	/**
	 * 上传文件
	 * 
	 * @param fileStream
	 *            文件流
	 * @param fileName
	 *            文件名
	 * @return 存储路径
	 */
	String uploadFile(InputStream fileStream, String fileName);
	
	/**
	 * 上传文件
	 * 
	 * @param fileStream
	 *            文件流
	 * @param fileName
	 *            文件名
	 * @param extraData
	 *            额外参数
	 * @return 存储路径
	 * @throws Exception
	 */
	String uploadFile(InputStream fileStream, String fileName,
			FSExtraData extraData);
			
	/**
	 * 下载文件
	 * 
	 * @param fileId
	 *            文件标识
	 * @return 字节数组
	 * @throws Exception
	 */
	byte[] downloadFile(String fileId);
	
	/**
	 * 下载文件
	 * 
	 * @param fileId
	 *            文件标识
	 * @param extraData
	 *            额外参数
	 * @return 字节数组
	 */
	byte[] downloadFile(String fileId, FSExtraData extraData);
	
	/**
	 * 删除文件
	 * 
	 * @param fileId
	 *            文件标识
	 */
	void deleteFile(String fileId);
	
	/**
	 * 删除文件
	 * 
	 * @param fileId
	 *            文件标识
	 * @param extraData
	 *            额外参数
	 */
	void deleteFile(String fileId, FSExtraData extraData);
	
	/**
	 * 获取文件信息
	 * 
	 * @param fileId
	 *            文件标识
	 * @return 文件信息
	 */
	FSFileInfo getFileInfo(String fileId);
	
	/**
	 * 获取文件信息
	 * 
	 * @param fileId
	 *            文件标识
	 * @param extraData
	 *            额外参数
	 * @return 文件信息
	 */
	FSFileInfo getFileInfo(String fileId, FSExtraData extraData);
	
	/**
	 * 返回文件完整路径
	 * 
	 * enable_easy_read=true：直接使用tracker + fileId。tracker为配置文件中的随机一个
	 * enable_easy_read=false：根据fileId查找到准确的storage，再获取文件信息
	 * 
	 * @param fileId
	 *            文件标识
	 * @return
	 */
	String getFullPath(String fileId);
}
