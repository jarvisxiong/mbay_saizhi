package org.sz.mbay.wallet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.wallet.bean.ChannelCampaign;
import org.sz.mbay.wallet.dao.ChannelCampaignDao;
import org.sz.mbay.wallet.service.ChannelCampaignService;

@Service
public class ChannelCampaignServiceImpl extends BaseServiceImpl implements ChannelCampaignService {
	
	@Autowired
	ChannelCampaignDao dao;

	@Override
	public List<ChannelCampaign> findAllChannelCampaign(PageInfo pageinfo) {
		return dao.findAllChannelCampaign(pageinfo);
	}

	@Override
	public ChannelCampaign findChannelCampaignById(int id) {
		return dao.findChannelCampaignById(id);
	}
	
}