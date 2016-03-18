package org.sz.mbay.stationLetter.dao;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.stationLetter.bean.UserStationLetter;

/**
 * 用户与站内信关联Dao
 * 
 * @author frank.zong
 * 
 */
public interface UserStationLetterDao extends BaseDao<UserStationLetter> {
	
	// 查询站内信未读数量
	public int getmsgCount(String usernumber);
	
	public int updatereadStatus(String id);
	
	public int countUnMsg(String usernumber);
	
	public int countOutMsg(String usernumber);
	
	public UserStationLetter findHasMsgById(Integer id);
	
	/**
	 * 获取站内信读取状态
	 * 
	 * @param id
	 * @return
	 */
	public int getHasMsgReadStatusById(Integer id);
}
