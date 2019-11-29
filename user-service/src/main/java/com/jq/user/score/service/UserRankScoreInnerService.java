package com.jq.user.score.service;

import com.jq.user.score.entity.UserRankScoreEntity;

import java.util.List;

public interface UserRankScoreInnerService {

    List<UserRankScoreEntity> findByRankId(Long rankId);

    /**
     * 根据等级id 查询该积分类型积分配置获取值
     * @param rankId
     * @param scoreType
     * @return
     */
    Integer checkScoreType(Long rankId,String scoreType);
}
