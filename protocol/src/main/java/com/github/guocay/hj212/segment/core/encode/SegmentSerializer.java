package com.github.guocay.hj212.segment.core.encode;

import com.github.guocay.hj212.exception.SegmentFormatException;
import com.github.guocay.hj212.segment.core.SegmentGenerator;

import java.io.IOException;

/**
 * @author aCay
 */
public interface SegmentSerializer<Target> {

    void serialize(SegmentGenerator generator, Target target) throws IOException, SegmentFormatException;
}
