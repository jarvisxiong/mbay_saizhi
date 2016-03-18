package org.sz.mbay.duiba.servlet;

import java.util.Arrays;

import org.sz.mbay.duiba.CreditAuditParams;
import org.sz.mbay.duiba.CreditTool;
import org.sz.mbay.duiba.http.HttpSupport;

import net.sf.json.JSONObject;

/**
 * 审核接口（暂不用）
 * @author frank.zong
 *
 */
public class Audit {
	
	public static void audit(String orderNum){
		String appkey = "4SWHaDcZ835efQuMw7scri2ve7xP";
		String appSecret = "3KkmmRQP25eJ8UBV92a5UoESqGes";
		CreditTool tool = new CreditTool(appkey, appSecret);
		CreditAuditParams params = new CreditAuditParams();
		params.setPassOrderNums(Arrays.asList(orderNum));//此处的订单号为兑吧的订单号，而非开发者的订单号
		//params.setRejectOrderNums(Arrays.asList("3","4"));//此处的订单号为兑吧的订单号，而非开发者的订单号
		String url = tool.buildCreditAuditRequest(params);
		try {
			String response = HttpSupport.httpGet(url);
			System.out.println("response -> " + response);
			JSONObject json = JSONObject.fromObject(response);
			JSONObject detail = JSONObject.fromObject(json.get("details"));
			JSONObject object = JSONObject.fromObject(detail.get(orderNum));
			Boolean success = object.getBoolean("success");
			if(!success){
				String message = object.getString("message");
				System.out.println("error message -> " + message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}