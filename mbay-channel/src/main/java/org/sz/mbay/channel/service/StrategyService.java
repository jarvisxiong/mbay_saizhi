package org.sz.mbay.channel.service;

import java.util.List;

import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.api.traffic.RedeemCodeJson;
import org.sz.mbay.channel.bean.Strategy;

public interface StrategyService {
	
	/**
	 * 添加策略
	 * 
	 * @param strategy
	 * @return
	 */
	public ExecuteResult addStrategy(Strategy strategy);
	
	/**
	 * 产讯当前用户所有添加策略
	 * 
	 * @param strategy
	 * @param pageinfo
	 * @return
	 */
	public List<Strategy> findAllStrategy(Strategy strategy, PageInfo pageinfo);
	
	/**
	 * 根据策略生成兑换码
	 * 
	 * @param userid
	 * @param stragegyid
	 * @return
	 */
	public ExecuteResult generateRedeemcode(long userid, int stragegyid);
	
	/**
	 * 策略详情
	 * 
	 * @param strategyid
	 * @return
	 */
	public Strategy getStrategyDetailinfo(int strategyid);
	
	public RedeemCodeJson generateRedeemcodeJson(int stragegyid);
	
	/**
	 * 删除策略
	 * 
	 * @return
	 */
	public ExecuteResult deleteStrategy(int strategyid);
	
}
