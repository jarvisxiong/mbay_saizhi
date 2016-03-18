package org.sz.mbay.paymb.pay.service;

import org.sz.mbay.paymb.pay.response.TradeResponse;

/**
 * @author han.han
 *
 */
public interface MBTradeService {

	/**
	 * MB支出交易
	 * 
	 * @param userNumber
	 *            商户编号
	 * @param mbuid
	 *            MB用户编号
	 * @param mb
	 *            消费MB
	 * @param orderNumber
	 *            订单号
	 * @return
	 */
	public TradeResponse expenditure(String userNumber, String MBUid, double mb, String orderNumber, String desc);

	/**
	 * MB余额查询交易
	 * 
	 * @param mbuid
	 *            MB用户编号
	 * @return
	 */
	public TradeResponse balance(String mbuid);

	/**
	 * MB反兑交易
	 * 
	 * @param userNumber
	 *            商户编号
	 * @param mbuid
	 *            MB用户标识
	 * @param mb
	 *            兑换额度
	 * @param orderNumber
	 *            商家订单号
	 * @param desc
	 *            交易描述
	 * @return
	 */
	public TradeResponse redeemMB(String userNumber, String MBUid, double mb, String orderNumber, String desc);


}
