package com.jq.user.refer.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.user.refer.dto.UserReferDTO;
import com.jq.user.refer.entity.UserReferEntity;

import java.util.List;
import java.util.Map;

/**
 * 〈推广链接内部〉
 *
 * @author Json
 * @create 2018/4/25
 */
public interface UserReferInnerService extends UserReferService {

//    /**
//     * 查询所有
//     *
//     * @return
//     * @param siteId
//     */
//    List<Map<String,Object>>findAll(Long siteId);
//
//    /**
//     * 根据条件查询 分页
//     * @param map
//     * @param page
//     */
//    List<UserReferDTO> findByCondition(Map map, Page page);
//
//    /**
//     * 根据主键查询
//     * @param id
//     * @return
//     */
//    UserReferEntity findById(Long id);

//    /**
//     * 新增推广链接
//     * @param refer
//     * @param tokenInfo
//     * @param linkType 链接类型 0 本站 1 外部链接
//     */
//    boolean save(UserReferEntity refer, String tokenInfo, Integer linkType);

//    /**
//     * 修改推广链接
//     * @param refer
//     * @param tokenInfo
//     */
//    boolean update(UserReferEntity refer, String tokenInfo);
//
//    /**
//     * 删除推广链接
//     * @param id
//     * @param tokenInfo
//     */
//    boolean delete(Long id, String tokenInfo);

//    /**
//     * 根据id 修改推广链接状态是否启用
//     * @param id
//     * @param status
//     * @param tokenInfo
//     * @return
//     */
//    boolean updateStatusById(Long id, Integer status, String tokenInfo);

    /**
     * 根据站点id和推广码查询
     * @param siteId 站点id
     * @param referCode 推广码
     * @return
     */
    UserReferEntity findBySiteIdAndReferCode(Long siteId,String referCode);


    UserReferEntity findByUserId(Long userId);

    /**
     * 根据id更新推广码使用次数
     *
     * @param id
     * @return
     */
    boolean updateReferUseCount(Long id);
}
