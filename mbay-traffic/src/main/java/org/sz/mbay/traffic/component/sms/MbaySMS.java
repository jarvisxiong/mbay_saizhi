package org.sz.mbay.traffic.component.sms;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ONECITY
 * 
 */
public class MbaySMS {
    private static final Logger LOGGER = LoggerFactory.getLogger(MbaySMS.class);

    /**
     * @param mobiles
     *            手机号 多个手机号用,隔开
     * @param content
     *            短信内容
     * @return
     */
    public static boolean sendSMS(String mobiles, SMSUnit unit) {
	String urlstr = "http://sdksms2.mbsnoc.com:8380/mbay-sms/sms/sendsms.mbay";
	System.out.println("短信模板ID:" + unit.getSmsId() + "--短信模板参数:"
		+ unit.getParam());
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug(mobiles + "短信发送：" + "短信模板ID:" + unit.getSmsId()
		    + "--短信模板参数:" + unit.getParam());
	}
	try {
	    URL url = new URL(urlstr);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("POST");// 提交模式
	    // conn.setConnectTimeout(10000);//连接超时 单位毫秒
	    // conn.setReadTimeout(2000);//读取超时 单位毫秒
	    conn.setDoOutput(true);// 是否输入参数
	    StringBuffer params = new StringBuffer();
	    params.append("mobiles=").append(mobiles).append("&")
		    .append("smsId=").append(unit.getSmsId()).append("&")
		    .append("param=").append(unit.getParam());
	    PrintWriter out = new PrintWriter(new OutputStreamWriter(
		    conn.getOutputStream(), "utf-8"));
	    out.print(params);
	    out.close();
	    conn.getInputStream();
	} catch (IOException e) {
	    LOGGER.error("sendSMS发送短信异常：" + "短信模板ID:" + unit.getSmsId()
		    + "--短信模板参数:" + unit.getParam(), e.fillInStackTrace());
	    return false;
	}// 输入参数
	return true;

    }

}
