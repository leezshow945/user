package com.jq.user.score.controller;

import com.jq.user.score.dto.UserSignDTO;
import com.jq.user.score.service.UserSignInnerService;
import com.jq.user.score.service.UserSignService;
import com.liying.common.service.ApiResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/inner/user/sign")
public class UserSignController implements UserSignService {

    @Resource
    private UserSignInnerService userSignInnerService;

    @Override
    public ApiResult userSignAPI(@RequestParam Long siteId, @RequestParam Long userId) {
        return userSignInnerService.userSignAPI(siteId, userId);
    }

    @Override
    public ApiResult<List<UserSignDTO>> getSignRecordAPI(@RequestParam Long siteId, @RequestParam Long userId) {
        return userSignInnerService.getSignRecordAPI(siteId, userId);
    }
}
