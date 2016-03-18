package org.sz.mbay.channel.action;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.base.annotation.Token;
import org.sz.mbay.base.constant.Globals;
import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.base.exception.ServiceException;
import org.sz.mbay.base.exception.error.ErrorInfo;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.web.servlet.tags.token.TokenProcessor;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.context.ChannelContext;
import org.sz.mbay.channel.framework.action.BaseAction;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.common.util.MbayDateFormat;
import org.sz.mbay.duiba.bean.DuiBaRelationShip;
import org.sz.mbay.duiba.service.DuiBaRelationShipService;
import org.sz.mbay.fs.FSClient;
import org.sz.mbay.fs.FSClientFactory;
import org.sz.mbay.fs.FSClientFactory.ClientType;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.sms.client.MbaySMS;
import org.sz.mbay.sms.template.enums.SMSType;
import org.sz.mbay.trafficred.bean.TimeQuantum;
import org.sz.mbay.trafficred.bean.TrafficRedAdvancedConfig;
import org.sz.mbay.trafficred.bean.TrafficRedCampaign;
import org.sz.mbay.trafficred.bean.TrafficRedCampaignMbay;
import org.sz.mbay.trafficred.bean.TrafficRedCampaignPackage;
import org.sz.mbay.trafficred.bean.TrafficRedExchangeRecord;
import org.sz.mbay.trafficred.bean.TrafficRedMbay;
import org.sz.mbay.trafficred.bean.TrafficRedMbayGiftConfig;
import org.sz.mbay.trafficred.bean.TrafficRedPackage;
import org.sz.mbay.trafficred.bean.TrafficRedProductConfig;
import org.sz.mbay.trafficred.bean.TrafficRedShareInfo;
import org.sz.mbay.trafficred.bean.TrafficRedTemplate;
import org.sz.mbay.trafficred.constant.TrafficRedConfig;
import org.sz.mbay.trafficred.constant.TrafficRedConstant;
import org.sz.mbay.trafficred.constant.error.TrafficRedError;
import org.sz.mbay.trafficred.constant.error.TrafficRedMobileError;
import org.sz.mbay.trafficred.dto.TrafficRedMbayDTO;
import org.sz.mbay.trafficred.dto.TrafficRedPackageDTO;
import org.sz.mbay.trafficred.enums.ProductType;
import org.sz.mbay.trafficred.enums.SharkCycleType;
import org.sz.mbay.trafficred.service.TimeQuantumService;
import org.sz.mbay.trafficred.service.TrafficRedAdvancedService;
import org.sz.mbay.trafficred.service.TrafficRedCampaignMbayService;
import org.sz.mbay.trafficred.service.TrafficRedCampaignPackageService;
import org.sz.mbay.trafficred.service.TrafficRedCampaignService;
import org.sz.mbay.trafficred.service.TrafficRedExchangeRecordService;
import org.sz.mbay.trafficred.service.TrafficRedMbayGiftConfigService;
import org.sz.mbay.trafficred.service.TrafficRedMbayService;
import org.sz.mbay.trafficred.service.TrafficRedPackageService;
import org.sz.mbay.trafficred.service.TrafficRedProductConfigService;
import org.sz.mbay.trafficred.service.TrafficRedShareInfoService;
import org.sz.mbay.trafficred.service.TrafficRedTmplService;
import org.sz.mbay.trafficred.valid.group.CampaignCreate;
import org.sz.mbay.trafficred.valid.group.CampaignUpdate;
import org.sz.mbay.trafficred.valid.group.TemplateUpdate;

import net.sf.json.JSONObject;

/**
 * 流量红包控制器
 * 
 * @author Fenlon
 * 		
 */
