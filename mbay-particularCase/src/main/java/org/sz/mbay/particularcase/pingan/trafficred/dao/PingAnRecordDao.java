package org.sz.mbay.particularcase.pingan.trafficred.dao;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.particularcase.pingan.trafficred.bean.PingAnRecord;
import org.sz.mbay.particularcase.pingan.trafficred.enums.MbayStatus;

/**
 * 平安
 * @author user
 *
 */
public interface PingAnRecordDao extends BaseDao<PingAnRecord> {
	
	/**
	 * 修改mbayStatus
	 * @param id
	 * @param mbayStatus
	 * @return
	 */
	public int updateMbayStatus(int id, MbayStatus mbayStatus);
}
