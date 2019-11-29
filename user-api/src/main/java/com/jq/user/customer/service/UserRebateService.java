package com.jq.user.customer.service;

import com.jq.user.customer.dto.*;
import com.jq.user.customer.fallbackfactory.UserRebateServiceFallbackFactory;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value = "user",path = "/inner/user/rebate",url = "${feign-url.user:}",fallbackFactory = UserRebateServiceFallbackFactory.class)
public interface UserRebateService {

    /**
     * @Author: levi
     * @Descript: 根据userId获取返点信息
     * @Date: 2018/5/10
     */
    @GetMapping(value = "getRebateByUserId")
    ApiResult<UserRebateDTO> getRebateByUserId(@RequestParam Long userId);
    /**
     * @param userId 用户userId
     * @param type   1-直属上级，2-直属下级，3-所有下级
     * @return List<Long>
     * @Author: levi
     * @Descript: 根据发送类型 获取直属上级id 1-直属上级，2-直属下级，3-所有下级 返回id结果集
     * @Date: 2018/5/9
     */
    @GetMapping(value = "getProxyByUserId")
    ApiResult<List<Long>> getProxyByUserId(@RequestParam Long userId, @RequestParam Integer type);

    /**
     * @Author: levi
     * @Descript: 站点代理会员信息列表查询
     * @return map{total=数据量,userList=数据列表}
     * @Date: 2018/5/18
     */
    @PostMapping(value = "qryUserByPageApi")
    ApiResult<PageInfo<UserRebateDTO>> qryUserByPageApi(@RequestBody QueryParamDTO dto);

    /**
     * @Author: levi
     * @Descript: 站点新增代理会员
     * @return map{userId=用户id,userName=用户名}
     * @Date: 2018/5/18
     */
    @PostMapping(value = "addUserApi")
    ApiResult<UserDTO> addUserApi(@RequestBody AddUserDTO dto);

    /**
     * Author: Brady
     * Description: 站点新增代理会员
     * Date: 2018/6/26
     */
    @PostMapping(value = "addTestUserApi")
    ApiResult addUserApi(@RequestBody TestUserDTO dto,@RequestParam Long siteId,
                         @RequestParam String updateUserName,@RequestParam String ip);

    /**
     * @Author: levi
     * @Descript: 更新会员基本信息与返点信息
     * @Date: 2018/5/2
     */
    @PutMapping(value = "updateBaseInfoApi")
    ApiResult<Boolean> updateBaseInfoApi(@RequestBody UserRebateUpdateDTO dto);

    /**
     * Author: Brady
     * Description: 根据userName和siteId更新用户基本资料
     * Date: 2018/6/25
     */
    @PutMapping(value = "updateTestBaseInfoApi")
    ApiResult<Boolean> updateBaseInfoApi(@RequestBody UpdateTestUserDTO dto, @RequestParam Long siteId);

    /**
     * @Author: levi
     * @Descript: 查看下级会员列表
     * @return ApiResult<List<UserRebateDTO>>
     * @Date: 2018/5/22
     */
    @PostMapping(value = "getSubUserListApi")
    ApiResult<PageInfo<UserRebateDTO>> getSubUserListApi(@RequestBody QueryParamDTO dto);

    /**
     * @Author: levi
     * @Descript: 动态获取代理会员模块page参数
     * @Date: 2018/5/22
     */
    @GetMapping(value = "getLevelAndRankApi")
    ApiResult<UserDTO> getLevelAndRankApi(@RequestParam Long siteId);

    /**
     * @Author: levi
     * @Descript: 获取用户path信息
     * @Date: 2018/6/5
     */
    @GetMapping(value = "getPathByApi")
    ApiResult<String> getPathByApi(@RequestParam Long userId);

    /**
     * @Author: levi
     * @Descript: 获取用户层级路径
     * @Date: 2018/6/14
     */
    @GetMapping(value = "getUserLevelPathApi")
    ApiResult<List<UserDTO>> getUserLevelPathApi(@RequestParam(required = false) String userName, @RequestParam Long siteId);

    /**
     * @Author: levi
     * @Descript: 获取用户返点信息
     * @return   map={key1:userRebateMap,key2:list}
     * @Date: 2018/6/14
     */
    @GetMapping(value = "getRebateByUserNameAndSiteIdApi")
    ApiResult<Map<String, Object>> getRebateByUserNameAndSiteIdApi(@RequestParam String userName,@RequestParam Long siteId);

    /**
     * Author: Brady
     * Description:根据用户名获得返点信息
     * Date: 2018/6/29
     */
    @GetMapping(value = "getUserRebateByUserNameAndSiteIdApi")
    ApiResult<Map<String, Object>> getUserRebateByUserNameAndSiteIdApi(@RequestParam String userName,@RequestParam Long siteId);

