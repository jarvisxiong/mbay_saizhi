package org.sz.mbay.mall.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.mall.bean.MallExchangeItem;
import org.sz.mbay.mall.dao.MallExchangeItemDao;
import org.sz.mbay.mall.enums.MallAudit;
import org.sz.mbay.mall.enums.MallItemType;
import org.sz.mbay.mall.enums.MallStatus;
import org.sz.mbay.mall.qo.MallExchangeItemQO;
import org.sz.mbay.mall.service.MallCouponTicketService;
import org.sz.mbay.mall.service.MallExchangeItemService;
import org.sz.mbay.mall.service.MallExtendLimitService;
import org.sz.mbay.mall.service.MallPictureService;

@Service
public class MallExchangeItemServiceImpl implements MallExchangeItemService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MallExchangeItemServiceImpl.class);
	
	@Autowired
	MallExchangeItemDao dao;
	@Autowired
	MallExtendLimitService limitService;
	@Autowired
	MallPictureService pictureService;
	@Autowired
	MallCouponTicketService ticketService;
	
	@Override
	public List<MallExchangeItem> findList(MallExchangeItemQO qo, PageInfo pageinfo) {
		return this.dao.findList(qo, pageinfo);
	}
	
	@Override
	@Transactional
	public ExecuteResult del(String itemNumber) {
		MallExchangeItem bean = this.dao.findOne(itemNumber);
		if (bean == null) {
			return new ExecuteResult(false, "没有找到对应的记录");
		}
		
		try {
			//删除额外限制和图片
			limitService.del(itemNumber);
			pictureService.del(itemNumber);
			if(bean.getType().equals(MallItemType.COUPON)){
				ticketService.del(bean.getItemNumber());
			}
			this.dao.deleteBean(itemNumber);
			return ExecuteResult.successExecute;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOGGER.error("MallExchangeItemService del Error", e.fillInStackTrace());
		}
		return new ExecuteResult(false, "删除失败");
	}
	
	@Override
	public void add(MallExchangeItem bean) {
		try {
			this.dao.createBean(bean);
		} catch (Exception e) {
			LOGGER.error("MallExchangeItemService add Error", e.fillInStackTrace());
			//删除额外限制和图片
			limitService.del(bean.getItemNumber());
			pictureService.del(bean.getItemNumber());
		}
	}
	
	@Override
	public void update(MallExchangeItem bean) {
		try {
			this.dao.updateBean(bean);
		} catch (Exception e) {
			LOGGER.error("MallExchangeItemService update Error", e.fillInStackTrace());
		}
	}
	
	@Override
	public MallExchangeItem findOne(String itemNumber) {
		return this.dao.findOne(itemNumber);
	}
	
	@Override
	public ExecuteResult changeStatus(String itemNumber, MallStatus status) {
		MallExchangeItem bean = this.dao.findOne(itemNumber);
		if (bean == null) {
			return new ExecuteResult(false, "没有找到对应的记录");
		}
		return this.dao.changeStatus(itemNumber, status);
	}
	
	@Override
	public ExecuteResult changeAudit(String itemNumber, MallAudit audit) {
		return this.dao.changeAudit(itemNumber, audit);
	}
	
	@Override
	public ExecuteResult changeRemainder(String itemNumber, int remainder) {
		return this.dao.changeRemainder(itemNumber, remainder);
	}
}
