package org.sz.mbay.apptemptation.service;

import java.util.List;

import org.sz.mbay.apptemptation.bean.AppTemptationBindIp;

public interface AppTemptationBindIpService {
	
	/**
	 * 根据活动编号和用户编号查找ip bind
	 * 
	 * @param campaignNumber
	 * @param userNumber
	 * @return
	 */
	List<AppTemptationBindIp> findAppTemptationBindIps(String campaignNumber);
	
	/**
	 * 根据id删除记录
	 * 
	 * @param id
	 * @return 
	 */
	boolean deleteAppTemptationBindIpById(Integer id);
	
	/**
	 * 新增ip
	 * 
	 * @param number
	 * @param ip
	 * @return
	 */
	Integer addAppTemptationBindIp(String number, String ip);
}
