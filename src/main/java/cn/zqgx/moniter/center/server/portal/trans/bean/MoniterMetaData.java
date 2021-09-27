package cn.zqgx.moniter.center.server.portal.trans.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_moniter_factor")
public class MoniterMetaData {

    @TableId("id")
    private String id;

    @TableField("park_code")
    private String parkCode;

    @TableField("mncode")
    private String mncode;

    @TableField("data_time")
    private String dataTime;

    @TableField("factor")
    private String factor;

    @TableField("value")
    private String value;

    @TableField("data_status")
    private String dataStatus;

    @TableField("time")
    private LocalDateTime time;
}
