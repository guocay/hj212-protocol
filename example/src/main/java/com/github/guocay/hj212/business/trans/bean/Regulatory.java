package com.github.guocay.hj212.business.trans.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("s_regulatory")
public class Regulatory {

	@TableId("id")
	private String id;

	@TableField("state")
	private String state;

	@TableField("code")
	private String code;

	@TableField("type")
	private String type;

	@TableField("park_code")
	private String parkCode;

	public String getId() {
		return id;
	}

	public Regulatory setId(String id) {
		this.id = id;
		return this;
	}

	public String getState() {
		return state;
	}

	public Regulatory setState(String state) {
		this.state = state;
		return this;
	}

	public String getCode() {
		return code;
	}

	public Regulatory setCode(String code) {
		this.code = code;
		return this;
	}

	public String getType() {
		return type;
	}

	public Regulatory setType(String type) {
		this.type = type;
		return this;
	}

	public String getParkCode() {
		return parkCode;
	}

	public Regulatory setParkCode(String parkCode) {
		this.parkCode = parkCode;
		return this;
	}
}
