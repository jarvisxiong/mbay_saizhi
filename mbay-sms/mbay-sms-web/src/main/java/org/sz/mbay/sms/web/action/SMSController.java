package org.sz.mbay.sms.web.action;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.sms.bean.Result;
import org.sz.mbay.sms.bean.SmsTemplate;
import org.sz.mbay.sms.enums.SMSServiceMethod;
import org.sz.mbay.sms.service.SMSService;
import org.sz.mbay.sms.service.SmsTemplateService;
import org.sz.mbay.sms.service.impl.YZXSMSServiceImpl;

@Controller
@RequestMapping("sms")
public class SMSController {
	
	private static Logger LOGGER = LoggerFactory.getLogger(SMSController.class);
	
	// 保存短信模板
	private static Map<String, SmsTemplate> smsTemplatMap = new HashMap<>();
	
	@Autowired
	private SmsTemplateService templateService;
	@Autowired
	private SMSService smsService;
	
	@RequestMapping("sendsmsui")
	public String sendSMSUI() {
		return "sms/sendSMS";
	}
	
	@RequestMapping("recharge_ui")
	public String rechargeUI() {
		return "sms/recharge";
	}
	
	@RequestMapping("callback_ui")
	public String callbackUI() {
		return "sms/callback";
	}
	
	@RequestMapping("voicecode_ui")
	public String sendVoiceCodeUI() {
		return "sms/voiceCode";
	}
	
	@RequestMapping("recharge")
	@ResponseBody
	public String recharge(
			@RequestParam String cardnumber,
			@RequestParam String password) {
		// return smsService.recharge(cardnumber, password);
		return "功能关闭";
	}
	
	@RequestMapping("check_balance")
	@ResponseBody
	public String checkBalance() {
		// return smsService.checkbalance();
		return "功能关闭";
	}
	
	@RequestMapping("callback")
	@ResponseBody
	public Result callBack(
			@RequestParam String mainMobile,
			@RequestParam String toMobile) {
		boolean flag = smsService instanceof YZXSMSServiceImpl;
		if (!flag) {
			Result result = new Result(false, "", "当前短信接口的实现方式无法使用语音功能",
					smsService.getClass().getName());
			return result;
		}
		return ((YZXSMSServiceImpl) smsService).callBack(mainMobile, toMobile);
	}
	
	/**
	 * 发送短信
	 * 
	 * @param mobiles
	 * @param smsId
	 * @param param
	 * @return
	 */
	@RequestMapping("sendsms")
	@ResponseBody
	public Result sendSMS(
			@RequestParam("mobiles") String mobiles,
			@RequestParam("smsName") String smsName,
			@RequestParam("param") String param) {
		LOGGER.info("接收到短信发送请求.手机号:{},模板ID：{},参数：{}", mobiles, smsName, param);
		SmsTemplate template = getSMSTemplate(smsName);
		if (template == null) {
			LOGGER.error("找不到短信模板：{}", smsName);
			return new Result(false, "", "找不到短信模板", "");
		}
		
		SMSServiceMethod method = template.getMethod();
		String impl = method.name();
		LOGGER.info("短信接口实现方：{}", impl);
		SMSService service = null;
		try {
			service = (SMSService) Class.forName(
					"org.sz.mbay.sms.service.impl." + impl).newInstance();
		} catch (Exception e) {
			LOGGER.error("找不到相应的短信接口实现类", e.fillInStackTrace());
			return new Result(false, "", "找不到相应的短信接口实现类", "");
		}
		return service.sendSMS(mobiles, template.getId(), param);
	}
	
	/*
	 * 根据短信模板类型返回对应类型的短信模板，并保存至map
	 */
	private SmsTemplate getSMSTemplate(String smsName) {
		SmsTemplate tmp = smsTemplatMap.get(smsName);
		if (tmp != null) {
			return tmp;
		}
		SmsTemplate template = templateService
				.findSmsTemplateByType(smsName);
		if (template != null) {
			smsTemplatMap.put(smsName, template);
		}
		return template;
	}
	
	/**
	 * 短信模版更新
	 * 
	 * @param smsId
	 */
	@RequestMapping("template/update")
	@ResponseBody
	public boolean templateUpdate(@RequestParam("smsName") String smsName) {
		SmsTemplate template = templateService.findSmsTemplateByType(smsName);
		if (template != null) {
			smsTemplatMap.put(smsName, template);
			return true;
		}
		return false;
	}
	
	/**
	 * 短信模版更新
	 * 
	 * @param smsId
	 */
	@RequestMapping("template/get")
	@ResponseBody
	public SmsTemplate templateGet(@RequestParam("smsName") String smsName) {
		return getSMSTemplate(smsName);
	}
	
	/**
	 * 语音验证码
	 * 
	 * @param mobile
	 * @param code
	 * @return
	 */
	@RequestMapping("sendVoiceCode")
	@ResponseBody
	public Result sendVoiceCode(
			@RequestParam("mobile") String mobile,
			@RequestParam("code") String code) {
		boolean flag = smsService instanceof YZXSMSServiceImpl;
		if (!flag) {
			Result result = new Result(false, "", "当前短信接口的实现方式无法使用语音功能",
					smsService.getClass().getName());
			return result;
		}
		return ((YZXSMSServiceImpl) smsService).sendVoiceCode(mobile, code);
	}
}
