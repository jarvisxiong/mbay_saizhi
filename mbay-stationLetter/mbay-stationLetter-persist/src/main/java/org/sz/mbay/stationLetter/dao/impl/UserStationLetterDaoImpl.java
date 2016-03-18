package org.sz.mbay.stationLetter.dao.impl;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.stationLetter.bean.UserStationLetter;
import org.sz.mbay.stationLetter.dao.UserStationLetterDao;

@Repository
public class UserStationLetterDaoImpl extends BaseDaoImpl<UserStationLetter> implements UserStationLetterDao {
	
	@Override
	public int getmsgCount(String usernumber) {
		return this.template.selectOne("getmsgCount", usernumber);
	}
	
	@Override
	public int updatereadStatus(String id) {
		return this.template.update("updatereadStatus", id);
	}
	
	@Override
	public int countUnMsg(String usernumber) {
		return this.template.selectOne("countUnMsg", usernumber);
	}
	
	@Override
	public int countOutMsg(String usernumber) {
		return this.template.selectOne("countOutMsg", usernumber);
	}
	
	@Override
	public UserStationLetter findHasMsgById(Integer id) {
		return this.template.selectOne("findHasMsgById", id);
	}
	
	@Override
	public int getHasMsgReadStatusById(Integer id) {
		return template.selectOne("getHasMsgReadStatusById", id);
	}
	
}
