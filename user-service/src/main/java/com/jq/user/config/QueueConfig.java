package com.jq.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * AmazonSQS 队列配置
 */
@Component
public class QueueConfig {

    public static String AWS_ACCESS_KEY_ID;
    public static String AWS_ACCESS_KEY;
    public static String AWS_REGION;

    @Value("${AWS_ACCESS_KEY}")
    public void setAwsSecretAccessKey(String value){QueueConfig.AWS_ACCESS_KEY=value;}
    @Value("${AWS_ACCESS_KEY_ID}")
    public void setAwsAccessKeyId(String value){QueueConfig.AWS_ACCESS_KEY_ID=value;}
    @Value("${AWS_REGION}")
    public void setAwsRegion(String value){QueueConfig.AWS_REGION=value;}
}
