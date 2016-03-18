package org.sz.mbay.channel.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.channel.bean.StoreOperator;

public interface StoreOperatorDao extends BaseDao<StoreOperator> {
	
	List<StoreOperator> findOperatorsByStoreId(long storeId);
	
	StoreOperator findOperator(String storeId, String cellphone);
	
	int countOperator(long storeId);
	
}
