package org.sz.mbay.channel.user.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.channel.user.qo.TrafficDetailQO;
import org.sz.mbay.channel.user.qo.TrafficTransferQO;
import org.sz.mbay.channel.useraccount.bean.MbayTraffic;
import org.sz.mbay.channel.useraccount.bean.MbayTrafficTransferOrder;
import org.sz.mbay.channel.useraccount.bean.UserMbayTrafficTrade;

public interface MbayTrafficDao extends BaseDao<MbayTraffic>  {
	
	/**
	 * 根据用户编号查询用户美贝流量
	 * @param userNumber 用户编号
	 * @return
	 */
	public MbayTraffic findMbayTraffic(String userNumber);
	
	public MbayTraffic findMbayTrafficWithLocked(String userNumber);
	
	/**
	 * 扣减用户美贝流量
	 * @param userNumber 用户编号
	 * @param traffic 扣减流量大小，请保证为正数
	 */
	public void reduceMbayTraffic(String userNumber,double traffic);
	
	
	/**
	 * 增加用户美贝流量
	 * @param userNumber
	 * @param traffic
	 */
	public void increaseMbayTraffic(String userNumber,double traffic);
	
	
	/**
	 * 查询转账订单
	 * 
	 * @return
	 */
	public List<MbayTrafficTransferOrder> findAllTrafficTransferOrder(
			TrafficTransferQO qo, PageInfo pageInfo);

	/**
	 * 查询用户流量池交易详情
	 * 
	 * @param qo
	 * @param pageInfo
	 * @return
	 */
	public List<UserMbayTrafficTrade> findAllMbayTrafficDetail(
			TrafficDetailQO qo, PageInfo pageInfo);
	
	

	/**
	 * 锁定商户美贝流量池
	 * @param userNumber 用户编号
	 * @param traffic 锁定大小
	 * @return
	 */
	public int lockedTraffic(String userNumber,double traffic);
	
	/**
	 * 解锁美贝流量锁定
	 * @param userNumber 用户编号
	 * @param traffic  解锁大小
	 * @return
	 */
	public int unlockedTraffic(String userNumber,double traffic);
	
	

}
