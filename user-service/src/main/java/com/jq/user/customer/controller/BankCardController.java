package com.jq.user.customer.controller;

import com.jq.user.bankcard.dto.BankCardDTO;
import com.jq.user.bankcard.dto.BankCardQryDTO;
import com.jq.user.bankcard.service.UserBankService;
import com.jq.user.customer.service.BankCardInnerService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/inner/user/bank")
public class BankCardController implements UserBankService {

    @Resource
    private BankCardInnerService bankCardInnerService;


    @Override
    public ApiResult<BankCardDTO> getBankCardByIdApi(@RequestParam Long bankCardId) {
        return bankCardInnerService.getBankCardByIdApi(bankCardId);
    }

    @Override
    public ApiResult<PageInfo<BankCardDTO>> getListBy(@RequestBody BankCardQryDTO dto) {
        return bankCardInnerService.getListBy(dto);
    }

    @Override
    public ApiResult<String> addBankCardApi(@RequestBody BankCardDTO dto) {
        return bankCardInnerService.addBankCardApi(dto);
    }

    @Override
    public ApiResult deleteApi(@RequestParam Long bankCardId, @RequestParam String manUserName, @RequestParam Long manUserId, @RequestParam Long siteId, @RequestParam Integer platformType) {
        return bankCardInnerService.deleteApi(bankCardId, manUserName, manUserId, siteId, platformType);
    }

    @Override
    public ApiResult setDefaultApi(@RequestParam Long bankCardId, @RequestParam String manUserName, @RequestParam Long manUserId, @RequestParam Long siteId, @RequestParam Integer platformType) {
        return bankCardInnerService.setDefaultApi(bankCardId, manUserName, manUserId, siteId, platformType);
    }

    @Override
    public ApiResult<Long> addBankCardApi(@RequestBody BankCardDTO dto, @RequestParam Long siteId, @RequestParam String manUserName,
                                          @RequestParam Long manUserId, @RequestParam Integer platformType) {
        return bankCardInnerService.addBankCardApi(dto, siteId, manUserName, manUserId, platformType);
    }

    @Override
    public ApiResult enableApi(@RequestParam Long bankCardId, @RequestParam Integer status, @RequestParam String manUserName,
                               @RequestParam Long manUserId, @RequestParam Long siteId, @RequestParam Integer platformType) {
        return bankCardInnerService.enableApi(bankCardId, status, manUserName, manUserId, siteId, platformType);
    }

    @Override
    public ApiResult updateApi(@RequestBody BankCardDTO dto, @RequestParam String manUserName, @RequestParam Long manUserId,
                               @RequestParam Long siteId, @RequestParam Integer platformType) {
        return bankCardInnerService.updateApi(dto, manUserName, manUserId, siteId, platformType);
    }

    @Override
    public ApiResult<BankCardDTO> getDefaultBankCardApi(@RequestParam Long userId) {
        return bankCardInnerService.getDefaultBankCardApi(userId);
    }

    @Override
    public ApiResult<BankCardDTO> findByIdApi(@RequestParam Long bankCardId) {
        return bankCardInnerService.findByIdApi(bankCardId);
    }

    @Override
    public ApiResult<Boolean> updateBankStatus(@RequestParam Integer type, @RequestParam Long bankId, @RequestParam Integer status,
                                               @RequestParam String manUserName,
                                               @RequestParam Long manUserId,@RequestParam Long siteId,@RequestParam Integer platformType) {
        return bankCardInnerService.updateBankStatus(type, bankId, status, manUserName, manUserId, siteId, platformType);
    }

    @Override
    public ApiResult<Boolean> deleteBankApi(@RequestParam Integer type,@RequestParam Long bankCardId,@RequestParam String manUserName,
                                            @RequestParam Long manUserId, @RequestParam Long siteId, @RequestParam Integer platformType) {
        return bankCardInnerService.deleteBankApi(type, bankCardId, manUserName, manUserId, siteId, platformType);
    }

}
