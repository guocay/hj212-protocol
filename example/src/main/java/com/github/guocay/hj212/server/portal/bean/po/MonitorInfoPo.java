package com.github.guocay.hj212.server.portal.bean.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
