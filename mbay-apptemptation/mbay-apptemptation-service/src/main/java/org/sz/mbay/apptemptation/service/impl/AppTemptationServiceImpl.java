package org.sz.mbay.apptemptation.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.sz.mbay.apptemptation.bean.AppCampaignStatistics;
import org.sz.mbay.apptemptation.bean.AppTemptation;
import org.sz.mbay.apptemptation.bean.AppTemptationAdvanced;
import org.sz.mbay.apptemptation.bean.AppTemptationBindIp;
import org.sz.mbay.apptemptation.bean.AppTemptationOrderStatistics;
import org.sz.mbay.apptemptation.bean.AppTemptationStrategy;
import org.sz.mbay.apptemptation.constant.error.AppTemptationError;
import org.sz.mbay.apptemptation.dao.AppTemptationBindIpDao;
import org.sz.mbay.apptemptation.dao.AppTemptationDao;
import org.sz.mbay.apptemptation.form.AppTemptationForm;
import org.sz.mbay.apptemptation.service.AppTemptationService;
import org.sz.mbay.base.area.Area;
import org.sz.mbay.base.constant.SerialSeed;
import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.base.enums.CampaignStep;
import org.sz.mbay.base.enums.StrategyState;
import org.sz.mbay.base.exception.ServiceException;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.UtilService;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.camapgin.common.service.CampaignService;
import org.sz.mbay.channel.user.service.UserAccountService;
import org.sz.mbay.channel.user.service.UserContextService;
import org.sz.mbay.common.util.MbayDateFormat;
import org.sz.mbay.common.util.MbayDateFormat.DatePattern;
import org.sz.mbay.operator.dao.OperatorDao;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.trafficorder.qo.TrafficOrderQO;

