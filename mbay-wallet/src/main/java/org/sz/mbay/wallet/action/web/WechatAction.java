package org.sz.mbay.wallet.action.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.base.wrap.ResponseFail;
import org.sz.mbay.base.wrap.ResponseSuccess;
import org.sz.mbay.remote.interfaces.wallet.RIMBAccountUtil;
import org.sz.mbay.remote.interfaces.wallet.RITradeRecordUtil;
import org.sz.mbay.remote.interfaces.wallet.base.RIResponse;
import org.sz.mbay.wallet.constant.error.TaskError;
import org.sz.mbay.wallet.context.WalletContext;
import org.sz.mbay.weixin.WeChatJSUtil;

@Controller("Web_WechatAction")
@RequestMapping("web/wechat")
public class WechatAction {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(WechatAction.class);
	
	/**
	 * 根据页面地址得到微信配置信息
	 * 
	 * @param url
	 *            需要调用微信接口的页面地址
	 * @return
	 */
	@ResponseBody
	@RequestMapping("js/config/get")
	public Object jsConfigGet(String url) {
		WeChatJSUtil.WeChatVerifyConfig config = WeChatJSUtil
				.getVerifyConfig(url);
		return config;
	}
	
	/**
	 * 分享成功，立即获取20MB（我是MB）
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("share/mb/get")
	public Object shareMbGet() {
		String mobile = WalletContext.getSessionUser().getMobile();
		
		// 验证任务是否已完成
		try {
			RIResponse trResp = RITradeRecordUtil.getTradeRecordList(mobile,
					"TASK_TRANSFER_MB", 1, 10);
			if (trResp.getData().getJSONArray("result").size() > 0) {
				return TaskError.TASK_REPEAT;
			}
		} catch (Exception e) {
			LOGGER.error("lookup record info error:{}", e.getMessage());
			return ResponseFail.create(e.getMessage());
		}
		
		// 账户操作
		try {
			RIResponse resp = RIMBAccountUtil.requestUserEnterOfAccount(mobile,
					20, "TASK_TRANSFER_MB", null, null);
			return ResponseSuccess
					.create(resp.getData().getString("serialNumber"));
		} catch (Exception e) {
			LOGGER.error("add balance error:{}", e.getMessage());
			return ResponseFail.create(e.getMessage());
		}
	}
}
