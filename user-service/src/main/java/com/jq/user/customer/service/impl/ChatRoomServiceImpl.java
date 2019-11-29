package com.jq.user.customer.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jq.user.code.UserCodeEnum;
import com.jq.user.constant.RedisKey;
import com.jq.user.customer.dao.UserDao;
import com.jq.user.customer.dao.UserTokenDao;
import com.jq.user.customer.entity.UserEntity;
import com.jq.user.customer.entity.UserTokenEntity;
import com.jq.user.customer.service.ChatRoomInnerService;
import com.jq.user.exception.UserException;
import com.jq.user.support.AsyncTask;
import com.liying.common.service.ApiResult;
import com.liying.common.service.RPCResult;
import com.liying.trade.order.api.OrderService;
import com.liying.trade.report.api.MemberReportService;
import com.liying.trade.report.resp.UserCurrentIncomeResp;
import com.liying.trade.report.resp.UserTotalAmountResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: Brady
 * @Description :
 * @Date: Create in 9:46 2018/12/28
 */

@Service
@Transactional
public class ChatRoomServiceImpl implements ChatRoomInnerService{

    private final static Logger logger = LoggerFactory.getLogger(ChatRoomServiceImpl.class);

    @Resource
    private StringRedisTemplate template;
    @Resource
    private UserTokenDao userTokenDao;
    @Resource
    private UserDao userDao;
    @Resource
    private AsyncTask asyncTask;
    @Resource
    private MemberReportService memberReportService;
    @Resource
    private OrderService orderService;
    @Value("${JWTTime}")
    private Integer ttlMillis;

    @Override
    public String getTokenInfo(String token) {

        if (StrUtil.isBlank(token)) {
            throw new UserException(UserCodeEnum.USER_TOKEN.getCode(), "token缺失");
        }
        ValueOperations<String, String> ops = template.opsForValue();
        String redisInfo = ops.get(RedisKey.USER_TOKEN + token);
        String msg = "getTokenApi 入参:" + token + ",redis查询结果为" + (StrUtil.isNotBlank(redisInfo) ? "存在" : "不存在");

        //进入redis存在业务逻辑
        if (StrUtil.isNotBlank(redisInfo)) {
            long expire = template.getExpire(RedisKey.USER_TOKEN + token);
            msg += ",剩余有效时限为" + expire;
            //1分钟内防刷
            if (expire < ((ttlMillis - 1) * 60)) {
                //异步处理token刷新业务逻辑
                msg += ",防刷鉴定通过,进行异步刷新";
                JSONObject userInfoJson = JSONUtil.parseObj(redisInfo);
                asyncTask.refreshTask(userInfoJson.getLong("userId"));
            }
            logger.info(msg);
            return redisInfo;
        } else {
            //进入redis不存在业务逻辑
            UserTokenEntity userTokenParam = new UserTokenEntity();
            userTokenParam.setSecretToken(token);
            UserTokenEntity userTokenDB = userTokenDao.selectOne(new QueryWrapper<>(userTokenParam));
            msg += ",db 查询为" + (ObjectUtil.isNotNull(userTokenDB) ? userTokenDB.toString() : "不存在");

            if (ObjectUtil.isNotNull(userTokenDB)) {
                Calendar nowCalendar = Calendar.getInstance();
                boolean flag = (nowCalendar.getTime().getTime() < userTokenDB.getAccessExpire().getTime());
                msg += ",过期时间判断为" + (flag ? "未过期" : "已过期");
                if (flag) {
                    //db未过期 进行防刷鉴定 异步方法刷新 redis db有效时间
                    Calendar tokenCalendar = Calendar.getInstance();
                    tokenCalendar.setTime(userTokenDB.getAccessExpire());
                    tokenCalendar.add(Calendar.MINUTE, -ttlMillis + 1);
                    msg += ",当前时间:" + nowCalendar.getTime().toString() + ",db过期时间计算后:" + tokenCalendar.getTime().toString();
                    if (nowCalendar.after(tokenCalendar)) {
                        msg += ",防刷鉴定通过,进行异步刷新";
                        asyncTask.refreshTask(userTokenDB);
                    }
                    logger.info(msg);
                    return userTokenDB.getAccessToken();
                }
            }

            //redis db为空,鉴权过期
            //redis为空,db已过期,鉴权过期
            logger.info(msg);
            throw new UserException(UserCodeEnum.USER_TOKEN.getCode(), "token已过期");
        }
    }

    @Override
    public List<Map<String, Object>> queryUserInfo(Long siteId) {
        List<Map<String,Object>> resultList = userDao.queryUserInfo(siteId);
        return resultList;
    }

    @Override
    public List<Map<String, Object>> queryUserInfo(List<Long> idList) {
        List<Map<String,Object>> resultList = userDao.queryUserInfoByIdList(idList);
        return resultList;
    }


    @Override
    public Map<String, Object> getUserInfo(Long id) {
        Map<String,Object> resultMap = userDao.getUserInfo(id);
        return resultMap;
    }

