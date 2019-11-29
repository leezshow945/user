package com.jq.user.customer.service;

import cn.hutool.core.codec.Base64;

import com.jq.user.customer.dto.*;
import com.jq.user.customer.entity.SysUserEntity;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Brady
 *         Descript:
 *         Date: 2018/5/2
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysUserServiceTest {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysUserInnerService sysUserInnerService;




    /**
     * Author: Brady
     * Description: 测试更新管理员信息
     * Date: 2018/5/8
     */
    @Test
    public void updateSysUserInfoTest() {
        SysUserEntity sysUser = new SysUserEntity();
        sysUser.setId(991526490668367874L);
        sysUser.setUserName("123");
        Boolean flag = sysUserInnerService.updateSysUser(sysUser);
        System.out.println(flag);
    }

    /**
     * Author: Brady
     * Description: 根据userId删除管理员用户
     * Date: 2018/5/8
     */
    @Test
    public void deleteSysUserByUserId() {
        Long userId = 1112L;
        Long siteId = 986810793321410512L;
        Boolean flag = sysUserInnerService.deleteSysUserByUserId(userId, siteId);
        System.out.println(flag);
    }

    /**
     * Author: Brady
     * Description: 管理员禁用
     * Date: 2018/5/9
     */
    @Test
    public void sysUserDisabledTest() {
        Long userId = 1112L;
        String userName = "admin1";
        Boolean flag = sysUserInnerService.sysUserDisabled(userId, userName);
        System.out.println(flag);
    }

    /**
     * Author: Brady
     * Description: 测试根据deptId查询部门管理员列表
     * Date: 2018/5/9
     */
    @Test
    public void queryDeptUserTest() {
        SysUserDTO sysUserDTO = new SysUserDTO();
        sysUserDTO.setDeptId(1111L);
        ApiResult<PageInfo<SysUserDTO>> sysUsersByDeptId = sysUserService.getSysUsersByDeptId(sysUserDTO);
        System.out.println(sysUsersByDeptId.toString());
    }

    /**
     * Author: Brady
     * Description: 测试根据userId判断该用户是管理员还是会员
     * Date: 2018/5/9
     */
    @Test
    public void judgeUserRoleTest() {
        Long userId = 989394742339330049L;
        ApiResult apiResult = sysUserService.judgeUserRole(userId);
        Map<String, Object> resultMap = (Map<String, Object>) apiResult.getData();
        System.out.println(resultMap);
        System.out.println(apiResult.getMessage());
    }

    /**
     * Author: Brady
     * Description: 测试首次登录修改密码
     * Date: 2018/5/11
     */
    @Test
    public void resetPwdTest() {
        String userName = "siteUser";
        Long userId = 991521777029783553L;
        Boolean flag = sysUserInnerService.resetSysUserPwd(userName, userId,"","");
        System.out.println(flag);
    }

    /**
     * Author: Brady
     * Description: 测试根据id获得用户名
     * Date: 2018/5/17
     */
    @Test
    public void getUserNameByIdTest() {
        Long userId = 991521777029783553l;
        System.out.println(sysUserService.getUserNameById(userId).getData());
        System.out.println(sysUserService.getUserNameById(userId).getMessage());
    }

    /**
     * Author: Brady
     * Description: 生成token
     * Date: 2018/5/22
     */
    @Test
    public void loginSubmitApiTest() {
        UserModelDTO userModelDTO = new UserModelDTO();
        userModelDTO.setUserName("whBrady");
        userModelDTO.setPassword("nLAz+Z2p5mf260K8iIjpC68bLIjcbfH0u+6GxwN8uCkG/y19mnIcNAzJlyoL17W6LLg00fZ9Rbf3\nYRWs/gO51LSZRlD8wED/2EBMQGRoTnbVOPEs2Fv5ZbQWdZ9PcbaWxvb3wkujzb/5Z3TtCiWt89ec\nX8edLGLyNTyd5HZFAtX4UTHhI1cr3kfnv6a0RpAK5IYh8RbpaFJTAcCva4q3BXAwR+Q3lz6m1XNN\nM3pzjkvqy6TDU7qqdt4lCEnmAaUx1ecQXtDJW/tQPy6ZfGlh14SMVgk2pXekqWUz8heBrcSdEcPE\nM51v6/U5W2HJndpq7uTf6anqoUMhIVS8Ffv1xQ==");
        userModelDTO.setPlatformType(1);
        ApiResult api = sysUserService.loginSubmitApi(userModelDTO,"","","");
        System.out.println(api.getMessage());
        System.out.println(api.getData());
    }

    /**
     * Author: Brady
     * Description:测试RPC分页查询试玩帐户
     * Date: 2018/5/22
     */
    @Test
    public void queryDemoUserPageApiTest() {
        UserDemoReqDTO userDemoReqDTO = new UserDemoReqDTO();
        userDemoReqDTO.setLoginBeginTime("");
        userDemoReqDTO.setLoginEndTime("");
        userDemoReqDTO.setGameCode("");
        userDemoReqDTO.setOrderBeginTime("");
        userDemoReqDTO.setOrderEndTime("");
        Long siteId = 1007161902619004929L;
        ApiResult api = sysUserService.queryDemoUserPageApi(userDemoReqDTO, siteId);
        System.out.println(api.getData().toString());
        System.out.println(api.getMessage());
    }

    /**
     * Author: Brady
     * Description: 测试RPC删除会员明细
     * Date: 2018/5/22
     */
    @Test
    public void deleteUserByIdApiTest() {
        Long userId = 996992007885615105L;
        ApiResult api = sysUserService.deleteUserByIdApi(userId);
        System.out.println(api.getMessage());
    }

    /**
     * Author: Brady
     * Description: RPC分页查询测试帐户
     * Date: 2018/5/22
     */
    @Test
    public void queryTestUserPageApiTest() {
        TestUserDTO testUserDTO = new TestUserDTO();
        Long siteId = 1033349363874201601L;
        testUserDTO.setSearchType("0");
        testUserDTO.setSearchName("");
        testUserDTO.setBeginTotalAmount("");
        testUserDTO.setEndTotalAmount("");
        testUserDTO.setHighLevelName("");
        testUserDTO.setIp("");
        testUserDTO.setRegResource("");
        testUserDTO.setDateType("0");
        testUserDTO.setBeginTime("");
        testUserDTO.setEndTime("");
        testUserDTO.setOrderBy("");
        testUserDTO.setSort("0");
        testUserDTO.setDoMain("");
        ApiResult<PageInfo<QueryTestUserDTO>> pageInfoApiResult = sysUserService.queryTestUserPageApi(testUserDTO, siteId);
        System.out.println(pageInfoApiResult);
    }

    /**
     * Author: Brady
     * Description: RPC测试今日注册会员
     * Date: 2018/5/22
     */
    @Test
    public void queryRegisterUserApiTest() {
        UserDTO userDTO = new UserDTO();
        userDTO.setSiteId(1050624121944207361L);
        ApiResult api = sysUserInnerService.queryRegisterUserApi(userDTO);
        System.out.println(api.getData().toString());
    }

    /**
     * Author: Brady
     * Description: RPC测试管理员列表
     * Date: 2018/5/23
     */
    @Test
    public void querySysUserListApiTest() {
        QuerySysUserDTO querySysUserDTO = new QuerySysUserDTO();
        querySysUserDTO.setOrderDirection("desc");
        querySysUserDTO.setUserName("");
        querySysUserDTO.setSiteId(0L);
        ApiResult api = sysUserService.querySysUserListApi(querySysUserDTO);
        System.out.println(api.getData().toString());
    }

    /**
     * Author: Brady
     * Description: RPC测试确认用户名是否存在
     * Date: 2018/5/23
     */
    @Test
    public void confirmExistUserNameApiTest() {
        String userName = "admin";
        ApiResult api = sysUserService.confirmExistUserNameApi(userName);
        System.out.println(api.getMessage());
    }

    /**
     * Author: Brady
     * Description: RPC测试更新管理员信息
     * Date: 2018/5/23
     */
    @Test
    public void updateSysUserApiTest() {
        SysUserUpdateInfoDTO sysUserUpdateInfoDTO = new SysUserUpdateInfoDTO();
        sysUserUpdateInfoDTO.setId(1011072490659221505L);
        sysUserUpdateInfoDTO.setSiteId(1007161902619004929L);
        String updateUserName = "brady";
        sysUserUpdateInfoDTO.setAllowIp("192.168.1.1");
        sysUserUpdateInfoDTO.setIsEnable(0);
        ApiResult api = sysUserService.updateSysUserApi(sysUserUpdateInfoDTO, updateUserName);
        System.out.println(api.getMessage());
    }

    /**
     * Author: Brady
     * Description: RPC测试删除管理员用户
     * Date: 2018/5/23
     */
    @Test
    public void deleteSysUserApiTest() {
        Long userId = 999922154225635329L;
        Long siteId = 99993444698032947L;
        ApiResult api = sysUserService.deleteSysUserApi(userId, siteId);
        System.out.println(api.getMessage());
    }

    /**
     * Author: Brady
     * Description: RPC测试禁用管理员用户
     * Date: 2018/5/23
     */
    @Test
    public void sysUserDisabledApiTest() {
        Long userId = 986810793321410513L;
        String updateUserName = "Brady";
        ApiResult api = sysUserService.sysUserDisabledApi(userId, updateUserName);
        System.out.println(api.getMessage());
    }

    /**
     * Author: Brady
     * Description: RPC测试重置管理员密码
     * Date: 2018/5/23
     */
    @Test
    public void resetSysUserPwdApiTest() {
        String updateUserName = "Brady";
        Long userId = 997026769056563201L;
        ApiResult api = sysUserService.resetSysUserPwdApi(userId, updateUserName,"","");
        System.out.println(api.getMessage());
    }

    @Test
    public void addSysUserApiTest() {
        SysUserUpdateInfoDTO sysUserUpdateInfoDTO = new SysUserUpdateInfoDTO();
        sysUserUpdateInfoDTO.setUserName("testtest1");
        sysUserUpdateInfoDTO.setDeptId(123l);
        sysUserUpdateInfoDTO.setSiteId(1006467073941540866L);
        sysUserUpdateInfoDTO.setRealName("cong");
        //ApiResult apiResult = sysUserService.addSysUserApi(sysUserUpdateInfoDTO,"","");
        //System.out.println(apiResult.getMessage());
    }

    @Test
    public void initSiteParamTest() {
        ApiResult<Map<String, Object>> mapApiResult = sysUserService.initSiteParam(55555555555555558l, "ceshiCode1", "测试title2",8080l, "测试员2号");
        System.out.println(mapApiResult.getData());
    }

    @Test
    public void getSysUserDTOApiTest() {
        Long siteId = 0L;
        Long userId = 1006488773743710209L;
        ApiResult<SysUserDTO> apiResult = sysUserService.getSysUserDTOApi(siteId, userId);
        SysUserDTO data = apiResult.getData();
        System.out.println(data.toString());
    }

    @Test
    public void bindGoogleAuthTest() {
        String userName = "testName";
        String googleSecret = "NUZIZSXROPPVMY";
        Long userId = 996557685386743810L;
        ApiResult<SysUserDTO> apiResult = sysUserService.bindGoogleAuthApi(userName, userId, googleSecret);
        SysUserDTO data = apiResult.getData();
        System.out.println(data);
    }

    @Test
    public void removeGoogleAuth() {
        Long userId = 996557685386743810L;
        ApiResult<SysUserDTO> apiResult = sysUserService.removeGoogleAuthApi(userId);
        SysUserDTO data = apiResult.getData();
        System.out.println(data);
    }

    /**
     * Author: Brady
     * Description: 测试RPC校验密码
     * Date: 2018/6/5
     */
    @Test
    public void verifyPwdApiTest() {
        Long userId = 986810793321410513L;
        String pwd = "Lqf9mIfekmw2c86NjvxiwYtGWoznhqQ5qW9OFHxrhqi326nUkTveSlxIzviD1O8ynFWt9531WQ9U\nwYgqYl90t7ZgQTJW1wJoNZmLYs6LD6B7VI4lvj/zydQoQ5tBGSfWgscQNwnU+mbAdz2ACstG0lMl\nIY6PWNFgmGdPLisDdk+xfrusdNQ/DRa9zzM2Vnvv5ZrxmPHSqih6PJy7uEASLH+Zkxh8QDV9FmC/\nq5zh/KmgYpQtTj3FqsUkRLNuVUcPWQe7qjNIrxWBaqWR32DAaLjnUtviV1IHpJ71ZFIgWxVPh9yI\njLYa3gHf7QN6roW7EMJh693nc0WPKXx6lkl0Qw==";
        ApiResult api = sysUserService.verifyPwdApi(userId, pwd);
        System.out.println(api.getMessage());
    }

    /**
     * Author: Brady
     * Description: 根据用户名查询管理员
     * Date: 2018/6/5
     */
    @Test
    public void getSysUserByNameApiTest() {
        String userName = "adminly";
        ApiResult sysUserByNameApi = sysUserService.getSysUserByNameApi(userName,"");
        System.out.println(sysUserByNameApi.getData());
        System.out.println(sysUserByNameApi.getMessage());
    }

    /**
     * Author: Brady
     * Description: 修改管理员密码
     * Date: 2018/6/5
     */
    @Test
    public void updatePwdApiTest() {
        UserModelDTO userModelDTO = new UserModelDTO();
        userModelDTO.setId(991526490668367874L);
        userModelDTO.setUserName("cong11");
        userModelDTO.setSiteId(1111L);
        userModelDTO.setPassword("Aav8E7s6S/xhFI/AttnBV3JQ3QxwqpzbE4EjZd6VfuApdme0EGrKNs5hC0ydZSUesohFTIu80jxA\n2YMAmzfgXZJutbDvmxJ6/tBl4/gThYQTUg63fQ9bPNmajxGJXIYAMD4pttcl6JsVc+iXohpN0CLV\nA78UEs2J2XS3Te/IBwD/YKt4hGozqE3JIWAUqb7NgOKu/8dqQHRx0a3fegYnwuw3HgZfZRqTNwN5\ny1OacQJyceLpjeFutJHW68dVYSBcAaAVV28VwoTYtW/i2lYwTbBgC+zqJTCkwh2KqC1MmrC/2+mk\nzRbc289XK5jgkduVxlIcv6lyeBavvlGHl3vC4Q==");
        userModelDTO.setNewPwd("La7Les5eBKU/At1Xw4RHiriYu2am6I24UJpJbVS1sbs5xO5T4WloiCEJM4J9PWtg1NG8rK8Ib54u\n8tX5Lzsdnip/NsK2ZZC7x1s9LOdnkR3mc8gWuxsePVGghq/WcYZFpskB59HQg8ti85WkB4CvuDj6\nS1yswslkyPzXUCscRAO+xKrjNDeyzAE/GnqgMy86E/DqD48Xf8f/za/EDLXKn2JDXOhHGbQJZOi+\ngoJRjF3wSd/ngwvHOUm7uVT7UqAxYNtZj7ibn4zw+uAzZHl8+JFuUyciivad9f93cYY4iJ+sx9xd\nbpP6IA0IhzvMKpKorYUZejUCBRAxqho080SNcw==");
        ApiResult apiResult = sysUserService.updatePwdApi(userModelDTO,"","");
        System.out.println(apiResult.getMessage());
    }

    @Test
    public void enableSysUserApiTest() {
        Long userId = 986810793321410512L;
        String userName = "";
        ApiResult api = sysUserService.enableSysUserApi(userId, userName);
        System.out.println(api.getMessage());
    }

    /**
     * Author: Brady
     * Description: 测试RPC查询制定用户名的userId
     * Date: 2018/6/11
     */
    @Test
    public void queryUserIdByUserNameApiTest() {
        String userName = "admindddd";
        ApiResult api = sysUserService.queryUserIdByUserNameApi(userName);
        System.out.println(api.getData().toString());
    }

    @Test
    public void test() {
        String data = Base64.encode("厅主");
        System.out.println(data);
        String finalData = Base64.decodeStr(data);
        System.out.println(finalData);
    }

    /**
     * Author: Brady
     * Description: 测试根据id集合查询管理员
     * Date: 2018/6/19
     */
    @Test
    public void querySysUserByIdApiTest(){
        List<Long> idList = new ArrayList<>();
        idList.add(1029603914761789453L);
        idList.add(1029603988861789454L);
        Long siteId = 0L;
        ApiResult api = sysUserService.querySysUserByIdApi(idList,siteId);
        System.out.println(api.getData().toString());
    }
}
