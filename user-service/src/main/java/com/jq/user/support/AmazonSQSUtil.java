package com.jq.user.support;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.*;
import com.jq.user.config.QueueConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * aws消息队列工具类
 */
public class AmazonSQSUtil {
    private static final Logger logger = LoggerFactory.getLogger(AmazonSQSUtil.class);

    private static String AWS_ACCESS_KEY_ID;
    private static String AWS_ACCESS_KEY;
    private static String AWS_REGION;

    private static AWSStaticCredentialsProvider credentialsProvider = null;
    private static AmazonSQS sqs = null;

    static {
        AWS_ACCESS_KEY_ID = QueueConfig.AWS_ACCESS_KEY_ID;
        AWS_ACCESS_KEY = QueueConfig.AWS_ACCESS_KEY;
        AWS_REGION = QueueConfig.AWS_REGION;
        AWSCredentials awsCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY_ID, AWS_ACCESS_KEY);
        credentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
        AmazonSQSClientBuilder sqsClientBuilder = AmazonSQSClientBuilder.standard()
                .withCredentials(credentialsProvider);
        sqsClientBuilder.setRegion(AWS_REGION);
        sqs = sqsClientBuilder.build();
    }

    //获取队列地址
    public static String getQueueUrl(String queueUrl) {
        return sqs.getQueueUrl(queueUrl).getQueueUrl();
    }

    //发送消息
    public static SendMessageResult SendMsg(String queueUrl, String dataStr) {
        SendMessageRequest sendRequest = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(dataStr)
                .withDelaySeconds(5);
        return sqs.sendMessage(sendRequest);
    }


    //接收消息
    public static void ReceivMsg(String[] args) {
        try {
            String queueUrl = sqs.getQueueUrl("lottery_queue").getQueueUrl();
            ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl);
            receiveMessageRequest.setMaxNumberOfMessages(1);
            List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
//            System.out.println(messages.get(0).getBody());
            sqs.deleteMessage(new DeleteMessageRequest(queueUrl, messages.get(0).getReceiptHandle()));
        } catch (Exception e) {
            logger.error("接收消息失败", e);
        }
    }

    public static void main(String[] args) {
        String queueUrl = sqs.getQueueUrl("income-pay-queue").getQueueUrl();
        SendMessageRequest sendRequest = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody("332")
                .withDelaySeconds(5);
        SendMessageResult sendMessageResult = sqs.sendMessage(sendRequest);
        String messageId = sendMessageResult.getMessageId();
    }


}
