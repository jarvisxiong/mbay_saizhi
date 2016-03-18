package org.sz.mbay.trafficred.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.base.dao.utils.PKgen;
import org.sz.mbay.trafficred.bean.TrafficRedMbayGift;
import org.sz.mbay.trafficred.dao.TrafficRedMbayGiftDao;

@Repository
public class TrafficRedMbayGiftDaoImpl extends BaseDaoImpl<TrafficRedMbayGift>
		implements TrafficRedMbayGiftDao {
		
	@Override
	public int insert(TrafficRedMbayGift gift) {
		gift.setId(PKgen.getInstance().nextPK());
		return template.insert("TrafficRedMbayGift.insert", gift);
	}
	
	@Override
	public TrafficRedMbayGift selectBySerialNumber(String decNum) {
		List<TrafficRedMbayGift> list = template.selectList(
				"TrafficRedMbayGift.selectBySerialNumber", decNum);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	
	@Override
	public boolean checkExistBySerialNumber(String decNum) {
		return template.selectOne(
				"TrafficRedMbayGift.checkExistBySerialNumber", decNum);
	}
	
	@Override
	public int updateByIdSelective(TrafficRedMbayGift gift) {
		return template.update("TrafficRedMbayGift.updateByIdSelective", gift);
	}
	
	@Override
	public boolean[] checkParticipable(String mobile, int[] ptimes) {
		Map<String, Object> params = new HashMap<>();
		params.put("mobile", mobile);
		params.put("dayTimes", ptimes[0]);
		params.put("weekTimes", ptimes[1]);
		params.put("monthTimes", ptimes[2]);
		Map<String, Boolean> res = template.selectOne(
				"TrafficRedMbayGift.checkParticipable", params);
		boolean[] pass = new boolean[3];
		pass[0] = res.get("dayPass");
		pass[1] = res.get("weekPass");
		pass[2] = res.get("monthPass");
		return pass;
	}
	
	@Override
	public TrafficRedMbayGift findById(long id) {
		return template.selectOne("TrafficRedMbayGift.findById", id);
	}
	
}
