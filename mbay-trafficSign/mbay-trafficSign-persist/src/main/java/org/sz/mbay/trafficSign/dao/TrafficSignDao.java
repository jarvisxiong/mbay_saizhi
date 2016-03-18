package org.sz.mbay.trafficSign.dao;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.trafficSign.bean.TrafficSign;

public interface TrafficSignDao extends BaseDao<TrafficSign> {
	
	//根据用户编号查询记录
	public TrafficSign findTrafficSignByUserNumber(String usernumber);
	
	// 根据id查询记录
	public TrafficSign findTrafficSignById(int id);
}
