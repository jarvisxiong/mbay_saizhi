package org.sz.mbay.promotion.service;

import java.util.List;

import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.base.wrap.RedeemResponse;
import org.sz.mbay.promotion.bean.RedeemCode;
import org.sz.mbay.promotion.enums.CodeEndType;
import org.sz.mbay.promotion.qo.RedeemCodeForm;
import org.sz.mbay.trafficred.enums.ProductType;

/**
 * @Description: 兑换码接口类
 * @author han.han
 * @date 2014-11-6 下午10:06:58
 * 		
 */
public interface RedeemCodeService {
	
	/**
	 * 根据活动编号生成兑换码
	 * 
	 * @param eventnumber
	 * @param method
	 * @return 兑换码编号
	 */
	public ExecuteResult generateRedeemCode(String eventnumber, String method);
	
	/**
	 * 根据活动编号生成兑换码(手动批量导入)
	 * 
	 * @param campaign
	 * @param code
	 * @return
	 */
	public ExecuteResult generateRedeemCodeByMunual(String campaignNumber,
			List<RedeemCodeForm> list, CodeEndType endType);
			
	/**
	 * 根据活动编号查询活动发放的兑换码
	 * 
	 * @param eventnumber
	 * @return
	 */
	public List<RedeemCode> findAllRedeemCodeByEventNumber(
			RedeemCodeForm codeForm, PageInfo pageInfo);
			
	/**
	 * 根据兑换码编号和活动编号 查询兑换码信息
	 * 
	 * @param code
	 * @return
	 */
	public RedeemCode findRedeemCodeByCodeNumber(String code,
			String campaignNumber);
			
	/**
	 * 充值美贝
	 * 
	 * @return
	 */
	public RedeemResponse codeRedemMbay(String campaignNumber,
			List<RedeemCodeForm> form);
			
	/**
	 * 充值美贝
	 * 
	 * @return
	 */
	public RedeemResponse codeRedemTraffic(RedeemCode redeemCode,
			String mobile);
			
	/**
	 * 更新兑换码记录（绑定核销码）
	 * 
	 * @param code
	 * @param campaignNumber
	 * @param verificateCode
	 * @return
	 */
	public boolean updateCodeVerificate(String code, String campaignNumber,
			String verificateCode);
			
	/**
	 * 查看活动下是否有当前核销码
	 * 
	 * @param eventnumber
	 * @param verificateCode
	 * @return
	 */
	public boolean isExistVerificateCode(String eventnumber,
			String verificateCode);
			
	/**
	 * 根据兑换码、活动编号查询状态
	 * 
	 * @param code
	 * @return
	 */
	public RedeemCode findStatusByCodeAndCampaignNumber(String code,
			String campaignNumber);
			
	/**
	 * 修改赠送状态为已赠送
	 * 
	 * @param code
	 * @param campaignNumber
	 * @return
	 */
	public boolean updateSended(String code, String campaignNumber);
	
	/**
	 * 当产品池余量低于阀值时发送警告短信
	 * 
	 * @param campaignNumber
	 * @param productType
	 */
	public void sendThresholdWarningSms(String campaignNumber,
			ProductType productType);
}
