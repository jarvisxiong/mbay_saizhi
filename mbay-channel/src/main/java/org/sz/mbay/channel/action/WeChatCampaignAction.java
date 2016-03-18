package org.sz.mbay.channel.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.sz.mbay.base.annotation.CacheControl;
import org.sz.mbay.base.annotation.CachePolicy;
import org.sz.mbay.base.annotation.Token;
import org.sz.mbay.base.area.Area;
import org.sz.mbay.base.constant.Globals;
import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.base.enums.CampaignStep;
import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.base.exception.ServiceException;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.web.servlet.tags.token.TokenProcessor;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.context.ChannelContext;
import org.sz.mbay.channel.costant.error.Error;
import org.sz.mbay.channel.costant.error.WeChatCampaignError;
import org.sz.mbay.channel.framework.action.BaseAction;
import org.sz.mbay.channel.user.enums.CertStatus;
import org.sz.mbay.channel.user.service.UserAccountService;
import org.sz.mbay.channel.user.service.UserContextService;
import org.sz.mbay.channel.user.service.UserService;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.common.util.MbayDateFormat;
import org.sz.mbay.fs.FSClient;
import org.sz.mbay.fs.FSClientFactory;
import org.sz.mbay.fs.FSClientFactory.ClientType;
import org.sz.mbay.fs.fastdfs.FDFSFileInfo;
import org.sz.mbay.operator.bean.TrafficPackage;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.sms.client.MbaySMS;
import org.sz.mbay.sms.template.enums.SMSType;
import org.sz.mbay.wechat.bean.WeChatCampaign;
import org.sz.mbay.wechat.bean.WeChatCampaignAdvanced;
import org.sz.mbay.wechat.bean.WeChatCampaignDefaultTemplate;
import org.sz.mbay.wechat.bean.WeChatCampaignStrategy;
import org.sz.mbay.wechat.bean.WeChatCampaignTemplate;
import org.sz.mbay.wechat.qo.WeChatCampaignForm;
import org.sz.mbay.wechat.service.WeChatCampaignService;

import net.sf.json.JSONObject;

/**
 * @Description: 微信伴侣管理
 * @author han.han
 * @date 2014-8-11 下午11:28:51
 * 		
 */
@Controller
@RequestMapping("wechatCampaign")
public class WeChatCampaignAction extends BaseAction {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(WeChatCampaignAction.class);
	/**
	 * 重定向到活动列表
	 */
	public static final String REDIRECT_CAMPAIGN_LIST = "redirect:/wechatCampaign/campaign_list.mbay";
	/**
	 * 活动列表
	 */
	public static final String CAMPAIGN_LIST = "wechat_campaign/campaign_list";
	/**
	 * 活动添加页面
	 */
	public static final String CAMPAIGN_ADD = "wechat_campaign/campaign_add";
	/**
	 * 活动详情
	 */
	public static final String CAMPAIGN_INFO = "wechat_campaign/campaign_info";
	/**
	 * 重定向到设置模板成功页面
	 */
	public static final String REDIRECT_SET_TEMPLATE_SUCCESS = "redirect:/wechatCampaign/set_template_success.mbay";
	/**
	 * 到设置模板成功页面
	 */
	public static final String SET_TEMPLATE_SUCCESS = "wechat_campaign/set_template_success";
	/**
	 * 微信营销工作平台
	 */
	public static final String WORKBENCH = "wechat_campaign/workbench";
	/**
	 * 重定向到选择活动模式页面
	 */
	public static final String REDIRECT_TO_SET_MODEL = "redirect:/wechatCampaign/to_set_model.mbay";
	/**
	 * 选择活动模式页面
	 */
	public static final String SET_MODEL = "wechat_campaign/set_model";
	/**
	 * 重定向到模板页面
	 */
	public static final String REDIRECT_TO_SET_TEMPLATE = "redirect:/wechatCampaign/to_set_template.mbay";
	/**
	 * 模板页面
	 */
	public static final String SET_TEMPLATE = "wechat_campaign/set_template";
	/**
	 * 开发者模式页面
	 */
	public static final String SET_ADVANCED = "wechat_campaign/set_advanced";
	/**
	 * 活动详情-模板页面
	 */
	public static final String CAMPAIGN_INFO_TEMPLATE = "wechat_campaign/campaign_info_template";
	/**
	 * 活动详情-开发者中心
	 */
	public static final String CAMPAIGN_INFO_ADVANCED = "wechat_campaign/campaign_info_advanced";
	/**
	 * 重定向至活动详情-模板页面
	 */
	public static final String REDIRECT_TO_CAMPAIGN_INFO_TEMPLATE = "redirect:/wechatCampaign/to_campaign_info_template.mbay?message=success";
	
