package cn.zqgx.moniter.center.hj212.format.segment.core.deser;

import cn.zqgx.moniter.center.hj212.format.segment.core.SegmentParser;
import cn.zqgx.moniter.center.hj212.format.segment.core.SegmentToken;
import cn.zqgx.moniter.center.hj212.format.segment.exception.SegmentFormatException;

import java.io.IOException;

/**
 * Created by xiaoyao9184 on 2017/12/15.
 */
public class MapValueSegmentDeserializer
        implements SegmentDeserializer<Object> {

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


//    private Map<String,Object> deserializeMap(SegmentParser parser){
//
//    }

}
