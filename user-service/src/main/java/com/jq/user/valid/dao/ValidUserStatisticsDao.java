package com.jq.user.valid.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jq.user.valid.entity.ValidUserStatisticsEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ValidUserStatisticsDao extends BaseMapper<ValidUserStatisticsEntity> {
    List<Map<String, Integer>> getValidUserInfo(Long siteId);

    Integer insertBatch(@Param("list") List<ValidUserStatisticsEntity> entityList);

    Integer updateBatch(@Param("list") List<ValidUserStatisticsEntity> entityList);

    List<Long> getValidUserIdList(@Param("list") List<Long> idList);
}
