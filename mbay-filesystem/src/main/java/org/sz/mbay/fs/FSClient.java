package org.sz.mbay.fs;

import java.io.File;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件系统管理对象
 * 
 * 策略模式
 * 
 * @author jerry
 */
public abstract class FSClient {
	
	// 某种实现了接口的文件管理服务
	protected FSService fsSerice;
	
	public FSClient() {
	}
	
	public FSClient(FSService fsSerice) {
		this.fsSerice = fsSerice;
	}
	
	public FSService getFsSerice() {
		return fsSerice;
	}
	
	public void setFsSerice(FSService fsSerice) {
		this.fsSerice = fsSerice;
	}
	
	public String uploadFile(String filePath) {
		return fsSerice.uploadFile(filePath);
	}
	
	public String uploadFile(String filePath, FSExtraData extraData) {
		return fsSerice.uploadFile(filePath, extraData);
	}
	
	public String uploadFile(File file) {
		return fsSerice.uploadFile(file);
	}
	
	public String uploadFile(File file, FSExtraData extraData) {
		return fsSerice.uploadFile(file, extraData);
	}
	
	public String uploadFile(MultipartFile file) {
		return fsSerice.uploadFile(file);
	}
	
	public String uploadFile(MultipartFile file, FSExtraData extraData) {
		return fsSerice.uploadFile(file, extraData);
	}
	
	public String uploadFile(InputStream fileStream, String fileName) {
		return fsSerice.uploadFile(fileStream, fileName);
	}
	
	public String uploadFile(InputStream fileStream, String fileName, FSExtraData extraData) {
		return fsSerice.uploadFile(fileStream, fileName, extraData);
	}
	
	public byte[] downloadFile(String fileId) {
		return fsSerice.downloadFile(fileId);
	}
	
	public byte[] downloadFile(String fileId, FSExtraData extraData) {
		return fsSerice.downloadFile(fileId, extraData);
	}
	
	public void deleteFile(String fileId) {
		fsSerice.deleteFile(fileId);
	}
	
	public void deleteFile(String fileId, FSExtraData extraData) {
		fsSerice.deleteFile(fileId, extraData);
	}
	
	public FSFileInfo getFileInfo(String fileId) {
		return fsSerice.getFileInfo(fileId);
	}
	
	public FSFileInfo getFileInfo(String fileId, FSExtraData extraData) {
		return fsSerice.getFileInfo(fileId, extraData);
	}
}