    /**
     * Author: Brady
     * Description: 根据用户名和站点获得用户(userId,status,path,isProxy,highAccount)
     * Date: 2018/6/19
     */
    @GetMapping(value = "getUserInfoAndPathApi")
    ApiResult<UserRebateDTO> getUserInfoAndPathApi(@RequestParam String userName,@RequestParam Long siteId);

    /**
     * @Author: levi
     * @Descript: 根据站点id查询所有层级
     * @Date: 2018/6/19
     */
    @GetMapping(value = "getAllLevelBySiteIdApi")
    ApiResult<List<Map<String,Object>>> getAllLevelBySiteIdApi(@RequestParam Long siteId);

    /**
     * @Author: levi
     * @Descript: 通过用户id集合，站点id，请求获取用户名和用户是不是代理
     * @Date: 2018/6/20
     */
    @GetMapping(value = "getUserType")
    ApiResult<List<UserDTO>> getUserType(@RequestParam List<Long> ids, @RequestParam Long siteId);

    /**
     * Author: Brady
     * Description: 根据用户名和站点获得用户(userId,status,path,highAccount,level,isProxy)
     * Date: 2018/6/20
     */
    @GetMapping(value = "queryUserInfoAndPathByIdApi")
    ApiResult<List<UserRebateDTO>> queryUserInfoAndPathByIdApi(@RequestParam List<Long> idList,@RequestParam Long siteId);

    /**
     * @Author: levi
     * @Descript: 根据用户名和站点获得用户信息(userId,status,path,highAccount,level,isProxy,返点信息)
     * @param idList 用户id集合
     * @param siteId 站点id
     * @param isProxy 是否代理 :  1-是  0-否
     * @Date: 2018/8/24
     */
    @GetMapping(value = "queryUserInfoAndPathByIdAndIsProxyApi")
    ApiResult<List<UserRebateDTO>> queryUserInfoAndPathByIdApi(@RequestParam List<Long> idList,
                                                               @RequestParam Long siteId, @RequestParam(value = "isProxy",required = false) Integer isProxy);

    /**
     * Author: Brady
     * Description: 修改初始化用户数据
     * Date: 2018/6/25
     */
    @GetMapping(value = "initUpdateApi")
    ApiResult<Map<String,Object>> initUpdateApi(@RequestParam Long userId,@RequestParam Long siteId);

    /**
     * @Author: levi
     * @Descript: 获取代理会员资金信息
     * @Date: 2018/6/25
     */
    @GetMapping(value = "getUserFundByUserIdAndSiteIdApi")
    ApiResult<UserDTO> getUserFundByUserIdAndSiteIdApi(@RequestParam Long userId,@RequestParam Long siteId);

    /**
     * @Author: levi
     * @Descript: 会员批量迁移
     * @param destId 目标代理id
     * @param destName 目标代理账号
     * @param userIds 源代理id集合
     * @Date: 2018/6/29
     */
    @PutMapping(value = "transferBatchApi")
    ApiResult<Boolean> transferBatchApi(@RequestParam Long destId,@RequestParam String destName,
                                        @RequestParam String userIds,@RequestParam String managerUserName);
    /**
     * @Author: levi
     * @Descript: 代理迁移
     * @param destId 目标代理id
     * @param sourceId 源代理id
     * @param destName 目标代理账号
     * @param sourceName 源代理账号
     * @param isTransfer 是否包含源代理
     * @param managerUserName 源代理账号
     * @Date: 2018/6/30
     */
    @PostMapping(value = "transferApi")
    ApiResult<Boolean> transferApi(@RequestParam Long destId,@RequestParam Long sourceId,
                                   @RequestParam String destName, @RequestParam String sourceName
            ,@RequestParam Integer isTransfer,@RequestParam  String managerUserName);
    /**
     * @Author: levi
     * @Descript: 获取用户相关资料
     * @Date: 2018/6/29
     */
    @GetMapping(value = "getRelateApi")
    ApiResult<Map<String,Object>> getRelateApi(@RequestParam Long userId);

    /**
     * @Author: levi
     * @Descript: 批量迁移前修改会员返点
     * @Date: 2018/7/21
     */
    @PostMapping(value = "updateRebate")
    ApiResult updateRebate(@RequestParam Long destId,@RequestParam List<String> userNameList,
                           @RequestParam Long siteId);
    /**
     * @Author: levi
     * @Descript: 获取用户详细信息
     * @param id 用户id
     * @Date: 2018/7/24
     */
    @GetMapping(value = "getUserDetailApi")
    ApiResult<UserRebateDTO> getUserDetailApi(@RequestParam Long id);

