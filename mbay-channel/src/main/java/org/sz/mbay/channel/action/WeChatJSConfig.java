package org.sz.mbay.channel.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.weixin.WeChatJSUtil;

/**
 * 与微信有关的Action层封装类
 * 
 * @author Fenlon
 * 
 */
@Controller
@RequestMapping("/wechat/")
public class WeChatJSConfig {
	
	/**
	 * 根据页面地址得到微信配置信息
	 * 
	 * @param url
	 *            需要调用微信接口的页面地址
	 * @return
	 */
	@RequestMapping(value = "config_info")
	@ResponseBody
	public WeChatJSUtil.WeChatVerifyConfig getConfigInfo(String url) {
		WeChatJSUtil.WeChatVerifyConfig config = WeChatJSUtil.getVerifyConfig(url);
		return config;
	}
}
