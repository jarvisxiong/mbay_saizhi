package org.sz.mbay.trafficred.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.base.dao.utils.PKgen;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.redis.common.aop.RedisCache;
import org.sz.mbay.redis.common.aop.RedisUpdate;
import org.sz.mbay.trafficred.bean.ClassifyPackageNums;
import org.sz.mbay.trafficred.bean.TrafficRedCampaignPackage;
import org.sz.mbay.trafficred.dao.TrafficRedCampaignPackageDao;

@Repository
public class TrafficRedCampaignPackageDaoImpl extends
		BaseDaoImpl<TrafficRedCampaignPackage>implements
		TrafficRedCampaignPackageDao {
		
	@Override
	public int create(TrafficRedCampaignPackage record) {
		record.setId(PKgen.getInstance().nextPK());
		return this.template.insert("TrafficRedCampaignPackage.create", record);
	}
	
	@Override
	public int createSelective(TrafficRedCampaignPackage record) {
		record.setId(PKgen.getInstance().nextPK());
		return this.template.insert(
				"TrafficRedCampaignPackage.createSelective", record);
	}
	
	@Override
	public TrafficRedCampaignPackage selectById(Long id) {
		return this.template.selectOne("TrafficRedCampaignPackage.selectById",
				id);
	}
	
	@RedisUpdate({
			"findValidMin${0.campaignId}",
			"findByCampaignIdAndOperatorType${0.campaignId}" })
	@Override
	public int updateByIdSelective(TrafficRedCampaignPackage record) {
		return this.template.update(
				"TrafficRedCampaignPackage.updateByIdSelective", record);
	}
	
	@RedisUpdate({
			"findValidMin${0.campaignId}",
			"findByCampaignIdAndOperatorType${0.campaignId}" })
	@Override
	public int updateById(TrafficRedCampaignPackage record) {
		return this.template.update("TrafficRedCampaignPackage.updateById",
				record);
	}
	
	@Override
	public List<TrafficRedCampaignPackage> findByCampaignId(Long cid) {
		return this.template.selectList(
				"TrafficRedCampaignPackage.findByCampaignId", cid);
	}
	
	@RedisCache({ "0", "1" })
	@Override
	public TrafficRedCampaignPackage findValidMin(Long cid, OperatorType type) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("cid", cid);
		param.put("operatorType", type);
		return template.selectOne(
				"TrafficRedCampaignPackage.findValidMin", param);
	}
	
	@RedisUpdate({
			"findValidMin${0}",
			"findByCampaignIdAndOperatorType${0}",
			"findClassifyPackages${0}" })
	@Override
	public int deleteByCampaignId(Long cid) {
		return this.template.delete(
				"TrafficRedCampaignPackage.deleteByCampaignId", cid);
	}
	
	@RedisCache({ "0", "1" })
	@Override
	public List<TrafficRedCampaignPackage> findByCampaignIdAndOperatorType(
			Long cid, OperatorType type) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("cid", cid);
		param.put("operatorType", type);
		return this.template.selectList(
				"TrafficRedCampaignPackage.findByCampaignIdAndOperatorType",
				param);
	}
	
	@RedisCache("0")
	@Override
	public ClassifyPackageNums findClassifyPackages(Long id) {
		return template.selectOne(
				"TrafficRedCampaignPackage.findClassifyPackages", id);
	}
	
	@RedisUpdate({
			"findValidMin${0}",
			"findByCampaignIdAndOperatorType${0}",
			"findClassifyPackages${0}" })
	@Override
	public int deleteCampaignTrafficProduct(long campaignId, long productId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignId", campaignId);
		map.put("packageId", productId);
		return template.delete(
				"TrafficRedCampaignPackage.deleteCampaignTrafficProduct",
				map);
	}
	
	@RedisUpdate({
			"findValidMin${0}",
			"findByCampaignIdAndOperatorType${0}" })
	@Override
	public int updateTrafficProductRatio(long campaignId, long productId,
			int ratio) {
		Map<String, Number> map = new HashMap<String, Number>();
		map.put("campaignId", campaignId);
		map.put("packageId", productId);
		map.put("ratio", ratio);
		return template.update(
				"TrafficRedCampaignPackage.updateTrafficProductRatio",
				map);
	}
}
