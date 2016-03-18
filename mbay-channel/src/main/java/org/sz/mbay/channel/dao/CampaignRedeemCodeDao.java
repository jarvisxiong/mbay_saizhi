package org.sz.mbay.channel.dao;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.channel.bean.CampaignRedeemCode;

public interface CampaignRedeemCodeDao extends BaseDao<CampaignRedeemCode> {
	
	String findRedeemCode(String campaignId, String cellPhone);
	
	CampaignRedeemCode findRedeemCodeByCode(String code);
	
	int updateCode(CampaignRedeemCode code);
	
}
