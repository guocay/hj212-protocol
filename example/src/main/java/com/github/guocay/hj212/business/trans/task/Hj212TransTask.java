package com.github.guocay.hj212.business.trans.task;

import com.github.guocay.hj212.business.core.annotaion.TaskMethod;
import com.github.guocay.hj212.business.core.util.Constant;
import com.github.guocay.hj212.business.trans.service.MonitorMetaDataService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时任务,用于执行有关212协议设备的数据(监测因子)转换
 * 每十分钟运行一次,查询前十五分钟内未处理的监测因子数据
 * @author GuoKai
 * @since 2019/11/19
 */
@Configuration
@ConfigurationProperties("application")
public class Hj212TransTask {

    private String parkCode;

    private final MonitorMetaDataService service;

	public Hj212TransTask(MonitorMetaDataService service) {
		this.service = service;
	}

	@Scheduled(fixedRate = Constant.MonitorTask.TASK_CYCLE,initialDelay = 5*1000L)
    @TaskMethod(taskId = "TASK0001", taskName = "监测元数据处理任务",taskSwitch = Constant.MonitorTask.TASK_SWITCH)
    public void monitorTask(){
        service.trans(parkCode);
    }

	public String getParkCode() {
		return parkCode;
	}

	public Hj212TransTask setParkCode(String parkCode) {
		this.parkCode = parkCode;
		return this;
	}
}
