package com.jq.user.proxy;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.jq.user.proxy.dto.UserProxyDTO;
import com.jq.user.proxy.entity.UserProxyEntity;
import com.jq.user.proxy.service.UserProxyInnerService;
import com.jq.user.proxy.service.UserProxyService;
import com.liying.common.service.ApiResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserProxyServiceTest {
    @Resource
    private UserProxyInnerService userProxyInnerService;

    @Test
    public void saveUserProxyTest() {
        Long highUserId = 123456L;
        Long userId = IdWorker.getId();
        String userName = "lynnnn";
        Integer integer = userProxyInnerService.saveUserProxy(highUserId, userId, userName);
        System.out.println(integer);
    }

    @Test
    public void getDirSubUserProxy() {
        Long userId = 1033349497995088084L;
        Long siteId = 1033349363874201601L;
        List<UserProxyEntity> dirSubUserProxy = userProxyInnerService.getDirSubUserProxy(userId, siteId);
        System.out.println(dirSubUserProxy);
    }

    @Test
    public void getAllSubUserProxy() {
//        Long userId = 1033349497995088084L;
//        Long siteId = 1033349363874201601L;
        Long userId = 1055185692579952381L;
        Long siteId = 1032556471336931330L;// 1945ms
        List<UserProxyEntity> allSubUserProxy = userProxyInnerService.getAllSubUserProxy(userId, siteId);
        System.out.println(allSubUserProxy);
    }

    @Test
    public void getDirHighUserProxy() {
        Long userId = 1033349498011865467L;
        Long siteId = 1033349363874201601L;
        UserProxyEntity dirHighUserProxy = userProxyInnerService.getDirHighUserProxy(userId, siteId);
        System.out.println(dirHighUserProxy);
    }

    @Test
    public void getAllHighUserProxy() {
        Long userId = 1033349498011865467L;
        Long siteId = 1033349363874201601L;
        List<UserProxyEntity> allHighUserProxy = userProxyInnerService.getAllHighUserProxy(userId, siteId);
        System.out.println(allHighUserProxy);
    }

    @Test
    public void getUserProxyLine() {
//        Long userId = 1033349498028642714L;
//        Long siteId = 1033349363874201601L;
//        Long userId = 1033349497986699368L;
//        Long siteId = 1033349363874201601L;
        Long userId = 1052826083813027841L;
        Long siteId = 1040499894897405953L;
        String userProxyLine = userProxyInnerService.getUserProxyLine(userId, siteId);
        System.out.println(userProxyLine);
    }

    @Test
    public void CountAllSubUserLine() {
        Long userId = 1052826083813027841L;
        Long siteId = 1040499894897405953L;
        Integer integer = userProxyInnerService.countAllSubUserLine(userId, siteId);
        System.out.println(integer);
    }

    @Test
    public void CountAllSubUserLineByList() {
        List<Long> idList = new ArrayList<>();
        idList.add(1033349497995088084L);
        idList.add(1033349497986699368L);
        Long siteId = 1033349363874201601L;
        Map<Long, Integer> map = userProxyInnerService.countAllSubUserLineByList(idList);
        System.out.println(map);
    }

    @Test
    public void transferUserProxy() {
        Long sourceId = 1057896808744751105L;
        Long destId = 1057895219447508993L;
        Long siteId = 123456789L;
        boolean isSuccess = userProxyInnerService.transferUserProxy(sourceId, destId, siteId);
        System.out.println(isSuccess);
    }

    @Test
    public void checkSourceLevelIsNoHighDest() {
        Long sourceId = 1057892082468872193L;
        Long destId = 1057893386469605377L;
        Long siteId = 123456789L;
        boolean b = userProxyInnerService.checkSourceLevelIsNoHighDest(sourceId, destId, siteId);
        System.out.println(b);
    }

    @Test
    public void findUserLevel() {
        Long userId = 1033349498028642714L;
        Long siteId = 1033349363874201601L;
        Integer userLevel = userProxyInnerService.findUserLevel(userId, siteId);
        System.out.println(userLevel);
    }

//    @Test
//    public void getDirHighUserProxyByIdList(){
//        List<Long> idList = new ArrayList<>();
//        idList.add(1033349498028642713L);
//        idList.add(1033349498028642714L);
//        List<UserProxyEntity> dirHighUserProxyList = userProxyInnerService.getDirHighUserProxyByIdList(idList);
//        System.out.println(dirHighUserProxyList);
//    }

    @Test
    public void getAllSubUserMapByIdList() {
        List<Long> idList = new ArrayList<>();
        idList.add(1049857631770963969L);
        idList.add(1049857751744835586L);
        Map<Long, List<Long>> allSubUserMapByIdList = userProxyInnerService.getAllSubUserMapByIdList(idList);
        System.out.println(allSubUserMapByIdList);
    }

    @Test
    public void getPath() {
        Long userId = 1052826083813027841L;
        Long siteId = 1052826083813027841L;
        String path = userProxyInnerService.getPath(userId, siteId);
        System.out.println(path);
    }

    @Test
    public void getSiteIdAllLevel() {
        Long siteId = 1033349363874201601L;
        List<Integer> siteIdAllLevel = userProxyInnerService.getSiteIdAllLevel(siteId);
        System.out.println(siteIdAllLevel);
    }

    @Test
    public void initDefault() {
        Integer integer = userProxyInnerService.initDefault(123456L, 123456789L, "test-code");
        System.out.println(integer);
    }

    @Test
    public void getUserLevelMap() {
        List<Long> idList = new ArrayList<>();
        idList.add(1033349498003476658L);
        idList.add(1033349498003476655L);
        idList.add(1033349498028642678L);
        idList.add(1033349498003476656L);
        Long siteId = 1040499894897405953L;
        Map<Long, Integer> userLevelMap = userProxyInnerService.getUserLevelMap(idList, siteId);
        System.out.println(userLevelMap);
    }

    @Resource
    private UserProxyService userProxyService;


    @Test
    public void getAllHighUserIdListApi(){
        List<Long> idList = new ArrayList<>();
        idList.add(1065243660291993601L);
        idList.add(1065155385585889282L);
        Long siteId = 1040499894897405953L;
        boolean isContain = false;
        ApiResult<List<Long>> allHighUserIdListApi = userProxyService.getAllHighUserIdListApi(idList, siteId, isContain);
        System.out.println(allHighUserIdListApi);
    }

    @Test
    public void getAllHighUserIdMapTest(){
        List<Long> idList = new ArrayList<>();
        idList.add(1065243660291993601L);
        idList.add(1065155385585889282L);
        Long siteId = 1040499894897405953L;
        Map<Long, List<Long>> allHighUserIdMap = userProxyInnerService.getAllHighUserIdMap(idList, siteId);
        System.out.println(allHighUserIdMap);
    }

    @Test
    public void getAllSubUserDirHighUser(){
        Long userId = 1049857631770963969L;
        Long siteId = 1040499894897405953L;
        ApiResult<List<UserProxyDTO>> allSubUserDirHighUser = userProxyService.getAllSubUserDirHighUserApi(userId, siteId);
        System.out.println(allSubUserDirHighUser);
    }

    @Test
    public void getAllLevelBySiteId(){
        Long siteId = 1040499894897405953L;
        Integer isDemo = 2;
        List<Map<String, Integer>> allLevelBySiteId = userProxyInnerService.getAllLevelBySiteId(siteId, isDemo);
        System.out.println(allLevelBySiteId);
    }

    @Test
    public void getAllProxyIdApiTest(){
        UserProxyDTO dto = new UserProxyDTO();
        List<Long> idList = new ArrayList<>();
        idList.add(1049858450125811713L);
//        idList.add(1065155385585889282L);
        Long siteId = 1040499894897405953L;
        dto.setIdList(idList);
        dto.setSiteId(siteId);
        dto.setProxyRelation(4);
        ApiResult<Map<Long, List<Long>>> allProxyIdApi = userProxyService.getAllProxyIdApi(dto);
        System.out.println(allProxyIdApi);
    }

    @Test
    public void getProxyNumberByListApiTest(){
        List<Long> userIdList = new ArrayList<>();
        userIdList.add(1056724526981771265L);
        userIdList.add(1042676876359823361L);
        System.out.println(userProxyService.getTeamNumberByListApi(userIdList, "2018-12-01", "2018-12-23", "awbnwp-0"));
    }
}
