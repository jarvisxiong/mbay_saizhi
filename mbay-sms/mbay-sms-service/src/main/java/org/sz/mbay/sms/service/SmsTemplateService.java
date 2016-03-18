package org.sz.mbay.sms.service;

import java.util.List;

import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.sms.bean.SmsTemplate;

public interface SmsTemplateService {
	
	/**
	 * 更新
	 * 
	 * @param bean
	 * @return
	 */
	public boolean update(SmsTemplate bean);
	
	/**
	 * 查询-单条
	 * 
	 * @param id
	 * @return
	 */
	public SmsTemplate findSmsTemplateById(int id);
	
	/**
	 * 查询 name->名称
	 * 
	 * @param name
	 * @return
	 */
	public List<SmsTemplate> findList(String name);
	
	/**
	 * 修改状态 id->id,status->状态
	 * 
	 * @param id
	 * @param status
	 */
	public void changeStatus(int id, EnableState status);
	
	/**
	 * 根据类型找短信模板
	 * 
	 * @param smsName
	 * @return
	 */
	public SmsTemplate findSmsTemplateByType(String smsName);
}
