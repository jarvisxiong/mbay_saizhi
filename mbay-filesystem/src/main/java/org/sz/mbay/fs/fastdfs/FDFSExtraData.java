package org.sz.mbay.fs.fastdfs;

import java.util.Map;

import org.sz.mbay.fs.FSExtraData;

/**
 * 文件上传、下载等操作额外参数
 *
 * @author jerry
 */
public class FDFSExtraData extends FSExtraData {
	
	// 组名/卷名
	private String groupName;
	
	// 文件元数据
	private Map<String, String> metas;
	
	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public Map<String, String> getMetas() {
		return metas;
	}
	
	public void setMetas(Map<String, String> metas) {
		this.metas = metas;
	}
}
