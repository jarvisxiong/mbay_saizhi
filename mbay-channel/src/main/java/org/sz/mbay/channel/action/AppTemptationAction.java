package org.sz.mbay.channel.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.sz.mbay.apptemptation.bean.AppTemptation;
import org.sz.mbay.apptemptation.bean.AppTemptationAdvanced;
import org.sz.mbay.apptemptation.bean.AppTemptationBindIp;
import org.sz.mbay.apptemptation.bean.AppTemptationOrderStatistics;
import org.sz.mbay.apptemptation.bean.AppTemptationStrategy;
import org.sz.mbay.apptemptation.constant.error.AppTemptationError;
import org.sz.mbay.apptemptation.form.AppTemptationForm;
import org.sz.mbay.apptemptation.service.AppTemptationBindIpService;
import org.sz.mbay.apptemptation.service.AppTemptationService;
import org.sz.mbay.base.annotation.CacheControl;
import org.sz.mbay.base.annotation.CachePolicy;
import org.sz.mbay.base.annotation.Token;
import org.sz.mbay.base.area.Area;
import org.sz.mbay.base.constant.Globals;
import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.base.exception.ServiceException;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.context.ChannelContext;
import org.sz.mbay.channel.framework.action.BaseAction;
import org.sz.mbay.channel.user.enums.CertStatus;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.common.util.MbayDateFormat;
import org.sz.mbay.operator.bean.TrafficPackage;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.sms.client.MbaySMS;
import org.sz.mbay.sms.template.enums.SMSType;
import org.sz.mbay.trafficorder.bean.TrafficOrder;
import org.sz.mbay.trafficorder.enums.OperatorRechargeStatus;
import org.sz.mbay.trafficorder.enums.TrafficOrderType;
import org.sz.mbay.trafficorder.qo.TrafficOrderQO;
import org.sz.mbay.trafficorder.service.TrafficOrderRefundService;
import org.sz.mbay.trafficorder.service.TrafficOrderService;

import net.sf.json.JSONObject;

/**
 * app诱惑
 * 
 * @author jerry
 */
