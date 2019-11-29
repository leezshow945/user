package com.jq.user.customer.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.user.customer.dto.UserRebateDTO;
import com.jq.user.customer.entity.UserRebateEntity;
import com.jq.user.customer.entity.UserWrapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserRebateDao extends BaseMapper<UserRebateEntity> {
    Map<String, Object> findByUserNameAndSiteId(@Param("userName") String userName, @Param("siteId") Long siteId);

    Map<String, Object> getUserRebateByUserNameAndSiteIdApi(@Param("userName") String userName, @Param("siteId") Long siteId);

    List<UserWrapper> getSubUserList(@Param("map") Map paramMap, Page page);

    Map<String,Object> getFinance(Map<String, Object> paramMap);

//    List<Integer> selectRebateLevel(@Param(value="siteId")Long siteId);

    Map<String,Object> getUserInfoAndPathApi(@Param("userName")String userName,@Param("siteId")Long siteId);

//    List<Map<String,Object>> getAllLevelBySiteId(@Param("siteId") Long siteId);

    List<Map<String,Object>> queryUserInfoByUserId(@Param("list") List<Long> ids);

    UserRebateDTO getUserDetail(@Param("id") Long id);

    List<Long> getAllJuniorUserId(@Param("userId") Long userId);

    List<UserRebateDTO> getHighLevelIdAndRank(@Param("list") List<Long> ids);

//    List<Long> getSubIdListById(@Param("id") Long id);

    List<UserRebateDTO> queryUserInfoAndPathByIdList(@Param("list") List<Long> idList,@Param("siteId") Long siteId,@Param("isProxy") Integer isProxy);

    List<UserRebateDTO> queryUserInfoAndPathById(@Param("list") List<Long> idList, @Param("siteId") Long siteId);

    List<Long> getEffectUserId(@Param("list") List<Long> userIds);

    List<Long> getProxyByUserId(@Param("userId") Long userId,@Param("type") Integer type);

    Integer deleteBanRebateUser(@Param("list") List<Long> idList, @Param("siteId") Long siteId);

    UserRebateDTO getUserRebateBy(Map<String, Object> paramMap);

    List<UserRebateEntity> getUserProxyTypeByIdList(@Param("list") List<Long> idList);

    List<UserRebateDTO> getUserByIdList(@Param("list") List<Long> idList, @Param("highLevelName") String highLevelName,@Param("siteId") Long siteId);
}
