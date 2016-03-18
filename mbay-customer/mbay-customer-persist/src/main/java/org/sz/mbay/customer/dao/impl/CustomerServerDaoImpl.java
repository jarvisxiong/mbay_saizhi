package org.sz.mbay.customer.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.customer.bean.BatchChargeInfo;
import org.sz.mbay.customer.bean.BatchChargeItem;
import org.sz.mbay.customer.bean.BatchChargeMobile;
import org.sz.mbay.customer.dao.CustomerServerDao;
import org.sz.mbay.customer.qo.BatchChargeMobileForm;
import org.sz.mbay.operator.bean.TrafficPackage;
import org.sz.mbay.operator.enums.OperatorType;

@Repository
public class CustomerServerDaoImpl extends BaseDaoImpl<BatchChargeInfo> implements CustomerServerDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServerDaoImpl.class);
	
	@Override
	public String getChargeInfoExcelURL(int id) {
		return super.template.selectOne("fincChargeInfoExcelURL", id);
	}
	
	@Override
	public BatchChargeInfo findBatchChargeInfo(int id, String unumber) {
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("id", id);
		maps.put("usernumber", unumber);
		return super.template.selectOne("findBatchChargeInfo", maps);
	}
	
	@Override
	public List<BatchChargeMobile> findChargeMobilesByBatchId(int batchid) {
		return this.template.selectList("findChargeMobilesByBatchId", batchid);
	}
	
	@Override
	public int increaseBatchChargeTimes(int infoid) {
		return this.template.update("increaseBatchChargeTimes", infoid);
	}
	
	@Override
	public List<BatchChargeMobile> findBatchChargeMobileInfo(BatchChargeMobileForm chargeMobileForm, PageInfo pageinfo) {
		return super.findList(chargeMobileForm, pageinfo, "BatchChargeMobileInfo");
	}
	
	@Override
	public void setBatchChargeCostmbay(int id, double costmbay) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("costmbay", costmbay);
		this.template.update("setBatchChargeCostmbay", map);
		
	}
	
	@Override
	public int deleteChargeMobile(int batchid, String mobile) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("batchid", batchid);
		map.put("mobile", mobile);
		return this.template.delete("deletChargeMobile", map);
	}
	
	@Override
	public void increaseBatchChargeMobileNum(int batchid) {
		this.template.update("increaseBatchChargeMobileNum", batchid);
	}
	
	@Override
	public void reduceBatchChargeMobileNum(int batchid) {
		this.template.update("reduceBatchChargeMobileNum", batchid);
	}
	
	@Override
	public void saveBatchChargeItem(BatchChargeItem bean) {
		try {
			super.createBean(bean);
		} catch (Exception e) {
			LOGGER.error("CustomerServerDaoImpl saveBatchChargeItem Error", e.fillInStackTrace());
		}
	}
	
	@Override
	public boolean isExistingBatchCharge(int batchid, String usernumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("batchid", batchid);
		map.put("usernumber", usernumber);
		return (Boolean) this.template.selectOne("isExistingBatchCharge", map);
	}
	
	@Override
	public TrafficPackage findTrafficPackage(int batchid, OperatorType operator) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("batchid", batchid);
		map.put("operator", operator);
		return this.template.selectOne("findTrafficPackageByBatchChargeStrategy", map);
	}
	
	@Override
	public void increaseBatchChargeInfoCostMbay(int batchid,double mbayprice) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", batchid);
		map.put("costmbay", mbayprice);
		this.template.update("increaseBatchChargeCostmbay", map);
	}
	
	@Override
	public void reduceBatchChargeInfoCostMbay(int batchid,double mbayprice) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", batchid);
		map.put("costmbay", mbayprice);
		this.template.update("reduceBatchChargeCostmbay", map);
	}
}