package org.sz.mbay.traffic.response;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class ZJXMLSupport {

	public static Document nocorrespondTraffic() {
		Document document = DocumentHelper.createDocument();
		Element status = document.addElement("Status");
		status.setText("failed");
		Element MsgType = document.addElement("MsgType");
		MsgType.setText("text");
		// <Content><![CDATA[this is a test]]></Content>
		Element content = document.addElement("Content");
		content.setText("没有对应流量充值包");
		return document;

	}

	public static Document unableRedeem(String code) {
		Document document = DocumentHelper.createDocument();
		Element status = document.addElement("Status");
		status.setText("failed");
		Element MsgType = document.addElement("MsgType");
		MsgType.setText("text");
		// <Content><![CDATA[this is a test]]></Content>
		Element content = document.addElement("Content");
		content.setText(getTextByCode(code));
		return document;

	}

	/*
	 * 00 缴费卡缴费成功 01 系统故障 02 缴费账户不存在 03 缴费账户存在但是不能用于缴费 04 帐务系统操作超时 05 不在缴费时段 06
	 * 缴费操作失败 07 卡不存在 08 密码错误 09 卡已过期 10 卡已经注销 11 充值中 12 卡已充值 13 非激活卡 14 余额不足
	 */
	public static String getTextByCode(String code) {
		if ("01".equals(code)) {
			return "运营商系统故障，暂无法充值";
		} else if ("02".equals(code) || "03".equals(code)) {
			return "不支持的手机号";
		} else if ("04".equals(code)) {
			return "运营商账户系统超时，请稍后再试";
		} else if ("05".equals(code)) {
			return "运营商不支持当前时间段充值，请稍后再试";
		} else if ("06".equals(code)) {
			return "运营商流量充值失败，请稍后再试";
		} else if ("07,08,09,10".indexOf(code) > -1) {
			return "系统卡密池维护，请稍后再试或联系客服人员";
		}
		return "未知编码：" + code;
	}

}
