package org.sz.mbay.channel.action;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.sz.mbay.base.annotation.CacheControl;
import org.sz.mbay.base.annotation.CachePolicy;
import org.sz.mbay.base.annotation.Token;
import org.sz.mbay.base.constant.Globals;
import org.sz.mbay.base.context.WebContext;
import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.base.exception.ServiceException;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.web.servlet.tags.token.TokenProcessor;
import org.sz.mbay.base.web.utils.RequestUtil;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.component.excel.EXCELType;
import org.sz.mbay.channel.component.excel.POIExcel;
import org.sz.mbay.channel.context.ChannelConfig;
import org.sz.mbay.channel.context.ChannelContext;
import org.sz.mbay.channel.costant.ChannelConstant.Wallet;
import org.sz.mbay.channel.costant.error.Error;
import org.sz.mbay.channel.framework.action.BaseAction;
import org.sz.mbay.channel.user.enums.CertStatus;
import org.sz.mbay.channel.user.service.MBAccountService;
import org.sz.mbay.channel.user.service.UserAccountService;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.common.util.MbayDateFormat;
import org.sz.mbay.common.util.MbayDateFormat.DateFormatter;
import org.sz.mbay.common.util.MbayDateFormat.DatePattern;
import org.sz.mbay.duiba.bean.DuiBaRelationShip;
import org.sz.mbay.duiba.service.DuiBaRelationShipService;
import org.sz.mbay.fs.FSClient;
import org.sz.mbay.fs.FSClientFactory;
import org.sz.mbay.fs.FSClientFactory.ClientType;
import org.sz.mbay.fs.fastdfs.FDFSFileInfo;
import org.sz.mbay.operator.dao.OperatorDao;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.promotion.bean.PromotionCampaign;
import org.sz.mbay.promotion.bean.PromotionCampaignAdvanced;
import org.sz.mbay.promotion.bean.PromotionCampaignMbay;
import org.sz.mbay.promotion.bean.PromotionCampaignPackage;
import org.sz.mbay.promotion.bean.PromotionCampaignShare;
import org.sz.mbay.promotion.bean.PromotionCampaignTemplate;
import org.sz.mbay.promotion.bean.PromotionProductConfig;
import org.sz.mbay.promotion.bean.RedeemCode;
import org.sz.mbay.promotion.datecal.CalendarRangeContext;
import org.sz.mbay.promotion.enums.CodeEndType;
import org.sz.mbay.promotion.enums.LimitType;
import org.sz.mbay.promotion.enums.ModelType;
import org.sz.mbay.promotion.error.PromotionCampaignError;
import org.sz.mbay.promotion.qo.CampaignForm;
import org.sz.mbay.promotion.qo.RedeemCodeForm;
import org.sz.mbay.promotion.service.PromotionCampaignMbayService;
import org.sz.mbay.promotion.service.PromotionCampaignPackageService;
import org.sz.mbay.promotion.service.PromotionCampaignService;
import org.sz.mbay.promotion.service.PromotionCampaignShareService;
import org.sz.mbay.promotion.service.PromotionProductConfigService;
import org.sz.mbay.promotion.service.RedeemCodeService;
import org.sz.mbay.remote.interfaces.wallet.RIMBAccountUtil;
import org.sz.mbay.remote.interfaces.wallet.base.RIResponse;
import org.sz.mbay.sms.client.MbaySMS;
import org.sz.mbay.sms.template.enums.SMSType;
import org.sz.mbay.trafficred.bean.TrafficRedMbay;
import org.sz.mbay.trafficred.bean.TrafficRedPackage;
import org.sz.mbay.trafficred.dto.TrafficRedMbayDTO;
import org.sz.mbay.trafficred.dto.TrafficRedPackageDTO;
import org.sz.mbay.trafficred.enums.ProductType;
import org.sz.mbay.trafficred.result.RedeemResult;
import org.sz.mbay.trafficred.service.TrafficRedMbayService;
import org.sz.mbay.trafficred.service.TrafficRedPackageService;

import net.sf.json.JSONObject;

/**
 * 促销神器：商户通过制定促销日期，有效期，并选择流量策略，生成兑换码发放给C用户。C用户通过此兑换码前往指定地方兑换流量，达到营销效果。
 * 促销神器活动原对活动的定义用的是Event单词
 * ，后经专业人士指出，改为Campaign单词，所以对于此Controller中的Event单词后续有时间会统一改为Campaign。
 * 
 * @Description: 促销神器Handler类
 * @author han.han
 * @date 2014-10-29 下午4:09:36
 * 		
 */
