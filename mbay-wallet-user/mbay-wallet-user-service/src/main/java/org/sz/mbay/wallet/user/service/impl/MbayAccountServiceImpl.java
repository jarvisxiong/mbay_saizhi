//package org.sz.mbay.wallet.user.service.impl;
//
//import java.math.BigDecimal;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.interceptor.TransactionAspectSupport;
//import org.sz.mbay.base.annotation.log.CalTimeConsuming;
//import org.sz.mbay.base.service.impl.BaseServiceImpl;
//import org.sz.mbay.base.wrap.Response;
//import org.sz.mbay.base.wrap.ResponseFail;
//import org.sz.mbay.base.wrap.ResponseSuccess;
//import org.sz.mbay.wallet.traderecord.bean.TradeRecord;
//import org.sz.mbay.wallet.traderecord.enums.AccountType;
//import org.sz.mbay.wallet.traderecord.enums.PaymentType;
//import org.sz.mbay.wallet.traderecord.enums.TradeType;
//import org.sz.mbay.wallet.traderecord.helper.TradeRecordHelper;
//import org.sz.mbay.wallet.traderecord.service.TradeRecordService;
//import org.sz.mbay.wallet.user.bean.MbayAccount;
//import org.sz.mbay.wallet.user.bean.User;
//import org.sz.mbay.wallet.user.dao.MbayAccountDao;
//import org.sz.mbay.wallet.user.error.AccountError;
//import org.sz.mbay.wallet.user.service.MbayAccountService;
//import org.sz.mbay.wallet.user.service.UserService;
//
//@Service
//public class MbayAccountServiceImpl extends BaseServiceImpl implements
//		MbayAccountService {
//		
//	private static final Logger LOGGER = LoggerFactory
//			.getLogger(MbayAccountServiceImpl.class);
//			
//	@Autowired
//	private MbayAccountDao mbayAccountDao;
//	@Autowired
//	private UserService userService;
//	@Autowired
//	private TradeRecordService tradeRecordService;
//	
//	@CalTimeConsuming
//	@Override
//	@Transactional
//	public Response addBalance(String tel, double balance,
//			TradeType tradeType, String relatedNumber, String desc) {
//		if (balance < 0) {
//			throw new IllegalArgumentException("balance cannot be negative:"
//					+ balance);
//		}
//		
//		try {
//			// 自动注册
//			User u = new User();
//			u.setTelephone(tel);
//			u.setRegSource(tradeType.name());
//			userService.autoRegister(u);
//			
//			// 加款
//			mbayAccountDao.addBalance(tel, balance);
//			
//			// 通用增减款
//			TradeRecord record = TradeRecordHelper.createCommonBalanceRecord(
//					tel,
//					new BigDecimal(balance),
//					AccountType.MBAY_ACCOUNT,
//					PaymentType.INCOME,
//					tradeType,
//					relatedNumber,
//					desc);
//					
//			// 创建交易记录
//			tradeRecordService.create(record);
//			
//			return ResponseSuccess.create(record.getSerialNumber());
//		} catch (Exception e) {
//			LOGGER.error("addBalance error: {}", e.getMessage());
//			TransactionAspectSupport.currentTransactionStatus()
//					.setRollbackOnly();
//			return ResponseFail.create(e.getMessage());
//		}
//	}
//	
//	@Override
//	public void create(MbayAccount ma) {
//		mbayAccountDao.insert(ma);
//	}
//	
//	@Override
//	public MbayAccount findByTelephone(String telephone) {
//		return mbayAccountDao.findByTelephone(telephone);
//	}
//	
//	@Override
//	@Transactional
//	public Response reduceBalance(String tel, double balance,
//			TradeType tradeType, String relatedNumber, String desc) {
//		if (balance < 0) {
//			throw new IllegalArgumentException("balance cannot be negative:"
//					+ balance);
//		}
//		
//		try {
//			// 自动注册
//			if (!userService.checkUserExsitByTelephone(tel)) {
//				User u = new User();
//				u.setTelephone(tel);
//				u.setRegSource(tradeType.name());
//				userService.autoRegister(u);
//				return AccountError.BALANCE_INSUFFICIENT;
//			}
//			
//			// 余额不足
//			BigDecimal ban = mbayAccountDao.getBalanceByTelephone(tel);
//			if (ban.compareTo(new BigDecimal(balance)) < 0) {
//				return AccountError.BALANCE_INSUFFICIENT;
//			}
//			
//			// 扣款
//			boolean suc = mbayAccountDao.reduceBalance(tel,
//					balance) == 1;
//			if (!suc) {
//				return AccountError.BALANCE_INSUFFICIENT;
//			}
//			
//			// 通用增减款
//			TradeRecord record = TradeRecordHelper.createCommonBalanceRecord(
//					tel,
//					new BigDecimal(balance),
//					AccountType.MBAY_ACCOUNT,
//					PaymentType.EXPENSE,
//					tradeType,
//					relatedNumber,
//					desc);
//					
//			// 创建交易记录
//			tradeRecordService.create(record);
//			
//			return ResponseSuccess.create(record.getSerialNumber());
//		} catch (Exception e) {
//			LOGGER.error("reduceBalance error: {}", e.getMessage());
//			TransactionAspectSupport.currentTransactionStatus()
//					.setRollbackOnly();
//			return ResponseFail.create(e.getMessage());
//		}
//	}
//	
//	@Override
//	public BigDecimal getBalanceByTelephone(String telephone) {
//		return mbayAccountDao.getBalanceByTelephone(telephone);
//	}
//	
//}
