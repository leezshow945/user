package com.jq.user.customer.service;

import com.jq.user.customer.dto.UserInfoDTO;
import com.jq.user.customer.entity.UserInfoEntity;
import com.liying.common.service.ApiResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoTest {

    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserInfoInnerService userInfoInnerService;

    @Test
    public void findByIdApiTest() {
        Long userId = 1032563546745552897L;
        ApiResult<UserInfoDTO> api = userInfoInnerService.getUserInfoByIdApi(userId);
        UserInfoDTO data = api.getData();
        System.out.println(data);
    }

    @Test
    public void updateUserInfoApiTest() {
        UserInfoDTO dto = new UserInfoDTO();
        dto.setUserId(1014030654208299010L);
        dto.setSex(1);
        dto.setPayPwd("1234");
        dto.setBirthday("2018-07-04");
        dto.setCardNo("");
        dto.setEmail("");
        dto.setQq("");
        dto.setProvinceId(0L);
        dto.setCityId(0L);
        dto.setRealName("测试");
        dto.setPhoto("");
        dto.setAddress("");

//        dto.setCardNo("123456789");
//        dto.setEmail("123456@qq.com");
//        dto.setMobile("12345678901");
//        dto.setPayPwd("1234");
//        dto.setQq("12345678");
//        dto.setSex(1);
//        dto.setRemark("remark test");
//        dto.setCityId(986883814002335746L);
//        dto.setWeChat("wechat Test");
        ApiResult apiResult = userInfoService.updateUserInfoApi(dto);
        System.out.println(apiResult);
    }

    @Test
    public void findUserDetailApi() {
        Long userId = 983579946843058178L;
        ApiResult<UserInfoDTO> userDetailApi = userInfoService.findUserDetailApi(userId);
        UserInfoDTO data = userDetailApi.getData();
        System.out.println(data);
    }

    @Test
    public void getUserInfoApiTest() {
        Long userId = 1019108842442616833L;
        ApiResult<UserInfoDTO> userInfoApi = userInfoService.getUserInfoApi(userId);
        UserInfoDTO data = userInfoApi.getData();
        System.out.println(data);
    }

    @Test
    public void saveUserInfoTest(){
        UserInfoEntity userInfoEntity  = new UserInfoEntity();
//        long id = IdWorker.getId();
        Long id = 1009645182934974465L;
        userInfoEntity.setId(id);
        userInfoEntity.setMobile("mobile");
        userInfoEntity.setAddress("address");
        userInfoEntity.setPhoto("photo123");
        boolean b = userInfoInnerService.saveUserInfo(userInfoEntity);
        System.out.println(b);
    }


    @Test
    public void updateUserPicture(){
        Long id = 1032876179236388865L;
        String pitureUrl = "";
        ApiResult<?> result = userInfoService.updateUserPicture(id, pitureUrl);
        System.out.println(result);
    }

    @Test
    public void findUserInfoByUserNamesApi() {
        String siteCode = "awbnwp-0";
        List<String> userNameList = new ArrayList<>();
        userNameList.add("whink1");
        userNameList.add("muxi1001");
        userNameList.add("yan007");
        ApiResult<List<UserInfoDTO>> result = userInfoService.findUserInfoByUserNamesApi(userNameList, siteCode);
        System.out.println(result);
    }

    @Test
    public void getUserInfoByIdListApiTest(){
        List<Long> id = new ArrayList<>();
        id.add(1049831682115432449L);
        id.add(1043085441469837314L);
        Long siteId = 1040499894897405953L;
        ApiResult<List<UserInfoDTO>> userInfoByIdListApi = userInfoService.getUserInfoByIdListApi(id, siteId);
        System.out.println(userInfoByIdListApi);
    }
}
