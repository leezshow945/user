package com.jq.user.customer.service;

import com.jq.user.bankcard.service.UserBankService;
import com.jq.user.customer.entity.BankCardEntity;
import com.jq.user.customer.vo.BankCardVO;

import java.util.List;
import java.util.Map;

public interface BankCardInnerService extends UserBankService {
    Map<String, Object> getAreaAndBank(Long userId);

    String saveBankCard(BankCardEntity bankCardEntity);

    List<Map<String, Object>> getBankCardInfo(Long userId);

    boolean enable(Long userBankId, Integer status, String manUserName, Long manUserId, Long siteId, Integer platformType);

    Map<String, Object> initUpdate(Long userBankId);

    boolean update(BankCardVO vo, String manUserName, Long manUserId, Long siteId, Integer platformType);
}
