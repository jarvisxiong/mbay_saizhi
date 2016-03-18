package org.sz.mbay.channel.user.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.channel.user.bean.UserContext;
import org.sz.mbay.channel.user.bean.UserRemindPoint;
import org.sz.mbay.channel.user.dao.UserContextDao;
import org.sz.mbay.channel.user.service.UserContextService;

@Service("UserContextServiceImpl")
public class UserContextServiceImpl extends BaseServiceImpl implements
		UserContextService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserContextServiceImpl.class);
	
	@Autowired
	UserContextDao usercontextdao;
	
	@Override
	public UserContext findUserContext(String usernumber) {
		return this.usercontextdao.findUserContext(usernumber);
	}
	
	@Override
	public boolean updateUserStrategyInUserCount(int userid) {
		return this.usercontextdao.updateUserStrategyInUserCount(userid) > 0;
	}
	
	@Override
	public void initUserContext(String usernumber) {
		this.usercontextdao.initUserContext(usernumber);
	}
	
	@Override
	public int findSmsCount(String userNumber) {
		return this.usercontextdao.findSmsCount(userNumber);
	}
	
	@Override
	public void accretionSMS(String userNumber, int smsAmount) {
		try {
			this.usercontextdao.accretionSMS(userNumber, smsAmount);
		} catch (Exception e) {
			LOGGER.error("accretionSMS", e.fillInStackTrace());
		}
	}
	
	@Override
	public boolean deductOneSMS(String userNumber) {
		// /if(this.findSmsCount(userNumber)>0){
		return this.usercontextdao.deductUserSMS(userNumber, 1) > 0;
		// /}
		// /return false;
	}
	
	@Override
	public int findSmsRemindNum(String userNumber) {
		return this.usercontextdao.findUserSmsRemindNum(userNumber);
	}
	
	@Override
	public int findMbayRemindNum(String userNumber) {
		return this.usercontextdao.findUserMbayRemindNum(userNumber);
	}
	
	@Override
	public boolean updateUserRemindPoint(UserRemindPoint remind,
			String userNumber) {
		return this.usercontextdao.updateUserRemindPoint(remind, userNumber) > 0;
	}
	
	@Override
	public UserRemindPoint findUserRemindPoint(String usernumber) {
		UserRemindPoint remind = this.usercontextdao
				.findUserRemindPoint(usernumber);
		if (remind == null) {
			// 数据库中无记录，插入记录。
			remind = new UserRemindPoint();
			remind.setMbayRemindPoint(500);
			remind.setSmsRemindPoint(50);
			remind.setMbay_sent(false);
			remind.setSms_sent(false);
			remind.setUsernumber(usernumber);
			this.usercontextdao.createBean(remind);
		}
		return remind;
	}
	
	@Override
	public int updateMbaySent(String usernumber, boolean mbay_sent) {
		return this.usercontextdao.updateMbaySent(usernumber, mbay_sent);
	}
	
	@Override
	public int updateSmsSent(String usernumber, boolean sms_sent) {
		return this.usercontextdao.updateSmsSent(usernumber, sms_sent);
	}

	@Override
	public void increaseOneCamNumInActive(String userNumber) {
		this.usercontextdao.increaseOneCamNumInActive(userNumber);
	}

	

	@Override
	public void deductOneCamNumInActive(String userNumber) {
		this.usercontextdao.deductOneCamNumInActive(userNumber);
		
	}
	
}