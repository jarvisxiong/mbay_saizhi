package org.sz.mbay.stationLetter.dao.impl;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.stationLetter.bean.StationLetter;
import org.sz.mbay.stationLetter.dao.StationLetterDao;

@Repository
public class StationLetterDaoImpl extends BaseDaoImpl<StationLetter> implements StationLetterDao {
	
	@Override
	public int updateReceiveStatus(int id) {
		return super.template.update("updateReceiveStatus", id);
	}
	
	@Override
	public int updateSendStatus(int id) {
		return super.template.update("updateSendStatus", id);
	}
	
}
