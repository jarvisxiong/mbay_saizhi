package org.sz.mbay.wechat.service;

import java.util.List;

import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.wechat.bean.WeChatCampaignAdvertise;

public interface WeChatCampaignAdvertiseService {

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
    public void add(WeChatCampaignAdvertise bean);

    /**
     * 更新
     * @param bean
     */
    public void update(WeChatCampaignAdvertise bean);
    
    /**
     * 查询-单条
     * @param id
     * @return
     */
    public WeChatCampaignAdvertise findOne(int id);
    
    /**
     * 查询-分页
     * @param usernumber
     * @param pageinfo
     * @return
     */
    public List<WeChatCampaignAdvertise> findList(String usernumber,PageInfo pageinfo);
    
    /**
     * 修改状态 id->id,enable->状态
     * @param id
     * @param enable
     */
    public void change(int id,EnableState enable);
}