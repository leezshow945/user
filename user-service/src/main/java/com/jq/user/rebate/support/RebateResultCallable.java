package com.jq.user.rebate.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.jq.framework.core.exception.ErrorCode;
import com.jq.user.constant.UserConstant;
import com.jq.user.customer.dao.UserRebateDao;
import com.jq.user.exception.UserException;
import com.jq.user.proxy.entity.UserProxyEntity;
import com.jq.user.proxy.service.UserProxyInnerService;
import com.jq.user.rebate.dao.RebateResultInfoDao;
import com.jq.user.rebate.dao.RebateVideoResultInfoDao;
import com.jq.user.rebate.entity.RebateResultEntity;
import com.jq.user.rebate.entity.RebateResultInfoEntity;
import com.jq.user.rebate.entity.RebateRuleInfoEntity;
import com.jq.user.rebate.entity.RebateVideoResultInfoEntity;
import com.jq.user.rebate.service.RebateRuleInfoInnerService;
import com.jq.user.rebate.service.RebateRuleInnerService;
import com.jq.user.support.AmazonSQSUtil;
import com.jq.user.support.SpringBeanUtil;
import com.liying.trade.user.resp.EffectiveBetUserResp;
import com.liying.trade.user.resp.VideoBetUserResp;
import com.liying.trade.user.vo.IncomePayReq;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * 多线程返水任务
 * 根据用户投注与返水规则进行返水记录并返回返水金额
 */
public class RebateResultCallable implements Callable<Long> {

    private final static org.slf4j.Logger logger = (org.slf4j.Logger) LoggerFactory.getLogger(RebateResultCallable.class);


    private UserRebateDao userRebateDao;
    private RebateRuleInfoInnerService rebateRuleInfoInnerService;
    private RebateResultInfoDao rebateResultInfoDao;
    private RebateVideoResultInfoDao rebateVideoResultInfoDao;
    private UserProxyInnerService userProxyInnerService;
    private RebateRuleInnerService rebateRuleInnerService;

    private RebateResultEntity resultEntity;
    private EffectiveBetUserResp betResp;
    private String batchNO;
    private String queueUrl;

    public RebateResultCallable(RebateResultEntity resultEntity, EffectiveBetUserResp betResp, String batchNO, String queueUrl) {
        this.resultEntity = resultEntity;
        this.betResp = betResp;
        this.batchNO = batchNO;
        this.queueUrl = queueUrl;
        this.userRebateDao = SpringBeanUtil.getBean(UserRebateDao.class);
        this.rebateRuleInfoInnerService = SpringBeanUtil.getBean(RebateRuleInfoInnerService.class);
        this.rebateResultInfoDao = SpringBeanUtil.getBean(RebateResultInfoDao.class);
        this.rebateVideoResultInfoDao = SpringBeanUtil.getBean(RebateVideoResultInfoDao.class);
        this.userProxyInnerService = SpringBeanUtil.getBean(UserProxyInnerService.class);
        this.rebateRuleInnerService = SpringBeanUtil.getBean(RebateRuleInnerService.class);
    }


