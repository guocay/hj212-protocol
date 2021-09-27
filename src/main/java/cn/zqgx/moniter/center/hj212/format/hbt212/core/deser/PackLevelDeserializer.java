package cn.zqgx.moniter.center.hj212.format.hbt212.core.deser;

import cn.zqgx.moniter.center.hj212.format.hbt212.core.T212Parser;
import cn.zqgx.moniter.center.hj212.format.hbt212.core.VerifyUtil;
import cn.zqgx.moniter.center.hj212.format.hbt212.exception.T212FormatException;
import cn.zqgx.moniter.center.hj212.format.hbt212.model.Pack;
import cn.zqgx.moniter.center.hj212.format.hbt212.model.verify.PacketElement;
import cn.zqgx.moniter.center.hj212.format.segment.base.cfger.Configurator;
import cn.zqgx.moniter.center.hj212.format.segment.base.cfger.Configured;

import java.io.IOException;

import static cn.zqgx.moniter.center.hj212.format.hbt212.core.feature.VerifyFeature.DATA_CRC;
import static cn.zqgx.moniter.center.hj212.format.hbt212.core.feature.VerifyFeature.DATA_LEN_RANGE;

/**
 * 通信包 级别 反序列化器
 * Created by xiaoyao9184 on 2017/12/15.
 */
public class PackLevelDeserializer
        implements T212Deserializer<Pack>, Configured<PackLevelDeserializer> {

    private Configurator<T212Parser> parserConfigurator;
    private int verifyFeature;

    @Override
    public void configured(Configurator<PackLevelDeserializer> configurator){
        configurator.config(this);
    }

    @Override
    public Pack deserialize(T212Parser parser) throws IOException, T212FormatException {
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

    public void setParserConfigurator(Configurator<T212Parser> parserConfigurator) {
        this.parserConfigurator = parserConfigurator;
    }

}
