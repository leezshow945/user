package com.jq.user.customer.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.jq.framework.core.utils.JwtUtils;
import com.jq.user.constant.RedisKey;
import com.jq.user.customer.dao.SysUserDao;
import com.jq.user.customer.dto.*;
import com.jq.user.customer.entity.UserEntity;
import com.jq.user.customer.entity.UserRebateEntity;
import com.jq.user.customer.vo.UserBaseInfoVO;
import com.jq.user.customer.vo.UserInfoVO;
import com.jq.user.log.dto.LogUserDTO;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import com.liying.common.service.RPCResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.jq.user.support.SpringBeanUtil.getActiveProfile;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserInnerService userInnerService;
    @Resource
    private UserInfoInnerService userInfoInnerService;
    @Resource
    private UserRebateInnerService userRebateInnerService;
     
    private StringRedisTemplate template;
     
    private UserService userService;
    @Value("${JWTTime}")
    private Integer ttlMillis;
    @Resource
    private SysUserDao sysUserDao;
    @Test
    public void queryFilterIdsTest(){

        List<Long> ids = new ArrayList<>();
        ids.add(1035055644771094529l);
        ids.add(1035105072157634561l);
        ApiResult<List<Long>> listApiResult = userService.queryFilterIds(1035055625884205057l,ids);
        System.out.println(listApiResult.toString());

    }
    /**
     * 测试Jwt
     */
    @Test
    public void jwtTest() {
        JSONObject userJson = new JSONObject();
        userJson.put("userId", "88888");
        userJson.put("siteId", "66666");
        String token = JwtUtils.create(userJson.toString(), ttlMillis * 60 * 1000);
        System.out.println(token);
        String tokenString = JwtUtils.verify(token);
        System.out.println(tokenString);

    }

    @Test
    public void jwtTest1() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmb28iOiJiYXIiLCJpYXQiOjE1MjQ5MDIxMDR9.xKDQg1xrXAUKuHeQtKQ2Js6QyQvb3FiHjEB1mMkfYC8";
        String tokeString = JwtUtils.verify(token);
        System.out.println(tokeString);
    }

    /**
     * 测试生成验证码
     */
    @Test
    public void createSecurityCodeTest() {
        String currTime = "1";
        userInnerService.createSecurityCode(currTime);
        String value = template.opsForValue().get(RedisKey.SECURITY_CODE + currTime + ":");
        System.out.println("value:" + value);
    }

    /**
     * Author: Brady
     * Description: 测试生成今日注册会员列表
     * Date: 2018/4/28
     */
