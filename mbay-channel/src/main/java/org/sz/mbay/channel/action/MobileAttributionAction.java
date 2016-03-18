package org.sz.mbay.channel.action;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.base.area.Area;
import org.sz.mbay.hcode.MbayHcode;
import org.sz.mbay.hcode.bean.HcodeInfo;

/**
 * 手机归属地查询
 * @author ONECITY
 *
 */
@Controller
@RequestMapping("mobile")
public class MobileAttributionAction {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MobileAttributionAction.class);
	@RequestMapping("attribution")
	@ResponseBody
	public JSONObject getHcodeinfo(@RequestParam("mobile") String mobile){
		try{			
		  HcodeInfo hcodeinfo= MbayHcode.getHcodeInfo(mobile);
		  //因为json执行fromObject方法时会重新getAttribution(),而从redis中读取的attibution已加上运营商
		  //故此重新设置attribution数据
		  hcodeinfo.setAttribution(Area.valueOf(hcodeinfo.getProvcode()).getName());
		  return JSONObject.fromObject(hcodeinfo);
		}catch(Exception e){
			LOGGER.error("getHcodeinfo查询归属地异常{}",e.getMessage());
		  return null;	
		}
	}

}
