package com.github.guocay.hj212.server.portal.trans.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
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
}
