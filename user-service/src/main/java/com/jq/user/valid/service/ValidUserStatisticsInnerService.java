package com.jq.user.valid.service;

import com.jq.user.customer.entity.UserEntity;

import java.util.List;
import java.util.Map;

public interface ValidUserStatisticsInnerService extends ValidUserStatisticsService {

    // 定时统计所有站点有效会员
    void statisticsAllSite();

    //定时有效会员参数入库
    void validUserDateSaveAndStatistics();

    // 统计有效会员
    boolean updateValidUserDateBySiteId(Long siteId);

    // 根据站点统计有效会员
    boolean statisticsBySiteId(Long siteId);

    // 组装用户信息
    Map<Long, Map<String, Integer>> assembleValidUserInfo(Long siteId);

    // 站点保存设置时初始化该站点所有有效会员数据
    boolean initValidUserData(Long siteId,String siteCode);

    // 用户注册是初始化该用户游戏会员数据
    boolean initValidUserInfo(UserEntity userEntity);

    // 统计有效会员数
    Integer countValidUserBySiteId(Long siteId);

    // 获取有效会员id
    List<Long> getValidUserIdList(List<Long> idList,Long siteId);
}
