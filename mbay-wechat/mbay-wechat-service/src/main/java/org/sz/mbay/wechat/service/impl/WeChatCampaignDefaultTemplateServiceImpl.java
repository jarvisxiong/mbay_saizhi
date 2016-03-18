package org.sz.mbay.wechat.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.wechat.bean.WeChatCampaignDefaultTemplate;
import org.sz.mbay.wechat.dao.WeChatCampaignDefaultTemplateDao;
import org.sz.mbay.wechat.service.WeChatCampaignDefaultTemplateService;

@Service
public class WeChatCampaignDefaultTemplateServiceImpl extends BaseServiceImpl implements WeChatCampaignDefaultTemplateService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WeChatCampaignDefaultTemplateServiceImpl.class);
	
	@Autowired
	WeChatCampaignDefaultTemplateDao dao;
	
	@Override
	public List<WeChatCampaignDefaultTemplate> findList(String usernumber, PageInfo pageinfo) {
		return this.dao.findList(usernumber, pageinfo);
	}
	
	@Override
	public boolean del(int id) {
		try {
			return this.dao.deleteBean(id) > 0;
		} catch (Exception e) {
			LOGGER.error("WeChatCampaignDefaultTemplateService del Error", e.fillInStackTrace());
		}
		return false;
	}
	
	@Override
	public void add(WeChatCampaignDefaultTemplate bean) {
		try {
			this.dao.createBean(bean);
		} catch (Exception e) {
			LOGGER.error("WeChatCampaignDefaultTemplateService add Error", e.fillInStackTrace());
		}
	}
	
	@Override
	public void update(WeChatCampaignDefaultTemplate bean) {
		try {
			this.dao.updateBean(bean);
		} catch (Exception e) {
			LOGGER.error("WeChatCampaignDefaultTemplateService update Error", e.fillInStackTrace());
		}
	}
	
	@Override
	public WeChatCampaignDefaultTemplate findOne(int id) {
		return this.dao.findOne(id);
	}
}
