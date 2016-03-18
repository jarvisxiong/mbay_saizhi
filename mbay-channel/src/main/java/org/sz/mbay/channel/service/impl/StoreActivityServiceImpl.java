package org.sz.mbay.channel.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sz.mbay.base.constant.CampaignConstant;
import org.sz.mbay.base.constant.SerialSeed;
import org.sz.mbay.base.dao.utils.PKgen;
import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.UtilService;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.bean.StoreCampaign;
import org.sz.mbay.channel.context.ChannelContext;
import org.sz.mbay.channel.dao.StoreActivityDao;
import org.sz.mbay.channel.service.StoreActivityService;
import org.sz.mbay.channel.service.StoreService;
import org.sz.mbay.channel.user.service.UserAccountService;
import org.sz.mbay.common.util.MbayDateFormat;
import org.sz.mbay.common.util.MbayDateFormat.DatePattern;
import org.sz.mbay.promotion.bean.CampaignStatistics;

@Service
public class StoreActivityServiceImpl implements StoreActivityService {
	
	private Logger logger = LoggerFactory
			.getLogger(StoreActivityServiceImpl.class);
			
	@Autowired
	StoreActivityDao storeCampaignDao;
	@Autowired
	StoreService storeService;
	@Autowired
	UtilService utilService;
	@Autowired
	UserAccountService userAccountService;
	
	@Override
	public List<StoreCampaign> finaAllByUser(String userNumber) {
		return this.storeCampaignDao.findActivitiesByUser(userNumber);
	}
	
	@Override
	@Transactional
	public StoreCampaign createActivity(StoreCampaign campaign,
			String userNumber) {
		DateTime now = DateTime.now();
		String number = MbayDateFormat
				.formatDateTime(now, DatePattern.yyyyMMdd);
		int nextid = 0;
		nextid = utilService.getNextIndex(StoreCampaign.class);
		String activityNumber = number + (SerialSeed.Event.O2O_ACTIVITY)
				+ (SerialSeed.CARDINAL_NUMBER + nextid);
		campaign.setNumber(activityNumber);
		double redeemDeduct = (campaign.getPrice()
				/ CampaignConstant.MBAY_PRICE)
				* CampaignConstant.O2O_CAMPAIGN_REDEEM_PERCENT;
		campaign.setDeductReedem(redeemDeduct);
		campaign.setLockMbay((redeemDeduct + 1) * campaign.getQuantity());
		double locakMbay = campaign.getLockMbay();
		long id = PKgen.getInstance().nextPK();
		campaign.setId(id);
		try {
			campaign = this.storeCampaignDao.createBean(campaign);
			if (campaign != null) {
				// 活动创建成功，锁定美贝
				this.userAccountService.lockedMbay(userNumber, locakMbay);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return campaign;
	}
	
	@Override
	public boolean delete(long id, String userNumber) {
		StoreCampaign campaign = new StoreCampaign();
		campaign.setId(id);
		campaign.setUserNumber(userNumber);
		campaign.setDeleted(true);
		int result = 0;
		try {
			// = this.storeCampaignDao.updateBean(campaign);
			result = this.storeCampaignDao.deleteBeanByParam(campaign);
			return result > 0;
		} catch (SQLException e) {
			this.logger.error("delete", e.fillInStackTrace());
		}
		return false;
	}
	
	@Override
	public StoreCampaign findCampaignById(long campaignId) {
		StoreCampaign campaign = null;
		try {
			campaign = this.storeCampaignDao.getBean(campaignId);
			return campaign;
		} catch (Exception e) {
			logger.error("findCampaignById", e.fillInStackTrace());
		}
		return campaign;
	}
	
	@Override
	public double findDeliverPriceById(long campaignId) {
		return this.storeCampaignDao.findDeliverPriceById(campaignId);
		
	}
	
	@Override
	public double findRedeemPriceById(String campaignId) {
		return this.storeCampaignDao.findRedeemPriceById(campaignId);
	}
	
	@Override
	public ExecuteResult update(StoreCampaign activity) {
		ExecuteResult result = new ExecuteResult(true, "跟新成功!");
		try {
			int r = this.storeCampaignDao.updateBean(activity);
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
	public ExecuteResult checkIsExpire(String userNumber) {
		ExecuteResult result = new ExecuteResult(true, "SUCCESS", "");
		int r = this.storeCampaignDao.checkIsExpire(userNumber);
		result.setMessage("一共跟新了：" + r + "条数据！");
		return result;
	}
	
	@Override
	@Transactional
	public ExecuteResult selectStore(long campaignId, long[] ids) {
		ExecuteResult result = new ExecuteResult(true, "SUCCESS", "活动初始化门店成功!");
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		try {
			if (ids.length > 0) {
				for (long storeId : ids) {
					this.storeService.joinActivity(storeId, campaignId,
							userNumber);
				}
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("活动初始化门店失败!");
			result.setError_code("CAMPAIGN INIT　STORE FAILED");
			this.logger.error("selectStore:活动创建完成后在选择门店来执行活动时出错啦！",
					e.fillInStackTrace());
			return result;
		}
		return result;
	}
	
	@Override
	public List<StoreCampaign> findActivitiesByUser(String userNumber,
			PageInfo pageinfo) {
		return this.storeCampaignDao.findList(userNumber, pageinfo,
				"StoreCampaign");
	}
	
	@Override
	public boolean updateStatus(long id, String userNumber,
			CampaignStatus status) {
		int a = this.storeCampaignDao.updateStatus(id, userNumber, status);
		return a > 0;
	}
	
	@Override
	public int updateRedeemNumAndLockMbayAndCostMbay(long campaignId,
			String userNumber, double price) {
		return this.storeCampaignDao.updateRedeemNumAndLockMbayAndCostMbay(
				campaignId, userNumber, price);
	}
	
	@Override
	public int updateDeliverNumAndLockMbayAndCostMbay(long campaignId,
			String userNumber, double price) {
		return this.storeCampaignDao.updateDeliverNumAndLockMbayAndCostMbay(
				campaignId, userNumber, price);
	}
	
	@Override
	@Transactional
	public int cancelCampaign(long id) {
		try {
			StoreCampaign campaign = this.storeCampaignDao.getBean(id);
			if (campaign.getStatus() == CampaignStatus.CANCLED) {
				return 0;
			}
			double mbay = campaign.getLockMbay();
			String userNumber = ChannelContext.getSessionChannel()
					.getUserNumber();
			int r = this.storeCampaignDao.updateLockMbay(id, mbay);
			if (r > 0) {
				this.userAccountService.unLockedMbay(userNumber, mbay);
			}
			return this.storeCampaignDao.updateStatus(id, userNumber,
					CampaignStatus.CANCLED);
		} catch (Exception e) {
			this.logger.error("cancelCampaign", e.fillInStackTrace());
		}
		return 0;
	}
	
	@Override
	public int updateLockMbay(long id, double price) {
		return this.storeCampaignDao.updateLockMbay(id, price);
	}
	
	@Override
	public CampaignStatistics statisticCampaign(String usernumber) {
		return this.storeCampaignDao.statisticCampaign(usernumber);
	}
	
	@Override
	public List<StoreCampaign> findAll(String userNumber) {
		return null;
	}
}
