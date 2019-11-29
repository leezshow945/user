package com.jq.user.valid.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jq.user.valid.entity.ValidUserSettingEntity;

public interface ValidUserSettingDao extends BaseMapper<ValidUserSettingEntity> {
    boolean updateAllColumn(ValidUserSettingEntity entity);
}
