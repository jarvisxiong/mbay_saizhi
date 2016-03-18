package org.sz.mbay.wallet.constant;


/**
 * 全局常量
 * 
 * @author jerry
 */
public class Global {
	
	/**
	 * cookie登陆用户名key
	 */
	public static final String LOGIN_MOBILE = "login_mobile";
	
	/**
	 * cookie登陆密码key
	 */
	public static final String LOGIN_PASSWORD = "login_password";
	
	// /**
	// * xmpp消息推送设置
	// */
	// public static class XmppConfig {
	//
	// /**
	// * 对应的应用名
	// */
	// public static final String APPLICATION = WalletConfig.XMPP_APPLICATION;
	//
	// /**
	// * apikey
	// */
	// public static final String APIKEY = WalletConfig.XMPP_APIKEY;
	//
	// /**
	// * 服务地址
	// */
	// public static final String URL = WalletConfig.XMPP_SERVER;
	// }
	
	/**
	 * session键
	 */
	public static class SessionKey {
		
		/**
		 * 登陆后保存在seession中的用户信息
		 */
		public static final String SESSION_USER = "session_user";
		
		/**
		 * 暂存的当前操作用户
		 */
		public static final String MOBILE = "mobile";
		
	}
	
	/**
	 * 文件上传
	 */
	public static class FileUploadSets {
		
		/**
		 * 图片格式
		 */
		public static final String IMAGE_TYPE = "jpg,jpeg,bmp,gif,png";
		
		/**
		 * 上传图片大小,5M（单位KB）
		 */
		public static final int UPLOAD_FILE_MAX_SIZE = 5 * 1024;
	}
}
