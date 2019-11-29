package com.jq.user.report.service;

import com.liying.common.service.ApiResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserReportServiceTest {
    @Resource
    private UserReportService userReportService;

    @Test
    public void getUserUnLoginNumOfDaysApiTest(){
        List<Long> idList = new ArrayList<>();
        idList.add(1075695045592543234L);
        idList.add(1075633559746703362L);
        idList.add(1032876662709616642L);
        ApiResult<Map<Long, Integer>> userUnLoginNumOfDaysApi = userReportService.getUserUnLoginNumOfDaysApi(idList);
        System.out.println(userUnLoginNumOfDaysApi);
    }
}
