package org.sz.mbay.trafficred.dao;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.trafficred.bean.TrafficRedMbayGift;

public interface TrafficRedMbayGiftDao extends BaseDao<TrafficRedMbayGift> {
	
	int insert(TrafficRedMbayGift gift);

	TrafficRedMbayGift selectBySerialNumber(String decNum);

	boolean checkExistBySerialNumber(String decNum);

	int updateByIdSelective(TrafficRedMbayGift gift);

	boolean[] checkParticipable(String mobile, int[] ptimes);

	TrafficRedMbayGift findById(long id);
	
}
