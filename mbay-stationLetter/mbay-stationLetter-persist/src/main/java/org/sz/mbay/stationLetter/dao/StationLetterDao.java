package org.sz.mbay.stationLetter.dao;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.stationLetter.bean.StationLetter;

/**
 * 站内信Dao
 * 
 * @author frank.zong
 * 
 */
public interface StationLetterDao extends BaseDao<StationLetter> {
	
	public int updateReceiveStatus(int id);
	
	public int updateSendStatus(int id);
	
}
