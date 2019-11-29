package com.jq.user.valid.service;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jq.user.customer.entity.UserEntity;
import com.jq.user.valid.dao.ValidUserStatisticsDao;
import com.jq.user.valid.dto.ValidUserSettingDTO;
import com.jq.user.valid.entity.ValidUserStatisticsEntity;
import com.liying.common.service.ApiResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidUserServiceTest {
    @Resource
    private ValidUserSettingService validUserSettingService;
    @Resource
    private ValidUserStatisticsInnerService validUserStatisticsInnerService;
    @Resource
    private ValidUserStatisticsDao validUserStatisticsDao;
    @Resource
    private ValidUserStatisticsService validUserStatisticsService;

    @Test
    public void insert() {
        ValidUserStatisticsEntity entity = new ValidUserStatisticsEntity();
        entity.setUserId(1234L);
        entity.setSiteCode("1233");
        entity.setSiteId(12345L);
        entity.setIsValid(0);
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        Integer insert = validUserStatisticsDao.insert(entity);
        System.out.println(insert);
    }

    @Test
    public void find() {
        List<ValidUserStatisticsEntity> validUserStatisticsEntities = validUserStatisticsDao.selectList(new QueryWrapper<>());
        System.out.println(validUserStatisticsEntities);
    }

    @Test
    public void saveOrUpdateValidUserSettingApiTest() {
        ValidUserSettingDTO dto = new ValidUserSettingDTO();
        dto.setSiteId(1040499894897405953L);
        dto.setSiteCode("awbnwp-0");
        dto.setRechargeAmount(1L);
        dto.setWithdrawAmount(2L);
        dto.setLoginDays(1);
        dto.setLoginCountNum(2);
        dto.setHasRealName(1);
        dto.setIsRepeat(0);
//        dto.setCreateTime(DateUtil.now());
        dto.setUpdateTime(DateUtil.now());
        dto.setCreateBy("levi");
        dto.setUpdateBy("levi");
        dto.setValidBetAmount(2L);
        dto.setValidOrderNum(3);
        dto.setRegisterDays(4);
        ApiResult apiResult = validUserSettingService.updateSettingApi(dto);
        System.out.println(apiResult);
    }

    @Test
    public void findBySiteIdApiTest() {
        ApiResult<ValidUserSettingDTO> bySiteIdApi = validUserSettingService.findBySiteIdApi(1040499894897405953L);
        System.out.println(bySiteIdApi);
    }

    @Test
    public void assembleRegisterDaysAndLoginCountTest() {
        Long siteId = 1040499894897405953L;
        Map<Long, Map<String, Integer>> longMapMap = validUserStatisticsInnerService.assembleValidUserInfo(siteId);
        System.out.println(longMapMap);
    }

    @Test
    public void getValidUserDateBySiteIdTest() {
        Long siteId = 1050624121944207361L;
        Integer type = 2;
        validUserStatisticsInnerService.updateValidUserDateBySiteId(siteId);
    }

    @Test
    public void statisticsBySiteIdTest() {
        Long siteId = 1050624121944207361L;
        boolean b = validUserStatisticsInnerService.statisticsBySiteId(siteId);
        System.out.println(b);
    }

    @Test
    public void countValidUserBySiteIdTest() {
        Long siteId = 1050624121944207361L;
        Integer integer = validUserStatisticsInnerService.countValidUserBySiteId(siteId);
        System.out.println(integer);
    }

    @Test
    public void validUserDateSaveAndStatisticsTest() {
        validUserStatisticsInnerService.validUserDateSaveAndStatistics();
    }

    @Test
    public void updateBatchTest() {
        List<ValidUserStatisticsEntity> list = new ArrayList<>();
        ValidUserStatisticsEntity entity = new ValidUserStatisticsEntity();
        entity.setRechargeAmount(0L);
        entity.setWithdrawAmount(0L);
        entity.setValidBetAmount(0L);
        entity.setValidOrderNum(0);
        entity.setRegisterDays(0);
        entity.setLoginDays(0);
        entity.setLoginCountNum(0);
        entity.setHasRealName(0);
        entity.setIsRepeat(0);
        entity.setIsValid(0);
        entity.setUpdateTime(new Date());
        entity.setUserId(1040499915534974977L);
        list.add(entity);
        entity.setUserId(1040519427137794050L);
        list.add(entity);
        Integer integer = validUserStatisticsDao.updateBatch(list);
        System.out.println(integer);
    }

    @Test
    public void initValidUserInfoTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(12345L);
        userEntity.setSiteId(1111L);
        userEntity.setSiteCode("test");
        validUserStatisticsInnerService.initValidUserInfo(userEntity);
    }

    @Test
    public void countValidUserBySiteIdApiTest() {
        Long siteId = 1040499894897405953L;
        ApiResult<Integer> integerApiResult = validUserStatisticsService.countValidUserBySiteIdApi(siteId);
        System.out.println(integerApiResult);
    }

    @Test
    public void initValidUserDataTest(){
        Long siteId = 1032563150157877249L;
        String siteCode = "jzwsfi-6";
        boolean b = validUserStatisticsInnerService.initValidUserData(siteId,siteCode);
        System.out.println(b);
    }
}
