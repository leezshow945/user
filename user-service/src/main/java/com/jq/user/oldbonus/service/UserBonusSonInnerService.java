package com.jq.user.oldbonus.service;

import com.jq.user.bonus.service.UserBonusSonService;

import java.util.List;
import java.util.Map;

/**
 * 〈分红设置〉
 *
 * @author Json
 * @create 2018/6/19
 */
public interface UserBonusSonInnerService  extends UserBonusSonService{

    /**
     *条件查询子单
     *
     * @param map1
     * int settingType,
     * String amountOrRebate,
     * String playType,
     * String gameCategoryId,
     * Integer rebateLevel,
     * String mainId,
     * String validMember,
     * String id,
     * String amount
     * @param map2
     * Integer bonusMode,
     * String bonusAmount,
     * String limitAmount,
     * String bonusPer,
     * @return map id mainId
     */
    List<Map<String,Object>> queryBonusSonByMainId(Map map1, Map map2);
}
