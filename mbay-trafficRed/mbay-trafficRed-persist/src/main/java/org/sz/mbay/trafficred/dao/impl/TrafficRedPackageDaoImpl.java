package org.sz.mbay.trafficred.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.trafficred.bean.TrafficRedPackage;
import org.sz.mbay.trafficred.dao.TrafficRedPackageDao;

@Repository
public class TrafficRedPackageDaoImpl extends BaseDaoImpl<TrafficRedPackage>
		implements TrafficRedPackageDao {
	
	@Override
	public int deleteById(Long id) {
		return this.template.delete("TrafficRedPackage.deleteById", id);
	}
	
	@Override
	public int create(TrafficRedPackage record) {
		return this.template.insert("TrafficRedPackage.create", record);
	}
	
	@Override
	public int createSelective(TrafficRedPackage record) {
		return this.template
				.insert("TrafficRedPackage.createSelective", record);
	}
	
	@Override
	public TrafficRedPackage selectById(Long id) {
		return this.template.selectOne("TrafficRedPackage.selectById", id);
	}
	
	@Override
	public int updateByIdSelective(TrafficRedPackage record) {
		return this.template.update("TrafficRedPackage.updateByIdSelective",
				record);
	}
	
	@Override
	public int updateById(TrafficRedPackage record) {
		return this.template.update("TrafficRedPackage.updateById", record);
	}
	
	@Override
	public List<TrafficRedPackage> findByOperatorType(OperatorType type) {
		return this.template.selectList("TrafficRedPackage.findByOperatorType",
				type);
	}

	@Override
	public int deleteByOperatorType(OperatorType type) {
		return template.delete("TrafficRedPackage.deleteByOperatorType", type);
	}
	
}
