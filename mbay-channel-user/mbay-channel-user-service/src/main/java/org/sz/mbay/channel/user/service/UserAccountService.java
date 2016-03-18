package org.sz.mbay.channel.user.service;

import java.util.List;

import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.user.enums.TradeType;
import org.sz.mbay.channel.user.exception.UserAccountTradeException;
import org.sz.mbay.channel.user.qo.MbayTransferOrderQO;
import org.sz.mbay.channel.user.qo.UserMbayTradeQO;
import org.sz.mbay.channel.useraccount.bean.MbayTransferInfo;
import org.sz.mbay.channel.useraccount.bean.MbayTransferOrder;
import org.sz.mbay.channel.useraccount.bean.UserAccount;
import org.sz.mbay.channel.useraccount.bean.UserMbayTrade;

public interface UserAccountService{
	/**
	 * 账户支出
	 * 记录交易明细.牵涉到此方法的请务必使用事务。此方法未单独使用事务，主要考虑到：若此处单独使用事务，在本事务提交后，而业务方法却因异常回滚，
	 * 导致扣款而未处理业务
	 * 
	 * @param type
	 *            交易类型
	 * @param ordernumber
	 *            交易订单号
	 * @param mbayprice
	 *            交易金额
	 * @param note
	 *            交易备注
	 * @return 成功 ExecuteResult 属性success 为true
	 *         message为交易单号，失败success为false，message为失败原因
	 */
	public void expenditure(final String usernumber,
			TradeType type, String ordernumber, double mbayprice, String note) throws UserAccountTradeException;

	/**
	 * 账户收入美贝
	 * 
	 * @param usernumber
	 *            收入放用户编号
	 * @param type
	 *            收入类型
	 * @param ordernumber
	 *            关联单号
	 * @param mbayprice
	 *            美贝金额
	 * @param note
	 *            备注
	 * @throws DealFailureException
	 *             抛出交易失败异常
	 */
	public void income(final String usernumber,
			TradeType type, String ordernumber, double mbayprice, String note) throws UserAccountTradeException;

	/**
	 * 用户美贝账户
	 * 
	 * @param uid
	 * @return
	 */
	public UserAccount findUserAccount(String usernumber);

	/**
	 * 查询对应账户余额
	 * 
	 * @param uerid
	 * @return
	 */
	public double getAccountAmount(String usernumber);

	/**
	 * 查询对应账户可用余额
	 * 
	 * @param uerid
	 * @return
	 */
	public double getAvailableAmount(String usernumber);

	/**
	 * 转账
	 * 
	 * @param recordid
	 * @return
	 */
	public ExecuteResult transfer(MbayTransferInfo transferInfo);
	
	

	////public boolean existTransferdeal(long orderid, String usernumber);

	/**
	 * 增加用户美贝锁定额
	 * 
	 * @param userid
	 * @param mbay
	 * @return
	 * @throws Exception
	 */
	public void lockedMbay(String usernumber, double mbay) throws UserAccountTradeException;

	/**
	 * 此方法只减少用户锁定额度，不减少总额度 减少用于美贝锁定额
	 * 
	 * @param userid
	 * @param mbay
	 * @return
	 * @throws Exception
	 */
	public void unLockedMbay(String usernumber, double mbay)throws UserAccountTradeException;
	
	
	/**
	 * 查询用户转账订单
	 * @param qo 查询QO
	 * @param pageInfo 分页信息
	 * @return
	 */
	public List<MbayTransferOrder> findAllMbayTransferOrder(MbayTransferOrderQO qo,PageInfo pageInfo);
	
	
	/**
	 * 查询用户美贝交易详情
	 * @param qo
	 * @param pageInfo
	 * @return
	 */
	public List<UserMbayTrade> findAllUserMbayTrade(UserMbayTradeQO qo,PageInfo pageInfo);

//	/**
//	 * 创建美贝预存订单
//	 * 
//	 * @param order
//	 * @return
//	 */
//	public MbayDepositOrder createMbayDepositOrder(MbayDepositOrder order);
//
//	/**
//	 * 根据订单号和用户编号查询预存订单
//	 * 
//	 * @param ordernumber
//	 * @param usernumber
//	 * @return
//	 */j
//	public MbayDepositOrder findMbayDepositOrder(String ordernumber,
//			String usernumber);
//
//	/**
//	 * 根据用户编号查询预存订单列表
//	 * 
//	 * @param userNumber
//	 * @return
//	 */
//	public List<MbayDepositOrder> findAllMbayDepositOrder(PageInfo pageinfo,
//			MbayDepositOrderForm mbayDepositOrderCriteria);
}
