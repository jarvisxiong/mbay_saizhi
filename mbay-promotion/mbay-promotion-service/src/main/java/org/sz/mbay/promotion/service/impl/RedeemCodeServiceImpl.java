package org.sz.mbay.promotion.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.math.RandomUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import org.sz.mbay.base.config.ResourceConfig;
import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.UtilService;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.base.wrap.MsgType;
import org.sz.mbay.base.wrap.RedeemResponse;
import org.sz.mbay.channel.user.service.MBAccountService;
import org.sz.mbay.channel.user.service.RedTrafficAccountService;
import org.sz.mbay.channel.user.service.UserAccountService;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.common.util.MbayDateFormat;
import org.sz.mbay.common.util.MbayDateFormat.DateFormatter;
import org.sz.mbay.hcode.MbayHcode;
import org.sz.mbay.hcode.bean.HcodeInfo;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.promotion.bean.PromotionCampaign;
import org.sz.mbay.promotion.bean.PromotionCampaignMbay;
import org.sz.mbay.promotion.bean.PromotionCampaignPackage;
import org.sz.mbay.promotion.bean.PromotionProductConfig;
import org.sz.mbay.promotion.bean.RedeemCode;
import org.sz.mbay.promotion.dao.PromotionCampaignMbayDao;
import org.sz.mbay.promotion.dao.PromotionCampaignPackageDao;
import org.sz.mbay.promotion.dao.RedeemCodeDao;
import org.sz.mbay.promotion.enums.CodeEndType;
import org.sz.mbay.promotion.enums.CodeType;
import org.sz.mbay.promotion.enums.RedeemCodeMethod;
import org.sz.mbay.promotion.enums.RedeemCodeStu;
import org.sz.mbay.promotion.qo.RedeemCodeForm;
import org.sz.mbay.promotion.response.PromotionCampaignMbayResponse;
import org.sz.mbay.promotion.response.PromotionCampaignPackageResponse;
import org.sz.mbay.promotion.service.PromotionCampaignService;
import org.sz.mbay.promotion.service.PromotionProductConfigService;
import org.sz.mbay.promotion.service.RedeemCodeService;
import org.sz.mbay.remote.interfaces.wallet.RIMBAccountUtil;
import org.sz.mbay.remote.interfaces.wallet.base.RIResponse;
import org.sz.mbay.sms.client.MbaySMS;
import org.sz.mbay.sms.template.enums.SMSType;
import org.sz.mbay.trafficorder.bean.TrafficRechargeInfo;
import org.sz.mbay.trafficorder.enums.TrafficOrderType;
import org.sz.mbay.trafficorder.service.TrafficOrderService;
import org.sz.mbay.trafficrecharge.service.TrafficRechargeService;
import org.sz.mbay.trafficred.bean.ThresholdWarnInfo;
import org.sz.mbay.trafficred.bean.TrafficRedProductConfig;
import org.sz.mbay.trafficred.enums.ProductType;

