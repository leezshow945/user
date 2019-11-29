package com.jq.user.proxy.service;

import com.jq.user.proxy.entity.UserProxyEntity;

import java.util.List;
import java.util.Map;

public interface UserProxyInnerService extends UserProxyService{

    //站点初始化时保存default代理关系
    Integer initDefault(Long defaultId, Long siteId, String siteCode);

    // 新增代理下级用户
    Integer saveUserProxy(Long highUserId, Long userId, String userName);

    // 查询直属下级
    List<UserProxyEntity> getDirSubUserProxy(Long userId, Long siteId);

    // 查询所有下级
    List<UserProxyEntity> getAllSubUserProxy(Long userId, Long siteId);

    //批量查询所有下级会员id
    Map<Long, List<Long>> getAllSubUserMapByIdList(List<Long> userIdList);

    // 查询直属上级
    UserProxyEntity getDirHighUserProxy(Long userId, Long siteId);

    // 查询所有上级
    List<UserProxyEntity> getAllHighUserProxy(Long userId, Long siteId);

    // 查询整条代理线(包含当前用户)
    String getUserProxyLine(Long userId, Long siteId);

    // 查询代理线(所有上级)
    String getPathLine(Long userId, Long siteId);

    // 查询所有下级数量
    Integer countAllSubUserLine(Long userId, Long siteId);

    // 批量查询下级用户数量
    Map<Long, Integer> countAllSubUserLineByList(List<Long> idList);

    // 查询用户集集合的层级
    Map<Long, Integer> getUserLevelMap(List<Long> userIdList, Long siteId);

    //  查询用户层级
    Integer findUserLevel(Long userId, Long siteId);

    // 代理迁移
    boolean transferUserProxy(Long sourceId, Long destId, Long siteId);

    // 源代理不是目标代理上级
    boolean checkSourceLevelIsNoHighDest(Long sourceId, Long destId, Long siteId);

    // 批量迁移
    boolean transferBatch(List<Long> sourceIdList, Long destId, Long siteId);

    // list集合提取id
    List<Long> userIdToList(List<UserProxyEntity> list);

    // list集合提取上级id
    List<Long> highUserIdToList(List<UserProxyEntity> list);

    // 根据userId获取所有上级id集合
    List<Long> getAllHighUserIdList(Long userId, Long siteId);

    // 根据用户id集合获取直属上级代理
    Map<Long, UserProxyEntity> getDirHighUserProxyMapByIdList(List<Long> idList, Long siteId);

    // 获取path路径,过渡使用
    String getPath(Long userId,Long siteId);

    // 根据当前用户id和下级用户名获取下级用户id
    Long getSubUserId(Long userId, String userName);

    // 获取站点所有层级
    List<Integer> getSiteIdAllLevel(Long siteId);

    // 获取所有上级id
    Map<Long,List<Long>> getAllHighUserIdMap(List<Long> idList,Long siteId);

    // 获取所有下级id
    Map<Long,List<Long>> getAllSubUserIdMap(List<Long> idList,Long siteId);

    // 获取站点测试账户的所有层级
    List<Map<String,Integer>> getAllLevelBySiteId(Long siteId,Integer isDemo);

    // 获取相关代理
    Map<Long,List<Long>> getAllProxyId(List<Long> idList, Long siteId, Integer proxyRelation);

    Map<String,  Map<Long,Integer>> getTeamNumberByList(List<Long> highUserIdList, String startDate, String endDate, String siteCode);

    Map<String,Integer> getTeamNewNumberGroupByDate(Long userId, String startDate, String endDate, String siteCode);

    // 根据当前用户id和下级用户名获取下级用户id
    List<Long> getSubUserId(Long userId, String userName, boolean isLike);
}
