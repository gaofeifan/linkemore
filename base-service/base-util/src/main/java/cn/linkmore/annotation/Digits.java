package cn.linkmore.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import cn.linkmore.validator.DigitsValidator;
import cn.linkmore.validator.VehicleMarkCheckValidator;

/**
 * 整数校验
 * @author   GFF
 * @Date     2018年8月11日
 * @Version  v2.0
 */
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DigitsValidator.class)
@Documented
public @interface Digits {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String regex() default "";
}