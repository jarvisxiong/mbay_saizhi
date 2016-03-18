package org.sz.mbay.promotion.service.impl;

import static org.springframework.util.Assert.notNull;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import org.sz.mbay.base.constant.SerialSeed;
import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.base.enums.CampaignStep;
import org.sz.mbay.base.exception.ServiceException;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.UtilService;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.camapgin.common.service.CampaignService;
import org.sz.mbay.channel.user.exception.MBAccountTradeException;
import org.sz.mbay.channel.user.exception.RedAccountTradeException;
import org.sz.mbay.channel.user.service.MBAccountService;
import org.sz.mbay.channel.user.service.RedTrafficAccountService;
import org.sz.mbay.channel.user.service.UserAccountService;
import org.sz.mbay.channel.user.service.UserContextService;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.common.util.MbayDateFormat;
import org.sz.mbay.common.util.MbayDateFormat.DatePattern;
import org.sz.mbay.fs.FSClient;
import org.sz.mbay.fs.FSClientFactory;
import org.sz.mbay.fs.FSClientFactory.ClientType;
import org.sz.mbay.operator.service.OperatorService;
import org.sz.mbay.promotion.bean.CampaignStatistics;
import org.sz.mbay.promotion.bean.PromotionCampaign;
import org.sz.mbay.promotion.bean.PromotionCampaignAdvanced;
import org.sz.mbay.promotion.bean.PromotionCampaignMbay;
import org.sz.mbay.promotion.bean.PromotionCampaignPackage;
import org.sz.mbay.promotion.bean.PromotionCampaignShare;
import org.sz.mbay.promotion.bean.PromotionCampaignTemplate;
import org.sz.mbay.promotion.bean.PromotionProductConfig;
import org.sz.mbay.promotion.config.PromotionResourceConfig;
import org.sz.mbay.promotion.dao.PromotionCampaignDao;
import org.sz.mbay.promotion.dao.PromotionCampaignMbayDao;
import org.sz.mbay.promotion.dao.PromotionCampaignPackageDao;
import org.sz.mbay.promotion.dao.PromotionCampaignShareDao;
import org.sz.mbay.promotion.dao.PromotionProductConfigDao;
import org.sz.mbay.promotion.error.PromotionCampaignError;
import org.sz.mbay.promotion.qo.CampaignForm;
import org.sz.mbay.promotion.service.PromotionCampaignService;
import org.sz.mbay.trafficred.enums.ProductType;

