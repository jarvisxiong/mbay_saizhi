package org.sz.mbay.wechat.service;

import java.util.List;

import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.base.enums.CampaignStep;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.base.wrap.RedeemResponse;
import org.sz.mbay.wechat.bean.WeChatCampaign;
import org.sz.mbay.wechat.bean.WeChatCampaignAdvanced;
import org.sz.mbay.wechat.bean.WeChatCampaignAdvertise;
import org.sz.mbay.wechat.bean.WeChatCampaignDefaultTemplate;
import org.sz.mbay.wechat.bean.WeChatCampaignStatistics;
import org.sz.mbay.wechat.bean.WeChatCampaignTemplate;
import org.sz.mbay.wechat.qo.WeChatCampaignForm;

/**
 * @Description: 微信伴侣Service层接口
 * @author frank.zong
 * @date 2015-01-05 下午15:48:26
 *
 */
public interface WeChatCampaignService {

	/**
	 * 添加活动
	 * @param bean
	 * @return
	 */
	public String addCampaign(WeChatCampaign bean);
	
	/**
	 * 创建开发者模式
	 * @param eventnumber
	 */
	public ExecuteResult addCampaignAdvanced(WeChatCampaignAdvanced advanced);
	
	/**
	 * 修改开发者模式
	 * @param eventnumber
	 */
	public ExecuteResult updateCampaignAdvanced(WeChatCampaignAdvanced advanced);
	
	/**
	 * 查询所有活动
	 * @param bean
	 * @param pageinfo
	 * @return
	 */
	public List<WeChatCampaign> findAllWeChatCampaign(WeChatCampaignForm form,PageInfo pageinfo);

	/**
	 * 查看活动详情
	 * @param eventnumber
	 * @return
	 */
	public WeChatCampaign findWeChatCampaign(String eventnumber,String usernumber);

	/**
	 * 根据id查询模板
	 * @param id
	 * @return
	 */
	public WeChatCampaignTemplate findCampaignTemplate(int id);
	
	/**
	 * 根据活动编号查询开发者模式信息
	 * @param campaignNumber
	 * @return
	 */
	public WeChatCampaignAdvanced findCampaignAdvanced(String campaignNumber);
	
	/**
	 * 根据id查询eventDefaultTemplate
	 * @param id
	 * @return
	 */
	public WeChatCampaignDefaultTemplate findWeChatCampaignDefaultTemplateById(long id);

	/**
	 * 根据活动编号查询模板信息
	 * @param eventnumber
	 * @return
	 */
	public WeChatCampaignTemplate findCampaignTemplate(String eventnumber);

	/**
	 * 更新模板
	 * @param bean
	 * @return
	 */
	public boolean updateCampaignTemplate(WeChatCampaignTemplate template);
	
	/**
	 * 取消用户指定的活动
	 * @param eventnumber
	 * @param usernumber
	 * @return
	 */
	public ExecuteResult cancelCampaign(String eventnumber,String usernumber);

	/**
	 * 设置模板为用户自定义
	 * @param eventumber
	 * @param template
	 */
	public void setCampaignTemplateWithCustom(String eventumber,WeChatCampaignTemplate template);
	
	/**获取活动创建到第几步
	 * @param eventnumber
	 * @param usernuber
	 * @return
	 */
	public CampaignStep findCampaignStep(String eventnumber,String usernuber);

	/**
	 * 活动赠送数量
	 * @param eventnumber
	 * @return
	 */
	public int getCampaignSendQuantity(String eventnumber);

	/**
	 * 活动已送出数量
	 * @param eventnumber
	 * @return
	 */
	public int findCampaignSendedQuantity(String eventnumber);

	/**
	 * 修改活动，单个号码是否可多次参与
	 * @param eventnumber
	 * @param repeat_enable
	 * @return
	 */
	public boolean updateCampaignRepeatEnable(String eventnumber,boolean repeat_enable);
	
	/**
	 * 更改活动日期
	 * @param event 活动
	 * @return  执行结果
	 */
	public ExecuteResult updateCampaignDate(String eventnumber,String eventstarttime, String eventendtime);

	/**
	 * 修改活动超出数量是否可继续兑换
	 * 
	 * @param eventnumber
	 * @param continuable
	 * @return
	 */
	public boolean updateCampaignContinuable(String eventnumber, boolean continuable);
	
	/**
	 * 修改活动是否可直接领取
	 * 
	 * @param eventnumber
	 * @param directEnable
	 * @return
	 */
	public boolean updateCampaignDirectEnable(String eventnumber, boolean directEnable);
	
	/**
	 * 查询所有模板
	 * @return
	 */
	public List<WeChatCampaignDefaultTemplate> findWeChatCampaignDefaultTemplateList();
	
	/**
	 * 查询所有广告图片
	 * @return
	 */
	public List<WeChatCampaignAdvertise> findWeChatCampaignAdvertiseList();
	
	/**
	 * 根据不同状态统计数据
	 * @param usernumber
	 * @return
	 */
	public WeChatCampaignStatistics statisticWeChatCampaign(String usernumber);
	
	/**
	 * 判断对应用户编号的活动编号是否存在
	 * @param eventNumber
	 * @param userNumber
	 * @return
	 */
	public boolean isExistingCampaign(String eventNumber,String userNumber);
	
	/**
	 * 判断未完善的活动是否可继续完善
	 * @param eventNumber
	 * @return
	 */
	public ExecuteResult isFeasibleForToComplete(String eventNumber);
	
	
	/**
	 * 查询活动当前状态
	 * @param campaignStatus
	 * @return
	 */
	public CampaignStatus findCampaignStatus(String campaignNumber);
	
	/**
	 * 查询活动是否可直接领取
	 * @param campaignNumber
	 * @return
	 */
	public boolean findCampaignDirectEnable(String campaignNumber);
	
	/**
	 * 活动流量充值(微信伴侣)
	 * 
	 * @param campaignNumber
	 * @param mobile
	 * @return
	 */
	public RedeemResponse rechargeTraffic(String campaignNumber, String mobile);
	
	/**
	 * 钱包流量充值(微信伴侣)
	 * 
	 * @param campaignNumber
	 * @param mobile
	 * @return
	 */
	public RedeemResponse rechargeTrafficByWallet(String campaignNumber, String mobile);
}