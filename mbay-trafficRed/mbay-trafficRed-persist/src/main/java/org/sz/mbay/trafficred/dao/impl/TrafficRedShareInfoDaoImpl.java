package org.sz.mbay.trafficred.dao.impl;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.base.dao.utils.PKgen;
import org.sz.mbay.redis.common.aop.RedisCache;
import org.sz.mbay.redis.common.aop.RedisUpdate;
import org.sz.mbay.trafficred.bean.TrafficRedShareInfo;
import org.sz.mbay.trafficred.dao.TrafficRedShareInfoDao;

@Repository
public class TrafficRedShareInfoDaoImpl extends
		BaseDaoImpl<TrafficRedShareInfo>implements TrafficRedShareInfoDao {
		
	@Override
	public int insert(TrafficRedShareInfo shareInfo) {
		shareInfo.setId(PKgen.getInstance().nextPK());
		return template.insert("TrafficRedShareInfo.insert", shareInfo);
	}
	
	@Override
	public int insertSelective(TrafficRedShareInfo shareInfo) {
		shareInfo.setId(PKgen.getInstance().nextPK());
		return template
				.insert("TrafficRedShareInfo.insertSelective", shareInfo);
	}
	
	@RedisCache("0")
	@Override
	public TrafficRedShareInfo selectById(long id) {
		return template.selectOne("TrafficRedShareInfo.selectById", id);
	}
	
	@RedisUpdate("selectById${0.id}")
	@Override
	public int updateById(TrafficRedShareInfo shareInfo) {
		return template.update("TrafficRedShareInfo.updateById", shareInfo);
	}
	
	@RedisUpdate("selectById${0.id}")
	@Override
	public int updateByIdSelective(TrafficRedShareInfo shareInfo) {
		return template.update("TrafficRedShareInfo.updateByIdSelective",
				shareInfo);
	}
	
}
