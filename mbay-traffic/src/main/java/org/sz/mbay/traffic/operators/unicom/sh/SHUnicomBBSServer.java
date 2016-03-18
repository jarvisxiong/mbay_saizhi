package org.sz.mbay.traffic.operators.unicom.sh;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.sz.mbay.common.util.HttpSupport;
import org.sz.mbay.operator.enums.TrafficType;
import org.sz.mbay.traffic.operators.TrafficRecharge;
import org.sz.mbay.traffic.operators.TrafficRechargeNotify;
import org.sz.mbay.traffic.response.ChargeResult;
import org.sz.mbay.traffic.util.Md5Encrypt;

import net.sf.json.JSONObject;

@Component("SHUnicomBBSServer")
public class SHUnicomBBSServer implements TrafficRecharge {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SHUnicomBBSServer.class);

	public ChargeResult charge(String mobile, TrafficType flowtype, int traffic,
			String ordernumber) {
		String url = "http://220.196.52.84/BssServer/Axon_key/51fd180999aa4f82bbe7f50334b713d8/";
		String productid = "";
		if (TrafficType.PROVINCE.equals(flowtype)) {
			productid = SHUnicomPackage.getProvincePackCodeByTraffic(traffic);
		} else {
			productid = SHUnicomPackage.getStatePackCodeByTraffic(traffic);
		}
		Map<String, String> map = new HashMap<String, String>();
		String timestamp = (System.currentTimeMillis() + "").substring(0, 10);
		String responsestr = SHUnicomPackage.agentid + productid + timestamp
				+ SHUnicomPackage.productpwd;
		String response = Md5Encrypt.md5(responsestr, "UTF-8").toLowerCase();
		Base64 base64 = new Base64();
		String backurl = new String(base64
				.encode("http://localhost:8180/mbaytraffic/traffic/backHandler"
						.getBytes()));
		map.put("agentid", SHUnicomPackage.agentid);
		map.put("timestamp", timestamp);
		map.put("response", response);
		map.put("jobid", SHUnicomPackage.jobid);
		map.put("productid", productid);
		map.put("tlimit", "1");
		map.put("mobile", mobile);
		map.put("backurl", backurl);
		String link = createUrl(url, map);
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("上海联通{}兑换连接{}", mobile, link);
		}
		String message = "";
		boolean success = false;
		try {
			String responsevalue = HttpSupport.connect(link);
			JSONObject object = JSONObject.fromObject(responsevalue);
			String pay = object.getString("pay");
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("上海联通{}流量充值结果：", mobile, pay);
			}
			if (pay == null) {
				message = "流量充值失败，请稍后重试！";
			} else {
				int flat = Integer.valueOf(pay);
				switch (flat) {
				case 0:
					message = "0流量充值成功";
					success = true;
					break;
				case 1:
					message = "1订购失败";
					break;
				case 2:
					message = "2您已订购此业务，无法重复订购";
					break;
				case 3:
					message = "3主套餐不能订购该业务";
					break;
				case 4:
					message = "4请求链接授权超时";
					break;
				case 8:
					message = "8产品互斥已订购过同类产品";
					break;
				case 98:
					message = "98非上海联通号码 ";
					break;
				case 99:
					message = "99取号失败,请重试";
					break;
				case 101:
					message = "101号码不存在";
					break;
				default:
					message = "未知代码：" + flat;
				}
			}
		} catch (Exception e) {
			LOGGER.error("请求上海联通借口异常", e.fillInStackTrace());
			message = "流量充值失败，请稍后再试！";
		}
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("{}返回充值结果：", mobile, message);
		}
		if (success) {
			TrafficRechargeNotify.successNotify(ordernumber);
			return new ChargeResult(ChargeResult.CHARGE_SUCCESS, message);
		} else {
			TrafficRechargeNotify.failNotify(ordernumber, message);
			return new ChargeResult(ChargeResult.CHARGE_FAILE, message);
		}
	}

	private String createUrl(String url, Map<String, String> map) {
		StringBuilder sb = new StringBuilder(100);
		sb.append(url + "?");
		Iterator<Map.Entry<String, String>> iterator = map.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entity = iterator.next();
			sb.append(entity.getKey() + "=" + entity.getValue());
			if (iterator.hasNext()) {
				sb.append("&");
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {

	}

	@Override
	public boolean isSupportTraffic(TrafficType ttype, int traffic) {
		if (ttype.equals(TrafficType.PROVINCE))
			return SHUnicomPackage
					.getProvincePackCodeByTraffic(traffic) != null;
		return SHUnicomPackage.getStatePackCodeByTraffic(traffic) != null;
	}

}
