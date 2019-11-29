package com.jq.user.index.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jq.framework.core.exception.Assert;
import com.jq.user.constant.UserConstant;
import com.jq.user.customer.dao.UserDao;
import com.jq.user.customer.entity.UserEntity;
import com.jq.user.index.dto.IndexDTO;
import com.jq.user.index.service.IndexInnerService;
import com.jq.user.log.dao.LogUserDao;
import com.jq.user.support.AddressUtil;
import com.liying.cash.pay.api.DepositService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.RPCResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class IndexServiceImpl implements IndexInnerService {

    @Resource
    private UserDao userDao;
    @Resource
    private LogUserDao logUserDao;
    @Resource
    private DepositService rpcDepositService;

    @Override
    public ApiResult<List<Long>> findNewUserIdList(String startDate, String endDate, Object siteSign) {
        Assert.isNull(startDate, "开始日期为空");
        Assert.isNull(endDate, "结束日期为空");
        QueryWrapper<UserEntity> ew = new QueryWrapper<>();
        DateTime start = DateUtil.beginOfDay(DateUtil.parseDate(startDate));
        DateTime end = DateUtil.endOfDay(DateUtil.parseDate(endDate));
        ew.select("id as userId");
        ew.eq("is_demo", UserConstant.IS_ZERO);
        ew.ge("create_time", start);
        ew.le("create_time", end);
        ew.eq(siteSign instanceof Long, "site_id", siteSign);
        ew.eq(siteSign instanceof String, "site_code", siteSign);
        List<Map<String,Object>> objects = userDao.selectMaps(ew);
        if (CollectionUtil.isEmpty(objects)) {
            return RPCResult.success(new ArrayList<>());
        }
        List<Long> idList = new ArrayList<>();
        for (Map<String,Object> map : objects) {
            idList.add((Long)map.get("userId"));
        }
        return RPCResult.success(idList);
    }

    @Override
    public ApiResult<Map<Long, String>> findNewUserMapApi(String startDate, String endDate, Object siteSign) {
        Assert.isEmpty(startDate, "开始日期为空");
        Assert.isEmpty(endDate, "结束日期为空");
        Assert.isNull(siteSign, "siteCode或siteId为空");
        QueryWrapper<UserEntity> ew = new QueryWrapper<>();
        DateTime start = DateUtil.beginOfDay(DateUtil.parseDate(startDate));
        DateTime end = DateUtil.endOfDay(DateUtil.parseDate(endDate));
        ew.select("id", "create_time");
        ew.eq("is_demo", UserConstant.IS_ZERO);
        ew.eq("is_del", UserConstant.IS_F);
        ew.ge("create_time", start);
        ew.le("create_time", end);
        ew.eq(siteSign instanceof Long, "site_id", siteSign);
        ew.eq(siteSign instanceof String, "site_code", siteSign);
        List<UserEntity> userEntityList = userDao.selectList(ew);
        Map<Long, String> userMap = new HashMap<>(userEntityList.size());
        if(CollectionUtil.isNotEmpty(userEntityList)){
            for(UserEntity userEntity : userEntityList){
                userMap.put(userEntity.getId(), DateUtil.formatDate(userEntity.getCreateTime()));
            }
        }
        return RPCResult.success(userMap);
    }

    @Override
    public ApiResult<Map<String, Integer>> countNewUserByDateGroup(String startDate, String endDate, Long siteId, Integer isProxy) {
        Assert.isNull(startDate, "开始日期为空");
        Assert.isNull(endDate, "结束日期为空");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("siteId", siteId);
        paramMap.put("isProxy", isProxy);
        if (DateUtil.today().equals(startDate)) {
            // 如果日期是当天,则查0~24h会员注册量
            paramMap.put("startDate", startDate);
            List<Map<String, String>> list = userDao.countNewUserByHourGroup(paramMap);
            Map<String, Integer> dataMap = new HashMap<>();
            for (Map<String, String> map : list) {
                String hour = map.get("hour");
                Integer num = Integer.parseInt(String.valueOf(map.get("num")));
                dataMap.put(hour, num);
            }
            return RPCResult.success(dataMap);
        }
        DateTime start = DateUtil.beginOfDay(DateUtil.parseDate(startDate));
        DateTime end = DateUtil.endOfDay(DateUtil.parseDate(endDate));
        paramMap.put("startTime", start);
        paramMap.put("endTime", end);
        List<Map<String, Object>> list = userDao.countNewUserByDateGroup(paramMap);
        Map<String, Integer> dataMap = new HashMap<>();
        for (Map<String, Object> map : list) {
            String date = DateUtil.formatDate((Date) map.get("date"));
            Integer num = Integer.parseInt(String.valueOf(map.get("num")));
            dataMap.put(date, num);
        }
        return RPCResult.success(dataMap);
    }

    @Override
    public ApiResult<Integer> countNewUserByDate(String startDate, String endDate, Long siteId, Integer isProxy) {
        Assert.isNull(startDate, "开始日期为空");
        Assert.isNull(endDate, "结束日期为空");
        DateTime start = DateUtil.beginOfDay(DateUtil.parseDate(startDate));
        DateTime end = DateUtil.endOfDay(DateUtil.parseDate(endDate));
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("siteId", siteId);
        paramMap.put("isProxy", isProxy);
        paramMap.put("startTime", start);
        paramMap.put("endTime", end);
        Integer num = userDao.countNewUserByDate(paramMap);
        return RPCResult.success(num);
    }

    @Override
    public ApiResult<Map<String, Integer>> getUserDisc(IndexDTO dto) {
        Assert.isNull(dto.getType(), "查询条件为空");
        Assert.isNull(dto.getSiteId(), "站点id为空");
        Assert.isNull(dto.getSiteCode(), "站点Code为空");
        String startTime = null;
        String endTime = null;
        if (StrUtil.isNotEmpty(dto.getStartDate())) {
            startTime = DateUtil.beginOfDay(DateUtil.parseDate(dto.getStartDate())).toString();
        }
        if (StrUtil.isNotEmpty(dto.getEndDate())) {
            endTime = DateUtil.endOfDay(DateUtil.parseDate(dto.getEndDate())).toString();
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        paramMap.put("siteId", dto.getSiteId());
        Map<String, Integer> dataMap = new HashMap<>();
        if (UserConstant.IS_ONE.equals(dto.getType())) {
            // 全部用户
            List<Map<String, String>> mapList = logUserDao.getUserDiscArea(paramMap);
            dataMap = assembleInfo(mapList);
        }else if (UserConstant.IS_TWO.equals(dto.getType())){
            //有效会员
            List<Map<String, String>> mapList = logUserDao.getEffectiveUserDiscArea(paramMap);
            dataMap = assembleInfo(mapList);
        }else if (UserConstant.IS_THREE.equals(dto.getType())){
            //充值会员
            ApiResult<List<Long>> listApiResult = rpcDepositService.queryuserIdBydeposit(startTime, endTime, dto.getSiteCode(), UserConstant.IS_TWO);
            if (!RPCResult.checkApiResult(listApiResult)){
                return RPCResult.custom(listApiResult.getCode(),listApiResult.getMessage());
            }
            if (CollectionUtil.isEmpty(listApiResult.getData())){
                return RPCResult.success(dataMap);
            }
            paramMap.put("userIdList",listApiResult.getData());
            List<Map<String, String>> mapList = logUserDao.getRechargeUserDiscArea(paramMap);
            dataMap = assembleInfo(mapList);
        }
        // 按数量倒序排序
        dataMap = this.sortByValueDescending(dataMap);
        return RPCResult.success(dataMap);
    }


    private Map<String, Integer> assembleInfo(List<Map<String, String>> mapList) {
        Map<String, Integer> dataMap = new HashMap<>();
        if (CollectionUtil.isEmpty(mapList)){
            return dataMap;
        }
        int otherNum = 0;
        for (Map<String, String> areaMap : mapList) {
            String area = areaMap.get("area");
            Integer num = Integer.valueOf(String.valueOf(areaMap.get("num")));
            if (AddressUtil.isProvince(area)) {
                //补充省后缀
                area = area + "省";
                dataMap.put(area, num);
            } else if (AddressUtil.isMunicipality(area)) {
                // 补充直辖市后缀
                area = area + "市";
                dataMap.put(area, num);
            } else if (AddressUtil.isRegion(area)) {
                // 自治区
                area = area + "自治区";
                dataMap.put(area, num);
            } else if (AddressUtil.isSpecialRegion(area)) {
                // 特别行政区
                area = area + "特别行政区";
                dataMap.put(area, num);
            } else {
                // 其他
                otherNum += num;
            }
        }
        dataMap.put("其他", otherNum);
        return dataMap;
    }

    /**
     * 使用 Map按value进行排序
     * @param map
     * @return
     */
    //降序排序
    private  <K, V extends Comparable<? super V>> Map<K, V> sortByValueDescending(Map<K, V> map)
    {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        list.sort((o1, o2) -> {
            int compare = (o1.getValue()).compareTo(o2.getValue());
            return -compare;
        });
        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
