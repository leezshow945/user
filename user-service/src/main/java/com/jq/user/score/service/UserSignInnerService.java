package com.jq.user.score.service;

import com.jq.user.score.entity.UserSignEntity;

import java.util.List;

public interface UserSignInnerService extends UserSignService {
    /**
     * 会员创建签到
     * @param userId
     * @return
     */
    boolean createSign(Long siteId,Long userId);

    /**
     * 检查今日是否已签到
     * @param siteId
     * @param userId
     * @return
     */
    boolean existSign(Long siteId,Long userId);

    /**
     * 获取用户签到记录
     */
    List<UserSignEntity> getSignRecord(Long siteId,Long userId);
}
