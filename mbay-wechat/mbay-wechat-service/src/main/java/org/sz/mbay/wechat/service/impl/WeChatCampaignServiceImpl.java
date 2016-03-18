package org.sz.mbay.wechat.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import org.sz.mbay.base.area.Area;
import org.sz.mbay.base.constant.SerialSeed;
import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.base.enums.CampaignStep;
import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.base.enums.SendWay;
import org.sz.mbay.base.enums.StrategyState;
import org.sz.mbay.base.exception.ServiceException;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.UtilService;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.base.wrap.MsgType;
import org.sz.mbay.base.wrap.RedeemResponse;
import org.sz.mbay.camapgin.common.service.CampaignService;
import org.sz.mbay.channel.user.enums.TradeType;
import org.sz.mbay.channel.user.service.UserAccountService;
import org.sz.mbay.channel.user.service.UserContextService;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.common.util.MbayDateFormat;
import org.sz.mbay.common.util.MbayDateFormat.DatePattern;
import org.sz.mbay.fs.FSClient;
import org.sz.mbay.fs.FSClientFactory;
import org.sz.mbay.fs.FSClientFactory.ClientType;
import org.sz.mbay.hcode.MbayHcode;
import org.sz.mbay.hcode.bean.HcodeInfo;
import org.sz.mbay.operator.dao.OperatorDao;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.sms.client.MbaySMS;
import org.sz.mbay.sms.template.enums.SMSType;
import org.sz.mbay.trafficorder.bean.TrafficRechargeInfo;
import org.sz.mbay.trafficorder.enums.TrafficOrderType;
import org.sz.mbay.trafficorder.service.TrafficOrderService;
import org.sz.mbay.trafficrecharge.service.TrafficRechargeService;
import org.sz.mbay.wechat.bean.WeChatCampaign;
import org.sz.mbay.wechat.bean.WeChatCampaignAdvanced;
import org.sz.mbay.wechat.bean.WeChatCampaignAdvertise;
import org.sz.mbay.wechat.bean.WeChatCampaignDefaultTemplate;
import org.sz.mbay.wechat.bean.WeChatCampaignStatistics;
import org.sz.mbay.wechat.bean.WeChatCampaignStrategy;
import org.sz.mbay.wechat.bean.WeChatCampaignTemplate;
import org.sz.mbay.wechat.config.WeChatResourceConfig;
import org.sz.mbay.wechat.dao.WeChatCampaignDao;
import org.sz.mbay.wechat.error.WeChatCampaignError;
import org.sz.mbay.wechat.qo.WeChatCampaignForm;
import org.sz.mbay.wechat.service.WeChatCampaignService;

