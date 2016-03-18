//package org.sz.mbay.wallet.user.service;
//
//import org.sz.mbay.wallet.user.bean.User;
//
///**
// * 账户服务层
// * 
// * @author jerry
// */
//public interface UserService {
//	
//	/**
//	 * 根据登录名找到用户
//	 * 
//	 * @param tel
//	 * @return
//	 */
//	User findByTelephone(String tel);
//	
//	/**
//	 * 注册账户
//	 * 
//	 * @param user
//	 * @return
//	 */
//	boolean create(User user);
//	
//	/**
//	 * 检测账户是否存在
//	 * 
//	 * @param loginName
//	 * @return
//	 */
//	boolean checkUserExsitByTelephone(String telephone);
//	
//	/**
//	 * 根据用户名查询密码
//	 * 
//	 * @param loginName
//	 * @return
//	 */
//	String getPasswordByTelephone(String telephone);
//	
//	/**
//	 * 用户是否已激活
//	 * 
//	 * @param telephone
//	 * @return
//	 */
//	boolean checkUserActiveByTelephone(String telephone);
//	
//	/**
//	 * 更新用户
//	 * 
//	 * @param user
//	 * @return
//	 */
//	boolean updateByTelephoneSelective(User user);
//	
//	/**
//	 * 自动注册
//	 * 
//	 * 号码不存在，创建记录，返回用户编号
//	 * 号码存在，返回用户编号
//	 * 
//	 * @param user
//	 * @return
//	 */
//	String autoRegister(User user);
//	
//}
