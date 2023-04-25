package com.github.guocay.hj212.business.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

@TableName("t_monitor_info")
public class MonitorInfoPo {
    @TableId("id")
    private String id;

    @TableField("info")
    private String info;

    @TableField("ip_address")
    private String IPAddress;

    @TableField("time")
    private LocalDateTime time;

	public String getId() {
		return id;
	}

	public MonitorInfoPo setId(String id) {
		this.id = id;
		return this;
	}

	public String getInfo() {
		return info;
	}

	public MonitorInfoPo setInfo(String info) {
		this.info = info;
		return this;
	}

	public String getIPAddress() {
		return IPAddress;
	}

	public MonitorInfoPo setIPAddress(String IPAddress) {
		this.IPAddress = IPAddress;
		return this;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public MonitorInfoPo setTime(LocalDateTime time) {
		this.time = time;
		return this;
	}
}
