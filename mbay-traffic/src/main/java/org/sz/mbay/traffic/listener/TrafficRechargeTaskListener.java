package org.sz.mbay.traffic.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.sz.mbay.traffic.timer.TimerManager;

/**
 * @Description:流量充值定时任务启动监听
 * @author han.han
 * @date 2014-10-15 上午10:55:04 
 *
 */
public class TrafficRechargeTaskListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
	new TimerManager();
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
