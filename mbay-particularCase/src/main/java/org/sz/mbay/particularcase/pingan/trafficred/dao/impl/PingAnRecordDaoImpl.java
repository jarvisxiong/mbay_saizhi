package org.sz.mbay.particularcase.pingan.trafficred.dao.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.particularcase.pingan.trafficred.bean.PingAnRecord;
import org.sz.mbay.particularcase.pingan.trafficred.dao.PingAnRecordDao;
import org.sz.mbay.particularcase.pingan.trafficred.enums.MbayStatus;

@Repository
public class PingAnRecordDaoImpl extends BaseDaoImpl<PingAnRecord> implements PingAnRecordDao {

	@Override
	public int updateMbayStatus(int id, MbayStatus mbayStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("mbayStatus", mbayStatus);
		return template.update("updateMbayStatus", map);
	}
}
