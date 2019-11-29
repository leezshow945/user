package com.jq.user.refer.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.user.refer.dto.UserReferDTO;
import com.jq.user.refer.entity.UserReferEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface UserReferDao extends BaseMapper<UserReferEntity> {

    /**
     * 查询所有
     * @return
     */
    List<Map<String,Object>> findAll(Long siteId);

    /**
     * 根据条件查询
     * @param map
     * @return
     */
    List<UserReferDTO> findByCondition(@Param("map") Map map, Page page);

    /**
     * 根据id和状态查询推广链接
     * @param id
     *
     * @param isDel
     * @return
     */
    UserReferEntity selectByIdAndStatus(@Param("id") Long id, @Param("isDel") Integer isDel);

    UserReferDTO selectByReferId(Long id);

    /**
     * 根据id集查询代理账户名
     * @param idList
     * @return
     */
    List<Map<String, Object>> getUserNameById(List<Long> idList);
}
