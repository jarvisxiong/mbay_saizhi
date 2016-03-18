package org.sz.mbay.wallet.service;

import java.util.List;

import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.wallet.bean.ChannelCampaign;

public interface ChannelCampaignService {
	
	public List<ChannelCampaign> findAllChannelCampaign(PageInfo pageinfo);
	
	public ChannelCampaign findChannelCampaignById(int id);

}
