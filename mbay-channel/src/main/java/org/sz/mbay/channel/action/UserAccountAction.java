package org.sz.mbay.channel.action;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.sz.mbay.base.annotation.CacheControl;
import org.sz.mbay.base.annotation.CachePolicy;
import org.sz.mbay.base.annotation.Token;
import org.sz.mbay.base.constant.Globals;
import org.sz.mbay.base.exception.ServiceException;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.web.servlet.tags.token.TokenProcessor;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.bean.AssetBank;
import org.sz.mbay.channel.bean.RemittanceRecord;
import org.sz.mbay.channel.context.ChannelConfig;
import org.sz.mbay.channel.context.ChannelContext;
import org.sz.mbay.channel.costant.error.Error;
import org.sz.mbay.channel.enums.RemittanceStatus;
import org.sz.mbay.channel.framework.action.BaseAction;
import org.sz.mbay.channel.service.AssetBankService;
import org.sz.mbay.channel.service.RemittanceRecordService;
import org.sz.mbay.channel.user.bean.User;
import org.sz.mbay.channel.user.enums.CertStatus;
import org.sz.mbay.channel.user.error.UserAccountTradeError;
import org.sz.mbay.channel.user.qo.TrafficDetailQO;
import org.sz.mbay.channel.user.qo.TrafficTransferQO;
import org.sz.mbay.channel.user.service.MBAccountService;
import org.sz.mbay.channel.user.service.RedTrafficAccountService;
import org.sz.mbay.channel.user.service.UserAccountService;
import org.sz.mbay.channel.user.service.UserService;
import org.sz.mbay.channel.user.wrap.TrafficTransferInfo;
import org.sz.mbay.channel.useraccount.bean.MbayTraffic;
import org.sz.mbay.channel.useraccount.bean.MbayTrafficTransferOrder;
import org.sz.mbay.channel.useraccount.bean.MbayTransferInfo;
import org.sz.mbay.channel.useraccount.bean.RedTraffic;
import org.sz.mbay.channel.useraccount.bean.RedTrafficTransferOrder;
import org.sz.mbay.channel.useraccount.bean.UserAccount;
import org.sz.mbay.channel.useraccount.bean.UserMbayTrafficTrade;
import org.sz.mbay.channel.useraccount.bean.UserRedTrafficTrade;
import org.sz.mbay.common.util.MbayDateFormat;
import org.sz.mbay.common.util.MbayDateFormat.DateFormatter;
import org.sz.mbay.common.util.Smart2000Util;
import org.sz.mbay.common.util.Smart2000Util.Smart2000Data;

import net.sf.json.JSONObject;

/**
 * 美贝资产Action 处理,用户美贝资产账户
 * 
 * @author ONECITY
 */
@Controller
@RequestMapping("account")
public class UserAccountAction extends BaseAction {
	
	// /TypeAliasRegistry df;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserAccountAction.class);
			
	/**
	 * 账户信息管理页面
	 */
	public static final String ACCOUNTMANAGER = "account/accountmanager";
	
	public static final String DEPOSITPREPROCESS = "account/depositPreprocess";
	
	public static final String TRANSFER_PAYMENT_FILL = "transfer/payment/fill";
	
	public static final String MBAY_TRAFFIC_FILL = "transfer/payment/fill";
	
	public static final String RED_TRAFFIC_FILL = "transfer/payment/fill";
	
	public static final String TRANSFER_ERROR = "transfer/error";
	
	public static final String REDIRECT_TRANSFER_CONFIRM = "redirect:/account/transfer/payment/confirm.mbay";
	
	public static final String TRANSFER_CONFIRM = "transfer/payment/confirm";
	
	public static final String TRANSFER_CASHIER_SUCCESS = "transfer/payment/success";
	// 重定向到美贝充值支付
	public static final String RECIRECT_DEPOSIT_PAY = "redirect:/account/depositPay.mbay";
	// 美贝充值支付页面
	public static final String DEPOSIT_PAY = "account/account_recharge_pay";
	
	public static final String REMITTANCERECORD_LIST = "account/remittance_record_list";
	
