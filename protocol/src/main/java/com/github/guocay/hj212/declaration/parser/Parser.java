package com.github.guocay.hj212.declaration.parser;

import com.github.guocay.hj212.exception.ProtocolFormatException;

import java.io.Closeable;
import java.io.IOException;

/**
 * T212解析器
 * @author aCay
 */
public interface Parser<Target>
        extends Closeable {

    Target parse() throws ProtocolFormatException, IOException;
}
