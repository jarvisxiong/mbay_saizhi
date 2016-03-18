package org.sz.mbay.operator.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.operator.bean.Contract;
import org.sz.mbay.operator.dao.ContractDao;

@Repository
public class ContractDaoImpl extends BaseDaoImpl<Contract> implements ContractDao {

	@Override
	public List<Contract> findContractByOperatorid(int operatorid) {
		return this.template.selectList("findContractByOperatorid", operatorid);
	}
}