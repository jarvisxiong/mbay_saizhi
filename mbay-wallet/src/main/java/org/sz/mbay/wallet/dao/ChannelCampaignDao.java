package org.sz.mbay.wallet.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.wallet.bean.ChannelCampaign;

public interface ChannelCampaignDao extends BaseDao<ChannelCampaign> {
	
	public List<ChannelCampaign> findAllChannelCampaign(PageInfo pageinfo);
	
	public ChannelCampaign findChannelCampaignById(int id);
	
}