//    @Test
//    public void queryRegisterUserTest() {
//        Pagination page = new Pagination();
//        page.setSize(10);
//        page.setCurrent(1);
//        Long siteId = 0L;
//        Map<String, Object> resultMap = userInnerService.queryRegisterUser(page,siteId);
//        System.out.println(resultMap);
//    }

    @Test
    public void createPwdTest() {
        String pwd = "123456";
        System.out.println(userInnerService.createPwd(pwd));
    }

    @Test
    public void demo1Test() {
        String password = "123456";
        KeyPair keyPair = SecureUtil.generateKeyPair("RSA");
        String privateKey = Base64.encode(keyPair.getPrivate().getEncoded());
        String publicKey = Base64.encode(keyPair.getPublic().getEncoded());
//        byte[] b = Base64.decode(publicKey);
//        System.out.println("0.5");
        RSA rsa = new RSA(null, publicKey);
        String publicPwd = rsa.encryptStr(password, KeyType.PublicKey);
        // String publicP = publicPwd.toString();

        RSA rsa1 = new RSA(privateKey, null);
        String finalPwd = rsa1.decryptStr(publicPwd, KeyType.PrivateKey);

        System.out.println(finalPwd);
    }

    @Test
    public void updateBaseInfoTest() {
        UserBaseInfoVO vo = new UserBaseInfoVO();
        vo.setId(983579946843058178L);
        vo.setRealName("baseRealName");
        vo.setPassword("basePassword");
        vo.setDpcRebate(11L);
        vo.setGpcRebate(12L);
        vo.setFlcRebate(13L);
        vo.setTyRebate(14L);
        vo.setTycpRebate(15L);
        vo.setQtRebate(16L);
        vo.setLhcRebate0(10L);
        vo.setLhcRebate1(11L);
        vo.setLhcRebate2(12L);
        vo.setLhcRebate3(13L);
        Map<String, Object> map = BeanUtil.beanToMap(vo);
        userRebateInnerService.updateBaseInfo(map);

    }

    @Test
    public void qryUserInfo() {
        Long id = 983579946843058178L;
        Map<String, Object> map = userInfoInnerService.findById(id);
        System.out.println(map);
    }

    @Test
    public void updateUserInfoTest() {
        UserInfoVO vo = new UserInfoVO();
        vo.setUserId(983579946843058178L);
        vo.setSex(1);
        vo.setPayPwd("2424234");
        vo.setCardNo("234134");
        vo.setEmail("341@qq.com");
        vo.setQq("1234123");
        vo.setWeChat("wechat");
        vo.setMobile("3452435");
        vo.setProvinceId(2345234L);
        vo.setCityId(984347241859846145L);
        vo.setRemark("rerererer");
        userInfoInnerService.updateUserInfo(vo);
    }


    @Test
    public void getRebate() {
        Long id = 983579946843058178L;
        UserRebateEntity rebate = userRebateInnerService.getRebate(id);
        System.out.println(rebate);
    }

    @Test
    public void getLevelTest() {
        Long siteId = 1040499894897405953L;
        Map<String, Object> map = userRebateInnerService.getLevel(siteId);
        System.out.println(map);
    }

    @Test
    public void encodeTest() {
        String name = "qqqqqqqqqqqqqqqqqqqq";
        String encode = Base64.encode(name);
        System.out.println("加密后：" + encode);
        String dencode = Base64.decodeStr(encode);
        System.out.println("解密后：" + dencode);
    }


    @Test
    public void getSubUserListTest() {
        Long userId = 983579946843058178L;
        String userName = null;
        String startTime = "2018-4-19 00:00:00";
        String endTime = "2018-4-27 23:59:59";
        Integer status = null;
        int current = 1;
        int size = 20;
        String orderByField = null;
        boolean isAsc = false;
//        List<UserWrapper> subUserList = userRebateInnerService.getSubUserList(userId, userName, status, startTime,
//                endTime, current, size, orderByField, isAsc, null);
//        System.out.println(subUserList);
    }

    @Test
    public void findUserDetail() {
        Long userId = 983579946843058178L;
        Map<String, String> userDetail = userInfoInnerService.findUserDetail(userId);
        System.out.println(userDetail);
    }

    /**
     * Author: Brady
     * Description:通过RSA解密，获得MD5密码
     * Date: 2018/5/3
     */
    @Test
    public void getMD5PwdByRSATest() {
        String password = "11D5BE0ACB3AB49C02E767B0AE777315C245CA2FD6E9FBDC28A72AE3CBE338E7B5D08C6F39C2309873EC4F33C5E122EDDCFE73D0160317071E3BAEB00303C0F1FF21ACD0B1598F8FA98F9D827D59AF044B2F7DE2F383F5E117D40584E6312A13EEA0F74CB96D7917FF0CAE1E2B6052219DA4ABD79D267D5D6D52F9D10B842744";
        String pwd = userInnerService.getMD5PwdByRSA(password);
        System.out.println("password:" + pwd);
    }

    /**
     * Author: Brady
     * Description: 测试更新最后游戏种类和时间
     * Date: 2018/5/9
     */
    @Test
    public void updateGameCategoryAndTimeTest() {
        Long userId = 994105621176238081L;
        String category = "六合彩";
        ApiResult apiResult = userService.updateGameCategoryAndTimeApi(userId, category);
        System.out.println(apiResult.getMessage());
    }

    /**
     * Author: Brady
     * Description: 查询用户信息
     * Date: 2018/5/14
     */
    @Test
    public void findUserInfoByIdTest() {
        Long userId = 666666666666666666l;
        ApiResult apiResult = userService.getUserDTOById(userId,0L);
        System.out.println(apiResult.getData().toString());
        System.out.println(apiResult.getMessage());
    }

    @Test
    public void getUserNameByIdTest() {
        Long userId = 11111L;
        String userName = userService.getUserNameById(userId).getData();
        System.out.println(userName);
        System.out.println(userService.getUserNameById(userId).getMessage());
    }

    /**
     * Author: Brady
     * Description: 测试rpc会员登录接口
     * Date: 2018/5/18
     */
    @Test
    public void loginSubmitApiTest() {
        UserModelDTO userModelDTO = new UserModelDTO();
        userModelDTO.setUserName("zhushen1");
        userModelDTO.setPassword("gXCzO8eA9pgPJ0/UDmJvAX5KVWwypaqGMhvhe9wJaeoXIY sA92h1bPB9c/J4mS1SIpgQYg 6UOnw8SK42WvEJId P4AKO2WpZw2FUn8YbntlswiDM Y9DUjVDSA/MuYfUGxsu9vZVPWZugJ0CwXb1UAbv6GTwfbsIJOJ3WYCn6ejwoU1QGhkLmuBvnU H/gwK MTBaaUKo3Bs13iJUe b65x16niex9CX30FaE6xtpwz2WFENMPVSF/2norLbxsWb28lZYx7jrngfewczn2Qj4 l9fvKgM9/jOq lVLCgxJwOf0DgpgEjMVqtbyeWUHGWMTNbQveGgjghmfAAehaA==");
        userModelDTO.setPlatformType(2);
        userModelDTO.setCurrTime("4e69c8f7eec9aabf3181f58724004d14");
        userModelDTO.setVerifyCode("g4bd");
        userModelDTO.setSiteId(1031885064860962817L);
        ApiResult api = userService.loginSubmitApi(userModelDTO,"","","");
        System.out.println(api.getMessage());
    }

    /**
     * Author: Brady
     * Description: 测试rpc会员注册接口
     * Date: 2018/5/18
     */
    @Test
    public void registerUserApiTest() {
        UserModelDTO user = new UserModelDTO();
        user.setUserName("test00111");
        user.setPassword("XfbXSg/65pJLDHippOHAf0NSrXmsHw5Oz0FWB/PIswsxXNSQgEVSpzFDavuKz/cqY9UC8b8hpIq/\n" +
                "hdNIAWIVtsCZOZgHU4e8sH+12S1CuqIjjKaOgFLOgIG29XrztEMDRwswc/nm7eNRm+m5IuWwPKBD\n" +
                "Dn4s/uyo94yj3vkdAwa4szCxedB6t4Xjcm4kEYNIYrOvTy77G/Nqi49iRuOI+H6Zg5yF/U6vcY7r\n" +
                "KXYUbhVvWjN4ff76jQ+kvIhomDPFRbAw/io5PGv1KqYpCneQ7p3lSH/VlRpRcrkLmaWW++VTLUVo\n" +
                "1S17G3a3WNkfBEe9ISjVkfe8ptuF2gpsUsaDsA==");
        user.setPlatformType(1);
        user.setCurrTime("081bdf3283d826dec43c2eb51144ee88");
        user.setVerifyCode("5ppr");
        user.setSiteId(1018752981500252161L);
        user.setReferCode("");
        String crateByUserName = "";
        String ip ="192.168.10.101" ;
        String url = "http://home.liying.com";
        user.setCreateByUserName(crateByUserName);
        user.setIp(ip);
        user.setUrl(url);
        UserRegConfigDTO userRegConfigDTO = new UserRegConfigDTO();
        System.out.println(userService.registerUserApi(user).getMessage());
    }



    /**
     * Author: Brady
     * Description: 注册试玩帐户
     * Date: 2018/5/18
     */
    @Test
    public void registerDemoUserApiTest() {
        String ip = "192.168.11.250";
        Long siteId = 1040499894897405953L;
        Integer platformType = 1;
        ApiResult api = userService.registerDemoUserApi(ip, siteId, platformType);
        System.out.println(api.getMessage());
    }

    /**
     * Author: Brady
     * Description: 测试生成验证码
     * Date: 2018/5/21
     */
    @Test
    public void createSecurityCodeApiTest() {
        String current = "1";
        System.out.println(userService.createSecurityCodeApi(current).getData());
    }

    /**
     * Author: Brady
     * Description: rpc测试生成rsa公钥
     * Date: 2018/5/30
     */
    @Test
    public void getRsaPublicKeyApi() {
        ApiResult api = userService.getRsaPublicKeyApi();
        System.out.println(api.getMessage());
        System.out.println(api.getData());
    }

    /**
     * Author: Brady
     * Description: 密码测试
     * Date: 2018/5/31
     */
    @Test
    public void passwordTest() {
        String privateRSAKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMTNMXRmEITFaafbu9VrVKmq4UH9I2D+g8HIzFzB4oILkY3w6GVRVMsmwJRJbr/GaD1hzrmbm8yVULwlJmnSJaZ94XYgRIrCq3Hnbiq5peMqR65DXV7Etc9WFGR2HddsM0j5YU9Hvorwqjo7XYzUbQzugOrKbO4YJcdnJ51SOLz/AgMBAAECgYEAkHwhadahHAkfh8hP/+D72FEHbf4eIlDZhMUaHKOP23AI3c/XnsL2YOvDiVrcP1m4IBCVyDkRSkL5chhIOnq5flZci5I9PB/TEfJjDOg0KuMqvWzZUTISeEF9ROBE0YNf7z670HDkAh5Rc9dL1duiRZZixHGbY7r8Ca9amlfxNakCQQD3UKy/SxDkciTSoBFCyvXu42+aNrvPHwpccqy2bDSAXcgCPJmMG2jbVSNdMupv+0J0/16RrlyA9oxP026lg5K7AkEAy7ZoquvKIPg0dz8fhItua1vQVdOby7ZfOXXB1BWBRdcIZbnkk7QvgWP0RlRSAEoB+bmXIzPGLANeoskCDwIEjQJAZpsr8Hl+WqkYqeILB0EZY7EsfjizFq10KQLSsSSXa6J89qCZu80rZcQe1e3n4enGmq/QVnSWoFKpx/yabUT3PQJAUQXB54lUoXxW0b7neD5EsqqX2CsL4iAtC5uMdh//hJmcG2muQhj+dAYfWAGdg8PruTlZpYSoI0VL1I8CJJn22QJBAIkjXWTK6AfE3lKcTI+6fQ4V5in+12YBSvFqujTPh9TMbwpMMNJA7DM3BDqDYoPg3pcagJq2S1JWYdE6AnJ5mII=";
        RSA rsa = new RSA(privateRSAKey, null);
        String password = "vMWU6LAmBQqnuyglm38PMkmanyxmpfOc/pqPwk37HrCvOCSkRUNzhaxsmGbNvJJxv+2g+nMNfSp7EJZlTKrtd7LsZ2eHLKZekBCgMxeYe3+cekPnn5v6YZEcqtqoYwzQxADJ4KLqbwZBzZzEiztMQDgwXfLdEf93ct8pNClHWLQ=";
        String MD5Pwd = rsa.decryptStr(password, KeyType.PrivateKey);
        System.out.println(MD5Pwd);
    }

    @Test
    public void testPassword() {
        String MD5Pwd = "e10adc3949ba59abbe56e057f20f883e";
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDHdRXTDkSKzo5rB1cxDqlIMYcNba8TyjqFY82cY3L246t6n/gE9RRYXSZqBHmpP+FRMIGv+Vmi18VGVAjTPweyd26jo4OqsmNX9/XXXBPlFJxPOpnWbj6r7OhUstILFXqkDocw9+iIkSs3FfTuwtqx7AcjT4Ygps3TakM1gi0faQIDAQAB";
        RSA rsa = new RSA(null, publicKey);
        System.out.println(rsa.encryptStr(MD5Pwd, KeyType.PublicKey));
    }

    @Test
    public void getBySiteCodeApiTest() {
        String siteCode = "liying";
        ApiResult<List<UserLevelDTO>> apiResult = userService.getUserLevelBySiteCodeApi(siteCode);
        List<UserLevelDTO> data = apiResult.getData();
        System.out.println(data);
    }

    /**
     * Author: Brady
     * Description:测试获取用户状态码
     * Date: 2018/6/15
     */
    @Test
    public void getUserStatusMapApiTest(){
        ApiResult api = userService.getUserStatusMapApi();
        System.out.println(api.getData());
    }

    /**
     * Author: Brady
     * Description: 测试获得获得用户初始化状态
     * Date: 2018/6/15
     */
    @Test
    public void getUserListParamApiTest(){
        System.out.println(userService.getUserListParamApi(986810793321410512L));
    }

    /**
     * Author: Brady
     * Description: 测试RPC根据List<userId>查询用户基本信息
     * Date: 2018/6/20
     */
    @Test
    public void queryUserDTOByListIdApiTest(){
        List<Long> idList = new ArrayList<>();
        idList.add(1006470027893886978L);
        idList.add(1006467666705747969L);
        ApiResult api = userService.queryUserDTOByListIdApi(idList,null,null);
        System.out.println(api.getData().toString());
    }

    /**
     * Author: Brady
     * Description: 测试根据用户名查询用户
     * Date: 2018/6/19
     */
    @Test
    public void getUserByUserNameApiTest(){
        Long siteId = 1007161902619004929L;
        String userName = "T08595";
        ApiResult api = userService.getUserByUserNameApi(userName,null);
        if(RPCResult.checkApiResult(api)) {
            System.out.println(api.getData().toString());
        }
    }

    @Test
    public void getUserBaseInfoApiTest(){
        Long siteId = 1050624121944207361L;
        Long userId = 1065526999741689857L;
        ApiResult<UserDTO> userBaseInfoApi = userService.getUserBaseInfoApi(siteId, userId);
        UserDTO data = userBaseInfoApi.getData();
        System.out.println(data);
    }

    /**
     * Author: Brady
     * Description: 测试RPC修改密码
     * Date: 2018/6/20
     */
    @Test
    public void updateUserPwdApiTest(){
        String oldPwd = "OIncudgxuk0dq/peszUZIvEmCUqIwZwSxrWUTMTgvqhosc0SAp7f3TYeVdTdIDSH9gwwFiOWkulO\nJIiqGu/URZhkO35oRRl2QD47TEQFu+UIjGzuevPPQQAWC04yx/ZbgQunWa71LsYHleByN/dDWX8K\n4tI40ZRviUOqn6FfskwXfLgCWdPParU9ahkJ52TG/JRs5xICvc9xVIE6MdZU1ZFZEQWNhKzKow2E\n4+vOUAUv74lNH3lonYmpWnwncIG0IL2lcGEE3OrtOuIkH7EFCv+ytvHZKgG/dFQ9IHh5dNTzefqw\n5E+7NjqT8PX00YUGjZcIWqNiSHSpWPaNiLAIqw==";
        String newPwd = "MCojoLSqDJmFq8Ly6HZtfFK8pAlT9yJ6npQF8MiNA9Q7gLApn9/ATUoGfkobBt+ONAO89cKfBRkC\npch89psJe11uld8zaG05d1KDKakwxjlbtb6D5aMv0cHCFEH9MEuE3cSsWpizXSVE0ESwT5S2QdhY\nLBFYaaJqZSeVfHy11YlwX8eJ+SQ4R4sznZYyOtFV/KFLHyEHj4r+sySvGvGd9+7L188xZlHinQMj\ny+CWllk51K76MCFR5ZxkKNK0uWvuXGNQ/R9/8CD0EjRTkbm3FJ1ugkikLdA83KRIASWXwZ+XCq4H\nNGRtc6XDb8PDW8Ti49JcVXH0Wc/BA/16SrPuQw==";
        Long userId = 1006467061593509890L;
        Long siteId = 1006467073941540866L;
        Integer platformType = 1;
        String token = "c2ff5160f3bf4c78b3bb77eeb4eeb71a";
        String url="";
        String ip ="";
        ApiResult api = userService.updateUserPwdApi(userId,oldPwd,newPwd,siteId,platformType,token,ip,url);
        System.out.println(api.getMessage());
    }

    @Test
    public void updateUserPayPwdApiTest(){
        String oldPwd = "GZ2XQd5IXjAlZhZCSfQOOeIosigTSH5js+hvFm2YSEoX4jCBs2pz3QRUdLA8vQJ9Z1xwJJHMHj7p\nx7xcOREsbT8BcaEdLyoTyjxzr8QPbNRSw7A2lpb36+HBOS7UAO+JxHBuQZ2ZVIMjt2VTD4E3yNal\nlRisIAoXMm9tpuwS1C9GWjwZuTmBwhfs0dGlwDZoAcixU6y55hApFaTVmwsk8b7ny/kjkVv2AyKW\nYZcZxdaNvOMP+/dOwh0JIMnUMldM0vHxJW5fBr2GlK3WE5/Leg9zdFPRxLRVBssNSY01uaTpUgRK\n8DbfWKjpbOtgD5yta1bbCrfHavSAYAdQOyCuaw==";
        String newPwd = "Bd77lQg/4vdV8u5gn744bVLj3oJUbC+Uq4lP2c3q8q2zVVbOGJI3ePW+IqeyowK3boYd/a7zAuKh\nATHjPfOz4m13mNYB5l6PUZvL+f1q/sSs5h94ISOdVrgSYuDQJ8f63Jursj8rM2I4F90+QRaGU0ok\nuGKYpy8P5xASDpk+ALq2gS/3kEwhw9eVw6QqXhONmiam8dK4/xAdPOXcDe4nKIylTIaRaFbA8wMA\nEgChvOr3Z63XUEyHhVE8iIS0WhNM7DduaBcjrvliLcWNfX7KQXwclb2fNZgbWKA2R4r50Kbe5ot1\nDbkpNfErr2IlFaDDICX36wXXyL1yHVvRdS8UFQ==";
        Long userId = 1006467061593509890L;
        Long siteId = 1006467073941540866L;
        Integer platformType = 1;
        String url="";
        String ip ="";
        ApiResult api = userService.updateUserPayPwdApi(userId,oldPwd,newPwd,siteId,platformType,ip,url);
        System.out.println(api.getMessage());
    }

    @Test
    public void getUserByUserNameTest(){
        Long userId = 1049857631770963969L;
        String userName = "zzj115";
        ApiResult<UserDTO> userByUserName = userService.getUserByUserName(userId, userName);
        UserDTO data = userByUserName.getData();
        System.out.println(data.getUserName());
        System.out.println(data.getPath());
        System.out.println(data.getAmount());
    }



    @Test
    public void getSubUserIdLikeApiTest(){
        Long userId = 1052766026828148738L;
        String userName = "levi";
        ApiResult<List<Long>> subUserIdApi = userService.getSubUserIdApi(userId, userName, true);
        System.out.println(subUserIdApi);
    }

    /**
     * Author: Brady
     * Description: 测试根据上级用户名称获取等级
     * Date: 2018/6/23
     */
    @Test
    public void getHighAccountNameToLevelPathApiTest(){
        ApiResult<List<Map<String, Object>>> highAccountNameToLevelPathApi = userService.getHighAccountNameToLevelPathApi(1007161902619004929L, "", "");
        System.out.println(highAccountNameToLevelPathApi);
    }

    @Test
    public void payTest(){
        String payPwd = userInnerService.createPwd("4321");
        String md5PayPdw = userInnerService.getMD5PwdByRSA(payPwd);
        System.out.println(md5PayPdw);
    }

    @Test
    public void checkOutUserExcelTest(){
        QueryParamDTO dto = new QueryParamDTO();
//        dto.setLoginName("zzj1991");
//        dto.setSearchType(0);
//        dto.setRebateType("gpc_rebate");
//        dto.setMinRebate(1300L);
//        dto.setMaxRebate(1400L);
        dto.setSiteId(1040499894897405953L);
        dto.setSiteCode("awbnwp-0");
        dto.setSearchType(4);
        dto.setLoginName("277530599");
        ApiResult<List<UserDTO>> listApiResult = userService.checkOutUserExcel(dto);
        List<UserDTO> data = listApiResult.getData();
        System.out.println(data);
    }
    @Test
    public void enableOrDisEnableUserTest(){
        Long userId = 1013697010760876034L;
        Integer status = 10;
        ApiResult<Boolean> booleanApiResult = userService.enableOrDisEnableUser(userId, status);
        Boolean data = booleanApiResult.getData();
        System.out.println(data);
    }

    @Test
    public void cleanUserApiTest(){
        Long siteId = 1016652747843932162L;
        ApiResult apiResult = userService.cleanUserApi(siteId);
        System.out.println(apiResult);
    }

    @Test
    public void Test(){
        Long siteId = 1011868438377877506L;
        Long defaultSysManId = sysUserDao.getDefaultSysManId(siteId);
        System.out.println(defaultSysManId);
    }

    @Test
    public void setPayPwd(){
        Long userId = 1014030654208299010L;

        String payPwd = "123456";
        String pwd = userInnerService.createPwd(payPwd);
        ApiResult<Boolean> booleanApiResult = userService.setPayPwd(userId, pwd);
        System.out.println(booleanApiResult);
    }

    @Test
    public void getTokenApiTest(){
        ApiResult apiResult = userService.getTokenApi("99fb4f5beee54ccbad3a620b4f70f7d3");
        if(RPCResult.checkApiResult(apiResult)){
            System.out.println(apiResult.getData());
        }
    }

    @Test
    public void queryUserDTOBySiteCodeApiTest(){
        ApiResult<List<UserDTO>> listApiResult = userService.queryUserDTOBySiteCodeApi("feng-7");
        if(RPCResult.checkApiResult(listApiResult)){
            System.out.println(listApiResult.getData());
        }
    }

    @Test
    public void queryUserLikeUserNameApiTest(){
        ApiResult<List<UserDTO>> brady = userService.queryUserLikeUserNameApi("brady", "feng-7");
        if(RPCResult.checkApiResult(brady)){
            System.out.println(brady.getData().toString());
        }
    }

    @Test
    public void verifyPayPwdApiTest(){
        Long siteId = 1006467073941540866L;
        Long userId = 1006467061593509890L;
        String updateUserName = "admin1" ;
        String payPwd = "ZOfsVA8THTP02SxvoxZgV3Upx2vr0OREebPwltMSRJ5LrFrul2vdObBTOL/65/8pgfC4zggSTLDw\n" +
                "Og+4d5hdrdj0spjwRnNkKtswoAt1x6AQqF1yJbp6ysoxOpvzTR4VXdL+CCGuUZHmdx9cJpCO6iE9\n" +
                "f+ZRa/bDe0YIsMi/5IT4dIVtb6diSEqhXEiZbd7uN9a+miEgRF6tNOSVvbkpFH+xqRqhQrIxk8Lq\n" +
                "57uct/c9R4HcmhzVfD07Ae1JYE/8Wn8Aa8Zpzy5AWekKdwADr7TQ1Mn3Fdr+gPLPbyj3+h5zDp1C\n" +
                "ZdQPJteSc+kaQthBktKltCDPVLBSTFfoRzoXog==";
        ApiResult apiResult = userService.verifyPayPwdApi("pay.card.user.pay.password.error.userId:",siteId, userId, updateUserName, payPwd, 1, "", "");
        if(RPCResult.checkApiResult(apiResult)){
            System.out.println(apiResult.getMessage());
        }
    }

    @Test
    public void getCountOfRegisterByTimeAndRegSourceApiTest(){
        ApiResult<Map<String, Integer>> countOfRegisterByTimeAndRegSourceApi = userService.getCountOfRegisterByTimeAndRegSourceApi("2018-07-29 00:00:00", "2018-07-31 00:00:00", "feng-7",null);
        if(RPCResult.checkApiResult(countOfRegisterByTimeAndRegSourceApi)){
            System.out.println(countOfRegisterByTimeAndRegSourceApi.getData().toString());
        }
    }

    @Test
    public void queryUserByListIdApiTest(){
        List<Long> idList = new ArrayList<>();
        idList.add(1007069040359346178L);
        idList.add(1007088586227027970L);
        idList.add(1007089900801593346L);
        ApiResult<List<UserDTO>> listApiResult = userService.queryUserByListIdApi(idList);
        if(RPCResult.checkApiResult(listApiResult)){
            System.out.println(listApiResult.getData().toString());
        }
    }

    @Test
    public void saveUserTest(){
        UserEntity user = new UserEntity();
        user.setUserName("123456");
        user.setSiteCode("feng-7");
        user.setSiteId(1018752981500252161L);
        user.setUserRankId(1018752981500252161L);
        user.setId(IdWorker.getId());
        UserEntity userEntity = userInnerService.saveUser(user,null,1018752981500252161L,0,null);
    }

    @Test
    public void getOnlineCountApiTest(){
        ApiResult<Integer> onlineCountApi = userService.getOnlineCountApi(1018752981500252161L,null);
        if(RPCResult.checkApiResult(onlineCountApi)){
            System.out.println(onlineCountApi.getData().toString());
        }
    }

    @Test
    public void Test1() {
        Boolean verifyCodeFlag = false;
        String active = getActiveProfile();
        if(active.equals("dev")){
            verifyCodeFlag = true;
        }
        System.out.println(verifyCodeFlag);
    }

    @Test
    public void queryUserByIpApiTest(){
        ApiResult<List<UserDTO>> listApiResult = userService.queryUserByIpApi("122.55.239.145", "vjoptr-0");
        if(RPCResult.checkApiResult(listApiResult)){
            System.out.println(listApiResult.getData().size());
            System.out.println(listApiResult.getData().toString());
        }
    }
    @Test
    public void getSiteUserPathLengthApi(){
        Long siteId  = 1033349363874201601L;
        ApiResult<Map<Integer, Object>> siteUserPathLengthApi = userService.getSiteUserPathLengthApi(siteId);
        System.out.println(siteUserPathLengthApi);
    }



    @Test
    public void verifyUserIsDemoByIdApiTest(){
        ApiResult<Boolean> apiResult = userService.verifyUserIsDemoByIdApi(1040499894897405953L, "shon01255");
        if(RPCResult.checkApiResult(apiResult)){
            System.out.println(apiResult);
        }
    }

    @Test
    public void logOutFrontUserTest(){
        ApiResult apiResult = userService.logOutFrontUser(1042677495371984898L, 11);
        if(RPCResult.checkApiResult(apiResult)){
            System.out.println(apiResult.getMessage());
        }
    }

    @Test
    public void kickOutUserApiTest(){
        ApiResult apiResult = userService.kickOutUserApi(1032875802931822594L);
        if(RPCResult.checkApiResult(apiResult)){
            System.out.println(apiResult.getMessage());
        }
    }

    @Test
    public void queryRegisterUserByProxyLineApi(){
        ApiResult<PageInfo<Map<String, Object>>> pageInfoApiResult = userService.queryRegisterUserByProxyLineApi(1033349497986699368L,"2018-01-01 00:00:00","2018-10-10 00:00:00",1,20);
        if(RPCResult.checkApiResult(pageInfoApiResult)){
            System.out.println(pageInfoApiResult.getData().getContent().toString());
        }
    }

    @Test
    public void getIdListBySiteCodeAndIsDemoApiTest(){
        ApiResult<List<Long>> idListBySiteCodeAndIsDemoApi = userService.getIdListBySiteCodeAndIsDemoApi("diaeyp-4", 2);
        if(RPCResult.checkApiResult(idListBySiteCodeAndIsDemoApi)){
            System.out.println(idListBySiteCodeAndIsDemoApi);
        }
    }
    @Test
    public void setEncryptedList(){
    	//设置密保
    	List<UserEncryptedDTO> dtoList = new  ArrayList<UserEncryptedDTO>();
    		UserEncryptedDTO dto1 = new UserEncryptedDTO();
    		dto1.setUserId(1077766744318738433L);
    		dto1.setQuestion("您的小学班主任是谁？");
    		dto1.setResult("张三");
    		UserEncryptedDTO dto2 = new UserEncryptedDTO();
    		dto2.setUserId(1077766744318738433L);
    		dto2.setQuestion("您的初中班主任是谁？");
    		dto2.setResult("李四");
    		UserEncryptedDTO dto3 = new UserEncryptedDTO();
    		dto3.setUserId(1077766744318738433L);
    		dto3.setQuestion("您的高中班主任是谁？");
    		dto3.setResult("王五");
    		dtoList.add(dto1);
    		dtoList.add(dto2);
    		dtoList.add(dto3);
	    ApiResult<?> result =  userService.setEncryptedApiResult(dtoList);
	    System.out.println(result);
    }
    
    @Test
    public void verifyUserName(){
	    ApiResult<?> userIdApiResult = userService.verifyUserName("dedede1", 1040499894897405953L, "6030", "771e837cb0cc09a9d4eea4d42ca9e593");
	    System.out.println(userIdApiResult);
    }
    
    
    
    @Test
    public void verifyPayPwd(){
    	Long userId = 1077766744318738433L;
    	Long siteId = 1040499894897405953L;
    	LogUserDTO dto = new LogUserDTO();
    	dto.setUserId(userId);
    	dto.setSiteId(siteId);
    	dto.setPlat(1);
    	dto.setLoginIp("127.0.0.1");
    	dto.setLoginUrl("test.caishen.38c8.com");
	    ApiResult<?> isPass = userService.verifyPayPwdApi("DAZ6rFCjKoykGYw62cPg6RU6zRllVAbRjtvWAagfOuU1y/Pd0pfMHVsr3ZJxBwL7N24TGxBeFw/0tB9IrTwNIwPlf8HMek9J4pYBL7REhTy3Q9e8b397A2hC6r5BAHmlawEz3WoMFrvgVWCfpf4vLyUgZQK2PL5+uwh0UlYAzY/KzxC6kMAN2Df1i139jdP9N+oCPXiykOsvhiJ4agXuolQIRFOOyqPElN7Vdoznng04yG78LNyAOLp1flWnUecLlv3qq7+kI/GhnH7NqOkmBOCi8Giil99AWj41QPBTAW3xGYcbyG+DdMtFqGCWptqYe1u5oexWz9o1qToNUnS3VA==",dto);
	    System.out.println(isPass);
    }
    
    @Test
    public void updatePwdByVerify(){
    	Long userId = 1077766744318738433L;
    	Long siteId = 1040499894897405953L;
	    ApiResult<?> isPass = userService.updatePwdByVerify(userId,"FMPPLdiuO3h3UdjKu+nmjfMD3vTNhu49gj1J3rWRkfpry0J4tt33K4rgHVwogVtoTiexyd9t2h0X/GuVmIOMR64GTr+V9Q4p6JsPcDuDodjmCtSWFWfn/bc7qbN+G6P4P/abdQRA5/pi34Bx5gx3pxiGC17BtRynB5/c2twzuqeXrqdXyJBHI68gp0UgivVbq5moFWQHgXo0PPb7BDyee1Bc91j6cjaRsybUdDJj24tEa0SIrg8dEngi1gO4WDEaHMR2PDN2rH3Yvw863kbb3zufCJqqcf+x/smxPZUIVpM5jlJDCVZNc8FrFLKyvzHZbqXIQV0kwiZdtj1m6GQWgQ==");
	    System.out.println(isPass);
    }
    @Test
    public void isSetPayPwd(){
    	Long userId = 1077766744318738433L;
    	Long siteId = 1040499894897405953L;
    	ApiResult<Boolean >result = userService.isSetPayPwdByUserId(userId, siteId);
    	System.out.println(result);
    	
    }
    @Test
    public void queryEncryptedById(){
    	Long userId = 1077766744318738433L;
    	Long otherUserId = 1080756651366612994L;
    	ApiResult<List<UserEncryptedDTO>>result = userService.queryEncryptedById(otherUserId);
    	System.out.println(result);
    }
    
    @Test
    public void verifyEncrypted(){
    	List<UserEncryptedDTO> dtoList = new  ArrayList<UserEncryptedDTO>();
		UserEncryptedDTO dto1 = new UserEncryptedDTO();
		dto1.setId(1082845858720030721L);
		dto1.setUserId(1077766744318738433L);
		dto1.setQuestion("您的小学班主任是谁？");
		dto1.setResult("张三");
		UserEncryptedDTO dto2 = new UserEncryptedDTO();
		dto2.setId(1082845862113222657L);
		dto2.setUserId(1077766744318738433L);
		dto2.setQuestion("您的初中班主任是谁？");
		dto2.setResult("李四");
		UserEncryptedDTO dto3 = new UserEncryptedDTO();
		dto3.setUserId(1077766744318738433L);
		dto3.setQuestion("您的高中班主任是谁？");
		dto3.setResult("王五");
		dto3.setId(1082845862956277761L);
		dtoList.add(dto1);
		dtoList.add(dto2);
		dtoList.add(dto3);
    	ApiResult<?> result = userService.verifyEncryptedApiResult(dtoList,  0,
                1077766744318738433L,1,1040499894897405953L,"127.0.0.1","test.caishen.38c8.com");
    	System.out.println(result);
    }
    
    @Test
    public  void updateEncrypted(){
    	List<UserEncryptedDTO> dtoList = new  ArrayList<UserEncryptedDTO>();
		UserEncryptedDTO dto1 = new UserEncryptedDTO();
		dto1.setUserId(1077766744318738433L);
		dto1.setQuestion("您的小学班主任是谁？");
		dto1.setResult("张三");
		UserEncryptedDTO dto2 = new UserEncryptedDTO();
		dto2.setUserId(1077766744318738433L);
		dto2.setQuestion("您的初中班主任是谁？");
		dto2.setResult("李四");
		UserEncryptedDTO dto3 = new UserEncryptedDTO();
		dto3.setUserId(1077766744318738433L);
		dto3.setQuestion("您的高中班主任是谁？？");
		dto3.setResult("王五");
		dtoList.add(dto1);
		dtoList.add(dto2);
		dtoList.add(dto3);
    	ApiResult<?> result =userService.updateEncrypted(dtoList,1077766744318738433L);
    	System.out.println(result);
    }

    @Test
    public void verifyLoginPwd(){
        String password = "Fm87fEP+gAZCgHSx6nEV0UJhRtLoETpoX4HbsHNUFXd2/hiQ+8kq8EaYz8EOQ3JLZwJxjE1YKm55Nq25S/YMZbpz/0yponECxZfP9REhXgzMNfVyBU+cItTCTCNy8TDVr2WaKl3Cy6lUSAINYkhtstJoj7MYCigPLd7pJH5F7Knm22sZ2H1sPUXT+B9ZF6zYQYNK/Jb+jtpZGOQDn7QKseeokS7ikkFpWYf9Ey0qcusqUtYfyqr0mjODv3uUEyp9jMS8NbVyRHPkgBV5VLSAUvbcjFSAWL87h/irl0vNQR5oBCHQhdjiCBqDKwlBlDQy8Dij1DLlHa69pVu0QP5F4A==";
        LogUserDTO dto = new LogUserDTO();
        dto.setUserId(1077766744318738433L);
        dto.setSiteCode("awbnwp-0");
        dto.setPlat(1);
        dto.setLoginIp("127.0.0.1");
        dto.setLoginUrl("test.caishen.38c8.com");
        dto.setSiteId(1040499894897405953L);
        ApiResult result= userService.verifyLoginPwd(password,dto) ;
        System.out.println(result);

    }
}
