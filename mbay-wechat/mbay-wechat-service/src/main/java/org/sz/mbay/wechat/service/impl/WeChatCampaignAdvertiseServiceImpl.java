package org.sz.mbay.wechat.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.wechat.bean.WeChatCampaignAdvertise;
import org.sz.mbay.wechat.dao.WeChatCampaignAdvertiseDao;
import org.sz.mbay.wechat.service.WeChatCampaignAdvertiseService;

@Service
public class WeChatCampaignAdvertiseServiceImpl implements WeChatCampaignAdvertiseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatCampaignAdvertiseServiceImpl.class);

    @Autowired
    WeChatCampaignAdvertiseDao dao;

    @Override
    public List<WeChatCampaignAdvertise> findList(String usernumber,PageInfo pageinfo) {
    	return this.dao.findList(usernumber, pageinfo);
    }
    
    @Override
    public boolean del(int id) {
        try {
           return this.dao.deleteBean(id) > 0;
        } catch (Exception e) {
            LOGGER.error("WeChatCampaignAdvertiseService del Error", e.fillInStackTrace());
        }
        return false;
    }

    @Override
    public void add(WeChatCampaignAdvertise bean) {
        try {
            this.dao.createBean(bean);
        } catch (Exception e) {
            LOGGER.error("WeChatCampaignAdvertiseService add Error", e.fillInStackTrace());
        }
    }
    
    @Override
    public void update(WeChatCampaignAdvertise bean) {
        try {
            this.dao.updateBean(bean);
        } catch (Exception e) {
            LOGGER.error("WeChatCampaignAdvertiseService update Error", e.fillInStackTrace());
        }
    }
    
    @Override
    public WeChatCampaignAdvertise findOne(int id) {
        return this.dao.findOne(id);
    }
    
    @Override
	public void change(int id, EnableState enable) {
    	WeChatCampaignAdvertise bean = null;
		try{
            bean = this.dao.findOne(id);
            bean.setStatus(enable);
            this.dao.updateBean(bean);
        }catch(Exception e){
            LOGGER.error("WeChatCampaignAdvertiseService change Error",e.fillInStackTrace());
        }
	}
}