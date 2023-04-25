package com.github.guocay.hj212.segment.core.decode;

import com.github.guocay.hj212.exception.SegmentFormatException;
import com.github.guocay.hj212.segment.core.SegmentParser;

import java.io.IOException;

/**
 * @author aCay
 */
public interface SegmentDeserializer<Target> {

    Target deserialize(SegmentParser parser) throws IOException, SegmentFormatException;
}