	public static final String REDIRECT_REMITTANCERECORD_LIST = "redirect:/account/remittanceRecord/list.mbay";
	// 联系上级充值
	public static final String UNABLE_TO_RECHARGE = "account/unable_to_recharge";
	// 美贝流量转账UI页面
	private static final String TO_TRANSFER = "account/mbaytraffic/transferui";
	// 美贝流量转账成功
	private static final String MBAY_TRAFFIC_TRANSFER_SUCCESS = "account/mbaytraffic/transfer_success";
	// 美贝流量转账记录
	private static final String MBAY_TRAFFIC_TRANSFER_RECORD = "account/mbaytraffic/transfer_record";
	// 美贝流量交易明细
	private static final String MBAY_TRAFFIC_TRADE_DETAIL = "account/mbaytraffic/trade_detail";
	
	// 红包流量转账UI页面
	private static final String REDTRAFFIC_TO_TRANSFER = "account/redtraffic/transferui";
	// 红包流量转账成功
	private static final String RED_TRAFFIC_TRANSFER_SUCCESS = "account/redtraffic/transfer_success";
	// 红包流量转账记录
	private static final String RED_TRAFFIC_TRANSFER_RECORD = "account/redtraffic/transfer_record";
	// 红包流量交易明细
	private static final String RED_TRAFFIC_TRADE_DETAIL = "account/redtraffic/trade_detail";
	// 美贝红包账户转入UI
	private static final String MBAY_TRANSFER_IN_RED_UI = "account/redtraffic/mbay_transfer_in_red";
	
	private static final String MBAY_TRANSFER_IN_RED_SUCCESS = "account/redtraffic/mbay_transfer_in_rde_success";
	
	@Autowired
	MBAccountService mbAccountService;
	
	@Autowired
	RedTrafficAccountService redTrafficAccountService;
	
	@Autowired
	UserAccountService accountService;
	
	@Autowired
	UserService channelservice;
	
	@Autowired
	AssetBankService bankservice;
	
	@Autowired
	RemittanceRecordService recordservice;
	
	/**
	 * 账户管理
	 * 
	 * @return
	 */
	@RequestMapping(value = "accountset", method = RequestMethod.GET)
	public String accountset(Model model) {
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		UserAccount useraccount = accountService.findUserAccount(userNumber);
		User user = this.channelservice.findUser(userNumber);
		MbayTraffic mbaytraffic = mbAccountService.findMbayTraffic(userNumber);
		RedTraffic redtraffic = redTrafficAccountService
				.findRedTraffic(userNumber);
		model.addAttribute("useraccount", useraccount);
		model.addAttribute("user", user);
		model.addAttribute("mbaytraffic", mbaytraffic);
		model.addAttribute("redtraffic", redtraffic);
		// System.out.println("start");
		// for(int i=0;i<500;i++){
		// new Thread(()->{
		// this.accountService.expenditure("549ED68B",
		// TradeType.TRAFFIC_RECHARGE, "201507161000112345", 20, "并发测试");
		// System.out.println(Thread.currentThread().getName());
		// },"thread-"+i).start();;
		// }
		return ACCOUNTMANAGER;
	}
	
	/**
	 * 到账户充值页面
	 * 
	 * @return
	 */
	@RequestMapping("depositPreprocess")
	@CacheControl(policy = CachePolicy.NO_CACHE)
	@Token(save = true)
	public String depositPreprocess(Model model, HttpServletRequest request,
			RedirectAttributes redAttr) {
		// 暂时屏蔽账户充值
		return UNABLE_TO_RECHARGE;
		/**
		 * User user = this.channelservice
		 * .findSubNumberAndAuthState(ChannelContext.getSessionChannel()
		 * .getUsernumber()); if
		 * (!user.getAuthstate().equals(CertAuthState.APPROVED)) { return
		 * PUBLIC_AUTHMSG_NOTE; } // 当前账户是否存在父级账户 if
		 * (!StringUtils.isEmpty(user.getSupnumber())) { return
		 * UNABLE_TO_RECHARGE; } String usernumber =
		 * ChannelContext.getSessionChannel().getUsernumber(); Account
		 * useraccount = assetsservice.findUserAccount(usernumber); User cuser =
		 * this.channelservice.findUser(usernumber);
		 * model.addAttribute("useraccount", useraccount);
		 * model.addAttribute("user", cuser); return DEPOSITPREPROCESS;
		 ***/
	}
	
