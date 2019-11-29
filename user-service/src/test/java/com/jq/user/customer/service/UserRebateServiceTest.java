package com.jq.user.customer.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.crypto.asymmetric.RSA;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.jq.user.customer.dao.UserRebateDao;
import com.jq.user.customer.dto.*;
import com.jq.user.customer.entity.UserRebateEntity;
import com.jq.user.proxy.service.UserProxyInnerService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRebateServiceTest {

    @Resource
    private UserRebateInnerService userRebateInnerService;
    @Resource
    private UserRebateService userRebateService;
    @Resource
    private UserInnerService userInnerService;
    @Resource
    private UserRebateTransService userRebateTransService;
    @Resource
    private UserProxyInnerService userProxyInnerService;

    @Test
    public void treeTest() {
        //新建高层
//        treeTestService.newUser("boss","default");
//        treeTestService.newUser("nickel","boss");
//        treeTestService.newUser("jim","nickel");
//        treeTestService.newUser("lee","jim");
//        treeTestService.newUser("levi","lee");
//        treeTestService.newUser("小levi","levi");
//        treeTestService.newUser("小小levi","小levi");
//
//        treeTestService.newUser("kobe","jim");
//        treeTestService.newUser("leo","nickel");
//        treeTestService.newUser("mdh","jim");
//        treeTestService.newUser("lxh","jim");
//
//        treeTestService.tranUser("levi","boss");


    }

    @Test
    public void getRebateByUserNameAndSiteIdTest() {
        String userName = "whlevi1";
        Long siteId = 1040499894897405953L;
        Map<String, Object> rebate = userRebateInnerService.getRebateByUserNameAndSiteId(userName, siteId);
        System.out.println(rebate);
    }

    @Test
    public void test() {
        String num = "1.3";
        BigDecimal mul = NumberUtil.mul(new BigDecimal(num), new BigDecimal(100));
        System.out.println(mul.longValue());
    }

    @Test
    public void baseInfoTest() {
        Long id = 983579946843058178L;
        Map<String, Object> baseInfo = userRebateInnerService.findBaseInfo(id);
        System.out.println(baseInfo);

    }

//    @Test
//    public void getPromo() {
//        Long userId = 983579946843058178L;
//        Integer isProxy = null;
//        String startTime = null;
//        String endTime = null;
//        String rebateLimit = "gpc_rebate";
//        Double min = 5.4;
//        Double max = 5.5;
//        List<Map<String, Object>> promo = userRebateInnerService.getPromo(userId, isProxy, startTime, endTime, rebateLimit, min, max);
//        for (Map<String, Object> map : promo) {
//            System.out.println(map);
//        }
//    }

    @Test
    public void getRebateByUserId() {
        Long userId = 1033349498011865480L;
        ApiResult<UserRebateDTO> result = userRebateService.getRebateByUserId(userId);
        UserRebateDTO dto = result.getData();
        System.out.println(dto);
    }

    @Test
    public void getUserId() {
        Long userId = 1040499915534974977L;
        Integer type = 2;
        ApiResult<List<Long>> proxyByUserId = userRebateService.getProxyByUserId(userId, type);
        System.out.println(proxyByUserId);
    }

    @Test
    public void qryUserByPage() {
        QueryParamDTO dto = new QueryParamDTO();
        dto.setSiteId(1040499894897405953L);
        dto.setSiteCode("awbnwp-0");
        dto.setLoginName("277530599");
        dto.setSearchType(4);
        ApiResult<PageInfo<UserRebateDTO>> mapApiResult = userRebateService.qryUserByPageApi(dto);
        System.out.println(mapApiResult);
    }

    @Test
    public void addUserApiTest() {
        AddUserDTO dto = new AddUserDTO();
        dto.setSiteId(1011868438377877506L);
        dto.setHighLevelName("jimmmm");
        dto.setDpcRebate(0d);
        dto.setFlcRebate(0d);
        dto.setGpcRebate(0d);
        dto.setIsDemo(0);
        dto.setIsProxy(0);
        dto.setLhcRebate0(0d);
        dto.setLhcRebate1(0d);
        dto.setLhcRebate2(0d);
        dto.setLhcRebate3(0d);
//        String password = "WR+/evcKlPM7dZMRsVMmUgFoEcpy7WE77EsbHKblKK7M1jQjS/pt+PlRg/FYrJPcKw7MgRLwQYJ4\nQLp5dFz5a6H6NkQ9dHTvV6ZuQvEJ2QnyxzF6Raeb9WJQaHPoyBXHfoGIjEshGUqkmA80mzjv/wvM\niLMflBn7BPjyrmxbaG/1y2oLPqzzr3/jqvhne6VzpzZNnpXJ4IW+2QYvp7UcV5kHgNw3hs8N6QJz\nmPOfeQelDBPI59Qv2yOWGRP/SGvhBJIpAWFKE8B3J94SO9hwHGliNxK57jJ0Babef2uMiI8zVTOU\n94/yoTvfXYO87O9xkQyB+FUEA0giURL2B7iVAQ==";
        String password = "q123456";
        String pwd = userInnerService.createPwd(password);
        dto.setPassword(pwd);
        dto.setQtRebate(0d);
        dto.setRealName("宁静");
        dto.setSiteCode("jq-0");
        dto.setUserName("levii724");
        dto.setTycpRebate(0d);
        dto.setTyRebate(0d);
//        String password = "password";
//        dto.setSysUserName("sysUserName");
//        dto.setSiteId(983579946843058179L);
//        dto.setHighLevelName("username");
//        dto.setIsProxy(0);
//        dto.setUserName("subuser");
//        String rsaPublicKey = userInnerService.getRSAPublicKey();
//        RSA rsa = new RSA(null, rsaPublicKey);
//        String RSApassword = rsa.encryptStr(password, KeyType.PublicKey);
//        dto.setPassword(RSApassword);
//        dto.setRealName("开发");
//        dto.setGpcRebate(1.1);
//        dto.setFlcRebate(2.2);
//        dto.setTycpRebate(3.3);
//        dto.setQtRebate(4.4);
//        dto.setTyRebate(10.5);
//        dto.setDpcRebate(6.6);
//        dto.setLhcRebate0(0.6);
//        dto.setLhcRebate1(1.6);
//        dto.setLhcRebate2(2.6);
//        dto.setLhcRebate3(3.6);
//        dto.setHighLevelName("leeeee");
//        dto.setDpcRebate(1000d);
//        dto.setFlcRebate(1000d);
//        dto.setGpcRebate(1000d);
//        dto.setIsDemo(0);
//        dto.setIsProxy(1);
//        dto.setLhcRebate0(1000d);
//        dto.setLhcRebate1(1000d);
//        dto.setLhcRebate2(1000d);
//        dto.setLhcRebate3(1000d);
//        String password = "123456";
//        String pwd = userInnerService.createPwd(password);
////        String md5PwdByRSAPwd = userInnerService.getMD5PwdByRSA(pwd);
//        dto.setPassword(pwd);
//        dto.setQtRebate(1000d);
//        dto.setRealName("测试");
//        dto.setSiteCode("jq-0");
//        dto.setSysUserName("sysMan");
//        dto.setUserName("lynnnnnn");
//        dto.setTycpRebate(1000d);
//        dto.setTyRebate(1000d);
//        dto.setSiteId(1011868438377877506L);
        ApiResult<UserDTO> result = userRebateService.addUserApi(dto);
        UserDTO data = result.getData();
        System.out.println(data);
    }

    @Test
    public void updateBaseInfoApi() {
        UserRebateUpdateDTO dto = new UserRebateUpdateDTO();
        String password = "password";
        dto.setUserId(983579946843058178L);
        String rsaPublicKey = userInnerService.getRSAPublicKey();
        RSA rsa = new RSA(null, rsaPublicKey);
//        String RSApassword = rsa.encryptStr(password, KeyType.PublicKey);
//        dto.setPassword(RSApassword);
        dto.setRealName("真实");
        dto.setGpcRebate("200");
        dto.setFlcRebate("300");
        dto.setTycpRebate("400");
        dto.setQtRebate("500");
        dto.setTyRebate("600");
        dto.setDpcRebate("700");
        dto.setLhcRebate0("800");
        dto.setLhcRebate1("900");
        dto.setLhcRebate2("1000");
        dto.setLhcRebate3("1000");
        ApiResult apiResult = userRebateService.updateBaseInfoApi(dto);
        System.out.println(apiResult);
    }




    @Test
    public void getSubUserListApi() {
//        String userName = "whlevi3";
        Long userId = 1065125573387415554L;
        Long siteId = 1040499894897405953L;
        String siteCode = "awbnwp-0";
//        String highUserName = "dingyu12";
//        String highLevelName = "whlevi1";
        QueryParamDTO dto = new QueryParamDTO();
        dto.setSiteCode(siteCode);
        dto.setUserId(userId);
        dto.setSiteId(siteId);
//        dto.setOrderField("balance");
//        dto.setUserName("qiang02");
//        dto.setIsOnLine(1);
//        dto.setHighLevelName(highUserName);
//        dto.setUserName(userName);
        dto.setIsSearchAll(1);
//        dto.setHighLevelName(highLevelName);
        ApiResult<PageInfo<UserRebateDTO>> subUserListApi = userRebateService.getSubUserListApi(dto);
        System.out.println(subUserListApi);

    }

    @Test
    public void getLevelApi() {
        Long siteId = 983579946843058179L;
        ApiResult<UserDTO> levelAndRankApi = userRebateService.getLevelAndRankApi(siteId);
        UserDTO data = levelAndRankApi.getData();
        System.out.println(data);
    }

    @Test
    public void getUserLevelPathApi() {
        String userName = "testusername";
        Long siteId = 1007129659188023298L;
        ApiResult<List<UserDTO>> userLevelPathApi = userRebateService.getUserLevelPathApi(userName, siteId);
        System.out.println(userLevelPathApi);
    }

    @Test
    public void getPathApiTest() {
        Long userId = 998747329471676417L;
        ApiResult<String> apiResult = userRebateService.getPathByApi(userId);
        String data = apiResult.getData();
        System.out.println(data);
    }


    @Test
    public void getUserInfoAndPathApiTest() {
        String userName = "test";
        Long siteId = 1006467073941540866L;
        ApiResult api = userRebateService.getUserInfoAndPathApi(userName, siteId);
        System.out.println(api.getData().toString());
    }

    @Test
    public void getAllLevelBySiteIdApi() {
        Long siteId = 1040499894897405953L;
        ApiResult<List<Map<String, Object>>> allLevelBySiteIdApi = userRebateService.getAllLevelBySiteIdApi(siteId);
        List<Map<String, Object>> data = allLevelBySiteIdApi.getData();
        System.out.println(data);
    }

    @Test
    public void addUserInnerTest() {
        UserRebateEntity userRebateEntity = new UserRebateEntity();
//        userRebateEntity.setHighLevelAccount("default");
        Long siteId = 1006467073941540866L;
        String siteCode = "liying-0";
        userRebateEntity.setDpcRebate(0L);
        userRebateEntity.setTycpRebate(0L);
        userRebateEntity.setQtRebate(0L);
        userRebateEntity.setTyRebate(0L);
        userRebateEntity.setFlcRebate(0L);
        userRebateEntity.setGpcRebate(0L);
        userRebateEntity.setLhcRebate0(0L);
        userRebateEntity.setLhcRebate1(0L);
        userRebateEntity.setLhcRebate2(0L);
        userRebateEntity.setLhcRebate3(0L);
        userRebateEntity.setSiteId(siteId);
        userRebateEntity.setSiteCode(siteCode);
        userRebateEntity.setId(IdWorker.getId());
        boolean b = userRebateTransService.saveUserRebate(userRebateEntity,null);
        System.out.println(b);
    }

    @Test
    public void getUserTypeTest() {
        List<Long> ids = new ArrayList<>();
        ids.add(111111111111111111L);
        ids.add(983579946843058178L);
        Long siteId = 983579946843058179L;
        ApiResult<List<UserDTO>> userType = userRebateService.getUserType(ids, siteId);
        List<UserDTO> data = userType.getData();
        for (UserDTO datum : data) {
            System.out.println(datum.getUserName() + ":" + datum.getIsProxy());
        }
    }

    @Test
    public void queryUserInfoAndPathByIdApiTest() {
        List<Long> idList = new ArrayList<>();
        idList.add(1035826975273046079L);
        idList.add(1035826975273046073L);
        Long siteId = 1032562600599195650L;
        ApiResult<List<UserRebateDTO>> userRebateDTOApiResult = userRebateService.queryUserInfoAndPathByIdApi(idList, siteId);
        System.out.println(userRebateDTOApiResult);
    }

    @Test
    public void queryUserInfoAndPathByIdList() {
        List<Long> idList = new ArrayList<>();
        idList.add(1035826975273046079L);
        idList.add(1035826975273046073L);
        Long siteId = 1032562600599195650L;
        Integer isProxy = 1;
        ApiResult<List<UserRebateDTO>> userRebateDTOApiResult = userRebateService.queryUserInfoAndPathByIdApi(idList,siteId,isProxy);
        System.out.println(userRebateDTOApiResult);
    }

    @Test
    public void getUserFundByUserIdAndSiteIdTest() {
        Long userId = null;
        Long siteId = null;
        userRebateService.getUserFundByUserIdAndSiteIdApi(999930626187038721L,siteId);
    }

    @Test
    public void getRebateByUserNameAndSiteIdApiTest() {
        String userName = "testusername";
        Long siteId = 1011868438377877506L;
        ApiResult<Map<String, Object>> rebateByUserNameAndSiteIdApi = userRebateService.getRebateByUserNameAndSiteIdApi(userName, siteId);
        Map<String, Object> data = rebateByUserNameAndSiteIdApi.getData();
        System.out.println(data);
    }

    @Test
    public void transferApiTest() {
        Long destId = 1023870285352808450L;
        Long sourceId = 1023870826627739649L;
        String destName = "nickel";
        String sourceName = "jimmmm";
        Integer isTransfer = 1;
        String manUserName = "whlevi123";
        ApiResult<Boolean> booleanApiResult = userRebateService.transferApi(destId, sourceId, destName, sourceName, isTransfer, manUserName);
        System.out.println(booleanApiResult);
    }

    @Test
    public void transferBatchApiTest() {
        Long destId = 1013698352808128514L;
        String destName = "leeeee";
        String userIds = "1013698998852608001,1013699897822003201";
        String managerUserName = "sysMan";
        ApiResult apiResult = userRebateService.transferBatchApi(destId, destName, userIds,managerUserName);
        Object data = apiResult.getData();
        System.out.println(data);
    }

    @Test
    public void getUserDetailApiTest(){
        Long id = 1054258784373895170L;
        ApiResult<UserRebateDTO> userDetailApi = userRebateService.getUserDetailApi(id);
        System.out.println(userDetailApi);
    }



    @Test
    public void getEffectUserId(){
        Long siteId = 1040499894895953L;
        List<Long> list = new ArrayList<>();
        list.add(1042610259043086337L);
        list.add(1042623959774851073L);
        list.add(1042674117883523073L);
        ApiResult<List<Long>> effectUserId = userRebateService.getEffectUserId(list,siteId);
        System.out.println(effectUserId);
    }

    @Test
    public void getDefaultAndDirectUserIdBySiteCodeTest(){
        String siteCode = "diaeyp-4";
        ApiResult<List<Long>> defaultAndDirectUserIdBySiteCode = userRebateService.getDefaultAndDirectUserIdBySiteCode(siteCode);
        System.out.println(defaultAndDirectUserIdBySiteCode);
    }
    @Test
    public void getHighLevelIdAndRankTest(){
        List<Long> ids = new ArrayList<>();
        ids.add(1049858450125811713L);
        ids.add(1049858560670887937L);
        ids.add(1033292159586467842L);
        ids.add(1033349497978310857L);
        ApiResult<List<UserRebateDTO>> highLevelIdAndRank = userRebateService.getHighLevelIdAndRank(ids);
        System.out.println(highLevelIdAndRank);
    }

    @Resource
    private UserRebateDao userRebateDao;

    @Test
    public void testTest(){
        List<Long> list = new ArrayList<>();
        list.add(1019907059979666014L);
        list.add(1019907059979666040L);
        list.add(1019907059979666081L);
        list.add(1019907057316282369L);
        Long siteId = null;
        List<UserRebateEntity> entityList = userRebateDao.selectBatchIds(list);
        //总的代理线Map
        Map<Long,Map> proxyLine = new HashMap<>();
        for(UserRebateEntity entity : entityList){
            List<Long> allHighUserIdList = userProxyInnerService.getAllHighUserIdList(entity.getId(), entity.getSiteId());
            if(CollectionUtil.isEmpty(allHighUserIdList)){
                continue;
            }
            //每个用户的上级
//            String[] paths = entity.getPath().split(",");
            for(int i = 0; i <allHighUserIdList.size();i++ ){
                //当前用户所属的代理线
                Long proxy = allHighUserIdList.get(i);
                //代理线中所有的下级
                Map<Long,Long> userLine = new HashMap<>();
                //如果代理线已存在，则从代理线Map中获取
                if(proxyLine.containsKey(proxy)){
                    userLine = proxyLine.get(proxy);
                }
                for(int j = i; j < allHighUserIdList.size(); j ++){
                    Long id = allHighUserIdList.get(j);
                    userLine.put(entity.getId(),entity.getId());
                    //将用户所有上级存入代理线中，包含了去重于合并操作
                    userLine.put(id,id);
                }
                proxyLine.put(proxy,userLine);
            }
            Map<Long,Long> self = new HashMap();
            self.put(entity.getId(),entity.getId());
            proxyLine.put(entity.getId(),self);
        }
        //组装数据格式
        Map<Long,List<Long>> result = new HashMap<>();
        for(Long key : proxyLine.keySet()){
            Map child = proxyLine.get(key);
            result.put(key,new ArrayList<>(child.keySet()));
            System.out.println("key:" + key + ", value:" + new ArrayList<>(child.keySet()));

        }
        //System.out.println(result);
    }

    @Test
    public void getHighLevelProxyByIdListTest(){
        long beginTime = System.currentTimeMillis();
        List<Long> idList = new ArrayList<>();
        idList.add(1049858560670887937L);
        idList.add(1049858502646886401L);
        ApiResult<Map<Long, List<Long>>> highLevelProxyByIdList = userRebateService.getHighLevelProxyByIdList(idList);
        long endTime = System.currentTimeMillis();
        System.out.println("执行时间：" + (endTime - beginTime));
        System.out.println(highLevelProxyByIdList);
    }

    @Test
    public void getSubIdListByIdListApiTest(){
        List<Long> idList = new ArrayList<>();
        idList.add(1023870826627739649L);
        idList.add(1023870285352808450L);
        ApiResult<Map<Long, List<Long>>> subUserListByIdListApi = userRebateService.getSubIdListByIdListApi(idList);
        System.out.println(subUserListByIdListApi);
    }

    @Test
    public void getUserIsSubLevelTest(){
        List<Long> idList = new ArrayList<>();
        idList.add(1035826975155605581L);
        idList.add(1052826083813027841L);
        idList.add(1035826975273046092L);
        ApiResult<Map<Long, Boolean>> mapApiResult = userRebateService.getUserIsSubLevel(idList);
        System.out.println(mapApiResult);

    }
    @Test
    public void getBanRebateUserListApi(){
        QueryParamDTO dto = new QueryParamDTO();
        dto.setSiteId(1040499894897405953L);
//        dto.setUserName("fe");
//        dto.setSearchType(2);
//        dto.setBeginTime("2018-9-21 12:00:00");
//        dto.setEndTime("2018-9-21 24:00:00");
        ApiResult<PageInfo<UserRebateDTO>> banRebateUserListApi = userRebateService.getBanRebateUserListApi(dto);
        System.out.println(banRebateUserListApi);
    }

    @Test
    public void setBanRebateUserApi(){
        String userNames = "whlevi4";
        Long siteId = 1018752981500252161L;
        ApiResult apiResult = userRebateService.setBanRebateUserApi(userNames, siteId);
        System.out.println(apiResult);
    }

    @Test
    public void deleteBanRebateUserApi(){
        List<Long> ids = new ArrayList<>();
        ids.add(1023870285352808450L);
        ids.add(1023871401251581953L);
        Long siteId = 1023846199012634626L;
        ApiResult apiResult = userRebateService.deleteBanRebateUserApi(ids, siteId);
        System.out.println(apiResult);
    }
    @Test
    public void getUserRebateBy() {
        String userName = "whlevi6";
        Long siteId = 1040499894897405953L;
        Integer isDemo = 0;
        ApiResult<UserRebateDTO> userRebateBy = userRebateService.getUserRebateBy(userName, siteId, isDemo);
        System.out.println(userRebateBy);
    }

    @Test
    public void transfer(){
//        Long destId, Long sourceId, String destName, String sourceName
//                , Integer isTransfer, String managerUserName
        ApiResult<Boolean> apiResult = userRebateService.transferApi(1033349497995088036L,1033349497936367621L, "ceshi01", "zhengshi",
                1, "qaz123");

        System.out.println(apiResult);
    }

    @Test
    public void getUserProxyTypeMap(){
        List<Long> idList  = new ArrayList<>();
        idList.add(1064088203326189569L);
        idList.add(1064029151908917250L);
        Map<Long, Integer> userProxyTypeMap = userRebateInnerService.getUserProxyTypeMap(idList);
        System.out.println(userProxyTypeMap);
    }

    @Test
    public void updateType(){
        Long userId = 1032450865023762433L;
        String siteCode = "ggfndz-0";
        ApiResult<?> result = userRebateService.updateUserType(userId, siteCode);
        System.out.println(result);
    }

    @Test
    public void getRelateApiTest(){
        ApiResult<Map<String, Object>> relateApi = userRebateInnerService.getRelateApi(1113048951649529857L);
        System.out.println(relateApi);
    }

    @Test
    public void find(){
        ApiResult<UserRebateDTO> byUserIdApi = userRebateService.findByUserIdApi(1049857631770963969L);
        System.out.println(byUserIdApi);
    }
}
