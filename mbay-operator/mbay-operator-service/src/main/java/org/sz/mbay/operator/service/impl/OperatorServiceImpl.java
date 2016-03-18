package org.sz.mbay.operator.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.sz.mbay.base.area.Area;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.UtilService;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.hcode.bean.HcodeInfo;
import org.sz.mbay.operator.bean.Contract;
import org.sz.mbay.operator.bean.Operator;
import org.sz.mbay.operator.bean.TrafficPackage;
import org.sz.mbay.operator.dao.ContractDao;
import org.sz.mbay.operator.dao.OperatorDao;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.operator.enums.TrafficType;
import org.sz.mbay.operator.service.OperatorService;

@Service(value = "OperatorServiceImpl")
public class OperatorServiceImpl extends BaseServiceImpl
		implements OperatorService {
		
	private static final Logger LOGGER = LoggerFactory
			.getLogger(OperatorServiceImpl.class);
			
	@Autowired
	OperatorDao operatordao;
	
	@Autowired
	UtilService utilService;
	
	@Override
	public TrafficPackage findTrafficPackage(int id) {
		try {
			return this.operatordao.findTrafficPackage(id);
		} catch (Exception e) {
			LOGGER.error("查询流量充值包异常findTrafficPackage", e.fillInStackTrace());
		}
		return null;
	}
	
	@Override
	public List<TrafficPackage> findAllTrafficPackage(Area area,
			OperatorType operator, TrafficType tafficType) {
		return this.operatordao.findAllTrafficPackage(area, operator,
				tafficType);
	}
	
	@Override
	public double getMaxPackagePrice(int[] packageids) {
		return this.operatordao.getMaxPackagePrice(packageids);
	}
	
	@Override
	public List<Operator> findAllOperatorByArea(Area area) {
		try {
			return this.operatordao.findAllOperatorByArea(area.getValue());
		} catch (Exception e) {
			LOGGER.error("findAllOperatorByArea", e.fillInStackTrace());
		}
		return new ArrayList<Operator>();
	}
	
	@Override
	public boolean isAvalidPackage(int packageid) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public TrafficPackage getBestTrafficPackage(HcodeInfo code, int traffic) {
		Area area = Area.valueOf(code.getProvcode());
		OperatorType operator = OperatorType.valueOf(code.getOperator());
		return this.operatordao.getBestTrafficPackage(area, operator, traffic);
	}
	
	// onembay
	@Autowired
	ContractDao contractdao;
	
	@Override
	@Transactional
	public Operator addOperator(Operator operator) {
		try {
			return operatordao.createBean(operator);
		} catch (Exception e) {
			LOGGER.error("addOperator", e.fillInStackTrace());
		}
		return null;
	}
	
	@Override
	public List<Operator> findAllOperator(Operator operator,
			PageInfo pageinfo) {
		try {
			return this.operatordao.findList(operator, pageinfo);
		} catch (Exception e) {
			LOGGER.error("findAllOperator", e.fillInStackTrace());
		}
		return new ArrayList<Operator>();
	}
	
	@Override
	public List<Operator> findAllOperatorOfIDAndName() {
		try {
			return this.operatordao.findList("OperatorOfIDAndName", null);
		} catch (Exception e) {
			LOGGER.error("findAllOperatorOfIDAndName", e.fillInStackTrace());
		}
		return new ArrayList<Operator>();
	}
	
	@Override
	public Operator findOperatorName(int id) {
		return this.operatordao.findOperatorName(id);
	}
	
	@Override
	public void addTrafficPackage(TrafficPackage trafficpackage)
			throws Exception {
		this.operatordao.createBean(trafficpackage);
	}
	
	@Override
	public Operator findOperatorInfo(int id) {
		try {
			return this.operatordao.getBean(id, Operator.class);
		} catch (Exception e) {
			LOGGER.error("findOperatorInfo", e.fillInStackTrace());
		}
		return null;
	}
	
	@Override
	public Contract addContract(Contract contract) {
		return this.contractdao.createBean(contract);
	}
	
	// 合同列表
	@Override
	public List<Contract> findContractByOperatorid(int operatorid) {
		return this.contractdao.findContractByOperatorid(operatorid);
	}
	
	@Override
	public int updateEnabled(int id) {
		return this.operatordao.updateEnabled(id);
	}
	
	@Override
	public int updatedisable(int id) {
		return this.operatordao.updatedisable(id);
	}
	
	@Override
	@Transactional
	public int updateOpertaorEnabled(int operatorid) {
		try {
			// 启用运营商
			int operator_result = this.operatordao.updateEnabled(operatorid);
			if (operator_result != 1) {
				return 0;
			}
			// 启用运营商下所有流量包
			this.operatordao.updateEnabledByOperatorId(operatorid);
			return 1;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			LOGGER.error("OperatorServiceImpl updateOpertaorEnabled Error",
					e.fillInStackTrace());
			return 0;
		}
	}
	
	@Override
	public int updateOperatordisable(int operatorid) {
		try {
			// 禁用运营商
			int operator_result = this.operatordao.updatedisable(operatorid);
			if (operator_result != 1) {
				return 0;
			}
			// 禁用运营商下所有流量包
			this.operatordao.updatedisableByOperatorId(operatorid);
			return 1;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			LOGGER.error("OperatorServiceImpl updateOperatordisable Error",
					e.fillInStackTrace());
			return 0;
		}
	}
	
	@Override
	public int findCountOperator(String area, String type) {
		return this.operatordao.findCountOperator(area, type);
	}
	
	public void updatePackageCode(int id, String package_code) {
		this.operatordao.updatePackageCode(id, package_code);
	}
	
	@Override
	public List<TrafficPackage> findByAreaAndOpetor(TrafficType type,
			OperatorType operatorType) {
		return this.operatordao.findByAreaAndOpetor(type, operatorType);
	}
	
	@Override
	public int getSerialNumber(int operatorid, int traffictype, int traffic) {
		return this.operatordao.getSerialNumber(operatorid, traffictype,
				traffic);
	}
	
	@Override
	public List<TrafficPackage> findAllTrafficPackage() {
		List<TrafficPackage> list = null;
		try {
			list = this.operatordao.findList("TrafficPackageInfomation", null);
		} catch (Exception e) {
			LOGGER.error("OperatorServiceImpl findAllTrafficPackage Error",
					e.fillInStackTrace());
		}
		return list;
	}

	@Override
	public List<TrafficPackage> findAllTrafficPackage(int operatorId) {
		return this.operatordao.findAllTrafficPackage(operatorId);
	}
}
