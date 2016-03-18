package org.sz.mbay.promotion.service;

import java.util.List;

import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.base.enums.CampaignStep;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.promotion.bean.CampaignStatistics;
import org.sz.mbay.promotion.bean.PromotionCampaign;
import org.sz.mbay.promotion.bean.PromotionCampaignAdvanced;
import org.sz.mbay.promotion.bean.PromotionCampaignMbay;
import org.sz.mbay.promotion.bean.PromotionCampaignPackage;
import org.sz.mbay.promotion.bean.PromotionCampaignShare;
import org.sz.mbay.promotion.bean.PromotionCampaignTemplate;
import org.sz.mbay.promotion.bean.PromotionProductConfig;
import org.sz.mbay.promotion.qo.CampaignForm;
import org.sz.mbay.trafficred.enums.ProductType;

/**
 * @Description: 促销神器Service层接口
 * @author frank.zong
 * @date 2015-01-05 下午15:48:26
 * 		
 */
public interface PromotionCampaignService {
	
	/**
	 * 添加活动
	 * 
	 * @param bean
	 * @param configs
	 * @return 活动编号
	 */
	public String addCampaign(PromotionCampaign bean,
			List<PromotionProductConfig> configs,
			PromotionCampaignShare shareInfo);
			
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
	 * 查看活动详情
	 * 
	 * @param eventnumber
	 * @return
	 */
	public PromotionCampaign findPromotionCampaign(String eventnumber,
			String usernumber);
			
	/**
	 * 根据活动编号和用户编号 判断是否存在此活动
	 * 
	 * @param eventNumber
	 * @param userNumber
	 * @return
	 */
	public boolean isExistCampaign(String eventNumber, String userNumber);
	
	/**
	 * 查询活动所属用户
	 * 
	 * @param eventNumber
	 * @return 用户编号
	 */
	public String getCampaignBelongsUser(String eventNumber);
	
	/**
	 * 查询活动信息
	 * 
	 * @param eventNumber
	 * @return
	 */
	public PromotionCampaign findPromotionCampaign(String eventNumber);
	
	/**
	 * 取消活动
	 * 
	 * @param eventNumber
	 * @param userNumber
	 */
	public ExecuteResult cancelCampaign(String eventNumber, String userNumber);
	
	/**
	 * 创建促销神器模板
	 * 
	 * @param eventNumber
	 * @param redeemPhotoBack
	 * @param photoBack
	 */
	public void setPromotionCampaignTemplate(
			PromotionCampaignTemplate template);
			
	/**
	 * 查询促销神器模板信息
	 * 
	 * @param eventNumber
	 * @return
	 */
	public PromotionCampaignTemplate findPromotionCampaignTemplate(
			String eventNumber);
			
	/**
	 * 查询促销神器编辑模式信息
	 * 
	 * @param id
	 * @return
	 */
	public PromotionCampaignTemplate findPromotionCampaignTemplate(int modelid);
	
	/**
	 * 获取活动创建到第几步
	 * 
	 * @param eventnumber
	 * @param usernuber
	 * @return
	 */
	public CampaignStep findCampaignStep(String eventnumber, String usernumber);
	
	/**
	 * 更改活动日期
	 * 
	 * @param event
	 *            活动
	 * @return 执行结果
	 */
	public ExecuteResult updateCampaignDate(String eventnumber,
			String eventstarttime, String eventendtime);
			
	/**
	 * 更改概率
	 * 
	 * @param event
	 *            活动
	 * @return 执行结果
	 */
	public ExecuteResult updateCampaignRate(String eventnumber, String rate);
	
	/**
	 * 根据不同状态统计数据
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
	 * 查询活动状态
	 * 
	 * @param campaignNumber
	 * @return
	 */
	public CampaignStatus findCampaignStatus(String campaignNumber,
			String userNumber);
			
	/**
	 * 修改核销码开启状态
	 * 
	 * @param campaignNumber
	 * @param verificate
	 * @return
	 */
	public boolean updateCampaignVerificate(String campaignNumber,
			boolean verificate);
			
	/**
	 * 修改超出发放兑换码状态
	 * 
	 * @param campaignNumber
	 * @param verificate
	 * @return
	 */
	public boolean updateCampaignContinuable(String campaignNumber,
			boolean continuable);
	
	/**
	 * 修改是否分享状态
	 * 
	 * @param campaignNumber
	 * @param share
	 * @return
	 */
	public boolean updateCampaignShare(String campaignNumber,
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
	 * 创建开发者模式
	 * 
	 * @param advanced
	 */
	public ExecuteResult addCampaignAdvanced(
			PromotionCampaignAdvanced advanced);
			
	/**
	 * 修改开发者模式
	 * 
	 * @param advanced
	 */
	public ExecuteResult updateCampaignAdvanced(
			PromotionCampaignAdvanced advanced);
			
	/**
	 * 为活动添加流量产品
	 * 
	 * @param redpackages
	 */
	public void addTrafficProducts(String campaignNumber,
			List<PromotionCampaignPackage> redpackages,
			PromotionProductConfig config);
			
	/**
	 * 为活动添加MB产品
	 * 
	 * @param mbayProducts
	 */
	public void addMbayProducts(String campaignNumber,
			List<PromotionCampaignMbay> mbayProducts,
			PromotionProductConfig config);
			
	/**
	 * 删除对应的活动产品
	 * 
	 * @param campaignNumber
	 *            活动编号
	 * @param ptype
	 *            产品类型
	 * @param productId
	 * @return
	 */
	public boolean deleteCampaignProduct(String campaignNumber,
			ProductType ptype,
			long productId);
			
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
	public ExecuteResult updateCampaignSendMB(String campaignNumber, int sendMB);
}
