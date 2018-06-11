package cn.linkmore.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import cn.linkmore.annotation.Digits;


public class DigitsValidator implements ConstraintValidator<Digits, String> {

	String regex = "^[1-9]+[0-9]*$";

	@Override
	public void initialize(Digits digits) {
		if(StringUtils.isNoneBlank(digits.regex())) {
			regex = digits.regex();
		}
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}



}