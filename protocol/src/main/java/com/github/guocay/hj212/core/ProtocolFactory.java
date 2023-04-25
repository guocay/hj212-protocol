package com.github.guocay.hj212.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.guocay.hj212.core.config.ProtocolConfigurator;
import com.github.guocay.hj212.core.converter.DataConverter;
import com.github.guocay.hj212.core.decode.ProtocolDeserializer;
import com.github.guocay.hj212.core.encode.ProtocolSerializer;
import com.github.guocay.hj212.segment.config.Configured;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * T212工厂
 * @author aCay
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ProtocolFactory {

    private ProtocolConfigurator configurator = new ProtocolConfigurator();
    final protected HashMap<Type, ProtocolDeserializer<Object>> _rootDeserializers = new HashMap<>();
    final protected HashMap<Type, ProtocolSerializer<Object>> _rootSerializers = new HashMap<>();



    public ProtocolFactory(){

    }

    public ProtocolFactory(ProtocolConfigurator configurator){
        this.configurator = configurator;
    }

    public ProtocolFactory copy() {
        ProtocolFactory factory = new ProtocolFactory();
        factory.setConfigurator(this.configurator);
        factory._rootDeserializers.putAll(this._rootDeserializers);
        factory._rootSerializers.putAll(this._rootSerializers);
        return factory;
    }



    public ProtocolConfigurator getConfigurator() {
        return configurator;
    }

    public void setConfigurator(ProtocolConfigurator configurator) {
        this.configurator = configurator;
    }

    /**
     * 创建解析器
     * @param is 字节流
     * @return 解析器
     */
    public ProtocolParser parser(InputStream is) {
        InputStreamReader dis = new InputStreamReader(is);
        ProtocolParser parser = new ProtocolParser(dis);
        parser.configured(configurator);
        return parser;
    }

    /**
     * 创建解析器
     * @param bytes 字节数组
     * @return 解析器
     */
    public ProtocolParser parser(byte[] bytes) {
        return parser(new ByteArrayInputStream(bytes));
    }

    /**
     * 创建解析器
     * @param reader 字符流
     * @return 解析器
     */
    public ProtocolParser parser(Reader reader) {
        ProtocolParser parser = new ProtocolParser(reader);
        parser.configured(configurator);
        return parser;
    }

    /**
     * 创建解析器
     * @param chars 字符数组
     * @return 解析器
     */
    public ProtocolParser parser(char[] chars) {
        return parser(new CharArrayReader(chars));
    }

    /**
     * 创建解析器
     * @param msg 字符串
     * @return 解析器
     */
    public ProtocolParser parser(String msg) {
        return parser(new StringReader(msg));
    }


    /**
     * 获取类型反序列化器
     * @param tClass 类型类
     * @param <T> 类型
     * @return 反序列化器
     */
	public <T> ProtocolDeserializer<T> deserializerFor(Class<T> tClass){
        ProtocolDeserializer<T> deserializer = (ProtocolDeserializer<T>) _rootDeserializers.get(tClass);
        if(deserializer instanceof Configured){
            Configured configured = (Configured) deserializer;
            configured.configured(configurator);
        }
        return deserializer;
    }

	public <T> ProtocolDeserializer<T> deserializerFor(Type type, Class<T> tClass) {
        ProtocolDeserializer<T> deserializer = (ProtocolDeserializer<T>) _rootDeserializers.get(type);
        if(deserializer instanceof Configured){
            Configured configured = (Configured) deserializer;
            configured.configured(configurator);
        }
        return deserializer;
    }


    /**
     * 注册类型反序列化器
     * @param deserializerClass 反序列化器
     */
    public void deserializerRegister(Class<? extends ProtocolDeserializer> deserializerClass) throws InstantiationException, IllegalAccessException {
        Type type = Stream.of(deserializerClass.getGenericInterfaces())
                .filter(t -> t instanceof ParameterizedType)
                .map(t -> (ParameterizedType)t)
                .filter(pt -> pt.getRawType().equals(ProtocolDeserializer.class))
                .map(pt -> pt.getActualTypeArguments()[0])
                .findFirst()
                .orElse(Object.class);

        deserializerRegister(type,deserializerClass);
    }

    /**
     * 注册类型反序列化器
     * @param type 类型
     * @param deserializerClass 反序列化器
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
	public void deserializerRegister(Type type, Class<? extends ProtocolDeserializer> deserializerClass) throws IllegalAccessException, InstantiationException {
        _rootDeserializers.put(type,deserializerClass.newInstance());
    }


    /**
     * 创建产生器
     * @param os 字节流
     * @return 产生器
     */
    public ProtocolGenerator generator(OutputStream os) {
        OutputStreamWriter osw = new OutputStreamWriter(os);
        ProtocolGenerator generator = new ProtocolGenerator(osw);
        generator.configured(configurator);
        return generator;
    }

    /**
     * 创建产生器
     * @param writer 字符流
     * @return 产生器
     */
    public ProtocolGenerator generator(Writer writer){
        ProtocolGenerator generator = new ProtocolGenerator(writer);
        generator.configured(configurator);
        return generator;
    }

    /**
     * 获取类型序列化器
     * @param <T> 类型
     * @return 序列化器
     */
    public <T> ProtocolSerializer<T> serializerFor(Class<T> value){
        ProtocolSerializer<T> serializer = (ProtocolSerializer<T>) _rootSerializers.get(value);
        if(serializer instanceof Configured){
            Configured configured = (Configured) serializer;
            configured.configured(configurator);
        }
        return serializer;
    }

    /**
     * 注册类型序列化器
     * @param serializerClass 序列化器
     */
    public void serializerRegister(Class<? extends ProtocolSerializer> serializerClass) throws InstantiationException, IllegalAccessException {
        Type type = Stream.of(serializerClass.getGenericInterfaces())
                .filter(t -> t instanceof ParameterizedType)
                .map(t -> (ParameterizedType)t)
                .filter(pt -> pt.getRawType().equals(ProtocolSerializer.class))
                .map(pt -> pt.getActualTypeArguments()[0])
                .findFirst()
                .orElse(Object.class);

        serializerRegister(type,serializerClass);
    }

    /**
     * 注册类型序列化器
     * @param type 类型
     * @param serializerClass 序列化器
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public void serializerRegister(Type type, Class<? extends ProtocolSerializer> serializerClass) throws IllegalAccessException, InstantiationException {
        _rootSerializers.put(type,serializerClass.newInstance());
    }

    public Validator validator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

    /**
     * null mean use default
     * @see ProtocolConfigurator#configure(DataConverter)
     * @return NULL
     */
    public ObjectMapper objectMapper() {
        return null;
    }

}
