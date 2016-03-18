package org.sz.mbay.wechat.dao;

import java.util.List;

import org.sz.mbay.base.area.Area;
import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.base.enums.CampaignStep;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.wechat.bean.WeChatCampaignAdvertise;
import org.sz.mbay.wechat.bean.WeChatCampaignDefaultTemplate;
import org.sz.mbay.wechat.bean.WeChatCampaign;
import org.sz.mbay.wechat.bean.WeChatCampaignAdvanced;
import org.sz.mbay.wechat.bean.WeChatCampaignStatistics;
import org.sz.mbay.wechat.bean.WeChatCampaignStrategy;
import org.sz.mbay.wechat.bean.WeChatCampaignTemplate;
import org.sz.mbay.wechat.qo.WeChatCampaignForm;

/**
 * @Description: 微信伴侣dao
 * @author frank.zong
 * @date 2015-1-5 上午11:22:42
 * 
 */
public interface WeChatCampaignDao extends BaseDao<WeChatCampaign> {

	/**
	 * 创建活动策略
	 * 
	 * @param startegy
	 */
	public void createCampaignStrategy(WeChatCampaignStrategy startegy);

	/**
	 * 根据用户编号和活动编号查询活动信息，此信息包含活动基本信息以及所对应的策略信息
	 * 
	 * @param eventnumber
	 * @param usernumber
	 * @return
	 */
	public WeChatCampaign findWeChatCampaignByNumber(String eventnumber,
			String usernumber);

	/**
	 * 根据活动编号和用户编号查询活动基本信息，不包含活动所关联的其他信息
	 * 
	 * @param eventnumber
	 * @param usernumber
	 * @return
	 */
	public WeChatCampaign findWeChatCampaignBaseInfo(String eventnumber);
	
	/**
	 * 根据活动编号查询开发者模式信息
	 * 
	 * @param campaignNumber
	 * @return
	 */
	public WeChatCampaignAdvanced findCampaignAdvanced(String campaignNumber);
	
	/**
	 * 更新开发者模式信息
	 * 
	 * @param campaignNumber
	 * @return
	 */
	public int updateCampaignAdvanced(WeChatCampaignAdvanced advanced);

	/**
	 * 设置活动锁定美贝
	 * 
	 * @param eventnumber
	 * @param maxunitprice
	 * @return
	 */
	public void setCampaignLockedMbay(String eventnumber, double maxunitprice)
			throws Exception;

	/**
	 * 根据活动编号查询活动预计赠送份数
	 * 
	 * @param eventnumber
	 * @return
	 */
	public int getCampaignSendQuantity(String eventnumber);

	/**
	 * 获取当前活动状态
	 * 
	 * @param eventnumber
	 * @param usernumber
	 * @return
	 */
	public CampaignStatus getCampaignState(String eventnumber, String usernumber);

	/**
	 * 调用存储过程取消活动
	 * 
	 * @param eventnumber
	 *            活动编号
	 * @param usernumber
	 *            用户编号
	 * @return
	 */
	public int cancelCampaign(String eventnumber, String usernumber);

	/**
	 * 查询活动列列表
	 * 
	 * @param event
	 * @param pageinfo
	 * @return
	 */
	public List<WeChatCampaign> findAllWeChatCampaign(WeChatCampaignForm event,
			PageInfo pageinfo);

	/**
	 * 修改活动创建步骤
	 * 
	 * @param eventnumber
	 * @param eventstep
	 * @return
	 */
	public int updateCampaignStep(String eventnumber, CampaignStep eventstep);

	/**
	 * 修改该活动状态
	 * 
	 * @param eventnumber
	 * @param state
	 * @return
	 */
	public int updateCampaignState(String eventnumber, CampaignStatus state);

	/**
	 * 查询当前活动场创建到第几步
	 * 
	 * @param eventnumber
	 * @param usernuber
	 * @return
	 */
	public CampaignStep findCampaignStep(String eventnumber, String usernuber);

