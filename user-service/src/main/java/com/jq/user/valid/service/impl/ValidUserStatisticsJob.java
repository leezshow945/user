package com.jq.user.valid.service.impl;

import com.jq.user.valid.service.ValidUserStatisticsInnerService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@JobHandler(value = "validUser")
public class ValidUserStatisticsJob extends IJobHandler {

    private final static Logger logger = LoggerFactory.getLogger(ValidUserStatisticsJob.class);
    @Resource
    private ValidUserStatisticsInnerService validUserStatisticsInnerService;

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        try {
            logger.info(">>>>>>>>>>>>>>>定时统计有效会员开始<<<<<<<<<<<<<<<");
            validUserStatisticsInnerService.validUserDateSaveAndStatistics();
            logger.info(">>>>>>>>>>>>>>>定时统计有效会员结束<<<<<<<<<<<<<<<");
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            logger.error("定时统计有效会员失败!!!!!!!!!");
            return ReturnT.FAIL;
        }
    }
}
