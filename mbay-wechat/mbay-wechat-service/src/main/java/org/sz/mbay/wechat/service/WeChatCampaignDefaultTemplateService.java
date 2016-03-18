package org.sz.mbay.wechat.service;

import java.util.List;

import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.wechat.bean.WeChatCampaignDefaultTemplate;

public interface WeChatCampaignDefaultTemplateService {

    /**
     * 删除
     * @param id
     * @return
     */
    public boolean del(int id);

    /**
     * 新增
     * @param bean
     */
    public void add(WeChatCampaignDefaultTemplate bean);

    /**
     * 更新
     * @param bean
     */
    public void update(WeChatCampaignDefaultTemplate bean);
    
    /**
     * 查询-单条
     * @param id
     * @return
     */
    public WeChatCampaignDefaultTemplate findOne(int id);
    
    /**
     * 查询-分页
     * @param usernumber
     * @param pageinfo
     * @return
     */
    public List<WeChatCampaignDefaultTemplate> findList(String usernumber,PageInfo pageinfo);
}