package org.sz.mbay.remote.interfaces.wallet;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.sz.mbay.remote.interfaces.wallet.base.RIResponse;
import org.sz.mbay.remote.interfaces.wallet.base.RIUtil;

/**
 * 账户接口辅助工具
 * 
 * @author jerry
 */
public class RIMBAccountUtil extends RIUtil {
	
	private static final String requestUserGetMBQty = new String(
			"/api/wallet/getMBQty/common/requestUserGetMBQty.json");
	private static final String requestUserEnterOfAccount = new String(
			"/api/wallet/getMBQty/common/requestUserEnterOfAccount.json");
	private static final String requestUserOutOfAccount = new String(
			"/api/wallet/getMBQty/common/requestUserOutOfAccount.json");
	private static final String requestUserEnterOfAccountTraffic = new String(
			"/api/wallet/getMBQty/traffic/requestUserEnterOfAccount.json");
			
	/**
	 * 获取账户余额
	 * 
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	public static RIResponse requestUserGetMBQty(String mobile)
			throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put(MODULE_CODE, "A0002");
		map.put("mobile", mobile);
		return connect(requestUserGetMBQty, map);
	}
	
	/**
	 * 通用 - 进账
	 * 
	 * @param mobile
	 * @param balance
	 * @return
	 * @throws Exception
	 */
	public static RIResponse requestUserEnterOfAccount(
			String mobile,
			double balance,
			String tradeType,
			String relatedNumber,
			String description) throws Exception {
		RIResponse resp = RIUserUtil.requestWalletAutoReg(mobile, tradeType);
		if (!resp.isStatus()) {
			return resp;
		}
		
		String bal = new BigDecimal(balance).setScale(2,
				RoundingMode.HALF_UP).toString();
		Map<String, String> map = new HashMap<>();
		map.put(MODULE_CODE, "A0003");
		map.put("mobile", mobile);
		map.put("balance", bal);
		map.put("tradeType", tradeType);
		map.put("description", description);
		map.put("relatedNumber", relatedNumber);
		return connect(requestUserEnterOfAccount, map);
	}
	
	/**
	 * 通用 - 出账
	 * 
	 * @param mobile
	 * @param balance
	 * @return
	 * @throws Exception
	 */
	public static RIResponse requestUserOutOfAccount(
			String mobile,
			double balance,
			String tradeType,
			String relatedNumber,
			String description) throws Exception {
		RIResponse resp = RIUserUtil.requestWalletAutoReg(mobile, tradeType);
		if (!resp.isStatus()) {
			return resp;
		}
		
		String bal = new BigDecimal(balance).setScale(2,
				RoundingMode.HALF_UP).toString();
		Map<String, String> map = new HashMap<>();
		map.put(MODULE_CODE, "A0004");
		map.put("mobile", mobile);
		map.put("balance", bal);
		map.put("tradeType", tradeType);
		map.put("description", description);
		map.put("relatedNumber", relatedNumber);
		return connect(requestUserOutOfAccount, map);
	}
	
	/**
	 * 流量红包摇一摇
	 * 
	 * @param mobile
	 * @param relatedNumber
	 * @param description
	 * @return
	 * @throws Exception
	 */
	public static RIResponse requestUserEnterOfAccountTraffic(String mobile,
			String relatedNumber,
			String description) throws Exception {
		RIResponse resp = RIUserUtil.requestWalletAutoReg(mobile,
				"TRAFFIC_RED_TRAFFIC");
		if (!resp.isStatus()) {
			return resp;
		}
		
		Map<String, String> map = new HashMap<>();
		map.put(MODULE_CODE, "A0018");
		map.put("mobile", mobile);
		map.put("description", description);
		map.put("relatedNumber", relatedNumber);
		return connect(requestUserEnterOfAccountTraffic, map);
	}
}
