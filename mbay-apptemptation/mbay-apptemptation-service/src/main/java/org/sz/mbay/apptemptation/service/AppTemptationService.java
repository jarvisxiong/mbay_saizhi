package org.sz.mbay.apptemptation.service;

import java.util.List;

import org.sz.mbay.apptemptation.bean.AppCampaignStatistics;
import org.sz.mbay.apptemptation.bean.AppTemptation;
import org.sz.mbay.apptemptation.bean.AppTemptationAdvanced;
import org.sz.mbay.apptemptation.bean.AppTemptationOrderStatistics;
import org.sz.mbay.apptemptation.bean.AppTemptationStrategy;
import org.sz.mbay.apptemptation.form.AppTemptationForm;
import org.sz.mbay.base.area.Area;
import org.sz.mbay.base.enums.CampaignStep;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.trafficorder.qo.TrafficOrderQO;

/**
 * app诱惑Service层接口
 * 
 * @author jerry
 */
public interface AppTemptationService {
	
	/**
	 * 添加活动
	 * 
	 * @param bean
	 * @return
	 */
	public String addAppTemptation(AppTemptation bean, String usernumber);
	
	/**
	 * 查询所有活动
	 * 
	 * @param bean
	 * @param pageinfo
	 * @return
	 */
	public List<AppTemptation> findAllAppTemptation(AppTemptationForm form,
			PageInfo pageinfo);
			
	/**
	 * 查看活动详情
	 * 
	 * @param eventnumber
	 * @return
	 */
	public AppTemptation findAppTemptation(String eventnumber);
			
	/**
	 * 取消用户指定的活动
	 * 
	 * @param eventnumber
	 * @param usernumber
	 * @return
	 */
	public ExecuteResult cancelAppTemptation(String eventnumber,
			String usernumber);
			
	/**
	 * 获取活动创建到第几步
	 * 
	 * @param eventnumber
	 * @param usernuber
	 * @return
	 */
	public CampaignStep findCampaignStep(String eventnumber, String usernuber);
	
	/**
	 * 活动赠送数量
	 * 
	 * @param eventnumber
	 * @return
	 */
	public int getAppTemptationSendQuantity(String eventnumber);
	
	/**
	 * 活动已送出数量
	 * 
	 * @param eventnumber
	 * @return
	 */
	public int findAppTemptationSendedQuantity(String eventnumber);
	
	/**
	 * 修改活动，单个号码是否可多次参与
	 * 
	 * @param eventnumber
	 * @param repeat_enable
	 * @return
	 */
	public boolean updateAppTemptationRepeatEnable(String eventnumber,
			boolean repeat_enable);
			
	/**
	 * 更改活动日期
	 * 
	 * @param event
	 *            活动
	 * @return 执行结果
	 */
	public ExecuteResult updateAppTemptationDate(String eventnumber,
			String eventstarttime, String eventendtime);
			
	/**
	 * 修改活动超出数量是否可继续兑换
	 * 
	 * @param eventnumber
	 * @param continuable
	 * @return
	 */
	public boolean updateAppTemptationContinuable(String eventnumber,
			boolean continuable);
			
	/**
	 * 根据不同状态统计数据
	 * 
	 * @param usernumber
	 * @return
	 */
	public AppCampaignStatistics statisticCampaign(String usernumber);
	
	/**
	 * 根据活动编号查询开发者模式信息
	 * 
	 * @param campaignNumber
	 * @return
	 */
	public AppTemptationAdvanced findAppTemptationAdvanced(
			String campaignNumber);
			
	/**
	 * 创建开发者模式
	 * 
	 * @param usernumber
	 * 			
	 * @param eventnumber
	 */
	public ExecuteResult addAppTemptationAdvanced(
			AppTemptationAdvanced advanced);
			
	/**
	 * 修改开发者模式
	 * 
	 * @param usernumber
	 * 			
	 * @param eventnumber
	 */
	public ExecuteResult updateAppTemptationAdvanced(
			AppTemptationAdvanced advanced);
			
	/**
	 * app诱惑统计信息
	 * 
	 * @param form
	 * @return
	 */
	AppTemptationOrderStatistics getAppTemptationOrderStatistics(
			TrafficOrderQO form);
			
	/**
	 * 修改活动短信提醒
	 * 
	 * @param eventnumber
	 * @param sendsms
	 * @return
	 */
	public boolean updateAppTemptationSendSms(String eventnumber,
			boolean sendsms);
			
	/**
	 * 根据活动编号查询活动赠送方式
	 * 
	 * @param eventnumber
	 * @return
	 */
	public AppTemptation findAppTemptationSendInfo(String eventnumber);
	
	/**
	 * 判断当前号码是否可以参与当前活动(app诱惑)
	 * 
	 * @param eventnumber
	 * @param mobile
	 * @return
	 */
	public ExecuteResult appTemptationJudgeMobileReceiveEnable(
			String eventnumber, String mobile);
			
	/**
	 * 根据活动编号，和号码归属地信息查询对应的活动流量包(app诱惑)
	 * 
	 * @param eventnumber
	 * @param area
	 * @param operator
	 * @return
	 */
	public AppTemptationStrategy findAppTemptationEventStrategyTrafficInfo(
			String eventnumber, Area area, OperatorType operator);
			
	/**
	 * 增加活动和策略送出数量，减少活动和用户锁定的美贝(app诱惑)
	 * 
	 * @param strategyNumber
	 *            策略编号
	 * @return
	 */
	public int increEventSentAnaReduLocked(String strategynumber);
	
	/**
	 * 活动号查用户编号
	 * 
	 * @param number
	 * @return
	 */
	public String getUserNumberByEventNumber(String number);
	
	/**
	 * 活动是否存在
	 * 
	 * @param eventnumber
	 * @return
	 */
	public boolean isExistingAppTemptation(String eventNumber,
			String userNumber);
}
