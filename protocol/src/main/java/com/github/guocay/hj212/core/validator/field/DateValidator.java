package com.github.guocay.hj212.core.validator.field;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 用于验证时间
 * @author aCay
 */
public class DateValidator implements ConstraintValidator<Date, String> {

    private String format;
    private boolean optional;

    @Override
    public void initialize(Date validDate) {
        this.format = validDate.format();
        this.optional = validDate.optional();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(value == null){
            return optional;
        }
        return isValidFormat(format, value);
    }

    public static boolean isValidFormat(String format, String value) {
        java.util.Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            if (value != null){
                date = sdf.parse(value);
                if (!value.equals(sdf.format(date))) {
                    date = null;
                }
            }

        } catch (ParseException ex) {
        }
        return date != null;
    }
}
