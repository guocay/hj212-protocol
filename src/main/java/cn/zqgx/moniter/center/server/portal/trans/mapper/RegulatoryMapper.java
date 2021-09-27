package cn.zqgx.moniter.center.server.portal.trans.mapper;

import cn.zqgx.moniter.center.server.portal.trans.bean.Regulatory;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@DS("environmental")
public interface RegulatoryMapper extends BaseMapper<Regulatory> {
    @Cacheable("cache")
    default List<Regulatory> getFactorByDeviceType(String parkCode, String monitorType) {
        LambdaQueryWrapper<Regulatory> wrapper = new LambdaQueryWrapper();
        wrapper.eq(Regulatory::getState, "1")
                .eq(Regulatory::getType, monitorType+"ZB")
                .eq(Regulatory::getParkCode,parkCode);
        return this.selectList(wrapper);
    }
}
