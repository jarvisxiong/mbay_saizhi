package org.sz.mbay.apptemptation.dao;

import java.util.List;

import org.sz.mbay.apptemptation.bean.AppCampaignStatistics;
import org.sz.mbay.apptemptation.bean.AppTemptation;
import org.sz.mbay.apptemptation.bean.AppTemptationAdvanced;
import org.sz.mbay.apptemptation.bean.AppTemptationOrderStatistics;
import org.sz.mbay.apptemptation.bean.AppTemptationStrategy;
import org.sz.mbay.apptemptation.form.AppTemptationForm;
import org.sz.mbay.base.area.Area;
import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.base.enums.CampaignStep;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.trafficorder.qo.TrafficOrderQO;

/**
 * app诱惑dao
 * 
 * @author jerry
 */
public interface AppTemptationDao extends BaseDao<AppTemptation> {
	
	public void createAppTemptationStrategy(AppTemptationStrategy startegy);
	
	public AppTemptation findAppTemptationByNumber(String eventnumber);
			
	public AppTemptation findAppTemptationBaseInfo(String eventnumber);
			
	public void setAppTemptationLockedMbay(String eventnumber,
			double maxunitprice) throws Exception;
			
	public int getAppTemptationSendQuantity(String eventnumber);
	
	public CampaignStatus getCampaignState(String eventnumber,
			String usernumber);
	
	public void updateAppTemptationTemplateWithoutShare(int modelid,
			long backphotoid, long buttonphotoid);
			
	public List<AppTemptation> findAllAppTemptation(AppTemptationForm event,
			PageInfo pageinfo);
			
	public int updateCampaignStep(String eventnumber, CampaignStep eventstep);
	
	public int updateCampaignState(String eventnumber, CampaignStatus state);
	
	public CampaignStep findCampaignStep(String eventnumber, String usernuber);
	
	public int findAppTemptationSendedQuantity(String eventnumber);
	
	public int updateAppTemptationRepeatEnable(String eventnumber,
			boolean repeat_enable);
			
	public int updateAppTemptationDate(String eventnumber,
			String eventstarttime, String eventendtime);
	
	public int getCountOfAppTemptation(String eventNumber, String userNumber);
	
	public int updateAppTemptationContinuable(String eventnumber,
			boolean continuable);
			
	public AppCampaignStatistics statisticCampaign(String usernumber);
	
	public boolean isExistingAppTemptation(String eventNumber,
			String userNumber);
			
	public AppTemptationAdvanced findAppTemptationAdvanced(
			String campaignNumber);
			
	public int updateAppTemptationAdvanced(AppTemptationAdvanced advanced);
	
	AppTemptationOrderStatistics getAppTemptationOrderStatistics(
			TrafficOrderQO form);
			
	public int updateAppTemptationSendSms(String eventnumber, boolean sendsms);
	
	public AppTemptation findAppTemptationSendInfo(String eventnumber);
	
	public ExecuteResult appTemptationJudgeMobileReceiveEnable(
			String eventnumber, String mobile);
			
	public AppTemptationStrategy findAppTemptationEventStrategyTrafficInfo(
			String eventnumber, Area area, OperatorType operator);
			
	public int increEventSentAnaReduLocked(String strategynumber);
	
	public List<String> findAllCampaignNumberOverToday();
	
	public List<String> findAllCampaignNumberStartToday();
	
	public int updateState(String number, CampaignStatus inActive);

	public String getUserNumberByEventNumber(String number);

	public int clearLocked(String number);
}
