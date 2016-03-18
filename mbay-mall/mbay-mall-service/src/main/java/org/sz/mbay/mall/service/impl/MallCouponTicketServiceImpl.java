package org.sz.mbay.mall.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.mall.bean.MallCouponTicket;
import org.sz.mbay.mall.dao.MallCouponTicketDao;
import org.sz.mbay.mall.service.MallCouponTicketService;

@Service
public class MallCouponTicketServiceImpl implements MallCouponTicketService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MallCouponTicketServiceImpl.class);
	
	@Autowired
	MallCouponTicketDao dao;
	
	@Override
	public boolean del(String itemNumber) {
		try {
			return this.dao.deleteMallCouponTicketByItemNumber(itemNumber);
		} catch (Exception e) {
			LOGGER.error("MallCouponTicketService del Error", e.fillInStackTrace());
		}
		return false;
	}
	
	@Override
	public boolean del(int id){
		try {
			return this.dao.deleteBean(id) > 0;
		} catch (Exception e) {
			LOGGER.error("MallCodeService del Error", e.fillInStackTrace());
		}
		return false;
	}
	
	@Override
	public void add(MallCouponTicket bean) {
		try {
			this.dao.createBean(bean);
		} catch (Exception e) {
			LOGGER.error("MallCouponTicketService add Error", e.fillInStackTrace());
		}
	}
	
	@Override
	public List<MallCouponTicket> findList(String itemNumber, String ticket, PageInfo pageinfo) {
		return this.dao.findList(itemNumber, ticket, pageinfo);
	}
	
}
