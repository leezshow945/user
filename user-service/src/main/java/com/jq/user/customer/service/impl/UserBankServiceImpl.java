package com.jq.user.customer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.framework.core.exception.Assert;
import com.jq.platform.sysmanage.dto.AreaDTO;
import com.jq.platform.sysmanage.dto.BankBranchDTO;
import com.jq.platform.sysmanage.dto.BankDTO;
import com.jq.platform.sysmanage.service.AreaService;
import com.jq.platform.sysmanage.service.BankBranchService;
import com.jq.platform.sysmanage.service.BankService;
import com.jq.user.bankcard.dto.BankCardDTO;
import com.jq.user.bankcard.dto.BankCardQryDTO;
import com.jq.user.code.UserCodeEnum;
import com.jq.user.constant.UserConstant;
import com.jq.user.customer.dao.BankCardDao;
import com.jq.user.customer.dao.UserDao;
import com.jq.user.customer.dao.UserInfoDao;
import com.jq.user.customer.entity.BankCardEntity;
import com.jq.user.customer.entity.UserEntity;
import com.jq.user.customer.entity.UserInfoEntity;
import com.jq.user.customer.service.BankCardInnerService;
import com.jq.user.customer.vo.BankCardVO;
import com.jq.user.exception.UserException;
import com.jq.user.support.PageUtil;
import com.liying.cash.pay.api.WithdrawService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import com.liying.common.service.RPCResult;
import com.liying.common.util.GlobalCacheKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class UserBankServiceImpl implements BankCardInnerService {

    private final static Logger logger = LoggerFactory.getLogger(UserBankServiceImpl.class);
    @Resource
    private AreaService areaService;
    @Resource
    private UserDao userDao;
    @Resource
    private BankService bankService;
    @Resource
    private BankCardDao bankCardDao;
    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private BankBranchService bankBranchService;
    @Resource
    private WithdrawService withdrawService;
    @Resource
    private RedisTemplate redisTemplate;


    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getAreaAndBank(Long userId) {
        UserEntity userEntity = userDao.selectById(userId);
        Assert.isNull(userEntity, "会员不存在");
        Map<String, Object> resultMap = new HashMap<>();
        // 获取系统所有的省份
        ApiResult<List<AreaDTO>> province = areaService.findProvince();
        if (!RPCResult.checkApiResult(province)) {
            throw new UserException(province.getCode(), province.getMessage());
        }
        Map<String, List<Map<Long, Object>>> provinceMap = new HashMap<>();
        for (AreaDTO area : province.getData()) {
            List<Map<Long, Object>> listMap = new ArrayList<>();
            Map<Long, Object> map = new HashMap<>();
            map.put(area.getId(), area.getAreaName());
            listMap.add(map);
            if (provinceMap.containsKey(area.getLargeArea())) {
                provinceMap.get(area.getLargeArea()).add(map);
            } else {
                provinceMap.put(area.getLargeArea(), listMap);
            }
        }
        // 获取系统所有的银行
        Map<String, List<Map<Long, Object>>> bankMap = new HashMap<>();
        ApiResult<List<BankDTO>> bankList = bankService.findBankDTOList();
        if (!RPCResult.checkApiResult(bankList)) {
            throw new UserException(bankList.getCode(), bankList.getMessage());
        }
        for (BankDTO bank : bankList.getData()) {
            List<Map<Long, Object>> listMap = new ArrayList<>();
            Map<Long, Object> map = new HashMap<>();
            map.put(bank.getId(), bank.getBankName());
            listMap.add(map);
            if (bankMap.containsKey(bank.getBankRegion())) {
                bankMap.get(bank.getBankRegion()).add(map);
            } else {
                bankMap.put(bank.getBankRegion(), listMap);
            }
        }
        resultMap.put("province", provinceMap);
        resultMap.put("bank", bankMap);
        resultMap.put("userName", userEntity.getUserName());
        return resultMap;
    }

    @Override
    public String saveBankCard(BankCardEntity entity) {
        Long userId = entity.getUserId();
        String cardNo = entity.getCardNo();
        Long siteId = entity.getSiteId();
        Assert.isNull(userId,"userId为空");
        Assert.isNull(siteId,"siteId为空");
        UserEntity user = userDao.selectById(userId);
        if (user == null || UserConstant.IS_T.equals(user.getIsDel())) {
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode());
        }
        String encodeCardNoStr = Base64.encode(cardNo);
        Integer count = bankCardDao.countBySiteIdAndCardNo(siteId, encodeCardNoStr);
        if (count != null && count > 0) {
            throw new UserException(UserCodeEnum.BANKCARD_EXIST.getCode());
        }
        UserInfoEntity userInfoEntity = userInfoDao.selectById(userId);
        if (userInfoEntity == null) {
            throw new UserException(UserCodeEnum.USERINFO_LACK.getCode());
        }
        String realName = userInfoEntity.getRealName();
        String cardUserName = userInfoEntity.getRealName();
        if (StrUtil.isEmpty(realName)){
            cardUserName = Base64.encode(entity.getCardUserName());
        }
        if (StrUtil.isEmpty(cardUserName)){
            throw new UserException(UserCodeEnum.REALNAME_ILLEGAL.getCode());
        }
        // 第一次绑定银行卡并且没有绑定真实姓名的情况下 更新绑定真实姓名
        if (StrUtil.isEmpty(realName)){
            userInfoEntity.setRealName(cardUserName);
            Integer integer = userInfoDao.updateById(userInfoEntity);
            if (integer > 0) {
                redisTemplate.delete(GlobalCacheKey.User.USER_INFO + userId);
            }
        }
        Long bankId = entity.getBankId();
        ApiResult<BankDTO> apiResult = bankService.findBankDTOById(bankId);
        if (!RPCResult.checkApiResult(apiResult)) {
            throw new UserException(apiResult.getCode(), apiResult.getMessage());
        }
        Long cityId = entity.getCityId();
        ApiResult<Map<String, Object>> areaMapResult = areaService.findAreaMapByCityId(cityId);
        if (!RPCResult.checkApiResult(areaMapResult)){
            throw new UserException(areaMapResult.getCode(),areaMapResult.getMessage());
        }
        Map<String, Object> data = areaMapResult.getData();
        if (data==null){
            throw new UserException(UserCodeEnum.AREA_NOT_EXIST.getCode());
        }
        String netAddr = entity.getNetAddr();
        BankDTO bankDTO = apiResult.getData();
        BankCardEntity bankcard = new BankCardEntity();
        bankcard.setId(IdWorker.getId());
        bankcard.setUserId(userId);
        bankcard.setProvinceId(entity.getProvinceId());
        bankcard.setCityId(entity.getCityId());
        String cityName = String.valueOf(data.get("cityName"));
        bankcard.setCity(cityName);
        String province = String.valueOf(data.get("provinceName"));
        bankcard.setProvince(province);
        bankcard.setBankId(bankId);
        String remark = entity.getRemark();
        bankcard.setRemark(remark);
        bankcard.setBankName(bankDTO.getBankName());
        bankcard.setNetAddr(netAddr);
        bankcard.setIsDel(UserConstant.IS_F);
        bankcard.setIsEnable(UserConstant.IS_T);
        bankcard.setIsDefault(UserConstant.IS_ZERO);
        bankcard.setCreateTime(new Date());
        bankcard.setUpdateTime(new Date());
        bankcard.setCreateBy(entity.getCreateBy());
        bankcard.setUpdateBy(entity.getCreateBy());
        bankcard.setCardUserName(cardUserName);
        String card = entity.getCardNo();
        String encode = Base64.encode(card);
        bankcard.setCardNo(encode);
        // 查询默认银行卡
        int result = bankCardDao.insert(bankcard);
        return result > 0 ? bankcard.getBankName():null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getBankCardInfo(Long userId) {
        List<BankCardEntity> list = bankCardDao.selectList(new QueryWrapper<BankCardEntity>().eq("user_id", userId).eq("is_enable", UserConstant.IS_T));
        List<Map<String, Object>> dataList = new ArrayList<>();
        if (list != null) {
            for (BankCardEntity userBank : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("bankName", userBank.getBankName());
                map.put("province", userBank.getProvince());
                map.put("city", userBank.getCity());
                map.put("netAddr", userBank.getNetAddr());
                String cardUserName = Base64.decodeStr(userBank.getCardUserName());
                map.put("cardUserName", cardUserName);
                String cardNo = Base64.decodeStr(userBank.getCardNo());
                map.put("cardNo", cardNo);
                map.put("createTime", userBank.getCreateTime());
                map.put("isEnable", userBank.getIsEnable());
                dataList.add(map);
            }
        }
        return dataList;
    }


    @Override
    public boolean enable(Long userBankId, Integer status, String manUserName, Long manUserId, Long siteId, Integer platformType) {
        BankCardEntity userBankEntity = bankCardDao.selectById(userBankId);
        Assert.isNull(userBankEntity, "银行卡不存在");
        if (UserConstant.IS_T.equals(status) && UserConstant.IS_T.equals(userBankEntity.getIsEnable())) {
            throw new UserException(UserCodeEnum.BANKCARD_FORBIDDEN.getCode());
        } else if (UserConstant.IS_F.equals(status) && UserConstant.IS_F.equals(userBankEntity.getIsEnable())) {
            throw new UserException(UserCodeEnum.BANKCARD_ENABLED.getCode());
        }
        BankCardEntity userBankUpdate = new BankCardEntity();
        userBankUpdate.setId(userBankId);
        userBankUpdate.setIsEnable(status);
        QueryWrapper<BankCardEntity> ew = new QueryWrapper<>();
        ew.eq("user_id", userBankEntity.getUserId());
        ew.eq("is_enable", UserConstant.IS_T);
        List<BankCardEntity> userBankList = bankCardDao.selectList(ew);
        if (UserConstant.IS_F.equals(status)) {
            if (userBankList.size() < 2) {
                //  查询是否存在未处理的订单
                UserEntity userEntity = userDao.selectById(userBankEntity.getUserId());
                ApiResult<Boolean> booleanApiResult = withdrawService.checkIsExistWaitWithdrawOrders(userEntity.getSiteId().toString(), userBankEntity.getUserId().toString());
                if (!RPCResult.checkApiResult(booleanApiResult)) {
                    throw new UserException(booleanApiResult.getCode());
                }
                Boolean exist = booleanApiResult.getData();
                if (exist) {
                    throw new UserException(UserCodeEnum.BANKCARD_FORBIDDEN_FAILED.getCode());
                }
            }
        }
        userBankUpdate.setUpdateTime(new Date());
        userBankUpdate.setUpdateBy(manUserName);
        Integer result = bankCardDao.updateById(userBankUpdate);
        return result == 1;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> initUpdate(Long bankCardId) {
        BankCardEntity bankCardEntity = bankCardDao.selectById(bankCardId);
        Assert.isNull(bankCardEntity, "银行卡不存在");
        ApiResult<List<AreaDTO>> area = areaService.findCityByProvinceId(bankCardEntity.getProvinceId());
        if (!RPCResult.checkApiResult(area)) {
            throw new UserException(area.getCode(), area.getMessage());
        }
        ApiResult<List<BankBranchDTO>> bankBranchList = bankBranchService.findBankBranchList(bankCardEntity.getBankId(), bankCardEntity.getCityId());
        if (!RPCResult.checkApiResult(bankBranchList)) {
            throw new UserException(bankBranchList.getCode(), bankBranchList.getMessage());
        }
        String cardUserName = Base64.decodeStr(bankCardEntity.getCardUserName());
        String cardNo = Base64.decodeStr(bankCardEntity.getCardNo());
        bankCardEntity.setCardUserName(cardUserName);
        bankCardEntity.setCardNo(cardNo);
        Map<String, Object> map = new HashMap<>();
        map.put("area", area.getData());
        map.put("bankCard", bankCardEntity);
        map.put("bankBranchList", bankBranchList.getData());
        return map;
    }

    @Override
    public boolean update(BankCardVO vo, String manUserName, Long manUserId, Long siteId, Integer platformType) {
        BankCardEntity bankCardEntity = bankCardDao.selectById(vo.getBankCardId());
        if (bankCardEntity == null) {
            throw new UserException(UserCodeEnum.BANKCARD_NOT_EXIST.getCode());
        }
        if (vo.getBankId() != null && !vo.getBankId().equals(bankCardEntity.getBankId())) {
            ApiResult<BankDTO> bankDTO = bankService.findBankDTOById(vo.getBankId());
            if (!RPCResult.checkApiResult(bankDTO)) {
                throw new UserException(bankDTO.getCode(), bankDTO.getMessage());
            }
            bankCardEntity.setBankId(bankDTO.getData().getId());
            bankCardEntity.setBankName(bankDTO.getData().getBankName());
        }
        if (StrUtil.isNotEmpty(vo.getBranchName())) {
            bankCardEntity.setNetAddr(vo.getBranchName());
            // 网点地址id置0 后期不再使用
            if (bankCardEntity.getNetAddrId() != null) {
                bankCardEntity.setNetAddrId(0L);
            }
        }
        if (vo.getCityId() != null && !vo.getCityId().equals(bankCardEntity.getCityId())) {
            ApiResult<Map<String, Object>> areaMap = areaService.findAreaMapByCityId(vo.getCityId());
            if (!RPCResult.checkApiResult(areaMap)) {
                throw new UserException(areaMap.getCode(), areaMap.getMessage());
            }
            bankCardEntity.setCityId(vo.getCityId());
            bankCardEntity.setProvinceId((Long) areaMap.getData().get("provinceId"));
            bankCardEntity.setCity((String) areaMap.getData().get("city"));
            bankCardEntity.setProvince((String) areaMap.getData().get("province"));
        }
        if (StrUtil.isNotEmpty(vo.getCardUserName())) {
            String cardUserName = Base64.encode(vo.getCardUserName());
            bankCardEntity.setCardUserName(cardUserName);
        }
        if (StrUtil.isNotEmpty(vo.getCardNo())) {
            String cardNo = Base64.encode(vo.getCardNo());
            bankCardEntity.setCardNo(cardNo);
        }
        bankCardEntity.setRemark(vo.getRemark());
        bankCardEntity.setUpdateTime(new Date());
        bankCardEntity.setUpdateBy(manUserName);
        Integer result = bankCardDao.updateById(bankCardEntity);
        return result == 1;
    }

    @Override
    public ApiResult<String> addBankCardApi(BankCardDTO dto) {
        BankCardEntity bankCardEntity = new BankCardEntity();
        BeanUtil.copyProperties(dto,bankCardEntity);
        bankCardEntity.setNetAddr(dto.getBranchName());
        bankCardEntity.setCreateBy(dto.getCreateBy());
        String bankName = saveBankCard(bankCardEntity);
        return RPCResult.success(bankName);
    }

    @Override
    public ApiResult deleteApi(@RequestParam Long bankCardId, @RequestParam String manUserName, @RequestParam Long manUserId, @RequestParam Long siteId, @RequestParam Integer platformType) {
        return RPCResult.fail();
    }

    @Override
    public ApiResult setDefaultApi(@RequestParam Long bankCardId, @RequestParam String manUserName, @RequestParam Long manUserId, @RequestParam Long siteId, @RequestParam Integer platformType) {
        return RPCResult.fail();
    }

    @Override
    @Deprecated
    public ApiResult<Long> addBankCardApi(@RequestBody BankCardDTO dto, @RequestParam Long siteId, @RequestParam String manUserName, @RequestParam Long manUserId, @RequestParam Integer platformType) {
        BankCardEntity bankCardEntity = new BankCardEntity();
        BeanUtil.copyProperties(dto,bankCardEntity);
        bankCardEntity.setNetAddr(dto.getBranchName());
        bankCardEntity.setCreateBy(dto.getCreateBy());
        bankCardEntity.setSiteId(siteId);
        String bankName = saveBankCard(bankCardEntity);
        return RPCResult.success(bankName);
    }

    @Override
    public ApiResult enableApi(Long bankCardId, Integer status, String manUserName, Long manUserId, Long siteId, Integer platformType) {
        boolean flag = enable(bankCardId, status, manUserName, manUserId, siteId, platformType);
        return flag ? RPCResult.success() : RPCResult.fail();
    }


    @Override
    public ApiResult updateApi(BankCardDTO dto, String manUserName, Long manUserId, Long siteId, Integer platformType) {
        BankCardVO vo = new BankCardVO();
        BeanUtil.copyProperties(dto, vo);
        vo.setBankCardId(dto.getUserBankId());
        boolean flag = update(vo, manUserName, manUserId, siteId, platformType);
        return flag ? RPCResult.success() : RPCResult.fail();
    }

    @Override
    public ApiResult<BankCardDTO> getDefaultBankCardApi(Long userId) {
        QueryWrapper<BankCardEntity> ew = new QueryWrapper<>();
        ew.eq("user_id", userId.toString());
        ew.eq("is_default", UserConstant.IS_T);
        ew.eq("is_del", UserConstant.IS_F);
        List<BankCardEntity> bankCardEntities = bankCardDao.selectList(ew);
        if (CollectionUtil.isNotEmpty(bankCardEntities)) {
            BankCardEntity bankCardEntity = bankCardEntities.get(0);
            BankCardDTO dto = new BankCardDTO();
            BeanUtil.copyProperties(bankCardEntity, dto);
            String cardNo = Base64.decodeStr(bankCardEntity.getCardNo());
            String cardUserName = Base64.decodeStr(bankCardEntity.getCardUserName());
            dto.setCardNo(cardNo);
            dto.setCardUserName(cardUserName);
            return RPCResult.success(dto);
        } else {
            return RPCResult.success(null);
        }
    }

    @Override
    public ApiResult<BankCardDTO> findByIdApi(Long bankCardId) {
        BankCardEntity bankCardEntity = bankCardDao.selectById(bankCardId);
        if (bankCardEntity == null) {
            return RPCResult.custom(UserCodeEnum.OBJECT_NOT_EXIST.getCode(), UserCodeEnum.OBJECT_NOT_EXIST.getMessage());
        }
        BankCardDTO dto = new BankCardDTO();
        BeanUtil.copyProperties(bankCardEntity, dto);
        String cardNo = Base64.decodeStr(bankCardEntity.getCardNo());
        String cardUserName = Base64.decodeStr(bankCardEntity.getCardUserName());
        dto.setCardNo(cardNo);
        dto.setCardUserName(cardUserName);
        return RPCResult.success(dto);
    }


    @Override
    public ApiResult<BankCardDTO> getBankCardByIdApi(Long bankCardId) {
        BankCardEntity bankCardEntity = bankCardDao.selectById(bankCardId);
        if (bankCardEntity == null || UserConstant.IS_T.equals(bankCardEntity.getIsDel())) {
            throw new UserException(UserCodeEnum.OBJECT_NOT_EXIST.getCode());
        }
        BankCardDTO dto = new BankCardDTO();
        BeanUtil.copyProperties(bankCardEntity, dto);
        if (StrUtil.isNotEmpty(dto.getCardNo())) {
            String cardNo = Base64.decodeStr(dto.getCardNo());
            dto.setCardNo(cardNo);
        }
        if (StrUtil.isNotEmpty(dto.getCardUserName())) {
            String cardUserName = Base64.decodeStr(dto.getCardUserName());
            dto.setCardUserName(cardUserName);
        }
        if (bankCardEntity.getNetAddrId() != null && bankCardEntity.getNetAddrId() != 0) {
            ApiResult<BankBranchDTO> bankBranchByIdApi = bankBranchService.findBankBranchByIdApi(bankCardEntity.getNetAddrId());
            if (!RPCResult.checkApiResult(bankBranchByIdApi)) {
                return RPCResult.fail(bankBranchByIdApi.getCode(), bankBranchByIdApi.getMessage());
            }
            dto.setBranchName(bankBranchByIdApi.getData().getBranchName());
        } else {
            dto.setBranchName(bankCardEntity.getNetAddr());
        }
        return RPCResult.success(dto);
    }

    @Override
    public ApiResult<PageInfo<BankCardDTO>> getListBy(BankCardQryDTO paramDto) {
        QueryWrapper<BankCardEntity> ew = new QueryWrapper<>();
        ew.eq("user_id", paramDto.getUserId());
        ew.eq("is_del", UserConstant.IS_F);
        if (StrUtil.isNotEmpty(paramDto.getCardNo())) {
            String encodeCardNo = Base64.encode(paramDto.getCardNo());
            ew.eq("card_no", encodeCardNo);
        }
        if (StrUtil.isNotEmpty(paramDto.getCardUserName())) {
            String encodeCardUserName = Base64.encode(paramDto.getCardUserName());
            ew.eq("card_user_name", encodeCardUserName);
        }
        Page page = PageUtil.buildPage(paramDto.getPage(), paramDto.getLimit(), paramDto.getOrderDirection(), paramDto.getOrderField());
        IPage<BankCardEntity> bankCardEntityIPage = bankCardDao.selectPage(page, ew);
        List<BankCardEntity> records = bankCardEntityIPage.getRecords();
        List<BankCardDTO> dtoList = new ArrayList<>();
        for (BankCardEntity bankCardEntity : records) {
            BankCardDTO dto = new BankCardDTO();
            BeanUtil.copyProperties(bankCardEntity, dto);
            if (bankCardEntity.getNetAddrId() != null && bankCardEntity.getNetAddrId() != 0L) {
                ApiResult<BankBranchDTO> bankBranchByIdApi = bankBranchService.findBankBranchByIdApi(bankCardEntity.getNetAddrId());
                BankBranchDTO data = bankBranchByIdApi.getData();
                if (data != null) {
                    String branchName = data.getBranchName();
                    dto.setBranchName(branchName);
                }
            }
            String cardNo = Base64.decodeStr(bankCardEntity.getCardNo());
            String cardUserName = Base64.decodeStr(bankCardEntity.getCardUserName());
            dto.setCardNo(cardNo);
            dto.setCardUserName(cardUserName);
            dtoList.add(dto);
        }
        PageInfo<BankCardDTO> pageInfo = new PageInfo<>(dtoList, (int) page.getCurrent(), (int) page.getSize(), page.getTotal());
        return RPCResult.success(pageInfo);
    }

    @Override
    public ApiResult<Boolean> updateBankStatus(Integer type, Long bankId, Integer status, String manUserName, Long manUserId, Long siteId, Integer platformType) {
        Assert.isNull(type, "操作类型为空");
        Assert.isNull(bankId, "请选择账户银行卡");
        Assert.isNull(status, "请选择设置后状态");
        BankCardEntity userBankEntity = bankCardDao.selectById(bankId);
        if (userBankEntity == null) {
            throw new UserException(UserCodeEnum.BANKCARD_NOT_EXIST.getCode());
        }
        if (userBankEntity.getIsEnable().equals(UserConstant.IS_F)) {
            throw new UserException(UserCodeEnum.BANKCARD_UN_ENABLED.getCode());
        }
        if (UserConstant.IS_ZERO == type && UserConstant.IS_TWO == userBankEntity.getIsDefault()) {
            status = UserConstant.IS_TWO;
        }
        userBankEntity.setIsDefault(status);
        userBankEntity.setUpdateTime(new Date());
        userBankEntity.setUpdateBy(manUserName);
        Integer result = bankCardDao.updateById(userBankEntity);
        return result == 1 ? RPCResult.success(true) : RPCResult.fail();
    }

    @Override
    public ApiResult<Boolean> deleteBankApi(Integer type, Long bankCardId, String manUserName, Long manUserId, Long siteId, Integer platformType) {
        BankCardEntity userBankEntity = bankCardDao.selectById(bankCardId);
        Assert.isNull(userBankEntity, "银行卡不存在");
        Assert.isNull(type, "请选择操作类型");
        Assert.isNull(bankCardId, "请选择银行卡");
        if (UserConstant.IS_T.equals(userBankEntity.getIsDel())) {
            throw new UserException(UserCodeEnum.BANKCARD_DELETED.getCode());
        }
        BankCardEntity userBank = new BankCardEntity();
        userBank.setId(bankCardId);
        userBank.setIsEnable(UserConstant.IS_F);
        Integer isDefault = userBankEntity.getIsDefault();
        if (UserConstant.IS_TWO == type && isDefault != UserConstant.IS_ZERO) {
            return RPCResult.fail(UserCodeEnum.BANKCARD_ORDER_UNTREATED.getCode(), "银行卡已被锁定,未能删除成功");
        }
        userBank.setIsDel(UserConstant.IS_T);
        userBank.setIsDefault(UserConstant.IS_ZERO);
        userBank.setUpdateTime(new Date());
        userBank.setUpdateBy(manUserName);
        Integer integer = bankCardDao.updateById(userBank);
        return integer == 1 ? RPCResult.success(true) : RPCResult.fail();
    }

}
