package org.sz.mbay.channel.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sz.mbay.base.dao.utils.PKgen;
import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.bean.CampaignRedeemCode;
import org.sz.mbay.channel.bean.Store;
import org.sz.mbay.channel.bean.StoreCampaign;
import org.sz.mbay.channel.bean.StoreCampaignOrder;
import org.sz.mbay.channel.bean.StoreOperator;
import org.sz.mbay.channel.dao.CampaignRedeemCodeDao;
import org.sz.mbay.channel.dao.StoreCampaignRecordDao;
import org.sz.mbay.channel.enums.OrderStatus;
import org.sz.mbay.channel.enums.StoreCampaginTradeType;
import org.sz.mbay.channel.service.CampaignRedeemCodeService;
import org.sz.mbay.channel.service.StoreActivityService;
import org.sz.mbay.channel.service.StoreCampaginOrderService;
import org.sz.mbay.channel.service.StoreService;
import org.sz.mbay.channel.user.enums.TradeType;
import org.sz.mbay.channel.user.exception.UserAccountTradeException;
import org.sz.mbay.channel.user.service.UserAccountService;
import org.sz.mbay.channel.wrap.O2OCampaignRedeenDetail;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.promotion.enums.RedeemCodeStu;

/**
 * 
 * @ClassName: CampaignRedeemCodeServiceImpl
 * 
 * @Description: 门店活动兑换码业务逻辑类的实现类
 * @author <a href="mailto:fenlonxiong@gmail.com">Fenlon</a>
 * 
 * @date 2015年1月22日 下午12:53:22
 * 
 */

