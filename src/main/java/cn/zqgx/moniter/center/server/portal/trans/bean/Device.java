package cn.zqgx.moniter.center.server.portal.trans.bean;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
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
}
