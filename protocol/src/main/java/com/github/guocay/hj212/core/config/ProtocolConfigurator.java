package com.github.guocay.hj212.core.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.guocay.hj212.core.ProtocolGenerator;
import com.github.guocay.hj212.core.ProtocolParser;
import com.github.guocay.hj212.core.converter.DataConverter;
import com.github.guocay.hj212.core.converter.DataReverseConverter;
import com.github.guocay.hj212.core.decode.CpDataLevelMapDeserializer;
import com.github.guocay.hj212.core.decode.DataDeserializer;
import com.github.guocay.hj212.core.decode.DataLevelMapDeserializer;
import com.github.guocay.hj212.core.decode.PackLevelDeserializer;
import com.github.guocay.hj212.core.encode.CpDataLevelMapDataSerializer;
import com.github.guocay.hj212.core.encode.DataSerializer;
import com.github.guocay.hj212.core.encode.PackLevelSerializer;
import com.github.guocay.hj212.core.encode.ProtocolCpMapPathValueSegmentSerializer;
import com.github.guocay.hj212.segment.config.Configurator;
import com.github.guocay.hj212.segment.config.MultipleConfiguratorAdapter;
import com.github.guocay.hj212.segment.core.SegmentGenerator;
import com.github.guocay.hj212.segment.core.SegmentParser;
import com.github.guocay.hj212.segment.core.decode.MapSegmentDeserializer;
import com.github.guocay.hj212.segment.core.decode.StringMapSegmentDeserializer;
import com.github.guocay.hj212.segment.core.encode.MapSegmentSerializer;
import jakarta.validation.Validator;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

/**
 * T212配置器
 * @author aCay
 */
