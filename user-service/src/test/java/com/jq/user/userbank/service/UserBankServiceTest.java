package com.jq.user.userbank.service;

import com.jq.user.bankcard.dto.BankCardDTO;
import com.jq.user.bankcard.service.UserBankService;
import com.liying.common.service.ApiResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserBankServiceTest {

    @Resource
    private UserBankService userBankService;

    /*@Test
    public void getAreaAndBankApi() {
        Long userId = 983579946843058178L;
        ApiResult<Map<String, Object>> areaAndBankApi = userBankService.getAreaAndBankApi(userId);
        Map<String, Object> data = areaAndBankApi.getData();
        System.out.println(data);
    }*/

    @Test
    public void addBankCardApi() {
        BankCardDTO dto = new BankCardDTO();
        dto.setUserId(1013698998852608001L);
        dto.setBankId(1014347088100646914L);
        dto.setProvinceId(1014079789040947202L);
        dto.setCityId(1014079789183553537L);
        dto.setNetAddrId(13534L);
        dto.setCardUserName("测试名");
        dto.setCardNo("1234567890111");
        Long siteId = 1011868438377877506L;
        String manUserName = "test";
        Long manUserId = 997366456329744385L;
        Integer platformType = 1;
        dto.setSiteId(siteId);
        dto.setCreateBy(manUserName);
        ApiResult<String> apiResult = userBankService.addBankCardApi(dto);
        System.out.println(apiResult);
    }


    @Test
    public void enableApi() {
        Long bankId = 1087299885442072578L;
        Integer  status = 0;
        String manUserName = "yan001";
        Long manUserId = 1041592999428026369L;
        Long siteId = 1040499894897405953L;
        Integer platformType = 1;
        ApiResult apiResult = userBankService.enableApi(bankId, status, manUserName, manUserId, siteId, platformType);
        System.out.println(apiResult);
    }

    /*@Test
    public void initUpdateApi() {
        Long userBankId = 993334646144397314L;
        ApiResult<Map<String, Object>> apiResult = userBankService.initUpdateApi(userBankId);
        Map<String, Object> data = apiResult.getData();
        System.out.println(data);
    }*/

    @Test
    public void updateApi() {
        BankCardDTO BankCardDTO = new BankCardDTO();
        BankCardDTO.setUserBankId(989432741781438466L);
        BankCardDTO.setBankId(981377450073141249L);
        BankCardDTO.setCityId(986889171474964481L);
        BankCardDTO.setCardUserName("测试名称");
        BankCardDTO.setCardNo("99999999");
        Long siteId = 986810793321410512L;
        String manUserName = "test";
        Long manUserId = 997366456329744385L;
        Integer platformType = 1;
        ApiResult apiResult = userBankService.updateApi(BankCardDTO, manUserName, manUserId, siteId, platformType);
    }

    @Test
    public void findByIdApiTest(){
        ApiResult<BankCardDTO> bankCardDTOApiResult = userBankService.findByIdApi(1033319741648338946L);
        System.out.println(bankCardDTOApiResult);
    }
    @Test
    public void updateBankApi(){
        Integer type = 1;
        Long bankId = 1087299885442072578L;
        Integer  status = 2;
        String manUserName = "yan001";
        Long manUserId = 1041592999428026369L;
        Long siteId = 1040499894897405953L;
        ApiResult<Boolean> result = userBankService.updateBankStatus(type,bankId,status,manUserName,manUserId,siteId,1);
        System.out.println(result);
    }
    @Test
    public void deleteBankApi(){
        Integer type= 1;
        Long bankCardId = 1087299885442072578L;
        String manUserName = "yan001";
        Long manUserId = 1041592999428026369L;
        Long siteId = 1040499894897405953L;
        Integer plat  = 1;
        ApiResult<Boolean> result = userBankService.deleteBankApi(type,bankCardId,manUserName,manUserId,siteId,plat);
        System.out.println(result);
    }
}