    @Override
    public Map<String, Object> getDetailUserInfo(Long id,String siteCode) {
        Map<String,Object> resultMap = userDao.getUserInfo(id);
        List<Long> idList = new ArrayList<>();
        idList.add(id);
        ApiResult<List<UserTotalAmountResp>> listApiResult = memberReportService.countUserTotalAmount(siteCode, idList);
        if(!RPCResult.checkApiResult(listApiResult)){
            throw new UserException(UserCodeEnum.RPC_SERVER_UNAVAILABLE.getCode(),"资金服务调用失败");
        }
        UserTotalAmountResp data = listApiResult.getData().get(0);
        resultMap.put("charge",data.getIncomeMoney());
        resultMap.put("charge7",data.getSevenIncomeMoney());
        resultMap.put("bet",data.getOrderMoney());
        return resultMap;
    }

    @Override
    public List<Map<String, Object>> queryDetailUserInfo(List<Long> idList, String siteCode) {
        List<Map<String,Object>> userInfoList = userDao.queryUserInfoByIdList(idList);
        ApiResult<List<UserTotalAmountResp>> listApiResult = memberReportService.countUserTotalAmount(siteCode, idList);
        if(!RPCResult.checkApiResult(listApiResult)){
            throw new UserException(UserCodeEnum.RPC_SERVER_UNAVAILABLE.getCode(),"memberReportService服务调用失败");
        }
        List<Map<String,Object>> resultList = new ArrayList<>();
        List<UserTotalAmountResp> data = listApiResult.getData();
        for (Map<String,Object> map :userInfoList) {
            if(ObjectUtil.isNull(map) || map.isEmpty()){
                break;
            }
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.putAll(map);
            for (UserTotalAmountResp userTotalAmountResp: data) {
                if(ObjectUtil.equal(map.get("id"),userTotalAmountResp.getUserId())){
                    resultMap.put("charge",userTotalAmountResp.getIncomeMoney());
                    resultMap.put("charge7",userTotalAmountResp.getSevenIncomeMoney());
                    resultMap.put("bet",userTotalAmountResp.getOrderMoney());
                    resultList.add(resultMap);
                    break;
                }
            }
            resultList.add(resultMap);
        }
        return resultList;
    }

    @Override
    public List<Map<String, Object>> queryCashInfo(List<Long> idList, String siteCode) {
        List<Map<String,Object>> infoMap = userDao.queryCashInfo(idList, siteCode);
        if(CollectionUtil.isEmpty(infoMap)){
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode(),UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        ApiResult<List<UserCurrentIncomeResp>> userCurrentIncome = memberReportService.getUserCurrentIncome(siteCode, idList);
        if(!RPCResult.checkApiResult(userCurrentIncome)){
            throw new UserException(UserCodeEnum.RPC_SERVER_UNAVAILABLE.getCode(),"memberReportService服务调用失败");
        }
        List<UserCurrentIncomeResp> cashList = userCurrentIncome.getData();
        ApiResult<Map<Long, Long>> todayWinLose = orderService.getTodayWinLose(siteCode, idList);
        if(!RPCResult.checkApiResult(todayWinLose)){
            throw new UserException(UserCodeEnum.RPC_SERVER_UNAVAILABLE.getCode(),"orderService服务调用失败");
        }
        Map<Long,Long> profitLossMap = todayWinLose.getData();
        List<Map<String,Object>> resultList = new ArrayList<>();
        for (Map<String,Object> userMap:infoMap) {
            if(userMap.containsKey("realName")){
                userMap.put("realName",Base64.decodeStr(userMap.get("realName").toString()));
            }
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.putAll(userMap);
            for (UserCurrentIncomeResp userCurrentIncomeResp:cashList) {
                if(ObjectUtil.equal(userMap.get("id"),userCurrentIncomeResp.getUserId())){
                    resultMap.put("income",userCurrentIncomeResp.getIncomeMoney());
                    resultMap.put("totalAmount",userCurrentIncomeResp.getTotalAmount());
                    break;
                }
            }
            if(profitLossMap.containsKey(Long.parseLong(userMap.get("id").toString()))){
                resultMap.put("profitLoss",profitLossMap.get(Long.parseLong(userMap.get("id").toString())));
            }
            resultList.add(resultMap);
        }
        return resultList;
    }


    @Override
    public Map<String,Object> loginSubmit(String userName, String password, Long siteId) {
        UserEntity user = userDao.findByUserName(userName,siteId);
        if(ObjectUtil.isNull(user)){
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode(),UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        String md5Pwd = SecureUtil.md5(password);
        if(!user.getPassword().equals(md5Pwd)){
            throw new UserException(UserCodeEnum.USER_NAMEORPWD_ERR.getCode(),UserCodeEnum.USER_NAMEORPWD_ERR.getMessage());
        }
        Map<String,Object> resultMap = userDao.getUserInfo(user.getId());
        return resultMap;
    }
}
