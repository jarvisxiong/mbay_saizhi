package cn.mbpartner.mbay.activity.check.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mbpartner.mbay.activity.check.dao.EventDao;
import cn.mbpartner.mbay.activity.check.service.EventService;

@Service("eventService")
public class EventServiceImpl implements EventService {
    @Autowired
    EventDao eventDao;

    public void setEventDao(EventDao eventDao) {
	this.eventDao = eventDao;
    }

    @Override
    public void checkDateByUsernumber(String usernumber) {
		this.eventDao.checkDateByUsernumber(usernumber);
    }
}
