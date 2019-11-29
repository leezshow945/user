package com.jq.user.customer.service;

import com.jq.user.customer.dto.QueryParamDTO;
import com.jq.user.customer.entity.UserRebateEntity;

import java.util.List;
import java.util.Map;

public interface UserRebateInnerService extends UserRebateService {


    UserRebateEntity getRebate(Long id);

    Map<String, Object> findBaseInfo(Long id);

    List<Map<String, Object>> getUserLevelPath(String userName, Long siteId);

    boolean updateBaseInfo(Map userParamMap);

    Map<String, Object> getLevel(Long siteId);

    Map<String, Object> getRebateByUserNameAndSiteId(String userName, Long siteId);

    Map<String, Object> getRelate(Long userId);

    Map<String, Object> getSubUserList(QueryParamDTO dto);

    List getRebateLevelList(Long siteId);

    boolean isBanRebate(UserRebateEntity userRebateEntity);

    Map<Long,Integer> getUserProxyTypeMap(List<Long> idList);
}
