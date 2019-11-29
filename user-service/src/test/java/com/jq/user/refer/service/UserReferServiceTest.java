package com.jq.user.refer.service;

import cn.hutool.json.JSONUtil;

import com.jq.user.constant.UserConstant;
import com.jq.user.refer.dto.UserReferDTO;
import com.jq.user.refer.entity.UserReferEntity;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;

/**
 * 〈〉
 *
 * @author Json
 * @create 2018/4/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserReferServiceTest {

    @Resource
    public UserReferInnerService userReferInnerService;
    @Resource
    public UserReferService userReferService;



    @Test
    public void testSave(){
        UserReferEntity entiry=new UserReferEntity();
        entiry.setIsApp(UserConstant.IS_F);
        entiry.setIsEnable(UserConstant.IS_T);
        entiry.setReferUrl("www.baidu.com");
        entiry.setSiteId(111111L);
        entiry.setUserId(1111L);
        entiry.setCreateBy("0");
        entiry.setUpdateBy("0");
        entiry.setUpdateTime(new Date());
        entiry.setCreateTime(new Date());
        entiry.setDpcRebate(1l);
        entiry.setTyRebate(2l);
        entiry.setTycpRebate(3l);
        entiry.setLhcRebate0(4l);
        entiry.setLhcRebate1(5l);
        entiry.setLhcRebate2(6l);
        entiry.setLhcRebate2(7l);
        entiry.setQtRebate(8l);
        entiry.setFlcRebate(9l);
        entiry.setGpcRebate(10l);
        entiry.setIsProxy(UserConstant.IS_T);
        entiry.setDomainUrl("www.baidu.com");
        System.out.println(JSONUtil.toJsonStr(entiry));
//        userReferInnerService.save(entiry,"{'userName':'hyh','userId':'123456'}",0);
    }
    @Test
    public void testFindById(){
//        UserReferEntity entity = userReferInnerService.findById(100L);

    }

    @Test
    public void getListByConditionApi() {
        UserReferDTO dto=new UserReferDTO();
//        dto.setUserId(1111L);
//        dto.setCreateTime("2018-05-08");
//        dto.setUserName("default");
        dto.setRebateType(9);
        dto.setStartRebate(100l);
        dto.setEndRebate(600l);
        ApiResult<PageInfo<UserReferDTO>> listByConditionApi = userReferService.getListByConditionApi(dto);
        System.out.println(JSONUtil.parseObj(listByConditionApi));
    }

    @Test
    public void saveApi() {
//        UserReferDTO{id=null, userId=1012521970538934274, userName='null', siteId=1011878781384740866, siteCode='liying-1', referCode='null', referUrl='null', domainUrl='null', flcRebate=0.2, gpcRebate=0.2, tycpRebate=0.4, qtRebate=null, tyRebate=0.7, lhcRebate0=0.2, lhcRebate1=0.1, lhcRebate2=1.1, lhcRebate3=1.3, dpcRebate=0.4, isProxy=0, isApp=null, isEnable=null, createTime=2018-07-04 11:21:23, createBy='cjf001', updateBy='cjf001', updateTime=2018-07-04 11:21:23, remark='null', isDel=0, linkType=1, rebateType=null, startRebate=null, endRebate=null, startDate=null, endDate=null, orderField='id', orderDirection='desc', page=1, limit=20}
        UserReferDTO dto=new UserReferDTO();
        dto.setUserId(1010108144785903618L);
        dto.setSiteId(1011868438377877506L);
        dto.setSiteCode("jq");
        dto.setCreateBy("aaa");
        dto.setCreateTime("2018-06-15 12:58:24");
        dto.setDomainUrl1("www.43aa.com");
        dto.setDpcRebate(4.1d);
        dto.setFlcRebate(4.1d);
        dto.setFlcRebate(4.1d);
        dto.setGpcRebate(4.1d);
        dto.setIsApp(0);
        dto.setLhcRebate0(4.1d);
        dto.setLhcRebate1(4.1d);
        dto.setLhcRebate2(4.1d);
        dto.setLinkType(1);
        dto.setQtRebate(4.1d);
        dto.setTycpRebate(4.1d);
        dto.setTyRebate(4.1d);
        ApiResult apiResult = userReferService.saveApi(dto);
        System.out.println(apiResult);
    }

    @Test
    public void getByIdApi() {
        ApiResult<UserReferDTO> byIdApi = userReferService.getByIdApi(1022397341283921922l);
        System.out.println(JSONUtil.parseObj(byIdApi.getData()));
    }

    @Test
    public void updateApi() {
        UserReferDTO dto=new UserReferDTO();
        dto.setId(1066337892482215938l);
        dto.setUserId(1044484359151742977L);
        dto.setSiteId(1040499894897405953L);
        dto.setReferCode("hao123");
        dto.setUpdateBy("sh");
        dto.setLinkType(0);
        dto.setDomainUrl("http://www.439911.com");
        dto.setDomainUrl1("http://testdesktop.38c8.com");
        ApiResult apiResult = userReferService.updateApi(dto);
        System.out.println(apiResult);
    }

    @Test
    public void updateStatusApi() {
        UserReferDTO dto=new UserReferDTO();
        dto.setUserId(1111L);
        dto.setIsEnable(11);
        ApiResult apiResult = userReferService.updateStatusApi(new UserReferDTO());
        System.out.println(apiResult);
    }

    @Test
    public void deleteApi() {
        ApiResult apiResult = userReferService.deleteApi(new UserReferDTO());
        System.out.println(apiResult);
    }

    @Test
    public void getByDomainUrlApi(){
//        ApiResult<List<UserReferDTO>> byDomainUrlApi = userReferService.getByDomainUrlApi(111111L, 0, "www.baidu.com");
        ApiResult<List<UserReferDTO>> byDomainUrlApi = userReferService.getByDomainUrlApi(111111L, null, null);
        System.out.println(byDomainUrlApi);
    }

    @Test
    public void getUserNameById(){
//        ApiResult<List<UserReferDTO>> byDomainUrlApi = userReferService.getByDomainUrlApi(111111L, 0, "www.baidu.com");
        List<Long> list=new ArrayList<>();
        list.add(1022679141792641025l);
        list.add(1022688677664927746l);
        list.add(1022691224772964354l);
        list.add(1022692507173023746l);
        list.add(1022692956752588802l);
        ApiResult<Map<Long, String>> userNameById = userReferService.getUserNameById(list);
        System.out.println(userNameById.getData());
    }

//    @Test
//    public void updateReferUseCount() {
//        Long id = 1031885122109505538L;
//        ApiResult<?> result = userReferService.updateReferUseCount(id);
//        System.out.println(result);
//    }

}
