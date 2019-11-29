package com.jq.user.favorite.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jq.user.code.UserCodeEnum;
import com.jq.user.constant.UserConstant;
import com.jq.user.customer.dao.UserDao;
import com.jq.user.customer.entity.UserEntity;
import com.jq.user.exception.UserException;
import com.jq.user.favorite.dao.FavoriteDao;
import com.jq.user.favorite.entity.FavoriteDTO;
import com.jq.user.favorite.entity.FavoriteEntity;
import com.jq.user.favorite.service.FavoriteInnerService;
import com.jq.user.support.ListUtils;
import com.liying.common.service.ApiResult;
import com.liying.common.service.RPCResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteInnerService {

    private final static Logger logger = LoggerFactory.getLogger(FavoriteServiceImpl.class);
    @Resource
    private FavoriteDao favoriteDao;
    @Resource
    private UserDao userDao;

    @Override
    @Transactional
    public ApiResult addFavoriteApi(List<FavoriteDTO> dtoList) {
        if (CollectionUtil.isEmpty(dtoList)) {
            throw new UserException(UserCodeEnum.FAVORITE_LIST_IS_NULL.getCode());
        }
        UserEntity userEntity = userDao.selectById(dtoList.get(0).getUserId());
        if (userEntity == null) {
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode());
        }
        int result = 0;
        for (FavoriteDTO favoriteDTO : dtoList) {
            boolean isNotNull = validate(favoriteDTO);
            Date now = new Date();
            if (isNotNull) {
                FavoriteEntity favoriteParam = new FavoriteEntity();
                favoriteParam.setUserId(favoriteDTO.getUserId());
                favoriteParam.setGameCode(favoriteDTO.getGameCode());
                favoriteParam.setIsDel(UserConstant.IS_T);
                FavoriteEntity favorite = favoriteDao.selectOne(new QueryWrapper<>(favoriteParam));
                if (favorite != null) {
                    // 已删除再收藏
                    favorite.setIsDel(UserConstant.IS_F);
                    favorite.setIsEnable(UserConstant.IS_F);
                    favorite.setUpdateTime(now);
                    result = favoriteDao.updateById(favorite);
                } else {
                    // 首次收藏
                    FavoriteEntity favoriteEntity = new FavoriteEntity();
                    BeanUtil.copyProperties(favoriteDTO, favoriteEntity);
                    favoriteEntity.setCreateTime(now);
                    favoriteEntity.setUpdateTime(now);
                    try {
                        result = favoriteDao.insert(favoriteEntity);
                    } catch (DuplicateKeyException e) {
                        throw new UserException(UserCodeEnum.GAME_CODE_DUPLICATE.getCode());
                    }
                }
            }
        }
        return result > 0 ? RPCResult.success() : RPCResult.fail();
    }

    private boolean validate(FavoriteDTO favoriteDTO) {
        if (favoriteDTO.getSiteId() == null) {
            throw new UserException(UserCodeEnum.SITE_ID_IS_NULL.getCode());
        }
        if (favoriteDTO.getUserId() == null) {
            throw new UserException(UserCodeEnum.USER_ID_IS_NULL.getCode());
        }
        if (StrUtil.isEmpty(favoriteDTO.getGameCode())) {
            throw new UserException(UserCodeEnum.GAME_CODE_IS_NULL.getCode());
        }
        if (StrUtil.isEmpty(favoriteDTO.getSiteCode())) {
            throw new UserException(UserCodeEnum.SITE_CODE_IS_NULL.getCode());
        }
        return true;
    }

    @Override
    @Transactional
    public ApiResult cancelFavoriteApi(List<String> gameCodeList, Long userId) {
        if (CollectionUtil.isEmpty(gameCodeList)) {
            throw new UserException(UserCodeEnum.FAVORITE_LIST_IS_NULL.getCode());
        }
        QueryWrapper<FavoriteEntity> param = new QueryWrapper<>();
        param.eq("user_id", userId);
        param.in("game_code", gameCodeList);
        param.eq("is_del", UserConstant.IS_F);
        List<FavoriteEntity> favoriteEntities = favoriteDao.selectList(param);
        if (CollectionUtil.isEmpty(favoriteEntities)) {
            throw new UserException(UserCodeEnum.NOT_FAVORITE.getCode());
        }
        QueryWrapper<FavoriteEntity> ew = new QueryWrapper<>();
        ew.eq("user_id", userId);
        ew.in("game_code", gameCodeList);
        ew.eq("is_del", UserConstant.IS_F);
        FavoriteEntity favoriteEntity = new FavoriteEntity();
        favoriteEntity.setIsDel(UserConstant.IS_T);
        Integer update = favoriteDao.update(favoriteEntity, ew);
        return update > 0 ? RPCResult.success() : RPCResult.fail();
    }

    @Override
    public ApiResult<List<FavoriteDTO>> searchFavoriteListApi(Long userId) {
        QueryWrapper<FavoriteEntity> ew = new QueryWrapper<>();
        ew.eq("user_id", userId);
        ew.eq("is_del", UserConstant.IS_F);
        List<FavoriteEntity> favoriteEntities = favoriteDao.selectList(ew);
        List<FavoriteDTO> dtoList = (List<FavoriteDTO>) ListUtils.listCopy(favoriteEntities, FavoriteDTO.class);
        return RPCResult.success(dtoList);
    }
}
