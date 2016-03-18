//package org.sz.mbay.wallet.user.service.impl;
//
//import java.math.BigDecimal;
//
//import org.apache.commons.lang.StringUtils;
//import org.joda.time.DateTime;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.interceptor.TransactionAspectSupport;
//import org.sz.mbay.base.service.impl.BaseServiceImpl;
//import org.sz.mbay.common.util.DigestUtils;
//import org.sz.mbay.wallet.user.bean.MbayAccount;
//import org.sz.mbay.wallet.user.bean.User;
//import org.sz.mbay.wallet.user.dao.MbayAccountDao;
//import org.sz.mbay.wallet.user.dao.UserDao;
//import org.sz.mbay.wallet.user.service.UserService;
//
//@Service("UserServiceImpl_Wallet")
//public class UserServiceImpl extends BaseServiceImpl implements UserService {
//	
//	private static final Logger LOGGER = LoggerFactory
//			.getLogger(UserServiceImpl.class);
//			
//	@Autowired
//	private UserDao userDao;
//	@Autowired
//	private MbayAccountDao mbayAccountDao;
//	
//	@Override
//	public User findByTelephone(String tel) {
//		return userDao.findByTelephone(tel);
//	}
//	
//	@Override
//	@Transactional
//	public boolean create(User user) {
//		if (StringUtils.isEmpty(user.getTelephone())) {
//			throw new IllegalArgumentException(
//					"create wallet user fail: telephone is not provided");
//		}
//		
//		try {
//			// 创建基本用户信息
//			String number = DigestUtils.crc32(user.getTelephone());
//			user.setCreateTime(DateTime.now());
//			user.setUserNumber(number);
//			user.setLikeNum(0);
//			userDao.insert(user);
//			
//			// 创建主账户
//			BigDecimal presentAmount = new BigDecimal(0);
//			MbayAccount ma = new MbayAccount();
//			ma.setTelephone(user.getTelephone());
//			ma.setBalance(presentAmount);
//			mbayAccountDao.insert(ma);
//			
//			return true;
//		} catch (Exception e) {
//			LOGGER.error("UserServiceImpl registerUser error:"
//					+ e.fillInStackTrace());
//			TransactionAspectSupport.currentTransactionStatus()
//					.setRollbackOnly();
//			return false;
//		}
//	}
//	
//	@Override
//	public boolean checkUserExsitByTelephone(String telephone) {
//		return userDao.checkUserExsitByTelephone(telephone);
//	}
//	
//	@Override
//	public String getPasswordByTelephone(String telephone) {
//		return userDao.getPasswordByTelephone(telephone);
//	}
//	
//	@Override
//	public boolean checkUserActiveByTelephone(String telephone) {
//		return userDao.checkUserActiveByTelephone(telephone);
//	}
//	
//	@Override
//	public boolean updateByTelephoneSelective(User user) {
//		return userDao.updateByTelephoneSelective(user) == 1;
//	}
//	
//	@Override
//	public String autoRegister(User user) {
//		// 验证用户名是否已注册
//		if (!userDao.checkUserExsitByTelephone(user.getTelephone())) {
//			boolean suc = create(user);
//			if (suc) {
//				return user.getUserNumber();
//			}
//			return null;
//		} else {
//			return userDao.findByTelephone(user.getTelephone()).getUserNumber();
//		}
//	}
//	
//}
