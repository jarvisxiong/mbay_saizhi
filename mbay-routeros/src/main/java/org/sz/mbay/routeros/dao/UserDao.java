package org.sz.mbay.routeros.dao;

import java.util.List;

import org.sz.mbay.routeros.bean.User;
import org.sz.mbay.routeros.entity.PageInfo;
import org.sz.mbay.routeros.framework.dao.DaoSupport;
import org.sz.mbay.routeros.qo.UserQO;

/**
 * 用户持久层
 * 
 * @author jerry
 */
public interface UserDao extends DaoSupport<User> {
	
	/**
	 * 查找用户
	 * 
	 * @param userQO
	 * @param pageinfo 
	 * @return
	 */
	List<User> findUsers(UserQO userQO, PageInfo pageinfo);
	
}