	// /**
	// * 账户充值。临时使用。后期应跳至银行付款界面
	// *
	// * @param mbay
	// * @return
	// */
	// @RequestMapping("deposit")
	// @Token(reset = true)
	// public String deposit(@RequestParam("mbay") BigDecimal mbay,
	// HttpServletRequest request, RedirectAttributes redAttr) {
	// if (mbay.doubleValue() < 0) {
	// redAttr.addAttribute(PUBLIC_MSG_NOTE_KEY, "充值额度应大于0！");
	// return REDIRECT_PUBLIC_MSG_NOTE;
	// }
	//
	// MbayDepositOrder order = new MbayDepositOrder();
	// order.setUsernumber(ChannelContext.getSessionChannel().getUserNumber());
	// order.setMbayQuantity(mbay);
	// order.setPrice(mbay.multiply(new BigDecimal(0.2)));
	// order.setState(DepositState.PAYMENTS_DUE);
	// order.setCreatetime(DateTime.now());
	// order = this.assetsservice.createMbayDepositOrder(order);
	// if (order == null) {
	// redAttr.addAttribute(PUBLIC_MSG_NOTE_KEY, "创建订单失败，请返回重试！");
	// return REDIRECT_PUBLIC_MSG_NOTE;
	// }
	// redAttr.addAttribute("depostnumber",
	// DigestUtils.pbeEncrypt(order.getDepositNumber()));
	// return RECIRECT_DEPOSIT_PAY;
	// }
	//
	// /**
	// * @param depostnumber
	// * @param redAttr
	// * @param model
	// * @return
	// */
	// @RequestMapping("depositPay")
	// public String depositPay(@RequestParam("depostnumber") String
	// depostnumber,
	// RedirectAttributes redAttr, Model model) {
	// depostnumber = DigestUtils.pbeDecrypt(depostnumber);
	// if (depostnumber == null) {
	// redAttr.addAttribute(PUBLIC_MSG_NOTE_KEY, "订单不存在，请返回重新创建！");
	// return PUBLIC_MSG_NOTE_KEY;
	// }
	// double accountAmount = this.assetsservice
	// .getAccountAmount(ChannelContext.getSessionChannel()
	// .getUserNumber());
	// MbayDepositOrder depositorder = this.assetsservice
	// .findMbayDepositOrder(depostnumber, ChannelContext
	// .getSessionChannel().getUserNumber());
	// model.addAttribute("depositorder", depositorder);
	// model.addAttribute("accountAmount", accountAmount);
	// List<AssetBank> bankList = bankservice.findList();
	// model.addAttribute("bankList", bankList);
	// return DEPOSIT_PAY;
	// }
	
	@RequestMapping("depositCashier")
	public String depositCashier(HttpServletRequest request) {
		String url = ChannelConfig.getMbayPayURL();
		String ordernumber = request.getParameter("orderNumber");
		String platform = request.getParameter("platform");
		int tradeType = 1; // 美贝充值
		String payMethod = request.getParameter("payMethod");
		String bankCode = request.getParameter("bankCode");
		url += "?orderNumber=" + ordernumber + "&tradeType=" + tradeType
				+ "&payMethod=" + payMethod + "&platform="
				+ platform + "&bankCode=" + bankCode;
				
		return "redirect:" + url;
	}
	
	@RequestMapping("remittanceRecord")
	@Token(save = true)
	public String remittanceRecord(HttpServletRequest request) {
		String usernumber = ChannelContext.getSessionChannel().getUserNumber();
		String price = request.getParameter("price");
		String time = request.getParameter("time");
		String description = request.getParameter("description");
		String bankId = request.getParameter("bankId");
		RemittanceRecord bean = new RemittanceRecord();
		bean.setUsernumber(usernumber);
		bean.setPrice(price);
		bean.setTime(time);
		bean.setDescription(description);
		// status:未处理
		bean.setStatus(RemittanceStatus.UNPROCESSED);
		if (bankId != null && !bankId.equals("")) {
			AssetBank bank = bankservice
					.findAssetBankById(Integer.valueOf(bankId));
			bean.setBank(bank);
		}
		recordservice.add(bean);
		return REDIRECT_REMITTANCERECORD_LIST;
	}
	
