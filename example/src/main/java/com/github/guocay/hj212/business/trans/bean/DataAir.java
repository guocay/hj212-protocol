package com.github.guocay.hj212.business.trans.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

@TableName("workairdata_real")
public class DataAir {
	@TableId("id")
	private String id;

	@TableField("mncode")
	private String mncode;

	@TableField("state")
	private String state;

	@TableField("monitor_time")
	private LocalDateTime monitorTime;

	@TableField("a21005")
	private String a21005;

	@TableField("a21026")
	private String a21026;

	@TableField("a21004")
	private String a21004;

	@TableField("a34004")
	private String a34004;

	@TableField("a34002")
	private String a34002;

	@TableField("o3")
	private String O3;

	public String getId() {
		return id;
	}

	public DataAir setId(String id) {
		this.id = id;
		return this;
	}

	public String getMncode() {
		return mncode;
	}

	public DataAir setMncode(String mncode) {
		this.mncode = mncode;
		return this;
	}

	public String getState() {
		return state;
	}

	public DataAir setState(String state) {
		this.state = state;
		return this;
	}

	public LocalDateTime getMonitorTime() {
		return monitorTime;
	}

	public DataAir setMonitorTime(LocalDateTime monitorTime) {
		this.monitorTime = monitorTime;
		return this;
	}

	public String getA21005() {
		return a21005;
	}

	public DataAir setA21005(String a21005) {
		this.a21005 = a21005;
		return this;
	}

	public String getA21026() {
		return a21026;
	}

	public DataAir setA21026(String a21026) {
		this.a21026 = a21026;
		return this;
	}

	public String getA21004() {
		return a21004;
	}

	public DataAir setA21004(String a21004) {
		this.a21004 = a21004;
		return this;
	}

	public String getA34004() {
		return a34004;
	}

	public DataAir setA34004(String a34004) {
		this.a34004 = a34004;
		return this;
	}

	public String getA34002() {
		return a34002;
	}

	public DataAir setA34002(String a34002) {
		this.a34002 = a34002;
		return this;
	}

	public String getO3() {
		return O3;
	}

	public DataAir setO3(String o3) {
		O3 = o3;
		return this;
	}
}
