package org.sz.mbay.channel.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.channel.user.dao.RedTrafficDao;
import org.sz.mbay.channel.user.qo.TrafficDetailQO;
import org.sz.mbay.channel.user.qo.TrafficTransferQO;
import org.sz.mbay.channel.useraccount.bean.RedTraffic;
import org.sz.mbay.channel.useraccount.bean.RedTrafficTransferOrder;
import org.sz.mbay.channel.useraccount.bean.UserRedTrafficTrade;

@Repository
public class RedTrafficDaoImpl extends BaseDaoImpl<RedTraffic> implements RedTrafficDao {

	@Override
	public RedTraffic findRedTraffic(String userNumber) {
		return super.template.selectOne("findRedTraffic", userNumber);
	}

	@Override
	public void reduceRedTraffic(String userNumber, double traffic) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNumber", userNumber);
		map.put("traffic", traffic);
		super.template.update("reduceRedTraffic", map);
	}

	@Override
	public List<RedTrafficTransferOrder> findAllTrafficTransferOrder(
			TrafficTransferQO qo, PageInfo pageInfo) {
		return super.findList(qo, pageInfo, "RedTrafficTransferOrder");
	}

	@Override
	public List<UserRedTrafficTrade> findAllRedTrafficDetail(TrafficDetailQO qo,
			PageInfo pageInfo) {
		return super.findList(qo, pageInfo, "RedTrafficDetail");
	}

	@Override
	public void increaseRedTraffic(String userNumber, double traffic) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNumber", userNumber);
		map.put("traffic", traffic);
		super.template.update("increaseRedTraffic", map);
	}
	
	
	@Override
	public int lockedTraffic(String userNumber, double traffic) {
		Map<String,Object>map=new HashMap<String,Object>();
		map.put("userNumber", userNumber);
		map.put("traffic", traffic);
	   return	this.template.update("lockedRedTraffic", map);
	}

	@Override
	public int unlockedTraffic(String userNumber, double traffic) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("userNumber", userNumber);
		map.put("traffic", traffic);
	 return	this.template.update("unlockedRedTraffic",map);
		
	}

	@Override
	public RedTraffic findRedTrafficWithLocked(String userNumber) {
		return super.template.selectOne("findRedTrafficWithLocked", userNumber);
	}

}
