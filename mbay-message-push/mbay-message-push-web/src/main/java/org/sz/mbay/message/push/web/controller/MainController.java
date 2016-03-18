package org.sz.mbay.message.push.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 主控制层
 *
 * @author jerry
 */
@Controller
@RequestMapping("main")
public class MainController {
	
	@RequestMapping("index")
	public String index() {
		return "index";
	}
}
