package org.sz.mbay.particularcase.pingan.trafficred.service;

import java.util.List;

import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.particularcase.pingan.trafficred.bean.PingAnRecord;
import org.sz.mbay.particularcase.pingan.trafficred.enums.MbayStatus;
import org.sz.mbay.particularcase.pingan.trafficred.qo.PingAnRecordForm;

/**
 * 平安Service
 * 
 * @author frank.zong
 *		
 */
public interface PingAnRecordService {
	
	/**
	 * 根据条件查询记录
	 * 
	 * @param form
	 * @param pageInfo
	 * @return
	 */
	public List<PingAnRecord> findList(PingAnRecordForm form, PageInfo pageInfo);
	
	/**
	 * 添加记录
	 * 
	 * @param bean
	 */
	public ExecuteResult add(PingAnRecord bean);
	
	/**
	 * 更新mbayStatus
	 * 
	 * @param id
	 * @param mbayStatus
	 */
	public ExecuteResult updateMbayStatus(int id, MbayStatus mbayStatus);
	
}
