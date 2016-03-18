package org.sz.mbay.operator.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.area.Area;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.operator.bean.Operator;
import org.sz.mbay.operator.bean.TrafficPackage;
import org.sz.mbay.operator.dao.OperatorDao;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.operator.enums.TrafficType;

@Repository
public class OperatorDaoImpl extends BaseDaoImpl<Operator> implements
		OperatorDao {

	@Override
	public List<Operator> findAllOperatorByArea(int area) throws Exception {
		return super.findList(area, null, "OperatorByArea");
	}

	@Override
	public TrafficPackage findTrafficPackage(int packageid) throws Exception {
		return super.getBean(packageid, TrafficPackage.class);
	}

	@Override
	public double getMaxPackagePrice(int[] packageids) {
		return this.template.selectOne("getMaxPackagePrice", packageids);
	}

	@Override
	public double getTrafficPackagePrice(int packageid) {
		return this.template.selectOne("getPackagePrice", packageid);
	}

	@Override
	public List<TrafficPackage> findAllTrafficPackage(Area area,
			OperatorType operator, TrafficType trafficType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("area", area);
		map.put("operator", operator);
		map.put("trafficType", trafficType);
		return this.template.selectList("findAllTrafficPackage", map);
	}

	@Override
	public TrafficPackage getBestTrafficPackage(Area area,
			OperatorType operator, int traffic) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("area", area);
			map.put("operator", operator);
			map.put("traffic", traffic);
			return this.template.selectOne("getBestTrafficPackage", map);
		} catch (Exception e) {
			return null;
		}
	}

	//onembay
	@Override
	public Operator findOperatorName(int id) {
		return super.template.selectOne("findOperatorName", id);
	}

	@Override
	public int findCountOperator(String area, String type) {
		Map<String, String> map = new HashMap<>();
		map.put("area", area);
		map.put("type", type);
		return super.template.selectOne("findCountOperator", map);
	}

	@Override
	public void updatePackageCode(int id, String package_code) {
		Map<String, String> map = new HashMap<String,String>();
		map.put("id", String.valueOf(id));
		map.put("packageCode", package_code);
		super.template.update("updatePackageCode",map);
	}
	
	@Override
	public int updateOperatorEnabled(int id) {
		return this.template.update("updateOperatorEnabled", id);
	}

	@Override
	public int updateOperatorDisable(int id) {
		return this.template.update("updateOperatorDisable", id);
	}
	
	@Override
	public int updateEnabled(int id) {
		return this.template.update("updateEnabled", id);
	}

	@Override
	public int updatedisable(int id) {
		return this.template.update("updatedisable", id);
	}

	@Override
	public int updateEnabledByOperatorId(int operatorid) {
		return this.template.update("updateEnabledByOperatorId", operatorid);
	}

	@Override
	public int updatedisableByOperatorId(int operatorid) {
		return this.template.update("updatedisableByOperatorId", operatorid);
	}

	@Override
	public List<TrafficPackage> findByAreaAndOpetor(TrafficType type,
			OperatorType operatorType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("trafficType", type);
		map.put("operatorType", operatorType);
		return this.template.selectList("findAllByAreaAndOpetor", map);
	}

	@Override
	public int getSerialNumber(int operatorid, int traffictype, int traffic) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("operatorid", operatorid);
		map.put("traffictype", traffictype);
		map.put("traffic", traffic);
		return this.template.selectOne("getSerialNumber", map);
	}

	@Override
	public List<TrafficPackage> findAllTrafficPackage(int operatorId) {
		return this.template.selectList("findAllTrafficPackageByOperator",operatorId);
	}
	
}