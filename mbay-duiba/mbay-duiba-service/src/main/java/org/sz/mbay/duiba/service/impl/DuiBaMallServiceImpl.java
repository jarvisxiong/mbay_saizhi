package org.sz.mbay.duiba.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.duiba.bean.DuiBaMall;
import org.sz.mbay.duiba.bean.DuiBaRelationShip;
import org.sz.mbay.duiba.dao.DuiBaMallDao;
import org.sz.mbay.duiba.dao.DuiBaRelationShipDao;
import org.sz.mbay.duiba.service.DuiBaMallService;

@Service
public class DuiBaMallServiceImpl implements DuiBaMallService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DuiBaMallServiceImpl.class);
	
	@Autowired
	DuiBaMallDao dao;
	@Autowired
	DuiBaRelationShipDao relationDao;
	
	@Override
	public List<DuiBaMall> findList(String name, PageInfo pageinfo) {
		return this.dao.findList(name, pageinfo);
	}
	
	@Override
	public List<DuiBaMall> findEnabledList() {
		return this.dao.findEnabledList();
	}
	
	@Override
	@Transactional
	public boolean del(int id) {
		try {
			this.dao.deleteBean(id);
			DuiBaRelationShip relation = new DuiBaRelationShip();
			DuiBaMall mall = new DuiBaMall();
			mall.setId(id);
			relation.setMall(mall);
			this.relationDao.deleteBeanByParam(relation);
			return true;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOGGER.error("DuiBaMallService del Error", e.fillInStackTrace());
		}
		return false;
	}
	
	@Override
	public void add(DuiBaMall bean) {
		try {
			this.dao.createBean(bean);
		} catch (Exception e) {
			LOGGER.error("DuiBaMallService add Error", e.fillInStackTrace());
		}
	}
	
	@Override
	public void update(DuiBaMall bean) {
		try {
			this.dao.updateBean(bean);
		} catch (Exception e) {
			LOGGER.error("DuiBaMallService update Error", e.fillInStackTrace());
		}
	}
	
	@Override
	public DuiBaMall findOne(int id) {
		return this.dao.findOne(id);
	}
	
	@Override
	public ExecuteResult change(int id, EnableState status) {
		DuiBaMall bean = null;
		try {
			bean = this.dao.findOne(id);
			bean.setStatus(status);
			return this.dao.updateBean(bean) > 0 ? new ExecuteResult(true, "") : new ExecuteResult(false, "");
		} catch (Exception e) {
			LOGGER.error("DuiBaMallService change Error", e.fillInStackTrace());
		}
		return new ExecuteResult(false, "");
	}
}
