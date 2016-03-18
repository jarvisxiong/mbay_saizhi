package org.sz.mbay.mall.service;

import java.util.List;

import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.mall.bean.MallExchangeItem;
import org.sz.mbay.mall.enums.MallAudit;
import org.sz.mbay.mall.enums.MallStatus;
import org.sz.mbay.mall.qo.MallExchangeItemQO;

public interface MallExchangeItemService {
	
	/**
	 * 删除
	 * 
	 * @param itemNumber
	 * @return
	 */
	public ExecuteResult del(String itemNumber);
	
	/**
	 * 新增
	 * 
	 * @param bean
	 */
	public void add(MallExchangeItem bean);
	
	/**
	 * 更新
	 * 
	 * @param bean
	 */
	public void update(MallExchangeItem bean);
	
	/**
	 * 查询-单条
	 * 
	 * @param itemNumber
	 * @return
	 */
	public MallExchangeItem findOne(String itemNumber);
	
	/**
	 * 查询-分页
	 * 
	 * @param qo
	 * @param pageinfo
	 * @return
	 */
	public List<MallExchangeItem> findList(MallExchangeItemQO qo, PageInfo pageinfo);
	
	/**
	 * 上/下架
	 * 
	 * @param itemNumber
	 * @param status
	 */
	public ExecuteResult changeStatus(String itemNumber, MallStatus status);
	
	/**
	 * 修改审核状态
	 * 
	 * @param itemNumber
	 * @param audit
	 * @return
	 */
	public ExecuteResult changeAudit(String itemNumber, MallAudit audit);
	
	/**
	 * 修改库存(原有数值上加入remainder)
	 * 
	 * @param itemNumber
	 * @param remainder
	 */
	public ExecuteResult changeRemainder(String itemNumber, int remainder);
}
