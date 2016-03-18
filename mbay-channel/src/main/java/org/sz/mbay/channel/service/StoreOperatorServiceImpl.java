package org.sz.mbay.channel.service;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sz.mbay.base.dao.utils.PKgen;
import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.channel.bean.Store;
import org.sz.mbay.channel.bean.StoreOperator;
import org.sz.mbay.channel.dao.StoreDao;
import org.sz.mbay.channel.dao.StoreOperatorDao;

@Service
public class StoreOperatorServiceImpl implements StoreOperatorService {
	
	private Logger logger =
			LoggerFactory.getLogger(StoreOperatorServiceImpl.class);
	
	@Autowired
	StoreOperatorDao storeOperatorDao;
	
	@Autowired
	StoreDao storeDao;
	
	@Override
	public List<StoreOperator> findOperators(long storeId) {
		return this.storeOperatorDao.findOperatorsByStoreId(storeId);
		
	}
	
	@Override
	public StoreOperator findOperatorById(long opeId) {
		StoreOperator operator = null;
		try {
			operator = this.storeOperatorDao.getBean(opeId);
			return operator;
		} catch (Exception e) {
			this.logger.error("findOperator::数据库层错处啦！", e.fillInStackTrace());
		}
		return operator;
		
	}
	
	@Override
	public StoreOperator findOperator(String authCode, String cellphone) {
		return this.storeOperatorDao.findOperator(authCode, cellphone);
	}
	
	@Override
	@Transactional
	public StoreOperator init(String cellphone, String password, String authCode) {
		StoreOperator operator = new StoreOperator();
		operator.setCellphone(cellphone);
		operator.setPassword(password);
		operator.setAuthCode(authCode);
		operator.setDeleted(false);
		operator.setStatus(EnableState.ENABLED);
		operator.setId(PKgen.getInstance().nextPK());
		// 由此可见是授权码必须为门店的候选码
		Store store = this.storeDao.findStoreByAuthCode(authCode);
		operator.setStoreId(store.getId());
		// 初始化操作员
		operator = this.storeOperatorDao.createBean(operator);
		// 修改门店状态为激活状态
		if (!store.isActive()) {
			this.storeDao.updateStoreIsActive(store.getId(), true);
		}
		return operator;
	}
	
	@Override
	@Transactional
	public boolean delete(long opeId, long storeId) {
		int result;
		try {
			result = this.storeOperatorDao.deleteBean(opeId);
			if (result > 0) {
				// 如果删除成功，判断当前操作员个数
				int c = this.storeOperatorDao.countOperator(storeId);
				if (c < 1) {
					// 说明当前门店没有可用的操作员了,门店状态改为未激活
					this.storeDao.updateStoreIsActive(storeId, false);
				}
			}
			return result > 0;
		} catch (SQLException e) {
			this.logger.error("delete:Data Access layer error", e.fillInStackTrace());
			e.printStackTrace();
		}
		return false;
	}
}
