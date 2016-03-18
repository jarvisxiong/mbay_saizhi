package org.sz.mbay.paymb.pay.parameter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.mbay.paymb.pay.annotation.TradePolicy;

/**
 * @author han.han
 *
 */
public class BalanceQueryParameterRequir extends AbstractParameterRequir implements ParameterRequir {

	private static final Logger LOGGER = LoggerFactory.getLogger(BalanceQueryParameterRequir.class);
	
	public BalanceQueryParameterRequir(){
		
	}

	private static List<Parameter> balanceParameters = new ArrayList<Parameter>();

	public static final String MB_UCODE = "mb_ucode";

	static {
		if(LOGGER.isInfoEnabled()){
			LOGGER.info("Class "+BalanceQueryParameterRequir.class.getName() +"have loaded");			
		}
		
		balanceParameters.addAll(essentialParameters);
		balanceParameters.add(new Parameter(MB_UCODE, true, ""));
		ParameterManger.registerParameterRequir(TradePolicy.BALANCE, new BalanceQueryParameterRequir());
	}

	@Override
	public List<Parameter> getRequiredParameters() {
		return balanceParameters;
	}

}