	/**
	 * 汇款记录
	 * 
	 * @param model
	 * @param pageinfo
	 * @param starttime
	 *            开始时间
	 * @param endtime
	 *            结束时间
	 * @param status
	 *            状态
	 * @return 列表页面
	 */
	@RequestMapping("remittanceRecord/list")
	@Token(reset = true)
	public String list(Model model, PageInfo pageinfo, String starttime,
			String endtime, RemittanceStatus status) {
		String usernumber = ChannelContext.getSessionChannel().getUserNumber();
		RemittanceRecord bean = new RemittanceRecord();
		bean.setUsernumber(usernumber);
		bean.setStarttime(DateTime.parse(starttime + " 00:00:00",
				DateFormatter.timeFormat));
		bean.setEndtime(MbayDateFormat.stringToTime(endtime + " 23:59:59"));
		if (status != null && status != RemittanceStatus.NON) {
			bean.setStatus(status);
		}
		List<RemittanceRecord> list = recordservice.findList(bean, pageinfo);
		model.addAttribute("list", list);
		model.addAttribute("starttime", starttime);
		model.addAttribute("endtime", endtime);
		model.addAttribute("status", status);
		model.addAttribute("pageinfo", pageinfo);
		return REMITTANCERECORD_LIST;
	}
	
	/**
	 * 查询转账账户状态
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("check_transfer_account")
	public String getUserStatus(@RequestParam String usernumber) {
		ExecuteResult result = authTransfer(usernumber);
		if (result.isSuccess()) {
			String certName = this.channelservice
					.findChannelCertRealName(usernumber);
			result.setMessage(certName);
		}
		JSONObject obj = JSONObject.fromObject(result);
		return obj.toString();
	}
	
	/** 验证消息提醒 */
	private ExecuteResult authTransfer(String usernumber) {
		User user = this.channelservice.findSubNumberAndAuthState(usernumber);
		if (user != null
				&& ChannelConfig.getMarketUsernumber().equals(
						ChannelContext.getSessionChannel().getUserNumber())
				&& user.getCertStatus().equals(CertStatus.APPROVED)) {
			return new ExecuteResult(true, user.getUsernumber() + "");
		} else {
			if (user == null) {
				return new ExecuteResult(false, "账户不存在!");
			}
			if (!user.getCertStatus().equals(CertStatus.APPROVED)) {
				return new ExecuteResult(false, "交易对方必须通过认证才可收款！");
			}
			if (ChannelContext.getSessionChannel().getUserNumber()
					.equals(usernumber)) {
				return new ExecuteResult(false, "付款方和收款方账户相同，不能转账!");
			}
			String sessionUserNumber = ChannelContext.getSessionChannel()
					.getUserNumber();
			String marketUserNumber = ChannelConfig.getMarketUsernumber();
			// 输入方的账号的上级等于当前用户的账号
			// 市场部账号可以向任意代理转账
			if (!sessionUserNumber.equals(user.getSupnumber())
					&& !sessionUserNumber.equals(marketUserNumber)) {
				return new ExecuteResult(false, "收款方账户必须绑定您的账号后才可转账！");
			}
			
			return new ExecuteResult(true, user.getUsernumber() + "");
		}
	}
	
	/**
	 * 转账交易
	 * 
	 * @return
	 */
	@RequestMapping("transfer/payment/fill")
	@CacheControl(policy = CachePolicy.NO_CACHE)
	@Token(save = true)
	public String transferfill(Model model) {
		if (!ChannelContext.getSessionChannel().getCertStatus()
				.equals(CertStatus.APPROVED)) {
			return PUBLIC_AUTHMSG_NOTE;
		}
		String usernumber = ChannelContext.getSessionChannel().getUserNumber();
		UserAccount useraccount = accountService.findUserAccount(usernumber);
		User user = this.channelservice.findUser(usernumber);
		model.addAttribute("useraccount", useraccount);
		model.addAttribute("user", user);
		return TRANSFER_PAYMENT_FILL;
	}
	
