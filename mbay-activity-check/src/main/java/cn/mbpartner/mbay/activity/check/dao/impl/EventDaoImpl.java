package cn.mbpartner.mbay.activity.check.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.mbpartner.mbay.activity.check.dao.EventDao;

@Repository
public class EventDaoImpl implements EventDao {
	
	SqlSessionTemplate template;
	
	@Autowired
	public void setTemplate(SqlSessionTemplate template) {
		this.template = template;
	}
	
	@Override
	public void checkDateByUsernumber(String usernumber) {
		/*
		 * Map<String, String> map = new HashMap<String, String>();
		 * map.put("usernumber", usernumber);
		 */
		this.template.update("checkDateByUsernumber", usernumber);
	}
}
