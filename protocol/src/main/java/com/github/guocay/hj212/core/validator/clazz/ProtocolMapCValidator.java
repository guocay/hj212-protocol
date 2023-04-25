package com.github.guocay.hj212.core.validator.clazz;

import com.github.guocay.hj212.core.validator.field.CharArray;
import com.github.guocay.hj212.core.validator.field.CharArrayValidator;
import com.github.guocay.hj212.model.verify.ProtocolMap;
import jakarta.validation.ConstraintValidator;


/**
 * @author aCay
 */
public class ProtocolMapCValidator
        extends ProtocolMapFieldValidator<FieldC, CharArray>
        implements ConstraintValidator<FieldC, ProtocolMap<String,?>> {

    public ProtocolMapCValidator() {
        super(new CharArrayValidator());
    }

    @Override
    public String getField(FieldC fieldC) {
        return fieldC.field();
    }

    @Override
    public CharArray getAnnotation(FieldC fieldC) {
        return fieldC.value();
    }

    @Override
    public boolean isFieldRegex(FieldC fieldC) {
        return fieldC.regex();
    }

    @Override
    public String getFieldMessage(CharArray value) {
        return value.message();
    }

}
