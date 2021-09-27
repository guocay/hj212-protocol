package cn.zqgx.moniter.center.server.portal.core.aop;

import cn.zqgx.moniter.center.server.portal.bean.po.MoniterErrorPo;
import cn.zqgx.moniter.center.server.portal.bean.po.MoniterInfoPo;
import cn.zqgx.moniter.center.server.portal.core.annotaion.MoniterServiceListen;
import cn.zqgx.moniter.center.server.portal.core.annotaion.ZqgxTaskMethod;
import cn.zqgx.moniter.center.server.portal.core.log.LoggingHelper;
import cn.zqgx.moniter.center.server.portal.mapper.MoniterErrorMapping;
import cn.zqgx.moniter.center.server.portal.mapper.MoniterInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class MoniterServiceAop {

  @Autowired
  private MoniterErrorMapping errorMapping;

  @Autowired
  private MoniterInfoMapper infoMapper;

  @Around("@annotation(method)")
  public Object serviceBefore(ProceedingJoinPoint point, MoniterServiceListen method){
    LoggingHelper.enter();
    Object obj;
    Object[] args = point.getArgs();

    log.info("System Message ==> Moniter Data Is Processing,Request Data Is: {}",args[0]);

    LocalDateTime time = LocalDateTime.now(Clock.systemDefaultZone());
    MoniterInfoPo infoPo = MoniterInfoPo.builder().info((String)args[0]).time(time).IPAddress((String)args[1]).build();
    infoMapper.insert(infoPo);
    try {
      obj = point.proceed();
    }catch (Throwable ex){
      //异常情况下,记录异常数据;
      errorMapping.insert(MoniterErrorPo.builder().error(ex.getMessage()).infoId(infoPo.getId()).time(time).build());
      log.error(ex.getMessage(), ex);
      throw new RuntimeException(ex);
    }
    log.info("System Message ==> Response Data Is: {}",obj);
    return obj;
  }

  @Around("@annotation(method)")
  public Object loggerTaskAround(ProceedingJoinPoint point, ZqgxTaskMethod method){
    LoggingHelper.enter();
    Object obj = false;
    //TaskLoggingPo po = TaskLoggingPo.builder().taskId(method.taskId()).taskName(method.taskName()).execTime(LocalDateTime.now()).detail(method.detail()).build();
    log.info("---------------定时器[{}({})]执行开始----------------",method.taskName(),method.taskId());
    if (!method.taskSwitch()){
      //mapper.insert(po.toBuilder().success("4").build());
      log.info("---------------定时器[{}({})]没有执行----------------",method.taskName(),method.taskId());
      return obj;
    }
    try {
      obj = point.proceed();
    } catch (Throwable throwable) {
      log.error(throwable.getMessage(),throwable);
      throw new RuntimeException(throwable);
    }
    //mapper.insert(po.toBuilder().success((boolean)obj ? "1" : "2").build());
    log.info("---------------定时器[{}({})]执行结束,Result({})----------------",method.taskName(),method.taskId(),obj);
    return obj;
  }

}
