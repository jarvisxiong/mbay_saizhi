package org.sz.mbay.channel.action.util;

public class FileUtil {
	private FileUtil(){};
	
	/**
	 * 获取文件扩展名
	 * @return
	 */
	public static String getFileExtension(String fileName){
		return fileName.substring(
				fileName.lastIndexOf(".") + 1, fileName.length());
	}

}
