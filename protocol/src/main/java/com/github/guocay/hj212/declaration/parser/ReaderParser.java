package com.github.guocay.hj212.declaration.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;

/**
 * 字符流解析器
 * @author aCay
 */
public abstract class ReaderParser<Target> implements Parser<Target> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReaderParser.class);

    protected Reader reader;

    public ReaderParser(Reader reader){
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
