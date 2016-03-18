package org.sz.mbay.trafficred.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sz.mbay.base.dao.utils.PKgen;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.trafficred.bean.ClassifyPackageNums;
import org.sz.mbay.trafficred.bean.TrafficRedCampaignPackage;
import org.sz.mbay.trafficred.dao.TrafficRedCampaignDao;
import org.sz.mbay.trafficred.dao.TrafficRedCampaignPackageDao;
import org.sz.mbay.trafficred.service.TrafficRedCampaignPackageService;

@Service
public class TrafficRedCampaignPackageServiceImpl extends BaseServiceImpl
		implements TrafficRedCampaignPackageService {
		
	@Autowired
	TrafficRedCampaignPackageDao campaignPackageDao;
	@Autowired
	TrafficRedCampaignDao trafficRedCampaignDao;
	
	@Override
	public TrafficRedCampaignPackage create(
			TrafficRedCampaignPackage trafficPackage) {
		return this.campaignPackageDao.createSelective(trafficPackage) > 0
				? trafficPackage
				: null;
	}
	
	@Override
	public List<TrafficRedCampaignPackage> findByCampaignId(Long cid) {
		return this.campaignPackageDao.findByCampaignId(cid);
	}
	
	@Override
	public TrafficRedCampaignPackage findValidMin(Long cid, OperatorType type) {
		return campaignPackageDao.findValidMin(cid, type);
	}
	
	@Override
	public Boolean deleteByCampaignId(Long cid) {
		return this.campaignPackageDao.deleteByCampaignId(cid) > 0 ? true
				: false;
	}
	
	@Override
	@Transactional
	public ExecuteResult chooseTraffic(TrafficRedCampaignPackage[] trcps) {
		Long cid = trcps[0].getCampaignId();
		Boolean resule = this.campaignPackageDao.deleteByCampaignId(cid) >= 0
				? true
				: false;
				
		if (!resule) {
			return new ExecuteResult(false, "CHOOSE ERROR", "流量包选择错误!");
		}
		for (TrafficRedCampaignPackage trafficRedCampaignPackage : trcps) {
			trafficRedCampaignPackage.setId(PKgen.getInstance().nextPK());
			this.campaignPackageDao.create(trafficRedCampaignPackage);
		}
		
		return new ExecuteResult(true, "CHOOSE SUCCESS", "操作成功");
	}
	
	@Override
	public boolean updateById(TrafficRedCampaignPackage pack) {
		return campaignPackageDao.updateByIdSelective(pack) > 0;
	}
	
	@Override
	public List<TrafficRedCampaignPackage> findByCampaignIdAndOperatorType(
			Long cid, OperatorType type) {
		return campaignPackageDao.findByCampaignIdAndOperatorType(cid,
				type);
	}
	
	@Override
	public ClassifyPackageNums findClassifyPackages(Long id) {
		return campaignPackageDao.findClassifyPackages(id);
	}
}
