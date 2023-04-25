package com.github.guocay.hj212.segment.core.encode;

import com.github.guocay.hj212.exception.SegmentFormatException;
import com.github.guocay.hj212.segment.core.SegmentGenerator;

import java.io.IOException;
import java.util.Map;

/**
 * @author aCay
 */
public class MapValueSegmentSerializer implements SegmentSerializer<Object> {

    protected final SegmentSerializer<Map<String,Object>> _valueSerializer;
    protected final Object2MapConverter _object2MapConverter;

    public MapValueSegmentSerializer(SegmentSerializer<Map<String,Object>> _valueSerializer,
                                     Object2MapConverter _object2MapConverter) {
        this._valueSerializer = _valueSerializer;
        this._object2MapConverter = _object2MapConverter;
    }

    @Override
    public void serialize(SegmentGenerator generator, Object data) throws IOException, SegmentFormatException {
        if(data instanceof String){
            String value = (String) data;
            generator.writeValue(value);
        }else {
            SegmentGenerator g = generator.writeObjectStart();
            Map<String,Object> map = _object2MapConverter.convert(data);
            _valueSerializer.serialize(g, map);
            generator.writeObjectEnd();
        }
    }

    /**
     * Object 转换
     */
    public interface Object2MapConverter {

        Map<String,Object> convert(Object src) throws SegmentFormatException;
    }

}
