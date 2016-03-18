package org.sz.mbay.rocketmq.listener;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.mbay.base.config.ResourceConfig;
import org.sz.mbay.base.context.SpringApplicationContext;
import org.sz.mbay.rocketmq.helper.TopicType;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.MessageExt;

public class RocketMQListener implements ServletContextListener {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RocketMQListener.class);
			
	private DefaultMQPushConsumer consumer;
	private DefaultMQProducer producer;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		consumer = (DefaultMQPushConsumer) SpringApplicationContext
				.getBean("consumer");
		producer = (DefaultMQProducer) SpringApplicationContext
				.getBean("producer");
				
		try {
			// consumer配置
			consumer.setNamesrvAddr(ResourceConfig.getProperty("namesrvAddr"));
			consumer.setConsumerGroup(
					ResourceConfig.getProperty("consumerGroup"));
			consumer.setInstanceName(
					ResourceConfig.getProperty("consumberInstanceName"));
					
			// producer配置
			producer.setNamesrvAddr(ResourceConfig.getProperty("namesrvAddr"));
			producer.setProducerGroup(
					ResourceConfig.getProperty("producerGroup"));
			producer.setInstanceName(
					ResourceConfig.getProperty("producerInstanceName"));
					
			// 订阅已定义的topic枚举
			List<String> topics = ResourceConfig
					.getRegProperties("subscribe.*");
			String[] spt = null;
			for (String topic : topics) {
				spt = topic.split("[/\\\\]");
				consumer.subscribe(spt[0], spt[1]);
			}
			
			// 定义消息处理监听器
			consumer.registerMessageListener(new MessageListenerConcurrently() {
				
				@Override
				public ConsumeConcurrentlyStatus consumeMessage(
						List<MessageExt> msgs,
						ConsumeConcurrentlyContext context) {
					LOGGER.info("{} Receive New Messages: {}",
							Thread.currentThread().getName(), msgs.size());
							
					for (MessageExt msg : msgs) {
						switch (TopicType.valueOf(msg.getTopic())) {
							// 流量充值
							case TRAFFIC_ORDER:
								System.out.println(111111);
								break;
							default:
								LOGGER.info("unhandled topic type:{}",
										msg.getTopic());
						}
					}
					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
				}
			});
			
			consumer.start();
		} catch (MQClientException e) {
			e.printStackTrace();
		}
		try {
			producer.start();
		} catch (MQClientException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		if (consumer != null) {
			consumer.shutdown();
		}
		if (producer != null) {
			producer.shutdown();
		}
	}
	
}
