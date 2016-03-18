package org.sz.mbay.particularcase.pingan.trafficred.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.particularcase.pingan.trafficred.bean.PingAnRecord;
import org.sz.mbay.particularcase.pingan.trafficred.dao.PingAnRecordDao;
import org.sz.mbay.particularcase.pingan.trafficred.enums.MbayStatus;
import org.sz.mbay.particularcase.pingan.trafficred.qo.PingAnRecordForm;
import org.sz.mbay.particularcase.pingan.trafficred.service.PingAnRecordService;

@Service
public class PingAnRecordServiceImpl extends BaseServiceImpl implements PingAnRecordService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PingAnRecordServiceImpl.class);
	
	@Autowired
	PingAnRecordDao dao;
	
	@Override
	public List<PingAnRecord> findList(PingAnRecordForm form, PageInfo pageInfo) {
		List<PingAnRecord> list = null;
		try {
			list = dao.findList(form, pageInfo, "PingAnRecord");
		} catch (Exception e) {
			LOGGER.error("PingAnRecordServiceImpl findList Error", e.fillInStackTrace());
		}
		return list;
	}
	
	@Override
	public ExecuteResult add(PingAnRecord bean) {
		try {
			dao.createBean(bean);
		} catch (Exception e) {
			LOGGER.error("PingAnRecordServiceImpl add Error", e.fillInStackTrace());
			return ExecuteResult.failExecute;
		}
		return ExecuteResult.successExecute;
	}
	
	@Override
	public ExecuteResult updateMbayStatus(int id, MbayStatus mbayStatus) {
		return dao.updateMbayStatus(id, mbayStatus) > 0 ? ExecuteResult.successExecute : ExecuteResult.failExecute;
	}
}
