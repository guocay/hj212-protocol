package com.github.guocay.hj212.server.portal.trans.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
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

}
