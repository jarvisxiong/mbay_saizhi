/*package org.sz.mbay.trafficrecharge.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.sz.mbay.trafficrecharge.bean.TrafficRechargeInfo;
import org.sz.mbay.trafficrecharge.enums.TrafficOrderType;
import org.sz.mbay.trafficrecharge.qo.TrafficOrderQO;
import org.sz.mbay.trafficrecharge.service.TrafficOrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@ContextConfiguration(locations = { "classpath*:applicationContext-*.xml" })
public class TrafficRechargeTest extends UnitTestBase {
	

	@Autowired
	TrafficOrderService rechargeService;
	
	@Test
	public void testRecharge(){
		rechargeService=super.getBean(TrafficOrderService.class);
		System.out.println(rechargeService==null);
		TrafficRechargeInfo info=new TrafficRechargeInfo();
		info.setMobile("18625427511");
		info.setRechargeType(TrafficOrderType.APP_CAMPAIGN);
		info.setRelationNumber("20150520100010");
		info.setTrafficPackageNumber(1);
		info.setUserNumber("D7F4EE6D");
		rechargeService.recharge(info);
		TrafficOrderQO qo=new TrafficOrderQO();
		this.rechargeService.findAllTrafficOrder(qo, null);
		
	}
}
*/