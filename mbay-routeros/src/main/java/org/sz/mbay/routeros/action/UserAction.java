package org.sz.mbay.routeros.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.sz.mbay.routeros.bean.User;
import org.sz.mbay.routeros.constant.Globals;
import org.sz.mbay.routeros.entity.PageInfo;
import org.sz.mbay.routeros.qo.UserQO;
import org.sz.mbay.routeros.service.UserService;

/**
 * 用户控制层
 *
 * @author jerry
 */
@Controller
@RequestMapping("user")
public class UserAction {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("list")
	public String list(
			UserQO userQO, 
			Model model,
			PageInfo pageinfo) {
		List<User> userList = userService.findUsers(userQO, pageinfo);
		model.addAttribute("userList", userList);
		model.addAttribute("userQO", userQO);
		model.addAttribute(Globals.PAGEINFO_KEY, pageinfo);
		return "user/list";
	}
}
