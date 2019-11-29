package com.jq.user.support;

import cn.hutool.core.util.ObjectUtil;
import com.jq.user.constant.RedisKey;
import com.jq.user.customer.dao.UserTokenDao;
import com.jq.user.customer.entity.UserTokenEntity;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * 异步任务执行类
 *
 * @Auther: Lee
 * @Date: 2019/1/11 15:24
 */
@Component
public class AsyncTask {
    private final static org.slf4j.Logger logger = (org.slf4j.Logger) LoggerFactory.getLogger(AsyncTask.class);
    @Resource
    UserTokenDao userTokenDao;
    @Value("${JWTTime}")
    private Integer ttlMillis;
    @Resource
    private StringRedisTemplate template;


    /**
     * 异步刷新用户 token
     * 适用场景为 鉴权接口 redis数据存在 且非一分钟内刷新
     * @param userId redis内用户id
     */
    @Async("AsyncTaskExecutor")
    public void refreshTask(Long userId) {
        UserTokenEntity tokenEntity = userTokenDao.selectById(userId);
        refreshToken(tokenEntity);
    }


    /**
     * 异步刷新用户 token 并判断是否一分钟内防、刷新
     * 适用场景为 鉴权接口 db数据未过期
     * @param tokenEntity 未过期db数据
     */
    @Async("AsyncTaskExecutor")
    public void refreshTask(UserTokenEntity tokenEntity) {
        refreshToken(tokenEntity);
    }


    /**
     * 进行用户token的redis 与 db 刷新
     * @param tokenEntity
     */
    private void refreshToken(UserTokenEntity tokenEntity) {
        if (ObjectUtil.isNotNull(tokenEntity)) {
            ValueOperations<String, String> ops = template.opsForValue();
            ops.set(RedisKey.USER_TOKEN + tokenEntity.getSecretToken(), tokenEntity.getAccessToken(), ttlMillis, TimeUnit.MINUTES);
            Calendar nowCalendar = Calendar.getInstance();
            nowCalendar.add(Calendar.MINUTE, ttlMillis);
            tokenEntity.setAccessExpire(nowCalendar.getTime());
            userTokenDao.updateById(tokenEntity);
        }
    }


}
