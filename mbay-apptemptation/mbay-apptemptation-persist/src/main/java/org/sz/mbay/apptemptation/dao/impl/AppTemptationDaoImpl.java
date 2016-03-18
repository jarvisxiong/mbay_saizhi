package org.sz.mbay.apptemptation.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.sz.mbay.apptemptation.bean.AppCampaignStatistics;
import org.sz.mbay.apptemptation.bean.AppTemptation;
import org.sz.mbay.apptemptation.bean.AppTemptationAdvanced;
import org.sz.mbay.apptemptation.bean.AppTemptationOrderStatistics;
import org.sz.mbay.apptemptation.bean.AppTemptationStrategy;
import org.sz.mbay.apptemptation.dao.AppTemptationDao;
import org.sz.mbay.apptemptation.form.AppTemptationForm;
import org.sz.mbay.base.area.Area;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.base.enums.CampaignStep;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.trafficorder.qo.TrafficOrderQO;

@Repository
public class AppTemptationDaoImpl extends BaseDaoImpl<AppTemptation>implements
		AppTemptationDao {
		
	@Override
	public void createAppTemptationStrategy(AppTemptationStrategy startegy) {
		this.template.insert("createAppTemptationStrategy", startegy);
	}
	
	@Override
	public void setAppTemptationLockedMbay(String eventnumber,
			double maxunitprice)
					throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eventnumber", eventnumber);
		map.put("maxpackageprice", maxunitprice);
		this.template.update("setAppTemptationLockedMbay", map);
	}
	
	@Override
	public int getAppTemptationSendQuantity(String eventnumber) {
		return this.template.selectOne("getAppTemptationSendQuantity",
				eventnumber);
	}
	
	@Override
	public CampaignStatus getCampaignState(String eventnumber,
			String usernumber) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("eventnumber", eventnumber);
		map.put("usernumber", usernumber);
		AppTemptation event = this.template.selectOne("getAppTemptationState",
				map);
		return event.getState();
	}
	
	@Override
	public void updateAppTemptationTemplateWithoutShare(int modelid,
			long backphotoid, long buttonphotoid) {
		Map<String, Number> map = new HashMap<String, Number>();
		map.put("modelid", modelid);
		map.put("backpthotoid", backphotoid);
		map.put("buttonphotoid", buttonphotoid);
		this.template.update("updateAppTemptationTemplateWithoutShare", map);
	}
	
	@Override
	public List<AppTemptation> findAllAppTemptation(AppTemptationForm form,
			PageInfo pageinfo) {
		return super.findList(form, pageinfo, "AppTemptation");
	}
	
	@Override
	public int updateCampaignStep(String eventnumber, CampaignStep eventstep) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("step", eventstep.ordinal());
		map.put("eventnumber", eventnumber);
		return this.template.update("updateAppTemptationStep", map);
	}
	
	@Override
	public AppTemptation findAppTemptationByNumber(String eventnumber) {
		return this.template.selectOne("findAppTemptationByNumber",
				eventnumber);
	}
	
	@Override
	public int updateCampaignState(String eventnumber, CampaignStatus state) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eventnumber", eventnumber);
		map.put("state", state.ordinal());
		return this.template.update("updateAppTemptationState", map);
	}
	
	@Override
	public CampaignStep findCampaignStep(String eventnumber,
			String usernumber) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("eventnumber", eventnumber);
		map.put("usernumber", usernumber);
		int step = this.template.selectOne("findAppTemptationStep", map);
		return CampaignStep.values()[step];
	}
	
	@Override
	public int findAppTemptationSendedQuantity(String eventnumber) {
		return this.template.selectOne("findAppTemptationSendedQuantity",
				eventnumber);
	}
	
	@Override
	public int updateAppTemptationRepeatEnable(String eventnumber,
			boolean repeat_enable) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eventnumber", eventnumber);
		map.put("repeat_enable", repeat_enable);
		return this.template.update("updateAppTemptationRepeatEnable", map);
	}
	
	@Override
	public int updateAppTemptationDate(String eventnumber,
			String eventstarttime,
			String eventendtime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eventnumber", eventnumber);
		map.put("eventstarttime", eventstarttime);
		map.put("eventendtime", eventendtime);
		return this.template.update("updateAppTemptationDate", map);
	}
	
	@Override
	public AppTemptation findAppTemptationBaseInfo(String eventnumber) {
		return this.template.selectOne("findAppTemptationBaseInfo",
				eventnumber);
	}
	
	@Override
	public int getCountOfAppTemptation(String eventNumber, String userNumber) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("eventNumber", eventNumber);
		map.put("userNumber", userNumber);
		return this.template.selectOne("getCountOfAppTemptation", map);
	}
	
	@Override
	public int updateAppTemptationContinuable(String eventnumber,
			boolean continuable) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eventnumber", eventnumber);
		map.put("continuable", continuable);
		return this.template.update("updateAppTemptationContinuable", map);
	}
	
	@Override
	public AppCampaignStatistics statisticCampaign(String usernumber) {
		return this.template.selectOne("appStatisticAppTemptation", usernumber);
	}
	
	@Override
	public boolean isExistingAppTemptation(String campaignNumber,
			String userNumber) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("campaignNumber", campaignNumber);
		map.put("userNumber", userNumber);
		return this.template.selectOne("isExistingAppTemptation", map);
	}
	
	@Override
	public AppTemptationAdvanced findAppTemptationAdvanced(
			String campaignNumber) {
		return this.template.selectOne(
				"findAppTemptationAdvancedByCampaignNumber", campaignNumber);
	}
	
	@Override
	public int updateAppTemptationAdvanced(AppTemptationAdvanced advanced) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", advanced.getId());
		map.put("token", advanced.getToken());
		return this.template.update("updateAppTemptationAdvanced", map);
	}
	
	@Override
	public AppTemptationOrderStatistics getAppTemptationOrderStatistics(
			TrafficOrderQO form) {
		return template.selectOne("getAppTemptationOrderStatistics", form);
	}
	
	@Override
	public int updateAppTemptationSendSms(String eventnumber, boolean sendsms) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eventnumber", eventnumber);
		map.put("sendsms", sendsms);
		return this.template.update("updateAppTemptationSendSms", map);
	}
	
	@Override
	public AppTemptation findAppTemptationSendInfo(String eventnumber) {
		return this.template.selectOne("findAppTemptationSendInfo",
				eventnumber);
	}
	
	@Override
	public ExecuteResult appTemptationJudgeMobileReceiveEnable(
			String eventnumber, String mobile) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("event_number", eventnumber);
		map.put("r_mobile", mobile);
		map.put("renable", 0);
		map.put("message", "");
		this.template.update("appTemptationJudgeMobileReceiveEnable", map);
		String enbale = map.get("renable").toString();
		if ("0".equals(enbale)) {// 不可参与
			String message = map.get("message").toString();
			return new ExecuteResult(false, message);
		}
		return new ExecuteResult(true, "");
	}
	
	@Override
	public AppTemptationStrategy findAppTemptationEventStrategyTrafficInfo(
			String eventnumber, Area area, OperatorType operator) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eventnumber", eventnumber);
		map.put("area", area.value);
		map.put("operator", operator.ordinal());
		return this.template.selectOne("findAppTemptationStrategyInfo", map);
	}
	
	@Override
	public int increEventSentAnaReduLocked(String strategynumber) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("event_strategy_number", strategynumber);
		return this.template.update("appTemptationCampaignSend", map);
	}
	
	@Override
	public List<String> findAllCampaignNumberOverToday() {
		Map<String, Object> params = new HashMap<>();
		params.put("now", DateTime.now());
		params.put("status", CampaignStatus.IN_ACTIVE);
		return template.selectList(
				"AppTemptation.findAllCampaignNumberOverToday", params);
	}
	
	@Override
	public List<String> findAllCampaignNumberStartToday() {
		Map<String, Object> params = new HashMap<>();
		params.put("now", DateTime.now());
		params.put("status", CampaignStatus.NOT_STARTED);
		return template.selectList(
				"AppTemptation.findAllCampaignNumberStartToday", params);
	}
	
	@Override
	public int updateState(String number, CampaignStatus inActive) {
		Map<String, Object> params = new HashMap<>();
		params.put("state", inActive);
		params.put("number", number);
		return template.update("AppTemptation.updateState", params);
	}
	
	@Override
	public String getUserNumberByEventNumber(String number) {
		return template.selectOne("AppTemptation.getUserNumberByEventNumber",
				number);
	}
	
	@Override
	public int clearLocked(String number) {
		return template.update("AppTemptation.clearLocked", number);
	}
}
