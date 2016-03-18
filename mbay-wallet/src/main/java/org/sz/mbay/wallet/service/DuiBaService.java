package org.sz.mbay.wallet.service;

import java.util.Map;

import org.sz.mbay.base.wrap.Response;
import org.sz.mbay.wallet.bean.DuiBa;
import org.sz.mbay.wallet.enums.DuiBaState;

public interface DuiBaService {
	
	/**
	 * 获取状态
	 * 
	 * @param orderNumber
	 * @return
	 */
	DuiBaState getState(String orderNumber);
	
	/**
	 * 创建记录
	 * 
	 * @param duiBa
	 * @return
	 */
	DuiBa create(DuiBa duiBa);
	
	/**
	 * 更新状态
	 * 
	 * @param orderNumber
	 * @param operated
	 * @return
	 */
	boolean updateState(String orderNumber, DuiBaState operated);
	
	/**
	 * 根据编号查找记录
	 * 
	 * @param orderNumber
	 * @return
	 */
	DuiBa findByOrderNumber(String orderNumber);
	
	/**
	 * 根据流水号查找记录
	 * 
	 * @param serialNumber
	 * @return
	 */
	DuiBa findBySerialNumber(String serialNumber);
	
	/**
	 * 兑换扣款
	 * 
	 * @param params
	 * @return
	 */
	Response reduceBalance(Map<String, Object> params);
	
	/**
	 * 兑换扣款失败回滚
	 * 
	 * @param orderNumber
	 * @return
	 */
	Response rollback(String orderNumber);
	
}
