package cn.zqgx.moniter.center.server.portal.core.annotaion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标记监测服务,主要用于AOP接入保存文本并打印日志
 * @author GuoKai
 * @date 2019/11/22
 */

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MoniterServiceListen {
  String value() default "";
  String type() default "";
}
