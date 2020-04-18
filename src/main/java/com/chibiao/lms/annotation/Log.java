package com.chibiao.lms.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 打印日志注解
 *
 * @author : 迟彪
 * @date : 2020/4/14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Log {
    /**
     * 方法log的key
     * @return
     */
    String jKey();

    /**
     * log开关
     * @return
     */
    boolean logSwitch() default true;

    /**
     * 异常返回结果
     * @return
     */
    boolean errorReturnHttpResult() default true;

}
