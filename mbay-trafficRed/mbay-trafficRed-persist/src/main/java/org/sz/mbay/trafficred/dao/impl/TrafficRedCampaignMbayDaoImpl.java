package org.sz.mbay.trafficred.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.base.dao.utils.PKgen;
import org.sz.mbay.redis.common.aop.RedisCache;
import org.sz.mbay.redis.common.aop.RedisUpdate;
import org.sz.mbay.trafficred.bean.TrafficRedCampaignMbay;
import org.sz.mbay.trafficred.dao.TrafficRedCampaignMbayDao;

@Repository
public class TrafficRedCampaignMbayDaoImpl extends
		BaseDaoImpl<TrafficRedCampaignMbay>implements
		TrafficRedCampaignMbayDao {
		
	@Override
	public int create(TrafficRedCampaignMbay record) {
		record.setId(PKgen.getInstance().nextPK());
		return this.template.insert("TrafficRedCampaignMbay.create", record);
	}
	
	@Override
	public int createSelective(TrafficRedCampaignMbay record) {
		return this.template.insert("TrafficRedCampaignMbay.createSelective",
				record);
	}
	
	@Override
	public TrafficRedCampaignMbay findById(Long id) {
		return this.template.selectOne("TrafficRedCampaignMbay.selectById", id);
	}
	
	@RedisUpdate({
			"findValidMin${0.campaignId}",
			"findByCampaignId${0.campaignId}" })
	@Override
	public int updateByIdSelective(TrafficRedCampaignMbay record) {
		return this.template.update(
				"TrafficRedCampaignMbay.updateByIdSelective", record);
	}
	
	@RedisUpdate({
			"findValidMin${0.campaignId}",
			"findByCampaignId${0.campaignId}" })
	@Override
	public int updateById(TrafficRedCampaignMbay record) {
		return this.template
				.update("TrafficRedCampaignMbay.updateById", record);
	}
	
	@RedisCache("0")
	@Override
	public List<TrafficRedCampaignMbay> findByCampaignId(Long cid) {
		return this.template.selectList(
				"TrafficRedCampaignMbay.findByCampaignId", cid);
	}
	
	@RedisUpdate({
			"findValidMin${0}",
			"findByCampaignId${0}",
			"countByCampaignId${0}" })
	@Override
	public int deleteByCampaignId(Long cid) {
		return this.template.delete(
				"TrafficRedCampaignMbay.deleteByCampaignId", cid);
	}
	
	@RedisCache("0")
	@Override
	public TrafficRedCampaignMbay findValidMin(Long id) {
		return template.selectOne("TrafficRedCampaignMbay.findValidMin", id);
	}
	
	@RedisCache("0")
	@Override
	public int countByCampaignId(Long id) {
		return template.selectOne("TrafficRedCampaignMbay.countByCampaignId",
				id);
	}
	
	@RedisUpdate({
			"findValidMin${0}",
			"findByCampaignId${0}",
			"countByCampaignId${0}" })
	@Override
	public int deleteCampaignMbayProduct(long campaignId, long productId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignId", campaignId);
		map.put("mbayId", productId);
		return template.delete(
				"TrafficRedCampaignMbay.deleteCampaignMbayProduct",
				map);
	}
	
	@RedisUpdate({
			"findValidMin${0}",
			"findByCampaignId${0}" })
	@Override
	public int updateMbayProductRatio(long campaignId, long productId,
			int ratio) {
		Map<String, Number> map = new HashMap<String, Number>();
		map.put("campaignId", campaignId);
		map.put("mbayId", productId);
		map.put("ratio", ratio);
		return template.update("TrafficRedCampaignMbay.updateMbayProductRatio",
				map);
	}
	
}