@Service
public class CampaignRedeemCodeServiceImpl implements CampaignRedeemCodeService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CampaignRedeemCodeDao campaignRedeemCodeDao;
	
	@Autowired
	StoreCampaignRecordDao storeCampaignRecordDao;
	
	@Autowired
	UserAccountService userAccountServcie;
	
	@Autowired
	StoreService storeService;
	
	@Autowired
	StoreActivityService activityService;
	
	@Autowired
	StoreCampaginOrderService campaignOrderService;
	
	@Override
	@Transactional
	public CampaignRedeemCode createRedeemCode(String cellPhone, long storeId,
			StoreCampaign campaign) {
		DateTime now = DateTime.now();
		long campaignId = campaign.getId();
		
		// TODO 美贝平台分发
		
		// 根据活动找到商家编号
		String userNumber = campaign.getUserNumber();
		// 1:判断商户余额是否足够
		// 查用户当前余额
		double balance = userAccountServcie.getAccountAmount(userNumber);
		if (balance < campaign.getDeductSend()) {
			// 如果商户余额不足，不创建兑换码,活动也不停止
			this.logger.info("createRedeemCode", "商户余额不足,请及时充值！");
			return null;
		}
		
		// 2：生成兑换码
		CampaignRedeemCode redeemCode = new CampaignRedeemCode();
		long id = PKgen.getInstance().nextPK();
		redeemCode.setId(id);
		redeemCode.setCellPhone(cellPhone);
		redeemCode.setCampaignId(campaignId);
		redeemCode.setStoreId(storeId);
		redeemCode.setRedeemStoreId(0);
		// 生成兑换码
		String code = generateCode(id + campaignId + "");
		redeemCode.setRedeemCode(code);
		redeemCode.setStatus(RedeemCodeStu.UN_REDEEM);
		redeemCode.setDateCreated(now);
		redeemCode.setCheckCode(generateCode(code));
		redeemCode = this.campaignRedeemCodeDao.createBean(redeemCode);
		// 3:生成订单
		/** 得到活动发放一个兑换码应该扣除的美贝数 **/
		double price = activityService.findDeliverPriceById(campaignId);
		StoreCampaignOrder order = new StoreCampaignOrder();
		order.setUserNumber(userNumber);
		String number = PKgen.getInstance().nextPK() + "";
		order.setNumber(number);
		order.setPrice(price);
		order.setRedeemCode(redeemCode.getRedeemCode());
		order.setStatus(OrderStatus.FINISHED);
		order.setType(StoreCampaginTradeType.DELIVER);
		order.setDateCreated(now);
		this.campaignOrderService.create(order);
		
		// 4:扣除活动单位发放美贝数额(发放一个兑换码，扣除相应的美贝)
		try {
			this.userAccountServcie.expenditure(
					userNumber, TradeType.STORE_CAMPAGIN,
					order.getNumber(), price, cellPhone);
			// 5:jiesuo kouchude meibei 解锁扣除的美贝
			this.userAccountServcie.unLockedMbay(userNumber, price);
		} catch (UserAccountTradeException e) {
			logger.error("createRedeemCode", e.getMessage());
			return null;
		}
		
		// 6:修改活动发放数量
		this.activityService.updateDeliverNumAndLockMbayAndCostMbay(campaignId, userNumber, price);
		
		// 7:修改门店活动记录中门店发放的数量
		this.storeCampaignRecordDao.updateDeliverNum(storeId, campaignId);
		return redeemCode;
	}
	
	/**
	 * 暂时先不检查用户编号是否已存在
	 * 
	 * @param source
	 * @return
	 */
	private String generateCode(String source) {
		return DigestUtils.crc32(source);
	}
	
	@Override
	public boolean findRedeemCode(String campaignId, String cellPhone) {
		String code = this.campaignRedeemCodeDao.findRedeemCode(campaignId,
				cellPhone);
		if (code == null) {
			return false;
		}
		return true;
	}
	
	@Override
	public ExecuteResult judgeValidate(StoreCampaign campaign, String cellPhone) {
		ExecuteResult result = new ExecuteResult(true, "您可以参加活动!");
		// 1: 判断活动是否存在
		if (campaign == null) {
			result.setError_code("CAMPAIGN NOT FOUND");
			result.setSuccess(false);
			result.setMessage("未找到该活动信息");
			return result;
		}
		
		// 2: 活动是否支持
		if (!campaign.getStatus().equals(CampaignStatus.IN_ACTIVE)) {
			result.setError_code("CAMPAIGN NOT SUPPORT");
			result.setSuccess(false);
			result.setMessage("活动不支持：状态为:" + campaign.getStatus());
			return result;
		}
		
		// 3: 判断发放总量
		if (campaign.getDeliverNum() == campaign.getQuantity()) {
			// 发放到达总量,是否修改活动状态呢？
			result.setError_code("CAMPAIGN HAS DELIVERED");
			result.setSuccess(false);
			result.setMessage("福利已经发放完啦！");
			return result;
		}
		
		// 4: 根据是否是否允许重复领取，来判断是否生成兑换码
		if (!campaign.isRepeatGet()) {
			// 如果不允许重复领取，则判断
			boolean exist = this.findRedeemCode(campaign.getNumber(),
					cellPhone);
			if (exist) {
				result.setError_code("HAS PICK IT UP");
				result.setSuccess(false);
				result.setMessage("您已近领取过该活动!");
				return result;
			}
		}
		// 说明没有任何问题，可以发兑换码
		return result;
	}
	
	@Override
	public CampaignRedeemCode findRedeemCode(String code) {
		return this.campaignRedeemCodeDao.findRedeemCodeByCode(code);
	}
	
	@Override
	@Transactional
	public ExecuteResult redeem(CampaignRedeemCode code, StoreOperator operator) {
		ExecuteResult result = new ExecuteResult(true, "");
		/*
		 * Store deliverStore =
		 * this.storeService.findStoreById(code.getStoreId());
		 * if (!deliverStore.isEnable()) {
		 * // 发放兑换码门店处于禁用状态
		 * result.setError_code("DELIVER STORE HAS DISABLED");
		 * result.setMessage("发放兑换码门店已经处于禁用状态，兑换码无法兑换!");
		 * result.setSuccess(false);
		 * return result;
		 * }
		 */
		Store redeemStore = this.storeService.findStoreByAuthCode(operator.getAuthCode());
		StoreCampaign campaign = this.activityService.findCampaignById(code.getCampaignId());
		String userNumber = campaign.getUserNumber();
		DateTime now = DateTime.now();
		// 1:判断商户余额是否足够
		// 查用户当前余额
		double balance = userAccountServcie.getAccountAmount(userNumber);
		/** 得到活动兑换一个兑换码应该扣除的美贝数 **/
		double price = campaign.getDeductReedem();
		if (balance < price) {
			// 如果商户余额不足，不创建兑换码,活动也不停止
			this.logger.info("createRedeemCode", "商户余额不足,请及时充值！");
			result.setError_code("PAY_NOT_ENOUGH");
			result.setMessage("账户余额不足!");
			result.setSuccess(false);
			return result;
		}
		
		// 2:生成订单
		StoreCampaignOrder order = new StoreCampaignOrder();
		order.setUserNumber(userNumber);
		String number = PKgen.getInstance().nextPK() + "";
		order.setNumber(number);
		order.setPrice(price);
		order.setRedeemCode(code.getRedeemCode());
		order.setStatus(OrderStatus.FINISHED);
		order.setType(StoreCampaginTradeType.REDEEM);
		order.setDateCreated(now);
		this.campaignOrderService.create(order);
		
		// 3:扣除活动单位兑换美贝数额(发放一个兑换码，扣除相应的美贝)
		try {
			this.userAccountServcie.expenditure(
					userNumber, TradeType.STORE_CAMPAGIN,
					order.getNumber(), price, code.getCellPhone());
			// 4:jiechu duiying de kouchumeibei
			this.userAccountServcie.unLockedMbay(userNumber, price);
		} catch (UserAccountTradeException e) {
			logger.info("createRedeemCode", e.getMessage());
			return null;
		}
		
		// 5:修改兑换码状态-兑换门店ID-和兑换操作员ID-兑换时间-数据修改时间
		if (redeemStore == null) {
			result.setSuccess(false);
			result.setMessage("");
			result.setError_code("STORE_NULL");
			return result;
		}
		code.setRedeemStoreId(redeemStore.getId());
		code.setOperator(operator);
		code.setStatus(RedeemCodeStu.REDEEMED);
		code.setRedeemTime(now);
		code.setDateModified(now);
		this.campaignRedeemCodeDao.updateCode(code);
		
		// 6:修改活动兑换数量he suoding meibeizhi
		this.activityService.updateRedeemNumAndLockMbayAndCostMbay(campaign.getId(), userNumber, price);
		
		// 7:修改门店活动记录中门店兑换的数量
		this.storeCampaignRecordDao.updateRedeemNum(redeemStore.getId(), campaign.getId());
		result.setMessage("兑换码：" + code.getRedeemCode() + "兑换成功!" + "checkCode:" + code.getCheckCode() + "--link:" + campaign.getLink() + "--price:" + campaign.getPrice());
		return result;
	}
	
	@Override
	public List<CampaignRedeemCode> findRedeemRecord(long redeemStoreId, PageInfo pageInfo)
	{
		CampaignRedeemCode code = new CampaignRedeemCode();
		code.setRedeemStoreId(redeemStoreId);
		return this.campaignRedeemCodeDao.findList(code, pageInfo);
	}
	
	@Override
	public List<O2OCampaignRedeenDetail> queryRedeemDetail(long campaignId, String userNumber, PageInfo pageInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignId", campaignId);
		map.put("userNumber", userNumber);
		return this.campaignRedeemCodeDao.findList(map, pageInfo, "RedeemDetail");
	}
	
	@Override
	public List<O2OCampaignRedeenDetail> queryRedeemDetailByCondition(long campaignId, String userNumber, DateTime startTime,
			DateTime endTime, String cellPhone, PageInfo pageInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignId", campaignId);
		map.put("userNumber", userNumber);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("cellPhone", cellPhone);
		return this.campaignRedeemCodeDao.findList(map, pageInfo, "RedeemDetailByCondition");
	}
}
