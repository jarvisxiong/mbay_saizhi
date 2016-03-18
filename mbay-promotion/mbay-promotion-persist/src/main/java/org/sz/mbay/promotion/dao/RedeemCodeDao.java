package org.sz.mbay.promotion.dao;

import java.sql.SQLException;
import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.promotion.bean.RedeemCode;
import org.sz.mbay.promotion.enums.RedeemCodeStu;
import org.sz.mbay.promotion.qo.RedeemCodeForm;

/**
 * 兑换码持久类
 * 
 * @author ONECITY
 * 		
 */
public interface RedeemCodeDao extends BaseDao<RedeemCode> {
	
	/**
	 * 修改兑换码状态
	 * 
	 * @param eventnumber
	 * @param code
	 * @param status
	 * @return
	 */
	public int updateCodeStatus(String eventnumber, String code,
			RedeemCodeStu status);
			
	/**
	 * 更具兑换码查询条件返回对应的兑换码集合
	 * 
	 * @param codeForm
	 * @param pageinfo
	 * @return
	 * @throws SQLException
	 */
	public List<RedeemCode> findAllRedeemCodeByEventNumber(
			RedeemCodeForm codeForm, PageInfo pageinfo);
			
	/**
	 * 根据兑换码编号和活动编号 查询兑换码信息
	 * 
	 * @param code
	 * @return
	 */
	public RedeemCode findRedeemCodeByCodeNumber(String code,
			String campaignNumber);
			
	/**
	 * 设置兑换码兑换单号
	 * 
	 * @param code
	 * @param orderNumber
	 * @param campaignNumber
	 */
	public void setRedeemCodeChargeOrderNumber(String code, String orderNumber, String campaignNumber);
	
	/**
	 * 设置兑换码兑换单号和手机号和状态和消耗美贝
	 * 
	 * @param rcode
	 *            兑换码
	 * @param orderNumber
	 *            订单号
	 * @param mobile
	 *            手机号
	 * @param mbayprice
	 *            消耗美贝
	 * @param campaignNumber
	 * 			  活动编号
	 */
	public void setOrderNumAndMobileAndStatusAndMbayPrice(String rcode, String orderNumber,
			String mobile, double mbayprice, String campaignNumber);
			
	/**
	 * 更新兑换码记录（绑定核销码）
	 * 
	 * @param code
	 * @param campaignNumber
	 * @param verificateCode
	 * @return
	 */
	public int updateCodeVerificate(String code, String campaignNumber,
			String verificateCode);
			
	/**
	 * 查看活动下是否有当前核销码
	 * 
	 * @param eventnumber
	 * @param verificateCode
	 * @return
	 */
	public int getCountOfVerificateCode(String eventnumber,
			String verificateCode);
			
	/**
	 * 修改兑换码领取时间
	 * 
	 * @param eventnumber
	 * @param code
	 * @return
	 */
	public int updateCodeGetTime(String eventnumber, String code);
	
	/**
	 * 修改兑换码兑换时间
	 * 
	 * @param eventnumber
	 * @param code
	 * @return
	 */
	public int updateCodeRedeemTime(String eventnumber, String code);
	
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
	public int updateSended(String code, String campaignNumber);
}
