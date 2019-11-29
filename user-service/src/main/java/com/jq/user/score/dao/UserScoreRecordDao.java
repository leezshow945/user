package com.jq.user.score.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.user.score.entity.UserScoreRecordEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserScoreRecordDao extends BaseMapper<UserScoreRecordEntity> {

    List<Map<String, Object>> findAll(@Param("map")Map map, Page page);

    List<Map<String, Object>> findAllRecord(@Param("map")Map map, Page page);

}
