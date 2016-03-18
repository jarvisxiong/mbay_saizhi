package org.sz.mbay.routeros.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.routeros.bean.User;
import org.sz.mbay.routeros.dao.UserDao;
import org.sz.mbay.routeros.entity.PageInfo;
import org.sz.mbay.routeros.framework.service.ServiceSupport;
import org.sz.mbay.routeros.qo.UserQO;
import org.sz.mbay.routeros.service.UserService;

@Service
public class UserServiceImpl extends ServiceSupport implements UserService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public List<User> findUsers(UserQO userQO, PageInfo pageinfo) {
		return userDao.findUsers(userQO, pageinfo);
	}
}
