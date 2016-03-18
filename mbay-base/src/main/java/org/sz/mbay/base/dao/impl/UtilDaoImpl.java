package org.sz.mbay.base.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.UtilDao;

@Repository
public class UtilDaoImpl implements UtilDao {
	
	@Autowired
	protected SqlSessionTemplate template;
	
	public int getNextIndex(String name) {
		Integer next = this.template.selectOne("Util.getTableIndex", name);
		if (next == null) {
			try {
				this.template.insert("Util.insertTableIndex", name);
			} catch (Exception e) {
				//如果插入失败则继续执行,直到执行成功为止
				getNextIndex(name);
			}
			next = this.template.selectOne("Util.getTableIndex", name);
		}
		
		return next;
		
	}
	
	@Override
	public void incrementIndex(String name) {
		this.template.update("Util.updateTableIndex", name);
		
	}
}
