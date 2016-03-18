package org.sz.mbay.traffic.response;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XMLSupport {
	
	public static Document textDocument(boolean success,String contents){
		Document document=DocumentHelper.createDocument();
		Element status=document.addElement("Status");
		status.setText(success?"success":"failed");
		Element MsgType=document.addElement("MsgType");
		MsgType.setText("text");
		//<Content><![CDATA[this is a test]]></Content>
		Element content=document.addElement("Content");
		content.setText(contents);
		return document;
	}
	/**
	 * 不支持的运营接口
	 * @return
	 */
	public static Document unabledOperator(){		
		return textDocument(false,"此地区运营商流量充值暂未开通");
	}
	/**
	 * 无效的充值流量 包
	 * @return
	 */
	public static Document invalidTraffic(int lastunit){
		return textDocument(false,"无效的流量包，充值流量应是"+lastunit+"的正整数倍");
	}
	
	/**
	 * 无效的充值流量 包
	 * @return
	 */
	public static Document  insufficientAccount(){
		return textDocument(false,"美贝余额不足于流量兑换，请充值");
	}
	
	/**
	 * 连接服务器超时
	 * @return
	 */
	public static Document  connectionTimeou(){
		return textDocument(false,"请求服务器超时，请稍后再试！");
	}
	
	
	/**
	 * @return
	 */
	public static Document rechargeing(){
		return textDocument(true,"流量充值正在处理");
	}
	
	

}
