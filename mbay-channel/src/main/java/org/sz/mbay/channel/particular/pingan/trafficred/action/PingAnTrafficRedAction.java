package org.sz.mbay.channel.particular.pingan.trafficred.action;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.sz.mbay.base.config.ResourceConfig;
import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.base.exception.ServiceException;
import org.sz.mbay.base.regex.pattern.RegExp;
import org.sz.mbay.base.web.utils.RequestUtil;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.context.ChannelConfig;
import org.sz.mbay.channel.costant.ChannelConstant.Wallet;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.common.util.MbayDateFormat;
import org.sz.mbay.hcode.MbayHcode;
import org.sz.mbay.hcode.bean.HcodeInfo;
import org.sz.mbay.particularcase.pingan.trafficred.bean.PingAnRecord;
import org.sz.mbay.particularcase.pingan.trafficred.enums.MbayStatus;
import org.sz.mbay.particularcase.pingan.trafficred.enums.PingAnStatus;
import org.sz.mbay.particularcase.pingan.trafficred.qo.PingAnRecordForm;
import org.sz.mbay.particularcase.pingan.trafficred.service.PingAnRecordService;
import org.sz.mbay.remote.interfaces.wallet.RIUserUtil;
import org.sz.mbay.trafficred.bean.TimeQuantum;
import org.sz.mbay.trafficred.bean.TrafficRedCampaign;
import org.sz.mbay.trafficred.bean.TrafficRedMbayGiftConfig;
import org.sz.mbay.trafficred.bean.TrafficRedShareInfo;
import org.sz.mbay.trafficred.bean.TrafficRedTemplate;
import org.sz.mbay.trafficred.constant.error.TrafficRedMobileError;
import org.sz.mbay.trafficred.dto.ResultDTO;
import org.sz.mbay.trafficred.enums.SharkCycleType;
import org.sz.mbay.trafficred.redeem.mb.RedeemMbay;
import org.sz.mbay.trafficred.result.GiftResult;
import org.sz.mbay.trafficred.result.RedeemDataResult;
import org.sz.mbay.trafficred.result.RedeemResult;
import org.sz.mbay.trafficred.result.Result;
import org.sz.mbay.trafficred.service.TrafficRedCampaignService;
import org.sz.mbay.trafficred.service.TrafficRedExchangeRecordService;
import org.sz.mbay.trafficred.service.TrafficRedMbayGiftConfigService;
import org.sz.mbay.trafficred.service.TrafficRedShareInfoService;
import org.sz.mbay.trafficred.service.TrafficRedTmplService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("pingan/trafficred")
public class PingAnTrafficRedAction {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PingAnTrafficRedAction.class);
			
	private static final String ERROR_MSG = "errorMsg";
	private static final String WALLET_INDEX = "walletIndex";
	private static final String INDEX = "particularcase/pingan/trafficred/index";
	private static final String ERROR_PAGE = "/particularcase/pingan/trafficred/error";
	private static final String SYSTEM_ERROR = "系统繁忙,请稍后重试";
	
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
	private TrafficRedMbayGiftConfigService mbayGiftConfigService;
	@Autowired
	private PingAnRecordService recordService;
	
	/**
	 * 输入手机号页面
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return INDEX;
	}
	
	/**
	 * otp对接
	 * 
	 * @param mobile
	 * @param model
	 * @param response
	 * @return
	 */
	@RequestMapping("/otp")
	public String otp(@RequestParam(value = "mobile") String mobile,
			Model model,
			HttpServletResponse response) {
		if (StringUtils.isEmpty(mobile)) {
			model.addAttribute(ERROR_MSG, "手机号不能为空");
			return INDEX;
		}
		if (!RegExp.mobile.matcher(mobile).matches()) {
			model.addAttribute(ERROR_MSG, "请输入正确的手机号");
			return INDEX;
		}
		
		// 自动注册
		try {
			RIUserUtil.requestWalletAutoReg(mobile, "平安好房");
		} catch (Exception e) {
			LOGGER.error("自动注册【{}】：code->{}, content->{}", mobile,
					RedeemResult.AUTO_REGISTER_ERROR.getCode(),
					RedeemResult.AUTO_REGISTER_ERROR.getContent());
			model.addAttribute(ERROR_MSG, SYSTEM_ERROR);
			return INDEX;
		}
		
		try {
			response.sendRedirect(
					"https://member.pinganfang.com/v2/event/marry/cpaindex?sfrom=cpa&mobile="
							+ mobile);
			return null;
		} catch (IOException e) {
			LOGGER.error("跳转平安otp接口失败", e.fillInStackTrace());
			model.addAttribute(ERROR_MSG, SYSTEM_ERROR);
			return INDEX;
		}
	}
	
	/**
	 * 创建回调记录
	 * 
	 * @return
	 */
	@RequestMapping("/link/callback")
	@ResponseBody
	public JSONObject linkCreate(@RequestParam(value = "mobile") String mobile,
			@RequestParam(value = "status") String iStatus) {
		PingAnRecord bean = new PingAnRecord();
		bean.setMobile(mobile);
		bean.setPingAnStatus(PingAnStatus.values()[Integer.valueOf(iStatus)]);
		bean.setMbayStatus(MbayStatus.NOT);
		bean.setCreateTime(DateTime.now());
		// 生成回调记录,死循环处理，保证回调记录必定生成
		while (true) {
			ExecuteResult result = recordService.add(bean);
			if (result.isSuccess()) {
				break;
			}
		}
		LOGGER.info("平安回调请求:mobile->{}, pingAnStatus->{}", mobile, iStatus);
		JSONObject json = new JSONObject();
		json.put("success", true);
		json.put("message", "回调成功");
		return json;
	}
	
	/**
	 * 判断是否生成回调记录
	 * 
	 * @return
	 */
	@RequestMapping("/link/judge")
	public String linkJudge(@RequestParam(value = "mobile") String mobile,
			Model model,
			RedirectAttributes attrs) {
		PingAnRecordForm form = new PingAnRecordForm();
		form.setMobile(mobile);
		form.setMbayStatus(MbayStatus.NOT);
		List<PingAnRecord> list = recordService.findList(form, null);
		if (list == null || list.size() == 0) {
			LOGGER.error("没有生成回调记录,mobile->{}", mobile);
			model.addAttribute(WALLET_INDEX,
					Wallet.INDEX + "?mobile=" + mobile);
			model.addAttribute(ERROR_MSG, SYSTEM_ERROR);
			return ERROR_PAGE;
		}
		PingAnRecord bean = list.get(0);
		if (!bean.getPingAnStatus().equals(PingAnStatus.SUCCESS)) {
			LOGGER.error("平安回调状态:mobile->{}; pingAnStatus->{}", mobile,
					bean.getPingAnStatus().getValue());
			model.addAttribute(WALLET_INDEX,
					Wallet.INDEX + "?mobile=" + mobile);
			model.addAttribute(ERROR_MSG, "您已注册过啦,不能多次领取MB");
			return ERROR_PAGE;
		}
		// 存在回调记录会更改处理状态,死循环处理，保证回调记录必定会改成已处理状态
		while (true) {
			ExecuteResult result = recordService.updateMbayStatus(bean.getId(),
					MbayStatus.FINISH);
			if (result.isSuccess()) {
				break;
			}
		}
		attrs.addAttribute("mobile", mobile);
		return "redirect:/pingan/trafficred/result_mbay_pack.mbay";
	}
	
	/**
	 * 摇到美贝流量处理
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("result_mbay_pack")
	public String resultMbayPack(
			@RequestParam("mobile") String mobile,
			HttpServletRequest request,
			HttpServletResponse response,
			Model model) {
		// 读取配置文件活动号
		String cnumber = ResourceConfig
				.getProperty("pingan_trafficred_campaign_number");
				
		// 号码验证
		Matcher matcher = RegExp.mobile.matcher(mobile);
		if (!matcher.matches()) {
			LOGGER.error("平安好房-流量红包【{}/{}】：code->{}, content->{}", cnumber,
					mobile,
					RedeemResult.MOBILE_INCORRECT.getCode(),
					RedeemResult.MOBILE_INCORRECT.getContent());
			model.addAttribute(WALLET_INDEX,
					Wallet.INDEX + "?mobile=" + mobile);
			model.addAttribute(ERROR_MSG, SYSTEM_ERROR);
			return ERROR_PAGE;
		}
		
		// hcode验证
		HcodeInfo hcode = MbayHcode.getHcodeInfo(mobile);
		if (hcode == null) {
			LOGGER.error("平安好房-流量红包【{}/{}】：code->{}, content->{}", cnumber,
					mobile,
					RedeemResult.MOBILE_HCODE_NOT_FOUND.getCode(),
					RedeemResult.MOBILE_HCODE_NOT_FOUND.getContent());
			model.addAttribute(WALLET_INDEX,
					Wallet.INDEX + "?mobile=" + mobile);
			model.addAttribute(ERROR_MSG, SYSTEM_ERROR);
			return ERROR_PAGE;
		}
		
		// 查询活动信息
		TrafficRedCampaign campaign = (TrafficRedCampaign) getCampaignInfo(
				cnumber, true, true, false, false);
				
		// 活动信息验证
		Result ckRes = checkRelatedInfos(campaign, mobile);
		if (!ckRes.getStatus()) {
			LOGGER.error("平安好房-流量红包【{}/{}】：code->{}, content->{}", cnumber,
					mobile,
					ckRes.getCode(), ckRes.getContent());
			model.addAttribute(WALLET_INDEX,
					Wallet.INDEX + "?mobile=" + mobile);
			model.addAttribute(ERROR_MSG, SYSTEM_ERROR);
			return ERROR_PAGE;
		}
		
		// 模拟摇一摇生成数据
		Result rRes = RedeemMbay.redeem(campaign.getId(), mobile);
		if (!rRes.getStatus()) {
			LOGGER.error("平安好房-流量红包【{}/{}】：code->{}, content->{}", cnumber,
					mobile,
					rRes.getCode(), rRes.getContent());
			model.addAttribute(WALLET_INDEX,
					Wallet.INDEX + "?mobile=" + mobile);
			model.addAttribute(ERROR_MSG, SYSTEM_ERROR);
			return ERROR_PAGE;
		}
		ResultDTO data = (ResultDTO) ((RedeemDataResult) rRes).getData();
		
		// 生成cookie
		String cnumber_encrypt = DigestUtils.pbeEncrypt(cnumber);
		Cookie cookie = RequestUtil.getCookie(request, cnumber_encrypt);
		if (cookie == null) {
			RequestUtil.setCookie(response, cnumber_encrypt,
					DigestUtils.pbeEncrypt(mobile), 30 * 24 * 60 * 60);
		}
		
		// 返回数据
		model.addAttribute("size",
				String.valueOf(data.getSize()).toCharArray());
		model.addAttribute("cnumber", DigestUtils.pbeEncrypt(cnumber));
		model.addAttribute("serialNumber", data.getSerialNumber());
		model.addAttribute("surprise", getSurprise(campaign
				.getTemplate().getLogoCycleLink(), mobile));
		model.addAttribute("duiba", getDuibaUrl(campaign.getMall().getId()));
		model.addAttribute("walletIndex", Wallet.INDEX + "?mobile=" + mobile);
		model.addAttribute("campaignName", campaign.getName());
		model.addAttribute("mbayTraffic", data.getSize());
		model.addAttribute("remindShare", false);
		model.addAttribute("continuable", false);
		
		return "/traffic_red/mobile/shake/result_mbay_pack";
	}
	
	/*
	 * 获取活动完整信息
	 */
	private Object getCampaignInfo(String cnumber, boolean loadTemplate,
			boolean loadShareInfo, boolean loadGiftConfig,
			boolean isAjax) {
		if (StringUtils.isEmpty(cnumber)) {
			if (isAjax) {
				return RedeemResult.CAMPAIGN_NUMBER_ERROR;
			} else {
				throw new ServiceException(
						TrafficRedMobileError.CAMPAIGN_NUMBER_ERROR);
			}
		}
		
		TrafficRedCampaign campaign = campaignService
				.findCampaignByNumber(cnumber);
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
	
	/*
	 * 兑吧商城链接
	 */
	private String getDuibaUrl(int mallId) {
		String duiba_url = Wallet.WL_DUIBA + "&mallId="
				+ (mallId == 0 ? ChannelConfig.getMallId()
						: String.valueOf(mallId));
		return duiba_url;
	}
	
	/*
	 * 检测相关参数
	 */
	private Result checkRelatedInfos(TrafficRedCampaign campaign,
			String mobile) {
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
}