	/**
	 * 美贝流量账户转账
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("transfer/payment/fillmbay")
	@CacheControl(policy = CachePolicy.NO_CACHE)
	@Token(save = true)
	public String mbaytraffictransferfill(Model model) {
		if (!ChannelContext.getSessionChannel().getCertStatus()
				.equals(CertStatus.APPROVED)) {
			return PUBLIC_AUTHMSG_NOTE;
		}
		String usernumber = ChannelContext.getSessionChannel().getUserNumber();
		UserAccount useraccount = accountService.findUserAccount(usernumber);
		User user = this.channelservice.findUser(usernumber);
		model.addAttribute("useraccount", useraccount);
		model.addAttribute("user", user);
		return MBAY_TRAFFIC_FILL;
	}
	
	/**
	 * 红包账户转账
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("transfer/payment/fillred")
	@CacheControl(policy = CachePolicy.NO_CACHE)
	@Token(save = true)
	public String redtraffictransferfill(Model model) {
		if (!ChannelContext.getSessionChannel().getCertStatus()
				.equals(CertStatus.APPROVED)) {
			return PUBLIC_AUTHMSG_NOTE;
		}
		String usernumber = ChannelContext.getSessionChannel().getUserNumber();
		UserAccount useraccount = accountService.findUserAccount(usernumber);
		User user = this.channelservice.findUser(usernumber);
		model.addAttribute("useraccount", useraccount);
		model.addAttribute("user", user);
		return RED_TRAFFIC_FILL;
	}
	
	/**
	 * 提交转账信息，跳转至转账确认
	 *
	 * @param mbayaccount
	 *            转入账户usernumber
	 * @param transferamount
	 *            转入数量
	 * @param sendamount
	 *            赠送数量
	 * @param transfernote
	 *            转账说明
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("transfer/payment/submit")
	@Token(reset = true)
	public String transfer(@Validated MbayTransferInfo transferInfo,
			BindingResult br, Model model,
			HttpServletRequest request) {
		// 加密狗验证
		if (ChannelConfig.getMarketUsernumber()
				.equals(ChannelContext.getSessionChannel().getUserNumber())) {
			Smart2000Data smData = Smart2000Util
					.getData(Smart2000Data.UID | Smart2000Data.MODULE);
			if (smData == null) {
				throw new ServiceException(
						UserAccountTradeError.SMART2000_VERIFY_ERROR);
			}
		}
		
		if (br.hasErrors()) {
			LOGGER.error("转账：商户{}提交参数验证不通过",
					ChannelContext.getSessionChannel().getUserNumber());
					
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, Error.FORM_VALIDATE_ERROR);
			TokenProcessor.getInstance().saveToken(request);
			return transferfill(model);// 返回至申请页面
		}
		transferInfo.setFromUserNumber(
				ChannelContext.getSessionChannel().getUserNumber());
				
		ExecuteResult result = authTransfer(transferInfo.getToUserNumber());
		if (!result.isSuccess()) {// 收款方账户异常，无法转账
			LOGGER.error("转账：商户{}转账审核不通过：{}",
					ChannelContext.getSessionChannel().getUserNumber(),
					result.getMessage());
			TokenProcessor.getInstance().saveToken(request);
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, result.getMessage());
			return TRANSFER_PAYMENT_FILL;
		}
		ExecuteResult transferResult = this.accountService
				.transfer(transferInfo);
		if (!transferResult.isSuccess()) {
			LOGGER.error("转账：商户{}转失败：{}",
					ChannelContext.getSessionChannel().getUserNumber(),
					result.getMessage());
			TokenProcessor.getInstance().saveToken(request);
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, result.getMessage());
			return TRANSFER_PAYMENT_FILL;
		}
		return TRANSFER_CASHIER_SUCCESS;
	}
	
	// /**
	// * 确认转账信息
	// *
	// * @param request
	// * @param model
	// * @return
	// */
	// @RequestMapping("transfer/payment/confirm")
	// public String transferconfirm(HttpServletRequest request, Model model) {
	// long orderid = 0;
	// try {
	// orderid = Long.valueOf(request.getParameter("orderid").toString());
	// } catch (Exception e) {
	// model.addAttribute(PUBLIC_MSG_NOTE_KEY, "抱歉系统出错！");
	// return TRANSFER_ERROR;
	// }
	// // 更加订单编号查询出转账订单数据
	// TransferOrder record = this.assetsservice.findTransferOrder(orderid,
	// ChannelContext.getSessionChannel().getUserNumber());
	// model.addAttribute("transfer_account",
	// User.getRealNameWithEncrypt(record.getOptuser().getName())
	// + "(" + record.getOptuser().getUsernumber() + ")");
	// model.addAttribute("transferrecord", record);
	// return TRANSFER_CONFIRM;
	// }
	