    /**
     * @Author: levi
     * @Descript: 获取代理关系线
     * @param userId 用户id
     * @Date: 2018/7/26
     */
    @GetMapping(value = "getProxyLine")
    ApiResult<String> getProxyLine(@RequestParam Long userId);

    /**
     * @Author: levi
     * @Descript: 获取代理实际有效会员（包括代理）
     * 过滤真实姓名为空代理会员，按真实姓名分组（过滤重复）
     * @Date: 2018/8/31
     */
    @GetMapping(value = "getEffectUserId")
    ApiResult<List<Long>> getEffectUserId(@RequestParam List<Long> userIds,@RequestParam  Long siteId);

    /**
     * @Author: levi
     * @Descript: 根据站点Code查询厅主以及厅主的所有直属下级id
     * @Date: 2018/8/9
     */
    @GetMapping(value = "getDefaultAndDirectUserIdBySiteCode")
    ApiResult<List<Long>> getDefaultAndDirectUserIdBySiteCode(@RequestParam String siteCode);

    /**
     * @Author: levi
     * @Descript: 根据用户id集合查询用户上级和等级
     * @param ids 用户id集合
     * @return List<UserRebateDTO> highLevelId:上级id,rankLevel:用户等级,rankName:等级名称,isDemo:账号类型(0-正式账号,1-试玩账号,2-测试账号)
     * @Date: 2018/8/10
     */
    @GetMapping(value = "getHighLevelIdAndRank")
    ApiResult<List<UserRebateDTO>> getHighLevelIdAndRank(@RequestParam List<Long> ids);

    /**
     * 根据用户列表查询所有的上级代理线
     * @param idList 用户id列表
     * @return ApiResult<Map<Long,List<Long>>>
     */
    @GetMapping(value = "getHighLevelProxyByIdList")
    ApiResult<Map<Long,List<Long>>> getHighLevelProxyByIdList(@RequestParam List<Long> idList);

    /**
     * 根据用户列表，查询这用户是否有下级，有：true，没有：false
     * @param idList 用户id列表
     * @return
     */
    @GetMapping(value = "getUserIsSubLevel")
    ApiResult<Map<Long,Boolean>> getUserIsSubLevel(@RequestParam List<Long> idList);

    /**
     * @Author: levi
     * @Descript: 根据用户id集合查询下级所有下级代理会员
     * @return Map<Long,List<Long>> key:用户id集合,value:所有下级用户id集合(包含用户id)
     * @Date: 2018/8/16
     */
    @GetMapping(value = "getSubIdListByIdListApi")
    ApiResult<Map<Long,List<Long>>> getSubIdListByIdListApi(@RequestParam List<Long> idList);

    /**
     * @Author: levi
     * @Descript: 获取禁止返点代理列表
     * @Date: 2018/9/21
     */
    @PostMapping(value = "getBanRebateUserListApi")
    ApiResult<PageInfo<UserRebateDTO>> getBanRebateUserListApi(@RequestBody QueryParamDTO dto);

    /**
     * @Author: levi
     * @Descript: 设置禁止返点代理
     * @Date: 2018/9/21
     */
    @PutMapping(value = "setBanRebateUserApi")
    ApiResult setBanRebateUserApi(@RequestParam String userName, @RequestParam Long siteId);

    /**
     * @Author: levi
     * @Descript: 批量删除禁止返点代理
     * @Date: 2018/9/21
     */
    @DeleteMapping(value = "deleteBanRebateUserApi")
    ApiResult deleteBanRebateUserApi(@RequestParam List<Long> idList,@RequestParam Long siteId);

    /**
     * @Author: levi
     * @Descript: 校验上级是否存在并获取返点
     * @param userName 用户名
     * @param siteId 站点id
     * @param isDemo 0-正式账户,1-试玩账户,2-测试账户
     * @Date: 2018/10/18
     */
    @GetMapping(value = "getUserRebateBy")
    ApiResult<UserRebateDTO> getUserRebateBy(@RequestParam String userName,
                                             @RequestParam Long siteId, @RequestParam Integer isDemo);

    /**
     * 更新用户账号类型
     *
     * @param userId
     * @return
     */
    @GetMapping(value = "updateUserType")
    ApiResult<?> updateUserType(@RequestParam Long userId,@RequestParam String siteCode);
    /**
     * 获取用户返点详情
     * @param userId 用户id
     * @return 用户返点详情
     */
    @GetMapping(value = "findByUserIdApi")
    ApiResult<UserRebateDTO> findByUserIdApi(@RequestParam Long userId);

}
