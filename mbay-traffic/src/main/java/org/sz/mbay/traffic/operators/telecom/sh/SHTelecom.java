package org.sz.mbay.traffic.operators.telecom.sh;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.sz.mbay.operator.enums.TrafficType;
import org.sz.mbay.traffic.operators.TrafficRecharge;
import org.sz.mbay.traffic.operators.TrafficRechargeNotify;
import org.sz.mbay.traffic.response.ChargeResult;

@Component("SHTelecom")
public class SHTelecom implements TrafficRecharge {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SHTelecom.class);

	@Override
	public ChargeResult charge(String mobile, TrafficType traffictype,
			int traffic, String ordernumber) {
		DateFormat formatt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = formatt.format(new Timestamp(System.currentTimeMillis()));
		StringBuilder sendFlow = new StringBuilder();
		sendFlow.append(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Header><ns1:CSBHeader soapenv:actor=\"http://schemas.xmlsoap.org/soap/actor/next\" soapenv:mustUnderstand=\"0\" xmlns:ns1=\"http://www.shtel.com.cn/csb/v2/\">");
		sendFlow.append("<ServiceName>ComplexOrderProdOffer</ServiceName>");
		sendFlow.append("<Consumer>SAIZ</Consumer>");// SAIZ 签约信息
		sendFlow.append("<RequestTime>" + time + "</RequestTime>");
		sendFlow.append("</ns1:CSBHeader>");
		sendFlow.append("</soapenv:Header>");
		sendFlow.append("<soapenv:Body>");
		sendFlow.append("<Id>" + mobile + "</Id>");// 手机号
		sendFlow.append("<Kind>2</Kind>");// 标识上面信息为手机号
		sendFlow.append("<Campaign_spcName>common</Campaign_spcName>");
		sendFlow.append("<OrderInfo>");
		sendFlow.append("<BusCode>SH30000</BusCode>");//
		sendFlow.append("<ServiceCode>SC10004</ServiceCode>");
		/// String teimstatem = "SZ" + System.currentTimeMillis();
		sendFlow.append("<MSGID>" + ordernumber + "</MSGID>");
		sendFlow.append("<OrderInfo>");
		sendFlow.append("<Login_Id>zhangwenj</Login_Id>");// zhangwenj 测试：
		sendFlow.append("<Channel>SAIZ</Channel>");// SAIZ
		sendFlow.append(" <ContactID>" + ordernumber + "</ContactID>");
		sendFlow.append("<Promotion_Prod_Id>"
				+ SHTelePackage.getPackCodeByTraffic(traffictype, traffic)
				+ "</Promotion_Prod_Id>");// 2-QESP67N /2-9ASBRPV 测试环境：2-8M6TUWB
		sendFlow.append("</OrderInfo>");
		sendFlow.append("</OrderInfo>");
		sendFlow.append("</soapenv:Body>");
		sendFlow.append("</soapenv:Envelope>");
		LOGGER.error(sendFlow.toString());
		try {
			String serviceURL = "http://222.68.185.224:10089/ComplexOrderProdOffer/V1_0";// 测试：10088，正式：10089
			String soapAction = "http://www.shtel.com.cn/csb/v2/OrderProdOffer";
			URL url = new URL(serviceURL);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpconn = (HttpURLConnection) connection;
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			bout.write(sendFlow.toString().getBytes());
			byte[] b = bout.toByteArray();
			httpconn.setRequestProperty("Content-Length",
					String.valueOf(b.length));
			httpconn.setRequestProperty("Content-Type",
					"text/xml; charset=utf-8");
			httpconn.setRequestProperty("SOAPAction", soapAction);
			httpconn.setRequestMethod("POST");
			httpconn.setDoInput(true);
			httpconn.setDoOutput(true);
			OutputStream out = httpconn.getOutputStream();
			out.write(b);
			out.close();
			InputStream isr = httpconn.getInputStream();

			StringBuilder sbuilder = new StringBuilder();
			try (BufferedReader input = new BufferedReader(
					new InputStreamReader(isr, "utf-8"))) {
				String str = input.readLine();
				while (str != null) {
					sbuilder.append(str);
					str = input.readLine();
				}
				LOGGER.error("上海电信充值返回：" + sbuilder.toString());
			}
			Document doucment = DocumentHelper.parseText(sbuilder.toString());
			Element root = doucment.getRootElement();
			Element body = root.element("Body");
			Element response = body.element("OrderInfoResponse");
			String reulst = response.elementTextTrim("Result");
			String msg = response.elementTextTrim("Msg");
			if ("0".equals(reulst)) {
				LOGGER.error("上海电信流量中，等待回调通知.");
				return new ChargeResult(ChargeResult.CHARGING, "充值中");
			} else {
				LOGGER.error("上海电信流量充值失败：{}", msg);
				TrafficRechargeNotify.failNotify(ordernumber, msg);
				return new ChargeResult(ChargeResult.CHARGE_FAILE, msg);
			}
		} catch (Exception e) {
			LOGGER.error("请求上海电信流量充值异常", e.fillInStackTrace());
			TrafficRechargeNotify.failNotify(ordernumber, "mbaytraffic系统异常，请查看日志信息");
			return new ChargeResult(ChargeResult.CHARGE_FAILE, "系统异常");
		}
		/****
		 * <?xml version="1.0" encoding="UTF-8"?>
		 * <soapenv:Envelope xmlns:soapenv=
		 * "http://schemas.xmlsoap.org/soap/envelope/"> <soapenv:Body>
		 * <ns1:OrderInfoResponse xmlns:ns1="http://www.shtel.com.cn/csb/v2/">
		 * <Result>0</Result>
		 * <Msg>提交CRM成功</Msg> </ns1:OrderInfoResponse> </soapenv:Body>
		 * </soapenv:Envelope>
		 * 
		 */

	}

	public static void main(String[] args) {
		SHTelecom tel = new SHTelecom();
		// tel.recharge("2014112010000949","18625427511",
		// TrafficType.PROVINCE,20);

	}

	/******
	 * public String mo() { String result = ""; String soapAction =
	 * "http://tempuri.org/mo"; String xml =
	 * "<?xml version=\"1.0\" encoding=\"utf-8\"?>"; xml +=
	 * "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
	 * ; xml += "<soap:Body>"; xml += "<mo xmlns=\"http://tempuri.org/\">"; xml
	 * += "<sn>" + sn + "</sn>"; xml += "<pwd>" + pwd + "</pwd>"; xml += "</mo>"
	 * ; xml += "</soap:Body>"; xml += "</soap:Envelope>"; URL url; try { url =
	 * new URL(serviceURL); URLConnection connection = url.openConnection();
	 * HttpURLConnection httpconn = (HttpURLConnection) connection;
	 * ByteArrayOutputStream bout = new ByteArrayOutputStream();
	 * bout.write(xml.getBytes()); byte[] b = bout.toByteArray();
	 * httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
	 * httpconn.setRequestProperty("Content-Type", "text/xml; charset=gb2312");
	 * httpconn.setRequestProperty("SOAPAction", soapAction);
	 * httpconn.setRequestMethod("POST"); httpconn.setDoInput(true);
	 * httpconn.setDoOutput(true); OutputStream out =
	 * httpconn.getOutputStream(); out.write(b); out.close(); InputStream isr =
	 * httpconn.getInputStream(); StringBuffer buff = new StringBuffer(); byte[]
	 * byte_receive = new byte[10240]; for (int i = 0; (i =
	 * isr.read(byte_receive)) != -1;) { buff.append(new String(byte_receive, 0,
	 * i)); } isr.close(); String result_before = buff.toString(); int start =
	 * result_before.indexOf("<moResult>"); int end = result_before.indexOf(
	 * "</moResult>"); result = result_before.substring(start + 10, end); return
	 * result; } catch (Exception e) { e.printStackTrace(); return ""; } }
	 *****/

	@Override
	public boolean isSupportTraffic(TrafficType ttype, int traffic) {
		return SHTelePackage.getPackCodeByTraffic(ttype, traffic) != null;
	}

}
