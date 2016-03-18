package cn.mbpartner.mbay.activity.check.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.mbpartner.mbay.activity.check.dao.UserDao;

@Repository(value = "userDao")
public class UserDaoImpl implements UserDao {

    @Autowired
    public SqlSessionTemplate template;

    @Override
    public List<String> findAllUsernumber() {
	return this.template.selectList("findAllUsernumber");
    }

}
