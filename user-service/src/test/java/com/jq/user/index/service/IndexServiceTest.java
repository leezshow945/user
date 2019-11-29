package com.jq.user.index.service;

import com.jq.user.index.dto.IndexDTO;
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
public class IndexServiceTest {
    @Resource
    private IndexService indexService;

    @Test
    public void findNewUserIdListTest(){
        String startDate = "2018-12-07";
        String endDate = "2018-12-07";
        Long siteId = 1040499894897405953L;
        String siteCode = "awbnwp-0";
        ApiResult<List<Long>> newUserIdList = indexService.findNewUserIdList(startDate, endDate, siteCode);
        System.out.println(newUserIdList);

    }

    @Test
    public void findNewUserIdListApiTest(){
        String startDate = "2018-12-07";
        String endDate = "2018-12-07";
        String siteCode = "awbnwp-0";
        ApiResult<Map<Long, String>> newUserListApi = indexService.findNewUserMapApi(startDate, endDate, siteCode);
        System.out.println(newUserListApi);

    }

    @Test
    public void countNewUserTest(){
        String startDate = "2018-12-11";
        String endDate = "2018-12-11";
        Long siteId = 1040499894897405953L;
        String siteCode = "awbnwp-0";
        Integer isProxy = null;
        ApiResult<Map<String, Integer>> mapApiResult = indexService.countNewUserByDateGroup(startDate, endDate, siteId, isProxy);
        System.out.println(mapApiResult);
    }

    @Test
    public void countNewUserByDateTest(){
        String startDate = "2018-12-01";
        String endDate = "2018-12-07";
        Long siteId = 1040499894897405953L;
        String siteCode = "awbnwp-0";
        Integer isProxy = null;
        ApiResult<Integer> integerApiResult = indexService.countNewUserByDate(startDate, endDate, siteId, isProxy);
        System.out.println(integerApiResult);
    }

    @Test
    public void getUserDisc(){
        Integer type = 3;
        String startDate = "2017-12-11";
        String endDate = "2018-12-11";
        String siteCode = "awbnwp-0";
        Long siteId = 1040499894897405953L;
        IndexDTO dto = new IndexDTO();
        dto.setStartDate(startDate);
        dto.setEndDate(endDate);
        dto.setSiteId(siteId);
        dto.setType(type);
        dto.setSiteCode(siteCode);
        ApiResult<Map<String, Integer>> userDisc = indexService.getUserDisc(dto);
        System.out.println(userDisc);
    }
}
