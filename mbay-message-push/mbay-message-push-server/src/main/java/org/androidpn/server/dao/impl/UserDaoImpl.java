/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.androidpn.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.androidpn.server.dao.UserDao;
import org.androidpn.server.model.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * This class is the implementation of UserDAO using Spring's HibernateTemplate.
 * 
 * @author Sehwan Noh (devnoh@gmail.com)
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
	
	@Override
	public List<User> getUsers(String application) {
		List<Object> params = new ArrayList<Object>(); 
		StringBuffer sb = new StringBuffer();
		sb.append(" from User where 1=1 ");
		if (!StringUtils.isEmpty(application)) {
			sb.append(" and application like ? ");
			params.add("%" + application + "%");
		}
		sb.append(" order by application,createdDate desc ");
		return selectList(sb.toString(), params.toArray());
	}
	
	@Override
	public User getUserByUsername(String application, String username) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" from User where username=? and application=? ");
		params.add(username);
		params.add(application);
		return selectOne(sb.toString(), params.toArray());
	}

	@Override
	public List<String> getAllApplications() {
		return selectList(" select distinct application from User ", String.class);
	}
	
}
