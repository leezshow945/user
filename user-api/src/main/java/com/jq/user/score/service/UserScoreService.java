package com.jq.user.score.service;

import com.jq.user.common.InitParameters;
import com.jq.user.score.dto.UserScoreDTO;
import com.jq.user.score.fallbackfactory.UserScoreServiceFallbackFactory;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "user",path = "/inner/user/score",url = "${feign-url.user:}",fallbackFactory = UserScoreServiceFallbackFactory.class)
public interface UserScoreService {

    /**
     * 根据站点id与page参数获取会员积分列表数据
     *
     * @param userScoreDTO
     * @return
     */
    @PostMapping(value = "queryScoreListAPI")
    ApiResult<PageInfo<UserScoreDTO>> queryScoreListAPI(@RequestBody UserScoreDTO userScoreDTO);

    /**
     * 根据站点id与page参数获取会员积分流水数据
     * B端查看站点下会员积分流水
     *
     * @param userScoreDTO
     * @return
     */
    @PostMapping(value = "queryScoreRecordListAPI")
    ApiResult<PageInfo<UserScoreDTO>> queryScoreRecordListAPI(@RequestBody UserScoreDTO userScoreDTO);

    /**
     * C端用户获取个人积分流水
     * @return
     */
    @PostMapping(value = "queryScoreRecordAPI")
    ApiResult<PageInfo<UserScoreDTO>> queryScoreRecordAPI(@RequestBody UserScoreDTO userScoreDTO);

    /**
     * 获取用户积分模块初始化参数集合
     *
     * @param
     * @return
     */
    @GetMapping(value = "getScoreListParamsAPI")
    ApiResult<InitParameters> getScoreListParamsAPI(@RequestParam Long siteId);

    /**
     * 获取用户积分流水模块初始化参数集合
     *
     * @param
     * @return
     */
    @GetMapping(value = "getScoreRecordListParamsAPI")
    ApiResult<InitParameters> getScoreRecordListParamsAPI(@RequestParam Long siteId);

    /**
     * 人工操作积分方法
     *
     * @param
     * @return
     */
    @PostMapping(value = "manualUpdateAPI")
    ApiResult manualUpdateAPI(@RequestBody UserScoreDTO userScoreDTO);

    /**
     * 更新用户积分方法(提供RPC接口调用为首次下注与首次充值，其余实现走内部service处理)
     *
     * @param scoreType{RECHARGE,ORDER}
     * @return
     */
    @PostMapping(value = "updateUserScore")
    ApiResult updateUserScore(@RequestParam String userId, @RequestParam String scoreType);

    /**
     * 充值更新用户积分方法
     *
     * @param
     * @return
     */
    @PostMapping(value = "updateUserScoreByRecharge")
    ApiResult updateUserScoreByRecharge(@RequestParam String userId,@RequestParam String money);

}
