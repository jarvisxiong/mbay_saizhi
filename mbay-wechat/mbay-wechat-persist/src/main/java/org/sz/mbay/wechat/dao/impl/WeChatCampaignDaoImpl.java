package org.sz.mbay.wechat.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.area.Area;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.base.enums.CampaignStep;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.wechat.bean.WeChatCampaign;
import org.sz.mbay.wechat.bean.WeChatCampaignAdvanced;
import org.sz.mbay.wechat.bean.WeChatCampaignAdvertise;
import org.sz.mbay.wechat.bean.WeChatCampaignDefaultTemplate;
import org.sz.mbay.wechat.bean.WeChatCampaignStatistics;
import org.sz.mbay.wechat.bean.WeChatCampaignStrategy;
import org.sz.mbay.wechat.bean.WeChatCampaignTemplate;
import org.sz.mbay.wechat.dao.WeChatCampaignDao;
import org.sz.mbay.wechat.qo.WeChatCampaignForm;

@Repository
public class WeChatCampaignDaoImpl extends BaseDaoImpl<WeChatCampaign> implements WeChatCampaignDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WeChatCampaignDaoImpl.class);
	
	@Override
	public void createCampaignStrategy(WeChatCampaignStrategy startegy) {
		this.template.insert("createWeChatCampaignStrategy", startegy);
	}
	
	@Override
	public void setCampaignLockedMbay(String eventnumber, double maxunitprice) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eventnumber", eventnumber);
		map.put("maxpackageprice", maxunitprice);
		this.template.update("setWeChatCampaignLockedMbay", map);
	}
	
	@Override
	public int getCampaignSendQuantity(String eventnumber) {
		return this.template.selectOne("getWeChatCampaignSendQuantity", eventnumber);
	}
	
	@Override
	public CampaignStatus getCampaignState(String eventnumber, String usernumber) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("eventnumber", eventnumber);
		map.put("usernumber", usernumber);
		WeChatCampaign event = this.template.selectOne("getWeChatCampaignState", map);
		return event.getState();
	}
	
	@Override
	public int cancelCampaign(String eventnumber, String usernumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eventnumber", eventnumber);
		map.put("usernumber", usernumber);
		map.put("rowc", 0);
		return this.template.update("cancelWeChatCampaign", map);
	}
	
	@Override
	public List<WeChatCampaign> findAllWeChatCampaign(WeChatCampaignForm form, PageInfo pageinfo) {
		return super.findList(form, pageinfo, "WeChatCampaign");
	}
	
	@Override
	public int updateCampaignStep(String eventnumber, CampaignStep eventstep) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("step", eventstep.ordinal());
		map.put("eventnumber", eventnumber);
		return this.template.update("updateWeChatCampaignStep", map);
	}
	
	@Override
	public WeChatCampaign findWeChatCampaignByNumber(String eventnumber, String usernumber) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("eventnumber", eventnumber);
		map.put("usernumber", usernumber);
		return this.template.selectOne("findWeChatCampaign", map);
	}
	
	@Override
	public int updateCampaignState(String eventnumber, CampaignStatus state) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eventnumber", eventnumber);
		map.put("state", state.ordinal());
		return this.template.update("updateWeChatCampaignState", map);
	}
	
	@Override
	public CampaignStep findCampaignStep(String eventnumber, String usernumber) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("eventnumber", eventnumber);
		map.put("usernumber", usernumber);
		int step = this.template.selectOne("findWeChatCampaignStep", map);
		return CampaignStep.values()[step];
	}
	
	@Override
	public WeChatCampaignTemplate findCampaignTemplate(String eventnumber) {
		return this.template.selectOne("findWeChatCampaignTemplateByEventNumber", eventnumber);
	}
	
	@Override
	public int findCampaignSendedQuantity(String eventnumber) {
		return this.template.selectOne("findWeChatCampaignSendedQuantity", eventnumber);
	}
	
	@Override
	public int updateCampaignRepeatEnable(String eventnumber, boolean repeat_enable) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eventnumber", eventnumber);
		map.put("repeat_enable", repeat_enable);
		return this.template.update("updateWeChatCampaignRepeatEnable", map);
	}
	
	@Override
	public int updateCampaignDate(String eventnumber, String eventstarttime, String eventendtime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eventnumber", eventnumber);
		map.put("eventstarttime", eventstarttime);
		map.put("eventendtime", eventendtime);
		return this.template.update("updateWeChatCampaignDate", map);
	}
	
	@Override
	public int completeCampaignCreate(String eventnumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eventnumber", eventnumber);
		map.put("rowc", 0);
		return this.template.update("completeWeChatCampaignCreate", map);
	}
	
	@Override
	public WeChatCampaign findWeChatCampaignBaseInfo(String eventnumber) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("eventnumber", eventnumber);
		return this.template.selectOne("findWeChatCampaignBaseInfo", map);
	}
	
	@Override
	public WeChatCampaignAdvanced findCampaignAdvanced(String campaignNumber) {
		return this.template.selectOne("findWeChatCampaignAdvancedByCampaignNumber", campaignNumber);
	}
	
	@Override
	public int updateCampaignAdvanced(WeChatCampaignAdvanced advanced) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", advanced.getId());
		map.put("token", advanced.getToken());
		return this.template.update("updateWeChatCampaignAdvanced", map);
	}
	
	@Override
	public int getCountOfCampaign(String eventNumber, String userNumber) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("eventNumber", eventNumber);
		map.put("userNumber", userNumber);
		return this.template.selectOne("getCountOfWeChatCampaign", map);
	}
	
	@Override
	public String findUserNumberByCampaignNumber(String eventnumber) {
		return this.template.selectOne("findUserNumberByWeChatCampaignNumber",
				eventnumber);
	}
	
	@Override
	public int updateCampaignContinuable(String eventnumber, boolean continuable) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eventnumber", eventnumber);
		map.put("continuable", continuable);
		return this.template.update("updateWeChatCampaignContinuable", map);
	}
	
	@Override
	public int updateCampaignDirectEnable(String eventnumber, boolean directEnable) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eventnumber", eventnumber);
		map.put("directEnable", directEnable);
		return this.template.update("updateWeChatCampaignDirectEnable", map);
	}
	
	@Override
	public List<WeChatCampaignDefaultTemplate> findWeChatCampaignDefaultTemplateList() {
		List<WeChatCampaignDefaultTemplate> list = null;
		try {
			list = this.template.selectList("findAllWeChatCampaignDefaultTemplate");
		} catch (Exception e) {
			LOGGER.error("WeChatCampaignDaoImpl findEventDefaultTemplateList Error", e.fillInStackTrace());
		}
		return list;
	}
	
	@Override
	public List<WeChatCampaignAdvertise> findWeChatCampaignAdvertiseList() {
		List<WeChatCampaignAdvertise> list = null;
		try {
			list = this.template.selectList("findAllWeChatCampaignAdvertise");
		} catch (Exception e) {
			LOGGER.error("WeChatCampaignDaoImpl findEventAdvertiseList Error", e.fillInStackTrace());
		}
		return list;
	}
	
	@Override
	public WeChatCampaignStatistics statisticWeChatCampaign(String usernumber) {
		return this.template.selectOne("statisticWechatCampaign", usernumber);
		
	}
	
	@Override
	public boolean isExistingCampaign(String campaignNumber, String userNumber) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("campaignNumber", campaignNumber);
		map.put("userNumber", userNumber);
		return this.template.selectOne("isExistingWeChatCampaign", map);
	}
	
	@Override
	public double getCampaignMaxPackegePrice(String campaignNumber) {
		return this.template.selectOne("getCampaignMaxPackegePrice", campaignNumber);
	}
	
	@Override
	public Boolean isDefault(String picture) {
		return (Integer) this.template.selectOne("isDefault", picture) > 0 ? true : false;
	}
	
	@Override
	public CampaignStatus findCampaignStatus(String campaignNumber) {
		return this.template.selectOne("findCampaignStatus", campaignNumber);
	}
	
	@Override
	public boolean findCampaignDirectEnable(String campaignNumber) {
		return (Integer) this.template.selectOne("findCampaignDirectEnable", campaignNumber) == 0 ? false : true;
	}
	
	@Override
	public WeChatCampaignStrategy findEventStrategyTrafficInfo(String eventnumber,
			Area area, OperatorType operator) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eventnumber", eventnumber);
		map.put("area", area.value);
		map.put("operator", operator.ordinal());
		return this.template.selectOne("findCampaignStrategyInfo", map);
	}
	
	@Override
	public WeChatCampaign findWeChatEventSendInfo(String eventnumber) {
		return this.template.selectOne("findWeChatCampaignSendInfo",
				eventnumber);
	}
	
	@Override
	public int weChatCampaignSend(String strategyNumber) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("event_strategy_number", strategyNumber);
		return this.template.update("weChatCampaignSend", map);
	}
	
	@Override
	public ExecuteResult judgeMobileReceiveEnable(String eventnumber,
			String mobile) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("event_number", eventnumber);
		map.put("r_mobile", mobile);
		map.put("renable", 0);
		map.put("message", "");
		this.template.update("wechatJudgeMobileReceiveEnable", map);
		String enbale = map.get("renable").toString();
		if ("0".equals(enbale)) {// 不可参与
			String message = map.get("message").toString();
			return new ExecuteResult(false, message);
		}
		return new ExecuteResult(true, "");
	}
	
	@Override
	public int increEventSentAnaReduLocked(String strategyNumber) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("event_strategy_number", strategyNumber);
		return this.template.update("weChatCampaignSend", map);
		
	}

	@Override
	public List<String> findAllWeChatCampaignNumberStartToday() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", CampaignStatus.NOT_STARTED);
		map.put("currentTime", DateTime.now());
		return this.template.selectList("findAllWeChatCampaignNumberStartToday", map);
	}

	@Override
	public List<String> findAllWeChatCampaignNumberOverToday() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", CampaignStatus.IN_ACTIVE);
		map.put("currentTime", DateTime.now());
		return this.template.selectList("findAllWeChatCampaignNumberOverToday", map);
	}
	
	@Override
	public void decreaseCampaignLockedMbay(String eventnumber) {
		this.template.update("decreaseWeChatCampaignLockedMbay", eventnumber);
	}
}