package org.sz.mbay.mall.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.mall.bean.MallExtendLimit;
import org.sz.mbay.mall.dao.MallExtendLimitDao;

@Repository
public class MallExtendLimitDaoImpl extends BaseDaoImpl<MallExtendLimit> implements MallExtendLimitDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MallExtendLimitDaoImpl.class);
	
	@Override
	public MallExtendLimit findOne(String itemNumber) {
		MallExtendLimit bean = null;
		try {
			bean = this.template.selectOne("findMallExtendLimitByItemNumber", itemNumber);
		} catch (Exception e) {
			LOGGER.error("MallExtendLimitDao findOne Error", e.fillInStackTrace());
		}
		return bean;
	}
	
}
