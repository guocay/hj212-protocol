package com.github.guocay.hj212.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.guocay.hj212.core.validator.field.CharArray;
import com.github.guocay.hj212.core.validator.field.Date;
import com.github.guocay.hj212.core.validator.field.NumberArray;
import com.github.guocay.hj212.model.verify.groups.VersionGroup;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Max;

import java.math.BigDecimal;

/**
 * 污染因子
 * @author aCay
 */
public class Pollution {

    /**
     * 污染物采样时间
     */
    @Date(format = "yyyyMMddHHmmssSSS", groups = VersionGroup.V2017.class)
    @JsonProperty("SampleTime")
    private String sampleTime;

    /**
     * 污染物实时采样数据
     */
    @JsonProperty("Rtd")
    private BigDecimal rtd;

    /**
     * 污染物指定时间内最小值
     */
    @JsonProperty("Min")
    private BigDecimal min;

    /**
     * 污染物指定时间内平均值
     */
    @JsonProperty("Avg")
    private BigDecimal avg;

    /**
     * 污染物指定时间内最大值
     */
    @JsonProperty("Max")
    private BigDecimal max;

    /**
     * 污染物实时采样折算数据
     */
    @JsonProperty("ZsRtd")
    private BigDecimal zsRtd;

    /**
     * 污染物指定时间内最小折算值
     */
    @JsonProperty("ZsMin")
    private BigDecimal zsMin;

    /**
     * 污染物指定时间内平均折算值
     */
    @JsonProperty("ZsAvg")
    private BigDecimal zsAvg;

    /**
     * 污染物指定时间内最大折算值
     */
    @JsonProperty("ZsMax")
    private BigDecimal zsMax;

    /**
     * 监测污染物实时数据标记
     */
    @CharArray(len = 1, groups = VersionGroup.V2017.class)
    @JsonProperty("Flag")
    private String flag;

    /**
     * 监测仪器扩充数据标记
     */
    @CharArray(len = 4, groups = VersionGroup.V2017.class)
    @JsonProperty("EFlag")
    private String eFlag;

    /**
     * 污染物指定时间内累计值
     */
    @JsonProperty("Cou")
    private BigDecimal cou;

    /**
     * 设备运行状态的实时采样值
     */
    @Max(value = 1, groups = VersionGroup.V2005.class)
    @JsonProperty("RS")
    private Integer rs;

    /**
     * 设备指定时间内的运行时间
     */
    @DecimalMax(value = "24", groups = VersionGroup.V2005.class)
    @JsonProperty("RT")
    private BigDecimal rt;

    /**
     * 污染物报警期间内采样值
     */
    @NumberArray(integer = 14, fraction = 2, groups = VersionGroup.V2005.class)
    @JsonProperty("Ala")
    private BigDecimal ala;

    /**
     * 污染物报警上限值
     */
    @NumberArray(integer = 14, fraction = 2, groups = VersionGroup.V2005.class)
    @JsonProperty("UpValue")
    private BigDecimal upValue;

    /**
     * 污染物报警下限值
     */
    @NumberArray(integer = 14, fraction = 2, groups = VersionGroup.V2005.class)
    @JsonProperty("LowValue")
    private BigDecimal lowValue;

    /**
     * 噪声监测日历史数据
     */
    @NumberArray(integer = 14, fraction = 2, groups = VersionGroup.V2005.class)
    @NumberArray(integer = 3, fraction = 1, groups = VersionGroup.V2017.class)
    @JsonProperty("Data")
    private String data;

    /**
     * 噪声昼间历史数据
     */
    @NumberArray(integer = 14, fraction = 2, groups = VersionGroup.V2005.class)
    @NumberArray(integer = 3, fraction = 1, groups = VersionGroup.V2017.class)
    @JsonProperty("DayData")
    private String dayData;

    /**
     * 噪声夜间历史数据
     */
    @NumberArray(integer = 14, fraction = 2, groups = VersionGroup.V2005.class)
    @NumberArray(integer = 3, fraction = 1, groups = VersionGroup.V2017.class)
    @JsonProperty("NightData")
    private String nightData;

    public String getSampleTime() {
        return sampleTime;
    }

    public void setSampleTime(String sampleTime) {
        this.sampleTime = sampleTime;
    }

    public BigDecimal getRtd() {
        return rtd;
    }

    public void setRtd(BigDecimal rtd) {
        this.rtd = rtd;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public BigDecimal getAvg() {
        return avg;
    }

    public void setAvg(BigDecimal avg) {
        this.avg = avg;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getZsRtd() {
        return zsRtd;
    }

    public void setZsRtd(BigDecimal zsRtd) {
        this.zsRtd = zsRtd;
    }

    public BigDecimal getZsMin() {
        return zsMin;
    }

    public void setZsMin(BigDecimal zsMin) {
        this.zsMin = zsMin;
    }

    public BigDecimal getZsAvg() {
        return zsAvg;
    }

    public void setZsAvg(BigDecimal zsAvg) {
        this.zsAvg = zsAvg;
    }

    public BigDecimal getZsMax() {
        return zsMax;
    }

    public void setZsMax(BigDecimal zsMax) {
        this.zsMax = zsMax;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String geteFlag() {
        return eFlag;
    }

    public void seteFlag(String eFlag) {
        this.eFlag = eFlag;
    }

    public BigDecimal getCou() {
        return cou;
    }

    public void setCou(BigDecimal cou) {
        this.cou = cou;
    }

    public Integer getRs() {
        return rs;
    }

    public void setRs(Integer rs) {
        this.rs = rs;
    }

    public BigDecimal getRt() {
        return rt;
    }

    public void setRt(BigDecimal rt) {
        this.rt = rt;
    }

    public BigDecimal getAla() {
        return ala;
    }

    public void setAla(BigDecimal ala) {
        this.ala = ala;
    }

    public BigDecimal getUpValue() {
        return upValue;
    }

    public void setUpValue(BigDecimal upValue) {
        this.upValue = upValue;
    }

    public BigDecimal getLowValue() {
        return lowValue;
    }

    public void setLowValue(BigDecimal lowValue) {
        this.lowValue = lowValue;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDayData() {
        return dayData;
    }

    public void setDayData(String dayData) {
        this.dayData = dayData;
    }

    public String getNightData() {
        return nightData;
    }

    public void setNightData(String nightData) {
        this.nightData = nightData;
    }
}
