package com.jq.user.customer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.jq.framework.core.exception.Assert;
import com.jq.platform.picture.dto.PictureLibraryDTO;
import com.jq.platform.picture.service.PictureLibraryService;
import com.jq.platform.sysmanage.service.AreaService;
import com.jq.user.code.UserCodeEnum;
import com.jq.user.constant.UserConstant;
import com.jq.user.customer.dao.UserDao;
import com.jq.user.customer.dao.UserInfoDao;
import com.jq.user.customer.dto.UserInfoDTO;
import com.jq.user.customer.entity.UserEntity;
import com.jq.user.customer.entity.UserInfoEntity;
import com.jq.user.customer.service.UserInfoInnerService;
import com.jq.user.customer.service.UserInnerService;
import com.jq.user.customer.vo.UserInfoVO;
import com.jq.user.exception.UserException;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import com.liying.common.service.RPCResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserInfoServiceImpl implements  UserInfoInnerService {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);
    @Resource
    private AreaService areaService;
    @Resource
    private PictureLibraryService pictureLibraryService;
    @Resource
    private UserDao userDao;
    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private UserInnerService userInnerService;

    @Override
    public Map<String, Object> findById(Long id) {
        UserEntity userEntity = userDao.selectById(id);
        if (userEntity == null || UserConstant.IS_T.equals(userEntity.getIsDel())) {
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode());
        }
        UserInfoEntity userInfoEntity = userInfoDao.selectById(id);
        ApiResult<Map<String, Object>> area = areaService.findAreaMapByCityId(userInfoEntity.getCityId());
        if (!RPCResult.checkApiResult(area)) {
            throw new UserException(area.getCode(), area.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        String cardNo = Base64.decodeStr(userInfoEntity.getCardNo());
        String email = Base64.decodeStr(userInfoEntity.getEmail());
        String qq = Base64.decodeStr(userInfoEntity.getQq());
        String weChat = Base64.decodeStr(userInfoEntity.getWeChat());
        String mobile = Base64.decodeStr(userInfoEntity.getMobile());
        String realName = Base64.decodeStr(userInfoEntity.getRealName());
        map.put("realName", realName);
        map.put("loginName", userEntity.getUserName());
        map.put("sex", userInfoEntity.getSex());
        map.put("payPwd", userEntity.getPayPwd());
        map.put("birthday", DateUtil.formatDate(userInfoEntity.getBirthday()));
        map.put("cardNo", cardNo);
        map.put("email", email);
        map.put("qq", qq);
        map.put("weChat", weChat);
        map.put("mobile", mobile);
        map.put("remark", userEntity.getRemark());
        map.putAll(area.getData());
        return map;
    }

    @Override
    @Transactional
    public boolean updateUserInfo(UserInfoVO vo) {
        UserEntity user = userDao.selectById(vo.getUserId());
        if (user == null || UserConstant.IS_T.equals(user.getIsDel())) {
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode());
        }
        UserEntity userEntity = new UserEntity();
        user.setId(vo.getUserId());
        user.setRemark(vo.getRemark());
        if (StrUtil.isNotEmpty(vo.getPayPwd())) {
            // 更新交易密码
            String md5PayPwd = userInnerService.getMD5PwdByRSA(vo.getPayPwd());
            user.setPayPwd(md5PayPwd);
        }
        user.setUpdateTime(new Date());
        userDao.updateById(userEntity);
        // 更新用户信息
        UserInfoEntity userInfo = new UserInfoEntity();
        userInfo.setId(vo.getUserId());
        userInfo.setSex(vo.getSex());
        String cardNo = Base64.encode(vo.getCardNo());
        userInfo.setCardNo(cardNo);
        String email = Base64.encode(vo.getEmail());
        userInfo.setEmail(email);
        String qq = Base64.encode(vo.getQq());
        userInfo.setQq(qq);
        String weChat = Base64.encode(vo.getWeChat());
        userInfo.setWeChat(weChat);
        String mobile = Base64.encode(vo.getMobile());
        userInfo.setMobile(mobile);
        userInfo.setProvinceId(vo.getProvinceId());
        userInfo.setCityId(vo.getCityId());
        Integer result = userInfoDao.updateById(userInfo);
        return result == 1;
    }

    @Override
    public Map<String, String> findUserDetail(Long userId) {
        UserEntity user = userDao.selectById(userId);
        if (user == null || UserConstant.IS_T.equals(user.getIsDel())) {
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode());
        }
        UserInfoEntity userInfoEntity = userInfoDao.selectById(userId);
        String realName = Base64.decodeStr(userInfoEntity.getRealName());
        String qq = Base64.decodeStr(userInfoEntity.getQq());
        String weChat = Base64.decodeStr(userInfoEntity.getWeChat());
        String mobile = Base64.decodeStr(userInfoEntity.getMobile());
        String email = Base64.decodeStr(userInfoEntity.getEmail());
        Map<String, String> map = new HashMap<>();
        map.put("userName", user.getUserName());
        map.put("realName", realName);
        map.put("birthday", DateUtil.formatDate(userInfoEntity.getBirthday()));
        map.put("qq", qq);
        map.put("weChat", weChat);
        map.put("mobile", mobile);
        map.put("email", email);
        map.put("lastLoginIp", user.getLastLoginIp());
        map.put("nickName", userInfoEntity.getNickName());
        return map;
    }

    @Override
    @Transactional
    public boolean saveUserInfo(UserInfoEntity userInfoVO) {
        if (userInfoVO.getId() == null) {
            throw new UserException(UserCodeEnum.USER_ID_DEFICIENCY.getCode());
        }
        UserInfoEntity userInfoEntity = userInfoDao.selectById(userInfoVO.getId());
        Integer result = 0;
        if (userInfoEntity == null) {
            userInfoEntity = new UserInfoEntity();
            userInfoEntity.setId(userInfoVO.getId());
            userInfoEntity.setCreateTime(new Date());
            userInfoEntity.setUpdateTime(new Date());
            UserInfoEntity userInfoEntity1 = createUserInfo(userInfoVO, userInfoEntity);
            result = userInfoDao.insert(userInfoEntity1);
        } else {
            UserInfoEntity userInfoEntity1 = createUserInfo(userInfoVO, userInfoEntity);
            result = userInfoDao.updateById(userInfoEntity1);
        }
        return result == 1;
    }

    private UserInfoEntity createUserInfo(UserInfoEntity userInfoVO, UserInfoEntity userInfoEntity) {
        BeanUtil.copyProperties(userInfoVO, userInfoEntity);
        if (StrUtil.isNotEmpty(userInfoVO.getCardNo())) {
            String card = Base64.encode(userInfoVO.getCardNo());
            userInfoEntity.setCardNo(card);
        }

        if (StrUtil.isNotEmpty(userInfoVO.getQq())) {
            String qq = Base64.encode(userInfoVO.getQq());
            userInfoEntity.setQq(qq);
        }
        if (StrUtil.isNotEmpty(userInfoVO.getWeChat())) {
            String weChat = Base64.encode(userInfoVO.getWeChat());
            userInfoEntity.setWeChat(weChat);
        }
        if (StrUtil.isNotEmpty(userInfoVO.getRealName())) {
            String realName = Base64.encode(userInfoVO.getRealName());
            userInfoEntity.setRealName(realName);
        }
        if (StrUtil.isNotEmpty(userInfoVO.getMobile())) {
            String mobile = Base64.encode(userInfoVO.getMobile());
            userInfoEntity.setMobile(mobile);
        }
        if (StrUtil.isNotEmpty(userInfoVO.getEmail())) {
            String email = Base64.encode(userInfoVO.getEmail());
            userInfoEntity.setEmail(email);
        }
        return userInfoEntity;
    }

    @Override
    public ApiResult<UserInfoDTO> getUserInfoByIdApi(Long userId) {
        UserEntity userEntity = userDao.selectById(userId);
        if (userEntity == null) {
            return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        UserInfoEntity userInfoEntity = userInfoDao.selectById(userId);
        UserInfoDTO dto = new UserInfoDTO();
        if (userInfoEntity != null) {
            Long cityId = userInfoEntity.getCityId();
            ApiResult<Map<String, Object>> area = areaService.findAreaMapByCityId(cityId);
            Map<String, Object> data = area.getData();
            if (CollectionUtil.isNotEmpty(data)) {
                dto.setProvinceId((Long) data.get("provinceId"));
                dto.setCityName((String) data.get("cityName"));
                dto.setCityId(userInfoEntity.getCityId());
            }
            String cardNo = Base64.decodeStr(userInfoEntity.getCardNo());
            String email = Base64.decodeStr(userInfoEntity.getEmail());
            String qq = Base64.decodeStr(userInfoEntity.getQq());
            String weChat = Base64.decodeStr(userInfoEntity.getWeChat());
            String mobile = Base64.decodeStr(userInfoEntity.getMobile());
            String realName = Base64.decodeStr(userInfoEntity.getRealName());
            dto.setBirthday(DateUtil.formatDate(userInfoEntity.getBirthday()));
            dto.setCardNo(cardNo);
            dto.setEmail(email);
            dto.setQq(qq);
            dto.setWeChat(weChat);
            dto.setMobile(mobile);
            dto.setRealName(realName);
            dto.setUserName(userEntity.getUserName());
            dto.setSex(userInfoEntity.getSex());
            dto.setAddress(userInfoEntity.getAddress());
            dto.setNickName(userInfoEntity.getNickName());
        }
        dto.setPayPwd(userEntity.getPayPwd());
        dto.setRemark(userEntity.getRemark());
        dto.setLastLoginIp(userEntity.getLastLoginIp());
        return RPCResult.success(dto);
    }

    @Override
    @Transactional
    public ApiResult updateUserInfoApi(UserInfoDTO dto) {
        UserEntity user = userDao.selectById(dto.getUserId());
        if (user == null) {
            return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setId(dto.getUserId());
        userEntity.setRemark(dto.getRemark());
        if (StrUtil.isNotEmpty(dto.getPayPwd())) {
            // 更新交易密码
            String md5PwdByRSA = userInnerService.getMD5PwdByRSA(dto.getPayPwd());
            userEntity.setPayPwd(md5PwdByRSA);
        }
        userEntity.setUpdateTime(new Date());
        userDao.updateById(userEntity);
        // 更新用户信息
        UserInfoEntity userInfo = new UserInfoEntity();
        userInfo.setId(dto.getUserId());
        userInfo.setSex(dto.getSex());
        if (StrUtil.isNotEmpty(dto.getCardNo())) {
            String cardNo = Base64.encode(dto.getCardNo());
            userInfo.setCardNo(cardNo);
        }
        if (StrUtil.isNotEmpty(dto.getEmail())) {
            String email = Base64.encode(dto.getEmail());
            userInfo.setEmail(email);
        }
        if (StrUtil.isNotEmpty(dto.getQq())) {
            String qq = Base64.encode(dto.getQq());
            userInfo.setQq(qq);
        }
        if (StrUtil.isNotEmpty(dto.getWeChat())) {
            String weChat = Base64.encode(dto.getWeChat());
            userInfo.setWeChat(weChat);
        }
        if (StrUtil.isNotEmpty(dto.getMobile())) {
            String mobile = Base64.encode(dto.getMobile());
            userInfo.setMobile(mobile);
        }
        if (StrUtil.isNotEmpty(dto.getRealName())) {
            UserInfoEntity oldUserInfo = userInfoDao.selectById(dto.getUserId());
            if (null != oldUserInfo && StrUtil.isEmpty(oldUserInfo.getRealName())) {
                String realName = Base64.encode(dto.getRealName());
                userInfo.setRealName(realName);
            }
        }
        if (StrUtil.isNotEmpty(dto.getBirthday())) {
            logger.debug(String.format("更新用户生日,userName: %s, birthday: %s",dto.getUserName(),dto.getBirthday()));
            userInfo.setBirthday(DateUtil.parse(dto.getBirthday()));
        }
        userInfo.setProvinceId(dto.getProvinceId());
        userInfo.setCityId(dto.getCityId());
        if (StrUtil.isNotEmpty(dto.getAddress())) {
            userInfo.setAddress(dto.getAddress());
        }
        if (StrUtil.isNotEmpty(dto.getNickName())) {
            userInfo.setNickName(dto.getNickName());
        }
        userInfo.setUpdateTime(new Date());
        Integer result = userInfoDao.updateById(userInfo);
        return result >0 ? RPCResult.success() : RPCResult.fail();
    }

    @Override
    public ApiResult<UserInfoDTO> findUserDetailApi(Long userId) {
        Map<String, String> userDetail = findUserDetail(userId);
        UserInfoDTO dto = new UserInfoDTO();
        dto.setRealName(userDetail.get("realName"));
        dto.setBirthday(userDetail.get("birthday"));
        dto.setQq(userDetail.get("qq"));
        dto.setWeChat(userDetail.get("weChat"));
        dto.setMobile(userDetail.get("mobile"));
        dto.setEmail(userDetail.get("email"));
        dto.setLastLoginIp(userDetail.get("lastLoginIp"));
        dto.setNickName(userDetail.get("nickName"));
        return RPCResult.success(dto);
    }

    @Override
    public ApiResult<UserInfoDTO> getUserInfoApi(Long userId) {
        UserInfoEntity userInfoEntity = userInfoDao.selectById(userId);
        if (userInfoEntity==null){
            throw new UserException(UserCodeEnum.USER_INFO_NOT_EXIST.getCode());
        }
        UserInfoDTO dto = new UserInfoDTO();
        BeanUtil.copyProperties(userInfoEntity, dto);
        String realName = Base64.decodeStr(userInfoEntity.getRealName());
        String mobile = Base64.decodeStr(userInfoEntity.getMobile());
        String email = Base64.decodeStr(userInfoEntity.getEmail());
        String cardNo = Base64.decodeStr(userInfoEntity.getCardNo());
        String qq = Base64.decodeStr(userInfoEntity.getQq());
        String weChat = Base64.decodeStr(userInfoEntity.getWeChat());
        dto.setRealName(realName);
        dto.setMobile(mobile);
        dto.setEmail(email);
        dto.setCardNo(cardNo);
        dto.setQq(qq);
        dto.setWeChat(weChat);
        dto.setNickName(userInfoEntity.getNickName());
        return RPCResult.success(dto);
    }

    @Override
    @Transactional
    public ApiResult<?> updateUserPicture(Long id, String pictureUrl) {
        UserInfoEntity userInfoEntity = userInfoDao.selectById(id);
        if (null == userInfoEntity) {
            throw new UserException(UserCodeEnum.USER_INFO_NOT_EXIST.getCode());
        }
        // 路径为空的，随机从图库获取一张图片更新
        // 图片路径为空，旧用户
        if (StringUtils.isEmpty(pictureUrl) && StringUtils.isEmpty(userInfoEntity.getPhoto())) {
            PictureLibraryDTO libraryDTO = new PictureLibraryDTO();
            libraryDTO.setSex(NumberUtils.toInt(String.valueOf(userInfoEntity.getSex()), 1));
            libraryDTO.setLimit(50);
            ApiResult<PageInfo<PictureLibraryDTO>> result = pictureLibraryService.queryPictureByPageApi(libraryDTO);
            if (result.getData() != null) {
                List<PictureLibraryDTO> list = result.getData().getContent();
                Random random = new Random();
                int n = random.nextInt(list.size());
                PictureLibraryDTO pictureLibraryDTO = list.get(n);
                pictureUrl = pictureLibraryDTO.getPictureUrl();
            }
        }
        if (StringUtils.isEmpty(pictureUrl)) {
            return RPCResult.success();
        }
        userInfoEntity.setPhoto(pictureUrl);
        boolean flag = userInfoDao.updateById(userInfoEntity) == 1;
        if (flag) {
            return RPCResult.success();
        }
        return RPCResult.fail();
    }

    @Override
    public ApiResult<List<UserInfoDTO>> findUserInfoByUserNamesApi(List<String> userNameList, String siteCode) {
        if (CollectionUtils.isEmpty(userNameList)) {
            return RPCResult.success();
        }
        if (StringUtils.isEmpty(siteCode)) {
            return RPCResult.fail(UserCodeEnum.PARAM_IS_NULL.getCode(), "站点编码不允许为空");
        }

        List<UserInfoDTO> resultDtos = userInfoDao.queryUserInfoByUserNames(userNameList, siteCode);
        if (!CollectionUtils.isEmpty(resultDtos)) {
            for (UserInfoDTO dto : resultDtos) {
                // 处理加密字段
                String realName = Base64.decodeStr(dto.getRealName());
                dto.setRealName(realName);
                String mobile = Base64.decodeStr(dto.getMobile());
                dto.setMobile(mobile);
                String email = Base64.decodeStr(dto.getEmail());
                dto.setEmail(email);
                String cardNo = Base64.decodeStr(dto.getCardNo());
                dto.setCardNo(cardNo);
                String qq = Base64.decodeStr(dto.getQq());
                dto.setQq(qq);
                String weChat = Base64.decodeStr(dto.getWeChat());
                dto.setWeChat(weChat);

            }
        }
        return RPCResult.success(resultDtos);
    }

    @Override
    public ApiResult<List<UserInfoDTO>> getUserInfoByIdListApi(@RequestParam List<Long> idList, @RequestParam Long siteId) {
        if (CollectionUtil.isEmpty(idList)){
            return RPCResult.custom(UserCodeEnum.USER_ID_IS_NULL.getCode(),UserCodeEnum.USER_ID_IS_NULL.getMessage());
        }
        Assert.isNull(siteId,"siteId缺失");
        List<UserInfoDTO> list =  userInfoDao.getUserInfoByIdList(idList,siteId);
        return RPCResult.success(list);
    }
}
