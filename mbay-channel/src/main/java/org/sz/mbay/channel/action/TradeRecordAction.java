package org.sz.mbay.channel.action;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.sz.mbay.base.constant.Globals;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.channel.bean.MbayDepositOrder;
import org.sz.mbay.channel.context.ChannelContext;
import org.sz.mbay.channel.form.MbayDepositOrderForm;
import org.sz.mbay.channel.framework.action.BaseAction;
import org.sz.mbay.channel.service.AppropriationOrderService;
import org.sz.mbay.channel.service.SMSPurchaseOrderService;
import org.sz.mbay.channel.service.StoreCampaginOrderService;
import org.sz.mbay.channel.user.qo.MbayTransferOrderQO;
import org.sz.mbay.channel.user.qo.UserMbayTradeQO;
import org.sz.mbay.channel.user.service.CertificateAuthService;
import org.sz.mbay.channel.user.service.UserAccountService;
import org.sz.mbay.channel.user.service.UserService;
import org.sz.mbay.channel.useraccount.bean.MbayTransferOrder;
import org.sz.mbay.channel.useraccount.bean.UserMbayTrade;
import org.sz.mbay.common.util.MbayDateFormat.DateFormatter;
import org.sz.mbay.trafficrecharge.service.TrafficRechargeService;