@Controller
@RequestMapping(value = "/traffic_red/")
public class TrafficRedAction extends BaseAction {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TrafficRedAction.class);
			
	@Autowired
	private TrafficRedCampaignService campaignService;
	@Autowired
	private TimeQuantumService quantumService;
	@Autowired
	private TrafficRedTmplService tmplService;
	@Autowired
	private TrafficRedExchangeRecordService exchangeRecordService;
	@Autowired
	private TrafficRedPackageService packageService;
	@Autowired
	private TrafficRedCampaignPackageService campaignPackageService;
	@Autowired
	private TrafficRedMbayService mbayService;
	@Autowired
	private TrafficRedCampaignMbayService campaignMbayService;
	@Autowired
	private DuiBaRelationShipService relationService;
	@Autowired
	private TrafficRedShareInfoService shareInfoService;
	@Autowired
	private TrafficRedMbayGiftConfigService mbayGiftConfigService;
	@Autowired
	private TrafficRedAdvancedService trafficRedAdvancedService;
	@Autowired
	private TrafficRedProductConfigService productConfigService;
	
	private FSClient fsClient = FSClientFactory.getClient(ClientType.FDFS);
	
	/**
	 * 流量红包主页
	 * 
	 * @return
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index() {
		return "/traffic_red/index";
	}
	
	/**
	 * 创建活动界面
	 * 
	 * @return
	 */
	@Token(save = true)
	@RequestMapping(value = "createUI", method = RequestMethod.GET)
	public String createUI(Model model) {
		// 获取短信模板
		model.addAttribute("sms_tempate",
				MbaySMS.getSMSContent(SMSType.ACTIVITY));
		model.addAttribute("sms_threshold",
				MbaySMS.getSMSContent(SMSType.TR_THRESHOLD_WARNING));
				
		// 获取红包产品
		model.addAttribute("MOBILE", initTrafficPack(OperatorType.MOBILE));
		model.addAttribute("TELECOM", initTrafficPack(OperatorType.TELECOM));
		model.addAttribute("UNICOM", initTrafficPack(OperatorType.UNICOM));
		
		// 获取美贝产品
		model.addAttribute("MBAYLIST", initMbay());
		
		// 获取商城
		List<DuiBaRelationShip> relationList = relationService
				.findList(ChannelContext.getSessionChannel().getUserNumber(),
						null);
		model.addAttribute("relationList", relationList);
		return "/traffic_red/create";
	}
	
	/**
	 * 创建活动,返回创建结果
	 * 
	 * @param model
	 * @param req
	 * @param campaign
	 * @param cResult
	 * @param startTime
	 * @param endTime
	 * @param h0
	 * @param m0
	 * @param h1
	 * @param m1
	 * @param trafficIds
	 * @param mbayIds
	 * @param request
	 * @return
	 */
	@Token(reset = true)
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(Model model, HttpServletRequest req,
			// 基本信息
			@Validated(CampaignCreate.class) TrafficRedCampaign campaign,
			BindingResult cResult,
			// 参与日期
			@RequestParam("start_Time") String startTime,
			@RequestParam("end_Time") String endTime,
			// 活动时间段
			@RequestParam(value = "h0", required = false) String[] h0,
			@RequestParam(value = "m0", required = false) String[] m0,
			@RequestParam(value = "h1", required = false) String[] h1,
			@RequestParam(value = "m1", required = false) String[] m1,
			// 流量/美贝产品
			@RequestParam("traffic_products") String trafficIds,
			@RequestParam("mbay_products") String mbayIds,
			HttpServletRequest request) {
		// 检测错误返回
		if (cResult.hasErrors()) {
			List<ObjectError> errs = new ArrayList<>();
			errs.addAll(cResult.getAllErrors());
			model.addAttribute(PUBLIC_MSG_NOTE_KEY,
					errs.get(0).getDefaultMessage());
			TokenProcessor.getInstance().saveToken(request);
			return createUI(model);
		}
		
		// 设置用户编号
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		campaign.setUserNumber(userNumber);
		
		// 活动日期处理
		startTime = startTime + " 00:00:00";
		endTime = endTime + " 23:59:59";
		DateTime start = MbayDateFormat.stringToTime(startTime);
		DateTime end = MbayDateFormat.stringToTime(endTime);
		if (end.isBeforeNow() || start.isAfter(end)) {
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, "活动日期设置错误");
			TokenProcessor.getInstance().saveToken(request);
			return createUI(model);
		}
		campaign.setStartTime(start);
		campaign.setEndTime(end);
		
		// 设置参与时间段
		campaign.setTimeQuantums(getTimeQuantum(h0, m0, h1, m1));
		
		// 设置参与次数
		if (campaign.getType() == SharkCycleType.NO_LIMITED) {
			campaign.setTimes(-1);
		}
		
		// 设置分享信息的分享次数
		if (campaign.getShareInfo().getShareTimes() == null) {
			campaign.getShareInfo().setShareTimes(0);
		}
		
		// 活动状态设置
		if (start.isAfterNow()) {
			// 如果开始时间在创建时间之后，设置为未开始
			campaign.setStatus(CampaignStatus.NOT_STARTED);
		} else {
			// 如果开始时间在创建时间之前，设置为活动中
			campaign.setStatus(CampaignStatus.IN_ACTIVE);
		}
		
		// 解析红包产品
		campaign.setPackages(analyseTrafficPack(trafficIds));
		
		// 解析美贝产品
		campaign.setMbays(analyseMbay(mbayIds));
		
		// 封装活动产品基本配置信息
		List<TrafficRedProductConfig> configs = new ArrayList<TrafficRedProductConfig>();
		String[] selectedProducts = request
				.getParameterValues("selected_product");
		for (String productType : selectedProducts) {
			ProductType type = ProductType.valueOf(productType);
			TrafficRedProductConfig trPconfig = new TrafficRedProductConfig(
					type);
			String typeStr = type.name();
			trPconfig.setPoolSize(Double
					.valueOf(request.getParameter(typeStr + "_poolsize")));
			trPconfig.setPoolRemain(trPconfig.getPoolSize());
			String dailyLimitStr = request
					.getParameter(typeStr + "_dailylimit");
			double dailyLimit = "".equals(dailyLimitStr)
					? TrafficRedProductConfig.TRAFFIC_UNLIMIT
					: Double.valueOf(dailyLimitStr);
			trPconfig.setDailyLimit(dailyLimit);
			trPconfig.setDailyRemain(trPconfig.getDailyLimit());
			String thresholdStr = request.getParameter(typeStr + "_threshold");
			int threshold = "".equals(thresholdStr)
					? TrafficRedProductConfig.TRAFFIC_UNLIMIT
					: Integer.valueOf(thresholdStr);
			trPconfig.setThreshold(threshold);
			trPconfig.setThresholdWarned(false);
			configs.add(trPconfig);
		}
		
		// 执行创建
		campaignService.create(campaign, configs);
		return "redirect:/traffic_red/detail.mbay?id=" + campaign.getId();
	}
	
	/**
	 * 取消活动界面
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}/cancel", method = RequestMethod.GET)
	public Object cancel(@PathVariable("id") long id) {
		return this.campaignService.cancel(id);
	}
	
	/**
	 * 活动详细界面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public String detail(Long id, Model model, HttpServletRequest request) {
		TrafficRedCampaign campaign = this.campaignService.find(id);
		List<TimeQuantum> quantums = this.quantumService.findByCampaignId(id);
		if (quantums != null) {
			campaign.setTimeQuantums(quantums);
		}
		
		TrafficRedShareInfo shareInfo = shareInfoService
				.findById(campaign.getShareInfo().getId());
		if (shareInfo == null) {
			throw new ServiceException(
					TrafficRedMobileError.SHAREINFO_NOT_EXIST);
		}
		
		campaign.setShareInfo(shareInfo);
		TrafficRedMbayGiftConfig giftConfig = mbayGiftConfigService
				.findById(campaign.getMbayGiftConfig().getId());
		if (giftConfig == null) {
			throw new ServiceException(
					TrafficRedMobileError.MBAY_GIFT_CONFIG_NOT_EXIST);
		}
		campaign.setMbayGiftConfig(giftConfig);
		model.addAttribute("c", campaign);
		TrafficRedAdvancedConfig config = this.trafficRedAdvancedService
				.findAdvancedConfig(id);
		model.addAttribute("advancedConfig", config);
		model.addAttribute("mobileDomain", TrafficRedConfig.TRAFFIC_RED_DOMAIN);
		model.addAttribute("campaignNumber",
				DigestUtils.pbeEncrypt(campaign.getNumber()));
		// 此处 需再整理
		String advancedUrl = TrafficRedConfig.TRAFFIC_RED_DOMAIN
				+ request.getContextPath()
				+ TrafficRedConstant.ADVANCED_SHAKE_URL;
		model.addAttribute("advancedURL", advancedUrl);
		String thresholdSMS = MbaySMS
				.getSMSContent(SMSType.TR_THRESHOLD_WARNING);
		model.addAttribute("thresholdSMS", thresholdSMS);
		// 获取商城
		List<DuiBaRelationShip> relationList = relationService
				.findList(ChannelContext.getSessionChannel().getUserNumber(),
						null);
		model.addAttribute("relationList", relationList);
		return "/traffic_red/detail";
	}
	
	/**
	 * 这个方法应一并查出所有活动产品，但因前期未有产品公共类，无法实现。暂分开处理。
	 * 
	 * @param campaignId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("campaign/{campaignId}/trafficProducts")
	public Object trafficProducts(@PathVariable long campaignId) {
		List<TrafficRedCampaignPackage> trafficProducts = campaignPackageService
				.findByCampaignId(campaignId);
		TrafficRedProductConfig config = productConfigService.findProductConfig(
				campaignId,
				ProductType.TRAFFIC_PACKAGE);
		if (config == null) {// 未选择产品
			config = new TrafficRedProductConfig();
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
	
	@ResponseBody
	@RequestMapping("campaign/{campaignId}/mbayProducts")
	public Object mbProducts(@PathVariable long campaignId) {
		List<TrafficRedCampaignMbay> mbayProducts = campaignMbayService
				.findByCampaignId(campaignId);
		TrafficRedProductConfig config = productConfigService
				.findProductConfig(campaignId, ProductType.MBAY_PACKAGE);
		if (config == null) {// 未选择产品
			config = new TrafficRedProductConfig();
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
	 * 分页显示活动列表
	 * 
	 * @param pageInfo
	 * @return
	 */
	@RequestMapping("list")
	public String list(PageInfo pageInfo, Model model) {
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		List<TrafficRedCampaign> campaigns = this.campaignService
				.findAllByPage(pageInfo, userNumber);
		model.addAttribute("campaigns", campaigns);
		model.addAttribute(Globals.PAGEINFO_KEY, pageInfo);
		return "/traffic_red/list";
	}
	
	/**
	 * 摇一摇记录
	 * 
	 * @param cnumber
	 *            活动编号
	 * @param mobile
	 *            手机号
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param pageInfo
	 *            分页
	 * @param model
	 * @return
	 */
	@RequestMapping("redeem_detail")
	public String campaignRecord(String cnumber, String mobile,
			String startTime, String endTime, PageInfo pageInfo,
			Model model) {
		if (cnumber == null) {
			throw new ServiceException(
					new ErrorInfo("CNUMBER CAN NOT NULL", "活动编号不能为空!"));
		}
		TrafficRedCampaign campaign = this.campaignService
				.findCampaignByNumber(cnumber);
		TrafficRedExchangeRecord record = new TrafficRedExchangeRecord();
		record.setCampaignNumber(cnumber);
		
		DateTime start = null;
		DateTime end = null;
		if ((startTime == "" || startTime == null)) {
			// throw new ServiceException(new ErrorInfo("DATETIME FORMAT ERROR",
			// "输入时间格式错误"));
			start = campaign.getStartTime();
		} else {
			startTime = startTime + " 00:00:00";
			start = MbayDateFormat.stringToTime(startTime);
		}
		if ((endTime == "" || endTime == null)) {
			// throw new ServiceException(new ErrorInfo("DATETIME FORMAT ERROR",
			// "输入时间格式错误"));
			end = campaign.getEndTime();
		} else {
			endTime = endTime + " 23:59:59";
			end = MbayDateFormat.stringToTime(endTime);
		}
		
		if ("".equals(mobile)) {
			mobile = null;
		}
		
		if (mobile != null) {
			record.setMobile(mobile);
		}
		
		List<TrafficRedExchangeRecord> records = exchangeRecordService
				.findByContion(pageInfo, cnumber, mobile, start,
						end);
		model.addAttribute(Globals.PAGEINFO_KEY, pageInfo);
		model.addAttribute("rs", records);
		model.addAttribute("campaign", campaign);
		model.addAttribute("mobile", mobile);
		return "/traffic_red/redeem_detail";
	}
	
	/**
	 * 删除活动产品
	 * 
	 * @param campaignId
	 *            活动ID
	 * @param productType
	 *            产品类型
	 * @param productid
	 *            产品编号
	 * @return
	 */
	@RequestMapping("campaign/{campaignId}/producttype/{productType}/productNumber/{productId}/delete")
	@ResponseBody
	public Object deleteCampaignProduct(@PathVariable long campaignId,
			@PathVariable ProductType productType,
			@PathVariable long productId) {
		if (!this.campaignService.isExistCampaign(campaignId)) {
			return new ExecuteResult(false, "找不到对应的活动！");
		}
		boolean dlelteSuccess = this.campaignService
				.deleteCampaignProduct(campaignId, productType, productId);
		if (dlelteSuccess) {
			return new ExecuteResult(true, "删除成功！");
		} else {
			return new ExecuteResult(false, "删除失败，请重试！");
		}
	}
	
	/**
	 * 修改活动产品权重
	 * 
	 * @param campaignId
	 *            活动编号
	 * @param productType
	 *            产品类型
	 * @param productId
	 *            产品编号
	 * @param ratio
	 *            权重
	 * @return
	 */
	@RequestMapping("/campaign/{campaignId}/producttype/{productType}/productNumber/{productId}/ratio/{ratio}/patch")
	@ResponseBody
	public Object updateCampaignProductRatio(@PathVariable long campaignId,
			@PathVariable ProductType productType,
			@PathVariable long productId, @PathVariable int ratio) {
		if (!this.campaignService.isExistCampaign(campaignId)) {
			return new ExecuteResult(false, "找不到对应的活动！");
		}
		boolean dlelteSuccess = this.campaignService
				.updateProductRatio(campaignId, productType, productId, ratio);
		if (dlelteSuccess) {
			return new ExecuteResult(true, "修改成功！");
		} else {
			return new ExecuteResult(false, "修改失败，请重试！");
		}
	}
	
	@ResponseBody
	@RequestMapping("/campaign/{campaignId}/template/{templateId}/shareTemplate")
	public Object shareTemplate(@PathVariable long campaignId,
			@PathVariable long templateId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!campaignService.isExistCampaign(campaignId)) {
			map.put("success", Boolean.FALSE);
			map.put("message", "未找到对应的活动！");
			return JSONObject.fromObject(map);
		}
		TrafficRedTemplate shareTemplate = tmplService.find(templateId);
		map.put("success", Boolean.TRUE);
		map.put("template", shareTemplate);
		try {
			String jsonString = new ObjectMapper().writeValueAsString(map);
			return jsonString;
		} catch (IOException e) {
		}
		return null;
	}
	
	@RequestMapping("/campaign/{campaignId}/template/{templateId}/update")
	public String updateTemplate(
			@PathVariable long campaignId,
			@PathVariable long templateId,
			@Validated({ TemplateUpdate.class }) TrafficRedTemplate template,
			BindingResult bResult,
			Model model,
			HttpServletRequest request) {
		if (bResult.hasErrors()) {
			model.addAttribute(PUBLIC_MSG_NOTE_KEY,
					bResult.getAllErrors().get(0).getDefaultMessage());
			return detail(campaignId, model, request);
		}
		
		if (!this.campaignService.isExistCampaign(campaignId)) {
			throw new ServiceException(TrafficRedError.NONE_EXIST_CAMPAIGN);
		}
		
		// 设置广告图片1
		if (template.getAdImg1File() != null
				&& !template.getAdImg1File().isEmpty()) {
			fsClient.deleteFile(template.getAdImg1());
			String adImg1 = fsClient.uploadFile(template.getAdImg1File());
			template.setAdImg1(StringUtils.trimToEmpty(adImg1));
		}
		
		// 设置广告图片2
		if (template.getAdImg2File() != null
				&& !template.getAdImg2File().isEmpty()) {
			fsClient.deleteFile(template.getAdImg2());
			String adImg2 = fsClient.uploadFile(template.getAdImg2File());
			template.setAdImg2(StringUtils.trimToEmpty(adImg2));
		}
		
		// 设置摇一摇首页图
		if (template.getShakeIndexImgFile() != null
				&& !template.getShakeIndexImgFile().isEmpty()) {
			fsClient.deleteFile(template.getShakeIndexImg());
			String skImg = fsClient.uploadFile(template.getShakeIndexImgFile());
			template.setShakeIndexImg(StringUtils.trimToEmpty(skImg));
		}
		
		// 设置摇一摇抽奖图
		if (template.getShakeUIImgFile() != null
				&& !template.getShakeUIImgFile().isEmpty()) {
			fsClient.deleteFile(template.getShakeUIImg());
			String skUIImg = fsClient.uploadFile(template.getShakeUIImgFile());
			template.setShakeUIImg(StringUtils.trimToEmpty(skUIImg));
		}
		
		template.setId(templateId);
		tmplService.updateByIdSelective(template);
		return "redirect:/traffic_red/detail.mbay?id=" + campaignId;
	}
	
	/**
	 * 添加产品
	 * 
	 * @param campaignId
	 *            活动编号
	 * @param productType
	 *            产品类型
	 * @param request
	 * @return
	 */
	@RequestMapping("/campaign/{campaignId}/producttype/{productType}/add")
	@ResponseBody
	public Object addCampaignProduct(@PathVariable long campaignId,
			@PathVariable ProductType productType,
			TrafficRedProductConfig config, HttpServletRequest request) {
		String[] productIds = request.getParameterValues("productId");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		switch (productType) {
			case TRAFFIC_PACKAGE:
				List<TrafficRedCampaignPackage> packages = new ArrayList<TrafficRedCampaignPackage>();
				for (int i = 0; i < productIds.length; i++) {
					int productId = Integer.valueOf(productIds[i]);
					String ratioStr = request.getParameter("ratio_" + productId)
							.toString();
					if (StringUtils.isEmpty(ratioStr)) {
						ratioStr = "1";
					}
					int ratio = Integer.valueOf(ratioStr);
					TrafficRedCampaignPackage tpackage = new TrafficRedCampaignPackage();
					tpackage.setCampaignId(campaignId);
					TrafficRedPackage trp = new TrafficRedPackage();
					trp.setPackageId(productId);
					tpackage.setRatio(ratio);
					tpackage.setTrafficPackage(trp);
					packages.add(tpackage);
				}
				try {
					this.campaignService.addTrafficProducts(campaignId,
							packages, config);
				} catch (Exception e) {
					map.put("success", false);
				}
				List<TrafficRedCampaignPackage> trafficProducts = campaignPackageService
						.findByCampaignId(campaignId);
				map.put("trafficProducts", trafficProducts);
				map.put("productConfig", config);
				break;
			case MBAY_PACKAGE:
				List<TrafficRedCampaignMbay> mbaProducts = new ArrayList<TrafficRedCampaignMbay>();
				for (int i = 0; i < productIds.length; i++) {
					int productId = Integer.valueOf(productIds[i]);
					String ratioStr = request.getParameter("ratio_" + productId)
							.toString();
					if (StringUtils.isEmpty(ratioStr)) {
						ratioStr = "1";
					}
					int ratio = Integer.valueOf(ratioStr);
					TrafficRedCampaignMbay mbay = new TrafficRedCampaignMbay();
					mbay.setCampaignId(campaignId);
					mbay.setRatio(ratio);
					TrafficRedMbay redmbay = new TrafficRedMbay();
					redmbay.setId(new Long(productId));
					mbay.setMbay(redmbay);
					mbaProducts.add(mbay);
				}
				try {
					this.campaignService.addMbayProducts(campaignId,
							mbaProducts, config);
				} catch (Exception e) {
					LOGGER.error("addCampaignProduct ERROR:",
							e.fillInStackTrace());
					map.put("success", false);
				}
				List<TrafficRedCampaignMbay> mbayProducts = campaignMbayService
						.findByCampaignId(campaignId);
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
	 * 获取未选择的流量包产品
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/campaign/{campaignId}/traffic_package/unselected")
	public Object trafficProductUnselected(@PathVariable long campaignId) {
		List<TrafficRedCampaignPackage> trafficProducts = campaignPackageService
				.findByCampaignId(campaignId);
		//
		List<TrafficRedPackage> mobilePackages = packageService
				.findByOperatorType(OperatorType.MOBILE);
		List<TrafficRedPackage> unicomPackages = packageService
				.findByOperatorType(OperatorType.UNICOM);
		List<TrafficRedPackage> telecomPackages = packageService
				.findByOperatorType(OperatorType.TELECOM);
		// 考虑到设计问题，产品服务不应该针对活动提供活动未选择的产品 方法。故设计为 产品方法提供所有的产品，由活动自身来剔除。
		for (TrafficRedCampaignPackage cp : trafficProducts) {
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
		boolean selected = productConfigService.isSelectedProduct(campaignId,
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
	 * @param campaignId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/campaign/{campaignId}/mbay_package/unselected")
	public Object mbayProductUnselected(@PathVariable long campaignId) {
		List<TrafficRedCampaignMbay> mbayProducts = campaignMbayService
				.findByCampaignId(campaignId);
		List<TrafficRedMbay> trafficRedMbays = mbayService.findAll();
		for (TrafficRedCampaignMbay rcm : mbayProducts) {
			for (TrafficRedMbay trm : trafficRedMbays) {
				if (trm.getId() == rcm.getMbay().getId()) {
					trafficRedMbays.remove(trm);
					break;
				}
			}
		}
		boolean selected = productConfigService.isSelectedProduct(campaignId,
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
	
	@RequestMapping("campaign/{campaignId}/producttype/{productType}/increase/{poolsize}/add")
	@ResponseBody
	public Object increaseProductPoolSize(@PathVariable long campaignId,
			@PathVariable ProductType productType,
			@PathVariable int poolsize) {
		return productConfigService.increaseProductPoolSize(campaignId,
				productType, poolsize,
				ChannelContext.getSessionChannel().getUserNumber());
	}
	
	@RequestMapping("campaign/{campaignId}/producttype/{productType}/dailylimit/{limit}/update")
	@ResponseBody
	public Object updateProductDailyLimit(@PathVariable long campaignId,
			@PathVariable ProductType productType,
			@PathVariable double limit) {
		ExecuteResult result = productConfigService
				.updateProductDailyLimit(campaignId, productType, limit);
		return result;
	}
	
	@RequestMapping("campaign/{campaignId}/producttype/{productType}/threshold/{threshold}/update")
	@ResponseBody
	public Object updateProductThreshold(@PathVariable long campaignId,
			@PathVariable ProductType productType,
			@PathVariable int threshold) {
		ExecuteResult result = productConfigService
				.updateProductThreshold(campaignId, productType, threshold);
		return result;
	}
	
	@RequestMapping("campaign/{campaignId}/advanced/enabled")
	@ResponseBody
	public Object enabledAdvanced(@PathVariable long campaignId) {
		this.trafficRedAdvancedService.enabledTrafficRedAdvanced(campaignId);
		return ExecuteResult.successExecute;
	}
	
	@RequestMapping("campaign/{campaignId}/advanced/disabled")
	@ResponseBody
	public Object disabledAdvanced(@PathVariable long campaignId) {
		this.trafficRedAdvancedService.disableTrafficRedAdvanced(campaignId);
		return ExecuteResult.successExecute;
	}
	
	@RequestMapping("campaign/{campaignId}/advanced/keyreset")
	@ResponseBody
	public Object advancedKeyReset(@PathVariable long campaignId) {
		String key = this.trafficRedAdvancedService.resetKey(campaignId);
		return new ExecuteResult(true, key);
	}
	
	/**
	 * 已结束活动的更新
	 * 
	 * @param cid
	 * @param endTime
	 * @return
	 */
	@RequestMapping("campaign/update/forOver")
	public String updateCampaignForOver(@RequestParam("id") Long id,
			@RequestParam("end_Time") String endTime) {
		if (!this.campaignService.isExistCampaign(id)) {
			throw new ServiceException(TrafficRedError.NONE_EXIST_CAMPAIGN);
		}
		TrafficRedCampaign campaign = new TrafficRedCampaign();
		campaign.setId(id);
		if (!StringUtils.isEmpty(endTime)) {
			endTime += " 23:59:59";
			DateTime end = MbayDateFormat.stringToTime(endTime);
			campaign.setEndTime(end);
			
			// 活动状态设置
			DateTime now = DateTime.now();
			if (now.isAfter(end)) {
				campaign.setStatus(CampaignStatus.OVER);
			} else {
				campaign.setStatus(CampaignStatus.IN_ACTIVE);
			}
		}
		campaignService.updateByIdSelective(campaign);
		return "redirect:/traffic_red/detail.mbay?id=" + id;
	}
	
	@RequestMapping("campaign/update")
	public String updateCampaign(
			@Validated(CampaignUpdate.class) TrafficRedCampaign campaign,
			BindingResult cResult,
			@RequestParam(value = "end_Time", required = false) String endTime,
			HttpServletRequest request,
			Model model) {
		// 检测错误返回
		if (cResult.hasErrors()) {
			model.addAttribute(PUBLIC_MSG_NOTE_KEY,
					cResult.getAllErrors().get(0).getDefaultMessage());
			return detail(campaign.getId(), model, request);
		}
		
		if (!this.campaignService.isExistCampaign(campaign.getId())) {
			throw new ServiceException(TrafficRedError.NONE_EXIST_CAMPAIGN);
		}
		
		if (!StringUtils.isEmpty(endTime)) {
			endTime += " 23:59:59";
			DateTime end = MbayDateFormat.stringToTime(endTime);
			campaign.setEndTime(end);
			
			// 活动状态设置
			DateTime now = DateTime.now();
			if (now.isAfter(end)) {
				campaign.setStatus(CampaignStatus.OVER);
			} else {
				campaign.setStatus(CampaignStatus.IN_ACTIVE);
			}
		}
		
		String startHour[] = request.getParameterValues("startHour");
		String startMinute[] = request.getParameterValues("startMinute");
		String endHour[] = request.getParameterValues("endHour");
		String endMinute[] = request.getParameterValues("endMinute");
		List<TimeQuantum> timeZones = getTimeQuantum(startHour,
				startMinute, endHour, endMinute);
		campaign.setTimeQuantums(timeZones);
		
		campaignService.updateCampaignInfo(campaign);
		return "redirect:/traffic_red/detail.mbay?id=" + campaign.getId();
	}
	/*----------------------------------------------
	 *                  私有辅助方法          
	 *----------------------------------------------*/
	
	/*
	 * 根据时间点获取时间段对象
	 * @param h0s 时（开始）
	 * @param m0s 分（开始）
	 * @param h1s 时 （结束）
	 * @param m1s 分（结束）
	 * @return1
	 */
	private List<TimeQuantum> getTimeQuantum(String[] h0, String[] m0,
			String[] h1, String[] m1) {
		List<TimeQuantum> quantums = new ArrayList<TimeQuantum>();
		
		// 未提供默认设置为全天可参加
		if (h0 == null || h0.length == 0 || m0 == null || m0.length == 0
				|| h1 == null || h1.length == 0 || m1 == null
				|| m1.length == 0) {
			return quantums;
			/*
			 * TimeQuantum quantum = new TimeQuantum();
			 * quantum.setStartTime(Time.valueOf("00:00:00"));
			 * quantum.setEndTime(Time.valueOf("23:59:59"));
			 * quantums.add(quantum);
			 */
		} else {
			int flag = 0;
			TimeQuantum quantum = null;
			Time startTime = null;
			Time endTime = null;
			String str = null;
			while (flag < h0.length) {
				if (h0[flag].equals("")) {
					flag++;
					continue;
				}
				str = h0[flag] + ":" + m0[flag] + ":" + "00";
				startTime = Time.valueOf(str);
				str = h1[flag] + ":" + m1[flag] + ":" + "00";
				endTime = Time.valueOf(str);
				quantum = new TimeQuantum();
				quantum.setStartTime(startTime);
				quantum.setEndTime(endTime);
				quantums.add(quantum);
				flag++;
			}
		}
		return quantums;
	}
	
	/*
	 * 设置流量产品
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
	
	/*
	 * 设置美贝产品
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
	
	/*
	 * 解析流量产品
	 * @param trafficIds
	 * @return
	 */
	private List<TrafficRedCampaignPackage> analyseTrafficPack(
			String trafficIds) {
		List<TrafficRedCampaignPackage> packs = new ArrayList<TrafficRedCampaignPackage>();
		if (!StringUtils.isEmpty(trafficIds)) {
			String[] pros = trafficIds.split("[,，]");
			String info[] = null;
			for (String pro : pros) {
				info = pro.split("-");
				TrafficRedCampaignPackage pack = new TrafficRedCampaignPackage();
				pack.setRatio(Integer.parseInt(info[1]));
				TrafficRedPackage rp = new TrafficRedPackage();
				rp.setPackageId(Integer.parseInt(info[0]));
				pack.setTrafficPackage(rp);
				packs.add(pack);
			}
		}
		return packs;
	}
	
	/*
	 * 解析美贝产品
	 * @param mbayIds
	 * @return
	 */
	private List<TrafficRedCampaignMbay> analyseMbay(String mbayIds) {
		List<TrafficRedCampaignMbay> mbays = new ArrayList<TrafficRedCampaignMbay>();
		if (!StringUtils.isEmpty(mbayIds)) {
			String[] pros = mbayIds.split("[,，]");
			String info[] = null;
			for (String pro : pros) {
				info = pro.split("-");
				TrafficRedCampaignMbay mbay = new TrafficRedCampaignMbay();
				mbay.setRatio(Integer.parseInt(info[1]));
				TrafficRedMbay mb = new TrafficRedMbay();
				mb.setId(Long.parseLong(info[0]));
				mbay.setMbay(mb);
				mbays.add(mbay);
			}
		}
		return mbays;
	}
	
}
