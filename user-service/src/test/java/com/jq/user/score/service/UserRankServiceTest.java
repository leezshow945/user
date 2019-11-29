package com.jq.user.score.service;


import cn.hutool.json.JSONObject;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.jq.user.customer.dao.UserDao;
import com.jq.user.customer.entity.UserEntity;
import com.jq.user.score.dao.UserRankDao;
import com.jq.user.score.dto.RankBonusConfigDTO;
import com.jq.user.score.dto.UserRankDTO;
import com.jq.user.score.entity.UserRankEntity;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRankServiceTest {
    @Resource
    UserRankInnerService userRankInnerService;
    @Resource
    UserRankScoreInnerService userRankScoreInnerService;
    @Resource
    private UserRankService userRankService;
    @Resource
    private UserRankDao userRankDao;
    @Resource
    private RankBonusService rankBonusService;
    @Resource
    private UserDao userDao;




    //表单验证方法测试集合
    @Test
    public void checkMaxScoreAllTest() {
        assert userRankInnerService.isMaxRankLevel(1006467073941540866l, 1);
        //assert userRankInnerService.checkMaxScoreUp(888888888888888888L,400,"1");
        //assert userRankInnerService.checkMaxScoreDown(888888888888888888L,400,"1");
        //assert (!userRankInnerService.existRankName(888888888888888888L, "心悦1"));
    }

    //测试获取站点默认等级
    @Test
    public void findDefaultRankTest() {
        assert userRankService.getDefaultIdBySiteId(888888888888888888L) != null;
    }

    //测试获取站点默认等级
    @Test
    public void queryRankListAPI() {
        UserRankDTO userRankDTO = new UserRankDTO();
        userRankDTO.setSiteId(0l);
        userRankDTO.setLimit(5);
        userRankDTO.setPage(2);
        userRankDTO.setOrderDirection("DESC");
        userRankDTO.setOrderField("id");
        userRankDTO.setSiteId(0l);
        ApiResult<PageInfo<UserRankDTO>> pageInfoApiResult = userRankService.queryRankListAPI(userRankDTO);
        System.out.println(pageInfoApiResult.getData().toString());
        assert pageInfoApiResult != null;
    }

    //测试RPC查询等级详细信息
    @Test
    public void rankInfoByIdTest() {
        ApiResult<UserRankDTO> rankInfoByIdAPI = userRankService.getRankInfoByIdAPI(984682723098001410l);
        System.out.println(rankInfoByIdAPI.getData().toString());
        assert rankInfoByIdAPI != null;
    }

    //测试rpc修改等级管理
    @Test
    public void updateRankAPITest() {
        UserRankDTO userRankDTO = new UserRankDTO();
        userRankDTO = userRankService.getRankInfoByIdAPI(984682723098001411l).getData();
        userRankDTO.setMaxScore(10);

        Map map = new HashMap();
        map.put("ORDER_check", "1");
        map.put("ORDER_val", "50");

        ApiResult apiResult = userRankService.updateRankInfoAPI(userRankDTO);
        assert apiResult != null;
    }

    /**
     * Author: Brady
     * Description:测试获取rankId与rankLevel对应Map
     * Date: 2018/6/15
     */
    @Test
    public void findAllRankMapBySiteIdTest() {
        System.out.println(userRankInnerService.findAllRankMapBySiteId(986810793321410512L));
    }

    @Test
    public void getAllLevelBySiteIdApi() {
        Long siteId = 986810793321410512L;
        ApiResult<List<String>> allLevelBySiteIdApi = userRankService.getAllRankBySiteIdApi(siteId);
        List<String> data = allLevelBySiteIdApi.getData();
        System.out.println(data);
    }

    //RPC用户等级积分详情接口
    @Test
    public void userRankScoreTest() {
        ApiResult apiResult = userRankService.getRankScoreInfo(1011878781384740866l, 1012521970538934274l);
        assert apiResult.getCode().equals(10000);
    }

    //RPC用户等级代理层级接口查询
    @Test
    public void userRankRebateTest() {
        UserRankDTO userRankDTO = new UserRankDTO();
        userRankDTO.setSiteCode("diaeyp-4");
        userRankDTO.setUserId(1033349498011865447L);
        UserRankDTO userRankDTO2 = new UserRankDTO();
        userRankDTO2.setSiteCode("diaeyp-4");
        userRankDTO2.setUserId(1033349498037030984L);

        List<UserRankDTO> userRankDTOList = new ArrayList<>();
        userRankDTOList.add(userRankDTO);
        userRankDTOList.add(userRankDTO2);

        ApiResult apiResult = userRankService.getRankAndRebateLevel(userRankDTOList);
        System.out.println(apiResult);
    }

    @Test
    public void initRankInfoAPITest() {
        ApiResult<UserRankDTO> userRankDTOApiResult = userRankService.initRankInfoAPI(1032192142905282562l);
        System.out.println(userRankDTOApiResult);
    }

    @Test
    public void insertTest() {
        UserRankEntity e = new UserRankEntity();
        e.setId(IdWorker.getId());
        e.setRankName("测试一下");
        e.setRankLevel(50);
        e.setIsDel(0);
        e.setMaxScore(50);
        e.setRechargeRatio(5.868);
        userRankDao.insert(e);
    }

    @Test
    public void queryRankBySiteIdTest(){
        ApiResult<List<UserRankDTO>> rankList = userRankService.queryRankBySiteIdAPI(1040499894897405953l);
        ApiResult<RankBonusConfigDTO> rankBonus = rankBonusService.getRankBonus(1031885064860962817l, null);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rankList",rankList.getData());
        jsonObject.put("rankBonus",rankBonus.getData());
        System.out.println(jsonObject);
    }

    @Test
    public void  checkMaxScoreTest(){
        UserEntity userEntity = userDao.findById(1040519427137794050l);
        System.out.println(userRankInnerService.checkUserScore(userEntity));

    }

    @Test
    public void getSiteRankInfoBonusTest(){
        ApiResult siteRankInfoBonus = userRankService.getSiteRankInfoBonus(1040499894897405953l);
        System.out.println(siteRankInfoBonus.toString());
    }
}
