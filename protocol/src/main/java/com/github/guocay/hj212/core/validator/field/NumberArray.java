package com.github.guocay.hj212.core.validator.field;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 数值字符串
 * @author aCay
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(NumberArray.List.class)
@Constraint(validatedBy = NumberArrayValidator.class)
public @interface NumberArray {

    /**
     * @return 整数最大位数
     */
    int integer();

    /**
     * @return 小数最大位数
     */
    int fraction() default 0;

    /**
     * @return 最小值
     */
    double min() default -1;

    /**
     * @return 最大值
     */
    double max() default -1;

    String message() default "invalid N type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean optional() default true;


    /**
     * Defines several {@link NumberArray} annotations on the same element.
     *
     * @see NumberArray
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    @interface List {

        NumberArray[] value();
    }
}
