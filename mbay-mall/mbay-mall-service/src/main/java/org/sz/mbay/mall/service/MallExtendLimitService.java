package org.sz.mbay.mall.service;

import org.sz.mbay.mall.bean.MallExtendLimit;

public interface MallExtendLimitService {
	
	/**
	 * 删除
	 * 
	 * @param itemNumber
	 * @return
	 */
	public boolean del(String itemNumber);
	
	/**
	 * 新增
	 * 
	 * @param bean
	 */
	public void add(MallExtendLimit bean);
	
	/**
	 * 更新
	 * 
	 * @param bean
	 */
	public void update(MallExtendLimit bean);
	
	/**
	 * 查询-单条
	 * 
	 * @param itemNumber
	 * @return
	 */
	public MallExtendLimit findOne(String itemNumber);
	
}
