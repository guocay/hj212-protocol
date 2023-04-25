package com.github.guocay.hj212.core.validator;

import com.github.guocay.hj212.model.DataFlag;
import com.github.guocay.hj212.model.verify.DataElement;
import com.github.guocay.hj212.model.verify.ProtocolMap;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * 字段忽略
 * @author aCay
 */
@SuppressWarnings("rawtypes")
public class ProtocolMapFieldMissingValidator
        implements ConstraintValidator<FieldMissing, ProtocolMap> {

    @SuppressWarnings("unused")
	private FieldMissing fieldMissing;


    @Override
    public void initialize(FieldMissing fieldMissing) {
        this.fieldMissing = fieldMissing;
    }

    @SuppressWarnings("unchecked")
	@Override
    public boolean isValid(ProtocolMap value, ConstraintValidatorContext constraintValidatorContext) {
        Map<String, String> result = value;

        Stream<DataElement> stream = Stream.of(DataElement.values())
                .filter(DataElement::isRequired);
        if(result.containsKey(DataElement.Flag.name())){
            String f = result.get(DataElement.Flag.name());
            int flag = Integer.valueOf(f);
            if(DataFlag.D.isMarked(flag)){
                stream = Stream.concat(stream,Stream.of(DataElement.PNO, DataElement.PNUM));
            }
        }

        Optional<DataElement> missing = stream
                .filter(e -> !result.containsKey(e.name()))
                .findFirst();
        if(missing.isPresent()){
            return false;
        }
        return true;
    }

}
