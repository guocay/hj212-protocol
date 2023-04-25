package com.github.guocay.hj212.business.trans.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.guocay.hj212.business.trans.bean.Regulatory;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@DS("environmental")
public interface RegulatoryMapper extends BaseMapper<Regulatory> {

    @Cacheable("cache")
    default List<Regulatory> getFactorByDeviceType(String parkCode, String monitorType) {
        LambdaQueryWrapper<Regulatory> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Regulatory::getState, "1")
                .eq(Regulatory::getType, monitorType+"ZB")
                .eq(Regulatory::getParkCode,parkCode);
        return selectList(wrapper);
    }
}
