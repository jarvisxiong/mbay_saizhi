package org.sz.mbay.channel.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.sz.mbay.apptemptation.constant.error.AppTemptationError;
import org.sz.mbay.base.annotation.CacheControl;
import org.sz.mbay.base.annotation.CachePolicy;
import org.sz.mbay.base.annotation.Token;
import org.sz.mbay.base.constant.Globals;
import org.sz.mbay.base.exception.ServiceException;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.channel.annotation.CertAuth;
import org.sz.mbay.channel.context.ChannelContext;
import org.sz.mbay.channel.costant.error.TrafficBuyNowError;
import org.sz.mbay.channel.enums.TrafficChargeOrderRefundStatus;
import org.sz.mbay.channel.framework.action.BaseAction;
import org.sz.mbay.channel.user.enums.TradeType;
import org.sz.mbay.channel.user.error.UserAccountTradeError;
import org.sz.mbay.channel.user.exception.UserAccountTradeException;
import org.sz.mbay.channel.user.service.UserAccountService;
import org.sz.mbay.trafficorder.bean.TrafficOrder;
import org.sz.mbay.trafficorder.bean.TrafficOrderRefund;
import org.sz.mbay.trafficorder.bean.TrafficRechargeInfo;
import org.sz.mbay.trafficorder.enums.OperatorRechargeStatus;
import org.sz.mbay.trafficorder.enums.TrafficOrderRefundStatus;
import org.sz.mbay.trafficorder.enums.TrafficOrderType;
import org.sz.mbay.trafficorder.qo.TrafficOrderQO;
import org.sz.mbay.trafficorder.service.TrafficOrderRefundService;
import org.sz.mbay.trafficorder.service.TrafficOrderService;
import org.sz.mbay.trafficrecharge.service.TrafficRechargeService;

import net.sf.json.JSONObject;

/**
 * @Description: 手机流量直冲
 * @author han.han
 * @date
 * 
 */
@Controller
@RequestMapping("traffic")
public class TrafficBuyNowAction extends BaseAction {
	
	public static final String TRAFFIC_ORDER_LIST_KEY="trafficcharges";
	// 流量直充
	public static final String TRAFFICPURCHASE = "traffic/redeemtraffic";
	// 流量直充确认
	public static final String TRAFFIC_CHARGE_CONFIRM = "traffic/charge_confirm";
	// 重定向至流量直充确认
	public static final String REDIRECT_CHARGE_CONFIRM = "redirect:/traffic/chargeconfirm.mbay";
	// 重定向到流量充值支付成功
	public static final String REDIRECT_CHARGE_PAY_SUCCESS = "redirect:/traffic/charge_pay_success.mbay";
	// 流量充值订单支付成功
	public static final String CHARGEORDER_PAY_SUCCESS = "traffic/chargeorder_pay_success";
	// 营销明细
	public static final String TRAFFIC_CHRGE_RECORD = "traffic/traffic_order_items";
	// 批充查看-营销明细
	public static final String CUSTOMER_TRAFFIC_CHRGE_RECORD = "traffic/customer_traffic_order_items";

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private TrafficOrderService trafficOrderService;

	@Autowired
	private TrafficRechargeService trafficRechargeService;

	@Autowired
	private TrafficOrderRefundService trafficOrderRefundService;

	/**
	 * 前往美贝流量兑换
	 * 
	 * @param view
	 * @return
	 */
	@RequestMapping("purchase")
	@Token(save = true)
	@CacheControl(policy = { CachePolicy.NO_CACHE })
	@CertAuth
	public String trafficpurchase(Model model, HttpServletRequest request) {
		double availableAmount = userAccountService
				.getAvailableAmount(ChannelContext.getSessionChannel().getUserNumber());
		model.addAttribute("availableAmount", availableAmount);
		return TRAFFICPURCHASE;
	}

	/**
	 * 手机流量直充订单创建
	 * 
	 * @param mobile
	 * @param packageid
	 * @param redAttr
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("charge/order_create")
	@Token(reset = true)
	public String ordercrate(@RequestParam("mobile") String mobile, @RequestParam("packageid") int packageid,
			Model model, RedirectAttributes redAttr, HttpServletRequest request) {
		// 创建订单
		TrafficRechargeInfo info = new TrafficRechargeInfo();
		info.setMobile(mobile);
		info.setRechargeType(TrafficOrderType.DIRECT_RECHARGE);
		info.setRelationNumber("");
		info.setTrafficPackageNumber(packageid);
		info.setUserNumber(ChannelContext.getSessionChannel().getUserNumber());
		String orderNumber = this.trafficOrderService.create(info);
		redAttr.addAttribute("chargeordernumber", orderNumber);
		return REDIRECT_CHARGE_CONFIRM;
	}

	/**
	 * 流量充值订单确认
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("chargeconfirm")
	@CacheControl(policy = { CachePolicy.NO_CACHE })
	@Token(save = true)
	public String chargeConfrim(@ModelAttribute("chargeordernumber") String ordernumber, HttpServletRequest request,
			Model model) {
		TrafficOrder order = trafficOrderService.findTrafficOrder(ordernumber);
		if (order == null) {
			throw new ServiceException(TrafficBuyNowError.NOT_FOUND_TRAFFIC_ORDER);
		}
		double amount = userAccountService.getAvailableAmount(ChannelContext.getSessionChannel().getUserNumber());
		if (order.getMbayPrice() > amount) {
			model.addAttribute("chargeenable", false);
		} else {
			model.addAttribute("chargeenable", true);
		}
		model.addAttribute("order", order);
		return TRAFFIC_CHARGE_CONFIRM;
	}

	/**
	 * 确认订单，执行流量充值
	 * 
	 * @return
	 */
	@RequestMapping("trafficCharge")
	@Token(reset = true)
	public String trafficCharge(HttpServletRequest request, RedirectAttributes redAttr) {
		String ordernumber = request.getParameter("chargeordernumber");
		TrafficOrder order = this.trafficOrderService.findTrafficOrder(ordernumber);
		if (order == null) {
			throw new ServiceException(TrafficBuyNowError.NOT_FOUND_TRAFFIC_ORDER);
		}
		try {
			this.userAccountService.expenditure(order.getUserNumber(), TradeType.TRAFFIC_RECHARGE, ordernumber,
					order.getMbayPrice(), "流量直充");
		} catch (UserAccountTradeException e) {
			throw new ServiceException(UserAccountTradeError.USER_ACCOUNT_TRADE_ERROR);
		}
		trafficRechargeService.recharge(ordernumber);
		return REDIRECT_CHARGE_PAY_SUCCESS;
	}

