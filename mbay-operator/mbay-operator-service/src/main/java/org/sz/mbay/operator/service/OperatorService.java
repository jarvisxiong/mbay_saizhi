package org.sz.mbay.operator.service;

import java.util.List;

import org.sz.mbay.base.area.Area;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.hcode.bean.HcodeInfo;
import org.sz.mbay.operator.bean.Contract;
import org.sz.mbay.operator.bean.Operator;
import org.sz.mbay.operator.bean.TrafficPackage;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.operator.enums.TrafficType;

public interface OperatorService {
	
	/**
	 * 根据区域位置返回岁对应的运营商
	 * 
	 * @param area
	 * @return
	 */
	public List<Operator> findAllOperatorByArea(Area area);
	
	/**
	 * @param area
	 * @param operator
	 * @param tafficType
	 * @return
	 */
	public List<TrafficPackage> findAllTrafficPackage(Area area,
			OperatorType operator, TrafficType tafficType);
	
	
	public List<TrafficPackage> findAllTrafficPackage(int operatorid);
	
	
	
	/**
	 * @param packageid
	 * @return
	 */
	public TrafficPackage findTrafficPackage(int packageid);
	
	/**
	 * 判断当前流量包是否有效
	 * 
	 * @param packageid
	 * @return
	 */
	public boolean isAvalidPackage(int packageid);
	
	/**
	 * 获取流量包中价格最高的流量包
	 * 
	 * @param packageids
	 * @return
	 */
	public double getMaxPackagePrice(int[] packageids);
	
	/**
	 * 智能路由，选择合适通道
	 * (即根据手机号判断本地是否存在流量包，如果存在，使用本地流量包，否则选择全国流量包)
	 * 
	 * @param code
	 *            号码信息
	 * @param traffic
	 *            流量包大小
	 * @return
	 */
	public TrafficPackage getBestTrafficPackage(HcodeInfo code, int traffic);
	
	// onembay
	/**
	 * 添加通信运营商
	 * 
	 * @param operator
	 * @return
	 */
	public Operator addOperator(Operator operator);
	
	/**
	 * 所有通信运营商
	 * 
	 * @param operator
	 * @param pageinfo
	 * @return
	 */
	public List<Operator> findAllOperator(Operator operator, PageInfo pageinfo);
	
	/**
	 * 查询通信运营商ID，NAME
	 * 
	 * @param operator
	 * @param pageinfo
	 * @return
	 */
	public Operator findOperatorName(int id);
	
	/**
	 * 所有通信运营商
	 * 
	 * @param operator
	 * @param pageinfo
	 * @return
	 */
	public List<Operator> findAllOperatorOfIDAndName();
	

	
	/**
	 * 添加运营商业务流量包
	 * 
	 * @param trafficpackages
	 */
	public void addTrafficPackage(TrafficPackage trafficpackage) throws Exception;
	
	public Operator findOperatorInfo(int id);
	
	public Contract addContract(Contract contract);
	
	public List<Contract> findContractByOperatorid(int operatorid);
	
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
	 * 运营商及其下流量包一起启用
	 * 
	 * @param operatorid
	 *            运营商id
	 * @return
	 */
	public int updateOpertaorEnabled(int operatorid);
	
	/**
	 * 运营商及其下流量包一起禁用
	 * 
	 * @param operatorid
	 *            运营商id
	 * @return
	 */
	public int updateOperatordisable(int operatorid);
	
	public int findCountOperator(String area, String type);
	
	public void updatePackageCode(int id, String package_code);
	
	public List<TrafficPackage> findByAreaAndOpetor(TrafficType type, OperatorType operatorType);

	/**
	 * 获取流水号
	 * @param operatorid
	 * @param traffictype
	 * @param traffic
	 * @return
	 */
	public int getSerialNumber(int operatorid, int traffictype, int traffic);
	
	public List<TrafficPackage> findAllTrafficPackage();
}
