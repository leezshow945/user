package com.jq.user.support;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * 直接通过Spring 上下文获取SpringBean,用于多线程环境
 * by lee
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware{

    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public static Object getBeanByName(String beanName) {
        if (context == null){
            return null;
        }
        return context.getBean(beanName);
    }

    public static <T> T getBean(Class<T> type) {
        return context.getBean(type);
    }

    // 国际化使用
    public static String getMessage(String key) {
        return context.getMessage(key, null, Locale.getDefault());
    }


    /// 获取当前环境
    public static String getActiveProfile() {
        return context.getEnvironment().getActiveProfiles()[0];
    }


}
