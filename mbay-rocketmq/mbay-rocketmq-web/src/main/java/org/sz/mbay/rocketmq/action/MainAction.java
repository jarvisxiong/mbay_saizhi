package org.sz.mbay.rocketmq.action;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.rocketmq.helper.TopicType;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;

/**
 * 消息队列处理
 * 
 * @author jerry
 */
@Controller
@RequestMapping("main")
public class MainAction {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MainAction.class);
			
	@Autowired
	private DefaultMQProducer producer;
	
	/**
	 * 发送消息
	 * 
	 * @param type
	 * @param message
	 */
	@ResponseBody
	@RequestMapping("msg/send")
	public void msgSend(@RequestParam("topic") TopicType type,
			@RequestParam("message") String message) {
		if (type == null || StringUtils.isEmpty(message)) {
			LOGGER.info("illegal params:topic:{}, message:{}", type, message);
			return;
		}
		
		Message msg = new Message();
		msg.setTopic(type.name());
		try {
			msg.setBody(message.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("translate message to utf-8 error:{}/{}",
					e.getMessage(), message);
		}
		try {
			SendResult result = producer.send(msg);
			if (result.getSendStatus() != SendStatus.SEND_OK) {
				LOGGER.error("send message error:{}/{}", result.getSendStatus(),
						message);
			}
		} catch (Exception e) {
			LOGGER.error("send message error:{}/{}", e.getMessage(), message);
		}
	}
}
