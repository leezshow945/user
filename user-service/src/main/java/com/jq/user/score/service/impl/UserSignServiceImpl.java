package com.jq.user.score.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jq.user.code.UserCodeEnum;
import com.jq.user.constant.ScoreTypeEnum;
import com.jq.user.constant.UserConstant;
import com.jq.user.constant.UserStatus;
import com.jq.user.customer.dao.UserDao;
import com.jq.user.customer.entity.UserEntity;
import com.jq.user.exception.UserException;
import com.jq.user.score.dao.UserSignDao;
import com.jq.user.score.dto.UserSignDTO;
import com.jq.user.score.entity.UserSignEntity;
import com.jq.user.score.service.UserScoreInnerService;
import com.jq.user.score.service.UserSignInnerService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.RPCResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserSignServiceImpl extends ServiceImpl<UserSignDao, UserSignEntity> implements UserSignInnerService {

    private static final Logger logger = LoggerFactory.getLogger(UserSignServiceImpl.class);
    @Resource
    private UserDao userDao;
    @Resource
    private UserSignDao userSignDao;
    @Resource
    private UserScoreInnerService userScoreInnerService;


    @Override
    public boolean createSign(Long siteId, Long userId) {
        UserEntity userEntity = userDao.selectById(userId);
        if (userEntity == null) {
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        if (userEntity.getIsDel().equals(UserConstant.IS_T) || !userEntity.getStatus().equals(UserStatus.USER_STATUS_10.getCode())) {
            throw new UserException(UserCodeEnum.USER_UNNORMAL_STATUS.getCode(), UserCodeEnum.USER_UNNORMAL_STATUS.getMessage());
        }
        if (existSign(userEntity.getSiteId(), userId)) {
            throw new UserException(UserCodeEnum.EXISTE_SIGN.getCode(), UserCodeEnum.EXISTE_SIGN.getMessage());
        }

        UserSignEntity userSignEntity = new UserSignEntity();
        userSignEntity.setSignTime(new Date());
        userSignEntity.setSiteId(siteId);
        userSignEntity.setUserId(userId);

        userScoreInnerService.updateUserScore(userEntity, ScoreTypeEnum.SCORE_TYPE_SIGN.getCode());

        return userSignDao.insert(userSignEntity) > 0;
    }


    @Override
    public boolean existSign(Long siteId, Long userId) {
        return userSignDao.selectCount(new QueryWrapper<UserSignEntity>()
                .eq("site_id", siteId)
                .eq("user_id", userId)
                .apply(" to_days(sign_time)=to_days(now())")) > 0;
    }

    @Override
    public List<UserSignEntity> getSignRecord(Long siteId, Long userId) {
        return list(new QueryWrapper<UserSignEntity>()
                .eq("site_id", siteId)
                .eq("user_id", userId)
                .apply("DATE_FORMAT(sign_time, '%Y%m' )= DATE_FORMAT(CURDATE( ), '%Y%m')"));
    }


    @Override
    public ApiResult<List<UserSignDTO>> getSignRecordAPI(Long siteId, Long userId) {
            List<UserSignEntity> signRecord = this.getSignRecord(siteId, userId);
            List<UserSignDTO> userSignDTOList = new ArrayList<>();
            for (UserSignEntity entity : signRecord) {
                UserSignDTO dto = new UserSignDTO();
                BeanUtil.copyProperties(entity, dto);
                userSignDTOList.add(dto);
            }
            return RPCResult.success(userSignDTOList);
    }

    @Override
    public ApiResult userSignAPI(Long siteId, Long userId) {
            UserEntity userEntity = userDao.selectById(userId);
            if (userEntity == null) {
                throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
            }
            if (userEntity.getIsDel().equals(UserConstant.IS_T) || !userEntity.getStatus().equals(UserStatus.USER_STATUS_10.getCode())) {
                throw new UserException(UserCodeEnum.USER_UNNORMAL_STATUS.getCode(), UserCodeEnum.USER_UNNORMAL_STATUS.getMessage());
            }
            if (existSign(userEntity.getSiteId(), userId)) {
                throw new UserException(UserCodeEnum.EXISTE_SIGN.getCode(), UserCodeEnum.EXISTE_SIGN.getMessage());
            }

            UserSignEntity userSignEntity = new UserSignEntity();
            userSignEntity.setSignTime(new Date());
            userSignEntity.setSiteId(siteId);
            userSignEntity.setUserId(userId);

            userScoreInnerService.updateUserScore(userEntity, ScoreTypeEnum.SCORE_TYPE_SIGN.getCode());

            return (userSignDao.insert(userSignEntity) > 0) ? RPCResult.success() : RPCResult.fail();
    }

}
