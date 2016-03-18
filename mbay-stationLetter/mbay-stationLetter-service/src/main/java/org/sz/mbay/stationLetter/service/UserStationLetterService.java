package org.sz.mbay.stationLetter.service;

import java.util.List;

import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.stationLetter.bean.UserStationLetter;

/**
 * 用户与站内信关系Service
 * @author frank.zong
 *
 */
public interface UserStationLetterService {
	
	public int getmsgCount(String usernumber);
	
	public List<UserStationLetter> findAllUserStationLetter(String unumber, PageInfo pageinfo);
	
	public ExecuteResult deleteMsg(int id);
	
	public ExecuteResult deleteSendMsg(int id);
	
	public int updatereadStatus(String id);
	
	public int countUnMsg(String usernumber);
	
	public List<UserStationLetter> findAllUserHasOutMsg(String usernumber, PageInfo pageinfo);
	
	public int countOutMsg(String usernumber);
	
	public UserStationLetter findHasMsgById(Integer id);
	
	public int getHasMsgReadStatusById(Integer id);
}
