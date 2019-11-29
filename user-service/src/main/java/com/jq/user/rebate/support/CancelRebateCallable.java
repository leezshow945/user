package com.jq.user.rebate.support;

import cn.hutool.json.JSONUtil;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.jq.user.constant.UserConstant;
import com.jq.user.rebate.dao.RebateResultDao;
import com.jq.user.rebate.dao.RebateResultInfoDao;
import com.jq.user.rebate.entity.RebateResultEntity;
import com.jq.user.rebate.entity.RebateResultInfoEntity;
import com.jq.user.support.AmazonSQSUtil;
import com.jq.user.support.SpringBeanUtil;
import com.liying.trade.user.vo.IncomePayReq;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * 多线程冲销任务
 * 根据用户返水记录进行冲销操作
 */

public class CancelRebateCallable implements Callable<Long> {

    private final static org.slf4j.Logger logger = (org.slf4j.Logger) LoggerFactory.getLogger(CancelRebateCallable.class);

    private RebateResultInfoEntity infoEntity;
    private String batchNO;
    private String queueUrl;
    private String createBy;
    private RebateResultEntity resultEntity;
    private RebateResultInfoDao rebateResultInfoDao;
    private RebateResultDao rebateResultDao;

    public CancelRebateCallable(RebateResultInfoEntity infoEntity, RebateResultEntity resultEntity, String batchNO, String queueUrl, String createBy) {
        this.infoEntity = infoEntity;
        this.resultEntity = resultEntity;
        this.batchNO = batchNO;
        this.queueUrl = queueUrl;
        this.createBy = createBy;
        this.rebateResultInfoDao = SpringBeanUtil.getBean(RebateResultInfoDao.class);
        this.rebateResultDao = SpringBeanUtil.getBean(RebateResultDao.class);
    }

    @Override
    public Long call() {
        infoEntity.setStatus(UserConstant.CANCEL_REBATE_STATUS);
        infoEntity.setUpdateTime(new Date());
        infoEntity.setUpdateBy(createBy);
        rebateResultInfoDao.updateById(infoEntity);
        if (infoEntity.getAllRebates() > 0) {
            //封装aws发送消息进行资金冲销
            IncomePayReq req = new IncomePayReq();
            req.setUserId(infoEntity.getUserId().toString());
            req.setSiteCode(resultEntity.getSiteCode());
            req.setMoney(infoEntity.getAllRebates().toString());
            req.setTradeType(UserConstant.TradeType.EXPENSE + "");
            req.setSubTradeType(UserConstant.TradeType.Expense.WRITE_OFF + "");
            req.setBatchNo(batchNO);
            req.setPayNo(UserConstant.OrderNoStartsWith.OUT + UUID.randomUUID().toString().replace("-", "").toUpperCase());
            req.setPayType(UserConstant.PayType.PAY_SYSTEM + "");
            req.setRemark("返水冲销:" + resultEntity.getBeginTime() + "到" + resultEntity.getEndTime());
            req.setPlatformType(UserConstant.PC + "");
            req.setIsDemo(UserConstant.IS_F.toString());
            req.setOperTime(infoEntity.getUpdateTime());

            List<IncomePayReq> reqList = new ArrayList<>();
            reqList.add(req);
            SendMessageResult sendMessageResult = AmazonSQSUtil.SendMsg(queueUrl, JSONUtil.parse(reqList).toString());
            logger.info("冲销返水操作发送完成------>" + sendMessageResult.toString());
        }
        return infoEntity.getAllRebates();
    }


}
