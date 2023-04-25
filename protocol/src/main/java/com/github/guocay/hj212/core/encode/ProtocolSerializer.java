package com.github.guocay.hj212.core.encode;


import com.github.guocay.hj212.core.ProtocolGenerator;
import com.github.guocay.hj212.exception.ProtocolFormatException;

import java.io.IOException;

/**
 * T212序列化器
 * @author aCay
 */
public interface ProtocolSerializer<Target> {

    void serialize(ProtocolGenerator generator, Target target) throws IOException, ProtocolFormatException;
}
