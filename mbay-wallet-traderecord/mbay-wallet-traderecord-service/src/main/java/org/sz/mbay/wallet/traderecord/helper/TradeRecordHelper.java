//package org.sz.mbay.wallet.traderecord.helper;
//
//import java.math.BigDecimal;
//
//import org.apache.commons.lang.StringUtils;
//import org.joda.time.DateTime;
//import org.sz.mbay.base.context.SpringApplicationContext;
//import org.sz.mbay.base.service.UtilService;
//import org.sz.mbay.wallet.traderecord.bean.TradeRecord;
//import org.sz.mbay.wallet.traderecord.enums.AccountType;
//import org.sz.mbay.wallet.traderecord.enums.PaymentType;
//import org.sz.mbay.wallet.traderecord.enums.TradeType;
//
///**
// * 交易记录创建中心
// * 
// * @author jerry
// */
//public class TradeRecordHelper {
//	
//	private static UtilService utilService;
//	
//	public static UtilService getUtilService() {
//		if (utilService == null) {
//			utilService = (UtilService) SpringApplicationContext
//					.getBean("utilServiceImpl");
//		}
//		return utilService;
//	}
//	
//	/*
//	 * 通用交易记录
//	 * @param accountType
//	 * @param paymentType
//	 * @param amount
//	 * @param masterNumber
//	 * @param reciprocalNumber
//	 * @param tradeType
//	 * @param description
//	 * @param relatedNumber
//	 * @param timeFix
//	 * @return
//	 */
//	private static synchronized TradeRecord createCommonRecord(
//			AccountType accountType,
//			PaymentType paymentType,
//			BigDecimal amount,
//			String masterTel,
//			String reciprocalTel,
//			TradeType tradeType,
//			String description,
//			String relatedNumber,
//			int timeFix) {
//		DateTime time = DateTime.now().plusSeconds(timeFix);
//		String timeStr = time.toString("yyyyMMddHHmmss");
//		
//		int order = tradeType.ordinal();
//		String tradeStr = String.format("%03d", order);
//		
//		int index = getUtilService().getNextIndex(TradeRecord.class);
//		
//		TradeRecord tradeRecord = new TradeRecord();
//		tradeRecord.setSerialNumber(timeStr + "00" + tradeStr + "00" + index);
//		tradeRecord.setAccountType(accountType);
//		tradeRecord.setMasterTelephone(masterTel);
//		tradeRecord.setReciprocalTelephone(reciprocalTel);
//		tradeRecord.setTradeType(tradeType);
//		tradeRecord.setCreateTime(time);
//		tradeRecord.setPaymentType(paymentType);
//		tradeRecord.setAmount(amount);
//		tradeRecord.setDescription(description);
//		tradeRecord.setRelatedNumber(relatedNumber);
//		return tradeRecord;
//	}
//	
//	/**
//	 * 账户增减款通用记录
//	 * 
//	 * @param masterNumber
//	 * @param amount
//	 * @param paymentType
//	 * @param tradeType
//	 * @return
//	 */
//	public static synchronized TradeRecord createCommonBalanceRecord(
//			String masterTel,
//			BigDecimal amount,
//			AccountType accountType,
//			PaymentType paymentType,
//			TradeType tradeType,
//			String relatedNumber,
//			String desc) {
//		StringBuffer sb = new StringBuffer();
//		sb.append(tradeType.getValue());
//		sb.append("：");
//		if (accountType == AccountType.MBAY_ACCOUNT) {
//			sb.append("MB账户");
//		}
//		if (paymentType == PaymentType.INCOME) {
//			sb.append("收入");
//		} else if (paymentType == PaymentType.EXPENSE) {
//			sb.append("支出");
//		}
//		sb.append(amount.setScale(2, BigDecimal.ROUND_HALF_UP));
//		sb.append("MB");
//		
//		if (StringUtils.isNotEmpty(desc)) {
//			sb.append("【");
//			sb.append(desc);
//			sb.append("】");
//		}
//		
//		return createCommonRecord(
//				accountType,
//				paymentType,
//				amount,
//				masterTel,
//				null,
//				tradeType,
//				sb.toString(),
//				relatedNumber,
//				0);
//	}
//	
//	/**
//	 * 流量红包摇到流量交易记录
//	 * 
//	 * @return
//	 */
//	public static synchronized TradeRecord createTrafficRedTrafficRecord(
//			String masterTel, String relatedNumber, String desc) {
//		StringBuffer sb = new StringBuffer();
//		sb.append(TradeType.TRAFFIC_RED_TRAFFIC.getValue());
//		sb.append("：");
//		sb.append("兑换【");
//		sb.append(desc);
//		sb.append("】");
//		
//		return createCommonRecord(
//				AccountType.NO_ACCOUNT,
//				PaymentType.TRAFFIC,
//				null,
//				masterTel,
//				null,
//				TradeType.TRAFFIC_RED_TRAFFIC,
//				sb.toString(),
//				relatedNumber,
//				0);
//	}
//}
