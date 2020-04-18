package com.chibiao.lms.config;

import com.chibiao.lms.aspect.LogAspects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 请输入描述
 *
 * @author : 迟彪
 * @date : 2020/4/16
 */
@Configuration
public class LogConfig {
    @Bean
    public LogAspects logAspects(){
        LogAspects logAspects = new LogAspects();
        logAspects.setAppName("lms");
        return logAspects;
    }
}
