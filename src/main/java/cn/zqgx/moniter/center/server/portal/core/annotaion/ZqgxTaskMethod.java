package cn.zqgx.moniter.center.server.portal.core.annotaion;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ZqgxTaskMethod {
    String taskId();
    String taskName();
    boolean taskSwitch();
    String detail() default "";
}
