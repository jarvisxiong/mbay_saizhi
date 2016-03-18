package org.sz.mbay.duiba.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.duiba.bean.DuiBaMall;
import org.sz.mbay.duiba.bean.DuiBaRelationShip;
import org.sz.mbay.duiba.dao.DuiBaRelationShipDao;
import org.sz.mbay.duiba.service.DuiBaRelationShipService;

@Service
public class DuiBaRelationShipServiceImpl implements DuiBaRelationShipService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DuiBaRelationShipServiceImpl.class);
	
	@Autowired
	DuiBaRelationShipDao dao;
	
	@Override
	public List<DuiBaRelationShip> findList(String usernumber, PageInfo pageinfo) {
		return this.dao.findList(usernumber, pageinfo);
	}
	
	@Override
	public boolean del(String usernumber, int mallId) {
		try {
			DuiBaMall mall = new DuiBaMall();
			mall.setId(mallId);
			DuiBaRelationShip relation = new DuiBaRelationShip();
			relation.setUsernumber(usernumber);
			relation.setMall(mall);
			return this.dao.deleteBeanByParam(relation) > 0;
		} catch (Exception e) {
			LOGGER.error("DuiBaRelationShipService del Error", e.fillInStackTrace());
		}
		return false;
	}
	
	@Override
	public void add(DuiBaRelationShip bean) {
		try {
			this.dao.createBean(bean);
		} catch (Exception e) {
			LOGGER.error("DuiBaRelationShipService add Error", e.fillInStackTrace());
		}
	}
	
	@Override
	public void update(DuiBaRelationShip bean) {
		try {
			this.dao.updateBean(bean);
		} catch (Exception e) {
			LOGGER.error("DuiBaRelationShipService update Error", e.fillInStackTrace());
		}
	}
	
	@Override
	public DuiBaRelationShip findOne(int id) {
		return this.dao.findOne(id);
	}
}
