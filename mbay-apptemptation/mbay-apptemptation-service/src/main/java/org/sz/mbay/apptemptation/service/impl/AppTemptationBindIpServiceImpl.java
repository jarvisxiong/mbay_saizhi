package org.sz.mbay.apptemptation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.apptemptation.bean.AppTemptationBindIp;
import org.sz.mbay.apptemptation.dao.AppTemptationBindIpDao;
import org.sz.mbay.apptemptation.service.AppTemptationBindIpService;
import org.sz.mbay.base.service.impl.BaseServiceImpl;

@Service
public class AppTemptationBindIpServiceImpl extends BaseServiceImpl implements AppTemptationBindIpService {
	
	@Autowired
	private AppTemptationBindIpDao appTemptationBindIpDao;
	
	@Override
	public List<AppTemptationBindIp> findAppTemptationBindIps(String campaignNumber) {
		return appTemptationBindIpDao.findAppTemptationBindIpsByCampaignNumber(campaignNumber);
	}
	
	@Override
	public boolean deleteAppTemptationBindIpById(Integer id) {
		return appTemptationBindIpDao.deleteAppTemptationBindIpById(id);
	}
	
	@Override
	public Integer addAppTemptationBindIp(String number, String ip) {
		return appTemptationBindIpDao.addAppTemptationBindIp(number, ip);
	}
	
}
