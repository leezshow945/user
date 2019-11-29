package com.jq.user.score.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jq.user.score.entity.UserRankScoreEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRankScoreDao extends BaseMapper<UserRankScoreEntity> {


    Integer addList(@Param("userRankScores")List<UserRankScoreEntity> userRankScores);

    Integer updateList(@Param("userRankScores")List<UserRankScoreEntity> scoreEntityList);
}
