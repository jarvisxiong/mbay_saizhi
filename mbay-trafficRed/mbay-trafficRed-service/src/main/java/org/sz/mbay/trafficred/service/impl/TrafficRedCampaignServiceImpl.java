package org.sz.mbay.trafficred.service.impl;

import static org.springframework.util.Assert.notNull;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.sz.mbay.base.config.ResourceConfig;
import org.sz.mbay.base.constant.SerialSeed;
import org.sz.mbay.base.enums.CampaignStatus;
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
import org.sz.mbay.channel.user.service.UserContextService;
import org.sz.mbay.common.util.MbayDateFormat;
import org.sz.mbay.common.util.MbayDateFormat.DatePattern;
import org.sz.mbay.fs.FSClient;
import org.sz.mbay.fs.FSClientFactory;
import org.sz.mbay.fs.FSClientFactory.ClientType;
import org.sz.mbay.redis.common.aop.RedisUpdate;
import org.sz.mbay.sms.client.MbaySMS;
import org.sz.mbay.sms.template.enums.SMSType;
import org.sz.mbay.trafficred.bean.CampaignStatistics;
import org.sz.mbay.trafficred.bean.ThresholdWarnInfo;
import org.sz.mbay.trafficred.bean.TimeQuantum;
import org.sz.mbay.trafficred.bean.TrafficRedCampaign;
import org.sz.mbay.trafficred.bean.TrafficRedCampaignMbay;
import org.sz.mbay.trafficred.bean.TrafficRedCampaignPackage;
import org.sz.mbay.trafficred.bean.TrafficRedMbayGiftConfig;
import org.sz.mbay.trafficred.bean.TrafficRedProductConfig;
import org.sz.mbay.trafficred.bean.TrafficRedShareInfo;
import org.sz.mbay.trafficred.bean.TrafficRedTemplate;
import org.sz.mbay.trafficred.constant.error.TrafficRedError;
import org.sz.mbay.trafficred.dao.TrafficRedCampaignDao;
import org.sz.mbay.trafficred.dao.TrafficRedCampaignMbayDao;
import org.sz.mbay.trafficred.dao.TrafficRedCampaignPackageDao;
import org.sz.mbay.trafficred.dao.impl.TrafficRedCampaignMbayDaoImpl;
import org.sz.mbay.trafficred.dao.impl.TrafficRedCampaignPackageDaoImpl;
import org.sz.mbay.trafficred.enums.ProductType;
import org.sz.mbay.trafficred.service.TimeQuantumService;
import org.sz.mbay.trafficred.service.TrafficRedAdvancedService;
import org.sz.mbay.trafficred.service.TrafficRedCampaignService;
import org.sz.mbay.trafficred.service.TrafficRedMbayGiftConfigService;
import org.sz.mbay.trafficred.service.TrafficRedProductConfigService;
import org.sz.mbay.trafficred.service.TrafficRedShareInfoService;
import org.sz.mbay.trafficred.service.TrafficRedTmplService;

