package org.sz.mbay.channel.service;

import java.util.List;

import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.bean.Store;
import org.sz.mbay.channel.bean.StoreCampaign;

public interface StoreService {
	
	public List<Store> findAllStores(String userNumber, PageInfo pageinfo);
	
	public int batchCreate(int num, int operatorNum, String userNumber);
	
	public boolean delete(long id);
	
	public int batchDelete(long[] ids, String userNumber);
	
	public int exitActivity(long id, long campaignId, String userNumber);
	
	public int joinActivity(long storeId, long campaignId, String userNumber);
	
	public int continueJoinActivity(long id, long campaignId,
			String userNumber);
	
	Store findStoreByIdAndUser(long id, String userNumber);
	
	Store findStoreById(long Id);
	
	public String findUserNumberById(long id);
	
	/**
	 * 查询门店有效的活动,即C扫码后可见的活动
	 * 
	 * @param storeId
	 * @param userNumber
	 * @return
	 */
	public List<StoreCampaign> findValidActivity(long storeId, String userNumber);
	
	public Store findStoreInfos(long id);
	
	public Store findStoreByAuthCode(String authCode);
	
	public ExecuteResult update(Store store);
	
	/**
	 * 查询门店已经参加的活动
	 * 
	 * @param id
	 * @return
	 */
	public List<StoreCampaign> findJoinedCampaignsByStatusAndTime(long id, PageInfo info);
	
	/**
	 * 检查是否已经参加过该活动
	 * 
	 * @param id
	 * @param campaignId
	 * @return
	 */
	public boolean chexkIsJoined(Long id, Long campaignId);
	
	public List<Store> findNotJoinedStores(long campaignId, PageInfo info);
	
	public List<StoreCampaign> findNotJoinedCampaignsByStatusAndTime(long id, String userNumber, PageInfo info);
	
	public List<Store> findAll(String userNumber);
}