	/**
	 * 根据活动编号查询模板信息
	 * 
	 * @param eventnumber
	 * @return
	 */
	public WeChatCampaignTemplate findCampaignTemplate(String eventnumber);

	/**
	 * 查询活动当前已送出数量
	 * 
	 * @param eventnumber
	 * @return
	 */
	public int findCampaignSendedQuantity(String eventnumber);

	/**
	 * 修改活动，单个号码是否可多次参与
	 * 
	 * @param eventnumber
	 * @param repeat_enable
	 * @return
	 */
	public int updateCampaignRepeatEnable(String eventnumber, boolean repeat_enable);

	/**
	 * 修改活动日期
	 * 
	 * @return
	 */
	public int updateCampaignDate(String eventnumber, String eventstarttime,
			String eventendtime);

	/**
	 * 完成活动创建，调用存储过程完成初始化
	 * 
	 * @param eventnumber
	 * @return
	 */
	public int completeCampaignCreate(String eventnumber);

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
	 * 修改活动超出数量是否可继续兑换
	 * 
	 * @param eventnumber
	 * @param continuable
	 * @return
	 */
	public int updateCampaignContinuable(String eventnumber, boolean continuable);
	
	/**
	 * 修改活动是否可直接领取
	 * 
	 * @param eventnumber
	 * @param directEnable
	 * @return
	 */
	public int updateCampaignDirectEnable(String eventnumber, boolean directEnable);

	/**
	 * 查询所有模板
	 * 
	 * @return
	 */
	public List<WeChatCampaignDefaultTemplate> findWeChatCampaignDefaultTemplateList();

	/**
	 * 查询所有广告图片
	 * 
	 * @return
	 */
	public List<WeChatCampaignAdvertise> findWeChatCampaignAdvertiseList();

	/**
	 * 根据不同状态统计数据
	 * 
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
	 * 获得活动初始化锁定美贝=预计发放数量
	 * @param campaignNumber
	 * @return
	 */
	public double getCampaignMaxPackegePrice(String campaignNumber);
	
	/**
	 * 判断是否为默认图片
	 * @param template
	 */
	public Boolean isDefault(String picture);
	
	/**
	 * 根据活动编号查询活动当前状态
	 * @param campaignNumber
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
	 * 根据活动编号查询活动赠送方式
	 * 
	 * @param eventnumber
	 * @return
	 */
	public WeChatCampaign findWeChatEventSendInfo(String eventnumber);
	
	/**
	 * 根据活动编号，和号码归属地信息查询对应的活动流量包
	 * 
	 * @param eventnumber
	 * @param area
	 * @param operator
	 * @return
	 */
	public WeChatCampaignStrategy findEventStrategyTrafficInfo(
			String eventnumber, Area area, OperatorType operator);
	
	/**
	 * 增加策略送出数量 并增加活动送出数量，减少活动锁定美贝，增加活动送出美贝
	 * @param strategyNumber
	 * @return
	 */
	public int weChatCampaignSend(String strategyNumber);
	
	/**
	 * 判断当前号码是否可以参与当前活动
	 * 
	 * @param eventnumber
	 * @param mobile
	 * @return
	 */
	public ExecuteResult judgeMobileReceiveEnable(String eventnumber,
			String mobile);
	
	/**
	 * 增加活动和策略送出数量，减少活动和用户锁定的美贝
	 * 
	 * @param strategyNumber
	 *            策略编号
	 * @return
	 */
	public int increEventSentAnaReduLocked(String strategyNumber);
	
	/**
	 * 查询活动开始日期为今天的未开始的活动
	 * @return campaignNumber
	 */
	public List<String> findAllWeChatCampaignNumberStartToday();
	
	/**
	 * 查询活动截至日期为今天的未结束的活动
	 * @return campaignNumber
	 */
	public List<String> findAllWeChatCampaignNumberOverToday();
	
	/**
	 * 解锁活动锁定美贝
	 * 
	 * @param eventnumber
	 */
	public void decreaseCampaignLockedMbay(String eventnumber);
}