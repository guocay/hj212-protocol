package com.github.guocay.hj212.server.portal.service;

import com.github.guocay.hj212.core.ProtocolMapper;
import com.github.guocay.hj212.model.CpData;
import com.github.guocay.hj212.model.Data;
import com.github.guocay.hj212.model.DataFlag;
import com.github.guocay.hj212.server.portal.bean.po.MonitorFactorPo;
import com.github.guocay.hj212.server.portal.core.annotaion.MonitorServiceListen;
import com.github.guocay.hj212.server.portal.core.util.Constant;
import com.github.guocay.hj212.server.portal.mapper.MonitorFactorMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Collections;

/**
 * 用于接收hj212协议内容
 * @author GuoKai
 * @since 2019/11/22
 */
@Slf4j
@Service
public class MonitorService {

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
                factorMapper.insert(MonitorFactorPo.builder().dataStatus(Constant.EFFECTIVE).mncode(data.getMn())
                        .dataTime(data.getCp().getDataTime()).factor(key).value(value.getRtd().toString())
                        .time(LocalDateTime.now(Clock.systemDefaultZone())).build());
            });
            if (DataFlag.A.isMarked(data.getDataFlag())) {
                Data builder = new Data();
                builder.setQn(data.getQn());
                builder.setSt(data.getSt());
                builder.setCn(Constant.DATA_RESPONSE);
                builder.setPw(data.getPw());
                builder.setMn(data.getMn());
                builder.setDataFlag(Collections.singletonList(DataFlag.V0));
                builder.setCp(new CpData());
                response = t212Mapper.writeDataAsString(builder);
            }
        }
        return response;
    }
}
