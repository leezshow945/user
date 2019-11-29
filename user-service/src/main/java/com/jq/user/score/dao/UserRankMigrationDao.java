package com.jq.user.score.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jq.user.score.entity.UserRankEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserRankMigrationDao extends BaseMapper<UserRankEntity> {

    void insertRelationTable(@Param("idList")List<Map> idList);

    List<String> getOldIds();

    List<Map> getOldUsers();

    void insertUserRelationTable(@Param("userList")List<Map> userList);
}
