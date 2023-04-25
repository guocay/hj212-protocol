package com.github.guocay.hj212.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.guocay.hj212.core.validator.field.CharArray;
import com.github.guocay.hj212.core.validator.field.Date;
import com.github.guocay.hj212.core.validator.field.NumberArray;
import com.github.guocay.hj212.model.verify.groups.VersionGroup;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.List;
import java.util.Map;

/**
 * 数据区
 * @author aCay
 */
public class CpData {

    public static String LIVE_SIDE = "LiveSide";
    public static String DEVICE = "Device";
    public static String POLLUTION = "Pollution";

    @Date(format = "yyyyMMddHHmmss")
    @JsonProperty("SystemTime")
    private String systemTime;

    @Date(format = "yyyyMMddHHmmssSSS", groups = VersionGroup.V2005.class)
    @JsonProperty("QN")
    private String qn;

    @NumberArray(integer = 3)
    @JsonProperty("QnRtn")
    private String qnRtn;

    @NumberArray(integer = 3)
    @JsonProperty("ExeRtn")
    private String exeRtn;

    @Max(value = 9999, groups = VersionGroup.V2005.class)
    @Min(value = 30, groups = VersionGroup.V2017.class)
    @Max(value = 3600, groups = VersionGroup.V2017.class)
    @JsonProperty("RtdInterval")
    private Integer rtdInterval;

    @Max(value = 99, groups = VersionGroup.V2017.class)
    @JsonProperty("MinInterval")
    private Integer minInterval;

    @Date(format = "yyyyMMddHHmmss", groups = VersionGroup.V2017.class)
    @JsonProperty("RestartTime")
    private String restartTime;

    @Date(format = "yyyyMMddHHmmss", groups = VersionGroup.V2005.class)
    @JsonProperty("AlarmTime")
    private String alarmTime;

    @NumberArray(integer = 1, min = 0, max = 1, groups = VersionGroup.V2005.class)
    @JsonProperty("AlarmType")
    private String alarmType;

    @NumberArray(integer = 20, groups = VersionGroup.V2005.class)
    @JsonProperty("ReportTarget")
    private String reportTarget;

    @CharArray(len = 6)
    @JsonProperty("PolId")
    private String polId;

    @Date(format = "yyyyMMddHHmmss")
    @JsonProperty("BeginTime")
    private String beginTime;

    @Date(format = "yyyyMMddHHmmss")
    @JsonProperty("EndTime")
    private String endTime;

    @Date(format = "yyyyMMddHHmmss")
    @JsonProperty("DataTime")
    private String dataTime;

    @NumberArray(integer = 4, groups = VersionGroup.V2005.class)
    @JsonProperty("ReportTime")
    private String reportTime;

    @NumberArray(integer = 14, groups = VersionGroup.V2005.class)
    @JsonProperty("DayStdValue")
    private String dayStdValue;

    @NumberArray(integer = 14, groups = VersionGroup.V2005.class)
    @JsonProperty("NightStdValue")
    private String nightStdValue;

    @Max(value = 9999, groups = VersionGroup.V2005.class)
    @JsonProperty("PNO")
    private Integer pNo;

    @Max(value = 9999, groups = VersionGroup.V2005.class)
    @JsonProperty("PNUM")
    private Integer pNum;

    @CharArray(len = 6, groups = VersionGroup.V2005.class)
    @JsonProperty("PW")
    private String pw;

    @CharArray(len = 6, groups = VersionGroup.V2017.class)
    @JsonProperty("NewPW")
    private String newPW;

    @Max(value = 99999, groups = VersionGroup.V2005.class)
    @Max(value = 99, groups = VersionGroup.V2017.class)
    @JsonProperty("OverTime")
    private Integer overTime;

    @Max(value = 99)
    @JsonProperty("ReCount")
    private Integer reCount;

    @Max(value = 99999, groups = VersionGroup.V2005.class)
    @JsonProperty("WarnTime")
    private Integer warnTime;

    @Max(value = 99, groups = VersionGroup.V2005.class)
    @Min(value = 0, groups = VersionGroup.V2017.class)
    @Max(value = 24, groups = VersionGroup.V2017.class)
    @JsonProperty("CTime")
    @JsonAlias({ "Ctime", "cTime" })
    private Integer cTime;

    @Min(value = 0, groups = VersionGroup.V2017.class)
    @Max(value = 99, groups = VersionGroup.V2017.class)
    @JsonProperty("VaseNo")
    private Integer vaseNo;

