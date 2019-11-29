package com.jq.user.log.service;

import com.jq.user.log.dto.HourlyOnLinesDTO;
import com.jq.user.log.dto.LogQryParamDTO;
import com.jq.user.log.dto.LogUserDTO;
import com.jq.user.log.fallbackfactory.LogUserServiceFallbackFactory;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "user",path = "/inner/user/log",url = "${feign-url.user:}",fallbackFactory = LogUserServiceFallbackFactory.class)
public interface LogUserService {

    /**
     * @Author: levi
     * @Descript: 新增用户日志
     * @Date: 2018/5/22
     */
    @PostMapping(value = "addUserLogApi")
    ApiResult addUserLogApi(@RequestBody LogUserDTO dto);

    /**
     * @Author: levi
     * @Descript: 查询用户登录日志
     * @Date: 2018/5/22
     */
    @PostMapping(value = "qryUserLoginLogApi")
    ApiResult<PageInfo<LogUserDTO>> qryUserLoginLogApi(@RequestBody LogQryParamDTO dto);

    /**
     * @Author: levi
     * @Descript: 查询用户操作日志
     * @Date: 2018/5/22
     */
    @PostMapping(value = "qryUserLogByRecordApi")
    ApiResult<PageInfo<LogUserDTO>> qryUserLogByRecordApi(@RequestBody LogQryParamDTO dto);

    /**
     * @Author: levi
     * @Descript: 批量删除日志
     * @Date: 2018/5/22
     */
    @DeleteMapping(value = "deleteBatchApi")
    ApiResult deleteBatchApi(@RequestParam String ids);

    /**
     * @Author: levi
     * @Descript: 删除日志
     * @Date: 2018/5/22
     */
    @DeleteMapping(value = "deleteApi")
    ApiResult deleteApi(@RequestParam Long id);

    /**
     * @Author: levi
     * @Descript: 按日期批量删除日志
     * @Date: 2018/5/31
     */
    @DeleteMapping(value = "deleteByDateApi")
    ApiResult deleteByDateApi(@RequestParam String type, @RequestParam String startTime, @RequestParam String endTime);

    /**
     * @Author: levi
     * @Descript: 获取过去24小时每小时在线会员数量
     * @Date: 2018/8/27
     */
    @GetMapping(value = "getHourlyOnLines")
    ApiResult<List<HourlyOnLinesDTO>> getHourlyOnLines(@RequestParam Long siteId);

    /**
     * @Author: levi
     * @Descript: 获取C端访问量
     * @param startDate 开始时间 xxxx-xx-xx
     * @param endDate 结束时间   xxxx-xx-xx
     * @param siteId 站点id
     * @Date: 2018/12/5
     */
    @GetMapping(value = "getPageView")
    ApiResult<Integer> getPageView(@RequestParam String startDate,@RequestParam String endDate,@RequestParam Long siteId);
}
