package org.sz.mbay.statistics.task;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.joda.time.DateTime;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.sz.mbay.statistics.context.StatisticsLog;

/**
 * 初始化日志定时任务
 * 
 * @author jerry
 */
public class LogInitTask extends QuartzJobBean {
	
	private static final String BASE_DIR = "statistics";
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		initLog();
	}
	
	/**
	 * 初始化日志对象
	 */
	public static void initLog() {
		DateTime now = DateTime.now();
		String dir = now.toString("yyyyMMdd");
		String hour = String.format("%2d", now.getHourOfDay());
		String logPath = BASE_DIR + "/" + dir + "/" + hour;
		StatisticsLog.setLogger(getLoggerByName(logPath));
	}
	
	/**
	 * 代码初始化log配置
	 * 
	 * @param name
	 * @return
	 */
	private static Logger getLoggerByName(String name) {
		// 生成新的Logger
		// 如果已經有了一個Logger實例返回現有的
		Logger logger = Logger.getLogger(name);
		
		// 清空Appender。特別是不想使用現存實例時一定要初期化
		logger.removeAllAppenders();
		
		// 設定Logger級別。
		logger.setLevel(Level.INFO);
		
		// 設定是否繼承父Logger。
		// 默認為true。繼承root輸出。
		// 設定false後將不輸出root。
		logger.setAdditivity(true);
		
		// 生成新的Appender
		FileAppender appender = new RollingFileAppender();
		PatternLayout layout = new PatternLayout();
		
		// log的输出形式
		String conversionPattern = "[%d{yyyy-MM-dd HH:mm:ss}] - %m%n";
		layout.setConversionPattern(conversionPattern);
		appender.setLayout(layout);
		
		// log输出路径
		// 这里使用了环境变量[catalina.home]，只有在tomcat环境下才可以取到
		String tomcatPath = System.getProperty("catalina.home");
		appender.setFile(tomcatPath + "/logs/" + name + ".log");
		
		// log的文字码
		appender.setEncoding("UTF-8");
		
		// true:在已存在log文件后面追加 false:新log覆盖以前的log
		appender.setAppend(true);
		
		// 适用当前配置
		appender.activateOptions();
		
		// 将新的Appender加到Logger中
		logger.addAppender(appender);
		
		return logger;
	}
	
}
