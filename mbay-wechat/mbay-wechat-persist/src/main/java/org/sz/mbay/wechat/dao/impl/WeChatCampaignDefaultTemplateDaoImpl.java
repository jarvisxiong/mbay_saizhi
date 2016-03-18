package org.sz.mbay.wechat.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.wechat.bean.WeChatCampaignDefaultTemplate;
import org.sz.mbay.wechat.dao.WeChatCampaignDefaultTemplateDao;

@Repository
public class WeChatCampaignDefaultTemplateDaoImpl extends BaseDaoImpl<WeChatCampaignDefaultTemplate> implements WeChatCampaignDefaultTemplateDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WeChatCampaignDefaultTemplateDaoImpl.class);
	
	@Override
	public WeChatCampaignDefaultTemplate findOne(int id) {
		WeChatCampaignDefaultTemplate bean = null;
		try {
			bean = this.template.selectOne("findEventDefaultTemplateById", id);
		} catch (Exception e) {
			LOGGER.error("WeChatCampaignDefaultTemplateDao findOne Error", e.fillInStackTrace());
		}
		return bean;
	}
	
	@Override
	public List<WeChatCampaignDefaultTemplate> findList(String usernumber, PageInfo pageinfo) {
		List<WeChatCampaignDefaultTemplate> list = null;
		try {
			list = super.findList(usernumber, pageinfo, "EventDefaultTemplate");
		} catch (Exception e) {
			LOGGER.error("WeChatCampaignDefaultTemplateDao findList Error", e.fillInStackTrace());
		}
		return list;
	}
}
