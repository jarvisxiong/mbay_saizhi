//package org.sz.mbay.wallet.user.dao.impl;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.stereotype.Repository;
//import org.sz.mbay.base.dao.impl.BaseDaoImpl;
//import org.sz.mbay.wallet.user.bean.MbayAccount;
//import org.sz.mbay.wallet.user.dao.MbayAccountDao;
//
//@Repository
//public class MbayAccountDaoImpl extends BaseDaoImpl<MbayAccount> implements
//		MbayAccountDao {
//	
//	@Override
//	public MbayAccount findByTelephone(String tel) {
//		return template.selectOne("MbayAccount.findByTelephone", tel);
//	}
//	
//	@Override
//	public int addBalance(String tel, double balance) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("telephone", tel);
//		map.put("balance", balance);
//		return template.update("MbayAccount.addBalance", map);
//	}
//	
//	@Override
//	public int reduceBalance(String tel, double balance) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("telephone", tel);
//		map.put("balance", balance);
//		return template.update("MbayAccount.reduceBalance", map);
//	}
//	
//	@Override
//	public void insert(MbayAccount ma) {
//		template.insert("MbayAccount.insert", ma);
//	}
//	
//	@Override
//	public BigDecimal getBalanceByTelephone(String tel) {
//		Double balance = template.selectOne(
//				"MbayAccount.getBalanceByTelephone", tel);
//		if (balance == null) {
//			balance = 0.0;
//		}
//		return new BigDecimal(balance);
//	}
//}
