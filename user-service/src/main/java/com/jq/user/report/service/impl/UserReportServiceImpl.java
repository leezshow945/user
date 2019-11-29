package com.jq.user.report.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jq.user.customer.dao.UserDao;
import com.jq.user.customer.entity.UserEntity;
import com.jq.user.report.service.UserReportInnerService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.RPCResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserReportServiceImpl implements UserReportInnerService {
    @Resource
    private UserDao userDao;

    @Override
    public ApiResult<Map<Long, Integer>> getUserUnLoginNumOfDaysApi(List<Long> userIdList) {
            Map<Long, Integer> userUnLoginNumOfDays = this.getUserUnLoginNumOfDays(userIdList);
            return RPCResult.success(userUnLoginNumOfDays);
    }

    @Override
    public Map<Long, Integer> getUserUnLoginNumOfDays(List<Long> userIdList) {
        if (CollectionUtil.isEmpty(userIdList)){
            return null;
        }
        QueryWrapper<UserEntity> ew = new QueryWrapper<>();
        ew.select(" id , datediff(now(),ifnull(last_login_time,create_time)) as num ");
        ew.in("id",userIdList);
        Map<Long,Integer> dataMap = new HashMap<>();
        List<Map<String, Object>> list = userDao.selectMaps(ew);
        for (Map<String, Object> map : list) {
            Long userId = Long.parseLong(String.valueOf(map.get("id")));
            Integer num = Integer.parseInt(String.valueOf(map.get("num")));
            dataMap.put(userId,num);
        }
        return dataMap;
    }
}
