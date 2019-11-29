package com.jq.user.oldbonus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.user.oldbonus.entity.UserBonusMainEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface UserBonusMainDao extends BaseMapper<UserBonusMainEntity> {
	/**
	 * 获取对应层级数据
	 * @param
	 * @parem type
	 * @author XC
	 */
	List<Map<String,Object>> queryBonusSetByLevel(Map map);


	/**
	 * 根据设置类型和签约状态查询设置主表
	 *
	 * @param settingType
	 * @param signStatus
	 * @return
	 */
	List<UserBonusMainEntity> queryBonusMain(String settingType, String signStatus, String siteId);

	/**
	 * 根据条件查询
	 *
	 * @param main
	 * @return
	 * @author zcxu
	 */
	List<UserBonusMainEntity> queryBonusMainByMain(UserBonusMainEntity main);

	/**
	 * 根据userId条件查询
	 *
	 * @param userId
	 * @return
	 * @author xc
	 */
	List<UserBonusMainEntity> queryBonusMainByUserId(String userId, boolean flag);

	/**
	 * 根据条件查询
	 *
	 * @param userId
	 * @param signStatus
	 * @param settingType
	 * @return
	 * @author xc
	 */
	List<UserBonusMainEntity> queryBonusMainByUserId2(String userId, String signStatus, String settingType);

	/**
	 * 分页查询 契约分红设置列表
	 * @param siteId 站点id
	 * @param gameCategoryId 游戏类型
	 * @param playType lhc玩法
	 * @param toUserName 接收用户名
	 * @param userName 发起用户名
	 * @param settingType 设置类型
	 * @param pagination
	 * @return
	 */
	List<Map<String,Object>> queryContractBonusInfo(@Param("map") Map map, Page page);

	/**
	 * 分页查询 契约分红设置列表
	 * @param siteId 站点id
	 * @param gameCategoryId 游戏类型
	 * @param playType lhc玩法
	 * @param toUserName 接收用户名
	 * @param userName 发起用户名
	 * @param settingType 设置类型
	 * @param pagination
	 * @return
	 */
	List<Map<String,Object>> queryContractBonus(@Param("map") Map map, Page page);

	/**
     * 根据主表id 查询契约分红设置详情
     * @param mainId
     * @param siteId
     * @return
     */
    List<Map<String,Object>> queryContractMainSonById(@Param("mainId") Long mainId,@Param("siteId") Long siteId);

	/**
	 * 条件查询 发起用户分红设置主表
	 * @param entity
	 * @return
	 */
	List<UserBonusMainEntity> queryContractRecord(UserBonusMainEntity entity);

    int updateAllColumnById(UserBonusMainEntity entity);
}
