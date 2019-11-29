package com.jq.user.bankcard.service;

import com.jq.user.bankcard.dto.BankCardDTO;
import com.jq.user.bankcard.dto.BankCardQryDTO;
import com.jq.user.bankcard.fallbackfactory.UserBankServiceFallbackFactory;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "user", path = "/inner/user/bank",url = "${feign-url.user:}",fallbackFactory = UserBankServiceFallbackFactory.class)
public interface UserBankService {

    /**
     * @Author: levi
     * @Descript: 根据银行卡id获取银行卡信息
     * @Date: 2018/6/13
     */
    @GetMapping(value = "getBankCardByIdApi")
    ApiResult<BankCardDTO> getBankCardByIdApi(@RequestParam Long bankCardId);

    /**
     * @Author: levi
     * @Descript: 获取用户银行卡列表
     * @Date: 2018/6/13
     */
    @PostMapping(value = "getListBy")
    ApiResult<PageInfo<BankCardDTO>> getListBy(@RequestBody BankCardQryDTO dto);

    /**
     * @Author: levi
     * @Descript: 添加用户银行卡
     * @param dto 银行卡信息
     * @param siteId 站点id
     * @param manUserName 管理员用户名
     * @param manUserId 管理员id
     * @param platformType 平台类型
     * @return map{userBankId=用户银行卡id}
     * @Date: 2018/5/23
     */
    @PostMapping(value = "addBankCardApi")
    ApiResult<String> addBankCardApi(@RequestBody BankCardDTO dto);

    /**
     * @Author: levi
     * @Descript: 删除银行卡
     * @param bankCardId 用户银行卡id
     * @param manUserName 管理员用户名
     * @param manUserId 管理员id
     * @param siteId 站点id
     * @param platformType 平台类型
     * @Date: 2018/5/23
     */
    @Deprecated
    @PostMapping(value = "deleteApi")
    ApiResult deleteApi(@RequestParam Long bankCardId,@RequestParam String manUserName,@RequestParam Long manUserId,@RequestParam Long siteId,@RequestParam Integer platformType);

    /**
     * @Author: levi
     * @Descript: 绑定银行卡
     * @param bankCardId 用户银行卡id
     * @param manUserName 管理员用户名
     * @param manUserId 管理员id
     * @param siteId 站点id
     * @param platformType 平台类型
     * @Date: 2018/5/23
     */
    @Deprecated
    @PostMapping(value = "setDefaultApi")
    ApiResult setDefaultApi(@RequestParam Long bankCardId,@RequestParam String manUserName,@RequestParam Long manUserId,@RequestParam Long siteId,@RequestParam Integer platformType);
    @PostMapping(value = "addBankCardApi1")
    @Deprecated
    ApiResult<Long> addBankCardApi(@RequestBody BankCardDTO dto,@RequestParam Long siteId,
                                   @RequestParam String manUserName,@RequestParam Long manUserId,
                                   @RequestParam Integer platformType);



    /**
     * @Author: levi
     * @Descript: 启用/禁用银行卡
     * @param bankCardId 用户银行卡id
     * @param manUserName 管理员用户名
     * @param manUserId 管理员id
     * @param siteId 站点id
     * @param platformType 平台类型
     * @Date: 2018/5/23
     */
    @PutMapping(value = "enableApi")
    ApiResult enableApi(@RequestParam Long bankCardId,@RequestParam Integer status,@RequestParam String manUserName,
                        @RequestParam Long manUserId,@RequestParam Long siteId,@RequestParam Integer platformType);

    /**
     * @Author: levimanUserId
     * @param dto 银行卡信息
     * @param siteId 站点id
     * @param manUserName 管理员用户名
     * @param manUserId 管理员id
     * @param platformType 平台类型
     * @Date: 2018/5/23
     */
    @PutMapping(value = "updateApi")
    ApiResult updateApi(@RequestBody BankCardDTO dto, @RequestParam String manUserName,@RequestParam Long manUserId,
                        @RequestParam Long siteId, @RequestParam Integer platformType);

    /**
     * @Author: levi
     * @Descript: 通过用户id获取用户默认银行卡
     * @Date: 2018/6/22
     */
    @GetMapping(value = "getDefaultBankCardApi")
    ApiResult<BankCardDTO> getDefaultBankCardApi(@RequestParam Long userId);

    /**
     * @Author: levi
     * @Descript: 根据银行卡id查询银行卡信息
     * @Date: 2018/9/27
     */
    @GetMapping(value = "findByIdApi")
    ApiResult<BankCardDTO> findByIdApi(@RequestParam Long bankCardId);

    /**
     * 修改银行卡状态
     * @param type 0->出款操作, 1->后台操作
     * @param bankId 银行卡id
     * @param status 0->未锁定（没有申请出款，初始状态），1->锁定（申请中），2->已锁定（已出款）
     * @param manUserName 操作用户名
     * @param manUserId 操作用户id
     * @param siteId 站点id
     * @param platformType 1->web 2->app平台类型(出款选择1)
     * @Author Solming
     * @return
     */
    @PutMapping(value = "updateBankStatus")
    ApiResult<Boolean> updateBankStatus(@RequestParam Integer type, @RequestParam Long bankId, @RequestParam Integer status,
                                        @RequestParam String manUserName,
                                        @RequestParam Long manUserId,@RequestParam Long siteId,@RequestParam Integer platformType);

    /**
     *
     * @param type 1->后台操作 , 2->前台操作
     * @param bankCardId 银行卡id
     * @param manUserName 操作人用户名
     * @param manUserId 操作人用户id
     * @param siteId 站点id
     * @param platformType 站点code
     * @return
     */
    @DeleteMapping(value = "deleteBankApi")
    ApiResult<Boolean> deleteBankApi(@RequestParam Integer type,@RequestParam Long bankCardId,@RequestParam String manUserName,
                                     @RequestParam Long manUserId, @RequestParam Long siteId, @RequestParam Integer platformType);
}
