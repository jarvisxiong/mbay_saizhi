package org.sz.mbay.channel.user.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.UtilService;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.user.dao.RedTrafficDao;
import org.sz.mbay.channel.user.enums.PaymentType;
import org.sz.mbay.channel.user.enums.TradeType;
import org.sz.mbay.channel.user.exception.RedAccountTradeException;
import org.sz.mbay.channel.user.exception.UserAccountTradeException;
import org.sz.mbay.channel.user.qo.TrafficDetailQO;
import org.sz.mbay.channel.user.qo.TrafficTransferQO;
import org.sz.mbay.channel.user.service.RedTrafficAccountService;
import org.sz.mbay.channel.user.service.UserAccountService;
import org.sz.mbay.channel.user.wrap.TrafficTransferInfo;
import org.sz.mbay.channel.useraccount.bean.RedTraffic;
import org.sz.mbay.channel.useraccount.bean.RedTrafficTradeRecord;
import org.sz.mbay.channel.useraccount.bean.RedTrafficTransferOrder;
import org.sz.mbay.channel.useraccount.bean.UserRedTrafficTrade;

@Service
public class RedTrafficAccountServiceImpl extends BaseServiceImpl
		implements RedTrafficAccountService {
		
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RedTrafficAccountServiceImpl.class);
			
	@Autowired
	UserAccountService userAccountService;
	
	@Autowired
	RedTrafficDao redTrafficDao;
	
	@Autowired
	UtilService utilService;
	
	public RedTraffic findRedTraffic(String userNumber) {
		RedTraffic redTraffic = this.redTrafficDao.findRedTraffic(userNumber);
		if (redTraffic == null) {
			redTraffic=	this.createRedTrafficAccount(userNumber);
		}
		return redTraffic;
	}
	
	@Override
	public double getBalance(String userNumber) {
		return this.findRedTraffic(userNumber).getTraffic();
	}
	
	@Override
	public double getAvailableBalance(String userNumber) {
		RedTraffic redTraffic = findRedTraffic(userNumber);
		return redTraffic.getTraffic() - redTraffic.getLockedTraffic();
	}
	
	@Override
	@Transactional
	public void expenditure(double traffic, String userNumber,
			TradeType tradeType, String relateNumber,
			String description) throws RedAccountTradeException {
		traffic = traffic < 0 ? 0 - traffic : traffic;// 保证消耗流量为正
		RedTraffic redTraffic = this.redTrafficDao
				.findRedTrafficWithLocked(userNumber);
		double dataTraffic = redTraffic.getTraffic();// 查询用户流量余量
		if ((redTraffic.getTraffic()
				- redTraffic.getLockedTraffic()) < traffic) {
			throw new RedAccountTradeException("账户余额不足");
		}
		// 创建流量消耗明细
		RedTrafficTradeRecord tradeRecord = new RedTrafficTradeRecord();
		tradeRecord.setCreateTime(DateTime.now());
		tradeRecord.setTradeName(tradeType.getValue()
				+ (StringUtils.isEmpty(description) ? "" : "-" + description));
		tradeRecord.setTradeType(tradeType);
		tradeRecord.setRelateNumber(relateNumber);
		tradeRecord.setTraffic(traffic);
		String tradenumber = this.utilService
				.getNextSerialNumber(RedTrafficTradeRecord.class);
		tradeRecord.setTradeNumber(tradenumber);
		double balance = dataTraffic - traffic;
		UserRedTrafficTrade urt = new UserRedTrafficTrade();
		urt.setBalance(balance);
		urt.setPaymentType(PaymentType.EXPENSE);
		urt.setUserNumber(userNumber);
		urt.setTradeRecord(tradeRecord);
		try {
			this.redTrafficDao.createBean(tradeRecord);
			this.redTrafficDao.createBean(urt);
			this.redTrafficDao.reduceRedTraffic(userNumber, traffic);
		} catch (Exception e) {// 捕捉异常
			LOGGER.error("{}账户美贝流量支出{}异常", userNumber, traffic,
					e.fillInStackTrace());
			// 交易异常，回滚事物
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			throw new RedAccountTradeException("账户交易失败");
			
		}
		
	}
	
	@Override
	@Transactional
	public void income(double traffic, String userNumber, TradeType tradeType,
			String relateNumber,
			String description) throws RedAccountTradeException {
		traffic = traffic < 0 ? 0 - traffic : traffic;// 保证消耗流量为正
		// 创建流量消耗明细
		RedTrafficTradeRecord tradeRecord = new RedTrafficTradeRecord();
		tradeRecord.setCreateTime(DateTime.now());
		tradeRecord.setTradeName(tradeType.getValue()
				+ (StringUtils.isEmpty(description) ? "" : "-" + description));
		tradeRecord.setTradeType(tradeType);
		tradeRecord.setRelateNumber(relateNumber);
		tradeRecord.setTraffic(traffic);
		String tradenumber = this.utilService
				.getNextSerialNumber(RedTrafficTradeRecord.class);
		tradeRecord.setTradeNumber(tradenumber);
		RedTraffic redTraffic = this.redTrafficDao
				.findRedTrafficWithLocked(userNumber);
		double balance = redTraffic.getTraffic() + traffic;
		UserRedTrafficTrade urt = new UserRedTrafficTrade();
		urt.setBalance(balance);
		urt.setPaymentType(PaymentType.INCOME);
		urt.setUserNumber(userNumber);
		urt.setTradeRecord(tradeRecord);
		try {
			this.redTrafficDao.createBean(tradeRecord);
			this.redTrafficDao.increaseRedTraffic(userNumber, traffic);
			this.redTrafficDao.createBean(urt);
		} catch (Exception e) {// 捕捉异常
			LOGGER.error("{}账户美贝流量收入{}异常", userNumber, traffic,
					e.fillInStackTrace());
			// 交易异常，回滚事物
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			throw new RedAccountTradeException("账户交易失败！");
		}
		
	}
	
	@Override
	@Transactional
	public ExecuteResult transfer(TrafficTransferInfo transferInfo) {
		RedTrafficTransferOrder transferOrder = new RedTrafficTransferOrder();
		transferOrder.setCreateTime(DateTime.now());
		transferOrder.setFromUserNumber(transferInfo.getFromUserNumber());
		transferOrder.setToUserNumber(transferInfo.getToUserNumber());
		transferOrder.setSendSms(transferInfo.isSendSms());
		int transferTraffic = transferInfo.getTraffic();
		transferOrder.setTraffic(transferTraffic);
		transferOrder.setRemark(transferInfo.getRemark());
		String orderNumber = this.utilService
				.getNextSerialNumber(RedTrafficTransferOrder.class);
		transferOrder.setOrderNumber(orderNumber);
		try {
			// 创建转账订单
			this.redTrafficDao.createBean(transferOrder);
			// 创建交易记录
			RedTrafficTradeRecord tradeRecord = new RedTrafficTradeRecord();
			tradeRecord.setCreateTime(DateTime.now());
			tradeRecord.setTradeName(
					TradeType.TRANSFER.getValue() + transferInfo.getRemark());
			tradeRecord.setTradeType(TradeType.TRANSFER);
			tradeRecord.setRelateNumber(transferOrder.getOrderNumber());
			tradeRecord.setTraffic(transferInfo.getTraffic());
			String tradeNumber = utilService
					.getNextSerialNumber(RedTrafficTradeRecord.class);
			tradeRecord.setTradeNumber(tradeNumber);
			this.redTrafficDao.createBean(tradeRecord);
			// 转出账户 支出
			RedTraffic fromRtraffic = this.redTrafficDao
					.findRedTrafficWithLocked(transferInfo.getFromUserNumber());
			double availeBalance = fromRtraffic.getTraffic()
					- fromRtraffic.getLockedTraffic();
			if (availeBalance - transferInfo.getTraffic() < 0) {
				throw new Exception(
						"商家" + transferInfo.getFromUserNumber() + "账户余额不足");
			}
			double balance = fromRtraffic.getTraffic()
					- transferInfo.getTraffic();
			UserRedTrafficTrade urt = new UserRedTrafficTrade();
			urt.setUserNumber(transferInfo.getFromUserNumber());
			urt.setBalance(balance);
			urt.setPaymentType(PaymentType.EXPENSE);
			urt.setTradeRecord(tradeRecord);
			this.redTrafficDao.createBean(urt);
			this.redTrafficDao.reduceRedTraffic(
					transferInfo.getFromUserNumber(), tradeRecord.getTraffic());
			// 转入账户 收入
			RedTraffic toUmtraffic = this.redTrafficDao
					.findRedTrafficWithLocked(transferInfo.getFromUserNumber());
			double toUBalance = toUmtraffic.getTraffic()
					+ tradeRecord.getTraffic();// 查询用户流量余量
					
			UserRedTrafficTrade tourt = new UserRedTrafficTrade();
			tourt.setUserNumber(transferInfo.getToUserNumber());
			tourt.setBalance(toUBalance);
			tourt.setPaymentType(PaymentType.INCOME);
			tourt.setTradeRecord(tradeRecord);
			this.redTrafficDao.createBean(tourt);
			this.redTrafficDao.increaseRedTraffic(
					transferInfo.getToUserNumber(), tradeRecord.getTraffic());
		} catch (Exception e) {
			LOGGER.error("美贝流量转账交易异常：", e.fillInStackTrace());
			// 交易异常，回滚事物
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return new ExecuteResult(false, "红包流量转账交易失败!");
		}
		return new ExecuteResult(true, "红包流量转账交易成功!");
		
	}
	
	@Override
	public List<RedTrafficTransferOrder> findAllTrafficTransferOrder(
			TrafficTransferQO qo, PageInfo pageInfo) {
		return this.redTrafficDao.findAllTrafficTransferOrder(qo, pageInfo);
	}
	
	@Override
	public List<UserRedTrafficTrade> findAllRedTrafficDetail(TrafficDetailQO qo,
			PageInfo pageInfo) {
		return this.redTrafficDao.findAllRedTrafficDetail(qo, pageInfo);
	}
	
	@Override
	public RedTraffic createRedTrafficAccount(String userNumber) {
		RedTraffic redTraffic=new RedTraffic();
		redTraffic.setUserNumber(userNumber);
		redTraffic.setTraffic(0);
		redTraffic.setLockedTraffic(0);
		return this.redTrafficDao.createBean(redTraffic);
	}
	
	@Override
	public void lockedTraffic(String userNumber, double traffic)
			throws RedAccountTradeException {
		int result = this.redTrafficDao.lockedTraffic(userNumber, traffic);
		if (result != 1) {
			throw new RedAccountTradeException("红包账户lockedTraffic 失败！");
		}
		
	}
	
	@Override
	public void unlockedTraffic(String userNumber, double traffic)
			throws RedAccountTradeException {
		traffic = traffic < 0 ? 0 - traffic : traffic;
		int result = this.redTrafficDao.unlockedTraffic(userNumber, traffic);
		if (result != 1) {
			throw new RedAccountTradeException("unlockedTraffic 失败");
		}
		
	}
	
	@Override
	@Transactional
	public ExecuteResult transferInFromMbayAccount(double transferInAmount,
			String userNumber) {
		if (transferInAmount <= 0) {
			return new ExecuteResult(false, "转入美贝须大于零！");
		}
		double mbayBalance = this.userAccountService
				.getAvailableAmount(userNumber);
		if (transferInAmount > mbayBalance) {
			return new ExecuteResult(false, "美贝账户可用余额不足于本次转入！");
		}
		try {
			this.userAccountService.expenditure(userNumber,
					TradeType.MBAY_TRANSFER_IN_RED, "", transferInAmount,
					"红包账户单次转入");
		} catch (UserAccountTradeException e) {
			LOGGER.error("美贝转入红包，美贝账户交易失败", e.fillInStackTrace());
			return new ExecuteResult(false, "美贝账户交易失败！");
		}
		try {
			this.income(transferInAmount, userNumber,
					TradeType.MBAY_TRANSFER_IN_RED, "",
					"红包账户单次转入");
		} catch (RedAccountTradeException e) {
			return new ExecuteResult(false, "红包账户交易失败");
		}
		
		return new ExecuteResult(true, "");
	}
	
}
