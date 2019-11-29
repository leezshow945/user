package com.jq.user.remote;

import com.jq.platform.site.dto.SiteDTO;
import com.jq.platform.site.dto.SiteRegisterConfigDTO;
import com.jq.platform.site.service.SiteRegisterConfigService;
import com.jq.platform.site.service.SiteService;
import com.jq.platform.sysmanage.dto.AreaDTO;
import com.jq.platform.sysmanage.dto.BankBranchDTO;
import com.jq.platform.sysmanage.dto.KeyValueDTO;
import com.jq.platform.sysmanage.service.AreaService;
import com.jq.platform.sysmanage.service.BankBranchService;
import com.jq.platform.sysmanage.service.KeyValueService;
import com.jq.user.customer.dto.QuerySysUserDTO;
import com.jq.user.customer.dto.UserModelDTO;
import com.jq.user.customer.service.SysUserService;
import com.jq.user.customer.service.UserRebateService;
import com.jq.user.exception.UserException;
import com.liying.cash.group.api.GroupService;
import com.liying.cash.group.resp.SiteDefaultGroupResp;
import com.liying.cash.group.vo.UserBindingDefaultGroupReq;
import com.liying.cash.pay.api.DepositService;
import com.liying.cash.pay.api.WithdrawService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.RPCResult;
import com.liying.trade.order.api.OrderService;
import com.liying.trade.order.api.OrderSubtotalService;
import com.liying.trade.order.resp.OrderResp;
import com.liying.trade.order.vo.TeamOrderReq;
import com.liying.trade.report.api.MemberReportService;
import com.liying.trade.report.resp.CountCapitalAndBetsResp;
import com.liying.trade.report.resp.EntryAndExitTotalResp;
import com.liying.trade.report.resp.TotalRechargeAndWithdrawResp;
import com.liying.trade.report.vo.EntryAndExitTotalReq;
import com.liying.trade.user.api.UserBonusService;
import com.liying.trade.user.api.UserFundService;
import com.liying.trade.user.vo.GetUserFundReq;
import com.liying.trade.user.vo.OrderSubtotalResp;
import com.liying.trade.user.vo.UserBonusReq;
import com.liying.trade.user.vo.UserFundResp;
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
public class RemoteTest {

     
    private AreaService areaService;
     
    private KeyValueService keyValueService;
     
    private SiteService siteService;
     
    private SysUserService sysUserService;
     
    private com.liying.trade.report.api.UserReportService userReportService;
     
    private WithdrawService withdrawService;
     
    private UserFundService userFundService;
     
    private OrderSubtotalService orderSubtotalService;
     
    private OrderService orderHisService;
     
    private BankBranchService bankBranchService;
     
    private GroupService groupService;
     
    private SiteRegisterConfigService siteRegisterConfigService;
     
    private OrderService orderService;
     
    private com.liying.trade.report.api.UserReportService tradeUserReportService;
    @Resource
    private UserRebateService userRebateService;
     
    private UserBonusService userBonusService;
     
    private DepositService rpcDepositService;
     
    private MemberReportService rpcMemberReportService;

    @Test
    public void AreaTest() {
        ApiResult<List<AreaDTO>> province = areaService.findProvince();
        for (AreaDTO areaDTO : province.getData()) {
            System.out.println(areaDTO);
        }
    }

    @Test
    public void indAreaMapTest() {
        Long cityId = 1014079628034199554L;
        ApiResult<Map<String, Object>> areaMap = areaService.findAreaMapByCityId(cityId);
        System.out.println(areaMap);
    }

    @Test
    public void keyValueTest() {
        ApiResult<Map<String, String>> score_type = keyValueService.findKeyValueMapByDictCode("SCORE_TYPE");
        System.out.println(score_type);
    }

    @Test
    public void siteServiceTest() {
        ApiResult<SiteDTO> result = siteService.findSiteDTOById(1031885064860962817L);
        System.out.println(result.toString());
    }

