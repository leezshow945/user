package com.jq.user.oldbonus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jq.user.oldbonus.entity.UserBonusSonEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface UserBonusSonDao extends BaseMapper<UserBonusSonEntity> {

	/**
	 *条件查询子单
	 *
	 * @param map
	 * int settingType,
	 * String amountOrRebate,
	 * String playType,
	 * String gameCategoryId,
	 * Integer rebateLevel,
	 * String mainId,
	 * String validMember,
	 * String id,
	 * String amount
	 * Integer bonusMode,
	 * String bonusAmount,
	 * String limitAmount,
	 * String bonusPer,
	 * @return
	 */
	List<Map<String, Object>> queryBonusSonByMainId(Map map);
	
	/**
	 * 根据mainId查询所有细单
	 * mainId为空时，查询所有细单
	 *
	 * @param mainId
	 * @return
	 * @anthor zcxu
	 */
	List<UserBonusSonEntity> querySonByMainId(@Param("mainId") Long mainId, @Param("isDel") Integer isDel);
	
	/**
	 *  获取分红规则细单
	 *  可根据细单类型根据返点或金额排序
	 * @Title: queryUserBonusSon 
	 * @Description: 获取所有分红（参数放空）规则细单  。根据规则id获取细单。获取返点大于某个规则细单（规则id放空）。获取指定某个规则小于某个返点的细单
	 * @param mainId 规则id
	 * @param rebate 返点
	 * @param amount 金额
	 * @param sort 排序
	 * @return 
	 * @return UserBonusSonModel    返回类型 
	 * @date 2017年10月11日 下午5:33:47 
	 * @author jfChen
	 */
	List<UserBonusSonEntity> queryUserBonusSon(@Param("mainId") Long mainId,@Param("rebate") String rebate,@Param("amount") String amount,@Param("sort") String sort);



}
