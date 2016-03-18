package org.sz.mbay.channel.service;

import java.util.List;
import java.util.Map;

import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.channel.bean.EmailTemplate;
import org.sz.mbay.channel.enums.EmailTemplateType;

/**
 * 邮件模板服务
 * 
 * @author jerry
 */
public interface EmailTemplateService {
	
	/**
	 * 查询模板列表
	 * 
	 * @param map
	 * @return
	 */
	List<EmailTemplate> findList(Map<String, Object> map);
	
	/**
	 * 改变启用，禁用状态
	 * 
	 * @param id
	 * @param status
	 */
	boolean changeStatus(int id, EnableState status);
	
	/**
	 * 根据id查找记录
	 * 
	 * @param id
	 * @return
	 */
	EmailTemplate findEmailTemplate(int id);
	
	/**
	 * 更新实体
	 * 
	 * @param bean
	 * @return
	 */
	boolean updateEmailTemplate(EmailTemplate bean);
	
	/**
	 * 根据模板类型查找记录
	 * 
	 * @param tempType
	 * @return
	 */
	EmailTemplate findEmailTemplate(EmailTemplateType tempType);
}
