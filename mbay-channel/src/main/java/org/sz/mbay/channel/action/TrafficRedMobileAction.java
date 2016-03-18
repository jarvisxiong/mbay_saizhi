package org.sz.mbay.channel.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.base.annotation.CacheControl;
import org.sz.mbay.base.annotation.CachePolicy;
import org.sz.mbay.base.annotation.Token;
import org.sz.mbay.base.annotation.hotlinking.Hotlinking;
import org.sz.mbay.base.annotation.log.CalTimeConsuming;
import org.sz.mbay.base.config.ResourceConfig;
import org.sz.mbay.base.context.WebContext;
import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.base.exception.ServiceException;
import org.sz.mbay.base.regex.pattern.RegExp;
import org.sz.mbay.base.web.utils.RequestUtil;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.captcha.CaptchaResult;
import org.sz.mbay.captcha.SMSAuthCodeUtil;
import org.sz.mbay.channel.context.ChannelConfig;
import org.sz.mbay.channel.costant.ChannelConstant.Wallet;
import org.sz.mbay.channel.exception.WebInterfaceException;
import org.sz.mbay.channel.framework.action.BaseAction;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.common.util.MbayDateFormat;
import org.sz.mbay.common.util.MbayDateFormat.DatePattern;
import org.sz.mbay.fs.FSClient;
import org.sz.mbay.fs.FSClientFactory;
import org.sz.mbay.fs.FSClientFactory.ClientType;
import org.sz.mbay.fs.fastdfs.FDFSFileInfo;
import org.sz.mbay.hcode.MbayHcode;
import org.sz.mbay.hcode.bean.HcodeInfo;
import org.sz.mbay.operator.bean.TrafficPackage;
import org.sz.mbay.operator.service.OperatorService;
import org.sz.mbay.remote.interfaces.wallet.RIMBAccountUtil;
import org.sz.mbay.remote.interfaces.wallet.RITradeRecordUtil;
import org.sz.mbay.remote.interfaces.wallet.base.RIResponse;
import org.sz.mbay.sms.client.MbaySMS;
import org.sz.mbay.sms.template.enums.CaptchaSMSType;
import org.sz.mbay.sms.template.enums.SMSType;
import org.sz.mbay.trafficorder.bean.TrafficRechargeInfo;
import org.sz.mbay.trafficorder.enums.TrafficOrderType;
import org.sz.mbay.trafficorder.service.TrafficOrderService;
import org.sz.mbay.trafficrecharge.service.TrafficRechargeService;
import org.sz.mbay.trafficred.bean.TimeQuantum;
import org.sz.mbay.trafficred.bean.TrafficRedAdvancedConfig;
import org.sz.mbay.trafficred.bean.TrafficRedCampaign;
import org.sz.mbay.trafficred.bean.TrafficRedExchangeRecord;
import org.sz.mbay.trafficred.bean.TrafficRedMbayGift;
import org.sz.mbay.trafficred.bean.TrafficRedMbayGiftConfig;
import org.sz.mbay.trafficred.bean.TrafficRedRaffle;
import org.sz.mbay.trafficred.bean.TrafficRedShareInfo;
import org.sz.mbay.trafficred.bean.TrafficRedTemplate;
import org.sz.mbay.trafficred.constant.TrafficRedConfig;
import org.sz.mbay.trafficred.constant.TrafficRedConstant;
import org.sz.mbay.trafficred.constant.error.TrafficRedAdvancedError;
import org.sz.mbay.trafficred.constant.error.TrafficRedMobileError;
import org.sz.mbay.trafficred.datecal.CalendarRangeContext;
import org.sz.mbay.trafficred.enums.MbayGiftState;
import org.sz.mbay.trafficred.enums.PackageState;
import org.sz.mbay.trafficred.enums.SharkCycleType;
import org.sz.mbay.trafficred.redeem.StrategyContext;
import org.sz.mbay.trafficred.result.GiftResult;
import org.sz.mbay.trafficred.result.RedeemDataResult;
import org.sz.mbay.trafficred.result.RedeemResult;
import org.sz.mbay.trafficred.result.Result;
import org.sz.mbay.trafficred.service.TrafficRedAdvancedService;
import org.sz.mbay.trafficred.service.TrafficRedCampaignService;
import org.sz.mbay.trafficred.service.TrafficRedExchangeRecordService;
import org.sz.mbay.trafficred.service.TrafficRedMbayGiftConfigService;
import org.sz.mbay.trafficred.service.TrafficRedMbayGiftLimitService;
import org.sz.mbay.trafficred.service.TrafficRedMbayGiftService;
import org.sz.mbay.trafficred.service.TrafficRedRaffleService;
import org.sz.mbay.trafficred.service.TrafficRedShareInfoService;
import org.sz.mbay.trafficred.service.TrafficRedTmplService;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.sf.json.JSONObject;

/**
 * 流量钱包 摇一摇控制器
 * 
 * @author Fenlon
 */
