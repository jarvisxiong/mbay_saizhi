package org.sz.mbay.channel.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.channel.bean.EmailTemplate;
import org.sz.mbay.channel.dao.EmailTemplateDao;
import org.sz.mbay.channel.enums.EmailTemplateType;

@Repository
public class EmailTemplateDaoImpl extends BaseDaoImpl<EmailTemplate> implements EmailTemplateDao {
	
	@Override
	public List<EmailTemplate> findList(Map<String, Object> map) {
		return template.selectList("findAllEmailTemplate", map);
	}
	
	@Override
	public EmailTemplate findEmailTemplateById(int id) {
		return template.selectOne("findEmailTemplateById", id);
	}

	@Override
	public int updateEmailTemplate(EmailTemplate bean) {
		return template.update("updateEmailTemplate", bean); 
	}

	@Override
	public EmailTemplate findEmailTemplateByType(EmailTemplateType tempType) {
		return template.selectOne("findEmailTemplateByType", tempType.ordinal());
	}
	
}
