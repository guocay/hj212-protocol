package com.github.guocay.hj212.business.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

@TableName("T_MONITOR_FACTOR")
public class MonitorFactorPo {
    @TableId("id")
    private String id;

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

	public MonitorFactorPo setId(String id) {
		this.id = id;
		return this;
	}

	public String getMncode() {
		return mncode;
	}

	public MonitorFactorPo setMncode(String mncode) {
		this.mncode = mncode;
		return this;
	}

	public String getDataTime() {
		return dataTime;
	}

	public MonitorFactorPo setDataTime(String dataTime) {
		this.dataTime = dataTime;
		return this;
	}

	public String getFactor() {
		return factor;
	}

	public MonitorFactorPo setFactor(String factor) {
		this.factor = factor;
		return this;
	}

	public String getValue() {
		return value;
	}

	public MonitorFactorPo setValue(String value) {
		this.value = value;
		return this;
	}

	public String getDataStatus() {
		return dataStatus;
	}

	public MonitorFactorPo setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
		return this;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public MonitorFactorPo setTime(LocalDateTime time) {
		this.time = time;
		return this;
	}
}
