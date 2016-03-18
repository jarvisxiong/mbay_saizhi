package org.sz.mbay.channel.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.channel.bean.Store;
import org.sz.mbay.channel.bean.StoreCampaign;
import org.sz.mbay.channel.bean.StoreCampaignRecord;

public interface StoreDao extends BaseDao<Store> {
	
	int batchAdd(List<Store> stores);
	
	Store findStoreById(long id);
	
	int deleteUpdateStore(long ids[], String userNumber);
	
	String findMaxStoreId(String userNumber);
	
	int updateActivityRecord(long storeId, long campaignId, boolean exited);
	
	int createActivityRecord(StoreCampaignRecord record);
	
	String findUserNumber(long id);
	
	Store findStoreByIdAndUser(long id, String userNumber);
	
	List<StoreCampaign> findValidActivity(long storeId, String userNumber);
	
	Store findSimpleStoreInfo(long id);
	
	Store findStoreByAuthCode(String authCode);
	
	int updateStoreIsActive(long id, boolean b);
	
	Long chexkIsJoined(Long id, Long campaignId);
	
	/**
	 * 根据用户编号得到所有的有效门店(用于到处到excel)
	 * 
	 * @param userNumber
	 * @return
	 */
	List<Store> findAll(String userNumber);
}
