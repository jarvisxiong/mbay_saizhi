package org.sz.mbay.trafficred.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.trafficred.bean.TrafficRedPackage;

/**
 * 流量红包活动
 * 
 * @author Fenlon
 * 
 */
public interface TrafficRedPackageDao extends BaseDao<TrafficRedPackage> {
	
	int deleteById(Long id);
	
	int create(TrafficRedPackage record);
	
	int createSelective(TrafficRedPackage record);
	
	TrafficRedPackage selectById(Long id);
	
	int updateByIdSelective(TrafficRedPackage record);
	
	int updateById(TrafficRedPackage record);
	
	List<TrafficRedPackage> findByOperatorType(OperatorType type);

	int deleteByOperatorType(OperatorType type);
}
