package org.sz.mbay.promotion.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.promotion.bean.RedeemCode;
import org.sz.mbay.promotion.dao.RedeemCodeDao;
import org.sz.mbay.promotion.enums.RedeemCodeStu;
import org.sz.mbay.promotion.qo.RedeemCodeForm;

@Repository
public class RedeemCodeDaoImpl extends BaseDaoImpl<RedeemCode>
		implements RedeemCodeDao {
	
	@Override
	public int updateCodeStatus(String eventnumber, String code,
			RedeemCodeStu status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("eventnumber", eventnumber);
		map.put("status", status);
		return super.template.update("updateCodeStatus", map);
	}
	
	@Override
	public List<RedeemCode> findAllRedeemCodeByEventNumber(
			RedeemCodeForm codeForm, PageInfo pageinfo) {
		return super.findList(codeForm, pageinfo, "RedeemCodeByEventNumber");
	}
	
	@Override
	public RedeemCode findRedeemCodeByCodeNumber(String code,
			String campaignNumber) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("code", code);
		map.put("campaignNumber", campaignNumber);
		return this.template.selectOne("findRedeemCode", map);
	}
	
	@Override
	public void setRedeemCodeChargeOrderNumber(String code,
			String orderNumber, String campaignNumber) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("redeemCode", code);
		map.put("orderNumber", orderNumber);
		map.put("codeStatus", RedeemCodeStu.REDEEMED.ordinal() + "");
		map.put("campaignNumber", campaignNumber);
		this.template.update("setRedeemCodeChargeOrderNumber", map);
	}
	
	@Override
	public void setOrderNumAndMobileAndStatusAndMbayPrice(String rcode, String orderNumber,
			String mobile, double mbayprice, String campaignNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("redeemCode", rcode);
		map.put("orderNumber", orderNumber);
		map.put("mobile", mobile);
		map.put("mbayprice", mbayprice);
		map.put("campaignNumber", campaignNumber);
		this.template.update("updateOrderNumAndMobileAndStatusAndMbayPrice", map);
	}
	
	@Override
	public int updateCodeVerificate(String code, String campaignNumber,
			String verificateCode) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("code", code);
		map.put("campaignNumber", campaignNumber);
		map.put("verificateCode", verificateCode);
		return this.template.update("updateCodeVerificate", map);
	}
	
	@Override
	public int getCountOfVerificateCode(String eventnumber,
			String verificateCode) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("eventnumber", eventnumber);
		map.put("verificateCode", verificateCode);
		return this.template.selectOne("getCountOfVerificateCode", map);
	}
	
	@Override
	public int updateCodeGetTime(String eventnumber, String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("eventnumber", eventnumber);
		map.put("getTime", DateTime.now());
		return super.template.update("updateCodeGetTime", map);
	}
	
	@Override
	public int updateCodeRedeemTime(String eventnumber, String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("eventnumber", eventnumber);
		map.put("redeemTime", DateTime.now());
		return super.template.update("updateCodeRedeemTime", map);
	}
	
	@Override
	public RedeemCode findStatusByCodeAndCampaignNumber(String code,
			String campaignNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("campaignNumber", campaignNumber);
		return this.template.selectOne("findStatusByCodeAndCampaignNumber",
				map);
	}
	
	@Override
	public int updateSended(String code, String campaignNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("campaignNumber", campaignNumber);
		return super.template.update("updateSended", map);
	}
}