@Controller
@RequestMapping("app_temptation")
public class AppTemptationAction extends BaseAction {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AppTemptationAction.class);
			
	@Autowired
	private AppTemptationService appTemptationService;
	@Autowired
	private AppTemptationBindIpService appTemptationBindIpService;
	@Autowired
	private TrafficOrderService trafficOrderService;
	@Autowired
	private TrafficOrderRefundService trafficOrderRefundService;
	
	/**
	 * 跳转至微信活动管理平台
	 * 
	 * @return
	 */
	@RequestMapping("workbench")
	public String workbench() {
		return "app_temptation/workbench";
	}
	
	/**
	 * 活动列表
	 * 
	 * @return
	 */
	@RequestMapping("campaign_list")
	public String campaign_list(AppTemptationForm form, Model model,
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
		List<AppTemptation> list = appTemptationService.findAllAppTemptation(
				form, pageinfo);
		model.addAttribute("listevent", list);
		model.addAttribute("event", form);
		model.addAttribute(Globals.PAGEINFO_KEY, pageinfo);
		return "app_temptation/campaign_list";
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
		return "app_temptation/campaign_add";
	}
	
	/**
	 * 添加活动
	 * 
	 * @return 重定向到选择模式
	 */
	@Token(reset = true)
	@RequestMapping("campaign_add")
	public String campaign_add(
			AppTemptation event,
			HttpServletRequest request,
			RedirectAttributes redAttr,
			Model model,
			@RequestParam("ips") String ips) {
		String usernumber = ChannelContext.getSessionChannel().getUserNumber();
		String starttime = request.getParameter("eventstarttime") + " 00:00:00";
		String endtime = request.getParameter("eventendtime") + " 23:59:59";
		event.setStarttime(MbayDateFormat.stringToTime(starttime));
		event.setEndingtime(MbayDateFormat.stringToTime(endtime));
		event.setUsernumber(usernumber);
		event.setCreatetime(DateTime.now());
		
		// 添加活动短信发送通知的控制 默认为true
		boolean flag = true;
		String issendsms = request.getParameter("sendsms");
		if (issendsms == null) {
			flag = false;
		}
		event.setSendsms(flag);
		
		String[] packagests = request.getParameterValues("packageid");// 策略所对应的流量包
		String[] supportArea = request.getParameterValues("area");// 策略所支持的地区
		String[] supportOperator = request.getParameterValues("operator");// 所支持的运营商
		List<AppTemptationStrategy> strategys = new ArrayList<AppTemptationStrategy>();
		try {
			for (int i = 0; i < packagests.length; i++) {
				int p = Integer.valueOf(packagests[i]);
				AppTemptationStrategy strategy = new AppTemptationStrategy();
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
			redAttr.addAttribute(PUBLIC_MSG_NOTE_KEY, "策略编号不正确，请返回重新选择！");
			return REDIRECT_PUBLIC_MSG_NOTE;
		}
		event.setStratetgylist(strategys);
		
		// 设置ip bind
		if (!StringUtils.isEmpty(ips)) {
			List<AppTemptationBindIp> ipList = new ArrayList<AppTemptationBindIp>();
			String[] ipArr = ips.split("[,，;；]");
			for (String ip : ipArr) {
				AppTemptationBindIp bean = new AppTemptationBindIp();
				bean.setIp(ip);
				ipList.add(bean);
			}
			event.setIpList(ipList);
		}
		String number = appTemptationService
				.addAppTemptation(event, usernumber);
		redAttr.addAttribute("campaignNumber",
				DigestUtils.pbeEncrypt(number));
		return "redirect:/app_temptation/to_set_advanced.mbay";
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
		redAttr.addAttribute("campaignNumber",
				DigestUtils.pbeEncrypt(eventnumber));
		return "redirect:/app_temptation/to_set_advanced.mbay";
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
		model.addAttribute("campaignNumber", campaignNumber);
		return "app_temptation/set_advanced";
	}
	
	/**
	 * 活动详情下开发者中心信息页面
	 * 
	 * @param eventnumber
	 * @param model
	 * @return
	 */
	@RequestMapping("to_info_advanced")
	public String to_info_advanced(
			@RequestParam("campaignNumber") String campaignNumber,
			Model model) {
		AppTemptationAdvanced advanced = appTemptationService
				.findAppTemptationAdvanced(campaignNumber);
		if (advanced != null) {
			model.addAttribute("token", advanced.getToken());
		}
		model.addAttribute("eventNumber", campaignNumber);
		model.addAttribute("campaignNumber",
				DigestUtils.pbeEncrypt(campaignNumber));
		return "app_temptation/campaign_info_advanced";
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
	public String set_advanced(
			@RequestParam("campaignNumber") String campaignNumber,
			@RequestParam("token") String token) {
		campaignNumber = DigestUtils.pbeDecrypt(campaignNumber);
		if (campaignNumber == null) {
			throw new ServiceException(
					AppTemptationError.CAMPAIGN_NUMBER_ERROR);
		}
		AppTemptationAdvanced advanced = appTemptationService
				.findAppTemptationAdvanced(campaignNumber);
		ExecuteResult result = null;
		if (advanced == null) {
			advanced = new AppTemptationAdvanced();
			advanced.setCampaignNumber(campaignNumber);
			advanced.setToken(token);
			advanced.setStatus(EnableState.ENABLED);
			result = appTemptationService.addAppTemptationAdvanced(advanced);
		} else {
			advanced.setToken(token);
			result = appTemptationService.updateAppTemptationAdvanced(advanced);
		}
		return String.valueOf(result.isSuccess());
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
		AppTemptation event = appTemptationService
				.findAppTemptation(eventnumber);
						
		// 数据加密
		if (event.getIpList() != null && !event.getIpList().isEmpty()) {
			for (AppTemptationBindIp ip : event.getIpList()) {
				ip.setIdStr(DigestUtils.pbeEncrypt(String.valueOf(ip
						.getId())));
			}
		}
		
		model.addAttribute("event", event);
		model.addAttribute("eventnumber",
				DigestUtils.pbeEncrypt(event.getEventnumber()));
		return "app_temptation/campaign_info";
	}
	
	/**
	 * 取消活动
	 */
	@RequestMapping("cancel_campaign")
	@ResponseBody
	public Object cancel_campaign(
			@RequestParam("eventnumber") String eventnumber) {
		eventnumber = DigestUtils.pbeDecrypt(eventnumber);
		if (eventnumber == null) {
			return new ExecuteResult(false, "活动编号不正确!");
		}
		ExecuteResult result = appTemptationService
				.cancelAppTemptation(eventnumber, ChannelContext
						.getSessionChannel().getUserNumber());
		return result;
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
		boolean result = appTemptationService.updateAppTemptationRepeatEnable(
				eventnumber, repeat_enable);
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
		return appTemptationService.updateAppTemptationContinuable(eventnumber,
				continuable);
	}
	
	/**
	 * 修改活动,短信提醒
	 * 
	 * @param eventnumber
	 * @param continuable
	 * @return
	 */
	@RequestMapping("upd_sendsms_model")
	@ResponseBody
	public boolean upd_sendsms_model(
			@RequestParam("eventnumber") String eventnumber,
			@RequestParam("sendsms") boolean sendsms) {
		eventnumber = DigestUtils.pbeDecrypt(eventnumber);
		if (eventnumber == null) {
			return false;
		}
		return appTemptationService.updateAppTemptationSendSms(eventnumber,
				sendsms);
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
			ExecuteResult result = appTemptationService
					.updateAppTemptationDate(eventnumber, starttime,
							endingtime);
			return result.isSuccess();
		}
		return false;
	}
	
	/**
	 * 删除绑定ip
	 * 
	 * @param campaignNumber
	 * @param id
	 */
	@ResponseBody
	@RequestMapping("delete_bind_ip")
	public Object deleteBindIp(@RequestParam("id") String id) {
		Integer idReal = Integer.parseInt(DigestUtils.pbeDecrypt(id));
		boolean suc = appTemptationBindIpService
				.deleteAppTemptationBindIpById(idReal);
		JSONObject obj = new JSONObject();
		obj.put("status", suc);
		return obj;
	}
	
	/**
	 * 新增ip
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("add_bind_ip")
	public Object addBindIp(
			@RequestParam("campaignNumber") String campaignNumber,
			@RequestParam("ip") String ip) {
		String number = DigestUtils.pbeDecrypt(campaignNumber);
		Integer id = appTemptationBindIpService.addAppTemptationBindIp(number,
				ip);
		JSONObject obj = new JSONObject();
		if (id != null) {
			obj.put("status", true);
			obj.put("id", DigestUtils.pbeEncrypt(String.valueOf(id)));
		} else {
			obj.put("status", false);
		}
		return obj;
	}
	
	/**
	 * 充值记录查询
	 * 
	 * @param campaignNumber
	 * @param model
	 * @return
	 */
	@RequestMapping("traffic_record")
	public String trafficRecord(
			@RequestParam("campaignNumber") String campaignNumber,
			TrafficOrderQO form,
			Model model,
			PageInfo pageInfo) {
		if (form == null || StringUtils.isEmpty(campaignNumber)) {
			return null;
		}
		
		// 本次查询的记录
		String decNumber = DigestUtils.pbeDecrypt(campaignNumber);
		form.setRelateNumber(decNumber);
		form.setOrderType(TrafficOrderType.APP_CAMPAIGN);
		List<TrafficOrder> datas = trafficOrderService.findAllTrafficOrder(
				form,
				pageInfo);
				
		// 查询退款记录
		for (TrafficOrder or : datas) {
			or.setRefund(trafficOrderRefundService.findByOrderNumber(
					or.getNumber()));
		}
		
		model.addAttribute("trafficRecordList", datas);
		model.addAttribute("campaignNumber", campaignNumber);
		model.addAttribute("form", form);
		
		// 统计信息
		AppTemptationOrderStatistics statistics = appTemptationService
				.getAppTemptationOrderStatistics(form);
		model.addAttribute("statistics", statistics);
		
		return "app_temptation/traffic_record";
	}
	
	/**
	 * app诱惑 - 结果查询
	 * 
	 * @param model
	 * @param pageinfo
	 * @param request
	 * @return
	 */
	@RequestMapping("result_list")
	public String resultList(
			Model model,
			PageInfo pageinfo,
			HttpServletRequest request) {
		TrafficOrderQO orderform = initQueryForm(request);
		List<TrafficOrder> trafficcharges = trafficOrderService
				.findAllTrafficOrder(orderform, pageinfo);
		model.addAttribute("trafficcharges", trafficcharges);
		model.addAttribute("orderform", orderform);
		model.addAttribute(Globals.PAGEINFO_KEY, pageinfo);
		return "app_temptation/result_list";
	}
	
	/**
	 * 结果查询 - 导出excel
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("export_result_list")
	public String exportResultList(Model model, HttpServletRequest request) {
		TrafficOrderQO orderform = initQueryForm(request);
		List<TrafficOrder> trafficcharges = trafficOrderService
				.findAllTrafficOrder(orderform, null);
		model.addAttribute("data", trafficcharges);
		return "appTemptationResultListExcel";
	}
	
	/***
	 * app诱惑 - 营销明细
	 * 
	 * @param model
	 * @param pageinfo
	 * @param request
	 * @return
	 */
	@RequestMapping("record")
	public String appTemptationRecord(
			Model model,
			PageInfo pageinfo,
			HttpServletRequest request) {
		TrafficOrderQO orderform = initQueryForm(request);
		List<TrafficOrder> trafficcharges = trafficOrderService
				.findAllTrafficOrder(orderform, pageinfo);
		model.addAttribute("trafficcharges", trafficcharges);
		model.addAttribute("orderform", orderform);
		model.addAttribute(Globals.PAGEINFO_KEY, pageinfo);
		return "app_temptation/result_list";
	}
	
	/**
	 * 营销明细 - 导出excel
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("export_record")
	public String exportRecord(Model model, HttpServletRequest request) {
		TrafficOrderQO orderform = initQueryForm(request);
		List<TrafficOrder> trafficcharges = trafficOrderService
				.findAllTrafficOrder(orderform, null);
		model.addAttribute("data", trafficcharges);
		return "appTemptationRecordExcel";
	}
	
	/**
	 * app诱惑 - 结果查询 - 初始化查询form
	 * 
	 * @param request
	 * @return
	 */
	private TrafficOrderQO initQueryForm(HttpServletRequest request) {
		TrafficOrderQO orderform = new TrafficOrderQO();
		orderform.setUserNumber(ChannelContext.getSessionChannel()
				.getUserNumber());
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		if (starttime != null && starttime.length() > 0) {
			orderform.setStartTime(starttime);
		}
		if (endtime != null && endtime.length() > 0) {
			orderform.setEndTime(endtime);
		}
		String eventnumber = request.getParameter("eventnumber");
		if (!StringUtils.isEmpty(eventnumber)) {
			orderform.setRelateNumber(eventnumber);
		}
		orderform.setMobile(request.getParameter("mobile"));
		orderform.setOrderNumber(request.getParameter("number"));
		String ors = request.getParameter("ors");
		if (!StringUtils.isEmpty(ors)) {
			OperatorRechargeStatus status = OperatorRechargeStatus.valueOf(ors);
			orderform.setOrs(status);
		}
		return orderform;
	}
}
