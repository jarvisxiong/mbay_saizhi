//package org.sz.mbay.wallet.user.dao;
//
//import java.math.BigDecimal;
//
//import org.sz.mbay.base.dao.BaseDao;
//import org.sz.mbay.wallet.user.bean.MbayAccount;
//
//public interface MbayAccountDao extends BaseDao<MbayAccount> {
//	
//	MbayAccount findByTelephone(String tel);
//	
//	int addBalance(String tel, double exPrice);
//	
//	int reduceBalance(String tel, double exPrice);
//	
//	void insert(MbayAccount ma);
//
//	BigDecimal getBalanceByTelephone(String tel);
//}
