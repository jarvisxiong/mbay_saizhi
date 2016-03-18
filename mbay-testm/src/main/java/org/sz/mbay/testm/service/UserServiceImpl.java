package org.sz.mbay.testm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.testm.bean.User;
import org.sz.mbay.testm.dao.UserDao;

@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {
	
	@Autowired
	UserDao userDao;
	@Override
	public User findUser(int id) {
			return userDao.getBean(id);
	}

}
