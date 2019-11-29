package com.jq.user.score.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.user.customer.entity.UserEntity;

import java.util.List;
import java.util.Map;

public interface UserScoreInnerService extends UserScoreService{
    List<Map<String,Object>> findAll(Long siteId, Map map, Page page);

    /**
     * 手工操作取出存入积分
     * @param map
     * userId 被操作的用户id
     * type 操作标识 0取出 1存入
     * value 操作积分数
     * createBy 操作人
     * siteId 站点id
     * remark 操作备注
     * @return boolean
     */
    boolean manualUpdate(Map map);

    List<Map<String,Object>> findAllRecord(Long siteId, Map map, Page page);

    /**
     * 更新用户积分方法
     * @param userEntity
     * @param scoreType
     * @return
     */
    boolean updateUserScore(UserEntity userEntity, String scoreType);

    /**
     * 判断积分流水记录
     * @param userId
     * @param scoreType
     * @return
     */
    boolean isExistRecord(Long siteId,Long userId,String scoreType);
}

