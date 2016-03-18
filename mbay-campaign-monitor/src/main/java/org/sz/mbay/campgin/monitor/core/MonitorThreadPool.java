package org.sz.mbay.campgin.monitor.core;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.mbay.base.context.SpringApplicationContext;
import org.sz.mbay.camapgin.common.service.CampaignService;

public class MonitorThreadPool {

	private static Logger logger = LoggerFactory
			.getLogger(MonitorThreadPool.class);

	public static void start() {
		if (logger.isInfoEnabled()) {
			logger.info("启动线程池，执行任务");
		}
		Map<String, CampaignService> campaignServices = SpringApplicationContext
				.getBeansOfType(CampaignService.class);
		if (logger.isInfoEnabled()) {
			logger.info("CampaignService 实现类数量:{}", campaignServices.size());
		}
		campaignServices.forEach((serviceName, campaignService) -> {
			if (logger.isInfoEnabled()) {
				logger.info(serviceName + "启动检测");
				process(campaignService);

			}

		});
	}

	private static void process(CampaignService campaignService) {
		ExecutorService executorService = Executors.newFixedThreadPool(20);
		List<String> overCampaignNumbers = campaignService
				.findAllCampaignNumberOverToday();
		overCampaignNumbers.forEach((camapaignNumber) -> {
			executorService.execute(() -> {
				logger.info("执行[" + campaignService.getClass().getSimpleName()
						+ "]:" + camapaignNumber + " 转变为已结束");
				try {
					campaignService.overCampaign(camapaignNumber);
				} catch (Exception e) {
					logger.error(
							"执行[" + campaignService.getClass().getSimpleName()
									+ "]:" + camapaignNumber + " 转变为已结束 异常",
							e.fillInStackTrace());
				}

			});

		});
		List<String> startCampaignNumbers = campaignService
				.findAllCampaignNumberStartToday();
		startCampaignNumbers.forEach((camapaignNumber) -> {
			logger.info("执行[" + campaignService.getClass().getSimpleName()
					+ "]:" + camapaignNumber + " 转变为 活动中");
			try {
				campaignService.startCampaign(camapaignNumber);
			} catch (Exception e) {
				logger.error(
						"执行[" + campaignService.getClass().getSimpleName()
								+ "]:" + camapaignNumber + " 开始活动 异常",
						e.fillInStackTrace());
			}

		});

	}

}
