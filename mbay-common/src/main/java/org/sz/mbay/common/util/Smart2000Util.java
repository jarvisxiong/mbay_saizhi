package org.sz.mbay.common.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.longmai.Smart2000.Smart2000Lib;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加密狗工具
 * 
 * @author jerry
 */
public final class Smart2000Util {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(Smart2000Util.class);
			
	private static final String CONF_FILE = "smart2000.properties";
	
	// 应用程序标识
	private static String APPID;
	
	// 4组用户密码
	private static int[] PSWD = new int[4];
	
	// 128组模块许可证
	private static Map<Integer, Integer> MD_LCODE = new HashMap<>();
	
	// 加密狗对象
	private static final Smart2000Lib DOG = Smart2000Lib.getInstance();
	
	/**
	 * 读取配置
	 */
	static {
		Properties props = PropertiesUtil.loadClasspathFile(CONF_FILE);
		Iterator<Object> keys = props.keySet().iterator();
		String key = null;
		String val = null;
		String propName = null;
		while (keys.hasNext()) {
			key = (String) keys.next();
			val = props.getProperty(key);
			
			if (key.startsWith("appID")) {
				APPID = val;
			}
			
			propName = "pswd";
			if (key.startsWith(propName)) {
				int index = Integer.parseInt(key.substring(
						key.indexOf(propName) + propName.length()));
				PSWD[index] = getInteger(val);
			}
			
			propName = "moduleLicenseCode";
			if (key.startsWith(propName)) {
				int index = Integer.parseInt(key.substring(
						key.indexOf(propName) + propName.length()));
				MD_LCODE.put(index, getInteger(val));
			}
		}
	}
	
	private static int getInteger(String str) {
		if (StringUtils.isEmpty(str)) {
			return 0;
		}
		if (str.startsWith("0x")) {
			return new BigInteger(str.substring(2), 16).intValue();
		} else {
			return Integer.parseInt(str);
		}
	}
	
	/**
	 * 验证合法性
	 * 
	 * @return
	 */
	public static boolean verify() {
		int keyHandle = verifyInner();
		return keyHandle != -1;
	}
	
	/*
	 * 验证并返回句柄
	 */
	private static int verifyInner() {
		int rtn;
		
		// 加密狗句柄数组，最多8个
		int[] keyHandles = new int[8];
		
		// 已连接加密狗数量
		int[] keyNumber = new int[1];
		
		// 查找加密狗
		rtn = DOG.Find(APPID, keyHandles, keyNumber);
		if (rtn != 0) {
			LOGGER.info("Smart2000 Find Failed, Error code = {}",
					DOG.GetLastError());
			return -1;
		}
		LOGGER.info("Smart2000 Find Success");
		
		// 二次认证A组密钥对
		int[] requestFromKey = new int[1];
		
		// 打开加密狗
		rtn = DOG.Open(keyHandles[0], PSWD[0], PSWD[1], PSWD[2], PSWD[3],
				requestFromKey);
		if (rtn != 0) {
			LOGGER.info("Smart2000 Open Failed, Error code = {}",
					DOG.GetLastError());
			closeDog(keyHandles[0]);
			return -1;
		}
		LOGGER.info("Smart2000 Open Success");
		
		// 二次验证
		int response = requestFromKey[0];
		rtn = DOG.Verify(keyHandles[0], response);
		if (rtn != 0) {
			LOGGER.info("Smart2000 Verify Failed, Error code = {}",
					DOG.GetLastError());
			closeDog(keyHandles[0]);
			return -1;
		}
		LOGGER.info("Smart2000 Verify Success");
		
		return keyHandles[0];
	}
	
	/*
	 * 关闭加密锁
	 */
	private static void closeDog(int keyHandle) {
		int rtn = DOG.Close(keyHandle);
		if (rtn != 0) {
			LOGGER.info("Smart2000 Close Failed, error Code = {}",
					DOG.GetLastError());
		}
		LOGGER.info("Smart2000 Close Success");
	}
	
