package com.github.guocay.hj212.business.service;

import com.github.guocay.hj212.business.po.MonitorFactorPo;
import com.github.guocay.hj212.core.ProtocolMapper;
import com.github.guocay.hj212.model.CpData;
import com.github.guocay.hj212.model.Data;
import com.github.guocay.hj212.model.DataFlag;
import com.github.guocay.hj212.business.core.annotaion.MonitorServiceListen;
import com.github.guocay.hj212.business.core.util.Constant;
import com.github.guocay.hj212.business.mapper.MonitorFactorMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Collections;

/**
 * 用于接收hj212协议内容
 * @author GuoKai
 * @since 2019/11/22
 */
@Service
public class MonitorService {

	private static final Logger log = LoggerFactory.getLogger(MonitorService.class);

    private final MonitorFactorMapping factorMapper;

    private final ProtocolMapper t212Mapper = ProtocolMapper.getInstance()
            .enableDefaultParserFeatures().enableDefaultVerifyFeatures();

    public MonitorService(MonitorFactorMapping factorMapper) {
        this.factorMapper = factorMapper;
    }

    @MonitorServiceListen("hj212协议接收服务")
    public String monitor(String monitorData,String ipAddress) throws Exception{
        String response = null;
        synchronized (t212Mapper) {
            Data data = t212Mapper.readData(monitorData);
            data.getCp().getPollution().forEach((key, value) -> {
				MonitorFactorPo monitorFactorPo = new MonitorFactorPo();
				monitorFactorPo.setDataStatus(Constant.EFFECTIVE).setMncode(data.getMn());
				monitorFactorPo.setDataTime(data.getCp().getDataTime()).setFactor(key);
				monitorFactorPo.setValue(value.getRtd().toString());
				monitorFactorPo.setTime(LocalDateTime.now(Clock.systemDefaultZone()));
                factorMapper.insert(monitorFactorPo);
			});
            if (DataFlag.A.isMarked(data.getDataFlag())) {
                Data builder = new Data();
                builder.setQn(data.getQn()).setSt(data.getSt()).setCn(Constant.DATA_RESPONSE);
                builder.setPw(data.getPw()).setMn(data.getMn()).setCp(new CpData());
                builder.setDataFlag(Collections.singletonList(DataFlag.V0));
                response = t212Mapper.writeDataAsString(builder);
            }
        }
        return response;
    }
}
