package com.github.guocay.hj212.segment.core.decode;

import com.github.guocay.hj212.exception.SegmentFormatException;
import com.github.guocay.hj212.segment.core.SegmentParser;
import com.github.guocay.hj212.segment.core.SegmentToken;

import java.io.IOException;

/**
 * @author aCay
 */
public class MapValueSegmentDeserializer implements SegmentDeserializer<Object> {

    protected final SegmentDeserializer<?> _valueDeserializer;

    public MapValueSegmentDeserializer(SegmentDeserializer<?> _valueDeserializer) {
        this._valueDeserializer = _valueDeserializer;
    }

    @SuppressWarnings("incomplete-switch")
	@Override
    public Object deserialize(SegmentParser parser) throws IOException, SegmentFormatException {
        Object result = null;
        SegmentToken token = parser.currentToken();
        switch (token){
            case NOT_AVAILABLE:
            case NULL_VALUE:
                return null;
            case END_KEY:
                result = parser.readValue();
                break;
            case START_OBJECT_VALUE:
                result = _valueDeserializer.deserialize(parser);
                break;
        }

        return result;
    }

}
