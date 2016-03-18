package org.sz.mbay.apptemptation.dao;

import java.util.List;

import org.sz.mbay.apptemptation.bean.AppTemptationBindIp;
import org.sz.mbay.base.dao.BaseDao;

/**
 * app诱惑ip bind
 * 
 * @author jerry
 */
public interface AppTemptationBindIpDao extends BaseDao<AppTemptationBindIp> {
	
	/**
	 * 根据用户编号查找ip bind
	 * 
	 * @param usernumber
	 * @return
	 */
	List<AppTemptationBindIp> findAppTemptationBindIpsByCampaignNumber(String campaignNumber);
	
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
