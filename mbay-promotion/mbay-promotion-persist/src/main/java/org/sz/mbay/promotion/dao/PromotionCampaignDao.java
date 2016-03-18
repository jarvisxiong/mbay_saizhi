package org.sz.mbay.promotion.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.base.enums.CampaignStep;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.promotion.bean.CampaignStatistics;
import org.sz.mbay.promotion.bean.PromotionCampaign;
import org.sz.mbay.promotion.bean.PromotionCampaignAdvanced;
import org.sz.mbay.promotion.bean.PromotionCampaignTemplate;
import org.sz.mbay.promotion.qo.CampaignForm;

/**
 * @Description: 促销神器dao
 * @author frank.zong
 * @date 2015-1-5 上午11:22:42
 * 		
 */
public interface PromotionCampaignDao extends BaseDao<PromotionCampaign> {
	
	/**
	 * 查询活动列表
	 * 
	 * @param form
	 * @param pageinfo
	 * @return
	 */
	public List<PromotionCampaign> findAllPromotionCampaign(CampaignForm form,
			PageInfo pageinfo);
			
	/**
	 * 根据活动编号和用户编号查询活动基本信息，不包含活动所关联的其他信息
	 * 
	 * @param eventnumber
	 * @param usernumber
	 * @return
	 */
	public PromotionCampaign findPromotionCampaignBaseInfo(String eventnumber);
	
	/**
	 * 修改该活动状态
	 * 
	 * @param eventnumber
	 * @param state
	 * @return
	 */
	public int updateCampaignState(String eventnumber, CampaignStatus state);
	
	/**
	 * 根据用户编号和活动编号查询活动信息，此信息包含活动基本信息以及所对应的策略信息
	 * 
	 * @param eventnumber
	 *            活动编号
	 * @param usernumber
	 *            用户编号
	 * @return
	 */
	public PromotionCampaign findPromotionCampaign(String eventnumber,
			String usernumber);
			
	/**
	 * 根据用户编号和活动编号查询活动是否存在
	 * 
	 * @param eventNumber
	 * @param userNumber
	 * @return
	 */
	public int getCountOfCampaign(String eventNumber, String userNumber);
	
	/**
	 * 根据活动号查询活动所对应的用户编号
	 * 
	 * @param eventnumber
	 *            活动编号
	 * @return 用户编号
	 */
	public String findUserNumberByCampaignNumber(String eventnumber);
	
	/**
	 * 修改活动创建步骤
	 * 
	 * @param eventnumber
	 * @param eventstep
	 * @return
	 */
	public int updateCampaignStep(String eventnumber, CampaignStep eventstep);
	
	/**
	 * 查询促销神器编辑模式信息
	 * 
	 * @param eventNumber
	 * @return
	 */
	public PromotionCampaignTemplate findPromotionCampaignTemplate(
			String eventNumber);
			
	/**
	 * 获取活动创建到第几步
	 * 
	 * @param eventnumber
	 * @param usernumber
	 * @return
	 */
	public CampaignStep findCampaignStep(String eventnumber, String usernumber);
	
	/**
	 * 修改活动日期
	 * 
	 * @return
	 */
	public int updateCampaignDate(String eventnumber, String eventstarttime,
			String eventendtime);
			
	/**
	 * 修改活动概率
	 * 
	 * @return
	 */
	public int updateCampaignRate(String eventnumber, String rate);
	
	/**
	 * 获取当前活动状态
	 * 
	 * @param eventnumber
	 * @param usernumber
	 * @return
	 */
	public CampaignStatus getCampaignState(String eventnumber,
			String usernumber);
			
	/**
	 * 统计活动数量信息
	 * 
	 * @param usernumber
	 * @return
	 */
	public CampaignStatistics statisticCampaign(String usernumber);
	
	/**
	 * 增加活动兑换码送出数量
	 * 
	 * @param campaignNumber
	 */
	public void increaseRcodeSentNum(String campaignNumber);
	
	/**
	 * 增加活动兑换码发放数量
	 * 
	 * @param campaignNumber
	 */
	public void increaseRcodeGotNum(String campaignNumber);
	
	/**
	 * 修改核销码是否开启
	 * 
	 * @param campaignNumber
	 * @param verificate
	 */
	public int updateCampaignVerificate(String campaignNumber,
			boolean verificate);
			
	/**
	 * 修改超出发放兑换码状态
	 * 
	 * @param campaignNumber
	 * @param verificate
	 * @return
	 */
	public int updateCampaignContinuable(String campaignNumber,
			boolean continuable);
			
	/**
	 * 修改是否分享
	 * 
	 * @param campaignNumber
	 * @param share
	 * @return
	 */
	public int updateCampaignShare(String campaignNumber,
			boolean share);
			
	/**
	 * 根据活动编号查询开发者模式信息
	 * 
	 * @param campaignNumber
	 * @return
	 */
	public PromotionCampaignAdvanced findCampaignAdvanced(
			String campaignNumber);
			
	/**
	 * 修改开发者模式
	 * 
	 * @param advanced
	 */
	public int updateCampaignAdvanced(PromotionCampaignAdvanced advanced);
	
	/**
	 * 查询活动开始日期为今天的未开始的活动
	 * 
	 * @return campaignNumber
	 */
	public List<String> findAllPromotionCampaignNumberStartToday();
	
	/**
	 * 查询活动截至日期为今天的未结束的活动
	 * 
	 * @return campaignNumber
	 */
	public List<String> findAllPromotionCampaignNumberOverToday();
	
	/**
	 * 活动产品池及单日上限等置空
	 * 
	 * @param eventnumber
	 */
	public void clearConfig(String eventnumber);
	
	/**
	 * 根据活动编号查询说明信息
	 * 
	 * @param campaignNumber
	 * @return
	 */
	public PromotionCampaign findQueryInfomation(String campaignNumber);
	
	/**
	 * 更改赠送MB
	 * 
	 * @param campaignNumber
	 * @param sendMB
	 * @return
	 */
	public int updateCampaignSendMB(String campaignNumber, int sendMB);
}
