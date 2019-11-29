package com.jq.user.log.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.framework.core.exception.Assert;
import com.jq.platform.auth.service.IpLimitService;
import com.jq.platform.site.dto.SiteDTO;
import com.jq.platform.site.service.SiteService;
import com.jq.user.code.UserCodeEnum;
import com.jq.user.constant.RedisKey;
import com.jq.user.constant.UserCfg;
import com.jq.user.constant.UserConstant;
import com.jq.user.customer.dao.UserDao;
import com.jq.user.customer.dao.UserTokenDao;
import com.jq.user.customer.entity.UserEntity;
import com.jq.user.customer.service.UserRebateService;
import com.jq.user.exception.UserException;
import com.jq.user.log.dao.LogUserDao;
import com.jq.user.log.dto.HourlyOnLinesDTO;
import com.jq.user.log.dto.LogQryParamDTO;
import com.jq.user.log.dto.LogUserDTO;
import com.jq.user.log.entity.LogUserEntity;
import com.jq.user.log.service.LogUserInnerService;
import com.jq.user.support.PageUtil;
import com.liying.cash.group.api.GroupService;
import com.liying.cash.group.resp.GroupUsersResp;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import com.liying.common.service.RPCResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class LogUserServiceImpl implements  LogUserInnerService {

    private static final Logger logger = LoggerFactory.getLogger(LogUserServiceImpl.class);

    @Resource
    private LogUserDao logUserDao;
    @Resource
    private GroupService groupService;
    @Resource
    private UserDao userDao;
    @Resource
    private UserRebateService userRebateService;
    @Resource
    private UserTokenDao userTokenDao;
    @Resource
    private StringRedisTemplate template;
    @Resource
    private SiteService siteService;
    @Resource
    private IpLimitService rpcIpLimitService;

    @Override
    @Transactional
    public boolean save(LogUserEntity log) {
        String accountType = log.getAccountType();
        if (!UserCfg.SYS.equals(log.getAccountType()) && !UserCfg.SITE.equals(log.getAccountType())) {
            UserEntity userEntity = userDao.selectById(log.getUserId());
            if (userEntity != null) {
                if (UserConstant.IS_ONE.equals(userEntity.getIsDemo())) {
                    accountType = UserCfg.DEMO;
                } else if (UserConstant.IS_TWO.equals(userEntity.getIsDemo())) {
                    accountType = UserCfg.TEST;
                }
            }
        }
        LogUserEntity logEntity = new LogUserEntity();
        BeanUtils.copyProperties(log, logEntity);
        logEntity.setAccountType(accountType);
        logEntity.setIsDel(UserConstant.IS_F);
        logEntity.setCreateTime(new Date());
        logEntity.setUpdateTime(new Date());
        Integer result = logUserDao.insert(logEntity);
        return result > 0;
    }

    @Override
    public List<Map<String, Object>> queryUserLogByRecord(Long siteId, String userName, String ip, String accountType, String startTime, String endTime, Integer isDiffArea, Page<LogUserEntity> page) {
        QueryWrapper<LogUserEntity> ew = new QueryWrapper<>();
        ew.eq("site_id", siteId);
        ew.eq("is_del", UserConstant.IS_F);
        if (StrUtil.isNotEmpty(userName)) {
            ew.eq("user_name", userName);
        }
        if (StrUtil.isNotEmpty(ip)) {
            ew.eq("login_ip", ip);
        }
        if (StrUtil.isNotEmpty(accountType)) {
            ew.eq("account_type", accountType);
        }
        if (isDiffArea != null) {
            ew.eq("is_diff_area_login", isDiffArea);
        }
        if (StrUtil.isNotEmpty(startTime) && StrUtil.isNotEmpty(endTime)) {
            ew.between("login_time", startTime, endTime);
        }
        int count = logUserDao.selectCount(ew);
        if (count == 0) {
            return null;
        }
        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", count);
        IPage<LogUserEntity> iPage = logUserDao.selectPage(page, ew);
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (LogUserEntity logUserEntity : iPage.getRecords()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", logUserEntity.getId() + "");
            map.put("userName", logUserEntity.getUserName());
            map.put("content", logUserEntity.getContent());
            map.put("ip", logUserEntity.getLoginIp());
            map.put("loginTime", DateUtil.formatDateTime(logUserEntity.getLoginTime()));
            map.put("type", logUserEntity.getType());
            map.put("accountType", logUserEntity.getAccountType());
            dataList.add(map);
        }
        dataList.add(pageMap);
        return dataList;
    }

    @Override
    @Transactional
    public boolean updateById(Long id) {
        LogUserEntity logUserEntity1 = logUserDao.selectById(id);
        if (logUserEntity1 == null) {
            throw new UserException(UserCodeEnum.OBJECT_NOT_EXIST.getCode());
        }
        if (UserConstant.IS_T.equals(logUserEntity1.getIsDel())) {
            throw new UserException(UserCodeEnum.OBJECT_DELETED.getCode());
        }
        LogUserEntity logUserEntity = new LogUserEntity();
        logUserEntity.setId(id);
        logUserEntity.setIsDel(UserConstant.IS_T);
        logUserEntity.setUpdateTime(new Date());
        Integer result = logUserDao.updateById(logUserEntity);
        return result == 1;
    }

    @Override
    @Transactional
    public void update(LogUserEntity logUser) {
        LogUserEntity logUserEntity = new LogUserEntity();
        BeanUtils.copyProperties(logUser, logUserEntity);
        logUser.setUpdateTime(new Date());
        logUserDao.updateById(logUserEntity);
    }

    @Override
    @Transactional
    public List<Map<String, Object>> qryUserLoginLog(Long siteId, Integer searchType, String keyword, Integer sort, String startTime, String endTime, Integer isDiffArea, Page page) {
        QueryWrapper<LogUserEntity> ew = new QueryWrapper<>();
        ew.eq("site_id", siteId);
        ew.eq("is_del", UserConstant.IS_F);
        if (searchType == 1 && keyword != null) {
            ew.eq("user_name", keyword);
        } else if (searchType == 2 && keyword != null) {
            ew.eq("login_ip", keyword);
        }
        ew.orderBy(true, sort == 1,"login_time");
        if (StrUtil.isNotEmpty(startTime) && StrUtil.isNotEmpty(endTime)) {
            ew.between("login_time", startTime, endTime);
        }
        if (isDiffArea != null) {
            ew.eq("is_diff_area_login", isDiffArea);
        }
        int count = logUserDao.selectCount(ew);
        if (count == 0) {
            return null;
        }
        IPage<LogUserEntity> iPage = logUserDao.selectPage(page, ew);
        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", count);
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (LogUserEntity logUserEntity : iPage.getRecords()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", logUserEntity.getId() + "");
            map.put("plat", logUserEntity.getPlat());
            map.put("userName", logUserEntity.getUserName());
            map.put("content", logUserEntity.getContent());
            map.put("loginIp", logUserEntity.getLoginIp());
            map.put("loginTime", DateUtil.formatDateTime(logUserEntity.getLoginTime()));
            map.put("loginArea", logUserEntity.getLoginArea());
            map.put("type", logUserEntity.getType());
            map.put("accountType", logUserEntity.getAccountType());
            dataList.add(map);
        }
        dataList.add(pageMap);
        return dataList;
    }

    @Override
    @Transactional
    public boolean deleteBatch(ArrayList<Long> idsList) {
        if (idsList == null || idsList.size() == 0) {
            return false;
        }
        int result = logUserDao.deleteBatch(idsList);
        return result == idsList.size();
    }

    @Override
    @Transactional
    public boolean saveLog(Integer plat, String userName, String content, Long loginId, String loginIp, String loginArea, Integer isDiffAreaLogin, String type, String accountType, Long siteId, String flagType, String loginUrl) {
        LogUserEntity logUserEntity = new LogUserEntity();
        logUserEntity.setPlat(plat);
        logUserEntity.setUserName(userName);
        logUserEntity.setContent(content);
        logUserEntity.setUserId(loginId);
        logUserEntity.setLoginIp(loginIp);
        logUserEntity.setLoginTime(new Date());
        logUserEntity.setLoginArea(loginArea);
        logUserEntity.setIsDiffAreaLogin(isDiffAreaLogin);
        logUserEntity.setType(type);
        logUserEntity.setAccountType(accountType);
        logUserEntity.setSiteId(siteId);
        logUserEntity.setIsDel(UserConstant.IS_F);
        logUserEntity.setFlagType(flagType);
        logUserEntity.setLoginUrl(loginUrl);
        Integer insert = logUserDao.insert(logUserEntity);
        return insert == 1;
    }

    @Override
    @Transactional
    public boolean addUserLog(Long userId, String userName, Integer platType, String content, String type,
                              String accountType, Long siteId, String flagType, String loginIp, String loginUrl) {
        Date now = new Date();
        if (!UserCfg.SYS.equals(accountType) && !UserCfg.SITE.equals(accountType)) {
            UserEntity userEntity = userDao.selectById(userId);
            if (userEntity != null) {
                if (UserConstant.IS_ONE.equals(userEntity.getIsDemo())) {
                    accountType = UserCfg.DEMO;
                } else if (UserConstant.IS_TWO.equals(userEntity.getIsDemo())) {
                    accountType = UserCfg.TEST;
                }
            }
        }
        LogUserEntity logUserEntity = new LogUserEntity();
        logUserEntity.setPlat(platType);
        logUserEntity.setUserName(userName);
        logUserEntity.setContent(content);
        logUserEntity.setUserId(userId);
        logUserEntity.setLoginIp(loginIp);
        logUserEntity.setLoginTime(new Date());
        logUserEntity.setType(type);
        logUserEntity.setAccountType(accountType);
        logUserEntity.setSiteId(siteId);
        logUserEntity.setIsDel(UserConstant.IS_F);
        logUserEntity.setFlagType(flagType);
        logUserEntity.setLoginUrl(loginUrl);
        logUserEntity.setCreateTime(now);
        logUserEntity.setUpdateTime(now);
        ApiResult<String> ipAttribution = null;
        if (StrUtil.isNotEmpty(loginIp)) {
            try {
                ipAttribution = rpcIpLimitService.getIpCountryApi(loginIp);
                logUserEntity.setLoginArea(ipAttribution.getData());
            } catch (Exception e) {
                logUserEntity.setLoginArea("未知");
            }
        }
        Integer insert = logUserDao.insert(logUserEntity);
        return insert == 1;
    }

    @Override
    @Transactional
    public boolean addUserLog(Long userId, String userName, Integer platType, String content, String type,
                              String accountType, Long siteId, String flagType, String loginIp,
                              String loginUrl, String loginArea, String siteCode) {
        Date now = new Date();
        if (!UserCfg.SYS.equals(accountType) && !UserCfg.SITE.equals(accountType)) {
            UserEntity userEntity = userDao.selectById(userId);
            if (userEntity != null) {
                if (UserConstant.IS_ONE.equals(userEntity.getIsDemo())) {
                    accountType = UserCfg.DEMO;
                } else if (UserConstant.IS_TWO.equals(userEntity.getIsDemo())) {
                    accountType = UserCfg.TEST;
                }
            }
        }
        LogUserEntity logUserEntity = new LogUserEntity();
        logUserEntity.setPlat(platType);
        logUserEntity.setUserName(userName);
        logUserEntity.setContent(content);
        logUserEntity.setUserId(userId);
        logUserEntity.setLoginIp(loginIp);
        logUserEntity.setLoginTime(new Date());
        logUserEntity.setLoginArea(loginArea);
        logUserEntity.setType(type);
        logUserEntity.setAccountType(accountType);
        logUserEntity.setSiteId(siteId);
        logUserEntity.setSiteCode(siteCode);
        logUserEntity.setIsDel(UserConstant.IS_F);
        logUserEntity.setFlagType(flagType);
        logUserEntity.setLoginUrl(loginUrl);
        logUserEntity.setCreateTime(now);
        logUserEntity.setUpdateTime(now);
        Integer insert = logUserDao.insert(logUserEntity);
        return insert > 0;
    }

    @Override
    public Integer getLoginCountByDay(Long id) {
        return logUserDao.getLoginCountByDay(id);
    }


    @Override
    @Transactional
    public ApiResult addUserLogApi(LogUserDTO dto) {
        Date now = new Date();
        String accountType = dto.getAccountType();
        if (!UserCfg.SYS.equals(accountType) && !UserCfg.SITE.equals(accountType)) {
            UserEntity userEntity = userDao.selectById(dto.getUserId());
            if (userEntity != null) {
                if (UserConstant.IS_ONE.equals(userEntity.getIsDemo())) {
                    accountType = UserCfg.DEMO;
                } else if (UserConstant.IS_TWO.equals(userEntity.getIsDemo())) {
                    accountType = UserCfg.TEST;
                }
            }
        }
        LogUserEntity logUserEntity = new LogUserEntity();
        logUserEntity.setPlat(dto.getPlat());
        logUserEntity.setUserName(dto.getUserName());
        logUserEntity.setContent(dto.getContent());
        logUserEntity.setUserId(dto.getUserId());
        logUserEntity.setLoginIp(dto.getLoginIp());
        logUserEntity.setLoginTime(now);
        logUserEntity.setLoginArea(dto.getLoginArea());
        logUserEntity.setType(dto.getType());
        logUserEntity.setSiteCode(dto.getSiteCode());
        logUserEntity.setAccountType(accountType);
        logUserEntity.setSiteId(dto.getSiteId());
        logUserEntity.setIsDel(UserConstant.IS_F);
        logUserEntity.setFlagType(dto.getFlagType());
        logUserEntity.setLoginUrl(dto.getLoginUrl());
        logUserEntity.setCreateTime(now);
        logUserEntity.setUpdateTime(now);
        Integer insert = logUserDao.insert(logUserEntity);
        return insert == 1 ? RPCResult.success() : RPCResult.fail();
    }

    @Override
    public ApiResult<PageInfo<LogUserDTO>> qryUserLoginLogApi(LogQryParamDTO dto) {
        QueryWrapper<LogUserEntity> ew = new QueryWrapper<>();
        ew.eq("is_del", UserConstant.IS_F);
        ew.eq("flag_type", UserCfg.USER_LOG_FLAG_TYPE_LOGIN);
        ew.eq(dto.getSiteId() != null,"site_id", dto.getSiteId());
        if (StrUtil.isNotEmpty(dto.getAccountType())) {
            ew.eq("account_type", dto.getAccountType());
        }
        if (StrUtil.isNotEmpty(dto.getType())) {
            ew.eq("type", dto.getType());
        }
        if (StrUtil.isNotEmpty(dto.getKeyword())) {
                // 账号查询
            ew.eq(UserConstant.IS_ONE.equals(dto.getSearchType()),"user_name", dto.getKeyword());
            ew.eq(UserConstant.IS_TWO.equals(dto.getSearchType()),"login_ip", dto.getKeyword());
             if (UserConstant.IS_THREE.equals(dto.getSearchType())) {
                // 用户分组查询
                ApiResult<List<GroupUsersResp>> listApiResult = groupService.queryUsersByGroup(dto.getSiteCode(), dto.getKeyword(), UserConstant.IS_F);
                List<GroupUsersResp> data = listApiResult.getData();
                if (CollectionUtil.isEmpty(data)) {
                    return RPCResult.success(new PageInfo<>(new ArrayList<>(), dto.getPage(), dto.getLimit(), 0));
                }
                List<String> userIdList = new ArrayList<>();
                for (GroupUsersResp datum : data) {
                    userIdList.add(datum.getUserId());
                }
                ew.in(true, "user_id", userIdList);
            } else if (UserConstant.IS_FOUR.equals(dto.getSearchType())) {
                // 代理查询
                UserEntity userParam = new UserEntity();
                userParam.setUserName(dto.getKeyword());
                userParam.setSiteId(dto.getSiteId());
                userParam.setIsDel(UserConstant.IS_F);
                UserEntity userEntity1 = userDao.selectOne(new QueryWrapper<>(userParam));
                if (userEntity1 == null) {
                    return RPCResult.success(new PageInfo<>(new ArrayList<>(), dto.getPage(), dto.getLimit(), 0));
                }
                ApiResult<List<Long>> listApiResult = userRebateService.getProxyByUserId(userEntity1.getId(), 2);
                List<Long> directUserIdList = listApiResult.getData();
                ew.in(true, "user_id", directUserIdList);
            }
        }
        ew.like(UserConstant.IS_FIVE.equals(dto.getSearchType()), "login_url", dto.getKeyword());
        ew.orderBy(true,UserConstant.IS_ONE.equals(dto.getSort()),"login_time");
        if (StrUtil.isNotEmpty(dto.getStartTime()) && StrUtil.isNotEmpty(dto.getEndTime())) {
            ew.between("login_time", dto.getStartTime(), dto.getEndTime());
        }
        ew.eq(dto.getIsDiffArea() != null,"is_diff_area_login", dto.getIsDiffArea());
        Page page = PageUtil.buildPage(dto.getPage(), dto.getLimit(), dto.getOrderDirection(), dto.getOrderField());
        IPage<LogUserEntity> iPage = logUserDao.selectPage(page, ew);
        List<LogUserDTO> dtoList = new ArrayList<>();
        for (LogUserEntity logUserEntity : iPage.getRecords()) {
            LogUserDTO dto1 = new LogUserDTO();
            BeanUtil.copyProperties(logUserEntity, dto1);
            dtoList.add(dto1);
        }
        PageInfo<LogUserDTO> pageInfo = new PageInfo<>(dtoList, (int)page.getCurrent(), (int)page.getSize(), page.getTotal());
        return RPCResult.success(pageInfo);
    }

    @Override
    public ApiResult<PageInfo<LogUserDTO>> qryUserLogByRecordApi(LogQryParamDTO dto) {
        QueryWrapper<LogUserEntity> ew = new QueryWrapper<>();
        ew.eq("is_del", UserConstant.IS_F);
        ew.eq("flag_type", UserCfg.USER_LOG_FLAG_TYPE_OPER);
        ew.eq(dto.getSiteId() != null,"site_id", dto.getSiteId());
        ew.eq(StrUtil.isNotEmpty(dto.getUserName()),"user_name", dto.getUserName());
        ew.eq(StrUtil.isNotEmpty(dto.getIp()),"login_ip", dto.getIp());
        ew.eq(StrUtil.isNotEmpty(dto.getAccountType()),"account_type", dto.getAccountType());
        ew.eq(StrUtil.isNotEmpty(dto.getType()),"type", dto.getType());
        ew.eq(dto.getIsDiffArea() != null,"is_diff_area_login", dto.getIsDiffArea());
        ew.ge(StrUtil.isNotEmpty(dto.getStartTime()), "create_time", dto.getStartTime());
        ew.le(StrUtil.isNotEmpty(dto.getEndTime()), "create_time", dto.getEndTime());
        Page page = PageUtil.buildPage(dto.getPage(), dto.getLimit(), dto.getOrderDirection(), dto.getOrderField());
        IPage<LogUserEntity> iPage = logUserDao.selectPage(page, ew);
        List<LogUserDTO> dtoList = new ArrayList<>();
        for (LogUserEntity logUserEntity : iPage.getRecords()) {
            LogUserDTO dto1 = new LogUserDTO();
            BeanUtil.copyProperties(logUserEntity, dto1);
            dtoList.add(dto1);
        }
        PageInfo<LogUserDTO> pageInfo = new PageInfo<>(dtoList, (int)page.getCurrent(), (int)page.getSize(), page.getTotal());
        return RPCResult.success(pageInfo);
    }

    @Override
    @Transactional
    public ApiResult deleteBatchApi(String ids) {
        if (StrUtil.isNotEmpty(ids)) {
            return RPCResult.fail();
        }
        String[] idArray = ids.split(",");
        ArrayList<Long> idList = new ArrayList<>();
        for (String s : idArray) {
            idList.add(Long.parseLong(s));
        }
        boolean flag = deleteBatch(idList);
        return flag ? RPCResult.success() : RPCResult.fail();
    }

    @Override
    @Transactional
    public ApiResult deleteApi(Long id) {
        boolean flag = updateById(id);
        return flag ? RPCResult.success() : RPCResult.fail();
    }

    @Override
    @Transactional
    public ApiResult deleteByDateApi(String type, String startTime, String endTime) {
        QueryWrapper<LogUserEntity> ew = new QueryWrapper<>();
        ew.eq("is_del", UserConstant.IS_F);
        ew.ge(true, "create_time", startTime);
        ew.lt("create_time",endTime);
        if (StrUtil.isNotEmpty(type)) {
            ew.eq("flag_type", type);
        }
        List<LogUserEntity> list = logUserDao.selectList(ew);
        List<Long> ids = new ArrayList<>();
        for (LogUserEntity logUserEntity : list) {
            ids.add(logUserEntity.getId());
        }
        boolean flag = deleteBatch(new ArrayList<>(ids));
        return flag ? RPCResult.success() : RPCResult.fail();
    }

    @Override
    public ApiResult<List<HourlyOnLinesDTO>> getHourlyOnLines(Long siteId) {
            if (siteId == null) {
                return RPCResult.custom(UserCodeEnum.SITE_ID_IS_NULL.getCode(), UserCodeEnum.SITE_ID_IS_NULL.getMessage());
            }
            ValueOperations<String, String> ops = template.opsForValue();
            // 取出站点24小时在线会员缓存
            Object data = ops.get(RedisKey.USER_HOURLY_ONLINES + siteId);
            if (data != null) {
                JSONObject jsonObject = JSONUtil.parseObj(data);
                String time = jsonObject.getStr("countTime");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
                DateTime countTime = DateUtil.parse(time, simpleDateFormat);
                DateTime now = DateUtil.parse(DateUtil.now(), simpleDateFormat);
                // 若当前时间未超过过期时间则返回缓存数据,否则重新统计刷新缓存
                if (!now.after(countTime)) {
                    List dtoList = (List) jsonObject.get("data");
                    return RPCResult.success(dtoList);
                }
            }
            ApiResult<SiteDTO> siteDTOById = siteService.findSiteDTOById(siteId);
            if (!RPCResult.checkApiResult(siteDTOById)) {
                return RPCResult.custom(siteDTOById.getCode(), siteDTOById.getMessage());
            }
            SiteDTO siteDTO = siteDTOById.getData();
            if (siteDTO == null) {
                return RPCResult.custom(UserCodeEnum.SITE_NOT_EXIST.getCode(), UserCodeEnum.SITE_NOT_EXIST.getMessage());
            }
            DateTime yesterday = DateUtil.yesterday();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
            List<HourlyOnLinesDTO> dtoList = new ArrayList<>();
            for (int i = 0; i < 24; i++) {
                int hour = DateUtil.hour(yesterday, true);
                String startTime = simpleDateFormat.format(yesterday);
                DateTime offset = yesterday.offset(DateField.HOUR_OF_DAY, 1);
                String  endTime = simpleDateFormat.format(offset);
                Integer num = logUserDao.getHourlyOnLines(startTime, endTime, siteId);
                HourlyOnLinesDTO dto = new HourlyOnLinesDTO();
                dto.setX(hour);
                dto.setY(num == null ? 0 : num);
                dtoList.add(dto);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", dtoList);
            jsonObject.put("countTime", DateUtil.now());
            String HourlyOnLinesData = JSONUtil.toJsonStr(jsonObject);
            ops.set(RedisKey.USER_HOURLY_ONLINES + siteId, HourlyOnLinesData,1, TimeUnit.HOURS);
            return RPCResult.success(dtoList);
    }

    @Override
    public ApiResult<Integer> getPageView(String startDate, String endDate, Long siteId) {
            Assert.isNull(startDate,"startDate为空");
            Assert.isNull(endDate,"endDate为空");
            DateTime startTime = DateUtil.beginOfDay(DateUtil.parseDate(startDate));
            DateTime endTime = DateUtil.endOfDay(DateUtil.parseDate(endDate));
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("startTime",startTime);
            paramMap.put("endTime",endTime);
            paramMap.put("siteId",siteId);
            Integer pageView =  logUserDao.getPageView(paramMap);
            return RPCResult.success(pageView);
    }
}