    @Test
    public void loginSubmitApiTest() {
        UserModelDTO userModelDTO = new UserModelDTO();
        userModelDTO.setUserName("admin");
        userModelDTO.setPassword("B72739774DF323334B1E8E80450854136722C2E9D115E5B10B7A4BA7AB04F9754289587181A545E8F7079A8983C789C6D8F93F646282923475F6494C15F1F5A4CE23A9BE29640FFC5C8F8778CCD15D293749B0AB13900FBD91798B7753B51A26773A0E1FE3E2C2BDF0ED4AD911AC18A36BE6C0129033576FDDE0F323B0120907");
        userModelDTO.setPlatformType(1);
        userModelDTO.setSiteId(0L);
        ApiResult api = sysUserService.loginSubmitApi(userModelDTO, "", "","");
        System.out.println(api.getData());
        System.out.println(api.getMessage());
    }



    @Test
    public void queryTotalRechargeAndWithdrawTest() {
        Long userId = 1020603924572811265L;
        String siteCode = "feng-7";
        ApiResult<TotalRechargeAndWithdrawResp> apiResult = userReportService.queryTotalRechargeAndWithdraw(siteCode, userId);
        System.out.println(apiResult);
    }

//    @Test
//    public void agentTeamFinancialReportTest() {
//        AgentTeamFinanciaReqVO vo = new AgentTeamFinanciaReqVO();
//        vo.setSiteId("liying");
//        vo.setBeginDate("2018-1-1");
//        vo.setEndDate("2019-1-1");
//        List<String> idList = new ArrayList<>();
//        idList.add("1");
//        vo.setUserIdList(idList);
//        ApiResult<AgentTeamFinancialReportResp> apiResult = userReportService.agentTeamFinancialReport(vo);
//        AgentTeamFinancialReportResp data = apiResult.getData();
//        System.out.println(data);
//
//    }

    @Test
    public void withdrawServiceTest() {
        String siteId = "CD7EAEA55A6E412EAE3955A12A7EAE85";
        String userId = "402881205fba4214015fbd60443c010f";
        ApiResult<Boolean> booleanApiResult = withdrawService.checkIsExistWaitWithdrawOrders(siteId, userId);
        Boolean data = booleanApiResult.getData();
        System.out.println(data);

    }

    @Test
    public void querySysUserListApiTest() {
        QuerySysUserDTO querySysUserDTO = new QuerySysUserDTO();
        querySysUserDTO.setOrderDirection("desc");
        querySysUserDTO.setPage(2);
        querySysUserDTO.setUserName("");
        querySysUserDTO.setSiteId(1007172040146915329L);
        ApiResult api = sysUserService.querySysUserListApi(querySysUserDTO);
        System.out.println(api.getData().toString());
    }

    @Test
    public void getUserFundTest() {
        Long userId = 1020603924572811265L;
        String siteCode = "feng-7";
        GetUserFundReq req = new GetUserFundReq();
        req.setUserId(userId.toString());
        req.setSiteCode(siteCode);
        ApiResult<UserFundResp> userFund = userFundService.getUserFund(req);
        System.out.println(userFund);

    }

    @Test
    public void queryUserFundTest() {
        String siteCode = "local";
        StringBuilder sb = new StringBuilder();
        sb.append("2").append(",").append("3");
        ApiResult<List<UserFundResp>> listApiResult = userFundService.queryUserFund(sb.toString(), siteCode);
        List<UserFundResp> data = listApiResult.getData();
        System.out.println(data);
    }

    @Test
    public void getTotalAmountTest() {
        String userId = "1";
        String siteCode = "local";
        ApiResult<Long> totalAmount = userFundService.getTotalAmount(userId, siteCode);
        System.out.println(totalAmount);

    }

