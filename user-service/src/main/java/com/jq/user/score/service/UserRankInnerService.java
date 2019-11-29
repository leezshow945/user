package com.jq.user.score.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.user.customer.entity.UserEntity;
import com.jq.user.score.dto.UserRankDTO;
import com.jq.user.score.entity.UserRankEntity;
import com.jq.user.score.entity.UserRankScoreEntity;

import java.util.List;
import java.util.Map;

public interface UserRankInnerService extends UserRankService {

    List<UserRankEntity> findAll(Long siteId, UserRankEntity userRankEntity, Page page);

    /**
     * 检查最大积分是否为站点等级中最大峰值
     * @param siteId
     * @param maxScore
     * @return
     */
    boolean isMaxScore(Long siteId, Integer maxScore);

    /**
     * 校验修改的最大积分值是否比上级阶级模板积分值大
     * @param siteId
     * @param maxScore
     * @param rankLevel
     * @return
     */
    boolean checkMaxScoreUp(Long siteId, Integer maxScore, Integer rankLevel);

    /**
     * 校验修改的最大积分值是否比下阶级模板积分值小
     * @param siteId
     * @param maxScore
     * @param rankLevel
     * @return
     */
    boolean checkMaxScoreDown(Long siteId, Integer maxScore, Integer rankLevel);

    UserRankEntity findById(Long rankId);

    /**
     * 检查等级名是否存在
     * @param siteId
     * @param rankName
     * @return
     */
    boolean existRankName(Long siteId, String rankName);

    /**
     * 检查等级是否为最大等级（现有等级条目数）
     * @param siteId
     * @param rankLevel
     * @return
     */
    boolean isMaxRankLevel(Long siteId, Integer rankLevel);

    /**
     * 新增一条等级数据与对应的积分类型数据
     * @param userRankEntity
     * @param scoreEntityList
     * @return
     */
    boolean add(UserRankEntity userRankEntity,List<UserRankScoreEntity> scoreEntityList);


    /**
     * 修改等级数据 新增或修改对应的积分类型数据
     * @param userRankEntity
     * @param newList
     * @param oldList
     * @return
     */
    boolean update(UserRankEntity userRankEntity, List<UserRankScoreEntity> newList, List<UserRankScoreEntity> oldList);

    /**
     * Author: lee
     * Description: 获取站点所有等级阶级level集合
     * Date: 2018/6/15
     */
    List getRankLevelList(Long siteId);

    /**
     * Author: Brady
     * Description: 获取rankId与rankLevel对应Map
     * Date: 2018/6/15
     */
    Map<Long,Integer> findAllRankMapBySiteId(Long siteId);


    /**
     * 检查用户接口方法
     * 1.更新用户积分
     * 2.检查用户所达到的最新等级
     * 3.根据晋级奖励与加奖来更新奖励到user信息内
     * @param userEntity
     * @return
     */
    boolean checkUserScore(UserEntity userEntity);


    UserRankDTO getRankInfoById(Long id);

    /**
     * 获取站点等级，跳级加奖奖励详情
     * @return
     */
    List<UserRankDTO> queryRankListBySiteId(Long siteId);

}