@Service
public class WeChatCampaignServiceImpl extends BaseServiceImpl implements
		WeChatCampaignService,CampaignService {
		
	private static final Logger LOGGER = LoggerFactory
			.getLogger(WeChatCampaignServiceImpl.class);
			
	@Autowired
	UtilService utilService;
	@Autowired
	WeChatCampaignDao campaignDao;
	@Autowired
	OperatorDao operatroDao;
	@Autowired
	UserAccountService userAccountService;
	@Autowired
	UserContextService userContextService;
	@Autowired
	TrafficRechargeService trafficService;
	@Autowired
	TrafficOrderService orderService;
	
	@Override
	@Transactional
	public String addCampaign(WeChatCampaign campaign) {
		int[] packages = new int[campaign.getStratetgylist().size()];
		for (int i = 0; i < packages.length; i++) {
			packages[i] = campaign.getStratetgylist().get(i)
					.getTrafficPackage().getId();
		}
		// 查询流量包中最大额度流量宝
		double maxpackageprice = this.operatroDao.getMaxPackagePrice(packages);
		// 查询账户可用余额
		double availbleamount = this.userAccountService
				.getAvailableAmount(campaign.getUsernumber());
		double lockMbay = maxpackageprice * campaign.getQuantity();
		if (lockMbay > availbleamount) {// 账户余额不足与创建此活动
			throw new ServiceException(
					WeChatCampaignError.CAMPAIGN_ADD_BALANCE_NOT_ENOUGH);
		}
		try {
			campaign.setState(CampaignStatus.NONE_FINISH);
			DateTime now = DateTime.now();
			String number = MbayDateFormat.formatDateTime(now,
					DatePattern.yyyyMMdd);
			int nextid = utilService.getNextIndex(WeChatCampaign.class);
			campaign.setEventnumber(number + (SerialSeed.Event.WECHAT)
					+ (SerialSeed.CARDINAL_NUMBER + nextid));
			if (campaign.getSendway().equals(SendWay.MBAY)) {// 送美贝
				
			}
			campaign.setAmount(lockMbay);
			campaign.setStep(CampaignStep.CREATED_CAMPAIGN);
			campaign = campaignDao.createBean(campaign);
			for (WeChatCampaignStrategy strategy : campaign
					.getStratetgylist()) {
				String strategyNumber = MbayDateFormat.formatDateTime(now,
						DatePattern.yyyyMMdd);
				int nextStrategyid = utilService
						.getNextIndex(WeChatCampaignStrategy.class);
				strategy.setStrategynumber(strategyNumber
						+ (SerialSeed.EventStrategy.WECHAT_STRATEGY)
						+ (SerialSeed.CARDINAL_NUMBER + nextStrategyid));
				strategy.setEventnumber(campaign.getEventnumber());
				strategy.setSendednum(0);
				strategy.setState(StrategyState.ENABLE);
				this.campaignDao.createCampaignStrategy(strategy);
			}
			// 增加账户锁定美贝
			this.userAccountService.lockedMbay(campaign.getUsernumber(), lockMbay);
			return campaign.getEventnumber();
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOGGER.error("addCampaign 添加活动异常", e.fillInStackTrace());
			throw new ServiceException(WeChatCampaignError.CAMPAIGN_ADD_ERROR);
		}
	}
	
	@Override
	@Transactional
	public ExecuteResult addCampaignAdvanced(WeChatCampaignAdvanced advanced) {
		advanced.setId(utilService.getNextIndex(WeChatCampaignAdvanced.class));
		try {
			this.campaignDao.createBean(advanced);
			WeChatCampaign campaign = this.campaignDao
					.findWeChatCampaignBaseInfo(advanced.getCampaignNumber());
			if (campaign.getState().equals(CampaignStatus.NONE_FINISH)) {
				if (MbayDateFormat.nowInTimeRange(campaign.getStarttime(),
						campaign.getEndingtime()) == 0) {
					this.campaignDao.updateCampaignState(advanced.getCampaignNumber(),
							CampaignStatus.IN_ACTIVE);
					this.userContextService
							.increaseOneCamNumInActive(campaign
									.getUsernumber());
				}
				if (DateTime.now().isBefore(campaign.getStarttime())) {
					this.campaignDao.updateCampaignState(advanced.getCampaignNumber(),
							CampaignStatus.NOT_STARTED);
				}
			}
			//this.campaignDao.completeCampaignCreate(advanced.getCampaignNumber());
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
			WeChatCampaignAdvanced advanced) {
		int result = this.campaignDao.updateCampaignAdvanced(advanced);
		return result > 0 ? new ExecuteResult(true, "操作成功!")
				: new ExecuteResult(false, "操作失败!");
	}
	
	@Override
	public List<WeChatCampaign> findAllWeChatCampaign(WeChatCampaignForm form,
			PageInfo pageinfo) {
		try {
			return this.campaignDao.findAllWeChatCampaign(form, pageinfo);
		} catch (Exception e) {
			LOGGER.error("findAllWeChatCampaign 查询活动列表异常",
					e.fillInStackTrace());
		}
		return new ArrayList<WeChatCampaign>();
	}
	
	@Override
	public WeChatCampaign findWeChatCampaign(String eventnumber,
			String usernumber) {
		try {
			return this.campaignDao.findWeChatCampaignByNumber(eventnumber,
					usernumber);
		} catch (Exception e) {
			LOGGER.error("WeChatCampaignService findWeChatCampaign Error",
					e.fillInStackTrace());
		}
		return null;
	}
	
	@Override
	public WeChatCampaignTemplate findCampaignTemplate(int id) {
		try {
			return this.campaignDao.getBean(id, WeChatCampaignTemplate.class);
		} catch (Exception e) {
			LOGGER.error("WeChatCampaignService findCampaignTemplate Error",
					e.fillInStackTrace());
			return null;
		}
	}
	
	@Override
	public WeChatCampaignAdvanced findCampaignAdvanced(String campaignNumber) {
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
	public boolean updateCampaignTemplate(WeChatCampaignTemplate template) {
		try {
			this.campaignDao.updateBean(template);
			WeChatCampaignTemplate bean = this.campaignDao.getBean(
					template.getId(), WeChatCampaignTemplate.class);
			if (bean != null && !StringUtils.isEmpty(bean.getEventnumber())) {
				WeChatCampaign campaign = this.campaignDao
						.findWeChatCampaignBaseInfo(template.getEventnumber());
				if (campaign.getState().equals(CampaignStatus.NONE_FINISH)) {
					if (MbayDateFormat.nowInTimeRange(campaign.getStarttime(),
							campaign.getEndingtime()) == 0) {
						this.campaignDao.updateCampaignState(template.getEventnumber(),
								CampaignStatus.IN_ACTIVE);
						this.userContextService
								.increaseOneCamNumInActive(campaign
										.getUsernumber());
					}
					if (DateTime.now().isBefore(campaign.getStarttime())) {
						this.campaignDao.updateCampaignState(template.getEventnumber(),
								CampaignStatus.NOT_STARTED);
					}
				}
				//this.campaignDao.completeCampaignCreate(bean.getEventnumber());
			}
			return true;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			LOGGER.error("WeChatCampaignService updateCampaignTemplate Error",
					e.fillInStackTrace());
		}
		return false;
	}
	
	@Override
	@Transactional
	public ExecuteResult cancelCampaign(String eventnumber, String usernumber) {
		CampaignStatus status = this.campaignDao.getCampaignState(eventnumber,
				usernumber);
		if (status.equals(CampaignStatus.CANCLED)) {
			return new ExecuteResult(false, "活动已取消！");
		}
		boolean result = dealCampaign(eventnumber, CampaignStatus.CANCLED);
		//int reuslt = this.campaignDao.cancelCampaign(eventnumber, usernumber);
		if (result) {
			return new ExecuteResult(true, "");
		}
		return new ExecuteResult(false, "活动取消失败");
		
	}
	
	@Override
	@Transactional
	public void setCampaignTemplateWithCustom(String eventnumber,
			WeChatCampaignTemplate template) {
		FSClient fsClient = FSClientFactory.getClient(ClientType.FDFS);
		try {
			// 如果存在已有记录，则删除原有图片信息
			WeChatCampaignTemplate oldTempate = findCampaignTemplate(
					eventnumber);
					
			// 判断是否需要删除原图片，如果需要则删除，不需要还装在原来的图片
			if (template.getShareImage() != null) {
				if (oldTempate != null && oldTempate.getShareImage() != null) {
					fsClient.deleteFile(oldTempate.getShareImage());
				}
				fsClient.uploadFile(template.getShareImage());
			}
			if (template.getBackphoto() != null) {
				if (oldTempate != null && oldTempate.getBackphoto() != null) {
					Boolean result = this.campaignDao.isDefault(oldTempate
							.getBackphoto());
					if (!result) {
						fsClient.deleteFile(oldTempate.getBackphoto());
					}
				}
				fsClient.uploadFile(template.getBackphoto());
			}
			if (template.getSuccessPhoto() != null) {
				if (oldTempate != null
						&& oldTempate.getSuccessPhoto() != null) {
					fsClient.deleteFile(oldTempate.getSuccessPhoto());
				}
				fsClient.uploadFile(template.getSuccessPhoto());
			}
			if (oldTempate == null) {
				int nextno = this.utilService
						.getNextIndex(WeChatCampaignTemplate.class);
				template.setId(nextno);
				template.setStatus(EnableState.ENABLED);
				String temurl = WeChatResourceConfig.getCampaignTemplateURL();
				temurl = temurl.replace("{model}",
						DigestUtils.pbeEncrypt(nextno + ""));
				template.setEventurl(temurl);
				this.campaignDao.createBean(template);
			} else {
				template.setId(oldTempate.getId());
				this.campaignDao.updateBean(template);
			}
			WeChatCampaign campaign = this.campaignDao
					.findWeChatCampaignBaseInfo(eventnumber);
			if (campaign.getState().equals(CampaignStatus.NONE_FINISH)) {
				if (MbayDateFormat.nowInTimeRange(campaign.getStarttime(),
						campaign.getEndingtime()) == 0) {
					this.campaignDao.updateCampaignState(eventnumber,
							CampaignStatus.IN_ACTIVE);
					this.userContextService
							.increaseOneCamNumInActive(campaign
									.getUsernumber());
				}
				if (DateTime.now().isBefore(campaign.getStarttime())) {
					this.campaignDao.updateCampaignState(eventnumber,
							CampaignStatus.NOT_STARTED);
				}
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			LOGGER.error(
					"WeChatCampaignService setCampaignTemplateWithCustom Error",
					e.fillInStackTrace());
			throw new ServiceException(
					WeChatCampaignError.CAMPAIGN_TEMPLATE_CREATE_ERROR);
		}
	}
	
	@Override
	public CampaignStep findCampaignStep(String eventnumber, String usernuber) {
		return this.campaignDao.findCampaignStep(eventnumber, usernuber);
	}
	
	@Override
	public int getCampaignSendQuantity(String eventnumber) {
		return this.campaignDao.getCampaignSendQuantity(eventnumber);
	}
	
	@Override
	public WeChatCampaignTemplate findCampaignTemplate(String eventnumber) {
		return this.campaignDao.findCampaignTemplate(eventnumber);
	}
	
	@Override
	public int findCampaignSendedQuantity(String eventnumber) {
		return this.campaignDao.findCampaignSendedQuantity(eventnumber);
	}
	
	@Override
	public boolean updateCampaignRepeatEnable(String eventnumber,
			boolean repeat_enable) {
		return this.campaignDao.updateCampaignRepeatEnable(eventnumber,
				repeat_enable) > 0;
	}
	
	@Override
	public ExecuteResult updateCampaignDate(String eventnumber,
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
		int ret = this.campaignDao.updateCampaignDate(eventnumber,
				eventstarttime, eventendtime);
		if (ret != 1) {
			new ExecuteResult(false, "修改失败！");
		}
		return new ExecuteResult(true, "修改成功！");
		
	}
	
	@Override
	public boolean updateCampaignContinuable(String eventnumber,
			boolean continuable) {
		return this.campaignDao.updateCampaignContinuable(eventnumber,
				continuable) > 0;
	}
	
	@Override
	public boolean updateCampaignDirectEnable(String eventnumber,
			boolean directEnable) {
		return this.campaignDao.updateCampaignDirectEnable(eventnumber,
				directEnable) > 0;
	}
	
	@Override
	public List<WeChatCampaignDefaultTemplate> findWeChatCampaignDefaultTemplateList() {
		return this.campaignDao.findWeChatCampaignDefaultTemplateList();
	}
	
	@Override
	public WeChatCampaignDefaultTemplate findWeChatCampaignDefaultTemplateById(
			long id) {
		try {
			return this.campaignDao.getBean(id,
					WeChatCampaignDefaultTemplate.class);
		} catch (Exception e) {
			LOGGER.error(
					"WeChatCampaignServiceImpl findEventDefaultTemplateById Error",
					e.fillInStackTrace());
			return null;
		}
	}
	
	@Override
	public List<WeChatCampaignAdvertise> findWeChatCampaignAdvertiseList() {
		return this.campaignDao.findWeChatCampaignAdvertiseList();
	}
	
	@Override
	public WeChatCampaignStatistics statisticWeChatCampaign(String usernumber) {
		return this.campaignDao.statisticWeChatCampaign(usernumber);
	}
	
	@Override
	public boolean isExistingCampaign(String eventNumber, String userNumber) {
		return this.campaignDao.isExistingCampaign(eventNumber, userNumber);
	}
	
	@Override
	public ExecuteResult isFeasibleForToComplete(String eventNumber) {
		// 1.判断活动是为此用户下
		// 2.判断活动是否为未开始
		// 3.判断当前日期是否为活动结束日期之前
		// 4.判断当前账户余额是否充足
		return null;
	}
	
	@Override
	public CampaignStatus findCampaignStatus(String campaignNumber) {
		return this.campaignDao.findCampaignStatus(campaignNumber);
	}
	
	@Override
	public boolean findCampaignDirectEnable(String campaignNumber) {
		return this.campaignDao.findCampaignDirectEnable(campaignNumber);
	}
	
	@Override
	public RedeemResponse rechargeTraffic(String campaignNumber,
			String mobile) {
		WeChatCampaign campaign = campaignDao
				.findWeChatCampaignBaseInfo(campaignNumber);
		if (CampaignStatus.CANCLED.equals(campaign.getState())) {
			return new RedeemResponse("CAMPAIGN_CANCLED", "此活动已取消！",
					MsgType.TEXT);
		}
		int intime = MbayDateFormat.nowInTimeRange(
				campaign.getStarttime(), campaign.getEndingtime());
		if (intime != 0) {
			String message = intime < 0 ? "活动还未开始！" : "活动已结束！";
			return new RedeemResponse("NOT_IN_TIME", message, MsgType.TEXT);
		}
		HcodeInfo hcode = MbayHcode.getHcodeInfo(mobile);
		if (hcode == null) {
			return RedeemResponse.NOTFOUND_MOBILE;
		}
		SendWay sendway = campaign.getSendway();
		// 送美贝
		if (SendWay.MBAY.equals(sendway)) {
		
		}
		// 送流量
		if (SendWay.TRAFFIC.equals(sendway)) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("活动赠送类型为：流量");
			}
			ExecuteResult judgeresult = campaignDao.judgeMobileReceiveEnable(
					campaignNumber, mobile);
			if (!judgeresult.isSuccess()) {
				return new RedeemResponse("NONE_SUPPORT_MOBILE",
						judgeresult.getMessage(), MsgType.TEXT);
			}
			Area area = Area.valueOf(hcode.getProvcode());
			OperatorType operator = OperatorType.valueOf(hcode.getOperator());
			WeChatCampaignStrategy strategy = campaignDao.findEventStrategyTrafficInfo(campaignNumber, area,operator);
			TransactionStatus status = this.startTransaction();
			String rechargeOrderNumber = "";
			try {
				TrafficRechargeInfo info = new TrafficRechargeInfo();
				info.setMobile(mobile);
				info.setRechargeType(TrafficOrderType.WECHAT_CAMPAIGN);
				info.setRelationNumber(campaignNumber);
				info.setTrafficPackageNumber(
						strategy.getTrafficPackage().getId());
				info.setUserNumber(campaign.getUsernumber());
				rechargeOrderNumber = orderService.create(info);
				// 账户支出
				userAccountService.expenditure(
						campaign.getUsernumber(), TradeType.WECHAT_CAMPAIGN,
						rechargeOrderNumber, strategy.getTrafficPackage().getMbayprice(),
						"微信伴侣营销");
				int quantity = campaign.getQuantity();
				int sendednum = campaign.getSendednum();
				// 当预计总量大于已送出数量的时候才会去减少锁定美贝
				if (quantity > sendednum) {
					// 减少账户锁定美贝
					userAccountService.unLockedMbay(
							campaign.getUsernumber(),
							strategy.getTrafficPackage()
									.getMbayprice());
				}
				// 增加策略送出数量 并增加活动送出数量，减少活动锁定美贝，增加活动送出美贝
				campaignDao.weChatCampaignSend(strategy.getStrategynumber());
				this.commitTransaction(status);
				//是否发送短信
				if(campaign.isSendsms()){
					String params = campaign.getEventname() + "," + strategy.getTrafficPackage().getTraffic();
					MbaySMS.sendSMS(SMSType.ACTIVITY, mobile, params);
				}
			} catch (Exception e) {
				LOGGER.error("参与活动，赠送流量异常：", e.fillInStackTrace());
				this.rollbackTransaction(status);
				return new RedeemResponse("CHANNEL_DEAL_EXCEPTION",
						"流量下发失败，请返回重试！", MsgType.TEXT);
			}
			this.trafficService.recharge(rechargeOrderNumber);
			return new RedeemResponse(true, "TRAFFIC_RECHARGEING",
					"流量已下发", MsgType.TEXT);
		}
		return RedeemResponse.TRAFFIC_RECHARGEING;
	}
	
	@Override
	public RedeemResponse rechargeTrafficByWallet(String campaignNumber,
			String mobile) {
		return null;
		// WeChatCampaign marketingevent = campaignDao
		// .findWeChatEventSendInfo(campaignNumber);
		// if (!CampaignStatus.IN_ACTIVE.equals(marketingevent.getState())) {
		// return new RedeemResponse("EVENT_CANCLED", "活动暂无法参与,请稍后再试!",
		// MsgType.TEXT);
		// }
		// int intime = MbayDateFormat.nowInTimaRange(
		// marketingevent.getStarttime(), marketingevent.getEndingtime());
		// if (intime != 0) {
		// String message = intime < 0 ? "活动还未开始！" : "活动已过期！";
		// return new RedeemResponse("NOT_IN_TIME", message, MsgType.TEXT);
		// }
		// // 超出不可重复
		// if (!marketingevent.isContinuable()) {
		// if (marketingevent.getSendednum() >= marketingevent.getQuantity()) {
		// return RedeemResponse.TRAFFIC_EXCHANGE_OVER;
		// }
		// }
		// SendWay sendway = marketingevent.getSendway();
		// // 送美贝
		// if (SendWay.MBAY.equals(sendway)) {
		// // TODO:微信营销送美贝
		// return null;
		// }
		// // 送流量
		// if (SendWay.TRAFFIC.equals(sendway)) {
		// // 检测手机号是否已注册美贝钱包
		// if (!mbayWalletUserService.isUserExist(mobile)) {
		// return new RedeemResponse("TEL_ISNOT_REGISTED",
		// "手机号尚未注册美贝钱包账户！", MsgType.TEXT);
		// }
		//
		// // 商户扣费
		// ExecuteResult judgeresult = this.eventtrafficDao
		// .judgeMobileReceiveEnable(eventnumber, mobile);
		// if (!judgeresult.isSuccess()) {
		// return new RedeemResponse("NONE_SUPPORT_MOBILE",
		// judgeresult.getMessage(), MsgType.TEXT);
		// }
		// HcodeInfo hcode=HCodeSupport.getInstance().getHcodeInfo(mobile);
		// if (hcode == null) {
		// return RedeemResponse.NOTFOUND_MOBILE;
		// }
		// Area area = Area.valueOf(hcode.getProvcode());
		// OperatorType operator = OperatorType.valueOf(hcode.getOperator());
		// WeChatCampaignStrategy strategy = this.eventtrafficDao
		// .findEventStrategyTrafficInfo(eventnumber, area, operator);
		// TrafficPackage trafficPackage = strategy.getTrafficPackage();
		// // 判断是否是全国流量包,如果是,则去看本地流量包是否存在
		// Boolean flag = ((trafficPackage.getOperator().getArea().value) == 0)
		// ? true
		// : false;
		// if (flag) {
		// // 判断本地是否存在流量包,如果存在则使用本地流量包，否则还是走全国流量包
		// TrafficPackage traffic_package = operatorService
		// .getBestTrafficPackage(hcode,
		// trafficPackage.getTraffic());
		// if (traffic_package != null) {
		// strategy.setTrafficPackage(traffic_package);
		// }
		// }
		// strategy.setEventnumber(eventnumber);
		// RedeemResponse response = createWalletRechargeOrder(mobile,
		// marketingevent.getUsernumber(), strategy);
		// if (!response.isSuccess()) {
		// return response;
		// }
		//
		// // 获取商户名称
		// String userNumber = marketingevent.getUsernumber();
		// String enterpriseName = certificateAuthService
		// .getEnterpriseName(userNumber);
		//
		// HttpSession session = ChannelContext.getHttpSession();
		// String linkUrl =
		// session.getAttribute("TEMPLATE_REQUEST_URL").toString();
		// session.removeAttribute("TEMPLATE_REQUEST_URL");
		//
		// // 产生券包
		// Ticket bt = new Ticket();
		// bt.setOwnerNumber(mbayWalletUserService.getUserNumberByTelephone(mobile));
		// bt.setTicketType(TicketType.WECHAT_CAMPAIGN);
		// WechatTicket ticket = new WechatTicket();
		// ticket.setTicket(bt);
		// ticket.setEventNumber(eventnumber);
		// ticket.setOrderNumber(response.getContent());
		// ticket.setLink(linkUrl);
		// ticket.setEnterpriseName(enterpriseName);
		// ticket.setUsed(false);
		// wechatTicketService.createTicket(ticket);
		// }
		// return RedeemResponse.TRAFFIC_RECHARGEING;
	}

	@Override
	public List<String> findAllCampaignNumberStartToday() {
		return campaignDao.findAllWeChatCampaignNumberStartToday();
	}

	@Override
	public List<String> findAllCampaignNumberOverToday() {
		return campaignDao.findAllWeChatCampaignNumberOverToday();
	}

	@Override
	@Transactional
	public void startCampaign(String campaignNumber) {
		try{
			WeChatCampaign campaign = campaignDao.findWeChatCampaignBaseInfo(campaignNumber);
			//修改活动状态
			campaignDao.updateCampaignState(campaignNumber, CampaignStatus.IN_ACTIVE);
			//增加活动中数量
			userContextService.increaseOneCamNumInActive(campaign.getUsernumber());
		}catch(Exception e){
			LOGGER.error("WeChatCampaignServiceImpl startCampaign Error", e.fillInStackTrace());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}

	@Override
	@Transactional
	public void overCampaign(String campaignNumber) {
		dealCampaign(campaignNumber, CampaignStatus.OVER);
	}
	
	private boolean dealCampaign(String campaignNumber, CampaignStatus status){
		try{
			WeChatCampaign campaign = campaignDao.findWeChatCampaignBaseInfo(campaignNumber);
			double amount = campaign.getAmount();
			//修改活动状态
			campaignDao.updateCampaignState(campaignNumber, status);
			//解除活动锁定美贝
			campaignDao.decreaseCampaignLockedMbay(campaignNumber);
			//解除池中锁定
			userAccountService.unLockedMbay(campaign.getUsernumber(), amount);
			//减少活动中数量
			userContextService.deductOneCamNumInActive(campaign.getUsernumber());
			return true;
		} catch (Exception e) {
			LOGGER.error("WeChatCampaignServiceImpl dealCampaign Error", e.fillInStackTrace());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
	}
}
