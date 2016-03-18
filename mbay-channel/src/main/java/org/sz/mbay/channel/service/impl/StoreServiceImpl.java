package org.sz.mbay.channel.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sz.mbay.base.dao.utils.PKgen;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.bean.Store;
import org.sz.mbay.channel.bean.StoreCampaign;
import org.sz.mbay.channel.bean.StoreCampaignRecord;
import org.sz.mbay.channel.context.ChannelContext;
import org.sz.mbay.channel.dao.StoreDao;
import org.sz.mbay.channel.service.StoreService;
import org.sz.mbay.common.util.DigestUtils;

@Service
public class StoreServiceImpl implements StoreService {
	
	@Autowired
	StoreDao storeDao;
	
	Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	@Override
	public List<Store> findAllStores(String userNumber, PageInfo pageinfo) {
		Store store = new Store();
		store.setUserNumber(userNumber);
		List<Store> stores = this.storeDao.findList(store, pageinfo);
		return stores;
	}
	
	@Override
	public int batchCreate(int num, int operatorNum, String userNumber) {
		
		String maxId = this.storeDao.findMaxStoreId(userNumber);
		if (maxId == null) {
			// 说明该用户还没有门店
			maxId = "S00000";
		}
		// 判断长度
		if (maxId.length() != 6) {
			return -1;
		}
		// 将得到的串Id转为int
		String number = maxId.substring(1);
		int storeNumber = Integer.parseInt(number);
		
		Store store = null;
		List<Store> stores = new ArrayList<Store>();
		for (int i = 0; i < num; i++) {
			storeNumber++;
			store = new Store();
			long id = PKgen.getInstance().nextPK();
			store.setId(id);
			store.setNumber(generateStoreId(storeNumber));
			store.setAuthCode(DigestUtils.crc32(id + ""));
			store.setDeleted(false);
			store.setEnable(true);
			store.setUserNumber(userNumber);
			store.setQrCodeId(i);
			store.setOperatorNum(operatorNum);
			stores.add(store);
		}
		return this.storeDao.batchAdd(stores);
	}
	
	@Override
	public Store findStoreByIdAndUser(long id, String userNumber) {
		return this.storeDao.findStoreByIdAndUser(id, userNumber);
	}
	
	@Override
	public boolean delete(long id) {
		int result = 0;
		try {
			result = this.storeDao.deleteBean(id);
			return result > 0;
		} catch (SQLException e) {
			logger.error("delete", e.fillInStackTrace());
		}
		return result > 0;
	}
	
	@Override
	public int batchDelete(long[] ids, String userNumber) {
		return this.storeDao.deleteUpdateStore(ids, userNumber);
	}
	
	private String generateStoreId(int id) {
		String storeId = "" + id;
		int len = storeId.length();
		switch (len) {
			case 1:
				storeId = "S0000" + storeId;
				break;
			case 2:
				storeId = "S000" + storeId;
				break;
			case 3:
				storeId = "S00" + storeId;
				break;
			case 4:
				storeId = "S0" + storeId;
				break;
			case 5:
				storeId = "S" + storeId;
				break;
			default:
				break;
		}
		return storeId;
	}
	
	@Override
	@Transactional
	public int exitActivity(long id, long campaignId, String userNumber) {
		if (!isUserNumberSame(userNumber, id)) {
			return -1;
		}
		return this.storeDao.updateActivityRecord(id, campaignId, true);
	}
	
	@Override
	@Transactional
	public int joinActivity(long id, long campaignId, String userNumber) {
		if (!isUserNumberSame(userNumber, id)) {
			return -1;
		}
		StoreCampaignRecord record = new StoreCampaignRecord();
		Store s = new Store();
		s.setId(id);
		record.setStore(s);
		//
		StoreCampaign campaign = new StoreCampaign();
		campaign.setId(campaignId);
		record.setStoreCampaign(campaign);
		return this.storeDao.createActivityRecord(record);
	}
	
	@Override
	@Transactional
	public int continueJoinActivity(long id, long campaignId,
			String userNumber) {
		if (!isUserNumberSame(userNumber, id)) {
			return -1;
		}
		return this.storeDao.updateActivityRecord(id, campaignId, false);
	}
	
	private boolean isUserNumberSame(String userNumber, long id) {
		String usernumber = this.storeDao.findUserNumber(id);
		if (!userNumber.equals(usernumber)) {
			return false;
		}
		return true;
	}
	
	@Override
	public Store findStoreById(long id) {
		return this.storeDao.findStoreById(id);
	}
	
	@Override
	public String findUserNumberById(long id) {
		return this.storeDao.findUserNumber(id);
	}
	
	@Override
	public List<StoreCampaign> findValidActivity(long storeId, String userNumber) {
		return this.storeDao.findValidActivity(storeId, userNumber);
	}
	
	@Override
	public Store findStoreInfos(long id) {
		return this.storeDao.findSimpleStoreInfo(id);
	}
	
	@Override
	public Store findStoreByAuthCode(String authCode) {
		return this.storeDao.findStoreByAuthCode(authCode);
	}
	
	@Override
	@Transactional
	public ExecuteResult update(Store store) {
		ExecuteResult result = new ExecuteResult(true, "跟新成功!");
		try {
			
			int r = this.storeDao.updateBean(store);
			if (r <= 0) {
				result.setError_code("FAILED");
				result.setSuccess(false);
				result.setMessage("跟新失败!");
				return result;
			}
			result.setError_code("SUCCESS");
			return result;
		} catch (SQLException e) {
			this.logger.error("update:数据库层出错:", e.fillInStackTrace());
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public List<StoreCampaign> findJoinedCampaignsByStatusAndTime(long id, PageInfo info) {
		return this.storeDao.findList(id, info, "JoinedCampaignsByStatusAndTime");
	}
	
	@Override
	public boolean chexkIsJoined(Long id, Long campaignId) {
		Long cid = this.storeDao.chexkIsJoined(id, campaignId);
		if (cid == null) {
			return false;
		}
		return true;
	}
	
	@Override
	public List<Store> findNotJoinedStores(long campaignId, PageInfo info) {
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignId", campaignId);
		map.put("userNumber", userNumber);
		return this.storeDao.findList(map, info, "NotJoinedStores");
	}
	
	@Override
	public List<StoreCampaign> findNotJoinedCampaignsByStatusAndTime(long id, String userNumber, PageInfo info) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("userNumber", userNumber);
		return this.storeDao.findList(map, info, "NotJoinedCampaignsByStatusAndTime");
	}
	
	@Override
	public List<Store> findAll(String userNumber) {
		return this.storeDao.findAll(userNumber);
	}
}
