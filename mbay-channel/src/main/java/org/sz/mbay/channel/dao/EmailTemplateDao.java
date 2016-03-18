package org.sz.mbay.channel.dao;

import java.util.List;
import java.util.Map;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.channel.bean.EmailTemplate;
import org.sz.mbay.channel.enums.EmailTemplateType;

/**
 * 邮件模板持久层
 * 
 * @author jerry
 */
public interface EmailTemplateDao extends BaseDao<EmailTemplate> {
	
	/**
	 * 查找模板列表
	 * 
	 * @param map
	 * @return
	 */
	List<EmailTemplate> findList(Map<String, Object> map);
	
	/**
	 * 根据id查找记录
	 * 
	 * @param id
	 * @return
	 */
	EmailTemplate findEmailTemplateById(int id);
	
	/**
	 * 更新实体
	 * 
	 * @param bean
	 * @return
	 */
	int updateEmailTemplate(EmailTemplate bean);
	
	/**
	 * 根据模板类型查找记录
	 * 
	 * @param tempType
	 * @return
	 */
	EmailTemplate findEmailTemplateByType(EmailTemplateType tempType);
}
