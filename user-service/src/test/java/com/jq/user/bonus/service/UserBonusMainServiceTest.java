package com.jq.user.bonus.service;

import cn.hutool.json.JSONUtil;
import com.jq.user.bonus.dto.UserBonusMainDTO;
import com.jq.user.bonus.dto.UserBonusMainSonDTO;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @author Json
 * @create 2018/6/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserBonusMainServiceTest {
     
    private UserBonusMainService userBonusMainService;

    @Test
    public void queryUserBonusSetByLevelApi(){
        ApiResult<List<Map<String,Object>>> listApiResult = userBonusMainService.queryUserBonusSetByLevelApi(123, 0, true, 123l);
        System.out.println(JSONUtil.toJsonStr(listApiResult));
    }

    @Test
    public void getByIdApi(){
        ApiResult<UserBonusMainDTO> byIdApi = userBonusMainService.getByIdApi(123l);
        System.out.println(JSONUtil.toJsonStr(byIdApi));
    }


    @Test
    public void updateApi(){
        UserBonusMainDTO dto=new UserBonusMainDTO();
        dto.setId(123l);
        dto.setGameId("305");
        userBonusMainService.updateApi(dto);
    }

    @Test
    public void queryContractRecordApi(){
        UserBonusMainDTO dto=new UserBonusMainDTO();
        dto.setSiteId(1040499894897405953L);
        dto.setUserId(1044494861697347586L);
        dto.setSettingType(1);
        dto.setGameCategoryId("gpc");
//        dto.setPlayType("125");
        ApiResult<List<UserBonusMainDTO>> userBonusMainDTOApiResult = userBonusMainService.queryContractRecordApi(dto);
        System.out.println(JSONUtil.toJsonStr(userBonusMainDTOApiResult));

    }

    @Test
    public void queryMainTest(){
        Long siteId = 123L;
        Integer settingType = 1;
        Integer signStatus = 123;
        ApiResult<List<UserBonusMainDTO>> listApiResult = userBonusMainService.queryMain(siteId, settingType, signStatus);
        List<UserBonusMainDTO> data = listApiResult.getData();
        System.out.println(data);
    }

    @Test
    public void saveApi(){
        UserBonusMainDTO dto=new UserBonusMainDTO();
        dto.setUpdateTime(new Date().toString());
        ApiResult apiResult = userBonusMainService.saveApi(dto);
        System.out.println(apiResult.getData());
    }


    @Test
    public void queryContractRecordByUserIdApi(){
        UserBonusMainDTO dto = new UserBonusMainDTO();
        dto.setUserId(1052563407102865410L);
        dto.setSiteId(1040499894897405953L);
        dto.setSettingType(1);
//        dto.setIsDel(1);
//        dto.setGameCategoryId("CP");
//        dto.setSignStatus(1);
//        dto.setStartTime("2018-10-18 18:03:30");
//        dto.setEndTime("2018-10-18 18:05:30");
        ApiResult<PageInfo<UserBonusMainDTO>> listApiResult = userBonusMainService.queryContractRecordByUserIdApi(dto);
        System.out.println(listApiResult);
    }


    @Test
    public void queryBonusMainSonApiTest(){
        ApiResult<List<UserBonusMainDTO>> apiResult = userBonusMainService.queryBonusMainSonApi(null, 0,0);
        System.out.println(JSONUtil.toJsonStr(apiResult));
    }

    @Test
    public void getBonusMainApiTest(){
        Integer settingType = 0;
        List<Integer> levelList = new ArrayList<>();
//        levelList.add(1);
//        levelList.add(2);
//        levelList.add(3);
        List<Long> toUserIdList = new ArrayList<>();
//        toUserIdList.add(1019066077414506498L);
//        toUserIdList.add(1019057458330497026L);
        Long siteId = 1011868438377877506L;
        ApiResult<List<UserBonusMainDTO>> bonusMainApi = userBonusMainService.getBonusMainApi(settingType, levelList, toUserIdList,siteId);
        System.out.println(bonusMainApi);
    }

    @Test
    public void queryContractBonusInfoApi(){
        Long siteId = 1040499894897405953L;
        Integer pageNum = 1;
        Integer pageSize = 2;
        String gameCategoryId = null;
        String playType = null;
        String toUserName = null;
        String userName = null;
        Integer settingType= 1;
        UserBonusMainDTO dto = new UserBonusMainDTO();
        dto.setSiteId(siteId);
        dto.setSettingType(settingType);
        dto.setIsDel(0);
        ApiResult<PageInfo<Map<String, Object>>> pageInfoApiResult = userBonusMainService.queryContractBonusInfoApi(dto);
        System.out.println(pageInfoApiResult);
    }

    @Test
    public void queryContractBonusApi(){
        Long siteId = 1040499894897405953L;
        Integer pageNum = 1;
        Integer pageSize = 2;
        String gameCategoryId = null;
        String playType = null;
        String toUserName = null;
        String userName = null;
        Integer settingType= 1;
        UserBonusMainDTO dto = new UserBonusMainDTO();
        dto.setSiteId(siteId);
        dto.setSettingType(settingType);
        dto.setIsDel(0);
        ApiResult<PageInfo<Map<String, Object>>> pageInfoApiResult = userBonusMainService.queryContractBonusApi(dto);
        System.out.println(pageInfoApiResult);
    }

    @Test
    public void updateSettleTime(){
        Long id = 1234L;
        ApiResult<Boolean> booleanApiResult = userBonusMainService.updateSettleTime(id,new Date());
        System.out.println(booleanApiResult);
    }

    @Test
    public void testEditOrSave(){
        String str ="{actualWinLose\n" +
                ":\n" +
                "[\"0\", \"1\"],\n" +
                "amount\n" +
                ":\n" +
                "[],\n" +
                "bonusAmount\n" +
                ":\n" +
                "[\"\", \"1\"],\n" +
                "bonusCycle\n" +
                ":\n" +
                "\"3\",\n" +
                "bonusMode\n" +
                ":\n" +
                "[\"0\", \"1\"],\n" +
                "bonusPer\n" +
                ":\n" +
                "[\"1\", \"\"],\n" +
                "bonusRule\n" +
                ":\n" +
                "\"0\",\n" +
                "bonusStrategy\n" +
                ":\n" +
                "\"0\",\n" +
                "distribute\n" +
                ":\n" +
                "\"1\",\n" +
                "gameList\n" +
                ":\n" +
                "[],\n" +
                "id\n" +
                ":\n" +
                "\"1065133414974287874\",\n" +
                "limitAmount\n" +
                ":\n" +
                "[\"0\", \"\"],\n" +
                "settingType\n" +
                ":\n" +
                "1,\n" +
                "sonId\n" +
                ":\n" +
                "[\"1065133415154642946\", \"\"],\n" +
                "validMember\n" +
                ":\n" +
                "[\"0\", \"1\"],\n" +
                "siteCode\n" +
                ":\n" +
                "\"awbnwp-0\",\n" +
                "siteId\n" +
                ":\n" +
                "\"1040499894897405953\"}";
        UserBonusMainSonDTO dto = JSONUtil.toBean(str, UserBonusMainSonDTO.class);
        userBonusMainService.editOrSaveApi(dto);
    }


    @Test
    public void testBatchQueryUserBonusMain(){
        List<Long> ids = new ArrayList<>();
        ids.add(1049615172767903745L);
        ids.add(1049654837663297538L);
        ids.add(1083302002669715458L);
        for(int i=0;i<100;i++){
            long l = System.currentTimeMillis();
            long l1 = System.currentTimeMillis();
            System.out.println(l1-l);
        }

    }

}
