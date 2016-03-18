package org.sz.mbay.wechat.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.wechat.bean.WeChatCampaignAdvertise;

public interface WeChatCampaignAdvertiseDao extends BaseDao<WeChatCampaignAdvertise> {
	
    //查询-单条
    public WeChatCampaignAdvertise findOne(int id);
    
    //查询-分页
    public List<WeChatCampaignAdvertise> findList(String usernumber,PageInfo pageinfo);
}
