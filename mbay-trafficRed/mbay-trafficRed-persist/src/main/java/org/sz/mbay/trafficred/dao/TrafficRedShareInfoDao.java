package org.sz.mbay.trafficred.dao;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.trafficred.bean.TrafficRedShareInfo;

public interface TrafficRedShareInfoDao extends BaseDao<TrafficRedShareInfo> {
	
	int insert(TrafficRedShareInfo shareInfo);
	
	int insertSelective(TrafficRedShareInfo shareInfo);

	TrafficRedShareInfo selectById(long id);

	int updateById(TrafficRedShareInfo shareInfo);

	int updateByIdSelective(TrafficRedShareInfo shareInfo);
	
}
