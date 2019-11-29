package com.jq.user.valid.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jq.framework.core.exception.Assert;
import com.jq.user.constant.UserConstant;
import com.jq.user.valid.dao.ValidUserSettingDao;
import com.jq.user.valid.dto.ValidUserSettingDTO;
import com.jq.user.valid.entity.ValidUserSettingEntity;
import com.jq.user.valid.service.ValidUserSettingInnerService;
import com.jq.user.valid.service.ValidUserStatisticsInnerService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.RPCResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ValidUserSettingServiceImpl
        extends ServiceImpl<ValidUserSettingDao, ValidUserSettingEntity>
        implements ValidUserSettingInnerService {

    @Resource
    private ValidUserStatisticsInnerService validUserStatisticsInnerService;
    @Resource
    private ValidUserSettingDao validUserSettingDao;
    private static ExecutorService exec = Executors.newCachedThreadPool();

    @Override
    public ValidUserSettingEntity findBySiteId(Long siteId) {
        return getById(siteId);
    }

    @Override
    public List<ValidUserSettingEntity> findAll() {
        return list(new QueryWrapper<ValidUserSettingEntity>().eq("is_del", UserConstant.IS_F));
    }

    @Override
    @Transactional
    public boolean saveSetting(ValidUserSettingEntity entity) {
        Assert.isNull(entity.getSiteId(), "siteId为空");
        Assert.isNull(entity.getSiteCode(), "siteCode为空");
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        boolean isSuccess = save(entity);
        if (isSuccess) {
            boolean initSuccess = validUserStatisticsInnerService.initValidUserData(entity.getSiteId(),entity.getSiteCode());
            if (initSuccess) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public boolean updateAllColumn(ValidUserSettingEntity entity) {
        return validUserSettingDao.updateAllColumn(entity);
    }


    @Override
    @Transactional
    public ApiResult updateSettingApi(ValidUserSettingDTO dto) {
        Assert.isNull(dto.getSiteId(), "siteId为空");
        Assert.isNull(dto.getSiteCode(), "siteCode为空");
        ValidUserSettingEntity entity = new ValidUserSettingEntity();
        BeanUtil.copyProperties(dto, entity);
        entity.setUpdateTime(new Date());
        boolean isSuccess = validUserSettingDao.updateAllColumn(entity);
        return isSuccess ? RPCResult.success() : RPCResult.fail();
    }

    @Override
    public ApiResult<ValidUserSettingDTO> findBySiteIdApi(Long siteId) {
        Assert.isNull(siteId, "siteId为空");
        ValidUserSettingEntity entity = this.findBySiteId(siteId);
        if (entity==null){
            return RPCResult.success(null);
        }
        ValidUserSettingDTO dto = new ValidUserSettingDTO();
        BeanUtil.copyProperties(entity, dto);
        return RPCResult.success(dto);
    }

    @Override
    @Transactional
    public ApiResult saveSettingApi(ValidUserSettingDTO dto) {
        ValidUserSettingEntity entity = new ValidUserSettingEntity();
        BeanUtil.copyProperties(dto, entity);
        boolean isSuccess = this.saveSetting(entity);
        return isSuccess ? RPCResult.success() : RPCResult.fail();
    }
}
