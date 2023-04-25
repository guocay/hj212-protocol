package com.github.guocay.hj212.core.validator.clazz;

import com.github.guocay.hj212.core.validator.field.NumberArray;
import com.github.guocay.hj212.core.validator.field.NumberArrayValidator;
import com.github.guocay.hj212.model.verify.ProtocolMap;
import jakarta.validation.ConstraintValidator;

/**
 * @author aCay
 */
public class ProtocolMapNValidator
        extends ProtocolMapFieldValidator<FieldN, NumberArray>
        implements ConstraintValidator<FieldN, ProtocolMap<String,?>> {

    public ProtocolMapNValidator() {
        super(new NumberArrayValidator());
    }

    @Override
    public String getField(FieldN fieldN) {
        return fieldN.field();
    }

    @Override
    public NumberArray getAnnotation(FieldN fieldN) {
        return fieldN.value();
    }

    @Override
    public boolean isFieldRegex(FieldN fieldN) {
        return fieldN.regex();
    }

    @Override
    public String getFieldMessage(NumberArray value) {
        return value.message();
    }
}
