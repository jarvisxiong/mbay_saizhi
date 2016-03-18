//package org.sz.mbay.wallet.user.dao;
//
//import org.sz.mbay.base.dao.BaseDao;
//import org.sz.mbay.wallet.user.bean.User;
//
///**
// * 用户持久层
// * 
// * @author jerry
// */
//public interface UserDao extends BaseDao<User> {
//	
//	User findByTelephone(String number);
//	
//	int updateByTelephoneSelective(User user);
//	
//	String getPasswordByTelephone(String telephone);
//	
//	boolean checkUserActiveByTelephone(String telephone);
//	
//	boolean checkUserExsitByTelephone(String telephone);
//	
//	int insert(User user);
//	
//}
