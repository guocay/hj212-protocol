package com.github.guocay.hj212.core.encode;


import com.github.guocay.hj212.core.ProtocolGenerator;
import com.github.guocay.hj212.core.VerifyUtil;
import com.github.guocay.hj212.exception.ProtocolFormatException;
import com.github.guocay.hj212.exception.SegmentFormatException;
import com.github.guocay.hj212.model.verify.PacketElement;
import com.github.guocay.hj212.segment.config.Configurator;
import com.github.guocay.hj212.segment.config.Configured;
import com.github.guocay.hj212.segment.core.SegmentGenerator;
import com.github.guocay.hj212.segment.core.encode.SegmentSerializer;
import jakarta.validation.Validator;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import static com.github.guocay.hj212.core.feature.VerifyFeature.DATA_LEN_RANGE;

/**
 * 数据区 级别 序列化器
 * @author aCay
 */
public class CpDataLevelMapDataSerializer
        implements ProtocolSerializer<Map<String,Object>>, Configured<CpDataLevelMapDataSerializer> {

    private int verifyFeature;
    private Configurator<SegmentGenerator> segmentGeneratorConfigurator;
    private SegmentSerializer<Map<String,Object>> segmentSerializer;
    @SuppressWarnings("unused")
	private Validator validator;

    @Override
    public void configured(Configurator<CpDataLevelMapDataSerializer> configurator){
        configurator.config(this);
    }

    @Override
    public void serialize(ProtocolGenerator generator, Map<String,Object> data) throws IOException, ProtocolFormatException {
        generator.writeHeader();

        char[] segment = serialize(data);

        if(DATA_LEN_RANGE.enabledIn(verifyFeature)){
            int segmentLen = segment.length;
            VerifyUtil.verifyRange(segmentLen,0,1024, PacketElement.DATA_LEN);
        }
        generator.writeDataAndLenAndCrc(segment);
        generator.writeFooter();
    }

    public char[] serialize(Map<String, Object> data) throws IOException, ProtocolFormatException {
        StringWriter writer = new StringWriter();
        SegmentGenerator generator = new SegmentGenerator(writer);
        generator.configured(segmentGeneratorConfigurator);

        try {
            segmentSerializer.serialize(generator,data);
        } catch (SegmentFormatException e) {
            ProtocolFormatException.segment_exception(e);
        }
        return writer.toString().toCharArray();
    }

    public void setVerifyFeature(int verifyFeature) {
        this.verifyFeature = verifyFeature;
    }

    public void setSegmentGeneratorConfigurator(Configurator<SegmentGenerator> segmentGeneratorConfigurator) {
        this.segmentGeneratorConfigurator = segmentGeneratorConfigurator;
    }

    public void setSegmentSerializer(SegmentSerializer<Map<String, Object>> segmentSerializer) {
        this.segmentSerializer = segmentSerializer;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

}
