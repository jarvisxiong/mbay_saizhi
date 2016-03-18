package org.sz.mbay.channel.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
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
import org.sz.mbay.base.exception.ServiceException;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.channel.bean.SMSPackage;
import org.sz.mbay.channel.bean.SMSPurchaseOrder;
import org.sz.mbay.channel.context.ChannelContext;
import org.sz.mbay.channel.costant.error.SMSPurchaseError;
import org.sz.mbay.channel.form.SMSPurchaseOrderForm;
import org.sz.mbay.channel.framework.action.BaseAction;
import org.sz.mbay.channel.service.SMSPackageService;
import org.sz.mbay.channel.service.SMSPurchaseOrderService;
import org.sz.mbay.channel.user.bean.UserRemindPoint;
import org.sz.mbay.channel.user.service.UserAccountService;
import org.sz.mbay.channel.user.service.UserContextService;
import org.sz.mbay.common.util.MbayDateFormat.DateFormatter;

@Controller
@RequestMapping("smspurchase")
public class SMSPurchaseAction extends BaseAction {
	
	public static final String SMSPURCHASEORDERLIST = "sms/sMSPurchaseOrder";
	public static final String SMSPACKAGERESULT = "sms/sms_order_pay_success";
	public static final String SMSPACKAGEPAGE = "sms/sMSPurchase";
	public static final String INDEX = "channel/index.mbay";
	public static final String SMS_ORDER_PAY_ERROR = "sms/sms_order_pay_error";
	public static final String SMS_ORDER_PAY_LACK_BLANCE = "sms/sms_order_pay_lack_blance";
	
	@Autowired
	SMSPackageService sMSPackageService;
	
	@Autowired
	UserContextService userContextService;
	
	@Autowired
	SMSPurchaseOrderService sMSPurchaseOrderService;
	
	@Autowired
	UserAccountService userAccountService;
	
	/**
	 * 短信购买交易列表
	 * 
	 * @param pageinfo
	 *            分页信息
	 * 
	 * @param starttime
	 *            查询的开始时间
	 * 
	 * @param endtime
	 * 
	 *            查询的结束时间
	 * @return
	 */
	@RequestMapping("sMSPurchaseRecord")
	public String sMSPurchaseOrderRecord(Model model, PageInfo pageinfo,
			@RequestParam(required = false) String starttime,
			@RequestParam(required = false) String endtime,
			@RequestParam(required = false) String depositNumber,
			HttpServletRequest request) {
		
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		SMSPurchaseOrderForm sMSPurchaseOrderFormCriteria = new SMSPurchaseOrderForm();
		sMSPurchaseOrderFormCriteria.setDepositNumber(depositNumber);
		sMSPurchaseOrderFormCriteria.setUserNumber(userNumber);
		
		if (StringUtils.isNotEmpty(starttime)) {
			sMSPurchaseOrderFormCriteria.setStarttime(DateTime.parse(starttime
					+ " 00:00:00", DateFormatter.timeFormat));
		}
		
		if (StringUtils.isNotEmpty(endtime)) {
			sMSPurchaseOrderFormCriteria.setEndtime(DateTime.parse(endtime
					+ " 23:59:59", DateFormatter.timeFormat));
		}
		
		List<SMSPurchaseOrder> sMSPurchaseOrderList = sMSPurchaseOrderService
				.findAllSMSPurchaseOrder(userNumber, pageinfo,
						sMSPurchaseOrderFormCriteria);
		model.addAttribute("sMSPurchaseOrderList", sMSPurchaseOrderList);
		model.addAttribute("pageinfo", pageinfo);
		model.addAttribute("sMSPurchaseOrderFormCriteria",
				sMSPurchaseOrderFormCriteria);
		return SMSPURCHASEORDERLIST;
	}
	
	@RequestMapping(value = "sMSPackage", method = RequestMethod.GET)
	@ResponseBody
	public List<SMSPackage> sMSPackage(Model model) {
		List<SMSPackage> sMSPackageList = sMSPackageService.findAllSMSPackage();
		return sMSPackageList;
	}
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("renderSMSPurchase")
	@CacheControl(policy = { CachePolicy.NO_CACHE })
	@Token(save = true)
	public String renderSMSPurchase(Model model, HttpServletRequest request) {
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		double mbayCount = userAccountService.getAvailableAmount(userNumber);
		int smsCount = userContextService.findSmsCount(userNumber);
		model.addAttribute("smsCount", smsCount);
		model.addAttribute("mbayCount", mbayCount);
		return SMSPACKAGEPAGE;
	}
	
	/**
	 * 短信购买成功
	 * 
	 * @return
	 */
	@RequestMapping("paySuccess")
	public String resultSuccess() {
		return SMSPACKAGERESULT;
	}
	
	/**
	 * 短信购买失败
	 * 
	 * @return
	 */
	@RequestMapping("payError")
	public String resultError() {
		return SMS_ORDER_PAY_ERROR;
	}
	
	/**
	 * 短信购买美贝不足
	 * 
	 * @return
	 */
	@RequestMapping("lackBlance")
	public String lackBlance() {
		return SMS_ORDER_PAY_LACK_BLANCE;
	}
	
	/**
	 * 执行短信购买
	 * 
	 * @param smsAmount
	 *            购买的短信数量
	 * 
	 * @param mbayAmount
	 *            消费的美贝数量
	 */
	@RequestMapping("sub_smspurchase_order")
	@Token(reset = true)
	public String OperateSMSPurchaseOrder(Model model,
			@RequestParam int smsPackageId, HttpServletRequest request) {
		SMSPackage smsPackage = this.sMSPackageService
				.findSMSPackage(smsPackageId);
		if (smsPackage == null) {
			throw new ServiceException(SMSPurchaseError.NONE_EXIST_SMSPACKAGE);
		}
		this.sMSPurchaseOrderService.purchaseSMS(smsPackage);
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		// 购买成功后，检测当前短信条数是否大于短信提醒阀值，且已经发送了短信余额提醒
		int smsCount = userContextService.findSmsCount(userNumber);
		// 得到用户提醒对象
		UserRemindPoint remindPoint = userContextService
				.findUserRemindPoint(userNumber);
		if (smsCount > remindPoint.getSmsRemindPoint()
				&& remindPoint.isSms_sent()) {
			// 如果购买短信后，短信数量大于短信余额阀值，则将是否已经短信发送改为false
			userContextService.updateSmsSent(userNumber, false);
		}
		return "redirect:" + "/smspurchase/paySuccess.mbay";
	}
}
