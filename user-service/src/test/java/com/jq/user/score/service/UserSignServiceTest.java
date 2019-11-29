package com.jq.user.score.service;

import com.jq.user.score.dto.UserSignDTO;
import com.liying.common.service.ApiResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserSignServiceTest {
    @Resource
    UserSignService userSignService;

    //签到RPC接口测试
    @Test
    public void userSignTest(){

        ApiResult apiResult = userSignService.userSignAPI(986810793321410512l,1007552882623460002l);

        assert apiResult.getCode().equals(10000);

        ApiResult<List<UserSignDTO>> signRecordAPI = userSignService.getSignRecordAPI(986810793321410512l, 1007552882623460002l);

        assert signRecordAPI.getCode().equals(10000);
    }
}
