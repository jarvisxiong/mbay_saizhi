package org.sz.mbay.wallet.service.impl;

import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.base.wrap.Response;
import org.sz.mbay.base.wrap.ResponseFail;
import org.sz.mbay.base.wrap.ResponseSuccess;
import org.sz.mbay.remote.interfaces.wallet.RIMBAccountUtil;
import org.sz.mbay.remote.interfaces.wallet.RITradeRecordUtil;
import org.sz.mbay.remote.interfaces.wallet.base.RIResponse;
import org.sz.mbay.wallet.bean.DuiBa;
import org.sz.mbay.wallet.dao.DuiBaDao;
import org.sz.mbay.wallet.enums.DuiBaState;
import org.sz.mbay.wallet.service.DuiBaService;

import net.sf.json.JSONObject;

@Service
public class DuiBaServiceImpl extends BaseServiceImpl implements DuiBaService {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DuiBaServiceImpl.class);
			
	@Autowired
	private DuiBaDao duiBaDao;
	
	@Override
	public DuiBaState getState(String orderNumber) {
		return duiBaDao.getState(orderNumber);
	}
	
	@Override
	public DuiBa create(DuiBa duiBa) {
		duiBaDao.createBySelective(duiBa);
		return duiBa;
	}
	
	@Override
	public boolean updateState(String orderNumber, DuiBaState state) {
		return duiBaDao.updateState(orderNumber, state);
	}
	
	@Override
	public DuiBa findByOrderNumber(String orderNumber) {
		return duiBaDao.findByOrderNumber(orderNumber);
	}
	
	@Override
	public DuiBa findBySerialNumber(String serialNumber) {
		return duiBaDao.findBySerialNumber(serialNumber);
	}
	
	@Override
	public Response reduceBalance(Map<String, Object> params) {
		String mobile = (String) params.get("mobile");
		String number = (String) params.get("number");
		String relatedNumber = (String) params.get("relatedNumber");
		double amount = (double) params.get("amount");
		
		LOGGER.info("{} try duiba reduce balance", mobile);
		
		// 扣款
		RIResponse resp = null;
		try {
			resp = RIMBAccountUtil.requestUserOutOfAccount(mobile,
					amount, "DUIBA_MARKET", relatedNumber, null);
			LOGGER.info("{} duiba reduce balance result:{}", mobile, resp);
		} catch (Exception e) {
			LOGGER.error("reduce balance error:{}", e.getMessage());
			return ResponseFail.create(e.getMessage());
		}
		
		LOGGER.info("{} try create duiba record, {}", mobile,
				resp.getData().getString("serialNumber"));
				
		// 兑吧记录
		String serialNumber = resp.getData().getString("serialNumber");
		DuiBa duiBa = new DuiBa();
		duiBa.setOrderNumber(number);
		duiBa.setSerialNumber(serialNumber);
		duiBa.setState(DuiBaState.CREATED);
		duiBa.setCreateTime(DateTime.now());
		duiBa.setMallId(Integer.parseInt(relatedNumber));
		create(duiBa);
		
		LOGGER.info("{} duiba record created", mobile);
		
		// 返回值
		JSONObject data = new JSONObject();
		data.put("snumber", serialNumber);
		data.put("amount", resp.getData().getDouble("balance"));
		
		LOGGER.info("{} return data:{}", mobile, data);
		return ResponseSuccess.create(data);
	}
	
	@Override
	public Response rollback(String orderNumber) {
		// 查询兑吧记录
		DuiBa duiba = findByOrderNumber(orderNumber);
		if (duiba == null) {
			LOGGER.error("rollback fail: orderNumber not exsit【{}】",
					orderNumber);
			return ResponseFail.create("orderNumber not exsit:{}", orderNumber);
		}
		
		// 查询订单信息
		RIResponse tdResp = null;
		try {
			tdResp = RITradeRecordUtil
					.requestGetTradeRecordBySerialNum(duiba.getSerialNumber());
		} catch (Exception e) {
			LOGGER.error("lookup trade info error:{}", e.getMessage());
			return ResponseFail.create(e.getMessage());
		}
		
		// 加款
		try {
			RIMBAccountUtil.requestUserEnterOfAccount(
					tdResp.getData().getString("mobile"),
					tdResp.getData().getJSONObject("result")
							.getDouble("amount"),
					"DUIBA_MARKET_ROLLBACK",
					duiba.getSerialNumber(),
					null);
		} catch (Exception e) {
			LOGGER.error("lookup trade info error:{}", e.getMessage());
			return ResponseFail.create(e.getMessage());
		}
		
		return Response.SUCCESS;
	}
	
}
