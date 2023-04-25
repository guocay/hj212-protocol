package com.github.guocay.hj212.declaration.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PushbackReader;

/**
 * 字符流解析器
 * @author aCay
 */
public abstract class PushBackReaderParser<Target> implements Parser<Target> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PushBackReaderParser.class);

    protected PushbackReader reader;

    public PushBackReaderParser(PushbackReader reader){
        this.reader = reader;
    }


    @Override
    public void close(){
        try {
            reader.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
