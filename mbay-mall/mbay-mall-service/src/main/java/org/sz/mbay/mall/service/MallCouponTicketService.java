package org.sz.mbay.mall.service;

import java.util.List;

import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.mall.bean.MallCouponTicket;

public interface MallCouponTicketService {
	
	/**
	 * 删除
	 * 
	 * @param itemNumber
	 * @return
	 */
	public boolean del(String itemNumber);
	
	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public boolean del(int id);
	
	/**
	 * 新增
	 * 
	 * @param bean
	 */
	public void add(MallCouponTicket bean);
	
	/**
	 * 根据兑换码编号查询
	 * 
	 * @param itemNumber
	 * @param ticket
	 * @param pageinfo
	 * @return
	 */
	public List<MallCouponTicket> findList(String itemNumber, String ticket, PageInfo pageinfo);
	
}
