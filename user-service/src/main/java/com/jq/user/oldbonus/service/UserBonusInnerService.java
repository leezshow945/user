package com.jq.user.oldbonus.service;


import com.jq.user.bonus.service.UserBonusService;

/**
 * 〈奖金记录〉
 *
 * @author Json
 * @create 2018/6/19
 */
public interface UserBonusInnerService extends UserBonusService {

    /**
     * 根据用户id 类型查询奖金记录
     * @param userId 用户id
     * @param dataType 奖金类型
     * @return
     */
    Long getTotalBonus(Long userId,Integer dataType);

    /**
     * @Author: levi
     * @Descript: 根据userId获取用户累计赚水
     * @Date: 2018/8/14
     */
    Long getTotalMakeWater(Long userId);
}
