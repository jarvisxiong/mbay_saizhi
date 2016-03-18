package org.sz.mbay.channel.dao.impl;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.channel.bean.ExchangeTicket;
import org.sz.mbay.channel.dao.ExchangeTicketDao;

@Repository
public class ExchangeTicketDaoImpl extends BaseDaoImpl<ExchangeTicket> implements ExchangeTicketDao {

	@Override
	public void createExchangeTicket(ExchangeTicket ticket) {
		template.insert("createExchangeTicket", ticket);
	}
	
}
