package org.sz.mbay.stationLetter.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.stationLetter.bean.UserStationLetter;
import org.sz.mbay.stationLetter.dao.StationLetterDao;
import org.sz.mbay.stationLetter.dao.UserStationLetterDao;
import org.sz.mbay.stationLetter.service.UserStationLetterService;

@Service
public class UserStationLetterServiceImpl extends BaseServiceImpl implements UserStationLetterService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserStationLetterServiceImpl.class);
	
	@Autowired
	UserStationLetterDao userStationLetterDao;
	
	@Autowired
	StationLetterDao stationLetterDao;
	
	@Override
	public int getmsgCount(String usernumber) {
		return this.userStationLetterDao.getmsgCount(usernumber);
	}
	
	@Override
	public List<UserStationLetter> findAllUserStationLetter(String unumber, PageInfo pageinfo){
		return this.userStationLetterDao.findList(unumber, pageinfo, "UserStationLetter");
	}
	
	@Override
	@Transactional
	public ExecuteResult deleteMsg(int id) {
		int ret = this.stationLetterDao.updateReceiveStatus(id);
		// 直接从数据库删除还是给个标示
		if (ret != 1) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new ExecuteResult(false, "删除失败!");
		} else {
			return new ExecuteResult(true, "删除成功!");
		}
	}
	
	@Override
	@Transactional
	public ExecuteResult deleteSendMsg(int id) {
		int ret = this.stationLetterDao.updateSendStatus(id);
		// 直接从数据库删除还是给个标示
		if (ret != 1) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new ExecuteResult(false, "删除失败!");
		} else {
			return new ExecuteResult(true, "删除成功!");
		}
	}
	
	@Override
	public int updatereadStatus(String id){
		try {
			return this.userStationLetterDao.updatereadStatus(id);
		} catch (Exception e) {
			LOGGER.error("updateUserhasMsg", e.fillInStackTrace());
		}
		return 0;
	}
	
	@Override
	public int countUnMsg(String usernumber) {
		return this.userStationLetterDao.countUnMsg(usernumber);
	}
	
	@Override
	public List<UserStationLetter> findAllUserHasOutMsg(String usernumber, PageInfo pageinfo) {
		return this.userStationLetterDao.findList(usernumber, pageinfo, "UserHasOutMsg");
	}
	
	@Override
	public int countOutMsg(String usernumber) {
		return this.userStationLetterDao.countOutMsg(usernumber);
	}
	
	@Override
	public UserStationLetter findHasMsgById(Integer id) {
		return this.userStationLetterDao.findHasMsgById(id);
	}
	
	@Override
	public int getHasMsgReadStatusById(Integer id) {
		return userStationLetterDao.getHasMsgReadStatusById(id);
	}
	
}
