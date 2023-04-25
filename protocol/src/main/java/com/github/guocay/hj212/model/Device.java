package com.github.guocay.hj212.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.guocay.hj212.model.verify.groups.VersionGroup;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Max;

import java.math.BigDecimal;

/**
 * 污染治理设施
 * @author aCay
 */
public class Device {

    /**
     * 污染治理设施运行状态的实时采样值
     */
    @Max(value = 5, groups = VersionGroup.V2017.class)
    @JsonProperty("RS")
    private int rs;

    /**
     * 污染治理设施一日内的运行时间
     */
    @DecimalMax(value = "24", groups = VersionGroup.V2017.class)
    @JsonProperty("RT")
    private BigDecimal rt;

    public int getRs() {
        return rs;
    }

    public void setRs(int rs) {
        this.rs = rs;
    }

    public BigDecimal getRt() {
        return rt;
    }

    public void setRt(BigDecimal rt) {
        this.rt = rt;
    }
}
