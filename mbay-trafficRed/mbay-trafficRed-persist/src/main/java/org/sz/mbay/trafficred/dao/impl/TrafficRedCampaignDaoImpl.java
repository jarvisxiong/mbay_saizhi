package org.sz.mbay.trafficred.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.base.dao.utils.PKgen;
import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.redis.common.aop.RedisCache;
import org.sz.mbay.redis.common.aop.RedisUpdate;
import org.sz.mbay.trafficred.bean.CampaignStatistics;
import org.sz.mbay.trafficred.bean.TrafficRedCampaign;
import org.sz.mbay.trafficred.dao.TrafficRedCampaignDao;

@Repository
public class TrafficRedCampaignDaoImpl extends BaseDaoImpl<TrafficRedCampaign>
		implements TrafficRedCampaignDao {
		
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public int createSelective(TrafficRedCampaign campaign) {
		campaign.setId(PKgen.getInstance().nextPK());
		return this.template.insert("TrafficRedCampaign.createSelective",
				campaign);
	}
	
	@Override
	public TrafficRedCampaign selectById(Long id) {
		return this.template.selectOne("TrafficRedCampaign.selectById", id);
	}
	
	@RedisUpdate({ "findCampaignByNumber${0.number}",
			"findProductHitRate${0.id}" })
	@Override
	public int updateByIdSelective(TrafficRedCampaign campaign) {
		return this.template.update("TrafficRedCampaign.updateByIdSelective",
				campaign);
	}
	
	@RedisUpdate({ "findCampaignByNumber${0.number}",
			"findProductHitRate${0.id}" })
	@Override
	public int updateById(TrafficRedCampaign campaign) {
		return template.update("TrafficRedCampaign.updateById", campaign);
	}
	
	@RedisCache("0")
	@Override
	public TrafficRedCampaign findCampaignByNumber(String campaignNumber) {
		return template.selectOne("TrafficRedCampaign.findCampaignByNumber",
				campaignNumber);
	}
	
	@Override
	public boolean isExistCampaign(long campaignId) {
		Integer num = this.template
				.selectOne("TrafficRedCampaign.isExistCampaign", campaignId);
		return 1 == num;
	}
	
	@Override
	public CampaignStatistics statisticCampaign(String userNumber) {
		return this.template.selectOne(
				"TrafficRedCampaign.statisticTrafficRedCampaign", userNumber);
	}
	
	@RedisCache(value = "0", updatable = false)
	@Override
	public String findCampaignNumberById(long campaignId) {
		return this.template.selectOne(
				"TrafficRedCampaign.findCampaignNumberById", campaignId);
	}
	
	@RedisCache(value = "0", updatable = false)
	@Override
	public String findCampaignBelongUser(long campaignId) {
		return this.template.selectOne(
				"TrafficRedCampaign.findCampaignBelongUser", campaignId);
	}
	
	@RedisCache("0")
	@Override
	public double findProductHitRate(long campaignId) {
		return this.template.selectOne("TrafficRedCampaign.findProductHitRate",
				campaignId);
	}
	
	@Override
	public List<String> findAllCampaignNumberStartToday() {
		Map<String, Object> params = new HashMap<>();
		params.put("now", DateTime.now());
		params.put("status", CampaignStatus.NOT_STARTED);
		return template.selectList(
				"TrafficRedCampaign.findAllCampaignNumberStartToday", params);
	}
	
	@Override
	public List<String> findAllCampaignNumberOverToday() {
		Map<String, Object> params = new HashMap<>();
		params.put("now", DateTime.now());
		params.put("status", CampaignStatus.IN_ACTIVE);
		return template.selectList(
				"TrafficRedCampaign.findAllCampaignNumberOverToday", params);
	}
	
	@Override
	public Long getIdByNumber(String number) {
		return template.selectOne("TrafficRedCampaign.getIdByNumber", number);
	}
	
}
