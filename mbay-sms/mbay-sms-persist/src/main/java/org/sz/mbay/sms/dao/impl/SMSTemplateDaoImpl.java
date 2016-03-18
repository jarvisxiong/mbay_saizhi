package org.sz.mbay.sms.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.sms.bean.SmsTemplate;
import org.sz.mbay.sms.dao.SMSTemplateDao;

@Repository
public class SMSTemplateDaoImpl extends BaseDaoImpl<SmsTemplate>
		implements SMSTemplateDao {
		
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SMSTemplateDaoImpl.class);
			
	@Override
	public List<SmsTemplate> findList(String name) {
		List<SmsTemplate> list = null;
		try {
			SmsTemplate template = new SmsTemplate();
			template.setName(name);
			list = this.template.selectList("SmsTemplate.findAllSmsTemplate",
					template);
		} catch (Exception e) {
			LOGGER.error("SmsTemplateDao findSmsTemplateById Error",
					e.fillInStackTrace());
		}
		return list;
	}
	
	@Override
	public SmsTemplate findSmsTemplateById(int id) {
		SmsTemplate bean = null;
		try {
			bean = this.template.selectOne("SmsTemplate.findSmsTemplateById",
					id);
		} catch (Exception e) {
			LOGGER.error("SmsTemplateDao findSmsTemplateById Error",
					e.fillInStackTrace());
		}
		return bean;
	}
	
	@Override
	public SmsTemplate findSmsTemplateByType(String smstype) {
		return this.template.selectOne("SmsTemplate.findSmsTemplateByType",
				smstype);
	}
	
	@Override
	public boolean update(SmsTemplate bean) {
		boolean result = false;
		try {
			result = super.updateBean(bean) > 0 ? true : false;
			return result;
		} catch (Exception e) {
			LOGGER.error("SmsTemplateDao update Error", e.fillInStackTrace());
		}
		return false;
	}
}
