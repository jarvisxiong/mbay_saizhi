package org.sz.mbay.channel.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.channel.bean.EmailTemplate;
import org.sz.mbay.channel.dao.EmailTemplateDao;
import org.sz.mbay.channel.enums.EmailTemplateType;
import org.sz.mbay.channel.service.EmailTemplateService;

@Service("EmailTemplateServiceImpl")
public class EmailTemplateServiceImpl implements EmailTemplateService {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(EmailTemplateServiceImpl.class);
	
	@Autowired
	private EmailTemplateDao emailTemplateDao;
	
	@Override
	public List<EmailTemplate> findList(Map<String, Object> map) {
		try {
			return emailTemplateDao.findList(map);
		} catch (Exception e) {
			LOGGER.error("EmailTemplateServiceImpl findList error", e.fillInStackTrace());
		}
		return null;
	}
	
	@Override
	public boolean changeStatus(int id, EnableState status) {
		try {
			EmailTemplate bean = emailTemplateDao.findEmailTemplateById(id);
			bean.setStatus(status);
			return emailTemplateDao.updateEmailTemplate(bean) == 1;
		} catch (Exception e) {
			LOGGER.error("EmailTemplateServiceImpl changeStatus error", e.fillInStackTrace());
		}
		return false;
	}
	
	@Override
	public EmailTemplate findEmailTemplate(int id) {
		try {
			return emailTemplateDao.findEmailTemplateById(id);
		} catch (Exception e) {
			LOGGER.error("EmailTemplateServiceImpl findEmailTemplate error", e.fillInStackTrace());
		}
		return null;
	}
	
	@Override
	public boolean updateEmailTemplate(EmailTemplate bean) {
		if (bean.getId() != null && findEmailTemplate(bean.getId()) != null) {
			return emailTemplateDao.updateEmailTemplate(bean) == 1;
		}
		return false;
	}
	
	@Override
	public EmailTemplate findEmailTemplate(EmailTemplateType tempType) {
		try {
			return emailTemplateDao.findEmailTemplateByType(tempType);
		} catch (Exception e) {
			LOGGER.error("EmailTemplateServiceImpl findEmailTemplate error", e.fillInStackTrace());
		}
		return null;
	}
	
}