@Controller
@RequestMapping("record")
public class TradeRecordAction extends BaseAction {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TradeRecordAction.class);
	
	public static final String ACCOUNTDETAIL = "trade/record/accountdetail";
	public static final String MBAYDEPOSITORDERLIST = "account/mbayDepositOrderRecord";
	public static final String TRANSFER_RECORD = "transfer/transfer_record";
	
	public static final String MBAY_APPROPR_DETAIL = "trade/record/mbay_appropr_detail";
	public static final String MBAY_DEPOSITE_DETAIL = "trade/record/mbay_deposite_detail";
	public static final String SMS_PURCHASE_DETAIL = "trade/record/sms_purchase_detail";
	public static final String TRAFFIC_CHARGE_DETAIL = "trade/record/traffic_charge_detail";
	public static final String TRANSFER_DETAIL = "trade/record/transfer_detail";
	public static final String STORE_CAMPAIGN_DETAIL = "trade/record/store_campaign_detail";
	
	
	@Autowired
	UserAccountService userAccountService;
	
	@Autowired
	TrafficRechargeService trafficService;
	
	@Autowired
	SMSPurchaseOrderService sMSPurchaseOrderService;
	
	@Autowired
	AppropriationOrderService appropriationOrderService;
	
	@Autowired
	CertificateAuthService certificateService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	StoreCampaginOrderService storeCampaginOrderService;
	
	/**
	 * 收支明细
	 * 
	 */
	@RequestMapping("accountdetail")
	public String accountdetail(Model model, PageInfo pageInfo, HttpServletRequest request) {
		UserMbayTradeQO qo = new UserMbayTradeQO();
		qo.setUserNumber(ChannelContext.getSessionChannel().getUserNumber());
		String starttime = request.getParameter("starttime");
		if (starttime != null && starttime.length() > 0) {
			qo.setStartTime(DateTime.parse(starttime + " 00:00:00", DateFormatter.timeFormat));
		}
		String endtime = request.getParameter("endtime");
		if (endtime != null && endtime.length() > 0) {
			qo.setEndTime(DateTime.parse(endtime + " 23:59:59", DateFormatter.timeFormat));
		}
		qo.setTradeNumber(request.getParameter("number"));
		List<UserMbayTrade> accountdetailitems = userAccountService.findAllUserMbayTrade(qo, pageInfo);
		model.addAttribute("accountdetailitems", accountdetailitems);
		model.addAttribute("detail", qo);
		model.addAttribute("pageinfo", pageInfo);
		return ACCOUNTDETAIL;
	}
	
	/**
	 * 所有转账记录
	 */
	@RequestMapping("transfer_record")
	public String transferrecord(Model model, PageInfo pageinfo, HttpServletRequest request) {
		MbayTransferOrderQO qo = new MbayTransferOrderQO();
		qo.setUserNumber(ChannelContext.getSessionChannel().getUserNumber());
		String starttime = request.getParameter("starttime");
		if (starttime != null && starttime.length() > 0) {
			qo.setStartTime(DateTime.parse(starttime + " 00:00:00", DateFormatter.timeFormat));
		}
		String endtime = request.getParameter("endtime");
		if (endtime != null && endtime.length() > 0) {
			qo.setEndTime(DateTime.parse(endtime + " 23:59:59", DateFormatter.timeFormat));
		}
		qo.setOrderNumber(request.getParameter("ordernumber"));
		List<MbayTransferOrder> transferOrders = userAccountService.findAllMbayTransferOrder(qo, pageinfo);
		
		model.addAttribute("transferOrders", transferOrders);
		model.addAttribute("orderForm", qo);
		model.addAttribute("pageinfo", pageinfo);
		return TRANSFER_RECORD;
	}
	
	@RequestMapping("mbayDepositOrderRecord")
	public String mbayDepositOrderRecord(Model model,
			PageInfo pageinfo,
			@RequestParam(required = false) String starttime,
			@RequestParam(required = false) String endtime,
			@RequestParam(required = false) String depositNumber,
			HttpServletRequest request) {
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		MbayDepositOrderForm mbayDepositOrderCriteria = new MbayDepositOrderForm();
		mbayDepositOrderCriteria.setDepositNumber(depositNumber);
		mbayDepositOrderCriteria.setUserNumber(userNumber);
		List<MbayDepositOrder> mbayDepositOrderList = null;
		
		if (StringUtils.isNotEmpty(starttime)) {
			mbayDepositOrderCriteria.setStarttime(DateTime.parse(starttime + " 00:00:00", DateFormatter.timeFormat));
		}
		
		if (StringUtils.isNotEmpty(endtime)) {
			mbayDepositOrderCriteria.setEndtime(DateTime.parse(endtime + " 23:59:59", DateFormatter.timeFormat));
		}
		
		try {
			//TODO:1
			//mbayDepositOrderList = userAccountService.findAllMbayDepositOrder(pageinfo, mbayDepositOrderCriteria);
		} catch (Exception e) {
			LOGGER.error("mbayDepositOrderRecord", e.fillInStackTrace());
		}
		
		model.addAttribute("mbayDepositOrderList", mbayDepositOrderList);
		model.addAttribute(Globals.PAGEINFO_KEY, pageinfo);
		model.addAttribute("mbayDepositOrderCriteria", mbayDepositOrderCriteria);
		return MBAYDEPOSITORDERLIST;
	}
	
	/**
	 * 交易明细
	 * 
	 * @return
	 *//*
	@RequestMapping(value = "tradedetail", method = RequestMethod.GET)
	public String tradeDetail(@RequestParam("sno") String serialNumber, Model model) {
		AccountDetailForm detail = new AccountDetailForm();
		detail.setAccountunumber(ChannelContext.getSessionChannel().getUsernumber());
		detail.setNumber(serialNumber);
		AccountDetail accountDetail = recordservice.findAccountDetail(detail);
		if (accountDetail != null) {
			model.addAttribute("accountDetail", accountDetail);
			switch (accountDetail.getTradetype()) {
			// 转账/赠送
				case TRANSFER:
				case SEND_MBAY:
					// 隐藏账号信息，格式：xxx + "******"
					//TODO:1
					//TransferOrder transferOrder = userAccountService.findTransferOrder(accountDetail.getOrdernumber());
					TransferOrder transferOrder = null;
					
					String userName = transferOrder.getOptuser().getUsername();
					if (userName.length() >= 4) {
						transferOrder.getOptuser().setUsername(userName.substring(0, 3) + "******");
					}
					model.addAttribute("tradeDetail", transferOrder);
					
					// 查询姓名，隐藏信息，格式：x + "*"(剩余字符数)
					try {
						String name = null;
						String tmpName = null;
						long userId = transferOrder.getOptuser().getId();
						if (transferOrder.getOptuser().getProperty() == ChannelProperty.PERSONAL) {
							Certificate cert = certificateService.findCertificate(userId);
							name = cert.getTrue_name();
						} else if (transferOrder.getOptuser().getProperty() == ChannelProperty.ENTERPRISE) {
							ChannelInfo channel = certificateService.findChannelInfo(ChannelContext.getSessionChannel().getUsernumber());
							name = channel.getContactsname();
						}
						tmpName = name;
						if (name.length() >= 2) {
							tmpName = name.substring(0, 1);
							for (int i = 0; i < name.length() - 1; i++) {
								tmpName += "*";
							}
						}
						model.addAttribute("realName", tmpName);
					} catch (Exception e) {
						LOGGER.error("tradeDetail:" + e.fillInStackTrace());
						throw new ServiceException(TradeRecordError.TRADE_RECORD_SEARCH_ERROR);
					}
					return TRANSFER_DETAIL;
					
					// 流量充值
				case TRAFFIC_RECHARGE:
					//TODO:1
					TrafficChargeOrder tcOrder = trafficService.findTrafficChargeOrder(accountDetail.getOrdernumber());
					model.addAttribute("tradeDetail", tcOrder);
					return TRAFFIC_CHARGE_DETAIL;
					
					// 美贝充值
				case MBAY_DEPOSIT:
					//TODO:1
					MbayDepositOrder mdorder = userAccountService.findMbayDepositOrder(accountDetail.getOrdernumber(),
							accountDetail.getDealunumber());
					model.addAttribute("tradeDetail", mdorder);
					return MBAY_DEPOSITE_DETAIL;
					
					// 短信购买
				case SMS_PURCHASE:
					SMSPurchaseOrder smsOrder = sMSPurchaseOrderService.findSMSPurchaseOrder(accountDetail.getOrdernumber());
					model.addAttribute("tradeDetail", smsOrder);
					return SMS_PURCHASE_DETAIL;
					
					// 财务拨款
				case MBAY_APPROPR:
					AppropriationOrder apOrder = appropriationOrderService.findAppropriationOrder(accountDetail.getOrdernumber());
					model.addAttribute("tradeDetail", apOrder);
					return MBAY_APPROPR_DETAIL;
				case STORE_CAMPAGIN:
					StoreCampaignOrder storeCampaignOrder = storeCampaginOrderService.findStoreCampaignOrder(accountDetail.getOrdernumber());
					model.addAttribute("storeCampaignDetail", storeCampaignOrder);
					return STORE_CAMPAIGN_DETAIL;
					// 默认
				default:
					throw new ServiceException(TradeRecordError.TRADE_RECORD_SEARCH_ERROR);
			}
		} else {
			throw new ServiceException(TradeRecordError.TRADE_RECORD_SEARCH_ERROR);
		}*/
	//}
}
