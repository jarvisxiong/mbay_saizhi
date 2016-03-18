package org.sz.mbay.fs.fastdfs;

import java.io.File;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.sz.mbay.fs.FSFileInfo;

/**
 * 文件信息
 * 
 * @author jerry
 */
public class FDFSFileInfo extends FSFileInfo {

	// 组名
	private String groupName;

	// 文件路径（不包含组名）
	private String filePath;

	// 某一时刻可用跟踪服务器ip地址(不含端口)
	private String trackerIpAddr;

	// 跟踪服务器http端口
	private Integer trackerHttpPort;

	// 跟踪器外网ip（含端口）
	private String publicNetworkIp;

	// 源存储服务器ip地址
	private String sourceIpAddr;

	// 文件大小
	private Long fileSize;

	// 文件创建时间
	private Date createTime;

	// 冗余校验码
	private Long crc32;

	// 元数据
	private Map<String, String> metaData;

	/**
	 * 获取文件在服务器的全路径
	 * 
	 * @return
	 */
	public String getFullPath() {
		String addr = null;
		if (trackerIpAddr != null && trackerIpAddr.length() > 0) {
			addr = trackerIpAddr + ":" + trackerHttpPort;
		}
		if (publicNetworkIp != null && publicNetworkIp.length() > 0) {
			addr = publicNetworkIp;
		}
		if (addr != null) {
			String path = "http://" + addr + File.separator;
			if (StringUtils.isNotEmpty(groupName)) {
				path += groupName + File.separator;
				if (StringUtils.isNotEmpty(filePath)) {
					path += filePath;
				}
			}
			return path.replace("\\", "/");
		}
		return StringUtils.EMPTY;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getSourceIpAddr() {
		return sourceIpAddr;
	}

	public void setSourceIpAddr(String sourceIpAddr) {
		this.sourceIpAddr = sourceIpAddr;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getCrc32() {
		return crc32;
	}

	public void setCrc32(Long crc32) {
		this.crc32 = crc32;
	}

	public Map<String, String> getMetaData() {
		return metaData;
	}

	public void setMetaData(Map<String, String> metaData) {
		this.metaData = metaData;
	}

	public String getTrackerIpAddr() {
		return trackerIpAddr;
	}

	public void setTrackerIpAddr(String trackerIpAddr) {
		this.trackerIpAddr = trackerIpAddr;
	}

	public Integer getTrackerHttpPort() {
		return trackerHttpPort;
	}

	public void setTrackerHttpPort(Integer trackerHttpPort) {
		this.trackerHttpPort = trackerHttpPort;
	}

	public String getPublicNetworkIp() {
		return publicNetworkIp;
	}

	public void setPublicNetworkIp(String publicNetworkIp) {
		this.publicNetworkIp = publicNetworkIp;
	}

	@Override
	public String toString() {
		return "FDFSFileInfo ["
				+ (groupName != null ? "groupName=" + groupName + ", " : "")
				+ (filePath != null ? "filePath=" + filePath + ", " : "")
				+ (trackerIpAddr != null ? "trackerIpAddr=" + trackerIpAddr
						+ ", " : "")
				+ (trackerHttpPort != null ? "trackerHttpPort="
						+ trackerHttpPort + ", " : "")
				+ (publicNetworkIp != null ? "publicNetworkIp="
						+ publicNetworkIp + ", " : "")
				+ (sourceIpAddr != null ? "sourceIpAddr=" + sourceIpAddr + ", "
						: "")
				+ (fileSize != null ? "fileSize=" + fileSize + ", " : "")
				+ (createTime != null ? "createTime=" + createTime + ", " : "")
				+ (crc32 != null ? "crc32=" + crc32 + ", " : "")
				+ (metaData != null ? "metaData=" + metaData + ", " : "")
				+ (getFullPath() != null ? "getFullPath()=" + getFullPath()
						: "") + "]";
	}

}
