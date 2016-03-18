package org.sz.mbay.trafficrecharge.service.impl;

import static org.springframework.util.Assert.notNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.config.ResourceConfig;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.common.util.HttpSupport;
import org.sz.mbay.trafficrecharge.service.TrafficRechargeService;

@Service
public class TrafficRechargeServiceImpl extends BaseServiceImpl
		implements TrafficRechargeService {
		
	public static final String MBAYTRAFFIC_CONFIG_KEY = "mbytraffic_url";
	Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	// @Autowired
	// TrafficOrderService orderService;
	
	/*
	 * @Autowired UserAccountService userAccountService;
	 */
	
	@Override
	public ExecuteResult recharge(String orderNumber) {
		notNull(orderNumber, "Property 'orderNumber' is required");
		// TrafficOrder order=this.orderService.findTrafficOrder(orderNumber);
		// if(order==null){
		// throw new TrafficRechargeException("根据订单号"+orderNumber+"未查询到对应的订单！");
		// }
		// 充值流量包
		Thread thread = new Thread(new TrafficCharge(orderNumber));
		thread.start();
		return new ExecuteResult(true, "流量充值成功");
	}
	
	class TrafficCharge implements Runnable {
		
		/** 充值单号 */
		private String ordernumber = "";
		
		/**
		 * 构造函数，需提供流量充值单号
		 * 
		 * @param ordernumber
		 */
		private TrafficCharge(String ordernumber) {
			this.ordernumber = ordernumber;
		}
		
		@Override
		public void run() {
			this.trafficCharge();
		}
		
		private void trafficCharge() {
			
			String url = ResourceConfig.getProperty(MBAYTRAFFIC_CONFIG_KEY)
					+ "?" + "ordernumber=" + ordernumber;
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("请求美贝流量接口充值流量：{}", url);
			}
			String response = "";
			try {
				response = HttpSupport.build(url).connect();
				
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("Response Info:{}", response);
				}
			} catch (Exception e) {
				LOGGER.error("TrafficRechargeServiceImpl 请求赛志mbaytraffic接口失败",
						e.fillInStackTrace());
			}
		}
		
		@Override
		public String toString() {
			return "TrafficCharge []";
		}
		
	}
	
}
