package cn.zqgx.moniter.center.hj212.format.segment.core.deser;

import cn.zqgx.moniter.center.hj212.format.segment.core.SegmentParser;
import cn.zqgx.moniter.center.hj212.format.segment.exception.SegmentFormatException;

import java.io.IOException;

/**
 * Created by xiaoyao9184 on 2018/1/4.
 */
public interface SegmentDeserializer<Target> {

    Target deserialize(SegmentParser parser) throws IOException, SegmentFormatException;
}
