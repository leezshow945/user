package com.jq.user.log.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.user.log.entity.LogUserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author:levi
 * @Descript:
 * @Date: 2018/4/4
 */
public interface LogUserInnerService extends LogUserService {

    boolean save(LogUserEntity log);

    List<Map<String, Object>> queryUserLogByRecord(Long siteId, String userName, String ip, String accountType, String startTime, String endTime, Integer isDiffArea, Page<LogUserEntity> page);

    boolean updateById(Long id);

    void update(LogUserEntity logUser);

    List<Map<String, Object>> qryUserLoginLog(Long siteId, Integer searchType, String keyword, Integer sort, String startTime, String endTime, Integer isDiffArea, Page page);

    boolean deleteBatch(ArrayList<Long> idsList);

    @Deprecated
    boolean saveLog(Integer plat, String userName, String content, Long loginId, String ip, String loginArea, Integer isDiffAreaLogin,
                    String type, String accountType, Long siteId, String flagType, String loginUrl);

    /**
     * 保存用户日志记录
     *
     * @param userId       用户id
     * @param userName     用户账号
     * @param platType     平台类型（1-pc,2-app）
     * @param content      日志内容（CommonEnum.USER_LOG_TYPE_..：具体项描述）== 登录/登出-登录/登出成功
     * @param type         用户日志类型 CommonEnum.USER_LOG_TYPE_...
     * @param accountType  账号类型(系统用户SYS; 会员USER;站点SITE;试玩DEMO) CommonEnum.ACCOUNT_TYPE_...
     * @param siteId       站点id
     * @param flagType     日志分类 CommonEnum.USER_LOG_FLAG_TYPE_FLAG_TYPE_...
     * @param loginIp      录ip
     * @param loginUrl     登录url
     * @return
     * @author cjf
     */
    boolean addUserLog(Long userId, String userName, Integer platType, String content, String type,
                       String accountType, Long siteId, String flagType, String loginIp, String loginUrl);

    boolean addUserLog(Long userId, String userName, Integer platType, String content, String type,
                       String accountType, Long siteId, String flagType, String loginIp, String loginUrl,
                       String loginArea,String siteCode);

    Integer getLoginCountByDay(Long id);
}
