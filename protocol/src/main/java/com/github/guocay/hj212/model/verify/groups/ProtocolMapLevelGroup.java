package com.github.guocay.hj212.model.verify.groups;

import com.github.guocay.hj212.model.DataFlag;

/**
 * T212Map级别 验证组
 * @see DataFlag#V0
 * @author aCay
 */
public interface ProtocolMapLevelGroup {

    /**
     * 数据段级别
     */
    interface DataLevel{}

    /**
     * 数据区级别
     */
    interface CpDataLevel{}
}
