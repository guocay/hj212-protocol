package cn.zqgx.moniter.center.server.portal.service;

import cn.zqgx.moniter.center.hj212.format.hbt212.core.T212Mapper;
import cn.zqgx.moniter.center.hj212.format.hbt212.model.CpData;
import cn.zqgx.moniter.center.hj212.format.hbt212.model.Data;
import cn.zqgx.moniter.center.hj212.format.hbt212.model.DataFlag;
import cn.zqgx.moniter.center.server.portal.bean.po.MoniterFactorPo;
import cn.zqgx.moniter.center.server.portal.core.annotaion.MoniterServiceListen;
import cn.zqgx.moniter.center.server.portal.core.util.Constant;
import cn.zqgx.moniter.center.server.portal.mapper.MoniterFactorMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 用于接收hj212协议内容
 * @author GuoKai
 * @date 2019/11/22
 */
@Slf4j
@Service
public class MoniterService {

    @Autowired
    private MoniterFactorMapping factorMappr;

    private T212Mapper t212Mapper = new T212Mapper().enableDefaultParserFeatures().enableDefaultVerifyFeatures();

    @MoniterServiceListen("hj212协议接收服务")
    public String moniter(String moniterData,String ipAddress) throws Exception{
        String response = null;
        synchronized (t212Mapper) {
            Data data = t212Mapper.readData(moniterData);
            data.getCp().getPollution().forEach((key, value) -> {
                factorMappr.insert(MoniterFactorPo.builder().dataStatus(Constant.EFFECTIVE).mncode(data.getMn())
                        .dataTime(data.getCp().getDataTime()).factor(key).value(value.getRtd().toString())
                        .time(LocalDateTime.now(Clock.systemDefaultZone())).build());
            });
            if (DataFlag.A.isMarked(data.getDataFlag())) {
                response = t212Mapper.writeDataAsString(Data.builder().qn(data.getQn()).st(data.getSt()).cn(Constant.DATA_RESPONSE)
                        .pw(data.getPw()).mn(data.getMn()).dataFlag(Arrays.asList(DataFlag.V0))
                        .cp(CpData.builder().build()).build());
            }
        }
        return response;
    }
}
