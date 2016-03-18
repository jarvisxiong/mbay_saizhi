package org.sz.mbay.redis.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.sz.mbay.common.util.HttpSupport;
import org.sz.mbay.redis.common.config.CommonConfig;

/**
 * mbayrediscommon调用入口
 * 
 * @author frank.zong
 */
public class MbayRedisCommon {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MbayRedisCommon.class);
			
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	/**
	 * 从redis中获取数据（通用）
	 * 
	 * @param key
	 * @return 如果查询失败返回null
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getOne(String key) {
		try {
			String response = HttpSupport.build(CommonConfig
					.getCommonRedisServer() + key).connect().trim();
			if (StringUtils.isEmpty(response)) {
				LOGGER.info("redis没有相关数据,key:" + key);
				return null;
			}
			LOGGER.info("{},redis获取的数据:{}", key, response);
			String[] arrs = response.split("\\|");
			return (T) MAPPER.readValue(arrs[0], Class.forName(arrs[1].trim()));
		} catch (Exception e) {
			LOGGER.error("redis获取数据失败,key:" + key, e.fillInStackTrace());
		}
		return null;
	}
	
	/**
	 * 保存数据到redis中（通用）
	 * 
	 * @param key
	 * @param object对象
	 * @return ExecuteResult对象
	 */
	public static boolean setOne(String key, Object bean) {
		try {
			Map<String, String> params = new HashMap<>();
			params.put("value", MAPPER.writeValueAsString(bean));
			params.put("key", key);
			params.put("className", bean.getClass().getName());
			
			String response = HttpSupport.build(
					CommonConfig.setCommonRedisServer(), params).connect()
					.trim();
			LOGGER.info("保存数据到redis中:{},key:{}", response, key);
			return Boolean.valueOf(response);
		} catch (Exception e) {
			LOGGER.error("保存数据到redis中失败,key:" + key, e.fillInStackTrace());
		}
		return false;
	}
	
	/**
	 * 保存数据到redis中（通用）- 可以设置有效期
	 * 
	 * @param key
	 * @param object对象
	 * @param seconds
	 *            有效期(秒)
	 * @return ExecuteResult对象
	 */
	public static boolean setOne(String key, Object bean, int seconds) {
		try {
			Map<String, String> params = new HashMap<>();
			params.put("value", MAPPER.writeValueAsString(bean));
			params.put("key", key);
			params.put("className", bean.getClass().getName());
			params.put("seconds", String.valueOf(seconds));
			
			String response = HttpSupport.build(
					CommonConfig.setCommonRedisServer(), params).connect()
					.trim();
			LOGGER.info("保存数据到redis中:{},key:{}", response, key);
			return Boolean.valueOf(response);
		} catch (Exception e) {
			LOGGER.error("保存数据到redis中失败,key:" + key, e.fillInStackTrace());
		}
		return false;
	}
	
	/**
	 * 从redis中获取数据（通用）
	 * 
	 * @param key
	 * @return 如果查询失败返回null
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getList(String key) {
		try {
			String response = HttpSupport.build(CommonConfig
					.getCommonRedisServer() + key).connect().trim();
			if (StringUtils.isEmpty(response)) {
				LOGGER.info("redis没有相关数据,key:" + key);
				return null;
			}
			LOGGER.info("{},redis获取的数据:{}", key, response);
			String[] arrs = response.split("\\|");
			JavaType type = MAPPER.getTypeFactory().constructParametricType(
					ArrayList.class, Class.forName(arrs[1].trim()));
			return (List<T>) MAPPER.readValue(arrs[0], type);
		} catch (Exception e) {
			LOGGER.error("redis获取数据失败,key:" + key, e.fillInStackTrace());
		}
		return null;
	}
	
	/**
	 * 保存数据到redis中（通用）
	 * 
	 * @param key
	 * @param list对象
	 * @return ExecuteResult对象
	 */
	public static boolean setList(String key, List<?> list) {
		if (list == null || list.size() == 0) {
			LOGGER.info("保存数据到redis中失败,list对象没有数据");
			del(key);
			return false;
		}
		
		try {
			Map<String, String> params = new HashMap<>();
			params.put("key", key);
			params.put("value", MAPPER.writeValueAsString(list));
			params.put("className", list.get(0).getClass().getName());
			
			String response = HttpSupport.build(
					CommonConfig.setCommonRedisServer(), params).connect()
					.trim();
			LOGGER.info("保存数据到redis中:{},key:{}", response, key);
			return Boolean.valueOf(response);
		} catch (Exception e) {
			LOGGER.error("保存数据到redis中失败,key:" + key, e.fillInStackTrace());
		}
		return false;
	}
	
	/**
	 * 保存数据到redis中（通用） - 可以设置有效期
	 * 
	 * @param key
	 * @param list对象
	 * @param seconds
	 *            有效期
	 * @return ExecuteResult对象
	 */
	public static boolean setList(String key, List<?> list, int seconds) {
		if (list == null || list.size() == 0) {
			LOGGER.info("保存数据到redis中失败,list对象没有数据");
			del(key);
			return false;
		}
		
		try {
			Map<String, String> params = new HashMap<>();
			params.put("key", key);
			params.put("value", MAPPER.writeValueAsString(list));
			params.put("className", list.get(0).getClass().getName());
			params.put("seconds", String.valueOf(seconds));
			
			String response = HttpSupport.build(
					CommonConfig.setCommonRedisServer(), params).connect()
					.trim();
			LOGGER.info("保存数据到redis中:{},key:{}", response, key);
			return Boolean.valueOf(response);
		} catch (Exception e) {
			LOGGER.error("保存数据到redis中失败,key:" + key, e.fillInStackTrace());
		}
		return false;
	}
	
	/**
	 * 从redis中删除数据（通用）
	 * 
	 * @param key
	 * @return 如果查询失败返回null
	 */
	public static boolean del(String key) {
		try {
			String response = HttpSupport.build(CommonConfig
					.delCommonRedisServer() + key).connect().trim();
			if (StringUtils.isEmpty(response)) {
				LOGGER.info("redis没有相关数据,key:" + key);
				return false;
			}
			LOGGER.info("redis删除结果:{},key:{}", response, key);
			return Boolean.valueOf(response);
		} catch (Exception e) {
			LOGGER.error("redis删除失败,key:" + key, e.fillInStackTrace());
			return false;
		}
	}
}
