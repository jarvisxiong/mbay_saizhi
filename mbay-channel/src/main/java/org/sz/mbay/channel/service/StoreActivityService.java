package org.sz.mbay.channel.service;

import java.util.List;

import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.bean.StoreCampaign;
import org.sz.mbay.promotion.bean.CampaignStatistics;

public interface StoreActivityService {
	
	/**
	 * 查询用户所有活动
	 * 
	 * @param userNumber
	 * @return
	 */
	List<StoreCampaign> finaAllByUser(String userNumber);
	
	/**
	 * 创建活动
	 * 
	 * @param activity
	 * @param userNumber
	 * @return
	 */
	public StoreCampaign createActivity(StoreCampaign activity,
			String userNumber);
	
	/**
	 * 删除活动
	 * 
	 * @param id
	 * @param userNumber
	 * @return
	 */
	boolean delete(long id, String userNumber);
	
	/**
	 * 查找活动
	 * 
	 * @param campaignId
	 * @return
	 */
	StoreCampaign findCampaignById(long campaignId);
	
	/**
	 * 查询发放兑换码平台收取费用
	 * 
	 * @param campaignId
	 * @return
	 */
	double findDeliverPriceById(long campaignId);
	
	/**
	 * 查询兑换兑换码平台收取费用
	 * 
	 * @param campaignId
	 * @return
	 */
	double findRedeemPriceById(String campaignId);
	
	/**
	 * 跟新活动
	 * 
	 * @param activity
	 * @return
	 */
	ExecuteResult update(StoreCampaign activity);
	
	/**
	 * 检查活动是否过期
	 * 
	 * @param userNumber
	 * @return
	 */
	ExecuteResult checkIsExpire(String userNumber);
	
	/**
	 * 选择门店
	 * 
	 * @param campaignId
	 *            活动ID
	 * @param ids
	 *            选择的门店IDS
	 * @return
	 */
	ExecuteResult selectStore(long campaignId, long[] ids);
	
	/**
	 * 根据用户编号找到所有活动并分页
	 * 
	 * @param userNumber
	 * @param info
	 * @return
	 */
	List<StoreCampaign> findActivitiesByUser(String userNumber, PageInfo info);
	
	/**
	 * 跟新活动状态
	 * 
	 * @param id
	 * @param userNumber
	 * @param status
	 * @return
	 */
	boolean updateStatus(long id, String userNumber, CampaignStatus status);
	
	/**
	 * 跟新兑换的兑换码数目和锁定美贝
	 * 
	 * @param id
	 * @param userNumber
	 * @param price
	 * @return
	 */
	int updateRedeemNumAndLockMbayAndCostMbay(long id, String userNumber, double price);
	
	/**
	 * 跟新发放兑换码数目和锁定美贝,以及消耗的美贝,锁定的较少的数额和消耗的增加的数额为同值
	 * 
	 * @param campaignId
	 *            活动ID
	 * @param userNumber
	 *            用户编号
	 * @param price
	 *            消耗和解锁美贝
	 * @return
	 */
	int updateDeliverNumAndLockMbayAndCostMbay(long campaignId, String userNumber, double price);
	
	/**
	 * 取消活动
	 * 
	 * @param id
	 * @return
	 */
	int cancelCampaign(long id);
	
	/**
	 * 跟新锁定美贝值
	 * 
	 * @param id
	 * @param price
	 * @return
	 */
	int updateLockMbay(long id, double price);
	
	/**
	 * 统计活动
	 * 
	 * @param usernumber
	 * @return
	 */
	CampaignStatistics statisticCampaign(String usernumber);
	
	/**
	 * 查询所有的门店活动
	 * 
	 * @param userNumber
	 * @return
	 */
	List<StoreCampaign> findAll(String userNumber);
}
