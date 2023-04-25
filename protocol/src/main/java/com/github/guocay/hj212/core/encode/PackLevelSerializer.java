package com.github.guocay.hj212.core.encode;

import com.github.guocay.hj212.core.ProtocolGenerator;
import com.github.guocay.hj212.core.VerifyUtil;
import com.github.guocay.hj212.exception.ProtocolFormatException;
import com.github.guocay.hj212.model.Pack;
import com.github.guocay.hj212.model.verify.PacketElement;
import com.github.guocay.hj212.segment.config.Configurator;
import com.github.guocay.hj212.segment.config.Configured;

import java.io.IOException;
import java.util.Arrays;

import static com.github.guocay.hj212.core.feature.VerifyFeature.DATA_CRC;
import static com.github.guocay.hj212.core.feature.VerifyFeature.DATA_LEN_RANGE;

/**
 * 通信包 级别 反序列化器
 * @author aCay
 */
public class PackLevelSerializer
        implements ProtocolSerializer<Pack>, Configured<PackLevelSerializer> {

    private Configurator<ProtocolGenerator> generatorConfigurator;
    private int verifyFeature;

    @Override
    public void configured(Configurator<PackLevelSerializer> configurator){
        configurator.config(this);
    }

    @Override
    public void serialize(ProtocolGenerator generator, Pack pack) throws IOException, ProtocolFormatException {
        generator.configured(generatorConfigurator);

        generator.writeHeader();

        if(DATA_LEN_RANGE.enabledIn(verifyFeature)){
            int segmentLen = 0;
            if(!Arrays.equals(pack.getLength(),new char[]{0,0,0,0})){
                segmentLen = Integer.parseInt(new String(pack.getLength()));
            }
            if(segmentLen == 0){
                segmentLen = pack.getSegment().length;
            }

            VerifyUtil.verifyRange(segmentLen,0,1024, PacketElement.DATA_LEN);
        }

        generator.writeDataAndLenAndCrc(pack.getSegment());

        if(DATA_CRC.enabledIn(verifyFeature)){
            if(Arrays.equals(pack.getCrc(),new char[]{0,0,0,0})){
                //ignore
            }else{
                VerifyUtil.verifyCrc(pack.getSegment(), pack.getCrc(), PacketElement.DATA_CRC);
            }
        }

        generator.writeFooter();
    }

    public void setVerifyFeature(int verifyFeature) {
        this.verifyFeature = verifyFeature;
    }

    public void setGeneratorConfigurator(Configurator<ProtocolGenerator> generatorConfigurator) {
        this.generatorConfigurator = generatorConfigurator;
    }

}