@Service
public class AppTemptationServiceImpl extends BaseServiceImpl implements
		AppTemptationService, CampaignService {
		
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AppTemptationServiceImpl.class);
			
	@Autowired
	private UtilService utilService;
	@Autowired
	private AppTemptationDao campaignDao;
	@Autowired
	private OperatorDao operatroDao;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private AppTemptationBindIpDao appTemptationBindIpDao;
	@Autowired
	private UserContextService userContextService;
	
	@Override
	@Transactional
	public String addAppTemptation(AppTemptation campaign, String userNumber) {
		int[] packages = new int[campaign.getStratetgylist().size()];
		for (int i = 0; i < packages.length; i++) {
			packages[i] = campaign.getStratetgylist().get(i)
					.getTrafficPackage()
					.getId();
		}
		
		// 查询流量包中最大额度流量宝
		double maxpackageprice = this.operatroDao.getMaxPackagePrice(packages);
		
		// 查询账户可用余额
		double availbleamount = userAccountService
				.getAvailableAmount(userNumber);
		double lockAmount = maxpackageprice * campaign.getQuantity();
		if (lockAmount > availbleamount) {
			throw new ServiceException(
					AppTemptationError.CAMPAIGN_ADD_BALANCE_NOT_ENOUGH);
		}
		
		try {
			// 创建活动
			campaign.setState(CampaignStatus.NONE_FINISH);
			DateTime now = DateTime.now();
			String number = MbayDateFormat.formatDateTime(now,
					DatePattern.yyyyMMdd);
			int nextid = utilService.getNextIndex(AppTemptation.class);
			campaign.setEventnumber(number + (SerialSeed.Event.APP_TEMPTATION)
					+ (SerialSeed.CARDINAL_NUMBER + nextid));
			campaign.setStep(CampaignStep.CREATED_CAMPAIGN);
			campaign.setAmount(lockAmount);
			campaign = campaignDao.createBean(campaign);
			
			// 锁定账户
			userAccountService.lockedMbay(userNumber, lockAmount);
			
			// 创建策略
			for (AppTemptationStrategy strategy : campaign.getStratetgylist()) {
				String strategyNumber = MbayDateFormat.formatDateTime(now,
						DatePattern.yyyyMMdd);
				int nextStrategyid = utilService
						.getNextIndex(AppTemptationStrategy.class);
				strategy.setStrategynumber(strategyNumber
						+ (SerialSeed.EventStrategy.APP_TEMPTATION_STRATEGY)
						+ (SerialSeed.CARDINAL_NUMBER + nextStrategyid));
				strategy.setEventnumber(campaign.getEventnumber());
				strategy.setSendednum(0);
				strategy.setState(StrategyState.ENABLE);
				this.campaignDao.createAppTemptationStrategy(strategy);
			}
			
			// create bind ip
			if (campaign.getIpList() != null
					&& !campaign.getIpList().isEmpty()) {
				for (AppTemptationBindIp ip : campaign.getIpList()) {
					ip.setCampaignNumber(campaign.getEventnumber());
					appTemptationBindIpDao.createBean(ip);
				}
			}
			
			return campaign.getEventnumber();
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			LOGGER.error("addCampaign 添加活动异常", e.fillInStackTrace());
			throw new ServiceException(AppTemptationError.CAMPAIGN_ADD_ERROR);
		}
	}
	
	@Override
	public List<AppTemptation> findAllAppTemptation(AppTemptationForm form,
			PageInfo pageinfo) {
		try {
			return this.campaignDao.findAllAppTemptation(form, pageinfo);
		} catch (Exception e) {
			LOGGER.error("findAllAppTemptation 查询活动列表异常", e.fillInStackTrace());
		}
		return new ArrayList<AppTemptation>();
	}
	
	@Override
	public AppTemptation findAppTemptation(String eventnumber) {
		try {
			return this.campaignDao.findAppTemptationByNumber(eventnumber);
		} catch (Exception e) {
			LOGGER.error("AppTemptationServiceImpl findAppTemptation Error",
					e.fillInStackTrace());
		}
		return null;
	}
	
	@Override
	public CampaignStep findCampaignStep(String eventnumber, String usernuber) {
		return this.campaignDao.findCampaignStep(eventnumber, usernuber);
	}
	
	@Override
	public int getAppTemptationSendQuantity(String eventnumber) {
		return this.campaignDao.getAppTemptationSendQuantity(eventnumber);
	}
	
	@Override
	public int findAppTemptationSendedQuantity(String eventnumber) {
		return this.campaignDao.findAppTemptationSendedQuantity(eventnumber);
	}
	
	@Override
	public boolean updateAppTemptationRepeatEnable(String eventnumber,
			boolean repeat_enable) {
		return this.campaignDao.updateAppTemptationRepeatEnable(eventnumber,
				repeat_enable) > 0;
	}
	
	@Override
	public ExecuteResult updateAppTemptationDate(String eventnumber,
			String eventstarttime, String eventendtime) {
		if (MbayDateFormat.stringToDate(eventstarttime).isAfter(
				MbayDateFormat.stringToDate(eventendtime))) {
			new ExecuteResult(false, "修改失败！");
		}
		if (eventstarttime != null) {
			// 判断开始日期如果是今天就修改活动状态
			DateTime now = MbayDateFormat.timeToDate(DateTime.now());
			DateTime start = MbayDateFormat.stringToDate(eventstarttime);
			if (now.isEqual(start)) { // 修改活动状态为活动中
				this.campaignDao.updateCampaignState(eventnumber,
						CampaignStatus.IN_ACTIVE);
			}
		}
		int ret = this.campaignDao.updateAppTemptationDate(eventnumber,
				eventstarttime,
				eventendtime);
		if (ret != 1) {
			new ExecuteResult(false, "修改失败！");
		}
		return new ExecuteResult(true, "修改成功！");
		
	}
	
	@Override
	public boolean updateAppTemptationContinuable(String eventnumber,
			boolean continuable) {
		return this.campaignDao
				.updateAppTemptationContinuable(eventnumber, continuable) > 0;
	}
	
	@Override
	public AppCampaignStatistics statisticCampaign(String usernumber) {
		return this.campaignDao.statisticCampaign(usernumber);
	}
	
	@Override
	public boolean isExistingAppTemptation(String eventNumber,
			String userNumber) {
		return campaignDao
				.isExistingAppTemptation(eventNumber, userNumber);
	}
	
	@Override
	public AppTemptationAdvanced findAppTemptationAdvanced(
			String campaignNumber) {
		try {
			return this.campaignDao.findAppTemptationAdvanced(campaignNumber);
		} catch (Exception e) {
			LOGGER.error("findAppTemptation Error", e.fillInStackTrace());
			return null;
		}
	}
	
	@Override
	@Transactional
	public ExecuteResult addAppTemptationAdvanced(
			AppTemptationAdvanced advanced) {
		advanced.setId(utilService.getNextIndex(AppTemptationAdvanced.class));
		try {
			campaignDao.createBean(advanced);
			
			AppTemptation app = findAppTemptation(advanced.getCampaignNumber());
					
			// 活动状态设置
			if (app.getStarttime().isAfterNow()) {
				// 如果开始时间在创建时间之后，设置为未开始
				campaignDao.updateCampaignState(advanced.getCampaignNumber(),
						CampaignStatus.NOT_STARTED);
			} else {
				// 如果开始时间在创建时间之前，设置为活动中
				campaignDao.updateCampaignState(advanced.getCampaignNumber(),
						CampaignStatus.IN_ACTIVE);
						
				// 活动中数量加1
				userContextService.increaseOneCamNumInActive(app.getUsernumber());
			}
			
			return new ExecuteResult(true, "操作成功!");
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			LOGGER.error("创建活动开发者模式异常:", e.fillInStackTrace());
		}
		return new ExecuteResult(false, "操作失败!");
	}
	
	@Override
	public ExecuteResult updateAppTemptationAdvanced(
			AppTemptationAdvanced advanced) {
		int result = this.campaignDao.updateAppTemptationAdvanced(advanced);
		return result > 0 ? new ExecuteResult(true, "操作成功!")
				: new ExecuteResult(false, "操作失败!");
	}
	
	@Override
	public AppTemptationOrderStatistics getAppTemptationOrderStatistics(
			TrafficOrderQO form) {
		return campaignDao.getAppTemptationOrderStatistics(form);
	}
	
	@Override
	public boolean updateAppTemptationSendSms(String eventnumber,
			boolean sendsms) {
		return this.campaignDao.updateAppTemptationSendSms(eventnumber,
				sendsms) > 0;
	}
	
	@Override
	public AppTemptation findAppTemptationSendInfo(String eventnumber) {
		return campaignDao.findAppTemptationSendInfo(eventnumber);
	}
	
	@Override
	public ExecuteResult appTemptationJudgeMobileReceiveEnable(
			String eventnumber, String mobile) {
		return campaignDao.appTemptationJudgeMobileReceiveEnable(eventnumber,
				mobile);
	}
	
	@Override
	public AppTemptationStrategy findAppTemptationEventStrategyTrafficInfo(
			String eventnumber, Area area, OperatorType operator) {
		return campaignDao.findAppTemptationEventStrategyTrafficInfo(
				eventnumber, area, operator);
	}
	
	@Override
	public int increEventSentAnaReduLocked(String strategynumber) {
		return campaignDao.increEventSentAnaReduLocked(strategynumber);
	}
	
	@Override
	public List<String> findAllCampaignNumberOverToday() {
		return campaignDao.findAllCampaignNumberOverToday();
	}
	
	@Override
	public List<String> findAllCampaignNumberStartToday() {
		return campaignDao.findAllCampaignNumberStartToday();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ExecuteResult cancelAppTemptation(String eventnumber,
			String userNumber) {
		if (!isExistingAppTemptation(eventnumber, userNumber)) {
			return new ExecuteResult(false, "找不到对应的活动！");
		}
		try {
			// 活动设为取消
			campaignDao.updateState(eventnumber, CampaignStatus.CANCLED);
			
			// 清理活动
			clearCampaign(eventnumber);
			
			return new ExecuteResult(true, "活动取消成功");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return new ExecuteResult(false, "活动取消失败");
		}
	}
	
	/*
	 * 活动结束或取消后的清理
	 */
	private void clearCampaign(String number) throws Exception {
		// 查询基本信息
		AppTemptation app = campaignDao.findAppTemptationBaseInfo(number);
		
		// 解除活动锁定美贝
		campaignDao.clearLocked(number);
		
		// 解除账户锁定美贝
		userAccountService.unLockedMbay(app.getUsernumber(), app.getAmount());
		
		// 活动中数量减1
		userContextService.deductOneCamNumInActive(app.getUsernumber());
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void overCampaign(String number) {
		try {
			// 活动设为结束
			campaignDao.updateState(number, CampaignStatus.OVER);
			
			// 清理活动
			clearCampaign(number);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
	}
	
	@Override
	@Transactional
	public void startCampaign(String number) {
		try {
			// 活动设为结束
			campaignDao.updateState(number, CampaignStatus.IN_ACTIVE);
			
			// 活动中数量加1
			userContextService.increaseOneCamNumInActive(
					getUserNumberByEventNumber(number));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
	}
	
	@Override
	public String getUserNumberByEventNumber(String number) {
		return campaignDao.getUserNumberByEventNumber(number);
	}
}
