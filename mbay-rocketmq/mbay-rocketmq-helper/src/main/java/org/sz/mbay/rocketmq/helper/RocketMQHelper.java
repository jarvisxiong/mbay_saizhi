package org.sz.mbay.rocketmq.helper;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.mbay.common.util.HttpSupport;
import org.sz.mbay.rocketmq.helper.config.RocketMQHelperConfig;

/**
 * 消息队列辅助类
 * 
 * @author jerry
 */
public class RocketMQHelper {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RocketMQHelper.class);
			
	public static void sendMessage(TopicType type, String msg) {
		Map<String, String> map = new HashMap<>();
		map.put("topic", type.name());
		map.put("message", msg);
		
		try {
			HttpSupport.connect(RocketMQHelperConfig.SEND_MSG_URL, map);
		} catch (Exception e) {
			LOGGER.info("send message error: topic:{}, message:{}, reason:{}",
					type, msg, e.getMessage());
		}
	}
}
