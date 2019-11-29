package com.jq.user;

import com.jq.boot.feign.FeignConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @author jim
 */
@EnableFeignClients(basePackages = {"com.jq", "com.liying"}, defaultConfiguration = FeignConfig.class)
@SpringBootApplication(scanBasePackages = {"com.jq", "com.liying"})
public class Application {
    private final static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        logger.info("server start...");
    }

}