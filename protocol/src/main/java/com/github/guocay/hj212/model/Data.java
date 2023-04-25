package com.github.guocay.hj212.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.guocay.hj212.core.validator.field.CharArray;
import com.github.guocay.hj212.core.validator.field.Date;
import com.github.guocay.hj212.model.verify.groups.ModeGroup;
import com.github.guocay.hj212.model.verify.groups.VersionGroup;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.List;

/**
 * 数据段
 * @author aCay
 */
@SuppressWarnings("unused")
public class Data {

    public static String CP = "CP";
    public static String FLAG = "Flag";

    /**
     * 请求编号
     */
    @Date(format = "yyyyMMddHHmmssSSS")
    @JsonProperty("QN")
    private String qn;

    /**
     * 总包号
     */
    @Max(value = 9999)
    @Min(value = 1, groups = ModeGroup.UseSubPacket.class)
    @JsonProperty("PNUM")
    private Integer pNum;

    /**
     * 包号
     */
    @Max(value = 9999)
    @Min(value = 1, groups = ModeGroup.UseSubPacket.class)
    @JsonProperty("PNO")
    private Integer pNo;

    /**
     * 系统编号
     */
    @CharArray(len = 2)
    @JsonProperty("ST")
    private String st;

    /**
     * 命令编号
     */
    @CharArray(len = 4)
    @JsonProperty("CN")
    private String cn;

    /**
     * 访问密码
     */
    @CharArray(len = 6)
    @JsonProperty("PW")
    private String pw;

    /**
     * 设备唯一标识
     */
    @CharArray(len = 14, groups = VersionGroup.V2005.class)
    @CharArray(len = 24, groups = VersionGroup.V2017.class)
    @JsonProperty("MN")
    private String mn;

    /**
     * 是否拆分包及应答标志
     */
    @JsonProperty("Flag")
    private List<DataFlag> dataFlag;

    /**
     * 指令参数
     */
    @Valid
    @JsonProperty("CP")
    private CpData cp;

    public String getQn() {
        return qn;
    }

    public void setQn(String qn) {
        this.qn = qn;
    }

    public Integer getpNum() {
        return pNum;
    }

    public void setpNum(Integer pNum) {
        this.pNum = pNum;
    }

    public Integer getpNo() {
        return pNo;
    }

    public void setpNo(Integer pNo) {
        this.pNo = pNo;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getMn() {
        return mn;
    }

    public void setMn(String mn) {
        this.mn = mn;
    }

    public List<DataFlag> getDataFlag() {
        return dataFlag;
    }

    public void setDataFlag(List<DataFlag> dataFlag) {
        this.dataFlag = dataFlag;
    }

    public CpData getCp() {
        return cp;
    }

    public void setCp(CpData cp) {
        this.cp = cp;
    }

}
