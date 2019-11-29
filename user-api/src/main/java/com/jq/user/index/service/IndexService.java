package com.jq.user.index.service;

import com.jq.user.index.dto.IndexDTO;
import com.jq.user.index.fallbackfactory.IndexServiceFallbackFactory;
import com.liying.common.service.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "user",path = "/inner/user/index",url = "${feign-url.user:}",fallbackFactory = IndexServiceFallbackFactory.class)
public interface IndexService {
    /**
     * @param startDate 开始时间 xxxx-xx-xx
     * @param endDate   结束时间 xxxx-xx-xx
     * @param siteSign  siteId或者siteCode
     * @return List<Long> userId集合
     * @Author: levi
     * @Descript: 查询单位时间范围内的新增会员
     * @Date: 2018/12/6
     */
    @GetMapping(value = "findNewUserIdList")
    ApiResult<List<Long>> findNewUserIdList(@RequestParam String startDate, @RequestParam String endDate, @RequestParam Object siteSign);


    /**
     * 查询新增会员id及创建日期
     * @param startDate 开始时间 xxxx-xx-xx
     * @param endDate   结束时间 xxxx-xx-xx
     * @param siteSign  siteId或者siteCode
     * @return 新增会员id及创建日期 
     */
    @GetMapping(value = "findNewUserMapApi")
    ApiResult<Map<Long, String>> findNewUserMapApi(@RequestParam String startDate, @RequestParam String endDate, @RequestParam Object siteSign);

    /**
     * @param startDate 开始时间 xxxx-xx-xx (若开始时间为当天,则以每小时为间隔,查0~24h注册量)
     * @param endDate   结束时间   xxxx-xx-xx
     * @param siteId    站点id
     * @param isProxy   是否代理  1:代理,0:会员,空:所有
     * @Author: levi
     * @Descript: 按日期分组统计新增会员数量
     * @Date: 2018/12/5
     */
    @GetMapping(value = "countNewUserByDateGroup")
    ApiResult<Map<String, Integer>> countNewUserByDateGroup(@RequestParam String startDate, @RequestParam String endDate,
                                                            @RequestParam Long siteId, @RequestParam(value = "isProxy",required = false) Integer isProxy);


    /**
     * @param startDate 开始日期 xxxx-xx-xx
     * @param endDate   结束日期 xxxx-xx-xx
     * @param siteId    站点id
     * @param isProxy   是否代理 1:代理,0:会员,空:所有
     * @return 注册用户数量
     * @Author: levi
     * @Descript: 按日期统计新增会员数量
     * @Date: 2018/12/7
     */
    @GetMapping(value = "countNewUserByDate")
    ApiResult<Integer> countNewUserByDate(@RequestParam String startDate, @RequestParam String endDate, @RequestParam Long siteId,
                                          @RequestParam(value = "isProxy",required = false) Integer isProxy);

    /**
     * @Author: levi
     * @param dto
     * @Descript: 用户分布
     * @Date: 2018/12/11
     */
    @PostMapping(value = "getUserDisc")
    ApiResult<Map<String, Integer>> getUserDisc(@RequestBody IndexDTO dto);
}