@Controller
@RequestMapping("promotionCampaign")
public class PromotionCampaignAction extends BaseAction {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PromotionCampaignAction.class);
			
	/**
	 * 微信营销工作平台
	 */
	public static final String WORKBENCH = "promotion_campaign/workbench";
	/**
	 * 活动列表
	 */
	public static final String CAMPAIGN_LIST = "promotion_campaign/campaign_list";
	/**
	 * 活动添加页面
	 */
	public static final String CAMPAIGN_ADD = "promotion_campaign/campaign_add";
	/**
	 * 重定向到选择活动模式页面
	 */
	public static final String REDIRECT_TO_SET_MODEL = "redirect:/promotionCampaign/to_set_model.mbay";
	/**
	 * 选择活动模式页面
	 */
	public static final String SET_MODEL = "promotion_campaign/set_model";
	/**
	 * 重定向到兑换模板
	 */
	public static final String REDIRECT_TO_SET_TEMPLATE = "redirect:/promotionCampaign/to_set_template.mbay";
	/**
	 * 编辑兑换模板
	 */
	public static final String SET_TEMPLATE = "promotion_campaign/set_template";
	/**
	 * 重定向到开发者模式页面
	 */
	public static final String REDIRECT_TO_SET_ADVANCED = "redirect:/promotionCampaign/to_set_advanced.mbay";
	/**
	 * 开发者模式页面
	 */
	public static final String SET_ADVANCED = "promotion_campaign/set_advanced";
	/**
	 * 重定向至设置模板成功页面
	 */
	public static final String REDIRECT_SET_TEMPLATE_SUCCESS = "redirect:/promotionCampaign/set_template_success.mbay";
	/**
	 * 到设置模板成功页面
	 */
	public static final String SET_TEMPLATE_SUCCESS = "promotion_campaign/set_template_success";
	/**
	 * 重定向至活动详情页面
	 */
	public static final String REDIRECT_TO_CAMPAIGN_INFO = "redirect:/promotionCampaign/campaign_info.mbay";
	/**
	 * 活动详情
	 */
	public static final String CAMPAIGN_INFO = "promotion_campaign/campaign_info";
	/**
	 * 活动详情-模板页面
	 */
	public static final String CAMPAIGN_INFO_TEMPLATE = "promotion_campaign/campaign_info_template";
	/**
	 * 活动详情-开发者中心
	 */
	public static final String CAMPAIGN_INFO_ADVANCED = "promotion_campaign/campaign_info_advanced";
	/**
	 * 重定向至活动详情-模板页面
	 */
	public static final String REDIRECT_TO_CAMPAIGN_INFO_TEMPLATE = "redirect:/promotionCampaign/to_campaign_info_template.mbay?message=success";
	/**
	 * 活动已发放的兑换码
	 */
	public static final String CODE_LIST = "promotion_campaign/code_list";
	/**
	 * 领取兑换码
	 */
	public static final String TO_GET_REDEECODE = "promotion_campaign/mobile/get_redeemcode_1";
	/**
	 * 兑换兑换码
	 */
	public static final String TO_REDEEM = "promotion_campaign/mobile/redeem_1";
	/**
	 * 兑换码说明页面
	 */
	public static final String TO_REDEEM_INSTRUCTION = "promotion_campaign/mobile/traffic_redeem_instruction";
	/**
	 * 失败页面
	 */
	public static final String TRAFFIC_FAIL = "wechat/trafficFail";
	/**
	 * 重定向到立即领取页面
	 */
	public static final String REDIRECT_GET = "redirect:/promotionCampaign/redeem_code/get_immediately.mbay";
	/**
	 * 重定向到立即兑换页面
	 */
	public static final String REDIRECT_REDEEM = "redirect:/promotionCampaign/redeem_code/redeem_immediately.mbay";
	/**
	 * 领取页面防盗链session下attribute名称
	 */
	public static final String PROMOTIONGETKEY = "promotionGetKey";
	/**
	 * 兑换页面防盗链session下attribute名称
	 */
	public static final String PROMOTIONREDEEMKEY = "promotionRedeemKey";
	/**
	 * 批量导入页面
	 */
	public static final String TO_IMPORT_ALL = "promotion_campaign/import_all";
	/**
	 * 重定向至批量导入页面
	 */
	public static final String REDIRECT_TO_IMPORT_ALL = "redirect:/promotionCampaign/toImportAll.mbay";
	
	@Autowired
	PromotionCampaignService promotionService;
	@Autowired
	RedeemCodeService redeemCodeService;
	@Autowired
	UserAccountService userAccountService;
	@Autowired
	OperatorDao operatroDao;
	@Autowired
	TrafficRedMbayService mbayService;
	@Autowired
	TrafficRedPackageService packageService;
	@Autowired
	PromotionCampaignMbayService campaignMbayService;
	@Autowired
	PromotionCampaignPackageService campaignPackageService;
	@Autowired
	PromotionProductConfigService productConfigService;
	@Autowired
	DuiBaRelationShipService relationService;
	@Autowired
	PromotionCampaignShareService shareService;
	@Autowired
	MBAccountService mbayAccountService;
	
	/**
	 * fastdfs文件管理
	 */
	private FSClient fsClient = FSClientFactory.getClient(ClientType.FDFS);
	
	/**
	 * 跳转至促销神器管理平台
	 * 
	 * @return
	 */
	@RequestMapping("workbench")
	public String workbench() {
		return WORKBENCH;
	}
	
	/**
	 * 活动列表
	 * 
	 * @return
	 */
	@RequestMapping("campaign_list")
	public String campaign_list(CampaignForm form, Model model,
			PageInfo pageinfo, HttpServletRequest request) {
		if (!ChannelContext.getSessionChannel().getCertStatus()
				.equals(CertStatus.APPROVED)) {
			return PUBLIC_AUTHMSG_NOTE;
		}
		String starttime = request.getParameter("eventstarttime");
		if (starttime != null && starttime.length() > 0) {
			form.setStarttime(MbayDateFormat.stringToTime(starttime
					+ " 00:00:00"));
		}
		String endtime = request.getParameter("eventendtime");
		if (endtime != null && endtime.length() > 0) {
			form.setEndtime(MbayDateFormat.stringToTime(endtime + " 23:59:59"));
		}
		form.setUsernumber(ChannelContext.getSessionChannel().getUserNumber());
		List<PromotionCampaign> list = this.promotionService
				.findAllPromotionCampaign(form, pageinfo);
		model.addAttribute("listevent", list);
		model.addAttribute("event", form);
		model.addAttribute(Globals.PAGEINFO_KEY, pageinfo);
		return CAMPAIGN_LIST;
	}
	
	/**
	 * 前往促销活动创建
	 * 
	 * @return
	 */
	@RequestMapping("to_campaign_add")
	@Token(save = true)
	@CacheControl(policy = CachePolicy.NO_CACHE)
	public String to_campaign_add(Model model) {
		// 获取流量产品
		model.addAttribute("MOBILE", initTrafficPack(OperatorType.MOBILE));
		model.addAttribute("TELECOM", initTrafficPack(OperatorType.TELECOM));
		model.addAttribute("UNICOM", initTrafficPack(OperatorType.UNICOM));
		
		// 获取MB产品
		model.addAttribute("MBAYLIST", initMbay());
		// 添加活动页面显示短信模板信息
		model.addAttribute("sms_tempate",
				MbaySMS.getSMSContent(SMSType.ACTIVITY));
		model.addAttribute("sms_threshold",
				MbaySMS.getSMSContent(SMSType.TR_THRESHOLD_WARNING));
				
		// 获取商城
		List<DuiBaRelationShip> relationList = relationService
				.findList(ChannelContext.getSessionChannel().getUserNumber(),
						null);
		model.addAttribute("relationList", relationList);
		return CAMPAIGN_ADD;
	}
	
	/**
	 * 创建活动
	 * 
	 * @return
	 */
	@RequestMapping("campaign_add")
	@Token(reset = true)
	public String campaign_add(@Validated PromotionCampaign campaign,
			BindingResult br, Model model, RedirectAttributes redAttr,
			@RequestParam("eventstarttime") String starttime,
			@RequestParam("eventendtime") String endtime,
			@RequestParam("traffic_products") String trafficIds,
			@RequestParam("mbay_products") String mbayIds,
			@RequestParam(value = "shareLink",
					required = false) String shareLink,
			@RequestParam(value = "shareTitle",
					required = false) String shareTitle,
			@RequestParam(value = "content", required = false) String content,
			@RequestParam(value = "shareTimes",
					required = false) String shareTimes,
			@RequestParam(value = "shareImage",
					required = false) MultipartFile shareImage,
			HttpServletRequest request) {
		if (br.hasErrors()) {
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, Error.FORM_VALIDATE_ERROR);
			TokenProcessor.getInstance().saveToken(request);
			return to_campaign_add(model);// 返回至申请页面
		}
		// 活动开始时间
		campaign.setStarttime(DateTime.parse(starttime + " 00:00:00",
				DateFormatter.timeFormat));
		// 活动结束时间
		campaign.setEndingtime(DateTime.parse(endtime + " 23:59:59",
				DateFormatter.timeFormat));
		campaign.setUsernumber(
				ChannelContext.getSessionChannel().getUserNumber());
		// 是否开启核销码
		boolean verificate = request.getParameter("verificate") != null;
		campaign.setVerificate(verificate);
		// 是否发送短信
		boolean sendsms = request.getParameter("sendsms") != null;
		campaign.setSendsms(sendsms);
		// 是否超出继续发放
		boolean continuable = request.getParameter("continuable") != null;
		campaign.setContinuable(continuable);
		// 是否分享
		boolean share = request.getParameter("share") != null;
		campaign.setShare(share);
		// 设置分享信息
		PromotionCampaignShare shareInfo = dealShareInfo(share, shareLink,
				shareTitle, content, shareTimes, shareImage);
				
		// 根据选择产品设置概率
		if (StringUtils.isEmpty(trafficIds)) {
			campaign.setTrafficRate(Double.valueOf("0"));
		} else if (StringUtils.isEmpty(mbayIds)) {
			campaign.setTrafficRate(Double.valueOf("100"));
		}
		// 解析流量产品
		campaign.setPackages(analyseTraffic(trafficIds));
		// 解析美贝产品
		campaign.setMbays(analyseMbay(mbayIds));
		
		// 封装产品基本信息
		List<PromotionProductConfig> configs = new ArrayList<PromotionProductConfig>();
		String[] selectedProducts = request
				.getParameterValues("selected_product");
		for (String productType : selectedProducts) {
			ProductType type = ProductType.valueOf(productType);
			PromotionProductConfig config = new PromotionProductConfig();
			config.setProductType(type);
			String typeStr = type.name();
			config.setPoolSize(Double
					.valueOf(request.getParameter(typeStr + "_poolsize")));
			config.setPoolRemain(config.getPoolSize());
			String dailyLimitStr = request
					.getParameter(typeStr + "_dailylimit");
			double dailyLimit = "".equals(dailyLimitStr)
					? PromotionProductConfig.TRAFFIC_UNLIMIT
					: Double.valueOf(dailyLimitStr);
			config.setDailyLimit(dailyLimit);
			config.setDailyRemain(config.getDailyLimit());
			String thresholdStr = request.getParameter(typeStr + "_threshold");
			int threshold = "".equals(thresholdStr)
					? PromotionProductConfig.TRAFFIC_UNLIMIT
					: Integer.valueOf(thresholdStr);
			config.setThreshold(threshold);
			config.setThresholdWarned(false);
			configs.add(config);
		}
		
		// 执行创建
		String campaignNumber = this.promotionService.addCampaign(campaign,
				configs, shareInfo);
		redAttr.addAttribute("campaignNumber",
				DigestUtils.pbeEncrypt(campaignNumber));
		redAttr.addAttribute("type", ModelType.NON.name());
		return REDIRECT_TO_SET_MODEL;
	}
	
	/**
	 * 跳转至选择活动模式
	 * 
	 * @param request
	 * @param redAttr
	 * @param model
	 * @return
	 */
	@RequestMapping("to_set_model")
	public String to_set_model(
			@RequestParam("campaignNumber") String campaignNumber,
			Model model) {
		String eventnumber = DigestUtils.pbeDecrypt(campaignNumber);
		if (eventnumber == null) {
			throw new ServiceException(
					PromotionCampaignError.CAMPAIGN_NUMBER_ERROR);
		}
		PromotionCampaign campaign = promotionService
				.findPromotionCampaign(eventnumber);
		if (campaign == null) {
			throw new ServiceException(
					PromotionCampaignError.CAMPAIGN_NUMBER_ERROR);
		}
		model.addAttribute("campaignName", campaign.getEventname());
		model.addAttribute("campaignNumber", campaignNumber);
		return SET_MODEL;
	}
	
	/**
	 * 跳转至编辑活动模板
	 * 
	 * @return
	 */
	@RequestMapping("to_set_template")
	@CacheControl(policy = { CachePolicy.NO_CACHE })
	@Token(save = true)
	public String to_set_template(
			@RequestParam(value = "campaignNumber") String campaignNumber,
			@RequestParam("type") String type, Model model) {
		String eventnumber = DigestUtils.pbeDecrypt(campaignNumber);
		if (eventnumber == null) {
			throw new ServiceException(
					PromotionCampaignError.CAMPAIGN_NUMBER_ERROR);
		}
		PromotionCampaign campaign = promotionService
				.findPromotionCampaign(eventnumber);
		if (campaign == null) {
			throw new ServiceException(
					PromotionCampaignError.CAMPAIGN_NUMBER_ERROR);
		}
		model.addAttribute("campaignName", campaign.getEventname());
		model.addAttribute("eventnumber", campaignNumber);
		model.addAttribute("type", type);
		return SET_TEMPLATE;
		
	}
	
	/**
	 * 编辑兑换模板
	 * 
	 * @param bk
	 *            模板背景图片
	 * @param eventnumber
	 *            活动编号 经PBE加密
	 * @param redAttr
	 * @return
	 */
	@RequestMapping("set_template")
	@Token(reset = true)
	public String set_template(HttpServletRequest request, Model model,
			@RequestParam(value = "redeemBk",
					required = false) MultipartFile redeemBk,
			@RequestParam(value = "bk", required = false) MultipartFile bk,
			@RequestParam("eventnumber") String eventnumber,
			@RequestParam("type") String type,
			RedirectAttributes attrs,
			@RequestParam(value = "info", required = false) boolean info,                    // 判断是否是由详情页面提交过来的:true->是,false->否
			@RequestParam(value = "introduction") String introduction,
			@RequestParam(value = "gotText") String gotText,
			@RequestParam(value = "redeemText") String redeemText,
			@RequestParam(value = "introductionText") String introductionText) {
		String pbeeventnumber = eventnumber;
		eventnumber = DigestUtils.pbeDecrypt(eventnumber);
		if (eventnumber == null) {
			throw new ServiceException(
					PromotionCampaignError.CAMPAIGN_NUMBER_ERROR);
		}
		// 设置模板参数
		PromotionCampaignTemplate template = new PromotionCampaignTemplate();
		template.setEventnumber(eventnumber);
		template.setIntroduction(introduction.trim());
		template.setGotText(gotText);
		template.setRedeemText(redeemText);
		template.setIntroductionText(introductionText);
		// 领取页面背景图片上传
		if (bk != null && bk.getSize() > 0) {// 表示上传了图片
			String picture = fsClient.uploadFile(bk);
			if (!StringUtils.isEmpty(picture)) {
				template.setBackphoto(picture);
			} else {
				TokenProcessor.getInstance().resetToken(request);
				model.addAttribute(PUBLIC_MSG_NOTE_KEY, "领取页面背景图片上传失败");
				return to_set_template(pbeeventnumber, "TEMPLATE",
						model);
			}
		}
		// 兑换页面背景图片上传
		if (redeemBk != null && redeemBk.getSize() > 0) {// 表示上传了图片
			String picture = fsClient.uploadFile(redeemBk);
			if (!StringUtils.isEmpty(picture)) {
				template.setRedeemBackphoto(picture);
			} else {
				TokenProcessor.getInstance().resetToken(request);
				model.addAttribute(PUBLIC_MSG_NOTE_KEY, "兑换页面背景图片上传失败");
				return to_set_template(pbeeventnumber, "TEMPLATE",
						model);
			}
		}
		this.promotionService.setPromotionCampaignTemplate(template);
		// 跳转地址根据info取值来判断:true->详情页面,false->成功页面
		String link_url = REDIRECT_SET_TEMPLATE_SUCCESS;
		if (info) {
			link_url = REDIRECT_TO_CAMPAIGN_INFO_TEMPLATE;
		} else {
			// 判断是否是编辑模式,如果是则跳转到批量导入页面,不是则跳入开发者模式页面
			ModelType mt = ModelType.valueOf(type);
			if (mt.equals(ModelType.TEMPLATE)) {
				link_url = REDIRECT_TO_IMPORT_ALL;
			} else if (mt.equals(ModelType.ADVANCED)) {
				link_url = REDIRECT_TO_SET_ADVANCED;
			}
		}
		attrs.addAttribute("eventnumber", eventnumber);
		attrs.addAttribute("campaignNumber", pbeeventnumber);
		attrs.addAttribute("type", type);
		return link_url;
	}
	
	/**
	 * 创建模板成功
	 * 
	 * @param eventnumber
	 * @return
	 */
	@RequestMapping("set_template_success")
	public ModelAndView set_template_success(
			@RequestParam String campaignNumber) {
		campaignNumber = DigestUtils.pbeDecrypt(campaignNumber);
		if (campaignNumber == null) {
			throw new ServiceException(
					PromotionCampaignError.CAMPAIGN_NUMBER_ERROR);
		}
		PromotionCampaign campaign = promotionService
				.findPromotionCampaign(campaignNumber);
		if (campaign == null) {
			throw new ServiceException(
					PromotionCampaignError.CAMPAIGN_NUMBER_ERROR);
		}
		ModelAndView view = new ModelAndView(SET_TEMPLATE_SUCCESS);
		PromotionCampaignTemplate emodel = this.promotionService
				.findPromotionCampaignTemplate(campaignNumber);
		view.addObject("model", emodel);
		view.addObject("campaignName", campaign.getEventname());
		return view;
	}
	
	/**
	 * 开发者模式页面
	 * 
	 * @param eventnumber
	 * @param model
	 * @return
	 */
	@RequestMapping("to_set_advanced")
	public String to_set_advanced(
			@RequestParam("campaignNumber") String campaignNumber,
			Model model) {
		String eventnumber = DigestUtils.pbeDecrypt(campaignNumber);
		if (eventnumber == null) {
			throw new ServiceException(
					PromotionCampaignError.CAMPAIGN_NUMBER_ERROR);
		}
		PromotionCampaign campaign = promotionService
				.findPromotionCampaign(eventnumber);
		if (campaign == null) {
			throw new ServiceException(
					PromotionCampaignError.CAMPAIGN_NUMBER_ERROR);
		}
		model.addAttribute("campaignName", campaign.getEventname());
		model.addAttribute("campaignNumber", campaignNumber);
		return SET_ADVANCED;
	}
	
	/**
	 * 开发者模式信息提交
	 * 
	 * @param eventnumber
	 * @param redAttr
	 * @return
	 */
	@RequestMapping("set_advanced")
	@ResponseBody
	public ExecuteResult set_advanced(
			@RequestParam("campaignNumber") String campaignNumber,
			@RequestParam("token") String token) {
		campaignNumber = DigestUtils.pbeDecrypt(campaignNumber);
		if (campaignNumber == null) {
			return new ExecuteResult(false, "活动编号不正确!");
		}
		PromotionCampaignAdvanced advanced = this.promotionService
				.findCampaignAdvanced(campaignNumber);
		ExecuteResult result = null;
		if (advanced == null) {
			advanced = new PromotionCampaignAdvanced();
			advanced.setCampaignNumber(campaignNumber);
			advanced.setToken(token);
			advanced.setStatus(EnableState.ENABLED);
			result = promotionService.addCampaignAdvanced(advanced);
		} else {
			advanced.setToken(token);
			result = promotionService.updateCampaignAdvanced(advanced);
		}
		return result;
	}
	
	/**
	 * 活动详情
	 * 
	 * @param eventnumber
	 * @return
	 */
	@RequestMapping("campaign_info")
	public ModelAndView campaign_info(@RequestParam String eventnumber) {
		PromotionCampaign event = this.promotionService
				.findPromotionCampaign(eventnumber, ChannelContext
						.getSessionChannel().getUserNumber());
		if (event == null) {
			throw new ServiceException(
					PromotionCampaignError.CAMPAIGN_NUMBER_ERROR);
		}
		
		PromotionCampaignTemplate emodel = this.promotionService
				.findPromotionCampaignTemplate(eventnumber);
		
		PromotionCampaignShare shareInfo = null;
		if(event.isShare()){
			shareInfo = shareService.
					findByCampaignNumber(eventnumber);
		}
		
		ModelAndView view = new ModelAndView(CAMPAIGN_INFO);
		view.addObject("status",
				promotionService.findCampaignStatus(eventnumber, ChannelContext
						.getSessionChannel().getUserNumber()));
		view.addObject("event", event);
		view.addObject("eventnumber",
				DigestUtils.pbeEncrypt(event.getEventnumber()));
		view.addObject("redeemurl",
				emodel == null ? "" : emodel.getRedeemurl());
		view.addObject("weburl", emodel == null ? "" : emodel.getEventurl());
		
		if(event.isShare()){
			FDFSFileInfo fileInfo = (FDFSFileInfo) fsClient
					.getFileInfo(shareInfo.getShareImage());
			shareInfo.setShareImage(fileInfo.getFullPath());
			view.addObject("shareInfo", shareInfo);
		}
		return view;
	}
	
	/**
	 * 活动详情下模板信息页面
	 * 
	 * @param eventnumber
	 * @param model
	 * @return
	 */
	@RequestMapping("to_campaign_info_template")
	@Token(save = true)
	@CacheControl(policy = { CachePolicy.NO_CACHE })
	public String to_campaign_info_template(
			@RequestParam("eventnumber") String eventnumber,
			Model model) {
		if (!this.promotionService.isExistCampaign(eventnumber, ChannelContext
				.getSessionChannel().getUserNumber())) {
			throw new ServiceException(
					PromotionCampaignError.CAMPAIGN_NUMBER_ERROR);
		}
		PromotionCampaign campaign = promotionService
				.findPromotionCampaign(eventnumber);
		model.addAttribute("campaignName", campaign.getEventname());
		model.addAttribute("status",
				promotionService.findCampaignStatus(eventnumber, ChannelContext
						.getSessionChannel().getUserNumber()));
		PromotionCampaignTemplate campaignTempate = this.promotionService
				.findPromotionCampaignTemplate(eventnumber);
		if (campaignTempate == null) {
			model.addAttribute("reviewEnable", false);
		} else {
			String backPhoto = "";
			if (!StringUtils.isEmpty(campaignTempate.getBackphoto())) {
				FDFSFileInfo back_fileinfo = (FDFSFileInfo) fsClient
						.getFileInfo(campaignTempate.getBackphoto());
				backPhoto = back_fileinfo.getFullPath();
			}
			campaignTempate.setBackphoto(backPhoto);
			String redeemBackPhoto = "";
			if (!StringUtils.isEmpty(campaignTempate.getRedeemBackphoto())) {
				FDFSFileInfo redeem_back_fileinfo = (FDFSFileInfo) fsClient
						.getFileInfo(campaignTempate.getRedeemBackphoto());
				redeemBackPhoto = redeem_back_fileinfo.getFullPath();
			}
			campaignTempate.setRedeemBackphoto(redeemBackPhoto);
			model.addAttribute("campaignTempate", campaignTempate);
			model.addAttribute("reviewEnable", true);
		}
		model.addAttribute("decode_campaignNumber", eventnumber);
		model.addAttribute("eventnumber", DigestUtils.pbeEncrypt(eventnumber));
		return CAMPAIGN_INFO_TEMPLATE;
	}
	
	/**
	 * 活动详情下开发者中心信息页面
	 * 
	 * @param eventnumber
	 * @param model
	 * @return
	 */
	@RequestMapping("to_campaign_info_advanced")
	public String to_campaign_info_advanced(
			@RequestParam("campaignNumber") String campaignNumber,
			Model model) {
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		if (!this.promotionService.isExistCampaign(campaignNumber,
				userNumber)) {
			throw new ServiceException(
					PromotionCampaignError.CAMPAIGN_NUMBER_ERROR);
		}
		PromotionCampaign campaign = promotionService
				.findPromotionCampaign(campaignNumber);
		model.addAttribute("campaignName", campaign.getEventname());
		CampaignStatus status = this.promotionService
				.findCampaignStatus(campaignNumber, userNumber);
		model.addAttribute("status", status);
		PromotionCampaignAdvanced advanced = this.promotionService
				.findCampaignAdvanced(campaignNumber);
		if (advanced != null) {
			model.addAttribute("token", advanced.getToken());
		}
		model.addAttribute("decode_campaignNumber", campaignNumber);
		model.addAttribute("campaignNumber",
				DigestUtils.pbeEncrypt(campaignNumber));
		return CAMPAIGN_INFO_ADVANCED;
	}
	
	/**
	 * 活动已发放兑换码
	 * 
	 * @param eventnumber
	 * @param pageInfo
	 * @return
	 */
	@RequestMapping("rdeemcode_list")
	public ModelAndView redeemCodeList(RedeemCodeForm codeForm,
			HttpServletRequest request, PageInfo pageInfo) {
		ModelAndView mv = new ModelAndView(CODE_LIST);
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		if (StringUtils.isEmpty(codeForm.getEventNumber())
				|| !promotionService.isExistCampaign(codeForm.getEventNumber(),
						userNumber)) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("兑换码发放记录：根据活动号{},用户号{},未查询到活动信息", codeForm
						.getEventNumber(), ChannelContext.getSessionChannel()
								.getUserNumber());
			}
			return mv;
		}
		PromotionCampaign campaign = promotionService
				.findPromotionCampaign(codeForm.getEventNumber());
		if (campaign == null) {
			throw new ServiceException(
					PromotionCampaignError.CAMPAIGN_NUMBER_ERROR);
		}
		mv.addObject("campaignName", campaign.getEventname());
		// 生成时间
		String starttime = request.getParameter("cstarttime");
		if (!StringUtils.isEmpty(starttime)) {
			codeForm.setStarttime(MbayDateFormat.stringToTime(starttime
					+ " 00:00:00"));
		}
		String endtime = request.getParameter("cendtime");
		if (!StringUtils.isEmpty(endtime)) {
			codeForm.setEndtime(MbayDateFormat.stringToTime(endtime
					+ " 23:59:59"));
		}
		// 领取时间
		String getstarttime = request.getParameter("cgetstarttime");
		if (!StringUtils.isEmpty(getstarttime)) {
			codeForm.setGetStartTime(MbayDateFormat.stringToTime(getstarttime));
		}
		String getendtime = request.getParameter("cgetendtime");
		if (!StringUtils.isEmpty(getendtime)) {
			codeForm.setGetEndTime(MbayDateFormat.stringToTime(getendtime));
		}
		// 兑换时间
		String redeemstarttime = request.getParameter("credeemstarttime");
		if (!StringUtils.isEmpty(redeemstarttime)) {
			codeForm.setRedeemStartTime(
					MbayDateFormat.stringToTime(redeemstarttime));
		}
		String redeemendtime = request.getParameter("credeemendtime");
		if (!StringUtils.isEmpty(redeemendtime)) {
			codeForm.setRedeemEndTime(
					MbayDateFormat.stringToTime(redeemendtime));
		}
		List<RedeemCode> codelist = this.redeemCodeService
				.findAllRedeemCodeByEventNumber(codeForm, pageInfo);
		mv.addObject("codelist", codelist);
		mv.addObject("codeForm", codeForm);
		mv.addObject("getstarttime", getstarttime);
		mv.addObject("getendtime", getendtime);
		mv.addObject("redeemstarttime", redeemstarttime);
		mv.addObject("redeemendtime", redeemendtime);
		mv.addObject(Globals.PAGEINFO_KEY, pageInfo);
		return mv;
	}
	
	/**
	 * 取消活动
	 * 
	 * @param eventnumber
	 *            活动编号
	 * @return
	 */
	@RequestMapping("cancel_campaign")
	@ResponseBody
	public ExecuteResult cancel_campaign(@RequestParam String eventnumber) {
		String usernumber = ChannelContext.getSessionChannel().getUserNumber();
		ExecuteResult result = this.promotionService.cancelCampaign(eventnumber,
				usernumber);
		return result;
	}
	
	/**
	 * 修改核销码开启状态
	 * 
	 * @param campaignNumber
	 * @param verificate
	 * @return
	 */
	@RequestMapping("update_campaign_verificate")
	@ResponseBody
	public boolean update_campaign_verificate(
			@RequestParam("campaignNumber") String campaignNumber,
			@RequestParam("verificate") boolean verificate) {
		campaignNumber = DigestUtils.pbeDecrypt(campaignNumber);
		if (campaignNumber == null) {
			return false;
		}
		boolean result = this.promotionService
				.updateCampaignVerificate(campaignNumber, verificate);
		return result;
	}
	
	/**
	 * 修改超出发放状态
	 * 
	 * @param campaignNumber
	 * @param repeat_enable
	 * @return
	 */
	@RequestMapping("update_campaign_continuable")
	@ResponseBody
	public boolean update_campaign_continuable(
			@RequestParam("campaignNumber") String campaignNumber,
			@RequestParam("continuable") boolean continuable) {
		campaignNumber = DigestUtils.pbeDecrypt(campaignNumber);
		if (campaignNumber == null) {
			return false;
		}
		boolean result = this.promotionService
				.updateCampaignContinuable(campaignNumber, continuable);
		return result;
	}
	
	/**
	 * 修改是否分享
	 * 
	 * @param campaignNumber
	 * @param share
	 * @return
	 */
	@RequestMapping("update_campaign_share")
	@ResponseBody
	public boolean update_campaign_share(
			@RequestParam("campaignNumber") String campaignNumber,
			@RequestParam("share") boolean share) {
		campaignNumber = DigestUtils.pbeDecrypt(campaignNumber);
		if (campaignNumber == null) {
			return false;
		}
		boolean result = this.promotionService
				.updateCampaignShare(campaignNumber, share);
		return result;
	}
	
	/**
	 * 
	 * 修改活动日期
	 * 
	 * @param certificate
	 * @return
	 */
	@RequestMapping("edit_campaign_date")
	@ResponseBody
	public boolean edit_campaign_date(
			Model model,
			@RequestParam("eventnumber") String eventnumber,
			@RequestParam(value = "starttime",
					required = false) String starttime,
			@RequestParam("endingtime") String endingtime,
			RedirectAttributes redAttr) {
		eventnumber = DigestUtils.pbeDecrypt(eventnumber);
		if (endingtime != null && endingtime != "") {
			ExecuteResult result = this.promotionService.updateCampaignDate(
					eventnumber, starttime, endingtime);
			return result.isSuccess();
		}
		return false;
	}
	
	/**
	 * 
	 * 修改概率
	 * 
	 * @param certificate
	 * @return
	 */
	@RequestMapping("edit_campaign_rate")
	@ResponseBody
	public boolean edit_campaign_date(
			@RequestParam("eventnumber") String eventnumber,
			@RequestParam("rate") String rate,
			RedirectAttributes redAttr) {
		eventnumber = DigestUtils.pbeDecrypt(eventnumber);
		if (!StringUtils.isEmpty(rate)) {
			ExecuteResult result = this.promotionService.updateCampaignRate(
					eventnumber, rate);
			return result.isSuccess();
		}
		return false;
	}
	
	/**
	 * 生成兑换码
	 * 
	 * @param campaignNumber
	 * @return
	 */
	@RequestMapping("redeem_code/generate_code")
	@ResponseBody
	public JSONObject generate_code(@RequestParam String campaignNumber,
			// 区别是再领一次还是原始流程,如果是由再领一次则全部都是系统自动生成码
			@RequestParam(value = "method", required = false) String method,
			HttpServletRequest request, HttpServletResponse response) {
		String redeemed_mark = "redeemed_mark_" + campaignNumber;
		Map<String, Object> map = new HashMap<String, Object>();
		
		campaignNumber = DigestUtils.pbeDecrypt(campaignNumber);
		if (campaignNumber == null) {
			map.put("success", false);
			map.put("message", "活动不存在!");
			return JSONObject.fromObject(map);
		}
		
		ExecuteResult result = canParkedCampaign(campaignNumber, redeemed_mark,
				request, response);
		if (!result.isSuccess()) {
			map.put("success", false);
			map.put("message", "您已经参与过活动,请勿重复参加,谢谢!");
			return JSONObject.fromObject(map);
		}
		// 记录领取次数
		int campaignCount = Integer.valueOf(result.getMessage());
		
		// 生成兑换码
		ExecuteResult codeResult = this.redeemCodeService
				.generateRedeemCode(campaignNumber, method);
				
		map.put("success", codeResult.isSuccess());
		if (!codeResult.isSuccess()) {
			map.put("message", codeResult.getMessage());
			return JSONObject.fromObject(map);
		}
		// 如果成功生成兑换码,记录cookie
		// 设置cookie 2个月 防止单用户用其它号码多次参与
		campaignCount = campaignCount + 1;
		RequestUtil.setCookie(response, redeemed_mark,
				MbayDateFormat.formatDateTime(DateTime.now(), "yyyy-MM-dd")
						+ "_" + campaignCount,
				365 * 30 * 24 * 60 * 60);
		map.put("code", codeResult.getMessage());
		return JSONObject.fromObject(map);
	}
	
	/**
	 * 为了防止盗链，重定向到兑换领取页面
	 * 
	 * @param campaignNumber
	 *            活动编号
	 * @param redAttr
	 * @return
	 */
	@RequestMapping("redeem_code/{campaignNumber}/get")
	public String getRedeemCode(@PathVariable String campaignNumber,
			// 区别是再领一次还是原始流程,如果是由再领一次则全部都是系统自动生成码
			@RequestParam(value = "method", required = false) String method,
			RedirectAttributes redAttr) {
		redAttr.addAttribute("campaignNumber", campaignNumber);
		redAttr.addAttribute("method", method);
		HttpSession session = WebContext.getHttpSession();
		session.setAttribute(PROMOTIONGETKEY, String.valueOf(Math.random()));
		return REDIRECT_GET;
	}
	
	/**
	 * 前往兑换码领取页面
	 * 
	 * @param campaignNumber
	 *            活动编号
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("redeem_code/get_immediately")
	public String get_immediately(@RequestParam String campaignNumber,
			// 区别是再领一次还是原始流程,如果是由再领一次则全部都是系统自动生成码
			@RequestParam(value = "method", required = false) String method,
			Model model, HttpServletRequest request,
			HttpServletResponse response) {
		String key = ChannelContext.getSessionAttribute(PROMOTIONGETKEY);
		if (key == null) {
			return TRAFFIC_FAIL;
		}
		// 清除session中attribute
		WebContext.getHttpSession().removeAttribute(PROMOTIONGETKEY);
		model.addAttribute("campaignNumber", campaignNumber);
		String redeemed_mark = "redeemed_mark_" + campaignNumber;
		campaignNumber = DigestUtils.pbeDecrypt(campaignNumber);
		if (campaignNumber == null) {// 解密活动编号失败，活动不存在
			return TRAFFIC_FAIL;
		}
		
		// 判断是否可以继续参与活动
		boolean parked = false;
		ExecuteResult result = canParkedCampaign(campaignNumber, redeemed_mark,
				request, response);
		if (!result.isSuccess()) {
			parked = true;
		}
		model.addAttribute("partaked", parked);
		
		PromotionCampaignTemplate campaignTemplate = promotionService
				.findPromotionCampaignTemplate(campaignNumber);
		String backphoto = "";
		String gotText = "";
		String introduction = "";
		if (campaignTemplate != null) {
			if (campaignTemplate.getBackphoto() != null) {
				FDFSFileInfo back_fileinfo = (FDFSFileInfo) fsClient
						.getFileInfo(campaignTemplate.getBackphoto());
				backphoto = back_fileinfo.getFullPath();
			}
			gotText = campaignTemplate.getGotText();
			introduction = campaignTemplate.getIntroduction();
		}
		model.addAttribute("backphoto", backphoto);
		model.addAttribute("introduction", introduction);
		model.addAttribute("gotText", gotText);
		model.addAttribute("method", method);
		return TO_GET_REDEECODE;
	}
	
	/**
	 * 为了防止盗链，重定向到兑换领取页面
	 * 
	 * @param campaignNumber
	 *            活动编号
	 * @param redAttr
	 * @return
	 */
	@RequestMapping("redeem_code/{campaignNumber}/redeem")
	public String toRedeem(@PathVariable String campaignNumber,
			RedirectAttributes redAttr) {
		redAttr.addAttribute("campaignNumber", campaignNumber);
		HttpSession session = WebContext.getHttpSession();
		session.setAttribute(PROMOTIONREDEEMKEY, String.valueOf(Math.random()));
		return REDIRECT_REDEEM;
	}
	
	/**
	 * 前往兑换码兑换页面
	 * 
	 * @param campaignNumber
	 *            活动编号
	 * @param model
	 * @return
	 */
	@RequestMapping("redeem_code/redeem_immediately")
	public String redeem_immediately(@RequestParam String campaignNumber,
			Model model) {
		String key = ChannelContext.getSessionAttribute(PROMOTIONREDEEMKEY);
		if (key == null) {
			return TRAFFIC_FAIL;
		}
		// 清除session中attribute
		WebContext.getHttpSession().removeAttribute(PROMOTIONREDEEMKEY);
		String decCampaignNumber = DigestUtils.pbeDecrypt(campaignNumber);
		if (decCampaignNumber == null) {// 解密活动编号失败，活动不存在
			return TRAFFIC_FAIL;
		}
		PromotionCampaignTemplate campaignTemplate = promotionService
				.findPromotionCampaignTemplate(decCampaignNumber);
		String redeemBackphoto = "";
		if (campaignTemplate != null
				&& campaignTemplate.getRedeemBackphoto() != null) {
			FDFSFileInfo back_fileinfo = (FDFSFileInfo) fsClient
					.getFileInfo(campaignTemplate.getRedeemBackphoto());
			redeemBackphoto = back_fileinfo.getFullPath();
		}
		model.addAttribute("redeemBackphoto", redeemBackphoto);
		model.addAttribute("campaignNumber", campaignNumber);
		model.addAttribute("redeemText", campaignTemplate.getRedeemText());
		model.addAttribute("introductionText",
				campaignTemplate.getIntroductionText());
		String userNumber = promotionService
				.getCampaignBelongsUser(decCampaignNumber);
		PromotionCampaign campaign = promotionService
				.findPromotionCampaign(decCampaignNumber, userNumber);
		model.addAttribute("campaign", campaign);
		return TO_REDEEM;
	}
	
	/**
	 * 前往兑换码说明页面
	 * 
	 * @return
	 */
	@RequestMapping("redeem_code/redeem_instruction")
	public String redeem_instruction() {
		HttpSession session = WebContext.getHttpSession();
		session.setAttribute(PROMOTIONREDEEMKEY, String.valueOf(Math.random()));
		return TO_REDEEM_INSTRUCTION;
	}
	
	/**
	 * 批量导入页面
	 * 
	 * @param campaignNumber
	 *            活动编号
	 * @param redAttr
	 * @return
	 */
	@RequestMapping("toImportAll")
	@Token(save = true)
	public String toImportAll(String eventnumber, Model model) {
		PromotionCampaign campaign = promotionService
				.findPromotionCampaign(eventnumber);
		if (campaign == null) {
			throw new ServiceException(
					PromotionCampaignError.CAMPAIGN_NUMBER_ERROR);
		}
		model.addAttribute("campaignName", campaign.getEventname());
		model.addAttribute("eventnumber", eventnumber);
		return TO_IMPORT_ALL;
	}
	
	/**
	 * 批量导入兑换码
	 * 
	 * @param file
	 * @param model
	 * @return
	 */
	@RequestMapping("importAll")
	@Token(reset = true)
	public String importAll(
			@RequestParam(value = "eventnumber") String campaignNumber,
			Model model,
			@RequestParam(value = "file") MultipartFile file,
			@RequestParam(value = "endType") CodeEndType endType,
			HttpServletRequest request) {
		// 解析excel文件
		String filename = file.getOriginalFilename();
		String etype = filename.substring(filename.indexOf(".") + 1);
		List<RedeemCodeForm> list = new ArrayList<RedeemCodeForm>();
		try {
			EXCELType type = EXCELType.valueOf(etype.toUpperCase());
			Iterator<Row> rowIterator = POIExcel
					.readExcel(file.getInputStream(), type);
			rowIterator.next();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Cell cell = row.getCell(0);
				if (cell == null) {
					break;
				}
				// 解析兑换码
				String code = "";
				if (row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC) {
					// 解决自动转换科学计数法问题
					DecimalFormat df = new DecimalFormat("0");
					code = df.format(row.getCell(0).getNumericCellValue());
				} else
					if (row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING) {
					code = row.getCell(0).getStringCellValue();
				}
				// 解析时间
				String time = "";
				if (row.getCell(1).getCellType() == Cell.CELL_TYPE_NUMERIC) {
					SimpleDateFormat sdf = new SimpleDateFormat(
							DatePattern.slashDate);
					time = sdf.format(row.getCell(1).getDateCellValue());
				} else
					if (row.getCell(1).getCellType() == Cell.CELL_TYPE_STRING) {
					time = row.getCell(1).getStringCellValue();
				}
				if (endType.equals(CodeEndType.DATE)) {
					// 判断时间是否在今天之前，如果在之前，则抛出异常
					DateTime dtime = MbayDateFormat.stringToSlashDate(time);
					if (dtime.isBefore(DateTime.now())) {
						throw new Exception("截止时间必须在今天之后");
					}
				}
				RedeemCodeForm form = new RedeemCodeForm();
				form.setCode(code);
				form.setTime(time);
				list.add(form);
			}
			if (list.isEmpty()) {
				throw new Exception("没有相关数据");
			}
			// 生成兑换码
			ExecuteResult result = redeemCodeService
					.generateRedeemCodeByMunual(campaignNumber, list, endType);
			if (!result.isSuccess()) {
				TokenProcessor.getInstance().saveToken(request);
				model.addAttribute(PUBLIC_MSG_NOTE_KEY, result.getMessage());
				return toImportAll(campaignNumber, model);
			}
			return "redirect:/promotionCampaign/rdeemcode_list.mbay?eventNumber="
					+ campaignNumber;
		} catch (Exception e) {
			LOGGER.error("文件解析失败,原因:" + e.getMessage());
			TokenProcessor.getInstance().saveToken(request);
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, "批量导入失败,请检查文件格式是否正确!");
			return toImportAll(campaignNumber, model);
		}
	}
	
	/**
	 * 查询红包产品
	 * 
	 * @param campaignNumber
	 * @return
	 */
	@ResponseBody
	@RequestMapping("{campaignNumber}/trafficProducts")
	public Object trafficProducts(@PathVariable String campaignNumber) {
		List<PromotionCampaignPackage> trafficProducts = campaignPackageService
				.findByCampaignNumber(campaignNumber);
		PromotionProductConfig config = productConfigService.findProductConfig(
				campaignNumber,
				ProductType.TRAFFIC_PACKAGE);
		if (config == null) {// 未选择产品
			config = new PromotionProductConfig();
		}
		String jsonString;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("trafficProducts", trafficProducts);
			map.put("productConfig", config);
			jsonString = new ObjectMapper().writeValueAsString(map);
			return jsonString;
		} catch (IOException e) {
		}
		return null;
	}
	
	/**
	 * 查询MB产品
	 * 
	 * @param campaignNumber
	 * @return
	 */
	@ResponseBody
	@RequestMapping("{campaignNumber}/mbayProducts")
	public Object mbProducts(@PathVariable String campaignNumber) {
		List<PromotionCampaignMbay> mbayProducts = campaignMbayService
				.findByCampaignNumber(campaignNumber);
		PromotionProductConfig config = productConfigService
				.findProductConfig(campaignNumber, ProductType.MBAY_PACKAGE);
		if (config == null) {// 未选择产品
			config = new PromotionProductConfig();
		}
		String jsonString;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mbayProducts", mbayProducts);
			map.put("productConfig", config);
			jsonString = new ObjectMapper().writeValueAsString(map);
			return jsonString;
		} catch (IOException e) {
		}
		return null;
	}
	
	/**
	 * 修改产品单日上限
	 * 
	 * @param campaignNumber
	 * @param productType
	 * @param limit
	 * @return
	 */
	@RequestMapping("{campaignNumber}/producttype/{productType}/dailylimit/{limit}/update")
	@ResponseBody
	public Object updateProductDailyLimit(@PathVariable String campaignNumber,
			@PathVariable ProductType productType,
			@PathVariable double limit) {
		ExecuteResult result = productConfigService
				.updateProductDailyLimit(campaignNumber, productType, limit);
		return result;
	}
	
	/**
	 * 修改小池
	 * 
	 * @param campaignNumber
	 * @param productType
	 * @param poolsize
	 * @return
	 */
	@RequestMapping("{campaignNumber}/producttype/{productType}/increase/{poolsize}/add")
	@ResponseBody
	public Object increaseProductPoolSize(@PathVariable String campaignNumber,
			@PathVariable ProductType productType,
			@PathVariable int poolsize) {
		return productConfigService.increaseProductPoolSize(campaignNumber,
				productType, poolsize,
				ChannelContext.getSessionChannel().getUserNumber());
	}
	
	/**
	 * 修改告警阀值
	 * 
	 * @param campaignNumber
	 * @param productType
	 * @param threshold
	 * @return
	 */
	@RequestMapping("{campaignNumber}/producttype/{productType}/threshold/{threshold}/update")
	@ResponseBody
	public Object updateProductThreshold(@PathVariable String campaignNumber,
			@PathVariable ProductType productType,
			@PathVariable int threshold) {
		ExecuteResult result = productConfigService
				.updateProductThreshold(campaignNumber, productType, threshold);
		return result;
	}
	
	/**
	 * 获取未选择的流量包产品
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("{campaignNumber}/traffic_package/unselected")
	public Object trafficProductUnselected(
			@PathVariable String campaignNumber) {
		List<PromotionCampaignPackage> trafficProducts = campaignPackageService
				.findByCampaignNumber(campaignNumber);
				
		List<TrafficRedPackage> mobilePackages = packageService
				.findByOperatorType(OperatorType.MOBILE);
		List<TrafficRedPackage> unicomPackages = packageService
				.findByOperatorType(OperatorType.UNICOM);
		List<TrafficRedPackage> telecomPackages = packageService
				.findByOperatorType(OperatorType.TELECOM);
		// 考虑到设计问题，产品服务不应该针对活动提供活动未选择的产品 方法。故设计为 产品方法提供所有的产品，由活动自身来剔除。
		for (PromotionCampaignPackage cp : trafficProducts) {
			// 剔除已选择的移动流量包
			if (cp.getTrafficPackage().getOperatorType()
					.equals(OperatorType.MOBILE)) {
				for (TrafficRedPackage mp : mobilePackages) {
					if (cp.getTrafficPackage().getPackageId() == mp
							.getPackageId()) {
						mobilePackages.remove(mp);
						break;
					}
				}
			}
			// 剔除已选择的联通流量包
			if (cp.getTrafficPackage().getOperatorType()
					.equals(OperatorType.UNICOM)) {
				for (TrafficRedPackage up : unicomPackages) {
					if (cp.getTrafficPackage().getPackageId() == up
							.getPackageId()) {
						unicomPackages.remove(up);
						break;
					}
				}
			}
			// 剔除已选择的电信流量包
			if (cp.getTrafficPackage().getOperatorType()
					.equals(OperatorType.TELECOM)) {
				for (TrafficRedPackage telp : telecomPackages) {
					if (cp.getTrafficPackage().getPackageId() == telp
							.getPackageId()) {
						telecomPackages.remove(telp);
						break;
					}
				}
			}
		}
		Map<String, Object> trafficPackages = new HashMap<String, Object>();
		trafficPackages.put(OperatorType.MOBILE.name(), mobilePackages);
		trafficPackages.put(OperatorType.UNICOM.name(), unicomPackages);
		trafficPackages.put(OperatorType.TELECOM.name(), telecomPackages);
		boolean selected = productConfigService.isSelectedProduct(
				campaignNumber,
				ProductType.TRAFFIC_PACKAGE);
		trafficPackages.put("selected", selected);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String jsonString = objectMapper
					.writeValueAsString(trafficPackages);
			return jsonString;
		} catch (IOException e) {
		}
		return null;
	}
	
	/**
	 * 查询未选择过的美贝产品
	 * 
	 * @param campaignNumber
	 * @return
	 */
	@ResponseBody
	@RequestMapping("{campaignNumber}/mbay_package/unselected")
	public Object mbayProductUnselected(@PathVariable String campaignNumber) {
		List<PromotionCampaignMbay> mbayProducts = campaignMbayService
				.findByCampaignNumber(campaignNumber);
		List<TrafficRedMbay> trafficRedMbays = mbayService.findAll();
		for (PromotionCampaignMbay rcm : mbayProducts) {
			for (TrafficRedMbay trm : trafficRedMbays) {
				if (trm.getId() == rcm.getMbay().getId()) {
					trafficRedMbays.remove(trm);
					break;
				}
			}
		}
		boolean selected = productConfigService.isSelectedProduct(
				campaignNumber,
				ProductType.MBAY_PACKAGE);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mbayProducts", trafficRedMbays);
		map.put("selected", selected);
		try {
			String jsonStr = new ObjectMapper().writeValueAsString(map);
			return jsonStr;
		} catch (IOException e) {
		}
		return null;
		
	}
	
	/**
	 * 添加产品
	 * 
	 * @param campaignNumber
	 *            活动编号
	 * @param productType
	 *            产品类型
	 * @param request
	 * @return
	 */
	@RequestMapping("{campaignNumber}/producttype/{productType}/add")
	@ResponseBody
	public Object addCampaignProduct(@PathVariable String campaignNumber,
			@PathVariable ProductType productType,
			PromotionProductConfig config, HttpServletRequest request) {
		String[] productIds = request.getParameterValues("productId");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		switch (productType) {
			case TRAFFIC_PACKAGE:
				List<PromotionCampaignPackage> packages = new ArrayList<PromotionCampaignPackage>();
				for (int i = 0; i < productIds.length; i++) {
					int productId = Integer.valueOf(productIds[i]);
					String ratioStr = request.getParameter("ratio_" + productId)
							.toString();
					if (StringUtils.isEmpty(ratioStr)) {
						ratioStr = "1";
					}
					int ratio = Integer.valueOf(ratioStr);
					PromotionCampaignPackage tpackage = new PromotionCampaignPackage();
					tpackage.setCampaignNumber(campaignNumber);
					TrafficRedPackage trp = new TrafficRedPackage();
					trp.setPackageId(productId);
					tpackage.setRatio(ratio);
					tpackage.setTrafficPackage(trp);
					packages.add(tpackage);
				}
				try {
					this.promotionService.addTrafficProducts(campaignNumber,
							packages, config);
				} catch (Exception e) {
					map.put("success", false);
				}
				List<PromotionCampaignPackage> trafficProducts = campaignPackageService
						.findByCampaignNumber(campaignNumber);
				map.put("trafficProducts", trafficProducts);
				map.put("productConfig", config);
				break;
			case MBAY_PACKAGE:
				List<PromotionCampaignMbay> mbaProducts = new ArrayList<PromotionCampaignMbay>();
				for (int i = 0; i < productIds.length; i++) {
					int productId = Integer.valueOf(productIds[i]);
					String ratioStr = request.getParameter("ratio_" + productId)
							.toString();
					if (StringUtils.isEmpty(ratioStr)) {
						ratioStr = "1";
					}
					int ratio = Integer.valueOf(ratioStr);
					PromotionCampaignMbay mbay = new PromotionCampaignMbay();
					mbay.setCampaignNumber(campaignNumber);
					mbay.setRatio(ratio);
					TrafficRedMbay redmbay = new TrafficRedMbay();
					redmbay.setId(new Long(productId));
					mbay.setMbay(redmbay);
					mbaProducts.add(mbay);
				}
				try {
					this.promotionService.addMbayProducts(campaignNumber,
							mbaProducts, config);
				} catch (Exception e) {
					LOGGER.error("addCampaignProduct ERROR:",
							e.fillInStackTrace());
					map.put("success", false);
				}
				List<PromotionCampaignMbay> mbayProducts = campaignMbayService
						.findByCampaignNumber(campaignNumber);
				map.put("mbayProducts", mbayProducts);
				map.put("productConfig", config);
		}
		String jsonString = "";
		try {
			jsonString = new ObjectMapper().writeValueAsString(map);
		} catch (Exception e) {
		}
		return jsonString;
		
	}
	
	/**
	 * 删除活动产品
	 * 
	 * @param campaignNumber
	 *            活动编号
	 * @param productType
	 *            产品类型
	 * @param productid
	 *            产品编号
	 * @return
	 */
	@RequestMapping("{campaignNumber}/producttype/{productType}/productNumber/{productId}/delete")
	@ResponseBody
	public Object deleteCampaignProduct(@PathVariable String campaignNumber,
			@PathVariable ProductType productType,
			@PathVariable long productId) {
		if (!this.promotionService.isExistCampaign(campaignNumber,
				ChannelContext.getSessionChannel().getUserNumber())) {
			return new ExecuteResult(false, "找不到对应的活动！");
		}
		boolean dlelteSuccess = this.promotionService
				.deleteCampaignProduct(campaignNumber, productType, productId);
		if (dlelteSuccess) {
			return new ExecuteResult(true, "删除成功！");
		} else {
			return new ExecuteResult(false, "删除失败，请重试！");
		}
	}
	
	/**
	 * MB结果页面
	 * 
	 * @param number
	 * @param mb
	 * @param model
	 * @param mobile
	 * @param code
	 * @param from
	 * @return
	 */
	@RequestMapping(value = "redeem_code/result_mbay_pack")
	public String resultMbayPack(@RequestParam(value = "number") String number,
			@RequestParam(value = "mb") String mb, Model model,
			@RequestParam(value = "mobile") String mobile,
			@RequestParam(value = "code") String code,
			// 来源标识: redeem->兑换流程,play->再玩一次
			@RequestParam(value = "from") String from) {
		// 加密的活动编号
		String enumber = number;
		number = DigestUtils.pbeDecrypt(number);
		if (StringUtils.isEmpty(number)) {
			throw new ServiceException(
					PromotionCampaignError.CAMPAIGN_NUMBER_ERROR);
		}
		// 获取活动信息
		PromotionCampaign campaign = promotionService
				.findPromotionCampaign(number);
		model.addAttribute("campaignName", campaign.getEventname());
		model.addAttribute("walletIndex", Wallet.INDEX + "?mobile=" + mobile);
		model.addAttribute("duiba", getDuibaUrl(campaign.getMall().getId()));
		model.addAttribute("youxi", Wallet.YOUXI);
		model.addAttribute("cnumber", number);
		model.addAttribute("enumber", enumber);
		model.addAttribute("size", mb.toCharArray());
		model.addAttribute("mbayTraffic", mb);
		model.addAttribute("code", code);
		// 获取核销码
		RedeemCode rcode = redeemCodeService.findRedeemCodeByCodeNumber(code,
				number);
		model.addAttribute("verificate", rcode.getVerificateCode());
		
		boolean remindShare = false;
		boolean continuable = false;
		boolean playAgain = false;
		if (from.equals("redeem")) {
			// 是否开启分享
			if (campaign.isShare()) {
				model.addAttribute("model", campaign.getModel());
				remindShare = true;
				continuable = true;
			}
		}
		if (from.equals("play")) {
			playAgain = true;
		}
		model.addAttribute("remindShare", remindShare);
		model.addAttribute("continuable", continuable);
		model.addAttribute("playAgain", playAgain);
		return "promotion_campaign/mobile/result_mbay_pack";
	}
	
	/**
	 * 再玩一次赠送MB
	 * 
	 * @param number
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "redeem_code/play_sendMB")
	@ResponseBody
	public JSONObject play_sendMB(@RequestParam(value = "number") String number,
			@RequestParam(value = "code") String code) {
		// 加密的活动编号
		String enumber = number;
		number = DigestUtils.pbeDecrypt(number);
		if (StringUtils.isEmpty(number)) {
			LOGGER.error("再玩一次赠送mb失败,原因:活动编号不存在,未解密活动编号:{}", enumber);
			return JSONObject
					.fromObject(new ExecuteResult(false, "系统繁忙,请稍后重试"));
		}
		
		// 该兑换码是否赠送过,如果赠送过则不再赠送
		RedeemCode rcode = redeemCodeService.findRedeemCodeByCodeNumber(code,
				number);
		if (!rcode.isSend()) {
			String mobile = rcode.getBindMobile();
			// 获取活动信息
			PromotionCampaign campaign = promotionService
					.findPromotionCampaign(number);
			int sendMB = campaign.getSendMB();
			
			try {
				// 产品配置
				PromotionProductConfig config = productConfigService.findProductConfig(
						number, ProductType.MBAY_PACKAGE);
				
				// MB池剩余流量减少
				boolean suc = productConfigService.reduceProductPoolRemain(
						number, ProductType.MBAY_PACKAGE, sendMB);
				if (!suc) {
					LOGGER.error("美贝流量池剩余流量减少失败,活动编号:{}", number);
					throw new Exception();
				}
				
				// MB池单日剩余流量减少
				boolean suc2 = productConfigService.reduceProductDailyRemain(
						number, ProductType.MBAY_PACKAGE, sendMB);
				if (!suc2) {
					LOGGER.error("美贝池单日剩余流量减少失败,活动编号:{}", number);
					throw new Exception();
				}
				
				// 解除锁定MB
				mbayAccountService.unlockedTraffic(campaign.getUsernumber(), sendMB);
				LOGGER.info("解除锁定MB成功,活动编号:{}", number);
				// 账户支出MB
				mbayAccountService.expenditure(sendMB,
						campaign.getUsernumber(),
						org.sz.mbay.channel.user.enums.TradeType.PROMOTION_CAMPAIGN,
						number, "促销神器分享送MB");
				LOGGER.info("账户支出MB成功,活动编号:{}", number);
				
				// 钱包增加MB
				RIResponse resp = RIMBAccountUtil.requestUserEnterOfAccount(
						mobile, sendMB, "PROMOTION_MBAY", number,
						null);
				if (!resp.isStatus()) { 
					LOGGER.error("钱包增加MB失败,活动编号:{}", number);
					throw new Exception();
				}
				String serialNumber = resp.getData()
						.getString("serialNumber");
				LOGGER.info("钱包增加mb成功,活动编号:{},流水号:{}", number,
						serialNumber);
						
				// 修改兑换码赠送状态为已赠送
				boolean result = redeemCodeService.updateSended(code,
						number);
				if (!result) { 
					LOGGER.error("修改兑换码赠送状态失败,活动编号:{},兑换码:{}", number, code);
					throw new Exception();
				}
				LOGGER.info("修改赠送状态为已赠送成功,活动编号:{},兑换码:{}", number,code);
				
				// 是否发送告警阀值短信(已发送过则不再进行发送)
				if (!config.isThresholdWarned()) {
					LOGGER.info("确定发送阀值短信,活动编号:{}", number);
					redeemCodeService.sendThresholdWarningSms(number,
							ProductType.MBAY_PACKAGE);
				}
				
				JSONObject json = new JSONObject();
				json.put("success", true);
				json.put("mobile", mobile);
				json.put("mb", sendMB);
				return json;
			} catch (Exception e) {
				LOGGER.error("再玩一次赠送mb失败,活动编号:{},兑换码:{}", number,
						code);
				return JSONObject
						.fromObject(new ExecuteResult(false, "系统繁忙,请稍后重试"));
			}
		} else {
			LOGGER.error("再玩一次赠送mb失败,原因:同一个兑换码只能使用一次再玩一次,活动编号:{},兑换码:{}",
					number, code);
			return JSONObject
					.fromObject(new ExecuteResult(false, "同一个兑换码只能使用一次再玩一次"));
		}
	}
	
	/**
	 * 配置微信
	 * 
	 * @param campaignNumber
	 * @return
	 */
	@ResponseBody
	@RequestMapping("redeem_code/wechatConfig")
	public Object wechatConfig(@RequestParam("cnumber") String cnumber) {
		// 获取是否开启分享
		PromotionCampaign campaign = promotionService
				.findPromotionCampaign(cnumber);
		// 获取分享信息
		PromotionCampaignShare shareInfo = shareService
				.findByCampaignNumber(cnumber);
				
		JSONObject data = new JSONObject();
		data.put("status", true);
		data.put("shareTimes", shareInfo.getShareTimes());
		data.put("content", shareInfo.getContent());
		data.put("shareTitle", shareInfo.getShareTitle());
		data.put("enableState", campaign.isShare());
		data.put("shareLink", shareInfo.getShareLink());
		
		FDFSFileInfo imageInfo = (FDFSFileInfo) fsClient.getFileInfo(shareInfo
				.getShareImage());
		data.put("shareImage", imageInfo.getFullPath());
		
		return data;
	}
	
	/**
	 * 分享成功后设置cookie
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "redeem_code/share_success",
			method = RequestMethod.GET)
	@ResponseBody
	public Object shareSuccess(@RequestParam("cNumber") String cNumber,
			@RequestParam("code") String code,
			HttpServletRequest request, HttpServletResponse response) {
		// 获取活动信息
		PromotionCampaign campaign = promotionService
				.findPromotionCampaign(cNumber);
				
		// 该兑换码是否赠送过,如果赠送过则不消耗分享次数
		RedeemCode rcode = redeemCodeService.findRedeemCodeByCodeNumber(code,
				cNumber);
		if (rcode.isSend()) {
			return RedeemResult.create(false, "INVALID_SHARE_TIMES", null);
		}
		
		// 获取分享信息
		PromotionCampaignShare shareInfo = shareService
				.findByCampaignNumber(cNumber);
				
		// 分享标识符,如果超出分享有效次数则不弹出提示
		boolean share_flag = true;
		int shareTimes = shareInfo.getShareTimes();
		if (!campaign.isShare() || shareTimes == 0) {
			return RedeemResult.create(false, "INVALID_SHARE_TIMES", null);
		}
		
		String value = MbayDateFormat.formatDateTime(DateTime.now(),
				DatePattern.isoDate);
		String key = "shared_mark_" + cNumber;
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
					campaign.getCampaignLimitType()).calculateInRande();
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
	 * 更新分享信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "shareChange", method = RequestMethod.POST)
	public String shareChange(@RequestParam("cNumber") String cNumber,
			@RequestParam(value = "sendMB", required = false) String sendMB,
			@RequestParam(value = "shareLink") String shareLink,
			@RequestParam(value = "shareTitle") String shareTitle,
			@RequestParam(value = "content") String content,
			@RequestParam(value = "shareTimes") String shareTimes,
			@RequestParam(value = "shareImage", required = false) MultipartFile shareImage,
			Model model, RedirectAttributes attrs) {
		// 获取活动信息
		PromotionCampaign campaign = promotionService
				.findPromotionCampaign(cNumber);
		
		if(campaign.isShare()){
			if(campaign.getModel().equals(org.sz.mbay.promotion.enums.Model.PLAY)){
				int mb = 0;
				if(!StringUtils.isEmpty(sendMB)){
					mb = Integer.valueOf(sendMB);
				}
				ExecuteResult result = promotionService.updateCampaignSendMB(cNumber, mb);
				if(!result.isSuccess()){
					LOGGER.error("修改赠送MB失败,活动编号:{},赠送MB:{}", cNumber, sendMB);
				}
			}
			
			// 获取分享信息
			PromotionCampaignShare shareInfo = shareService
					.findByCampaignNumber(cNumber);
			
			if(shareInfo == null){
				shareInfo = dealShareInfo(true, shareLink, shareTitle, 
						content, shareTimes, shareImage);
				shareService.addShare(shareInfo);
			}else{
				shareInfo.setShareLink(shareLink);
				shareInfo.setShareTitle(shareTitle);
				shareInfo.setContent(content);
				shareInfo.setShareTimes(Integer.valueOf(shareTimes));
				
				if(shareImage != null && shareImage.getSize() > 0){
					String image = fsClient.uploadFile(shareImage);
					shareInfo.setShareImage(image);
				}
				shareService.updateShare(shareInfo);
			}
		}
		attrs.addAttribute("eventnumber", cNumber);
		return REDIRECT_TO_CAMPAIGN_INFO;
	}
	
	/**
	 * 兑吧商城链接
	 */
	private String getDuibaUrl(int mallId) {
		String duiba_url = Wallet.WL_DUIBA + "&mallId="
				+ (mallId == 0 ? ChannelConfig.getMallId()
						: String.valueOf(mallId));
		return duiba_url;
	}
	
	/**
	 * 设置流量产品
	 * 
	 * @param oType
	 * @param trcps
	 * @return
	 */
	private List<TrafficRedPackageDTO> initTrafficPack(OperatorType oType) {
		List<TrafficRedPackageDTO> list = new ArrayList<TrafficRedPackageDTO>();
		for (TrafficRedPackage trp : packageService.findByOperatorType(oType)) {
			TrafficRedPackageDTO data = new TrafficRedPackageDTO();
			data.setTraffic(trp.getTraffic());
			data.setPackageId(trp.getPackageId());
			data.setOperatorType(trp.getOperatorType());
			list.add(data);
		}
		return list;
	}
	
	/**
	 * 设置美贝产品
	 * 
	 * @param mbayCmpList
	 * @return
	 */
	private List<TrafficRedMbayDTO> initMbay() {
		List<TrafficRedMbayDTO> datas = new ArrayList<TrafficRedMbayDTO>();
		for (TrafficRedMbay mb : mbayService.findAll()) {
			TrafficRedMbayDTO data = new TrafficRedMbayDTO();
			data.setMbay(mb.getMbay().intValue());
			data.setMbayId(mb.getId());
			datas.add(data);
		}
		return datas;
	}
	
	/**
	 * 解析流量产品
	 * 
	 * @param trafficIds
	 * @return
	 */
	private List<PromotionCampaignPackage> analyseTraffic(
			String trafficIds) {
		List<PromotionCampaignPackage> packs = new ArrayList<PromotionCampaignPackage>();
		if (!StringUtils.isEmpty(trafficIds)) {
			String[] pros = trafficIds.split("[,，]");
			String info[] = null;
			for (String pro : pros) {
				info = pro.split("-");
				PromotionCampaignPackage pack = new PromotionCampaignPackage();
				pack.setRatio(Integer.parseInt(info[1]));
				TrafficRedPackage rp = new TrafficRedPackage();
				rp.setPackageId(Integer.parseInt(info[0]));
				pack.setTrafficPackage(rp);
				packs.add(pack);
			}
		}
		return packs;
	}
	
	/**
	 * 解析美贝产品
	 * 
	 * @param mbayIds
	 * @return
	 */
	private List<PromotionCampaignMbay> analyseMbay(String mbayIds) {
		List<PromotionCampaignMbay> mbays = new ArrayList<PromotionCampaignMbay>();
		if (!StringUtils.isEmpty(mbayIds)) {
			String[] pros = mbayIds.split("[,，]");
			String info[] = null;
			for (String pro : pros) {
				info = pro.split("-");
				PromotionCampaignMbay mbay = new PromotionCampaignMbay();
				mbay.setRatio(Integer.parseInt(info[1]));
				TrafficRedMbay mb = new TrafficRedMbay();
				mb.setId(Long.parseLong(info[0]));
				mbay.setMbay(mb);
				mbays.add(mbay);
			}
		}
		return mbays;
	}
	
	/**
	 * 设置分享信息
	 * 
	 * @param share
	 * @param shareLink
	 * @param shareTitle
	 * @param content
	 * @param shareTimes
	 * @param shareImage
	 * @return
	 */
	private PromotionCampaignShare dealShareInfo(boolean share,
			String shareLink, String shareTitle, String content,
			String shareTimes, MultipartFile shareImage) {
		PromotionCampaignShare shareInfo = null;
		if (share) {
			shareInfo = new PromotionCampaignShare();
			shareInfo.setShareLink(shareLink);
			shareInfo.setShareTitle(shareTitle);
			shareInfo.setContent(content);
			shareInfo.setShareTimes(Integer.valueOf(shareTimes));
			
			String image = fsClient.uploadFile(shareImage);
			shareInfo.setShareImage(image);
		}
		return shareInfo;
	}
	
	/**
	 * 判断是否可以继续参与活动
	 * 
	 * @param campaignNumber
	 * @param redeemed_mark
	 * @param campaignCount
	 * @param request
	 * @param response
	 * @return
	 */
	private ExecuteResult canParkedCampaign(String campaignNumber,
			String redeemed_mark,
			HttpServletRequest request, HttpServletResponse response) {
		// 获取活动信息
		PromotionCampaign campaign = promotionService
				.findPromotionCampaign(campaignNumber);
		LimitType campaignLimitType = campaign.getCampaignLimitType();
		int campaignLimitValue = campaign.getCampaignLimitValue();
		
		String campaignTime = "";
		// 记录领取次数
		int campaignCount = 0;
		Cookie campaignCookie = RequestUtil.getCookie(request, redeemed_mark);
		if (campaignCookie == null) {
			RequestUtil.setCookie(response, redeemed_mark,
					MbayDateFormat.formatDateTime(DateTime.now(),
							DatePattern.isoDate) + "_" + 0,
					365 * 30 * 24 * 60 * 60);
			campaignTime = MbayDateFormat.formatDateTime(DateTime.now(),
					DatePattern.isoDate);
		} else {
			String campaignCookieValue = RequestUtil
					.getCookie(request, redeemed_mark).getValue();
			campaignTime = campaignCookieValue.split("_")[0];
			campaignCount = Integer.valueOf(campaignCookieValue.split("_")[1]);
		}
		
		// 如果是不限则不进行限制判断(活动判断)
		if (!campaignLimitType.equals(LimitType.UNLIMITED)) {
			if (campaignLimitType.equals(LimitType.DAY)) {
				// 当前时间在cookie记录时间的一天之后
				if (MbayDateFormat.stringToDate(campaignTime).plusDays(1)
						.isBefore(DateTime.now())) {
					// 重新设置cookie
					RequestUtil.setCookie(response, redeemed_mark,
							MbayDateFormat.formatDateTime(DateTime.now(),
									DatePattern.isoDate) + "_" + 0,
							24 * 60 * 60);
					campaignCount = 0;
				}
			}
			if (campaignLimitType.equals(LimitType.WEEK)) {
				// 当前时间在cookie记录时间的一周之后
				if (MbayDateFormat.stringToDate(campaignTime).plusWeeks(1)
						.isBefore(DateTime.now())) {
					// 重新设置cookie
					RequestUtil.setCookie(response, redeemed_mark,
							MbayDateFormat.formatDateTime(DateTime.now(),
									DatePattern.isoDate) + "_" + 0,
							7 * 24 * 60 * 60);
					campaignCount = 0;
				}
			}
			if (campaignLimitType.equals(LimitType.MONTH)) {
				// 当前时间在cookie记录时间的一月之后
				if (MbayDateFormat.stringToDate(campaignTime).plusMonths(1)
						.isBefore(DateTime.now())) {
					// 重新设置cookie
					RequestUtil.setCookie(response, redeemed_mark,
							MbayDateFormat.formatDateTime(DateTime.now(),
									DatePattern.isoDate) + "_" + 0,
							30 * 24 * 60 * 60);
					campaignCount = 0;
				}
			}
			
			boolean flag = true;
			if (campaign.isShare()) {
				int shareTimes = 0;
				// 如果是再领一次则查询分享次数
				if(campaign.getModel().equals(org.sz.mbay.promotion.enums.Model.GET)){
					shareTimes = queryShareTimes(campaignNumber, request);
				}
				if (campaignCount >= campaignLimitValue + shareTimes) {
					flag = false;
				}
			} else if (campaignCount >= campaignLimitValue) {
				flag = false;
			}
			return new ExecuteResult(flag, String.valueOf(campaignCount));
		}
		return new ExecuteResult(true, String.valueOf(campaignCount));
	}
	
	/**
	 * 查询分享次数
	 * 
	 * @param campaignNumber
	 * @param request
	 * @return
	 */
	private int queryShareTimes(String campaignNumber,
			HttpServletRequest request) {
		int shareTimes = 0;
		// 获取分享次数(cookie),key对应shareSuccess方法中的key
		String shareKey = "shared_mark_" + campaignNumber;
		Cookie shareCookie = RequestUtil.getCookie(request, shareKey);
		if (shareCookie != null) {
			String timesStr = shareCookie.getValue();
			int shareTimesFromCookie = timesStr.split(",").length;
			// 获取分享次数(数据库)
			PromotionCampaignShare shareInfo = shareService
					.findByCampaignNumber(campaignNumber);
			int shareTimesFromSql = shareInfo.getShareTimes();
			// 判断分享次数，如果cookie值大于数据库值，则以数据库值为准
			if (shareTimesFromCookie >= shareTimesFromSql) {
				shareTimes = shareTimesFromSql;
			} else {
				shareTimes = shareTimesFromCookie;
			}
		}
		return shareTimes;
	}
}
