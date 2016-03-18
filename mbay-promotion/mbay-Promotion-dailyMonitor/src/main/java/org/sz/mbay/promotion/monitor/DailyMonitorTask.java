package org.sz.mbay.promotion.monitor;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.sz.mbay.base.context.SpringApplicationContext;
import org.sz.mbay.promotion.service.PromotionProductConfigService;

@Component
public class DailyMonitorTask extends QuartzJobBean {
	
	private static Logger logger = LoggerFactory
			.getLogger(DailyMonitorTask.class);
			
	PromotionProductConfigService productConfigService = SpringApplicationContext
			.getBean(PromotionProductConfigService.class);
			
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("执行每日 当日上线重置  定时任务");
		productConfigService.resetDailyRemain();
		logger.info("执行每日 当日上线重置  执行完毕");
	}
	
}
