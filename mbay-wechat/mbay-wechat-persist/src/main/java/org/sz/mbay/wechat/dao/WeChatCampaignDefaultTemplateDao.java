package org.sz.mbay.wechat.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.wechat.bean.WeChatCampaignDefaultTemplate;

public interface WeChatCampaignDefaultTemplateDao extends BaseDao<WeChatCampaignDefaultTemplate> {
	
	// 查询-单条
	public WeChatCampaignDefaultTemplate findOne(int id);
	
	// 查询-分页
	public List<WeChatCampaignDefaultTemplate> findList(String usernumber, PageInfo pageinfo);
}
