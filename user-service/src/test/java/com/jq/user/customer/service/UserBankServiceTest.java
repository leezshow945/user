package com.jq.user.customer.service;

import com.jq.platform.sysmanage.dto.BankDTO;
import com.jq.platform.sysmanage.service.BankService;
import com.jq.user.bankcard.dto.BankCardDTO;
import com.jq.user.bankcard.dto.BankCardQryDTO;
import com.jq.user.bankcard.service.UserBankService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserBankServiceTest {

     
    private BankService bankService;
    @Resource
    private BankCardInnerService userBankInnerService;
     
    private UserBankService userBankService;

    @Test
    public void bankTest() {
        ApiResult<List<BankDTO>> bank = bankService.findBankDTOList();
        for (BankDTO bank1 : bank.getData()) {
            System.out.println(bank1);
        }
    }

    @Test
    public void selectByIdTest() {
        ApiResult<BankDTO> bank = bankService.findBankDTOById(980745413549154305L);
        System.out.println(bank.getData().getBankName());
    }

    @Test
    public void getUserBankInfo() {
        Long userId = 983579946843058178L;
        List<Map<String, Object>> userBankInfo = userBankInnerService.getBankCardInfo(userId);
        for (Map<String, Object> map : userBankInfo) {
            System.out.println(map);
        }
    }





    @Test
    public void getListByUserIdTest(){
        Long userId = 1006470027893886978L;
        BankCardQryDTO dto = new BankCardQryDTO();
        dto.setUserId(userId);
        dto.setCardUserName("持卡人姓名");
        ApiResult<PageInfo<BankCardDTO>> listByUserId = userBankService.getListBy(dto);
        PageInfo<BankCardDTO> data = listByUserId.getData();
        System.out.println(data);

    }

    @Test
    public void getBankCardByIdApiTest(){
        Long bankCardId = 1006723890894442498L;
        ApiResult<BankCardDTO> bankCardByIdApi = userBankService.getBankCardByIdApi(bankCardId);
        BankCardDTO data = bankCardByIdApi.getData();
        System.out.println(data);
    }

    @Test
    public void getDefaultBankCardTest(){
        Long userId = 983579946843058178L;
        ApiResult<BankCardDTO> defaultBankCardApi = userBankService.getDefaultBankCardApi(userId);
        BankCardDTO data = defaultBankCardApi.getData();
        System.out.println(data);
    }
}
