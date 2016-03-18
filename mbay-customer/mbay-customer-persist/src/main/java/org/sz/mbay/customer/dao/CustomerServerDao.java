package org.sz.mbay.customer.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.customer.bean.BatchChargeInfo;
import org.sz.mbay.customer.bean.BatchChargeItem;
import org.sz.mbay.customer.bean.BatchChargeMobile;
import org.sz.mbay.customer.qo.BatchChargeMobileForm;
import org.sz.mbay.operator.bean.TrafficPackage;
import org.sz.mbay.operator.enums.OperatorType;

public interface CustomerServerDao extends BaseDao<BatchChargeInfo> {
	
	public String getChargeInfoExcelURL(int uid);
	
	/**
	 * 根据ID和UNumber查询批充信息
	 * 
	 * @param id
	 * @param usernumber
	 * @return
	 */
	public BatchChargeInfo findBatchChargeInfo(int id, String usernumber);
	
	/**
	 * 增加批充次数
	 * 
	 * @param infoid
	 *            批充id
	 * @return
	 */
	public int increaseBatchChargeTimes(int infoid);
	
	/**
	 * 批充手机信息
	 * 
	 * @param bid
	 * @param usernumber
	 * @return
	 */
	public List<BatchChargeMobile> findBatchChargeMobileInfo(BatchChargeMobileForm chargeMobileForm, PageInfo pageinfo);
	
	public List<BatchChargeMobile> findChargeMobilesByBatchId(int batchid);
	
	/**
	 * 设置批充消耗美贝
	 * 
	 * @param id
	 * @param costmbay
	 */
	public void setBatchChargeCostmbay(int id, double costmbay);
	
	/**
	 * 删除指定批充手机号码
	 * 
	 * @param batchid
	 * @param mobile
	 * @param usernumber
	 * @return
	 */
	public int deleteChargeMobile(int batchid, String mobile);
	
	/**
	 * 增加批充信息手机号码数量
	 * 
	 * @param infoid
	 * @return
	 */
	public void increaseBatchChargeMobileNum(int infoid);
	
	/**
	 * 减少批充号码数量
	 * 
	 * @param infoid
	 * 
	 */
	public void reduceBatchChargeMobileNum(int infoid);
	
	/**
	 * 保存批充记录
	 * 
	 * @param batchid
	 */
	public void saveBatchChargeItem(BatchChargeItem bean);
	
	/**
	 * 判断是否存在批充信息
	 * 
	 * @param batchid
	 * @param usernumber
	 * @return
	 */
	public boolean isExistingBatchCharge(int batchid, String usernumber);
	
	/**
	 * 查询流量包相关信息
	 * 
	 * @param batchid
	 * 			批充id
	 * @param operator
	 * 			运营商类型
	 * @return
	 */
	public TrafficPackage findTrafficPackage(int batchid, OperatorType operator);
	
	/**
	 * 增加批充信息消耗美贝
	 * 
	 * @param batchid
	 * 			批充id
	 * @param mbayprice
	 * 			增加的消耗美贝
	 */
	public void increaseBatchChargeInfoCostMbay(int batchid,double mbayprice);
	
	/**
	 * 减少批充信息消耗美贝
	 * 
	 * @param batchid
	 * 			批充id
	 * @param mbayprice
	 * 			减少的消耗美贝
	 */
	public void reduceBatchChargeInfoCostMbay(int batchid,double mbayprice);
}