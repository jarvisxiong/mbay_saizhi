//package org.sz.mbay.channel.service.test;
//
//import junit.framework.TestCase;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//import org.sz.mbay.base.pagehelper.PageInfo;
//import org.sz.mbay.channel.user.service.UserAccountService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
//@Transactional
//@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
//public class AssetsServiceImplTest extends TestCase {
//
//    @Autowired
//    UserAccountService userAccountService;
//
//    @Test
//    public void testFindAllMbayDepositOrder() {
//        PageInfo pageinfo = new PageInfo();
//        pageinfo.setPagenum(1);
//        //assetsservice.findAllMbayDepositOrder("D7F4EE6D", pageinfo);
//    }
//
//    @Test
//    public void testCountMbayDepositOrder() {
//        PageInfo pageinfo = new PageInfo();
//        pageinfo.setPagenum(1);
//       // assetsservice.countMbayDepositOrder("D7F4EE6D", pageinfo);
//    }
//}
