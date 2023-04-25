package com.github.guocay.hj212.business.trans.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

@TableName("t_monitor_factor")
public class MonitorMetaData {

    @TableId("id")
    private String id;

    @TableField("park_code")
    private String parkCode;

    @TableField("mncode")
    private String mncode;

    @TableField("data_time")
    private String dataTime;

    @TableField("factor")
    private String factor;

    @TableField("value")
    private String value;

    @TableField("data_status")
    private String dataStatus;

    @TableField("time")
    private LocalDateTime time;

	public String getId() {
		return id;
	}

	public MonitorMetaData setId(String id) {
		this.id = id;
		return this;
	}

	public String getParkCode() {
		return parkCode;
	}

	public MonitorMetaData setParkCode(String parkCode) {
		this.parkCode = parkCode;
		return this;
	}

	public String getMncode() {
		return mncode;
	}

	public MonitorMetaData setMncode(String mncode) {
		this.mncode = mncode;
		return this;
	}

	public String getDataTime() {
		return dataTime;
	}

	public MonitorMetaData setDataTime(String dataTime) {
		this.dataTime = dataTime;
		return this;
	}

	public String getFactor() {
		return factor;
	}

	public MonitorMetaData setFactor(String factor) {
		this.factor = factor;
		return this;
	}

	public String getValue() {
		return value;
	}

	public MonitorMetaData setValue(String value) {
		this.value = value;
		return this;
	}

	public String getDataStatus() {
		return dataStatus;
	}

	public MonitorMetaData setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
		return this;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public MonitorMetaData setTime(LocalDateTime time) {
		this.time = time;
		return this;
	}
}
