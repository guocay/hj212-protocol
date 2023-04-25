package com.github.guocay.hj212.business.trans.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

@TableName("workdangergasdata_real")
public class DataVoc {

	@TableId("id")
	private String id;

	@TableField("mncode")
	private String mncode;

	@TableField("state")
	private String state;

	@TableField("monitor_time")
	private LocalDateTime monitorTime;

	@TableField("a24088")
	private String a24088;

	@TableField("a25005")
	private String a25005;

	@TableField("a05002")
	private String a05002;

	public String getId() {
		return id;
	}

	public DataVoc setId(String id) {
		this.id = id;
		return this;
	}

	public String getMncode() {
		return mncode;
	}

	public DataVoc setMncode(String mncode) {
		this.mncode = mncode;
		return this;
	}

	public String getState() {
		return state;
	}

	public DataVoc setState(String state) {
		this.state = state;
		return this;
	}

	public LocalDateTime getMonitorTime() {
		return monitorTime;
	}

	public DataVoc setMonitorTime(LocalDateTime monitorTime) {
		this.monitorTime = monitorTime;
		return this;
	}

	public String getA24088() {
		return a24088;
	}

	public DataVoc setA24088(String a24088) {
		this.a24088 = a24088;
		return this;
	}

	public String getA25005() {
		return a25005;
	}

	public DataVoc setA25005(String a25005) {
		this.a25005 = a25005;
		return this;
	}

	public String getA05002() {
		return a05002;
	}

	public DataVoc setA05002(String a05002) {
		this.a05002 = a05002;
		return this;
	}
}
