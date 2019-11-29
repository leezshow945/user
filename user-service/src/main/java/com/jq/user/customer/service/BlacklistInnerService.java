package com.jq.user.customer.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.user.customer.entity.BlacklistEntity;

import java.util.Map;

public interface BlacklistInnerService {

    /**
     * 主键id查询黑名单
     *
     * @param id 主键id
     * @return BlacklistEntity
     */
    BlacklistEntity selectById(Long id);

    /**
     * 列表条件查询黑名单信息
     *
     * @param blacklistEntity Entity对象
     * @param page            分页对象
     * @return Map<String,Object>
     */
    Map<String, Object> queryList(BlacklistEntity blacklistEntity, Page page);

    /**
     * 新增黑名单信息
     *
     * @param blacklistEntity Entity对象
     * @return Boolean
     */
    Boolean insert(BlacklistEntity blacklistEntity);

    /**
     * 更新黑名单信息
     *
     * @param blacklistEntity Entity对象
     * @return Boolean
     */
    Boolean update(BlacklistEntity blacklistEntity);


    /**
     * 批量(单个)删除区域信息
     *
     * @param ids 主键id字符串
     * @return Boolean
     */
    Boolean deleteBatchIds(String ids);

}