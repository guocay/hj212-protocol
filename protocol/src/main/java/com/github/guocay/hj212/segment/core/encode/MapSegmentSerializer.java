package com.github.guocay.hj212.segment.core.encode;

import com.github.guocay.hj212.exception.SegmentFormatException;
import com.github.guocay.hj212.segment.core.SegmentGenerator;

import java.io.IOException;
import java.util.Map;

/**
 * @author aCay
 */
public class MapSegmentSerializer implements SegmentSerializer<Map<String,Object>> {


    protected SegmentSerializer<Object> valueDeserializer;

    public MapSegmentSerializer() {
        this.valueDeserializer = new MapValueSegmentSerializer(this, o -> {
            throw new SegmentFormatException("Default value serializer not support serialize this type: "
                    + o.getClass().getName());
        });
    }

    public MapSegmentSerializer(SegmentSerializer<Object> _valueDeserializer) {
        this.valueDeserializer = _valueDeserializer;
    }


    @Override
    public void serialize(SegmentGenerator generator, Map<String, Object> data) throws IOException, SegmentFormatException {
        if(generator.nextToken() == null){
            generator.initToken();
        }

        writeMap(generator,data);
    }


    private void writeMap(SegmentGenerator generator, Map<String, Object> result) throws IOException, SegmentFormatException {
        for (Map.Entry<String, Object> kv : result.entrySet()) {
            generator.writeKey(kv.getKey());
            valueDeserializer.serialize(generator,kv.getValue());
        }
    }

    public void setValueDeserializer(SegmentSerializer<Object> valueDeserializer) {
        this.valueDeserializer = valueDeserializer;
    }
}
