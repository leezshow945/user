package com.jq.user.proxy.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jq.user.proxy.entity.UserProxyEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserProxyDao extends BaseMapper<UserProxyEntity> {

    Integer deleteUserProxy(@Param("sourceId") Long sourceId, @Param("siteId") Long siteId);

    Integer insertBatch(@Param("list") List<UserProxyEntity> userProxyEntity);

    List<UserProxyEntity> selectNewProxyToInsert(@Param("sourceId") Long sourceId, @Param("destId") Long destId, @Param("siteId") Long siteId);

    boolean deleteForTransferBatch(@Param("list") List<Long> sourceIdList, @Param("siteId") Long siteId);

    List<UserProxyEntity> selectForTransferBatch(@Param("list") List<Long> sourceIdList, @Param("destId") Long destId, @Param("siteId") Long siteId);

    List<Map<String,Integer>> getAllLevelBySiteId(@Param("siteId") Long siteId, @Param("isDemo") Integer isDemo);

    List<Map<String, Object>> getTeamNumberByList(@Param("userIdList") List<Long> userIdList, @Param("siteId") Long siteId);

    List<Map<String,Object>> queryRegisterUserByProxyLineApi(IPage page, @Param("id") Long id, @Param("startTime") String startTime, @Param("endTime") String endTime);
}
