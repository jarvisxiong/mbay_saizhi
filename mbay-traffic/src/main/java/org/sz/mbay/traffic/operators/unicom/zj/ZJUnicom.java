package org.sz.mbay.traffic.operators.unicom.zj;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.sz.mbay.operator.enums.TrafficType;
import org.sz.mbay.traffic.bean.Trafficcard;
import org.sz.mbay.traffic.operators.TrafficRecharge;
import org.sz.mbay.traffic.response.ChargeResult;
import org.sz.mbay.traffic.response.ZJUnicomResponseSupport;
import org.sz.mbay.traffic.util.Md5Encrypt;

/**
 * 浙江联通流量兑换
 * 
 * @author ONECITY
 * 
 */
@Component("ZJUnicom")
public class ZJUnicom implements TrafficRecharge {
	static final Logger LOGGER = LoggerFactory.getLogger(ZJUnicom.class);
	// /public String gateway = "http://211.157.96.13:8080/szservice/zjwcdma?";
	public String gateway = "http://124.160.11.211:7001/crmService/chinaUnicom/cardCharge?";
	// @Autowired
	// private TrafficcardDao trafficcarddao;

	public ChargeResult charge(String mobile, TrafficType flowtype, int traffic,
			String ordernumber) {
		// int recharged = 0;
		List<Trafficcard> trafficcards = findTrafficcardsByTraffic(traffic);
		if (trafficcards == null || trafficcards.size() == 0) {
			// 无对应的流量充值包
			LOGGER.info(mobile + "无对应的流量充值包");
			return new ChargeResult(ChargeResult.CHARGE_FAILE, "无对应的流量充值包");
		}
		Trafficcard trafficcard = trafficcards.remove(0);
		ZJAPIResult zjapiresult = rechargeTraffic(mobile, trafficcard);
		if (!zjapiresult.success) {// 未请求到浙江联通
			switch (zjapiresult.code) {
			// 请求连接超时
			case ZJAPIResult.CONNECTIONTIMEOUT:
				return new ChargeResult(ChargeResult.CHARGE_FAILE, "请求运营商链接超时");
			// 读取服务器超时
			case ZJAPIResult.READTIMEOUT:
				// TODO 这里应把此兑换信息扔进后续处理程序，以便完成充值
				return new ChargeResult(ChargeResult.CHARGE_FAILE, "请求运营商读取超时");
			}
		}
		ZJAPIResult.ZJResult zjresult = zjapiresult.zjresult;
		String code = zjresult.code;
		String status = zjresult.status;
		String desc = zjresult.desc;
		/*
		 * 00 缴费卡缴费成功 01 系统故障 02 缴费账户不存在 03 缴费账户存在但是不能用于缴费 04 帐务系统操作超时 05 不在缴费时段
		 * 06 缴费操作失败 07 卡不存在 08 密码错误 09 卡已过期 10 卡已经注销 11 充值中 12 卡已充值 13 非激活卡 14
		 * 余额不足
		 */
		String errorcode = "01,02,03,04,05,06,07,08,09,10,12,13,14";
		if (errorcode.indexOf(code) > -1) {
			if ("07,08,09,10,12,13,14".indexOf(code) > -1) {
				trafficcard.setRemark(desc);
				trafficcard.setStatus(Trafficcard.UNABLEUSE);
				// trafficcarddao.updateTrafficcard(trafficcard);
			}
		}
		if ("00".equals(code) || "11".equals(code)) {
			trafficcard.setStatus(Trafficcard.USED);
			// trafficcarddao.updateTrafficcard(trafficcard);
			/**
			 * for (Trafficcard card : trafficcards) { ZJAPIResult apiresult =
			 * rechargeTraffic(mobile, card); if (apiresult.success) { //String
			 * icode = apiresult.zjresult.code; //String idesc = zjresult.desc;
			 * /** if (!"00".equals(icode) && !"11".equals(icode)) {
			 * card.setRemark(idesc); card.setStatus(Trafficcard.UNABLEUSE);
			 * trafficcarddao.updateTrafficcard(trafficcard); }else{
			 * card.setStatus(Trafficcard.USED);
			 * trafficcarddao.updateTrafficcard(trafficcard);
			 * 
			 * //}
			 * 
			 * }
			 * 
			 * }
			 ****/
		}
		return ZJUnicomResponseSupport.rechargeResponse(code,
				trafficcard.getCardnum());

		// /return ResponseSupport.TRAFFIC_RECHARGEING;
	}

	/**
	 * 根据充值流量查询兑换卡，若兑换卡不足，则返回NULL
	 * 
	 * @param traffic
	 */
	private List<Trafficcard> findTrafficcardsByTraffic(int traffic) {
		return null;
		// return this.trafficcarddao.findTrafficcardsByTraffic(traffic);
	}

