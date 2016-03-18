package org.sz.mbay.trafficred.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.trafficred.bean.TrafficRedMbay;
import org.sz.mbay.trafficred.dao.TrafficRedMbayDao;

@Repository
public class TrafficRedMbayDaoImpl extends BaseDaoImpl<TrafficRedMbay>
		implements TrafficRedMbayDao {
	
	@Override
	public List<TrafficRedMbay> findAll() {
		return template.selectList("TrafficRedMbay.findAll");
	}
	
	@Override
	public void deleteById(Long mid) {
		template.delete("TrafficRedMbay.deleteById", mid);
	}
	
	@Override
	public int createSelective(TrafficRedMbay bean) {
		return template.insert("TrafficRedMbay.createSelective", bean);
	}
	
	@Override
	public int updateByIdSelective(TrafficRedMbay bean) {
		return template.update("TrafficRedMbay.updateByIdSelective", bean);
	}
	
}
