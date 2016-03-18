package org.sz.mbay.mall.dao;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.mall.bean.MallExtendLimit;

public interface MallExtendLimitDao extends BaseDao<MallExtendLimit> {
	
	/**
	 * 根据兑换码编号查询
	 * @param itemNumber
	 * @return
	 */
	public MallExtendLimit findOne(String itemNumber);
	
}
