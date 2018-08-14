package cn.linkmore.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import cn.linkmore.validator.VehicleMarkCheckValidator;

/**
 * 车牌号校验注释
 * @author   GFF
 * @Date     2018年8月11日
 * @Version  v2.0
 */
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = VehicleMarkCheckValidator.class)
@Documented
public @interface VehicleMarkCheck {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] values();
    
    int[] lengths();
}