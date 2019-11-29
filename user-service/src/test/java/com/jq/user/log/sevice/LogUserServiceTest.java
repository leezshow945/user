package com.jq.user.log.sevice;


import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.user.constant.UserConstant;
import com.jq.user.log.dao.LogUserDao;
import com.jq.user.log.dto.HourlyOnLinesDTO;
import com.jq.user.log.dto.LogQryParamDTO;
import com.jq.user.log.dto.LogUserDTO;
import com.jq.user.log.entity.LogUserEntity;
import com.jq.user.log.service.LogUserInnerService;
import com.jq.user.log.service.LogUserService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
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
public class LogUserServiceTest {

    @Resource
    private LogUserDao logUserDao;
    @Resource
    private LogUserInnerService logUserInnerService;
    @Resource
    private LogUserService logUserService;


    @Test
    public void insertTest() {
        LogUserEntity logUserEntity = new LogUserEntity();
        long loginId = IdWorker.getId();
        long loginIp = IdWorker.getId();
        long siteId = IdWorker.getId();

        logUserEntity.setPlat(1);
        logUserEntity.setUserName("test1");
        logUserEntity.setContent("content");
        logUserEntity.setUserId(loginId);
        logUserEntity.setLoginIp("192.168.11.1");
        logUserEntity.setLoginTime(new Date());
        logUserEntity.setLoginArea("中国 武汉");
        logUserEntity.setIsDiffAreaLogin(1);
        logUserEntity.setType("LOGIN_SUCCESS");
        logUserEntity.setAccountType("SYS");
        logUserEntity.setSiteId(siteId);
        logUserEntity.setIsDel(UserConstant.IS_F);
        logUserEntity.setFlagType("操作日志");
        logUserEntity.setLoginUrl("logUrl");
        logUserEntity.setCreateTime(new Date());
        logUserEntity.setUpdateTime(new Date());
        logUserDao.insert(logUserEntity);
        LogUserEntity logUserEntity1 = logUserDao.selectById(logUserEntity.getId());
        assert logUserEntity.getId().equals(logUserEntity1.getId());
    }

    @Test
    public void saveTest() {
//        LogUserDTO logUser = new LogUserDTO();
//        long loginId = IdWorker.getId();
//        long loginIp = IdWorker.getId();
//        long siteId = IdWorker.getId();
//        logUser.setPlat("PLAT");
//        logUser.setLoginAccount("test2");
//        logUser.setContent("content");
//        logUser.setLoginId(loginId);
//        logUser.setLoginIp(loginIp);
//        logUser.setLoginTime(new Date());
//        logUser.setLoginArea("中国 武汉");
//        logUser.setIsDiffAreaLogin("T");
//        logUser.setType("LOGIN_SUCCESS");
//        logUser.setAccountType("SYS");
//        logUser.setSiteId(siteId);
//        logUser.setIsDel(UserConstant.IS_F);
//        logUser.setFlagType("操作日志");
//        logUser.setLoginUrl("logUrl");
//        logUser.setCreateTime(new Date());
//        logUser.setUpdateTime(new Date());
//        logUserInnerService.save(logUser);
    }

    @Test
    public void queryUserLogByRecordTest() {
        Long siteId = 986810793321410512L;
        String userName = "";
        String ip = "";
        String accountType = "";
        String startTime = "2018-5-01 00:00:00";
        String endTime = "2018-5-31";
        Integer isDiffArea = null;
        int current = 1;
        int size = 10;
        List<Map<String, Object>> list = logUserInnerService.queryUserLogByRecord(siteId, userName, ip, accountType,
                startTime, endTime, isDiffArea, new Page(current, size));
        for (Map<String, Object> map : list) {
            System.out.println(map);
        }

    }

    @Test
    public void qryUserLoginLog() {
        Long siteId = 983579946843058179L;
        Integer searchType = 1;
        Integer sort = 1;
        String keyword = null;
        String startTime = null;
        String endTime = null;
        Integer isDiffArea = null;
        int current = 1;
        int size = 10;
        List<Map<String, Object>> list = logUserInnerService.qryUserLoginLog(siteId, searchType, keyword, sort,
                startTime, endTime, isDiffArea, new Page(current, size));
        for (Map<String, Object> map : list) {
            System.out.println(map);
        }

    }

