package org.sz.mbay.channel.service;

import java.util.List;

import org.joda.time.DateTime;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.bean.CampaignRedeemCode;
import org.sz.mbay.channel.bean.StoreCampaign;
import org.sz.mbay.channel.bean.StoreOperator;
import org.sz.mbay.channel.wrap.O2OCampaignRedeenDetail;

/**
 * 
 * @ClassName: CampaignRedeemCodeService
 * 
 * @Description: 门店活动兑换码业务逻辑类
 * 
 * @author <a href="mailto:fenlonxiong@gmail.com">Fenlon</a>
 * 
 * @date 2015年1月22日 下午12:54:24
 * 
 */
public interface CampaignRedeemCodeService {
	
	CampaignRedeemCode createRedeemCode(String cellPhone, long storeId,
			StoreCampaign activity);
	
	/**
	 * 根据C的手机号和活动编号查找兑换码
	 * 
	 * @param activityId
	 * @param cellPhone
	 * @return
	 */
	boolean findRedeemCode(String activityId, String cellPhone);
	
	/**
	 * 判断活动是否支持发放兑换码
	 * 
	 * @param activity
	 * @param cellPhone
	 * @return
	 */
	ExecuteResult judgeValidate(StoreCampaign activity, String cellPhone);
	
	/**
	 * 根据兑换码找到兑换码对象(完整信息)
	 * 
	 * @param code
	 *            兑换码
	 * @return
	 */
	CampaignRedeemCode findRedeemCode(String code);
	
	/**
	 * 兑换码兑换
	 * 
	 * @param code
	 *            兑换码
	 * @param operator
	 *            操作员
	 * @return
	 */
	ExecuteResult redeem(CampaignRedeemCode code, StoreOperator operator);
	
	/**
	 * 查询兑换记录
	 * 
	 * @param redeemStoreId
	 *            兑换门店
	 * @param pageInfo
	 *            分页信息
	 * @return
	 */
	List<CampaignRedeemCode> findRedeemRecord(long redeemStoreId, PageInfo pageInfo);
	
	/**
	 * 查询活动兑换详细
	 * 
	 * @param campaignId
	 *            活动ID
	 * @param userNumber
	 *            商家编号
	 * @param pageInfo
	 *            分页信息
	 * @return
	 */
	List<O2OCampaignRedeenDetail> queryRedeemDetail(long campaignId, String userNumber, PageInfo pageInfo);
	
	/**
	 * 根据条件查询活动兑换信息
	 * 
	 * @param campaignId
	 *            活动ID
	 * @param userNumber
	 *            用户编号
	 * @param start
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @param cellPhone
	 *            手机号
	 * @param pageInfo
	 *            分页信息
	 * @return
	 */
	List<O2OCampaignRedeenDetail> queryRedeemDetailByCondition(long campaignId, String userNumber, DateTime start, DateTime end, String cellPhone, PageInfo pageInfo);
}
