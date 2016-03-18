package org.sz.mbay.statistics.context;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.sz.mbay.statistics.event.BaseEvent;
import org.sz.mbay.statistics.task.LogInitTask;

/**
 * 保存日志对象的上下文
 * 
 * 每天0点会被任务初始化
 * 
 * 此log用来记录统计信息
 * 
 * @author jerry
 */
public class StatisticsLog {
	
	private static Logger LOG;
	
	public static void setLogger(Logger log) {
		if (log != null) {
			LOG = log;
		}
	}
	
	public static void log(BaseEvent evt) {
		log(evt.statisticsString());
	}
	
	public static void log(String message) {
		if (StringUtils.isNotEmpty(message)) {
			if (LOG == null) {
				// 服务器重启定时任务没有执行时，手动初始化日志对象
				LogInitTask.initLog();
			}
			LOG.info(message);
		}
	}
	
}
