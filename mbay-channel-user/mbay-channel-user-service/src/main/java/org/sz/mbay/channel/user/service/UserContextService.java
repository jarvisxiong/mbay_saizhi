package org.sz.mbay.channel.user.service;

import org.sz.mbay.base.service.BaseService;
import org.sz.mbay.channel.user.bean.UserContext;
import org.sz.mbay.channel.user.bean.UserRemindPoint;

public interface UserContextService extends BaseService {

	/**
	 * 渠道商上下文信息
	 * 
	 * @param userid
	 * @return
	 */
	public UserContext findUserContext(String usernumber);

	/**
	 * 初始化usercontext
	 * 
	 * @param userid
	 * @return
	 */
	public void initUserContext(String usernumber);

	/**
	 * 修改渠道商策略使用信息
	 * 
	 * @param userid
	 * @return
	 */
	public boolean updateUserStrategyInUserCount(int userid);

	/**
	 * 查询用户短信数量
	 * 
	 * @param userNumber
	 * @return
	 */
	public int findSmsCount(String userNumber);

	/**
	 * @param userNumber
	 * @param smsAmount
	 */
	public void accretionSMS(String userNumber, int smsAmount);

	/**
	 * 发送短信扣除用户一条短信
	 * 
	 * @param userNumber
	 * @return
	 */
	public boolean deductOneSMS(String userNumber);

	/**
	 * 更具用户id得到用户短信数量提醒阀值
	 * 
	 * @param userNumber
	 * @return
	 */
	public int findSmsRemindNum(String userNumber);

	/**
	 * 更具用户id得到用户美贝短信提醒阀值
	 * 
	 * @param userNumber
	 * @return
	 */
	public int findMbayRemindNum(String userNumber);

	/**
	 * 根据用户编号跟新用户对应的用户提醒对象（美贝、短信剩余）
	 * 
	 * @param remind
	 *            用户提醒对象
	 * @param userNumber
	 *            用户编号
	 * @return
	 */
	public boolean updateUserRemindPoint(UserRemindPoint remind,
			String userNumber);

	/**
	 * 根据用户编号得到用户提醒封装类
	 * 
	 * @param usernumber
	 *            用户编号
	 * @return 用户提醒封装类（bean.wrap）
	 */
	public UserRemindPoint findUserRemindPoint(String usernumber);

	/**
	 * 根据用户编号，跟新用户是否还需要美贝阀值提醒
	 * 
	 * @param usernumber
	 *            用户编号
	 * @param mbay_sent
	 *            是否还需要mbay余额提醒
	 * @return
	 */
	public int updateMbaySent(String usernumber, boolean mbay_sent);

	/**
	 * 根据用户编号，跟新用户是否还需要短信阀值提醒
	 * 
	 * @param usernumber
	 *            用户编号
	 * @param sms_sent
	 *            是否还需要美贝阀值提醒
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