    @Test
    public void deleteBatch() {
        ArrayList<Long> list = new ArrayList<>();
        list.add(982870150993133581L);
        list.add(982870150993133569L);
        boolean b = logUserInnerService.deleteBatch(list);
        assert b;
    }

    @Test
    public void updateByIdTest() {
        Long id = 982869259351212033L;
        LogUserEntity logUserEntity = logUserDao.selectById(id);
        assert UserConstant.IS_F.equals(logUserEntity.getIsDel());
        logUserInnerService.updateById(id);
        LogUserEntity logUserEntity1 = logUserDao.selectById(id);
        assert UserConstant.IS_T.equals(logUserEntity1.getIsDel());
    }

    @Test
    public void addUserLogApi() {
        LogUserDTO dto = new LogUserDTO();
        dto.setPlat(1);
        dto.setUserName("userName");
        dto.setContent("content");
        dto.setUserId(983579946843058178L);
        dto.setLoginIp("loginIp");
        dto.setType("TEST");
        dto.setSiteCode("LYJT");
        dto.setAccountType("USER");
        dto.setSiteId(986810793321410512L);
        ApiResult apiResult = logUserService.addUserLogApi(dto);
    }

    @Test
    public void qryUserLoginLogApi() {
        LogQryParamDTO logQryParamDTO = new LogQryParamDTO();
        logQryParamDTO.setSiteId(1011879551446372353L);
        logQryParamDTO.setSiteCode("test-2");
        logQryParamDTO.setSearchType(3);
        logQryParamDTO.setKeyword("yan009");
//        Long siteId = 986810793321410512L;
//        String userName = "";
//        String ip = "";
//        String accountType = "";
//        String startTime = "2018-5-01 00:00:00";
//        String endTime = "2018-5-31";
//        Integer isDiffArea = null;
//        int current = 1;
//        int size = 10;
        ApiResult<PageInfo<LogUserDTO>> apiResult = logUserService.qryUserLoginLogApi(logQryParamDTO);
        PageInfo<LogUserDTO> data = apiResult.getData();
        System.out.println(data);
    }

    @Test
    public void qryUserLogByRecordApi() {
        LogQryParamDTO logQryParamDTO = new LogQryParamDTO();
//        logQryParamDTO.setSiteId(983579946843058179L);
//        logQryParamDTO.setStartTime("2018-05-10");
        Long siteId = 986810793321410512L;
        String userName = "";
        String ip = "";
        String accountType = "";
        String startTime = "2018-5-01 00:00:00";
        String endTime = "2018-5-31 00:00:00";
        Integer isDiffArea = null;
        int current = 1;
        int size = 10;
        logQryParamDTO.setSiteId(siteId);
        logQryParamDTO.setStartTime(startTime);
        logQryParamDTO.setEndTime(endTime);
        ApiResult<PageInfo<LogUserDTO>> apiResult = logUserService.qryUserLogByRecordApi(logQryParamDTO);
        PageInfo<LogUserDTO> data = apiResult.getData();
        System.out.println(data);
    }

    @Test
    public void deleteBatchApi() {
//        List<Long> ids = new ArrayList<>();
//        ids.add(982870150993133573L);
//        ids.add(982870150993133577L);
        String ids = "982870150993133577,982870150993133585";
        ApiResult apiResult = logUserService.deleteBatchApi(ids);
        System.out.println(apiResult);
    }

    @Test
    public void deleteApi() {
        ApiResult apiResult = logUserService.deleteApi(982870150993133589L);
    }

    @Test
    public void deleteByDateApiTest() {
        String startTime = "2018-5-8";
        String endTime = "2018-5-10";
        String type = null;
        ApiResult apiResult = logUserService.deleteByDateApi(type, startTime, endTime);
        System.out.println(apiResult);
    }

    @Test
    public void getHourlyOnLines(){
        Long siteId =1018752981500252161L;
        long start = System.currentTimeMillis();
        ApiResult<List<HourlyOnLinesDTO>> hourlyOnLines = logUserService.getHourlyOnLines(siteId);
        long end = System.currentTimeMillis();
        System.out.println(hourlyOnLines);
        long time = end-start;
        System.out.println("耗时:"+time);
    }

    @Test
    public void getPageView(){
        String startTime = "2018-12-06";
        String endTime = "2018-12-13";
        Long siteId = 1040499894897405953L;
        ApiResult<Integer> pageView = logUserService.getPageView(startTime, endTime, siteId);
        System.out.println(pageView);
    }
}
