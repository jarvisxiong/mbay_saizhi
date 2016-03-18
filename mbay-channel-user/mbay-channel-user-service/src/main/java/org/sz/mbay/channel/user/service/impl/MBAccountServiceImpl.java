package org.sz.mbay.channel.user.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.UtilService;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.user.dao.MbayTrafficDao;
import org.sz.mbay.channel.user.enums.PaymentType;
import org.sz.mbay.channel.user.enums.TradeType;
import org.sz.mbay.channel.user.exception.MBAccountTradeException;
import org.sz.mbay.channel.user.qo.TrafficDetailQO;
import org.sz.mbay.channel.user.qo.TrafficTransferQO;
import org.sz.mbay.channel.user.service.MBAccountService;
import org.sz.mbay.channel.user.wrap.TrafficTransferInfo;
import org.sz.mbay.channel.useraccount.bean.MbayTraffic;
import org.sz.mbay.channel.useraccount.bean.MbayTrafficTradeRecord;
import org.sz.mbay.channel.useraccount.bean.MbayTrafficTransferOrder;
import org.sz.mbay.channel.useraccount.bean.UserMbayTrafficTrade;

@Service
public class MBAccountServiceImpl extends BaseServiceImpl implements MBAccountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MBAccountServiceImpl.class);

	@Autowired
	MbayTrafficDao mbayTrafficDao;

	@Autowired
	UtilService utilService;

	@Override
	public MbayTraffic findMbayTraffic(String userNumber) {
		MbayTraffic mbTraffic = this.mbayTrafficDao.findMbayTraffic(userNumber);
		if (mbTraffic == null) {
			mbTraffic=this.createMbayTrafficAccount(userNumber);
		}
		return mbTraffic;
	}

	@Override
	public double getBalance(String userNumber) {
		return this.findMbayTraffic(userNumber).getTraffic();
	}

	@Override
	public double getAvailableBalance(String userNumber) {
		MbayTraffic mbayTraffic = findMbayTraffic(userNumber);
		return mbayTraffic.getTraffic() - mbayTraffic.getLockedTraffic();
	}

	@Override
	@Transactional
	public void expenditure(double traffic, String userNumber, TradeType tradeType, String relateNumber,
			String description) throws MBAccountTradeException {
		traffic = traffic < 0 ? 0 - traffic : traffic;// 保证消耗流量为正
		MbayTraffic mTraffic = this.mbayTrafficDao.findMbayTrafficWithLocked(userNumber);
		double trafficBalance = mTraffic.getTraffic();
		if ((trafficBalance - mTraffic.getLockedTraffic()) < traffic) {
			throw new MBAccountTradeException("MB账户余额不足！");
		}
		// 创建流量消耗明细
		MbayTrafficTradeRecord tradeRecord = new MbayTrafficTradeRecord();
		tradeRecord.setCreateTime(DateTime.now());
		tradeRecord.setTradeName(tradeType.getValue() + (StringUtils.isEmpty(description) ? "" : "-" + description));
		tradeRecord.setTradeType(tradeType);
		tradeRecord.setRelateNumber(relateNumber);
		tradeRecord.setTraffic(traffic);
		String tradeNumber = this.utilService.getNextSerialNumber(MbayTrafficTradeRecord.class);
		tradeRecord.setTradeNumber(tradeNumber);
		UserMbayTrafficTrade umt = new UserMbayTrafficTrade();
		double balance = trafficBalance - traffic;
		umt.setBalance(balance);
		umt.setPaymentType(PaymentType.EXPENSE);
		umt.setUserNumber(userNumber);
		umt.setTradeRecord(tradeRecord);
		try {
			this.mbayTrafficDao.createBean(tradeRecord);
			this.mbayTrafficDao.createBean(umt);
			this.mbayTrafficDao.reduceMbayTraffic(userNumber, traffic);
		} catch (Exception e) {// 捕捉异常
			LOGGER.error("{}账户MB流量支出{}异常", userNumber, traffic, e.fillInStackTrace());
			// 交易异常，回滚事物
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new MBAccountTradeException("MB账户交易失败");

		}

	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void income(double traffic, String userNumber, TradeType tradeType, String relateNumber, String description)
			throws MBAccountTradeException {
		traffic = traffic < 0 ? 0 - traffic : traffic;// 保证消耗流量为正
		// 创建流量消耗明细
		MbayTrafficTradeRecord tradeRecord = new MbayTrafficTradeRecord();
		tradeRecord.setCreateTime(DateTime.now());
		tradeRecord.setTradeName(tradeType.getValue() + (StringUtils.isEmpty(description) ? "" : "-" + description));
		tradeRecord.setTradeType(tradeType);
		tradeRecord.setRelateNumber(relateNumber);
		tradeRecord.setTraffic(traffic);
		String tradeNumber = this.utilService.getNextSerialNumber(MbayTrafficTradeRecord.class);
		tradeRecord.setTradeNumber(tradeNumber);
		MbayTraffic mtraffic = this.mbayTrafficDao.findMbayTrafficWithLocked(userNumber);
		double dataTraffic = mtraffic.getTraffic();// 查询用户流量余量
		double balance = dataTraffic + traffic;
		UserMbayTrafficTrade urt = new UserMbayTrafficTrade();
		urt.setBalance(balance);
		urt.setPaymentType(PaymentType.INCOME);
		urt.setTradeRecord(tradeRecord);
		urt.setUserNumber(userNumber);
		try {
			this.mbayTrafficDao.createBean(tradeRecord);
			this.mbayTrafficDao.createBean(urt);
			this.mbayTrafficDao.increaseMbayTraffic(userNumber, traffic);
		} catch (Exception e) {// 捕捉异常
			LOGGER.error("{}账户美贝流量收入{}异常", userNumber, traffic, e.fillInStackTrace());
			// 交易异常，回滚事物
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

			throw new MBAccountTradeException("MB账户交易失败");
		}

	}

	@Override
	@Transactional
	public ExecuteResult transfer(TrafficTransferInfo transferInfo) {
		MbayTrafficTransferOrder transferOrder = new MbayTrafficTransferOrder();
		transferOrder.setCreateTime(DateTime.now());
		transferOrder.setFromUserNumber(transferInfo.getFromUserNumber());
		transferOrder.setToUserNumber(transferInfo.getToUserNumber());
		transferOrder.setSendSms(transferInfo.isSendSms());
		int transferTraffic = transferInfo.getTraffic();
		transferOrder.setTraffic(transferTraffic);
		transferOrder.setRemark(transferInfo.getRemark());
		try {
		String orderNumber = this.utilService.getNextSerialNumber(MbayTrafficTransferOrder.class);
			transferOrder.setOrderNumber(orderNumber);
		
			// 创建转账订单
			this.mbayTrafficDao.createBean(transferOrder);
			// 创建交易记录
			MbayTrafficTradeRecord tradeRecord = new MbayTrafficTradeRecord();
			tradeRecord.setCreateTime(DateTime.now());
			tradeRecord.setTradeName(TradeType.TRANSFER.getValue() + transferInfo.getRemark());
			tradeRecord.setTradeType(TradeType.TRANSFER);
			tradeRecord.setRelateNumber(transferOrder.getOrderNumber());
			tradeRecord.setTraffic(transferInfo.getTraffic());
			String tradeNumber = utilService.getNextSerialNumber(MbayTrafficTradeRecord.class);
			tradeRecord.setTradeNumber(tradeNumber);
			this.mbayTrafficDao.createBean(tradeRecord);
			// 转出账户 支出
			MbayTraffic fromMtraffic = this.mbayTrafficDao.findMbayTrafficWithLocked(transferInfo.getFromUserNumber());
			double availeBalance = fromMtraffic.getTraffic() - fromMtraffic.getLockedTraffic();
			if (availeBalance - transferInfo.getTraffic() < 0) {
				throw new Exception("商家" + transferInfo.getFromUserNumber() + "账户余额不足");
			}
			double balance = fromMtraffic.getTraffic() - transferInfo.getTraffic();
			UserMbayTrafficTrade urt = new UserMbayTrafficTrade();
			urt.setUserNumber(transferInfo.getFromUserNumber());
			urt.setBalance(balance);
			urt.setPaymentType(PaymentType.EXPENSE);
			urt.setTradeRecord(tradeRecord);
			this.mbayTrafficDao.createBean(urt);
			this.mbayTrafficDao.reduceMbayTraffic(transferInfo.getFromUserNumber(), tradeRecord.getTraffic());
			// 转入账户 收入
			MbayTraffic toUmtraffic = this.mbayTrafficDao.findMbayTrafficWithLocked(transferInfo.getFromUserNumber());
			double toUBalance = toUmtraffic.getTraffic() + tradeRecord.getTraffic();// 查询用户流量余量

			UserMbayTrafficTrade tourt = new UserMbayTrafficTrade();
			tourt.setUserNumber(transferInfo.getToUserNumber());
			tourt.setBalance(toUBalance);
			tourt.setPaymentType(PaymentType.INCOME);
			tourt.setTradeRecord(tradeRecord);
			this.mbayTrafficDao.createBean(tourt);
			this.mbayTrafficDao.increaseMbayTraffic(transferInfo.getToUserNumber(), tradeRecord.getTraffic());
		} catch (Exception e) {
			LOGGER.error("美贝流量转账交易异常：", e.fillInStackTrace());
			// 交易异常，回滚事物
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new ExecuteResult(false, "MB流量转账交易失败!");
		}
		return new ExecuteResult(true, "MB流量转账交易成功!");

	}

	@Override
	public List<MbayTrafficTransferOrder> findAllTrafficTransferOrder(TrafficTransferQO qo, PageInfo pageInfo) {
		return this.mbayTrafficDao.findAllTrafficTransferOrder(qo, pageInfo);
	}

	@Override
	public List<UserMbayTrafficTrade> findAllMbayTrafficDetail(TrafficDetailQO qo, PageInfo pageInfo) {
		return this.mbayTrafficDao.findAllMbayTrafficDetail(qo, pageInfo);
	}

	@Override
	public MbayTraffic createMbayTrafficAccount(String userNumber) {
		MbayTraffic mbayTraffic=new MbayTraffic();
		mbayTraffic.setUserNumber(userNumber);
		mbayTraffic.setTraffic(0);
		mbayTraffic.setLockedTraffic(0);
		return this.mbayTrafficDao.createBean(mbayTraffic);
	}

	@Override
	public void lockedTraffic(String userNumber, double traffic) throws MBAccountTradeException {
		traffic = traffic < 0 ? 0 - traffic : traffic;
		int result = this.mbayTrafficDao.lockedTraffic(userNumber, traffic);
		if (result != 1) {
			///throw new MBAccountTradeException("商户"+userNumber+"MB账户锁定MB失败！");
		}
	}

	@Override
	public void unlockedTraffic(String userNumber, double traffic) throws MBAccountTradeException {
		traffic = traffic < 0 ? 0 - traffic : traffic;
		int result = this.mbayTrafficDao.unlockedTraffic(userNumber, traffic);
		if (result != 1) {
			//throw new MBAccountTradeException("商户"+userNumber+"MB账户锁定MB失败！");
		}
	}

}
