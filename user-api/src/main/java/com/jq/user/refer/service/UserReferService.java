package com.jq.user.refer.service;


import com.jq.user.refer.dto.UserReferDTO;
import com.jq.user.refer.fallbackfactory.UserReferServiceFallbackFactory;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 〈推广链接〉
 *
 * @author Json
 * @create 2018/4/25
 */
@FeignClient(value = "user",path = "/inner/user/refer",url = "${feign-url.user:}",fallbackFactory = UserReferServiceFallbackFactory.class)
public interface UserReferService {

    /**
     * 条件查询推广链接列表（分页）
     * @param dto
     *  startDate ,endDate 日期格式 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    @PostMapping(value = "getListByConditionApi")
    ApiResult<PageInfo<UserReferDTO>>  getListByConditionApi(@RequestBody UserReferDTO dto);

    /**
     * 新增推广链接
     * @param dto
     * userId 必须 createBy 必须 创建实间
     * @return
     */
    @PostMapping(value = "saveApi")
    ApiResult saveApi(@RequestBody UserReferDTO dto);

    /**
     * 根据主键查询推广链接
     * @param referId
     * @return
     */
    @GetMapping(value = "getByIdApi")
    ApiResult<UserReferDTO> getByIdApi(@RequestParam Long referId);

    /**
     * 修改推广链接
     * @param dto
     * id 必须 updateBy 必须
     * @return
     */
    @PutMapping(value = "updateApi")
    ApiResult updateApi(@RequestBody UserReferDTO dto);

    /**
     * 更新推广链接状态
     * @return
     */
    @PutMapping(value = "updateStatusApi")
    ApiResult updateStatusApi(@RequestBody UserReferDTO dto);

    /**
     * 根据主键删除
     * @return
     */
    @PostMapping(value = "deleteApi")
    ApiResult deleteApi(@RequestBody UserReferDTO dto);

    /**
     * 根据链接类型，链接 查询
     * @param siteId 站点id
     * @param linkType 链接类型
     * @param domainUrl 域名
     * @return
     */
    @GetMapping(value = "getByDomainUrlApi")
    ApiResult<List<UserReferDTO>> getByDomainUrlApi(@RequestParam Long siteId, @RequestParam Integer linkType,
                                                    @RequestParam String domainUrl);

    /**
     * 根据id查询代理账户名 批量
     * @param idList
     *
     * @return map <id,userName>
     */
    @GetMapping(value = "getUserNameById")
    ApiResult<Map<Long,String>> getUserNameById(@RequestParam List<Long> idList);

}
