package org.sz.mbay.wechat.dao.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.wechat.bean.WeChatCampaignAdvertise;
import org.sz.mbay.wechat.dao.WeChatCampaignAdvertiseDao;

@Repository
public class WeChatCampaignAdvertiseDaoImpl extends BaseDaoImpl<WeChatCampaignAdvertise> implements WeChatCampaignAdvertiseDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatCampaignAdvertiseDaoImpl.class);

    @Override
    public WeChatCampaignAdvertise findOne(int id) {
    	WeChatCampaignAdvertise bean = null;
        try{
        	bean = this.template.selectOne("findEventAdvertiseById",id);
        }catch(Exception e){
            LOGGER.error("WeChatCampaignAdvertiseDao findOne Error",e.fillInStackTrace());
        }
        return bean;
    }
    
    @Override
	public List<WeChatCampaignAdvertise> findList(String usernumber, PageInfo pageinfo){
    	List<WeChatCampaignAdvertise> list= null;
        try {
            list = super.findList(usernumber, pageinfo, "EventAdvertise");
        } catch (Exception e) {
            LOGGER.error("WeChatCampaignAdvertiseDao findList Error", e.fillInStackTrace());
        }
        return list;
	}
}