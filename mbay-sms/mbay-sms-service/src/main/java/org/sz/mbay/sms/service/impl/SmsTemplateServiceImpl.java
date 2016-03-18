package org.sz.mbay.sms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.sms.bean.SmsTemplate;
import org.sz.mbay.sms.dao.SMSTemplateDao;
import org.sz.mbay.sms.service.SmsTemplateService;

@Service("smsTemplateService")
public class SmsTemplateServiceImpl extends BaseServiceImpl implements SmsTemplateService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SmsTemplateServiceImpl.class);
	
	@Autowired
	SMSTemplateDao dao;

	@Override
	public List<SmsTemplate> findList(String name) {
		try {
			SmsTemplate template = new SmsTemplate();
			template.setName(name);
			return this.dao.findList(name);
		} catch (Exception e) {
			LOGGER.error("SmsTemplateService findAllSmsTemplate Error", e.fillInStackTrace());
		}
		return new ArrayList<SmsTemplate>();
	}
	
	@Override
	public boolean update(SmsTemplate bean) {
		boolean result = false;
		try {
			result = this.dao.update(bean);
			return result;
		} catch (Exception e) {
			LOGGER.error("SmsTemplateService update Error", e.fillInStackTrace());
		}
		return false;
	}
	
	@Override
	public SmsTemplate findSmsTemplateById(int id) {
		SmsTemplate bean = null;
		try {
			bean = this.dao.findSmsTemplateById(id);
		} catch (Exception e) {
			LOGGER.error("SmsTemplateService findSmsTemplateById Error", e.fillInStackTrace());
		}
		return bean;
	}
	
	@Override
	public void changeStatus(int id, EnableState status) {
		SmsTemplate bean = null;
		try {
			bean = this.dao.findSmsTemplateById(id);
			bean.setStatus(status);
			this.dao.update(bean);
		} catch (Exception e) {
			LOGGER.error("SmsTemplateService changeStatus Error", e.fillInStackTrace());
		}
	}
	
	@Override
	public SmsTemplate findSmsTemplateByType(String type) {
		return dao.findSmsTemplateByType(type);
	}

}
