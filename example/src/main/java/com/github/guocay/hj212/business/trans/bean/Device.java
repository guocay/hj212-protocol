package com.github.guocay.hj212.business.trans.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("workequipment_maintain")
public class Device {

	@TableId("id")
	private String id;

	@TableField("state")
	private String state;

	@TableField("mncode")
	private String mncode;

	@TableField("park_code")
	private String parkCode;

	@TableField("monitor_type")
	private String monitorType;

	public String getId() {
		return id;
	}

	public Device setId(String id) {
		this.id = id;
		return this;
	}

	public String getState() {
		return state;
	}

	public Device setState(String state) {
		this.state = state;
		return this;
	}

	public String getMncode() {
		return mncode;
	}

	public Device setMncode(String mncode) {
		this.mncode = mncode;
		return this;
	}

	public String getParkCode() {
		return parkCode;
	}

	public Device setParkCode(String parkCode) {
		this.parkCode = parkCode;
		return this;
	}

	public String getMonitorType() {
		return monitorType;
	}

	public Device setMonitorType(String monitorType) {
		this.monitorType = monitorType;
		return this;
	}
}
