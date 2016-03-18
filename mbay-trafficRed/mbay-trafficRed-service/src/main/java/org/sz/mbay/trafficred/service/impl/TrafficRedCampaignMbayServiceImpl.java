package org.sz.mbay.trafficred.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.dao.utils.PKgen;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.trafficred.bean.TrafficRedCampaignMbay;
import org.sz.mbay.trafficred.dao.TrafficRedCampaignDao;
import org.sz.mbay.trafficred.dao.TrafficRedCampaignMbayDao;
import org.sz.mbay.trafficred.service.TrafficRedCampaignMbayService;

@Service
public class TrafficRedCampaignMbayServiceImpl extends BaseServiceImpl
		implements TrafficRedCampaignMbayService {
		
	@Autowired
	TrafficRedCampaignMbayDao trafficRedCampaignMbayDao;
	@Autowired
	TrafficRedCampaignDao trafficRedCampaignDao;
	
	@Override
	public TrafficRedCampaignMbay find(Long id) {
		return this.trafficRedCampaignMbayDao.findById(id);
	}
	
	@Override
	public TrafficRedCampaignMbay create(TrafficRedCampaignMbay mbay) {
		mbay.setId(PKgen.getInstance().nextPK());
		return this.trafficRedCampaignMbayDao.createSelective(mbay) > 0 ? mbay
				: null;
	}
	
	@Override
	public List<TrafficRedCampaignMbay> findByCampaignId(Long cid) {
		return this.trafficRedCampaignMbayDao.findByCampaignId(cid);
	}
	
	@Override
	public ExecuteResult chooseMbay(TrafficRedCampaignMbay[] trcps) {
		Long cid = trcps[0].getCampaignId();
		Boolean result = this.trafficRedCampaignMbayDao
				.deleteByCampaignId(cid) >= 0 ? true : false;
		if (!result) {
			return new ExecuteResult(false, "CHOOSE ERROR", "美贝流量选择错误");
		}
		for (TrafficRedCampaignMbay trafficRedCampaignMbay : trcps) {
			this.trafficRedCampaignMbayDao.create(trafficRedCampaignMbay);
		}
		return new ExecuteResult(true, "CHOOSE SUCCESS", "操作成功");
	}
	
	@Override
	public TrafficRedCampaignMbay findValidMin(Long id) {
		return trafficRedCampaignMbayDao.findValidMin(id);
	}
	
	@Override
	public int countByCampaignId(Long id) {
		return trafficRedCampaignMbayDao.countByCampaignId(id);
	}
}
