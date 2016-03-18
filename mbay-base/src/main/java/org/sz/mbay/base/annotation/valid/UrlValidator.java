package org.sz.mbay.base.annotation.valid;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

public class UrlValidator implements ConstraintValidator<Url, String> {
	
	private static String reg = "^(\\w+:\\/\\/)?[\\w-]+(\\.[\\w-]+)+.*$";
	private static Pattern pat = Pattern.compile(reg);
	
	@Override
	public void initialize(Url url) {
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext arg1) {
		if (StringUtils.isEmpty(value)
				|| "http://".equalsIgnoreCase(value)
				|| "https://".equalsIgnoreCase(value)) {
			return true;
		}
		return pat.matcher(value).matches();
	}
	
}
