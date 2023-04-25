package com.github.guocay.hj212.segment.core.encode;

import com.github.guocay.hj212.exception.SegmentFormatException;
import com.github.guocay.hj212.segment.core.SegmentGenerator;

import java.io.IOException;
import java.util.Map;

/**
 * @author aCay
 */
public class StringMapSegmentSerializer implements SegmentSerializer<Map<String,String>> {

    @Override
    public void serialize(SegmentGenerator generator, Map<String, String> data) throws IOException, SegmentFormatException {
        if(generator.nextToken() == null){
            generator.initToken();
        }
        writeMap(generator,data);
    }

    private void writeMap(SegmentGenerator generator, Map<String, String> result) throws IOException, SegmentFormatException {
        for (Map.Entry<String, String> kv : result.entrySet()) {
            generator.writeKey(kv.getKey());
            generator.writeValue(kv.getValue());
        }
    }

}
