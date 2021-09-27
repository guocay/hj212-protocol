package cn.zqgx.moniter.center.server.portal.trans.task;

import cn.zqgx.moniter.center.server.portal.core.annotaion.ZqgxTaskMethod;
import cn.zqgx.moniter.center.server.portal.core.util.Constant;
import cn.zqgx.moniter.center.server.portal.trans.service.MoniterMetaDataService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时任务,用于执行有关212协议设备的数据(监测因子)转换
 * 每十分钟运行一次,查询前十五分钟内未处理的监测因子数据
 * @author GuoKai
 * @date 2019/11/19
 */
@Data
@Slf4j
@Configuration
@ConfigurationProperties("application")
public class Hj212TransTask {

    private String parkCode;

    @Autowired
    private MoniterMetaDataService service;

    @Scheduled(fixedRate = Constant.MoniterTask.TASK_CYCLE,initialDelay = 5*1000L)
    @ZqgxTaskMethod(taskId = "TASK0001", taskName = "监测元数据处理任务",taskSwitch = Constant.MoniterTask.TASK_SWITCH)
    public boolean moniterTask(){
        return service.trans(parkCode);
    }
}
