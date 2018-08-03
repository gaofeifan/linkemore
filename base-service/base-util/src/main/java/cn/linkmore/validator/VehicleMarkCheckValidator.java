package cn.linkmore.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import cn.linkmore.annotation.VehicleMarkCheck;

public class VehicleMarkCheckValidator implements ConstraintValidator<VehicleMarkCheck, String> {

	private String[] values;

	private int[] lengths;

	@Override
	public void initialize(VehicleMarkCheck vm) {
		values = vm.values();
		lengths = vm.lengths();
		// var
		// xreg=/^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}(([0-9]{5}[DF]$)|([DF][A-HJ-NP-Z0-9][0-9]{4}$))/;
		// var
		// creg=/^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳]{1}$/;
	}

	@Override
	public boolean isValid(String str, ConstraintValidatorContext context) {
		if(StringUtils.isBlank(str)) {
			return true;
		}
		for (int i = 0; i < lengths.length; i++) {
			if (str.length() == lengths[i]) {
				Pattern pattern = Pattern.compile(values[i]);
				Matcher matcher = pattern.matcher(str);
				return matcher.matches();
			}
		}
		return false;
	}

}
