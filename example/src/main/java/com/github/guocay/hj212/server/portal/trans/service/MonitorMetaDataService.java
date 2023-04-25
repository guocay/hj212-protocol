package com.github.guocay.hj212.server.portal.trans.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.github.guocay.hj212.server.portal.core.util.Constant;
import com.github.guocay.hj212.server.portal.trans.bean.DataAir;
import com.github.guocay.hj212.server.portal.trans.bean.DataVoc;
import com.github.guocay.hj212.server.portal.trans.bean.Device;
import com.github.guocay.hj212.server.portal.trans.bean.MonitorMetaData;
import com.github.guocay.hj212.server.portal.trans.bean.Regulatory;
import com.github.guocay.hj212.server.portal.trans.mapper.DataAirMapper;
import com.github.guocay.hj212.server.portal.trans.mapper.DataVocMapper;
import com.github.guocay.hj212.server.portal.trans.mapper.DeviceMapper;
import com.github.guocay.hj212.server.portal.trans.mapper.MonitorMetaDataMapper;
import com.github.guocay.hj212.server.portal.trans.mapper.RegulatoryMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MonitorMetaDataService {

    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private RegulatoryMapper regulatoryMapper;
    @Autowired
    private MonitorMetaDataMapper moniterMetaDataMapper;
    @Autowired
    private DataAirMapper airMapper;
    @Autowired
    private DataVocMapper vocMapper;

    public boolean trans(String parkCode) {
        LocalDateTime localDateTime = LocalDateTime.now();
        //第一步,查出所有的设备;
        deviceMapper.getDeviceList(parkCode).forEach(item -> {
            List<Regulatory> factorList = regulatoryMapper.getFactorByDeviceType(parkCode,item.getMonitorType());
            if(factorList.size() == 0) return;
            LambdaQueryWrapper<MonitorMetaData> queryWrapper = new LambdaQueryWrapper();
            queryWrapper.eq(MonitorMetaData::getMncode, item.getMncode())
                    .in(MonitorMetaData::getFactor, factorList.stream().map(Regulatory::getCode).collect(Collectors.toList()))
                    .ge(MonitorMetaData::getTime, localDateTime.minus(Constant.MonitorTask.CALLBACK_CYCLE, ChronoUnit.MILLIS))
                    .eq(MonitorMetaData::getDataStatus, Constant.EFFECTIVE)
                    .orderByAsc(MonitorMetaData::getDataTime);
            List<MonitorMetaData> metaList = moniterMetaDataMapper.selectList(queryWrapper);
            if (metaList.size() == 0) return;
            //第二步,将数据加工成对象,并保存;
            Map<String, Map<String, String>> map = new HashMap<>();
            metaList.forEach((meta) -> {
                Map<String, String> data;
                String dateTime;
                if (ObjectUtil.isEmpty(data = map.get(dateTime = meta.getDataTime())))
                    map.put(dateTime, data = new HashMap<String, String>());
                data.put(meta.getFactor(), meta.getValue());
            });
            //第三步,插入数据;
            map.forEach((key, val) -> {
                log.info(val.toString());
                LocalDateTime time = LocalDateTime.parse(key, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                switch (item.getMonitorType()) {
                    case Constant.MonitorType.TYPE_LIST_AIR:
                        airMapper.insert(setFieldValue(DataAir.builder().mncode(item.getMncode().toLowerCase())
                                .monitorTime(time).state("1").build(), val));
                        break;
                    case Constant.MonitorType.TYPE_LIST_DANGERGAS:
                        vocMapper.insert(setFieldValue(DataVoc.builder().mncode(item.getMncode().toLowerCase())
                                .monitorTime(time).state("1").build(), val));
                        break;
                }
            });
            //第三步,将加工过的数据的状态置为已处理;逻辑删除
            LambdaUpdateWrapper<MonitorMetaData> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(MonitorMetaData::getDataStatus, Constant.UN_EFFECTIVE)
                    .in(MonitorMetaData::getId, metaList.stream().map(MonitorMetaData::getId).collect(Collectors.toList()));
            moniterMetaDataMapper.update(null, updateWrapper);
        });
        return true;
    }

    private <T> T setFieldValue(T data, Map<String, String> val) {
        val.forEach((factor, value) -> {
            try {
                ReflectUtil.setFieldValue(data, factor, value);
            } catch (IllegalArgumentException ex) {
                log.error(ex.getMessage());
            }
        });
        return data;
    }

    @Cacheable
    public List<Regulatory> getFactorByDeviceType(String parkCode,String monitorType) {
        LambdaQueryWrapper<Regulatory> wrapper = new LambdaQueryWrapper();
        wrapper.eq(Regulatory::getState, "1")
                .eq(Regulatory::getType, monitorType)
                .eq(Regulatory::getParkCode,parkCode);
        return regulatoryMapper.selectList(wrapper);
    }

    @Cacheable
    public List<Device> getDeviceList(String parkCode) {
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper();
        wrapper.eq(Device::getState, "1");
                //.eq(Device::getParkCode,parkCode);
        return deviceMapper.selectList(wrapper);
    }
}
