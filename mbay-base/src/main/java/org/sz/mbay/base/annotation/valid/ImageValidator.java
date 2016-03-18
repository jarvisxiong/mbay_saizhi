package org.sz.mbay.base.annotation.valid;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class ImageValidator
		implements ConstraintValidator<Image, MultipartFile> {
		
	private static String reg = ".*\\.(jpg|png|jpeg|gif)$";
	private static Pattern pat = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
	
	@Override
	public void initialize(Image img) {
	}
	
	@Override
	public boolean isValid(MultipartFile file,
			ConstraintValidatorContext arg1) {
		if (file == null || file.isEmpty()) {
			return true;
		}
		String name = file.getOriginalFilename();
		return pat.matcher(name).matches();
	}
	
}
