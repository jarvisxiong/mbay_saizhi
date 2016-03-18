package org.sz.mbay.channel.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.channel.user.dao.MbayTrafficDao;
import org.sz.mbay.channel.user.qo.TrafficDetailQO;
import org.sz.mbay.channel.user.qo.TrafficTransferQO;
import org.sz.mbay.channel.useraccount.bean.MbayTraffic;
import org.sz.mbay.channel.useraccount.bean.MbayTrafficTransferOrder;
import org.sz.mbay.channel.useraccount.bean.UserMbayTrafficTrade;

@Repository
public class MbayTrafficDaoImpl extends BaseDaoImpl<MbayTraffic> implements MbayTrafficDao {

	@Override
	public MbayTraffic findMbayTraffic(String userNumber) {
		return super.template.selectOne("findMbayTraffic", userNumber);
	}

	@Override
	public void reduceMbayTraffic(String userNumber, double  traffic) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNumber", userNumber);
		map.put("traffic", traffic);
		Integer.valueOf("");
		super.template.update("reduceMbayTraffic", map);
	}

	@Override
	public List<MbayTrafficTransferOrder> findAllTrafficTransferOrder(
			TrafficTransferQO qo, PageInfo pageInfo) {
		return super.findList(qo, pageInfo, "MbayTrafficTransferOrder");
	}

	@Override
	public List<UserMbayTrafficTrade> findAllMbayTrafficDetail(TrafficDetailQO qo,
			PageInfo pageInfo) {
		return super.findList(qo, pageInfo, "MbayTrafficDetail");
	}

	@Override
	public void increaseMbayTraffic(String userNumber, double traffic) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNumber", userNumber);
		map.put("traffic", traffic);
		super.template.update("increaseMbayTraffic", map);
	}

	@Override
	public int lockedTraffic(String userNumber, double traffic) {
		Map<String,Object>map=new HashMap<String,Object>();
		map.put("userNumber", userNumber);
		map.put("traffic", traffic);
		return this.template.update("lockedMbayTraffic", map);
	}

	@Override
	public int  unlockedTraffic(String userNumber, double traffic) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("userNumber", userNumber);
		map.put("traffic", traffic);
		return this.template.update("unlockedMbayTraffic",map);
		
	}

	@Override
	public MbayTraffic findMbayTrafficWithLocked(String userNumber) {
		return super.template.selectOne("findMbayTrafficWithLocked", userNumber);
	}

}
