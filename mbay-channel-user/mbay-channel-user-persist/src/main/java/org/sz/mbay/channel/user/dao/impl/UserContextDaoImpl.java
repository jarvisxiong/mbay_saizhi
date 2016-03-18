package org.sz.mbay.channel.user.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.channel.user.bean.UserContext;
import org.sz.mbay.channel.user.bean.UserRemindPoint;
import org.sz.mbay.channel.user.dao.UserContextDao;

@Repository
public class UserContextDaoImpl extends BaseDaoImpl<UserContext> implements
		UserContextDao {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserContextDaoImpl.class);
	
	@Override
	public UserContext findUserContext(String usernumber) {
		return super.template.selectOne("findUserContext", usernumber);
	}
	
	@Override
	public int updateUserStrategyInUserCount(long userid) {
		return this.template.update("updateUserStrategyInUserCount", userid);
	}
	
	@Override
	public void initUserContext(String usernumber) {
		this.template.update("initUserContext", usernumber);
	}
	
	@Override
	public int findSmsCount(String userNumber) {
		return this.template.selectOne("selectSmsCount", userNumber);
	}
	
	@Override
	public void accretionSMS(String userNumber, int smsAmount) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userNumber", userNumber);
		map.put("smsAmount", "" + smsAmount);
		try {
			this.template.update("accretionSMS", map);
		} catch (Exception e) {
			LOGGER.error("accretionSMS", e.fillInStackTrace());
		}
	}
	
	@Override
	public int deductUserSMS(String usernumber, int smsAmount) {
		if (smsAmount < 0) {
			smsAmount = 0 - smsAmount;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("usernumber", usernumber);
		map.put("smsAmount", smsAmount);
		return this.template.update("deductUserSMS", map);
	}
	
	@Override
	public int findUserMbayRemindNum(String userNumber) {
		return this.template.selectOne("findMbayRemindNum", userNumber);
	}
	
	@Override
	public int findUserSmsRemindNum(String userNumber) {
		return this.template.selectOne("findSmsRemindNum", userNumber);
	}
	
	@Override
	public int updateUserRemindPoint(UserRemindPoint remind,
			String userNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("remind", remind);
		map.put("userNumber", userNumber);
		return this.template.update("updateUserRemindPoint", map);
	}
	
	@Override
	public UserRemindPoint findUserRemindPoint(String usernumber) {
		return this.template.selectOne("findUserRemindPoint", usernumber);
	}
	
	@Override
	public int updateMbaySent(String usernumber, boolean mbay_sent) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("usernumber", usernumber);
		map.put("mbay_sent", mbay_sent);
		return this.template.update("updateMbaySent", map);
	}
	
	@Override
	public int updateSmsSent(String usernumber, boolean sms_sent) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("usernumber", usernumber);
		map.put("sms_sent", sms_sent);
		return this.template.update("updateSmsSent", map);
	}
	
	

	@Override
	public void increaseOneCamNumInActive(String userNumber) {
		this.template.update("increaseNumOfCampaignInTime", userNumber);		
	}

	@Override
	public void deductOneCamNumInActive(String userNumber) {
		this.template.update("deductOneCamNumInActive", userNumber);
		
	}
}
