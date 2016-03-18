package org.sz.mbay.channel.user.dao;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.channel.user.bean.UserContext;
import org.sz.mbay.channel.user.bean.UserRemindPoint;

public interface UserContextDao extends BaseDao<UserContext> {

	/**
	 * 渠道商上下文信息
	 * 
	 * @param userid
	 * @return
	 */
	public UserContext findUserContext(String usernumber);

	public int updateUserStrategyInUserCount(long userid);

	public void initUserContext(String usernumber);

	public int findSmsCount(String userNumber);

	public void accretionSMS(String userNumber, int smsAmount);

	/**
	 * 扣除用户短信数量
	 * 
	 * @param usernumber
	 *            用户编号
	 * @param smsAmount
	 *            短信数量
	 * @return
	 */
	public int deductUserSMS(String usernumber, int smsAmount);

	/**
	 * 更具用户编号得到美贝值提醒阀值
	 * 
	 * @param userNumber
	 *            用户编号
	 * @return 用户设置的mbay余额阀值
	 */

	public int findUserMbayRemindNum(String userNumber);

	/**
	 * 更具用户编号得到短信数量提醒阀值
	 * 
	 * @param userNumber
	 *            用户编号
	 * @return 用户设置的短信余额阀值
	 */

	public int findUserSmsRemindNum(String userNumber);

	/**
	 * 根据用户编号跟新用户对应的用户提醒对象（美贝、短信剩余）
	 * 
	 * @param remind
	 *            用户提醒对象
	 * @param userNumber
	 *            用户编号
	 * @return
	 */
	public int updateUserRemindPoint(UserRemindPoint remind, String userNumber);

	/**
	 * 更具用户编号得到用户美贝和短信设置的阀值
	 * 
	 * @param usernumber
	 * @return 封装用户提醒阀值类的对象（bean.wrap）
	 */
	public UserRemindPoint findUserRemindPoint(String usernumber);

	/**
	 * 更新用户是否已经发送美贝提醒
	 * 
	 * @param usernumber
	 * @param may_sent
	 * @return
	 */
	public int updateMbaySent(String usernumber, boolean mbay_sent);

	/**
	 * 更新用户是否已经发送断线提醒
	 * 
	 * @param usernumber
	 *            用户编号
	 * @param sms_sent
	 *            是否已经短信余额提醒
	 * @return
	 */
	public int updateSmsSent(String usernumber, boolean sms_sent);

	/**
	 * 增加用户 活动中 数量
	 * 
	 * @param userNumber
	 *            用户编号
	 */
	public void increaseOneCamNumInActive(String userNumber);

	/**
	 * 减少一个用户活动中数量
	 * 
	 * @param userNumber
	 */
	public void deductOneCamNumInActive(String userNumber);

}
