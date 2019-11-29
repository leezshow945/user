package com.jq.user.score.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jq.user.score.dto.UserRankDTO;
import com.jq.user.score.entity.UserRankEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserRankDao extends BaseMapper<UserRankEntity> {

    UserRankEntity findById(Long id);

    List<Integer> selectRankLevel(@Param(value="siteId")Long siteId);

    Long findDefaultRank(Long siteId);

    List<UserRankDTO> getRankAndRebateLevel(@Param(value="siteCode")String siteCode,@Param(value="idList")List<UserRankDTO> idList);
}
