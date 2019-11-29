package com.jq.user.customer.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.framework.core.exception.Assert;
import com.liying.common.service.ApiResult;
import com.jq.user.customer.dao.UserDao;
import com.jq.user.customer.entity.BlacklistEntity;
import com.jq.user.customer.service.BlacklistInnerService;
import com.liying.common.service.RPCResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @author cx
 * @Description: 黑名单管理
 * @date 2018/4/8 16:32
 */
@RestController
@RequestMapping("/blacklist")
public class BlacklistController {

     
    private BlacklistInnerService blacklistInnerService;
    @Resource
    private UserDao userDao;


    /**
     * 根据列表条件查询黑名单
     *
     * @param blacklistEntity 黑名单对象
     * @param page            分页对象
     * @return ApiResult<Map>
     */
    @GetMapping
    public ApiResult<Map> query(BlacklistEntity blacklistEntity, Page page) {
        Map<String, Object> blacklistMap = blacklistInnerService.queryList(blacklistEntity, page);
        return RPCResult.success(blacklistMap);
    }

    /**
     * 新增黑名单信息
     *
     * @param blacklistEntity 黑名单对象
     * @param tokeninfo       token
     * @return ApiResult<String>
     */
    @PostMapping
    public ApiResult<String> add(@RequestBody BlacklistEntity blacklistEntity,
                                 @RequestHeader("tokeninfo") String tokeninfo) {
        JSONObject jsonObject = JSONUtil.parseObj(tokeninfo);
        String userName = userDao.findUserNameById(blacklistEntity.getUserId());
        blacklistEntity.setUserName(userName);
        blacklistEntity.setCreateBy(jsonObject.getStr("userName"));
        blacklistEntity.setUpdateBy(jsonObject.getStr("userName"));
        Date nowDate = new Date();
        blacklistEntity.setCreateTime(nowDate);
        blacklistEntity.setUpdateTime(nowDate);
        Boolean flag = this.blacklistInnerService.insert(blacklistEntity);
        return flag ? RPCResult.success():RPCResult.fail();
    }

    /**
     * 修改黑名单信息
     *
     * @param blacklistEntity 黑名单对象
     * @return ApiResult<String>
     */
    @PutMapping
    public ApiResult<String> update(@RequestBody BlacklistEntity blacklistEntity,
                                    @RequestHeader("tokeninfo") String tokeninfo) {
        Assert.isNull(blacklistEntity.getId(), "主键id不能为空");
        String userName = userDao.findUserNameById(blacklistEntity.getUserId());
        blacklistEntity.setUserName(userName);
        JSONObject jsonObject = JSONUtil.parseObj(tokeninfo);
        blacklistEntity.setUpdateTime(new Date());
        blacklistEntity.setUpdateBy(jsonObject.getStr("userName"));
        Boolean flag = this.blacklistInnerService.update(blacklistEntity);
        return flag ? RPCResult.success():RPCResult.fail();
    }


    /**
     * 批量(单个)删除
     *
     * @param ids 主键id串
     * @return ApiResult<String>
     */
    @DeleteMapping("{ids}")
    public ApiResult<String> deleteBatchIds(@PathVariable("ids") String ids) {
        Assert.isEmpty(ids, "参数ids不能为空");
        Boolean flag = blacklistInnerService.deleteBatchIds(ids);
        return flag ? RPCResult.success():RPCResult.fail();
    }
}