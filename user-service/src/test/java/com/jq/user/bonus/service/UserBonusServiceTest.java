package com.jq.user.bonus.service;

import cn.hutool.json.JSONUtil;
import com.jq.user.bonus.dto.UserBonusDTO;
import com.jq.user.bonus.dto.UserBonusMainDTO;
import com.jq.user.oldbonus.dao.UserBonusDao;
import com.jq.user.oldbonus.service.UserBonusInnerService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @author Json
 * @create 2018/6/19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserBonusServiceTest {

     
    private UserBonusService userBonusService;
     
    private UserBonusDao userBonusDao;
    @Resource
    private UserBonusInnerService userBonusInnerService;




    @Test
    public void queryUserBonusByUserIdApi(){
        ApiResult<List<UserBonusDTO>> listApiResult = userBonusService.queryUserBonusByUserIdApi(123L,123L,true,0);
        System.out.println(JSONUtil.parse(listApiResult));

    }


    @Test
    public void queryUserBonusDividByPageApi(){
        UserBonusDTO dto=new UserBonusDTO();
        dto.setDataType(0);
        dto.setPeriods("20190114,20190115");
        dto.setSiteId(1040499894897405953L);
        dto.setSiteCode("awbnwp-0");
        ApiResult<PageInfo<UserBonusDTO>> pageInfoApiResult = userBonusService.queryUserBonusDividByPageApi(dto);
        System.out.println(JSONUtil.parse(pageInfoApiResult));
    }

    @Test
    public void queryUserWagesByPageApiTest(){
        UserBonusDTO dto=new UserBonusDTO();
        dto.setAuditorId(1006489386778988546L);
        dto.setDataType(2);
        ApiResult<PageInfo<UserBonusDTO>> pageInfoApiResult = userBonusService.queryUserWagesByPageApi(dto);
        System.out.println(JSONUtil.parse(pageInfoApiResult));
    }
    @Test
    public void batchSave(){
        UserBonusDTO dto = new UserBonusDTO();
        dto.setGameCategoryId("gpc");
        dto.setPeriods("20180718");
        dto.setAllBettingAmount(19000l);
        dto.setAllBettingNumber(190);
        dto.setRebate(1900l);
        dto.setExamineState(1);
        dto.setWinAmount(0l);
        dto.setTeamMakeWater(0l);
        dto.setWinLose(19000l);
        dto.setActivity(0l);
        dto.setActualWinLose(0l);
        dto.setBonus(19000000l);
        dto.setDataType(2);
        dto.setToUserId(1019780907776630785l);
        dto.setMainId(1018801606758076418l);
        dto.setSiteId(1018752981500252161l);
        dto.setLevel(1);
        List<UserBonusDTO> bonusList = new ArrayList<UserBonusDTO>();
        bonusList.add(dto);
        // 批量保存
        ApiResult<?> apiResult = userBonusService.insertBonusBatch(bonusList);
        System.out.println(apiResult);
    }

    @Test
    public void getTotal(){
        Long totalBonus = userBonusDao.getTotalBonus(999930626187038721l, null);
        System.out.println(totalBonus);
    }

    @Test
    public void getPastPeriodBonusApi(){
        String gameCategory = "gpc";
        String playType =null;
        Long toUserId = 1020888444244619266L;
        String periods = "20180731";
        Integer dateType = 0;
        Long siteId = 1018752981500252161L;
        Long mainId =1L;
        ApiResult<UserBonusDTO> pastPeriodBonusApi = userBonusService.getPastPeriodBonusApi(mainId,gameCategory, playType, toUserId, periods, dateType, siteId,0);
        System.out.println(pastPeriodBonusApi);
    }

    @Test
    public void getTotalMakeWater(){
        Long toUserId= 1014030654208299010L;
        long totalMakeWater = userBonusInnerService.getTotalMakeWater(toUserId);
        System.out.println(totalMakeWater);
    }

    @Test
    public void getUsersDailyBonus(){
        List<Long> list=new ArrayList<>();
        list.add(1033776286870876161l);

        List<Map<String, Long>> maps = userBonusDao.selectUsersDailyBonus(list, 2, "2018-08-30 18:19:19", "2018-08-30 18:50:19");
        System.out.println(JSONUtil.toJsonStr(maps));
    }

    @Test
    public void getBonusTotalByTypeApi(){
        String gameCategoryId= null;
        String playType = null;
        List<Long> toUserIdList = new ArrayList<>();
        Integer dataType = null;
        Long siteId = null;
        String startTime = null;
        String endTime = null;
//        ApiResult<Long> bonusTotalByTypeApi = userBonusService.getBonusTotalByTypeApi(gameCategoryId, playType, toUserIdList, dataType,
//                siteId, startTime, endTime);
//        System.out.println(bonusTotalByTypeApi);
    }

    @Test
    public void checkUnDistributeRecord(){
        UserBonusMainDTO dto  = new UserBonusMainDTO();
        dto.setUserId(1033775924797583361L);
        dto.setSiteId(1031885064860962817L);
        dto.setSettingType(1);
        ApiResult<Boolean> booleanApiResult = userBonusService.checkUnDistributeRecord(dto);
        System.out.println(booleanApiResult);
    }
}
