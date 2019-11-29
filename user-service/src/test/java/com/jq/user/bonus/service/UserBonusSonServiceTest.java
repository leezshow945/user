package com.jq.user.bonus.service;

import cn.hutool.json.JSONUtil;
import com.jq.user.bonus.dto.UserBonusSonDTO;
import com.liying.common.service.ApiResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 〈〉
 *
 * @author Json
 * @create 2018/6/22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserBonusSonServiceTest {

     
    private UserBonusSonService userBonusSonService;


    @Test
    public void queryUserBonusSonApi(){
        ApiResult<List<UserBonusSonDTO>> desc = userBonusSonService.queryUserBonusSonApi(123l, "0", "", "desc");
        System.out.println(JSONUtil.toJsonStr(desc));
    }

    @Test
    public void updateApi(){
        UserBonusSonDTO dto=new UserBonusSonDTO();
        dto.setId(123L);
        dto.setAmount(666L);
        userBonusSonService.updateApi(dto);
    }

    @Test
    public void querySonByMainIdApi(){
        ApiResult<List<UserBonusSonDTO>> listApiResult = userBonusSonService.querySonByMainIdApi(123l);
        System.out.println(JSONUtil.toJsonStr(listApiResult));
    }

    @Test
    public void queryBonusSonByMainIdApi(){
//        Map<String,Object> map1=new HashMap<>();
//        map1.put("mainId",123l);
//        map1.put("settingType",0);
//        map1.put("amount",667l);
//        map1.put("amountOrRebate",125l);
////        map1.put("settingType",0);
//        Map<String,Object> map2=new HashMap<>();
//        ApiResult<List<UserBonusSonDTO>> listApiResult = userBonusSonService.queryBonusSonByMainIdApi(map1, map2);
//        System.out.println(JSONUtil.toJsonStr(listApiResult));
    }

    @Test
    public void getUserBonusTest(){
        Integer settingType = 0;
        Long mainId = 1L;
        ApiResult<List<UserBonusSonDTO>> userBonus = userBonusSonService.getUserBonusSonApi(settingType, mainId);
        List<UserBonusSonDTO> data = userBonus.getData();
        System.out.println(data);
    }
}