	@Autowired
	WeChatCampaignService service;
	@Autowired
	UserContextService usercontextservice;
	@Autowired
	UserAccountService userAccountService;
	@Autowired
	UserService userService;
	
	/**
	 * fastdfs文件管理
	 */
	private FSClient fsClient = FSClientFactory.getClient(ClientType.FDFS);
	
	/**
	 * 跳转至微信活动管理平台
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
	public String campaign_list(WeChatCampaignForm form, Model model,
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
		List<WeChatCampaign> list = this.service.findAllWeChatCampaign(form,
				pageinfo);
		model.addAttribute("listevent", list);
		model.addAttribute("event", form);
		model.addAttribute(Globals.PAGEINFO_KEY, pageinfo);
		return CAMPAIGN_LIST;
	}
	
	/**
	 * 到添加活动页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "to_campaign_add", method = RequestMethod.GET)
	@CacheControl(policy = { CachePolicy.NO_CACHE })
	@Token(save = true)
	public String to_campaign_add(Model model) {
		// 添加活动页面显示短信模板信息
		String sms_tempate = MbaySMS.getSMSContent(SMSType.ACTIVITY);
		model.addAttribute("sms_tempate", sms_tempate);
		return CAMPAIGN_ADD;
	}
	
	/**
	 * 添加活动
	 * 
	 * @return 重定向到选择模式
	 */
	@Token(reset = true)
	@RequestMapping("campaign_add")
	public String campaign_add(@Validated WeChatCampaign event,
			BindingResult br, HttpServletRequest request,
			RedirectAttributes redAttr, Model model) {
		if (br.hasErrors()) {
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, Error.FORM_VALIDATE_ERROR);
			TokenProcessor.getInstance().saveToken(request);
			return to_campaign_add(model);// 返回至申请页面
		}
		String starttime = request.getParameter("eventstarttime") + " 00:00:00";
		String endtime = request.getParameter("eventendtime") + " 23:59:59";
		event.setStarttime(MbayDateFormat.stringToTime(starttime));
		event.setEndingtime(MbayDateFormat.stringToTime(endtime));
		event.setUsernumber(ChannelContext.getSessionChannel().getUserNumber());
		event.setCreatetime(DateTime.now());
		// 添加活动短信发送通知的控制 默认为true
		boolean flag = request.getParameter("sendsms") != null;
		event.setSendsms(flag);
		String[] packagests = request.getParameterValues("packageid");// 策略所对应的流量包
		String[] supportArea = request.getParameterValues("area");// 策略所支持的地区
		String[] supportOperator = request.getParameterValues("operator");// 所支持的运营商
		List<WeChatCampaignStrategy> strategys = new ArrayList<WeChatCampaignStrategy>();
		try {
			for (int i = 0; i < packagests.length; i++) {
				int p = Integer.valueOf(packagests[i]);
				WeChatCampaignStrategy strategy = new WeChatCampaignStrategy();
				TrafficPackage tpackage = new TrafficPackage();
				tpackage.setId(p);
				strategy.setTrafficPackage(tpackage);
				strategy.setSupportArea(Area.valueOf(Integer
						.valueOf(supportArea[i])));
				strategy.setSupportOperator(OperatorType.valueOf(Integer
						.valueOf(supportOperator[i])));
				strategys.add(strategy);
			}
		} catch (NumberFormatException e) {
			LOGGER.error("添加策略，从页面获取策略id，转换为int类型失败", e.fillInStackTrace());
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, "策略编号不正确，请返回重新选择！");
			TokenProcessor.getInstance().saveToken(request);
			return to_campaign_add(model);// 返回至申请页面
		}
		event.setStratetgylist(strategys);
		String number = service.addCampaign(event);
		redAttr.addAttribute("campaignNumber",
				DigestUtils.pbeEncrypt(number));
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
	public String to_set_model(HttpServletRequest request, Model model) {
		String campaignNumber = request.getParameter("campaignNumber");
		String eventnumber = DigestUtils.pbeDecrypt(campaignNumber);
		if (eventnumber == null) {
			throw new ServiceException(
					WeChatCampaignError.CAMPAIGN_NUMBER_ERROR);
		}
		WeChatCampaign campaign = service.findWeChatCampaign(eventnumber,
				ChannelContext.getSessionChannel().getUserNumber());
		if (campaign == null) {
			throw new ServiceException(
					WeChatCampaignError.CAMPAIGN_NUMBER_ERROR);
		}
		model.addAttribute("campaignName", campaign.getEventname());
		model.addAttribute("campaignNumber", campaignNumber);
		return SET_MODEL;
	}
	
	/**
	 * 重定向到模板
	 * 
	 * @author tom
	 * @time 2014-9-10下午2:45:31
	 */
	@RequestMapping("to_set_template")
	@Token(save = true)
	@CacheControl(policy = { CachePolicy.NO_CACHE })
	public String to_set_template(
			@RequestParam("campaignNumber") String campaignNumber,
			Model model) {
		String eventnumber = DigestUtils.pbeDecrypt(campaignNumber);
		if (eventnumber == null) {
			throw new ServiceException(
					WeChatCampaignError.CAMPAIGN_NUMBER_ERROR);
		}
		WeChatCampaign campaign = service.findWeChatCampaign(eventnumber,
				ChannelContext.getSessionChannel().getUserNumber());
		if (campaign == null) {
			throw new ServiceException(
					WeChatCampaignError.CAMPAIGN_NUMBER_ERROR);
		}
		model.addAttribute("campaignName", campaign.getEventname());
		model.addAttribute("eventnumber", campaignNumber);
		List<WeChatCampaignDefaultTemplate> list = this.service
				.findWeChatCampaignDefaultTemplateList();
		// 变更list下图片地址为完整地址
		List<WeChatCampaignDefaultTemplate> list_new = new ArrayList<WeChatCampaignDefaultTemplate>();
		for (WeChatCampaignDefaultTemplate template : list) {
			FDFSFileInfo back_fileinfo = (FDFSFileInfo) fsClient
					.getFileInfo(template.getBackPicture());
			template.setBackPicture(back_fileinfo.getFullPath());
			list_new.add(template);
		}
		model.addAttribute("list", list_new);
		return SET_TEMPLATE;
	}
	
	/**
	 * 模板提交
	 * 
	 * @param certificate
	 * @return
	 */
	@RequestMapping("set_template")
	@Token(reset = true)
	public String set_template(
			@RequestParam(value = "bk", required = false) MultipartFile bk,
			@RequestParam(value = "givesuccess",
					required = false) MultipartFile givesuccess,
			@RequestParam(value = "shareImageFile",
					required = false) MultipartFile shareImageFile,
			@RequestParam("eventnumber") String eventnumber,
			@RequestParam("eventDefaultTemplateId") int defaultTemplateId,
			@ModelAttribute WeChatCampaignTemplate campaignTemplate,
			@RequestParam(value = "info", required = false) boolean info,  // 判断是否是由详情页面提交过来的:true->是,false->否
			HttpServletRequest request, Model model,
			RedirectAttributes redAttr) {
		String pbeeventnumber = eventnumber;
		eventnumber = DigestUtils.pbeDecrypt(eventnumber);
		if (eventnumber == null) {
			throw new ServiceException(
					WeChatCampaignError.CAMPAIGN_NUMBER_ERROR);
		}
		campaignTemplate.setEventnumber(eventnumber);
		// 设置分享图片
		if (campaignTemplate.getShareTimes() > 0) {
			if (shareImageFile != null && shareImageFile.getSize() > 0) {// 设置了分享图片
				String picture = fsClient.uploadFile(shareImageFile);
				if (!StringUtils.isEmpty(picture)) {
					campaignTemplate.setShareImage(picture);
				} else {
					TokenProcessor.getInstance().resetToken(request);
					model.addAttribute(PUBLIC_MSG_NOTE_KEY, "分享图片上传失败");
					to_set_template(eventnumber, model);
				}
			}
		}
		// 设置成功图片
		if (givesuccess != null && givesuccess.getSize() > 0) {
			String picture = fsClient.uploadFile(givesuccess);
			if (!StringUtils.isEmpty(picture)) {
				campaignTemplate.setSuccessPhoto(picture);
			} else {
				TokenProcessor.getInstance().resetToken(request);
				model.addAttribute(PUBLIC_MSG_NOTE_KEY, "成功图片上传失败");
				to_set_template(eventnumber, model);
			}
		}
		if (defaultTemplateId != 0) {// 表示选用的为默认模板
			WeChatCampaignDefaultTemplate defaultTemplate = this.service
					.findWeChatCampaignDefaultTemplateById(defaultTemplateId);
			campaignTemplate.setBackphoto(defaultTemplate.getBackPicture());
		} else {// 表示用户自定义的图片背景模板
			// 背景图片
			if (bk != null && bk.getSize() > 0) {
				String picture = fsClient.uploadFile(bk);
				if (!StringUtils.isEmpty(picture)) {
					campaignTemplate.setBackphoto(picture);
				} else {
					TokenProcessor.getInstance().resetToken(request);
					model.addAttribute(PUBLIC_MSG_NOTE_KEY, "自定义背景图片上传失败");
					to_set_template(eventnumber, model);
				}
			}
		}
		// 设置用户自定义编辑模板
		this.service.setCampaignTemplateWithCustom(eventnumber,
				campaignTemplate);
		redAttr.addAttribute("eventnumber", eventnumber);
		redAttr.addAttribute("campaignNumber", pbeeventnumber);
		String link_url = REDIRECT_SET_TEMPLATE_SUCCESS;
		if (info) {
			link_url = REDIRECT_TO_CAMPAIGN_INFO_TEMPLATE;
		}
		return link_url;
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
			@RequestParam("eventnumber") String eventnumber, Model model) {
		if (!this.service.isExistingCampaign(eventnumber, ChannelContext
				.getSessionChannel().getUserNumber())) {
			throw new ServiceException(
					WeChatCampaignError.CAMPAIGN_NUMBER_ERROR);
		}
		WeChatCampaign campaign = service.findWeChatCampaign(eventnumber,
				ChannelContext.getSessionChannel().getUserNumber());
		model.addAttribute("campaignName", campaign.getEventname());
		WeChatCampaignTemplate campaignTempate = this.service
				.findCampaignTemplate(eventnumber);
		List<WeChatCampaignDefaultTemplate> list = this.service
				.findWeChatCampaignDefaultTemplateList();
		// 变更list下图片地址为完整地址
		List<WeChatCampaignDefaultTemplate> list_new = new ArrayList<WeChatCampaignDefaultTemplate>();
		for (WeChatCampaignDefaultTemplate template : list) {
			FDFSFileInfo back_fileinfo = (FDFSFileInfo) fsClient
					.getFileInfo(template.getBackPicture());
			template.setBackPicture(back_fileinfo.getFullPath());
			list_new.add(template);
		}
		if (campaignTempate == null) {
			model.addAttribute("reviewEnable", false);
		} else {
			model.addAttribute("campaignTempate", campaignTempate);
			FDFSFileInfo back_fileinfo = (FDFSFileInfo) fsClient
					.getFileInfo(campaignTempate.getBackphoto());
			campaignTempate.setBackphoto(back_fileinfo.getFullPath());
			String shareImage = "";
			if (!StringUtils.isEmpty(campaignTempate.getShareImage())) {
				FDFSFileInfo share_fileinfo = (FDFSFileInfo) fsClient
						.getFileInfo(campaignTempate.getShareImage());
				shareImage = share_fileinfo.getFullPath();
			}
			campaignTempate.setShareImage(shareImage);
			String successPhoto = "";
			if (!StringUtils.isEmpty(campaignTempate.getSuccessPhoto())) {
				FDFSFileInfo success_fileinfo = (FDFSFileInfo) fsClient
						.getFileInfo(campaignTempate.getSuccessPhoto());
				successPhoto = success_fileinfo.getFullPath();
			}
			campaignTempate.setSuccessPhoto(successPhoto);
			model.addAttribute("reviewEnable", true);
		}
		CampaignStatus status = this.service.findCampaignStatus(eventnumber);
		model.addAttribute("list", list_new);
		model.addAttribute("decode_campaignNumber", eventnumber);
		model.addAttribute("eventnumber",
				DigestUtils.pbeEncrypt(eventnumber));
		model.addAttribute("campaignStatus", status);
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
		if (!this.service.isExistingCampaign(campaignNumber, ChannelContext
				.getSessionChannel().getUserNumber())) {
			throw new ServiceException(
					WeChatCampaignError.CAMPAIGN_NUMBER_ERROR);
		}
		WeChatCampaign campaign = service.findWeChatCampaign(campaignNumber,
				ChannelContext.getSessionChannel().getUserNumber());
		model.addAttribute("campaignName", campaign.getEventname());
		CampaignStatus status = this.service.findCampaignStatus(campaignNumber);
		model.addAttribute("status", status);
		WeChatCampaignAdvanced advanced = this.service
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
	 * 创建模板成功
	 * 
	 * @param eventnumber
	 * @param redAttr
	 * @param model
	 * @return
	 */
	@RequestMapping("set_template_success")
	public String set_template_success(
			@RequestParam("campaignNumber") String campaignNumber,
			RedirectAttributes redAttr, Model model) {
		campaignNumber = DigestUtils.pbeDecrypt(campaignNumber);
		if (campaignNumber == null) {
			throw new ServiceException(
					WeChatCampaignError.CAMPAIGN_NUMBER_ERROR);
		}
		WeChatCampaign campaign = service.findWeChatCampaign(campaignNumber,
				ChannelContext.getSessionChannel().getUserNumber());
		if (campaign == null) {
			throw new ServiceException(
					WeChatCampaignError.CAMPAIGN_NUMBER_ERROR);
		}
		model.addAttribute("campaignName", campaign.getEventname());
		WeChatCampaignTemplate emodel = this.service
				.findCampaignTemplate(campaignNumber);
		model.addAttribute("model", emodel);
		return SET_TEMPLATE_SUCCESS;
	}
	
	/**
	 * 重定向到设置开发者模式页面
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
					WeChatCampaignError.CAMPAIGN_NUMBER_ERROR);
		}
		WeChatCampaign campaign = service.findWeChatCampaign(eventnumber,
				ChannelContext.getSessionChannel().getUserNumber());
		if (campaign == null) {
			throw new ServiceException(
					WeChatCampaignError.CAMPAIGN_NUMBER_ERROR);
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
		WeChatCampaignAdvanced advanced = this.service
				.findCampaignAdvanced(campaignNumber);
		ExecuteResult result = null;
		if (advanced == null) {
			advanced = new WeChatCampaignAdvanced();
			advanced.setCampaignNumber(campaignNumber);
			advanced.setToken(token);
			advanced.setStatus(EnableState.ENABLED);
			result = service.addCampaignAdvanced(advanced);
		} else {
			advanced.setToken(token);
			result = service.updateCampaignAdvanced(advanced);
		}
		return result;
	}
	
	/**
	 * 活动详情
	 * 
	 * @param eventnumber
	 * @param redAttr
	 * @param model
	 * @return
	 */
	@RequestMapping("campaign_info")
	public String campaign_info(
			@RequestParam("eventnumber") String eventnumber,
			RedirectAttributes redAttr, Model model) {
		WeChatCampaign event = this.service.findWeChatCampaign(eventnumber,
				ChannelContext.getSessionChannel().getUserNumber());
		if (event == null) {
			throw new ServiceException(
					WeChatCampaignError.CAMPAIGN_NUMBER_ERROR);
		}
		WeChatCampaignTemplate template = this.service
				.findCampaignTemplate(eventnumber);
		if (template != null) {
			model.addAttribute("url", template.getEventurl());
		}
		model.addAttribute("event", event);
		model.addAttribute("eventnumber",
				DigestUtils.pbeEncrypt(event.getEventnumber()));
		return CAMPAIGN_INFO;
	}
	
	/**
	 * 取消活动
	 */
	@RequestMapping("cancle_campaign")
	@ResponseBody
	public JSONObject cancel_campaign(
			@RequestParam("eventnumber") String eventnumber) {
		eventnumber = DigestUtils.pbeDecrypt(eventnumber);
		if (eventnumber == null) {
			return JSONObject.fromObject(new ExecuteResult(false, "活动编号不正确!"));
		}
		ExecuteResult result = this.service.cancelCampaign(eventnumber,
				ChannelContext.getSessionChannel().getUserNumber());
		return JSONObject.fromObject(result);
	}
	
	/**
	 * 继续完善活动
	 * 
	 * @return
	 */
	@RequestMapping("continue_campaign")
	public String continue_campaign(
			@RequestParam("eventnumber") String eventnumber,
			RedirectAttributes redAttr) {
		CampaignStep eventstep = this.service.findCampaignStep(eventnumber,
				ChannelContext.getSessionChannel().getUserNumber());
		if (eventstep.equals(CampaignStep.CREATED_CAMPAIGN)) {
			redAttr.addAttribute("eventnumber",
					DigestUtils.pbeEncrypt(eventnumber));
			return REDIRECT_TO_SET_TEMPLATE;
		}
		return "";
	}
	
	/**
	 * 修改活动,单个号码是否可重复参与
	 * 
	 * @param eventnumber
	 * @param repeat_enable
	 * @return
	 */
	@RequestMapping("upd_repeat_model")
	@ResponseBody
	public boolean upd_repeat_model(
			@RequestParam("eventnumber") String eventnumber,
			@RequestParam("repeat_enable") boolean repeat_enable) {
		eventnumber = DigestUtils.pbeDecrypt(eventnumber);
		if (eventnumber == null) {
			return false;
		}
		boolean result = this.service.updateCampaignRepeatEnable(eventnumber,
				repeat_enable);
		return result;
	}
	
	/**
	 * 修改活动,超出数量是否可继续兑换
	 * 
	 * @param eventnumber
	 * @param continuable
	 * @return
	 */
	@RequestMapping("upd_continuable_model")
	@ResponseBody
	public boolean upd_continuable_model(
			@RequestParam("eventnumber") String eventnumber,
			@RequestParam("continuable") boolean continuable) {
		eventnumber = DigestUtils.pbeDecrypt(eventnumber);
		if (eventnumber == null) {
			return false;
		}
		return service.updateCampaignContinuable(eventnumber, continuable);
	}
	
	/**
	 * 修改活动,是否可直接领取
	 * 
	 * @param eventnumber
	 * @param directEnable
	 * @return
	 */
	@RequestMapping("changeDirectEnable")
	@ResponseBody
	public boolean changeDirectEnable(
			@RequestParam("eventnumber") String eventnumber,
			@RequestParam("directEnable") boolean directEnable) {
		eventnumber = DigestUtils.pbeDecrypt(eventnumber);
		if (eventnumber == null) {
			return false;
		}
		return service.updateCampaignDirectEnable(eventnumber, directEnable);
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
			ExecuteResult result = this.service.updateCampaignDate(eventnumber,
					starttime, endingtime);
			return result.isSuccess();
		}
		return false;
	}
}
