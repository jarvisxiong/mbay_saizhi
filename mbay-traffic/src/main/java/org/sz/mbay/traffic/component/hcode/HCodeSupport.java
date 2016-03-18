package org.sz.mbay.traffic.component.hcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sz.mbay.base.context.SpringApplicationContext;
import org.sz.mbay.hcode.service.HcodeInfoService;

@Component
public class HCodeSupport {
	
	@Autowired
	HcodeInfoService hcodeService;

	public  org.sz.mbay.hcode.bean.HcodeInfo getHcodeInfo(String mobile) {
		return hcodeService.getHcodeInfo(mobile);
	}
	
	
	
	public static HCodeSupport getInstance(){
		return (HCodeSupport) SpringApplicationContext.getBean(HCodeSupport.class);
	}
	
}
