package com.jq.user.handler;

import com.jq.user.customer.service.HealthCheckInnerService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@JobHandler(value = "healthCheck")
public class HealthCheckHandler extends IJobHandler {

    private final static Logger logger = LoggerFactory.getLogger(HealthCheckHandler.class);
    @Resource
    private HealthCheckInnerService healthCheckInnerService;


    /**
     * check Service 注销不正常service
     * clean Consul unPassing Service
     * PASSING：正常  WARNING  CRITICAL  UNKNOWN：不正常
     * @return
     */

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        try {
            logger.info(">>>>>>>>>>>>>>>consul nopassing check start<<<<<<<<<<<<<<<");
            healthCheckInnerService.healthCheck();
            logger.info(">>>>>>>>>>>>>>>consul nopassing check end<<<<<<<<<<<<<<<");
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            logger.error("consul nopassing check fail!!!!!!!!!");
            return ReturnT.FAIL;
        }
    }
}
