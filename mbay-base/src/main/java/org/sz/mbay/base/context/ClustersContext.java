package org.sz.mbay.base.context;

import org.sz.mbay.redis.common.MbayRedisCommon;

/**
 * @author han.han
 *         用于处理集群情况下 服务共享信息。此种信息全部保存在redis
 * 		
 */
public class ClustersContext {
	
	public static void setAttribute(String name, Object object) {
		MbayRedisCommon.setOne(name, object);
		
	}
	
	public static Object getAttribute(String name) {
		return MbayRedisCommon.getOne(name);
	}
}
