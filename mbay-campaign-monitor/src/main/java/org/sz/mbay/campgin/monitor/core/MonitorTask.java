package org.sz.mbay.campgin.monitor.core;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component("monitorTask")
public class MonitorTask extends QuartzJobBean {
	private static Logger logger = LoggerFactory
			.getLogger(MonitorTask.class);

	//XmlWebApplicationContext
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		if(logger.isInfoEnabled()){
			logger.info("执行每日 活动 开始/结束检查 定时任务");
		}	
	///	ClassPathXmlApplicationContext
		MonitorThreadPool.start();
	}

}
