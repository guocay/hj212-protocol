package com.github.guocay.hj212.server.portal.trans.mapper;

import com.github.guocay.hj212.server.portal.trans.bean.Device;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@DS("environmental")
public interface DeviceMapper extends BaseMapper<Device> {
    @Cacheable("cache")
    default List<Device> getDeviceList(String parkCode) {
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper();
        wrapper.eq(Device::getState, "1")
            .eq(Device::getParkCode,parkCode);
        return this.selectList(wrapper);
    }
}