@Controller
@RequestMapping(value = "tr_mobile")
public class TrafficRedMobileAction extends BaseAction {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TrafficRedMobileAction.class);
			
	@Autowired
	private TrafficRedCampaignService campaignService;
	@Autowired
	private TrafficRedTmplService trafficRedTmplService;
	@Autowired
	private TrafficRedExchangeRecordService exchangeRecordService;
	@Autowired
	private TrafficRedShareInfoService trafficRedShareInfoService;
	@Autowired
	private TrafficRedShareInfoService shareInfoService;
	@Autowired
	private OperatorService operatorService;
	@Autowired
	private TrafficOrderService trafficOrderService;
	@Autowired
	private TrafficRechargeService trafficRechargeService;
	@Autowired
	private TrafficRedMbayGiftConfigService mbayGiftConfigService;
	@Autowired
	private TrafficRedMbayGiftService mbayGiftService;
	@Autowired
	private TrafficRedAdvancedService advancedService;
	@Autowired
	private TrafficRedMbayGiftLimitService mbayGiftLimitService;
	@Autowired
	private TrafficRedRaffleService raffleService;
	
	// fastdfs文件管理
	private FSClient fsClient = FSClientFactory.getClient(ClientType.FDFS);
	
	@RequestMapping("/advanced")
	public String sharkAdvanced(HttpServletRequest request) {
		String userNumber = request.getParameter("userNumber");
		String campaignIdStr = request.getParameter("campaignID");
		String singntimeStr = request.getParameter("signtime");
		String code = request.getParameter("code");
		String sign = request.getParameter("sign");
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("商户{}请求流量红包活动{}开发者接口。code：{}", userNumber,
					campaignIdStr, code);
		}
		// 1.判断参数是否提供全
		if (StringUtils.isEmpty(userNumber)
				|| StringUtils.isEmpty(campaignIdStr)
				|| StringUtils.isEmpty(singntimeStr)
				|| StringUtils.isEmpty(code)
				|| StringUtils.isEmpty(sign)) {
			LOGGER.error(
					"userNumber:{},campaignId:{},signtime:{},code:{},sign:{}",
					userNumber, campaignIdStr, singntimeStr, code, sign);
			throw new WebInterfaceException(
					TrafficRedAdvancedError.ILLEGAL_ARGUMENT);
		}
		// 2.判断是否超时
		long signTime = 0;
		try {
			signTime = Long.valueOf(singntimeStr);
		} catch (NumberFormatException nfe) {
			LOGGER.error("ERROR signTime:{}", singntimeStr);
			throw new WebInterfaceException(
					TrafficRedAdvancedError.ILLEGAL_ARGUMENT);
		}
		long mist = System.currentTimeMillis() - signTime;
		if (mist > TrafficRedConstant.MISTIMING) {
			throw new WebInterfaceException(
					TrafficRedAdvancedError.REQUEST_TIMEOUT);
		}
		// 3.判断是否存在的活动
		long campaignId = 0;
		try {
			campaignId = Long.valueOf(campaignIdStr);
		} catch (NumberFormatException nfe) {
			LOGGER.error("ERROR campaignId:{}", campaignIdStr);
			throw new WebInterfaceException(
					TrafficRedAdvancedError.ILLEGAL_ARGUMENT);
		}
		if (!campaignService.isExistCampaign(campaignId)) {
			LOGGER.error(
					"ERROR SELLER_OR_CAMPAIGN_NOT_EXIST :userNumber:{},campaignId:{}",
					userNumber, campaignIdStr);
			throw new WebInterfaceException(
					TrafficRedAdvancedError.SELLER_OR_CAMPAIGN_NOT_EXIST);
		}
		// 判断是否启用开发者模式
		TrafficRedAdvancedConfig config = this.advancedService
				.findAdvancedConfig(campaignId);
		if (config == null || !config.isEnabled()) {
			throw new WebInterfaceException(
					TrafficRedAdvancedError.DISABLED_ADVANCED);
		}
		// userNumber+campaignId+singntime+code+key
		String singStr = userNumber + campaignId + singntimeStr + code
				+ config.getKey();
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("code{}加密串：{}", code, singStr);
		}
		String serverSign = DigestUtils.md5Encrypt(singStr);
		if (!serverSign.equals(sign)) {
			LOGGER.error("ERROR ILLEGAL_SIGN :serverSign:{},customerSign:{}",
					serverSign, sign);
			throw new WebInterfaceException(
					TrafficRedAdvancedError.ILLEGAL_SIGN);
		}
		String campaignNumber = this.campaignService
				.findCampaignNumberById(campaignId);
		return "redirect:/tr_mobile/shakeIndex.mbay?number="
				+ DigestUtils.pbeEncrypt(campaignNumber);
	}
	
	/**
	 * 摇一摇首页 - 防盗链
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@Hotlinking
	@CacheControl(policy = CachePolicy.NO_CACHE)
	@RequestMapping({ "shakeIndex", "shakeIndexReal" })
	public String sharkIndex(@RequestParam("number") String number,
			Model model, HttpServletRequest request) {
		number = StringUtils.trim(number);
		
		// 信息加载
		TrafficRedCampaign campaign = (TrafficRedCampaign) getCampaignInfo(
				number, true, false, false, false);
				
		// 信息检测
		String mobile = getCookieMobile(request, number);
		Result result = checkInfosBefore(campaign);
		if (!result.getStatus()) {
			return errorPage(model, result, campaign.getTemplate()
					.getLogoCycleLink(), mobile);
		}
		
		// 第二次摇跳过输号码页面，进入摇一摇
		if (mobile != null) {
			return "redirect:/tr_mobile/shakeUI.mbay?number=" + number;
		}
		model.addAttribute("c", campaign);
		model.addAttribute("cnumber", number);
		
		// 酷乐特列
		String kule = ResourceConfig.getProperty("kule_cnumber");
		if (kule.equals(DigestUtils.pbeDecrypt(number))) {
			return "/traffic_red/mobile/customize/kule/shake/shakeIndex";
		}
		
		// 是否有定制页面
		if (StringUtils.isNotEmpty(campaign.getTemplate().getShakeIndexImg())) {
			return "/traffic_red/mobile/customize/shake/shakeIndex";
		}
		return "/traffic_red/mobile/shake/shakeIndex";
	}
	
	/**
	 * 摇完后记录活动编号和号码到cookie 目的：限制手机某一活动只能使用一个号码参与
	 * 
	 * @param number
	 * @param mobile
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("remMobile")
	public Object remMobile(@RequestParam("number") String number,
			@RequestParam("mobile") String mobile,
			HttpServletRequest request,
			HttpServletResponse response) {
		Cookie cookie = RequestUtil.getCookie(request, number);
		if (cookie == null) {
			RequestUtil.setCookie(response, number,
					DigestUtils.pbeEncrypt(mobile), 30 * 24 * 60 * 60);
		}
		return RedeemResult.SUCCESS;
	}
	
	/*
	 * 重定向到错误页面
	 */
	private String errorPage(Model model, Result result, String surprise,
			String mobile) {
		model.addAttribute("errorCode", result.getCode());
		model.addAttribute("errorMsg", result.getContent());
		model.addAttribute("walletIndex", Wallet.INDEX);
		model.addAttribute("surprise", getSurprise(surprise, mobile));
		return "/traffic_red/mobile/shake/error_info";
	}
	
	/*
	 * 检测相关参数
	 * @param campaignNumber
	 * @param mobile
	 * @return
	 */
	private Result checkInfosBefore(TrafficRedCampaign campaign) {
		if (campaign == null) {
			return RedeemResult.CAMPAIGN_NOT_EXIST;
		}
		
		// 验证活动状态
		switch (campaign.getStatus()) {
			case CANCLED:
				return RedeemResult.CAMPAIGN_CANCELED;
			case NOT_STARTED:
				return RedeemResult.CAMPAIGN_NOT_STARTED;
			case OVER:
				return RedeemResult.CAMPAIGN_ENDED;
			default:
		}
		
		List<TimeQuantum> timeList = campaign.getTimeQuantums();
		if (timeList == null || timeList.isEmpty()) {
			return RedeemResult.SUCCESS;
		}
		
		// 验证活动时间段
		DateTime now = DateTime.now();
		String dateStr = now.toString("yyyy-MM-dd ");
		DateTime startTime, endTime;
		boolean passTime = false;
		for (TimeQuantum time : timeList) {
			startTime = DateTime.parse(dateStr + time.getStartTime(),
					MbayDateFormat.DateFormatter.timeFormat);
			endTime = DateTime.parse(dateStr + time.getEndTime(),
					MbayDateFormat.DateFormatter.timeFormat);
			if (now.isAfter(startTime) && now.isBefore(endTime)) {
				passTime = true;
				break;
			}
		}
		if (!passTime) {
			return RedeemResult.TIME_NOT_RATIONAL;
		}
		
		return RedeemResult.SUCCESS;
	}
	
	/**
	 * 摇一摇界面 - 防盗链
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@Hotlinking
	@RequestMapping({ "shakeUI", "shakeUIReal" })
	public String sharkUI(@RequestParam("number") String number,
			Model model, HttpServletRequest request) {
		// 活动信息加载
		TrafficRedCampaign campaign = (TrafficRedCampaign) getCampaignInfo(
				number, true, false, false, false);
				
		// cookie中获取手机号
		String mobile = getCookieMobile(request, number);
		if (mobile == null) {
			return errorPage(model, RedeemResult.MOBILE_NOT_SAME, campaign
					.getTemplate().getLogoCycleLink(), mobile);
		}
		
		model.addAttribute("walletIndex", Wallet.INDEX + "?mobile=" + mobile);
		model.addAttribute("cnumber", number);
		model.addAttribute("mobile", mobile);
		
		// 酷乐特列
		String kule = ResourceConfig.getProperty("kule_cnumber");
		if (kule.equals(DigestUtils.pbeDecrypt(number))) {
			return "/traffic_red/mobile/customize/kule/shake/shakeUI";
		}
		
		// 是否有定制页面
		if (StringUtils.isNotEmpty(campaign.getTemplate().getShakeUIImg())) {
			model.addAttribute("shakeUIImg",
					campaign.getTemplate().getShakeUIImg());
			return "/traffic_red/mobile/customize/shake/shakeUI";
		}
		return "/traffic_red/mobile/shake/shakeUI";
	}
	
	/**
	 * 摇到流量包处理
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@Token(save = true)
	@CacheControl(policy = CachePolicy.NO_CACHE)
	@RequestMapping(value = "result_traffic_pack", method = RequestMethod.GET)
	public String resultTrafficPack(@RequestParam("size") Integer size,
			@RequestParam("recordId") String recordId,
			@RequestParam("cnumber") String cnumber, Model model,
			HttpServletRequest request) {
		TrafficRedCampaign campaign = (TrafficRedCampaign) getCampaignInfo(
				cnumber, true, false, false, false);
				
		model.addAttribute("size", String.valueOf(size).toCharArray());
		model.addAttribute("cnumber", cnumber);
		model.addAttribute("traddicSize", size);
		model.addAttribute("recordId", recordId);
		model.addAttribute(
				"surprise",
				getSurprise(campaign.getTemplate().getLogoCycleLink(),
						getCookieMobile(request, cnumber)));
						
		return "/traffic_red/mobile/shake/result_traffic_pack";
	}
	
	/**
	 * 摇到美贝流量处理
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "result_mbay_pack", method = RequestMethod.GET)
	public String resultMbayPack(@RequestParam("size") Integer size,
			@RequestParam("recordId") String recordId,
			@RequestParam("cnumber") String cnumber,
			@RequestParam("serialNumber") String serialNumber, Model model,
			HttpServletRequest request) {
		TrafficRedCampaign campaign = (TrafficRedCampaign) getCampaignInfo(
				cnumber, true, true, false, false);
				
		String mobile = getCookieMobile(request, cnumber);
		if (mobile == null) {
			return errorPage(model, RedeemResult.COOKIE_NOT_SUPPORTTED,
					campaign.getTemplate().getLogoCycleLink(), mobile);
		}
		
		model.addAttribute("size", String.valueOf(size).toCharArray());
		model.addAttribute("cnumber", cnumber);
		model.addAttribute("serialNumber", serialNumber);
		model.addAttribute("surprise",
				getSurprise(campaign.getTemplate().getLogoCycleLink(),
						getCookieMobile(request, cnumber)));
		model.addAttribute("duiba", getDuibaUrl(campaign.getMall().getId()));
		model.addAttribute("walletIndex", Wallet.INDEX + "?mobile=" + mobile);
		model.addAttribute("campaignName", campaign.getName());
		model.addAttribute("mbayTraffic", size);
		model.addAttribute("youxi", Wallet.YOUXI);
		
		if (campaign.getType() == SharkCycleType.NO_LIMITED) {
			model.addAttribute("remindShare", false);
			model.addAttribute("continuable", true);
		} else {
			int cTimes = campaign.getTimes();
			int sTime = campaign.getShareInfo()
					.getEnableState() == EnableState.DISENABLE ? 0
							: campaign.getShareInfo().getShareTimes();
			int ptimes = exchangeRecordService.getParticipatedTimes(
					campaign.getNumber(), mobile, campaign.getType());
			model.addAttribute("remindShare", ptimes >= cTimes);
			model.addAttribute("continuable", ptimes < cTimes + sTime);
		}
		
		return "/traffic_red/mobile/shake/result_mbay_pack";
	}
	
	/*
	 * 兑吧商城链接
	 */
	private String getDuibaUrl(int mallId) {
		String duiba_url = Wallet.WL_DUIBA + "&mallId="
				+ (mallId == 0 ? ChannelConfig.getMallId()
						: String.valueOf(mallId));
		return duiba_url;
	}
	
	/**
	 * 流量包兑换结果
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "redeem_result", method = RequestMethod.GET)
	public String redeemResult(@RequestParam("cnumber") String cnumber,
			@RequestParam("size") Integer size, Model model,
			HttpServletRequest request) {
		TrafficRedCampaign campaign = (TrafficRedCampaign) getCampaignInfo(
				cnumber, true, true, false, false);
				
		String mobile = getCookieMobile(request, cnumber);
		if (mobile == null) {
			return errorPage(model, RedeemResult.COOKIE_NOT_SUPPORTTED,
					campaign.getTemplate().getLogoCycleLink(),
					mobile);
		}
		
		if (size == null) {
			return errorPage(model, RedeemResult.TRAFFIC_SIZE_ERROR, campaign
					.getTemplate().getLogoCycleLink(), mobile);
		}
		
		model.addAttribute("size", (String.valueOf(size)).toCharArray());
		model.addAttribute("template", campaign.getTemplate());
		model.addAttribute("cnumber", cnumber);
		model.addAttribute("walletIndex", Wallet.INDEX + "?mobile=" + mobile);
		model.addAttribute("surprise",
				getSurprise(campaign.getTemplate().getLogoCycleLink(), mobile));
				
		// 判断分享后能否增加摇一摇次数
		String key = cnumber + "_" + mobile;
		Cookie cookie = RequestUtil.getCookie(request, key);
		int avaibleTimes = 0;
		if (cookie != null) {
			List<DateTime> dateTimes = new ArrayList<DateTime>();
			String timesStr = cookie.getValue();
			LOGGER.info("COOKIE VALUE:{}", timesStr);
			for (String time : timesStr.split(",")) {
				DateTime dateTime = MbayDateFormat.stringToDate(time);
				if (dateTime != null)
					dateTimes.add(dateTime);
			}
			avaibleTimes = new CalendarRangeContext(dateTimes,
					campaign.getType()).calculateInRande();
		}
		int dtime = campaign.getShareInfo()
				.getEnableState() == EnableState.DISENABLE ? 0
						: campaign.getShareInfo().getShareTimes();
		model.addAttribute("sharable", dtime != 0 && dtime > avaibleTimes);
		
		return "/traffic_red/mobile/shake/redeem_result";
	}
	
	/**
	 * 分享成功后设置cookie
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "share_success", method = RequestMethod.GET)
	@ResponseBody
	public Object shareSuccess(@RequestParam("cNumber") String cNumber,
			HttpServletRequest request,
			HttpServletResponse response) {
		String mobile = getCookieMobile(request, cNumber);
		if (mobile == null) {
			return RedeemResult.COOKIE_NOT_SUPPORTTED;
		}
		
		String key = cNumber + "_" + mobile;
		
		Result res = (Result) getCampaignInfo(cNumber, false, true, false,
				true);
		if (!res.getStatus()) {
			return res;
		}
		TrafficRedCampaign campaign = (TrafficRedCampaign) ((RedeemDataResult) (res))
				.getData();
				
		// 分享标识符,如果超出分享有效次数则不弹出提示
		boolean share_flag = true;
		int shareTimes = campaign.getShareInfo().getShareTimes();
		if (campaign.getShareInfo().getEnableState() == EnableState.DISENABLE
				|| shareTimes == 0) {
			return RedeemResult.create(false, "INVALID_SHARE_TIMES", null);
		}
		
		String value = MbayDateFormat.formatDateTime(DateTime.now(),
				DatePattern.isoDate);
		Cookie cookie = RequestUtil.getCookie(request, key);
		if (cookie != null) {
			List<DateTime> dateTimes = new ArrayList<DateTime>();
			String timesStr = cookie.getValue();
			LOGGER.info("COOKIE VALUE:{}", timesStr);
			for (String time : timesStr.split(",")) {
				DateTime dateTime = MbayDateFormat.stringToDate(time);
				if (dateTime != null) {
					dateTimes.add(dateTime);
				}
			}
			
			int avaibleTimes = new CalendarRangeContext(dateTimes,
					campaign.getType()).calculateInRande();
			LOGGER.info("avaibleTimes:{},shareTimes:{}", avaibleTimes,
					shareTimes);
			if (avaibleTimes < shareTimes) {
				value = timesStr + "," + value;
				share_flag = true;
			} else {
				value = timesStr;
				share_flag = false;
			}
		} else {
			LOGGER.info("avaibleTimes:{},shareTimes:{}", 0, shareTimes);
			if (shareTimes > 0) {
				share_flag = true;
			} else {
				share_flag = false;
			}
		}
		
		// 设置cookie,时效为1个月
		RequestUtil.setCookie(response, key, value, 60 * 60 * 24 * 30);
		return RedeemResult.create(share_flag, null, null);
	}
	
	/**
	 * 配置微信
	 * 
	 * @param cnumber
	 * @return
	 */
	@ResponseBody
	@RequestMapping("wechatConfig")
	public Object wechatConfig(@RequestParam("cnumber") String cnumber) {
		Result res = (Result) getCampaignInfo(cnumber, false, true, false,
				true);
		if (!res.getStatus()) {
			return res;
		}
		TrafficRedCampaign campaign = (TrafficRedCampaign) ((RedeemDataResult) (res))
				.getData();
		TrafficRedShareInfo shareInfo = campaign.getShareInfo();
		
		JSONObject data = new JSONObject();
		data.put("status", true);
		data.put("shareTimes", shareInfo.getShareTimes());
		data.put("content", shareInfo.getContent());
		data.put("shareTitle", shareInfo.getShareTitle());
		data.put("enableState", shareInfo.getEnableState());
		data.put("shareLink", shareInfo.getShareLink());
		
		FDFSFileInfo imageInfo = (FDFSFileInfo) fsClient.getFileInfo(shareInfo
				.getShareImg());
		data.put("shareImage", imageInfo.getFullPath());
		
		return data;
	}
	
	/*
	 * 获取手机号
	 */
	@ResponseBody
	@RequestMapping("getCookieMobile")
	public String getCookieMobile(HttpServletRequest request,
			@RequestParam("cnumber") String cnumber) {
		Cookie mc = RequestUtil.getCookie(request, cnumber);
		if (mc == null) {
			return null;
		}
		return DigestUtils.pbeDecrypt(mc.getValue());
	}
	
	/*
	 * 获取活动完整信息
	 */
	private Object getCampaignInfo(
			String cnumber,
			boolean loadTemplate,
			boolean loadShareInfo,
			boolean loadGiftConfig,
			boolean isAjax) {
		// 编号解密
		String decNum = DigestUtils.pbeDecrypt(cnumber);
		if (StringUtils.isEmpty(decNum)) {
			if (isAjax) {
				return RedeemResult.CAMPAIGN_NUMBER_ERROR;
			} else {
				throw new ServiceException(
						TrafficRedMobileError.CAMPAIGN_NUMBER_ERROR);
			}
		}
		
		// 获取活动基本信息
		TrafficRedCampaign campaign = campaignService
				.findCampaignByNumber(decNum);
		if (campaign == null) {
			if (isAjax) {
				return RedeemResult.CAMPAIGN_NOT_EXIST;
			} else {
				throw new ServiceException(
						TrafficRedMobileError.CAMPAIGN_NOT_EXIST);
			}
		}
		
		if (loadGiftConfig) {
			TrafficRedMbayGiftConfig giftConfig = mbayGiftConfigService
					.findById(campaign.getMbayGiftConfig().getId());
			if (giftConfig == null) {
				if (isAjax) {
					return GiftResult.MBAY_GIFT_CONFIG_NOT_EXIST;
				} else {
					throw new ServiceException(
							TrafficRedMobileError.MBAY_GIFT_CONFIG_NOT_EXIST);
				}
			}
			campaign.setMbayGiftConfig(giftConfig);
		}
		
		if (loadTemplate) {
			TrafficRedTemplate template = trafficRedTmplService.find(campaign
					.getTemplate().getId());
			if (template == null) {
				if (isAjax) {
					return RedeemResult.TEMPLATE_NOT_EXIST;
				} else {
					throw new ServiceException(
							TrafficRedMobileError.TEMPLATE_NOT_EXIST);
				}
			}
			campaign.setTemplate(template);
		}
		
		if (loadShareInfo) {
			TrafficRedShareInfo shareInfo = trafficRedShareInfoService
					.findById(campaign.getShareInfo().getId());
			if (shareInfo == null) {
				if (isAjax) {
					return RedeemResult.SHAREINFO_NOT_EXIST;
				} else {
					throw new ServiceException(
							TrafficRedMobileError.SHAREINFO_NOT_EXIST);
				}
			}
			campaign.setShareInfo(shareInfo);
		}
		
		if (isAjax) {
			return RedeemDataResult.create(campaign);
		} else {
			return campaign;
		}
	}
	
	/*
	 * 获取有惊喜连接
	 */
	private String getSurprise(String url, String mobile) {
		if (url != null) {
			url = url.trim();
			if (StringUtils.isEmpty(url) || "http://".equalsIgnoreCase(url)
					|| "https://".equalsIgnoreCase(url)) {
				return Wallet.CAMPAIGN_LIST
						+ (StringUtils.isEmpty(mobile) ? "" : "?mobile="
								+ mobile);
			}
		}
		return url;
	}
	
	/**
	 * 先输号码
	 * 
	 * @param campaignNumber
	 * @param mobile
	 * @return
	 */
	@ResponseBody
	@CalTimeConsuming
	@JsonSerialize
	@RequestMapping("redeem/before")
	public Object redeemBefore(
			@RequestParam("number") String number,
			HttpServletRequest request,
			Model model) {
		String mobile = getCookieMobile(request, number);
		if (mobile == null) {
			return RedeemResult.COOKIE_NOT_SUPPORTTED;
		}
		
		LOGGER.info("手机号{}参与活动{}摇一摇", mobile, number);
		
		// 活动编号解密
		String decNum = DigestUtils.pbeDecrypt(number);
		if (decNum == null) {
			LOGGER.info("流量红包【{}/{}】：{}", decNum, mobile,
					RedeemResult.CAMPAIGN_NUMBER_ERROR.getCode());
			return RedeemResult.CAMPAIGN_NUMBER_ERROR;
		}
		
		// 号码验证
		Matcher matcher = RegExp.mobile.matcher(mobile);
		if (!matcher.matches()) {
			LOGGER.info("流量红包【{}/{}】：{}", decNum, mobile,
					RedeemResult.MOBILE_INCORRECT.getCode());
			return RedeemResult.MOBILE_INCORRECT;
		}
		
		// hcode验证
		HcodeInfo hcode = MbayHcode.getHcodeInfo(mobile);
		if (hcode == null) {
			LOGGER.info("流量红包【{}/{}】：{}", decNum, mobile,
					RedeemResult.MOBILE_HCODE_NOT_FOUND.getCode());
			return RedeemResult.MOBILE_HCODE_NOT_FOUND;
		}
		
		// 防模拟并发盗刷
		synchronized (WebContext.getHttpSession()) {
			// 获取活动信息
			Result cres = (Result) getCampaignInfo(number, false, true, false,
					true);
			if (!cres.getStatus()) {
				return cres;
			}
			TrafficRedCampaign campaign = (TrafficRedCampaign) ((RedeemDataResult) (cres))
					.getData();
					
			// 信息验证
			Result ckRes = checkRelatedInfos(campaign, mobile, request);
			if (!ckRes.getStatus()) {
				LOGGER.info("流量红包【{}/{}】：{}", decNum, mobile, ckRes.getCode());
				
				// 验证失败原因为次数超出时检测是否可以再摇一次
				// 因为可能分享后还能继续摇
				if (ckRes.equals(RedeemResult.TIMES_EXCEED_TOTAL)
						|| ckRes.equals(RedeemResult.TIMES_EXCEED_DAY)
						|| ckRes.equals(RedeemResult.TIMES_EXCEED_WEEK)
						|| ckRes.equals(RedeemResult.TIMES_EXCEED_MONTH)) {
					return checkShakeable(campaign, mobile, ckRes);
				}
				return ckRes;
			}
			
			// 查找并执行兑换策略
			Result res = StrategyContext.process(campaign.getId(), mobile);
			
			// 未中奖时检测是否可以再摇一次
			if (res.equals(RedeemResult.NOT_WIN)) {
				return checkShakeable(campaign, mobile, res);
			}
			return res;
		}
	}
	
	/*
	 * 检测是否能继续参与活动
	 */
	private Object checkShakeable(TrafficRedCampaign campaign, String mobile,
			Result res) {
		JSONObject data = new JSONObject();
		data.put("status", res.getStatus());
		data.put("content", res.getContent());
		if (campaign.getType() == SharkCycleType.NO_LIMITED) {
			data.put("code", "SHAKE_AGAIN");
		} else {
			int cTimes = campaign.getTimes();
			int sTime = campaign.getShareInfo()
					.getEnableState() == EnableState.DISENABLE ? 0
							: campaign.getShareInfo().getShareTimes();
			int ptimes = exchangeRecordService.getParticipatedTimes(
					campaign.getNumber(), mobile, campaign.getType());
			if (ptimes < cTimes + sTime) {
				if (ptimes >= cTimes) {
					data.put("code", "SHAKE_REMIND");
				} else {
					data.put("code", "SHAKE_AGAIN");
				}
			} else {
				data.put("code", res.getCode());
			}
		}
		return data;
	}
	
	/**
	 * 立即兑换流量
	 * 
	 * @param data
	 * @return
	 */
	@ResponseBody
	@Token(reset = true)
	@RequestMapping("redeem/exchange_traffic")
	public Object redeemExchangeTraffic(
			@RequestParam("recordId") String recordId) {
		// id解密
		String decId = DigestUtils.pbeDecrypt(recordId);
		if (StringUtils.isEmpty(decId)) {
			LOGGER.info("流量红包 => 兑换流量：<{}>{}", recordId, "recordId不存在");
			return RedeemResult.EXCHANGE_RECORD_ID_ERROR;
		}
		
		// 检测是否已兑换
		Long rid = Long.parseLong(decId);
		TrafficRedExchangeRecord record = exchangeRecordService.findById(rid);
		if (record == null) {
			LOGGER.info("流量红包 => 兑换流量：<{}>{}", recordId, "recordId不存在");
			return RedeemResult.EXCHANGE_RECORD_ID_ERROR;
		}
		
		// 尚未兑换
		if (record.getPackageState() == PackageState.CREATED) {
			// 获取活动信息
			TrafficRedCampaign campaign = campaignService
					.findCampaignByNumber(record.getCampaignNumber());
			if (campaign == null) {
				LOGGER.info("流量红包 => 兑换流量【{}/{}】：{}",
						record.getCampaignNumber(), record.getMobile(),
						"活动不存在");
				return RedeemResult.CAMPAIGN_NOT_EXIST;
			}
			
			// 获取流量包信息
			TrafficPackage tp = operatorService.findTrafficPackage(record
					.getPackageId());
			if (tp == null) {
				LOGGER.info("流量红包 => 兑换流量【{}/{}】：{}",
						record.getCampaignNumber(), record.getMobile(),
						RedeemResult.TRAFFIC_PRODUCT_NOT_EXIST.getCode());
				return RedeemResult.TRAFFIC_PRODUCT_NOT_EXIST;
			}
			
			// 创建流量订单
			TrafficRechargeInfo info = new TrafficRechargeInfo();
			info.setMobile(record.getMobile());
			info.setRechargeType(TrafficOrderType.TRAFFIC_RED);
			info.setRelationNumber(campaign.getNumber());
			info.setTrafficPackageNumber(record.getPackageId());
			info.setUserNumber(campaign.getUserNumber());
			String orderNumber = trafficOrderService.create(info);
			
			// 请求充值流量
			trafficRechargeService.recharge(orderNumber);
			
			// 条件为true时发送短信
			if (campaign.getSendsms()) {
				String params = campaign.getName() + "," + tp.getTraffic();
				MbaySMS.sendSMS(SMSType.ACTIVITY, record.getMobile(),
						params);
			}
			
			// 更新状态
			record.setPackageState(PackageState.RECIEVED);
			exchangeRecordService.updateRecord(record);
			
			return RedeemResult.SUCCESS;
		}
		
		// 重复兑换
		return RedeemResult.TRAFFIC_REDEEM_REPEAT;
	}
	
	/*
	 * 检测相关参数
	 */
	private Result checkRelatedInfos(TrafficRedCampaign campaign,
			String mobile, HttpServletRequest request) {
		// 获取活动信息
		if (campaign == null) {
			return RedeemResult.CAMPAIGN_NOT_EXIST;
		}
		
		// 验证活动状态
		switch (campaign.getStatus()) {
			case CANCLED:
				return RedeemResult.CAMPAIGN_CANCELED;
			case NOT_STARTED:
				return RedeemResult.CAMPAIGN_NOT_STARTED;
			case OVER:
				return RedeemResult.CAMPAIGN_ENDED;
			default:
		}
		
		// 验证活动时间段
		List<TimeQuantum> timeList = campaign.getTimeQuantums();
		if (timeList != null && !timeList.isEmpty()) {
			DateTime now = DateTime.now();
			String dateStr = now.toString("yyyy-MM-dd ");
			DateTime startTime, endTime;
			boolean passTime = false;
			for (TimeQuantum time : timeList) {
				startTime = DateTime.parse(dateStr + time.getStartTime(),
						MbayDateFormat.DateFormatter.timeFormat);
				endTime = DateTime.parse(dateStr + time.getEndTime(),
						MbayDateFormat.DateFormatter.timeFormat);
				if (now.isAfter(startTime) && now.isBefore(endTime)) {
					passTime = true;
					break;
				}
			}
			if (!passTime) {
				return RedeemResult.TIME_NOT_RATIONAL;
			}
		}
		
		// 可参与性检测
		if (campaign.getType() != SharkCycleType.NO_LIMITED) {
			// 读取分享次数
			int sharedTime = 0;
			String ckName = DigestUtils.pbeEncrypt(campaign.getNumber()) + "_"
					+ mobile;
			LOGGER.info("cookieName:{}", ckName);
			Cookie cookie = RequestUtil.getCookie(request, ckName);
			if (cookie != null) {
				List<DateTime> dateTimes = new ArrayList<DateTime>();
				String timesStr = cookie.getValue();
				for (String time : timesStr.split(",")) {
					DateTime dateTime = MbayDateFormat.stringToDate(time);
					if (dateTime != null)
						dateTimes.add(dateTime);
				}
				sharedTime = new CalendarRangeContext(dateTimes,
						campaign.getType()).calculateInRande();
			}
			
			// 设置最终可参与次数
			int finalTimes = campaign.getTimes();
			LOGGER.info("活动{}限制次数:{},用户{}已分享次数{}", campaign.getNumber(),
					finalTimes, mobile, sharedTime);
			if (sharedTime > 0) {
				TrafficRedShareInfo shareInfo = shareInfoService
						.findById(campaign.getShareInfo().getId());
				int dtime = shareInfo.getEnableState() == EnableState.DISENABLE
						? 0 : shareInfo.getShareTimes();
				if (sharedTime >= dtime) {
					finalTimes += dtime;
				} else {
					finalTimes += sharedTime;
				}
			}
			
			// 号码次数限制检测
			boolean enable = exchangeRecordService.checkMobileEnterable(
					campaign.getNumber(), mobile,
					campaign.getType(), finalTimes);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("判断结果：{}", enable);
			}
			if (!enable) {
				switch (campaign.getType()) {
					case TOTAL:
						return RedeemResult.TIMES_EXCEED_TOTAL;
					case DAY:
						return RedeemResult.TIMES_EXCEED_DAY;
					case WEEK:
						return RedeemResult.TIMES_EXCEED_WEEK;
					case MONTH:
						return RedeemResult.TIMES_EXCEED_MONTH;
					default:
				}
			}
		}
		
		return RedeemResult.SUCCESS;
	}
	
	/**
	 * MB送人
	 * 
	 * @param serialNumber
	 * @return
	 */
	@ResponseBody
	@RequestMapping("mbay/gift")
	public Object mbayGift(@RequestParam("serialNumber") String serialNumber) {
		// 交易记录id解密
		String decNum = DigestUtils.pbeDecrypt(serialNumber);
		if (StringUtils.isEmpty(decNum)) {
			LOGGER.error("流量红包 => 送人：<{}>{}", serialNumber, "serialNumber不存在");
			return GiftResult.TRADE_RECORD_NUMBER_ERROR;
		}
		
		// 检查是否已经分享过
		if (mbayGiftService.checkExistBySerialNumber(decNum)) {
			return GiftResult.MBAY_GIFT_SHARE_REPEAT;
		}
		
		// 获取交易记录信息
		RIResponse trResp = null;
		try {
			trResp = RITradeRecordUtil
					.requestGetTradeRecordBySerialNum(decNum);
		} catch (Exception e) {
			LOGGER.error("lookup trade record error:{}", e.getMessage());
			return GiftResult.create(false, e.getMessage());
		}
		String mobile = trResp.getData().getString("mobile");
		BigDecimal amount = new BigDecimal(
				trResp.getData().getJSONObject("result").getDouble("amount"));
				
		// 获取用户余额
		RIResponse mbResp = null;
		try {
			mbResp = RIMBAccountUtil.requestUserGetMBQty(mobile);
		} catch (Exception e) {
			LOGGER.error("lookup trade record error:{}", e.getMessage());
			return GiftResult.create(false, e.getMessage());
		}
		BigDecimal balance = new BigDecimal(
				mbResp.getData().getDouble("balance"));
				
		// 检测余额是否充足
		if (balance.compareTo(amount) < 0) {
			return GiftResult.BALANCE_INSUFFICIENT;
		}
		
		return GiftResult.SUCCESS;
	}
	
	/**
	 * MB送人 - 获取分享配置
	 * 
	 * @param cnumber
	 * @param recordId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("mbay/gift/wechatConfig")
	public Object mbayGiftWechatConfig(@RequestParam("cnumber") String cnumber,
			@RequestParam("serialNumber") String serialNumber,
			HttpServletRequest request) {
		// 获取活动信息
		Result cres = (Result) getCampaignInfo(cnumber, false, false, true,
				true);
		if (!cres.getStatus()) {
			return cres;
		}
		TrafficRedCampaign campaign = (TrafficRedCampaign) ((RedeemDataResult) (cres))
				.getData();
				
		JSONObject data = new JSONObject();
		data.put("status", true);
		data.put("shareTitle", campaign.getMbayGiftConfig().getShareTitle());
		data.put("content", campaign.getMbayGiftConfig().getShareContent());
		data.put("shareLink", getPath(request)
				+ "/tr_mobile/mbay/gift/grab/ui.mbay?serialNumber="
				+ serialNumber
				+ "&cnumber=" + cnumber);
		FDFSFileInfo imageInfo = (FDFSFileInfo) fsClient.getFileInfo(campaign
				.getMbayGiftConfig().getShareImg());
		data.put("shareImage", imageInfo.getFullPath());
		return data;
	}
	
	/*
	 * 项目地址
	 */
	private String getPath(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath();
	}
	
	/**
	 * MB送人 - 分享成功后钱包扣款
	 * 
	 * @param recordId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("mbay/gift/share")
	public Object mbayGiftShare(
			@RequestParam("serialNumber") String serialNumber) {
		// 交易记录id解密
		String decNum = DigestUtils.pbeDecrypt(serialNumber);
		if (StringUtils.isEmpty(decNum)) {
			LOGGER.error("流量红包 => 送人：<{}>{}", serialNumber, "serialNumber不存在");
			return GiftResult.TRADE_RECORD_NUMBER_ERROR;
		}
		
		// 获取兑换记录信息
		RIResponse trResp = null;
		try {
			trResp = RITradeRecordUtil
					.requestGetTradeRecordBySerialNum(decNum);
		} catch (Exception e) {
			LOGGER.error("lookup trade record error:{}", e.getMessage());
			return GiftResult.create(false, e.getMessage());
		}
		String mobile = trResp.getData().getString("mobile");
		double amount = trResp.getData().getJSONObject("result")
				.getDouble("amount");
				
		// 检查是否已经分享过
		if (mbayGiftService.checkExistBySerialNumber(decNum)) {
			return GiftResult.MBAY_GIFT_SHARE_REPEAT;
		}
		
		// 钱包扣款
		try {
			RIMBAccountUtil.requestUserOutOfAccount(mobile,
					amount, "TRAFFIC_RED_MBAY_GIFT", decNum, null);
		} catch (Exception e) {
			LOGGER.error("reduce balance error:{}", e.getMessage());
			return GiftResult.create(false, e.getMessage());
		}
		
		// 创建送人记录
		TrafficRedMbayGift gift = new TrafficRedMbayGift();
		gift.setCreateTime(DateTime.now());
		gift.setMbayGiftState(MbayGiftState.CREATED);
		gift.setSerialNumber(decNum);
		gift.setSender(mobile);
		gift.setAmount(amount);
		mbayGiftService.create(gift);
		
		return RedeemResult.SUCCESS;
	}
	
	/**
	 * MB送人 - 抢 - 界面
	 * 
	 * @return
	 */
	@RequestMapping("mbay/gift/grab/ui")
	public String mbayGiftGrab(@RequestParam("cnumber") String cnumber,
			@RequestParam("serialNumber") String serialNumber,
			HttpServletResponse response,
			Model model) {
		// 活动结束，禁止领取
		TrafficRedCampaign campaign = (TrafficRedCampaign) getCampaignInfo(
				cnumber, false, false, true, false);
		if (campaign.getStatus() == CampaignStatus.CANCLED
				|| campaign.getStatus() == CampaignStatus.OVER) {
			try {
				response.sendRedirect(TrafficRedConfig.CAMPAIGN_OVER_URL);
			} catch (IOException e) {
				LOGGER.error("sendRedirect error: {}", e.getMessage());
			}
			return null;
		}
		String imgPath = ((FDFSFileInfo) fsClient.getFileInfo(campaign
				.getMbayGiftConfig().getBgImg())).getFullPath();
		model.addAttribute("bgImg", imgPath);
		model.addAttribute("cnumber", cnumber);
		model.addAttribute("serialNumber", serialNumber);
		return "/traffic_red/mobile/shake/gift";
	}
	
	/**
	 * MB送人 - 抢
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("mbay/gift/grab")
	public Object mbayGiftGrab(@RequestParam("cnumber") String cnumber,
			@RequestParam("serialNumber") String serialNumber,
			@RequestParam("mobile") String mobile) {
		// 获取活动信息
		Result cres = (Result) getCampaignInfo(cnumber, false, false, false,
				true);
		if (!cres.getStatus()) {
			return cres;
		}
		TrafficRedCampaign campaign = (TrafficRedCampaign) ((RedeemDataResult) (cres))
				.getData();
				
		// 活动结束，禁止领取
		if (campaign.getStatus() == CampaignStatus.CANCLED
				|| campaign.getStatus() == CampaignStatus.OVER) {
			return RedeemResult.CAMPAIGN_ENDED;
		}
		
		// 查询送人记录
		String decSNum = DigestUtils.pbeDecrypt(serialNumber);
		TrafficRedMbayGift gift = mbayGiftService
				.findBySerialNumber(decSNum);
		if (gift == null) {
			return GiftResult.MBAY_GIFT_NOT_EXIST;
		}
		
		// 链接过期
		DateTime createDate = gift.getCreateTime();
		int expire = mbayGiftLimitService.find().getValidity();
		if (createDate.plusHours(expire).isBeforeNow()) {
			return GiftResult.MBAY_GIFT_LINK_EXPIRED;
		}
		
		// 已被领取
		if (gift.getMbayGiftState() == MbayGiftState.RECIEVED) {
			// 自己领取
			if (gift.getReciever().equals(mobile)) {
				return GiftResult.MBAY_GIFT_RECIEVERD_YOURSELF;
			}
			// 他人领取
			return GiftResult.MBAY_GIFT_RECIEVERD;
		}
		
		// 禁止自己领取
		if (gift.getSender().equals(mobile)) {
			return GiftResult.SELF_RECIEVE_FORBID;
		}
		
		// 检测领取次数是否超出
		if (mbayGiftLimitService.find().getLimit()) {
			Result res = mbayGiftService.checkParticipable(mobile);
			if (!res.getStatus()) {
				return res;
			}
		}
		
		// 领取
		return mbayGiftService.grab(gift.getId(), decSNum, mobile);
	}
	
	/**
	 * 领取成功
	 * 
	 * @return
	 */
	@RequestMapping("mbay/gift/grab/success")
	public String mbayGiftGrabSuccess(
			@RequestParam("serialNumber") String serialNumber,
			@RequestParam("cnumber") String cnumber,
			@RequestParam("mobile") String mobile,
			Model model) {
		TrafficRedCampaign campaign = (TrafficRedCampaign) getCampaignInfo(
				cnumber, false, false, true, false);
		String decSNum = DigestUtils.pbeDecrypt(serialNumber);
		
		RIResponse trResp = null;
		try {
			trResp = RITradeRecordUtil
					.requestGetTradeRecordBySerialNum(decSNum);
		} catch (Exception e) {
			LOGGER.error("lookup trade record error:{}", e.getMessage());
		}
		Double amount = trResp.getData().getJSONObject("result")
				.getDouble("amount");
				
		model.addAttribute("mobile", mobile);
		model.addAttribute("serialNumber", serialNumber);
		model.addAttribute("cnumber", cnumber);
		model.addAttribute("size", amount.intValue());
		model.addAttribute("walletIndex", Wallet.INDEX + "?mobile="
				+ mobile);
		model.addAttribute("duiba", getDuibaUrl(campaign.getMall().getId()));
		model.addAttribute("partUrl", campaign.getMbayGiftConfig()
				.getParticipationLink());
		return "/traffic_red/mobile/shake/gift-success";
	}
	
	/**
	 * 领取失败
	 * 
	 * @return
	 */
	@RequestMapping("mbay/gift/grab/fail")
	public String mbayGiftGrabFail(
			@RequestParam("code") String code,
			@RequestParam("serialNumber") String serialNumber,
			@RequestParam("cnumber") String cnumber,
			@RequestParam("mobile") String mobile,
			Model model) {
		TrafficRedCampaign campaign = (TrafficRedCampaign) getCampaignInfo(
				cnumber, false, false, true, false);
				
		Result res = GiftResult.valueOf(code);
		model.addAttribute("share", GiftResult.TIMES_EXCEED_DAY.equals(res)
				|| GiftResult.TIMES_EXCEED_WEEK.equals(res)
				|| GiftResult.TIMES_EXCEED_MONTH.equals(res));
		model.addAttribute("msg", res.getContent());
		model.addAttribute("serialNumber", serialNumber);
		model.addAttribute("cnumber", cnumber);
		model.addAttribute("walletIndex", Wallet.INDEX + "?mobile="
				+ mobile);
		model.addAttribute("duiba", getDuibaUrl(campaign.getMall().getId()));
		model.addAttribute("partUrl", campaign.getMbayGiftConfig()
				.getParticipationLink());
		return "/traffic_red/mobile/shake/gift-fail";
	}
	
	/**
	 * 抽奖功能-跳转到立即抽奖的页面
	 * 
	 * @param cNumber
	 *            活动编号
	 * @return
	 */
	@RequestMapping("toRaffle")
	@CacheControl(policy = CachePolicy.NO_CACHE)
	public String toRaffle(@RequestParam("cNumber") String cNumber,
			HttpServletRequest request, Model model,
			HttpServletResponse response) {
		// 获取手机号
		String mobile = getCookieMobile(request, cNumber);
		if (mobile == null) {
			throw new ServiceException(
					TrafficRedMobileError.COOKIE_NOT_SUPPORTTED);
		}
		// 查找当前手机号是否存在抽奖记录
		TrafficRedRaffle raffle = raffleService.findByMobile(mobile);
		// 如果不存在或者当前时间不在有效期内则跳转到立即抽奖的页面（有验证码的页面）
		if (raffle == null || DateTime.now().isAfter(raffle.getValidTime())) {
			model.addAttribute("mobile", mobile);
			model.addAttribute("cNumber", cNumber);
			return "/traffic_red/mobile/shake/raffle";
		}
		// 记录到数据库中,续加72小时
		boolean result = raffleService
				.updateByIdSelective(dealRaffle(request, raffle));
		if (!result) {
			LOGGER.error("更新抽奖记录失败,mobile:" + raffle.getMobile());
		}
		// 抽奖商城地址
		String url = getDuibaUrl(
				Integer.parseInt(ChannelConfig.getRaffleMallId()));
		try {
			response.sendRedirect(url);
			return null;
		} catch (IOException e) {
			LOGGER.error("直接跳转到抽奖商城页面失败", e.fillInStackTrace());
			throw new ServiceException(
					TrafficRedMobileError.COOKIE_NOT_SUPPORTTED);
		}
	}
	
	/**
	 * 抽奖功能-生成验证码
	 * 
	 * @param mobile
	 * @param request
	 */
	@RequestMapping("send_authcode")
	public void send_authcode(@RequestParam("cNumber") String cNumber,
			HttpServletRequest request) {
		// 获取手机号
		String mobile = getCookieMobile(request, cNumber);
		if (mobile == null) {
			throw new ServiceException(
					TrafficRedMobileError.COOKIE_NOT_SUPPORTTED);
		}
		// 发送验证码
		String code = SMSAuthCodeUtil
				.generateAuthCode(CaptchaSMSType.CAPTCHA_COMMON, mobile);
		MbaySMS.sendCaptchaSMS(CaptchaSMSType.CAPTCHA_COMMON, mobile, code);
	}
	
	/**
	 * 抽奖功能-生成语音验证码
	 * 
	 * @param request
	 */
	@RequestMapping("send_voiceyzm")
	public void sendVoiceYzm(@RequestParam("cNumber") String cNumber,
			HttpServletRequest request) {
		// 获取手机号
		String mobile = getCookieMobile(request, cNumber);
		if (mobile == null) {
			throw new ServiceException(
					TrafficRedMobileError.COOKIE_NOT_SUPPORTTED);
		}
		String code = SMSAuthCodeUtil
				.generateAuthCode(CaptchaSMSType.CAPTCHA_COMMON, mobile);
		MbaySMS.sendVoiceCode(mobile, code);
	}
	
	/**
	 * 抽奖功能-立即抽奖
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("raffle")
	@ResponseBody
	public ExecuteResult raffle(@RequestParam("cNumber") String cNumber,
			@RequestParam("authcode") String authcode,
			HttpServletRequest request,
			Model model,
			HttpServletResponse response) {
		// 获取手机号
		String mobile = getCookieMobile(request, cNumber);
		if (mobile == null) {
			throw new ServiceException(
					TrafficRedMobileError.COOKIE_NOT_SUPPORTTED);
		}
		
		CaptchaResult authResult = SMSAuthCodeUtil
				.isAuthCodeValid(CaptchaSMSType.CAPTCHA_COMMON, authcode,
						mobile);
		if (!authResult.isSuccess()) {
			return new ExecuteResult(false, authResult.getMessage());
		}
		SMSAuthCodeUtil.removeAuthCode(CaptchaSMSType.CAPTCHA_COMMON);
		
		// 查找当前手机号是否存在抽奖记录
		TrafficRedRaffle raffle = raffleService.findByMobile(mobile);
		if (raffle == null) {
			raffle = new TrafficRedRaffle();
			raffle.setId(0L);
		}
		// 记录到数据库中
		raffle.setMobile(mobile);
		boolean result = false;
		if (raffle.getId() == 0) {
			result = raffleService.create(dealRaffle(request, raffle));
		} else {
			result = raffleService
					.updateByIdSelective(dealRaffle(request, raffle));
		}
		if (!result) {
			LOGGER.error("创建或者抽奖记录失败,mobile:" + mobile);
			return new ExecuteResult(false, "系统繁忙,请稍后重试!");
		}
		return new ExecuteResult(true,
				getDuibaUrl(Integer.parseInt(ChannelConfig.getRaffleMallId())));
	}
	
	/**
	 * 处理抽奖信息(设置有效期、ip)
	 * 
	 * @param request
	 * @param raffle
	 * @return
	 */
	private TrafficRedRaffle dealRaffle(HttpServletRequest request,
			TrafficRedRaffle raffle) {
		String reqIp = RequestUtil.getIP(request);
		LOGGER.info("抽奖 mobile:" + raffle.getMobile() + "/client-ip:" + reqIp);
		raffle.setIp(reqIp);
		raffle.setValidTime(
				DateTime.now().plusHours(TrafficRedConstant.RAFFLE_VALID_TIME));
		return raffle;
	}
	
}