    @Test
    public void countOrderSubtotalTest() {
        String userId = "1020603924572811265";
        String siteCode = "feng-7";
        ApiResult<OrderSubtotalResp> apiResult = orderSubtotalService.countOrderSubtotal(userId, siteCode, null);
        OrderSubtotalResp data = apiResult.getData();
        System.out.println(data);

    }

    @Test
    public void queryTeamHistoryOrderTest() {
//        TeamHisOrderParamVO vo = new TeamHisOrderParamVO();
//        vo.setUserName();
//        vo.setOrderNo();
//        vo.setGameCode("gc4501000");
//        vo.setPid("4501000");
//        vo.setPlayId("4501000");
//        vo.setStatus();
//        vo.setBeginDate("2018-04-26");
//        vo.setEndDate("2018-04-27");
//        vo.setSiteCode("test");
//        vo.setUserId("123");
//        vo.setPageNo("1");
//        vo.setPageSize("20");
//        ApiResult<PageInfo<TeamHistoryOrderResp>> pageInfoApiResult = orderHisService.queryTeamHistoryOrder(vo);
//        PageInfo<TeamHistoryOrderResp> data = pageInfoApiResult.getData();
//        List<TeamHistoryOrderResp> content = data.getContent();
//        System.out.println(content);

    }

    @Test
    public void queryTeamBetTest() {
        TeamOrderReq req = new TeamOrderReq();
        ApiResult<com.liying.common.PageInfo<OrderResp>> apiResult = orderService.queryTeamBet(req);
        com.liying.common.PageInfo<OrderResp> data = apiResult.getData();
        System.out.println(data);

    }

    @Test
    public void getEffectiveMembersTest() {
        Long userId = 983579946843058178L;
        String startTime = "2018-1-1";
        String endTime = "2019-1-1";
        String siteCode = "liying";
        ApiResult<Long> apiResult = orderService.getEffectivMembers(userId, startTime, endTime, siteCode);
        Long data = apiResult.getData();
        System.out.println(data);

    }

    @Test
    public void getEffectiveBetMoneyTest() {
        Long userId = 983579946843058178L;
        String startTime = "2018-1-1";
        String endTime = "2019-1-1";
        String siteCode = "liying";
        ApiResult<Long> apiResult = orderService.getEffectiveBetMoney(userId, startTime, endTime, siteCode);
        Long data = apiResult.getData();
        System.out.println(data);

    }

    @Test
    public void getBetMembersTest() {
        Long userId = 983579946843058178L;
        String startTime = "2018-1-1";
        String endTime = "2019-1-1";
        String siteCode = "liying";
        ApiResult<Long> apiResult = orderService.getBetMembers(siteCode, userId, startTime, endTime);
        Long data = apiResult.getData();
        System.out.println(data);
    }

    @Test
    public void getBankBranchTest() {
        Long cityId = 986895795828633602L;
        Long bankId = 980745413549154305L;
        ApiResult<List<BankBranchDTO>> bankBranchList = bankBranchService.findBankBranchList(bankId, cityId);
        List<BankBranchDTO> data = bankBranchList.getData();
        System.out.println(data);
    }

    /**
     * Author: Brady
     * Description: 测试GroupService
     * Date: 2018/6/15
     */
    @Test
    public void GroupServiceTest() {
        UserBindingDefaultGroupReq userBindingDefaultGroupReq = new UserBindingDefaultGroupReq();
        Long userId = 1006735578252791809L;
        userBindingDefaultGroupReq.setUserId(userId);
        userBindingDefaultGroupReq.setSiteCode("test-2");
        userBindingDefaultGroupReq.setUserName("T08595");
        ApiResult api = groupService.userBindingDefaultGroup(userBindingDefaultGroupReq);
        System.out.println(api.getData());
        System.out.println(api.getMessage());
    }

