package org.sz.mbay.mall.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.mall.bean.MallCouponTicket;
import org.sz.mbay.mall.dao.MallCouponTicketDao;

@Repository
public class MallCouponTicketDaoImpl extends BaseDaoImpl<MallCouponTicket> implements MallCouponTicketDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MallCouponTicketDaoImpl.class);
	
	@Override
	public List<MallCouponTicket> findList(String itemNumber, String ticket, PageInfo pageinfo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("itemNumber", itemNumber);
		map.put("ticket", ticket);
		List<MallCouponTicket> list = null;
		try {
			list = super.findList(map, pageinfo, "MallCouponTicket");
		} catch (Exception e) {
			LOGGER.error("MallCouponTicketDao findList Error", e.fillInStackTrace());
		}
		return list;
	}
	
	@Override
	public boolean deleteMallCouponTicketByItemNumber(String itemNumber) {
		try {
			return template.delete("deleteMallCouponTicketByItemNumber", itemNumber) > 0;
		} catch (Exception e) {
			LOGGER.error("MallCouponTicketDao deleteMallCouponTicketByItemNumber Error", e.fillInStackTrace());
		}
		return false;
	}
}
