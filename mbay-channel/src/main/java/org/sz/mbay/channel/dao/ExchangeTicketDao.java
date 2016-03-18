package org.sz.mbay.channel.dao;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.channel.bean.ExchangeTicket;

/**
 * 券包持久层
 * 
 * @author jerry
 */
public interface ExchangeTicketDao extends BaseDao<ExchangeTicket> {
	
	/**
	 * 创建券包
	 * 
	 * @param ticket
	 */
	void createExchangeTicket(ExchangeTicket ticket);
}
