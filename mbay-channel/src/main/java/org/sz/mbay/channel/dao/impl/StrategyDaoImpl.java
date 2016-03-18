package org.sz.mbay.channel.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.area.Area;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.channel.bean.Strategy;
import org.sz.mbay.channel.dao.StrategyDao;
import org.sz.mbay.operator.enums.OperatorType;

@Repository
public class StrategyDaoImpl extends BaseDaoImpl<Strategy> implements StrategyDao {
	
	@Override
	public int plusRedeemCodeSend(int strategyid) {
		return this.template.update("plusRedeemCodeSend", strategyid);
	}
	
	@Override
	public double getMaxUnitMbByArea(Area area) throws Exception {
		return this.template.selectOne("findMaxUnitMbByArea", area.value);
	}
	
	@Override
	public double getUnitMbByAreaAndOperator(Area area, OperatorType operator) throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("area", area.value);
		map.put("operator", operator.ordinal());
		return this.template.selectOne("findUnitMbByAreaAndOperator", map);
	}
	
	@Override
	public int updateStrategyRedeemCodeURL(int strategyid, String url) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("strategyid", strategyid);
		map.put("url", url);
		return this.template.update("updateStrategyRedeemCodeURL", map);
	}
	
	@Override
	public int deleteStragegy(int id, long userid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("strategyid", id);
		map.put("userid", userid);
		return this.template.update("deleteStrategy", map);
	}
	
}
