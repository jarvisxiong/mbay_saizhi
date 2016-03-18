package org.sz.mbay.trafficred.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.base.dao.utils.PKgen;
import org.sz.mbay.redis.common.aop.RedisCache;
import org.sz.mbay.redis.common.aop.RedisUpdate;
import org.sz.mbay.trafficred.bean.TimeQuantum;
import org.sz.mbay.trafficred.dao.TimeQuantumDao;

/**
 * 时间段
 * 
 * @author Fenlon
 * 		
 */
@Repository
public class TimeQuantumDaoImpl extends BaseDaoImpl<TimeQuantum>
		implements TimeQuantumDao {
		
	@Override
	public int create(TimeQuantum timeQuantum) {
		timeQuantum.setId(PKgen.getInstance().nextPK());
		return this.template.insert("TimeQuantum.create", timeQuantum);
	}
	
	@Override
	public int createSelective(TimeQuantum timeQuantum) {
		timeQuantum.setId(PKgen.getInstance().nextPK());
		return this.template.insert("TimeQuantum.createSelective", timeQuantum);
	}
	
	@RedisCache("0")
	@Override
	public List<TimeQuantum> findByCampaignId(Long campaignId) {
		return this.template.selectList("TimeQuantum.findByCampaignId",
				campaignId);
	}
	
	@RedisUpdate("findByCampaignId${0}")
	@Override
	public void deleteAllByCampaignId(long campaignId) {
		this.template.delete("deleteAllByCampaignId", campaignId);
	}
	
}