	public static void main(String[] args) {
		ZJUnicom unicom = new ZJUnicom();
		Trafficcard card = new Trafficcard();
		card.setId(20140821);
		card.setCardnum("058111377936");
		card.setPassword("61632329");
		card.setMoney("1");
		unicom.rechargeTraffic("18626880607", card);
	}

	/**
	 * 对接流量充值接口充值
	 * 
	 * @param trafficcards
	 * @return 返回成功充值流量
	 */
	public ZJAPIResult rechargeTraffic(String mobile, Trafficcard trafficcard) {
		HttpURLConnection connection = connectionToZJ(mobile, trafficcard);
		if (connection == null) {
			// 请求服务器超时
			return ZJAPIResult.connectionTimeOut();
		}
		InputStream input = null;
		try {
			input = connection.getInputStream();
		} catch (Exception e) {
			LOGGER.error("服务器返回数据超时");
			return ZJAPIResult.readeTimeOut();

		}
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(input);
		} catch (DocumentException e) {
		}
		Element root = document.getRootElement();
		String status = root.elementTextTrim("status");
		String failCode = root.elementTextTrim("code");
		String failDesc = root.elementTextTrim("desc");
		LOGGER.debug("充值返回值：{}", status + failCode + failDesc);
		ZJAPIResult result = new ZJAPIResult(true);
		ZJAPIResult.ZJResult zjresult = result.new ZJResult();
		zjresult.code = failCode;
		zjresult.status = status;
		zjresult.desc = failDesc;
		result.zjresult = zjresult;

		return result;

	}

	private HttpURLConnection connectionToZJ(String mobile,
			Trafficcard trafficcard) {
		String key = "tfty766guj7cji989jhggyj7uh7yg5t65";// 密钥
		String supplierId = "712456636";// 供应商编码（后期由浙江联通提供）
		String tId = trafficcard.getId() + "";// 事务标识
		String ts = System.currentTimeMillis() + "";
		Map<String, String> map = new HashMap<String, String>();
		String cardnumber = trafficcard.getCardnum();
		String password = trafficcard.getPassword();
		String money = trafficcard.getMoney() + "";
		map.put("serviceNum", mobile);
		map.put("cardNum", cardnumber);
		map.put("password", password);
		map.put("money", money);
		map.put("supplierId", supplierId);
		map.put("ts", ts);
		map.put("tId", tId);
		String sign = sign(map, key);
		String itemurl = "";
		HttpURLConnection connection = null;
		try {
			itemurl = gateway + "cardNum="
					+ URLEncoder.encode(cardnumber, "GBK") + "&password="
					+ URLEncoder.encode(password, "GBK") + "&serviceNum="
					+ URLEncoder.encode(mobile, "GBK") + "&money="
					+ URLEncoder.encode(money, "GBK") + "&supplierId="
					+ URLEncoder.encode(supplierId, "GBK") + "&ts="
					+ URLEncoder.encode(ts, "GBK") + "&tId="
					+ URLEncoder.encode(tId, "GBK") + "&sign="
					+ URLEncoder.encode(sign, "GBK");
			URL url = new URL(itemurl);
			LOGGER.error("MB流量兑换http请求URL：{}", itemurl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(10000);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.connect();
		} catch (IOException e) {
			LOGGER.error("请求连接浙江联通超时{}", e.fillInStackTrace());
			return null;
		}
		return connection;
	}

	public boolean sendSMS(String phonenumber, String content) {
		try {
			URL url = new URL("http://localhost:80/sdksms/sms?service=sendSMS");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");// 提交模式
			// conn.setConnectTimeout(10000);//连接超时 单位毫秒
			// conn.setReadTimeout(2000);//读取超时 单位毫秒
			conn.setDoOutput(true);// 是否输入参数
			StringBuffer params = new StringBuffer();
			params.append("phonenumber").append("=").append(phonenumber)
					.append("&").append("contents").append("=").append(content);
			PrintWriter out = new PrintWriter(
					new OutputStreamWriter(conn.getOutputStream(), "utf-8"));
			out.print(params);
			out.close();
			conn.getInputStream();
		} catch (IOException e) {

		} // 输入参数

		return true;

	}

	private static String sign(Map<String, String> params, String privatekey) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			if (value == null || value.length() == 0) {
				continue;
			}
			prestr = prestr + key + "" + value;
		}
		return Md5Encrypt.md5(prestr + privatekey, "UTF-8");
	}

	@Override
	public boolean isSupportTraffic(TrafficType ttype, int traffic) {
		return true;
	}

}
