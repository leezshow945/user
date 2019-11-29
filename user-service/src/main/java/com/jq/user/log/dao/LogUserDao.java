package com.jq.user.log.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jq.user.log.entity.LogUserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface LogUserDao extends BaseMapper<LogUserEntity> {

    void insertList(List<LogUserEntity> list);

    Integer deleteBatch(@Param("ids") ArrayList<Long> ids);

    Integer getHourlyOnLines(@Param("startTime") String startTime,@Param("endTime") String endTime, @Param("siteId") Long siteId);

    Integer getLoginCountByDay(@Param("id") Long id);

    List<Map<String,String>> getUserDiscArea(Map<String, Object> paramMap);

    List<Map<String,String>> getEffectiveUserDiscArea(Map<String, Object> paramMap);

    List<Map<String,String>> getRechargeUserDiscArea(Map<String, Object> paramMap);

    Integer getPageView(Map<String, Object> paramMap);
}
