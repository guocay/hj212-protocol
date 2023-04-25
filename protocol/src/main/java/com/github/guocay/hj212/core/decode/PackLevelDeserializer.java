package com.github.guocay.hj212.core.decode;


import com.github.guocay.hj212.core.ProtocolParser;
import com.github.guocay.hj212.core.VerifyUtil;
import com.github.guocay.hj212.exception.ProtocolFormatException;
import com.github.guocay.hj212.model.Pack;
import com.github.guocay.hj212.model.verify.PacketElement;
import com.github.guocay.hj212.segment.config.Configurator;
import com.github.guocay.hj212.segment.config.Configured;

import java.io.IOException;

import static com.github.guocay.hj212.core.feature.VerifyFeature.DATA_CRC;
import static com.github.guocay.hj212.core.feature.VerifyFeature.DATA_LEN_RANGE;

/**
 * 通信包 级别 反序列化器
 * @author aCay
 */
public class PackLevelDeserializer
        implements ProtocolDeserializer<Pack>, Configured<PackLevelDeserializer> {

    private Configurator<ProtocolParser> parserConfigurator;
    private int verifyFeature;

    @Override
    public void configured(Configurator<PackLevelDeserializer> configurator){
        configurator.config(this);
    }

    @Override
    public Pack deserialize(ProtocolParser parser) throws IOException, ProtocolFormatException {
        parser.configured(parserConfigurator);

        Pack pack = new Pack();
        pack.setHeader(parser.readHeader());
        pack.setLength(parser.readDataLen());

        int segmentLen = Integer.parseInt(new String(pack.getLength()));
        if(DATA_LEN_RANGE.enabledIn(verifyFeature)){
            VerifyUtil.verifyRange(segmentLen,0,1024, PacketElement.DATA_LEN);
        }
        pack.setData(parser.readData(segmentLen));
        pack.setCrc(parser.readCrc());

        if(DATA_CRC.enabledIn(verifyFeature)){
            VerifyUtil.verifyCrc(pack.getSegment(), pack.getCrc(), PacketElement.DATA_CRC);
        }
        pack.setFooter(parser.readFooter());

        return pack;
    }

    public void setVerifyFeature(int verifyFeature) {
        this.verifyFeature = verifyFeature;
    }

    public void setParserConfigurator(Configurator<ProtocolParser> parserConfigurator) {
        this.parserConfigurator = parserConfigurator;
    }

}
