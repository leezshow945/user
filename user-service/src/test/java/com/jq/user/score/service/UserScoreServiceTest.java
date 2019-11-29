package com.jq.user.score.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.user.common.InitParameters;
import com.jq.user.constant.ScoreTypeEnum;
import com.jq.user.score.dto.UserScoreDTO;
import com.jq.user.support.PageUtil;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserScoreServiceTest {
    @Resource
    UserScoreInnerService userScoreInnerService;
    @Resource
    UserScoreService userScoreService;
    @Resource
    UserSignInnerService userSignInnerService;

    //查询会员积分测试
    @Test
    public void queryListTest(){
        Map paramMap = new HashMap();
        paramMap.put("status",10);
        List<Map<String,Object>> userScoreList= userScoreInnerService.findAll(983579946843058179L,paramMap,new Page());
        assert userScoreList.size()>0;
    }

    //会员人工操作积分
    @Test
    public void manualUpdateTest(){
        Map paramMap = new HashMap();
        paramMap.put("type",0);
        paramMap.put("userId",666666666666666666L);
        paramMap.put("value",5);
        paramMap.put("createBy", "周杰伦");
        paramMap.put("siteId",0L);
        assert userScoreInnerService.manualUpdate(paramMap);
    }

    //查询会员流水
    @Test
    public void queryRecordTest(){
        Map paramMap = new HashMap();
        paramMap.put("timeStr","20190211");
        Page page = PageUtil.buildPage(1, 10, "DESC", "usr.create_time");
        List<Map<String,Object>> userRecordList= userScoreInnerService.findAllRecord(1040499894897405953L,paramMap,page);
        assert userRecordList.size()>0;
    }

    //查询会员积分RPC测试
    @Test
    public void queryListRPCTest(){
        UserScoreDTO userScoreDTO = new UserScoreDTO();
        userScoreDTO.setSiteId(1040499894897405953l);
        userScoreDTO.setUserName("shon102");
        ApiResult<PageInfo<UserScoreDTO>> pageInfoApiResult = userScoreService.queryScoreListAPI(userScoreDTO);

        System.out.println(pageInfoApiResult);
    }

    //查询会员积分流水RPC测试
    @Test
    public void queryRecodeListRPCTest(){
        UserScoreDTO userScoreDTO = new UserScoreDTO();
        userScoreDTO.setBeginTime("2018-01-06");
        userScoreDTO.setEndTime("2018-10-06");
        ApiResult<PageInfo<UserScoreDTO>> pageInfoApiResult = userScoreService.queryScoreRecordListAPI(userScoreDTO);
        assert pageInfoApiResult.getData().getContent().size()>0;
        System.out.println(pageInfoApiResult.getData().toString());
    }

    //查询初始化参数RPC接口测试
    @Test
    public void queryInitParamsTest(){
        ApiResult<InitParameters> scoreListParamsAPI = userScoreService.getScoreListParamsAPI(0l);

        assert scoreListParamsAPI.getData()!=null;
    }

    //人工操作积分RPC接口测试
    @Test
    public void manualUpdateRPCTest(){
        UserScoreDTO userScoreDTO = new UserScoreDTO();

        userScoreDTO.setUserId(999930626187038721l);
        userScoreDTO.setType(0);
        userScoreDTO.setScoreVal(1);
        userScoreDTO.setCreateBy("杰伦");
        ApiResult apiResult = userScoreService.manualUpdateAPI(userScoreDTO);

        assert apiResult.getData()!=null;

    }

    //积分计算测试
    @Test
    public void updateScoreTest(){

        ApiResult apiResult = userScoreService.updateUserScore("1007552882623460002", ScoreTypeEnum.SCORE_TYPE_RECHARGE.getCode());

        assert apiResult.getCode().equals(10000);
    }

    //签到测试集合
    @Test
    public void signTest(){
        boolean sign = userSignInnerService.createSign(986810793321410512l, 1007552882623460002l);
        assert sign;
    }

    //RPC用户积分流水
    @Test
    public void userScoreTest(){

        UserScoreDTO userScoreDTO=new UserScoreDTO();
        userScoreDTO.setUserId(1033538588008591361l);
        userScoreDTO.setSiteId(1031885064860962817l);
        userScoreDTO.setScoreCode("GET_TO_RANK");
        userScoreDTO.setBeginTime("2018-06-18");
        userScoreDTO.setEndTime("2019-06-29");
        ApiResult apiResult = userScoreService.queryScoreRecordAPI(userScoreDTO);
        assert apiResult.getCode().equals(10000);
    }

    @Test
    public void userRechargeTest(){

        ApiResult apiResult = userScoreService.updateUserScoreByRecharge("1098901538293219329","100");
        assert apiResult.getCode().equals(10000);
    }



}
