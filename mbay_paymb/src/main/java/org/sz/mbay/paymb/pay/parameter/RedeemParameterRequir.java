package org.sz.mbay.paymb.pay.parameter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.mbay.paymb.pay.annotation.TradePolicy;

public class RedeemParameterRequir extends AbstractParameterRequir implements ParameterRequir {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedeemParameterRequir.class);
	
	public RedeemParameterRequir(){
		
	}

	private static List<Parameter> redeemParameters = new ArrayList<Parameter>();

	public static String TOTAL_FEE = "total_fee";
	public static String DESC = "desc";
	public static String OUT_TRADE_NO = "out_trade_no";
	public static String MB_UID = "mb_uid";

	static {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Class " + RedeemParameterRequir.class.getName() + "have loaded");
		}

		redeemParameters.addAll(essentialParameters);

		redeemParameters.add(new Parameter(TOTAL_FEE, true, "^[1-4][0-9]{0,2}|500$"));
		redeemParameters.add(new Parameter(DESC, false, "^[\\dA-Za-z]{0,200}$"));
		redeemParameters.add(new Parameter(OUT_TRADE_NO, true, "^[\\dA-Za-z]{1,64}$"));
		redeemParameters.add(new Parameter(MB_UID, true, "^0?(13|15|18|14|17)[0-9]{9}$"));

		ParameterManger.registerParameterRequir(TradePolicy.REDEEM, new RedeemParameterRequir());
	}

	@Override
	public List<Parameter> getRequiredParameters() {
		return redeemParameters;
	}

}
