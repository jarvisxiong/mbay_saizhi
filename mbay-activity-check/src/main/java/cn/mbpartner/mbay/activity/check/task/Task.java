package cn.mbpartner.mbay.activity.check.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.mbpartner.mbay.activity.check.context.SpringApplicationContext;
import cn.mbpartner.mbay.activity.check.service.EventService;

public class Task {

    private static Logger logger = LoggerFactory.getLogger(Task.class);
    private String usernumber;

    public Task(String usernumber) {
	this.usernumber = usernumber;
    }

    public String getUsernumber() {
	return usernumber;
    }

    public void setUsernumber(String usernumber) {
	this.usernumber = usernumber;
    }

    public void work() {
	EventService eventService = (EventService) SpringApplicationContext
		.getBean("eventService");
	eventService.checkDateByUsernumber(usernumber);
	logger.info("market Activity checked:::at:::" + new Date().toString());
    }
}
