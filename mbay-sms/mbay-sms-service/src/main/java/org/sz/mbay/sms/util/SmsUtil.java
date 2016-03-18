package org.sz.mbay.sms.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sz.mbay.sms.bean.SmsTemplate;
import org.sz.mbay.sms.bean.SmsUnit;
import org.sz.mbay.sms.enums.SmsServiceType;

public class SmsUtil {
	
	public static String getSMSContent(SmsUnit unit) {
		String content = unit.getContent();
		String param = unit.getParam();
		String[] params = param.split(",");
		int index = 0;
		Pattern p = Pattern.compile("\\{[^\\{\\}]*\\}");
		Matcher m = p.matcher(content);
		while (m.find()) {
			content = content.replace(m.group(0), params[index]);
			index++;
		}
		return content;
	}
	
	/**
	 * 更具服务平台类型来获取短信模板，因为不同的平台模板格式不一样，主要是区别在后缀位置
	 * 
	 * @param smsId
	 *            短信模板ID
	 * @param param
	 *            短信膜拜参数
	 * @param type
	 *            平台类型
	 * @return
	 */
	public static String getSMSContent(int smsId, String param,
			SmsServiceType type) {
		SmsTemplate template = SMSTemplateSupport.getSMSTemplate(smsId, type);
		if (template == null) {
			return null;
		}
		SmsUnit unit = new SmsUnit(smsId, param, template.getContent());
		return SmsUtil.getSMSContent(unit);
	}
}
