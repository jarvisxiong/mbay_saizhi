package org.sz.mbay.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.sz.mbay.base.service.BaseService;

/**
 * @author ONECITY
 * 
 */
public class BaseServiceImpl implements BaseService {
	
	@Autowired
	DataSourceTransactionManager transactionManager;
	
	/**
	 * 手动开始事务
	 * 
	 * @return
	 */
	public TransactionStatus startTransaction() {
		TransactionDefinition definition = new DefaultTransactionDefinition(
				TransactionDefinition.PROPAGATION_REQUIRED);
		return transactionManager.getTransaction(definition);
	}
	
	/**
	 * 手动提交事务
	 * 
	 * @param status
	 */
	public void commitTransaction(TransactionStatus status) {
		this.transactionManager.commit(status);
	}
	
	/**
	 * 手动回滚事务
	 * 
	 * @param status
	 */
	public void rollbackTransaction(TransactionStatus status) {
		this.transactionManager.rollback(status);
	}
	
}
