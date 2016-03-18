package org.sz.mbay.wallet.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.wallet.bean.ChannelCampaign;
import org.sz.mbay.wallet.dao.ChannelCampaignDao;

@Repository
public class ChannelCampaignDaoImpl extends BaseDaoImpl<ChannelCampaign>
		implements ChannelCampaignDao {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ChannelCampaignDaoImpl.class);
	
	@Override
	public List<ChannelCampaign> findAllChannelCampaign(PageInfo pageinfo) {
		List<ChannelCampaign> list = null;
		try {
			list = super.findList("ChannelCampaign", pageinfo);
		} catch (Exception e) {
			LOGGER.error("ChannelCampaignDaoImpl findAllChannelCampaign Error",
					e.fillInStackTrace());
		}
		return list;
	}
	
	@Override
	public ChannelCampaign findChannelCampaignById(int id) {
		ChannelCampaign bean = null;
		try {
			bean = this.template.selectOne("findChannelCampaignById", id);
		} catch (Exception e) {
			LOGGER.error(
					"ChannelCampaignDaoImpl findChannelCampaignById Error",
					e.fillInStackTrace());
		}
		return bean;
	}
	
}
