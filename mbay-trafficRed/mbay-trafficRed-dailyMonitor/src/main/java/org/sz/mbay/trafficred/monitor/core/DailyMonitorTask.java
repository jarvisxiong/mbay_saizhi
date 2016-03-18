package org.sz.mbay.trafficred.monitor.core;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.sz.mbay.base.context.SpringApplicationContext;
import org.sz.mbay.trafficred.service.TrafficRedProductConfigService;

@Component("monitorTask")
public class DailyMonitorTask extends QuartzJobBean {
	
	private static Logger logger = LoggerFactory
			.getLogger(DailyMonitorTask.class);
			
	TrafficRedProductConfigService productConfigService = SpringApplicationContext
			.getBean(TrafficRedProductConfigService.class);
			
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		if (logger.isInfoEnabled()) {
			logger.info("执行每日 当日上线重置  定时任务");
		}
		productConfigService.resetDailyRemain();
		if (logger.isInfoEnabled()) {
			logger.info("执行每日 当日上线重置  执行完毕");
		}
	}
	
}
