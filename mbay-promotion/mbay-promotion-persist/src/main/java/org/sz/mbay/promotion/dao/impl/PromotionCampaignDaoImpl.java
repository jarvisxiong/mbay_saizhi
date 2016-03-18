package org.sz.mbay.promotion.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.base.enums.CampaignStep;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.promotion.bean.CampaignStatistics;
import org.sz.mbay.promotion.bean.PromotionCampaign;
import org.sz.mbay.promotion.bean.PromotionCampaignAdvanced;
import org.sz.mbay.promotion.bean.PromotionCampaignTemplate;
import org.sz.mbay.promotion.dao.PromotionCampaignDao;
import org.sz.mbay.promotion.qo.CampaignForm;

@Repository
public class PromotionCampaignDaoImpl extends BaseDaoImpl<PromotionCampaign> implements PromotionCampaignDao {
	
	@Override
	public List<PromotionCampaign> findAllPromotionCampaign(CampaignForm event, PageInfo pageinfo) {
		return super.findList(event, pageinfo, "PromotionCampaign");
	}
	
	@Override
	public PromotionCampaign findPromotionCampaign(String eventnumber, String usernumber) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("eventnumber", eventnumber);
		map.put("usernumber", usernumber);
		return this.template.selectOne("findPromotionCampaign", map);
	}
	
	@Override
	public int updateCampaignState(String eventnumber, CampaignStatus state) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eventnumber", eventnumber);
		map.put("state", state.ordinal());
		return this.template.update("updatePromotionCampaignState", map);
	}
	
	@Override
	public PromotionCampaign findPromotionCampaignBaseInfo(String eventnumber) {
		return this.template.selectOne("findPromotionCampaignBaseInfo", eventnumber);
	}
	
	@Override
	public int getCountOfCampaign(String eventNumber, String userNumber) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("eventNumber", eventNumber);
		map.put("userNumber", userNumber);
		return this.template.selectOne("getCountOfPromotionCampaign", map);
	}
	
	@Override
	public String findUserNumberByCampaignNumber(String eventnumber) {
		return this.template.selectOne("findUserNumberByPromotionCampaignNumber", eventnumber);
	}
	
	@Override
	public int updateCampaignStep(String eventnumber, CampaignStep eventstep) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("step", eventstep.ordinal());
		map.put("eventnumber", eventnumber);
		return this.template.update("updatePromotionCampaignStep", map);
	}
	
	@Override
	public PromotionCampaignTemplate findPromotionCampaignTemplate(String eventNumber) {
		return this.template.selectOne("findPromotionCampaignTemplateByEventNumber", eventNumber);
	}
	
	@Override
	public CampaignStep findCampaignStep(String eventnumber, String usernumber) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("eventnumber", eventnumber);
		map.put("usernumber", usernumber);
		int step = this.template.selectOne("findPromotionCampaignStep", map);
		return CampaignStep.values()[step];
	}
	
	@Override
	public int updateCampaignDate(String eventnumber, String eventstarttime, String eventendtime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eventnumber", eventnumber);
		map.put("eventstarttime", eventstarttime);
		map.put("eventendtime", eventendtime);
		return this.template.update("updatePromotionCampaignDate", map);
	}
	
	@Override
	public int updateCampaignRate(String eventnumber, String rate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eventnumber", eventnumber);
		map.put("rate", rate);
		return this.template.update("updatePromotionCampaignRate", map);
	}
	
	@Override
	public CampaignStatus getCampaignState(String eventnumber, String usernumber) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("eventnumber", eventnumber);
		map.put("usernumber", usernumber);
		PromotionCampaign event = this.template.selectOne("getPromotionCampaignState", map);
		return event.getState();
	}
	
	@Override
	public CampaignStatistics statisticCampaign(String usernumber) {
		return this.template.selectOne("statisticCampaign", usernumber);
	}
	
	@Override
	public void increaseRcodeSentNum(String campaignNumber) {
		this.template.update("increaseRcodeSentNum", campaignNumber);
	}
	
	@Override
	public void increaseRcodeGotNum(String campaignNumber) {
		this.template.update("increaseRcodeGotNum", campaignNumber);
	}
	
	@Override
	public int updateCampaignVerificate(String campaignNumber, boolean verificate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eventnumber", campaignNumber);
		map.put("verificate", verificate);
		return this.template.update("updatePromotionCampaignVerificate", map);
	}
	
	@Override
	public int updateCampaignContinuable(String campaignNumber, boolean continuable) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eventnumber", campaignNumber);
		map.put("continuable", continuable);
		return this.template.update("updatePromotionCampaignContinuable", map);
	}
	
	@Override
	public int updateCampaignShare(String campaignNumber, boolean share) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eventnumber", campaignNumber);
		map.put("share", share);
		return this.template.update("updatePromotionCampaignShare", map);
	}
	
	@Override
	public PromotionCampaignAdvanced findCampaignAdvanced(String campaignNumber) {
		return this.template.selectOne("findPromotionCampaignAdvancedByCampaignNumber", campaignNumber);
	}
	
	@Override
	public int updateCampaignAdvanced(PromotionCampaignAdvanced advanced) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", advanced.getId());
		map.put("token", advanced.getToken());
		return this.template.update("updatePromotionCampaignAdvanced", map);
	}

	@Override
	public List<String> findAllPromotionCampaignNumberStartToday() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", CampaignStatus.NOT_STARTED);
		map.put("currentTime", DateTime.now());
		return this.template.selectList("findAllPromotionCampaignNumberStartToday", map);
	}
	
	@Override
	public List<String> findAllPromotionCampaignNumberOverToday() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", CampaignStatus.IN_ACTIVE);
		map.put("currentTime", DateTime.now());
		return this.template.selectList("findAllPromotionCampaignNumberOverToday", map);
	}
	
	@Override
	public void clearConfig(String eventnumber) {
		this.template.update("clearPromotionConfig", eventnumber);
	}
	
	@Override
	public PromotionCampaign findQueryInfomation(String campaignNumber) {
		return this.template.selectOne("findQueryInfomation", campaignNumber);
	}
	
	@Override
	public int updateCampaignSendMB(String campaignNumber, int sendMB) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignNumber", campaignNumber);
		map.put("sendMB", sendMB);
		return this.template.update("updateCampaignSendMB", map);
	}
}
