package org.sz.mbay.redis;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class CommonRedis {
	
	protected static Logger LOGGER = Logger.getLogger(CommonRedis.class);
	
	private static JedisSentinelPool pool;
	
	private CommonRedis() {}

	@SuppressWarnings("resource")
	private static JedisSentinelPool getShardedJedisPool() {
		if (pool == null) {
			ApplicationContext ctx = new ClassPathXmlApplicationContext("mbay-redis.xml");
			pool = (JedisSentinelPool) ctx.getBean("shardedJedisPool");
		}
		return pool;
	}
	
	/**
	 * 设置数据到redis中
	 * @param key
	 * @param value
	 * @param seconds
	 * @return ExecuteResult
	 */
	@SuppressWarnings("deprecation")
	public static boolean setToRedis(String key,String value, String seconds) {
		Jedis jedis = getShardedJedisPool().getResource();
		String result = "";
		if(StringUtils.isEmpty(seconds)){
			result = jedis.set(key, value);
		}else{
			result = jedis.setex(key, Integer.valueOf(seconds), value);
		}
		if(StringUtils.isEmpty(result)){
			LOGGER.error("保存数据到redis中失败,key:" + key);
			return false;
		}
		LOGGER.info("成功保存数据到redis中,key:" + key);
		getShardedJedisPool().returnResource(jedis);
		return true;
	}
	
	/**
	 * 根据key从redis中获取相应数据
	 * 
	 * @param key
	 * @return 如果失败返回null
	 */
	@SuppressWarnings("deprecation")
	public static String getFromRedis(String key) {
		if (StringUtils.isEmpty(key)) {
			LOGGER.error("从redis中获取数据失败,传入的key值为空");
			return null;
		}
		Jedis jedis = getShardedJedisPool().getResource();
		String value = jedis.get(key);
		if (StringUtils.isEmpty(value)) {
			LOGGER.error("没有在redis中找到相应的数据,key:" + key);
			return null;
		}
		LOGGER.info("成功从redis中取出数据,key:" + key);
		getShardedJedisPool().returnResource(jedis);
		return value;
	}
	
	/**
	 * 根据key从redis中删除相应数据
	 * 
	 * @param key
	 * @return 如果失败返回null
	 */
	@SuppressWarnings("deprecation")
	public static boolean delFromRedis(String key) {
		Jedis jedis = getShardedJedisPool().getResource();
		jedis.del(key);
		LOGGER.info("成功从redis中删除数据,key:" + key);
		getShardedJedisPool().returnResource(jedis);
		return true;
	}
}