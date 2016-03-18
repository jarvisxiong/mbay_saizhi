package org.sz.mbay.sms.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.mbay.base.context.SpringApplicationContext;
import org.sz.mbay.sms.bean.SmsTemplate;
import org.sz.mbay.sms.enums.SmsServiceType;
import org.sz.mbay.sms.service.SmsTemplateService;

/**
 * 
 * @ClassName: SMSTemplateSupport
 * 
 * @Description: 短信模板支持类
 * 
 * @author <a href="mailto:fenlonxiong@gmail.com">Fenlon</a>
 * 
 * @date 2014年12月19日 上午11:16:55
 * 
 */

public class SMSTemplateSupport {
	
	// 保存短信模板
	private static Map<Integer, SmsTemplate> smsTemplatMap = new HashMap<Integer, SmsTemplate>();
	
	private static Logger logger = LoggerFactory
			.getLogger(SMSTemplateSupport.class);
	
	public static SmsTemplate getSMSTemplate(int smsId, SmsServiceType type) {
		SmsTemplateService templateCallService = (SmsTemplateService) SpringApplicationContext
				.getBean("smsTemplateService");
		SmsTemplate template = null;
		if (smsTemplatMap.get(smsId) != null) {
			template = smsTemplatMap.get(smsId);
			return template;
		} else {
			template = templateCallService.findSmsTemplateById(smsId);
			if (template != null) {
				switch (type) {
					case SDK:
						template.setContent(template.getContent() + "【"
								+ template.getSuffix() + "】");
						break;
					case HHM:
						template.setContent("【" + template.getSuffix() + "】"
								+ template.getContent());
						break;
					default:
						// 云之讯的不用处理
						break;
				}
				smsTemplatMap.put(smsId, template);
				return template;
			}
		}
		logger.warn("getSMSTemplate", "短信模板为NULL");
		return template;
	}
	
}
