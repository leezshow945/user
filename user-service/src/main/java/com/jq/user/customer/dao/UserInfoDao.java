package com.jq.user.customer.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jq.user.customer.dto.UserInfoDTO;
import com.jq.user.customer.entity.UserInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoDao extends BaseMapper<UserInfoEntity> {
    Integer updateSysUserInfo(UserInfoEntity userInfo);

    /**
     * 批量查询用户基本信息
     *
     * @param userNameList
     * @param siteCode
     * @return
     */
    List<UserInfoDTO> queryUserInfoByUserNames(@Param("userNameList") List<String> userNameList, @Param("siteCode") String siteCode);

    List<UserInfoDTO> getUserInfoByIdList(@Param("list") List<Long> idList, @Param("siteId") Long siteId);
}
