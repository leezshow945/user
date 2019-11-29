package com.jq.user.score.controller;


import com.jq.user.common.InitParameters;
import com.jq.user.score.dto.UserScoreDTO;
import com.jq.user.score.service.UserScoreInnerService;
import com.jq.user.score.service.UserScoreService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户积分Controller
 */
@RestController
@RequestMapping(value = "/inner/user/score")
public class UserScoreController implements UserScoreService {


    @Resource
    private UserScoreInnerService userScoreInnerService;


    @Override
    public ApiResult<PageInfo<UserScoreDTO>> queryScoreListAPI(@RequestBody UserScoreDTO userScoreDTO) {
        return userScoreInnerService.queryScoreListAPI(userScoreDTO);
    }

    @Override
    public ApiResult<PageInfo<UserScoreDTO>> queryScoreRecordListAPI(@RequestBody UserScoreDTO userScoreDTO) {
        return userScoreInnerService.queryScoreRecordListAPI(userScoreDTO);
    }

    @Override
    public ApiResult<PageInfo<UserScoreDTO>> queryScoreRecordAPI(@RequestBody UserScoreDTO userScoreDTO) {
        return userScoreInnerService.queryScoreRecordAPI(userScoreDTO);
    }

    @Override
    public ApiResult<InitParameters> getScoreListParamsAPI(@RequestParam Long siteId) {
        return userScoreInnerService.getScoreListParamsAPI(siteId);
    }

    @Override
    public ApiResult<InitParameters> getScoreRecordListParamsAPI(@RequestParam Long siteId) {
        return userScoreInnerService.getScoreRecordListParamsAPI(siteId);
    }

    @Override
    public ApiResult manualUpdateAPI(@RequestBody UserScoreDTO userScoreDTO) {
        return userScoreInnerService.manualUpdateAPI(userScoreDTO);
    }

    @Override
    public ApiResult updateUserScore(@RequestParam String userId, @RequestParam String scoreType) {
        return userScoreInnerService.updateUserScore(userId, scoreType);
    }

    @Override
    public ApiResult updateUserScoreByRecharge(@RequestParam String userId, @RequestParam String money) {
        return userScoreInnerService.updateUserScoreByRecharge(userId, money);
    }
}
