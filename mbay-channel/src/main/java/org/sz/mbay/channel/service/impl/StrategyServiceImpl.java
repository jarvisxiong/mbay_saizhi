package org.sz.mbay.channel.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.api.traffic.RedeemCodeJson;
import org.sz.mbay.channel.bean.Strategy;
import org.sz.mbay.channel.context.ChannelContext;
import org.sz.mbay.channel.dao.StrategyDao;
import org.sz.mbay.channel.service.StrategyService;
import org.sz.mbay.channel.user.dao.UserContextDao;
import org.sz.mbay.channel.user.dao.UserDao;
import org.sz.mbay.channel.user.service.UserAccountService;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.promotion.bean.RedeemCode;

@Service
public class StrategyServiceImpl extends BaseServiceImpl implements
		StrategyService {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StrategyServiceImpl.class);
	
	@Autowired
	StrategyDao strategydao;
	@Autowired
	UserDao userDao;
	@Autowired
	UserContextDao contextdao;
	@Autowired
	UserAccountService userAccountService;
	
	@Override
	@Transactional
	public ExecuteResult addStrategy(Strategy strategy) {
		try {
			double productprice = 0;
			double unitmb = 1;
			// / if (strategy.getSendway().key == Sendway.TRAFFIC.key) {
			if (true) {
				if (strategy.getTeloperator() == OperatorType.THREENETS) {
					unitmb = this.strategydao.getMaxUnitMbByArea(strategy
							.getArea());
				} else {
					unitmb = this.strategydao.getUnitMbByAreaAndOperator(
							strategy.getArea(), strategy.getTeloperator());
				}
				productprice = unitmb * strategy.getQuantity()
						* strategy.getSendnum();
			} else {
				// / productprice = strategy.getQuantity() *
				// strategy.getSendnum();
			}
			double amount = this.userAccountService
					.getAvailableAmount(ChannelContext.getSessionChannel()
							.getUserNumber());
			if (productprice > amount) {
				return new ExecuteResult(false, "账户余额不足");
			}
			strategy.setUnitmb(unitmb * strategy.getSendnum());
			strategy.setMbaybalance(productprice);
			strategy.setMbayamount(strategy.getMbaybalance());
			strategy = strategydao.createBean(strategy);
			String privatekey = this.userDao.getPrivatekeyByUid(strategy
					.getUserid());
			// TODO 需要对DES.encode 返回null处理
//			String url = ChannelConfig.getRedeemCodeURL() + "?channelid="
//					+ strategy.getUserid() + "&strategy="
//					+ DigestUtils.des.encode(String.valueOf(strategy.getId()), privatekey)
//					+ "&mobile=手机号";
			// / strategy.setRedeemcodeurl(url);
		//	this.strategydao.updateStrategyRedeemCodeURL(strategy.getId(), url);
			// //contextdao.initUserContext(strategy.getUserid());
			// /assetsdao.increaseLockedAmount(strategy.getUserid(),
			// /strategy.getMbaybalance());
			
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			LOGGER.error("addStrategy", e.fillInStackTrace());
			return new ExecuteResult(false, "系统异常，添加策略失败，请联系管理员");
		}
		return new ExecuteResult(true, "");
	}
	
	@Override
	public List<Strategy> findAllStrategy(Strategy strategy, PageInfo pageinfo) {
		try {
			return strategydao.findList(strategy, pageinfo);
		} catch (Exception e) {
			LOGGER.error("findAllStrategy", e.fillInStackTrace());
		}
		return new ArrayList<Strategy>();
	}
	
	@Override
	@Transactional
	public ExecuteResult generateRedeemcode(long userid, int stragegyid) {
		/***
		 * try { Strategy strategy = this.strategydao.getBean(stragegyid); if
		 * (strategy == null) { return new ExecuteResult(false, "策略不存在"); } if
		 * (strategy.getUserid() != userid) { return new ExecuteResult(false,
		 * "您无权限生成策略兑换码"); }
		 * 
		 * return strategyRedeemcode(strategy); } catch (Exception e) {
		 * LOGGER.error("generateRedeemcode", e.fillInStackTrace()); } return
		 * new ExecuteResult(false, "系统异常");
		 ****/
		return null;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public RedeemCodeJson strategyRedeemcode(Strategy strategy) {
		
		Date date = new Date(System.currentTimeMillis());
		if (date.before(strategy.getStarttime())) {
			return RedeemCodeJson.ACTIVE_NOTSTART;
		}
		if (date.after(strategy.getEndtime())) {
			return RedeemCodeJson.ACTIVE_OVER;
		}
		// TODO 数据库应锁定对应此策略行记录。
		if (strategy.getSendnum() != 0
				&& strategy.getSended() >= strategy.getQuantity()) {
			return RedeemCodeJson.REDEEMCODE_OVER;
		}
		
		String code = DigestUtils.md5Encrypt(System.currentTimeMillis() + ""
				+ strategy.getSecuritycode()).substring(8, 24)
				.toUpperCase();
		RedeemCode rcode = new RedeemCode();
		rcode.setCode(code);
		// /rcode.setCodestatus(RedeemCodeStu.UNUSED);
		Timestamp now = new Timestamp(System.currentTimeMillis());
		// /rcode.setCratetime(now);
		Timestamp expiretime = new Timestamp(System.currentTimeMillis());
		expiretime.setDate(expiretime.getDate() + strategy.getValidityday());
		// /rcode.setExpiretime(expiretime);
		// /rcode.setStrategy(strategy);
		try {
			this.strategydao.createBean(rcode);
			this.strategydao.plusRedeemCodeSend(strategy.getId());
			
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			LOGGER.error("strategyRedeemcode", e.fillInStackTrace());
			return RedeemCodeJson.SYSTEM_ERROR;
		}
		
		return new RedeemCodeJson(RedeemCodeJson.SUCCESS, "", "", code);
	}
	
	@Override
	public Strategy getStrategyDetailinfo(int strategyid) {
		try {
			return this.strategydao.getBean(strategyid);
		} catch (Exception e) {
			LOGGER.error("getStrategyDetailinfo", e.fillInStackTrace());
			
		}
		return null;
	}
	
	@Override
	@Transactional
	public RedeemCodeJson generateRedeemcodeJson(int stragegyid) {
		// / HcodeInfo
		// codeinfo=MbayHCodeSupport.getMbayHcodeInstance().getHcodeInfo(mobile);
		// if(codeinfo==null){
		// return RedeemCodeJson.INVALID_MOBILE;
		// }
		Strategy strategy = this.getStrategyDetailinfo(stragegyid);
		/***
		 * if(strategy.getArea().getKey()!=Area.QUANGUO.getKey()){
		 * if(strategy.getArea().getKey()!=codeinfo.getProvcode()){ //不支持此的地区用户
		 * return RedeemCodeJson.NONSUPPORT_MOBILE; } }
		 * if(strategy.getTeloperator
		 * ().getKey()!=TeleOperator.THREENETS.getKey()){
		 * if(strategy.getTeloperator().getKey()!=codeinfo.getOperator()){
		 * //不支网络段用户 return RedeemCodeJson.NONSUPPORT_MOBILE; } }
		 ****/
		return strategyRedeemcode(strategy);
	}
	
	@Override
	@Transactional
	public ExecuteResult deleteStrategy(int strategyid) {
		try {
			Strategy strategy = this.strategydao.getBean(strategyid);
			int i = this.strategydao.deleteStragegy(strategyid, ChannelContext
					.getSessionChannel().getId());
			this.contextdao.updateUserStrategyInUserCount(ChannelContext
					.getSessionChannel().getId());
			if (i > 0) {
				// // this.assetsdao
				// .reduceLockedAmount(ChannelContext.getSessionChannel()
				// .getId(), strategy.getMbaybalance());
				return new ExecuteResult(true, "");
			} else {
				throw new Exception("");
			}
		} catch (Exception e) {
			LOGGER.error("deleteStrategy", e.fillInStackTrace());
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
		return new ExecuteResult(false, "删除失败");
	}
	
}
