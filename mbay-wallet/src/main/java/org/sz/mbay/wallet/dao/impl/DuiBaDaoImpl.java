package org.sz.mbay.wallet.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.wallet.bean.DuiBa;
import org.sz.mbay.wallet.dao.DuiBaDao;
import org.sz.mbay.wallet.enums.DuiBaState;

@Repository
public class DuiBaDaoImpl extends BaseDaoImpl<DuiBa> implements DuiBaDao{

	@Override
	public DuiBaState getState(String orderNumber) {
		return template.selectOne("DuiBa.getState", orderNumber);
	}

	@Override
	public boolean createBySelective(DuiBa duiBa) {
		return template.insert("DuiBa.createBySelective", duiBa) > 0;
	}

	@Override
	public boolean updateState(String orderNumber, DuiBaState state) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderNumber", orderNumber);
		param.put("state", state);
		return template.update("DuiBa.updateState", param) > 0;
	}

	@Override
	public DuiBa findByOrderNumber(String orderNumber) {
		return template.selectOne("DuiBa.findByOrderNumber", orderNumber);
	}

	@Override
	public DuiBa findBySerialNumber(String serialNumber) {
		return template.selectOne("DuiBa.findBySerialNumber", serialNumber);
	}
	
}
