package com.jq.user.bonus.service;

import cn.hutool.json.JSONUtil;
import com.jq.user.bonus.dto.UserBonusMainSettingDTO;
import com.jq.user.bonus.dto.UserBonusSettingDTO;
import com.liying.common.service.ApiResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserBonusSettingServiceTest {
    @Resource
    private UserBonusSettingService userBonusSettingService;

    @Test
    public void queryBonusSetByLevelApiTest() {
        Long siteId = 1006467073941540866L;
        Integer level = 1;
        Integer settingType = 3;
        ApiResult<Map<String, Object>> apiResult = userBonusSettingService.queryBonusSetByLevelApi(siteId, level, settingType);
        Map<String, Object> data = apiResult.getData();
        System.out.println(data);
    }

    @Test
    public void getBonusSettingApiTest() {
        Long mainId = 123L;
        Integer settingType = 2;
        ApiResult<List<UserBonusSettingDTO>> bonusSettingApi = userBonusSettingService.getBonusSettingApi(settingType, mainId);
        List<UserBonusSettingDTO> data = bonusSettingApi.getData();
        System.out.println(data);
    }
    @Test
    public void queryUserBonusSettingApi(){
        ApiResult<List<Map<String, Object>>> apiResult = userBonusSettingService.queryUserBonusSettingApi(1054565686383276034L);
        System.out.println(apiResult);
    }


    @Test
    public void testUpadateUserBonusSetting(){
        String body = "{bonus\n" +
                ":\n" +
                "[\"\", \"\", \"\"],\n" +
                "bonusCycle\n" +
                ":\n" +
                "\"0\",\n" +
                "bonusMode\n" +
                ":\n" +
                "[\"0\", \"0\", \"0\"],\n" +
                "daysPer\n" +
                ":\n" +
                "[\"1\", \"1\", \"1\"],\n" +
                "distribute\n" +
                ":\n" +
                "0,\n" +
                "gameList\n" +
                ":\n" +
                "[],\n" +
                "id\n" +
                ":\n" +
                "\"\",\n" +
                "playType\n" +
                ":\n" +
                "\"\",\n" +
                "rebate\n" +
                ":\n" +
                "[0, 0, 0],\n" +
                "rebateLevel\n" +
                ":\n" +
                "\"1\",\n" +
                "settingType\n" +
                ":\n" +
                "2,\n" +
                "sonId\n" +
                ":\n" +
                "[\"\", \"\", \"\"],\n" +
                "teamActualLose\n" +
                ":\n" +
                "[\"1\", \"2\", \"2\"],\n" +
                "teamDailyAmount\n" +
                ":\n" +
                "[\"1\", \"2\", \"2\"],\n" +
                "validMember\n" +
                ":\n" +
                "[\"1\", \"1\", \"1\"],\n" +
                "ykPer\n" +
                ":\n" +
                "[\"\", \"\", \"\"]}";
        ApiResult apiResult = userBonusSettingService.updateUserBonusSetting(JSONUtil.toBean(body, UserBonusMainSettingDTO.class));
    }


    public static void main(String[] args) {
        String body = "{\n" +
                "    \"bonus\": [\n" +
                "        \"0\",\n" +
                "        \"1000\",\n" +
                "        \"200\",\n" +
                "        \"\"\n" +
                "    ],\n" +
                "    \"bonusCycle\": \"3\",\n" +
                "    \"bonusMode\": [\n" +
                "        \"0\",\n" +
                "        \"1\",\n" +
                "        \"1\",\n" +
                "        \"0\"\n" +
                "    ],\n" +
                "    \"daysPer\": [\n" +
                "        \"1\",\n" +
                "        \"0\",\n" +
                "        \"0\",\n" +
                "        \"4\"\n" +
                "    ],\n" +
                "    \"distribute\": \"1\",\n" +
                "    \"gameCategoryId\": \"CP\",\n" +
                "    \"gameList\": [\n" +
                "        \"bjpk10\",\n" +
                "        \"5fsc_s\",\n" +
                "        \"xyft_s\",\n" +
                "        \"qwsm\",\n" +
                "        \"5fsc\",\n" +
                "        \"qwsm_s\",\n" +
                "        \"mhft_s\",\n" +
                "        \"xyft\",\n" +
                "        \"3fsc_s\",\n" +
                "        \"mhft\",\n" +
                "        \"3fsc\"\n" +
                "    ],\n" +
                "    \"id\": \"1070522366315528193\",\n" +
                "    \"playType\": \"\",\n" +
                "    \"rebate\": [\n" +
                "        \"1\",\n" +
                "        \"1\",\n" +
                "        \"3\",\n" +
                "        \"4\"\n" +
                "    ],\n" +
                "    \"rebateLevel\": \"2\",\n" +
                "    \"settingType\": 2,\n" +
                "    \"sonId\": [\n" +
                "        \"1070522366445551618\",\n" +
                "        \"1070522901166862338\",\n" +
                "        \"1070522901217193985\",\n" +
                "        \"\"\n" +
                "    ],\n" +
                "    \"teamActualLose\": [\n" +
                "        \"1\",\n" +
                "        \"1\",\n" +
                "        \"10\",\n" +
                "        \"4\"\n" +
                "    ],\n" +
                "    \"teamDailyAmount\": [\n" +
                "        \"1\",\n" +
                "        \"10\",\n" +
                "        \"20\",\n" +
                "        \"4\"\n" +
                "    ],\n" +
                "    \"validMember\": [\n" +
                "        \"1\",\n" +
                "        \"1\",\n" +
                "        \"2\",\n" +
                "        \"4\"\n" +
                "    ],\n" +
                "    \"ykPer\": [\n" +
                "        \"0\",\n" +
                "        \"5\",\n" +
                "        \"10\",\n" +
                "        \"\"\n" +
                "    ]\n" +
                "}";

        UserBonusMainSettingDTO userBonusMainSettingDTO = JSONUtil.toBean(body, UserBonusMainSettingDTO.class);
        System.out.println();
    }
}
