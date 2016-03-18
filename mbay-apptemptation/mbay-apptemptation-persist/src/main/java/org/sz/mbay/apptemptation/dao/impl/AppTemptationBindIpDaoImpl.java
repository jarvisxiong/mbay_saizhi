package org.sz.mbay.apptemptation.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.sz.mbay.apptemptation.bean.AppTemptationBindIp;
import org.sz.mbay.apptemptation.dao.AppTemptationBindIpDao;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;

@Repository
public class AppTemptationBindIpDaoImpl extends BaseDaoImpl<AppTemptationBindIp> implements AppTemptationBindIpDao {

	@Override
	public List<AppTemptationBindIp> findAppTemptationBindIpsByCampaignNumber(String campaignNumber) {
		return template.selectList("findAppTemptationBindIpsByCampaignNumber", campaignNumber);
	}

	@Override
	public boolean deleteAppTemptationBindIpById(Integer id) {
		return template.delete("deleteAppTemptationBindIpById", id) == 1;
	}

	@Override
	public Integer addAppTemptationBindIp(String number, String ip) {
		AppTemptationBindIp bean = new AppTemptationBindIp();
		bean.setCampaignNumber(number);
		bean.setIp(ip);
		template.insert("createAppTemptationBindIp", bean);
		return bean.getId().intValue();
	}
	
}
