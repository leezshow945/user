package com.jq.user.customer.dao;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.platform.sysmanage.dto.AreaDTO;
import com.jq.platform.sysmanage.service.AreaService;
import com.jq.user.customer.entity.UserEntity;
import com.jq.user.customer.entity.UserWrapper;
import com.jq.user.customer.vo.UserVO;
import com.liying.common.service.ApiResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Created by ZhangCong on 2018/4/4
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

    @Resource
    private UserDao userDao;
    @Resource
    private BankCardDao bankCardDao;
    private AreaService areaService;
    @Resource
    private UserRebateDao userRebateDao;

//    @Test
//    public void getProxyByUserIdTest(){
//        Long userId = 1023872965148160002L;
//        List<Long> proxyByUserId = userRebateDao.getProxyByUserId(null, 2);
//        System.out.println(proxyByUserId);
//    }


    @Test
    public void queryUserByModel() {
        UserVO userVO = new UserVO();
        Page page = new Page();
        userVO.setSearchType("1");
        userVO.setLoginName("l");
        userVO.setSort("2");
        userVO.setIsProxy("1");
        page.setCurrent(1);
        page.setSize(2);
        Long siteId = 123L;
        Map<String, Object> map = BeanUtil.beanToMap(userVO);
        List<UserWrapper> list = userDao.queryUserByModel(page, map);
        for (UserWrapper userWrapper : list) {
            System.out.println(userWrapper);
        }
    }

    @Test
    public void selectSiteIdAndCardNo() {
        long siteId = 1050624121944207361L;
        String cardNo = "OTg5ODk4OTg4ODg4";
        Integer num = bankCardDao.countBySiteIdAndCardNo(siteId, cardNo);
        System.out.println(num);
    }

    @Test
    public void test() {
        ApiResult<List<AreaDTO>> province = areaService.findProvince();
        for (AreaDTO area : province.getData()) {
            System.out.println(area);
        }
    }

    @Test
    public void selectByLoginNameTest() throws Exception {
        String userName = "admin";
        Long siteId = 123L;
        UserEntity userEntity = userDao.findByUserName(userName,siteId);
        System.out.println(userEntity.getPassword());
    }

    @Test
    public void confirmExistLoginNameTest(){
        String loginName = "admin";
        Long siteId = 123L;
        UserEntity userEntity = userDao.confirmExistUserName(siteId,loginName);
        System.out.println(userEntity);
    }

    @Test
    public void saveTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("e10adc3949ba59abbe56e057f20f883e");
        userEntity.setUserName("admin");
        userEntity.setSiteId(111111L);
        userEntity.setCreateTime(new Date());
        userEntity.setUpdateTime(new Date());
        userEntity.setId(IdWorker.getId());
        userEntity.setUserRankId(1111111L);
        userDao.insert(userEntity);
    }



    @Test
    public void pathTest(){
//        String path = "1011868438377877507,1013691319396536322,1013697010760876034,1020588732136783874"; [default, nickel, jimmmm, ces7216]
        String path = "1011868438377877507,1013691319396536322,1020588732136783874,1013697010760876034";
//        String path2 ="1011868438377877507,1020588732136783874,1013697010760876034,1013691319396536322";
        List<String> idList = Arrays.asList(path.toString().split(",")).stream().collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        Map<String,String> pathMap = new HashMap<>();
        List<Map<String,String>> map = userDao.getUserNameListByIdList(idList);
        for (Map<String, String> stringStringMap : map) {
            pathMap.put(String.valueOf(stringStringMap.get("id")),stringStringMap.get("user_name"));
        }
        for (String id : idList) {
            String idStr = pathMap.get(id);
            sb.append(idStr).append("-");
        }
        sb.deleteCharAt(sb.length()-1);
        System.out.println(sb.toString());
    }

    @Test
    public void queryUserOnlineApi(){
        Page page = new Page(1,10);
        String userNme = null;
        Long siteId  =null;
        List<Long> idList  = new ArrayList<>();
        idList.add(1033349498028642713L);
        idList.add(1033349498028642714L);
        String type = "1";
        List<Map<String, Object>> list = userDao.queryUserOnlineApi(page, userNme, siteId, idList, type);
        System.out.println(list);
    }
}