    @Test
    public void registerSiteConfigTests() {
        ApiResult<List<SiteRegisterConfigDTO>> apiResult = siteRegisterConfigService.findSiteRegisterConfigBySiteIdApi(1006467073941540866L);
        if (!RPCResult.checkApiResult(apiResult)) {
            throw new UserException(apiResult.getCode());
        }
        System.out.println(apiResult.getData().toString());
    }

    @Test
    public void addFoundTest() {
//
//        for (int i = 0; i < 10; i++) {
//            AddUserFundReq addUserFundReq = new AddUserFundReq();
//            addUserFundReq.setSiteCode("liying-0");
//            addUserFundReq.setUserId(Convert.toStr(IdWorker.getId()));
//            addUserFundReq.setUsername("测试资金" + i);
//            addUserFundReq.setSuperiorUserId(Convert.toStr(UserConstant.IS_ZERO));  //顶级用户
//            ApiResult apiResultUserFund = userFundService.addUserFund(addUserFundReq);
//            System.out.println("第" + i + "次结果---=====》》》》" + (apiResultUserFund.getCode() == 10000));
//            assert apiResultUserFund.getCode() == 10000;
//        }


        ApiResult<?> apiResultInitSite = orderService.initSite("jq-1", 986810793321410513l, "default");
        assert apiResultInitSite.getCode() == 10000;
    }

    @Test
    public void getDefaultGroupTest() {
        String siteCode = "liying-1";
        ApiResult<SiteDefaultGroupResp> defaultGroup = groupService.getDefaultGroup(siteCode);
        SiteDefaultGroupResp data = defaultGroup.getData();
        System.out.println(data);
    }

    @Test
    public void queryEntryAndExitTotalTest() {
        EntryAndExitTotalReq req = new EntryAndExitTotalReq();
        req.setSiteCode("loose");
        req.setBeginDate("2018-07-02");
        req.setEndDate("2018-07-04");
        List<Long> userIdList = new ArrayList<Long>();
        userIdList.add(33333L);
        userIdList.add(44444L);
        userIdList.add(55555L);
        req.setUserIdList(userIdList);
        ApiResult<List<EntryAndExitTotalResp>> listApiResult = tradeUserReportService.queryEntryAndExitTotal(req);
        List<EntryAndExitTotalResp> data = listApiResult.getData();
        System.out.println(data);
    }

