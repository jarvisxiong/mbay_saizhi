package org.sz.mbay.channel.user.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.channel.user.qo.TrafficDetailQO;
import org.sz.mbay.channel.user.qo.TrafficTransferQO;
import org.sz.mbay.channel.useraccount.bean.RedTraffic;
import org.sz.mbay.channel.useraccount.bean.RedTrafficTransferOrder;
import org.sz.mbay.channel.useraccount.bean.UserRedTrafficTrade;

public interface RedTrafficDao extends BaseDao<RedTraffic>  {
	
	/**
	 * 根据用户编号查询用户美贝流量
	 * @param userNumber 用户编号
	 * @return
	 */
	public RedTraffic findRedTraffic(String userNumber);
	
	/**
	 * 根据用户编号查询用户美贝流量  并锁定
	 * @param userNumber 用户编号
	 * @return
	 */
	public RedTraffic findRedTrafficWithLocked(String userNumber);
	
	/**
	 * 扣减用户美贝流量
	 * @param userNumber 用户编号
	 * @param traffic 扣减流量大小，请保证为正数
	 */
	public void reduceRedTraffic(String userNumber,double traffic);
	
	
	/**
	 * 增加用户美贝流量
	 * @param userNumber
	 * @param traffic
	 */
	public void increaseRedTraffic(String userNumber,double traffic);
	
	
	/**
	 * 查询转账订单
	 * 
	 * @return
	 */
	public List<RedTrafficTransferOrder> findAllTrafficTransferOrder(
			TrafficTransferQO qo, PageInfo pageInfo);

	/**
	 * 查询用户流量池交易详情
	 * 
	 * @param qo
	 * @param pageInfo
	 * @return
	 */
	public List<UserRedTrafficTrade> findAllRedTrafficDetail(
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
