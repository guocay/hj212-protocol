package com.github.guocay.hj212.model.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.guocay.hj212.model.CpData;
import com.github.guocay.hj212.model.Data;
import com.github.guocay.hj212.model.DataFlag;

import java.util.List;

/**
 * 混合 序列化 {@link Data}时使用
 * @author aCay
 */
public abstract class DataSerializationMixin {

    @JsonProperty("QN")
    abstract String getQn();

    @JsonProperty("PNUM")
    abstract int getpNum();

    @JsonProperty("PNO")
    abstract int getpNo();

    @JsonProperty("ST")
    abstract String getSt();

    @JsonProperty("CN")
    abstract String getCn();

    @JsonProperty("PW")
    abstract String getPw();

    @JsonProperty("MN")
    abstract String getMn();

    @JsonProperty("Flag")
    abstract List<DataFlag> getDataFlag();

    @JsonProperty("CP")
    abstract CpData getCp();

}
