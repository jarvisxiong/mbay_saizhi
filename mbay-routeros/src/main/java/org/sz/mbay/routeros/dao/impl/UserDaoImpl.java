package org.sz.mbay.routeros.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.sz.mbay.routeros.bean.User;
import org.sz.mbay.routeros.dao.UserDao;
import org.sz.mbay.routeros.entity.PageInfo;
import org.sz.mbay.routeros.framework.dao.DaoSupportImpl;
import org.sz.mbay.routeros.qo.UserQO;

@Repository
public class UserDaoImpl extends DaoSupportImpl<User> implements UserDao {

	@Override
	public List<User> findUsers(UserQO userQO, PageInfo pageinfo) {
		userQO.setComments(userQO.getComments());
		return super.findList(userQO, pageinfo, "User");
	}

}
