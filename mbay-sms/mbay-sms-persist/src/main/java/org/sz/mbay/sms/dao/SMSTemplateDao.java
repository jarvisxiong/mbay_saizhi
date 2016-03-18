package org.sz.mbay.sms.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.sms.bean.SmsTemplate;

public interface SMSTemplateDao extends BaseDao<SmsTemplate> {
	
	public boolean update(SmsTemplate bean);
	
	public SmsTemplate findSmsTemplateById(int id);
	
	public List<SmsTemplate> findList(String name);
	
	public SmsTemplate findSmsTemplateByType(String type);
}
