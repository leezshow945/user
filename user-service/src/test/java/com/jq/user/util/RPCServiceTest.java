package com.jq.user.util;


import com.jq.platform.sysmanage.service.KeyValueService;
import com.jq.user.customer.dto.UserDTO;
import com.jq.user.customer.service.UserService;
import com.liying.common.service.ApiResult;
import com.liying.game.api.GamePlayService;
import com.liying.game.api.resp.GameResp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RPCServiceTest {

     
    private GamePlayService gamePlayService;

     
    private KeyValueService keyValueService;

     
    private UserService userService;


    @Test
    public void RPCAllTest() {
        ApiResult<Map<String, String>> keyValueList = keyValueService.findKeyValueMapByDictCode("SCORE_TYPE");
        assert keyValueList!=null;
        System.out.println(keyValueList.getData());


        ApiResult<List<GameResp>> gameList = gamePlayService.queryGameList();
        System.out.println(gameList.getData());
        assert  gameList!=null;
    }

    //测试platform接口
    @Test
    public void platformTest(){
        ApiResult<Map<String, String>> keyValueList = keyValueService.findKeyValueMapByDictCode("SCORE_TYPE");
        assert keyValueList!=null;
        System.out.println(keyValueList.getData());
    }

    //测试game-rpc接口
    @Test
    public void gameTest(){
        ApiResult<List<GameResp>> gameList = gamePlayService.queryGameList();
        System.out.println(gameList.getData());
        assert  gameList!=null;
    }

    //测试user接口
    @Test
    public void userTest(){
        ApiResult<UserDTO> userDTOApiResult = userService.registerDemoUserApi("123213", 8888888L, 0);
        System.out.println(userDTOApiResult.getData());
        assert  userDTOApiResult!=null;
    }
}
