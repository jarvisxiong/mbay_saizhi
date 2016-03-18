package org.sz.mbay.operator.dao;

import java.util.List;

import org.sz.mbay.base.area.Area;
import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.operator.bean.Operator;
import org.sz.mbay.operator.bean.TrafficPackage;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.operator.enums.TrafficType;

public interface OperatorDao extends BaseDao<Operator> {
	
	/**
	 * 根据区域位置返回岁对应的运营商
	 * 
	 * @param area
	 * @return
	 */
	public List<Operator> findAllOperatorByArea(int area) throws Exception;
	
	/**
	 * 根据地区和运营商查询所有对应的流量包
	 * 
	 * @param area
	 * @param operator
	 * @return
	 */
	public List<TrafficPackage> findAllTrafficPackage(Area area,
			OperatorType operator, TrafficType tafficType);
			
	/**
	 * 根据ID查询对应的流量包
	 * 
	 * @param packageid
	 * @return
	 */
	public TrafficPackage findTrafficPackage(int packageid) throws Exception;
	
	/**
	 * 获取流量包中价格最高的流量包
	 * 
	 * @param packageids
	 * @return
	 */
	public double getMaxPackagePrice(int[] packageids);
	
	/**
	 * 获取流量包价格
	 * 
	 * @param packageids
	 * @return
	 */
	public double getTrafficPackagePrice(int packageid);
	
	/**
	 * 根据地区和运营商查询对应的流量包
	 * 
	 * @param packageid
	 * @return
	 */
	public TrafficPackage getBestTrafficPackage(Area area,
			OperatorType operator, int traffic);
			
	// onembay
	public Operator findOperatorName(int id);
	
	public int findCountOperator(String area, String type);
	
	public void updatePackageCode(int id, String package_code);
	
	/**
	 * 运营商启用
	 * 
	 * @param id
	 *            运营商id
	 * @return
	 */
	public int updateOperatorEnabled(int id);
	
	/**
	 * 运营商禁用
	 * 
	 * @param id
	 *            运营商id
	 * @return
	 */
	public int updateOperatorDisable(int id);
	
	/**
	 * 流量包启用
	 * 
	 * @param id
	 *            流量包id
	 * @return
	 */
	public int updateEnabled(int id);
	
	/**
	 * 流量包禁用
	 * 
	 * @param id
	 *            流量包id
	 * @return
	 */
	public int updatedisable(int id);
	
	/**
	 * 根据运营商id批量启用流量包
	 * 
	 * @param operatorid
	 *            运营商id
	 * @return
	 */
	public int updateEnabledByOperatorId(int operatorid);
	
	/**
	 * 根据运营商id批量禁用流量包
	 * 
	 * @param operatorid
	 *            运营商id
	 * @return
	 */
	public int updatedisableByOperatorId(int operatorid);
	
	/**
	 * 根据地区和运营商选择流量包
	 * 
	 * @param area
	 * @param operator
	 * @return
	 */
	public List<TrafficPackage> findByAreaAndOpetor(TrafficType type,
			OperatorType operatorType);
	
	public List<TrafficPackage> findAllTrafficPackage(int operatorId);
		
	/**
	 * 获取流水号
	 * @param operatorid
	 * @param traffictype
	 * @param traffic
	 * @return
	 */
	public int getSerialNumber(int operatorid, int traffictype, int traffic);
}
