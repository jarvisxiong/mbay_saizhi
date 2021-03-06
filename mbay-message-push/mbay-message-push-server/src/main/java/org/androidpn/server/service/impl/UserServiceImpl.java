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
package org.androidpn.server.service.impl;

import java.util.List;

import javax.persistence.EntityExistsException;

import org.androidpn.server.dao.UserDao;
import org.androidpn.server.exception.UserExistsException;
import org.androidpn.server.model.User;
import org.androidpn.server.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/** 
 * This class is the implementation of UserService.
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
@Service
public class UserServiceImpl implements UserService {

    protected final Log log = LogFactory.getLog(getClass());
    
    @Autowired
    private UserDao userDao;

    @Override
    public User getUser(String userId) {
    	return userDao.get(userId);
    }

    @Override
    public List<User> getUsers(String applicaiton) {
        return userDao.getUsers(applicaiton);
    }

    @Override
    public User saveUser(User user) throws UserExistsException {
        try {
            return userDao.saveOrUpdate(user);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new UserExistsException("User '" + user.getUsername()
                    + "' already exists!");
        } catch (EntityExistsException e) { // needed for JPA
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new UserExistsException("User '" + user.getUsername()
                    + "' already exists!");
        }
    }

    @Override
    public User getUserByUsername(String application, String username) {
        return (User) userDao.getUserByUsername(application, username);
    }

    @Override
    public void removeUser(Long userId) {
        userDao.delete(userId);
    }

	@Override
	public List<String> getAllApplications() {
		return userDao.getAllApplications();
	}

}
