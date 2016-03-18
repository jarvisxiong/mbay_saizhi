package org.sz.mbay.mall.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.mall.bean.MallExchangeItem;
import org.sz.mbay.mall.dao.MallExchangeItemDao;
import org.sz.mbay.mall.enums.MallAudit;
import org.sz.mbay.mall.enums.MallStatus;
import org.sz.mbay.mall.qo.MallExchangeItemQO;

@Repository
public class MallExchangeItemDaoImpl extends BaseDaoImpl<MallExchangeItem> implements MallExchangeItemDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MallExchangeItemDaoImpl.class);
	
	@Override
	public MallExchangeItem findOne(String itemNumber) {
		MallExchangeItem bean = null;
		try {
			bean = this.template.selectOne("findMallExchangeItemByItemNumber", itemNumber);
		} catch (Exception e) {
			LOGGER.error("MallExchangeItemDao findOne Error", e.fillInStackTrace());
		}
		return bean;
	}
	
	@Override
	public List<MallExchangeItem> findList(MallExchangeItemQO qo, PageInfo pageinfo) {
		List<MallExchangeItem> list = null;
		try {
			list = super.findList(qo, pageinfo, "MallExchangeItem");
		} catch (Exception e) {
			LOGGER.error("MallExchangeItemDao findList Error", e.fillInStackTrace());
		}
		return list;
	}

	@Override
	public ExecuteResult changeStatus(String itemNumber, MallStatus status) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(status.equals(MallStatus.ON)){
			map.put("audit", MallAudit.ONSHELF);
		}else if(status.equals(MallStatus.OFF)){
			map.put("audit", MallAudit.OFFSHELF);
		}
		map.put("itemNumber", itemNumber);
		map.put("status", status);
		try {
			this.template.update("updateMallExchangeItemStatus", map);
			return ExecuteResult.successExecute;
		} catch (Exception e) {
			LOGGER.error("MallExchangeItemDao change Error", e.fillInStackTrace());
		}
		return new ExecuteResult(false, "操作失败");
	}
	
	@Override
	public ExecuteResult changeAudit(String itemNumber, MallAudit audit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("itemNumber", itemNumber);
		map.put("audit", audit);
		try {
			this.template.update("updateMallExchangeItemAudit", map);
			return ExecuteResult.successExecute;
		} catch (Exception e) {
			LOGGER.error("MallExchangeItemDao change Error", e.fillInStackTrace());
		}
		return new ExecuteResult(false, "操作失败");
	}
	
	@Override
	public ExecuteResult changeRemainder(String itemNumber, int remainder) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("itemNumber", itemNumber);
		map.put("remainder", remainder);
		try {
			this.template.update("updateMallExchangeItemRemainder", map);
			return ExecuteResult.successExecute;
		} catch (Exception e) {
			LOGGER.error("MallExchangeItemDao changeRemainder Error", e.fillInStackTrace());
		}
		return new ExecuteResult(false, "操作失败");
	}
}
