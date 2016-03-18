package org.sz.mbay.trafficred.dao.impl;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.base.dao.utils.PKgen;
import org.sz.mbay.trafficred.bean.TrafficRedRaffle;
import org.sz.mbay.trafficred.dao.TrafficRedRaffleDao;

@Repository
public class TrafficRedRaffleDaoImpl extends BaseDaoImpl<TrafficRedRaffle>
		implements TrafficRedRaffleDao {
		
	@Override
	public int insert(TrafficRedRaffle bean) {
		bean.setId(PKgen.getInstance().nextPK());
		return template.insert("TrafficRedRaffle.insert", bean);
	}
	
	@Override
	public int updateByIdSelective(TrafficRedRaffle bean) {
		return template.update("TrafficRedRaffle.updateByIdSelective", bean);
	}
	
	@Override
	public TrafficRedRaffle selectById(Long id) {
		return template.selectOne("TrafficRedRaffle.selectById", id);
	}
	
	@Override
	public TrafficRedRaffle selectByMobile(String mobile) {
		return template.selectOne("TrafficRedRaffle.selectByMobile", mobile);
	}
	
}
