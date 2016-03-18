package org.sz.mbay.channel.dao;

import org.sz.mbay.base.area.Area;
import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.channel.bean.Strategy;
import org.sz.mbay.operator.enums.OperatorType;

public interface StrategyDao extends BaseDao<Strategy> {
	
	// / public Strategy findStrategyByIdAndUid(int userid,int strategyid);
	
	/**
	 * 怎加策略兑换码发放数量
	 * 
	 * @param strategyid
	 * @return
	 */
	public int plusRedeemCodeSend(int strategyid);
	
	/**
	 * 根据覆盖区域获取最大美贝对应流量比例值
	 * 
	 * @param area
	 * @return
	 * @throws Exception
	 */
	public double getMaxUnitMbByArea(Area area) throws Exception;
	
	/**
	 * 根据覆盖区域以及运营商获取对应 美贝比例
	 * 
	 * @param area
	 * @param operator
	 * @return
	 * @throws Exception
	 */
	public double getUnitMbByAreaAndOperator(Area area, OperatorType operator) throws Exception;
	
	/**
	 * @param strategyid
	 * @param url
	 * @return
	 */
	public int updateStrategyRedeemCodeURL(int strategyid, String url);
	
	public int deleteStragegy(int id, long userid);
	
}