	/**
	 * 获取相关数据，由数据对应的标志决定，多个标志可以用按位或组合‘|’
	 * 
	 * @param dataFlags
	 * @return
	 */
	public static Smart2000Data getData(int dataFlags) {
		int keyHandler = verifyInner();
		if (keyHandler == -1) {
			return null;
		}
		
		Smart2000Data data = new Smart2000Data(dataFlags);
		
		// 用户序列号
		if ((dataFlags & Smart2000Data.UID) != 0) {
			byte[] UID = new byte[32];
			int rtn = DOG.GetUid(keyHandler, UID);
			if (rtn != 0) {
				LOGGER.info("Smart2000 Get ID Failed, Error Code = {}",
						DOG.GetLastError());
			} else {
				data.uid = new String(UID);
			}
		}
		
		// 模块
		if ((dataFlags & Smart2000Data.MODULE) != 0) {
			MD_LCODE.forEach((key, val) -> {
				if (val != 0) {
					byte[] md = new byte[32];
					int rtn = DOG.ModuleVerify(keyHandler, val, md);
					if (rtn != 0) {
						LOGGER.info(
								"Smart2000 ModuleVerify Failed, error code = {}",
								DOG.GetLastError());
					} else {
						data.module[key] = md;
					}
				}
			});
		}
		
		// 文件存储区
		if ((dataFlags & Smart2000Data.FILE_STORAGE) != 0) {
			int nLen = 8192;
			byte[] pData = new byte[nLen];
			int rtn = DOG.ReadFileStorage(keyHandler, 0, nLen, pData);
			if (rtn != 0) {
				LOGGER.info(
						"Smart2000 ReadFileStorage Failed, Error code = {}",
						DOG.GetLastError());
			} else {
				data.fileStorage = pData;
			}
		}
		
		// 分页存储区
		if ((dataFlags & Smart2000Data.PAGE_STORAGE) != 0) {
			int rtn;
			int len = 1024 * 2;
			byte[] xData;
			for (int i = 0; i < 8; i++) {
				xData = new byte[len];
				rtn = DOG.ReadPage(keyHandler, i, 0, len, xData);
				if (rtn != 0) {
					LOGGER.info("Smart2000 ReadPage {} Failed, Error code = {}",
							i, DOG.GetLastError());
				} else {
					data.pageStorage[i] = xData;
				}
			}
		}
		
		closeDog(keyHandler);
		return data;
	}
	
	/**
	 * smart2000 数据结构
	 * 
	 * 默认编码gbk
	 * 
	 * @author jerry
	 */
	public static class Smart2000Data {
		
		/** 是否获取硬件序列号 */
		public static final int UID = 0x01;
		
		/** 是否获取模块数据（共128个模块） */
		public static final int MODULE = 0x02;
		
		/** 文件存储区（共8K） */
		public static final int FILE_STORAGE = 0x04;
		
		/** 分页存储区（共8页，每页2K） */
		public static final int PAGE_STORAGE = 0x08;
		
		// 默认编码
		private static final String CHARSET = "gbk";
		
		// 指定获取哪些数据的标识
		private int flag;
		
		// 硬件序列号
		private String uid;
		
		// 模块数据
		private byte[][] module;
		
		// 文件存储区
		private byte[] fileStorage;
		
		// 分页存储区
		private byte[][] pageStorage;
		
		private Smart2000Data(int dataFlags) {
			this.flag = dataFlags;
			if ((dataFlags & MODULE) != 0) {
				module = new byte[128][32];
			}
			if ((dataFlags & FILE_STORAGE) != 0) {
				fileStorage = new byte[1024 * 8];
			}
			if ((dataFlags & PAGE_STORAGE) != 0) {
				pageStorage = new byte[8][1024 * 2];
			}
		}
		
		/**
		 * 获取哪些数据的标识
		 * 
		 * @return
		 */
		public int getFlag() {
			return flag;
		}
		
		/**
		 * 获取用户序列号
		 * 
		 * @return
		 */
		public String getUID() {
			return uid;
		}
		
