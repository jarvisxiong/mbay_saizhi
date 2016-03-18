package org.sz.mbay.customer.service;

import java.util.List;

import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.customer.bean.BatchChargeInfo;
import org.sz.mbay.customer.bean.BatchChargeItem;
import org.sz.mbay.customer.bean.BatchChargeMobile;
import org.sz.mbay.customer.qo.BatchChargeMobileForm;
import org.sz.mbay.operator.enums.OperatorType;

public interface CustomerServerService {
	
	/**
	 * 添加流量批充信息
	 * 
	 * @param batchinfo
	 * @return
	 */
	public ExecuteResult addBatchChargeInfo(BatchChargeInfo batchinfo);
	
	public List<BatchChargeInfo> findAllBatchChargeInfo(BatchChargeInfo chargeinfo, PageInfo pageinfo);
	
	public String getChargeInfoExcelURL(int uid);
	
	/**
	 * 批充手机号码信息
	 * 
	 * @param bid
	 * @param pageinfo
	 * @return
	 */
	public List<BatchChargeMobile> findBatchChargeMobileInfo(BatchChargeMobileForm chargeMobileForm, PageInfo pageinfo);
	
	/**
	 * 根据id和usernumber查询批充信息
	 * 
	 * @param batchid
	 * @param usernumber
	 * @return
	 */
	public BatchChargeInfo findBatchChargeInfo(int batchid, String usernumber);
	
	/**
	 * 根据批充信息ID流量批充
	 * 
	 * @param batchid
	 * @param sms
	 *            是否短信提醒
	 */
	public ExecuteResult batchCharge(int batchid, boolean sms, String usernumber);
	
	public List<BatchChargeItem> findBatchChargeItems(int batchid, PageInfo pageinfo);
	
	/**
	 * 判断是否存在批充信息
	 * 
	 * @param batchid
	 * @param usernumber
	 * @return
	 */
	public boolean isExistingBatchCharge(int batchid, String usernumber);
	
	/**
	 * 删除指定批充信息手机号码
	 * 
	 * @param batchid
	 * @param mobile
	 * @param operator
	 * @return
	 */
	public boolean deleteChargeMobile(int batchid, String mobile, OperatorType operator);
	
	/**
	 * @param mobile
	 *            void
	 * @author tom
	 * @time 2014-9-11下午7:53:16
	 */
	public void addBatchChargeMobile(BatchChargeMobile mobile);
	
}