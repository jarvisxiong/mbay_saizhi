package org.sz.mbay.routeros.service;

import java.util.List;

import org.sz.mbay.routeros.bean.User;
import org.sz.mbay.routeros.entity.PageInfo;
import org.sz.mbay.routeros.qo.UserQO;

/**
 * 用户服务
 * 
 * @author jerry
 */
public interface UserService {
	
	/**
	 * 查询用户
	 * 
	 * @param userQO
	 * @param pageinfo 
	 * @return
	 */
	List<User> findUsers(UserQO userQO, PageInfo pageinfo);
	
}
