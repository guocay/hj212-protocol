package com.github.guocay.hj212.core.validator.field;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * 验证 数字集合
 * @author aCay
 */
public class NumberArrayValidator implements ConstraintValidator<NumberArray, String> {

    private int int_len_max;
    private int fraction_len_max;
    private double min;
    private double max;
    private boolean optional;

    @Override
    public void initialize(NumberArray n) {
        this.int_len_max = n.integer();
        this.fraction_len_max = n.fraction();
        this.min = n.min();
        this.max = n.max();
        this.optional = n.optional();

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(value == null){
            return optional;
        }

        try{
            BigDecimal decimal = new BigDecimal(value);
            int int_len = getNumLength(decimal.longValue());
            int fraction_len = decimal.scale();
            boolean result = int_len <= int_len_max &&
                    fraction_len <= fraction_len_max;

            if(min > 0 &&
                    decimal.doubleValue() < min){
                return false;
            }
            if(max > 0 &&
                    decimal.doubleValue() > max){
                return false;
            }
            return result;
        }catch (NumberFormatException e){
            return false;
        }
    }

    @Deprecated
    public static boolean isValidFormat(String format, String value) {
        Number number = null;
        try {
            DecimalFormat sdf = new DecimalFormat(format);
            if (value != null){
                number = sdf.parse(value);
                //12345 + #### = 12345 位数不对
                if (!value.equals(sdf.format(number))) {
                    number = null;
                }
            }

        } catch (ParseException ex) {
        }
        return number != null;
    }

    private static int getNumLength(long num){
        num = num>0?num:-num;
        if (num==0) {
            return 1;
        }
        return (int) Math.log10(num)+1;
    }
}
