package org.sz.mbay.channel.api.traffic.service;

import java.io.ByteArrayInputStream;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.sz.mbay.base.area.Area;
import org.sz.mbay.base.service.UtilService;
import org.sz.mbay.base.wrap.MsgType;
import org.sz.mbay.base.wrap.RedeemResponse;
import org.sz.mbay.channel.bean.Strategy;
import org.sz.mbay.channel.context.ChannelConfig;
import org.sz.mbay.channel.user.service.UserAccountService;
import org.sz.mbay.common.util.HttpSupport;
import org.sz.mbay.hcode.MbayHcode;
import org.sz.mbay.hcode.bean.HcodeInfo;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.promotion.bean.RedeemCode;
import org.sz.mbay.promotion.dao.RedeemCodeDao;

/**
 * @author ONECITY 接口不应该关心 锁定美贝美贝金额信息，所以，扣除美贝余额和扣除锁定美贝余额在mbaychannel中实现
 * 
 */
@Component
public class CodeRedeemTrafficService {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CodeRedeemTrafficService.class);
	@Autowired
	RedeemCodeDao redeemCodeDao;
	@Autowired
	UserAccountService userAccountService;
	@Autowired
	UtilService utilservice;
	
	@Transactional
	public RedeemResponse redeem(String redeemcode, String mobile) {
		RedeemCode code = null;
		try {
			//code = redeemCodeDao.findRedeemCodeByCode(redeemcode);
		} catch (Exception e) {
			LOGGER.error("findRedeemCodeByCode根据兑换码查询兑换码信息异常：{}",
					e.fillInStackTrace());
			return RedeemResponse.SYSTEMERROR;
		}
		// 无效的兑换码
		if (code == null) {
			return RedeemResponse.INVALIDREDEEMCODE;
		}
		// /if (code.getCodestatus().key == RedeemCodeStu.USED.key) {
		// /return RedeemResponse.CODE_REDEEMED;
		// /}
		// 判断兑换码是否已过期
		if (DateTime.now().isAfter(code.getExpiretime())) {
			return RedeemResponse.EXPIREDREDEEMCODE;
		}
		Strategy strategy = null;// / code.getStrategy();
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("策略信息[地区:{},运营商：{}]", strategy.getArea().value,
					strategy.getTeloperator().getValue());
		}
		HcodeInfo codeinfo = MbayHcode.getHcodeInfo(mobile);
		if (codeinfo == null) {
			return RedeemResponse.NOTFOUND_MOBILE;
		}
		if (strategy.getArea().value != Area.QUANGUO.value) {
			// 不支持的手机号
			if (strategy.getArea().value != codeinfo.getProvcode()) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.info("策略范围非全国，不支持此手机号");
				}
				return RedeemResponse.NONSUPPORT_MOBILE;
			}
		}
		if (OperatorType.THREENETS != strategy.getTeloperator()) {
			if (strategy.getTeloperator().ordinal() != codeinfo.getOperator()) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.info("策略非三网，不支持此手机号");
				}
				return RedeemResponse.NONSUPPORT_MOBILE;
			}
		}
		return trafficCharge(mobile, strategy, redeemcode);
	}
	
	@Transactional
	private RedeemResponse trafficCharge(String mobile, Strategy strategy,
			String code) {
		//TODO:1
		/*double amount = this.userAccountService.getAccountBalance("");
		if (strategy.getUnitmb() > amount) {
			return RedeemResponse.INSUFFICIENT_ACCOUNT;
		}*/
		String url = ChannelConfig.getTrafficMbayRedeemURL() + "?" + "mobile="
				+ mobile + "&area=" + strategy.getArea().value + "&operator="
				+ strategy.getTeloperator().ordinal() + "&flowtype="
				+ strategy.getTraffictype().getValue() + "&flow="
				+ strategy.getSendnum() + "&userid=" + strategy.getUserid();
		String response = "";
		try {
			response = HttpSupport.build(url).connect();
		} catch (Exception e) {
			LOGGER.error("RedeemResponse httpRequest", e.fillInStackTrace());
			return new RedeemResponse("SYSTEM_ERROR", "无法连接到服务器，请稍后在试",
					MsgType.TEXT);
		}
		try {
		/*	DateTime now = DateTime.now();
			String snumber = MbayDateFormat.formatDateTime(now,
					DatePattern.yyyyMMdd);
			// int nexno = this.utilservice.getNextIndex(AccountDetail.class);
			// snumber = snumber + Transactiontype.TRAFFICCHARGENO
			// / + (10000000 + nexno);
			AccountDetail detail = new AccountDetail();
			detail.setDealunumber("");
			detail.setAmount(0 - strategy.getUnitmb());
			detail.setInfo("兑换码流量兑换-" + mobile);
			//TODO:1
			//detail.setBalance(amount - strategy.getUnitmb());
			detail.setNumber(snumber);
			detail.setType(PaymentType.EXPENSE);
			// / detail.setTradetype(Transactiontype.TRAFFIC_CHARGE_TRADE);
			detail.setTime(now);
			// TODO 这里应该记录兑换信息以便使渠道商可查询的到。
			detail.setOrdernumber("");
			//TODO:1
			//this.userAccountService.(detail);
			userAccountService.reduceLockedAmount("", strategy.getUnitmb());
			//TODO:1
			//userAccountService.reduceUserAmount("", strategy.getUnitmb());
			this.redeemCodeDao.updateCodeToRedeemed(code);*/
			//this.userAccountService.userAmountExpenditure(usernumber, type, ordernumber, mbayprice, note);
			
		} catch (Exception e1) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			LOGGER.error("");
		}
		SAXReader saxReader = new SAXReader();
		Document document;
		try {
			System.out.println(response);
			document = saxReader.read(new ByteArrayInputStream(response
					.getBytes("utf-8")));
			Element rootElement = document.getRootElement();
			String status = rootElement.elementTextTrim("status");
			String msgType = rootElement.elementTextTrim("msgType");
			String content = rootElement.elementTextTrim("content");
			if ("TRAFFIC_RECHARGEING".equals(status)) {
				return new RedeemResponse(true, status, content, msgType);
			}
			return new RedeemResponse(false, status, content, msgType);
		} catch (Exception e) {
			System.out.println("解析异常");
			e.printStackTrace();
		}
		return null;
	}
	
}
