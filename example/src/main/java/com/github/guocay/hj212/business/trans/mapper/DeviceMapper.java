package com.github.guocay.hj212.business.trans.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.guocay.hj212.business.trans.bean.Device;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@DS("environmental")
public interface DeviceMapper extends BaseMapper<Device> {

    @Cacheable("cache")
    default List<Device> getDeviceList(String parkCode) {
        LambdaQueryWrapper<Device> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Device::getState, "1")
            .eq(Device::getParkCode,parkCode);
        return this.selectList(wrapper);
    }
}
