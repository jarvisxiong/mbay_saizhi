package org.sz.mbay.mall.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.mall.bean.MallCouponTicket;

public interface MallCouponTicketDao extends BaseDao<MallCouponTicket> {
	
	/**
	 * 根据兑换码编号查询
	 * @param itemNumber
	 * @param ticket
	 * @param pageinfo
	 * @return
	 */
	public List<MallCouponTicket> findList(String itemNumber, String ticket, PageInfo pageinfo);
	
	/**
	 * 根据兑换码编号删除
	 * @param itemNumber
	 * @return
	 */
	public boolean deleteMallCouponTicketByItemNumber(String itemNumber);
}
