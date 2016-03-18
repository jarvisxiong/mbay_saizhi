package org.sz.mbay.channel.api.traffic.service;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.sz.mbay.base.area.Area;
import org.sz.mbay.base.service.UtilService;
import org.sz.mbay.base.wrap.MsgType;
import org.sz.mbay.base.wrap.RedeemResponse;
import org.sz.mbay.channel.bean.Strategy;
import org.sz.mbay.channel.dao.StrategyDao;
import org.sz.mbay.channel.service.impl.StrategyServiceImpl;
import org.sz.mbay.channel.user.service.UserAccountService;
import org.sz.mbay.common.util.MbayDateFormat;
import org.sz.mbay.common.util.MbayDateFormat.DatePattern;
import org.sz.mbay.hcode.MbayHcode;
import org.sz.mbay.hcode.bean.HcodeInfo;
import org.sz.mbay.operator.dao.OperatorDao;
import org.sz.mbay.operator.enums.OperatorType;

@Component
public class TrafficChargeService {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StrategyServiceImpl.class);
	@Autowired
	UserAccountService userAccountService;
	@Autowired
	OperatorDao operatordao;
	@Autowired
	StrategyDao strategydao;
	@Autowired
	UtilService utilService;
	
	public RedeemResponse chargeTraffic(String mobile, int userid,
			int strategyid) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("请求美贝API接口兑换流量，手机号:{},用户：{},策略：{}", mobile, userid,
					strategyid);
		}
		try {
			HcodeInfo codeinfo = MbayHcode.getHcodeInfo(mobile);
			Strategy strategy = strategydao.getBean(strategyid); // /getTrafficStrategy(strategyid);
			if (strategy.getArea().value != Area.QUANGUO.value) {
				if (strategy.getArea().value != codeinfo.getProvcode()) {
					return RedeemResponse.NONSUPPORT_MOBILE;
				}
			}
			if (strategy.getTeloperator() != OperatorType.THREENETS) {
				if (strategy.getTeloperator().ordinal() != codeinfo
						.getOperator()) {
					return RedeemResponse.NONSUPPORT_MOBILE;
				}
			}
			/*Trafficbusiness biz = this.operatordao.findTraffiUnitPrice(strategy
					.getArea().value, strategy.getTeloperator().ordinal(),
					strategy.getTraffictype().ordinal());
			double amount = this.assetsdao.getAccountBalance("");
			double productprice = biz.getUnitprice() * strategy.getSendnum();
			if (productprice > amount) {
				return new RedeemResponse("INSUFFICIENT_ACCOUNT", "账户余额不足",
						MsgType.TEXT);
			}*/
			DateTime now = DateTime.now();
			String snumber = MbayDateFormat.formatDateTime(now,
					DatePattern.yyyyMMdd);
			// /int nexno = this.utilService.getNextIndex(AccountDetail.class);
			// snumber = snumber + Transactiontype.TRAFFICCHARGENO
			// / + (10000000 + nexno);
		/*	AccountDetail detail = new AccountDetail();
			// / detail.setAccountid(strategy.getUserid());
			//detail.setAmount(0 - productprice);
			detail.setInfo("流量兑换-" + mobile);
			//detail.setBalance(amount - productprice);
			detail.setNumber(snumber);
			detail.setType(PaymentType.EXPENSE);
			// // detail.setTradetype(Transactiontype.TRAFFIC_CHARGE_TRADE);
			detail.setTime(now);
			// TODO 这里应该记录兑换信息以便使渠道商可查询的到。
			detail.setOrdernumber("");
			//TODO:1
			//this.userAccountService.createBean(detail);
			// 减少账户美贝余额
			//assetsdao.reduceUserAmount("", productprice);
			// / return TrafficCharge.trafficCharge(strategy.getUserid(),mobile,
			// codeinfo.getProvcode(),
			// / codeinfo.getOperator(), strategy.getTraffictype().getValue(),
			// // strategy.getSendnum());
*/		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOGGER.error("通过美贝购买流量异常", e.fillInStackTrace());
		}
		return new RedeemResponse("SYSTEM_ERROR", "系统异常", MsgType.TEXT);
	}
	
	/**
	 * private Strategy getTrafficStrategy(int strategyid) {
	 * 
	 * strategy.setSendnum(20);
	 * Area area = new Area();
	 * area.setKey(33);
	 * strategy.setArea(area);
	 * TeleOperator tel = TeleOperator.UNICOM;
	 * strategy.setTeloperator(tel);
	 * Traffictype traffictype = Traffictype.PROVINCE;
	 * strategy.setTraffictype(traffictype);
	 * return strategy;
	 * }
	 **/
	
}
