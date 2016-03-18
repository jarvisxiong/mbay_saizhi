package org.sz.mbay.channel.user.service;

import java.util.List;

import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.user.enums.TradeType;
import org.sz.mbay.channel.user.exception.RedAccountTradeException;
import org.sz.mbay.channel.user.qo.TrafficDetailQO;
import org.sz.mbay.channel.user.qo.TrafficTransferQO;
import org.sz.mbay.channel.user.wrap.TrafficTransferInfo;
import org.sz.mbay.channel.useraccount.bean.RedTraffic;
import org.sz.mbay.channel.useraccount.bean.RedTrafficTransferOrder;
import org.sz.mbay.channel.useraccount.bean.UserRedTrafficTrade;

public interface RedTrafficAccountService {
	
	public RedTraffic findRedTraffic(String userNumber);
	
	/**
	 * 创建红包账户
	 * @param redTraffic
	 */
	public RedTraffic createRedTrafficAccount(String userNumber);

	/**
	 * 根据用户编号查询用户流量
	 * 
	 * @param userNumber
	 *            用户编号
	 * @return
	 */
	public double  getBalance(String userNumber);
	
	
	/**
	 * @param userNumber
	 * @return
	 */
	public double getAvailableBalance(String userNumber);

	/**
	 * 流量池流量消耗
	 * 
	 * @param traffic
	 *            流量大小
	 * @param userNumber
	 *            用户编号
	 * @param relateNumber
	 *            关联号
	 * @param description
	 *            描述
	 * @return
	 */
	public void expenditure(double traffic, String userNumber,
			TradeType tradeType, String relateNumber, String description) throws RedAccountTradeException;
	

	/**
	 * 流量池流量划入
	 * 
	 * @param traffic
	 * @param userNumber
	 * @param relateNumber
	 * @param description
	 * @return
	 */
	public void income(double traffic, String userNumber,
			TradeType tradeType, String relateNumber, String description)throws RedAccountTradeException;

	/**
	 * 流量转账
	 * 
	 * @param fromUserNumber
	 *            转出方
	 * @param toUserNumer
	 *            转入方
	 * @param traffic
	 */
	public ExecuteResult transfer(TrafficTransferInfo transferInfo);

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
	public void lockedTraffic(String userNumber,double traffic)throws RedAccountTradeException;
	
	/**
	 * 解锁美贝流量锁定
	 * @param userNumber 用户编号
	 * @param traffic  解锁大小
	 * @return
	 */
	public void unlockedTraffic(String userNumber,double traffic)throws RedAccountTradeException;
	
	/**
	 * @param transferInAmount
	 * @param userNumber
	 * @return
	 */
	public ExecuteResult transferInFromMbayAccount(double transferInAmount,String userNumber);

}