    @Date(format = "HHmmss", groups = VersionGroup.V2017.class)
    @JsonProperty("CstartTime")
    private String cStartTime;

    @Min(value = 0, groups = VersionGroup.V2017.class)
    @Max(value = 120, groups = VersionGroup.V2017.class)
    @JsonProperty("Stime")
    private Integer sTime;

    @CharArray(len = 6, groups = VersionGroup.V2017.class)
    @JsonProperty("InfoId")
    private String infoId;

    @JsonProperty("Flag")
    private List<DataFlag> dataFlag;

    @JsonProperty("Pollution")
    private Map<String,Pollution> pollution;

    @JsonProperty("Device")
    private Map<String,Device> device;

    @JsonProperty("LiveSide")
    private Map<String,LiveSide> liveSide;

    public String getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(String systemTime) {
        this.systemTime = systemTime;
    }

    public String getQn() {
        return qn;
    }

    public void setQn(String qn) {
        this.qn = qn;
    }

    public String getQnRtn() {
        return qnRtn;
    }

    public void setQnRtn(String qnRtn) {
        this.qnRtn = qnRtn;
    }

    public String getExeRtn() {
        return exeRtn;
    }

    public void setExeRtn(String exeRtn) {
        this.exeRtn = exeRtn;
    }

    public Integer getRtdInterval() {
        return rtdInterval;
    }

    public void setRtdInterval(Integer rtdInterval) {
        this.rtdInterval = rtdInterval;
    }

    public Integer getMinInterval() {
        return minInterval;
    }

    public void setMinInterval(Integer minInterval) {
        this.minInterval = minInterval;
    }

    public String getRestartTime() {
        return restartTime;
    }

    public void setRestartTime(String restartTime) {
        this.restartTime = restartTime;
    }

    public Map<String, Pollution> getPollution() {
        return pollution;
    }

    public void setPollution(Map<String, Pollution> pollution) {
        this.pollution = pollution;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getReportTarget() {
        return reportTarget;
    }

    public void setReportTarget(String reportTarget) {
        this.reportTarget = reportTarget;
    }

    public String getPolId() {
        return polId;
    }

    public void setPolId(String polId) {
        this.polId = polId;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getDayStdValue() {
        return dayStdValue;
    }

    public void setDayStdValue(String dayStdValue) {
        this.dayStdValue = dayStdValue;
    }

    public String getNightStdValue() {
        return nightStdValue;
    }

    public void setNightStdValue(String nightStdValue) {
        this.nightStdValue = nightStdValue;
    }

    public List<DataFlag> getDataFlag() {
        return dataFlag;
    }

    public void setDataFlag(List<DataFlag> dataFlag) {
        this.dataFlag = dataFlag;
    }

    public Integer getpNo() {
        return pNo;
    }

    public void setpNo(Integer pNo) {
        this.pNo = pNo;
    }

    public Integer getpNum() {
        return pNum;
    }

    public void setpNum(Integer pNum) {
        this.pNum = pNum;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getNewPW() {
        return newPW;
    }

    public void setNewPW(String newPW) {
        this.newPW = newPW;
    }

    public Integer getOverTime() {
        return overTime;
    }

    public void setOverTime(Integer overTime) {
        this.overTime = overTime;
    }

    public Integer getReCount() {
        return reCount;
    }

    public void setReCount(Integer reCount) {
        this.reCount = reCount;
    }

    public Integer getWarnTime() {
        return warnTime;
    }

    public void setWarnTime(Integer warnTime) {
        this.warnTime = warnTime;
    }

    public Integer getcTime() {
        return cTime;
    }

    public void setcTime(Integer cTime) {
        this.cTime = cTime;
    }

    public Integer getVaseNo() {
        return vaseNo;
    }

    public void setVaseNo(Integer vaseNo) {
        this.vaseNo = vaseNo;
    }

    public String getcStartTime() {
        return cStartTime;
    }

    public void setcStartTime(String cStartTime) {
        this.cStartTime = cStartTime;
    }

    public Integer getsTime() {
        return sTime;
    }

    public void setsTime(Integer sTime) {
        this.sTime = sTime;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public Map<String,Device> getDevice() {
        return device;
    }

    public void setDevice(Map<String,Device> device) {
        this.device = device;
    }

    public Map<String,LiveSide> getLiveSide() {
        return liveSide;
    }

    public void setLiveSide(Map<String,LiveSide> liveSide) {
        this.liveSide = liveSide;
    }
}
