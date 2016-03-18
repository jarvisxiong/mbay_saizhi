package org.sz.mbay.routeros.framework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 基本service辅助
 * 
 * @author jerry
 */
@Service
public class ServiceSupport {
	
	@Autowired
	private DataSourceTransactionManager transactionManager;
	
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
		transactionManager.commit(status);
	}
	
	/**
	 * 手动回滚事务
	 * 
	 * @param status
	 */
	public void rollbackTransaction(TransactionStatus status) {
		transactionManager.rollback(status);
	}
}
