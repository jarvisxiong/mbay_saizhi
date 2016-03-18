package org.sz.mbay.channel.user.service.impl;

import java.util.List;

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
import org.sz.mbay.channel.user.bean.User;
import org.sz.mbay.channel.user.dao.UserAccountDao;
import org.sz.mbay.channel.user.enums.PaymentType;
import org.sz.mbay.channel.user.enums.TradeType;
import org.sz.mbay.channel.user.exception.UserAccountTradeException;
import org.sz.mbay.channel.user.qo.MbayTransferOrderQO;
import org.sz.mbay.channel.user.qo.UserMbayTradeQO;
import org.sz.mbay.channel.user.service.UserAccountService;
import org.sz.mbay.channel.useraccount.bean.MbayTradeRecord;
import org.sz.mbay.channel.useraccount.bean.MbayTrafficTradeRecord;
import org.sz.mbay.channel.useraccount.bean.MbayTransferInfo;
import org.sz.mbay.channel.useraccount.bean.MbayTransferOrder;
import org.sz.mbay.channel.useraccount.bean.UserAccount;
import org.sz.mbay.channel.useraccount.bean.UserMbayTrade;

@Service
public class UserAccountServiceImpl extends BaseServiceImpl implements UserAccountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserAccountServiceImpl.class);

	@Autowired
	UserAccountDao userAccountDao;
	@Autowired
	UtilService utilService;

	@Override
	@Transactional
	public void expenditure(String userNumber, TradeType type, String relateNumber, double mbayprice, String remark)
			throws UserAccountTradeException {
		mbayprice = mbayprice < 0 ? 0 - mbayprice : mbayprice;

		try {
			UserAccount userAaccount = this.userAccountDao.findUserAccountWithLocked(userNumber);
			double balance = userAaccount.getAmount().doubleValue() - userAaccount.getLockedamount().doubleValue();// 查询账户可用余额
			if (mbayprice > balance) {
				throw new UserAccountTradeException("用户"+userNumber+"账户余额不足");
			}
			balance = userAaccount.getAmount().doubleValue() - mbayprice;
			MbayTradeRecord record = new MbayTradeRecord();
			record.setAmount(mbayprice);
			String tradeNumber = this.utilService.getNextSerialNumber(MbayTradeRecord.class);
			record.setTradeNumber(tradeNumber);
			record.setRelateNumber(relateNumber);
			record.setTradeType(type);
			record.setTradeName(type.getValue() + "-" + remark);
			record.setCreateTime(DateTime.now());
			this.userAccountDao.createBean(record);
			UserMbayTrade tradeInfo = new UserMbayTrade();
			tradeInfo.setUserNumber(userNumber);
			tradeInfo.setBalance(balance);
			tradeInfo.setPaymentType(PaymentType.EXPENSE);
			tradeInfo.setTradeRecord(record);
			this.userAccountDao.createBean(tradeInfo);
			boolean isReduceSuccess = this.userAccountDao.reduceAccountMbay(userNumber, mbayprice);
			if (!isReduceSuccess) {
				LOGGER.error("{}账户userAccountDao.reduceAccountMbay失败", userNumber);
				throw new UserAccountTradeException("reduceAccountMbay 失败");
			}
		} catch (Exception e) {
			System.out.println(e.fillInStackTrace());
			LOGGER.error("{}账户美贝账户支出{}异常", userNumber, mbayprice, e.fillInStackTrace());
			// 交易异常，回滚事物
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		}
		
	}

	@Override
	@Transactional
	public void income(String userNumber, TradeType type, String relateNumber, double mbayprice,
			String remark)throws UserAccountTradeException {
		mbayprice = mbayprice < 0 ? 0 - mbayprice : mbayprice;
		try {
			UserAccount userAaccount = this.userAccountDao.findUserAccountWithLocked(userNumber);
			double balance = userAaccount.getAmount().doubleValue() + mbayprice;
			MbayTradeRecord record = new MbayTradeRecord();
			record.setAmount(mbayprice);
			String tradeNumber = this.utilService.getNextSerialNumber(MbayTradeRecord.class);
			record.setTradeNumber(tradeNumber);
			record.setRelateNumber(relateNumber);
			record.setTradeType(type);
			record.setTradeName(type.getValue() + "-" + remark);
			record.setCreateTime(DateTime.now());
			this.userAccountDao.createBean(record);
			UserMbayTrade tradeInfo = new UserMbayTrade();
			tradeInfo.setUserNumber(userNumber);
			tradeInfo.setBalance(balance);
			tradeInfo.setPaymentType(PaymentType.INCOME);
			tradeInfo.setTradeRecord(record);
			this.userAccountDao.createBean(tradeInfo);
			this.userAccountDao.increaseAccountMbay(userNumber, mbayprice);
		} catch (Exception e) {
			LOGGER.error("{}账户美贝账户支出{}异常", userNumber, mbayprice, e.fillInStackTrace());
			// 交易异常，回滚事物
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new UserAccountTradeException("账户交易失败，请重试！");

		}
	}

	@Override
	public UserAccount findUserAccount(String userNumber) {
		return this.userAccountDao.findUserAccount(userNumber);
	}

	@Override
	public double getAccountAmount(String userNumber) {
		return this.findUserAccount(userNumber).getAmount().doubleValue();
	}

	@Override
	public double getAvailableAmount(String userNumber) {
		UserAccount userAccount = this.findUserAccount(userNumber);
		double lockedMbay = userAccount.getLockedamount().doubleValue();
		double mbayAmount = userAccount.getAmount().doubleValue();
		return mbayAmount - lockedMbay;
	}

	@Override
	@Transactional
	public ExecuteResult transfer(MbayTransferInfo transferInfo) {
		MbayTransferOrder transferOrder = new MbayTransferOrder();
		transferOrder.setCreateTime(DateTime.now());
		transferOrder.setFromUserNumber(transferInfo.getFromUserNumber());
		User toUser = new User();
		toUser.setUsernumber(transferInfo.getToUserNumber());
		transferOrder.setToUser(toUser);
		transferOrder.setPayAmount(transferInfo.getMbayAmount());
		transferOrder.setSendAmount(transferInfo.getSendAmount());
		transferOrder.setTransferNote(transferInfo.getRemark());
		String orderNumber = this.utilService.getNextSerialNumber(MbayTransferOrder.class);
		transferOrder.setOrderNumber(orderNumber);
		try {
			// 创建转账订单
			this.userAccountDao.createBean(transferOrder);
			// 创建交易记录
			MbayTradeRecord tradeRecord = new MbayTradeRecord();
			tradeRecord.setCreateTime(DateTime.now());
			tradeRecord.setTradeName(TradeType.TRANSFER.getValue() + transferInfo.getRemark());
			tradeRecord.setTradeType(TradeType.TRANSFER);
			tradeRecord.setRelateNumber(transferOrder.getOrderNumber());
			tradeRecord.setAmount(transferInfo.getMbayAmount() + transferInfo.getSendAmount());
			String tradeNumber = utilService.getNextSerialNumber(MbayTrafficTradeRecord.class);
			tradeRecord.setTradeNumber(tradeNumber);
			this.userAccountDao.createBean(tradeRecord);
			// 转出账户 支出
			UserAccount userAaccount = this.userAccountDao.findUserAccountWithLocked(transferInfo.getFromUserNumber());
			double availeBalance = userAaccount.getAmount().doubleValue()
					- userAaccount.getLockedamount().doubleValue();
			if (availeBalance - tradeRecord.getAmount() < 0) {
				throw new Exception("商家" + transferInfo.getFromUserNumber() + "账户余额不足");
			}
			double balance = userAaccount.getAmount().doubleValue() - tradeRecord.getAmount();
			UserMbayTrade urt = new UserMbayTrade();
			urt.setUserNumber(transferInfo.getFromUserNumber());
			urt.setBalance(balance);
			urt.setPaymentType(PaymentType.EXPENSE);
			urt.setTradeRecord(tradeRecord);
			this.userAccountDao.createBean(urt);
			this.userAccountDao.reduceAccountMbay(transferInfo.getFromUserNumber(), tradeRecord.getAmount());
			// 转入账户 收入
			UserAccount inUserAaccount = this.userAccountDao
					.findUserAccountWithLocked((transferInfo.getFromUserNumber()));
			double toUBalance = inUserAaccount.getAmount().doubleValue() + tradeRecord.getAmount();// 查询用户流量余量

			UserMbayTrade tourt = new UserMbayTrade();
			tourt.setUserNumber(transferInfo.getToUserNumber());
			tourt.setBalance(toUBalance);
			tourt.setPaymentType(PaymentType.INCOME);
			tourt.setTradeRecord(tradeRecord);
			this.userAccountDao.createBean(tourt);

			this.userAccountDao.increaseAccountMbay(transferInfo.getToUserNumber(), tradeRecord.getAmount());
		} catch (Exception e) {
			LOGGER.error("美贝流量转账交易异常：", e.fillInStackTrace());
			// 交易异常，回滚事物
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new ExecuteResult(false, "美贝转账交易失败!");
		}
		return new ExecuteResult(true, "美贝转账交易成功!");

	}

	@Override
	public void lockedMbay(String userNumber, double mbay) throws UserAccountTradeException {
		boolean result = this.userAccountDao.increaseLockedMbay(userNumber, mbay) == 1;
		if (!result) {
			throw new UserAccountTradeException("锁定账户美贝失败，请检查账户可用余额是否锁定美贝");
		}
	}

	@Override
	public void unLockedMbay(String userNumber, double mbay)throws UserAccountTradeException {
		boolean result = this.userAccountDao.reduceLockedMbay(userNumber, mbay);
		if (!result) {
			LOGGER.error("商户{}unLockedMbay失败", userNumber);
			throw new UserAccountTradeException("解除账户锁定美贝失败");
		}
	}

	@Override
	public List<MbayTransferOrder> findAllMbayTransferOrder(MbayTransferOrderQO qo, PageInfo pageInfo) {
		return this.userAccountDao.findList(MbayTransferOrder.class, qo, pageInfo);
	}

	@Override
	public List<UserMbayTrade> findAllUserMbayTrade(UserMbayTradeQO qo, PageInfo pageInfo) {
		return this.userAccountDao.findList(UserMbayTrade.class, qo, pageInfo);
	}

}