		/**
		 * 获取所有模块数据，字节形式
		 * 
		 * @return
		 */
		public byte[][] getAllModuleBytes() {
			return module;
		}
		
		/**
		 * 获取下标对应的模块数据，字节形式
		 * 
		 * @param index
		 * @return
		 */
		public byte[] getModuleBytes(int index) {
			if (module == null || index < 0 || index >= module.length) {
				return null;
			}
			return module[index];
		}
		
		/**
		 * 获取下标对应的模块数据，字符串形式，使用默认编码
		 * 
		 * @param index
		 * @return
		 */
		public String getModuleString(int index) {
			return getModuleString(index, CHARSET);
		}
		
		/**
		 * 获取下标对应的模块数据，字符串形式
		 * 
		 * @param index
		 * @param charset
		 * @return
		 */
		public String getModuleString(int index, String charset) {
			return bytes2String(getModuleBytes(index), charset);
		}
		
		/**
		 * 获取所有模块数据，字符串形式，使用默认编码
		 * 
		 * @return
		 */
		public String[] getAllModuleStrings() {
			return getAllModuleStrings(CHARSET);
		}
		
		/**
		 * 获取所有模块数据，字符串形式
		 * 
		 * @param charset
		 * @return
		 */
		public String[] getAllModuleStrings(String charset) {
			if (module == null) {
				return null;
			}
			String[] data = new String[128];
			for (int i = 0; i < data.length; i++) {
				data[i] = bytes2String(module[i], charset);
			}
			return data;
		}
		
		/**
		 * 获取文件存储区数据，字节形式
		 * 
		 * @return
		 */
		public byte[] getFileStorageBytes() {
			return fileStorage;
		}
		
		/**
		 * 获取文件存储区数据，字符串形式
		 * 
		 * @return
		 */
		public String getFileStorageString() {
			return getFileStorageString(CHARSET);
		}
		
		/**
		 * 获取文件存储区数据，字符串形式
		 * 
		 * @return
		 */
		public String getFileStorageString(String charset) {
			return bytes2String(fileStorage, charset);
		}
		
		/**
		 * 获取所有的分页存储区数据
		 * 
		 * @return
		 */
		public byte[][] getAllPageStorageBytes() {
			return pageStorage;
		}
		
		/**
		 * 获取下标对应的分页存储区数据
		 * 
		 * @param index
		 * @return
		 */
		public byte[] getPageStorageBytes(int index) {
			if (pageStorage == null || index < 0
					|| index >= pageStorage.length) {
				return null;
			}
			return pageStorage[index];
		}
		
		/**
		 * 获取指定分页存储区数据，字符串形式，使用默认编码
		 * 
		 * @param index
		 * @return
		 */
		public String getPageStorageString(int index) {
			return getPageStorageString(index, CHARSET);
		}
		
		/**
		 * 获取指定分页存储区数据，字符串形式
		 * 
		 * @param index
		 * @param charser
		 * @return
		 */
		public String getPageStorageString(int index, String charset) {
			return bytes2String(getPageStorageBytes(index), charset);
		}
		
		/**
		 * 获取所有分页数据，字符串形式，使用默认编码
		 * 
		 * @return
		 */
		public String[] getAllPageStorageString() {
			return getAllPageStorageString(CHARSET);
		}
		
		/**
		 * 获取所有分页数据，字符串形式
		 * 
		 * @param charset
		 * @return
		 */
		public String[] getAllPageStorageString(String charset) {
			if (pageStorage == null) {
				return null;
			}
			String[] data = new String[8];
			for (int i = 0; i < data.length; i++) {
				data[i] = bytes2String(pageStorage[i], charset);
			}
			return data;
		}
		
		/*
		 * 字节转字符串
		 */
		private static String bytes2String(byte[] bys, String charset) {
			if (bys == null) {
				return null;
			}
			try {
				return new String(bys, charset).trim();
			} catch (UnsupportedEncodingException e) {
				LOGGER.error(e.getMessage());
				return new String(bys).trim();
			}
		}
		
	}
}
