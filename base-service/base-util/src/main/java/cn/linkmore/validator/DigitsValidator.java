package cn.linkmore.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import cn.linkmore.annotation.Digits;


/**
 * 整数校验
 * @author   GFF
 * @Date     2018年8月11日
 * @Version  v2.0
 */
public class DigitsValidator implements ConstraintValidator<Digits, Object> {

	String regex = "^[1-9]|[1-9][0-9]*$";

	@Override
	public void initialize(Digits digits) {
		if(StringUtils.isNotBlank(digits.regex())) {
			regex = digits.regex();
		}
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value.toString());
		return matcher.matches();
	}



}
