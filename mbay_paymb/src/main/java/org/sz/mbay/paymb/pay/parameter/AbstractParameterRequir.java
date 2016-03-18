package org.sz.mbay.paymb.pay.parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author han.han
 *
 */
 public class AbstractParameterRequir {

	public static String USER_NUMBER = "user_number";
	public static String _INPUT_CHARSET = "_input_charset";
	public static String SIGN_TYPE = "sign_type";
	public static String SIGN = "sign";

	@SuppressWarnings("serial")
	static final List<Parameter> essentialParameters = new ArrayList<Parameter>() {
		{
			add(new Parameter(USER_NUMBER, true, "^[\\dA-Za-z]{8}$"));
			add(new Parameter(_INPUT_CHARSET, true, "^(UTF-8|GBK|GB2312)$"));
			add(new Parameter(SIGN_TYPE, true, "^(MD5)$"));
			add(new Parameter(SIGN, true, "^[\\dA-Za-z]{32}$"));
		}
	};

}
