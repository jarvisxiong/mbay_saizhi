package org.sz.mbay.customer.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.UtilService;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.user.service.UserAccountService;
import org.sz.mbay.customer.bean.BatchChargeInfo;
import org.sz.mbay.customer.bean.BatchChargeItem;
import org.sz.mbay.customer.bean.BatchChargeMobile;
import org.sz.mbay.customer.bean.BatchChargeStrategy;
import org.sz.mbay.customer.dao.CustomerServerDao;
import org.sz.mbay.customer.enums.BatchChargeMethod;
import org.sz.mbay.customer.qo.BatchChargeMobileForm;
import org.sz.mbay.customer.service.CustomerServerService;
import org.sz.mbay.hcode.MbayHcode;
import org.sz.mbay.hcode.bean.HcodeInfo;
import org.sz.mbay.operator.bean.TrafficPackage;
import org.sz.mbay.operator.dao.OperatorDao;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.trafficorder.bean.TrafficRechargeInfo;
import org.sz.mbay.trafficorder.enums.TrafficOrderType;
import org.sz.mbay.trafficorder.service.TrafficOrderService;
import org.sz.mbay.trafficrecharge.service.TrafficRechargeService;

@Service
public class CustomerServerServiceImpl extends BaseServiceImpl implements CustomerServerService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServerServiceImpl.class);
	@Autowired
	CustomerServerDao serverDao;
	@Autowired
	OperatorDao operatordao;
	@Autowired
	TrafficRechargeService trafficService;
	@Autowired
	UserAccountService assetsService;
	@Autowired
	UtilService utilService;
	@Autowired
	TrafficOrderService orderService;
	
	@Override
	@Transactional
	public ExecuteResult addBatchChargeInfo(BatchChargeInfo batchinfo) {
		try {
			batchinfo = this.serverDao.createBean(batchinfo);
			List<BatchChargeStrategy> strategys = batchinfo.getStrategys();
			Map<Integer, TrafficPackage> tmap = new HashMap<Integer, TrafficPackage>();
			for (BatchChargeStrategy strategy : strategys) {
				int operatorid = strategy.getOperator().ordinal();
				int packageid = strategy.getTrafficpackage().getId();
				TrafficPackage tpackage = tmap.get(operatorid);
				// 查询对应的流量包
				if (tpackage == null) {
					tpackage = this.operatordao.findTrafficPackage(packageid);
					tmap.put(operatorid, tpackage);
				}
				strategy.setBatchid(batchinfo.getId());
				this.serverDao.createBean(strategy);
			}
			List<BatchChargeMobile> chargemobiles = batchinfo.getBatchchargemobiles();
			double costmbay = 0;
			// TODO 关于批充消耗美贝需再考虑
			for (BatchChargeMobile chargemobile : chargemobiles) {
				int operatorid = chargemobile.getOperator().ordinal();
				TrafficPackage tpackage = tmap.get(operatorid);
				costmbay += tpackage.getMbayprice();
				chargemobile.setBatchid(batchinfo.getId());
				this.serverDao.createBean(chargemobile);
			}
			this.serverDao.setBatchChargeCostmbay(batchinfo.getId(), costmbay);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOGGER.error("addBatchChargeInfo", e.fillInStackTrace());
			return new ExecuteResult(false, "文件上传失败");
		}
		return new ExecuteResult(true, batchinfo.getId() + "");
	}
	
	@Override
	public List<BatchChargeInfo> findAllBatchChargeInfo(
			BatchChargeInfo chargeinfo, PageInfo pageinfo) {
		try {
			return this.serverDao.findList(chargeinfo, pageinfo);
		} catch (Exception e) {
			LOGGER.error("findAllBatchChargeInfo", e.fillInStackTrace());
		}
		return new ArrayList<BatchChargeInfo>();
	}
	
	@Override
	public String getChargeInfoExcelURL(int uid) {
		return this.serverDao.getChargeInfoExcelURL(uid);
	}
	
	@Override
	public List<BatchChargeMobile> findBatchChargeMobileInfo(
			BatchChargeMobileForm chargeMobileForm, PageInfo pageinfo) {
		return this.serverDao.findBatchChargeMobileInfo(chargeMobileForm,
				pageinfo);
	}
	
	// TODO : 这里需要考虑是重建订单后逐个充值还是边创建边充值
	@Override
	public ExecuteResult batchCharge(int batchid, boolean sms, String usernumber) {
		BatchChargeInfo chargeinfo = this.findBatchChargeInfo(batchid, usernumber);
		// 查询商户美贝账户美贝数量是否充足
		double amount = this.assetsService.getAvailableAmount(usernumber);
		if (chargeinfo.getCostmbay() > amount) {
			return new ExecuteResult(false, "您的账户余额不足，请充值！");
		}
		List<BatchChargeMobile> chargemobiles = this.serverDao.findChargeMobilesByBatchId(batchid);
		// 保存批充记录
		int nextId = this.utilService.getNextIndex(BatchChargeItem.class);
		// 保存批充单次充值记录
		BatchChargeItem item = new BatchChargeItem();  // 批充单次充值记录
		item.setId(nextId);
		item.setBatchid(batchid);
		item.setNum(chargemobiles.size());// 获取手机号码数量
		item.setCreatetime(new Timestamp(new Date().getTime()));
		item.setChargemethod(BatchChargeMethod.HAND_CHARGE);// 流量充值方式 设置为：手动充值
		item.setNote("");
		this.serverDao.saveBatchChargeItem(item);
		return this.batchTrafficOrder(chargemobiles, nextId, batchid, usernumber);
	}
	
	@Transactional
	public ExecuteResult batchTrafficOrder(List<BatchChargeMobile> mobiles, int itemid, int batchid, String userNumber)
	{
		List<String> orderNumbers = new ArrayList<String>();
		// 手动开始事物
		TransactionStatus status = this.startTransaction();
		// 循环创建流量订单
		try {
			for (BatchChargeMobile mobile : mobiles) {
				String rechargeOrderNumber = "";
					TrafficRechargeInfo info = new TrafficRechargeInfo();
					info.setMobile(mobile.getMobile());
					// 查询手机号H码信息
					HcodeInfo codeinfo = MbayHcode.getHcodeInfo(mobile.getMobile());
					if(codeinfo == null){
						throw new Exception("没有找到对应的hcode信息,mobile:" + mobile.getMobile());
					}
					TrafficPackage trafficPackage = serverDao.findTrafficPackage(batchid, OperatorType.valueOf(codeinfo.getOperator()));
					if(trafficPackage == null){
						throw new Exception("没有找到对应的流量包信息,batchid:" + batchid + ",operatorid:" + codeinfo.getOperator());
					}
					info.setTrafficPackageNumber(trafficPackage.getId());
					info.setRelationNumber(String.valueOf(itemid));
					info.setUserNumber(userNumber);
					info.setRechargeType(TrafficOrderType.CUSTOMER_SERVER);
					rechargeOrderNumber = orderService.create(info);
					assetsService.expenditure(
							userNumber, org.sz.mbay.channel.user.enums.TradeType.CUSTOMER_SERVER,
							rechargeOrderNumber, trafficPackage.getMbayprice(),
							"客户关怀营销");
					orderNumbers.add(rechargeOrderNumber);
			} 
			this.commitTransaction(status);// 提交事物
		} catch (Exception e) {
			LOGGER.error("客户关怀流量批充异常", e.fillInStackTrace());
			this.rollbackTransaction(status);// 回滚事物
			return new ExecuteResult(false, "创建流量批充订单失败，请重试！");
		}
		if(orderNumbers == null || orderNumbers.size() == 0){
			LOGGER.error("客户关怀流量批充异常: 没有生成相关订单信息");
			return new ExecuteResult(false, "创建流量批充订单失败，请重试！");
		}
		for(String orderNumber : orderNumbers){
			trafficService.recharge(orderNumber);
		}
		this.serverDao.increaseBatchChargeTimes(batchid);
		return new ExecuteResult(true, "流量批充已下发！");
	}
	
	@Override
	public List<BatchChargeItem> findBatchChargeItems(int batchid,
			PageInfo pageinfo) {
		try {
			return this.serverDao.findList(batchid, pageinfo,
					BatchChargeItem.class.getSimpleName());
		} catch (Exception e) {
			LOGGER.error("findBatchChargeItems", e.fillInStackTrace());
		}
		return new ArrayList<BatchChargeItem>();
	}
	
	@Override
	public BatchChargeInfo findBatchChargeInfo(int batchid, String usernumber) {
		return this.serverDao.findBatchChargeInfo(batchid, usernumber);
	}
	
	@Override
	@Transactional
	public boolean deleteChargeMobile(int batchid, String mobile,OperatorType operator) {
		try {
			// 删除批充号码信息
			this.serverDao.deleteChargeMobile(batchid, mobile);
			// 减少批充号码数量
			this.serverDao.reduceBatchChargeMobileNum(batchid);
			// 获取流量包价格
			TrafficPackage trafficPackage = this.serverDao.findTrafficPackage(batchid, operator);
			if(trafficPackage == null){
				throw new Exception();
			}
			double mbayprice = trafficPackage.getMbayprice();
			// 增加所需消耗美贝
			this.serverDao.reduceBatchChargeInfoCostMbay(batchid, mbayprice);
		} catch (Exception e) {
			LOGGER.error("deleteChargeMobile ERROR:", e.fillInStackTrace());
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return false;
		}
		return true;
	}
	
	@Override
	@Transactional
	public void addBatchChargeMobile(BatchChargeMobile mobile) {
		try {
			// 创建实体bean
			this.serverDao.createBean(mobile);
			// 增加批充手机号码数量
			this.serverDao.increaseBatchChargeMobileNum(mobile.getBatchid());
			// 获取流量包价格
			TrafficPackage trafficPackage = this.serverDao.findTrafficPackage(mobile.getBatchid(), mobile.getOperator());
			if(trafficPackage == null){
				throw new Exception();
			}
			double mbayprice = trafficPackage.getMbayprice();
			// 增加所需消耗美贝
			this.serverDao.increaseBatchChargeInfoCostMbay(mobile.getBatchid(), mbayprice);
		} catch (Exception e) {
			LOGGER.error("addBatchChargeMobile ERROR:", e.fillInStackTrace());
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
	}
	
	@Override
	public boolean isExistingBatchCharge(int batchid, String usernumber) {
		return this.serverDao.isExistingBatchCharge(batchid, usernumber);
	}
	
}