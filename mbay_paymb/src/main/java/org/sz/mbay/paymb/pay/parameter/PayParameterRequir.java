package org.sz.mbay.paymb.pay.parameter;

import java.util.ArrayList;
import java.util.List;

import org.sz.mbay.paymb.pay.annotation.TradePolicy;

public class PayParameterRequir extends AbstractParameterRequir implements ParameterRequir {
	
	public PayParameterRequir(){
		
	}


	private static List<Parameter> payParameters = new ArrayList<Parameter>();

	public static String TOTAL_FEE = "total_fee";
	public static String DESC = "desc";
	public static String OUT_TRADE_NO = "out_trade_no";
	public static String MB_UID = "mb_uid";

	static {

		payParameters.addAll(essentialParameters);

		payParameters.add(new Parameter(TOTAL_FEE, true, "^[1-4][0-9]{0,2}|500$"));
		payParameters.add(new Parameter(DESC, false, ""));
		payParameters.add(new Parameter(OUT_TRADE_NO, true, "^[\\dA-Za-z]{1,64}$"));
		payParameters.add(new Parameter(MB_UID, true, "^0?(13|15|18|14|17)[0-9]{9}$"));

		ParameterManger.registerParameterRequir(TradePolicy.PAY, new PayParameterRequir());
	}

	@Override
	public List<Parameter> getRequiredParameters() {
		return payParameters;
	}

}
