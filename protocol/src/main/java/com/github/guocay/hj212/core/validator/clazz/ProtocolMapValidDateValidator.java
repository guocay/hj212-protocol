package com.github.guocay.hj212.core.validator.clazz;

import com.github.guocay.hj212.core.validator.field.Date;
import com.github.guocay.hj212.core.validator.field.DateValidator;
import com.github.guocay.hj212.model.verify.ProtocolMap;
import jakarta.validation.ConstraintValidator;


/**
 * @author aCay
 */
public class ProtocolMapValidDateValidator
        extends ProtocolMapFieldValidator<FieldValidDate, Date>
        implements ConstraintValidator<FieldValidDate, ProtocolMap<String,?>> {

    public ProtocolMapValidDateValidator() {
        super(new DateValidator());
    }

    @Override
    public String getField(FieldValidDate field) {
        return field.field();
    }

    @Override
    public Date getAnnotation(FieldValidDate field) {
        return field.value();
    }

    @Override
    public boolean isFieldRegex(FieldValidDate field) {
        return field.regex();
    }

    @Override
    public String getFieldMessage(Date value) {
        return value.message();
    }
}
