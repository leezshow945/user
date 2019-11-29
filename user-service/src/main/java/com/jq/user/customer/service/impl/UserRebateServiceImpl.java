package com.jq.user.customer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.framework.core.exception.Assert;
import com.jq.platform.domain.dto.DomainDTO;
import com.jq.platform.domain.service.DomainService;
import com.jq.platform.site.service.SiteService;
import com.jq.report.member.dto.MemberDTO;
import com.jq.report.member.service.MemberService;
import com.jq.user.code.UserCodeEnum;
import com.jq.user.constant.UserConstant;
import com.jq.user.customer.dao.*;
import com.jq.user.customer.dto.*;
import com.jq.user.customer.entity.*;
import com.jq.user.customer.service.UserInnerService;
import com.jq.user.customer.service.UserRebateInnerService;
import com.jq.user.customer.service.UserRebateTransService;
import com.jq.user.exception.UserException;
import com.jq.user.log.service.LogUserInnerService;
import com.jq.user.oldbonus.service.UserBonusInnerService;
import com.jq.user.proxy.dao.UserProxyDao;
import com.jq.user.proxy.entity.UserProxyEntity;
import com.jq.user.proxy.service.UserProxyInnerService;
import com.jq.user.score.dao.UserRankDao;
import com.jq.user.score.entity.UserRankEntity;
import com.jq.user.support.ListUtils;
import com.jq.user.support.PageUtil;
import com.jq.user.valid.service.ValidUserStatisticsInnerService;
import com.liying.cash.group.api.GroupService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import com.liying.common.service.RPCResult;
import com.liying.trade.order.api.OrderSubtotalService;
import com.liying.trade.user.api.UserBonusService;
import com.liying.trade.user.api.UserFundService;
import com.liying.trade.user.vo.GetUserFundReq;
import com.liying.trade.user.vo.UserFundReq;
import com.liying.trade.user.vo.UserFundResp;
import com.liying.tradehis.order.api.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserRebateServiceImpl implements UserRebateInnerService {

    private final static Logger logger = LoggerFactory.getLogger(UserRebateServiceImpl.class);

    @Resource
    private UserRebateDao userRebateDao;
    @Resource
    private UserDao userDao;
    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private UserInnerService userInnerService;
    @Resource
    private SiteService siteService;
    @Resource
    private com.liying.trade.report.api.UserReportService userReportService;
    @Resource
    private UserRankDao userRankDao;
    @Resource
    private UserFundService userFundService;
    @Resource
    private OrderService orderHisService;
    @Resource
    private OrderSubtotalService orderSubtotalService;
    @Resource
    private com.liying.trade.order.api.OrderService orderService;
    @Resource
    private DomainService domainService;
    @Resource
    private GroupService groupService;
    @Resource
    private BankCardDao bankCardDao;
    @Resource
    private UserTokenDao userTokenDao;
    @Resource
    private UserBonusService userBonusService;
    @Resource
    private UserBonusInnerService userBonusInnerService;
    @Resource
    private UserRebateTransService transferService;
    @Resource
    private LogUserInnerService logUserInnerService;
    @Resource
    private UserProxyInnerService userProxyInnerService;
    @Resource
    private UserProxyDao userProxyDao;
    @Resource
    private ValidUserStatisticsInnerService validUserStatisticsInnerService;
    @Resource
    private MemberService memberService;


    @Override
    @Transactional(readOnly = true)
    public UserRebateEntity getRebate(Long id) {
        return userRebateDao.selectById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getUserLevelPath(String userName, Long siteId) {
        if (StrUtil.isEmpty(userName)){
            throw new UserException(UserCodeEnum.USER_NAME_IS_NULL.getCode());
        }
        if (siteId == null){
            throw new UserException(UserCodeEnum.SITE_ID_IS_NULL.getCode());
        }
        UserEntity userEntityParam = new UserEntity();
        userEntityParam.setUserName(userName);
        userEntityParam.setSiteId(siteId);
        userEntityParam.setIsDel(UserConstant.IS_F);
        userEntityParam.setIsDemo(UserConstant.IS_F);
        UserEntity userEntity = userDao.selectOne(new QueryWrapper<>(userEntityParam));
        if (userEntity == null) {
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode());
        }
        List<UserProxyEntity> allHighUserProxy = userProxyInnerService.getAllHighUserProxy(userEntity.getId(), userEntity.getSiteId());
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (UserProxyEntity userProxyEntity : allHighUserProxy) {
            Map<String, Object> map = new HashMap<>();
            map.put("id",userProxyEntity.getHighUserId());
            map.put("loginName",userProxyEntity.getHighUserName());
            dataList.add(map);
        }
        return dataList;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> findBaseInfo(Long id) {
        UserEntity userEntity = userDao.selectById(id);
        if (userEntity == null || UserConstant.IS_T.equals(userEntity.getIsDel())) {
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode());
        }
        Map<String, Object> map = new HashMap<>();
        UserInfoEntity userInfo = userInfoDao.selectById(id);
        UserRebateEntity rebate = userRebateDao.selectById(id);
        BigDecimal unit = new BigDecimal(100);
        map.put("loginName", userEntity.getUserName());
        String realName = Base64.decodeStr(userInfo.getRealName());
        map.put("realName", realName);
        map.put("isProxy", rebate.getIsProxy());
        map.put("gpcRebate", new BigDecimal(rebate.getGpcRebate()).divide(unit));
        map.put("DpcRebate", new BigDecimal(rebate.getDpcRebate()).divide(unit));
        map.put("FlcRebate", new BigDecimal(rebate.getFlcRebate()).divide(unit));
        map.put("tycpRebate", new BigDecimal(rebate.getTycpRebate()).divide(unit));
        map.put("tyRebate", new BigDecimal(rebate.getTyRebate()).divide(unit));
        map.put("qtRebate", new BigDecimal(rebate.getQtRebate()).divide(unit));
        map.put("lhcRebate0", new BigDecimal(rebate.getLhcRebate0()).divide(unit));
        map.put("lhcRebate1", new BigDecimal(rebate.getLhcRebate1()).divide(unit));
        map.put("lhcRebate2", new BigDecimal(rebate.getLhcRebate2()).divide(unit));
        map.put("lhcRebate3", new BigDecimal(rebate.getLhcRebate3()).divide(unit));
        return map;
    }




    @Override
    public boolean updateBaseInfo(Map userParamMap) {
        Long userId = (Long) userParamMap.get("userId");
        UserEntity userEntity = userDao.selectById(userId);
        if (userEntity == null || UserConstant.IS_T.equals(userEntity.getIsDel())) {
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode());
        }
        String password = (String) userParamMap.get("password");
        if (StrUtil.isNotEmpty(password)) {
            // 更新密码
            UserEntity user = new UserEntity();
            user.setId(userId);
            // 解密密码
            String md5Password = userInnerService.getMD5PwdByRSA(password);
            user.setPassword(md5Password);
            user.setUpdateTime(new Date());
            userDao.updateById(user);
        }
        if (userParamMap.get("realName") != null) {
            //更新真实姓名
            UserInfoEntity userInfo = new UserInfoEntity();
            userInfo.setId(userId);
            userInfo.setRealName((String) userParamMap.get("realName"));
            userInfo.setUpdateTime(new Date());
            userInfoDao.updateById(userInfo);
        }
        // 保存返点信息
        UserRebateEntity rebate = new UserRebateEntity();
        rebate.setId(userId);
        rebate.setGpcRebate((Long) userParamMap.get("gpcRebate"));
        rebate.setFlcRebate((Long) userParamMap.get("flcRebate"));
        rebate.setTycpRebate((Long) userParamMap.get("tycpRebate"));
        rebate.setQtRebate((Long) userParamMap.get("qtRebate"));
        rebate.setTyRebate((Long) userParamMap.get("tyRebate"));
        rebate.setDpcRebate((Long) userParamMap.get("dpcRebate"));
        rebate.setLhcRebate0((Long) userParamMap.get("lhcRebate0"));
        rebate.setLhcRebate1((Long) userParamMap.get("lhcRebate1"));
        rebate.setLhcRebate2((Long) userParamMap.get("lhcRebate2"));
        rebate.setLhcRebate3((Long) userParamMap.get("lhcRebate3"));
        rebate.setUpdateTime(new Date());
        Integer result = userRebateDao.updateById(rebate);
        return result == 1;
    }




    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getLevel(Long siteId) {
        QueryWrapper<UserProxyEntity> userProxyEw = new QueryWrapper<>();
        QueryWrapper<UserRankEntity> rankEw = new QueryWrapper<>();
        userProxyEw.select("max(level) as level");
        rankEw.select("max(rank_level) as rankLevel");
        if (siteId != null) {
            userProxyEw.eq("site_id", siteId);
            rankEw.eq("site_id", siteId);
        }
        List<Map<String,Object>> maxProxyLevel = userProxyDao.selectMaps(userProxyEw);
        List<Map<String,Object>> maxRank = userRankDao.selectMaps(rankEw);
        Map<String, Object> map = new HashMap<>();
        if (CollectionUtil.isNotEmpty(maxProxyLevel)) {
            map.put("maxLevel", maxProxyLevel.get(0).get("level"));
        }
        if (CollectionUtil.isNotEmpty(maxRank)) {
            map.put("maxRank", maxRank.get(0).get("rankLevel"));
        }
        return map;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getRebateByUserNameAndSiteId(String userName, Long siteId) {
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> userRebateMap = userRebateDao.findByUserNameAndSiteId(userName, siteId);
        if (userRebateMap == null) {
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode());
        }
        DomainDTO domainDTO = new DomainDTO();
        domainDTO.setSiteId(siteId);
        domainDTO.setIsEnable(UserConstant.IS_T);
        domainDTO.setIsSpread(UserConstant.IS_T);
        ApiResult<List<DomainDTO>> apiResult = domainService.findListApi(domainDTO);
        if (!RPCResult.checkApiResult(apiResult)) {
            throw new UserException(apiResult.getCode(), apiResult.getMessage());
        }
        List<DomainDTO> data = apiResult.getData();
        if (CollectionUtil.isEmpty(data)) {
            dataMap.put("siteLine", new ArrayList<DomainDTO>());
        } else {
            dataMap.put("siteLine", data);
        }
        dataMap.put("agentMap", userRebateMap);

        return dataMap;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getRelate(Long userId) {
        UserEntity user = userDao.selectById(userId);
        if (user == null || UserConstant.IS_T.equals(user.getIsDel())) {
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode());
        }
        UserInfoEntity userInfoEntity = userInfoDao.selectById(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("userId", user.getId().toString());
        map.put("userName", user.getUserName());
        map.put("createTime", DateUtil.formatDateTime(user.getCreateTime()));
        map.put("lastLoginTime", user.getLastLoginTime() == null ? "无登录记录" : DateUtil.formatDateTime(user.getLastLoginTime()));
        map.put("lastChangePwdTime", user.getLastChangePwdTime() == null ? "无修改密码记录" : DateUtil.formatDateTime(user.getLastChangePwdTime()));
        map.put("loginCount", user.getLoginCount());
        map.put("isDemo", user.getIsDemo() != null ? user.getIsDemo().toString() : "1");
        // 用户资产
        GetUserFundReq req = new GetUserFundReq();
        req.setUserId(userId.toString());
        req.setSiteCode(user.getSiteCode());
        ApiResult<UserFundResp> userFundApiResult = userFundService.getUserFund(req);
        UserInfoEntity userInfo = userInfoDao.selectById(userId);
        UserFundResp userFund = userFundApiResult.getData();
        MemberDTO dto = new MemberDTO();
        dto.setUserId(userId);
        dto.setSiteCode(user.getSiteCode());
        ApiResult<MemberDTO> memberDTOApiResult = memberService.findOneApi(dto);
        MemberDTO data = memberDTOApiResult.getData();
        // 冻结资产
        map.put("freezeAmount", userFund != null ? userFund.getFreezeAmount() : 0);
        // 可用资产
        map.put("canAmount", userFund != null ? userFund.getCanAmount() : 0);
        // 总资产
        map.put("totalAmount", userFund != null ? userFund.getTotalAmount() : 0);
        // 查询用户基本信息
        map.put("regUrl", userInfo.getRegUrl());
        map.put("regSource", userInfo.getRegSource());
        map.put("regIp", userInfo.getRegIp());
        // 可用积分
        map.put("canSore", user.getCanScore());
        // 冻结积分
        map.put("freezeScore", user.getFreezeScore());
        // 积分
        map.put("score", user.getScore());
        //  注册ip
        map.put("regIp", userInfoEntity.getRegIp() == null ? "未知" : userInfoEntity.getRegIp());
        // 注册域名
        map.put("regUrl", userInfoEntity.getRegUrl() == null ? "未知" : userInfoEntity.getRegUrl());
        // 注册来源
        map.put("regSource", userInfoEntity.getRegSource() == null ? "未知" : userInfoEntity.getRegSource());
        //最后登录ip
        map.put("lastLoginIp", user.getLastLoginIp());
        // 用户充值总额
        map.put("totalRecharge",  0);
        // 用户提现总额
        map.put("totalWithdraw",  0);
        // 用户投注总额
        map.put("betMoney", 0);
        // 用户总返点
        map.put("rebateMoney",  0);
        // 用户中奖总额
        map.put("winAmount",  0);
        //  个人总结算
        map.put("winLose", 0);
        if (data == null) {
            return map;
        }
        // 用户充值总额
        map.put("totalRecharge", data.getIncomeMoney() != null ? data.getIncomeMoney() : 0);
        // 用户提现总额
        map.put("totalWithdraw", data.getPayMoney() != null ? data.getPayMoney() : 0);
        // 用户投注总额
        map.put("betMoney", data.getBetMoney() != null ? data.getBetMoney() : 0);
        // 用户总返点
        map.put("rebateMoney", data.getRebateMoney() != null ? data.getRebateMoney() : 0);
        // 用户中奖总额
        map.put("winAmount", data.getWinAmount() != null ? data.getWinAmount() : 0);
        //  个人总结算
        map.put("winLose", data.getProfit() != null ? data.getProfit() : 0);
        return map;
    }


    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getSubUserList(QueryParamDTO dto) {
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", dto.getUserId());
        paramMap.put("siteId", dto.getSiteId());
        paramMap.put("userName", dto.getUserName());
        paramMap.put("startTime", dto.getStartTime());
        paramMap.put("endTime", dto.getEndTime());
        paramMap.put("isOnLine", dto.getIsOnLine());
        paramMap.put("highLevelName",dto.getHighLevelName());
        paramMap.put("isSearchAll",dto.getIsSearchAll());
        if (dto.getOrderField().equals("create_time")) {
           dto.setOrderField("u.create_time");
        }
        Page page = PageUtil.buildPage(dto.getPage(), dto.getLimit(), dto.getOrderDirection(), dto.getOrderField());
        List<UserWrapper> list = userRebateDao.getSubUserList(paramMap, page);
        if (list.size() > 0) {
            StringBuilder ids = new StringBuilder();
            for (UserWrapper userWrapper : list) {
                ids.append(userWrapper.getUserId()).append(",");
                userWrapper.getIsOnline();
            }
            ids.deleteCharAt(ids.length() - 1);
            ApiResult<List<UserFundResp>> userFundApiResult = userFundService.queryUserFund(ids.toString(), dto.getSiteCode());
            List<UserFundResp> data = userFundApiResult.getData();
            if (data != null) {
                Map<Long,Long> canAmountMap = new HashMap<>();
                for (UserFundResp datum : data) {
                    canAmountMap.put(datum.getId(),datum.getCanAmount());
                }
                for (UserWrapper userWrapper : list) {
                    userWrapper.setCanAmount(canAmountMap.get(userWrapper.getUserId()));
                }
            }
        }
        dataMap.put("list", list);
        dataMap.put("total", page.getTotal());
        dataMap.put("current", dto.getPage());
        dataMap.put("size", dto.getLimit());
        return dataMap;
    }

    @Override
    @Transactional(readOnly = true)
    public List getRebateLevelList(Long siteId) {
        return userProxyInnerService.getSiteIdAllLevel(siteId);
    }

    @Override
    public boolean isBanRebate(UserRebateEntity userRebateEntity) {
        List<UserProxyEntity> allHighUserProxy = userProxyInnerService.getAllHighUserProxy(userRebateEntity.getId(), userRebateEntity.getSiteId());
        List<Long> highUserIdList = userProxyInnerService.highUserIdToList(allHighUserProxy);
        highUserIdList.add(userRebateEntity.getId());
        QueryWrapper<UserRebateEntity> ew = new QueryWrapper<>();
        ew.in("id",highUserIdList);
        ew.isNotNull("ban_rebate_time");
        Integer count = userRebateDao.selectCount(ew);
        return count>0;
    }

    @Override
    public Map<Long, Integer> getUserProxyTypeMap(List<Long> idList) {
        Map<Long,Integer> dataMap = new HashMap<>();
        if (CollectionUtil.isEmpty(idList)){
            return dataMap;
        }
        List<UserRebateEntity> userRebateEntityList = userRebateDao.getUserProxyTypeByIdList(idList);
        for (UserRebateEntity userRebateEntity : userRebateEntityList) {
            dataMap.put(userRebateEntity.getId(), userRebateEntity.getIsProxy());
        }
        return dataMap;
    }


    @Override
    @Transactional(readOnly = true)
    public ApiResult<List<Long>> getProxyByUserId(Long userId, Integer type) {
        //1-直属上级，2-直属下级，3-所有下级
        UserEntity userEntity = userDao.selectById(userId);
        if (userEntity == null) {
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode());
        }
        List<Long> idList = new ArrayList<>();
        if (type == 1) {
            UserProxyEntity dirHighUserProxy = userProxyInnerService.getDirHighUserProxy(userId, userEntity.getSiteId());
            if (dirHighUserProxy!=null) {
                idList.add(dirHighUserProxy.getHighUserId());
                return RPCResult.success(idList);
            }
        } else if (type == 2) {
            List<UserProxyEntity> dirSubUserProxy = userProxyInnerService.getDirSubUserProxy(userId, userEntity.getSiteId());
            idList = userProxyInnerService.userIdToList(dirSubUserProxy);
            return RPCResult.success(idList);
        } else if (type == 3) {
            List<UserProxyEntity> allSubUserProxy = userProxyInnerService.getAllSubUserProxy(userId, userEntity.getSiteId());
            idList = userProxyInnerService.userIdToList(allSubUserProxy);
            return RPCResult.success(idList);
        }
        return RPCResult.success(new ArrayList<>());
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResult<PageInfo<UserRebateDTO>> qryUserByPageApi(QueryParamDTO dto) {
        long start = System.currentTimeMillis();
        logger.debug(String.format("分页查询代理会员列表开始, 站点code: %s",dto.getSiteCode()));
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("siteId", dto.getSiteId());
        paramMap.put("isProxy", dto.getIsProxy());
        paramMap.put("level", dto.getLevel());
        paramMap.put("rankLevel", dto.getRankLevel());
        paramMap.put("status", dto.getStatus());
        paramMap.put("highLevelId", dto.getHighLevelId());
        paramMap.put("highLevelName", dto.getHighLevelName());
        paramMap.put("regSource", dto.getRegSource());
        paramMap.put("regIp", dto.getRegIp());
        paramMap.put("lastLoginIp", dto.getLastLoginIp());
        paramMap.put("beginTime", dto.getBeginTime());
        paramMap.put("endTime", dto.getEndTime());
        paramMap.put("orderBy", dto.getOrderBy());
        paramMap.put("searchType", dto.getSearchType());
        paramMap.put("dateType", dto.getDateType());
        paramMap.put("sort", dto.getSort());
        paramMap.put("keyword", dto.getLoginName());
        paramMap.put("rebateType",dto.getRebateType());
        paramMap.put("minRebate",dto.getMinRebate());
        paramMap.put("maxRebate",dto.getMaxRebate());
        Page page = PageUtil.buildPage(dto.getPage(), dto.getLimit(), dto.getOrderDirection(), dto.getOrderField());
        List<UserWrapper> userWrappers = userDao.queryUserByModel(page, paramMap);
        List<UserRebateDTO> dtoList = new ArrayList<>();
        if (userWrappers.size() > 0) {
            StringBuilder ids = new StringBuilder();
            for (UserWrapper userWrapper : userWrappers) {
                ids.append(userWrapper.getId()).append(",");
            }
            ids.deleteCharAt(ids.length() - 1);
            long start1 = System.currentTimeMillis();
            logger.debug(String.format("查询用户资金开始: 站点code: %s,参数: %s",dto.getSiteCode(),ids.toString()));
            ApiResult<List<UserFundResp>> userFundApiResult = userFundService.queryUserFund(ids.toString(), dto.getSiteCode());
            long end1 = System.currentTimeMillis();
            logger.debug(String.format("查询用户资金结束,站点code: %s,查询结果:%s,耗时: %s",dto.getSiteCode(),userFundApiResult.getMessage(),end1-start1));
            List<UserFundResp> data = userFundApiResult.getData();
            Map<Long,UserFundResp> map = new HashMap<>();
            if (data != null) {
                for (UserFundResp datum : data) {
                    map.put(datum.getId(),datum);
                }
            }
            for (UserWrapper userWrapper : userWrappers) {
                UserRebateDTO dto1 = new UserRebateDTO();
                BeanUtil.copyProperties(userWrapper, dto1);
                String realName = Base64.decodeStr(userWrapper.getRealName());
                dto1.setRealName(realName);
                // 启用
                dto1.setControlStatus("0|0|0|0");
                if ("11".equals(userWrapper.getStatus())) {
                    // 停用
                    dto1.setControlStatus("1|0|0|0");
                } else if ("21".equals(userWrapper.getStatus())) {
                    // 暂停投注
                    dto1.setControlStatus("0|1|0|0");
                } else if ("31".equals(userWrapper.getStatus())) {
                    // 冻结
                    dto1.setControlStatus("0|0|1|0");
                } else if ("41".equals(userWrapper.getStatus())) {
                    // 拉黑
                    dto1.setControlStatus("0|0|0|1");
                }
                dtoList.add(dto1);
                UserFundResp userFundResp = map.get(dto1.getId());
                if (userFundResp==null){
                    continue;
                }
                dto1.setTotalAmount(userFundResp.getTotalAmount());
                dto1.setCanAmount(userFundResp.getCanAmount());
                dto1.setFreezeAmount(userFundResp.getFreezeAmount());
            }
        }
        PageInfo<UserRebateDTO> pageInfo = new PageInfo<>(dtoList, (int)page.getCurrent(), (int)page.getSize(), page.getTotal());
        long end = System.currentTimeMillis();
        logger.debug(String.format("分页查询代理会员列表结束..站点code: %s,耗时: %s",dto.getSiteCode(),end-start));
        return RPCResult.success(pageInfo);
    }

    @Override
    public ApiResult<UserDTO> addUserApi(AddUserDTO dto) {
       return transferService.addUserApi(dto);
    }

    @Override
    public ApiResult addUserApi(TestUserDTO dto, Long siteId, String updateUserName, String ip) {
        return transferService.addUserApi(dto, siteId, updateUserName, ip);

    }

    @Override
    public ApiResult<Boolean> updateBaseInfoApi(UserRebateUpdateDTO dto) {
        return transferService.updateBaseInfoApi(dto);
    }


    @Override
    public ApiResult<Boolean> updateBaseInfoApi(UpdateTestUserDTO dto, Long siteId) {
            return transferService.updateBaseInfoApi(dto, siteId);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResult<PageInfo<UserRebateDTO>> getSubUserListApi(QueryParamDTO dto) {
        if("balance".equals(dto.getOrderField())){
           return getUserSortByAmount(dto);
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", dto.getUserId());
        paramMap.put("siteId", dto.getSiteId());
        paramMap.put("userName", dto.getUserName());
        paramMap.put("startTime", dto.getStartTime());
        paramMap.put("endTime", dto.getEndTime());
        paramMap.put("isOnLine", dto.getIsOnLine());
        paramMap.put("highLevelName",dto.getHighLevelName());
        paramMap.put("isSearchAll",dto.getIsSearchAll());
        if (dto.getOrderField().equals("create_time")) {
            dto.setOrderField("u.create_time");
        }
        Page page = PageUtil.buildPage(dto.getPage(), dto.getLimit(), dto.getOrderDirection(), dto.getOrderField());
        List<UserWrapper> list = userRebateDao.getSubUserList(paramMap, page);
        if (list.size() > 0) {
            StringBuilder ids = new StringBuilder();
            for (UserWrapper userWrapper : list) {
                ids.append(userWrapper.getUserId()).append(",");
                userWrapper.getIsOnline();
            }
            ids.deleteCharAt(ids.length() - 1);
            ApiResult<List<UserFundResp>> userFundApiResult = userFundService.queryUserFund(ids.toString(), dto.getSiteCode());
            List<UserFundResp> data = userFundApiResult.getData();
            if (data != null) {
                Map<Long,Long> canAmountMap = new HashMap<>();
                for (UserFundResp datum : data) {
                    canAmountMap.put(datum.getId(),datum.getCanAmount());
                }
                for (UserWrapper userWrapper : list) {
                    userWrapper.setCanAmount(canAmountMap.get(userWrapper.getUserId()));
                }
            }
        }
        List<UserRebateDTO> dataList = (List<UserRebateDTO>) ListUtils.listCopy(list, UserRebateDTO.class);
        PageInfo<UserRebateDTO> pageInfo = new PageInfo<>(dataList, dto.getPage(),dto.getLimit(), page.getTotal());
        return RPCResult.success(pageInfo);
    }

    public ApiResult<PageInfo<UserRebateDTO>> getUserSortByAmount(QueryParamDTO dto) {
        if (StrUtil.isNotEmpty(dto.getHighLevelName())){
            UserEntity userEntity = userInnerService.getUserByUserName(dto.getHighLevelName(), dto.getSiteId());
            if (userEntity==null){
                return RPCResult.success(new PageInfo(new ArrayList(), dto.getPage(),dto.getLimit(), 0));
            }
            dto.setUserId(userEntity.getId());
        }
        List<UserProxyEntity> dirSubUserProxy = userProxyInnerService.getDirSubUserProxy(dto.getUserId(), dto.getSiteId());
        List<Long> list = userProxyInnerService.userIdToList(dirSubUserProxy);
        if (CollectionUtil.isEmpty(list)){
            return RPCResult.success(new PageInfo(new ArrayList(), dto.getPage(),dto.getLimit(), 0));
        }
        String subUserIds = StringUtils.join(list, ",");
        UserFundReq req = new UserFundReq();
        req.setPageNo(dto.getPage().toString());
        req.setPageSize(dto.getLimit().toString());
        req.setUserIds(subUserIds);
        req.setSort(dto.getOrderDirection());
        req.setSiteCode(dto.getSiteCode());
        ApiResult<com.liying.common.PageInfo<UserFundResp>> pageInfoApiResult = userFundService.queryUserFund(req);
        if (!RPCResult.checkApiResult(pageInfoApiResult)){
            return RPCResult.custom(pageInfoApiResult.getCode(),pageInfoApiResult.getMessage());
        }
        com.liying.common.PageInfo<UserFundResp> data = pageInfoApiResult.getData();
        List<UserFundResp> content = data.getContent();
        List<Long> idList = new ArrayList<>();
        Map<Long,Long> canAmountMap = new HashMap<>();
        for (UserFundResp userFundResp : content) {
            idList.add(userFundResp.getId());
            canAmountMap.put(userFundResp.getId(),userFundResp.getCanAmount());
        }
        List<UserRebateDTO> userRebateDTOList = userRebateDao.getUserByIdList(idList,dto.getHighLevelName(),dto.getSiteId());
        for (UserRebateDTO userRebateDTO : userRebateDTOList) {
            Long canAmount = canAmountMap.getOrDefault(userRebateDTO.getUserId(), 0L);
            userRebateDTO.setCanAmount(canAmount);
        }
        if ("desc".equals(dto.getOrderDirection())) {
            userRebateDTOList = userRebateDTOList.stream().sorted((u1, u2) -> u2.getCanAmount().compareTo(u1.getCanAmount())).collect(Collectors.toList());
        }else if ("asc".equals(dto.getOrderDirection())){
            userRebateDTOList = userRebateDTOList.stream().sorted(Comparator.comparing(UserRebateDTO::getCanAmount)).collect(Collectors.toList());
        }
        PageInfo<UserRebateDTO> pageInfo = new PageInfo<>(userRebateDTOList, dto.getPage(),dto.getLimit(), data.getTotalElements());
        return RPCResult.success(pageInfo);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResult<UserDTO> getLevelAndRankApi(Long siteId) {
        Map<String, Object> map = getLevel(siteId);
        UserDTO dto = new UserDTO();
        dto.setMaxLevel((Integer) map.get("maxLevel"));
        dto.setMaxRank((Integer) map.get("maxRank"));
        return RPCResult.success(dto);
    }

    @Transactional(readOnly = true)
    public ApiResult<List<UserDTO>> getUserLevelPathApi(String userName, Long siteId) {
        List<Map<String, Object>> list = getUserLevelPath(userName, siteId);
        List<UserDTO> dtoList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            UserDTO dto = new UserDTO();
            dto.setId((Long) map.get("id"));
            dto.setUserName((String) map.get("loginName"));
            dtoList.add(dto);
        }
        return RPCResult.success(dtoList);
    }

    @Override
    public ApiResult<Map<String, Object>> getRebateByUserNameAndSiteIdApi(String userName, Long siteId) {
        Map<String, Object> rebateMap = getRebateByUserNameAndSiteId(userName, siteId);
        return RPCResult.success(rebateMap);
    }

    @Override
    public ApiResult<Map<String, Object>> getUserRebateByUserNameAndSiteIdApi(String userName, Long siteId) {
        Map<String, Object> rebateMap = userRebateDao.getUserRebateByUserNameAndSiteIdApi(userName, siteId);
        if (CollectionUtil.isEmpty(rebateMap)){
            return RPCResult.success(new HashMap<>());
        }
        Object userId = rebateMap.get("userId");
        UserProxyEntity dirHighUserProxy = userProxyInnerService.getDirHighUserProxy(Long.parseLong(String.valueOf(userId)), siteId);
        if (dirHighUserProxy!=null) {
            rebateMap.put("highLevelAccount", dirHighUserProxy.getHighUserName());
        }
        return RPCResult.success(rebateMap);
    }

    @Override
    public ApiResult<UserRebateDTO> getUserInfoAndPathApi(String userName, Long siteId) {
        Map<String, Object> resultMap = userRebateDao.getUserInfoAndPathApi(userName, siteId);
        if (CollectionUtil.isEmpty(resultMap)){
            return RPCResult.success(new UserRebateDTO());
        }
        Object userIdObj = resultMap.get("userId");
        if (userIdObj!=null) {
            Long userId = Long.parseLong(String.valueOf(userIdObj));
            UserProxyEntity dirHighUserProxy = userProxyInnerService.getDirHighUserProxy(userId, siteId);
            resultMap.put("highAccount",dirHighUserProxy!=null ? dirHighUserProxy.getHighUserName():null);
            String path =  userProxyInnerService.getPath(userId,siteId);
            resultMap.put("path",path);
        }
        UserRebateDTO dto = BeanUtil.mapToBean(resultMap, UserRebateDTO.class, true);
        return RPCResult.success(dto);
    }

    @Override
    public ApiResult<List<Map<String, Object>>> getAllLevelBySiteIdApi(Long siteId) {
        QueryWrapper<UserProxyEntity> ew = new QueryWrapper<>();
        ew.select("distinct `level`");
        ew.eq("site_id",siteId);
        ew.ne("level",UserConstant.IS_ZERO);
        List<Map<String, Object>> list = userProxyDao.selectMaps(ew);
        return RPCResult.success(list);
    }

    @Override
    public ApiResult<List<UserDTO>> getUserType(List<Long> ids, Long siteId) {
        List<UserDTO> dtoList = userDao.selectUserType(ids, siteId);
        return RPCResult.success(dtoList);
    }

    @Override
    public ApiResult<List<UserRebateDTO>> queryUserInfoAndPathByIdApi(List<Long> idList, Long siteId) {
        List<UserRebateDTO> resultList = new ArrayList<>();
        if (CollectionUtil.isEmpty(idList)){
            return RPCResult.success(resultList);
        }
        resultList = userRebateDao.queryUserInfoAndPathById(idList,siteId);
        Map<Long, List<Long>> allHighUserIdMap = userProxyInnerService.getAllHighUserIdMap(idList, siteId);
        for (UserRebateDTO userRebateDTO : resultList) {
            List<Long> list = allHighUserIdMap.get(userRebateDTO.getUserId());
            String path = org.apache.commons.lang3.StringUtils.join(list, ",");
            userRebateDTO.setPath(path);
        }
        return RPCResult.success(resultList);
    }

    @Override
    public ApiResult<List<UserRebateDTO>> queryUserInfoAndPathByIdApi(List<Long> idList, Long siteId,Integer isProxy) {
        List<UserRebateDTO> resultList = new ArrayList<>();
        if (CollectionUtil.isEmpty(idList)){
            return RPCResult.success(resultList);
        }
        resultList = userRebateDao.queryUserInfoAndPathByIdList(idList,siteId,isProxy);
        Map<Long, List<Long>> allHighUserIdMap = userProxyInnerService.getAllHighUserIdMap(idList, siteId);
        for (UserRebateDTO userRebateDTO : resultList) {
            List<Long> list = allHighUserIdMap.get(userRebateDTO.getUserId());
            String path = org.apache.commons.lang3.StringUtils.join(list, ",");
            userRebateDTO.setPath(path);
        }
        return RPCResult.success(resultList);
    }

    @Override
    public ApiResult<Map<String, Object>> initUpdateApi(Long userId, Long siteId) {
        UserEntity userModel = userDao.findById(userId);
        if (userModel == null) {
            return RPCResult.success();
        }
        logger.info("查询用户本身");

        ApiResult<Map<String, Object>> rebateByUserNameAndSiteIdApi = this.getUserRebateByUserNameAndSiteIdApi(userModel.getUserName(), siteId);
        if (!RPCResult.checkApiResult(rebateByUserNameAndSiteIdApi)) {
            throw new UserException(rebateByUserNameAndSiteIdApi.getCode(), rebateByUserNameAndSiteIdApi.getMessage());
        }
        Map<String, Object> user = rebateByUserNameAndSiteIdApi.getData();
        logger.info(String.format("查询用户[{%s}]自己返点,user:[{%s}]", userModel.getUserName(), user));
        String real = (String) user.get("realName");
        if (StrUtil.isNotEmpty(real)
                || null != real) {
            String realName = Base64.decodeStr(String.valueOf(user.get("realName")));
            user.put("realName", realName);
        }
        logger.info("查询用户上级");
        Map<String, Object> highMap = new HashMap<String, Object>();
        highMap.put("gpcRebate", 10000);//高频彩
        highMap.put("flcRebate", 10000);//棋牌
        highMap.put("tycpRebate", 10000);//体育彩票
        highMap.put("qtRebate", 10000);//其他
        highMap.put("tyRebate", 10000);//体育
        highMap.put("dpcRebate", 10000);//低频彩
        highMap.put("lhcRebate0", 10000);//六合彩组0
        highMap.put("lhcRebate1", 10000);//六合彩组1
        highMap.put("lhcRebate2", 10000);//六合彩组2
        highMap.put("lhcRebate3", 10000);//六合彩组3
        if (StrUtil.isNotBlank(String.valueOf(user.get("highLevelAccount")))) {
            //若是上级空，则返点都为100。厅主默认无上级
            ApiResult<Map<String, Object>> highLevelAccountApi = this.getRebateByUserNameAndSiteIdApi(user.get("highLevelAccount").toString(), siteId);
            if (!RPCResult.checkApiResult(highLevelAccountApi)) {
                throw new UserException(highLevelAccountApi.getCode(), highLevelAccountApi.getMessage());
            }
            Map<String, Object> high = highLevelAccountApi.getData();
            if (null != high) {
                Map<String, Object> highRebate = (Map<String, Object>) high.get("agentMap");
                highMap.put("gpcRebate", highRebate.get("gpcRebate"));//高频彩
                highMap.put("flcRebate", highRebate.get("flcRebate"));//福利彩
                highMap.put("tycpRebate", highRebate.get("tycpRebate"));//体育彩票
                highMap.put("qtRebate", highRebate.get("qtRebate"));//其他
                highMap.put("tyRebate", highRebate.get("tyRebate"));//体育
                highMap.put("dpcRebate", highRebate.get("dpcRebate"));//低频彩
                highMap.put("lhcRebate0", highRebate.get("lhcRebate0"));//六合彩组0
                highMap.put("lhcRebate1", highRebate.get("lhcRebate1"));//六合彩组1
                highMap.put("lhcRebate2", highRebate.get("lhcRebate2"));//六合彩组2
                highMap.put("lhcRebate3", highRebate.get("lhcRebate3"));//六合彩组3
            }
        }
        logger.info(String.format("查询用户上级[{%s}]返点user:[{%s}]", user.get("highLevelAccount"), highMap));
        highMap.put("user", user);
        return RPCResult.success(highMap);
    }

    @Override
    public ApiResult<UserDTO> getUserFundByUserIdAndSiteIdApi(Long userId, Long siteId) {
        GetUserFundReq req = new GetUserFundReq();
        req.setUserId(userId.toString());
        req.setSiteCode(siteId.toString());
        ApiResult<UserFundResp> userFund = userFundService.getUserFund(req);
        if (!RPCResult.checkApiResult(userFund)) {
            throw new UserException(userFund.getCode(), userFund.getMessage());
        }
        UserFundResp data = userFund.getData();
        UserDTO dto = new UserDTO();
        BeanUtil.copyProperties(data, dto);
        return RPCResult.success(dto);
    }

    @Override
    public ApiResult<Boolean> transferBatchApi(Long destId, String destName, String userIds, String managerUseName) {
        return transferService.transferBatchApi(destId, destName, userIds, managerUseName);
    }

    @Override
    public ApiResult<Boolean> transferApi(Long destId, Long sourceId, String destName, String sourceName, Integer isTransfer, String managerUserName) {
        return transferService.transfer(destId, sourceId, destName, sourceName, isTransfer, managerUserName);
    }

    @Override
    public ApiResult<Map<String, Object>> getRelateApi(Long userId) {
        Map<String, Object> relate = getRelate(userId);
        return RPCResult.success(relate);
    }

    @Override
    public ApiResult updateRebate(Long destId, List<String> userNameList, Long siteId) {
        return transferService.updateRebate(destId,userNameList,siteId);
    }

    @Override
    public ApiResult<UserRebateDTO> getUserDetailApi(Long id) {
        UserRebateDTO dto = userRebateDao.getUserDetail(id);
        if (dto == null) {
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode());
        }
        UserTokenEntity userTokenEntity = userTokenDao.selectById(id);
        if (userTokenEntity == null || userTokenEntity.getAccessExpire().before(new Date())) {
            dto.setIsOnline(UserConstant.IS_F);
        } else {
            dto.setIsOnline(UserConstant.IS_T);
        }
        String userProxyLine = userProxyInnerService.getPathLine(id, dto.getSiteId());
        Integer level = userProxyInnerService.findUserLevel(id, dto.getSiteId());
        dto.setLevel(level);
        String realName = Base64.decodeStr(dto.getRealName());
        dto.setRealName(realName);
        dto.setPath(userProxyLine);
        GetUserFundReq req = new GetUserFundReq();
        req.setUserId(id.toString());
        req.setSiteCode(dto.getSiteCode());
        ApiResult<UserFundResp> userFund = userFundService.getUserFund(req);
        UserFundResp data = userFund.getData();
        if (data!=null) {
            dto.setTotalAmount(data.getTotalAmount());
            dto.setCanAmount(data.getCanAmount());
            dto.setFreezeAmount(data.getFreezeAmount());
        }
        Long bonusTotal = userBonusInnerService.getTotalBonus(id, 0);
        Long contractBonusTotal= userBonusInnerService.getTotalBonus(id, 1);
        Long wagesTotal = userBonusInnerService.getTotalBonus(id, 2);
        Long contractWagesTotal = userBonusInnerService.getTotalBonus(id, 3);
        Long totalMakeWater = userBonusInnerService.getTotalMakeWater(id);
        dto.setBonusTotal(bonusTotal!=null?bonusTotal:0);
        dto.setContractBonusTotal(contractBonusTotal!=null?contractBonusTotal:0);
        dto.setWagesTotal(wagesTotal!=null?wagesTotal:0);
        dto.setContractWagesTotal(contractWagesTotal!=null?contractWagesTotal:0);
        dto.setTotalMakeWater(totalMakeWater!=null?totalMakeWater:0);
        Integer loginCountByDay = logUserInnerService.getLoginCountByDay(id);
        dto.setLoginCountByDay(loginCountByDay!=null?loginCountByDay:0);
        QueryWrapper<BankCardEntity> ew = new QueryWrapper<>();
        ew.eq("user_id", id);
        ew.eq("is_enable", UserConstant.IS_T);
        ew.eq("is_default", UserConstant.IS_T);
        ew.eq("is_del", UserConstant.IS_F);
        List<BankCardEntity> bankCardEntities = bankCardDao.selectList(ew);
        if (CollectionUtil.isNotEmpty(bankCardEntities)) {
            BankCardEntity bankCardEntity = bankCardEntities.get(0);
            dto.setDefaultBankName(bankCardEntity.getBankName());
            String cardNo = Base64.decodeStr(bankCardEntity.getCardNo());
            dto.setCardNo(cardNo);
        } else {
            dto.setDefaultBankName("无默认银行卡");
            dto.setCardNo("无");
        }
        return RPCResult.success(dto);
    }

    @Override
    public ApiResult<String> getProxyLine(Long userId) {
        UserEntity userEntity = userDao.selectById(userId);
        if (userEntity==null){
            return RPCResult.fail(UserCodeEnum.USER_NOT_EXIST.getCode(),UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        String userProxyLine = userProxyInnerService.getUserProxyLine(userId, userEntity.getSiteId());
        if (StrUtil.isNotEmpty(userProxyLine)&& userProxyLine.startsWith("default")){
            userProxyLine = userProxyLine.replaceFirst("default", "厅主");
        }
        return RPCResult.success(userProxyLine);
    }

    @Override
    public ApiResult<List<Long>> getEffectUserId(List<Long> userIds, Long siteId) {
        Assert.isNull(siteId,"siteId为空");
        List<Long> effectUserIdList = new ArrayList<>();
        if (CollectionUtil.isEmpty(userIds)){
            return RPCResult.success(effectUserIdList);
        }
        effectUserIdList = validUserStatisticsInnerService.getValidUserIdList(userIds, siteId);
        return RPCResult.success(effectUserIdList);
    }

    @Override
    public ApiResult<List<Long>> getDefaultAndDirectUserIdBySiteCode(String siteCode) {
        QueryWrapper<UserProxyEntity> ew = new QueryWrapper<>();
        ew.eq("high_user_name","default");
        ew.eq("site_code",siteCode);
        ew.in("level",new Integer[]{0,1});
        List<UserProxyEntity> userProxyEntities = userProxyDao.selectList(ew);
        List<Long> idList = userProxyInnerService.userIdToList(userProxyEntities);
        return RPCResult.success(idList);
    }

    @Override
    public ApiResult<List<UserRebateDTO>> getHighLevelIdAndRank(List<Long> ids) {
        if (CollectionUtil.isEmpty(ids)){
            return RPCResult.success(new ArrayList<>());
        }
        List<UserRebateDTO> list =  userRebateDao.getHighLevelIdAndRank(ids);
        return RPCResult.success(list);
    }

    @Override
    public ApiResult<String> getPathByApi(Long userId) {
        UserEntity userEntity = userDao.selectById(userId);
        if (userEntity==null){
            return RPCResult.fail(UserCodeEnum.USER_NOT_EXIST.getCode(),UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        String path = userProxyInnerService.getPath(userId,userEntity.getSiteId());
        return RPCResult.success(path);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResult<UserRebateDTO> getRebateByUserId(Long userId) {
        UserRebateEntity userRebateEntity = userRebateDao.selectById(userId);
        if (userRebateEntity == null) {
            return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        UserProxyEntity dirHighUserProxy = userProxyInnerService.getDirHighUserProxy(userId, userRebateEntity.getSiteId());
        UserRebateDTO dto = new UserRebateDTO();
        BeanUtil.copyProperties(userRebateEntity, dto);
        if (dirHighUserProxy!=null) {
            dto.setHighAccount(dirHighUserProxy.getHighUserName());
            dto.setHighLevelId(dirHighUserProxy.getHighUserId());
        }
        UserEntity userEntity = userDao.selectById(userId);
        if (userEntity != null) {
            dto.setLoginName(userEntity.getUserName());
            dto.setPassword(userEntity.getPassword());
            dto.setStatus(userEntity.getStatus().toString());
        }
        UserInfoEntity userInfoEntity = userInfoDao.selectById(userId);
        if (userInfoEntity != null) {
            String mobile = Base64.decodeStr(userInfoEntity.getMobile());
            String realName = Base64.decodeStr(userInfoEntity.getRealName());
            dto.setMobile(mobile);
            dto.setRealName(realName);
        }
        boolean banRebate = isBanRebate(userRebateEntity);
        dto.setBanRebate(banRebate);
        logger.debug("获取用户返点及相关信息查询结果:userId={},userName={},siteCode={},isBanRebate={}",
                dto.getId(),dto.getLoginName(),dto.getSiteCode(),dto.isBanRebate());
        return RPCResult.success(dto);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResult<Map<Long, List<Long>>> getHighLevelProxyByIdList(List<Long> idList) {
        if (CollectionUtil.isEmpty(idList)){
            return RPCResult.success(new HashMap<>());
        }
        List<UserEntity> userEntities = userDao.selectBatchIds(idList);
        if (CollectionUtil.isEmpty(userEntities)){
            return RPCResult.success(new HashMap<>());
        }
        //总的代理线Map
        Long siteId = userEntities.get(0).getSiteId();
        Map<Long, List<Long>> allHighUserIdMap = userProxyInnerService.getAllHighUserIdMap(idList, siteId);
        Map<Long,Map> proxyLine = new HashMap<>();
        for(UserEntity entity : userEntities){
            List<Long> allHighUserIdList = allHighUserIdMap.get(entity.getId());
            if(CollectionUtil.isEmpty(allHighUserIdList)){
                continue;
            }
            //每个用户的上级
            for(int i = 0; i <allHighUserIdList.size();i++ ){
                //当前用户所属的代理线
                Long proxy = allHighUserIdList.get(i);
                //代理线中所有的下级
                Map<Long,Long> userLine = proxyLine.get(proxy);
                //如果代理线不存在，则新建
                if(userLine == null){
                    userLine = new HashMap<>();
                }
                userLine.put(entity.getId(),entity.getId());
                for(int j = i; j < allHighUserIdList.size(); j ++){
                    Long id = allHighUserIdList.get(j);
                    //将用户所有上级存入代理线中，包含了去重于合并操作
                    userLine.put(id, id);
                }
                proxyLine.put(proxy,userLine);
            }
            //如果代理线中没有当前用户,则将用户本身加入代理线
            if(!proxyLine.containsKey(entity.getId())){
                Map<Long,Long> self = new HashMap<>();
                self.put(entity.getId(),entity.getId());
                proxyLine.put(entity.getId(),self);
            }
        }

        //组装数据格式
        Map<Long,List<Long>> result = new HashMap<>();
        for(Long key : proxyLine.keySet()){
            Map child = proxyLine.get(key);
            result.put(key,new ArrayList<>(child.keySet()));
        }
        return RPCResult.success(result);
    }

    @Override
    public ApiResult<Map<Long, Boolean>> getUserIsSubLevel(List<Long> idList) {
        Map<Long, Integer> longIntegerMap = userProxyInnerService.countAllSubUserLineByList(idList);
        Map<Long,Boolean> booleanMap = new HashMap<>();
        for (Long aLong : idList) {
            booleanMap.put(aLong,longIntegerMap.get(aLong)!=null);
        }
        return RPCResult.success(booleanMap);
    }

    @Override
    public ApiResult<Map<Long, List<Long>>> getSubIdListByIdListApi(List<Long> idList) {
        Map<Long, List<Long>> allSubUserMapByIdList = userProxyInnerService.getAllSubUserMapByIdList(idList);
        return RPCResult.success(allSubUserMapByIdList);
    }

    @Override
    public ApiResult<PageInfo<UserRebateDTO>> getBanRebateUserListApi(QueryParamDTO dto) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("siteId",dto.getSiteId());
        paramMap.put("searchType",dto.getSearchType());
        paramMap.put("keyword",dto.getUserName());
        paramMap.put("dateType",dto.getDateType());
        paramMap.put("beginTime",dto.getBeginTime());
        paramMap.put("endTime",dto.getEndTime());
        if (dto.getSearchType()!=null&&dto.getSearchType()==3){
            String realName = Base64.encode(dto.getUserName());
            paramMap.put("keyword",realName);
        }
        Page page = PageUtil.buildPage(dto.getPage(), dto.getLimit(), dto.getOrderDirection(), dto.getOrderField());
        List<UserRebateDTO> dtoList = userDao.getBanRebateUserList(paramMap,page);
        PageInfo<UserRebateDTO> pageInfo = new PageInfo<>(dtoList, (int)page.getCurrent(), (int)page.getSize(), page.getTotal());
        return RPCResult.success(pageInfo);
    }

    @Override
    public ApiResult setBanRebateUserApi(String userNames, Long siteId) {
        if (StrUtil.isEmpty(userNames)){
            return RPCResult.custom(UserCodeEnum.USER_NAME_IS_NULL.getCode(),UserCodeEnum.USER_NAME_IS_NULL.getMessage());
        }
        if (siteId==null){
            return RPCResult.custom(UserCodeEnum.SITE_ID_IS_NULL.getCode(),UserCodeEnum.SITE_ID_IS_NULL.getMessage());
        }
        String[] userName = userNames.split(",");
        QueryWrapper<UserEntity> ew = new QueryWrapper<>();
        ew.in("user_name",userName);
        ew.eq("is_del",UserConstant.IS_F);
        ew.eq("is_demo",UserConstant.IS_F);
        ew.eq("site_id",siteId);
        List<UserEntity> userEntities = userDao.selectList(ew);
        if (CollectionUtil.isEmpty(userEntities)){
            return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(),UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        List<Long> ids = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            ids.add(userEntity.getId());
        }
        QueryWrapper<UserRebateEntity> userRebateEw = new QueryWrapper<>();
        userRebateEw.in("id",ids);
        userRebateEw.eq("is_proxy",UserConstant.IS_T);
        userRebateEw.isNull("ban_rebate_time");
        UserRebateEntity userRebateEntity = new UserRebateEntity();
        userRebateEntity.setBanRebateTime(new Date());
        Integer update = userRebateDao.update(userRebateEntity, userRebateEw);
        if (update==0){
            throw new UserException(UserCodeEnum.BAN_USER_REBATE_FAIL.getCode());
        }
        return RPCResult.success();
    }

    @Override
    public ApiResult deleteBanRebateUserApi(List<Long> idList, Long siteId) {
        if (CollectionUtil.isEmpty(idList)){
            return RPCResult.custom(UserCodeEnum.USER_ID_IS_NULL.getCode(),UserCodeEnum.USER_ID_IS_NULL.getMessage());
        }
        if (siteId==null){
            return RPCResult.custom(UserCodeEnum.SITE_ID_IS_NULL.getCode(),UserCodeEnum.SITE_CODE_IS_NULL.getMessage());
        }
        Integer update = userRebateDao.deleteBanRebateUser(idList,siteId);
        if (update==0){
            return RPCResult.custom(UserCodeEnum.BAN_REBATE_USER_DELETED.getCode(),UserCodeEnum.BAN_REBATE_USER_DELETED.getMessage());
        }
        return RPCResult.success();
    }

    @Override
    public ApiResult<UserRebateDTO> getUserRebateBy(String userName, Long siteId, Integer isDemo) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userName",userName);
        paramMap.put("siteId",siteId);
        paramMap.put("isDemo",isDemo);
        UserRebateDTO userRebateDTO =  userRebateDao.getUserRebateBy(paramMap);
        if (userRebateDTO==null){
            return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(),UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        UserProxyEntity dirHighUserProxy = userProxyInnerService.getDirHighUserProxy(userRebateDTO.getUserId(), siteId);
        if (dirHighUserProxy!=null){
            userRebateDTO.setHighAccount(dirHighUserProxy.getHighUserName());
        }
        return RPCResult.success(userRebateDTO);
    }

    @Override
    public ApiResult<?> updateUserType(Long userId, String siteCode) {
        UserRebateEntity userRebateEntity = userRebateDao.selectById(userId);
        if (null == userRebateEntity) {
            return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(),UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        if (!userRebateEntity.getSiteCode().equals(siteCode)) {
            return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(),UserCodeEnum.USER_NOT_EXIST.getMessage());
        }

        userRebateEntity.setIsProxy(1);
        Integer count=userRebateDao.updateById(userRebateEntity);
        if (count == 1) {
            return RPCResult.success();
        }
        return RPCResult.fail();
    }

    @Override
    public ApiResult<UserRebateDTO> findByUserIdApi(Long userId) {
        UserRebateEntity rebate = this.getRebate(userId);
        UserRebateDTO dto = new UserRebateDTO();
        if(rebate !=null) {
            BeanUtil.copyProperties(rebate, dto);
        }
        return RPCResult.success(dto);
    }
}
