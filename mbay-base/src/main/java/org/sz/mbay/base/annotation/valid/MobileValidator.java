package org.sz.mbay.base.annotation.valid;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

public class MobileValidator implements ConstraintValidator<Mobile, String> {
	
	private static String reg = "^0?(13|15|18|14|17)[0-9]{9}$";
	private static Pattern pat = Pattern.compile(reg);
	
	@Override
	public void initialize(Mobile mobile) {
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext arg1) {
		if (StringUtils.isEmpty(value)) {
			return true;
		}
		return pat.matcher(value).matches();
	}
}