    @Override
    public Long call() {
        RebateResultInfoEntity infoEntity = new RebateResultInfoEntity();
        BeanUtil.copyProperties(betResp, infoEntity);
        infoEntity.setUserName(betResp.getUsername());//username属性名不同
        infoEntity.setResultId(resultEntity.getId());
        infoEntity.setId(IdWorker.getId());
        UserProxyEntity dirHighUserProxy = userProxyInnerService.getDirHighUserProxy(betResp.getUserId(), resultEntity.getSiteId());
        if (dirHighUserProxy != null) {
            infoEntity.setHighLevelAccount(dirHighUserProxy.getHighUserName());
        }
        //获取符合当前用户有效投注的规则组详情
        double rebate;
        RebateRuleInfoEntity thisRule = rebateRuleInfoInnerService.getRuleInfoByBet(resultEntity.getSiteId(), resultEntity.getRuleId(), betResp.getEffectiveBets());
        if (thisRule.getGpcRebate() > 0 && betResp.getGpcBets() > 0) {
            rebate = (Math.round((thisRule.getGpcRebate() / (double) 100) * (betResp.getGpcBets() / 100)));
            infoEntity.setGpcRebate(rebate > 0 ? Math.round(rebate) : 0);
        }
        if (thisRule.getDpcRebate() > 0 && betResp.getDpcBets() > 0) {
            rebate = (Math.round((thisRule.getDpcRebate() / (double) 100) * (betResp.getDpcBets() / 100)));
            infoEntity.setDpcRebate(rebate > 0 ? Math.round(rebate) : 0);
        }
        if (thisRule.getFlcRebate() > 0 && betResp.getFlcBets() > 0) {
            rebate = (Math.round((thisRule.getFlcRebate() / (double) 100) * (betResp.getFlcBets() / 100)));
            infoEntity.setFlcRebate(rebate > 0 ? Math.round(rebate) : 0);
        }
        if (thisRule.getTycpRebate() > 0 && betResp.getTycpBets() > 0) {
            rebate = (Math.round((thisRule.getTycpRebate() / (double) 100) * (betResp.getTycpBets() / 100)));
            infoEntity.setTycpRebate(rebate > 0 ? Math.round(rebate) : 0);
        }
        if (thisRule.getTyRebate() > 0 && betResp.getTyBets() > 0) {
            rebate = (Math.round((thisRule.getTyRebate() / (double) 100) * (betResp.getTyBets() / 100)));
            infoEntity.setTyRebate(rebate > 0 ? Math.round(rebate) : 0);
        }
        if (thisRule.getQtRebate() > 0 && betResp.getQtBets() > 0) {
            rebate = (Math.round((thisRule.getQtRebate() / (double) 100) * (betResp.getQtBets() / 100)));
            infoEntity.setQtRebate(rebate > 0 ? Math.round(rebate) : 0);
        }
        if (thisRule.getLhcRebate0() > 0 && betResp.getLhc0Bets() > 0) {
            rebate = (Math.round((thisRule.getLhcRebate0() / (double) 100) * (betResp.getLhc0Bets() / 100)));
            infoEntity.setLhc0Rebate(rebate > 0 ? Math.round(rebate) : 0);
        }
        if (thisRule.getLhcRebate1() > 0 && betResp.getLhc1Bets() > 0) {
            rebate = (Math.round((thisRule.getLhcRebate1() / (double) 100) * (betResp.getLhc1Bets() / 100)));
            infoEntity.setLhc1Rebate(rebate > 0 ? Math.round(rebate) : 0);
        }
        if (thisRule.getLhcRebate2() > 0 && betResp.getLhc2Bets() > 0) {
            rebate = (Math.round((thisRule.getLhcRebate2() / (double) 100) * (betResp.getLhc2Bets() / 100)));
            infoEntity.setLhc2Rebate(rebate > 0 ? Math.round(rebate) : 0);
        }
        if (thisRule.getLhcRebate3() > 0 && betResp.getLhc3Bets() > 0) {
            rebate = (Math.round((thisRule.getLhcRebate3() / (double) 100) * (betResp.getLhc3Bets() / 100)));
            infoEntity.setLhc3Rebate(rebate > 0 ? Math.round(rebate) : 0);
        }


        //总返水金额
        //计算彩票总返水金额
        long sumRebate = infoEntity.getGpcRebate() + infoEntity.getDpcRebate() + infoEntity.getFlcRebate() + infoEntity.getTyRebate()
         + infoEntity.getTycpRebate() + infoEntity.getLhc0Rebate() + infoEntity.getLhc1Rebate()
         + infoEntity.getLhc2Rebate() + infoEntity.getLhc3Rebate();

        //封装统计真人游戏返水值
        //返回的真人有效投注已做非0投注筛选，规则组Map已做非0返水值筛选
        Map<String, Long> videoMap = rebateRuleInnerService.getVideoRebateMap(thisRule.getId());

        for (VideoBetUserResp videoBet : betResp.getVideoBets()) {
            Long ruleRebate = videoMap.get(videoBet.getGameCode());
            //根据真人游戏code从返水规则videoMap取得该游戏返水值
            if (ObjectUtil.isNotNull(ruleRebate)) {
                RebateVideoResultInfoEntity videoEntity = new RebateVideoResultInfoEntity();
                BeanUtil.copyProperties(videoBet, videoEntity);
                videoEntity.setResultInfoId(infoEntity.getId());
                double videoRebate = (Math.round((ruleRebate / (double) 100) * (videoBet.getGameBet() / (double) 100)));
                videoEntity.setGameRebate(videoRebate > 0 ? Math.round(videoRebate) : 0);
                sumRebate += videoEntity.getGameRebate();
                rebateVideoResultInfoDao.insert(videoEntity);
            }
        }

        infoEntity.setAllRebates(sumRebate < thisRule.getRebateMost() ? sumRebate : thisRule.getRebateMost());
        infoEntity.setStatus(UserConstant.REBATE_STATUS);
        infoEntity.setCreateBy(resultEntity.getCreateBy());
        infoEntity.setUpdateBy(resultEntity.getCreateBy());
        infoEntity.setCreateTime(resultEntity.getCreateTime());
        infoEntity.setUpdateTime(resultEntity.getCreateTime());

        if (rebateResultInfoDao.insert(infoEntity) <= 0) {
            throw new UserException(ErrorCode.DEFAULT_CODE, ErrorCode.DEFAULT_MSG);
        }

        if (infoEntity.getAllRebates() > 0) {
            //封装aws发送消息
            IncomePayReq req = new IncomePayReq();
            req.setUserId(infoEntity.getUserId().toString());
            req.setSiteCode(resultEntity.getSiteCode());
            req.setMoney(infoEntity.getAllRebates().toString());
            req.setTradeType(UserConstant.TradeType.INCOME + "");
            req.setSubTradeType(UserConstant.TradeType.Income.REBATE_WATER + "");
            req.setBatchNo(batchNO);
            req.setPayNo(UserConstant.OrderNoStartsWith.IN + UUID.randomUUID().toString().replace("-", "").toUpperCase());
            req.setPayType(UserConstant.PayType.PAY_SYSTEM + "");
            req.setRemark("返水:" + resultEntity.getBeginTime() + "到" + resultEntity.getEndTime());
            req.setPlatformType(UserConstant.PC + "");
            req.setIsDemo(UserConstant.IS_F.toString());
            req.setOperTime(resultEntity.getDoTime());

            List<IncomePayReq> reqList = new ArrayList<>();
            reqList.add(req);

            SendMessageResult sendMessageResult = AmazonSQSUtil.SendMsg(queueUrl, JSONUtil.parse(reqList).toString());
            logger.info("返水操作发送完成------>" + sendMessageResult.toString());
        }
        return infoEntity.getAllRebates();
    }
}
