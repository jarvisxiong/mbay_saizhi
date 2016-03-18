package org.sz.mbay.channel.context;

import java.util.HashMap;
import java.util.Map;

import org.sz.mbay.base.context.SpringApplicationContext;
import org.sz.mbay.channel.bean.EmailTemplate;
import org.sz.mbay.channel.enums.EmailTemplateType;
import org.sz.mbay.channel.service.EmailTemplateService;
import org.sz.mbay.channel.service.impl.EmailTemplateServiceImpl;

/**
 * 邮件模板
 * 
 * @author jerry
 */
public class EmailTemplateSupport {
	
	// 保存邮件模板
	private static Map<Integer, EmailTemplate> emailTemplatMap = new HashMap<Integer, EmailTemplate>();
	
	/**
	 * 根据邮件模板类型返回对应类型的短信模板，并保存至map
	 * 
	 * @param tempType
	 * @return
	 */
	private static EmailTemplate getEmailTemplate(EmailTemplateType tempType) {
		EmailTemplateService templateCallService = (EmailTemplateService) SpringApplicationContext
				.getBean(EmailTemplateServiceImpl.class.getSimpleName());
		EmailTemplate template = null;
		if (emailTemplatMap.get(tempType.ordinal()) != null) {
			template = emailTemplatMap.get(tempType.ordinal());
		} else {
			template = templateCallService.findEmailTemplate(tempType);
			if (template != null) {
				emailTemplatMap.put(tempType.ordinal(), template);
			}
		}
		return template;
	}
	
	/**
	 * 实名认证通过邮件提醒
	 * 
	 * @param userName
	 *            账号
	 * @return
	 */
	public static EmailTemplate getCertificateSuccessEmail(String userName) {
		EmailTemplate result = new EmailTemplate();
		result.setContent("");
		
		EmailTemplate template = getEmailTemplate(EmailTemplateType.CERTIFICATE_SUCCESS);
		if (template != null) {
			result.setContent(template.getContent().replace("【账号】", userName));
			result.setName(template.getName());
		}
		
		return result;
	}
	
	/**
	 * 邮件找回密码
	 * 
	 * @param userName
	 *            账号
	 * @param digest
	 *            效验码
	 * @return
	 */
	public static EmailTemplate getResetPasswordEmail(String userName,
			String digest) {
		EmailTemplate result = new EmailTemplate();
		result.setContent("");
		
		EmailTemplate template = getEmailTemplate(EmailTemplateType.RESET_PASSWORD);
		if (template != null) {
			String content = template.getContent();
			content = content.replace("【账号】", userName)
					.replace("【效验码】", digest);
			result.setContent(content);
			result.setName(template.getName());
		}
		
		return result;
	}
	
	/**
	 * 转账成功收款方站内信
	 * 
	 * @param userNumber
	 *            商户编号
	 * @param account
	 *            转账金额(美贝)
	 * @return
	 */
	public static EmailTemplate getTransferNoticeEmail(String userNumber,
			double account) {
		EmailTemplate result = new EmailTemplate();
		result.setContent("");
		EmailTemplate template = getEmailTemplate(EmailTemplateType.TRANSFER_NOTICE);
		if (template != null) {
			String content = template.getContent();
			content = content.replace("【商户编号】", userNumber).replace("【金额】",
					String.valueOf(account));
			result.setContent(content);
			result.setName(template.getName());
		}
		return result;
	}
	
	/**
	 * 转账赠送美贝站内邮件
	 * 
	 * @param userNumber
	 *            商户编号
	 * @param account
	 *            赠送金额(美贝)
	 * @return
	 */
	public static EmailTemplate getTransferPresentEmail(String userNumber,
			double presentAmount) {
		EmailTemplate result = new EmailTemplate();
		result.setContent("");
		EmailTemplate template = getEmailTemplate(EmailTemplateType.TRANSFER_PRESENT);
		if (template != null) {
			String content = template.getContent();
			content = content.replace("【商户编号】", userNumber).replace("【赠送金额】",
					String.valueOf(presentAmount));
			result.setContent(content);
			result.setName(template.getName());
		}
		return result;
	}
	
	public static boolean resetEmailTemplateByType(EmailTemplateType type) {
		emailTemplatMap.remove(type.ordinal());
		if (emailTemplatMap.containsKey(type.ordinal())) {
			return false;
		}
		return true;
	}
}
