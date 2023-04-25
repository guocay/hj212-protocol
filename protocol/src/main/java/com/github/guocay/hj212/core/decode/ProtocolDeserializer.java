package com.github.guocay.hj212.core.decode;

import com.github.guocay.hj212.core.ProtocolParser;
import com.github.guocay.hj212.exception.ProtocolFormatException;

import java.io.IOException;

/**
 * T212反序列化器
 * @author aCay
 */
public interface ProtocolDeserializer<Target> {

    Target deserialize(ProtocolParser parser) throws IOException, ProtocolFormatException;
}
