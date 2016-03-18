package org.sz.mbay.channel.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.sz.mbay.base.annotation.CacheControl;
import org.sz.mbay.base.annotation.CachePolicy;
import org.sz.mbay.base.annotation.Token;
import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.base.web.servlet.tags.token.TokenProcessor;
import org.sz.mbay.channel.context.ChannelContext;
import org.sz.mbay.channel.costant.error.Error;
import org.sz.mbay.channel.framework.action.BaseAction;
import org.sz.mbay.trafficSign.bean.TrafficSign;
import org.sz.mbay.trafficSign.enums.TrafficSignStatus;
import org.sz.mbay.trafficSign.service.TrafficSignService;

/**
 * @Description: 流量充值实时订购接口管理
 * @author frank.zong
 * @date 2014-9-28 下午11:07:40
 * 		
 */
@Controller
@RequestMapping("trafficSign")
public class TrafficAPISignAction extends BaseAction {
	
	@Autowired
	TrafficSignService service;
	
	// 签约管理界面
	public static final String SIGN_MANAGE = "traffic_sign/sign_manager";
	// 签约产品
	public static final String TO_PRODUCTSIGN = "traffic_sign/product_sign";
	
	public static final String REDIRECT_SIGN_SUCCESS = "redirect:/trafficSign/sign_success.mbay";
	
	public static final String SIGN_SUCCESS = "traffic_sign/sign_success";
	
	/**
	 * 签约接口管理
	 * 
	 * @return
	 */
	@RequestMapping("signManage")
	public String signManage(Model model) {
		String pid = "";
		String key = "";
		String status = "1";
		String reason = "";
		String usernumber = ChannelContext.getSessionChannel().getUserNumber();
		TrafficSign bean = service.findTrafficSignByUserNumber(usernumber);
		// 如果存在记录则为1，否则为0
		if (bean == null) {
			model.addAttribute("isExist", 0);
		} else {
			// 状态 0->未审核,1->审核通过,2->审核不通过
			TrafficSignStatus status_value = bean.getStatus();
			if (status_value.equals(TrafficSignStatus.NOT_AUDIT)) {
				status = "0";
			} else if (status_value.equals(TrafficSignStatus.AUDIT_SUCCESS)) {
				pid = bean.getUsernumber();
				key = bean.getPid();
			} else if (status_value.equals(TrafficSignStatus.AUDIT_FAIL)) {
				status = "2";
				reason = bean.getReason();
			}
			model.addAttribute("isExist", 1);
		}
		model.addAttribute("pid", pid);
		model.addAttribute("key", key);
		model.addAttribute("status", status);
		model.addAttribute("reason", reason);
		return SIGN_MANAGE;
	}
	
	/**
	 * 跳转到签约页面
	 * 
	 * @return
	 */
	@RequestMapping("productSign")
	@CacheControl(policy = CachePolicy.NO_CACHE)
	@Token(save = true)
	public String productSign(HttpServletRequest request) {
		return TO_PRODUCTSIGN;
	}
	
	/**
	 * 提交签约信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "sub_sign", method = RequestMethod.POST)
	@Token(reset = true)
	public String subProductSign(Model model, @Valid TrafficSign bean,
			BindingResult br, HttpServletRequest request) {
		if (br.hasErrors()) {
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, Error.FORM_VALIDATE_ERROR);
			TokenProcessor.getInstance().saveToken(request);
			return productSign(request);
		}
		
		// 用户编号
		String usernumber = ChannelContext.getSessionChannel().getUserNumber();
		
		// 查询是否已有记录,如果有则删除
		TrafficSign sign = service.findTrafficSignByUserNumber(usernumber);
		if (sign != null) {
			service.del(sign.getId());
		}
		
		bean.setUsernumber(usernumber);
		// 网址
		bean.setUrl("http://" + bean.getUrl());
		// 签约时间
		bean.setTime((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
				.format(new Date()));
		// 状态 0:等待审核
		bean.setStatus(TrafficSignStatus.NOT_AUDIT);
		// 是否启用->默认启用
		bean.setEnable(EnableState.ENABLED);
		service.add(bean);
		return REDIRECT_SIGN_SUCCESS;
	}
	
	/**
	 * @return
	 */
	@RequestMapping("sign_success")
	public String signSuccess() {
		return SIGN_SUCCESS;
	}
}