@Service
public class RedeemCodeServiceImpl extends BaseServiceImpl
		implements RedeemCodeService {
		
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RedeemCodeServiceImpl.class);
			
	@Autowired
	RedeemCodeDao redeemCodeDao;
	@Autowired
	PromotionCampaignService promotionService;
	@Autowired
	UtilService utilService;
	@Autowired
	UserAccountService assetsService;
	@Autowired
	TrafficRechargeService trafficService;
	@Autowired
	TrafficOrderService orderService;
	@Autowired
	MBAccountService mbayService;
	@Autowired
	RedTrafficAccountService redTrafficService;
	@Autowired
	PromotionCampaignPackageDao campaignPackageDao;
	@Autowired
	PromotionCampaignMbayDao campaignMbayDao;
	@Autowired
	PromotionProductConfigService configService;
	
	@Override
	@Transactional
	public ExecuteResult generateRedeemCode(String eventnumber, String method) {
		PromotionCampaign campaign = this.promotionService
				.findPromotionCampaign(eventnumber);
		if (campaign == null) {
			LOGGER.error(
					"generateRedeemCode ERROR,can't find PromotionEvent by eventnumber={}",
					eventnumber);
			return new ExecuteResult(false, "活动不存在！");
		}
		CampaignStatus campaginStatus = campaign.getState();
		if (campaginStatus.equals(CampaignStatus.IN_ACTIVE)) {// 避免活动已过期，但活动状态未及时更改
			if (DateTime.now().isAfter(campaign.getEndingtime())) {
				return new ExecuteResult(false, "您来晚了，活动已经结束！");
			}
		}
		if (!campaginStatus.equals(CampaignStatus.IN_ACTIVE)) {
			if (campaginStatus.equals(CampaignStatus.NOT_STARTED)) {// 未开始，避免说动状态未及时更改的情况。
				int isrange = MbayDateFormat.nowInTimeRange(
						campaign.getStarttime(), campaign.getEndingtime());
				if (isrange != 0) {
					return new ExecuteResult(false,
							"活动" + (isrange > 0 ? "已经结束！" : "还没有开始！"));
				}
			}
			return new ExecuteResult(false, "您来晚了，活动已经结束！");
		}
		
		// 区别是再领一次还是原始流程,method为空:原始流程,method不为空:再领一次
		if(StringUtils.isEmpty(method)){
			// 判断是否已经批量导入过兑换码
			RedeemCodeForm codeForm = new RedeemCodeForm();
			codeForm.setEventNumber(eventnumber);
			codeForm.setCodeStatus(RedeemCodeStu.UN_REDEEM);
			codeForm.setStarttime(campaign.getStarttime());
			codeForm.setEndtime(campaign.getEndingtime());
			List<RedeemCode> codeList = redeemCodeDao
					.findAllRedeemCodeByEventNumber(codeForm, null);
			// 如果存在则直接返回
			if (codeList != null && codeList.size() > 0) {
				RedeemCode rcode = codeList.get(0);
				try {
					this.redeemCodeDao.updateCodeStatus(eventnumber,
							rcode.getCode(), RedeemCodeStu.GOT);
					this.redeemCodeDao.updateCodeGetTime(eventnumber,
							rcode.getCode());
					this.promotionService.increaseRcodeGotNum(eventnumber);
					return new ExecuteResult(true, rcode.getCode());
				} catch (Exception e) {
					TransactionAspectSupport.currentTransactionStatus()
							.setRollbackOnly();
					LOGGER.error("generateRedeemCode updateStatus ERROR",
							e.fillInStackTrace());
					return new ExecuteResult(false, "兑换码领取失败，请稍后重试！");
				}
			}
		}
		
		// 查看是否需要超出发放
		if (campaign.isContinuable()) {
			// 抽奖概率
			double trafficRate = campaign.getTrafficRate();
			// 否则系统自动生成
			String userNumber = campaign.getUsernumber();
			String code = generateCode(eventnumber, userNumber);// 兑换码
			RedeemCode rCode = new RedeemCode();
			rCode.setCodeStatus(RedeemCodeStu.GOT);// 已领取
			rCode.setCodeType(dealRate(trafficRate));// 兑换码类型
			rCode.setCreatetime(DateTime.now());
			rCode.setEventnumber(eventnumber);// 活动编号
			// rCode.setEventType(event.getEventType());// 活动类型
			DateTime expire = DateTime.now()
					.plusDays(campaign.getValidityday());
			expire = DateTime.parse(expire.toString("yyyy-MM-dd") + " 23:59:59",
					DateFormatter.timeFormat);
			rCode.setExpiretime(expire);
			rCode.setStarttime(campaign.getStarttime());// 兑换开始时间
			rCode.setCode(code);
			rCode.setMethod(RedeemCodeMethod.AUTO);// 自动生成
			rCode.setGettime(DateTime.now());// 领取时间
			rCode.setSend(false); // 默认没有赠送过mb
			// 考虑到锁定美贝和解锁美贝的麻烦，以及兑换码过期为兑需解锁美贝，故此
			try {
				redeemCodeDao.createBean(rCode);
				this.promotionService.increaseRcodeGotNum(eventnumber);
				return new ExecuteResult(true, code);
			} catch (Exception e) {
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
				LOGGER.error("generateRedeemCode createRedeemCode ERROR",
						e.fillInStackTrace());
				return new ExecuteResult(false, "兑换码领取失败，请稍后重试！");
			}
		} else {
			return new ExecuteResult(false, "您来晚了，兑换码已发完毕！");
		}
	}
	
	@Override
	@Transactional
	public ExecuteResult generateRedeemCodeByMunual(String campaignNumber,
			List<RedeemCodeForm> list, CodeEndType endType) {
		PromotionCampaign campaign = this.promotionService
				.findPromotionCampaign(campaignNumber);
		if (campaign == null) {
			LOGGER.error("can't find PromotionEvent by campaignNumber={}",
					campaignNumber);
			return new ExecuteResult(false, "活动不存在！");
		}
		CampaignStatus campaginStatus = campaign.getState();
		if (campaginStatus.equals(CampaignStatus.IN_ACTIVE)) {// 避免活动已过期，但活动状态未及时更改
			if (DateTime.now().isAfter(campaign.getEndingtime())) {
				return new ExecuteResult(false, "您来晚了，活动已经结束！");
			}
		}
		if (!campaginStatus.equals(CampaignStatus.IN_ACTIVE)) {
			if (campaginStatus.equals(CampaignStatus.NOT_STARTED)) {// 未开始，避免说动状态未及时更改的情况。
				int isrange = MbayDateFormat.nowInTimeRange(
						campaign.getStarttime(), campaign.getEndingtime());
				if (isrange != 0) {
					return new ExecuteResult(false,
							"活动" + (isrange > 0 ? "已经结束！" : "还没有开始！"));
				}
			}
			return new ExecuteResult(false, "您来晚了，活动已经结束！");
		}
		
		try {
			double trafficRate = campaign.getTrafficRate();
			for (RedeemCodeForm m : list) {
				String mobile = m.getMobile();
				// 如果有手机号则判断手机号是否正确
				if (!StringUtils.isEmpty(mobile)) {
					HcodeInfo codeinfo = MbayHcode.getHcodeInfo(mobile);
					if (codeinfo == null) {
						LOGGER.error("根据手机号码{}未找到对应的手机号码信息", mobile);
						return new ExecuteResult(false, "未找到对应的手机号码信息");
					}
				}
				String code = m.getCode();
				String value = m.getTime();
				String time = "";
				DateTimeFormatter formatter = null;
				// 直接截止时间
				if (endType.equals(CodeEndType.DATE)) {
					time = value + " 23:59:59";
					formatter = DateFormatter.slashTimeFormat;
					// 当前时间加上截止小时
				} else if (endType.equals(CodeEndType.HOUR)) {
					time = DateTime.now().plusHours(Integer.valueOf(value))
							.toString(DateFormatter.timeFormat);
					formatter = DateFormatter.timeFormat;
				}
				RedeemCode rCode = new RedeemCode();
				rCode.setCodeStatus(RedeemCodeStu.UN_REDEEM);// 未兑换
				rCode.setCodeType(dealRate(trafficRate));// 兑换码类型
				rCode.setCreatetime(DateTime.now());
				rCode.setEventnumber(campaign.getEventnumber());// 活动编号
				rCode.setExpiretime(
						DateTime.parse(time, formatter));
				rCode.setStarttime(campaign.getStarttime());// 兑换开始时间
				rCode.setCode(code);
				rCode.setMethod(RedeemCodeMethod.MANUAL);// 手动生成
				rCode.setBindMobile(mobile);
				rCode.setSend(false); // 默认没有赠送过mb
				redeemCodeDao.createBean(rCode);
			}
			return ExecuteResult.successExecute;
		} catch (Exception e) {
			LOGGER.error("generateRedeemCodeByMunual 执行失败",
					e.fillInStackTrace());
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return ExecuteResult.failExecute;
		}
	}
	
	/**
	 * 暂时生成兑换码的方式为crc32加密
	 * 
	 * (已不用)MD5加密（用户编号和兑换码id）后截取8-24下标位，共计16位 生成兑换码
	 * 
	 * @param eventnumber
	 * @return
	 */
	private String generateCode(String eventnumber, String usernumber) {
		long nextnumber = utilService.getNextIndex(RedeemCode.class);
		String str = eventnumber + usernumber + nextnumber;
		String r = DigestUtils.crc32(str);
		return r.toUpperCase();
	}
	
	@Override
	public List<RedeemCode> findAllRedeemCodeByEventNumber(
			RedeemCodeForm codeForm, PageInfo pageInfo) {
		try {
			return this.redeemCodeDao.findAllRedeemCodeByEventNumber(codeForm,
					pageInfo);
		} catch (Exception e) {
			LOGGER.error("findAllRedeemCodeByEventNumber ERROR",
					e.fillInStackTrace());
		}
		return new ArrayList<RedeemCode>();
	}
	
	@Override
	public RedeemCode findRedeemCodeByCodeNumber(String code,
			String campaignNumber) {
		return this.redeemCodeDao.findRedeemCodeByCodeNumber(code,
				campaignNumber);
	}
	
	@Override
	@Transactional
	public RedeemResponse codeRedemMbay(String campaignNumber,
			List<RedeemCodeForm> list) {
		// 查询活动基本信息，包含活动时间，活动状态
		PromotionCampaign event = this.promotionService
				.findPromotionCampaign(campaignNumber);
		if (event.getState().equals(CampaignStatus.CANCLED)) {
			return new RedeemResponse(false, "EVENT_HAS_CANCELD", "活动已取消",
					MsgType.TEXT);
		}
		PromotionCampaignMbayResponse mbayResponse = null;
		if (list != null && list.size() > 0) {
			LOGGER.info("当前活动对应活动编号{},用户编号{}", campaignNumber,
					event.getUsernumber());
			try {
				for (RedeemCodeForm form : list) {
					String mobile = form.getMobile();
					HcodeInfo codeinfo = MbayHcode.getHcodeInfo(mobile);
					if (codeinfo == null) {
						LOGGER.error("根据手机号码{}未找到对应的手机号码信息", mobile);
						throw new Exception();
					}
					
					// 产品配置
					PromotionProductConfig config = configService
							.findProductConfig(
									campaignNumber, ProductType.MBAY_PACKAGE);
									
					// 随机抽取MB产品
					mbayResponse = selectMbayProduct(campaignNumber, config);
					if (!mbayResponse.isSuccess()) {
						return new RedeemResponse(false,
								"PROMOTION_CAMPAIGN_MBAY_ERROR",
								mbayResponse.getContent(),
								MsgType.TEXT);
					}
					int mbay = mbayResponse.getMbay();
					
					// MB池剩余流量减少
					boolean suc = configService.reduceProductPoolRemain(
							campaignNumber, ProductType.MBAY_PACKAGE, mbay);
					if (!suc) {
						LOGGER.error("美贝流量池剩余流量减少失败,活动编号:{}", campaignNumber);
						throw new Exception();
					}
					
					// MB池单日剩余流量减少
					boolean suc2 = configService.reduceProductDailyRemain(
							campaignNumber, ProductType.MBAY_PACKAGE, mbay);
					if (!suc2) {
						LOGGER.error("美贝池单日剩余流量减少失败,活动编号:{}", campaignNumber);
						throw new Exception();
					}
					
					// 解除锁定MB
					mbayService.unlockedTraffic(event.getUsernumber(), mbay);
					LOGGER.info("解除锁定MB成功,活动编号:{}", campaignNumber);
					// 账户支出MB
					mbayService.expenditure(mbay,
							event.getUsernumber(),
							org.sz.mbay.channel.user.enums.TradeType.PROMOTION_CAMPAIGN,
							campaignNumber, "促销神器送MB");
					LOGGER.info("账户支出MB成功,活动编号:{}", campaignNumber);
							
					// 钱包增加MB
					RIResponse resp = RIMBAccountUtil.requestUserEnterOfAccount(
							mobile, mbay, "PROMOTION_MBAY", campaignNumber,
							null);
					if (!resp.isStatus()) {
						LOGGER.error("钱包增加MB失败,活动编号:{}", campaignNumber);
						throw new Exception();
					}
					String serialNumber = resp.getData()
							.getString("serialNumber");
					LOGGER.info("钱包增加MB成功,活动编号:{},流水号:{}", campaignNumber, serialNumber);
							
					this.promotionService.increaseRcodeSentNum(campaignNumber);
					LOGGER.info("修改活动送出数量成功,活动编号:{}", campaignNumber);
					redeemCodeDao.updateCodeRedeemTime(campaignNumber,
							form.getCode());
					LOGGER.info("修改活动领取时间成功,活动编号:{}", campaignNumber);
					redeemCodeDao.setOrderNumAndMobileAndStatusAndMbayPrice(
							form.getCode(),
							serialNumber, mobile, mbay,
							campaignNumber);
					LOGGER.info("设置兑换码兑换单号和手机号和状态和消耗美贝成功,活动编号:{}", campaignNumber);
					// 是否发送告警阀值短信(已发送过则不再进行发送)
					if (!config.isThresholdWarned()) {
						LOGGER.info("确定发送阀值短信,活动编号:{}", campaignNumber);
						sendThresholdWarningSms(campaignNumber,
								ProductType.MBAY_PACKAGE);
					}
					LOGGER.info("codeRedeemMbay方法执行成功,活动编号:{}", campaignNumber);
				}
			} catch (Exception e) {
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
				return new RedeemResponse(false, "SYSTEM_ERROR", "系统繁忙,请稍后重试",
						MsgType.TEXT);
			}
		}
		return new RedeemResponse(true, RedeemResponse.STR_RECHARGEING,
				String.valueOf(mbayResponse.getMbay()), CodeType.MBAY.name());
	}
	
	@Override
	@Transactional
	public RedeemResponse codeRedemTraffic(RedeemCode rcode, String mobile) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(mobile + "流量兑换，兑换码信息{}", "code:" + rcode.getCode()
					+ "campaignNumber:" + rcode.getEventnumber());
		}
		String userNumber = this.promotionService
				.getCampaignBelongsUser(rcode.getEventnumber());// 活动所属商户
		// 查询活动基本信息，包含活动状态
		PromotionCampaign event = this.promotionService
				.findPromotionCampaign(rcode.getEventnumber());
		if (event.getState().equals(CampaignStatus.CANCLED)) {
			return new RedeemResponse(false, "EVENT_HAS_CANCELD", "活动已取消",
					MsgType.TEXT);
		}
		HcodeInfo codeinfo = MbayHcode.getHcodeInfo(mobile);
		if (codeinfo == null) {
			LOGGER.error("根据手机号码{}未找到对应的手机号码信息", mobile);
			return RedeemResponse.NOTFOUND_MOBILE;
		}
		TransactionStatus status = this.startTransaction();
		String rechargeOrderNumber = "";
		double ttraffic = 0;
		try {
			// 产品配置
			PromotionProductConfig config = configService.findProductConfig(
					rcode.getEventnumber(), ProductType.TRAFFIC_PACKAGE);
			// 随机抽取流量产品
			PromotionCampaignPackageResponse packageResponse = selectTrafficProduct(
					rcode.getEventnumber(), codeinfo, config);
			if (!packageResponse.isSuccess()) {
				/*return new RedeemResponse(false,
						"PROMOTION_CAMPAIGN_PACKAGE_ERROR",
						packageResponse.getContent(),
						MsgType.TEXT);*/
				//如果出现问题则全部走送MB流程
				List<RedeemCodeForm> list = new ArrayList<RedeemCodeForm>();
				RedeemCodeForm form = new RedeemCodeForm();
				form.setCode(rcode.getCode());
				form.setMobile(mobile);
				list.add(form);
				return codeRedemMbay(rcode.getEventnumber(), list);
			}
			PromotionCampaignPackage campaignPackage = packageResponse
					.getCampaignPackage();
			TrafficRechargeInfo info = new TrafficRechargeInfo();
			info.setUserNumber(userNumber);
			info.setRelationNumber(rcode.getEventnumber());
			info.setMobile(mobile);
			info.setTrafficPackageNumber(
					campaignPackage.getTrafficPackage().getPackageId());
			info.setRechargeType(TrafficOrderType.PROMOTION_CAMPAIGN);
			rechargeOrderNumber = orderService.create(info);
			
			double mbayprice = campaignPackage.getTrafficPackage()
					.getTrafficPackage().getMbayprice();
			int traffic = campaignPackage.getTrafficPackage().getTraffic();
			ttraffic = traffic;
			// 流量池剩余流量减少
			boolean suc = configService.reduceProductPoolRemain(
					rcode.getEventnumber(), ProductType.TRAFFIC_PACKAGE,
					mbayprice);
			if (!suc) {
				LOGGER.error("流量池剩余流量减少失败,活动编号:{}", rcode.getEventnumber());
				throw new Exception();
			}
			
			// 流量池单日剩余流量减少
			boolean suc2 = configService.reduceProductDailyRemain(
					rcode.getEventnumber(), ProductType.TRAFFIC_PACKAGE,
					mbayprice);
			if (!suc2) {
				LOGGER.error("美贝池单日剩余流量减少失败,活动编号:{}", rcode.getEventnumber());
				throw new Exception();
			}
			
			// 解除锁定美贝
			redTrafficService.unlockedTraffic(userNumber, mbayprice);
			// 账户支出美贝
			redTrafficService.expenditure(mbayprice, userNumber,
					org.sz.mbay.channel.user.enums.TradeType.PROMOTION_CAMPAIGN,
					rcode.getEventnumber(),
					"促销神器送流量");
			this.promotionService.increaseRcodeSentNum(rcode.getEventnumber());
			redeemCodeDao.updateCodeRedeemTime(rcode.getEventnumber(),
					rcode.getCode());
			redeemCodeDao.setOrderNumAndMobileAndStatusAndMbayPrice(
					rcode.getCode(),
					rechargeOrderNumber, mobile, mbayprice,
					rcode.getEventnumber());
			this.commitTransaction(status);
			// 是否发送短信
			if (event.isSendsms()) {
				String params = event.getEventname() + ","
						+ traffic;
				MbaySMS.sendSMS(SMSType.ACTIVITY, mobile, params);
			}
			// 是否发送告警阀值短信(已发送过则不再进行发送)
			if (!config.isThresholdWarned()) {
				sendThresholdWarningSms(rcode.getEventnumber(),
						ProductType.TRAFFIC_PACKAGE);
			}
		} catch (Exception e) {
			LOGGER.error("流量兑换，赠送流量异常：", e.fillInStackTrace());
			this.rollbackTransaction(status);
			return new RedeemResponse("SYSTEM_ERROR", "系统繁忙，请稍后重试！",
					MsgType.TEXT);
		}
		this.trafficService.recharge(rechargeOrderNumber);
		return new RedeemResponse(true, RedeemResponse.STR_RECHARGEING,
				String.valueOf(ttraffic),
				MsgType.TEXT);
	}
	
	@Override
	public boolean updateCodeVerificate(String code, String campaignNumber,
			String verificateCode) {
		return redeemCodeDao.updateCodeVerificate(code, campaignNumber,
				verificateCode) > 0;
	}
	
	@Override
	public boolean isExistVerificateCode(String eventnumber,
			String verificateCode) {
		return redeemCodeDao.getCountOfVerificateCode(eventnumber,
				verificateCode) > 0;
	}
	
	private CodeType dealRate(double rate) {
		if (rate == 0) {
			return CodeType.MBAY;
		} else if (rate == 100) {
			return CodeType.TRAFFIC;
		} else {
			Random r = new Random();
			double d = r.nextDouble() * 100;
			if (d >= 0 && d <= rate) {
				return CodeType.TRAFFIC;
			} else {
				return CodeType.MBAY;
			}
		}
	}
	
	/**
	 * 随机抽取美贝产品
	 * 
	 * @param campaignNumber
	 * @param config
	 * @return
	 * @throws Exception
	 */
	private PromotionCampaignMbayResponse selectMbayProduct(
			String campaignNumber,
			PromotionProductConfig config) throws Exception {
		// 查询配置信息
		PromotionCampaignMbay minMbay = campaignMbayDao
				.findValidMin(campaignNumber);
				
		// 未设置美贝产品
		if (minMbay == null || minMbay.getRatio() <= 0) {
			LOGGER.error("活动编号:{},说明:{}", campaignNumber, "未设置美贝产品");
			return new PromotionCampaignMbayResponse(0, "未设置美贝产品");
		}
		
		// 美贝池耗尽
		if (config.getPoolRemain() < minMbay.getMbay().getMbay()) {
			LOGGER.error("活动编号:{},说明:{}", campaignNumber, "美贝池耗尽");
			return new PromotionCampaignMbayResponse(0, "流量全部都领完了");
		}
		
		// 流量是否达到单日领取上限
		if (config.getDailyLimit() != PromotionProductConfig.TRAFFIC_UNLIMIT
				&& config.getDailyRemain() < minMbay.getMbay().getMbay()) {
			LOGGER.error("活动编号:{},说明:{}", campaignNumber, "达到单日领取上限");
			return new PromotionCampaignMbayResponse(0, "达到单日领取上限");
		}
		
		// 获取产品包
		List<PromotionCampaignMbay> datas = campaignMbayDao
				.findByCampaignNumber(campaignNumber);
				
		// 产品包不存在
		if (datas == null || datas.isEmpty()) {
			LOGGER.error("活动编号:{},说明:{}", campaignNumber, "美贝产品不存在");
			return new PromotionCampaignMbayResponse(0, "美贝产品不存在");
		}
		
		// 本次可领最大美贝
		double maxMbay = config.getPoolRemain();
		if (config.getDailyLimit() != TrafficRedProductConfig.TRAFFIC_UNLIMIT
				&& maxMbay > config.getDailyRemain()) {
			maxMbay = config.getDailyRemain();
		}
		
		Iterator<PromotionCampaignMbay> itor = datas.iterator();
		PromotionCampaignMbay tmp = null;
		int seed = 0;
		while (itor.hasNext()) {
			tmp = itor.next();
			
			// 去除不能满足的美贝包
			if (tmp.getRatio() <= 0 || tmp.getMbay().getMbay() > maxMbay) {
				itor.remove();
			} else {
				// 权重相加
				seed += tmp.getRatio();
			}
		}
		int rand = RandomUtils.nextInt(seed);
		int mbay = datas.get(rand).getMbay().getMbay();
		LOGGER.info("活动编号:{},说明:{}", campaignNumber, "抽取到的产品为" + mbay + "MB");
		return new PromotionCampaignMbayResponse(true, mbay, "");
	}
	
	/**
	 * 随机抽取流量产品
	 * 
	 * @param campaignNumber
	 * @return
	 * @throws Exception
	 */
	private PromotionCampaignPackageResponse selectTrafficProduct(
			String campaignNumber,
			HcodeInfo hcode, PromotionProductConfig config) {
		// 查询配置信息
		PromotionCampaignPackage minTraffic = campaignPackageDao
				.findValidMin(campaignNumber,
						OperatorType.valueOf(hcode.getOperator()));
						
		// 未设置流量产品
		if (minTraffic == null || minTraffic.getRatio() <= 0) {
			LOGGER.error("活动编号:{},说明:{}", campaignNumber, "未设置流量产品");
			return new PromotionCampaignPackageResponse(null, "未设置流量产品");
		}
		
		double minPrice = minTraffic.getTrafficPackage().getTrafficPackage()
				.getMbayprice();
		// 美贝池耗尽
		if (config.getPoolRemain() < minPrice) {
			LOGGER.error("活动编号:{},说明:{}", campaignNumber, "流量池耗尽");
			return new PromotionCampaignPackageResponse(null, "流量全部都领完了");
		}
		
		// 流量是否达到单日领取上限
		if (config.getDailyLimit() != PromotionProductConfig.TRAFFIC_UNLIMIT
				&& config.getDailyRemain() < minPrice) {
			LOGGER.error("活动编号:{},说明:{}", campaignNumber, "达到单日领取上限");
			return new PromotionCampaignPackageResponse(null, "达到单日领取上限");
		}
		
		// 获取产品包
		List<PromotionCampaignPackage> datas = campaignPackageDao
				.findByCampaignNumberAndOperatorType(campaignNumber,
						OperatorType.valueOf(hcode.getOperator()));
						
		// 产品包不存在
		if (datas == null || datas.isEmpty()) {
			LOGGER.error("活动编号:{},说明:{}", campaignNumber, "流量产品不存在");
			return new PromotionCampaignPackageResponse(null, "流量产品不存在");
		}
		
		// 本次可领最大流量
		double maxTraffic = config.getPoolRemain();
		if (config.getDailyLimit() != TrafficRedProductConfig.TRAFFIC_UNLIMIT
				&& maxTraffic > config.getDailyRemain()) {
			maxTraffic = config.getDailyRemain();
		}
		
		Iterator<PromotionCampaignPackage> itor = datas.iterator();
		PromotionCampaignPackage tmp = null;
		int seed = 0;
		while (itor.hasNext()) {
			tmp = itor.next();
			double packagePrice = tmp.getTrafficPackage().getTrafficPackage()
					.getMbayprice();
					
			// 去除不能满足的流量包
			if (tmp.getRatio() <= 0 || packagePrice > maxTraffic) {
				itor.remove();
			}
			// 权重相加
			else {
				seed += tmp.getRatio();
			}
		}
		int rand = RandomUtils.nextInt(seed);
		PromotionCampaignPackage campaignPackage = datas.get(rand);
		double mbay = campaignPackage.getTrafficPackage()
				.getTrafficPackage().getMbayprice();
		LOGGER.info("活动编号:{},说明:{}", campaignNumber, "抽取到的产品为" + mbay + "美贝");
		return new PromotionCampaignPackageResponse(true, campaignPackage, "");
	}
	
	@Override
	public RedeemCode findStatusByCodeAndCampaignNumber(String code,
			String campaignNumber) {
		return redeemCodeDao.findStatusByCodeAndCampaignNumber(code,
				campaignNumber);
	}
	
	@Override
	public boolean updateSended(String code, String campaignNumber) {
		return redeemCodeDao.updateSended(code, campaignNumber) > 0;
	}
	
	@Override
	public void sendThresholdWarningSms(String campaignNumber,
			ProductType productType) {
		if (configService.isProductPoolRemainLessThanThreshold(
				campaignNumber, productType)) {
			// 修改时加锁，同时只能有一个人修改
			boolean changeResult = configService
					.changeProductThresholdWarned(campaignNumber,
							productType);
			// 修改失败表示已发送过短信
			if (changeResult) {
				ThresholdWarnInfo info = configService
						.findThresholdWarnInfo(campaignNumber, productType);
				String pcdomain = ResourceConfig.getWebDomain();
				String smsProperties = info.getCampaignName() + ","
						+ productType.getValue() + "," + info.getThreshold()
						+ ","
						+ productType.getValue() + "," + pcdomain;
				MbaySMS.sendSMS(SMSType.TR_THRESHOLD_WARNING,
						info.getWarningMobile(),
						smsProperties);
			}
		}
	}
}
