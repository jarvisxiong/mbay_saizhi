package org.sz.mbay.mall.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.mall.bean.MallExtendLimit;
import org.sz.mbay.mall.dao.MallExtendLimitDao;
import org.sz.mbay.mall.service.MallExtendLimitService;

@Service
public class MallExtendLimitServiceImpl implements MallExtendLimitService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MallExtendLimitServiceImpl.class);
	
	@Autowired
	MallExtendLimitDao dao;
	
	@Override
	public boolean del(String itemNumber) {
		try {
			return this.dao.deleteBean(itemNumber) > 0;
		} catch (Exception e) {
			LOGGER.error("MallExtendLimitService del Error", e.fillInStackTrace());
		}
		return false;
	}
	
	@Override
	public void add(MallExtendLimit bean) {
		try {
			this.dao.createBean(bean);
		} catch (Exception e) {
			LOGGER.error("MallExtendLimitService add Error", e.fillInStackTrace());
		}
	}
	
	@Override
	public void update(MallExtendLimit bean) {
		try {
			this.dao.updateBean(bean);
		} catch (Exception e) {
			LOGGER.error("MallExtendLimitService update Error", e.fillInStackTrace());
		}
	}
	
	@Override
	public MallExtendLimit findOne(String itemNumber) {
		return this.dao.findOne(itemNumber);
	}
	
}
