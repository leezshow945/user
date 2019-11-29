package com.jq.user.proxy.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jq.framework.core.exception.Assert;
import com.jq.platform.site.dto.SiteDTO;
import com.jq.platform.site.service.SiteService;
import com.jq.user.code.UserCodeEnum;
import com.jq.user.constant.UserCfg;
import com.jq.user.constant.UserConstant;
import com.jq.user.exception.UserException;
import com.jq.user.proxy.dao.UserProxyDao;
import com.jq.user.proxy.dto.UserProxyDTO;
import com.jq.user.proxy.entity.UserProxyEntity;
import com.jq.user.proxy.service.UserProxyInnerService;
import com.jq.user.support.DateUtil;
import com.jq.user.support.ListUtils;
import com.liying.common.service.ApiResult;
import com.liying.common.service.RPCResult;
import com.liying.common.util.Results;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserProxyServiceImpl extends ServiceImpl<UserProxyDao, UserProxyEntity> implements UserProxyInnerService {
    @Resource
    private UserProxyDao userProxyDao;
    @Resource
    private SiteService rpcSiteService;

    @Override
    public Integer initDefault(Long defaultId, Long siteId, String siteCode) {
        UserProxyEntity userProxyEntity = new UserProxyEntity();
        userProxyEntity.setId(IdWorker.getId());
        userProxyEntity.setUserId(defaultId);
        userProxyEntity.setHighUserId(defaultId);
        userProxyEntity.setUserName(UserCfg.DEFAULT_SYS_USER_NAME);
        userProxyEntity.setLevel(0);
        userProxyEntity.setHighUserName(UserCfg.DEFAULT_SYS_USER_NAME);
        userProxyEntity.setSiteId(siteId);
        userProxyEntity.setSiteCode(siteCode);
        userProxyEntity.setCreateTime(new Date());
        userProxyEntity.setUpdateTime(new Date());
        return userProxyDao.insert(userProxyEntity);
    }

    @Override
    @Transactional
    public Integer saveUserProxy(Long highUserId, Long userId, String userName) {
        // 插入自身数据
        List<UserProxyEntity> highUserProxyList = list(new QueryWrapper<UserProxyEntity>().eq("high_user_id", highUserId).eq("level", 0));
        UserProxyEntity highUser = highUserProxyList.get(0);
        Date now = new Date();
        List<UserProxyEntity> userProxyList = new ArrayList<>();
        UserProxyEntity userProxy = new UserProxyEntity();
        userProxy.setHighUserId(userId);
        userProxy.setLevel(0);
        userProxy.setUserId(userId);
        userProxy.setSiteId(highUser.getSiteId());
        userProxy.setSiteCode(highUser.getSiteCode());
        userProxy.setUserName(userName);
        userProxy.setHighUserName(userName);
        userProxy.setCreateTime(now);
        userProxy.setUpdateTime(now);
        userProxyList.add(userProxy);
        // 插入直属上级关联的数据
        UserProxyEntity highUserProxyEntity = new UserProxyEntity();
        highUserProxyEntity.setHighUserId(highUserId);
        highUserProxyEntity.setLevel(1);
        highUserProxyEntity.setUserId(userId);
        highUserProxyEntity.setSiteId(highUser.getSiteId());
        highUserProxyEntity.setSiteCode(highUser.getSiteCode());
        highUserProxyEntity.setHighUserName(highUser.getUserName());
        highUserProxyEntity.setUserName(userName);
        highUserProxyEntity.setCreateTime(now);
        highUserProxyEntity.setUpdateTime(now);
        userProxyList.add(highUserProxyEntity);

        if (UserCfg.DEFAULT_SYS_USER_NAME.equals(highUser.getUserName())) {
            //如果父级ID为厅主代表他是厅主的角色,则只插入自身数据
            return userProxyDao.insertBatch(userProxyList);
        }
        //查询父级的所有上级
        List<UserProxyEntity> allHighUserProxyList = list(new QueryWrapper<UserProxyEntity>().eq("user_id", highUserId).ne(true, "level", UserConstant.IS_ZERO));
        //循环父级并和新插入的数据关联
        for (UserProxyEntity allHighUserProxy : allHighUserProxyList) {
            UserProxyEntity highProxy = new UserProxyEntity();
            highProxy.setUserId(userId);
            highProxy.setLevel(allHighUserProxy.getLevel() + 1);
            highProxy.setHighUserName(allHighUserProxy.getHighUserName());
            highProxy.setUserName(userName);
            highProxy.setHighUserId(allHighUserProxy.getHighUserId());
            highProxy.setSiteId(allHighUserProxy.getSiteId());
            highProxy.setSiteCode(allHighUserProxy.getSiteCode());
            highProxy.setCreateTime(now);
            highProxy.setUpdateTime(now);
            userProxyList.add(highProxy);
        }
        return userProxyDao.insertBatch(userProxyList);
    }

    @Override
    public List<UserProxyEntity> getDirSubUserProxy(Long userId, Long siteId) {
        QueryWrapper<UserProxyEntity> ew = new QueryWrapper<>();
        ew.eq("high_user_id", userId);
        ew.eq("site_id", siteId);
        ew.eq("level", 1);
        return list(ew);
    }

    @Override
    public List<UserProxyEntity> getAllSubUserProxy(Long userId, Long siteId) {
        QueryWrapper<UserProxyEntity> ew = new QueryWrapper<>();
        ew.eq("high_user_id", userId);
        ew.eq("site_id", siteId).ne(true, "level", UserConstant.IS_ZERO);
        return list(ew);
    }

    @Override
    public Map<Long, List<Long>> getAllSubUserMapByIdList(List<Long> userIdList) {
        QueryWrapper<UserProxyEntity> ew = new QueryWrapper<>();
        ew.select("high_user_id,user_id");
        ew.ne(true, "level", UserConstant.IS_ZERO);
        ew.in("high_user_id", userIdList);
        List<Map<String, Object>> list = listMaps(ew);
        Map<Long, List<Long>> userIdMap = new HashMap<>();
        for (Long highUserId : userIdList) {
            List<Long> subUserIdList = new ArrayList<>();
            for (Map<String, Object> map : list) {
                Object id = map.get("high_user_id");
                if (id.equals(highUserId)) {
                    Object userIdObj = map.get("user_id");
                    Long userId = Long.parseLong(String.valueOf(userIdObj));
                    subUserIdList.add(userId);
                }
            }
            userIdMap.put(highUserId, subUserIdList);
        }
        return userIdMap;
    }

    @Override
    public UserProxyEntity getDirHighUserProxy(Long userId, Long siteId) {
        QueryWrapper<UserProxyEntity> ew = new QueryWrapper<>();
        ew.eq("user_id", userId);
        ew.eq("site_id", siteId);
        ew.eq("level", 1);
        return getOne(ew);
    }

    @Override
    public List<UserProxyEntity> getAllHighUserProxy(Long userId, Long siteId) {
        return list(new QueryWrapper<UserProxyEntity>().eq("user_id", userId).eq("site_id", siteId).ne(true, "level", UserConstant.IS_ZERO));
    }

    @Override
    public String getUserProxyLine(Long userId, Long siteId) {
        List<UserProxyEntity> allProxyLine = list(new QueryWrapper<UserProxyEntity>().eq("user_id", userId).eq("site_id", siteId));
        if (CollectionUtil.isEmpty(allProxyLine)) {
            return null;
        }
        Map<Integer, String> map = new HashMap<>();
        for (UserProxyEntity userProxyEntity : allProxyLine) {
            map.put(userProxyEntity.getLevel(), userProxyEntity.getHighUserName());
        }
        StringBuilder userProxy = new StringBuilder();
        for (int i = 0; i < allProxyLine.size(); i++) {
            userProxy.append(map.get((allProxyLine.size() - 1) - i)).append("-");
        }
        userProxy.deleteCharAt(userProxy.length() - 1);
        return userProxy.toString();
    }

    @Override
    public String getPathLine(Long userId, Long siteId) {
        List<UserProxyEntity> allProxyLine = list(new QueryWrapper<UserProxyEntity>().eq("user_id", userId).eq("site_id", siteId).ne(true, "level", UserConstant.IS_ZERO));
        if (CollectionUtil.isEmpty(allProxyLine)) {
            return null;
        }
        Map<Integer, String> map = new HashMap<>();
        for (UserProxyEntity userProxyEntity : allProxyLine) {
            map.put(userProxyEntity.getLevel(), userProxyEntity.getHighUserName());
        }
        StringBuilder userProxy = new StringBuilder();
        for (int i = 0; i < allProxyLine.size(); i++) {
            userProxy.append(map.get((allProxyLine.size()) - i)).append("-");
        }
        userProxy.deleteCharAt(userProxy.length() - 1);
        return userProxy.toString();
    }

    @Override
    public Integer countAllSubUserLine(Long userId, Long siteId) {
        QueryWrapper<UserProxyEntity> ew = new QueryWrapper<>();
        ew.eq("high_user_id", userId);
        ew.eq("site_id", siteId);
        ew.ne(true, "level", UserConstant.IS_ZERO);
        return count(ew);
    }

    @Override
    public Map<Long, Integer> countAllSubUserLineByList(List<Long> idList) {
        QueryWrapper<UserProxyEntity> ew = new QueryWrapper<>();
        ew.select("high_user_id,count(*) num");
        ew.in("high_user_id", idList);
        ew.ne(true, "level", UserConstant.IS_ZERO);
        ew.groupBy("high_user_id");
        List<Map<String, Object>> list = listMaps(ew);
        Map<Long, Integer> dataMap = new HashMap<>();
        for (Map<String, Object> map : list) {
            dataMap.put(Long.parseLong(String.valueOf(map.get("high_user_id"))),
                    Integer.parseInt(String.valueOf(map.get("num"))));
        }
        return dataMap;
    }

    @Override
    public Map<Long, Integer> getUserLevelMap(List<Long> userIdList, Long siteId) {
        QueryWrapper<UserProxyEntity> ew = new QueryWrapper<>();
        ew.select("user_id,max(`level`) as level");
        ew.in("user_id", userIdList);
        ew.eq("site_id", siteId);
        ew.groupBy("user_id");
        List<Map<String, Object>> list = userProxyDao.selectMaps(ew);
        Map<Long, Integer> dataMap = new HashMap<>();
        for (Map<String, Object> map : list) {
            dataMap.put(Long.parseLong(String.valueOf(map.get("user_id"))), Integer.parseInt(String.valueOf(map.get("level"))));
        }
        return dataMap;
    }

    @Override
    public Integer findUserLevel(Long userId, Long siteId) {
        QueryWrapper<UserProxyEntity> ew = new QueryWrapper<>();
        ew.select("max(`level`) as level");
        ew.eq("user_id", userId);
        ew.eq("site_id", siteId);
        UserProxyEntity userProxyEntity = getOne(ew);
        if (userProxyEntity == null) {
            return null;
        }
        return userProxyEntity.getLevel();
    }

    @Override
    @Transactional
    public boolean transferUserProxy(Long sourceId, Long destId, Long siteId) {
        Integer deleteCount = userProxyDao.deleteUserProxy(sourceId, siteId);
        List<UserProxyEntity> userProxyEntities = userProxyDao.selectNewProxyToInsert(sourceId, destId, siteId);
        Integer integerCount = userProxyDao.insertBatch(userProxyEntities);
        return deleteCount > 0 && integerCount > 0;
    }

    @Override
    public boolean checkSourceLevelIsNoHighDest(Long sourceId, Long destId, Long siteId) {
        QueryWrapper<UserProxyEntity> ew = new QueryWrapper<>();
        ew.eq("high_user_id", sourceId);
        ew.eq("user_id", destId);
        ew.eq("site_id", siteId);
        Integer num = userProxyDao.selectCount(ew);
        return num > 0;
    }

    @Override
    @Transactional
    public boolean transferBatch(List<Long> sourceIdList, Long destId, Long siteId) {
        boolean deleteSuccess = userProxyDao.deleteForTransferBatch(sourceIdList, siteId);
        List<UserProxyEntity> userProxyEntityList = userProxyDao.selectForTransferBatch(sourceIdList, destId, siteId);
        Integer integerCount = userProxyDao.insertBatch(userProxyEntityList);
        return deleteSuccess && integerCount > 0;
    }

    @Override
    public List<Long> userIdToList(List<UserProxyEntity> list) {
        List<Long> idList = new ArrayList<>();
        if (CollectionUtil.isEmpty(list)) {
            return idList;
        }
        for (UserProxyEntity userProxyEntity : list) {
            idList.add(userProxyEntity.getUserId());
        }
        return idList;
    }

    @Override
    public List<Long> highUserIdToList(List<UserProxyEntity> list) {
        List<Long> idList = new ArrayList<>();
        if (CollectionUtil.isEmpty(list)) {
            return idList;
        }
        for (UserProxyEntity userProxyEntity : list) {
            idList.add(userProxyEntity.getHighUserId());
        }
        return idList;
    }

    @Override
    public List<Long> getAllHighUserIdList(Long userId, Long siteId) {
        List<UserProxyEntity> allHighUserProxy = getAllHighUserProxy(userId, siteId);
        return highUserIdToList(allHighUserProxy);
    }

    @Override
    public Map<Long, UserProxyEntity> getDirHighUserProxyMapByIdList(List<Long> idList, Long siteId) {
        QueryWrapper<UserProxyEntity> ew = new QueryWrapper<>();
        ew.in("user_id", idList);
        ew.eq("level", 1);
        ew.eq("site_id", siteId);
        List<UserProxyEntity> userProxyEntities = list(ew);
        Map<Long, UserProxyEntity> dataMap = new HashMap<>();
        for (UserProxyEntity userProxyEntity : userProxyEntities) {
            dataMap.put(userProxyEntity.getUserId(), userProxyEntity);
        }
        return dataMap;
    }

    @Override
    public String getPath(Long userId, Long siteId) {
        List<UserProxyEntity> allHighUserProxy = getAllHighUserProxy(userId, siteId);
        if (CollectionUtil.isEmpty(allHighUserProxy)) {
            return null;
        }
        Map<Integer, Long> map = new HashMap<>();
        for (UserProxyEntity userProxyEntity : allHighUserProxy) {
            map.put(userProxyEntity.getLevel(), userProxyEntity.getHighUserId());
        }
        StringBuilder userProxy = new StringBuilder();
        for (int i = 0; i < allHighUserProxy.size(); i++) {
            userProxy.append(map.get((allHighUserProxy.size()) - i)).append(",");
        }
        userProxy.deleteCharAt(userProxy.length() - 1);
        return userProxy.toString();
    }

    @Override
    public Long getSubUserId(Long userId, String userName) {
        QueryWrapper<UserProxyEntity> ew = new QueryWrapper<>();
        ew.eq("high_user_id", userId).eq("user_name", userName);
        List<UserProxyEntity> userProxyEntityList = list(ew);
        if (CollectionUtil.isEmpty(userProxyEntityList)) {
            return null;
        }
        UserProxyEntity userProxyEntity = userProxyEntityList.get(0);
        return userProxyEntity.getUserId();
    }

    @Override
    public List<Integer> getSiteIdAllLevel(Long siteId) {
        QueryWrapper<UserProxyEntity> ew = new QueryWrapper<>();
        ew.select("distinct `level`");
        ew.eq(siteId != null, "site_id", siteId);
        ew.orderByDesc("level");
        List<Map<String, Object>> list = listMaps(ew);
        List<Integer> levelList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            levelList.add((Integer) map.get("level"));
        }
        return levelList;
    }

    @Override
    public Map<Long, List<Long>> getAllHighUserIdMap(List<Long> idList, Long siteId) {
        if (CollectionUtil.isEmpty(idList)) {
            return new HashMap<>();
        }
        QueryWrapper<UserProxyEntity> ew = new QueryWrapper<>();
        ew.in("user_id", idList);
        ew.eq("site_id", siteId);
        ew.ne(true, "level", UserConstant.IS_ZERO);
        Map<Long, List<Long>> dataMap = new HashMap<>();
        List<UserProxyEntity> userProxyEntities = list(ew);
        if (CollectionUtil.isEmpty(userProxyEntities)) {
            return new HashMap<>();
        }
        // 将上级按层级从高到低排序
        userProxyEntities = userProxyEntities.stream().sorted((u1, u2) -> u2.getLevel().compareTo(u1.getLevel())).collect(Collectors.toList());
        for (Long userId : idList) {
            List<Long> highUserId = new ArrayList<>();
            for (UserProxyEntity userProxyEntity : userProxyEntities) {
                if (userId.equals(userProxyEntity.getUserId())) {
                    highUserId.add(userProxyEntity.getHighUserId());
                }
            }
            dataMap.put(userId, highUserId);
        }
        return dataMap;
    }

    @Override
    public Map<Long, List<Long>> getAllSubUserIdMap(List<Long> idList, Long siteId) {
        if (CollectionUtil.isEmpty(idList)) {
            return new HashMap<>();
        }
        QueryWrapper<UserProxyEntity> ew = new QueryWrapper<>();
        ew.in("high_user_id", idList);
        ew.eq("site_id", siteId);
        ew.ne(true, "level", UserConstant.IS_ZERO);
        Map<Long, List<Long>> dataMap = new HashMap<>();
        List<UserProxyEntity> userProxyEntities = list(ew);
        if (CollectionUtil.isEmpty(userProxyEntities)) {
            return new HashMap<>();
        }
        for (Long highUserId : idList) {
            List<Long> userId = new ArrayList<>();
            for (UserProxyEntity userProxyEntity : userProxyEntities) {
                if (highUserId.equals(userProxyEntity.getHighUserId())) {
                    userId.add(userProxyEntity.getUserId());
                }
            }
            dataMap.put(highUserId, userId);
        }
        return dataMap;
    }

    @Override
    public List<Map<String, Integer>> getAllLevelBySiteId(Long siteId, Integer isDemo) {
        return userProxyDao.getAllLevelBySiteId(siteId, isDemo);
    }

    private Map<Long, List<Long>> getAllDirHighUserIdMap(List<Long> idList, Long siteId) {
        QueryWrapper<UserProxyEntity> ew = new QueryWrapper<>();
        ew.in("user_id", idList);
        ew.eq("site_id", siteId);
        ew.eq("level", UserConstant.IS_ONE);
        Map<Long, List<Long>> dataMap = new HashMap<>();
        List<UserProxyEntity> userProxyEntities = list(ew);
        if (CollectionUtil.isEmpty(userProxyEntities)) {
            return new HashMap<>();
        }
        for (UserProxyEntity userProxyEntity : userProxyEntities) {
            List<Long> dirHighUserIdList = new ArrayList<>();
            dirHighUserIdList.add(userProxyEntity.getHighUserId());
            dataMap.put(userProxyEntity.getUserId(), dirHighUserIdList);
        }
        return dataMap;
    }

    private Map<Long, List<Long>> getAllDirSubUserIdMap(List<Long> idList, Long siteId) {
        QueryWrapper<UserProxyEntity> ew = new QueryWrapper<>();
        ew.in("high_user_id", idList);
        ew.eq("site_id", siteId);
        ew.eq("level", UserConstant.IS_ONE);
        Map<Long, List<Long>> dataMap = new HashMap<>();
        List<UserProxyEntity> userProxyEntities = list(ew);
        if (CollectionUtil.isEmpty(userProxyEntities)) {
            return new HashMap<>();
        }
        for (Long highUserId : idList) {
            List<Long> userId = new ArrayList<>();
            for (UserProxyEntity userProxyEntity : userProxyEntities) {
                if (highUserId.equals(userProxyEntity.getHighUserId())) {
                    userId.add(userProxyEntity.getUserId());
                }
            }
            dataMap.put(highUserId, userId);
        }
        return dataMap;
    }

    @Override
    public Map<Long, List<Long>> getAllProxyId(List<Long> idList, Long siteId, Integer proxyRelation) {
        // 1-直属上级，2-直属下级，3-所有下级,4-所有上级
        Assert.isNull(siteId, "siteId缺失");
        Assert.isNull(proxyRelation, "proxyRelation缺失");
        Map<Long, List<Long>> map = new HashMap<>();
        if (CollectionUtil.isEmpty(idList)) {
            return map;
        }
        if (UserConstant.IS_ONE.equals(proxyRelation)) {
            // 直属上级
            map = this.getAllDirHighUserIdMap(idList, siteId);
        } else if (UserConstant.IS_TWO.equals(proxyRelation)) {
            // 直属下级
            map = this.getAllDirSubUserIdMap(idList, siteId);
        } else if (UserConstant.IS_THREE.equals(proxyRelation)) {
            // 所有下级
            map = this.getAllSubUserIdMap(idList, siteId);
        } else if (UserConstant.IS_FOUR.equals(proxyRelation)) {
            // 所有上级
            map = this.getAllHighUserIdMap(idList, siteId);
        }
        return map;
    }

    @Override
    public Map<String, Map<Long, Integer>> getTeamNumberByList(List<Long> highUserIdList, String startDate, String endDate, String siteCode) {
        Assert.isEmpty(startDate, "开始时间为空");
        Assert.isEmpty(endDate, "结束时间为空");
        Assert.isEmpty(siteCode, "siteCode为空");
        if (CollectionUtil.isEmpty(highUserIdList)) {
            throw new UserException(UserCodeEnum.USER_ID_LIST_EMPTY.getCode(), UserCodeEnum.USER_ID_LIST_EMPTY.getMessage());
        }
        ApiResult<SiteDTO> siteDTOByCode = rpcSiteService.findSiteDTOByCode(siteCode);
        if (!Results.isSuccess(siteDTOByCode)) {
            throw new UserException(siteDTOByCode.getCode(), siteDTOByCode.getMessage());
        }
        Long siteId = siteDTOByCode.getData().getId();
        Date start = DateUtil.strToYYMMDDDate(startDate);
        // 获取7天前的日期
        DateTime dateTime = cn.hutool.core.date.DateUtil.offsetDay(start, -7);
        String date7Ago = DateUtil.dateToYYYYMMDDString(dateTime);

        List<Map<String, Object>> mapsNumber = userProxyDao.getTeamNumberByList(highUserIdList, siteId);
        Map<Long, Integer> teamNumber = new HashMap<>(16);
        Map<Long, Integer> teamNewNumber = new HashMap<>(16);
        Map<Long, Integer> teamNotLogin = new HashMap<>(16);
        Map<Long, List<Long>> teamUserId = new HashMap<>(16);
        for (Map<String, Object> map : mapsNumber) {
            // 判断是否有userId或highUserId
            if (!map.containsKey("highUserId") || !map.containsKey("userId")) {
                continue;
            }
            Long highUserId = Long.valueOf(map.get("highUserId").toString());
            Long userId = Long.valueOf(map.get("userId").toString());
            if (teamNumber.containsKey(highUserId)) {
                teamNumber.put(highUserId, teamNumber.get(highUserId) + 1);
                // 保存团队id
                List<Long> longList = teamUserId.get(highUserId);
                longList.add(userId);
                teamUserId.put(highUserId, longList);
            } else {
                List<Long> userIdList = new ArrayList<>();
                userIdList.add(userId);
                teamNumber.put(highUserId, 1);
                // 保存团队id
                teamUserId.put(highUserId, userIdList);
            }

            // 保存团队单位时间内新增人数
            String createTime = null;
            if (map.containsKey("createTime")) {
                createTime = map.get("createTime").toString();
                if (createTime.compareTo(startDate) >= 0 && createTime.compareTo(endDate) <= 0) {
                    if (teamNewNumber.containsKey(highUserId)) {
                        teamNewNumber.put(highUserId, teamNewNumber.get(highUserId) + 1);
                    } else {
                        teamNewNumber.put(highUserId, 1);
                    }
                }
            }

            // 保存团队单位时间内7天未登录人数
            String lastLoginTime = null;
            if (map.containsKey("lastLoginTime")) {
                lastLoginTime = map.get("lastLoginTime").toString();
            } else {
                lastLoginTime = createTime;
            }
            if (createTime != null && lastLoginTime.compareTo(date7Ago) < 0) {
                if (teamNotLogin.containsKey(highUserId)) {
                    teamNotLogin.put(highUserId, teamNotLogin.get(highUserId) + 1);
                } else {
                    teamNotLogin.put(highUserId, 1);
                }
            }
        }
        Map<String, Map<Long, Integer>> resultMap = new HashMap<>(3);

        resultMap.put(UserConstant.ProxyNumberFiled.TEAM_NUMBER, teamNumber);
        resultMap.put(UserConstant.ProxyNumberFiled.TEAM_NEW_NUMBER, teamNewNumber);
        resultMap.put(UserConstant.ProxyNumberFiled.TEAM_NOT_LOGIN, teamNotLogin);
        return resultMap;
    }

    @Override
    public Map<String, Integer> getTeamNewNumberGroupByDate(Long userId, String startDate, String endDate, String siteCode) {
        Assert.isNull(userId, "查询用户id为空");
        Assert.isEmpty(startDate, "开始时间为空");
        Assert.isEmpty(endDate, "结束时间为空");
        Assert.isEmpty(siteCode, "siteCode为空");

        ApiResult<SiteDTO> siteDTOByCode = rpcSiteService.findSiteDTOByCode(siteCode);
        if (!Results.isSuccess(siteDTOByCode)) {
            throw new UserException(siteDTOByCode.getCode(), siteDTOByCode.getMessage());
        }
        Long siteId = siteDTOByCode.getData().getId();
        QueryWrapper<UserProxyEntity> entityWrapper = new QueryWrapper<>();
        entityWrapper.select("DATE_FORMAT(create_time, '%Y-%m-%d') createTime, count(id) number");
        entityWrapper.eq("high_user_id", userId);
        entityWrapper.eq("site_id", siteId);
        entityWrapper.between("create_time", startDate + " 00:00:00", endDate + " 23:59:59");
        entityWrapper.groupBy("DATE_FORMAT(create_time, '%Y-%m-%d')");
        List<Map<String, Object>> teamNumberGroupByDate = userProxyDao.selectMaps(entityWrapper);
        Map<String, Integer> newNumber = new HashMap<>(16);
        if (CollectionUtil.isEmpty(teamNumberGroupByDate)) {
            return newNumber;
        }
        for (Map<String, Object> map : teamNumberGroupByDate) {
            newNumber.put(map.get("createTime").toString(), Integer.valueOf(map.get("number").toString()));
        }
        return newNumber;
    }

    @Override
    public List<Long> getSubUserId(Long userId, String userName,boolean isLike) {
        QueryWrapper<UserProxyEntity> ew = new QueryWrapper<>();
        ew.eq("high_user_id", userId).like(isLike, "user_name", userName);
        List<UserProxyEntity> userProxyEntityList = list(ew);
        if (CollectionUtil.isEmpty(userProxyEntityList)) {
            return null;
        }
        List<Long> idList = new ArrayList<>();
        for (UserProxyEntity userProxyEntity : userProxyEntityList) {
            idList.add(userProxyEntity.getUserId());
        }
        return idList;
    }

    @Override
    public ApiResult<List<Long>> getAllHighUserIdListApi(List<Long> idList, Long siteId, Boolean isContain) {
        if (CollectionUtil.isEmpty(idList)) {
            return Results.success(new ArrayList<>());
        }
        QueryWrapper<UserProxyEntity> ew = new QueryWrapper<>();
        ew.select("distinct high_user_id");
        ew.in("user_id", idList);
        ew.eq("site_id", siteId);
        ew.ne(!isContain, "level", UserConstant.IS_ZERO);
        List<UserProxyEntity> userProxyEntities = userProxyDao.selectList(ew);
        List<Long> list = this.highUserIdToList(userProxyEntities);
        return Results.success(list);
    }

    @Override
    public ApiResult<List<UserProxyDTO>> getAllSubUserDirHighUserApi(Long userId, Long siteId) {
        Assert.isNull(userId, "userId为空");
        Assert.isNull(siteId, "siteId为空");
        QueryWrapper<UserProxyEntity> ew = new QueryWrapper<>();
        ew.eq("high_user_id", userId);
        ew.eq("site_id", siteId);
        List<UserProxyEntity> allSubUserList = userProxyDao.selectList(ew);
        if (CollectionUtil.isEmpty(allSubUserList)) {
            return RPCResult.success(new ArrayList<>());
        }
        List<Long> allSubUserIdList = this.userIdToList(allSubUserList);
        ew = new QueryWrapper<>();
        ew.in("user_id", allSubUserIdList);
        ew.eq("site_id", siteId);
        ew.eq("level", UserConstant.IS_ONE);
        List<UserProxyEntity> userProxyEntities = userProxyDao.selectList(ew);
        List<UserProxyDTO> userProxyDTOList = (List<UserProxyDTO>) ListUtils.listCopy(userProxyEntities, UserProxyDTO.class);
        return RPCResult.success(userProxyDTOList);
    }

    @Override
    public ApiResult<List<Map<String, Integer>>> getAllLevelBySiteIdApi(Long siteId, Integer isDemo) {
        Assert.isNull(siteId, "站点id为空");
        List<Map<String, Integer>> allLevelBySiteId = this.getAllLevelBySiteId(siteId, isDemo);
        return RPCResult.success(allLevelBySiteId);
    }

    @Override
    public ApiResult<Map<Long, List<Long>>> getAllProxyIdApi(UserProxyDTO dto) {
        Map<Long, List<Long>> allProxy = this.getAllProxyId(dto.getIdList(), dto.getSiteId(), dto.getProxyRelation());
        return RPCResult.success(allProxy);
    }

    @Override
    public ApiResult<Map<String, Map<Long, Integer>>> getTeamNumberByListApi(List<Long> highUserIdList, String startDate, String endDate, String siteCode) {
        Map<String, Map<Long, Integer>> teamNumberByList = this.getTeamNumberByList(highUserIdList, startDate, endDate, siteCode);
        return RPCResult.success(teamNumberByList);
    }

    @Override
    public ApiResult<Map<String, Integer>> getTeamNewNumberGroupByDateApi(Long userId, String startDate, String endDate, String siteCode) {
        Map<String, Integer> teamNumberByList = this.getTeamNewNumberGroupByDate(userId, startDate, endDate, siteCode);
        return RPCResult.success(teamNumberByList);
    }

}
