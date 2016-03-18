package org.sz.mbay.stationLetter.service.impl;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.stationLetter.bean.StationLetter;
import org.sz.mbay.stationLetter.bean.UserStationLetter;
import org.sz.mbay.stationLetter.dao.StationLetterDao;
import org.sz.mbay.stationLetter.dao.UserStationLetterDao;
import org.sz.mbay.stationLetter.service.StationLetterService;

@Service
public class StationLetterServiceImpl extends BaseServiceImpl implements StationLetterService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StationLetterServiceImpl.class);
	
	@Autowired
	StationLetterDao stationLetterDao;
	
	@Autowired
	UserStationLetterDao userStationLetterDao;
	
	@Override
	@Transactional
	public boolean sendWebSiteEmail(StationLetter message, String usernumber, String currentUserNumber) {
		try {
			message.setSendTime(DateTime.now());
			message = stationLetterDao.createBean(message);
			UserStationLetter hasMsg = new UserStationLetter();
			hasMsg.setMessage(message);
			hasMsg.setReceverUnumber(usernumber);// 收件人
			hasMsg.setSenderUnumber(currentUserNumber);// 发件人
			userStationLetterDao.createBean(hasMsg);
			return true;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOGGER.error("createMsg", e.fillInStackTrace());
		}
		return false;
	}
	
	@Override
	public StationLetter findMessage(String msgid) {
		try {
			return stationLetterDao.getBean(msgid);
		} catch (Exception e) {
			LOGGER.error("findMessage", e.fillInStackTrace());
			return null;
		}
	}
	
}
