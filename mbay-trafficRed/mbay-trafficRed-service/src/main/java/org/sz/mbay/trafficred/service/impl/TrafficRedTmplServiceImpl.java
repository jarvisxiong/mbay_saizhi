package org.sz.mbay.trafficred.service.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.trafficred.bean.TrafficRedTemplate;
import org.sz.mbay.trafficred.dao.TrafficRedTemplateDao;
import org.sz.mbay.trafficred.qo.TrafficRedCampaignQO;
import org.sz.mbay.trafficred.service.TrafficRedTmplService;

@Service
public class TrafficRedTmplServiceImpl extends BaseServiceImpl implements
		TrafficRedTmplService {
		
	@Autowired
	TrafficRedTemplateDao redTemplateDao;
	
	@Override
	public TrafficRedTemplate create(TrafficRedTemplate template) {
		if (template.getAdLink1() == null) {
			template.setAdLink1("");
		}
		if (template.getAdLink2() == null) {
			template.setAdLink2("");
		}
		if (template.getSharkResultLink() == null) {
			template.setSharkResultLink("");
		}
		if (template.getLogoCycleLink() == null) {
			template.setLogoCycleLink("");
		}
		
		int r = this.redTemplateDao.create(template);
		return r > 0 ? template : null;
	}
	
	@Override
	public TrafficRedTemplate find(long id) {
		return this.redTemplateDao.selectById(id);
	}
	
	@Override
	public TrafficRedTemplate updateByIdSelective(TrafficRedTemplate template) {
		return this.redTemplateDao.updateByIdSelective(template) > 0 ? template
				: null;
	}
	
	@Override
	public List<TrafficRedTemplate> findPageOfShakeCustomized(
			TrafficRedCampaignQO qo, PageInfo pageInfo) {
		return redTemplateDao.findList(qo, pageInfo, "ShakeCustomized");
	}
	
	@Override
	public boolean clearShakeCustomize(Long id) {
		return redTemplateDao.clearShakeCustomize(id) == 1;
	}
	
	@Override
	public boolean createShakeCustomize(TrafficRedTemplate bean) {
		bean.setShakeImgsAddTime(DateTime.now());
		return redTemplateDao.createShakeCustomize(bean) == 1;
	}

	@Override
	public boolean isExistShakeCustomize(Long cid) {
		return redTemplateDao.isExistShakeCustomize(cid);
	}
}