	/**
	 * 至流量充值订单支付成功页面
	 * 
	 * @return
	 */
	@RequestMapping("charge_pay_success")
	public String chargePaySuccess() {
		return CHARGEORDER_PAY_SUCCESS;
	}

	/**
	 * 营销明细
	 * 
	 * @param model
	 * @param pageinfo
	 * @param request
	 * @return
	 */
	@RequestMapping("record")
	public String chargeRecord(Model model, TrafficOrderQO orderform, PageInfo pageinfo, HttpServletRequest request) {
		orderform.setUserNumber(ChannelContext.getSessionChannel().getUserNumber());
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		String eventnumber = request.getParameter("eventnumber");
		if (starttime != null && starttime.length() > 0) {
			orderform.setStartTime(starttime);
		}
		if (endtime != null && endtime.length() > 0) {
			orderform.setEndTime(endtime);
		}
		if (eventnumber != null && !eventnumber.equals("")) {
			orderform.setRelateNumber(eventnumber);
		}
		orderform.setMobile(request.getParameter("mobile"));
		orderform.setOrderNumber(request.getParameter("number"));
		List<TrafficOrder> trafficcharges = trafficOrderService.findAllTrafficOrder(orderform, pageinfo);
		model.addAttribute(TRAFFIC_ORDER_LIST_KEY, trafficcharges);
		model.addAttribute("orderform", orderform);
		model.addAttribute(Globals.PAGEINFO_KEY, pageinfo);
		return TRAFFIC_CHRGE_RECORD;
	}

	@RequestMapping("record/download")
	public String downlaodRecord(Model model, TrafficOrderQO orderform, PageInfo pageinfo, HttpServletRequest request) {
		this.chargeRecord(model, orderform, null, request);
		return "trafficRecordDownload";

	}

	/***
	 * 营销明细 客户关怀
	 * 
	 * @param model
	 * @param pageinfo
	 * @param request
	 * @return
	 */
	@RequestMapping("recordByBatchChargeItem")
	public String recordByBatchChargeItem(@RequestParam("itemid") int itemid, Model model, PageInfo pageinfo,
			HttpServletRequest request) {
		// 流量充值订单操作记录
		TrafficOrderQO orderform = new TrafficOrderQO();
		orderform.setUserNumber(ChannelContext.getSessionChannel().getUserNumber());
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		String eventnumber = request.getParameter("eventnumber");
		if (starttime != null && starttime.length() > 0) {
			orderform.setStartTime(endtime);
		}
		if (endtime != null && endtime.length() > 0) {
			orderform.setEndTime(endtime);
		}
		if (eventnumber != null && !eventnumber.equals("")) {
			orderform.setRelateNumber(eventnumber);
		}
		orderform.setRelateNumber(String.valueOf(itemid));
		orderform.setMobile(request.getParameter("mobile"));
		orderform.setOrderNumber(request.getParameter("number"));
		List<TrafficOrder> trafficcharges = trafficOrderService.findAllTrafficOrder(orderform, pageinfo);
		model.addAttribute("trafficcharges", trafficcharges);
		model.addAttribute("orderform", orderform);
		model.addAttribute(Globals.PAGEINFO_KEY, pageinfo);
		model.addAttribute("itemid", itemid);
		return CUSTOMER_TRAFFIC_CHRGE_RECORD;
	}

	/**
	 * 退款申请
	 * 
	 * @param orderNumber
	 * @return
	 */
	@ResponseBody
	@RequestMapping("refund")
	public JSONObject refund(@RequestParam("orderNumber") String orderNumber) {
		TrafficOrder order = trafficOrderService.findTrafficOrder(orderNumber);
		if (order == null) {
			return JSONObject.fromObject(AppTemptationError.ORDER_NUMBER_ERROR);
		}
		if (order.getOrs() != OperatorRechargeStatus.OPER_RECHARGE_FAIL) {
			return JSONObject.fromObject(AppTemptationError.ILLEGAL_REFUND_ERROR);
		}

		TrafficOrderRefund refund = new TrafficOrderRefund();
		refund.setOrderNumber(orderNumber);
		refund.setMbayPrice(order.getMbayPrice());
		refund.setCreateTime(DateTime.now());
		refund.setStatus(TrafficOrderRefundStatus.PROCESSING);
		trafficOrderRefundService.create(refund);

		JSONObject obj = new JSONObject();
		obj.put("status", true);
		obj.put("refund", TrafficChargeOrderRefundStatus.PROCESSING.getValue());
		return JSONObject.fromObject(obj);
	}

}
