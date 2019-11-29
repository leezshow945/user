package com.jq.user.customer.service.impl;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.user.constant.UserConstant;
import com.jq.user.customer.dao.BlacklistDao;
import com.jq.user.customer.entity.BlacklistEntity;
import com.jq.user.customer.service.BlacklistInnerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class BlacklistServiceImpl implements BlacklistInnerService {
    @Resource
    private BlacklistDao blacklistDao;

    /**
     * 主键id查询黑名单
     *
     * @param id 主键id
     * @return BlacklistEntity
     */
    @Override
    public BlacklistEntity selectById(Long id) {
        return blacklistDao.selectById(id);
    }

    /**
     * 列表条件查询黑名单信息
     *
     * @param blacklistEntity Entity对象
     * @param page            分页对象
     * @return Map<String,Object>
     */
    @Override
    public Map<String, Object> queryList(BlacklistEntity blacklistEntity, Page page) {
        blacklistEntity.setIsDel(UserConstant.IS_F);
        IPage iPage = blacklistDao.selectPage(page, new QueryWrapper<>(blacklistEntity));
        Map<String, Object> map = new HashMap<>();
        map.put("total", page.getTotal());
        map.put("data", iPage.getRecords());
        return map;
    }

    /**
     * 新增黑名单信息
     *
     * @param blacklistEntity Entity对象
     * @return Boolean
     */
    @Override
    public Boolean insert(BlacklistEntity blacklistEntity) {
        return blacklistDao.insert(blacklistEntity) == 1;
    }

    /**
     * 更新黑名单信息
     *
     * @param blacklistEntity Entity对象
     * @return Boolean
     */
    @Override
    public Boolean update(BlacklistEntity blacklistEntity) {
        return blacklistDao.updateById(blacklistEntity) == 1;
    }


    /**
     * 批量(单个)删除
     * @param ids 主键id字符串
     * @return Boolean
     */
    @Override
    public Boolean deleteBatchIds(String ids) {
        String[] idArray = ids.split(",");
        ArrayList<String> idList = CollUtil.newArrayList(idArray);
        return blacklistDao.deleteBatchIds(idList) > 0;
    }

}