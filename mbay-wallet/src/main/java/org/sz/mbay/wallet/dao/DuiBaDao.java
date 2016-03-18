package org.sz.mbay.wallet.dao;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.wallet.bean.DuiBa;
import org.sz.mbay.wallet.enums.DuiBaState;

public interface DuiBaDao extends BaseDao<DuiBa> {
	
	DuiBaState getState(String orderNumber);

	boolean createBySelective(DuiBa duiBa);

	boolean updateState(String orderNumber, DuiBaState state);

	DuiBa findByOrderNumber(String orderNumber);

	DuiBa findBySerialNumber(String serialNumber);
	
}
