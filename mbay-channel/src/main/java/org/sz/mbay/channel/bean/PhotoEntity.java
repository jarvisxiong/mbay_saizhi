package org.sz.mbay.channel.bean;

import java.io.Serializable;

/**
 * @Description: 图片实体bean,用于保存图片到数据库
 * @author han.han
 * @date 2014-9-9 下午3:09:09 
 *
 */
@SuppressWarnings("serial")
public class PhotoEntity implements Serializable {
	
	public PhotoEntity(){}
	public PhotoEntity(long id){
		this.id=id;
	}
	
	public PhotoEntity(String fileName,byte[] photoData){
		this.fileName=fileName;
		this.photoData=photoData;
	}
	
	/* 标识 */
	private long id;
	
	/* 文件名称 */
	private String fileName;
	
	/* 图片字节 */
	private byte[] photoData;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getPhotoData() {
		return photoData;
	}

	public void setPhotoData(byte[] photoData) {
		this.photoData = photoData;
	}
	
	 

}
