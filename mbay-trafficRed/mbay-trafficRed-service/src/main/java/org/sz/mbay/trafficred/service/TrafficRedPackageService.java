package org.sz.mbay.trafficred.service;

import java.util.List;

import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.trafficred.bean.TrafficRedPackage;

public interface TrafficRedPackageService {
	
	/**
	 * 根据ID查找流量包
	 * 
	 * @param id
	 * @return
	 */
	TrafficRedPackage findById(Long id);
	
	/**
	 * 根据运营商查询后台设置的流量包
	 * 
	 * @param type
	 * @return
	 */
	List<TrafficRedPackage> findByOperatorType(OperatorType type);
	
	/**
	 * 删除
	 * 
	 * @param pid
	 */
	void deleteById(Long pid);
	
	/**
	 * 新增
	 * 
	 * @param trps
	 */
	void addPackages(TrafficRedPackage[] trps);
			
	/**
	 * 根据运营商类别删除
	 * 
	 * @param type
	 */
	void deleteByOperatorType(OperatorType type);
}
