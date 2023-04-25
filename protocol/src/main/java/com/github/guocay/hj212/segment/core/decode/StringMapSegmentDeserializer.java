package com.github.guocay.hj212.segment.core.decode;

import com.github.guocay.hj212.exception.SegmentFormatException;
import com.github.guocay.hj212.segment.core.SegmentParser;
import com.github.guocay.hj212.segment.core.SegmentToken;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author aCay
 */
public class StringMapSegmentDeserializer implements SegmentDeserializer<Map<String,String>> {


    protected final SegmentDeserializer<String> _valueDeserializer;

    public StringMapSegmentDeserializer() {
        _valueDeserializer = new StringSegmentDeserializer();
    }

    @Override
    public Map<String, String> deserialize(SegmentParser parser) throws IOException, SegmentFormatException {
        if(parser.currentToken() == null){
            parser.initToken();
        }

        Map<String, String> result = new LinkedHashMap<>();
        readMap(parser,result);
        return result;
    }

    @SuppressWarnings("incomplete-switch")
	private void readMap(SegmentParser parser, Map<String, String> result) throws IOException, SegmentFormatException {
        String key = parser.readKey();
        for (; key != null; key = parser.readKey()) {
            SegmentToken token = parser.currentToken();
            String value = null;

            switch (token){
                case NOT_AVAILABLE:
                    //end
                    return;
                case END_PART_KEY:
                    //NOT possible
                    assert false;
                case END_SUB_ENTRY:
                case END_ENTRY:
                case END_OBJECT_VALUE:
                    //NULL value
                    break;
                case END_KEY:
                case START_OBJECT_VALUE:
                    //Normal value
                    value = _valueDeserializer.deserialize(parser);
                    break;
            }
            result.put(key, value);
        }
    }

}
