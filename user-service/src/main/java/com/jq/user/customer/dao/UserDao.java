package com.jq.user.customer.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.user.customer.dto.RegisterUserDTO;
import com.jq.user.customer.dto.UserDTO;
import com.jq.user.customer.dto.UserRebateDTO;
import com.jq.user.customer.entity.UserEntity;
import com.jq.user.customer.entity.UserWrapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author Created by ZhangCong on 2018/4/13.
 */
public interface UserDao extends BaseMapper<UserEntity> {

    UserEntity findByUserName(@Param("userName") String userName, @Param("siteId") Long siteId);

    String findUserNameById(Long id);

    UserEntity confirmExistUserName(@Param("siteId") Long siteId, @Param("userName") String userName);

    UserEntity findById(Long id);

    List<UserWrapper> queryUserByModel(Page page, @Param("map") Map<String, Object> map);

    List<Map<String, Object>> queryDemoUserPage(@Param("siteId") Long siteId, Page page, @Param("loginBeginTime") String loginBeginTime, @Param("loginEndTime") String loginEndTime, @Param("orderBeginTime") String orderBeginTime, @Param("orderEndTime") String orderEndTime, @Param("gameCode") String gameCode);

    List<Map<String, Object>> queryTestUserPage(Page page,@Param("map") Map map);

    Integer deleteByUserId(@Param("userId") Long userId);

    Integer updateGameCategoryAndTime(@Param("userId") Long userId, @Param("category") String category, @Param("date") Date date);

    List<UserWrapper> getUserLevelBySiteCode(String code);

    List<Long> queryUserIdByUserNameApi(String userName);

    List<UserDTO> selectUserType(@Param("list") List<Long> ids, @Param("siteId") Long siteId);

    List<RegisterUserDTO> queryRegisterUserApi(Page page, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("siteId") Long siteId);

    List<Map<String, Object>> queryUserOnlineApi(Page page, @Param("userName") String userName, @Param("siteId") Long siteId,@Param("idList") List<Long> idList,@Param("type") String type);

    Integer getOnlineCountApi(@Param("siteId") Long siteId, @Param("idList") List<Long> idList);

    void updateBatchUserStatus(@Param("idList") List<Long> userIds, @Param("status") Integer status);

    List<UserDTO> checkOutUserExcel(Map<String, Object> paramMap);

    void batchDeleteUserApi(@Param("siteId") Long siteId, @Param("idStr") String idStr);

    List<Map<String,String>> getUserNameListByIdList(@Param("list") List<String> idList);

    List<UserDTO> queryUserLikeUserNameApi(@Param("userName")String userName,@Param("siteCode") String siteCode);

    Integer getCountOfRegisterByTimeAndRegSourceApi(@Param("regSource")Integer regSource,@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("siteCode") String siteCode,@Param("idList") List idList);

    List<UserDTO> queryUserByListIdApi(@Param("idList") List<Long> idList);

    List<UserDTO> queryUserDTOByListIdApi(@Param("listId") List<Long> listId,@Param("siteId") Long siteId,@Param("isDemo") Integer isDemo);

    List<Long> queryFilterIds(@Param("siteId")Long siteId,@Param("idList")List<Long> idList);

    List<UserRebateDTO> getBanRebateUserList(@Param("map") Map<String, Object> map, Page page);

    List<UserDTO> queryUserByIpApi(@Param("ip") String ip,@Param("siteCode") String siteCode);

    List<Map<String,Object>> countNewUserByDateGroup(Map<String, Object> paramMap);

    List<Map<String,String>> countNewUserByHourGroup(Map<String, Object> paramMap);

    Integer countNewUserByDate(Map<String, Object> paramMap);

    List<Long> getIdListBySiteCodeAndIsDemoApi(@Param("siteCode") String siteCode, @Param("isDemo") int isDemo);

    List<Map<String,Object>> queryUserInfo(Long siteId);

    Map<String,Object> getUserInfo(Long id);

    List<Map<String,Object>> queryUserInfoByIdList(@Param("idList") List<Long> idList);

    List<Map<String,Object>> queryCashInfo(@Param("idList") List<Long> idList,@Param("siteCode") String siteCode);
}
