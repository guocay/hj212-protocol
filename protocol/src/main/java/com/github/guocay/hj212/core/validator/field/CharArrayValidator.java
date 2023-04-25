package com.github.guocay.hj212.core.validator.field;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 用于验证字符数组
 * @author aCay
 */
public class CharArrayValidator implements ConstraintValidator<CharArray, String> {

    private int max;

    @Override
    public void initialize(CharArray validDate) {
        this.max = validDate.len();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        int len;
        if(value == null){
            len = 0;
        }else{
            len = value.length();
        }
        return len <= max;
    }

}
