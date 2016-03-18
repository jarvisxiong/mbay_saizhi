package org.sz.mbay.channel.service;

import org.sz.mbay.channel.bean.ExchangeTicket;

/**
 * 券包服务层
 * 
 * @author jerry
 */
public interface ExchangeTicketService {
	
	/**
	 * 创建券包
	 * 
	 * @param ticket
	 */
	void createExchangeTicket(ExchangeTicket ticket);
	
}
