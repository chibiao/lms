package com.chibiao.lms.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 请输入描述
 *
 * @author : 迟彪
 * @date : 2020/5/11
 */
@Component
public class SpringUtil  implements ApplicationContextAware {
    /**
     * 当前IOC
     *
     */
    private static ApplicationContext applicationContext;
    /**
     * 设置applicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 从当前IOC获取bean
     */
    public static <T> T getObject(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }
}
