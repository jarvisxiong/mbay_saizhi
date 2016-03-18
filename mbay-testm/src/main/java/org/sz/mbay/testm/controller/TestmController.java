package org.sz.mbay.testm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.sz.mbay.testm.bean.User;
import org.sz.mbay.testm.service.UserService;

@Controller
@RequestMapping("user")
public class TestmController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("ud")
	public String userDetail(){
		User user=this.userService.findUser(1);
		return "";
	}
	

}
