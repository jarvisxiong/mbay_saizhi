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
package org.sz.mbay.message.push.web.controller;

import java.util.List;

import org.androidpn.server.model.User;
import org.androidpn.server.service.UserService;
import org.androidpn.server.xmpp.presence.PresenceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * A controller class to process the user related requests.
 * 
 * @author Sehwan Noh (devnoh@gmail.com)
 */
@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("list")
	public String list(
			@RequestParam(value = "application", required = false) String application, 
			Model model) throws Exception {
		PresenceManager presenceManager = new PresenceManager();
		List<User> userList = userService.getUsers(application);
		for (User user : userList) {
			if (presenceManager.isAvailable(user)) {		
				user.setOnline(true);
			} else {
				user.setOnline(false);
			}
		}
		model.addAttribute("userList", userList);
		model.addAttribute("applicationQO", application);
		return "user/list";
	}
	
}
