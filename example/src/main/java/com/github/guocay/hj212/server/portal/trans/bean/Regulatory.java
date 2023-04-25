package com.github.guocay.hj212.server.portal.trans.bean;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
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

}
