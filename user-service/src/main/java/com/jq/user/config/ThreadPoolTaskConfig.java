package com.jq.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 * @Auther: Lee
 * @Date: 2019/1/14 10:20
 */
@Configuration
@EnableAsync
public class ThreadPoolTaskConfig {


    /**
     * 异步线程池配置
     * @return
     */
    @Bean("AsyncTaskExecutor")
    public ThreadPoolTaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);//线程池维护线程的最小数量
        executor.setQueueCapacity(10);//缓冲队列
        executor.setThreadNamePrefix("User-Service-");
        // 线程池对拒绝任务的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}
