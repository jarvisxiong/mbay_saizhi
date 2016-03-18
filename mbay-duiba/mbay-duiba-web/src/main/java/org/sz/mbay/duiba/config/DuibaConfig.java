package org.sz.mbay.duiba.config;

import java.util.ResourceBundle;

/**
 * 配置文件类
 * 
 * @author frank.zong
 */
public class DuibaConfig{
	
	private DuibaConfig(){}
	
	private static ResourceBundle bundle;
	
	/**
	 * 获取appkey
	 */
	public static String getAppKey() {
		return getBundle().getString("appkey");
	}
	
	/**
	 * 获取appsecret
	 */
	public static String getAppSecret() {
		return getBundle().getString("appsecret");
	}
	
	/**
	 * 获取账户
	 */
	public static String getAccount(){
		return getBundle().getString("getAccount");
	}
	
	/**
	 * 扣除积分
	 */
	public static String reduce(){
		return getBundle().getString("reduce");
	}
	
	/**
	 * 回滚积分
	 */
	public static String rollback(){
		return getBundle().getString("rollback");
	}
	
	/**
	 * 获取订单是否已经执行过操作的状态
	 */
	public static String getState(){
		return getBundle().getString("getState");
	}
	
	/**
	 * 更改订单状态
	 */
	public static String updateState(){
		return getBundle().getString("updateState");
	}
	
	private static ResourceBundle getBundle(){
		if(bundle == null){
			bundle = ResourceBundle.getBundle("duiba");
			if (bundle == null) {  
				System.out.println("[duiba.properties] is not found!");
		    }
		}
		return bundle;
	}
}