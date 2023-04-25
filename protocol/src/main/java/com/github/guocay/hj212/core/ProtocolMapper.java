package com.github.guocay.hj212.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.guocay.hj212.core.config.ProtocolConfigurator;
import com.github.guocay.hj212.core.decode.CpDataLevelMapDeserializer;
import com.github.guocay.hj212.core.decode.DataDeserializer;
import com.github.guocay.hj212.core.decode.DataLevelMapDeserializer;
import com.github.guocay.hj212.core.decode.PackLevelDeserializer;
import com.github.guocay.hj212.core.decode.ProtocolDeserializer;
import com.github.guocay.hj212.core.encode.CpDataLevelMapDataSerializer;
import com.github.guocay.hj212.core.encode.DataSerializer;
import com.github.guocay.hj212.core.encode.PackLevelSerializer;
import com.github.guocay.hj212.core.encode.ProtocolSerializer;
import com.github.guocay.hj212.core.feature.ParserFeature;
import com.github.guocay.hj212.core.feature.VerifyFeature;
import com.github.guocay.hj212.exception.ProtocolFormatException;
import com.github.guocay.hj212.model.Data;
import com.github.guocay.hj212.segment.config.Feature;
import com.github.guocay.hj212.segment.core.feature.SegmentParserFeature;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * T212映射器
 * @author aCay
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class ProtocolMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProtocolMapper.class);

    private static ProtocolFactory t212FactoryProtoType;

    static {
        try {
            t212FactoryProtoType = new ProtocolFactory();
            //注册 反序列化器
            t212FactoryProtoType.deserializerRegister(CpDataLevelMapDeserializer.class);
            t212FactoryProtoType.deserializerRegister(DataLevelMapDeserializer.class);
            t212FactoryProtoType.deserializerRegister(PackLevelDeserializer.class);
            t212FactoryProtoType.deserializerRegister(Map.class, CpDataLevelMapDeserializer.class);
            t212FactoryProtoType.deserializerRegister(Data.class, DataDeserializer.class);
            //默认 反序列化器
            t212FactoryProtoType.deserializerRegister(Object.class, CpDataLevelMapDeserializer.class);

            //注册 序列化器
            t212FactoryProtoType.serializerRegister(PackLevelSerializer.class);
            t212FactoryProtoType.serializerRegister(Data.class, DataSerializer.class);
            t212FactoryProtoType.serializerRegister(Map.class, CpDataLevelMapDataSerializer.class);
            //没有默认 序列化器
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private int verifyFeatures;
    private int parserFeatures;
    private final ProtocolFactory factory;
    private ProtocolConfigurator configurator;
    private final Validator validator;
    private final ObjectMapper objectMapper;


    public ProtocolMapper(){
        this.factory = t212FactoryProtoType.copy();
        this.configurator = new ProtocolConfigurator();
        this.validator = factory.validator();
        this.objectMapper = factory.objectMapper();
    }

    public static ProtocolMapper getInstance(){
        return new ProtocolMapper();
    }


    /*
    /**********************************************************
    /* Configuration
    /**********************************************************
     */
    private static final int SEGMENT_FEATURE_BIT_OFFSET = 8;

    public ProtocolMapper enableDefaultParserFeatures() {
        parserFeatures = Feature.collectFeatureDefaults(SegmentParserFeature.class);
        parserFeatures = parserFeatures << SEGMENT_FEATURE_BIT_OFFSET;
        parserFeatures = parserFeatures | Feature.collectFeatureDefaults(ParserFeature.class);
        return this;
    }

    public ProtocolMapper enableDefaultVerifyFeatures() {
        verifyFeatures = verifyFeatures | Feature.collectFeatureDefaults(VerifyFeature.class);
        return this;
    }


    public ProtocolMapper enable(SegmentParserFeature feature) {
        parserFeatures = parserFeatures | feature.getMask() << SEGMENT_FEATURE_BIT_OFFSET;
        return this;
    }

    public ProtocolMapper enable(ParserFeature feature) {
        parserFeatures = parserFeatures | feature.getMask();
        return this;
    }

    public ProtocolMapper enable(VerifyFeature feature) {
        verifyFeatures = verifyFeatures | feature.getMask();
        return this;
    }


    public ProtocolMapper disable(SegmentParserFeature feature) {
        parserFeatures = parserFeatures & ~(feature.getMask() << SEGMENT_FEATURE_BIT_OFFSET);
        return this;
    }

    public ProtocolMapper disable(ParserFeature feature) {
        parserFeatures = parserFeatures & ~feature.getMask();
        return this;
    }

    public ProtocolMapper disable(VerifyFeature feature) {
        verifyFeatures = verifyFeatures & ~feature.getMask();
        return this;
    }

    public ProtocolMapper configurator(ProtocolConfigurator configurator){
        this.configurator = configurator;
        return this;
    }

    public ObjectMapper objectMapper(){
        return this.objectMapper;
    }

    private ProtocolMapper applyConfigurator(){
        configurator.setSegmentParserFeature(this.parserFeatures >> SEGMENT_FEATURE_BIT_OFFSET);
        configurator.setParserFeature(this.parserFeatures & 0x00FF);
        configurator.setVerifyFeature(this.verifyFeatures);
        configurator.setValidator(this.validator);
        configurator.setObjectMapper(this.objectMapper);
        factory.setConfigurator(configurator);
        return this;
    }

    /*
    /**********************************************************
    /* Public API : read
    /* (mapping from T212 to Java types);
    /* main methods
    /**********************************************************
     */
    public <T> T readValue(InputStream is, Class<T> value) throws IOException, ProtocolFormatException {
        applyConfigurator();
        return _readValueAndClose(factory.parser(is),value);
    }

    public <T> T readValue(byte[] bytes, Class<T> value) throws IOException, ProtocolFormatException {
        applyConfigurator();
        return _readValueAndClose(factory.parser(bytes),value);
    }

    public <T> T readValue(Reader reader, Class<T> value) throws IOException, ProtocolFormatException {
        applyConfigurator();
        return _readValueAndClose(factory.parser(reader),value);
    }

    public <T> T readValue(String data, Class<T> value) throws IOException, ProtocolFormatException {
        applyConfigurator();
        return _readValueAndClose(factory.parser(data),value);
    }

    private <T> T _readValueAndClose(ProtocolParser parser, Class<T> value) throws IOException, ProtocolFormatException {
        ProtocolDeserializer<T> deserializer = factory.deserializerFor(value);
        try(ProtocolParser p = parser){
            return deserializer.deserialize(p);
        }catch (RuntimeException e){
            throw new ProtocolFormatException(e.getMessage(), e);
        }
    }

    private <T> T _readValueAndClose(ProtocolParser parser, Type type, Class<T> value) throws IOException, ProtocolFormatException {
        ProtocolDeserializer<T> deserializer = factory.deserializerFor(type,value);
        try(ProtocolParser p = parser){
            return deserializer.deserialize(p);
        }catch (RuntimeException e){
            throw new ProtocolFormatException("Runtime error",e);
        }
    }

    public <T> void writeValueAsStream(T value, Class<T> type, OutputStream outputStream) throws IOException, ProtocolFormatException {
        applyConfigurator();
        _writeValueAndClose(factory.generator(outputStream),value,type);
    }

    public <T> void writeValueAsWriter(T value, Class<T> type, Writer writer) throws IOException, ProtocolFormatException {
        applyConfigurator();
        _writeValueAndClose(factory.generator(writer),value,type);
    }

    private <T> void _writeValueAndClose(ProtocolGenerator generator, T value, Class<T> type) throws IOException, ProtocolFormatException {
        ProtocolSerializer<T> serializer = factory.serializerFor(type);
        try(ProtocolGenerator g = generator){
            serializer.serialize(g,value);
        }catch (RuntimeException e){
            throw new ProtocolFormatException("Runtime error",e);
        }
    }



    private static Supplier<Type> getMapGenericType(){
        return () -> new Map<String,String>(){
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(Object key) {
                return false;
            }

            @Override
            public boolean containsValue(Object value) {
                return false;
            }

            @Override
            public String get(Object key) {
                return null;
            }

            @Override
            public String put(String key, String value) {
                return null;
            }

            @Override
            public String remove(Object key) {
                return null;
            }

            @Override
            public void putAll(Map<? extends String, ? extends String> m) {

            }

            @Override
            public void clear() {

            }

            @Override
            public Set<String> keySet() {
                return null;
            }

            @Override
            public Collection<String> values() {
                return null;
            }

            @Override
            public Set<Entry<String, String>> entrySet() {
                return null;
            }
        }.getClass().getGenericInterfaces()[0];
    }

    private static Supplier<Type> getDeepMapGenericType(){
        return () -> new Map<String,Object>(){
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(Object key) {
                return false;
            }

            @Override
            public boolean containsValue(Object value) {
                return false;
            }

            @Override
            public String get(Object key) {
                return null;
            }

            @Override
            public String put(String key, Object value) {
                return null;
            }

            @Override
            public String remove(Object key) {
                return null;
            }

            @Override
            public void putAll(Map<? extends String, ? extends Object> m) {

            }

            @Override
            public void clear() {

            }

            @Override
            public Set<String> keySet() {
                return null;
            }

            @Override
            public Collection<Object> values() {
                return null;
            }

            @Override
            public Set<Entry<String, Object>> entrySet() {
                return null;
            }
        }.getClass().getGenericInterfaces()[0];
    }

	public Map<String,String> readMap(InputStream is) throws IOException, ProtocolFormatException {
        applyConfigurator();
        //noinspection unchecked
        return _readValueAndClose(factory.parser(is),getMapGenericType().get(),Map.class);
    }

    public Map<String,String> readMap(byte[] bytes) throws IOException, ProtocolFormatException {
        applyConfigurator();
        //noinspection unchecked
        return _readValueAndClose(factory.parser(bytes),getMapGenericType().get(),Map.class);
    }

    public Map<String,String> readMap(Reader reader) throws IOException, ProtocolFormatException {
        applyConfigurator();
        //noinspection unchecked
        return _readValueAndClose(factory.parser(reader),getMapGenericType().get(),Map.class);
    }

    public Map<String,String> readMap(String data) throws IOException, ProtocolFormatException {
        applyConfigurator();
        //noinspection unchecked
        return _readValueAndClose(factory.parser(data),getMapGenericType().get(),Map.class);
    }


    public Map<String,Object> readDeepMap(InputStream is) throws IOException, ProtocolFormatException {
        applyConfigurator();
        //noinspection unchecked
        return _readValueAndClose(factory.parser(is),getDeepMapGenericType().get(),Map.class);
    }

    public Map<String,Object> readDeepMap(byte[] bytes) throws IOException, ProtocolFormatException {
        applyConfigurator();
        //noinspection unchecked
        return _readValueAndClose(factory.parser(bytes),getDeepMapGenericType().get(),Map.class);
    }

    public Map<String,Object> readDeepMap(Reader reader) throws IOException, ProtocolFormatException {
        applyConfigurator();
        //noinspection unchecked
        return _readValueAndClose(factory.parser(reader),getDeepMapGenericType().get(),Map.class);
    }

    public Map<String,Object> readDeepMap(String data) throws IOException, ProtocolFormatException {
        applyConfigurator();
        //noinspection unchecked
        return _readValueAndClose(factory.parser(data),getDeepMapGenericType().get(),Map.class);
    }


    public Data readData(InputStream is) throws IOException, ProtocolFormatException {
        return readValue(is,Data.class);
    }

    public Data readData(byte[] bytes) throws IOException, ProtocolFormatException {
        return readValue(bytes,Data.class);
    }

    public Data readData(Reader reader) throws IOException, ProtocolFormatException {
        return readValue(reader,Data.class);
    }

    public Data readData(String data) throws IOException, ProtocolFormatException {
        return readValue(data,Data.class);
    }


	public String writeMapAsString(Map data) throws IOException, ProtocolFormatException {
        StringWriter sw = new StringWriter();
        writeValueAsWriter(data,Map.class,sw);
        return sw.toString();
    }

    public char[] writeMapAsCharArray(Map data) throws IOException, ProtocolFormatException {
        return writeMapAsString(data).toCharArray();
    }

    public String writeDataAsString(Data data) throws IOException, ProtocolFormatException {
        StringWriter sw = new StringWriter();
        writeValueAsWriter(data,Data.class,sw);
        return sw.toString();
    }

    public char[] writeDataAsCharArray(Data data) throws IOException, ProtocolFormatException {
        return writeDataAsString(data).toCharArray();
    }

}
