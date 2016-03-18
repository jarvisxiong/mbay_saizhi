package org.sz.mbay.channel.user.dao;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.channel.useraccount.bean.UserAccount;

public interface UserAccountDao extends BaseDao<UserAccount> {
	
	
	
	
	/**
	 * 减少账户美贝
	 * @param userNumber
	 * @param mbay
	 */
	public boolean reduceAccountMbay(String userNumber,double mbay);
	
	
	/**
	 * 增加账户美贝
	 * @param userNumber
	 * @param mbay
	 */
	public int increaseAccountMbay(String userNumber,double mbay);
	
	
	/**
	 * 查询用户账户
	 * @param userNumber
	 * @return
	 */
	public UserAccount findUserAccount(String userNumber);
	
	/**
	 * 查询用户账户并加锁
	 * @param userNumber
	 * @return
	 */
	public UserAccount findUserAccountWithLocked(String userNumber);
	
	
	/**
	 * 增加用户锁定的美贝
	 * @param userNumber
	 * @param mbay
	 * @return
	 */
	public int increaseLockedMbay(String userNumber, double mbay);
	/**
	 * 减少用户锁定的美贝
	 * @param userNumber
	 * @param mbay
	 * @return
	 */
	public boolean reduceLockedMbay(String userNumber, double mbay);

}
