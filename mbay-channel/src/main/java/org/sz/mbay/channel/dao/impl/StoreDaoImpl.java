package org.sz.mbay.channel.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.channel.bean.Store;
import org.sz.mbay.channel.bean.StoreCampaign;
import org.sz.mbay.channel.bean.StoreCampaignRecord;
import org.sz.mbay.channel.dao.StoreDao;

@Repository
public class StoreDaoImpl extends BaseDaoImpl<Store> implements StoreDao {
	
	@Override
	public int batchAdd(List<Store> stores) {
		return this.template.insert("batchCreateStore", stores);
	}
	
	@Override
	public Store findStoreByIdAndUser(long id, String userNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("userNumber", userNumber);
		return this.template.selectOne("findStoreByIdAndUser", map);
	}
	
	@Override
	public Store findStoreById(long id) {
		return this.template.selectOne("findStoreById", id);
	}
	
	@Override
	public int deleteUpdateStore(long[] ids, String userNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		map.put("userNumber", userNumber);
		return this.template.update("batchUpdateStore", map);
	}
	
	@Override
	public String findMaxStoreId(String userNumber) {
		return this.template.selectOne("findMaxStoreId", userNumber);
	}
	
	@Override
	public int updateActivityRecord(long storeId, long campaignId,
			boolean exited) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("storeId", storeId);
		map.put("campaignId", campaignId);
		map.put("exited", exited);
		return this.template.update("updateActivityRecord", map);
	}
	
	@Override
	public int createActivityRecord(StoreCampaignRecord record) {
		return this.template.update("createActivityRecord", record);
	}
	
	@Override
	public String findUserNumber(long id) {
		return this.template.selectOne("findUserNumber", id);
	}
	
	@Override
	public List<StoreCampaign> findValidActivity(long storeId, String userNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("storeId", storeId);
		map.put("userNumber", userNumber);
		return this.template.selectList("findValidActivity", map);
	}
	
	@Override
	public Store findSimpleStoreInfo(long id) {
		return this.template.selectOne("findSimpleStoreInfo", id);
	}
	
	@Override
	public Store findStoreByAuthCode(String authCode) {
		return this.template.selectOne("findStoreByAuthCode", authCode);
	}
	
	@Override
	public int updateStoreIsActive(long id, boolean active) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("active", active);
		return this.template.update("updateStoreIsActive", map);
	}
	
	@Override
	public Long chexkIsJoined(Long id, Long campaignId) {
		HashMap<String, Long> map = new HashMap<String, Long>();
		map.put("storeId", id);
		map.put("campaignId", campaignId);
		return this.template.selectOne("chexkIsJoined", map);
		
	}
	
	@Override
	public List<Store> findAll(String userNumber) {
		return this.template.selectList("findAll", userNumber);
	}
}
