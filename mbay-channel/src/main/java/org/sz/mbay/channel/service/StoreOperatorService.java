package org.sz.mbay.channel.service;

import java.util.List;

import org.sz.mbay.channel.bean.StoreOperator;

public interface StoreOperatorService {
	
	public List<StoreOperator> findOperators(long storeId);
	
	public StoreOperator findOperator(String authCode, String cellphone);
	
	public StoreOperator init(String cellphone, String password, String authCode);
	
	public StoreOperator findOperatorById(long opeId);
	
	boolean delete(long opeId, long storeId);
	
}