@Service
public class PromotionCampaignServiceImpl extends BaseServiceImpl
		implements PromotionCampaignService, CampaignService {
		
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PromotionCampaignServiceImpl.class);
			
	private FSClient fsClient = FSClientFactory.getClient(ClientType.FDFS);
	
	@Autowired
	PromotionCampaignDao campaignDao;
	@Autowired
	UtilService utilService;
	@Autowired
	UserAccountService userAccountService;
	@Autowired
	OperatorService operatroService;
	@Autowired
	UserContextService userContextService;
	@Autowired
	MBAccountService mbayService;
	@Autowired
	RedTrafficAccountService trafficService;
	@Autowired
	PromotionCampaignPackageDao campaignPackageDao;
	@Autowired
	PromotionCampaignMbayDao campaignMbayDao;
	@Autowired
	PromotionProductConfigDao configDao;
	@Autowired
	PromotionCampaignShareDao shareDao;
	
	@Override
	@Transactional
	public String addCampaign(PromotionCampaign campaign,
			List<PromotionProductConfig> configs, PromotionCampaignShare shareInfo) {
		try {
			campaign.setStep(CampaignStep.CREATED_CAMPAIGN);// 设置当前活动创建到第几步
			campaign.setState(CampaignStatus.NONE_FINISH);
			campaign.setCreatetime(DateTime.now());
			String number = MbayDateFormat.formatDateTime(DateTime.now(),
					DatePattern.yyyyMMdd);
			int nextid = utilService.getNextIndex(PromotionCampaign.class);
			campaign.setEventnumber(number + (SerialSeed.Event.REDEEM)
					+ (SerialSeed.CARDINAL_NUMBER + nextid));
			// 保存活动基本信息
			campaignDao.createBean(campaign);
			
			// 设置分享信息
			if(campaign.isShare()){
				shareInfo.setCampaignNumber(campaign.getEventnumber());
				shareDao.createBean(shareInfo);
			}
			
			// 保存流量产品
			for (PromotionCampaignPackage pack : campaign.getPackages()) {
				pack.setCampaignNumber(campaign.getEventnumber());
				campaignPackageDao.createBean(pack);
			}
			
			// 保存美贝产品
			for (PromotionCampaignMbay mbay : campaign.getMbays()) {
				mbay.setCampaignNumber(campaign.getEventnumber());
				campaignMbayDao.createBean(mbay);
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			LOGGER.error("创建促销活动异常", e.fillInStackTrace());
			throw new ServiceException(
					PromotionCampaignError.CAMPAIGN_ADD_ERROR);
		}
		
		String userNumber = campaign.getUsernumber();
		// 设置产品配置信息
		for (PromotionProductConfig config : configs) {
			config.setCampaignNumber(campaign.getEventnumber());
			configDao.createBean(config);
			switch (config.getProductType()) {
				case TRAFFIC_PACKAGE:
					try {
						trafficService.lockedTraffic(userNumber,
								config.getPoolSize());
					} catch (RedAccountTradeException e) {
						TransactionAspectSupport.currentTransactionStatus()
								.setRollbackOnly();
						throw new ServiceException(
								PromotionCampaignError.TRAFFIC_TRADE_ERROR);
					}
					
					break;
				case MBAY_PACKAGE:
					try {
						mbayService.lockedTraffic(userNumber,
								config.getPoolSize());
					} catch (MBAccountTradeException e) {
						TransactionAspectSupport.currentTransactionStatus()
								.setRollbackOnly();
						throw new ServiceException(
								PromotionCampaignError.MBAY_TRADE_ERROR);
					}
					break;
			}
		}
		
		return campaign.getEventnumber();
	}
	
	@Override
	public List<PromotionCampaign> findAllPromotionCampaign(CampaignForm event,
			PageInfo pageinfo) {
		try {
			return this.campaignDao.findAllPromotionCampaign(event, pageinfo);
		} catch (Exception e) {
			LOGGER.error("findAllPromotionCampaign 查询活动列表异常",
					e.fillInStackTrace());
		}
		return new ArrayList<PromotionCampaign>();
	}
	
	@Override
	public PromotionCampaign findPromotionCampaign(String eventnumber,
			String usernumber) {
		return this.campaignDao.findPromotionCampaign(eventnumber, usernumber);
	}
	
	@Override
	public boolean isExistCampaign(String eventNumber, String userNumber) {
		return this.campaignDao.getCountOfCampaign(eventNumber,
				userNumber) == 1;
	}
	
	@Override
	public String getCampaignBelongsUser(String eventNumber) {
		return this.campaignDao.findUserNumberByCampaignNumber(eventNumber);
	}
	
	@Override
	public PromotionCampaign findPromotionCampaign(String eventNumber) {
		return this.campaignDao.findPromotionCampaignBaseInfo(eventNumber);
	}
	
	@Override
	@Transactional
	public ExecuteResult cancelCampaign(String eventNumber, String userNumber) {
		CampaignStatus eventstate = this.campaignDao
				.getCampaignState(eventNumber, userNumber);
		if (eventstate.equals(CampaignStatus.CANCLED)) {
			return new ExecuteResult(false, "活动已取消！");
		}
		boolean result = dealCampaign(eventNumber, CampaignStatus.CANCLED);
		if (result) {
			return new ExecuteResult(true, "");
		}
		return new ExecuteResult(false, "活动取消失败");
	}
	
	@Override
	@Transactional
	public void setPromotionCampaignTemplate(
			PromotionCampaignTemplate template) {
		try {
			String eventNumber = template.getEventnumber();
			// 如果存在已有记录，则删除原有图片信息
			PromotionCampaignTemplate campaignTempate = findPromotionCampaignTemplate(
					eventNumber);
			// 判断是否需要删除原图片，如果需要则删除，不需要还装在原来的图片
			boolean updateFlag = false;
			if (template.getBackphoto() != null) {
				if (campaignTempate != null
						&& campaignTempate.getBackphoto() != null) {
					fsClient.deleteFile(campaignTempate.getBackphoto());
				}
				fsClient.uploadFile(template.getBackphoto());
				updateFlag = true;
			}
			if (template.getRedeemBackphoto() != null) {
				if (campaignTempate != null
						&& campaignTempate.getRedeemBackphoto() != null) {
					fsClient.deleteFile(campaignTempate.getRedeemBackphoto());
				}
				fsClient.uploadFile(template.getRedeemBackphoto());
				updateFlag = true;
			}
			if (template.getIntroduction() != null) {
				updateFlag = true;
			}
			if (!StringUtils.isEmpty(template.getGotText())) {
				updateFlag = true;
			}
			if (!StringUtils.isEmpty(template.getRedeemText())) {
				updateFlag = true;
			}
			if (!StringUtils.isEmpty(template.getIntroductionText())) {
				updateFlag = true;
			}
			if (campaignTempate != null) {
				if (updateFlag) {
					template.setId(campaignTempate.getId());
					this.campaignDao.updateBean(template);
				}
			} else {
				String url = PromotionResourceConfig.getPromotionGetURL();
				url = url.replace("{campaignNumber}",
						DigestUtils.pbeEncrypt(eventNumber));
				template.setEventurl(url);
				String redeemurl = PromotionResourceConfig
						.getPromotionRedeemURL();
				redeemurl = redeemurl.replace("{campaignNumber}",
						DigestUtils.pbeEncrypt(eventNumber));
				template.setRedeemurl(redeemurl);
				int nextno = this.utilService
						.getNextIndex(PromotionCampaignTemplate.class);
				template.setId(nextno);
				template.setEventnumber(eventNumber);
				this.campaignDao.createBean(template);
			}
			PromotionCampaign promotion = this.campaignDao
					.findPromotionCampaignBaseInfo(template.getEventnumber());
			if (promotion.getState().equals(CampaignStatus.NONE_FINISH)) {
				if (MbayDateFormat.nowInTimeRange(promotion.getStarttime(),
						promotion.getEndingtime()) == 0) {
					this.campaignDao.updateCampaignState(
							promotion.getEventnumber(),
							CampaignStatus.IN_ACTIVE);
					this.userContextService.increaseOneCamNumInActive(
							promotion.getUsernumber());
				}
				if (DateTime.now().isBefore(promotion.getStarttime())) {
					this.campaignDao.updateCampaignState(
							promotion.getEventnumber(),
							CampaignStatus.NOT_STARTED);
				}
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			LOGGER.error("setPromotionCampaignTemplate", e.fillInStackTrace());
			throw new ServiceException(
					PromotionCampaignError.CAMPAIGN_TEMPLATE_ERROR);
		}
	}
	
	@Override
	public PromotionCampaignTemplate findPromotionCampaignTemplate(
			String eventNumber) {
		return this.campaignDao.findPromotionCampaignTemplate(eventNumber);
	}
	
	@Override
	public PromotionCampaignTemplate findPromotionCampaignTemplate(
			int modelid) {
		try {
			return this.campaignDao.getBean(modelid,
					PromotionCampaignTemplate.class);
		} catch (Exception e) {
			LOGGER.error("findPromotionCampaignTemplate异常:",
					e.fillInStackTrace());
			return null;
		}
	}
	
	@Override
	public CampaignStep findCampaignStep(String eventnumber,
			String usernumber) {
		return this.campaignDao.findCampaignStep(eventnumber, usernumber);
	}
	
	@Override
	public ExecuteResult updateCampaignDate(String eventnumber,
			String eventstarttime, String eventendtime) {
		if (MbayDateFormat.stringToDate(eventstarttime)
				.isAfter(MbayDateFormat.stringToDate(eventendtime))) {
			return new ExecuteResult(false, "修改失败！");
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
		int ret = this.campaignDao.updateCampaignDate(eventnumber,
				eventstarttime, eventendtime);
		if (ret != 1) {
			return new ExecuteResult(false, "修改失败！");
		}
		return new ExecuteResult(true, "修改成功！");
		
	}
	
	@Override
	public ExecuteResult updateCampaignRate(String eventnumber, String rate) {
		int ret = this.campaignDao.updateCampaignRate(eventnumber,rate);
		if (ret != 1) {
			return new ExecuteResult(false, "修改失败！");
		}
		return new ExecuteResult(true, "修改成功！");
		
	}
	
	@Override
	public CampaignStatistics statisticCampaign(String usernumber) {
		return this.campaignDao.statisticCampaign(usernumber);
	}
	
	@Override
	public void increaseRcodeSentNum(String campaignNumber) {
		this.campaignDao.increaseRcodeSentNum(campaignNumber);
	}
	
	@Override
	public void increaseRcodeGotNum(String campaignNumber) {
		this.campaignDao.increaseRcodeGotNum(campaignNumber);
	}
	
	@Override
	public CampaignStatus findCampaignStatus(String campaignNumber,
			String userNumber) {
		return this.campaignDao.getCampaignState(campaignNumber, userNumber);
	}
	
	@Override
	public boolean updateCampaignVerificate(String campaignNumber,
			boolean verificate) {
		return (this.campaignDao.updateCampaignVerificate(campaignNumber,
				verificate) > 0);
	}
	
	@Override
	public boolean updateCampaignContinuable(String campaignNumber,
			boolean continuable) {
		return (this.campaignDao.updateCampaignContinuable(campaignNumber,
				continuable) > 0);
	}
	
	@Override
	public boolean updateCampaignShare(String campaignNumber,
			boolean share) {
		return (this.campaignDao.updateCampaignShare(campaignNumber,
				share) > 0);
	}
	
	@Override
	public PromotionCampaignAdvanced findCampaignAdvanced(
			String campaignNumber) {
		try {
			return this.campaignDao.findCampaignAdvanced(campaignNumber);
		} catch (Exception e) {
			LOGGER.error("WeChatCampaignService findCampaignTemplate Error",
					e.fillInStackTrace());
			return null;
		}
	}
	
	@Override
	@Transactional
	public ExecuteResult addCampaignAdvanced(
			PromotionCampaignAdvanced advanced) {
		advanced.setId(
				utilService.getNextIndex(PromotionCampaignAdvanced.class));
		try {
			this.campaignDao.createBean(advanced);
			PromotionCampaign promotion = this.campaignDao
					.findPromotionCampaignBaseInfo(
							advanced.getCampaignNumber());
			if (promotion.getState().equals(CampaignStatus.NONE_FINISH)) {
				if (MbayDateFormat.nowInTimeRange(promotion.getStarttime(),
						promotion.getEndingtime()) == 0) {
					this.campaignDao.updateCampaignState(
							promotion.getEventnumber(),
							CampaignStatus.IN_ACTIVE);
					this.userContextService.increaseOneCamNumInActive(
							promotion.getUsernumber());
				}
				if (DateTime.now().isBefore(promotion.getStarttime())) {
					this.campaignDao.updateCampaignState(
							promotion.getEventnumber(),
							CampaignStatus.NOT_STARTED);
				}
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
	public ExecuteResult updateCampaignAdvanced(
			PromotionCampaignAdvanced advanced) {
		int result = this.campaignDao.updateCampaignAdvanced(advanced);
		return result > 0 ? new ExecuteResult(true, "操作成功!")
				: new ExecuteResult(false, "操作失败!");
	}
	
	@Override
	public List<String> findAllCampaignNumberStartToday() {
		return campaignDao.findAllPromotionCampaignNumberStartToday();
	}
	
	@Override
	public List<String> findAllCampaignNumberOverToday() {
		return campaignDao.findAllPromotionCampaignNumberOverToday();
	}
	
	@Override
	public void startCampaign(String campaignNumber) {
		try {
			PromotionCampaign campaign = campaignDao
					.findPromotionCampaignBaseInfo(campaignNumber);
			// 修改活动状态
			campaignDao.updateCampaignState(campaignNumber,
					CampaignStatus.IN_ACTIVE);
			// 增加活动中数量
			userContextService
					.increaseOneCamNumInActive(campaign.getUsernumber());
		} catch (Exception e) {
			LOGGER.error("PromotionCampaignServiceImpl startCampaign Error",
					e.fillInStackTrace());
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
	}
	
	@Override
	@Transactional
	public void overCampaign(String campaignNumber) {
		dealCampaign(campaignNumber, CampaignStatus.OVER);
	}
	
	private boolean dealCampaign(String campaignNumber, CampaignStatus status) {
		try {
			PromotionCampaign campaign = campaignDao
					.findPromotionCampaignBaseInfo(campaignNumber);
			String usernumber = campaign.getUsernumber();
			// 解锁红包池
			PromotionProductConfig tcfg = configDao
					.findProductConfig(campaignNumber,
							ProductType.TRAFFIC_PACKAGE);
			if (tcfg != null) {
				trafficService.unlockedTraffic(usernumber,
						tcfg.getPoolRemain());
			}
			
			// 解锁美贝池
			PromotionProductConfig mcfg = configDao
					.findProductConfig(campaignNumber,
							ProductType.MBAY_PACKAGE);
			if (mcfg != null) {
				mbayService.unlockedTraffic(usernumber,
						mcfg.getPoolRemain());
			}
			// 修改活动状态为已结束
			campaignDao.updateCampaignState(campaignNumber, status);
			// 活动产品池及单日上限等置空
			campaignDao.clearConfig(campaignNumber);
			// 减少活动中数量
			userContextService
					.deductOneCamNumInActive(campaign.getUsernumber());
			return true;
		} catch (Exception e) {
			LOGGER.error("PromotionCampaignServiceImpl overCampaign Error",
					e.fillInStackTrace());
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return false;
		}
	}
	
	@Override
	@Transactional
	public void addTrafficProducts(String campaignNumber,
			List<PromotionCampaignPackage> redpackages,
			PromotionProductConfig config) {
		if (!configDao.isSelectedProduct(campaignNumber,
				ProductType.TRAFFIC_PACKAGE)) {
			config.setCampaignNumber(campaignNumber);
			config.setDailyRemain(config.getDailyLimit());
			config.setPoolRemain(config.getPoolSize());
			notNull(config, "property TrafficRedProductConfig can't be null");
			configDao.createBean(config);
		}
		// 保存流量产品
		for (PromotionCampaignPackage pack : redpackages) {
			campaignPackageDao.createBean(pack);
		}
	}
	
	@Override
	@Transactional
	public void addMbayProducts(String campaignNumber,
			List<PromotionCampaignMbay> mbayProducts,
			PromotionProductConfig config) {
		if (!configDao.isSelectedProduct(campaignNumber,
				ProductType.MBAY_PACKAGE)) {
			config.setCampaignNumber(campaignNumber);
			config.setDailyRemain(config.getDailyLimit());
			config.setPoolRemain(config.getPoolSize());
			notNull(config, "property TrafficRedProductConfig can't be null");
			configDao.createBean(config);
		}
		// 保存美贝产品
		for (PromotionCampaignMbay mbay : mbayProducts) {
			campaignMbayDao.createBean(mbay);
		}
	}
	
	@Override
	public boolean deleteCampaignProduct(String campaignNumber,
			ProductType ptype,
			long productId) {
		switch (ptype) {
			case TRAFFIC_PACKAGE:
				return campaignPackageDao.deleteCampaignTrafficProduct(
						campaignNumber, productId) == 1;
			case MBAY_PACKAGE:
				return campaignMbayDao.deleteCampaignMbayProduct(campaignNumber,
						productId) == 1;
		}
		return false;
	}

	@Override
	public PromotionCampaign findQueryInfomation(String campaignNumber) {
		return campaignDao.findQueryInfomation(campaignNumber);
	}
	
	@Override
	public ExecuteResult updateCampaignSendMB(String campaignNumber, int sendMB) {
		int ret = this.campaignDao.updateCampaignSendMB(campaignNumber,sendMB);
		if (ret != 1) {
			return new ExecuteResult(false, "修改失败！");
		}
		return new ExecuteResult(true, "修改成功！");
		
	}
}