@Service("TrafficRedCampaignServiceImpl")
public class TrafficRedCampaignServiceImpl extends BaseServiceImpl
		implements TrafficRedCampaignService, CampaignService {
		
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// fastdfs文件管理
	private FSClient fsClient = FSClientFactory.getClient(ClientType.FDFS);
	
	@Autowired
	private TrafficRedCampaignDao campaignDao;
	@Autowired
	private UtilService utilService;
	@Autowired
	private TimeQuantumService quantumService;
	@Autowired
	private RedTrafficAccountService redTrafficAccountService;
	@Autowired
	private MBAccountService mbAccountService;
	@Autowired
	private TrafficRedCampaignPackageDao campaignPackageDao;
	@Autowired
	private TrafficRedCampaignMbayDao campaignMbayDao;
	@Autowired
	private TrafficRedTmplService tmplService;
	@Autowired
	private TrafficRedShareInfoService shareInfoService;
	@Autowired
	private TrafficRedMbayGiftConfigService mbayGiftConfigService;
	@Autowired
	private TrafficRedAdvancedService advncedService;
	@Autowired
	private TrafficRedProductConfigService productConfigService;
	@Autowired
	private UserContextService userContextService;
	
	@Transactional
	@Override
	public void create(TrafficRedCampaign campaign,
			List<TrafficRedProductConfig> productConfigs) {
		final TrafficRedTemplate template = campaign.getTemplate();
		final TrafficRedShareInfo shareInfo = campaign.getShareInfo();
		final TrafficRedMbayGiftConfig mbayGiftConfig = campaign
				.getMbayGiftConfig();
				
		try {
			// 设置分享图片
			String shareImg = fsClient.uploadFile(shareInfo.getShareImgFile());
			shareInfo.setShareImg(StringUtils.trimToEmpty(shareImg));
			
			// 设置广告图片1
			String adImg1 = fsClient.uploadFile(template.getAdImg1File());
			template.setAdImg1(StringUtils.trimToEmpty(adImg1));
			
			// 设置广告图片2
			String adImg2 = fsClient.uploadFile(template.getAdImg2File());
			template.setAdImg2(StringUtils.trimToEmpty(adImg2));
			
			// 设置摇一摇首页图
			String skIndex = fsClient
					.uploadFile(template.getShakeIndexImgFile());
			template.setShakeIndexImg(StringUtils.trimToEmpty(skIndex));
			
			// 设置摇一摇抽奖图
			String skUI = fsClient.uploadFile(template.getShakeUIImgFile());
			template.setShakeUIImg(StringUtils.trimToEmpty(skUI));
			
			// 设置送人背景图片
			String giftBgImg = fsClient
					.uploadFile(mbayGiftConfig.getGiftBgImgFile());
			mbayGiftConfig.setBgImg(StringUtils.trimToEmpty(giftBgImg));
			
			// 设置送人分享图片
			String giftShareImg = fsClient
					.uploadFile(mbayGiftConfig.getGiftShareImgFile());
			mbayGiftConfig.setShareImg(StringUtils.trimToEmpty(giftShareImg));
			
			// 生成活动编号
			DateTime now = DateTime.now();
			String number = MbayDateFormat.formatDateTime(now,
					DatePattern.yyyyMMdd);
			int nextid = utilService.getNextIndex(campaign.getClass());
			String campaignNumber = number + (SerialSeed.Event.TRAFFIC_RED)
					+ (SerialSeed.CARDINAL_NUMBER + nextid);
			campaign.setNumber(campaignNumber);
			campaign.setDateCreated(DateTime.now());
			
			// 保存基本信息
			campaignDao.createSelective(campaign);
			
			// 如果已经是活动中，活动中数量加1
			if (campaign.getStatus() == CampaignStatus.IN_ACTIVE) {
				userContextService.increaseOneCamNumInActive(
						campaign.getUserNumber());
			}
			
			// 保存时间段
			if (campaign.getTimeQuantums() != null) {
				for (TimeQuantum quantum : campaign.getTimeQuantums()) {
					quantum.setTrafficRedCampaignId(campaign.getId());
					quantumService.create(quantum);
				}
			}
			
			// 保存用户自定义编辑模板
			template.setCampaignId(campaign.getId());
			tmplService.create(template);
			
			// 保存分享信息
			shareInfo.setCampaignId(campaign.getId());
			shareInfoService.create(shareInfo);
			
			// 保存送人配置信息
			mbayGiftConfig.setCampaignId(campaign.getId());
			mbayGiftConfigService.create(mbayGiftConfig);
			
			// 更新：活动模板id/分享信息id/送人信息id
			TrafficRedCampaign upcmp = new TrafficRedCampaign();
			upcmp.setId(campaign.getId());
			upcmp.setTemplate(template);
			upcmp.setShareInfo(shareInfo);
			upcmp.setMbayGiftConfig(mbayGiftConfig);
			campaignDao.updateByIdSelective(upcmp);
			
			// 保存流量产品
			for (TrafficRedCampaignPackage pack : campaign.getPackages()) {
				pack.setCampaignId(campaign.getId());
				campaignPackageDao.create(pack);
			}
			
			// 保存美贝产品
			for (TrafficRedCampaignMbay mbay : campaign.getMbays()) {
				mbay.setCampaignId(campaign.getId());
				campaignMbayDao.create(mbay);
			}
			
			// 创建开发者模式配置信息
			advncedService.createAdvanceConfig(campaign.getId());
		} catch (Exception e) {
			logger.error("create:创建活动失败:", e.fillInStackTrace());
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			deleteUploadFiles(template, shareInfo);
			throw new ServiceException(TrafficRedError.CAMPAIGN_CREATE_ERROR);
		}
		
		// 扣除红包大池
		String userNumber = campaign.getUserNumber();
		// 设置产品配置信息
		for (TrafficRedProductConfig config : productConfigs) {
			config.setCampaignId(campaign.getId());
			productConfigService.create(config);
			switch (config.getProductType()) {
				case TRAFFIC_PACKAGE:
					try {
						redTrafficAccountService.lockedTraffic(userNumber,
								config.getPoolSize());
					} catch (RedAccountTradeException e) {
						TransactionAspectSupport.currentTransactionStatus()
								.setRollbackOnly();
						throw new ServiceException(
								TrafficRedError.RED_TRAFFIC_TRADE_ERROR);
					}
					
					break;
				case MBAY_PACKAGE:
					try {
						mbAccountService.lockedTraffic(userNumber,
								config.getPoolSize());
					} catch (MBAccountTradeException e) {
						TransactionAspectSupport.currentTransactionStatus()
								.setRollbackOnly();
						throw new ServiceException(
								TrafficRedError.MBAY_TRAFFIC_TRADE_ERROR);
					}
					
					break;
			}
		}
	}
	
	/**
	 * 删除上传图片
	 * 
	 * @param template
	 */
	private void deleteUploadFiles(final TrafficRedTemplate template,
			final TrafficRedShareInfo shareInfo) {
		if (template != null) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					fsClient.deleteFile(template.getAdImg1());
					fsClient.deleteFile(template.getAdImg2());
					fsClient.deleteFile(shareInfo.getShareImg());
				}
			}).start();
		}
	}
	
	@Override
	public List<TrafficRedCampaign> findAllByPage(PageInfo pageInfo,
			String userNumber) {
		return campaignDao.findList(userNumber, pageInfo, "ByPage");
	}
	
	@Override
	public TrafficRedCampaign findCampaignByNumber(String campaignNumber) {
		TrafficRedCampaign campaign = campaignDao
				.findCampaignByNumber(campaignNumber);
		if (campaign != null) {
			campaign.setTimeQuantums(
					quantumService.findByCampaignId(campaign.getId()));
		}
		
		return campaign;
	}
	
	@Override
	public TrafficRedCampaign updateByIdSelective(TrafficRedCampaign campaign) {
		return campaignDao.updateByIdSelective(campaign) > 0 ? campaign : null;
	}
	
	@Override
	public TrafficRedCampaign find(Long id) {
		return campaignDao.selectById(id);
	}
	
	@Override
	public boolean deleteCampaignProduct(long campaignId, ProductType ptype,
			long productId) {
		switch (ptype) {
			case TRAFFIC_PACKAGE:
				return campaignPackageDao.deleteCampaignTrafficProduct(
						campaignId, productId) == 1;
			case MBAY_PACKAGE:
				return campaignMbayDao.deleteCampaignMbayProduct(campaignId,
						productId) == 1;
		}
		return false;
	}
	
	@Override
	public boolean isExistCampaign(long campaignId) {
		return this.campaignDao.isExistCampaign(campaignId);
	}
	
	@Override
	public boolean updateProductRatio(long campaignId, ProductType ptype,
			long productId, int ratio) {
		switch (ptype) {
			case TRAFFIC_PACKAGE:
				return this.campaignPackageDao.updateTrafficProductRatio(
						campaignId,
						productId, ratio) == 1;
			case MBAY_PACKAGE:
				return this.campaignMbayDao.updateMbayProductRatio(campaignId,
						productId, ratio) == 1;
		}
		return false;
	}
	
	@RedisUpdate(
			clazz = TrafficRedCampaignPackageDaoImpl.class,
			value = {
					"${0}findValidMin${0}",
					"${0}findByCampaignIdAndOperatorType${0}",
					"${0}findClassifyPackages${0}" })
	@Override
	@Transactional
	public void addTrafficProducts(long campaignId,
			List<TrafficRedCampaignPackage> redpackages,
			TrafficRedProductConfig config) {
		if (!productConfigService.isSelectedProduct(campaignId,
				ProductType.TRAFFIC_PACKAGE)) {
			config.setCampaignId(campaignId);
			config.setDailyRemain(config.getDailyLimit());
			config.setPoolRemain(config.getPoolSize());
			notNull(config, "property TrafficRedProductConfig can't be null");
			productConfigService.create(config);
		}
		// 保存流量产品
		for (TrafficRedCampaignPackage pack : redpackages) {
			campaignPackageDao.create(pack);
		}
	}
	
	@RedisUpdate(
			clazz = TrafficRedCampaignMbayDaoImpl.class,
			value = {
					"${0}findValidMin${0}",
					"${0}findByCampaignId${0}",
					"${0}countByCampaignId${0}" })
	@Override
	@Transactional
	public void addMbayProducts(long campaignId,
			List<TrafficRedCampaignMbay> mbayProducts,
			TrafficRedProductConfig config) {
		if (!productConfigService.isSelectedProduct(campaignId,
				ProductType.MBAY_PACKAGE)) {
			config.setCampaignId(campaignId);
			config.setDailyRemain(config.getDailyLimit());
			config.setPoolRemain(config.getPoolSize());
			notNull(config, "property TrafficRedProductConfig can't be null");
			productConfigService.create(config);
		}
		// 保存美贝产品
		for (TrafficRedCampaignMbay mbay : mbayProducts) {
			campaignMbayDao.create(mbay);
		}
	}
	
	@Override
	@Transactional
	public void updateCampaignInfo(TrafficRedCampaign campaign) {
		final TrafficRedShareInfo shareInfo = campaign.getShareInfo();
		final TrafficRedMbayGiftConfig mbayGiftConfig = campaign
				.getMbayGiftConfig();
				
		try {
			if (mbayGiftConfig.getGiftShareImgFile() != null
					&& !mbayGiftConfig.getGiftShareImgFile().isEmpty()) {
				fsClient.deleteFile(mbayGiftConfig.getShareImg());
				String giftShareImg = fsClient
						.uploadFile(mbayGiftConfig.getGiftShareImgFile());
				mbayGiftConfig.setShareImg(giftShareImg);
			}
			if (mbayGiftConfig.getGiftBgImgFile() != null
					&& !mbayGiftConfig.getGiftBgImgFile().isEmpty()) {
				fsClient.deleteFile(mbayGiftConfig.getBgImg());
				String giftBgImg = fsClient
						.uploadFile(mbayGiftConfig.getGiftBgImgFile());
				mbayGiftConfig.setBgImg(giftBgImg);
			}
			if (shareInfo.getShareImgFile() != null
					&& !shareInfo.getShareImgFile().isEmpty()) {
				fsClient.deleteFile(shareInfo.getShareImg());
				String shareImg = fsClient
						.uploadFile(shareInfo.getShareImgFile());
				campaign.getShareInfo().setShareImg(shareImg);
			}
			
			campaignDao.updateByIdSelective(campaign);
			quantumService.deleteAllByCampaignId(campaign.getId());
			for (TimeQuantum quantum : campaign.getTimeQuantums()) {
				quantum.setTrafficRedCampaignId(campaign.getId());
				quantumService.create(quantum);
			}
			
			shareInfoService.updateByIdSelective(shareInfo);
			mbayGiftConfigService.updateByIdSelective(mbayGiftConfig);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			logger.error("修改流量红包活动异常", e.fillInStackTrace());
		}
	}
	
	@Override
	public CampaignStatistics statisticCampaign(String userNumber) {
		return this.campaignDao.statisticCampaign(userNumber);
	}
	
	@Override
	public String findCampaignNumberById(long campaignId) {
		return this.campaignDao.findCampaignNumberById(campaignId);
	}
	
	@Override
	public String findCampaignBelongUser(long campaignId) {
		return this.campaignDao.findCampaignBelongUser(campaignId);
	}
	
	@Override
	public double findProductHitRate(long campaignId) {
		return this.campaignDao.findProductHitRate(campaignId);
	}
	
	/**
	 * 发送产品池余量低于阀值时的警告短信
	 * 
	 * @param campaignId
	 * @param productType
	 */
	public void sendThresholdWarningSms(long campaignId,
			ProductType productType) {
		// 尊敬的美贝直通车用户，您的活动:【{活动名称}】{产品类型}产品余额已低于{阀值}{产品类型}，
		// 请您及时登录平台{平台网址}进行续充，以免影响您的活动正常使用。客服电话：
		ThresholdWarnInfo info = productConfigService
				.findThresholdWarnInfo(campaignId, productType);
		String pcdomain = ResourceConfig.getWebDomain();
		String smsProperties = info.getCampaignName() + ","
				+ productType.getValue() + "," + info.getThreshold() + ","
				+ productType.getValue() + "," + pcdomain;
		MbaySMS.sendSMS(SMSType.TR_THRESHOLD_WARNING, info.getWarningMobile(),
				smsProperties);
	}
	
	@Override
	public void checkThresholdAndSendWarning(long campaignId,
			ProductType productType) {
		new Thread(() -> {
			if (productConfigService.isProductPoolRemainLessThanThreshold(
					campaignId,
					productType)) {
				// 修改时加锁，同时只能有一个人修改
				boolean changeResult = productConfigService
						.changeProductThresholdWarned(campaignId, productType);
				// 修改失败表示已发送过短信
				if (changeResult) {
					this.sendThresholdWarningSms(campaignId, productType);
				}
			}
		}).start();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ExecuteResult cancel(long campaignId) {
		if (!this.isExistCampaign(campaignId)) {
			return new ExecuteResult(false, "找不到对应的活动！");
		}
		try {
			// 活动设为取消
			TrafficRedCampaign cmp = new TrafficRedCampaign();
			cmp.setId(campaignId);
			cmp.setStatus(CampaignStatus.CANCLED);
			updateByIdSelective(cmp);
			
			// 活动清理
			clearCampaign(campaignId);
			return new ExecuteResult(true, "SUCCESS", "活动取消成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return new ExecuteResult(false, "活动取消失败！");
		}
	}
	
	/*
	 * 活动结束或取消后的清理
	 */
	private void clearCampaign(long id) throws Exception {
		// 查询用户编号
		String usernumber = findCampaignBelongUser(id);
		
		// 解锁红包池
		TrafficRedProductConfig tcfg = productConfigService
				.findProductConfig(id, ProductType.TRAFFIC_PACKAGE);
		if (tcfg != null) {
			redTrafficAccountService.unlockedTraffic(usernumber,
					tcfg.getPoolRemain());
		}
		
		// 解锁美贝池
		TrafficRedProductConfig mcfg = productConfigService
				.findProductConfig(id, ProductType.MBAY_PACKAGE);
		if (mcfg != null) {
			mbAccountService.unlockedTraffic(usernumber,
					mcfg.getPoolRemain());
		}
		
		// 活动产品池及单日上限等置空
		productConfigService.clearConfig(id);
		
		// 活动中数量减1
		userContextService.deductOneCamNumInActive(usernumber);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void overCampaign(String number) {
		Long id = getIdByNumber(number);
		if (id != null && id > 0) {
			try {
				// 活动设为结束
				TrafficRedCampaign cmp = new TrafficRedCampaign();
				cmp.setId(id);
				cmp.setStatus(CampaignStatus.OVER);
				updateByIdSelective(cmp);
				
				// 活动清理
				clearCampaign(id);
			} catch (Exception e) {
				logger.error(e.getMessage());
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
			}
		}
	}
	
	@Override
	@Transactional
	public void startCampaign(String number) {
		Long id = getIdByNumber(number);
		if (id != null && id > 0) {
			try {
				// 活动设为活动中
				TrafficRedCampaign cmp = new TrafficRedCampaign();
				cmp.setId(id);
				cmp.setStatus(CampaignStatus.IN_ACTIVE);
				updateByIdSelective(cmp);
				
				// 活动中数量加1
				String usernumber = findCampaignBelongUser(id);
				userContextService.increaseOneCamNumInActive(usernumber);
			} catch (Exception e) {
				logger.error(e.getMessage());
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
			}
		}
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
	public Long getIdByNumber(String number) {
		return campaignDao.getIdByNumber(number);
	}
	
}
