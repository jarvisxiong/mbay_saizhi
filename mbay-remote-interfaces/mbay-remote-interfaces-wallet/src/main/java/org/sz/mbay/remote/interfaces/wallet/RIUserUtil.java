package org.sz.mbay.remote.interfaces.wallet;

import java.util.HashMap;
import java.util.Map;

import org.sz.mbay.remote.interfaces.wallet.base.RIResponse;
import org.sz.mbay.remote.interfaces.wallet.base.RIUtil;

/**
 * 用户接口辅助工具
 * 
 * @author jerry
 */
public class RIUserUtil extends RIUtil {
	
	private static final String requestUserLogin = new String(
			"/api/wallet/login/requestUserLogin.json");
	private static final String requestWalletAutoReg = new String(
			"/api/wallet/autoReg/requestWalletAutoReg.json");
	private static final String requestUserUpdatePassWord = new String(
			"/api/wallet/setPassword/requestUserUpdatePassWord.json");
	private static final String requestUserInfoByMobile = new String(
			"/api/wallet/walletUserInfo/requestUserInfoByMobile.json");
	private static final String requestUserIsExistByMobile = new String(
			"/api/wallet/userExist/requestUserIsExistByMobile.json");
			
	/**
	 * 登录
	 * 
	 * @param mobile
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	public static RIResponse requestUserLogin(String mobile,
			String pwd) throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put(MODULE_CODE, "A0013");
		map.put("mobile", mobile);
		map.put("password", pwd);
		return connect(requestUserLogin, map);
	}
	
	/**
	 * 注册
	 * 
	 * @param mobile
	 * @param tradeType
	 * @return
	 * @throws Exception
	 */
	public static RIResponse requestWalletAutoReg(String mobile,
			String regSource) throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put(MODULE_CODE, "A0001");
		map.put("mobile", mobile);
		map.put("regSource", regSource);
		return connect(requestWalletAutoReg, map);
	}
	
	/**
	 * 修改密码
	 * 
	 * @param mobile
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static RIResponse requestUserUpdatePassWord(String mobile,
			String password) throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put(MODULE_CODE, "A0007");
		map.put("mobile", mobile);
		map.put("password", password);
		return connect(requestUserUpdatePassWord, map);
	}
	
	/**
	 * 获取基本信息
	 * 
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	public static RIResponse requestUserInfoByMobile(String mobile)
			throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put(MODULE_CODE, "A0014");
		map.put("mobile", mobile);
		return connect(requestUserInfoByMobile, map);
	}
	
	/**
	 * 检测用户是否存在
	 * 
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	public static RIResponse requestUserIsExistByMobile(String mobile)
			throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put(MODULE_CODE, "A0015");
		map.put("mobile", mobile);
		return connect(requestUserIsExistByMobile, map);
	}
}
