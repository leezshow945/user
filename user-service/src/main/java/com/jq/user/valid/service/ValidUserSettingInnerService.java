package com.jq.user.valid.service;

import com.jq.user.valid.entity.ValidUserSettingEntity;

import java.util.List;

public interface ValidUserSettingInnerService extends ValidUserSettingService {

    ValidUserSettingEntity findBySiteId(Long siteId);

    List<ValidUserSettingEntity> findAll();

    // 保存有效会员设置信息
    boolean saveSetting(ValidUserSettingEntity entity);

    boolean updateAllColumn(ValidUserSettingEntity entity);
}
