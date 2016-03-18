package org.sz.mbay.channel.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/o2o/")
public class O2OAction {
	
	@RequestMapping("index")
	public String index() {
		return "o2o_campaign/index";
	}
}
