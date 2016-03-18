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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.androidpn.server.enums.BrocastUserType;
import org.androidpn.server.service.UserService;
import org.androidpn.server.util.Config;
import org.androidpn.server.xmpp.push.Message;
import org.androidpn.server.xmpp.push.NotificationManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * A controller class to process the notification related requests.
 * 
 * @author Sehwan Noh (devnoh@gmail.com)
 */
@Controller
@RequestMapping("notification")
public class NotificationController {
	
	private static final String apiKey = Config.getString("apiKey", "");
	
	private NotificationManager notificationManager = new NotificationManager();
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("form")
	public String list(HttpSession session, Model model) throws Exception {
		// 查询所有application种类
		List<String> apps = userService.getAllApplications();
		model.addAttribute("applicationList", apps);
		
		// 设置上次选择的application
		Object obj = session.getAttribute("applicationChoosed");
		String applicationChoosed = obj == null ? null : (String) obj;
		model.addAttribute("applicationChoosed", applicationChoosed);
		return "notification/form";
	}
	
	@RequestMapping("send")
	public String send(
			@RequestParam("application") String application,
			@RequestParam("broadcast") String broadcast,
			@RequestParam("username") String username,
			@RequestParam("title") String title,
			@RequestParam("message") String message,
			@RequestParam("uri") String uri,
			HttpSession session,
			Model model) throws Exception {
		session.setAttribute("applicationChoosed", application);
		
		Message msg = new Message();
		msg.setApiKey(apiKey);
		msg.setApplication(application);
		msg.setTitle(title);
		msg.setUri(uri);
		msg.setContent(message);
		msg.setMessageCategory(0);
		
		BrocastUserType brocastUserType = BrocastUserType.valueOf(broadcast);
		switch (brocastUserType) {
			case ALL_USER:
				notificationManager.sendToAllUsers(msg);
				break;
			case ONLINE_USER:
				notificationManager.sendToOnlineUsers(msg);
				break;
			case SPECIFIED_USER:
				notificationManager.sendToSpecifiedUsers(msg, username.split("[;；]"));
			default:
		}
		
		return "redirect:/notification/form.mbay";
	}
	
	/**
	 * 向指定用户发送消息
	 * 
	 * @param request
	 * @param response
	 * @param message
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("push_unicast_message")
	public void pushUnicastMessage(
			HttpServletRequest request,
			HttpServletResponse response,
			Message message) throws Exception {
		if (message == null
				|| StringUtils.isEmpty(message.getApiKey())
				|| StringUtils.isEmpty(message.getApplication())
				|| StringUtils.isEmpty(message.getUsername())
				|| !message.getApiKey().equals(apiKey)) {
			return;
		}
		String[] userArr = message.getUsername().split("[;；]");
		notificationManager.sendToSpecifiedUsers(message, userArr);
	}
	
	/**
	 * 向所有用户发送消息
	 * 
	 * @param request
	 * @param response
	 * @param message
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("push_multicast_message")
	public void pushMulticastMessage(
			HttpServletRequest request,
			HttpServletResponse response,
			Message message) throws Exception {
		if (message == null
				|| StringUtils.isEmpty(message.getApiKey())
				|| StringUtils.isEmpty(message.getApplication())
				|| !message.getApiKey().equals(apiKey)) {
			return;
		}
		notificationManager.sendToAllUsers(message);
	}
}
