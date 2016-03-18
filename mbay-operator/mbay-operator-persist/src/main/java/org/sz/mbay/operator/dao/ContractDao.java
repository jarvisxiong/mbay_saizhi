package org.sz.mbay.operator.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.operator.bean.Contract;

public interface ContractDao extends BaseDao<Contract> {

	List<Contract> findContractByOperatorid(int operatorid);
}