    @Test
    public void keyValueInsertTest() {
//        //修改
//        String UPDATE = "UPDATE";
        KeyValueDTO dto1 = new KeyValueDTO();
        dto1.setDictCode("LOG_TYPE");
        dto1.setDictName("日志类型");
        dto1.setDictKey("UPDATE");
        dto1.setDictValue("更新");
        dto1.setIsEnable(1);
        keyValueService.insertApi(dto1);
//        //删除
//        String DELETE ="DELETE";
        KeyValueDTO dto2 = new KeyValueDTO();
        dto2.setDictCode("LOG_TYPE");
        dto2.setDictName("日志类型");
        dto2.setDictKey("DELETE");
        dto2.setDictValue("删除");
        dto2.setIsEnable(1);
        keyValueService.insertApi(dto2);
//        //增加
//        String ADD ="ADD";
        KeyValueDTO dto3 = new KeyValueDTO();
        dto3.setDictCode("LOG_TYPE");
        dto3.setDictName("日志类型");
        dto3.setDictKey("ADD");
        dto3.setDictValue("增加");
        dto3.setIsEnable(1);
        keyValueService.insertApi(dto3);
//        //注册成功
//        String REGISTER_SUCCESS="REGISTER_SUCCESS";
        KeyValueDTO dto4 = new KeyValueDTO();
        dto4.setDictCode("LOG_TYPE");
        dto4.setDictName("日志类型");
        dto4.setDictKey("REGISTER_SUCCESS");
        dto4.setDictValue("注册成功");
        dto4.setIsEnable(1);
        keyValueService.insertApi(dto4);
//        //注册失败
//        String REGISTER_FAILURE="REGISTER_FAILURE";
        KeyValueDTO dto5 = new KeyValueDTO();
        dto5.setDictCode("LOG_TYPE");
        dto5.setDictName("日志类型");
        dto5.setDictKey("REGISTER_FAILURE");
        dto5.setDictValue("注册失败");
        dto5.setIsEnable(1);
        keyValueService.insertApi(dto5);
//        //分组
//        String USER_GROUP = "USER_GROUP";
        KeyValueDTO dto6 = new KeyValueDTO();
        dto6.setDictCode("LOG_TYPE");
        dto6.setDictName("日志类型");
        dto6.setDictKey("USER_GROUP");
        dto6.setDictValue("分组");
        dto6.setIsEnable(1);
        keyValueService.insertApi(dto6);
//        //收款账户
//        String COLLECTING_ACCOUNT = "COLLECTING_ACCOUNT";
        KeyValueDTO dto7 = new KeyValueDTO();
        dto7.setDictCode("LOG_TYPE");
        dto7.setDictName("日志类型");
        dto7.setDictKey("COLLECTING_ACCOUNT");
        dto7.setDictValue("收款账户");
        dto7.setIsEnable(1);
        keyValueService.insertApi(dto7);
//        //入款模板
//        String DEPOSIT_MODE = "DEPOSIT_MODE";
        KeyValueDTO dto8 = new KeyValueDTO();
        dto8.setDictCode("LOG_TYPE");
        dto8.setDictName("日志类型");
        dto8.setDictKey("DEPOSIT_MODE");
        dto8.setDictValue("入款模板");
        dto8.setIsEnable(1);
        keyValueService.insertApi(dto8);
//        //出款模板
//        String WITHDRAW_MODE = "WITHDRAW_MODE";
        KeyValueDTO dto9 = new KeyValueDTO();
        dto9.setDictCode("LOG_TYPE");
        dto9.setDictName("日志类型");
        dto9.setDictKey("WITHDRAW_MODE");
        dto9.setDictValue("出款模板");
        dto9.setIsEnable(1);
        keyValueService.insertApi(dto9);
    }

    @Test
    public void teamAmount(){
        Long id = 1021257330991837185L;
        ApiResult<List<Long>> proxyByUserId = userRebateService.getProxyByUserId(id, 3);
        List<Long> data = proxyByUserId.getData();
        data.add(id);
        System.out.println(data);
        ApiResult<Long> totalAmount = userFundService.getTotalAmount(data, "feng-7");
        System.out.println(totalAmount);
    }
    @Test
    public void settingTest(){
        List<UserBonusReq> reqList = new ArrayList<>();
        UserBonusReq req = new UserBonusReq();
        req.setUserId(1023872965148160002L);
        req.setLevel(4);
        req.setSiteId(1023846199012634626L);
        req.setSiteCode("yxgnmu-1");
        req.setSettingType(0);
        System.out.println(userBonusService);
        ApiResult<?> settle = userBonusService.settle(reqList);
        System.out.println(settle);
    }

    @Test
    public void queryuserIdBydeposit(){
        String startTime = "2017-11-12 00:00:00";
        String endTime = "2018-12-12 23:59:59";
        String siteCode = "awbnwp-0";
        Integer userType = 2;
        ApiResult<List<Long>> effectUser = rpcDepositService.queryuserIdBydeposit(startTime, endTime, siteCode, 1);
        ApiResult<List<Long>> rechargeUser = rpcDepositService.queryuserIdBydeposit(startTime, endTime, siteCode, 2);
        System.out.println("有效会员数: "+effectUser.getData().size());
        System.out.println("充值会员数: "+rechargeUser.getData().size());
    }
    @Test
    public void countCapitalAndBetsTest(){
        ApiResult<List<CountCapitalAndBetsResp>> listApiResult = rpcMemberReportService.countCapitalAndBets("awbnwp-0");
        System.out.println(listApiResult.getData().size());
        System.out.println(listApiResult);
    }
}
