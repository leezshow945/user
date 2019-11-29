package com.jq.user.proxy.service;


import com.jq.user.proxy.dto.UserProxyDTO;
import com.jq.user.proxy.fallbackfactory.UserProxyServiceFallbackFactory;
import com.liying.common.service.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "user",path = "/inner/user/proxy",url = "${feign-url.user:}",fallbackFactory = UserProxyServiceFallbackFactory.class)
public interface UserProxyService {

    /**
     * @param idList    userId集合
     * @param siteId    站点id
     * @param isContain 是否包含userId true:包含,false:不包含
     * @Author: levi
     * @Descript: 根据userId集合获取所有上级id
     * @Date: 2018/11/22
     */
    @GetMapping(value = "getAllHighUserIdListApi")
    ApiResult<List<Long>> getAllHighUserIdListApi(@RequestParam List<Long> idList, @RequestParam Long siteId, @RequestParam Boolean isContain);

    /**
     * @Author: levi
     * @Descript: 获取代理所有下级的直属上级( for video report)
     * @param userId 用户id
     * @param siteId 站点id
     * @return 代理关系实体 (代理所有下级的直属上级)
     * @Date: 2018/12/11
     */
    @GetMapping(value = "getAllSubUserDirHighUserApi")
    ApiResult<List<UserProxyDTO>> getAllSubUserDirHighUserApi(@RequestParam Long userId, @RequestParam Long siteId);

    /**
     * @Author: levi
     * @param siteId 站点id
     * @param isDemo 0-正式账号, 2-测试账号,null-所有
     * @Descript: 根据siteId和账号类型 获取站点所有层级
     * @Date: 2018/12/18
     */
    @GetMapping(value = "getAllLevelBySiteIdApi")
    ApiResult<List<Map<String,Integer>>> getAllLevelBySiteIdApi(@RequestParam Long siteId,@RequestParam Integer isDemo);

    /**
     * @Author: levi
     * @Descript: 批量查询用户直属上级,所有上级,直属下级,所有下级
     * @param dto proxyRelation   1-直属上级，2-直属下级，3-所有下级,4-所有上级
     *            idList  userId集合
     *            siteId 站点id
     * @return map<Long,List<Long>>
     * @Date: 2018/12/19
     */
    @PostMapping(value = "getAllProxyIdApi")
    ApiResult<Map<Long,List<Long>>> getAllProxyIdApi(@RequestBody UserProxyDTO dto);

    /**
     * 批量查询用户团队人数、新增人数、7天未登录人数
     * @param highUserIdList 代理用户id
     * @param startDate 开启日期 xxxx-xx-xx
     * @param endDate 结束日期 xxxx-xx-xx
     * @param siteCode 站点code
     * @return 用户团队人数、新增人数、7天未登录人数
     */
    @GetMapping(value = "getTeamNumberByListApi")
    ApiResult<Map<String, Map<Long,Integer>>> getTeamNumberByListApi(@RequestParam List<Long> highUserIdList,
                                                                     @RequestParam String startDate, @RequestParam String endDate,
                                                                     @RequestParam String siteCode);

    /**
     * 查询单个用户新增团队人数
     * @param userId 代理用户id
     * @param startDate 开启日期 xxxx-xx-xx
     * @param endDate 结束日期 xxxx-xx-xx
     * @param siteCode 站点code
     * @return 单个用户新增团队人数
     */
    @GetMapping(value = "getTeamNewNumberGroupByDateApi")
    ApiResult<Map<String,Integer>> getTeamNewNumberGroupByDateApi(@RequestParam Long userId,@RequestParam String startDate,
                                                                  @RequestParam String endDate,@RequestParam String siteCode);
}
