package com.github.guocay.hj212.core.validator.clazz;

import com.github.guocay.hj212.exception.ProtocolFormatException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintViolation;

import java.lang.annotation.Annotation;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author aCay
 */
public abstract class FieldValidator<A extends Annotation,V,AF extends Annotation,FV>
        implements ConstraintValidator<A, V> {

    protected String field;
    private AF af;
    private ConstraintValidator<AF,FV> constraintValidator;

    public FieldValidator(ConstraintValidator<AF,FV> constraintValidator){
        this.constraintValidator = constraintValidator;
    }

    @Override
    public void initialize(A a) {
        this.field = getField(a);
        this.af = getAnnotation(a);
        constraintValidator.initialize(af);
    }

    @Override
    public boolean isValid(V value, ConstraintValidatorContext constraintValidatorContext) {
        FV fv = getFieldValue(value,field);
        boolean result = constraintValidator.isValid(fv,constraintValidatorContext);
        if(!result){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(getFieldMessage(af))
                    .addPropertyNode(field).addConstraintViolation();
        }
        return result;
    }

    public abstract String getField(A a);
    public abstract AF getAnnotation(A a);
    public abstract FV getFieldValue(V value,String field);
    public abstract String getFieldMessage(AF value);

    @SuppressWarnings("rawtypes")
	public static void create_format_exception(Set<? extends ConstraintViolation> constraintViolationSet, Object result) throws ProtocolFormatException {
        Map<String, String> typePropertys = constraintViolationSet
                .stream()
                .collect(Collectors.groupingBy(
                        ConstraintViolation::getMessage,
                        new Collector<ConstraintViolation,StringJoiner,String>(){

                            @Override
                            public Supplier<StringJoiner> supplier() {
                                return () -> new StringJoiner(",", "", "");
                            }

                            @Override
                            public BiConsumer<StringJoiner, ConstraintViolation> accumulator() {
                                return (s,cv) -> s.add("'" + cv.getPropertyPath() + "'");
                            }

                            @Override
                            public BinaryOperator<StringJoiner> combiner() {
                                return StringJoiner::merge;
                            }

                            @Override
                            public Function<StringJoiner, String> finisher() {
                                return StringJoiner::toString;
                            }

                            @Override
                            public Set<Characteristics> characteristics() {
                                return Collections.emptySet();
                            }
                        }
                ));
        String msg = typePropertys.entrySet()
                .stream()
                .map(kv -> "\t"
                        + kv.getKey()
                        + " -> "
                        + kv.getValue())
                .collect(Collectors.joining("\n"));
        throw new ProtocolFormatException("Validate error\n" + msg)
                .withResult(result);
    }

    @SuppressWarnings("rawtypes")
	public static void create_format_exception2(Set<? extends ConstraintViolation> constraintViolationSet) throws ProtocolFormatException {
        Map<String, List<ConstraintViolation>> typeCVs = constraintViolationSet
                .stream()
                .collect(Collectors.groupingBy(
                        ConstraintViolation::getMessage,
                        Collectors.toList())
                );

        Map<String, String> typePropertys = typeCVs.entrySet()
                .stream()
                .map(kv -> {
                    String pps = kv.getValue()
                            .stream()
                            .map(cv -> "'" + cv.getPropertyPath() + "'")
                            .collect(Collectors.joining(","));
                    return new AbstractMap.SimpleEntry<>(kv.getKey(),pps);
                })
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

        String msg = typePropertys.entrySet()
                .stream()
                .map(kv -> "\t"
                        + kv.getKey()
                        + " -> "
                        + kv.getValue())
                .collect(Collectors.joining("\n"));
        throw new ProtocolFormatException("Validate error\n" + msg);
    }
}
