package org.sz.mbay.remote.interfaces.wallet;

import java.util.HashMap;
import java.util.Map;

import org.sz.mbay.remote.interfaces.wallet.base.RIResponse;
import org.sz.mbay.remote.interfaces.wallet.base.RIUtil;

/**
 * 交易辅助工具
 * 
 * @author jerry
 */
public class RITradeRecordUtil extends RIUtil {
	
	private static final String getTradeRecordList = new String(
			"/api/wallet/tradeRecord/getTradeRecordList.json");
	private static final String requestGetTradeRecordBySerialNum = new String(
			"/api/wallet/getTradeRecord/requestGetTradeRecordBySerialNum.json");
			
	/**
	 * 获取交易列表
	 * 
	 * @param mobile
	 * @param tradeType
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public static RIResponse getTradeRecordList(
			String mobile,
			String tradeType,
			int pageNum,
			int pageSize) throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put(MODULE_CODE, "A0016");
		map.put("mobile", mobile);
		map.put("tradeType", tradeType);
		map.put("pageNum", String.valueOf(pageNum));
		map.put("pageSize", String.valueOf(pageSize));
		return connect(getTradeRecordList, map);
	}
	
	/**
	 * 查询实体
	 * 
	 * @param snumber
	 * @return
	 * @throws Exception
	 */
	public static RIResponse requestGetTradeRecordBySerialNum(String snumber)
			throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put(MODULE_CODE, "A0017");
		map.put("serialNumber", snumber);
		return connect(requestGetTradeRecordBySerialNum, map);
	}
}
