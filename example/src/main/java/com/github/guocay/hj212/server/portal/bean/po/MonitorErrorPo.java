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
@TableName("t_monitor_error")
public class MonitorErrorPo {
    @TableId("id")
    private String id;

    @TableField("info_id")
    private String infoId;

    @TableField("error")
    private String error;

    @TableField("time")
    private LocalDateTime time;
}
