package org.sz.mbay.wallet.service.impl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.base.wrap.Response;
import org.sz.mbay.base.wrap.ResponseFail;
import org.sz.mbay.base.wrap.ResponseSuccess;
import org.sz.mbay.operator.bean.TrafficPackage;
import org.sz.mbay.remote.interfaces.wallet.RIMBAccountUtil;
import org.sz.mbay.remote.interfaces.wallet.base.RIResponse;
import org.sz.mbay.trafficorder.bean.TrafficRechargeInfo;
import org.sz.mbay.trafficorder.enums.TrafficOrderType;
import org.sz.mbay.trafficorder.service.TrafficOrderService;
import org.sz.mbay.wallet.constant.error.AccountError;
import org.sz.mbay.wallet.service.TrafficRedeemService;

@Service
public class TrafficRedeemServiceImpl extends BaseServiceImpl implements
		TrafficRedeemService {
		
	private Logger LOGGER = LoggerFactory
			.getLogger(TrafficRedeemServiceImpl.class);
			
	@Autowired
	private TrafficOrderService trafficOrderService;
	
	@Override
	@Transactional
	public Response trafficExchange(String mobile, TrafficPackage pack,
			String desc) {
		// 获取账户可用余额
		RIResponse mbResp = null;
		try {
			mbResp = RIMBAccountUtil.requestUserGetMBQty(mobile);
		} catch (Exception e) {
			LOGGER.error("get balance error:{}", e.getMessage());
			return ResponseFail.create(e.getMessage());
		}
		
		BigDecimal mbBalance = new BigDecimal(
				mbResp.getData().getDouble("balance"));
		BigDecimal mbayPrice = new BigDecimal(pack.getTraffic());
		
		// 账户扣款
		// 主账户余额 小于扣款金额
		if (mbBalance.compareTo(mbayPrice) < 0) {
			return AccountError.BALANCE_INSUFFICIENT;
		} else {
			try {
				// 创建充值订单
				TrafficRechargeInfo info = new TrafficRechargeInfo();
				info.setMobile(mobile);
				info.setRechargeType(TrafficOrderType.DIRECT_RECHARGE);
				info.setTrafficPackageNumber(pack.getId());
				info.setUserNumber(mbResp.getData().getString("userNum"));
				String orderNumber = trafficOrderService.create(info);
				
				// 主账户减额
				RIMBAccountUtil.requestUserOutOfAccount(
						mobile, mbayPrice.doubleValue(), "TRAFFIC_EXCHANGE",
						orderNumber, desc);
				return ResponseSuccess.create(orderNumber);
			} catch (Exception e) {
				LOGGER.error("get balance error:{}", e.getMessage());
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
				return ResponseFail.create(e.getMessage());
			}
		}
	}
}
