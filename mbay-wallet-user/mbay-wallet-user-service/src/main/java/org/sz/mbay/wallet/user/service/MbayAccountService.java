//package org.sz.mbay.wallet.user.service;
//
//import java.math.BigDecimal;
//
//import org.sz.mbay.base.wrap.Response;
//import org.sz.mbay.wallet.traderecord.enums.TradeType;
//import org.sz.mbay.wallet.user.bean.MbayAccount;
//
//public interface MbayAccountService {
//	
//	/**
//	 * 根据用户号码查找实体
//	 * 
//	 * @return
//	 */
//	MbayAccount findByTelephone(String telephone);
//	
//	/**
//	 * 获取余额
//	 * 
//	 * @return
//	 */
//	BigDecimal getBalanceByTelephone(String telephone);
//	
//	/**
//	 * 增加金额（自带通用交易明细）
//	 * 
//	 * 自动注册
//	 * 
//	 * @param number
//	 * @param balance
//	 * @return
//	 */
//	Response addBalance(String telephone, double balance, TradeType tradeType,
//			String relatedNumber, String desc);
//			
//	/**
//	 * 减少金额（自带通用交易明细）
//	 * 
//	 * 自动注册
//	 * 
//	 * @param number
//	 * @param balance
//	 * @param tradeType
//	 * @param relatedNumber
//	 * @return
//	 */
//	Response reduceBalance(String telephone, double balance,
//			TradeType tradeType, String relatedNumber, String desc);
//			
//	/**
//	 * 创建记录
//	 * 
//	 * @param ma
//	 */
//	void create(MbayAccount ma);
//}
