package org.sz.mbay.trafficred.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.trafficred.bean.TrafficRedMbay;

public interface TrafficRedMbayDao extends BaseDao<TrafficRedMbay> {
	
	List<TrafficRedMbay> findAll();
	
	void deleteById(Long mid);
	
	int createSelective(TrafficRedMbay bean);
	
	int updateByIdSelective(TrafficRedMbay bean);
}
