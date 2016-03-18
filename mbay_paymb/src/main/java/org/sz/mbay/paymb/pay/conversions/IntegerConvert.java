package org.sz.mbay.paymb.pay.conversions;

import org.springframework.core.convert.converter.Converter;


public class IntegerConvert implements Converter<String, Integer> {

	@Override
	public Integer convert(String source) {
		return Integer.valueOf(source);
	}

}
