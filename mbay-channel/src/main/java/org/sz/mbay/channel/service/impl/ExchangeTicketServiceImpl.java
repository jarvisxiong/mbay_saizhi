package org.sz.mbay.channel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.channel.bean.ExchangeTicket;
import org.sz.mbay.channel.dao.ExchangeTicketDao;
import org.sz.mbay.channel.service.ExchangeTicketService;

@Service
public class ExchangeTicketServiceImpl extends BaseServiceImpl implements ExchangeTicketService {
	
	@Autowired
	private ExchangeTicketDao exchangeTicketDao;
	
	@Override
	public void createExchangeTicket(ExchangeTicket ticket) {
		exchangeTicketDao.createExchangeTicket(ticket);
	}
	
}
