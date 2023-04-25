package com.github.guocay.hj212.business.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

@TableName("t_monitor_error")
public class MonitorErrorPo {

    @TableId("id")
    private String id;

    @TableField("info_id")
    private String infoId;

    @TableField("error")
    private String error;

    @TableField("time")
    private LocalDateTime time;

	public String getId() {
		return id;
	}

	public MonitorErrorPo setId(String id) {
		this.id = id;
		return this;
	}

	public String getInfoId() {
		return infoId;
	}

	public MonitorErrorPo setInfoId(String infoId) {
		this.infoId = infoId;
		return this;
	}

	public String getError() {
		return error;
	}

	public MonitorErrorPo setError(String error) {
		this.error = error;
		return this;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public MonitorErrorPo setTime(LocalDateTime time) {
		this.time = time;
		return this;
	}
}
