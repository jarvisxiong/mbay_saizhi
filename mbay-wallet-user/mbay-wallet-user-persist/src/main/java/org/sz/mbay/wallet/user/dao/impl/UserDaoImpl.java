//package org.sz.mbay.wallet.user.dao.impl;
//
//import org.springframework.stereotype.Repository;
//import org.sz.mbay.base.dao.impl.BaseDaoImpl;
//import org.sz.mbay.wallet.user.bean.User;
//import org.sz.mbay.wallet.user.dao.UserDao;
//
//@Repository("UserDaoImpl_Wallet")
//public class UserDaoImpl extends BaseDaoImpl<User>implements UserDao {
//	
//	@Override
//	public User findByTelephone(String tel) {
//		return template.selectOne("WalletUser.findByTelephone", tel);
//	}
//	
//	@Override
//	public int updateByTelephoneSelective(User user) {
//		return template.update("WalletUser.updateByTelephoneSelective", user);
//	}
//	
//	@Override
//	public String getPasswordByTelephone(String telephone) {
//		return template.selectOne("WalletUser.getPasswordByTelephone",
//				telephone);
//	}
//	
//	@Override
//	public boolean checkUserActiveByTelephone(String telephone) {
//		Boolean res = template.selectOne(
//				"WalletUser.checkUserActiveByTelephone",
//				telephone);
//		return res != null ? res : false;
//	}
//	
//	@Override
//	public boolean checkUserExsitByTelephone(String telephone) {
//		return template.selectOne("WalletUser.checkUserExsitByTelephone",
//				telephone);
//	}
//	
//	@Override
//	public int insert(User user) {
//		return template.insert("WalletUser.insert", user);
//	}
//	
//}
