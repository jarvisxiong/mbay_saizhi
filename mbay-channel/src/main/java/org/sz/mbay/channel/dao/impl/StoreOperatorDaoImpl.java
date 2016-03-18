package org.sz.mbay.channel.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.channel.bean.StoreOperator;
import org.sz.mbay.channel.dao.StoreOperatorDao;

@Repository
public class StoreOperatorDaoImpl extends BaseDaoImpl<StoreOperator>
		implements StoreOperatorDao {
	
	@Override
	public List<StoreOperator> findOperatorsByStoreId(long storeId) {
		return this.template.selectList("findOperatorsByStoreId", storeId);
	}
	
	@Override
	public StoreOperator findOperator(String authCode, String cellphone) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("authCode", authCode);
		map.put("cellphone", cellphone);
		return this.template.selectOne("findOperatorByCellPhone", map);
	}
	
	@Override
	public int countOperator(long storeId) {
		return this.template.selectOne("countOperator", storeId);
	}
}
