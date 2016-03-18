package org.sz.mbay.remote.interfaces.wallet;

import org.sz.mbay.remote.interfaces.wallet.base.RIResponse;

/**
 * Unit test for simple App.
 */
public class AppTest {
	
	public static void main(String[] args) {
		// RIResponse resp = null;
		// try {
		// resp = RIMBAccountUtil.requestUserEnterOfAccount(
		// "15251778128", 1, "PROMOTION_MBAY", "111",
		// null);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// System.out.println(resp);
		
		// try {
		// RIResponse resp = RITradeRecordUtil.getTradeRecordList("18105191646",
		// "TASK_TRANSFER_MB", 1, 10);
		// System.out.println(resp);
		// if (resp.getData().getJSONArray("result").size() > 0) {
		// System.out.println("xxx");
		// }
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		
		// try {
		// RIResponse resp =
		// RIUserUtil.requestUserIsExistByMobile("13512312312");
		// System.out.println(resp);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		
		// try {
		// RIResponse resp = RITradeRecordUtil
		// .getTradeRecordList("18105191646", null, 1, 10);
		// System.out.println(resp);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		RIResponse resp2;
		try {
			resp2 = RIMBAccountUtil.requestUserGetMBQty("15251778128");
			System.out.println(resp2);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		try {
			RIResponse resp = RIMBAccountUtil.requestUserEnterOfAccount(
					"15251778128", 4, "TRAFFIC_RED_TRAFFIC", "222", null);
			System.out.println(resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			RIResponse resp = RIMBAccountUtil.requestUserOutOfAccount(
					"15251778128", 3.0, "TRAFFIC_RED_TRAFFIC", "222", null);
			System.out.println(resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// try {
		// RIResponse resp =
		// RITradeRecordUtil.requestGetTradeRecordBySerialNum("150918160506800595");
		// System.out.println(resp);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}
}
