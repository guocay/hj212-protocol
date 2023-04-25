package com.github.guocay.hj212.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.guocay.hj212.core.validator.field.CharArray;
import com.github.guocay.hj212.model.verify.groups.VersionGroup;

/**
 * 现场端
 * @author aCay
 */
public class LiveSide {

    /**
     * 现场端信息
     */
    @JsonProperty("Info")
    private String info;

    /**
     * 在线监控（监测）仪器仪表编码
     */
    @CharArray(len = 24, groups = VersionGroup.V2017.class)
    @JsonProperty("SN")
    private String sn;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
