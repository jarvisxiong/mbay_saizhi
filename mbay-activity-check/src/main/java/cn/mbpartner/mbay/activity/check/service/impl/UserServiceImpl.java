package cn.mbpartner.mbay.activity.check.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mbpartner.mbay.activity.check.dao.UserDao;
import cn.mbpartner.mbay.activity.check.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public List<String> findAllUsernumber() {
	return userDao.findAllUsernumber();
    }

}
