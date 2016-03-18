package org.sz.mbay.paymb.pay.service.impl;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.user.enums.TradeType;
import org.sz.mbay.channel.user.exception.MBAccountTradeException;
import org.sz.mbay.channel.user.service.MBAccountService;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.common.util.HttpSupport;
import org.sz.mbay.paymb.pay.response.TradeResponse;
import org.sz.mbay.paymb.pay.service.MBTradeService;

import net.sf.json.JSONObject;

@Service
public class MBTradeServiceImpl implements MBTradeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MBTradeServiceImpl.class);

	private static final JSONObject MB_CONNECT_FAIL_JSON = new JSONObject(false);

	private static final ExecuteResult MB_TRADE_SUCCESS = new ExecuteResult(true);

	private static final ExecuteResult MB_CONNECT_FAIL = new ExecuteResult(false, "MB账户连接异常");
	
	private static final ExecuteResult MB_REGISTER_FAIL = new ExecuteResult(false, "MB用户注册失败");

	@Autowired
	MBAccountService mbAccountService;

	/**
	 * MB支出
	 *
	 */
	@Override
	@Transactional
	public TradeResponse expenditure(String userNumber, String mbuid, double mb, String orderNumber, String desc) {
		LOGGER.info("执行商户{} {}MB收入", userNumber, mb);
		try {
			this.mbAccountService.income(mb, userNumber, TradeType.MALL_REDEEM, orderNumber,
					TradeType.MALL_REDEEM.getValue() + desc);
		} catch (MBAccountTradeException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOGGER.error("商户{}MB交易异常{}", userNumber, e.getMessage());
			return TradeResponse.Pay.SELLER_TRADE_FAIL;
		}

		// 执行MB用户MB支出
		LOGGER.info("执行MB用户{} {}MB支出请求", mbuid, mb);
		ExecuteResult executeResult = MBTrade.expenditure(mbuid, mb, orderNumber, desc);
		LOGGER.info("MB用户{}MB支出结果：{}", mbuid, executeResult.isSuccess() + executeResult.getMessage());
		if (!executeResult.isSuccess()) {
			LOGGER.info("MB支出交易失败");
			// 回滚事务
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return TradeResponse.mbTradeFail(executeResult.getMessage());
		}

		LOGGER.info("MB交易成功");
		return TradeResponse.PAY_SUCCESS;

	}

	/**
	 * MB余额
	 * 
	 */
	@Override
	public TradeResponse balance(String mbuid) {
		LOGGER.info("请求MB用户{}余额查询", mbuid);
		ExecuteResult tradeResponse = MBTrade.balance(mbuid);
		if (tradeResponse.equals(MB_CONNECT_FAIL)) {
			return TradeResponse.MB_COONCT_ERROR;
		}
		// 交易成功
		if (tradeResponse.isSuccess()) {
			return TradeResponse.BalanceQuery.querySuccess(Double.valueOf(tradeResponse.getMessage()));
		}
		return TradeResponse.mbTradeFail(tradeResponse.getMessage());
	}

	/**
	 * 第三方用户反兑MB
	 * 
	 */
	@Override
	@Transactional
	public TradeResponse redeemMB(String userNumber, String mbUid, double mb, String outOrderNumber, String desc) {

		LOGGER.info("用户兑换MB，执行商户{} {}MB支出：", userNumber, mb);
		try {
			this.mbAccountService.expenditure(mb, userNumber, TradeType.MALL_REDEEM, outOrderNumber, desc);
		} catch (MBAccountTradeException e) {
			LOGGER.error("商户{}MB交易异常{}", userNumber, e.getMessage());
			// 回滚事务
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return TradeResponse.Redeem.SELLER_TRADE_FAIL;
		}

		LOGGER.info("执行MB用户{}{}MB入账请求", mbUid, mb);
		ExecuteResult executeResult = MBTrade.income(mbUid, mb, outOrderNumber, desc);
		LOGGER.info("MB用户{}MB入账结果：{}", mbUid, executeResult.isSuccess() + executeResult.getMessage());
		if (!executeResult.isSuccess()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOGGER.info("用户{}MB入账交易失败", mbUid);
			// 回滚事务
			return TradeResponse.mbTradeFail(executeResult.getMessage());

		}
		LOGGER.info("用户{}MB入账交易成功", mbUid);
		return TradeResponse.PAY_SUCCESS;

	}

	private static class MBTrade {
		/**
		 * MB支出交易
		 * 
		 * @param mbUid
		 *            用户标识，以手机号标识
		 * @param mb
		 *            交易MB
		 * @param outOrderNumber
		 *            外部订单号
		 * @param desc
		 *            交易描述
		 * @return
		 */
		public static ExecuteResult expenditure(String mbUid, double mb, String outOrderNumber, String desc) {
			@SuppressWarnings("serial")
			TreeMap<String, String> pareamMap = new TreeMap<String, String>() {
				{
					put("moduleCode", MBServiceConfig.Pay.MODULE_CODE);
					put("mobile", mbUid);
					put("balance", String.valueOf(mb));
					put("tradeType", MBServiceConfig.Pay.TRADE_TYPE);
					put("description", StringUtils.isEmpty(desc) ? "mall trade" : desc);
					put("relatedNumber", outOrderNumber);
				}
			};
			JSONObject tradeResponse = connectRequest(MBServiceConfig.Pay.URL, pareamMap);
			if (tradeResponse.equals(MB_CONNECT_FAIL_JSON)) {
				return MB_CONNECT_FAIL;
			}
			if (tradeResponse.getBoolean("status")) {
				return MB_TRADE_SUCCESS;
			}
			JSONObject failData = tradeResponse.getJSONObject("data");
			return new ExecuteResult(false, failData.getString("msg"));
		}

		/**
		 * MB入账
		 * 
		 * @param mbUid
		 *            用户标识，以手机号标识
		 * @param mb
		 *            交易MB
		 * @param outOrderNumber
		 *            外部订单号
		 * @param desc
		 *            交易描述
		 * @return
		 */
		public static ExecuteResult income(String mbUid, double mb, String outOrderNumber, String desc) {
			@SuppressWarnings("serial")
			TreeMap<String, String> paramMap = new TreeMap<String, String>() {
				{
					put("moduleCode", MBServiceConfig.Redeem.MODULE_CODE);
					put("mobile", mbUid);
					put("balance", String.valueOf(mb));
					put("tradeType", MBServiceConfig.Redeem.TRADE_TYPE);
					put("description", StringUtils.isEmpty(desc) ? "mall trade" : desc);
					put("relatedNumber", outOrderNumber);
				}
			};
			JSONObject tradeResponse = connectRequest(MBServiceConfig.Redeem.URL, paramMap);
			if (tradeResponse.equals(MB_CONNECT_FAIL_JSON)) {
				return MB_CONNECT_FAIL;
			}
			if (tradeResponse.getBoolean("status")) {
				return MB_TRADE_SUCCESS;
			}
			JSONObject failData = tradeResponse.getJSONObject("data");
			String responseMsg = failData.getString("msg");
			if ("钱包用户不存在".equals(responseMsg)) {// 用户不存在，自动注册

				LOGGER.info("钱包用户不存在，注册钱包用户");
				boolean regsuccess = registerMBuser(mbUid);
				if (regsuccess) {
					//再次入账
					return income(mbUid, mb, outOrderNumber, desc);
				}
				return MB_REGISTER_FAIL;

			}
			return new ExecuteResult(false, responseMsg);
		}

		/**
		 * @param mbuid
		 *            账户标识，以手机号标识
		 * @return
		 */
		public static ExecuteResult balance(String mbUid) {
			@SuppressWarnings("serial")
			TreeMap<String, String> paramMap = new TreeMap<String, String>() {
				{
					put("moduleCode", MBServiceConfig.Balance.MODULE_CODE);
					put("mobile", mbUid);
				}
			};
			JSONObject tradeResponse = connectRequest(MBServiceConfig.Balance.URL, paramMap);
			if (tradeResponse.equals(MB_CONNECT_FAIL_JSON)) {
				return MB_CONNECT_FAIL;
			}
			// 交易成功
			if (tradeResponse.getBoolean("status")) {
				JSONObject jsonData = tradeResponse.getJSONObject("data");
				return new ExecuteResult(true, jsonData.getString("balance"));
			}
			JSONObject failData = tradeResponse.getJSONObject("data");
			return new ExecuteResult(false, failData.getString("msg"));
		}

		private static boolean registerMBuser(String mbuid) {
			TreeMap<String, String> regMap = new TreeMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 2331934967545398836L;

				{
					
					put("moduleCode", MBServiceConfig.USignUp.MODULE_CODE);
					put("mobile", mbuid);
					put("regSource", MBServiceConfig.USignUp.SIGN_SORUCE);

				}
			};
			JSONObject regResponse = connectRequest(MBServiceConfig.USignUp.URL, regMap);
			if (regResponse.getBoolean("status")) {
				return true;
			}
			JSONObject failData = regResponse.getJSONObject("data");
			String msg = failData.getString("msg");
			LOGGER.error("请求MB注册失败", msg);
			return false;

		}

		private static JSONObject connectRequest(String tradeUrl, TreeMap<String, String> paramMap) {
			assert tradeUrl != null;
			assert paramMap != null;
			
			StringBuilder standbySign = new StringBuilder(30);
			StringBuilder param = new StringBuilder(30);
			for (Map.Entry<String, String> entry : paramMap.entrySet()) {
				standbySign.append(entry.getKey() + "=" + entry.getValue() + "&");
				String value = DigestUtils.pbeEncrypt(entry.getValue());
				if (entry.getKey().equals("moduleCode")) {
					continue;
				}
				param.append("&" + entry.getKey() + "=" + value);

			}
			LOGGER.info("请求MB接口，代签名串：{}", standbySign.toString());
			String sign = DigestUtils.md5Encrypt(standbySign.toString());
			String url = tradeUrl + "?" + param.toString().substring(1) + "&signature=" + sign;
			LOGGER.info("HTTP URL：{}", url);
			JSONObject tradeResponse = null;
			try {
				String response = HttpSupport.connect(url);
				tradeResponse = JSONObject.fromObject(response);
			} catch (Exception e) {
				LOGGER.info("请求MB接口，Http连接异常：", e.fillInStackTrace());
				return MB_CONNECT_FAIL_JSON;
			}
			LOGGER.info("MB接口返回信息：{}", tradeResponse.toString());
			// 交易成功
			return tradeResponse;

		}

	}
}
