package org.sz.mbay.trafficred.dao;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.trafficred.bean.TrafficRedRaffle;

public interface TrafficRedRaffleDao extends BaseDao<TrafficRedRaffle> {
	
	int insert(TrafficRedRaffle bean);
	
	int updateByIdSelective(TrafficRedRaffle bean);
	
	TrafficRedRaffle selectById(Long id);

	TrafficRedRaffle selectByMobile(String mobile);
}