	// /**
	// * 执行转账操作
	// *
	// * @param request
	// * @param model
	// * @return
	// */
	// @RequestMapping("transfer/payment/cashier")
	// public String transfercashier(@RequestParam long orderid, Model model) {
	// assetsservice.accountTransfer(orderid);
	// return TRANSFER_CASHIER_SUCCESS;
	// }
	
	@RequestMapping("getUserAmount")
	@ResponseBody
	public JSONObject userAmount() {
		double useramount = this.accountService.getAvailableAmount(
				ChannelContext.getSessionChannel().getUserNumber());
		Map<String, String> map = new HashMap<String, String>();
		DecimalFormat df = new DecimalFormat("####0.00");
		map.put("amount", df.format(useramount));
		return JSONObject.fromObject(map);
	}
	
	/**
	 * To美贝流量账户转账
	 * 
	 * @return
	 */
	@RequestMapping("mbaytraffic/transfer/index")
	@Token(save = true)
	public ModelAndView toTransfer() {
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		MbayTraffic mbayTraffic = this.mbAccountService
				.findMbayTraffic(userNumber);
		ModelAndView view = new ModelAndView(TO_TRANSFER);
		view.addObject("mbayTraffic", mbayTraffic);
		return view;
	}
	
	/**
	 * @return
	 */
	@RequestMapping("mbaytraffic/transfer/dotransfer")
	@Token(reset = true)
	public ModelAndView mbayTrafficTransfer(
			@Validated TrafficTransferInfo transferInfo, BindingResult br,
			Model model,
			HttpServletRequest request) {
		// 加密狗验证
		if (ChannelConfig.getMarketUsernumber()
				.equals(ChannelContext.getSessionChannel().getUserNumber())) {
			Smart2000Data smData = Smart2000Util
					.getData(Smart2000Data.UID | Smart2000Data.MODULE);
			if (smData == null) {
				throw new ServiceException(
						UserAccountTradeError.SMART2000_VERIFY_ERROR);
			}
		}
		
		if (br.hasErrors() || transferInfo.getTraffic() <= 0) {
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, Error.FORM_VALIDATE_ERROR);
			TokenProcessor.getInstance().saveToken(request);
			return toTransfer();// 返回至申请页面
		}
		ExecuteResult result = authTransfer(transferInfo.getToUserNumber());
		if (!result.isSuccess()) {// 收款方账户异常，无法转账
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, result.getMessage());
			TokenProcessor.getInstance().saveToken(request);
			return toTransfer();// 返回至申请页面
		}
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		transferInfo.setFromUserNumber(userNumber);
		MbayTraffic mbaytraffic = this.mbAccountService
				.findMbayTraffic(userNumber);
		int balance = mbaytraffic.getTraffic();
		if (transferInfo.getTraffic() > balance) {
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, "您的账户余额不足于本次转账！");
			TokenProcessor.getInstance().saveToken(request);
			return toTransfer();// 返回至申请页面
		}
		ExecuteResult executeResult = this.mbAccountService
				.transfer(transferInfo);
		if (!executeResult.isSuccess()) {
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, executeResult.getMessage());
			TokenProcessor.getInstance().saveToken(request);
			return toTransfer();// 返回至申请页面
		}
		ModelAndView view = new ModelAndView(MBAY_TRAFFIC_TRANSFER_SUCCESS);
		return view;
	}
	
	/**
	 * 美贝流量账户转账记录
	 * 
	 * @param qo
	 * @param pageInfo
	 * @return
	 */
	@RequestMapping("mbaytraffic/transfer/record")
	public ModelAndView transferRecord(TrafficTransferQO qo, PageInfo pageInfo,
			HttpServletRequest request) {
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		qo.setUserNumber(userNumber);
		String startTime = request.getParameter("starttime");
		if (!StringUtils.isEmpty(startTime)) {
			qo.setStartTime(
					MbayDateFormat.stringToTime(startTime + " 00:00:00"));
		}
		String endTime = request.getParameter("endtime");
		if (!StringUtils.isEmpty(endTime)) {
			qo.setEndTime(MbayDateFormat.stringToTime(endTime + " 23:59:59"));
		}
		List<MbayTrafficTransferOrder> transgerOrders = this.mbAccountService
				.findAllTrafficTransferOrder(qo, pageInfo);
		ModelAndView view = new ModelAndView(MBAY_TRAFFIC_TRANSFER_RECORD);
		view.addObject("transgerOrders", transgerOrders);
		return view;
	}
	
	@RequestMapping("mbaytraffic/tradedetail")
	public ModelAndView tradedetail(TrafficDetailQO qo, PageInfo pageInfo,
			HttpServletRequest request) {
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		String startTime = request.getParameter("starttime");
		if (!StringUtils.isEmpty(startTime)) {
			qo.setStartTime(
					MbayDateFormat.stringToTime(startTime + " 00:00:00"));
		}
		String endTime = request.getParameter("endtime");
		if (!StringUtils.isEmpty(endTime)) {
			qo.setEndTime(MbayDateFormat.stringToTime(endTime + " 23:59:59"));
		}
		qo.setUserNumber(userNumber);
		List<UserMbayTrafficTrade> trafficDetail = this.mbAccountService
				.findAllMbayTrafficDetail(qo, pageInfo);
		ModelAndView view = new ModelAndView(MBAY_TRAFFIC_TRADE_DETAIL);
		view.addObject("trafficDetail", trafficDetail);
		view.addObject(Globals.PAGEINFO_KEY, pageInfo);
		view.addObject("detailQO", qo);
		return view;
	}
	
	@ResponseBody
	@RequestMapping("mbaytraffic/balance")
	public JSONObject mbaytrafficBalance() {
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		Map<String, Double> map = new HashMap<String, Double>();
		map.put("balance", this.mbAccountService.getBalance(userNumber));
		return JSONObject.fromObject(map);
	}
	
	@ResponseBody
	@RequestMapping("redtraffic/balance")
	public JSONObject redTrafficBalance() {
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		RedTraffic redTraffic = this.redTrafficAccountService
				.findRedTraffic(userNumber);
		Map<String, Double> map = new HashMap<String, Double>();
		map.put("balance", redTraffic.getTraffic());
		return JSONObject.fromObject(map);
	}
	
	/**
	 * To美贝流量账户转账
	 * 
	 * @return
	 */
	@RequestMapping("redtraffic/transfer/index")
	@Token(save = true)
	public ModelAndView toRedTrafficTransfer() {
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		RedTraffic mbayTraffic = this.redTrafficAccountService
				.findRedTraffic(userNumber);
		ModelAndView view = new ModelAndView(REDTRAFFIC_TO_TRANSFER);
		view.addObject("redTraffic", mbayTraffic);
		return view;
	}
	
	/**
	 * @return
	 */
	@RequestMapping("redtraffic/transfer/dotransfer")
	@Token(reset = true)
	public ModelAndView redTrafficTransfer(
			@Validated TrafficTransferInfo transferInfo, BindingResult br,
			Model model,
			HttpServletRequest request) {
		// 加密狗验证
		if (ChannelConfig.getMarketUsernumber()
				.equals(ChannelContext.getSessionChannel().getUserNumber())) {
			Smart2000Data smData = Smart2000Util
					.getData(Smart2000Data.UID | Smart2000Data.MODULE);
			if (smData == null) {
				throw new ServiceException(
						UserAccountTradeError.SMART2000_VERIFY_ERROR);
			}
		}
		
		if (br.hasErrors() || transferInfo.getTraffic() <= 0) {
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, Error.FORM_VALIDATE_ERROR);
			TokenProcessor.getInstance().saveToken(request);
			return toRedTrafficTransfer();// 返回至申请页面
		}
		ExecuteResult result = authTransfer(transferInfo.getToUserNumber());
		if (!result.isSuccess()) {// 收款方账户异常，无法转账
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, result.getMessage());
			TokenProcessor.getInstance().saveToken(request);
			return toRedTrafficTransfer();// 返回至申请页面
		}
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		transferInfo.setFromUserNumber(userNumber);
		RedTraffic mbaytraffic = this.redTrafficAccountService
				.findRedTraffic(userNumber);
		double balance = mbaytraffic.getTraffic();
		if (transferInfo.getTraffic() > balance) {
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, "您的账户余额不足于本次转账！");
			TokenProcessor.getInstance().saveToken(request);
			return toRedTrafficTransfer();// 返回至申请页面
		}
		ExecuteResult executeResult = this.redTrafficAccountService
				.transfer(transferInfo);
		if (!executeResult.isSuccess()) {
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, executeResult.getMessage());
			TokenProcessor.getInstance().saveToken(request);
			return toRedTrafficTransfer();// 返回至申请页面
		}
		ModelAndView view = new ModelAndView(RED_TRAFFIC_TRANSFER_SUCCESS);
		return view;
	}
	
	/**
	 * 美贝流量账户转账记录
	 * 
	 * @param qo
	 * @param pageInfo
	 * @return
	 */
	@RequestMapping("redtraffic/transfer/record")
	public ModelAndView redTrafficTransferRecord(TrafficTransferQO qo,
			PageInfo pageInfo, HttpServletRequest request) {
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		qo.setUserNumber(userNumber);
		String startTime = request.getParameter("starttime");
		if (!StringUtils.isEmpty(startTime)) {
			qo.setStartTime(
					MbayDateFormat.stringToTime(startTime + " 00:00:00"));
		}
		String endTime = request.getParameter("endtime");
		if (!StringUtils.isEmpty(endTime)) {
			qo.setEndTime(MbayDateFormat.stringToTime(endTime + " 23:59:59"));
		}
		List<RedTrafficTransferOrder> transgerOrders = this.redTrafficAccountService
				.findAllTrafficTransferOrder(qo,
						pageInfo);
		ModelAndView view = new ModelAndView(RED_TRAFFIC_TRANSFER_RECORD);
		view.addObject("transgerOrders", transgerOrders);
		return view;
	}
	
	@RequestMapping("redtraffic/tradedetail")
	public ModelAndView redTrafficTradedetail(TrafficDetailQO qo,
			PageInfo pageInfo, HttpServletRequest request) {
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		String startTime = request.getParameter("starttime");
		if (!StringUtils.isEmpty(startTime)) {
			qo.setStartTime(
					MbayDateFormat.stringToTime(startTime + " 00:00:00"));
		}
		String endTime = request.getParameter("endtime");
		if (!StringUtils.isEmpty(endTime)) {
			qo.setEndTime(MbayDateFormat.stringToTime(endTime + " 23:59:59"));
		}
		qo.setUserNumber(userNumber);
		List<UserRedTrafficTrade> trafficDetail = this.redTrafficAccountService
				.findAllRedTrafficDetail(qo, pageInfo);
		ModelAndView view = new ModelAndView(RED_TRAFFIC_TRADE_DETAIL);
		view.addObject("trafficDetail", trafficDetail);
		view.addObject(Globals.PAGEINFO_KEY, pageInfo);
		view.addObject("detailQO", qo);
		return view;
	}
	
	/**
	 * 美贝账户向红包账户转入
	 * 
	 * @return
	 */
	@Token(save = true)
	@RequestMapping("redtraffic/mbay_transfer_in_red/ui")
	public ModelAndView mbayTransferInRedUI() {
		ModelAndView mav = new ModelAndView(MBAY_TRANSFER_IN_RED_UI);
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		// 红包账户余额
		double redBalance = this.redTrafficAccountService
				.getBalance(userNumber);
		// 美贝账户余额
		double mbayBalance = this.accountService.getAvailableAmount(userNumber);
		
		mav.addObject("redBalance", redBalance);
		mav.addObject("mbayBalance", mbayBalance);
		return mav;
	}
	
	@Token(reset = true)
	@RequestMapping("redtraffic/mbay_transfer_in_red/transfer")
	public String mbayTransferInRed(@RequestParam double transferAmount,
			Model model, HttpServletRequest request) {
		if (transferAmount <= 0) {
			TokenProcessor.getInstance().saveToken(request);
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, "转入美贝须大于零！");
			return MBAY_TRANSFER_IN_RED_UI;
		}
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		ExecuteResult resulet = this.redTrafficAccountService
				.transferInFromMbayAccount(transferAmount, userNumber);
		if (!resulet.isSuccess()) {
			TokenProcessor.getInstance().saveToken(request);
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, resulet.getMessage());
			return MBAY_TRANSFER_IN_RED_UI;
		}
		return MBAY_TRANSFER_IN_RED_SUCCESS;
	}
	
}