public class ProtocolConfigurator
        extends MultipleConfiguratorAdapter {

    private int segmentParserFeature;
    private int parserFeature;

    private int verifyFeature;
    private Validator validator;
    private ObjectMapper objectMapper;

    private int segmentGeneratorFeature;
    private final int generatorFeature = 0;

    public void setSegmentParserFeature(int segmentParserFeature) {
        this.segmentParserFeature = segmentParserFeature;
    }

    public void setParserFeature(int parserFeature) {
        this.parserFeature = parserFeature;
    }

    public void setVerifyFeature(int verifyFeature) {
        this.verifyFeature = verifyFeature;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void setSegmentGeneratorFeature(int segmentGeneratorFeature) {
        this.segmentGeneratorFeature = segmentGeneratorFeature;
    }

    class SegmentParserConfigurator implements Configurator<SegmentParser> {
        @Override
        public void config(SegmentParser parser) {
            ProtocolConfigurator.this.configure(parser);
        }
    }
    class T212ParserConfigurator implements Configurator<ProtocolParser> {
        @Override
        public void config(ProtocolParser parser) {
            ProtocolConfigurator.this.configure(parser);
        }
    }
    class PackLevelDeserializerConfigurator implements Configurator<PackLevelDeserializer> {
        @Override
        public void config(PackLevelDeserializer deserializer) {
            ProtocolConfigurator.this.configure(deserializer);
        }
    }
    class DataLevelMapDeserializerConfigurator implements Configurator<DataLevelMapDeserializer> {
        @Override
        public void config(DataLevelMapDeserializer deserializer) {
            ProtocolConfigurator.this.configure(deserializer);
        }
    }
    class CpDataLevelMapDeserializerConfigurator implements Configurator<CpDataLevelMapDeserializer> {
        @Override
        public void config(CpDataLevelMapDeserializer deserializer) {
            ProtocolConfigurator.this.configure(deserializer);
        }
    }
    class DataDeserializerConfigurator implements Configurator<DataDeserializer> {
        @Override
        public void config(DataDeserializer deserializer) {
            ProtocolConfigurator.this.configure(deserializer);
        }
    }
    class DataConverterConfigurator implements Configurator<DataConverter> {
        @Override
        public void config(DataConverter converter) {
            ProtocolConfigurator.this.configure(converter);
        }
    }

    class SegmentGeneratorConfigurator implements Configurator<SegmentGenerator> {
        @Override
        public void config(SegmentGenerator generator) {
            ProtocolConfigurator.this.configure(generator);
        }
    }
    class T212GeneratorConfigurator implements Configurator<ProtocolGenerator> {
        @Override
        public void config(ProtocolGenerator generator) {
            ProtocolConfigurator.this.configure(generator);
        }
    }
    class PackLevelSerializerConfigurator implements Configurator<PackLevelSerializer> {
        @Override
        public void config(PackLevelSerializer serializer) {
            ProtocolConfigurator.this.configure(serializer);
        }
    }
    class CpDataLevelMapDataSerializerConfigurator implements Configurator<CpDataLevelMapDataSerializer> {
        @Override
        public void config(CpDataLevelMapDataSerializer serializer) {
            ProtocolConfigurator.this.configure(serializer);
        }
    }
    class DataSerializerConfigurator implements Configurator<DataSerializer> {
        @Override
        public void config(DataSerializer serializer) {
            ProtocolConfigurator.this.configure(serializer);
        }
    }
    class DataReverseConverterConfigurator implements Configurator<DataReverseConverter> {
        @Override
        public void config(DataReverseConverter converter) {
            ProtocolConfigurator.this.configure(converter);
        }
    }

    @SuppressWarnings("rawtypes")
	@Override
    public Collection<Configurator> configurators() {
        return Stream.of(
                new SegmentParserConfigurator(),
                new T212ParserConfigurator(),
                new PackLevelDeserializerConfigurator(),
                new DataLevelMapDeserializerConfigurator(),
                new CpDataLevelMapDeserializerConfigurator(),
                new DataDeserializerConfigurator(),
                new DataConverterConfigurator(),

                new SegmentGeneratorConfigurator(),
                new T212GeneratorConfigurator(),
                new PackLevelSerializerConfigurator(),
                new CpDataLevelMapDataSerializerConfigurator(),
                new DataSerializerConfigurator(),
                new DataReverseConverterConfigurator()
        )
                .collect(Collectors.toSet());
    }

    /**
     * 泛型方法实现
     * @see Configurator#config(Object)
     * @param parser
     */
    public void configure(SegmentParser parser){
        if(parser.currentToken() == null){
            parser.initToken();
        }
        parser.setParserFeature(segmentParserFeature);
    }

    /**
     * 泛型方法实现
     * @see Configurator#config(Object)
     * @param parser
     */
    public void configure(ProtocolParser parser){
        parser.setParserFeature(parserFeature);
    }

    /**
     * 泛型方法实现
     * @see Configurator#config(Object)
     * @param deserializer
     */
    public void configure(PackLevelDeserializer deserializer){
        deserializer.setVerifyFeature(verifyFeature);
        deserializer.setParserConfigurator(this::configure);
    }

    /**
     * 泛型方法实现
     * @see Configurator#config(Object)
     * @param deserializer
     */
    public void configure(DataLevelMapDeserializer deserializer){
        deserializer.setVerifyFeature(verifyFeature);
        deserializer.setValidator(validator);
        deserializer.setSegmentParserConfigurator(this::configure);
        deserializer.setSegmentDeserializer(new StringMapSegmentDeserializer());
    }

    /**
     * 泛型方法实现
     * @see Configurator#config(Object)
     * @param deserializer
     */
    public void configure(CpDataLevelMapDeserializer deserializer){
        deserializer.setVerifyFeature(verifyFeature);
        deserializer.setValidator(validator);
        deserializer.setSegmentParserConfigurator(this::configure);
        deserializer.setSegmentDeserializer(new MapSegmentDeserializer());
    }

    /**
     * 泛型方法实现
     * @see Configurator#config(Object)
     * @param deserializer
     */
    public void configure(DataDeserializer deserializer){
        deserializer.setVerifyFeature(verifyFeature);
        deserializer.setValidator(validator);
        deserializer.setSegmentParserConfigurator(this::configure);
        deserializer.setSegmentDeserializer(new MapSegmentDeserializer());
        deserializer.setDataConverterConfigurator(this::configure);
    }

    /**
     * 泛型方法实现
     * @see Configurator#config(Object)
     * @param dataConverter
     */
    public void configure(DataConverter dataConverter){
        ObjectMapper objectMapper = this.objectMapper;
        if(objectMapper == null){
            objectMapper = new ObjectMapper()
                    .configure(FAIL_ON_UNKNOWN_PROPERTIES,false);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
        dataConverter.setObjectMapper(objectMapper);
    }


    /**
     * 泛型方法实现
     * @see Configurator#config(Object)
     * @param generator
     */
    private void configure(SegmentGenerator generator){
        if(generator.nextToken() == null){
            generator.initToken();
        }
        generator.setGeneratorFeature(segmentGeneratorFeature);
    }

    /**
     * 泛型方法实现
     * @see Configurator#config(Object)
     * @param generator
     */
    private void configure(ProtocolGenerator generator) {
        generator.setGeneratorFeature(generatorFeature);
    }

    /**
     * 泛型方法实现
     * @see Configurator#config(Object)
     * @param serializer
     */
    private void configure(PackLevelSerializer serializer) {
        serializer.setVerifyFeature(verifyFeature);
        serializer.setGeneratorConfigurator(this::configure);
    }

    /**
     * 泛型方法实现
     * @see Configurator#config(Object)
     * @param serializer
     */
    public void configure(CpDataLevelMapDataSerializer serializer){
        serializer.setVerifyFeature(verifyFeature);
        serializer.setValidator(validator);
        serializer.setSegmentGeneratorConfigurator(this::configure);
        serializer.setSegmentSerializer(new MapSegmentSerializer(new ProtocolCpMapPathValueSegmentSerializer()));
    }

    /**
     * 泛型方法实现
     * @see Configurator#config(Object)
     * @param serializer
     */
    private void configure(DataSerializer serializer) {
        serializer.setVerifyFeature(verifyFeature);
        serializer.setValidator(validator);
        serializer.setSegmentGeneratorConfigurator(this::configure);
        serializer.setSegmentSerializer(new MapSegmentSerializer(new ProtocolCpMapPathValueSegmentSerializer()));
        serializer.setDataReverseConverterConfigurator(this::configure);
    }

    /**
     * 泛型方法实现
     * @see Configurator#config(Object)
     * @param dataReverseConverter
     */
    private void configure(DataReverseConverter dataReverseConverter) {
        ObjectMapper objectMapper = this.objectMapper;
        if(objectMapper == null){
            objectMapper = new ObjectMapper()
                    .configure(FAIL_ON_UNKNOWN_PROPERTIES,false);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
        dataReverseConverter.setObjectMapper(objectMapper);
    }

}
