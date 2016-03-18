package org.sz.mbay.channel.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.channel.bean.CampaignRedeemCode;
import org.sz.mbay.channel.dao.CampaignRedeemCodeDao;

@Repository
public class CampaignRedeemCodeDaoImpl extends BaseDaoImpl<CampaignRedeemCode> implements CampaignRedeemCodeDao {
	
	@Override
	public String findRedeemCode(String campaignId, String cellPhone) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("campaignId", campaignId);
		map.put("cellPhone", cellPhone);
		return this.template.selectOne("findCampaignRedeemCode", map);
	}
	
	@Override
	public CampaignRedeemCode findRedeemCodeByCode(String code) {
		return this.template.selectOne("findRedeemCodeByCode", code);
	}
	
	@Override
	public int updateCode(CampaignRedeemCode code) {
		return this.template.update("updateCode", code);
	}
}
